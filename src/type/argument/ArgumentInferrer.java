package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Functor;
import type.ConstraintSet;
import type.PolarizedPath;
import type.ReceiveConstraint;
import type.RootPath;
import type.TypeConstraintException;
import type.TypeEnv;
import type.UnifyConstraint;

import compile.structure.Atom;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

/**
 * ルールごとに、アトム引数の型／モードを推論する。
 * 文脈定義がルールごとに扱うほうが都合が良い為。
 * @author kudo
 *
 */
public class ArgumentInferrer {
	private RuleStructure rule;
	
	private Set<ContextDef> defs;
	
	private ConstraintSet constraints;

	/** */
	public ArgumentInferrer(RuleStructure rule, ConstraintSet constraints){
		this.rule = rule;
		this.constraints = constraints;
	}
	/**
	 * グローバルルート膜に対してのみ呼ばれる
	 * @param top
	 */
	public ArgumentInferrer(Membrane top, ConstraintSet constraints){
		RuleStructure tmprule = new RuleStructure(new Membrane(null),"tmp");
		tmprule.leftMem = new Membrane(null);
		tmprule.rightMem = top;
		this.rule = tmprule;
		this.constraints = constraints;
	}

	public void infer() throws TypeConstraintException{
		defs = new HashSet<ContextDef>();
		inferArgumentRule(rule);
	}
	/**
	 * @param mem
	 * @param freelinks
	 *            free links already checked
	 * @return free links in the process at mem
	 */
	private Set<LinkOccurrence> inferArgumentMembrane(Membrane mem, Set<LinkOccurrence> freelinks) throws TypeConstraintException{

		//ルールについて走査する
		Iterator<RuleStructure> itr = mem.rules.iterator();
		while (itr.hasNext()) {
			new ArgumentInferrer(itr.next(),constraints).infer();
		}

		// 子膜について走査する
		Iterator<Membrane> itm = mem.mems.iterator();
		while (itm.hasNext()) {
			freelinks = inferArgumentMembrane(itm.next(), freelinks);
		}

		// この時点で、子孫膜に出現する全てのアトム／プロセス文脈について、局所リンクの処理は終わっている
		
		Iterator<Atom> ita = mem.atoms.iterator();
		while (ita.hasNext()) {
			Atom atom = ita.next();
			int out = TypeEnv.outOfPassiveAtom(atom);
			if (out == TypeEnv.CONNECTOR) continue; // '='/2だったら無視する
			else
				freelinks = inferArgumentAtom(atom, freelinks);
		}

		// この時点で、子孫膜に出現する全てのアトム／プロセス文脈、およびこの膜のアトムの引数の処理は終わっている
		// つまり残るはこの膜に出現するプロセス文脈と、この膜の自由リンクのみ
		
		// プロセス文脈
		Iterator<ProcessContext> itp = mem.processContexts.iterator();
		while(itp.hasNext()){
			ContextDef def = itp.next().def;
			if(!defs.contains(def))defs.add(def);
		}
		//型付きプロセス文脈
		Iterator<ProcessContext> ittp = mem.typedProcessContexts.iterator();
		while(ittp.hasNext()){
			ContextDef def = ittp.next().def;
			if(!defs.contains(def))defs.add(def);
		}
		return freelinks;
	}

	/**
	 * inferrence
	 * 
	 * @param rule
	 */
	private void inferArgumentRule(RuleStructure rule) throws TypeConstraintException{
		// 左辺／右辺それぞれについて型／モードを解決し、1回出現するリンクを集める
		Set<LinkOccurrence> freelinksLeft = inferArgumentMembrane(rule.leftMem, new HashSet<LinkOccurrence>());
		Set<LinkOccurrence> freelinksRight = inferArgumentMembrane(rule.rightMem, new HashSet<LinkOccurrence>());
		Iterator<LinkOccurrence> it = freelinksLeft.iterator();
		while (it.hasNext()) {
			LinkOccurrence leftlink = it.next();
			LinkOccurrence rightlink = TypeEnv.getRealBuddy(leftlink);
			if (!freelinksRight.contains(rightlink)){ // リンクが左辺／右辺出現なら
				throw new TypeConstraintException("link occurs once in a rule.");
			}
			addConstraintAboutLinks(1, leftlink, rightlink);
		}
		//プロセス文脈について処理する
		Iterator<ContextDef> itd = defs.iterator();
		while(itd.hasNext()){
			ContextDef def = itd.next();
			Iterator<ProcessContext> itp = def.rhsOccs.iterator();
			while(itp.hasNext()){
				processExplicitLinks((ProcessContext)def.lhsOcc, itp.next());
			}
		}
	}

	/**
	 * アトムの引数を走査し、1回目の出現はfreelinksに登録し、
	 * 2回目の出現であれば局所リンクとして制約を課す。
	 * ただしプロセス文脈に接続している場合は無視する。
	 * @param atom
	 * @param freelinks
	 * @return
	 */
	private Set<LinkOccurrence> inferArgumentAtom(Atom atom, Set<LinkOccurrence> freelinks)throws TypeConstraintException{
		for (int i = 0; i < atom.args.length; i++) {
			LinkOccurrence lo = atom.args[i];
			LinkOccurrence b = TypeEnv.getRealBuddy(lo);
			if(b.atom instanceof ProcessContext)continue; // プロセス文脈に接続している
			if (freelinks.contains(b)) { // 局所リンク
				addConstraintAboutLinks(-1, lo, b);
				freelinks.remove(b);
			} else
				freelinks.add(lo);
		}
		return freelinks;
	}
	
	/** プロセス文脈の左辺／右辺出現のそれぞれの引数に対して同じであるという制約をかける */
	private void processExplicitLinks(ProcessContext lhsOcc, ProcessContext rhsOcc){
		for(int i=0;i<lhsOcc.args.length;i++){
			LinkOccurrence lhsPartner = TypeEnv.getRealBuddy(lhsOcc.args[i]);
			LinkOccurrence rhsPartner = TypeEnv.getRealBuddy(rhsOcc.args[i]);
			if(rhsPartner.atom instanceof Atom){
				if(TypeEnv.isLHSAtom((Atom)rhsPartner.atom))
					addUnifyConstraint(-1,lhsPartner,rhsPartner);
				else
					addUnifyConstraint(1,lhsPartner,rhsPartner);
			}
			else{ // 右辺出現がプロセス文脈と継っている
				// そいつの左辺出現の相方をとってくる
				LinkOccurrence partnerOfPartner = TypeEnv.getRealBuddy(((ProcessContext)rhsPartner.atom).def.lhsOcc.args[i]);
				addUnifyConstraint(-1,lhsPartner, partnerOfPartner);
			}
		}
	}

	/**
	 * If $lo or $b is output-argument, add receive-passive-constraint. Else,
	 * add unify-constraint.
	 * 
	 * @param sign
	 * @param lo
	 * @param b
	 */
	private void addConstraintAboutLinks(int sign, LinkOccurrence lo, LinkOccurrence b) throws TypeConstraintException{
		int out = TypeEnv.outOfPassiveAtom((Atom)lo.atom);
		if(out == lo.pos){ // データアトムの出力引数
			if(TypeEnv.outOfPassiveAtom((Atom)b.atom) == b.pos)//!= TypeEnv.ACTIVE)
				throw new TypeConstraintException("MODE ERROR : output arguments connected each other.");
			else addReceiveConstraint(-sign, b, ((Atom)lo.atom).functor);
		}
		else{
			if(TypeEnv.outOfPassiveAtom((Atom)b.atom) == b.pos) //!= TypeEnv.ACTIVE)
				addConstraintAboutLinks(sign, b, lo);
			else addUnifyConstraint(sign, lo, b);
		}
	}

	private void addUnifyConstraint(int sign, LinkOccurrence lo,
			LinkOccurrence b) {
		constraints.add(new UnifyConstraint(new PolarizedPath(1, new RootPath(lo)),
				new PolarizedPath(sign, new RootPath(b))));
	}

	private void addReceiveConstraint(int sign, LinkOccurrence b, Functor f) {
		constraints.add(new ReceiveConstraint(new PolarizedPath(sign, new RootPath(b)), f));
	}


}

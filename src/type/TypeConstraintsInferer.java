package type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import runtime.Functor;

import compile.structure.Atom;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

/**
 * This class infers type constrains from COMPILE STRUCTURE
 * 
 * @author kudo
 * @since 2006/06/03 (Sat.)
 */
public class TypeConstraintsInferer {

	/** membrane contains all processes */
	private Membrane root;

	private ConstraintSet constraints = new ConstraintSet();

	/**
	 * @param root
	 */
	public TypeConstraintsInferer(Membrane root) {
		this.root = root;
	}

	/**
	 * distinguish active atom from passive atom
	 * 
	 * @param atom
	 * @return
	 */
	private int outOfPassiveAtom(Atom atom) {
		return TypeEnv.outOfPassiveAtom(atom);
	}
	private boolean isActiveAtom(Atom atom){
		return outOfPassiveAtom(atom) == TypeEnv.ACTIVE;
	}

	public void infer() throws TypeConstraintException {
		// 全ての膜について、ルールの左辺最外部出現かどうかの情報を得る
		collectLHSMems();
		
		// TODO Active Head Condition をチェックする
		
		// 個数制約を推論する
		new QuantityInferrer().inferQuantity();
		
		// 出現制約を推論する
		inferOccurrence();
		// 全ての引数についてモード変数、型変数を振る
		inferArgument();
		solvePathes();
		constraints.solveUnifyConstraints();
	}

	/** 左辺膜および左辺出現膜の集合 */
	private Set<Membrane> lhsmems = new HashSet<Membrane>();

	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * @param mem
	 */
	private void collectLHSMems(){
		Iterator<RuleStructure> it = root.rules.iterator();
		while(it.hasNext()){
			collectLHSMems(it.next());
		}
	}
	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * @param rule
	 */
	private void collectLHSMems(RuleStructure rule){
		collectLHSMem(rule.leftMem);
//		 左辺にルールは出現しない
		Iterator<RuleStructure> it = rule.rightMem.rules.iterator();
		while(it.hasNext()){
			collectLHSMems(it.next());
		}
	}
	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * @param mem 左辺出現膜
	 */
	private void collectLHSMem(Membrane mem){
		lhsmems.add(mem);
		Iterator it = mem.mems.iterator();
		while(it.hasNext()){
			Membrane cmem = (Membrane)it.next();
			collectLHSMem(cmem);
		}
	}
	
	/** 左辺のアトムかどうかを返す */
	private boolean isLHSAtom(Atom atom) {
		return isLHSMem(atom.mem);
	}

	/** 左辺の膜かどうかを返す */
	private boolean isLHSMem(Membrane mem) {
		return lhsmems.contains(mem);
	}

	/** 出現制約を推論する */
	private void inferOccurrence(){
		inferOccurrenceMembrane(root);
	}
	/** 出現制約を推論する */
	private void inferOccurrenceMembrane(Membrane mem){
		/** アクティブアトムについて出現制約を課す */
		Iterator<Atom> ita = mem.atoms.iterator();
		while(ita.hasNext()){
			Atom atom = ita.next();
			if(isActiveAtom(atom))
				add(new AtomOccurrenceConstraint(mem.name,atom.functor));
		}
		/** 子膜について出現制約を課し、走査 */
		Iterator<Membrane> itm = mem.mems.iterator();
		while(itm.hasNext()){
			Membrane child = itm.next();
			add(new MembraneOccurrenceConstraint(mem.name,child.name));
			inferOccurrenceMembrane(child);
		}
		/** ルールの左辺／右辺を走査 */
		Iterator<RuleStructure> itr = mem.rules.iterator();
		while(itr.hasNext()){
			RuleStructure rule = itr.next();
			inferOccurrenceMembrane(rule.leftMem);
			inferOccurrenceMembrane(rule.rightMem);
		}
	}

	/** 引数制約を推論する */
	private void inferArgument() throws TypeConstraintException{
		new ArgumentInferrer(root).infer();
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
		int out = outOfPassiveAtom((Atom)lo.atom);
		if(out == lo.pos){ // データアトムの出力引数
			if(outOfPassiveAtom((Atom)b.atom) == b.pos)//!= TypeEnv.ACTIVE)
				throw new TypeConstraintException("MODE ERROR : output arguments connected each other.");
			else addReceiveConstraint(-sign, b, ((Atom)lo.atom).functor);
		}
		else{
			if(outOfPassiveAtom((Atom)b.atom) == b.pos) //!= TypeEnv.ACTIVE)
				addConstraintAboutLinks(sign, b, lo);
			else addUnifyConstraint(sign, lo, b);
		}
	}

	private void addUnifyConstraint(int sign, LinkOccurrence lo,
			LinkOccurrence b) {
		add(new UnifyConstraint(new PolarizedPath(1, new RootPath(lo)),
				new PolarizedPath(sign, new RootPath(b))));
	}

	private void addReceiveConstraint(int sign, LinkOccurrence b, Functor f) {
		add(new ReceiveConstraint(new PolarizedPath(sign, new RootPath(b)), f));
	}

	/**
	 * get real buddy through =/2, $out, $in
	 * 
	 * @param lo
	 * @return
	 */
	private LinkOccurrence getRealBuddy(LinkOccurrence lo) {
		if (lo.buddy.atom instanceof Atom) {
			Atom a = (Atom) lo.buddy.atom;
			int o = outOfPassiveAtom(a);
			if (o == TypeEnv.CONNECTOR)
				return getRealBuddy(a.args[1 - lo.buddy.pos]);
			else
				return lo.buddy;
		} else
			return lo.buddy;
	}

	private void add(Constraint c) {
		constraints.add(c);
	}

	/**
	 * change RootPath into ActiveAtomPath or TracingPath.
	 * 
	 */
	private void solvePathes() {
		Set unSolvedRPCs = constraints.getReceivePassiveConstraints();
		Iterator it = unSolvedRPCs.iterator();
		while (it.hasNext()) {
			ReceiveConstraint rpc = (ReceiveConstraint) it.next();
			rpc.setPPath(solvePolarizedPath(rpc.getPPath()));
		}
		constraints.refreshReceivePassiveConstraints(unSolvedRPCs);
		Set unSolvedUCs = constraints.getUnifyConstraints();
		it = unSolvedUCs.iterator();
		while (it.hasNext()) {
			UnifyConstraint uc = (UnifyConstraint) it.next();
			uc.setPPathes(solvePolarizedPath(uc.getPPath1()),
					solvePolarizedPath(uc.getPPath2()));
		}
	}

	private PolarizedPath solvePolarizedPath(PolarizedPath pp) {
		Path p = pp.getPath();
		if (!(p instanceof RootPath)) {
			System.out.println("fatal error in solving path.");
			return pp;
		}
		LinkOccurrence lo = ((RootPath) p).getTarget();
		if (!(lo.atom instanceof Atom))
			return pp;
		PolarizedPath tp = getPolarizedPath(lo);
		return new PolarizedPath(pp.getSign() * tp.getSign(), tp.getPath());
	}

	/**
	 * get the path to the active head atom.
	 * 
	 * @param lo :
	 *            argument of Atom (not Atomic)
	 * @return
	 */
	private PolarizedPath getPolarizedPath(LinkOccurrence lo) {
		Atom atom = (Atom) lo.atom;
		int out = outOfPassiveAtom(atom);
		if (out == TypeEnv.ACTIVE) {
			return new PolarizedPath(1, new ActiveAtomPath(atom.mem.name,
					atom.functor, lo.pos));
		} else if (out == TypeEnv.CONNECTOR) {
			System.out.println("fatal error in getting path.");
			return null;
		} else {
			LinkOccurrence tl = getRealBuddy(atom.args[out]);
			if (!(tl.atom instanceof Atom))
				return new PolarizedPath(1, new RootPath(tl));
			Atom ta = (Atom) tl.atom;
			PolarizedPath pp = getPolarizedPath(tl);
			if (isLHSAtom(atom) == isLHSAtom(ta))
				return new PolarizedPath(pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
			else
				return new PolarizedPath(-pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
		}
	}

	public void printAllConstraints() {
		constraints.printAllConstraints();
	}

	/**
	 * ルールごとに、アトム引数の型／モードを推論する。
	 * 文脈定義がルールごとに扱うほうが都合が良い為。
	 * @author kudo
	 *
	 */
	class ArgumentInferrer{
		RuleStructure rule;
		
		Set<ContextDef> defs;

		/** */
		ArgumentInferrer(RuleStructure rule){
			this.rule = rule;
		}
		/**
		 * グローバルルート膜に対してのみ呼ばれる
		 * @param top
		 */
		ArgumentInferrer(Membrane top){
			RuleStructure tmprule = new RuleStructure(new Membrane(null),"tmp");
			tmprule.leftMem = new Membrane(null);
			tmprule.rightMem = top;
			this.rule = tmprule;
		}
		void infer() throws TypeConstraintException{
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
				new ArgumentInferrer(itr.next()).infer();
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
				int out = outOfPassiveAtom(atom);
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
				LinkOccurrence rightlink = getRealBuddy(leftlink);
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
				LinkOccurrence b = getRealBuddy(lo);
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
				LinkOccurrence lhsPartner = getRealBuddy(lhsOcc.args[i]);
				LinkOccurrence rhsPartner = getRealBuddy(rhsOcc.args[i]);
				if(rhsPartner.atom instanceof Atom){
					if(isLHSAtom((Atom)rhsPartner.atom))
						addUnifyConstraint(-1,lhsPartner,rhsPartner);
					else
						addUnifyConstraint(1,lhsPartner,rhsPartner);
				}
				else{ // 右辺出現がプロセス文脈と継っている
					// そいつの左辺出現の相方をとってくる
					LinkOccurrence partnerOfPartner = getRealBuddy(((ProcessContext)rhsPartner.atom).def.lhsOcc.args[i]);
					addUnifyConstraint(-1,lhsPartner, partnerOfPartner);
				}
			}
		}
	}

	/**
	 * 量的解析(?)
	 * @author kudo
	 *
	 */
	class QuantityInferrer{
		QuantityInferrer(){
			
		}
		void inferQuantity(){
			
		}
		
		/** アトムについての「量」を管理するクラス */
		class AtomQuantity{
//			Functor f;
			int min = 0;
			int max = 0;
			AtomQuantity(){}
			AtomQuantity merge(AtomQuantity aq1, AtomQuantity aq2){
				AtomQuantity ret = new AtomQuantity();
				ret.min = (aq1.min<aq2.min)?aq1.min:aq2.min;
				ret.max = (aq1.max>aq2.max)?aq1.max:aq2.max;
				return ret;
			}
		}
		class Quantitys{
			Map<Functor,AtomQuantity> functorToQuantity;
			Quantitys(){
				functorToQuantity = new HashMap();
			}
			void putAtomQuantity(Functor f, AtomQuantity aq){
				if(!functorToQuantity.containsKey(f)){
					functorToQuantity.put(f,aq);
				}
				else{
					AtomQuantity aqold = functorToQuantity.get(f);
					functorToQuantity.put(f,aq.merge(aqold, aq));
				}
			}
		}
	}
}


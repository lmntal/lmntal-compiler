package type.argument;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import runtime.Functor;
import type.TypeEnv;
import type.TypeException;

import compile.structure.Atom;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

/**
 * ルールごとに、アトム引数の型／モードを推論する。
 * @author kudo
 *
 */
public class ArgumentInferer {
	private RuleStructure rule;
	
	/** プロセス文脈定義の集合 */
	private Set<ContextDef> defs;
	
	private ConstraintSet constraints;
	
	public ConstraintSet getConstraints(){
		return constraints;
	}

	/** */
	public ArgumentInferer(RuleStructure rule, ConstraintSet constraints){
		this.rule = rule;
		this.constraints = constraints;
	}

	/**
	 * グローバルルート膜に対してのみ呼ばれる
	 * @param top
	 */
	public ArgumentInferer(Membrane top){//, ConstraintSet constraints){
		RuleStructure tmprule = new RuleStructure(new Membrane(null),"tmp");
		tmprule.leftMem = new Membrane(null);
		tmprule.rightMem = top;
		this.rule = tmprule;
		this.constraints = new ConstraintSet();//constraints;
	}
	
	public void printAll(){
		constraints.printAllConstraints();
	}

	/**
	 * 
	 * @throws TypeException
	 */
	public void infer() throws TypeException{
		defs = new HashSet<ContextDef>();

		// TODO Active Head Condition をチェックする
		// 全ての引数についてモード変数、型変数を振る
		inferArgumentRule(rule);

		//プロセス文脈について処理する
		processLinksOfProcessContexts();
		
		solvePathes();
		constraints.solveUnifyConstraints();
		
	}
	
	/**
	 * プロセス文脈の引数について処理する
	 */
	private void processLinksOfProcessContexts()throws TypeException{
		for(ContextDef def : defs)
			for(ProcessContext rhsOcc : (List<ProcessContext>)def.rhsOccs)
				processExplicitLinks((ProcessContext)def.lhsOcc, rhsOcc);
	}

	/**
	 * @param mem
	 * @param freelinks
	 *            free links already checked
	 * @return free links in the process at mem
	 */
	private Set<LinkOccurrence> inferArgumentMembrane(Membrane mem, Set<LinkOccurrence> freelinks) throws TypeException{

		//ルールについて走査する
		for(RuleStructure rs : (List<RuleStructure>)mem.rules){
//			new ArgumentInferer(rs,constraints).infer();
			inferArgumentRule(rs);
		}

		// 子膜について走査する
		for(Membrane child : (List<Membrane>)mem.mems){
			freelinks = inferArgumentMembrane(child, freelinks);
		}

		// この時点で、子孫膜に出現する全てのアトム／プロセス文脈について、局所リンクの処理は終わっている
		
		for(Atom atom : (List<Atom>)mem.atoms){
			if (TypeEnv.outOfPassiveAtom(atom) != TypeEnv.CONNECTOR) // '='/2だったら無視する
				freelinks = inferArgumentAtom(atom, freelinks);
		}

		// この時点で、子孫膜に出現する全てのアトム／プロセス文脈、およびこの膜のアトムの引数の処理は終わっている
		// つまり残るはこの膜に出現するプロセス文脈と、この膜の自由リンクのみ
		
		//文脈定義を集める
		
		// プロセス文脈
		for(ProcessContext pc : (List<ProcessContext>)mem.processContexts){
			ContextDef def = pc.def;
			if(!defs.contains(def))defs.add(def);
		}
		//型付きプロセス文脈
		for(ProcessContext tpc : (List<ProcessContext>)mem.typedProcessContexts){
			ContextDef def = tpc.def;
			if(TypeEnv.dataTypeOfContextDef(def) == null){
				if(!defs.contains(def))defs.add(def);
			}
			else{
				LinkOccurrence lo = tpc.args[0];
				LinkOccurrence b = TypeEnv.getRealBuddy(lo);
				if(b.atom instanceof ProcessContext){
					ProcessContext budpc = (ProcessContext)b.atom;
					if(TypeEnv.dataTypeOfContextDef(budpc.def)!= null){
						throw new TypeException("output arguments connected each other. : " + lo.atom.getName() + " <=> " + b.atom.getName());
					}
					else{
						freelinks.add(lo);
						continue;
					}
				}
				else{
					if(freelinks.contains(b)){
						addConstraintAboutLinks(-1, lo, b);
						freelinks.remove(b);
					}
					else
						freelinks.add(lo);
				}
			}
		}
		return freelinks;
	}

	/**
	 * inferrence
	 * 
	 * @param rule
	 */
	private void inferArgumentRule(RuleStructure rule) throws TypeException{
		// 左辺／右辺それぞれについて型／モードを解決し、1回出現するリンクを集める
		Set<LinkOccurrence> freelinksLeft = inferArgumentMembrane(rule.leftMem, new HashSet<LinkOccurrence>());
		Set<LinkOccurrence> freelinksRight = inferArgumentMembrane(rule.rightMem, new HashSet<LinkOccurrence>());
		for(LinkOccurrence leftlink : freelinksLeft){
			LinkOccurrence rightlink = TypeEnv.getRealBuddy(leftlink);
			if (!freelinksRight.contains(rightlink)) // リンクが左辺／右辺出現でないなら
				throw new TypeException("link occurs once in a rule.");
			if(leftlink.atom instanceof Atom){
				addConstraintAboutLinks(1, rightlink, leftlink);
			}
			else if(rightlink.atom instanceof ProcessContext)
				throw new TypeException("SYNTAX ERROR : Process Context's link inherited.");
			else addConstraintAboutLinks(1, leftlink, rightlink);
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
	private Set<LinkOccurrence> inferArgumentAtom(Atom atom, Set<LinkOccurrence> freelinks)throws TypeException{
		for (int i = 0; i < atom.args.length; i++) {
			LinkOccurrence lo = atom.args[i];
			LinkOccurrence b = TypeEnv.getRealBuddy(lo);
			if(b.atom instanceof ProcessContext){ // プロセス文脈に接続している
				ProcessContext pc = (ProcessContext)b.atom;
				if(TypeEnv.dataTypeOfContextDef(pc.def) != null){ // データ型
					if(freelinks.contains(b)){
						addConstraintAboutLinks(-1, b, lo);
						freelinks.remove(b);
					}
					else
						freelinks.add(lo);
				}
				else continue;
			}
			if (freelinks.contains(b)) { // 局所リンク
				addConstraintAboutLinks(-1, lo, b);
				freelinks.remove(b);
			} else
				freelinks.add(lo);
		}
		return freelinks;
	}
	
	/** プロセス文脈の左辺／右辺出現のそれぞれの引数に対して同じであるという制約をかける */
	private void processExplicitLinks(ProcessContext lhsOcc, ProcessContext rhsOcc)throws TypeException{
		for(int i=0;i<lhsOcc.args.length;i++){
			LinkOccurrence lhsPartner = TypeEnv.getRealBuddy(lhsOcc.args[i]);
			LinkOccurrence rhsPartner = TypeEnv.getRealBuddy(rhsOcc.args[i]);
			if(rhsPartner.atom instanceof Atom){
				if(TypeEnv.isLHSAtom((Atom)rhsPartner.atom))
//					addUnifyConstraint(-1,lhsPartner,rhsPartner);
					addConstraintAboutLinks(-1,lhsPartner,rhsPartner);
				else
//					addUnifyConstraint(1,lhsPartner,rhsPartner);
					addConstraintAboutLinks(1,lhsPartner,rhsPartner);
			}
			else{ // 右辺出現がプロセス文脈と継っている
				ProcessContext pc = (ProcessContext)rhsPartner.atom;
				Functor df = TypeEnv.dataTypeOfContextDef(pc.def);
				if(df==null){// 型付きでない
					// そいつの左辺出現の相方をとってくる
					LinkOccurrence partnerOfPartner =
						TypeEnv.getRealBuddy(((ProcessContext)rhsPartner.atom).def.lhsOcc.args[i]);
//					addUnifyConstraint(-1,lhsPartner, partnerOfPartner);
					addConstraintAboutLinks(-1,lhsPartner,partnerOfPartner);
				}
				else{
					addConstraintAboutLinks(1,rhsPartner,lhsPartner);
//					LinkOccurrence partnerOfPartner = TypeEnv.getRealBuddy(((ProcessContext)rhsPartner.atom).def.lhsOcc.args[i]);
//					add
				}
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
	private void addConstraintAboutLinks(int sign, LinkOccurrence lo, LinkOccurrence b) throws TypeException{
		// 型付きプロセス文脈で、データ型の時にはデータアトムとして扱う
		if(lo.atom instanceof ProcessContext){
			ProcessContext pc = (ProcessContext)lo.atom;
			Functor df = TypeEnv.dataTypeOfContextDef(pc.def);
			if(df != null)
				addReceiveConstraint(-sign, b, df);
		}
		else{
			int out = TypeEnv.outOfPassiveAtom((Atom)lo.atom);
			if(out == lo.pos){ // データアトムの出力引数
				if(TypeEnv.outOfPassiveAtom((Atom)b.atom) == b.pos)//!= TypeEnv.ACTIVE)
					if(sign == -1)
						throw new TypeException("output arguments connected each other. : " + lo.atom.getName() + " <=> " + b.atom.getName() + " in line " + lo.atom.line);
					else{
						// TODO データアトムの引数同士がルール左辺・右辺で受け継がれる場合はどうしたらいいんだろう
						addUnifyConstraint(sign, lo, b);
					}
				else addReceiveConstraint(-sign, b, ((Atom)lo.atom).functor);
			}
			else{
				if(TypeEnv.outOfPassiveAtom((Atom)b.atom) == b.pos) //!= TypeEnv.ACTIVE)
					addConstraintAboutLinks(sign, b, lo);
				else addUnifyConstraint(sign, lo, b);
			}
		}
	}

	private void addUnifyConstraint(int sign, LinkOccurrence lo, LinkOccurrence b) {
		constraints.add(new UnifyConstraint(new PolarizedPath(1, new RootPath(lo)),
				new PolarizedPath(sign, new RootPath(b))));
	}

	private void addReceiveConstraint(int sign, LinkOccurrence b, Functor f) {
		constraints.add(new ReceiveConstraint(new PolarizedPath(sign, new RootPath(b)), f));
	}

	/**
	 * change RootPath into ActiveAtomPath or TracingPath.
	 * 
	 */
	private void solvePathes()throws TypeException{
		Set<ReceiveConstraint> unSolvedRPCs = constraints.getReceivePassiveConstraints();
		for(ReceiveConstraint rpc : unSolvedRPCs){
			rpc.setPPath(solvePolarizedPath(rpc.getPPath()));
		}
		constraints.refreshReceivePassiveConstraints(unSolvedRPCs);
		for(UnifyConstraint uc : constraints.getUnifyConstraints()){
			uc.setPPathes(solvePolarizedPath(uc.getPPath1()),
					solvePolarizedPath(uc.getPPath2()));
		}
	}

	private PolarizedPath solvePolarizedPath(PolarizedPath pp)throws TypeException{
		Path p = pp.getPath();
		if (!(p instanceof RootPath)) {
			System.out.println("fatal error in solving path.");
			if(p instanceof ActiveAtomPath)
				System.out.println("\tactive atom path");
			else if(p instanceof TracingPath)
				System.out.println("\ttracing path");
			return pp;
		}
		LinkOccurrence lo = ((RootPath) p).getTarget();
		if (!(lo.atom instanceof Atom))
			return pp;
		Set<LinkOccurrence> traced = new HashSet<LinkOccurrence>();
		PolarizedPath tp = getPolarizedPath(traced, lo);
		if(tp == null){
			return new PolarizedPath(1,p);
		}
		return new PolarizedPath(pp.getSign() * tp.getSign(), tp.getPath());
	}

	/**
	 * get the path to the active head atom.
	 * 
	 * @param lo :
	 *            argument of Atom (not Atomic)
	 * @return
	 */
	private PolarizedPath getPolarizedPath(Set<LinkOccurrence> traced, LinkOccurrence lo)throws TypeException{
		if(traced.contains(lo)){
			// TODO この場合は1つ辿るとこまでのPathが得られるようにする
			return null;
		}
		Atom atom = (Atom) lo.atom;
		int out = TypeEnv.outOfPassiveAtom(atom);
		if (out == TypeEnv.ACTIVE) {
			return new PolarizedPath(1, new ActiveAtomPath(TypeEnv.getMemName(atom.mem),
					atom.functor, lo.pos));
		} else if (out == TypeEnv.CONNECTOR) {
			System.out.println("fatal error in getting path.");
			return null;
		} else {
			LinkOccurrence tl = TypeEnv.getRealBuddy(atom.args[out]);
			if (!(tl.atom instanceof Atom))
				return new PolarizedPath(1, new RootPath(tl));
			Atom ta = (Atom) tl.atom;
			PolarizedPath pp = null;
			
			traced.add(lo);
			pp = getPolarizedPath(traced, tl);
			if(pp == null)return null;
			if (TypeEnv.isLHSAtom(atom) == TypeEnv.isLHSAtom(ta))
				return new PolarizedPath(pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
			else
				return new PolarizedPath(-pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
		}
	}

}

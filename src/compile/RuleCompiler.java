package compile;

import java.util.*;
import runtime.Env;
import runtime.Rule;
//import runtime.InterpretedRuleset;
import runtime.InlineUnit;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.Functor;
import runtime.Inline;
import compile.structure.*;

/**
 * コンパイル時ルール構造（compile.structure.RuleStructure）を
 * （インタプリタ動作可能な）ルールオブジェクト（runtime.Rule）に変換する。
 * <p>
 * 子ルール構造は無視される代わりに、同じ膜の持つルールセット（runtime.Ruleset）が参照される。
 * したがってこのクラスを呼び出す RulesetCompiler は、
 * 子ルール構造を事前にルールセットにコンパイルしておく必要がある。
 * 
 * @author n-kato, hara
 */
public class RuleCompiler {
	/** 単一化を意味する（ものとしてRuleCompilerが考える）ファンクタ。=/2 */
	public static final Functor FUNC_UNIFY = new Functor("=", 2);

	/** コンパイルされるルール構造 */
	public RuleStructure rs;
	
	/** コンパイルされるルールに対応するルールオブジェクト */
	public Rule theRule;
	
	public List atomMatch;
	public List memMatch;
	public List guard;
	public List body;
	int varcount;			// 次の変数番号
	
	List rhsatoms;
	Map  rhsatompath;		// 右辺のアトム (Atom) -> 変数番号 (Integer)
	Map  rhsmempath;		// 右辺の膜 (Membrane) -> 変数番号 (Integer)	
	List lhsatoms;
	List lhsmems;
	Map  lhsatompath;		// 左辺のアトム (Atom) -> 変数番号 (Integer)
	Map  lhsmempath;		// 左辺の膜 (Membrane) -> 変数番号 (Integer)
	Map  lhslinkpath;		// 左辺のアトムの変数番号 (Integer) -> リンクの変数番号の配列 (int[])
	
	HeadCompiler hc;
	
	final int lhsmemToPath(Membrane mem) { return ((Integer)lhsmempath.get(mem)).intValue(); }
	final int rhsmemToPath(Membrane mem) { return ((Integer)rhsmempath.get(mem)).intValue(); }
	final int lhsatomToPath(Atom atom) { return ((Integer)lhsatompath.get(atom)).intValue(); } 
	final int rhsatomToPath(Atom atom) { return ((Integer)rhsatompath.get(atom)).intValue(); } 
	final int lhslinkToPath(Atom atom, int pos) {
		int atompath = lhsatomToPath(atom);
		return ((int[])lhslinkpath.get(new Integer(atompath)))[pos];
	}
	
	public String unitName;
	/** ヘッドのマッチング終了後の継続命令列のラベル */
	private InstructionList contLabel;

	/**
	 * 指定された RuleStructure 用のルールをつくる
	 */
	RuleCompiler(RuleStructure rs) {
		this(rs, InlineUnit.DEFAULT_UNITNAME);
	}
	RuleCompiler(RuleStructure rs, String unitName) {
		//Env.n("RuleCompiler");
		//Env.d(rs);
		this.unitName = unitName;
		this.rs = rs;
	}
	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルする
	 */
	public Rule compile() {
		Env.c("compile");
		liftupActiveAtoms(rs.leftMem);
		simplify();
		theRule = new Rule(rs.toString());
		
		hc = new HeadCompiler();//rs.leftMem;
		hc.enumFormals(rs.leftMem);	// 左辺に対する仮引数リストを作る
		
		//とりあえず常にガードコンパイラを呼ぶ事にしてしまう by mizuno
//		if (!rs.typedProcessContexts.isEmpty() || !rs.guardNegatives.isEmpty()) {
		if (true) {
			theRule.guardLabel = new InstructionList();
			guard = theRule.guardLabel.insts;
		}
		else guard = null;
		theRule.bodyLabel = new InstructionList();
		body = theRule.bodyLabel.insts;
		contLabel = (guard != null ? theRule.guardLabel : theRule.bodyLabel);		
		
		compile_l();
		compile_g();
		compile_r();

		theRule.memMatch  = memMatch;
		theRule.atomMatch = atomMatch;
		theRule.guard     = guard;
		theRule.body      = body;
		optimize();
		return theRule;
	}
	
	/** 左辺膜をコンパイルする */
	private void compile_l() {
		Env.c("compile_l");
		
		theRule.atomMatchLabel = new InstructionList();
		atomMatch = theRule.atomMatchLabel.insts;
		
		int maxvarcount = 2;	// アトム主導用（仮）
		for (int firstid = 0; firstid <= hc.atoms.size(); firstid++) {
			hc.prepare(); // 変数番号を初期化			
			if (firstid < hc.atoms.size()) {			
				if (Env.shuffle >= Env.SHUFFLE_DONTUSEATOMSTACKS) continue;
				// Env.SHUFFLE_DEFAULT ならば、ルールの反応確率を優先するためアトム主導テストは行わない
				
				Atom atom = (Atom)hc.atoms.get(firstid);
				if (!atom.functor.isActive()) continue;
				
				// アトム主導
				InstructionList tmplabel = new InstructionList();
				tmplabel.insts = hc.match;
				atomMatch.add(new Instruction(Instruction.BRANCH, tmplabel));
				
				hc.mempaths.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
				hc.atompaths.put(atom, new Integer(1));		// 主導するアトムの変数番号は 1
				hc.varcount = 2;
				hc.match.add(new Instruction(Instruction.FUNC, 1, atom.functor));
				Membrane mem = atom.mem;
				if (mem == rs.leftMem) {
					hc.match.add(new Instruction(Instruction.TESTMEM, 0, 1));
				}
				else {
					hc.match.add(new Instruction(Instruction.GETMEM, hc.varcount, 1));
					hc.match.add(new Instruction(Instruction.LOCK,   hc.varcount));
					hc.mempaths.put(mem, new Integer(hc.varcount++));
					mem = mem.mem;
					while (mem != rs.leftMem) {
						hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
						hc.match.add(new Instruction(Instruction.LOCK,     hc.varcount));
						hc.mempaths.put(mem, new Integer(hc.varcount++));
						mem = mem.mem;
					}
					hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
					hc.match.add(new Instruction(Instruction.EQMEM, 0, hc.varcount++));
				}
				hc.getLinks(1, atom.functor.getArity()); //リンクの一括取得(RISC化) by mizuno
				Atom firstatom = (Atom)hc.atoms.get(firstid);
				hc.compileLinkedGroup(firstatom);
				hc.compileMembrane(firstatom.mem);
			} else {
				// 膜主導
				theRule.memMatchLabel = hc.matchLabel;
				memMatch = hc.match;
				hc.mempaths.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
			}
			hc.compileMembrane(rs.leftMem);
			if (hc.match == memMatch) {
				hc.match.add(0, Instruction.spec(1, hc.varcount));
			}
			else {
				hc.match.add(0, Instruction.spec(2, hc.varcount));
			}
			// jump命令群の生成
			List memActuals  = hc.getMemActuals();
			List atomActuals = hc.getAtomActuals();
			List varActuals  = hc.getVarActuals();
			// - コード#1
			hc.match.add( Instruction.jump(contLabel, memActuals, atomActuals, varActuals) );
			// - コード#2
//			hc.match.add( Instruction.inlinereact(theRule, memActuals, atomActuals, varActuals) );
//			int formals = memActuals.size() + atomActuals.size() + varActuals.size();
//			hc.match.add( Instruction.spec(formals, formals) );
//			hc.match.add( hc.getResetVarsInstruction() );
//			List brancharg = new ArrayList();
//			brancharg.add(body);
//			hc.match.add( new Instruction(Instruction.BRANCH, brancharg) );
			
			// jump命令群の生成終わり
			if (maxvarcount < hc.varcount) maxvarcount = hc.varcount;
		}
		atomMatch.add(0, Instruction.spec(2,maxvarcount));
	}
	
	// todo spec命令を外側に持ち上げる最適化器を実装する
	
	/**
	 * 左辺のアトムのリンクに対してgetlinkを行い、変数番号を登録する。(RISC化)
	 * 将来的にはリンクオブジェクトをボディ命令列の引数に渡すようにするかもしれない。
	 */
	private void getLHSLinks() {
		lhslinkpath = new HashMap();
		for (int i = 0; i < lhsatoms.size(); i++) {
			Atom atom = (Atom)lhsatoms.get(i);
			int atompath = lhsatomToPath(atom);
			int arity = atom.functor.getArity();
			int[] paths = new int[arity];
			for (int j = 0; j < arity; j++) {
				paths[j] = varcount;
				body.add(new Instruction(Instruction.GETLINK, varcount, atompath, j));
				varcount++;
			}
			lhslinkpath.put(new Integer(atompath), paths);
		}
	}

	/** 右辺膜をコンパイルする */
	private void compile_r() {
		Env.c("compile_r");
		int formals = varcount;
		body.add( Instruction.commit(theRule) );
		inc_guard();
			
		rhsatoms    = new ArrayList();
		rhsatompath = new HashMap();
		rhsmempath  = new HashMap();
		int toplevelmemid = lhsmemToPath(rs.leftMem);
		rhsmempath.put(rs.rightMem, new Integer(toplevelmemid));
		
		//Env.d("rs.leftMem -> "+rs.leftMem);
		//Env.d("lhsmempaths.get(rs.leftMem) -> "+lhsmempaths.get(rs.leftMem));
		//Env.d("rhsmempaths -> "+rhsmempaths);

		recursiveLockLHSNonlinearProcessContextMems();
		dequeueLHSAtoms();
		removeLHSAtoms();
		removeLHSTypedProcesses();
		if (removeLHSMem(rs.leftMem) >= 2) {
			body.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, toplevelmemid));
		}
		buildRHSMem(rs.rightMem);
		if (!rs.rightMem.processContexts.isEmpty()) {
			body.add(new Instruction(Instruction.REMOVETEMPORARYPROXIES, toplevelmemid));
		}
		copyRules(rs.rightMem);
		loadRulesets(rs.rightMem);		
		buildRHSTypedProcesses();
		buildRHSAtoms(rs.rightMem);
		// ここでvarcountの最終値が確定することになっている。変更時は適切に下に移動すること。
		updateLinks();
		enqueueRHSAtoms();
		addInline();
		addRegAndLoadModules();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms();
		freeLHSTypedProcesses();
		freeLHSSingletonProcessContexts();
		recursiveUnlockLHSNonlinearProcessContextMems();
		unlockReusedOrNewRootMem(rs.rightMem);
		//
		body.add(0, Instruction.spec(formals, varcount));
		
//		if (rs.rightMem.mems.isEmpty() && rs.rightMem.ruleContexts.isEmpty()
//		 && rs.rightMem.processContexts.isEmpty() && rs.rightMem.rulesets.isEmpty()) {
//			body.add(new Instruction(Instruction.CONTINUE));
//		} else 
		body.add(new Instruction(Instruction.PROCEED));
	}
		
	////////////////////////////////////////////////////////////////
	//
	// ガード関係
	//

	/** ヘッドの膜とアトムに対して、仮引数番号を登録する */
	private void genLHSPaths() {
		lhsatompath = new HashMap();
		lhsmempath  = new HashMap();
		varcount = 0;
		for (int i = 0; i < lhsmems.size(); i++) {
			lhsmempath.put(lhsmems.get(i), new Integer(varcount++));
		}
		for (int i = 0; i < lhsatoms.size(); i++) {
			lhsatompath.put(lhsatoms.get(i), new Integer(varcount++));
		}
	}
	
	private void inc_guard() {
		// ガードの取り込み
		varcount = lhsatoms.size() + lhsmems.size();
		getLHSLinks();
		// typedcxtdefs = gc.typedcxtdefs;
		// varcount = lhsatoms.size() + lhsmems.size() + rs.typedProcessContexts.size();
		genTypedProcessContextPaths();
	}

//	private void inc_head(HeadCompiler hc) {
//		// ヘッドの取り込み
//		lhsatoms = hc.atoms;
//		lhsmems  = hc.mems;
//		genLHSPaths();
//		varcount = lhsatoms.size() + lhsmems.size();
//	}

	/** ガードをコンパイルする */
	private void compile_g() {
		lhsmems  = hc.mems;
		lhsatoms = hc.atoms;
		genLHSPaths();
		gc = new GuardCompiler(this, hc);
		if (guard == null) return;
		int formals = gc.varcount;
		gc.checkMembraneStatus();
		gc.getLHSLinks();
		gc.fixTypedProcesses();
		varcount = gc.varcount;
		compileNegatives();
		guard.add( 0, Instruction.spec(formals,varcount) );
		guard.add( Instruction.jump(theRule.bodyLabel, gc.getMemActuals(),
			gc.getAtomActuals(), gc.getVarActuals()) );
		//RISC化で、暫定処置としてガードでgetlinkした物をボディに渡さない事にしたので、
		//ガード命令列の局所変数の数とボディ命令列の引数の数が一致しなくなった。by mizuno
		varcount = gc.getMemActuals().size() + gc.getAtomActuals().size() 
					+ gc.getVarActuals().size();
	}
	void compileNegatives() {
		Iterator it = rs.guardNegatives.iterator();
		while (it.hasNext()) {
			LinkedList eqs = (LinkedList)it.next();
			HeadCompiler negcmp = hc.getNormalizedHeadCompiler();
			negcmp.varcount = varcount;
			negcmp.compileNegativeCondition(eqs);
			guard.add(new Instruction(Instruction.NOT, negcmp.matchLabel));
			if (varcount < negcmp.varcount)  varcount = negcmp.varcount;
		}
	}
	
	// 型付きプロセス文脈関係
	
	GuardCompiler gc;
	/** 型付きプロセス文脈の右辺での出現 (Context) -> 変数番号 */
	HashMap rhstypedcxtpaths = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号（Body実行時） */
	HashMap typedcxtsrcs  = new HashMap();
	/** Body実行時なので、UNBOUNDにはならない */
	int typedcxtToSrcPath(ContextDef def) {
		return ((Integer)typedcxtsrcs.get(def)).intValue();
	}
	/***/
	int rhstypedcxtToPath(Context cxt) {
		return ((Integer)rhstypedcxtpaths.get(cxt)).intValue();
	}

	private void genTypedProcessContextPaths() {
		Iterator it = gc.typedcxtdefs.iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			typedcxtsrcs.put( def, new Integer(varcount++) );
		}
	}
//	public void enumTypedContextDefs() {
//		Iterator it = rs.typedProcessContexts.values().iterator();
//		while (it.hasNext()) {
//			ContextDef def = (ContextDef)it.next();
//			typedcxtdefs.add(def);
//		}
//	}
	private void removeLHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Context pc = def.lhsOcc;
			if (pc != null) { // ヘッドのときのみ除去する
				if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
					body.add(new Instruction( Instruction.REMOVEATOM,
						typedcxtToSrcPath(def), lhsmemToPath(pc.mem) ));
				}
			}
		}
	}	
	private void freeLHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
				body.add(new Instruction( Instruction.FREEATOM,
					typedcxtToSrcPath(def) ));
			}
		}
	}	

	//kudo
	private void freeLHSSingletonProcessContexts(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) { // 非線型のとき1つだけ再利用するようにしたら size == 0 に直せる
				body.add(new Instruction( Instruction.DROPMEM,
					lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	//kudo
	private void recursiveLockLHSNonlinearProcessContextMems(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				body.add(new Instruction( Instruction.RECURSIVELOCK,
					lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	//kudo
	private void recursiveUnlockLHSNonlinearProcessContextMems(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				body.add(new Instruction( Instruction.RECURSIVEUNLOCK,
					lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}


	private void buildRHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext()) {
				ProcessContext pc = (ProcessContext)it2.next();
				if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
					int atompath = varcount++;
					body.add(new Instruction( Instruction.COPYATOM, atompath,
						rhsmemToPath(pc.mem),
						typedcxtToSrcPath(pc.def) ));
					rhstypedcxtpaths.put(pc, new Integer(atompath));
				}
			}
		}
	}	

	////////////////////////////////////////////////////////////////

	/** 膜階層下にあるアクティブアトムを各膜内で先頭方向にスライド移動する。*/
	private static void liftupActiveAtoms(Membrane mem) {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			liftupActiveAtoms((Membrane)it.next());
		}
		LinkedList atomlist = new LinkedList();
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			atomlist.add(it.next());
		}
		mem.atoms.clear();
		it = atomlist.iterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			if (a.functor.isActive()) {
				mem.atoms.add(a);
				it.remove();
			}
		}
		mem.atoms.addAll(atomlist);	
	}
	/** ルールの左辺と右辺に対してstaticUnifyを呼ぶ */
	public void simplify() {
		staticUnify(rs.leftMem);
		staticUnify(rs.rightMem);
		if (rs.leftMem.atoms.isEmpty() && rs.leftMem.mems.isEmpty() && !rs.fSuppressEmptyHeadWarning) {
			warning("WARNING: rule with empty head: " + rs);
		}
	}
	
	/** 指定された膜とその子孫に存在する冗長な =（todo および自由リンク管理アトム）を除去する */
	public void staticUnify(Membrane mem) {
		Env.c("RuleCompiler::staticUnify");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			staticUnify((Membrane)it.next());
		}
		ArrayList removedAtoms = new ArrayList();
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (atom.functor.equals(FUNC_UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				if (link1.atom.mem != mem && link2.atom.mem != mem) {
					// 単一化アトムのリンク先が両方とも他の膜につながっている場合
					if (mem == rs.leftMem) {
							// // <strike> ( X=Y :- p(X,Y) ) は意味解析エラー
							// //（=は通常のヘッドアトムと見なして放置される）</strike>
							// error("COMPILE ERROR: head contains body unification");
						// ( X=Y :- p(X,Y) ) は ( :- p(X,X) ) になる
					}
					else {
						// ( p(X,Y) :- X=Y ) はUNIFYボディ命令を出力するのでここでは何もしない
						continue;
					}
				}
				link1.buddy = link2;
				link2.buddy = link1;
				link2.name = link1.name;
				removedAtoms.add(atom);
			}
		}
		it = removedAtoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atom.mem.atoms.remove(atom);
		}
	}
	
	
	private void optimize() {
		Env.c("optimize");
//		Optimizer.optimize(memMatch, body);
		Optimizer.optimizeRule(theRule);
	}

	////////////////////////////////////////////////////////////////
	//
	// ボディ実行
	//
	
	/** 左辺のアトムを所属膜から除去する。*/
	private void removeLHSAtoms() {
		//Env.c("RuleCompiler::removeLHSAtoms");
		for (int i = 0; i < lhsatoms.size(); i++) {
			Atom atom = (Atom)lhsatoms.get(i);
			body.add( Instruction.removeatom(
				lhsatomToPath(atom), // ← lhsmems.size() + i に一致する
				lhsmemToPath(atom.mem), atom.functor ));
		}
	}
	/** 左辺のアトムを実行アトムスタックから除去する。*/
	private void dequeueLHSAtoms() {
		for (int i = 0; i < lhsatoms.size(); i++) {
			Atom atom = (Atom)lhsatoms.get(i);
			if (!atom.functor.equals(Functor.INSIDE_PROXY)
			 && !atom.functor.equals(Functor.OUTSIDE_PROXY)
			 && atom.functor.isSymbol() ) {
				body.add( Instruction.dequeueatom(
					lhsatomToPath(atom) // ← lhsmems.size() + i に一致する
					));
			}
		}
	}
	/** 左辺の膜を子膜側から再帰的に除去する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int removeLHSMem(Membrane mem) {
		//Env.c("RuleCompiler::removeLHSMem");
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int subcount = removeLHSMem(submem);
			body.add(new Instruction(Instruction.REMOVEMEM, lhsmemToPath(submem), lhsmemToPath(mem))); //第2引数追加 by mizuno
			if (subcount > 0) {
				body.add(new Instruction(Instruction.REMOVEPROXIES, lhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}	

	//
	
	HashMap rhsmappaths = new HashMap();	// 右辺の非線型$p出現(ProcessContext) -> mapの変数番号(Integer)
	static final int NOTCOPIED = -1;		// rhsmappaths未登録時の値
	private int rhspcToMapPath(ProcessContext pc) {
		if (!rhsmappaths.containsKey(pc)) return NOTCOPIED;
		return ((Integer)rhsmappaths.get(pc)).intValue();
	}
	
	//
	
	private boolean fUseMoveCells = true;	// 線型$pに対してMOVECELLSを使い最適化するか（開発時向け変数）

	/** 膜の階層構造およびプロセス文脈の内容を親膜側から再帰的に生成する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int buildRHSMem(Membrane mem) {
		Env.c("RuleCompiler::buildRHSMem" + mem);
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			if (pc.def.lhsOcc.mem == null) {
				error("SYSTEM ERROR: ProcessContext.def.lhsOcc.mem is not set");
			}
			if (rhsmemToPath(mem) != lhsmemToPath(pc.def.lhsOcc.mem)) {
				if (fUseMoveCells && /*pc.def.rhsOccs.get(0) == pc*/ pc.def.rhsOccs.size() == 1) {
					body.add(new Instruction(Instruction.MOVECELLS,
						rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
				} 
				else {
					int rethashmap = varcount++;
					body.add(new Instruction(Instruction.COPYMEM,
						rethashmap, rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
					rhsmappaths.put(pc,new Integer(rethashmap));
					//else {
					//	error("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + pc);
					//	corrupted();
					//}
				}
			}
		}
		it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int submempath = varcount++;
			rhsmempath.put(submem, new Integer(submempath));
			if (submem.pragmaAtHost != null) { // 右辺で＠指定されている場合
				if (submem.pragmaAtHost.def == null) {
					error("SYSTEM ERROR: pragmaAtHost.def is not set: " + submem.pragmaAtHost.getQualifiedName());
					corrupted();
				}
				int nodedescatomid = typedcxtToSrcPath(submem.pragmaAtHost.def);
				body.add( new Instruction(Instruction.NEWROOT, submempath, rhsmemToPath(mem),
					nodedescatomid) );
			}
			else { // 通常の右辺膜の場合
				body.add( Instruction.newmem(submempath, rhsmemToPath(mem) ) );
			}
			if (submem.name != null)
				body.add(new Instruction( Instruction.SETMEMNAME, submempath, submem.name.intern() ));
			int subcount = buildRHSMem(submem);
			if (subcount > 0) {
				body.add(new Instruction(Instruction.INSERTPROXIES,
					rhsmemToPath(mem), rhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}
	
	/** 右辺の膜内のルール文脈の内容を生成する */
	private void copyRules(Membrane mem) {
		Env.c("RuleCompiler::copyRules");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			copyRules((Membrane)it.next());
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();			
			if (rhsmemToPath(mem) == lhsmemToPath(rc.def.lhsOcc.mem)) continue;
			body.add(new Instruction( Instruction.COPYRULES, rhsmemToPath(mem), lhsmemToPath(rc.def.lhsOcc.mem) ));
		}
	}
	/** 右辺の膜内のルールの内容を生成する */	
	private void loadRulesets(Membrane mem) {
		Env.c("RuleCompiler::loadRulesets");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			loadRulesets((Membrane)it.next());
		}
		it = mem.rulesets.iterator();
		while (it.hasNext()) {
//			if (!mem.rules.isEmpty()) {
				body.add(Instruction.loadruleset(rhsmemToPath(mem), (runtime.Ruleset)it.next()));
//			} 
		}
	}
	
	/** 右辺の膜内のアトムを生成する。
	 * 単一化アトムならばUNIFY命令を生成し、
	 * それ以外ならば右辺のアトムのリストrhsatomsに追加する。 */
	private void buildRHSAtoms(Membrane mem) {
		Env.c("RuleCompiler::buildRHSAtoms");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			buildRHSAtoms((Membrane)it.next());
		}
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();			
			if (atom.functor.equals(FUNC_UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				body.add(new Instruction( Instruction.UNIFY,
					lhsatomToPath(link1.atom), link1.pos,
					lhsatomToPath(link2.atom), link2.pos, rhsmemToPath(mem) ));
			} else {
				int atomid = varcount++;
				rhsatompath.put(atom, new Integer(atomid));
				rhsatoms.add(atom);
				body.add( Instruction.newatom(atomid, rhsmemToPath(mem), atom.functor));
			}
		}
	}
	
	/** リンクの張り替えと生成を行う
	 * todo コードを整理する */
	private void updateLinks() {
		Env.c("RuleCompiler::updateLinks");
		
		// PART 1 - 右辺のアトムに出現するリンク
		Iterator it = rhsatoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence link = atom.args[pos].buddy;
				Membrane targetmem = atom.mem;
				if (atom.functor.equals(Functor.INSIDE_PROXY) && pos == 0) {
					targetmem = targetmem.mem;	// $inの第1引数は親膜が管理する
				}
				if (link.atom instanceof ProcessContext) {
					// アトムのリンク先がプロセス文脈/型付きプロセス文脈のとき
					ProcessContext pc = (ProcessContext)link.atom;
					if (pc.mem.typedProcessContexts.contains(pc)) {
						// パッシブ型制限より、右辺のアトムのリンク先の型付きプロセス文脈は右辺に限られる。
						// ( :- type($pc) | atom(X), $pc[X|] )
						if (gc.typedcxttypes.get(pc.def) == GuardCompiler.UNARY_ATOM_TYPE) {
							body.add( Instruction.newlink(
										rhsatomToPath(atom), pos,
										rhstypedcxtToPath(pc), 0,
										rhsmemToPath(targetmem) ));
						}
					} else { // 型付きでないとき
						// 左辺の型なしプロセス文脈はトップレベルに無く、左辺子膜内とは直接リンクできないため、
						// リンク先の型なしプロセス文脈は右辺に限られる。そして、そのプロセス文脈の
						// 左辺での出現における対応する自由リンクは、左辺のアトムに接続している。
						// ( { org(Y,), $pc[Y,|] } :- atom(X), $pc[X,|] )
						if (fUseMoveCells && pc.def.rhsOccs.size() == 1) {
							LinkOccurrence orglink = pc.def.lhsOcc.args[link.pos].buddy; // org引数のYの出現
							body.add( new Instruction(Instruction.RELINK,
											rhsatomToPath(atom), pos,
											lhsatomToPath(orglink.atom), orglink.pos,
											rhsmemToPath(targetmem) ));
						}
						else {
							LinkOccurrence orglink = pc.def.lhsOcc.args[link.pos].buddy; // org引数のYの出現
//							int srclink = varcount++;
							int srclink = lhslinkToPath(orglink.atom, orglink.pos);
							int copiedlink = varcount++;
							int atomlink = varcount++;
//							body.add( new Instruction(Instruction.GETLINK,
//											srclink, lhsatomToPath(orglink.atom), orglink.pos ));
							body.add( new Instruction(Instruction.LOOKUPLINK,
											copiedlink, rhspcToMapPath(pc),
											srclink));
							body.add( new Instruction(Instruction.ALLOCLINK,
											atomlink, rhsatomToPath(atom), pos ));
							body.add( new Instruction(Instruction.UNIFYLINKS,
											copiedlink, atomlink, rhsmemToPath(targetmem) ));
						}
					}
					continue;
				}
				// リンク先はアトム
				if (link.atom.mem == rs.leftMem) { // ( buddy(X) :- atom(X) )
					body.add( new Instruction(Instruction.RELINK,//LOCALRELINKに修正予定
						rhsatomToPath(atom), pos,
						lhsatomToPath(link.atom), link.pos,
						rhsmemToPath(targetmem) ));
				} else { // ( :- buddy(X), atom(X) )
					if (rhsatomToPath(atom) < rhsatomToPath(link.atom)
					|| (rhsatomToPath(atom) == rhsatomToPath(link.atom) && pos < link.pos)) {
						body.add( new Instruction(Instruction.NEWLINK,
										rhsatomToPath(atom), pos,
										rhsatomToPath(link.atom), link.pos,
										rhsmemToPath(targetmem) ));
					}
				}
			}
		}
		// PART 2 - 右辺の型付きプロセス文脈に出現するリンク
		it = rhstypedcxtpaths.keySet().iterator();
		while (it.hasNext()) {
			ProcessContext atom = (ProcessContext)it.next();
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence link = atom.args[pos].buddy;
				if (link == null) {
					error("SYSTEM ERROR: buddy of process context explicit free link is not set");
				}
				if (!(link.atom instanceof ProcessContext)) {
					// 型付きプロセス文脈のリンク先がアトムのとき
					if (lhsatoms.contains(link.atom)) { // ( buddy(X) :- type($atom) | $atom[X|] )
						if (gc.typedcxttypes.get(atom.def) == GuardCompiler.UNARY_ATOM_TYPE) {
							body.add( new Instruction(Instruction.RELINK,
								rhstypedcxtToPath(atom), 0,
								lhsatomToPath(link.atom), link.pos,
								rhsmemToPath(atom.mem) ));
						}
					}
					else if (rhsatoms.contains(link.atom)) { // ( :- type($atom) | buddy(X), $atom[X|] )
						// PART1でnewlink済みなので、何もしない
					}
					else {
						error("SYSTEM ERROR: unknown buddy of body typed process context");
						corrupted();
					}
					continue;
				}
				ProcessContext buddypc = (ProcessContext)link.atom;
				if (buddypc.mem.typedProcessContexts.contains(buddypc)) {
					// リンク先が型付きプロセス文脈のとき、パッシブ型制限より、リンク先も右辺。
					// ( :- type($atom),type($buddypc) | $buddypc[X|], $atom[X|] )
					if (rhstypedcxtToPath(atom) < rhstypedcxtToPath(buddypc)
					 || (rhstypedcxtToPath(atom) == rhstypedcxtToPath(buddypc) && pos < link.pos)) {
						if (gc.typedcxttypes.get(atom.def) == GuardCompiler.UNARY_ATOM_TYPE
						 && gc.typedcxttypes.get(buddypc.def) == GuardCompiler.UNARY_ATOM_TYPE) {
							body.add( new Instruction(Instruction.NEWLINK,
								rhstypedcxtToPath(atom), 0,
								rhstypedcxtToPath(buddypc), 0,
								rhsmemToPath(atom.mem) ));
						}
					}
				}
				else {
					// リンク先が型付きでないプロセス文脈のとき、PART1と同じ理由で$buddypcは右辺。
					// ( {org(Y,), $buddypc[Y,|]} :- type($atom) | $buddypc[X,|], $atom[X|] )
					LinkOccurrence orglink = buddypc.def.lhsOcc.args[pos].buddy; // org引数のYの出現
					if (gc.typedcxttypes.get(atom.def) == GuardCompiler.UNARY_ATOM_TYPE) {
						body.add( new Instruction(Instruction.RELINK,
												rhstypedcxtToPath(atom), 0,
												lhsatomToPath(orglink.atom), orglink.pos,
												rhsmemToPath(atom.mem) ));
					}
				}
			}
		}
		// PART 3 - 右辺の型付きでないプロセス文脈に出現するリンク
		it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext()) {
				ProcessContext atom = (ProcessContext)it2.next();
				for (int pos = 0; pos < atom.functor.getArity(); pos++) {
					LinkOccurrence link = atom.args[pos].buddy;	// 明示的な自由リンクのリンク先のリンク出現
					if (!(link.atom instanceof ProcessContext)) {
						// 型付きでないプロセス文脈のリンク先がアトムのとき
						if (lhsatoms.contains(link.atom)) { // リンク先は左辺のトップレベル
							// ( {src(Z,),$atom[Z,|]},buddy(X) :- $atom[X,|] )
							if (fUseMoveCells && atom.def.rhsOccs.size() == 1) {							
								LinkOccurrence srclink = atom.def.lhsOcc.args[pos].buddy; // src引数のZの出現
								body.add( new Instruction(Instruction.LOCALUNIFY,
													lhsatomToPath(link.atom), link.pos,
													lhsatomToPath(srclink.atom), srclink.pos,
													rhsmemToPath(atom.mem) )); // 本膜
							}
							else {
								LinkOccurrence srclink = atom.def.lhsOcc.args[pos].buddy; // src引数のZの出現
//								int srclinkid  = varcount++;
								int srclinkid  = lhslinkToPath(srclink.atom, srclink.pos); 
								int copiedlink = varcount++;
//								int buddylink  = varcount++;
								int buddylink  = lhslinkToPath(link.atom, link.pos); 
//								body.add( new Instruction(Instruction.GETLINK,
//												srclinkid, lhsatomToPath(srclink.atom), srclink.pos ));
								body.add( new Instruction(Instruction.LOOKUPLINK,
												copiedlink, rhspcToMapPath(atom), srclinkid));
//								body.add( new Instruction(Instruction.GETLINK,
//												buddylink, lhsatomToPath(link.atom), link.pos ));
								body.add( new Instruction(Instruction.UNIFYLINKS,
												copiedlink, buddylink, rhsmemToPath(atom.mem) ));
							}
						}
						else if (rhsatoms.contains(link.atom)) { // ( :- buddy(X), $atom[X,|] )
							// PART1でnewlink済みなので、何もしない
						}
						else {
							error("SYSTEM ERROR: unknown buddy of body typed process context");
							corrupted();
						}
						continue;
					}
					else {
						ProcessContext buddypc = (ProcessContext)link.atom;
						if (buddypc.mem.typedProcessContexts.contains(buddypc)) {
							// PART2でrelink済みなので、何もしない
						}
						else {
							// リンク先が型付きでないプロセス文脈のとき、PART1と同じ理由で$buddypcは右辺。
							// ( {org(Y,),$buddypc[Y,|]},{src(Z,),$atom[Z,|]} :- $buddypc[X,|],$atom[X,|] )
							LinkOccurrence orglink = buddypc.def.lhsOcc.args[link.pos].buddy; // org引数のYの出現
							LinkOccurrence srclink = atom.   def.lhsOcc.args[pos     ].buddy; // src引数のZの出現

							// 線型   → 左辺の$p出現内の対応するリンク出現の反対側（＝orglinkまたはsrclink）
							// 非線型 → (mapの変数番号,明示的な引数内のpos)
							int atommap  = rhspcToMapPath(atom);
							int atompos  = pos;
							int buddymap = rhspcToMapPath(buddypc);
							int buddypos = link.pos; 
							if (atommap == -1) {
								atommap = lhsatomToPath(srclink.atom);
								atompos = srclink.pos;
							}
							if (buddymap == -1) {
								buddymap = lhsatomToPath(orglink.atom);
								buddypos = orglink.pos;
							}
							
							if (atommap < buddymap || (atommap == buddymap && atompos < buddypos)) {
								if (fUseMoveCells && atom.def.rhsOccs.size() == 1 && buddypc.def.rhsOccs.size() == 1) {
									body.add( new Instruction(Instruction.UNIFY,
													lhsatomToPath(srclink.atom), srclink.pos,
													lhsatomToPath(orglink.atom), orglink.pos,
													rhsmemToPath(atom.mem) ));
								}
								else {
//									int buddylink       = varcount++;
									int buddylink       = lhslinkToPath(srclink.atom, srclink.pos); 
									int buddycopiedlink = buddylink;
//									body.add( new Instruction(Instruction.GETLINK,
//													buddylink, lhsatomToPath(srclink.atom), srclink.pos ));
									if (buddypc.def.rhsOccs.size() != 1) {
										buddycopiedlink = varcount++;
										body.add( new Instruction(Instruction.LOOKUPLINK,
														buddycopiedlink, rhspcToMapPath(buddypc), buddylink ));
									}							
//									int atomlink       = varcount++;
									int atomlink       = lhslinkToPath(orglink.atom, orglink.pos); 
									int atomcopiedlink = atomlink;
//									body.add( new Instruction(Instruction.GETLINK,
//													atomlink, lhsatomToPath(orglink.atom), orglink.pos ));
									if (atom.def.rhsOccs.size() != 1) {
										atomcopiedlink = varcount++;
										body.add( new Instruction(Instruction.LOOKUPLINK,
														atomcopiedlink, rhspcToMapPath(atom), atomlink ));
									}
									body.add( new Instruction(Instruction.UNIFYLINKS,
													buddycopiedlink, atomcopiedlink, rhsmemToPath(atom.mem) ));
								}
							}
						}
					}
				}
			}
		}
	}
	/** 右辺のアトムを実行アトムスタックに積む */
	private void enqueueRHSAtoms() {
		int index = body.size(); // 末尾再帰最適化の効果を最大化するため、逆順に積む（コードがセコい）
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (!atom.functor.equals(Functor.INSIDE_PROXY)
			 && !atom.functor.equals(Functor.OUTSIDE_PROXY) 
			 && atom.functor.isSymbol()
			 && atom.functor.isActive() ) {
				body.add(index, new Instruction(Instruction.ENQUEUEATOM, rhsatomToPath(atom)));
			}
		}
	}
	/** インラインコードを実行する命令を生成する */
	private void addInline() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			int atomID = rhsatomToPath(atom);
			Inline.register(unitName, atom.functor.getName());
			int codeID = Inline.getCodeID(unitName, atom.functor.getName());
			if(codeID == -1) continue;
			body.add( new Instruction(Instruction.INLINE, atomID, unitName, codeID));
		}
	}
	static final Functor FUNC_USE = new Functor("use",1);
	/** モジュールを読み込む */
	private void addRegAndLoadModules() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			//REG
			if(atom.functor.getArity()==1 && atom.functor.getName().equals("module")) {
				Module.regMemName(atom.args[0].buddy.atom.functor.getName(), atom.mem);
			}
			
			//LOAD
			if (atom.functor.equals(FUNC_USE)) {
				body.add( new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem),
					atom.args[0].buddy.atom.functor.getName()) );
			}
			String path = atom.getPath(); // .functor.path;
			if(path!=null && !path.equals(atom.mem.name)) {
				// この時点では解決できないモジュールがあるので名前にしておく
				body.add( new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem), path));
			}
		}
	}
	/**（再利用された膜または）新しいルート膜に対して、子孫膜から順番にUNLOCKMEMを発行する。
	 * ただし現在の実装では、この時点ではまだ膜は再利用されていない。*/
	private void unlockReusedOrNewRootMem(Membrane mem) {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) unlockReusedOrNewRootMem((Membrane)it.next());
		if (mem.pragmaAtHost != null) { // 右辺で＠指定されている場合
			body.add(new Instruction(Instruction.UNLOCKMEM, rhsmemToPath(mem)));
		}
	}
	/** 左辺の膜を廃棄する */
	private void freeLHSMem(Membrane mem) {
		Env.c("RuleCompiler::freeLHSMem");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			freeLHSMem(submem);
			// 再利用された場合freeしてはいけない
			body.add(new Instruction(Instruction.FREEMEM, lhsmemToPath(submem)));
		}
	}
	/** 左辺のアトムを廃棄する */
	private void freeLHSAtoms() {
		for (int i = 0; i < lhsatoms.size(); i++) {
			body.add( new Instruction(Instruction.FREEATOM, lhsmems.size() + i ));
		}
	}
	
	/** デバッグ用表示 */
	private void showInstructions() {
		Iterator it;
		it = atomMatch.listIterator();
		Env.d("--atomMatches:");
		while(it.hasNext()) Env.d((Instruction)it.next());
		
		it = memMatch.listIterator();
		Env.d("--memMatch:");
		while(it.hasNext()) Env.d((Instruction)it.next());
		
		it = body.listIterator();
		Env.d("--body:");
		while(it.hasNext()) Env.d((Instruction)it.next());
	}

	////////////////////////////////////////////////////////////////
	// 仮。LMNParserのものと統合し、おそらくEnvに移動する予定
	
	public void corrupted() {
		error("SYSTEM ERROR: error recovery for the previous error is not implemented");
	}
	public void error(String text) {
		System.out.println(text);
	}
	public void warning(String text) {
		System.out.println(text);
	}
}


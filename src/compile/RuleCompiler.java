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
	
	int varcount;
	
	List rhsatoms;
	/** 右辺のアトム (Atom) -> 変数番号 (Integer) */
	Map  rhsatompath;
	/** 右辺の膜 (Membrane) -> 変数番号 (Integer) */
	Map  rhsmempath;
	
	List lhsatoms;
	List lhsmems;
	/** 左辺のアトム (Atom) -> 変数番号 (Integer) */
	Map  lhsatompath;
	/** 左辺の膜 (Membrane) -> 変数番号 (Integer) */
	Map  lhsmempath;
	
//	private List newatoms = new ArrayList();	// rhsatomsと同じなので統合
	
	HeadCompiler hc;
	
	final int lhsmemToPath(Membrane mem) { return ((Integer)lhsmempath.get(mem)).intValue(); }
	final int rhsmemToPath(Membrane mem) { return ((Integer)rhsmempath.get(mem)).intValue(); }
	//final int lhsatomToID(Atom atom) { return lhsatomToPath(atom) - 1; }
	final int lhsatomToPath(Atom atom) { return ((Integer)lhsatompath.get(atom)).intValue(); } 
	final int rhsatomToPath(Atom atom) { return ((Integer)rhsatompath.get(atom)).intValue(); } 
	
	public String unitName;
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
	/** ヘッドのマッチング終了後の継続命令列のラベル */
	private InstructionList contLabel;
	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルする
	 */
	public Rule compile() {
		Env.c("compile");
		liftupActiveAtoms(rs.leftMem);
		simplify();
		theRule = new Rule(rs.toString());
		
		hc = new HeadCompiler(rs.leftMem);
		hc.enumFormals(rs.leftMem);	// ヘッドに対する仮引数リストを作る
		
		if (!rs.typedProcessContexts.isEmpty()) {
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
		
		optimize();	// optimize if $optlevel > 0
		
		return theRule;
	}
	
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
				
				// アトム主導
				InstructionList tmplabel = new InstructionList();
				tmplabel.insts = hc.match;
				atomMatch.add(new Instruction(Instruction.BRANCH, tmplabel));
				
				hc.mempaths.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
				Atom atom = (Atom)hc.atoms.get(firstid);
				hc.atompaths.put(atom, new Integer(1));	// 主導するアトムの変数番号は 1
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
					while (mem != rs.leftMem) {
						hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
						hc.match.add(new Instruction(Instruction.LOCK,     hc.varcount));
						hc.mempaths.put(mem, new Integer(hc.varcount++));
						mem = mem.mem;
					}
					hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
					hc.match.add(new Instruction(Instruction.EQMEM, 0, hc.varcount++));
				}
				hc.compileLinkedGroup((Atom)hc.atoms.get(firstid));
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
	
	// TODO spec命令を外側に持ち上げる最適化器を実装する
	
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
		addLoadModules();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms();
		freeLHSTypedProcesses();
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
	
	
	static final Object UNARY_ATOM_TYPE  = "U"; // 1引数アトム
	static final Object LINEAR_ATOM_TYPE = "L"; // 任意のプロセス $p[X|*V]
	static final Object GROUND_LINK_TYPE = "G"; // 基底項プロセス

	/** 型付きプロセス文脈定義 (ContextDef) -> データ型の種類を表すラップされた型検査命令番号(Integer) */
	HashMap typedcxtdatatypes = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> データ型のパターンを表す定数オブジェクト */
	HashMap typedcxttypes = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現の変数番号（def.src は現在未使用） */
	HashMap typedcxtsrcs  = new HashMap();
	/** 型付きプロセス文脈の右辺での出現 (Context) -> 変数番号 */
	HashMap rhstypedcxtpaths = new HashMap();
	/** ソース出現が特定された型付きプロセス文脈定義のセット
	 * <p>identifiedCxtdefs.contains(x) は、左辺に出現するかまたはloadedであること。*/
	HashSet identifiedCxtdefs = new HashSet(); 
	/** 型付きプロセス文脈定義のリスト（仮引数IDの管理に使用する）
	 * <p>実際にはtypedcxtsrcsのキーを追加された順番に並べたもの。*/
	List typedcxtdefs = new ArrayList();
		
	static final int UNBOUND = -1;
		
	int typedcxtToSrcPath(ContextDef def) {
		if (!typedcxtsrcs.containsKey(def)) return UNBOUND;
		return ((Integer)typedcxtsrcs.get(def)).intValue();
	}
	int rhstypedcxtToPath(Context cxt) {
		return ((Integer)rhstypedcxtpaths.get(cxt)).intValue();
	}
	
	static final int ISINT    = Instruction.ISINT;
	static final int ISFLOAT  = Instruction.ISFLOAT;
	static final int ISSTRING = Instruction.ISSTRING;
	static HashMap guardLibrary1 = new HashMap(); // 1入力ガード型制約名
	static HashMap guardLibrary2 = new HashMap(); // 2入力ガード型制約名
	static {
		guardLibrary2.put(new Functor("<.",   2), new int[]{ISFLOAT,ISFLOAT, Instruction.FLT});
		guardLibrary2.put(new Functor("=<.",  2), new int[]{ISFLOAT,ISFLOAT, Instruction.FLE});
		guardLibrary2.put(new Functor(">.",   2), new int[]{ISFLOAT,ISFLOAT, Instruction.FGT});
		guardLibrary2.put(new Functor(">=.",  2), new int[]{ISFLOAT,ISFLOAT, Instruction.FGE});
		guardLibrary2.put(new Functor("<",    2), new int[]{ISINT,  ISINT,   Instruction.ILT});
		guardLibrary2.put(new Functor("=<",   2), new int[]{ISINT,  ISINT,   Instruction.ILE});
		guardLibrary2.put(new Functor(">",    2), new int[]{ISINT,  ISINT,   Instruction.IGT});
		guardLibrary2.put(new Functor(">=",   2), new int[]{ISINT,  ISINT,   Instruction.IGE});
		guardLibrary2.put(new Functor("=:=",  2), new int[]{ISINT,  ISINT,   Instruction.IEQ});
		guardLibrary2.put(new Functor("=\\=", 2), new int[]{ISINT,  ISINT,   Instruction.INE});
		guardLibrary2.put(new Functor("=:=.", 2), new int[]{ISFLOAT,ISFLOAT, Instruction.FEQ});
		guardLibrary2.put(new Functor("=\\=.",2), new int[]{ISFLOAT,ISFLOAT, Instruction.FNE});
		guardLibrary2.put(new Functor("+.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FADD, ISFLOAT});
		guardLibrary2.put(new Functor("-.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FSUB, ISFLOAT});
		guardLibrary2.put(new Functor("*.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FMUL, ISFLOAT});
		guardLibrary2.put(new Functor("/.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FDIV, ISFLOAT});
		guardLibrary2.put(new Functor("+",    3), new int[]{ISINT,  ISINT,   Instruction.IADD, ISINT});
		guardLibrary2.put(new Functor("-",    3), new int[]{ISINT,  ISINT,   Instruction.ISUB, ISINT});
		guardLibrary2.put(new Functor("*",    3), new int[]{ISINT,  ISINT,   Instruction.IMUL, ISINT});
		guardLibrary2.put(new Functor("/",    3), new int[]{ISINT,  ISINT,   Instruction.IDIV, ISINT});
		guardLibrary2.put(new Functor("mod",  3), new int[]{ISINT,  ISINT,   Instruction.IMOD, ISINT});
		guardLibrary1.put(new Functor("int",   1), new int[]{ISINT});
		guardLibrary1.put(new Functor("string",1), new int[]{ISSTRING});
		guardLibrary1.put(new Functor("float", 1), new int[]{ISFLOAT});
		guardLibrary1.put(new Functor("float", 2), new int[]{ISINT,          Instruction.INT2FLOAT, ISFLOAT});
		guardLibrary1.put(new Functor("int",   2), new int[]{ISFLOAT,        Instruction.FLOAT2INT, ISINT});
	}	

	private void inc_head() {
		// ヘッドの取り込み
		lhsatoms = hc.atoms;
		lhsmems  = hc.mems;
		genLHSPaths();
		varcount = lhsatoms.size() + lhsmems.size();
	}
	private void inc_guard() {
		// ガードの取り込み
		varcount = lhsatoms.size() + lhsmems.size();
		// typedcxtdefs = gc.typedcxtdefs;
		genTypedProcessContextPaths();
//		varcount = lhsatoms.size() + lhsmems.size() + rs.typedProcessContexts.size();
	}
	private void genTypedProcessContextPaths() {
		Iterator it = typedcxtdefs.iterator();
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
	
	/** ガードをコンパイルする（仮） */
	private void compile_g() {
		inc_head();
		if (guard == null) return;
		int formals = varcount;
		fixTypedProcesses();
		guard.add( 0, Instruction.spec(formals,varcount) );
		guard.add( Instruction.jump(theRule.bodyLabel, gc_getMemActuals(),
			gc_getAtomActuals(), gc_getVarActuals()) );
	}
	public List gc_getMemActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < lhsmems.size(); i++) {
			args.add( lhsmempath.get(lhsmems.get(i)) );
		}
		return args;
	}
	public List gc_getAtomActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < lhsatoms.size(); i++) {
			args.add( lhsatompath.get(lhsatoms.get(i)) );
		}
		Iterator it = typedcxtdefs.iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (typedcxttypes.get(def) == UNARY_ATOM_TYPE)
				args.add( new Integer(typedcxtToSrcPath(def)) );
		}
		return args;
	}		
	public List gc_getVarActuals() {
		return new ArrayList();
	}
	private void fixTypedProcesses() {
		// 左辺に出現する型付きプロセス文脈を特定されたものとしてマークする。
		identifiedCxtdefs = new HashSet();
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.src != null) {
				if (def.src.mem == rs.guardMem) { def.src = null; } // 再呼び出しに対応（仮）
				else {
					identifiedCxtdefs.add(def);
					// 左辺の型付きプロセス文脈の明示的な自由リンクの先が、左辺のアトムに出現することを確認する。
					// 出現しない場合はコンパイルエラーとする。この制限を「パッシブ型制限」と呼ぶことにする。
					// 【注意】パッシブ型制限は、型は非アクティブなデータを表すことを想定することにより正当化される。
					// つまり、( 2(X) :- found(X) ) や ( 2(3) :- ok ) で2や3を$pで表すことはできない。
					// しかし実際には処理系側の都合による制限である。
					// なお、プログラミングの観点から、右辺の型付きプロセス文脈の明示的な自由リンクの先は任意としている。
					if (!lhsatompath.containsKey(def.src.args[0].buddy.atom)) {
						error("COMPILE ERROR: a partner atom is required for the head occurrence of typed process context: " + def.getName());
						corrupted();
						guard.add(new Instruction(Instruction.LOCK, 0));
						return;
					}
				}
			}
		}
		// 全ての型付きプロセス文脈が特定され、型が決定するまで繰り返す
		LinkedList cstrs = new LinkedList();
		it = rs.guardMem.atoms.iterator();
		while (it.hasNext()) cstrs.add(it.next());
		boolean changed;
		do {
			changed = false;
			ListIterator lit = cstrs.listIterator();
			while (lit.hasNext()) {
				Atom cstr = (Atom)lit.next();
				Functor func = cstr.functor;
				ContextDef def1 = null;
				ContextDef def2 = null;
				ContextDef def3 = null;
				if (func.getArity() > 0)  def1 = ((ProcessContext)cstr.args[0].buddy.atom).def;
				if (func.getArity() > 1)  def2 = ((ProcessContext)cstr.args[1].buddy.atom).def;
				if (func.getArity() > 2)  def3 = ((ProcessContext)cstr.args[2].buddy.atom).def;

				if (func.getSymbolFunctorID().equals("unary_1")) {
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					guard.add(new Instruction(Instruction.ISUNARY, atomid1));
				}
				else if (func.equals(new Functor("\\=",2))) {
					// NSAMEFUNC を作るか？
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = loadUnaryAtom(def2);
					guard.add(new Instruction(Instruction.ISUNARY, atomid1));
					guard.add(new Instruction(Instruction.ISUNARY, atomid2));
					int funcid1 = varcount++;
					int funcid2 = varcount++;
					guard.add(new Instruction(Instruction.GETFUNC, funcid1, atomid1));
					guard.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
					guard.add(new Instruction(Instruction.NEQFUNC, funcid1, funcid2));
				}
				else if (func.getSymbolFunctorID().equals("class_2")) {
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = varcount++;
					guard.add(new Instruction(Instruction.GETCLASS, atomid2, atomid1));
					bindToUnaryAtom(def2, atomid2);
					typedcxtdatatypes.put(def2, new Integer(ISSTRING));
				}
				else if (func instanceof runtime.IntegerFunctor) {
					bindToFunctor(def1, func);
					typedcxtdatatypes.put(def1, new Integer(ISINT));
				}
				else if (func instanceof runtime.FloatingFunctor) {
					bindToFunctor(def1, func);
					typedcxtdatatypes.put(def1, new Integer(ISFLOAT));
				}
				else if (func instanceof runtime.StringFunctor) {
					bindToFunctor(def1, func);
					typedcxtdatatypes.put(def1, new Integer(ISSTRING));
				}
//				else if (func instanceof runtime.ObjectFunctor
//				&& ((runtime.ObjectFunctor)func).getObject() instanceof String) {
//					bindToFunctor(def1, func);
//					typedcxtdatatypes.put(def1, new Integer(ISSTRING));
//				}
				else if (func.equals(FUNC_UNIFY)) {
					if (!identifiedCxtdefs.contains(def2)) {
						ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
						if (!identifiedCxtdefs.contains(def2)) continue;
					}
					int atomid2 = loadUnaryAtom(def2);
					if (!identifiedCxtdefs.contains(def1)) {
						// todo 複製された参照を実装する
						int funcid2 = varcount++;
						guard.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
						int atomid1 = varcount++;
						guard.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
						typedcxtsrcs.put(def1, new Integer(atomid1));
						typedcxtdefs.add(def1);
						identifiedCxtdefs.add(def1);
						typedcxttypes.put(def1, UNARY_ATOM_TYPE);
					}
					else bindToUnaryAtom(def1, atomid2);
					//
					Object newdatatype = typedcxtdatatypes.get(def2);
					if (newdatatype == null) newdatatype = typedcxtdatatypes.get(def1);
					typedcxtdatatypes.put(def1,newdatatype);
					typedcxtdatatypes.put(def2,newdatatype);
				}
				else if (func.equals(new Functor("==",2))) {
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					int atomid2 = loadUnaryAtom(def2);
					bindToUnaryAtom(def1, atomid2);
					//
					Object newdatatype = typedcxtdatatypes.get(def1);
					if (newdatatype == null) newdatatype = typedcxtdatatypes.get(def2);
					typedcxtdatatypes.put(def1,newdatatype);
					typedcxtdatatypes.put(def2,newdatatype);
				}
				else if (guardLibrary1.containsKey(func)) {
					int[] desc = (int[])guardLibrary1.get(func);
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					if (!new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						guard.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (func.getArity() == 1) {
						if (desc.length > 1) guard.add(new Instruction(desc[1], atomid1));
					}
					else {
						int atomid2 = varcount++;
						guard.add(new Instruction(desc[1], atomid2, atomid1));
						bindToUnaryAtom(def2, atomid2);
						typedcxtdatatypes.put(def2, new Integer(desc[2]));
					}
				}
				else if (guardLibrary2.containsKey(func)) {
					int[] desc = (int[])guardLibrary2.get(func);
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = loadUnaryAtom(def2);
					if (!new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						guard.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (!new Integer(desc[1]).equals(typedcxtdatatypes.get(def2))) {
						guard.add(new Instruction(desc[1], atomid2));
						typedcxtdatatypes.put(def1, new Integer(desc[1]));
					}
					if (func.getArity() == 2) {
						guard.add(new Instruction(desc[2], atomid1, atomid2));
					}
					else {
						int atomid3 = varcount++;
						guard.add(new Instruction(desc[2], atomid3, atomid1, atomid2));
						bindToUnaryAtom(def3, atomid3);
						typedcxtdatatypes.put(def3, new Integer(desc[3]));
					}
				}
				else {
					error("COMPILE ERROR: unrecognized guard type constraint name: " + cstr);
					corrupted();
					guard.add(new Instruction(Instruction.LOCK, 0));
					return;
				}
				lit.remove();
				changed = true;
			}
			if (cstrs.isEmpty()) return;
		}
		while (changed);
		// 型付け失敗
		guard.add(new Instruction(Instruction.LOCK, 0));
		error("COMPILE ERROR: never proceeding guard type constraints: " + cstrs);
		corrupted();
	}
	/** 型付きプロセス文脈defを1引数ファンクタfuncで束縛する */
	private void bindToFunctor(ContextDef def, Functor func) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			int atomid = varcount++;
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
			guard.add(new Instruction(Instruction.ALLOCATOM, atomid, func));			
		}
		else {
			int atomid = typedcxtToSrcPath(def);
			if (atomid == UNBOUND) {
				LinkOccurrence srclink = def.src.args[0].buddy; // defのソース出現を指すアトム側の引数
				atomid = varcount++;
				guard.add(new Instruction(Instruction.DEREFATOM,
					atomid, lhsatomToPath(srclink.atom), srclink.pos));
				typedcxtsrcs.put(def, new Integer(atomid));
				typedcxtdefs.add(def);
			}
			guard.add(new Instruction(Instruction.FUNC, atomid, func));
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
	}
	/** 型付きプロセス文脈defを1引数アトム$atomidのファンクタで束縛する */
	private void bindToUnaryAtom(ContextDef def, int atomid) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
		}
		else {
			int loadedatomid = typedcxtToSrcPath(def);
			if (loadedatomid == UNBOUND) {
				LinkOccurrence srclink = def.src.args[0].buddy;
				loadedatomid = varcount++;
				guard.add(new Instruction(Instruction.DEREFATOM,
					loadedatomid, lhsatomToPath(srclink.atom), srclink.pos));
				typedcxtsrcs.put(def, new Integer(loadedatomid));
				typedcxtdefs.add(def);
			}
			guard.add(new Instruction(Instruction.SAMEFUNC, atomid, loadedatomid));
//			int funcid1 = varcount++;
//			int funcid2 = varcount++;
//			guard.add(new Instruction(Instruction.GETFUNC, funcid1, atomid));
//			guard.add(new Instruction(Instruction.GETFUNC, funcid2, loadedatomid));
//			guard.add(new Instruction(Instruction.EQFUNC,  funcid1, funcid2));
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
	}
	/** 型付きプロセス文脈defの（特定されている）ソース出現の
	 * （明示的な自由リンクが出現する）アトムを取得する。
	 * また、このアトムが1引数であると仮定して、型情報を更新する。
	 * @return 取得したアトムの変数番号 */
	private int loadUnaryAtom(ContextDef def) {
		int atomid = typedcxtToSrcPath(def);
		if (atomid == UNBOUND) {
			LinkOccurrence srclink = def.src.args[0].buddy;
			atomid = varcount++;
			guard.add(new Instruction(Instruction.DEREFATOM,
				atomid, lhsatomToPath(srclink.atom), srclink.pos));
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
		return atomid;
	}
	
	private void removeLHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Context pc = def.src;
			if (pc != null) { // ヘッドのときのみ
				if (typedcxttypes.get(def) == UNARY_ATOM_TYPE) {
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
			if (typedcxttypes.get(def) == UNARY_ATOM_TYPE) {
				body.add(new Instruction( Instruction.FREEATOM,
					typedcxtToSrcPath(def) ));
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
				if (typedcxttypes.get(def) == UNARY_ATOM_TYPE) {
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

	/** ルールの左辺と右辺に対してstaticUnifyを呼ぶ */
	public void simplify() {
		staticUnify(rs.leftMem);
		staticUnify(rs.rightMem);
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
						// ( X=Y :- p(X,Y) ) は意味解析エラー（=は通常のヘッドアトムと見なして放置される）
						error("COMPILE ERROR: head contains body unification");
					}
					else {
						// ( p(X,Y) :- X=Y ) はUNIFYボディ命令を出力するのでここでは何もしない
					}
				} else {
					link1.buddy = link2;
					link2.buddy = link1;
					link2.name = link1.name;
					removedAtoms.add(atom);
				}
			}
		}
		it = removedAtoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atom.mem.atoms.remove(atom);
		}
	}
	
	/** ヘッドの膜とアトムに対して、仮引数番号を登録する */
	private void genLHSPaths() {
		Env.c("RuleCompiler::genLHSMemPaths");
		lhsatompath = new HashMap();
		lhsmempath  = new HashMap();
		for (int i = 0; i < lhsmems.size(); i++) {
			lhsmempath.put(lhsmems.get(i), new Integer(i));
		}
		for (int i = 0; i < lhsatoms.size(); i++) {
			lhsatompath.put(lhsatoms.get(i), new Integer( lhsmems.size() + i ));
		}
		//Env.d("lhsmempaths"+lhsmempaths);
	}
	
	private void optimize() {
		Env.c("optimize");
//		Optimizer.optimize(memMatch, body);
		Optimizer.optimizeRule(theRule);
	}	
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

	/** 膜の階層構造およびプロセス文脈の内容を親膜側から再帰的に生成する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int buildRHSMem(Membrane mem) {
		Env.c("RuleCompiler::buildRHSMem" + mem);
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			if (pc.def.src.mem == null) {
				error("SYSTEM ERROR: ProcessContext.def.src.mem is not set");
			}
			if (rhsmemToPath(mem) != lhsmemToPath(pc.def.src.mem)) {
				if (pc.def.rhsOccs.get(0) == pc) {
					body.add(new Instruction(Instruction.MOVECELLS,
						rhsmemToPath(mem), lhsmemToPath(pc.def.src.mem) ));
				} 
				//else {
				//	error("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + pc);
				//	corrupted();
				//}
			}
		}
		it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			//
			Module.regMemName(submem.name, submem);
			int submempath = varcount++;
			rhsmempath.put(submem, new Integer(submempath));
			body.add( Instruction.newmem(submempath, rhsmemToPath(mem) ) );
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
			if (rhsmemToPath(mem) == lhsmemToPath(rc.def.src.mem)) continue;
			body.add(new Instruction( Instruction.COPYRULES, rhsmemToPath(mem), lhsmemToPath(rc.def.src.mem) ));
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
					lhsatomToPath(link2.atom), link2.pos));
			} else {
				int atomid = varcount++;
				rhsatompath.put(atom, new Integer(atomid));
				rhsatoms.add(atom);
				body.add( Instruction.newatom(atomid, rhsmemToPath(mem), atom.functor));
			}
		}
	}
	/** リンクの張り替えと生成を行う
	 * TODO コードを整理する */
	private void updateLinks() {
		Env.c("RuleCompiler::updateLinks");
		
		// PART 1 - 右辺のアトムに出現するリンク
		Iterator it = rhsatoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();			
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence link = atom.args[pos].buddy;
				if (link.atom instanceof ProcessContext) {
					// アトムのリンク先がプロセス文脈/型付きプロセス文脈のとき
					ProcessContext pc = (ProcessContext)link.atom;
					if (pc.mem.typedProcessContexts.contains(pc)) {
						// パッシブ型制限より、右辺のアトムのリンク先の型付きプロセス文脈は右辺に限られる。
						// ( :- type($pc) | atom(X), $pc[X|] )
						if (typedcxttypes.get(pc.def) == UNARY_ATOM_TYPE) {
							body.add( Instruction.newlink(
											rhsatomToPath(atom), pos,
											rhstypedcxtToPath(pc), 0,
											rhsmemToPath(atom.mem) ));
						}
					} else { // 型付きでないとき
						// 左辺の型なしプロセス文脈はトップレベルに無く、左辺子膜内とは直接リンクできないため、
						// リンク先の型なしプロセス文脈は右辺に限られる。そして、そのプロセス文脈の
						// 左辺での出現における対応する自由リンクは、左辺のアトムに接続している。
						// ( { org(Y,), $pc[Y,|] } :- atom(X), $pc[X,|] )
						LinkOccurrence orglink = pc.buddy.args[link.pos].buddy; // org引数のYの出現
							body.add( new Instruction(Instruction.RELINK,
											rhsatomToPath(atom), pos,
											lhsatomToPath(orglink.atom), orglink.pos,
											rhsmemToPath(atom.mem) ));
					}
					continue;
				}
				// リンク先はアトム
				if (link.atom.mem == rs.leftMem) { // ( buddy(X) :- atom(X) )
					body.add( new Instruction(Instruction.RELINK,//LOCALRELINKに修正予定
						rhsatomToPath(atom), pos,
						lhsatomToPath(link.atom), link.pos,
						rhsmemToPath(atom.mem) ));
				} else { // ( :- buddy(X), atom(X) )
					if (rhsatomToPath(atom) < rhsatomToPath(link.atom)
					|| (rhsatomToPath(atom) == rhsatomToPath(link.atom) && pos < link.pos)) {
						body.add( new Instruction(Instruction.NEWLINK,
							rhsatomToPath(atom), pos,
							rhsatomToPath(link.atom), link.pos,
							rhsmemToPath(atom.mem) ));
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
						if (typedcxttypes.get(atom.def) == UNARY_ATOM_TYPE) {
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
						if (typedcxttypes.get(atom.def) == UNARY_ATOM_TYPE
						 && typedcxttypes.get(buddypc.def) == UNARY_ATOM_TYPE) {
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
					LinkOccurrence orglink = buddypc.buddy.args[pos].buddy; // org引数のYの出現
					if (typedcxttypes.get(atom.def) == UNARY_ATOM_TYPE) {
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
					LinkOccurrence link = atom.args[pos].buddy;
					if (!(link.atom instanceof ProcessContext)) {
						// 型付きでないプロセス文脈のリンク先がアトムのとき
						if (lhsatoms.contains(link.atom)) { // リンク先は左辺のトップレベル
							// ( {src(Z,),$atom[Z,|]},buddy(X) :- $atom[X,|] )
							LinkOccurrence srclink = atom.buddy.args[pos].buddy; // src引数のZの出現
							body.add( new Instruction(Instruction.LOCALUNIFY,
								lhsatomToPath(link.atom), link.pos,
								lhsatomToPath(srclink.atom), srclink.pos,
								rhsmemToPath(atom.mem) )); // 本膜
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
						if (!buddypc.mem.typedProcessContexts.contains(buddypc)) {
							// リンク先が型付きでないプロセス文脈のとき、PART1と同じ理由で$buddypcは右辺。
							// ( {org(Y,),$buddypc[Y,|]},{src(Z,),$atom[Z,|]} :- $buddypc[X,|],$atom[X,|] )
							LinkOccurrence orglink = buddypc.buddy.args[link.pos].buddy; // org引数のYの出現
							LinkOccurrence srclink = atom.   buddy.args[link.pos].buddy; // src引数のZの出現
							body.add( new Instruction(Instruction.UNIFY,
														lhsatomToPath(srclink.atom), srclink.pos,
														lhsatomToPath(orglink.atom), orglink.pos,
														rhsmemToPath(atom.mem) ));
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
	private void addLoadModules() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
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

	/**
	 * デバッグ用表示
	 */
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


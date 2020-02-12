package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import runtime.Env;
import runtime.Instruction;
import runtime.functor.Functor;
import runtime.functor.SymbolFunctor;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;

class GuardCompiler extends HeadCompiler
{
	static final Object UNARY_ATOM_TYPE  = "U"; // 1引数アトム
	static final Object GROUND_LINK_TYPE = "G"; // 基底項プロセス
	static final Object HLGROUND_LINK_TYPE = "HLG"; // ハイパーリンク基底項プロセス
//	static final Object LINEAR_ATOM_TYPE = "L"; // 任意のプロセス $p[X|*V]
	
	/** 型付きプロセス文脈定義 (ContextDef) -> データ型の種類を表すラップされた型検査命令番号(Integer) */
	HashMap<ContextDef, Integer> typedCxtDataTypes = new HashMap<>();
	/** 型付きプロセス文脈定義 (ContextDef) -> データ型のパターンを表す定数オブジェクト */
	HashMap<ContextDef, Object> typedCxtTypes = new HashMap<>();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号 */
	HashMap<ContextDef, Integer> typedCxtSrcs  = new HashMap<>();
	/** ground型付きプロセス文脈定義(ContextDef) -> リンクのソース出現（コピー元とする出現）のリストの変数番号 */
	HashMap<ContextDef, Integer> groundSrcs = new HashMap<>();
	/** 膜(Membrane) -> (その膜に存在するground型付きプロセス文脈定義(ContextDef) -> 構成アトム数)というマップ */
	HashMap<Membrane, HashMap<ContextDef, Integer>> memToGroundSizes = new HashMap<>();
	/** ソース出現が特定された型付きプロセス文脈定義のセット
	 * <p>identifiedCxtdefs.contains(x) は、左辺に出現するかまたはloadedであることを表す。*/
	HashSet<ContextDef> identifiedCxtdefs = new HashSet<>();
	/** 型付きプロセス文脈定義のリスト（仮引数IDの管理に使用する）
	 * <p>実際にはtypedcxtsrcsのキーを追加された順番に並べたもの。*/
	List<ContextDef> typedCxtDefs = new ArrayList<>();
	/** newアトム -> newアトムの引数の接続先アトム一覧 */
  	HashMap<Atom, Atom[]> newAtomArgAtoms = new HashMap<>(); // hlgroundattr@onuma
	/** hlground型付きプロセス文脈定義(ContextDef) -> hlgroundの属性 */
	HashMap<ContextDef, Atom[]> hlgroundAttrs = new HashMap<>();
	
  	public List<Functor> getHlgroundAttrs(Atom[] hlgroundArgAtoms) {
		List<Functor> attrs = new ArrayList<>(); // hlgroundの属性
		for (Atom a : hlgroundArgAtoms) {
			if (a != null) {
				attrs.add(a.functor);	
			}
		}
		return attrs;
  	}
  	
	private int typedcxtToSrcPath(ContextDef def) {
		if (!typedCxtSrcs.containsKey(def)) return UNBOUND;
		return typedCxtSrcs.get(def);
	}

	private int groundToSrcPath(ContextDef def) {
		if (!groundSrcs.containsKey(def)) return UNBOUND;
		return groundSrcs.get(def);
	}

	private static final int ISINT    = Instruction.ISINT;		// 型制約の引数が整数型であることを表す
	private static final int ISFLOAT  = Instruction.ISFLOAT;	// 〃 浮動小数点数型
	private static final int ISSTRING = Instruction.ISSTRING;	// 〃 文字列型
	// private static final int ISMEM    = Instruction.ANYMEM;		// 〃 膜（getRuntime専用）
//	private static final int ISNAME    = Instruction.ISNAME;   	// 〃 name型 (SLIM専用) //seiji
//	private static final int ISCONAME  = Instruction.ISCONAME; 	// 〃 coname型 (SLIM専用) //seiji
	private static final int ISHLINK   = Instruction.ISHLINK; 	// 〃 hlink型 (SLIM専用) //seiji
	private static Map<Functor, int[]> guardLibrary0 = new HashMap<>(); // 0入力ガード型制約名//seiji
	private static Map<Functor, int[]> guardLibrary1 = new HashMap<>(); // 1入力ガード型制約名
	private static Map<Functor, int[]> guardLibrary2 = new HashMap<>(); // 2入力ガード型制約名

	static
	{
		// ガード制約を予め（手動）コンパイルしておく
		putLibrary("<."   , 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FLT));
		putLibrary("=<."  , 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FLE));
		putLibrary(">."   , 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FGT));
		putLibrary(">=."  , 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FGE));
		putLibrary("<"    , 2, 2, array(ISINT  , ISINT  , Instruction.ILT));
		putLibrary("=<"   , 2, 2, array(ISINT  , ISINT  , Instruction.ILE));
		putLibrary(">"    , 2, 2, array(ISINT  , ISINT  , Instruction.IGT));
		putLibrary(">="   , 2, 2, array(ISINT  , ISINT  , Instruction.IGE));
		putLibrary("=:="  , 2, 2, array(ISINT  , ISINT  , Instruction.IEQ));
		putLibrary("=\\=" , 2, 2, array(ISINT  , ISINT  , Instruction.INE));
		putLibrary("=:=." , 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FEQ));
		putLibrary("=\\=.", 2, 2, array(ISFLOAT, ISFLOAT, Instruction.FNE));
		putLibrary("+."   , 3, 2, array(ISFLOAT, ISFLOAT, Instruction.FADD, ISFLOAT));
		putLibrary("-."   , 3, 2, array(ISFLOAT, ISFLOAT, Instruction.FSUB, ISFLOAT));
		putLibrary("*."   , 3, 2, array(ISFLOAT, ISFLOAT, Instruction.FMUL, ISFLOAT));
		putLibrary("/."   , 3, 2, array(ISFLOAT, ISFLOAT, Instruction.FDIV, ISFLOAT));
		putLibrary("+"    , 3, 2, array(ISINT  , ISINT  , Instruction.IADD, ISINT));
		putLibrary("-"    , 3, 2, array(ISINT  , ISINT  , Instruction.ISUB, ISINT));
		putLibrary("*"    , 3, 2, array(ISINT  , ISINT  , Instruction.IMUL, ISINT));
		putLibrary("/"    , 3, 2, array(ISINT  , ISINT  , Instruction.IDIV, ISINT));
		putLibrary("mod"  , 3, 2, array(ISINT  , ISINT  , Instruction.IMOD, ISINT));
		putLibrary("logand" , 3, 2, array(ISINT  , ISINT  , Instruction.IAND, ISINT));
		putLibrary("logior" , 3, 2, array(ISINT  , ISINT  , Instruction.IOR, ISINT));
		putLibrary("logxor" , 3, 2, array(ISINT  , ISINT  , Instruction.IXOR, ISINT));
//		putLibrary("ash" , 3, 2, array(ISINT  , ISINT  , Instruction.IASH, ISINT));
		putLibrary("int"  , 1, 1, array(ISINT));
		putLibrary("float", 1, 1, array(ISFLOAT));
		putLibrary("+"    , 2, 1, array(ISINT  ,      -1,            ISINT));
		putLibrary("-"    , 2, 1, array(ISINT  , Instruction.INEG,   ISINT));
//		putLibrary("lognot"    , 2, 1, array(ISINT  , Instruction.INOT,   ISINT));
		putLibrary("+."   , 2, 1, array(ISFLOAT,      -1,            ISFLOAT));
		putLibrary("-."   , 2, 1, array(ISFLOAT, Instruction.FNEG,   ISFLOAT));
		putLibrary("float", 2, 1, array(ISINT  , Instruction.INT2FLOAT, ISFLOAT));
		putLibrary("int",   2, 1, array(ISFLOAT, Instruction.FLOAT2INT, ISINT));
		if (Env.hyperLink)
		{
			putLibrary("new"       , 1, 0, array(Instruction.NEWHLINK, ISINT));
			putLibrary("make"      , 2, 1, array(ISINT, Instruction.MAKEHLINK, ISINT));
			putLibrary("hlink"     , 1, 1, array(ISHLINK));
			putLibrary("num"       , 2, 1, array(ISHLINK, Instruction.GETNUM, ISINT));
			//putLibrary("name"      , 1, 1, array(ISNAME));
			//putLibrary("coname"    , 1, 1, array(ISCONAME));
			//putLibrary("setconame" , 2, 1, array(ISNAME, Instruction.SETCONAME, ISINT));
			//putLibrary("!"         , 2, 1, array(ISNAME, Instruction.SETCONAME, ISINT));
			//putLibrary("hasconame" , 1, 1, array(ISNAME, Instruction.HASCONAME));
			//putLibrary("nhasconame", 1, 1, array(ISNAME, Instruction.NHASCONAME));
			//putLibrary("getconame" , 2, 1, array(ISNAME, Instruction.GETCONAME, ISINT));
			//putLibrary("getname"   , 2, 1, array(ISCONAME, Instruction.GETNAME, ISINT));
			//putLibrary("><"        , 3, 2, array(ISNAME, ISNAME, Instruction.UNIFYHLINK, ISINT));
			//putLibrary("and"       , 3, 2, array(ISNAME, ISNAME, Instruction.UNIFYCONAMEAND, ISINT));
			//putLibrary("or"        , 3, 2, array(ISNAME, ISNAME, Instruction.UNIFYCONAMEOR, ISINT));
		}
	}

	/**
	 * 名前、アリティによって表される {@code input} 入力ガード制約にコンパイル済みコードを定義する。
	 */
	private static void putLibrary(String name, int arity, int input, int[] instructions)
	{
		Map<Functor, int[]> target = null;
		switch (input)
		{
		case 0: target = guardLibrary0; break;
		case 1: target = guardLibrary1; break;
		case 2: target = guardLibrary2; break;
		default:
			throw new RuntimeException("Illegal parameter input = " + input);
		}
		if (target != null)
		{
			target.put(new SymbolFunctor(name, arity), instructions);
		}
	}

	private static int[] array(int ... args)
	{
		return args;
	}

	//
	private RuleCompiler rc;			// rc.rs用
	private List<Atom> typeConstraints;		// 型制約のリスト
	private Map<String, ContextDef>  typedProcessContexts;	// 型付きプロセス文脈名から定義へのマップ

	GuardCompiler(RuleCompiler rc, HeadCompiler hc)
	{
		super();
		this.rc = rc;
		this.initNormalizedCompiler(hc);
		match = rc.guard;
		typeConstraints      = rc.rs.guardMem.atoms;
		typedProcessContexts = rc.rs.typedProcessContexts;

		putLibrary("string", 1, 1, array(ISSTRING));
		//guardLibrary2.put(new SymbolFunctor("class",2), new int[]{0,      ISSTRING,Instruction.INSTANCEOF});
		//guardLibrary1.put(new SymbolFunctor("class", 2), new int[]{0,              Instruction.GETCLASS,  ISSTRING});
		putLibrary("connectRuntime", 1, 1, array(ISSTRING, Instruction.CONNECTRUNTIME));
	}

	/** initNormalizedCompiler呼び出し後に呼ばれる。
	 * 左辺関係型付き$pに対して、ガード用の仮引数番号を
	 * 変数番号として左辺関係型付き$pのマッチングを取り終わった内部状態を持つようにする。*/
	// private final void initNormalizedGuardCompiler(GuardCompiler gc) {
	// 	identifiedCxtdefs = (HashSet<ContextDef>)gc.identifiedCxtdefs.clone();
	// 	typedCxtDataTypes = (HashMap<ContextDef, Integer>)gc.typedCxtDataTypes.clone();
	// 	typedCxtDefs = (ArrayList<ContextDef>)((ArrayList<ContextDef>)gc.typedCxtDefs).clone();
	// 	typedCxtSrcs = (HashMap<ContextDef, Integer>)gc.typedCxtSrcs.clone();
	// 	typedCxtTypes = (HashMap<ContextDef, Object>)gc.typedCxtTypes.clone();
	// 	varCount = gc.varCount;	// 重複
	// }

	/** ガード否定条件のコンパイルで使うためにthisに対する正規化されたGuardCompilerを作成して返す。
	 * 正規化とは、左辺の全てのアトム/膜および左辺関係型付き$pに対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺と左辺関係型制約のマッチングを取り終わった内部状態を持つようにすることを意味する。*/
	// private final GuardCompiler getNormalizedGuardCompiler() {
	// 	GuardCompiler gc = new GuardCompiler(rc,this);
	// 	gc.initNormalizedGuardCompiler(this);
	// 	return gc;
	// }
	//

	/**
	 * プロセス文脈のない膜やstableな膜の検査を行う。
	 * RISC化に伴い、ヘッドコンパイラから移動してきた。 by mizuno
	 */
	void checkMembraneStatus() {
		// プロセス文脈がないときは、アトムと子膜の個数がマッチすることを確認する
		for (int i = 0; i < mems.size(); i++) {
			Membrane mem = (Membrane)mems.get(i);
			int mempath = memToPath(mem);
			if (mempath == 0) continue; //本膜に対しては何もしない
			if (mem.processContexts.isEmpty()) {
				countAtomsOfMembrane(mem);
				match.add(new Instruction(Instruction.NMEMS,  mempath, mem.mems.size()));
			}
			//
			if (mem.ruleContexts.isEmpty()) {
				match.add(new Instruction(Instruction.NORULES, mempath));
			}
			if (mem.stable) {
				match.add(new Instruction(Instruction.STABLE, mempath));
			}
		}
	}
	/** 引数に渡されたアトムのリンクに対してgetlinkを行い、変数番号を登録する。(RISC化)
	 * <strike>将来的にはリンクオブジェクトをガード命令列の引数に渡すようにするかも知れない。</strike>
	 * 将来的にはガード命令列はヘッド命令列にインライン展開される予定なので、
	 * このメソッドで生成されるgetlinkは冗長命令の除去により消せる見込み。*/
	void getLHSLinks() {
		for (int i = 0; i < atoms.size(); i++) {
			Atom atom = (Atom)atoms.get(i);
			int atompath = atomToPath(atom);
			int arity = atom.getArity();
			int[] paths = new int[arity];
			for (int j = 0; j < arity; j++) {
				paths[j] = varCount;
				if (!atom.args[j].name.startsWith("!")) { // hlground hypergetlink
					match.add(new Instruction(Instruction.GETLINK, varCount, atompath, j));					
				} else {
					match.add(new Instruction(Instruction.HYPERGETLINK, varCount, atompath, j));
				}
				varCount++;
			}
			linkPaths.put(atompath, paths);
		}
	}

	/**
	 * 型付きプロセス文脈が表すプロセスを一意に決定する。
	 * このメソッドは長い。
	 */
	void fixTypedProcesses() throws CompileException
	{
		// STEP 1 - 左辺に出現する型付きプロセス文脈を特定されたものとしてマークする。
		identifiedCxtdefs = new HashSet<>();
		for (ContextDef def : typedProcessContexts.values())
		{
			if (def.lhsOcc != null)
			{
				identifiedCxtdefs.add(def);
				// 左辺の型付きプロセス文脈の明示的な自由リンクの先が、左辺のアトムに出現することを確認する。
				// 出現しない場合はコンパイルエラーとする。この制限を「パッシブ型制限」と呼ぶことにする。
				// 【注意】パッシブ型制限は、型は非アクティブなデータを表すことを想定することにより正当化される。
				// つまり、( 2(X) :- found(X) ) や ( 2(3) :- ok ) で2や3を$pで表すことはできない。
				// しかし実際には処理系側の都合による制限である。
				// なお、プログラミングの観点から、右辺の型付きプロセス文脈の明示的な自由リンクの先は任意としている。
				//
				// ( 2006/09/13 kudo ) 2引数以上の型付きプロセス文脈の導入に伴い，全ての引数をチェックするようにする
				for (int i = 0; i < def.lhsOcc.args.length; i++)
				{
					if (!atomPaths.containsKey(def.lhsOcc.args[i].buddy.atom))
					{
						error("COMPILE ERROR: a partner atom is required for the head occurrence of typed process context: " + def.getName());
					}
				}
			}
			else if (def.lhsMem != null)
			{
				if (def.lhsMem.pragmaAtHost.def == def)
				{
					// 左辺の＠指定で定義される場合
					identifiedCxtdefs.add(def);
					int atomid = varCount++;
					match.add(new Instruction(Instruction.GETRUNTIME, atomid, memToPath(def.lhsMem)));
					typedCxtSrcs.put(def, atomid);
					typedCxtDefs.add(def);
					typedCxtTypes.put(def, UNARY_ATOM_TYPE);
					typedCxtDataTypes.put(def, ISSTRING);
				}
			}
		}

		// STEP 2 - 全ての型付きプロセス文脈が特定され、型が決定するまで繰り返す
		List<Atom> cstrs = new LinkedList<>(typeConstraints);

		{
			// uniq, not_uniq を最初に（少なくともint, unary などの前に）処理する
			List<Atom> tmpFirst = new LinkedList<>();
			List<Atom> tmpLast = new LinkedList<>();
			for (Iterator<Atom> it = cstrs.iterator(); it.hasNext();)
			{
				Atom a = it.next();
				if (a.functor.getName().endsWith("uniq") || a.functor.getName().equals("custom"))
				{
					tmpFirst.add(a);
					it.remove();
				}
				if (a.functor.getName().startsWith("custom") ||
					a.functor.getName().equals("new") ||
					a.functor.getName().equals("make"))
				{
					tmpLast.add(a);
					it.remove();
				}
			}
			tmpFirst.addAll(cstrs);
			tmpFirst.addAll(tmpLast);
			cstrs = tmpFirst;
		}

		boolean changed;
		do
		{
			changed = false;
			FixType:
				for (ListIterator<Atom> lit = cstrs.listIterator(); lit.hasNext();)
				{
					Atom cstr = lit.next();
					Functor func = cstr.functor;

					ContextDef def1 = null;
					ContextDef def2 = null;
					ContextDef def3 = null;
					if (func.getArity() > 0)  def1 = ((ProcessContext)cstr.args[0].buddy.atom).def;
					if (func.getArity() > 1)  def2 = ((ProcessContext)cstr.args[1].buddy.atom).def;
					if (func.getArity() > 2)  def3 = ((ProcessContext)cstr.args[2].buddy.atom).def;

					if (func.equals("unary", 1))
					{
						if (!identifiedCxtdefs.contains(def1)) continue;
						int atomid1 = loadUnaryAtom(def1);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
					}
					else if (func.equals("ground", 1))
					{
						if (!identifiedCxtdefs.contains(def1)) continue;
						checkGroundLink(def1);
					}
					else if (func.getName().equals("hlground"))
					{
						if (!identifiedCxtdefs.contains(def1)) continue;
						// hlgroundの属性取得
						Atom hlgroundAtom = cstr;
						int i = 0;
						Atom[] attrAtoms = new Atom[hlgroundAtom.args.length-1];
						for (LinkOccurrence link : hlgroundAtom.args) {
							if (i != 0) {
								Atomic linkedAtom = link.buddy.atom;
								ContextDef d = ((ProcessContext) linkedAtom).def;
								for (ProcessContext pc : linkedAtom.mem.typedProcessContexts) {
									if (pc.def == d && pc != linkedAtom) {
										if (pc.args.length != 0) {
											attrAtoms[i-1] = (Atom) pc.args[0].buddy.atom;	
										}
									}
								}
							}
							i++;
						}
						hlgroundAttrs.put(def1, attrAtoms);
						
						checkHLGroundLink(def1);
					}
					// ガードインライン
					else if (func.getName().startsWith("custom_"))
					{
						boolean hasError=false;
						if(func.getName().length()<7+func.getArity()+1) hasError=true;
						boolean[] isIn = new boolean[func.getArity()];
						if(func.getName().charAt(7+isIn.length)!='_') hasError=true;
						for (int i = 0; i < isIn.length; i++)
						{
							char ch = func.getName().charAt(7 + i);
							if (ch != 'i' && ch != 'o') hasError = true;
							isIn[i] = ch == 'i';
						}
						if (hasError)
						{
							String mo = "";
							for(int i=0;i<isIn.length;i++) mo += "?";
							error("Guard "+func.getName()+" should be custom_"+mo+"_xxxx. (? : 'i' when input, 'o' when output)");
						}

						String guardID = func.getName().substring(7+func.getArity()+1);
						List<Integer> vars = new ArrayList<>();
						List<Integer> out = new ArrayList<>(); // 出力引数
						for(int k=0;k<cstr.args.length;k++) {
							ContextDef defK = ((ProcessContext)cstr.args[k].buddy.atom).def;
							// 入力引数が未束縛なら延期
							if (isIn[k] && !identifiedCxtdefs.contains(defK)) {
								continue FixType;
							}
							int aid;
							if(identifiedCxtdefs.contains(defK)) {
								aid = typedcxtToSrcPath(defK);
								if(aid==UNBOUND) {
									checkGroundLink(defK);
									aid = groundToSrcPath(defK);
								}
//								aid = loadUnaryAtom(defK);
							} else {
								int atomid = varCount++;
								bindToUnaryAtom(defK, atomid);
								typedCxtDataTypes.put(def3, ISINT);
								aid = typedcxtToSrcPath(defK);
								out.add(aid);
							}
							vars.add(aid);
//							vars.add(loadUnaryAtom(def1));
//							System.out.println("varcount "+varcount);
//							System.out.println("1 "+typedcxtdatatypes);
//							System.out.println("1 "+typedcxtdefs);
//							System.out.println("1 "+typedcxtsrcs);
//							System.out.println("1 "+typedcxttypes);
						}
//						System.out.println("vars "+vars);
						match.add(new Instruction(Instruction.GUARD_INLINE, guardID, vars, out));
					}
					else if (func.getName().equals("uniq") || func.getName().equals("not_uniq"))
					{
						List<Integer> uniqVars = new ArrayList<>();
						for (int k = 0; k < cstr.args.length; k++)
						{
							ContextDef defK = ((ProcessContext)cstr.args[k].buddy.atom).def;
							if (!identifiedCxtdefs.contains(defK)) continue FixType; // 未割り当てのプロセス（表記に-が付くもの）は認めない
							int srcPath;
//							srcPath = typedcxtToSrcPath(defK);
//							Env.p("VAR# "+srcPath);
//							if(srcPath==UNBOUND) {
							checkGroundLink(defK);
							srcPath = groundToSrcPath(defK);
//							}
//							Env.p("VAR## "+srcPath);
							if(srcPath==UNBOUND) continue FixType;
							uniqVars.add(srcPath);
						}
						if(func.getName().equals("uniq"))
						{
							match.add(new Instruction(Instruction.UNIQ, uniqVars));
						}
						else
						{
							match.add(new Instruction(Instruction.NOT_UNIQ, uniqVars));
						}
						rc.theRule.hasUniq = true;
					}
					else if (func.equals("\\=", 2))
					{
						// NSAMEFUNC を作るか？
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;

						//groundの否定にした。(2006-02-18 by kudo)
						// .. :- unary(A),A\=B | ..
						//の場合、Bがgroundで構わない。Aがunaryで、かつBが異なる構造の時に反応する。
						//この点は、==とは違う。==の場合、そもそも同じ型でなければマッチしないため。
						//Bがunaryの時に限定したければ、unary(B)を書き加えればよい。
						//(何も考えずに実装したらそうなったのだが、結果的に一番柔軟で直感的な形だと思う。)
						if(!GROUND_ALLOWED ||
							typedCxtTypes.get(def1) == UNARY_ATOM_TYPE ||
							typedCxtTypes.get(def2) == UNARY_ATOM_TYPE)
						{
							int atomid1 = loadUnaryAtom(def1);
							int atomid2 = loadUnaryAtom(def2);
							if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
								connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
							match.add(new Instruction(Instruction.ISUNARY, atomid1));
							match.add(new Instruction(Instruction.ISUNARY, atomid2));
							int funcid1 = varCount++;
							int funcid2 = varCount++;
							match.add(new Instruction(Instruction.GETFUNC, funcid1, atomid1));
							match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
							match.add(new Instruction(Instruction.NEQFUNC, funcid1, funcid2));
						}
						else{
							checkGroundLink(def1);
							checkGroundLink(def2);
							if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
								connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
							int linkid1 = loadGroundLink(def1);
							int linkid2 = loadGroundLink(def2);
//							match.add(new Instruction(Instruction.ISGROUND,linkid1));
//							match.add(new Instruction(Instruction.ISGROUND,linkid2));
							match.add(new Instruction(Instruction.NEQGROUND,linkid1,linkid2));
						}
					}
					// (2006.07.06 n-kato)
					else if (func.equals("class", 2))
					{
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
						int atomid1 = loadUnaryAtom(def1);
						int atomid2 = loadUnaryAtom(def2);
						if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
							connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
						if (ISSTRING != typedCxtDataTypes.get(def2))
						{
							match.add(new Instruction(ISSTRING, atomid2));
							typedCxtDataTypes.put(def2, ISSTRING);
						}
						// todo: Instruction.INSTANCEOF
						int classnameAtomid = varCount++;
						match.add(new Instruction(Instruction.GETCLASS, classnameAtomid, atomid1));
						match.add(new Instruction(Instruction.SUBCLASS, classnameAtomid, atomid2));
					}
					else if (func.isInteger())
					{
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, ISINT);
					}
					else if (func.isNumber())
					{
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, ISFLOAT);
					}
					else if (func.isString())
					{
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, ISSTRING);
					}
//					else if (func instanceof runtime.ObjectFunctor
//					&& ((runtime.ObjectFunctor)func).getObject() instanceof String) {
//					bindToFunctor(def1, func);
//					typedcxtdatatypes.put(def1, ISSTRING);
//					}
					else if (cstr.isSelfEvaluated && func.getArity() == 1)
					{
						bindToFunctor(def1, func);
						// typedcxtdatatypes.put(def1, Instruction.ISUNARY);
					}
					else if (func.equals(Functor.UNIFY)) // (-X = +Y)
					{
						if (!identifiedCxtdefs.contains(def2)) { // (+X = -Y) は (-Y = +X) として処理する
							ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
							if (!identifiedCxtdefs.contains(def2)) continue;
						}
						// 未特定のdef1 = groundのdef2 は許されない
						if(GROUND_ALLOWED && typedCxtTypes.get(def2) != UNARY_ATOM_TYPE){
							if (!identifiedCxtdefs.contains(def1)) continue;
						}
						processEquivalenceConstraint(def1,def2);
					}
//					else if (func.equals(new SymbolFunctor("==",2))) { // (+X == +Y)
//						if (!identifiedCxtdefs.contains(def1)) continue;
//						if (!identifiedCxtdefs.contains(def2)) continue;
//						processEquivalenceConstraint(def1,def2);
//					}
					else if (func.equals("==", 2)) // (+X == +Y) //seiji
					{
						/* unary用比較演算子 (10/07/07 seiji) */
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
						int atomid1 = loadUnaryAtom(def1);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
						int atomid2 = loadUnaryAtom(def2);
						match.add(new Instruction(Instruction.ISUNARY, atomid2));
						match.add(new Instruction(Instruction.SAMEFUNC, atomid1, atomid2));
					}
					else if (func.equals("\\==", 2)) // (+X \== +Y) //seiji
					{
						/* unary用比較演算子 (11/01/25 seiji) */
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
						int atomid1 = loadUnaryAtom(def1);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
						int atomid2 = loadUnaryAtom(def2);
						match.add(new Instruction(Instruction.ISUNARY, atomid2));
						int funcid1 = varCount++;
						int funcid2 = varCount++;
						match.add(new Instruction(Instruction.GETFUNC, funcid1, atomid1));
						match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
						match.add(new Instruction(Instruction.NEQFUNC, funcid1, funcid2));
					}
//					else if (func.equals(new SymbolFunctor("===",2))) { // (+X === +Y) //seiji
//						/* hlink用比較演算子 (10/07/07 seiji) */
//						if (!identifiedCxtdefs.contains(def1)) continue;
//						if (!identifiedCxtdefs.contains(def2)) continue;
//						int atomid1 = loadUnaryAtom(def1);
//						match.add(new Instruction(Instruction.ISHLINK, atomid1));
//						int atomid2 = loadUnaryAtom(def2);
//						match.add(new Instruction(Instruction.ISHLINK, atomid2));
//						match.add(new Instruction(Instruction.SAMEFUNC, atomid1, atomid2));
//					}
					else if (func.getName().equals("new") && func.getArity() >= 2) // newhlinkwithattr
					{
						// newの2番目以降についてアトムを取得しnewAtomArgAtomsに格納する
						Atom newAtom = cstr;
						int i = 0;
						Atom[] newArgAtoms = new Atom[newAtom.args.length];
						for (LinkOccurrence link : newAtom.args) {
							if (i != 0) {
								Atomic linkedAtom = link.buddy.atom;
								ContextDef d = ((ProcessContext) linkedAtom).def;
								for (ProcessContext pc : linkedAtom.mem.typedProcessContexts) {
									if (pc.def == d && pc != linkedAtom) {
										if (pc.args.length != 0) {
											newArgAtoms[i] = (Atom) pc.args[0].buddy.atom;	
										}
									}
								}
							}
							i++;
						}
						newAtomArgAtoms.put(newAtom, newArgAtoms);
						List<Functor> attrs = getHlgroundAttrs(newArgAtoms);

						int atomid = varCount++;
						match.add(new Instruction(Instruction.NEWHLINKWITHATTR, atomid, attrs.get(0)));
						bindToUnaryAtom(def1, atomid);
						typedCxtDataTypes.put(def1, Instruction.ISINT);
						if (identifiedCxtdefs.contains(def1))
						{
							int funcid2 = varCount++;
							match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid));
							int atomid1 = varCount++;
							match.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
							typedCxtSrcs.put(def1, atomid1);
							typedCxtDefs.add(def1);
							identifiedCxtdefs.add(def1);
							typedCxtTypes.put(def1, UNARY_ATOM_TYPE);
						}
					}
					else if (func.getName().equals("hlink") && func.getArity() >= 2) // getattratom
					{
						int[] desc = array(ISHLINK);
						if (!identifiedCxtdefs.contains(def1)) continue;
						int atomid1 = loadUnaryAtom(def1);
						int dstatomid = varCount++;
						if (desc[0] != 0 && (!typedCxtDataTypes.containsKey(def1) || desc[0] != typedCxtDataTypes.get(def1)))
						{
							match.add(new Instruction(desc[0], atomid1));
							typedCxtDataTypes.put(def1, desc[0]);
						}
						match.add(new Instruction(Instruction.GETATTRATOM, dstatomid, atomid1));
						int atomid2 = loadUnaryAtom(def2);
						match.add(new Instruction(Instruction.SAMEFUNC, dstatomid, atomid2));
					}
					else if (guardLibrary0.containsKey(func)) // 0入力制約//seiji
					{
						int[] desc = guardLibrary0.get(func);
						int atomid = varCount++;
						match.add(new Instruction(desc[0], atomid));
						bindToUnaryAtom(def1, atomid);
						typedCxtDataTypes.put(def1, desc[1]);
						if (identifiedCxtdefs.contains(def1))
						{
							int funcid2 = varCount++;
							match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid));
							int atomid1 = varCount++;
							match.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
							typedCxtSrcs.put(def1, atomid1);
							typedCxtDefs.add(def1);
							identifiedCxtdefs.add(def1);
							typedCxtTypes.put(def1, UNARY_ATOM_TYPE);
						}
					}
					else if (guardLibrary1.containsKey(func)) // 1入力制約
					{
						int[] desc = guardLibrary1.get(func);
						if (!identifiedCxtdefs.contains(def1)) continue;
						int atomid1 = loadUnaryAtom(def1);
						if (desc[0] != 0 && (!typedCxtDataTypes.containsKey(def1) || desc[0] != typedCxtDataTypes.get(def1)))
						{
							match.add(new Instruction(desc[0], atomid1));
							typedCxtDataTypes.put(def1, desc[0]);
						}

						if (func.getArity() == 1) // {t1,inst} --> p(+X1)
						{
							// // 060831okabe
							// // 以下をコメントアウト．
							// // つまりconnectruntime はput してget されるだけ．
							// // TODO よってconnectruntime はいらないので何とかする．（ライブラリを使った分散を作るときまで放置でよい）
							// hyperlinkのためにコメントアウト解除 (2010/07/07 seiji)
							if (desc.length > 1) match.add(new Instruction(desc[1], atomid1));
						}
						else // {t1,inst,t2} --> p(+X1,-X2)
						{
							int atomid2;
							if (desc[1] == -1) // 単項 + と +. だけ特別扱い
							{
								atomid2 = atomid1;
								//bindToUnaryAtom 内で、実際に使うアトムを生成している。
							}
							else
							{
//								if (func.equals(new SymbolFunctor("getconame", 2))) // getconame制約のための処理
//									match.add(new Instruction(Instruction.HASCONAME, atomid1));
								atomid2 = varCount++;
								match.add(new Instruction(desc[1], atomid2, atomid1));
							}
							// 2006.07.06 n-kato //2006.07.01 by inui
							// if (func.equals(classFunctor)) bindToUnaryAtom(def2, atomid2, Instruction.SUBCLASS);
							// else
							bindToUnaryAtom(def2, atomid2);
							typedCxtDataTypes.put(def2, desc[2]);
						}
					}
					else if (guardLibrary2.containsKey(func)) // 2入力制約
					{
						int[] desc = guardLibrary2.get(func);
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;

						//Util.println("st");
						int atomid1 = loadUnaryAtom(def1);
						int atomid2 = loadUnaryAtom(def2);
						if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
							connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
						//Util.println("end");

						Integer t1 = typedCxtDataTypes.get(def1);
						if (desc[0] != 0 && (t1 == null || desc[0] != t1))
						{
							match.add(new Instruction(desc[0], atomid1));
							typedCxtDataTypes.put(def1, desc[0]);
						}

						Integer t2 = typedCxtDataTypes.get(def2);
						if (desc[1] != 0 && (t2 == null || desc[1] != t2))
						{
							match.add(new Instruction(desc[1], atomid2));
							typedCxtDataTypes.put(def2, desc[1]);
						}

						if (func.getArity() == 2) // {t1,t2,inst} --> p(+X1,+X2)
						{
							match.add(new Instruction(desc[2], atomid1, atomid2));
						}
						else // desc={t1,t2,inst,t3} --> p(+X1,+X2,-X3)
						{
							int atomid3 = varCount++;
							match.add(new Instruction(desc[2], atomid3, atomid1, atomid2));
							bindToUnaryAtom(def3, atomid3);
							typedCxtDataTypes.put(def3, desc[3]);
						}
					}
//					else if (func.getArity() == 1 && func.isSymbol()) {
//						// new, hlink
//						// bindToFunctor(def1, func);
//					}
					else
					{
						error("COMPILE ERROR: unrecognized type constraint: " + cstr);
						discardTypeConstraint(cstr); // ここには来ない
					}
					lit.remove();
					changed = true;
				}
			if (cstrs.isEmpty()) return;
		}
		while (changed);

		// STEP 3 - 型付け失敗
		String text = "";
		for (Atom cstr : cstrs)
		{
			discardTypeConstraint(cstr);
			if (text.length() > 0)  text += ", ";
			text += cstr.toStringAsTypeConstraint();
		}
		error("COMPILE ERROR: never proceeding type constraint: " + text);
	}
	
	private boolean GROUND_ALLOWED = true;
	/** 制約 X=Y または X==Y を処理する。ただしdef2は特定されていなければならない。*/
	private void processEquivalenceConstraint(ContextDef def1, ContextDef def2) throws CompileException{
		boolean checkNeeded = (typedCxtTypes.get(def1) == null
				&& typedCxtTypes.get(def2) == null); // 型付きであることの検査が必要かどうか
		//boolean GROUND_ALLOWED = true;
		// GROUND_ALLOWED のとき (unary = ?) は (? = unary) として処理する（ただし?はgroundまたはnull）
		if (GROUND_ALLOWED && typedCxtTypes.get(def2) != UNARY_ATOM_TYPE) {
			if (typedCxtTypes.get(def1) == UNARY_ATOM_TYPE) {
				ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
			}
		}
		if (GROUND_ALLOWED && typedCxtTypes.get(def2) != UNARY_ATOM_TYPE) { // (? = ground)
			//if(checkNeeded){
			checkGroundLink(def1);
			checkGroundLink(def2);
			//}
			int linkid1 = loadGroundLink(def1);
			int linkid2 = loadGroundLink(def2);

			/** groundについては、(未特定の$p1)=(特定済の$p2)という形は許されないものとする。fix..ではじく */
			match.add(new Instruction(Instruction.EQGROUND,linkid1,linkid2));
		}
		else {
			int atomid2 = loadUnaryAtom(def2);
			if (checkNeeded) match.add(new Instruction(Instruction.ISUNARY, atomid2));
			if (!identifiedCxtdefs.contains(def1)) { // (未特定の$p1)=(特定済の$p2) 
				// todo 同じ変数を共有した方がよい。できるか？
				int funcid2 = varCount++;
				match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
				int atomid1 = varCount++;
				match.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
				typedCxtSrcs.put(def1, atomid1);
				typedCxtDefs.add(def1);
				identifiedCxtdefs.add(def1);
				typedCxtTypes.put(def1, UNARY_ATOM_TYPE);
			}
			else bindToUnaryAtom(def1, atomid2);
			Integer newdatatype = typedCxtDataTypes.get(def2);
			if (newdatatype == null) newdatatype = typedCxtDataTypes.get(def1);
			typedCxtDataTypes.put(def1,newdatatype);
			typedCxtDataTypes.put(def2,newdatatype);
		}
		if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
			connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
	}

	/** 型制約を廃棄する。エラー復帰用メソッド */
	private void discardTypeConstraint(Atom cstr) throws CompileException{
		match.add(Instruction.fail());
		for (int i = 0; i < cstr.functor.getArity(); i++) {
			ContextDef def = ((ProcessContext)cstr.args[i].buddy.atom).def;
			bindToFunctor(def,new SymbolFunctor("*",1));
		}
	}
	/** 型付きプロセス文脈defを1引数ファンクタfuncで束縛する */
	private void bindToFunctor(ContextDef def, Functor func) throws CompileException
	{
		if (!identifiedCxtdefs.contains(def))
		{
			identifiedCxtdefs.add(def);
			int atomid = varCount++;
			typedCxtSrcs.put(def, atomid);
			typedCxtDefs.add(def);
			match.add(new Instruction(Instruction.ALLOCATOM, atomid, func));
		}
		else
		{
			checkUnaryProcessContext(def);
			int atomid = typedcxtToSrcPath(def);
			if (atomid == UNBOUND)
			{
				LinkOccurrence srclink = def.lhsOcc.args[0].buddy; // defのソース出現を指すアトム側の引数
				atomid = varCount++;
				match.add(new Instruction(Instruction.DEREFATOM,
						atomid, atomToPath(srclink.atom), srclink.pos));
				typedCxtSrcs.put(def, atomid);
				typedCxtDefs.add(def);
				match.add(new Instruction(Instruction.FUNC, atomid, func));
				getLinks(atomid, 1, match);
			}
			else
			{
				match.add(new Instruction(Instruction.FUNC, atomid, func));
			}
		}
		typedCxtTypes.put(def, UNARY_ATOM_TYPE);
	}

	/** 型付きプロセス文脈defを1引数アトム$atomidのファンクタで束縛する */
	private void bindToUnaryAtom(ContextDef def, int atomid) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			typedCxtSrcs.put(def, atomid);
			typedCxtDefs.add(def);
		}
		else {
			int loadedatomid = typedcxtToSrcPath(def);
			if (loadedatomid == UNBOUND) {
				LinkOccurrence srclink = def.lhsOcc.args[0].buddy;
				loadedatomid = varCount++;
//				Util.println("bindToUnaryAtom " + srclink.atom);
				match.add(new Instruction(Instruction.DEREFATOM,
						loadedatomid, atomToPath(srclink.atom), srclink.pos));
				typedCxtSrcs.put(def, loadedatomid);
				typedCxtDefs.add(def);
				match.add(new Instruction(Instruction.SAMEFUNC, atomid, loadedatomid));
				getLinks(loadedatomid, 1, match);
			} else {
				match.add(new Instruction(Instruction.SAMEFUNC, atomid, loadedatomid));
			}
//			int funcid1 = varcount++;
//			int funcid2 = varcount++;
//			match.add(new Instruction(Instruction.GETFUNC, funcid1, atomid));
//			match.add(new Instruction(Instruction.GETFUNC, funcid2, loadedatomid));
//			match.add(new Instruction(Instruction.EQFUNC,  funcid1, funcid2));
		}
		typedCxtTypes.put(def, UNARY_ATOM_TYPE);
	}
	/** 型付きプロセス文脈defの（特定されている）ソース出現の
	 * （明示的な自由リンクが出現する）アトムを取得する。
	 * また、このアトムが1引数であると仮定して、型情報を更新する。
	 * @return 取得したアトムの変数番号 */
	private int loadUnaryAtom(ContextDef def) throws CompileException{
		int atomid = typedcxtToSrcPath(def);
		if (atomid == UNBOUND) {
			checkUnaryProcessContext(def);
			LinkOccurrence srclink = def.lhsOcc.args[0].buddy;
			atomid = varCount++;
			match.add(new Instruction(Instruction.DEREFATOM,
					atomid, atomToPath(srclink.atom), srclink.pos));
			typedCxtSrcs.put(def, atomid);
			typedCxtDefs.add(def);
			getLinks(atomid, 1, match);
		}
		typedCxtTypes.put(def, UNARY_ATOM_TYPE);
		return atomid;
	}	

	/** 型付プロセス文脈defの（特定されている）ソース出現のリンクを取得する。
	 *  それらをリスト変数に格納する．
	 *  その変数番号をgroundsrcsに追加する。
	 *  型情報を更新する。
	 *  @param def プロセス文脈定義
	 *  @return リンクリストの変数番号 */
	private int loadGroundLink(ContextDef def) {
//		ArrayList linkids = groundToSrcPath(def);
		int linkids = groundToSrcPath(def);
		if( linkids == UNBOUND ){
			linkids = varCount++;
			match.add(new Instruction(Instruction.NEWLIST,linkids));
			for(int i=0;i<def.lhsOcc.args.length;i++){
//				Util.println("loadGroundLink "+def.lhsOcc.args[i].buddy.atom);
				int[] paths = (int[])linkPaths.get(atomToPath(def.lhsOcc.args[i].buddy.atom));
				//linkids[i] = paths[def.lhsOcc.args[i].buddy.pos];
//				linkids.set(i, paths[def.lhsOcc.args[i].buddy.pos]);
//				groundsrcs.put(def, linkids);
				match.add(new Instruction(Instruction.ADDTOLIST,linkids, paths[def.lhsOcc.args[i].buddy.pos]));
			}
			groundSrcs.put(def, linkids);
		}
		return linkids;
	}

	//左辺の膜(Membrane) -> その膜のアトムが入ったsetを指す変数番号(Integer)
//	HashMap memToAtomSetPath = new HashMap();

	//左辺の膜(Membrane) -> その膜のアトムの明示的な自由リンクが入ったlistを指す変数番号(Integer)
//	HashMap memToLinkListPath = new HashMap();

	/** 型付プロセス文脈defが、基底項プロセスかどうか検査する。
	 *  @param def プロセス文脈定義 */
	private void checkGroundLink(ContextDef def) {
		if(typedCxtTypes.get(def) != UNARY_ATOM_TYPE && typedCxtTypes.get(def) != GROUND_LINK_TYPE && typedCxtTypes.get(def) != HLGROUND_LINK_TYPE){
			typedCxtTypes.put(def,GROUND_LINK_TYPE);
//			int linkid = loadGroundLink(def);
//			ArrayList linkids = loadGroundLink(def);
			int linkids = loadGroundLink(def);
			int srclinklistpath;
//			if(!memToLinkListPath.containsKey(def.lhsOcc.mem)){
			srclinklistpath = varCount++;
			// 避けるリンクのリスト
			match.add(new Instruction(Instruction.NEWLIST,srclinklistpath));

			// 左辺出現アトムの，全ての引数(を指すリンク)のうち,
			// 左辺の自由リンクもしくは同じ膜のプロセス文脈に接続していて
			// このプロセス文脈の根でないものをリストに追加する
			for(Atom atom : def.lhsOcc.mem.atoms){
//				Util.println("checkGroundLink"+atom);
				int[] paths = (int[])linkPaths.get(atomToPath(atom));
				for(int i=0;i<atom.args.length;i++){
//					match.add(new Instruction(Instruction.ADDATOMTOSET,srcsetpath,atomToPath((Atom)it.next())));
					if(def.lhsOcc.mem.parent == null){ // 左辺出現がルール最外部
						if( atom.args[i].buddy.atom.mem!=rc.rs.rightMem)
							// 反対側が右辺出現の時のみ追加
							if(!def.lhsOcc.mem.typedProcessContexts.contains(atom.args[i].buddy.atom))
								continue;
					}else{ // 左辺出現が膜内
						if(!def.lhsOcc.mem.processContexts.contains(atom.args[i].buddy.atom))  // 反対側がプロセス文脈の引数の時のみ追加
							if(!def.lhsOcc.mem.typedProcessContexts.contains(atom.args[i].buddy.atom))
								continue;
					}
					boolean flgNotAdd = false; // その引数を避けるべきリストに「加えない」場合true
					for(int j=0;j<def.lhsOcc.args.length;j++){
						LinkOccurrence ro = def.lhsOcc.args[j].buddy;
						if(ro == atom.args[i])
							flgNotAdd = true;
					}
					if(!flgNotAdd){
						match.add(new Instruction(Instruction.ADDTOLIST,srclinklistpath,paths[i]));
						if(Env.findatom2 && def.lhsOcc!=null)
							connectAtoms(def.lhsOcc.args[0].buddy.atom, atom.args[i].atom);
					}
				}
			}
//			memToLinkListPath.put(def.lhsOcc.mem, srclinklistpath);
//			}
//			else srclinklistpath = ((Integer)memToLinkListPath.get(def.lhsOcc.mem)).intValue();
			int natom = varCount++;
			match.add(new Instruction(Instruction.ISGROUND, natom, linkids, srclinklistpath));//,memToPath(def.lhsOcc.mem)));
			rc.hasISGROUND = false;
			if(!memToGroundSizes.containsKey(def.lhsOcc.mem))memToGroundSizes.put(def.lhsOcc.mem, new HashMap<>());
			memToGroundSizes.get(def.lhsOcc.mem).put(def, natom);
			
		}
		return;
	}
	
	/** 型付プロセス文脈defが、基底項プロセスかどうか検査する。
	 *  @param def プロセス文脈定義 */
	// hlground型
	private void checkHLGroundLink(ContextDef def) {
		if(typedCxtTypes.get(def) != UNARY_ATOM_TYPE && typedCxtTypes.get(def) != GROUND_LINK_TYPE && typedCxtTypes.get(def) != HLGROUND_LINK_TYPE){
			typedCxtTypes.put(def,HLGROUND_LINK_TYPE);
//			int linkid = loadGroundLink(def);
//			ArrayList linkids = loadGroundLink(def);
			int linkids = loadGroundLink(def);
			int srclinklistpath;
//			if(!memToLinkListPath.containsKey(def.lhsOcc.mem)){
			srclinklistpath = varCount++;
			// 避けるリンクのリスト
			match.add(new Instruction(Instruction.NEWLIST,srclinklistpath));

			// 左辺出現アトムの，全ての引数(を指すリンク)のうち,
			// 左辺の自由リンクもしくは同じ膜のプロセス文脈に接続していて
			// このプロセス文脈の根でないものをリストに追加する
			for(Atom atom : def.lhsOcc.mem.atoms){
//				Util.println("checkGroundLink"+atom);
				int[] paths = (int[])linkPaths.get(atomToPath(atom));
				for(int i=0;i<atom.args.length;i++){
//					match.add(new Instruction(Instruction.ADDATOMTOSET,srcsetpath,atomToPath((Atom)it.next())));
					if(def.lhsOcc.mem.parent == null){ // 左辺出現がルール最外部
						if( atom.args[i].buddy.atom.mem!=rc.rs.rightMem)
							// 反対側が右辺出現の時のみ追加
							if(!def.lhsOcc.mem.typedProcessContexts.contains(atom.args[i].buddy.atom))
								continue;
					}else{ // 左辺出現が膜内
						if(!def.lhsOcc.mem.processContexts.contains(atom.args[i].buddy.atom))  // 反対側がプロセス文脈の引数の時のみ追加
							if(!def.lhsOcc.mem.typedProcessContexts.contains(atom.args[i].buddy.atom))
								continue;
					}
					boolean flgNotAdd = false; // その引数を避けるべきリストに「加えない」場合true
					for(int j=0;j<def.lhsOcc.args.length;j++){
						LinkOccurrence ro = def.lhsOcc.args[j].buddy;
						if(ro == atom.args[i])
							flgNotAdd = true;
					}
					if(!flgNotAdd){
						match.add(new Instruction(Instruction.ADDTOLIST,srclinklistpath,paths[i]));
						if(Env.findatom2 && def.lhsOcc!=null)
							connectAtoms(def.lhsOcc.args[0].buddy.atom, atom.args[i].atom);
					}
				}
			}
//			memToLinkListPath.put(def.lhsOcc.mem, srclinklistpath);
//			}
//			else srclinklistpath = ((Integer)memToLinkListPath.get(def.lhsOcc.mem)).intValue();
			Atom[] atoms = hlgroundAttrs.get(def); // hlgroundの属性
			List<Functor> attrs = getHlgroundAttrs(atoms);
			int natom = varCount++;
			match.add(new Instruction(Instruction.ISHLGROUND, natom, linkids, srclinklistpath, attrs));//,memToPath(def.lhsOcc.mem)));
			rc.hasISGROUND = false;
			if(!memToGroundSizes.containsKey(def.lhsOcc.mem))memToGroundSizes.put(def.lhsOcc.mem, new HashMap<>());
			memToGroundSizes.get(def.lhsOcc.mem).put(def, natom);
			
		}
		return;
	}
	
	/**
	 * unary型に制約されたプロセス文脈が左辺に出現し、1引数であることを確認する．
	 * @param def
	 * @throws CompileException
	 */
	private void checkUnaryProcessContext(ContextDef def) throws CompileException{
		if(def.lhsOcc == null)
			error("COMPILE ERROR: unary type process context must occur in LHS");
		else if(def.lhsOcc.args.length!=1)	
			error("COMPILE ERROR: unary type process context must have exactly one argument : " + def.lhsOcc);
	}


	/**
	 * 膜内のアトム数を数える。$pが無いことが条件。
	 * ground,unaryについてもきちんと考える。
	 * 
	 * @param mem 数をチェックする膜
	 */
	private void countAtomsOfMembrane(Membrane mem){
		if(!memToGroundSizes.containsKey(mem)){ // 厳しくRISC化するなら、natomsも分けるべきか
			match.add(new Instruction(Instruction.NATOMS, memToPath(mem),
					mem.getNormalAtomCount() + mem.typedProcessContexts.size() ));
		}else{
			Map<ContextDef, Integer> gmap = memToGroundSizes.get(mem);
			//普通のアトムの個数と、unaryの個数
			int ausize = mem.getNormalAtomCount() + mem.typedProcessContexts.size() - gmap.size();
			int ausfunc = varCount++;
			match.add(new Instruction(Instruction.LOADFUNC,ausfunc,new runtime.functor.IntegerFunctor(ausize)));
			//各groundについて、isground命令で貰ってきたground構成アトム数を足していく
			int allfunc = ausfunc;	
			for(ContextDef def : gmap.keySet()){
				int natomfp = gmap.get(def);
				int newfunc = varCount++;
				match.add(new Instruction(Instruction.IADDFUNC,newfunc,allfunc,natomfp));
				allfunc = newfunc;
			}
			match.add(new Instruction(Instruction.NATOMSINDIRECT,memToPath(mem),allfunc));
		}
	}


	////////////////////////////////////////////////////////////////

	/** HeadCompiler.getAtomActualsのオーバーライド。
	 * GuardCompilerは現状では、atomsに対応する変数番号のリストの後に、
	 * typedcxtdefsのうちUNARY_ATOM_TYPEであるようなものの変数番号のリストをつなげたArrayListを返す。*/
	List<Integer> getAtomActuals() {
		List<Integer> args = new ArrayList<>();
		for (int i = 0; i < atoms.size(); i++) {
			args.add( atomPaths.get(atoms.get(i)) );
		}
		for(ContextDef def : typedCxtDefs){
			if (typedCxtTypes.get(def) == UNARY_ATOM_TYPE)
				args.add( typedcxtToSrcPath(def) );
		}
		return args;
	}

	//

	void error(String text) throws CompileException {
		Env.error(text);
		throw new CompileException("COMPILE ERROR");
	}

	private void connectAtoms(Atomic a1, Atomic a2){
		Membrane m1, m2;
		m1 = a1.mem;
		m2 = a2.mem;
		if(m1==m2)
			m1.connect(a1, a2);
		else {
			Membrane p1, p2, c1, c2;
			p2 = m2.parent;
			c2 = m2;
			while(p2 !=null){
				if(m1==p2){
					m1.connect(a1, c2);
					return ;
				}
				c2 = p2;
				p2 = c2.parent;
			}

			p1 = m1.parent;
			c1 = m1;
			while(p1 !=null){
				if(p1==m2){
					m2.connect(c1, a2);
					return ;
				}
				c1 = p1;
				p1 = c1.parent;
			}

			p1 = m1.parent;
			c1 = m1;
			while(p1 !=null){
				p2 = m2.parent;
				c2 = m2;
				while(p2 !=null){
					if(p1==p2){
						p1.connect(c1, c2);
						return ;
					}
					c2 = p2;
					p2 = c2.parent;
				}
				c1 = p1;
				p1 = c1.parent;
			}
		}
	}

}

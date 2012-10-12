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
import runtime.Functor;
import runtime.Instruction;
import runtime.SymbolFunctor;

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
//	static final Object LINEAR_ATOM_TYPE = "L"; // 任意のプロセス $p[X|*V]

	/** 型付きプロセス文脈定義 (ContextDef) -> データ型の種類を表すラップされた型検査命令番号(Integer) */
	HashMap<ContextDef, Integer> typedCxtDataTypes = new HashMap<ContextDef, Integer>();
	/** 型付きプロセス文脈定義 (ContextDef) -> データ型のパターンを表す定数オブジェクト */
	HashMap<ContextDef, Object> typedCxtTypes = new HashMap<ContextDef, Object>();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号 */
	HashMap<ContextDef, Integer> typedCxtSrcs  = new HashMap<ContextDef, Integer>();
	/** ground型付きプロセス文脈定義(ContextDef) -> リンクのソース出現（コピー元とする出現）のリストの変数番号 */
	HashMap<ContextDef, Integer> groundSrcs = new HashMap<ContextDef, Integer>();
	/** 膜(Membrane) -> (その膜に存在するground型付きプロセス文脈定義(ContextDef) -> 構成アトム数)というマップ */
	HashMap<Membrane, HashMap<ContextDef, Integer>> memToGroundSizes = new HashMap<Membrane, HashMap<ContextDef, Integer>>();
	/** ソース出現が特定された型付きプロセス文脈定義のセット
	 * <p>identifiedCxtdefs.contains(x) は、左辺に出現するかまたはloadedであることを表す。*/
	HashSet<ContextDef> identifiedCxtdefs = new HashSet<ContextDef>(); 
	/** 型付きプロセス文脈定義のリスト（仮引数IDの管理に使用する）
	 * <p>実際にはtypedcxtsrcsのキーを追加された順番に並べたもの。*/
	List<ContextDef> typedCxtDefs = new ArrayList<ContextDef>();

	private int typedcxtToSrcPath(ContextDef def) {
		if (!typedCxtSrcs.containsKey(def)) return UNBOUND;
		return typedCxtSrcs.get(def);
	}

	private int groundToSrcPath(ContextDef def) {
		if (!groundSrcs.containsKey(def)) return UNBOUND;
		return groundSrcs.get(def).intValue();
	}

	private static final int ISINT    = Instruction.ISINT;		// 型制約の引数が整数型であることを表す
	private static final int ISFLOAT  = Instruction.ISFLOAT;		// 〃 浮動小数点数型
	private static final int ISSTRING = Instruction.ISSTRING;	// 〃 文字列型
	private static final int ISMEM    = Instruction.ANYMEM;		// 〃 膜（getRuntime専用）
//	private static final int ISNAME    = Instruction.ISNAME;   	// 〃 name型 (SLIM専用) //seiji
//	private static final int ISCONAME  = Instruction.ISCONAME; 	// 〃 coname型 (SLIM専用) //seiji
	private static final int ISHLINK   = Instruction.ISHLINK; 	// 〃 hlink型 (SLIM専用) //seiji
	private static HashMap<Functor, int[]> guardLibrary0 = new HashMap<Functor, int[]>(); // 0入力ガード型制約名//seiji
	private static HashMap<Functor, int[]> guardLibrary1 = new HashMap<Functor, int[]>(); // 1入力ガード型制約名
	private static HashMap<Functor, int[]> guardLibrary2 = new HashMap<Functor, int[]>(); // 2入力ガード型制約名
	static {
		guardLibrary2.put(new SymbolFunctor("<.",   2), new int[]{ISFLOAT,ISFLOAT, Instruction.FLT});
		guardLibrary2.put(new SymbolFunctor("=<.",  2), new int[]{ISFLOAT,ISFLOAT, Instruction.FLE});
		guardLibrary2.put(new SymbolFunctor(">.",   2), new int[]{ISFLOAT,ISFLOAT, Instruction.FGT});
		guardLibrary2.put(new SymbolFunctor(">=.",  2), new int[]{ISFLOAT,ISFLOAT, Instruction.FGE});
		guardLibrary2.put(new SymbolFunctor("<",    2), new int[]{ISINT,  ISINT,   Instruction.ILT});
		guardLibrary2.put(new SymbolFunctor("=<",   2), new int[]{ISINT,  ISINT,   Instruction.ILE});
		guardLibrary2.put(new SymbolFunctor(">",    2), new int[]{ISINT,  ISINT,   Instruction.IGT});
		guardLibrary2.put(new SymbolFunctor(">=",   2), new int[]{ISINT,  ISINT,   Instruction.IGE});
		guardLibrary2.put(new SymbolFunctor("=:=",  2), new int[]{ISINT,  ISINT,   Instruction.IEQ});
		guardLibrary2.put(new SymbolFunctor("=\\=", 2), new int[]{ISINT,  ISINT,   Instruction.INE});
		guardLibrary2.put(new SymbolFunctor("=:=.", 2), new int[]{ISFLOAT,ISFLOAT, Instruction.FEQ});
		guardLibrary2.put(new SymbolFunctor("=\\=.",2), new int[]{ISFLOAT,ISFLOAT, Instruction.FNE});
		guardLibrary2.put(new SymbolFunctor("+.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FADD, ISFLOAT});
		guardLibrary2.put(new SymbolFunctor("-.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FSUB, ISFLOAT});
		guardLibrary2.put(new SymbolFunctor("*.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FMUL, ISFLOAT});
		guardLibrary2.put(new SymbolFunctor("/.",   3), new int[]{ISFLOAT,ISFLOAT, Instruction.FDIV, ISFLOAT});
		guardLibrary2.put(new SymbolFunctor("+",    3), new int[]{ISINT,  ISINT,   Instruction.IADD, ISINT});
		guardLibrary2.put(new SymbolFunctor("-",    3), new int[]{ISINT,  ISINT,   Instruction.ISUB, ISINT});
		guardLibrary2.put(new SymbolFunctor("*",    3), new int[]{ISINT,  ISINT,   Instruction.IMUL, ISINT});
		guardLibrary2.put(new SymbolFunctor("/",    3), new int[]{ISINT,  ISINT,   Instruction.IDIV, ISINT});
		guardLibrary2.put(new SymbolFunctor("mod",  3), new int[]{ISINT,  ISINT,   Instruction.IMOD, ISINT});	
		guardLibrary1.put(new SymbolFunctor("int",   1), new int[]{ISINT});
		guardLibrary1.put(new SymbolFunctor("float", 1), new int[]{ISFLOAT});
		guardLibrary1.put(new SymbolFunctor("+",     2), new int[]{ISINT,          -1,                    ISINT});
		guardLibrary1.put(new SymbolFunctor("-",     2), new int[]{ISINT,          Instruction.INEG,      ISINT});
		guardLibrary1.put(new SymbolFunctor("+.",    2), new int[]{ISFLOAT,        -1,                    ISFLOAT});
		guardLibrary1.put(new SymbolFunctor("-.",    2), new int[]{ISFLOAT,        Instruction.FNEG,      ISFLOAT});
		guardLibrary1.put(new SymbolFunctor("float", 2), new int[]{ISINT,          Instruction.INT2FLOAT, ISFLOAT});
		guardLibrary1.put(new SymbolFunctor("int",   2), new int[]{ISFLOAT,        Instruction.FLOAT2INT, ISINT});
        if (Env.slimcode && Env.hyperLink) {
            guardLibrary0.put(new SymbolFunctor("new", 1), new int[]{Instruction.NEWHLINK, ISINT});
            guardLibrary1.put(new SymbolFunctor("make", 2), new int[]{ISINT, Instruction.MAKEHLINK, ISINT});
            guardLibrary1.put(new SymbolFunctor("hlink", 1), new int[]{ISHLINK});
            guardLibrary1.put(new SymbolFunctor("num", 2), new int[]{ISHLINK, Instruction.GETNUM, ISINT});
//			guardLibrary1.put(new SymbolFunctor("name",   1), new int[]{ISNAME});
//			guardLibrary1.put(new SymbolFunctor("coname", 1), new int[]{ISCONAME});
//			guardLibrary1.put(new SymbolFunctor("setconame", 2), new int[]{ISNAME, Instruction.SETCONAME, ISINT});
//			guardLibrary1.put(new SymbolFunctor("!", 2), new int[]{ISNAME, Instruction.SETCONAME, ISINT});
//			guardLibrary1.put(new SymbolFunctor("hasconame", 1), new int[]{ISNAME, Instruction.HASCONAME});
//			guardLibrary1.put(new SymbolFunctor("nhasconame", 1), new int[]{ISNAME, Instruction.NHASCONAME});
//			guardLibrary1.put(new SymbolFunctor("getconame", 2), new int[]{ISNAME, Instruction.GETCONAME, ISINT});
//			guardLibrary1.put(new SymbolFunctor("getname", 2), new int[]{ISCONAME, Instruction.GETNAME, ISINT});
//			guardLibrary2.put(new SymbolFunctor("><",  3), new int[]{ISNAME, ISNAME, Instruction.UNIFYHLINK, ISINT});
//			guardLibrary2.put(new SymbolFunctor("and",  3), new int[]{ISNAME, ISNAME, Instruction.UNIFYCONAMEAND, ISINT});
//			guardLibrary2.put(new SymbolFunctor("or",  3), new int[]{ISNAME, ISNAME, Instruction.UNIFYCONAMEOR, ISINT});
        }
	}

	//
	private RuleCompiler rc;			// rc.rs用
	private List<Atom> typeConstraints;		// 型制約のリスト
	private Map<String, ContextDef>  typedProcessContexts;	// 型付きプロセス文脈名から定義へのマップ

	GuardCompiler(RuleCompiler rc, HeadCompiler hc) {
		super();
		this.rc = rc;
		this.initNormalizedCompiler(hc);
		match = rc.guard;
		typeConstraints      = rc.rs.guardMem.atoms;
		typedProcessContexts = rc.rs.typedProcessContexts;

		guardLibrary1.put(new SymbolFunctor("string",1), new int[]{ISSTRING});
//		guardLibrary2.put(new SymbolFunctor("class",2), new int[]{0,      ISSTRING,Instruction.INSTANCEOF});
//		guardLibrary1.put(new SymbolFunctor("class", 2), new int[]{0,              Instruction.GETCLASS,  ISSTRING});
		guardLibrary1.put(new SymbolFunctor("connectRuntime",1), new int[]{ISSTRING, Instruction.CONNECTRUNTIME});
	}

	/** initNormalizedCompiler呼び出し後に呼ばれる。
	 * 左辺関係型付き$pに対して、ガード用の仮引数番号を
	 * 変数番号として左辺関係型付き$pのマッチングを取り終わった内部状態を持つようにする。*/
	private final void initNormalizedGuardCompiler(GuardCompiler gc) {
		identifiedCxtdefs = (HashSet)gc.identifiedCxtdefs.clone();
		typedCxtDataTypes = (HashMap)gc.typedCxtDataTypes.clone();
		typedCxtDefs = (ArrayList)((ArrayList)gc.typedCxtDefs).clone();
		typedCxtSrcs = (HashMap)gc.typedCxtSrcs.clone();
		typedCxtTypes = (HashMap)gc.typedCxtTypes.clone();
		varCount = gc.varCount;	// 重複
	}

	/** ガード否定条件のコンパイルで使うためにthisに対する正規化されたGuardCompilerを作成して返す。
	 * 正規化とは、左辺の全てのアトム/膜および左辺関係型付き$pに対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺と左辺関係型制約のマッチングを取り終わった内部状態を持つようにすることを意味する。*/
	private final GuardCompiler getNormalizedGuardCompiler() {
		GuardCompiler gc = new GuardCompiler(rc,this);
		gc.initNormalizedGuardCompiler(this);
		return gc;
	}
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
				match.add(new Instruction(Instruction.GETLINK, varCount, atompath, j));
				varCount++;
			}
			linkPaths.put(new Integer(atompath), paths);
		}
	}

	/** 型付きプロセス文脈が表すプロセスを一意に決定する。*/
	void fixTypedProcesses() throws CompileException {
		// STEP 1 - 左辺に出現する型付きプロセス文脈を特定されたものとしてマークする。
		identifiedCxtdefs = new HashSet<ContextDef>();
		for(ContextDef def : typedProcessContexts.values()){
			if (def.lhsOcc != null) {
				identifiedCxtdefs.add(def);
				// 左辺の型付きプロセス文脈の明示的な自由リンクの先が、左辺のアトムに出現することを確認する。
				// 出現しない場合はコンパイルエラーとする。この制限を「パッシブ型制限」と呼ぶことにする。
				// 【注意】パッシブ型制限は、型は非アクティブなデータを表すことを想定することにより正当化される。
				// つまり、( 2(X) :- found(X) ) や ( 2(3) :- ok ) で2や3を$pで表すことはできない。
				// しかし実際には処理系側の都合による制限である。
				// なお、プログラミングの観点から、右辺の型付きプロセス文脈の明示的な自由リンクの先は任意としている。
				//
				// ( 2006/09/13 kudo ) 2引数以上の型付きプロセス文脈の導入に伴い，全ての引数をチェックするようにする
				for(int i=0;i<def.lhsOcc.args.length;i++){
					if (!atomPaths.containsKey(def.lhsOcc.args[i].buddy.atom)) {
						error("COMPILE ERROR: a partner atom is required for the head occurrence of typed process context: " + def.getName());
					}
				}
			}
			else if (def.lhsMem != null) {
				if (def.lhsMem.pragmaAtHost.def == def) {
					// 左辺の＠指定で定義される場合
					identifiedCxtdefs.add(def);
					int atomid = varCount++;
					match.add(new Instruction(Instruction.GETRUNTIME, atomid, memToPath(def.lhsMem)));
					typedCxtSrcs.put(def, new Integer(atomid));
					typedCxtDefs.add(def);
					typedCxtTypes.put(def, UNARY_ATOM_TYPE);
					typedCxtDataTypes.put(def, new Integer(ISSTRING));
				}
			}
		}
		// STEP 2 - 全ての型付きプロセス文脈が特定され、型が決定するまで繰り返す		
		LinkedList<Atom> cstrs = new LinkedList<Atom>(typeConstraints);

		{
			// uniq, not_uniq を最初に（少なくともint, unary などの前に）処理する
			LinkedList<Atom> tmpFirst = new LinkedList<Atom>();
			LinkedList<Atom> tmpLast = new LinkedList<Atom>();
			for(Iterator<Atom> it = cstrs.iterator(); it.hasNext();){
				Atom a = it.next();
				if(a.functor.getName().endsWith("uniq") || a.functor.getName().equals("custom")) {
					tmpFirst.add(a);
					it.remove();
				}
				if(a.functor.getName().startsWith("custom") 
						|| a.functor.getName().equals("new")
						|| a.functor.getName().equals("make")) {
					tmpLast.add(a);
					it.remove();
				}
			}
			tmpFirst.addAll(cstrs);
			tmpFirst.addAll(tmpLast);
			cstrs = tmpFirst;
		}

		boolean changed;
		do {
			changed = false;
			FixType:
				for(ListIterator<Atom> lit = cstrs.listIterator(); lit.hasNext();){
					Atom cstr = lit.next();
					Functor func = cstr.functor;
					ContextDef def1 = null;
					ContextDef def2 = null;
					ContextDef def3 = null;
					if (func.getArity() > 0)  def1 = ((ProcessContext)cstr.args[0].buddy.atom).def;
					if (func.getArity() > 1)  def2 = ((ProcessContext)cstr.args[1].buddy.atom).def;
					if (func.getArity() > 2)  def3 = ((ProcessContext)cstr.args[2].buddy.atom).def;

					if (func.equals(new SymbolFunctor("unary", 1))) {
						if (!identifiedCxtdefs.contains(def1)) continue;
						int atomid1 = loadUnaryAtom(def1);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
					}
					else if (func.equals(new SymbolFunctor("ground", 1))){
						if (!identifiedCxtdefs.contains(def1)) continue;
						checkGroundLink(def1);
					}
					// ガードインライン
					else if (func.getName().startsWith("custom_")) {
						boolean hasError=false;
						if(func.getName().length()<7+func.getArity()+1) hasError=true;
						boolean[] isIn = new boolean[func.getArity()];
						if(func.getName().charAt(7+isIn.length)!='_') hasError=true;
						for(int i=0;i<isIn.length;i++) {
							char ch = func.getName().charAt(7+i);
							if(ch!='i' && ch!='o') hasError=true;
							isIn[i] = ch=='i';
						}
						if(hasError) {
							String mo = "";
							for(int i=0;i<isIn.length;i++) mo += "?";
							error("Guard "+func.getName()+" should be custom_"+mo+"_xxxx. (? : 'i' when input, 'o' when output)");
						}

						String guardID = func.getName().substring(7+func.getArity()+1);
						ArrayList<Integer> vars = new ArrayList<Integer>();
						ArrayList<Integer> out = new ArrayList<Integer>(); // 出力引数
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
								typedCxtDataTypes.put(def3, new Integer(ISINT));
								aid = typedcxtToSrcPath(defK);
								out.add(new Integer(aid));
							}
							vars.add(new Integer(aid));
//							vars.add(new Integer(loadUnaryAtom(def1)));
//							System.out.println("varcount "+varcount);
//							System.out.println("1 "+typedcxtdatatypes);
//							System.out.println("1 "+typedcxtdefs);
//							System.out.println("1 "+typedcxtsrcs);
//							System.out.println("1 "+typedcxttypes);
						}
//						System.out.println("vars "+vars);
						match.add(new Instruction(Instruction.GUARD_INLINE, guardID, vars, out));
					}
					else if (func.getName().equals("uniq") || func.getName().equals("not_uniq")){
						ArrayList<Integer> uniqVars = new ArrayList<Integer>();
						for(int k=0;k<cstr.args.length;k++) {
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
							uniqVars.add(new Integer(srcPath));
						}
						if(func.getName().equals("uniq")) {
							match.add(new Instruction(Instruction.UNIQ, uniqVars));
						} else {
							match.add(new Instruction(Instruction.NOT_UNIQ, uniqVars));
						}
						rc.theRule.hasUniq = true;
					}
					else if (func.equals(new SymbolFunctor("\\=",2))) {
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
								typedCxtTypes.get(def2) == UNARY_ATOM_TYPE){
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
					else if (func.equals(new SymbolFunctor("class", 2))) {
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
						int atomid1 = loadUnaryAtom(def1);
						int atomid2 = loadUnaryAtom(def2);
						if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
							connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
						if (!new Integer(ISSTRING).equals(typedCxtDataTypes.get(def2))) {
							match.add(new Instruction(ISSTRING, atomid2));
							typedCxtDataTypes.put(def2, new Integer(ISSTRING));
						}
						// todo: Instruction.INSTANCEOF
						int classnameAtomid = varCount++;
						match.add(new Instruction(Instruction.GETCLASS, classnameAtomid, atomid1));
						match.add(new Instruction(Instruction.SUBCLASS, classnameAtomid, atomid2));
					}
					else if (func instanceof runtime.IntegerFunctor) {
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, new Integer(ISINT));
					}
					else if (func instanceof runtime.FloatingFunctor) {
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, new Integer(ISFLOAT));
					}
					else if (func instanceof runtime.StringFunctor) {
						bindToFunctor(def1, func);
						typedCxtDataTypes.put(def1, new Integer(ISSTRING));
					}
//					else if (func instanceof runtime.ObjectFunctor
//					&& ((runtime.ObjectFunctor)func).getObject() instanceof String) {
//					bindToFunctor(def1, func);
//					typedcxtdatatypes.put(def1, new Integer(ISSTRING));
//					}
					else if (cstr.isSelfEvaluated && func.getArity() == 1) {
						bindToFunctor(def1, func);
						// typedcxtdatatypes.put(def1, new Integer(Instruction.ISUNARY));
					}
					else if (func.equals(Functor.UNIFY)) { // (-X = +Y)
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
					else if (func.equals(new SymbolFunctor("==",2))) { // (+X == +Y) //seiji
						/* unary用比較演算子 (10/07/07 seiji) */
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
						int atomid1 = loadUnaryAtom(def1);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
						int atomid2 = loadUnaryAtom(def2);
						match.add(new Instruction(Instruction.ISUNARY, atomid2));
						match.add(new Instruction(Instruction.SAMEFUNC, atomid1, atomid2));
					}
					else if (func.equals(new SymbolFunctor("\\==",2))) { // (+X \== +Y) //seiji
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
					else if (guardLibrary0.containsKey(func)) { // 0入力制約//seiji
						int[] desc = guardLibrary0.get(func);
						int atomid = varCount++;
						match.add(new Instruction(desc[0], atomid));
						bindToUnaryAtom(def1, atomid);
						typedCxtDataTypes.put(def1, new Integer(desc[1]));
						if (identifiedCxtdefs.contains(def1)) {
							 int funcid2 = varCount++;
							 match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid));
							 int atomid1 = varCount++;
							 match.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
							 typedCxtSrcs.put(def1, new Integer(atomid1));
							 typedCxtDefs.add(def1);
							 identifiedCxtdefs.add(def1);
							 typedCxtTypes.put(def1, UNARY_ATOM_TYPE);

						}						
					}
					else if (guardLibrary1.containsKey(func)) { // 1入力制約
						int[] desc = guardLibrary1.get(func);
						if (!identifiedCxtdefs.contains(def1)) continue;
						int atomid1 = loadUnaryAtom(def1);
						if (desc[0] != 0 && !new Integer(desc[0]).equals(typedCxtDataTypes.get(def1))) {
							match.add(new Instruction(desc[0], atomid1));
							typedCxtDataTypes.put(def1, new Integer(desc[0]));
						}
						if (func.getArity() == 1) { // {t1,inst} --> p(+X1)
							// // 060831okabe
							// // 以下をコメントアウト．
							// // つまりconnectruntime はput してget されるだけ．
							// // TODO よってconnectruntime はいらないので何とかする．（ライブラリを使った分散を作るときまで放置でよい）
							// hyperlinkのためにコメントアウト解除 (2010/07/07 seiji)
							if (desc.length > 1) match.add(new Instruction(desc[1], atomid1));
						}
						else { // {t1,inst,t2} --> p(+X1,-X2)
							int atomid2;
							if (desc[1] == -1) { // 単項 + と +. だけ特別扱い 
								atomid2 = atomid1;
								//bindToUnaryAtom 内で、実際に使うアトムを生成している。
							} else {
//								if (func.equals(new SymbolFunctor("getconame", 2))) // getconame制約のための処理
//									match.add(new Instruction(Instruction.HASCONAME, atomid1));
								atomid2 = varCount++;
								match.add(new Instruction(desc[1], atomid2, atomid1));
							}
							// 2006.07.06 n-kato //2006.07.01 by inui
							// if (func.equals(classFunctor)) bindToUnaryAtom(def2, atomid2, Instruction.SUBCLASS);
							// else
							bindToUnaryAtom(def2, atomid2);
							typedCxtDataTypes.put(def2, new Integer(desc[2]));
						}
					}
					else if (guardLibrary2.containsKey(func)) { // 2入力制約
						int[] desc = guardLibrary2.get(func);
						if (!identifiedCxtdefs.contains(def1)) continue;
						if (!identifiedCxtdefs.contains(def2)) continue;
//						Util.println("st");
						int atomid1 = loadUnaryAtom(def1);
						int atomid2 = loadUnaryAtom(def2);
						if(Env.findatom2 && def1.lhsOcc!=null && def2.lhsOcc!=null)
							connectAtoms(def1.lhsOcc.args[0].buddy.atom, def2.lhsOcc.args[0].buddy.atom);
//						Util.println("end");
						if (desc[0] != 0 && !new Integer(desc[0]).equals(typedCxtDataTypes.get(def1))) {
							match.add(new Instruction(desc[0], atomid1));
							typedCxtDataTypes.put(def1, new Integer(desc[0]));
						}
						if (desc[1] != 0 && !new Integer(desc[1]).equals(typedCxtDataTypes.get(def2))) {
							match.add(new Instruction(desc[1], atomid2));
							typedCxtDataTypes.put(def2, new Integer(desc[1]));
						}
						if (func.getArity() == 2) { // {t1,t2,inst} --> p(+X1,+X2)
							match.add(new Instruction(desc[2], atomid1, atomid2));
						}
						else { // desc={t1,t2,inst,t3} --> p(+X1,+X2,-X3)
							int atomid3 = varCount++;
							match.add(new Instruction(desc[2], atomid3, atomid1, atomid2));
							bindToUnaryAtom(def3, atomid3);
							typedCxtDataTypes.put(def3, new Integer(desc[3]));
						}
					}
					else {
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
		for(ListIterator<Atom> lit = cstrs.listIterator(); lit.hasNext();){
			Atom cstr = lit.next();
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
				typedCxtSrcs.put(def1, new Integer(atomid1));
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
	private void bindToFunctor(ContextDef def, Functor func) throws CompileException{
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			int atomid = varCount++;
			typedCxtSrcs.put(def, new Integer(atomid));
			typedCxtDefs.add(def);
			match.add(new Instruction(Instruction.ALLOCATOM, atomid, func));			
		}
		else {
			checkUnaryProcessContext(def);
			int atomid = typedcxtToSrcPath(def);
			if (atomid == UNBOUND) {
				LinkOccurrence srclink = def.lhsOcc.args[0].buddy; // defのソース出現を指すアトム側の引数
				atomid = varCount++;
//				Util.println("bindToFunctor " + srclink.atom);
				match.add(new Instruction(Instruction.DEREFATOM,
						atomid, atomToPath(srclink.atom), srclink.pos));
				typedCxtSrcs.put(def, new Integer(atomid));
				typedCxtDefs.add(def);
				match.add(new Instruction(Instruction.FUNC, atomid, func));
				getLinks(atomid, 1, match);
			} else {
				match.add(new Instruction(Instruction.FUNC, atomid, func));
			}
		}
		typedCxtTypes.put(def, UNARY_ATOM_TYPE);
	}
	/** 型付きプロセス文脈defを1引数アトム$atomidのファンクタで束縛する */
	private void bindToUnaryAtom(ContextDef def, int atomid) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			typedCxtSrcs.put(def, new Integer(atomid));
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
				typedCxtSrcs.put(def, new Integer(loadedatomid));
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
			typedCxtSrcs.put(def, new Integer(atomid));
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
				int[] paths = (int[])linkPaths.get(new Integer(atomToPath(def.lhsOcc.args[i].buddy.atom)));
				//linkids[i] = paths[def.lhsOcc.args[i].buddy.pos];
//				linkids.set(i,new Integer(paths[def.lhsOcc.args[i].buddy.pos]));
//				groundsrcs.put(def,new Integer(linkids));
				match.add(new Instruction(Instruction.ADDTOLIST,linkids, paths[def.lhsOcc.args[i].buddy.pos]));
			}
			groundSrcs.put(def,new Integer(linkids));
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
		if(typedCxtTypes.get(def) != UNARY_ATOM_TYPE && typedCxtTypes.get(def) != GROUND_LINK_TYPE){
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
				int[] paths = (int[])linkPaths.get(new Integer(atomToPath(atom)));
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
//			memToLinkListPath.put(def.lhsOcc.mem,new Integer(srclinklistpath));
//			}
//			else srclinklistpath = ((Integer)memToLinkListPath.get(def.lhsOcc.mem)).intValue();
			int natom = varCount++;
			match.add(new Instruction(Instruction.ISGROUND, natom, linkids, srclinklistpath));//,memToPath(def.lhsOcc.mem)));
			rc.hasISGROUND = false;
			if(!memToGroundSizes.containsKey(def.lhsOcc.mem))memToGroundSizes.put(def.lhsOcc.mem,new HashMap<ContextDef, Integer>());
			memToGroundSizes.get(def.lhsOcc.mem).put(def,new Integer(natom));
			
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
			match.add(new Instruction(Instruction.LOADFUNC,ausfunc,new runtime.IntegerFunctor(ausize)));
			//各groundについて、isground命令で貰ってきたground構成アトム数を足していく
			int allfunc = ausfunc;	
			for(ContextDef def : gmap.keySet()){
				int natomfp = gmap.get(def).intValue();
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
		List<Integer> args = new ArrayList<Integer>();		
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
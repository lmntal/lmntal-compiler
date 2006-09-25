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
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;

public class GuardCompiler extends HeadCompiler {
	static final Object UNARY_ATOM_TYPE  = "U"; // 1引数アトム
	static final Object GROUND_LINK_TYPE = "G"; // 基底項プロセス
//	static final Object LINEAR_ATOM_TYPE = "L"; // 任意のプロセス $p[X|*V]

	/** 型付きプロセス文脈定義 (ContextDef) -> データ型の種類を表すラップされた型検査命令番号(Integer) */
	HashMap typedcxtdatatypes = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> データ型のパターンを表す定数オブジェクト */
	HashMap typedcxttypes = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号 */
	HashMap typedcxtsrcs  = new HashMap();
	/** ground型付きプロセス文脈定義(ContextDef) -> リンクのソース出現（コピー元とする出現）のリストの変数番号 */
	HashMap groundsrcs = new HashMap();
	/** 膜(Membrane) -> (その膜に存在するground型付きプロセス文脈定義(ContextDef) -> 構成アトム数)というマップ */
	HashMap memToGroundSizes = new HashMap();
	/** ソース出現が特定された型付きプロセス文脈定義のセット
	 * <p>identifiedCxtdefs.contains(x) は、左辺に出現するかまたはloadedであることを表す。*/
	HashSet identifiedCxtdefs = new HashSet(); 
	/** 型付きプロセス文脈定義のリスト（仮引数IDの管理に使用する）
	 * <p>実際にはtypedcxtsrcsのキーを追加された順番に並べたもの。*/
	List typedcxtdefs = new ArrayList();
	
	int typedcxtToSrcPath(ContextDef def) {
		if (!typedcxtsrcs.containsKey(def)) return UNBOUND;
		return ((Integer)typedcxtsrcs.get(def)).intValue();
	}
	
	int groundToSrcPath(ContextDef def) {
		if (!groundsrcs.containsKey(def)) return UNBOUND;
		return ((Integer)groundsrcs.get(def)).intValue();
	}
	
	static final int ISINT    = Instruction.ISINT;	// 型制約の引数が整数型であることを表す
	static final int ISFLOAT  = Instruction.ISFLOAT;	// 〃 浮動小数点数型
	static final int ISSTRING = Instruction.ISSTRING;	// 〃 文字列型
	static final int ISMEM    = Instruction.ANYMEM;	// 〃 膜（getRuntime専用）
	static HashMap guardLibrary1 = new HashMap(); // 1入力ガード型制約名
	static HashMap guardLibrary2 = new HashMap(); // 2入力ガード型制約名
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
	}
	
	//
	RuleCompiler rc;			// rc.rs用
	List typeConstraints;		// 型制約のリスト
	Map  typedProcessContexts;	// 型付きプロセス文脈名から定義へのマップ
	
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
	final void initNormalizedGuardCompiler(GuardCompiler gc) {
		identifiedCxtdefs = (HashSet)gc.identifiedCxtdefs.clone();
		typedcxtdatatypes = (HashMap)gc.typedcxtdatatypes.clone();
		typedcxtdefs = (ArrayList)((ArrayList)gc.typedcxtdefs).clone();
		typedcxtsrcs = (HashMap)gc.typedcxtsrcs.clone();
		typedcxttypes = (HashMap)gc.typedcxttypes.clone();
		varcount = gc.varcount;	// 重複
	}
	
	/** ガード否定条件のコンパイルで使うためにthisに対する正規化されたGuardCompilerを作成して返す。
	 * 正規化とは、左辺の全てのアトム/膜および左辺関係型付き$pに対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺と左辺関係型制約のマッチングを取り終わった内部状態を持つようにすることを意味する。*/
	final GuardCompiler getNormalizedGuardCompiler() {
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
				paths[j] = varcount;
				match.add(new Instruction(Instruction.GETLINK, varcount, atompath, j));
				varcount++;
			}
			linkpaths.put(new Integer(atompath), paths);
		}
	}
	
	/** 型付きプロセス文脈が表すプロセスを一意に決定する。*/
	void fixTypedProcesses() throws CompileException {
		// STEP 1 - 左辺に出現する型付きプロセス文脈を特定されたものとしてマークする。
		identifiedCxtdefs = new HashSet();
		Iterator it = typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
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
					if (!atompaths.containsKey(def.lhsOcc.args[i].buddy.atom)) {
						error("COMPILE ERROR: a partner atom is required for the head occurrence of typed process context: " + def.getName());
					}
				}
			}
			else if (def.lhsMem != null) {
				if (def.lhsMem.pragmaAtHost.def == def) {
					// 左辺の＠指定で定義される場合
					identifiedCxtdefs.add(def);
					int atomid = varcount++;
					match.add(new Instruction(Instruction.GETRUNTIME, atomid, memToPath(def.lhsMem)));
					typedcxtsrcs.put(def, new Integer(atomid));
					typedcxtdefs.add(def);
					typedcxttypes.put(def, UNARY_ATOM_TYPE);
					typedcxtdatatypes.put(def, new Integer(ISSTRING));
				}
			}
		}
		// STEP 2 - 全ての型付きプロセス文脈が特定され、型が決定するまで繰り返す		
		LinkedList cstrs = new LinkedList();
		it = typeConstraints.iterator();
		while (it.hasNext()) cstrs.add(it.next());
		
		{
			// uniq, not_uniq を最初に（少なくともint, unary などの前に）処理する
			Iterator it0 = cstrs.iterator();
			LinkedList tmpFirst = new LinkedList();
			LinkedList tmpLast = new LinkedList();
			while(it0.hasNext()) {
				Atom a = (Atom)it0.next();
				if(a.functor.getName().endsWith("uniq") || a.functor.getName().equals("custom")) {
					tmpFirst.add(a);
					it0.remove();
				}
				if(a.functor.getName().startsWith("custom")) {
					tmpLast.add(a);
					it0.remove();
				}
			}
			tmpFirst.addAll(cstrs);
			tmpFirst.addAll(tmpLast);
			cstrs = tmpFirst;
		}
		
		boolean changed;
		do {
			changed = false;
			ListIterator lit = cstrs.listIterator();
			FixType:
			while (lit.hasNext()) {
				Atom cstr = (Atom)lit.next();
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
					ArrayList vars = new ArrayList();
					ArrayList out = new ArrayList(); // 出力引数
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
//							aid = loadUnaryAtom(defK);
						} else {
							int atomid = varcount++;
							bindToUnaryAtom(defK, atomid);
							typedcxtdatatypes.put(def3, new Integer(ISINT));
							aid = typedcxtToSrcPath(defK);
							out.add(new Integer(aid));
						}
						vars.add(new Integer(aid));
//						vars.add(new Integer(loadUnaryAtom(def1)));
//						System.out.println("varcount "+varcount);
//						System.out.println("1 "+typedcxtdatatypes);
//						System.out.println("1 "+typedcxtdefs);
//						System.out.println("1 "+typedcxtsrcs);
//						System.out.println("1 "+typedcxttypes);
					}
//					System.out.println("vars "+vars);
					match.add(new Instruction(Instruction.GUARD_INLINE, guardID, vars, out));
				}
				else if (func.getName().equals("uniq") || func.getName().equals("not_uniq")){
					ArrayList uniqVars = new ArrayList();
					for(int k=0;k<cstr.args.length;k++) {
						ContextDef defK = ((ProcessContext)cstr.args[k].buddy.atom).def;
						if (!identifiedCxtdefs.contains(defK)) continue FixType; // 未割り当てのプロセス（表記に-が付くもの）は認めない
						int srcPath;
//						srcPath = typedcxtToSrcPath(defK);
//						Env.p("VAR# "+srcPath);
//						if(srcPath==UNBOUND) {
							checkGroundLink(defK);
							srcPath = groundToSrcPath(defK);
//						}
//						Env.p("VAR## "+srcPath);
						if(srcPath==UNBOUND) continue FixType;
						uniqVars.add(new Integer(srcPath));
					}
					if(func.getName().equals("uniq")) {
						match.add(new Instruction(Instruction.UNIQ, uniqVars));
					} else {
						match.add(new Instruction(Instruction.NOT_UNIQ, uniqVars));
					}
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
							typedcxttypes.get(def1) == UNARY_ATOM_TYPE ||
							typedcxttypes.get(def2) == UNARY_ATOM_TYPE){
						int atomid1 = loadUnaryAtom(def1);
						int atomid2 = loadUnaryAtom(def2);
						match.add(new Instruction(Instruction.ISUNARY, atomid1));
						match.add(new Instruction(Instruction.ISUNARY, atomid2));
						int funcid1 = varcount++;
						int funcid2 = varcount++;
						match.add(new Instruction(Instruction.GETFUNC, funcid1, atomid1));
						match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
						match.add(new Instruction(Instruction.NEQFUNC, funcid1, funcid2));
					}
					else{
						checkGroundLink(def1);
						checkGroundLink(def2);
						int linkid1 = loadGroundLink(def1);
						int linkid2 = loadGroundLink(def2);
//						match.add(new Instruction(Instruction.ISGROUND,linkid1));
//						match.add(new Instruction(Instruction.ISGROUND,linkid2));
						match.add(new Instruction(Instruction.NEQGROUND,linkid1,linkid2));
					}
				}
				// (2006.07.06 n-kato)
				else if (func.equals(new SymbolFunctor("class", 2))) {
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = loadUnaryAtom(def2);
					if (!new Integer(ISSTRING).equals(typedcxtdatatypes.get(def2))) {
						match.add(new Instruction(ISSTRING, atomid2));
						typedcxtdatatypes.put(def2, new Integer(ISSTRING));
					}
					// todo: Instruction.INSTANCEOF
					int classnameAtomid = varcount++;
					match.add(new Instruction(Instruction.GETCLASS, classnameAtomid, atomid1));
					match.add(new Instruction(Instruction.SUBCLASS, classnameAtomid, atomid2));
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
					if(GROUND_ALLOWED && typedcxttypes.get(def2) != UNARY_ATOM_TYPE){
						if (!identifiedCxtdefs.contains(def1)) continue;
					}
					processEquivalenceConstraint(def1,def2);
				}
				else if (func.equals(new SymbolFunctor("==",2))) { // (+X == +Y)
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					processEquivalenceConstraint(def1,def2);
				}
				else if (guardLibrary1.containsKey(func)) { // 1入力制約
					int[] desc = (int[])guardLibrary1.get(func);
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					if (desc[0] != 0 && !new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						match.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (func.getArity() == 1) { // {t1,inst} --> p(+X1)
						// 060831okabe
						// 以下をコメントアウト．
						// つまりconnectruntime はput してget されるだけ．
						// TODO よってconnectruntime はいらないので何とかする．（ライブラリを使った分散を作るときまで放置でよい）
//						if (desc.length > 1) match.add(new Instruction(desc[1], atomid1));
					}
					else { // {t1,inst,t2} --> p(+X1,-X2)
						int atomid2;
						if (desc[1] == -1) { // 単項 + と +. だけ特別扱い 
							atomid2 = atomid1;
							//bindToUnaryAtom 内で、実際に使うアトムを生成している。
						} else {
							atomid2 = varcount++;
							match.add(new Instruction(desc[1], atomid2, atomid1));
						}
						// 2006.07.06 n-kato //2006.07.01 by inui
						// if (func.equals(classFunctor)) bindToUnaryAtom(def2, atomid2, Instruction.SUBCLASS);
						// else
						bindToUnaryAtom(def2, atomid2);
						typedcxtdatatypes.put(def2, new Integer(desc[2]));
					}
				}
				else if (guardLibrary2.containsKey(func)) { // 2入力制約
					int[] desc = (int[])guardLibrary2.get(func);
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = loadUnaryAtom(def2);
					if (desc[0] != 0 && !new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						match.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (desc[1] != 0 && !new Integer(desc[1]).equals(typedcxtdatatypes.get(def2))) {
						match.add(new Instruction(desc[1], atomid2));
						typedcxtdatatypes.put(def2, new Integer(desc[1]));
					}
					if (func.getArity() == 2) { // {t1,t2,inst} --> p(+X1,+X2)
						match.add(new Instruction(desc[2], atomid1, atomid2));
					}
					else { // desc={t1,t2,inst,t3} --> p(+X1,+X2,-X3)
						int atomid3 = varcount++;
						match.add(new Instruction(desc[2], atomid3, atomid1, atomid2));
						bindToUnaryAtom(def3, atomid3);
						typedcxtdatatypes.put(def3, new Integer(desc[3]));
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
		ListIterator lit = cstrs.listIterator();
		String text = "";
		while (lit.hasNext()) {
			Atom cstr = (Atom)lit.next();
			discardTypeConstraint(cstr);
			if (text.length() > 0)  text += ", ";
			text += cstr.toStringAsTypeConstraint();
		}
		error("COMPILE ERROR: never proceeding type constraint: " + text);
	}
	boolean GROUND_ALLOWED = true;
	/** 制約 X=Y または X==Y を処理する。ただしdef2は特定されていなければならない。*/
	private void processEquivalenceConstraint(ContextDef def1, ContextDef def2) throws CompileException{
		boolean checkNeeded = (typedcxttypes.get(def1) == null
							 && typedcxttypes.get(def2) == null); // 型付きであることの検査が必要かどうか
		//boolean GROUND_ALLOWED = true;
		// GROUND_ALLOWED のとき (unary = ?) は (? = unary) として処理する（ただし?はgroundまたはnull）
		if (GROUND_ALLOWED && typedcxttypes.get(def2) != UNARY_ATOM_TYPE) {
			if (typedcxttypes.get(def1) == UNARY_ATOM_TYPE) {
				ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
			}
		}
		if (GROUND_ALLOWED && typedcxttypes.get(def2) != UNARY_ATOM_TYPE) { // (? = ground)
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
				int funcid2 = varcount++;
				match.add(new Instruction(Instruction.GETFUNC, funcid2, atomid2));
				int atomid1 = varcount++;
				match.add(new Instruction(Instruction.ALLOCATOMINDIRECT, atomid1, funcid2));
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
			int atomid = varcount++;
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
			match.add(new Instruction(Instruction.ALLOCATOM, atomid, func));			
		}
		else {
			checkUnaryProcessContext(def);
			int atomid = typedcxtToSrcPath(def);
			if (atomid == UNBOUND) {
				LinkOccurrence srclink = def.lhsOcc.args[0].buddy; // defのソース出現を指すアトム側の引数
				atomid = varcount++;
				match.add(new Instruction(Instruction.DEREFATOM,
					atomid, atomToPath(srclink.atom), srclink.pos));
				typedcxtsrcs.put(def, new Integer(atomid));
				typedcxtdefs.add(def);
				match.add(new Instruction(Instruction.FUNC, atomid, func));
				getLinks(atomid, 1);
			} else {
				match.add(new Instruction(Instruction.FUNC, atomid, func));
			}
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
	}
	/** 型付きプロセス文脈defを1引数アトム$atomidのファンクタで束縛する */
	//2006.07.01 束縛する命令(?) bindid 引数を追加 by inui
	private void bindToUnaryAtom(ContextDef def, int atomid) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
		}
		else {
			int loadedatomid = typedcxtToSrcPath(def);
			if (loadedatomid == UNBOUND) {
				LinkOccurrence srclink = def.lhsOcc.args[0].buddy;
				loadedatomid = varcount++;
				match.add(new Instruction(Instruction.DEREFATOM,
					loadedatomid, atomToPath(srclink.atom), srclink.pos));
				typedcxtsrcs.put(def, new Integer(loadedatomid));
				typedcxtdefs.add(def);
				match.add(new Instruction(Instruction.SAMEFUNC, atomid, loadedatomid));
				getLinks(loadedatomid, 1);
			} else {
				match.add(new Instruction(Instruction.SAMEFUNC, atomid, loadedatomid));
			}
//			int funcid1 = varcount++;
//			int funcid2 = varcount++;
//			match.add(new Instruction(Instruction.GETFUNC, funcid1, atomid));
//			match.add(new Instruction(Instruction.GETFUNC, funcid2, loadedatomid));
//			match.add(new Instruction(Instruction.EQFUNC,  funcid1, funcid2));
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
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
			atomid = varcount++;
			match.add(new Instruction(Instruction.DEREFATOM,
				atomid, atomToPath(srclink.atom), srclink.pos));
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
			getLinks(atomid, 1);
		}
		typedcxttypes.put(def, UNARY_ATOM_TYPE);
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
			linkids = varcount++;
			match.add(new Instruction(Instruction.NEWLIST,linkids));
			for(int i=0;i<def.lhsOcc.args.length;i++){
				int[] paths = (int[])linkpaths.get(new Integer(atomToPath(def.lhsOcc.args[i].buddy.atom)));
				//linkids[i] = paths[def.lhsOcc.args[i].buddy.pos];
//				linkids.set(i,new Integer(paths[def.lhsOcc.args[i].buddy.pos]));
//				groundsrcs.put(def,new Integer(linkids));
				match.add(new Instruction(Instruction.ADDTOLIST,linkids, paths[def.lhsOcc.args[i].buddy.pos]));
			}
			groundsrcs.put(def,new Integer(linkids));
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
		if(typedcxttypes.get(def) != UNARY_ATOM_TYPE && typedcxttypes.get(def) != GROUND_LINK_TYPE){
			typedcxttypes.put(def,GROUND_LINK_TYPE);
//			int linkid = loadGroundLink(def);
//			ArrayList linkids = loadGroundLink(def);
			int linkids = loadGroundLink(def);
			int srclinklistpath;
//			if(!memToLinkListPath.containsKey(def.lhsOcc.mem)){
				srclinklistpath = varcount++;
				// 避けるリンクのリスト
				match.add(new Instruction(Instruction.NEWLIST,srclinklistpath));
				
				// 左辺出現アトムの，全ての引数(を指すリンク)のうち,
				// 左辺の自由リンクもしくは同じ膜のプロセス文脈に接続していて
				// このプロセス文脈の根でないものをリストに追加する
				Iterator it = def.lhsOcc.mem.atoms.iterator();
				while(it.hasNext()){
					Atom atom = (Atom)it.next();
					int[] paths = (int[])linkpaths.get(new Integer(atomToPath(atom)));
					for(int i=0;i<atom.args.length;i++){
//						match.add(new Instruction(Instruction.ADDATOMTOSET,srcsetpath,atomToPath((Atom)it.next())));
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
						if(!flgNotAdd)
							match.add(new Instruction(Instruction.ADDTOLIST,srclinklistpath,paths[i]));
					}
				}
//				memToLinkListPath.put(def.lhsOcc.mem,new Integer(srclinklistpath));
//			}
//			else srclinklistpath = ((Integer)memToLinkListPath.get(def.lhsOcc.mem)).intValue();
			int natom = varcount++;
			match.add(new Instruction(Instruction.ISGROUND, natom, linkids, srclinklistpath));//,memToPath(def.lhsOcc.mem)));
			if(!memToGroundSizes.containsKey(def.lhsOcc.mem))memToGroundSizes.put(def.lhsOcc.mem,new HashMap());
			((Map)memToGroundSizes.get(def.lhsOcc.mem)).put(def,new Integer(natom));
		}
		return;
	}

	/**
	 * unary型に制約されたプロセス文脈が1引数であることを確認する．
	 * @param def
	 * @throws CompileException
	 */
	private void checkUnaryProcessContext(ContextDef def) throws CompileException{
		if(def.lhsOcc.args.length!=1)	
			error("COMPILE ERROR: unary type process context must has exactly one argument : " + def.lhsOcc);
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
			Map gmap = (Map)memToGroundSizes.get(mem);
			//普通のアトムの個数と、unaryの個数
			int ausize = mem.getNormalAtomCount() + mem.typedProcessContexts.size() - gmap.size();
			int ausfunc = varcount++;
			match.add(new Instruction(Instruction.LOADFUNC,ausfunc,new runtime.IntegerFunctor(ausize)));
			//各groundについて、isground命令で貰ってきたground構成アトム数を足していく
			int allfunc = ausfunc;	
			Iterator it2 = gmap.keySet().iterator();
			while(it2.hasNext()){
				ContextDef def = (ContextDef)it2.next();
				int natomfp = ((Integer)gmap.get(def)).intValue();
				int newfunc = varcount++;
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
	public List getAtomActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < atoms.size(); i++) {
			args.add( atompaths.get(atoms.get(i)) );
		}
		Iterator it = typedcxtdefs.iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (typedcxttypes.get(def) == UNARY_ATOM_TYPE)
				args.add( new Integer(typedcxtToSrcPath(def)) );
		}
		return args;
	}
	
	//
	
	void error(String text) throws CompileException {
		Env.error(text);
		throw new CompileException("COMPILE ERROR");
	}

}
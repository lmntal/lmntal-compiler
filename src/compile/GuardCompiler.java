package compile;

import java.util.*;
//import runtime.Env;
import runtime.Instruction;
//import runtime.InstructionList;
import runtime.Functor;
import compile.structure.*;

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
	
	static final int ISINT    = Instruction.ISINT;	// 型制約の引数が整数型であることを表す
	static final int ISFLOAT  = Instruction.ISFLOAT;	// 〃 浮動小数点数型
	static final int ISSTRING = Instruction.ISSTRING;	// 〃 文字列型
	static final int ISMEM    = Instruction.ANYMEM;	// 〃 膜（getRuntime専用）
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
		guardLibrary1.put(new Functor("connectRuntime",1), new int[]{ISSTRING, Instruction.CONNECTRUNTIME});
	}
	
	//
	RuleCompiler rc;			// エラー出力用
	List typeConstraints;		// 型制約のリスト
	Map  typedProcessContexts;	// 型付きプロセス文脈名から定義へのマップ
	
	GuardCompiler(RuleCompiler rc, HeadCompiler hc) {
		super();
		this.rc = rc;
		this.initNormalizedCompiler(hc);
		match = rc.guard;
		typeConstraints      = rc.rs.guardMem.atoms;
		typedProcessContexts = rc.rs.typedProcessContexts;
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
	//				match.add(new Instruction(Instruction.NATOMS, submempath, submem.atoms.size()));
				// TODO （機能拡張）単一のアトム以外にマッチする型付きプロセス文脈でも正しく動くようにする(1)
				match.add(new Instruction(Instruction.NATOMS, mempath,
					mem.getNormalAtomCount() + mem.typedProcessContexts.size() ));
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
	void fixTypedProcesses() {
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
				if (!atompaths.containsKey(def.lhsOcc.args[0].buddy.atom)) {
					rc.error("COMPILE ERROR: a partner atom is required for the head occurrence of typed process context: " + def.getName());
					rc.corrupted();
					match.add(Instruction.fail());
					return;
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
					match.add(new Instruction(Instruction.ISUNARY, atomid1));
				}
				else if (func.equals(new Functor("\\=",2))) {
					// NSAMEFUNC を作るか？
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
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
				else if (func.getSymbolFunctorID().equals("class_2")) {
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					int atomid2 = varcount++;
					match.add(new Instruction(Instruction.GETCLASS, atomid2, atomid1));
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
				else if (func.equals(RuleCompiler.FUNC_UNIFY)) { // (-X = +Y)
					if (!identifiedCxtdefs.contains(def2)) { // (+X = -Y) は (-Y = +X) として処理する
						ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
						if (!identifiedCxtdefs.contains(def2)) continue;
					}
					processEquivalenceConstraint(def1,def2);
				}
				else if (func.equals(new Functor("==",2))) { // (+X == +Y)
					if (!identifiedCxtdefs.contains(def1)) continue;
					if (!identifiedCxtdefs.contains(def2)) continue;
					processEquivalenceConstraint(def1,def2);
				}
				else if (guardLibrary1.containsKey(func)) { // 1入力制約
					int[] desc = (int[])guardLibrary1.get(func);
					if (!identifiedCxtdefs.contains(def1)) continue;
					int atomid1 = loadUnaryAtom(def1);
					if (!new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						match.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (func.getArity() == 1) { // {t1,inst} --> p(+X1)
						if (desc.length > 1) match.add(new Instruction(desc[1], atomid1));
					}
					else { // {t1,inst,t2} --> p(+X1,-X2)
						int atomid2 = varcount++;
						match.add(new Instruction(desc[1], atomid2, atomid1));
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
					if (!new Integer(desc[0]).equals(typedcxtdatatypes.get(def1))) {
						match.add(new Instruction(desc[0], atomid1));
						typedcxtdatatypes.put(def1, new Integer(desc[0]));
					}
					if (!new Integer(desc[1]).equals(typedcxtdatatypes.get(def2))) {
						match.add(new Instruction(desc[1], atomid2));
						typedcxtdatatypes.put(def1, new Integer(desc[1]));
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
					rc.error("COMPILE ERROR: unrecognized type constraint: " + cstr);
					discardTypeConstraint(cstr);
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
		rc.error("COMPILE ERROR: never proceeding type constraint: " + text);
	}
	/** 制約 X=Y または X==Y を処理する。ただしdef2は特定されていなければならない。*/
	private void processEquivalenceConstraint(ContextDef def1, ContextDef def2) {
		boolean checkNeeded = (typedcxttypes.get(def1) == null
							 && typedcxttypes.get(def2) == null); // 型付きであることの検査が必要かどうか
		boolean GROUND_ALLOWED = false;
		// GROUND_ALLOWED のとき (unary = ?) は (? = unary) として処理する（ただし?はgroundまたはnull）
		if (GROUND_ALLOWED && typedcxttypes.get(def2) != UNARY_ATOM_TYPE) {
			if (typedcxttypes.get(def1) == UNARY_ATOM_TYPE) {
				ContextDef swaptmp=def1; def1=def2; def2=swaptmp;
			}
		}
		if (GROUND_ALLOWED && typedcxttypes.get(def2) != UNARY_ATOM_TYPE) { // (? = ground)
			// todo 実装
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
	private void discardTypeConstraint(Atom cstr) {
		match.add(Instruction.fail());
		for (int i = 0; i < cstr.functor.getArity(); i++) {
			ContextDef def = ((ProcessContext)cstr.args[i].buddy.atom).def;
			bindToFunctor(def,new Functor("*",1));
		}
	}
	/** 型付きプロセス文脈defを1引数ファンクタfuncで束縛する */
	private void bindToFunctor(ContextDef def, Functor func) {
		if (!identifiedCxtdefs.contains(def)) {
			identifiedCxtdefs.add(def);
			int atomid = varcount++;
			typedcxtsrcs.put(def, new Integer(atomid));
			typedcxtdefs.add(def);
			match.add(new Instruction(Instruction.ALLOCATOM, atomid, func));			
		}
		else {
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
	private int loadUnaryAtom(ContextDef def) {
		int atomid = typedcxtToSrcPath(def);
		if (atomid == UNBOUND) {
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
}
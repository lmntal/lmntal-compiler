/*
 * 作成日: 2003/10/28
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import runtime.Functor;
import runtime.Instruction;
import runtime.InstructionList;
import compile.structure.*;

/**
 * 膜に対するマッチング命令列を出力する
 * @author n-kato, pa
 * 
 * 仮引数IDは廃止された。
 * Ruby版では仮引数IDがそのままボディ命令列の仮引数番号を表していたが、今は違うので、仮引数ID自身には意味が無くなったため。
 * この廃止に伴って、仮引数IDでループする部分は全てatoms.iterator()を使うように変更する。
 * 
 * todo pathという命名が現状を正しく表していない。実際はvarnumだが、長いのでidにしようかとも思っている
 * 
 * <p><b>現状</b>　
 * 現在マッチング命令列では、本膜の変数番号を0、主導するアトムの変数番号を1にしている。これは今後も変えない。
 * ボディ命令列の仮引数では、先にmemsを枚挙してから、その続きの変数番号にatomsを枚挙している。
 */
public class HeadCompiler {
//	/** 左辺膜 */
//	public Membrane lhsmem;//m;
	/** マッチング命令列（のラベル）*/
	public InstructionList matchLabel;
	/** matchLabel.insts */
	public List match;

	public List mems			= new ArrayList();	// 出現する膜のリスト。[0]がm
	public List atoms			= new ArrayList();	// 出現するアトムのリスト	
	public Map  mempaths		= new HashMap();	// Membrane -> 変数番号
	public Map  atompaths		= new HashMap();	// Atomic -> 変数番号
	public Map  linkpaths		= new HashMap();	// Atomの変数番号 -> リンクの変数番号の配列
	
	private Map atomids		= new HashMap();	// Atom -> atoms内のindex（廃止の方向で検討する）
	private HashSet visited	= new HashSet();	// Atom -> boolean, マッチング命令を生成したかどうか
	private HashSet memVisited	= new HashSet();	// Membrane -> boolean, compileMembraneを呼んだかどうか

	boolean fFindDataAtoms;						// データアトムをfindatomしてよいかどうか
	boolean UNTYPED_COMPILE	= false;			// fFindDataAtomsの初期値
	
	int varcount;	// いずれアトムと膜で分けるべきだと思う
	
	private HashMap proccxteqMap = new HashMap(); // Membrane -> ProcessContextEquation
	
	final boolean isAtomLoaded(Atomic atom) { return atompaths.containsKey(atom); }
	final boolean isMemLoaded(Membrane mem) { return mempaths.containsKey(mem); }

	final int atomToPath(Atomic atom) { 
		if (!isAtomLoaded(atom)) return UNBOUND;
		return ((Integer)atompaths.get(atom)).intValue();
	}
	final int memToPath(Membrane mem) {
		 if (!isMemLoaded(mem)) return UNBOUND;
		 return ((Integer)mempaths.get(mem)).intValue();
	}
	final int linkToPath(int atomid, int pos) {
		if (!linkpaths.containsKey(new Integer(atomid))) return UNBOUND;
		return ((int[])linkpaths.get(new Integer(atomid)))[pos];
	}
	
	static final int UNBOUND = -1;
	
	HeadCompiler() {}
	
	/** ガード否定条件およびボディのコンパイルで使うために、
	 * thisを指定されたhcに対する正規化されたHeadCompilerとする。
	 * 正規化とは、左辺の全てのアトムおよび膜に対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺のマッチングを取り終わった内部状態を持つようにすることを意味する。*/
	final void initNormalizedCompiler(HeadCompiler hc) {
		matchLabel = new InstructionList();
		match = matchLabel.insts;
		mems.addAll(hc.mems);
		atoms.addAll(hc.atoms);
		varcount = 0;
		Iterator it = mems.iterator();
		while (it.hasNext()) {
			mempaths.put(it.next(), new Integer(varcount++));
		}
		it = atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atompaths.put(atom, new Integer(varcount));
			atomids.put(atom, new Integer(varcount++));
			visited.add(atom);
		}
	}
	
	/** ガード否定条件のコンパイルで使うためにthisに対する正規化されたHeadCompilerを作成して返す。
	 * 正規化とは、左辺の全てのアトムおよび膜に対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺のマッチングを取り終わった内部状態を持つようにすることを意味する。*/
	final HeadCompiler getNormalizedHeadCompiler() {
		HeadCompiler hc = new HeadCompiler();
		hc.initNormalizedCompiler(this);
		return hc;
	}
	/** 膜memの子孫の全てのアトムと膜を、それぞれリストatomsとmemsに追加する。
	 * リスト内の追加された位置がそのアトムおよび膜の仮引数IDになる。*/
	public void enumFormals(Membrane mem) {
		Env.c("enumFormals");
		Iterator it = mem.atoms.iterator();
		while (it.hasNext()) {
			// 左辺に出現したアトムを登録する
			Atom atom = (Atom)it.next();
			atomids.put(atom, new Integer(atoms.size()));
			atoms.add(atom);
		}
		mems.add(mem);	// 本膜はmems[0]
		it = mem.mems.iterator();
		while (it.hasNext()) {
			enumFormals((Membrane)it.next());
		}
	}
	
	public void prepare() {
		Env.c("prepare");
		mempaths.clear();
		atompaths.clear();
		visited.clear();
		memVisited.clear();
		matchLabel = new InstructionList();
		match = matchLabel.insts;
		varcount = 1;	// [0]は本膜
//		mempaths.put(mems.get(0), new Integer(0));	// 本膜の変数番号は 0
		fFindDataAtoms = UNTYPED_COMPILE;
	}

	/**
	 * 指定されたアトムに対してgetlinkを行い、変数番号をlinkpathsに登録する。
	 * RISC化に伴い追加(mizuno)
	 */
	public final void getLinks(int atompath, int arity) {
		int[] paths = new int[arity];
		for (int i = 0; i < arity; i++) {
			paths[i] = varcount;
			match.add(new Instruction(Instruction.GETLINK, varcount, atompath, i));
			varcount++;
		}
		linkpaths.put(new Integer(atompath), paths);
	}
	
	/** リンクでつながったアトムおよびその所属膜に対してマッチングを行う。
	 * また、途中で見つかった「新しい膜」のそれぞれに対して、compileMembraneを呼ぶ。
	 */
	public void compileLinkedGroup(Atom firstatom) {
		Env.c("compileLinkedGroup");
		LinkedList newmemlist = new LinkedList();
		LinkedList atomqueue = new LinkedList();
		atomqueue.add(firstatom);
		while( ! atomqueue.isEmpty() ) {
			Atom atom = (Atom)atomqueue.removeFirst();			
			if (visited.contains(atom)) continue;
			visited.add(atom);
			
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence buddylink = atom.args[pos].buddy;
				if (buddylink == null) continue; // ガード匿名リンクは無視
				if (!atomids.containsKey(buddylink.atom)) continue; // 右辺や$p（およびlhs->neg）へのリンクは無視
				Atom buddyatom = (Atom)buddylink.atom;
				
				if (atomToPath(buddyatom) != UNBOUND) {
					// リンク先のアトムをすでに取得している場合。
					// - リンク先のアトムbuddyatomと以前に取得したアトムの同一性を検査する。
					//   ただし検査は変数番号および引数番号の組に基づいた片方向のみでよい。
					// neg(等式右辺トップレベル)->lhs(左辺の非トップレベル)のとき
					if (proccxteqMap.containsKey(atom.mem)
					 && !proccxteqMap.containsKey(buddyatom.mem)
					 && buddyatom.mem.mem != null) {
						// just skip
					}
					else {
						// lhs(>)->lhs(<) または neg(>)->neg(<) ならば、
						// すでに同一性を確認するコードを出力しているため、何もしない
						int b = atomToPath(buddyatom);
						int t = atomToPath(atom);
						if (b < t) continue;
						if (b == t && buddylink.pos < pos) continue;
					}
				}
				// リンク先のアトムを新しい変数に取得する (*A)
				int buddyatompath = varcount++;
				match.add( new Instruction(Instruction.DEREF,
					buddyatompath, atomToPath(atom), pos, buddylink.pos ));
				
				// リンク先が他の等式右辺のアトムの場合（等式間リンクの場合）
				// 膜間の自由リンク管理アトム鎖の検査をし、膜階層がマッチするか検査を行う。
				// また*AのDEREFの第4引数およびbuddyatompathを訂正する。
				if (proccxteqMap.containsKey(atom.mem)
				 && proccxteqMap.containsKey(buddyatom.mem) && buddyatom.mem != atom.mem) {				
					// ( 0: 1:{$p[|*X],2:{$q[|*Y]}} :- \+($p=(atom(L),$pp),$q=(buddy(L),$qq)) | ... )
					// このルールのガードの意味:
					// ( 0: 1:{atom(L),$pp[|*XX],2:{buddy(L),$qq[|*YY]}} :- ... ) にはマッチしない
					int firstindex = match.size() - 1; // atomからのDEREF命令を指す
					//
					LinkedList atomSupermems  = new LinkedList(); // atomの広義先祖膜列（親膜側が先頭）
					LinkedList buddySupermems = new LinkedList(); // buddyの広義先祖膜列（親膜側が先頭）
					// 広義先祖膜列の計算
					// atomSupermems = {0,1}; buddySupermems = {0,1,2}
					Membrane mem = ((ProcessContextEquation)proccxteqMap.get(buddyatom.mem)).def.lhsOcc.mem;
					while (mem != null) {
						buddySupermems.addFirst(mem);
						mem = mem.mem;
					}
					mem = ((ProcessContextEquation)proccxteqMap.get(atom.mem)).def.lhsOcc.mem;
					while (mem != null) {
						atomSupermems.addFirst(mem);
						mem = mem.mem;
					}
					// 広義先祖膜列の共通部分削除
					// atomSupermems = {}; buddySupermems = {2}
					Iterator ita = atomSupermems.iterator();
					Iterator itb = buddySupermems.iterator();
					while (ita.hasNext() && itb.hasNext() && ita.next() == itb.next()) {
						ita.remove();
						itb.remove();
					}
					// 広義先祖膜列を命令列に変換しbuddyatompathを訂正する
					while (!atomSupermems.isEmpty()) {
						mem = (Membrane)atomSupermems.removeLast();
						match.add( new Instruction(Instruction.FUNC, buddyatompath, Functor.INSIDE_PROXY) );
						match.add( new Instruction(Instruction.DEREF, buddyatompath + 1, buddyatompath,     0, 0) );
						match.add( new Instruction(Instruction.DEREF, buddyatompath + 2, buddyatompath + 1, 1, 1) );
						buddyatompath += 2;
					}
					while (!buddySupermems.isEmpty()) {
						mem = (Membrane)buddySupermems.removeFirst();
						match.add( new Instruction(Instruction.FUNC, buddyatompath, Functor.OUTSIDE_PROXY) );
						match.add( new Instruction(Instruction.DEREF, buddyatompath + 1, buddyatompath,     0, 0) );
						match.add( new Instruction(Instruction.TESTMEM, memToPath(mem), buddyatompath + 1) );
						match.add( new Instruction(Instruction.DEREF, buddyatompath + 2, buddyatompath + 1, 1, 1) );
						buddyatompath += 2;
					}
					varcount = buddyatompath + 1;
					int lastindex = match.size() - 1; // buddyatomを取得するためのDEREF命令を指す
					
					// deref命令の第4引数を修正する					
					// - deref [-tmp1atom,atom,atompos,buddypos] ==> deref [-tmp1atom,atom,atompos,1]
//					((Instruction)match.get(firstindex)).setArg4(new Integer(1));
					Instruction oldfirst = (Instruction)match.remove(firstindex);
					Instruction newfirst = new Instruction(Instruction.DEREF,
						oldfirst.getIntArg1(), oldfirst.getIntArg2(), oldfirst.getIntArg3(), 1);
					match.add(firstindex,newfirst);
					// - deref [-buddyatom,tmpatom,tmppos,1] ==> deref [-buddyatom,buddypos,atompos,buddypos]
//					((Instruction)match.get(lastindex)).setArg4(new Integer(buddylink.pos));
					Instruction oldlast = (Instruction)match.remove(lastindex);
					Instruction newlast = new Instruction(Instruction.DEREF,
						oldlast.getIntArg1(), oldlast.getIntArg2(), oldlast.getIntArg3(), buddylink.pos);
					match.add(lastindex,newlast);
				}
				
				if (atomToPath(buddyatom) != UNBOUND) {
					// リンク先のアトムをすでに取得している場合
					// lhs(<)->lhs(>), neg(<)->neg(>), neg->lhs なのでリンク先のアトムの同一性を確認
					match.add( new Instruction(Instruction.EQATOM,
						buddyatompath, atomToPath(buddyatom) ));
					continue;
				}
				
				// リンク先のアトムをまだ取得していない場合

				// リンク先のアトムbuddyatomとファンクタおよび所属膜が同じアトムのうち、
				// 今まで取得したアトムであり、かつ今回の到着先の引数位置のリンクを逆向きにたどると
				// 右辺または$pにつながるようなどのアトムともbuddyatomが異なることを確認する。(2004.6.4)
				Membrane[] testmems = { buddyatom.mem };
				if (proccxteqMap.containsKey(buddyatom.mem)) {
					// $p等式トップレベルへのリンクのときは、$pがヘッド出現する膜とも比較する
					testmems = new Membrane[]{ buddyatom.mem,
						((ProcessContextEquation)proccxteqMap.get(buddyatom.mem)).def.lhsOcc.mem };
				}
				for (int i = 0; i < testmems.length; i++) {
					Iterator it = testmems[i].atoms.iterator();
					while (it.hasNext()) {
						Atom otheratom = (Atom)it.next();					
						int other = atomToPath(otheratom);
						if (other == UNBOUND) continue;
						if (!otheratom.functor.equals(buddyatom.functor)) continue;
						if (atomids.containsKey(otheratom.args[buddylink.pos].buddy.atom)) continue;
						match.add(new Instruction(Instruction.NEQATOM, buddyatompath, other));
					}
				}	
							
				// リンク先のアトムを変数に取得する
				
				atompaths.put(buddyatom, new Integer(buddyatompath));
				atomqueue.addLast( buddyatom );
				match.add(new Instruction(Instruction.FUNC, buddyatompath, buddyatom.functor));
				
				// リンク先の膜の特定
				
				if (atom.functor.equals(runtime.Functor.OUTSIDE_PROXY) && pos == 0) {
					// 子膜へのリンクの場合、子膜の同一性を検査しなければならない
					Membrane buddymem = buddyatom.mem;								
					int buddymempath = memToPath(buddyatom.mem);
					if (buddymempath != UNBOUND) {
						match.add(new Instruction( Instruction.TESTMEM, buddymempath, buddyatompath ));
					}
					else {
						buddymempath = varcount++;
						mempaths.put(buddymem, new Integer(buddymempath));
						match.add(new Instruction( Instruction.LOCKMEM, buddymempath, buddyatompath ));
						newmemlist.add(buddymem);
					// // GETMEM時代のコード
					//	Iterator it = buddymem.mem.mems.iterator();
					//	while (it.hasNext()) {
					//		Membrane othermem = it.getNext();
					//		if (othermem != buddymem && memToPath(othermem) != UNBOUND) {
					//			match.add(new Instruction( Instruction.NEQMEM,
					//				buddymempath, memIDPath(othermem) ));
					//		}
					//	}
					}
				}
				//リンクの一括取得(RISC化) by mizuno
				getLinks(buddyatompath, buddyatom.functor.getArity());
			}
		}
		// 見つかった新しい子膜にあるアトムを優先的に検査する。
		// ただしアクティブアトムがある膜を優先する。
		Iterator it = newmemlist.iterator();
		nextmem:
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			Iterator it2 = mem.atoms.iterator();
			while (it2.hasNext()) {
				Atom atom = (Atom)it2.next();
				if (!isAtomLoaded(atom) && atom.functor.isActive()) {
					compileMembrane(mem);
					it.remove();
					continue nextmem;
				}					
			}
		}
		it = newmemlist.iterator();
		while (it.hasNext()) {
			compileMembrane((Membrane)it.next());
		}
	}
	/** 引き続きこのヘッドを型なしでコンパイルするための準備をする。*/
	public void switchToUntypedCompilation() {
		fFindDataAtoms = true;
		memVisited.clear();
	}
	/** 膜および子孫の膜に対してマッチングを行う */
	public void compileMembrane(Membrane mem) {
		Env.c("compileMembrane");
		if (memVisited.contains(mem)) return;
		memVisited.add(mem);

		int thismempath = memToPath(mem);
		
		Iterator it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (!atom.functor.isActive() && !fFindDataAtoms) continue;
			if (atomToPath(atom) == UNBOUND) {
				// 見つかったアトムを変数に取得する
				int atompath = varcount++;
				match.add(Instruction.findatom(atompath, thismempath, atom.functor));
				// すでに取得している同じ所属膜かつ同じファンクタを持つアトムとの非同一性を検査する
				Membrane[] testmems = { mem };
				if (proccxteqMap.containsKey(mem)) {
					// $p等式トップレベルのアトムのときは、$pがヘッド出現する膜とも比較する
					testmems = new Membrane[]{ mem,
						((ProcessContextEquation)proccxteqMap.get(mem)).def.lhsOcc.mem };
				}
				for (int i = 0; i < testmems.length; i++) {
					Iterator it2 = testmems[i].atoms.iterator();
					while (it2.hasNext()) {
						Atom otheratom = (Atom)it2.next();					
						int other = atomToPath(otheratom);
						if (other == UNBOUND) continue;
						if (!otheratom.functor.equals(atom.functor)) continue;
						//if (otheratom == atom) continue;
						match.add(new Instruction(Instruction.NEQATOM, atompath, other));
					}
				}
				atompaths.put(atom, new Integer(atompath));
				//リンクの一括取得(RISC化) by mizuno
				getLinks(atompath, atom.functor.getArity());
				compileLinkedGroup(atom);
			}
//			compileLinkedGroup(atom);	// 2行上に移動してみた n-kato (2004.7.16)
		}
		it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int submempath = memToPath(submem);
			if (submempath == UNBOUND) {
				// 子膜を変数に取得する
				submempath = varcount++;
				match.add(Instruction.anymem(submempath, thismempath));
// NEQMEM は不要になっているが、参考のためにコードは残しておく。
//				Iterator it2 = mem.mems.iterator();
//				while (it2.hasNext()) {
//					Membrane othermem = (Membrane)it2.next();
//					int other = memToPath(othermem);
//					if (other == UNBOUND) continue;
//					//if (othermem == submem) continue;
//					match.add(new Instruction(Instruction.NEQMEM, submempath, other));
//				}
				mempaths.put(submem, new Integer(submempath));
			}
			//プロセス文脈がない場合やstableの検査は、ガードコンパイラに移動した。by mizuno
			compileMembrane(submem);
		}
	}
	/** 膜および子孫の膜に対して自由リンクの個数を調べる。
	 * <p>かつて$p等式右辺膜以外の場合は、自由リンクに関する検査を行う必要があった。
	 * しかし現在 redex "Tθ" に = を含んでもよい言語仕様になっているため、この検査は実は不要。
	 * したがってこのメソッドは呼ばれない。(n-kato 2004.11.24--2004.11.26) */
	public void checkFreeLinkCount(Membrane mem) {
		if (!mem.processContexts.isEmpty()) {
			int thismempath = memToPath(mem);
			ProcessContext pc = (ProcessContext)mem.processContexts.get(0); // 左辺膜の$p（必ず非トップ膜）
			// 明示的なリンク先（必ず非トップ膜のアトム（自由リンク管理アトムを含む））が
			// 自由リンク出力管理アトムでないことを確認する
			for (int i = 0; i < pc.args.length; i++) {
				int freelinktestedatompath = varcount++;
				match.add(new Instruction(Instruction.DEREFATOM, freelinktestedatompath,
					atomToPath(pc.args[i].buddy.atom), pc.args[i].buddy.pos));
				match.add(new Instruction(Instruction.NOTFUNC, freelinktestedatompath,
					Functor.INSIDE_PROXY));
			}
			// リンク束が無い場合
			if (pc.bundle == null) {
				match.add(new Instruction(Instruction.NFREELINKS, thismempath,
					mem.getFreeLinkAtomCount()));					
			}
		}
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			checkFreeLinkCount(submem);
		}
	}
//	public Instruction getResetVarsInstruction() {
//		return Instruction.resetvars(getMemActuals(), getAtomActuals(), getVarActuals());
//	}
	/** 次の命令列（ヘッド命令列→ガード命令列→ボディ命令列）への膜引数列を返す。
	 * 具体的にはmemsに対応する変数番号のリストを格納したArrayListを返す。*/
	public List getMemActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < mems.size(); i++) {
			args.add( mempaths.get(mems.get(i)) );
		}
		return args;
	}
	/** 次の命令列（ヘッド命令列→ガード命令列→ボディ命令列）へのアトム引数列を返す。
	 * 具体的にはHeadCompilerはatomsに対応する変数番号のリストを格納したArrayListを返す。*/
	public List getAtomActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < atoms.size(); i++) {
			args.add( atompaths.get(atoms.get(i)) );
		}
		return args;
	}		
	/** 次の命令列（ヘッド命令列→ガード命令列→ボディ命令列）への膜やアトム以外の引数列を返す。
	 * 具体的にはHeadCompilerは空のArrayListを返す。*/
	public List getVarActuals() {
		return new ArrayList();
	}
	
	////////////////////////////////////////////////////////////////
	
	/** ガード否定条件をコンパイルする */
	void compileNegativeCondition(LinkedList eqs) {
		//int formals = varcount;
		//matchLabel.setFormals(formals);
		Iterator it = eqs.iterator();
		while (it.hasNext()) {
			ProcessContextEquation eq = (ProcessContextEquation)it.next();
			enumFormals(eq.mem);
			mempaths.put(eq.mem, mempaths.get(eq.def.lhsOcc.mem));
			proccxteqMap.put(eq.mem, eq);
		}
		it = eqs.iterator();
		while (it.hasNext()) {
			ProcessContextEquation eq = (ProcessContextEquation)it.next();
			compileMembrane(eq.mem);
			// プロセス文脈がないときは、アトムと子膜の個数がマッチすることを確認する
			if (eq.mem.processContexts.isEmpty()) {
				// TODO （機能拡張）単一のアトム以外にマッチする型付きプロセス文脈でも正しく動くようにする(2)
				match.add(new Instruction(Instruction.NATOMS, mempaths.get(eq.mem),
					  eq.def.lhsOcc.mem.getNormalAtomCount() + eq.def.lhsOcc.mem.typedProcessContexts.size()
					+ eq.mem.getNormalAtomCount() + eq.mem.typedProcessContexts.size() ));
				match.add(new Instruction(Instruction.NMEMS, mempaths.get(eq.mem),
					eq.def.lhsOcc.mem.mems.size() + eq.mem.mems.size() ));
			}
			else {
				ProcessContext pc = (ProcessContext)eq.mem.processContexts.get(0);
				if (pc.bundle == null) {
					// TODO （機能拡張）自由リンクの個数を検査する。ただし実装する前に$ppの明示的な自由リンクの集合を明らかにしなければならない。
				}
			}
			// eq.mem.ruleContexts は無視される					
		}
		// todo 自由リンク
		match.add(new Instruction(Instruction.PROCEED));	// 旧STOP
		//matchLabel.updateLocals(varcount);
	}
}
// TODO （機能拡張）ガード否定条件の中の型付きプロセス文脈をコンパイルする

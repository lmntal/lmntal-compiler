/*
 * 作成日: 2003/10/28
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import runtime.Functor;
import runtime.Instruction;
import compile.structure.*;

/**
 * 膜に対するマッチング命令列を出力する
 * @author n-kato, pa
 * 
 * TODO 簡単のため、いずれ仮引数IDを廃止する。
 * Ruby版では仮引数IDがそのままボディ命令列の仮引数番号を表していたが、今は違うので、仮引数ID自身には意味が無くなったため。
 * この廃止に伴って、仮引数IDでループする部分は全てatoms.iterator()を使うように変更する。
 * 
 * TODO pathという命名が現状を正しく表していない。実際はvarnumだが、長いのでidにしようかとも思っている
 * 
 * <p><b>現状</b>　
 * 現在マッチング命令列では、本膜の変数番号を0、主導するアトムの変数番号を1にしている。これは今後も変えない。
 * ボディ命令列の仮引数では、先にmemsを枚挙してから、その続きの変数番号にatomsを枚挙している。
 * 本膜の仮引数IDが0である。この制約は今後、仮引数IDがなくなると同時になくなる。
 */
public class HeadCompiler {
	public Membrane m;
	
	/**
	 * OBSOLETE: 空の膜を保持する。実引数リストは通常アトムから成るが、空膜にマッチさせる時は空膜自身が実引数となる。
	 * OBSOLETE: 実引数リスト内で、任意の空膜は任意のアトムより後ろに来る。
	 * メモ: 空膜と言っても{$p}なども含まれる
	 */
	public List mems          = new ArrayList();	// 出現する膜のリスト。[0]がm
	public List atoms         = new ArrayList();	// 出現するアトムのリスト
	
	public boolean visited[];

	public Map  mempaths      = new HashMap();	// Membrane -> 変数番号
	public Map  atompaths     = new HashMap();	// Atom -> 変数番号。いずれatomids/atomidpathに取って代わる
	
	public Map  atomids       = new HashMap();	// Atom -> 仮引数ID
	public List atomidpath    = new ArrayList();	// 仮引数ID -> 変数番号。いずれ配列にする。
	
	public List match         = new ArrayList();
	
	int varcount;	// いずれアトムと膜で分けるべきだと思う
	
	// いずれこれらのマクロは統廃合する

	final int atomToID(Atom atom) { return ((Integer)atomids.get(atom)).intValue(); }
	final int atomToPath(Atom atom) { return atomIDToPath(atomToID(atom)); }

	final boolean isAtomLoaded(Atom atom) { return isAtomIDLoaded(atomToID(atom)); }
	final boolean isAtomIDLoaded(int id) { return atomidpath.get(id) != null; }
	final boolean isMemLoaded(Membrane mem) { return mempaths.containsKey(mem); }

	final int atomIDToPath(int id) {
		if (!isAtomIDLoaded(id)) return UNBOUND;
		return ((Integer)atomidpath.get(id)).intValue();
	}
	final int memToPath(Membrane mem) {
		 if (!isMemLoaded(mem)) return UNBOUND;
		 return ((Integer)mempaths.get(mem)).intValue();
	}
	static final int UNBOUND = -1;
	
	HeadCompiler(Membrane m) {
		//Env.n("HeadCompiler");
		this.m = m;
	}
	
	/** 膜memの子孫の全てのアトムと膜を、それぞれリストatomsとmemsに追加する。
	 * リスト内の追加された位置がそのアトムおよび膜の仮引数IDになる。*/
	public void enumFormals(Membrane mem) {
		Env.c("enumFormals");
		Iterator it = mem.atoms.iterator();
		while (it.hasNext()) {
			// アトムの仮引数IDを登録する
			Atom atom = (Atom)it.next();
			atomids.put(atom, new Integer(atoms.size()));
			atoms.add(atom);
		}
		mems.add(mem);	// 本膜はmems[0]
		it = mem.mems.iterator();
		while (it.hasNext()) {
			enumFormals((Membrane)it.next());
		}
		// OBSOLETE: 子膜それぞれにやってたのを、自分に関して調べるように変更。
		// OBSOLETE: これで大元の膜に対してこれを書く必要がなくなった（とおもう）
		// // bodyでのgetparentを廃止するために条件判定を除去。本膜を[0]にするため上に移動。
		//if(mem.atoms.size() + mem.mems.size() == 0) {
		//mems.add(mem);
		//}
		//Env.d("atomids = "+atomids);
		//Env.d("atoms = "+atoms);
		//Env.d("mems = "+mems);
	}
	
	public void prepare() {
		Env.c("prepare");
		varcount = 1;	// [0]は本膜
		mempaths.clear();
		atompaths.clear();
		visited = new boolean[atoms.size()];
		atomidpath = new ArrayList();
		for (int i = 0; i < atoms.size(); i++) atomidpath.add(null);
		match = new ArrayList();
	}
	
	public void compileLinkedGroup(int atomid) {
		Env.c("compileLinkedGroup");
		LinkedList targetidqueue = new LinkedList();
		targetidqueue.add(new Integer(atomid));
		while( ! targetidqueue.isEmpty() ) {
			int targetid = ((Integer)targetidqueue.removeFirst()).intValue();
			
			if (visited[targetid]) continue;
			visited[targetid] = true;
			
			Atom atom = (Atom)atoms.get(targetid);
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence buddylink = atom.args[pos].buddy;
				if (buddylink == null) continue; // ガード匿名リンクは無視
				
				Atom buddyatom = buddylink.atom;
				if (!atomids.containsKey(buddyatom)) continue; // 右辺へのリンクは無視
				int buddyid = atomToID(buddyatom);
				
				if (atomIDToPath(buddyid) != UNBOUND) { // リンク先のアトムをすでに取得している場合
					// lhs(>)->lhs(<) または neg(>)->sameneg(<) ならば、
					// すでに同一性を確認するコードを出力しているため、何もしない
					if (true 
					 || (buddyatom.mem == m && atom.mem == m) // 否定条件コンパイル時にdebug予定
					   ) { 
						int b = atomIDToPath(buddyid);
						int t = atomIDToPath(targetid);
						if (b < t) continue;
						if (b == t && buddylink.pos < pos) continue;
					}
				}
				int buddyatompath = varcount++;
				match.add( new Instruction(Instruction.DEREF,
					buddyatompath, atomIDToPath(targetid), pos, buddylink.pos ));
					
				if( atomIDToPath(buddyid) != UNBOUND ) { // リンク先のアトムをすでに取得している場合
					// lhs(<)->lhs(>), neg(<)->neg(>), neg->lhs なのでリンク先のアトムの同一性を確認
					match.add(new Instruction(Instruction.EQATOM,
						buddyatompath, atomIDToPath(buddyid) ));
					continue;
				}
				
				// リンク先のアトムを変数に取得する
				
				atomidpath.set(buddyid, new Integer(buddyatompath));
				targetidqueue.addLast( new Integer(buddyid) );
				match.add(new Instruction(Instruction.FUNC, buddyatompath, buddyatom.functor));
				
				if (atom.functor.equals(runtime.Functor.OUTSIDE_PROXY) && pos == 0) {
					// 子膜へのリンクの場合
					Membrane buddymem = buddyatom.mem;								
					int buddymempath = memToPath(buddyatom.mem);
					if (buddymempath != UNBOUND) {
						match.add(new Instruction( Instruction.TESTMEM, buddymempath, buddyatompath ));
					}
					else {
						buddymempath = varcount++;
						mempaths.put(buddymem, new Integer(buddymempath));
						match.add(new Instruction( Instruction.LOCKMEM, buddymempath, buddyatompath ));
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
			}
		}
	}
	public void compileMembrane(Membrane mem) {
		Env.c("compileMembrane");
		int thismempath = memToPath(mem);
		
		Iterator it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			int targetid = atomToID(atom); // 仮引数ID
			if (atomIDToPath(targetid) == UNBOUND) {
				// 見つかったアトムを変数に取得する
				int atompath = varcount++;
				match.add(Instruction.findatom(atompath, thismempath, atom.functor));
				// すでに取得しているアトムとの非同一性を検査する
				Iterator it2 = mem.atoms.iterator();
				while (it2.hasNext()) {
					Atom otheratom = (Atom)it2.next();					
					int other = atomToPath(otheratom);
					if (other == UNBOUND) continue;
					if (!otheratom.functor.equals(atom.functor)) continue;
					//if (otheratom == atom) continue;
					match.add(new Instruction(Instruction.NEQATOM, atompath, other));
				}
				atomidpath.set(targetid, new Integer(atompath));
			}
			compileLinkedGroup(targetid);
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
			// プロセス文脈がないときは、アトムと子膜の個数がマッチすることを確認する
			// （ガードコンパイラに移動する予定）
			if (submem.processContexts.isEmpty()) {
//				match.add(new Instruction(Instruction.NATOMS, submempath, submem.atoms.size()));
				// TODO 単一のアトム以外にマッチする型付きプロセス文脈でも正しく動くようにする
				match.add(new Instruction(Instruction.NATOMS, submempath,
					submem.getNormalAtomCount() + submem.typedProcessContexts.size()));
				match.add(new Instruction(Instruction.NMEMS,  submempath, submem.mems.size()));
			}
			//
			if (submem.ruleContexts.isEmpty()) {
				match.add(new Instruction(Instruction.NORULES, submempath));
			}
			if (submem.stable) {
				match.add(new Instruction(Instruction.STABLE, submempath));
			}
			compileMembrane(submem);
		}
		if (!mem.processContexts.isEmpty()) {
			ProcessContext pc = (ProcessContext)mem.processContexts.get(0);
			for (int i = 0; i < pc.args.length; i++) {
				int freelinktestedatompath = varcount++;
				match.add(new Instruction(Instruction.DEREFATOM, freelinktestedatompath,
					atomToPath(pc.args[i].buddy.atom), pc.args[i].buddy.pos));
				match.add(new Instruction(Instruction.NOTFUNC, freelinktestedatompath,
					Functor.INSIDE_PROXY));
			}
			if (pc.bundle == null) {
				match.add(new Instruction(Instruction.NFREELINKS, thismempath,
					mem.getFreeLinkAtomCount()));					
			}
		}
	}
	public Instruction getResetVarsInstruction() {
		return Instruction.resetvars(getMemActuals(), getAtomActuals(), getVarActuals());
	}
	public List getAtomActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < atoms.size(); i++) {
			args.add( atomidpath.get(i) );
		}
		return args;
	}		
	public List getMemActuals() {
		List args = new ArrayList();		
		for (int i = 0; i < mems.size(); i++) {
			args.add( mempaths.get(mems.get(i)) );
		}
		return args;
	}
	public List getVarActuals() {
		return new ArrayList();
	}
}

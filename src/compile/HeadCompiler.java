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
 * TODO pathという命名が現状を正しく表していない。実際はvarnumだが、長いのでidにしようかとも思っている
 * 
 * <p><b>現状</b>　
 * 現在マッチング命令列では、本膜の変数番号を0、主導するアトムの変数番号を1にしている。これは今後も変えない。
 * ボディ命令列の仮引数では、先にmemsを枚挙してから、その続きの変数番号にatomsを枚挙している。
 */
public class HeadCompiler {
	/** 左辺膜 */
	public Membrane m;
	/** マッチング命令列（のラベル）*/
	public InstructionList matchLabel;
	/** matchLabel.insts */
	public List match;

	public List mems			= new ArrayList();	// 出現する膜のリスト。[0]がm
	public List atoms			= new ArrayList();	// 出現するアトムのリスト	
	public Map  mempaths		= new HashMap();	// Membrane -> 変数番号
	public Map  atompaths		= new HashMap();	// Atom -> 変数番号
	
	private Map atomids		= new HashMap();	// Atom -> atoms内のindex（廃止の方向で検討する）
	private HashSet visited	= new HashSet();	// Atom -> boolean, マッチング命令を生成したかどうか
	
	int varcount;	// いずれアトムと膜で分けるべきだと思う
	
	final boolean isAtomLoaded(Atom atom) { return atompaths.containsKey(atom); }
	final boolean isMemLoaded(Membrane mem) { return mempaths.containsKey(mem); }

	final int atomToPath(Atom atom) { 
		if (!isAtomLoaded(atom)) return UNBOUND;
		return ((Integer)atompaths.get(atom)).intValue();
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
	final HeadCompiler getNormalizedHeadCompiler() {
		HeadCompiler nhc = new HeadCompiler(m);
		nhc.matchLabel = new InstructionList();
		nhc.match = nhc.matchLabel.insts;
		nhc.mems.addAll(mems);
		nhc.atoms.addAll(atoms);
		nhc.varcount = 0;
		Iterator it = mems.iterator();
		while (it.hasNext()) {
			nhc.mempaths.put(it.next(), new Integer(nhc.varcount++));
		}
		it = atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			nhc.atompaths.put(atom, new Integer(nhc.varcount));
			nhc.atomids.put(atom, new Integer(nhc.varcount++));
			nhc.visited.add(atom);
		}
		return nhc;
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
		varcount = 1;	// [0]は本膜
		mempaths.clear();
		atompaths.clear();
		visited.clear();
		matchLabel = new InstructionList();
		match = matchLabel.insts;
	}
	
	public void compileLinkedGroup(Atom firstatom) {
		Env.c("compileLinkedGroup");
		LinkedList atomqueue = new LinkedList();
		atomqueue.add(firstatom);
		while( ! atomqueue.isEmpty() ) {
			Atom atom = (Atom)atomqueue.removeFirst();			
			if (visited.contains(atom)) continue;
			visited.add(atom);
			
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence buddylink = atom.args[pos].buddy;
				if (buddylink == null) continue; // ガード匿名リンクは無視
				
				Atom buddyatom = buddylink.atom;
				if (!atomids.containsKey(buddyatom)) continue; // 右辺や$pへのリンクは無視
				
				if (atomToPath(buddyatom) != UNBOUND) { // リンク先のアトムをすでに取得している場合
					// lhs(>)->lhs(<) または neg(>)->sameneg(<) ならば、
					// すでに同一性を確認するコードを出力しているため、何もしない
					if (true 
					 || (buddyatom.mem == m && atom.mem == m) // 否定条件コンパイル時にdebug予定
					   ) { 
						int b = atomToPath(buddyatom);
						int t = atomToPath(atom);
						if (b < t) continue;
						if (b == t && buddylink.pos < pos) continue;
					}
				}
				int buddyatompath = varcount++;
				match.add( new Instruction(Instruction.DEREF,
					buddyatompath, atomToPath(atom), pos, buddylink.pos ));
					
				if( atomToPath(buddyatom) != UNBOUND ) { // リンク先のアトムをすでに取得している場合
					// lhs(<)->lhs(>), neg(<)->neg(>), neg->lhs なのでリンク先のアトムの同一性を確認
					match.add(new Instruction(Instruction.EQATOM,
						buddyatompath, atomToPath(buddyatom) ));
					continue;
				}
				else { // リンク先のアトムをまだ取得していない場合
					// リンク先の引数位置が右辺または$pへのリンクであり、かつ同じファンクタを持つ
					// ようなどのアトムとも異なることを確かめなければならない。(2004.6.4)
					Iterator it = buddyatom.mem.atoms.iterator();
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
			if (atomToPath(atom) == UNBOUND) {
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
				atompaths.put(atom, new Integer(atompath));
			}
			compileLinkedGroup(atom);
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
				// TODO 単一のアトム以外にマッチする型付きプロセス文脈でも正しく動くようにする(1)
				match.add(new Instruction(Instruction.NATOMS, submempath,
					submem.getNormalAtomCount() + submem.typedProcessContexts.size() ));
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
			args.add( atompaths.get(atoms.get(i)) );
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

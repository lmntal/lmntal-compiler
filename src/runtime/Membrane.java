package runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.QueuedEntity;
import util.Stack;

/**
 * 抽象膜クラス。ローカル膜クラスおよびリモート膜クラスの親クラス
 * @author Mizuno
 */
abstract class AbstractMembrane extends QueuedEntity {
	/** この膜を管理するマシン */
	protected AbstractMachine machine;
	/** 親膜。リモートにあるならばRemoteMembraneオブジェクトまたはnullを参照する */
	protected AbstractMembrane parent;
	/** アトムの集合 */
	protected AtomSet atoms = new AtomSet();
	/** 子膜の集合 */
	protected Set mems = new HashSet();
//	/** この膜にあるproxy以外のアトムの数。 */
//	protected int atomCount = 0;
//	/** このセルの自由リンクの数 */
//	protected int freeLinkCount = 0;
	/** ルールセットの集合。 */
	protected List rulesets = new ArrayList();
	/** trueならばこの膜以下に適用できるルールが無い */
	protected boolean stable = false;
	/** ロックされている時にtrue */
	protected boolean locked = false;
//	/** 最後にロックを取得した膜 */
//	protected AbstractMembrane lastLockedMem;

	private static int nextId = 0;
	private int id;
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定されたマシンに所属する膜を作成する。
	 */
	protected AbstractMembrane(AbstractMachine machine, AbstractMembrane parent) {
		this.machine = machine;
		this.parent = parent;
		id = nextId++;
	}

	///////////////////////////////
	// 情報の取得

	/**
	 * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
	 * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
	 */
	public int hashCode() {
		return id;
	}
	/** この膜のローカルIDを取得する */
	String getLocalID() {
		return Integer.toString(id);
	}
	/** この膜が所属する計算ノードにおける、この膜のIDを取得する */
	abstract String getMemID();
	/** この膜が所属する計算ノードにおける、この膜の指定されたアトムのIDを取得する */
	abstract String getAtomID(Atom atom);
	
	//
	
	/** この膜を管理するマシンの取得 */
	AbstractMachine getMachine() {
		return machine;
	}
	/** 親膜の取得 */
	AbstractMembrane getParent() {
		return parent;
	}
	int getMemCount() {
		return mems.size();
	}
	/** proxy以外のアトムの数を取得 */
	int getAtomCount() {
		return atoms.getNormalAtomCount();
	}
	/** このセルの自由リンクの数を取得 */
	int getFreeLinkCount() {
		return atoms.getAtomCountOfFunctor(Functor.INSIDE_PROXY);
	}
	/** この膜とその子孫に適用できるルールがない場合にtrue */
	boolean isStable() {
		return stable;
	}
	/** stableフラグをONにする 10/26矢島 machine.exec()内で使う*/
	void toStable(){
		stable = true;
	}
	/** この膜にルールがあればtrue */
	boolean hasRule() {
		return rulesets.size() > 0;
	}
	boolean isRoot() {
		return machine.getRoot() == this;
	}
	
	// 反復子
	
	/** この膜にあるアトムの反復子を取得する */
	Iterator atomIterator() {
		return atoms.iterator();
	}
	/** この膜にある子膜の反復子を取得する */
	Iterator memIterator() {
		return mems.iterator();
	}
	/** 名前funcを持つアトムの反復子を取得する */
	Iterator atomIteratorOfFunctor(Functor functor) {
		return atoms.iteratorOfFunctor(functor);
	}
	/** この膜にあるルールセットの反復子を返す */
	Iterator rulesetIterator() {
		return rulesets.iterator();
	}

	///////////////////////////////
	// 操作（RemoteMembraneではオーバーライドされる）

	// 操作1 - ルールの操作
	
	/** ルールを全て消去する */
	void clearRules() {
		rulesets.clear();
	}
	/** srcMemにあるルールをこの膜にコピーする。 */
	void inheritRules(AbstractMembrane srcMem) {
		rulesets.addAll(srcMem.rulesets);
	}
	/** ルールセットを追加 */
	void loadRuleset(Ruleset srcRuleset) {
		rulesets.add(srcRuleset);
	}

	// 操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加する。
	 * TODO 実行スタックに自動的には詰まれないように仕様変更してもよいかもしれない。
	 * ただしその場合は、newatom命令の次にenqueueatomボディ命令が必要になる。
	 */
	Atom newAtom(Functor functor) {
		Atom a = new Atom(this, functor);
// #if (VERSION != 1.16_mizuno)
		addAtom(a);
		atoms.add(a);
// #else
//	atoms.add(atom);
//	if (functor.isActive()) {
//		enqueueAtom(a);
//	}
//	atomCount++;
// #endif
		return a;
	}
	/** 1引数のnewAtomを呼び出すマクロ */
	final Atom newAtom(String name, int arity) {
		return newAtom(new Functor(name, arity));
	}	
	/** この膜にアトムを追加するための内部命令。
	 * アクティブアトムの場合には実行スタックに追加する。
	 * pourでも使用される予定。 */
	protected final void addAtom(Atom atom) {
		atoms.add(atom);
		if (atom.getFunctor().isActive()) {
			enqueueAtom(atom);
		} 
//		atomCount++;
	}

	/** 指定された子膜に新しいinside_proxyアトムを追加する */
	Atom newFreeLink(AbstractMembrane mem) {
		return mem.newAtom(Functor.INSIDE_PROXY);
	}
	/** 指定されたアトムの名前を変える */
	void alterAtomFunctor(Atom atom, Functor func) {
		atoms.remove(atom);
		atom.changeFunctor(func);
		atoms.add(atom);
	}

	/** 指定されたアトムをこの膜の実行スタックに積む */
	abstract protected void enqueueAtom(Atom atom);
	/** この膜が移動された後、アクティブアトムを実行スタックに入れるために呼び出される。
	 * <p>Ruby版ではmovedTo(machine,dstMem)を再帰呼び出ししていたが、
	 * キューし直すべきかどうかの判断の手間が掛かりすぎるため子孫の膜に対する処理は廃止された。 */
	abstract protected void enqueueAllAtoms();

	/** 指定されたアトムをこの膜から除去する。
	 * 実行スタックに入っている場合、実行スタックから取り除く。
	 * TODO enqueueatom同様dequeueatomボディ命令を独立させる方法もある。
	 * AbstractMembrane#dequeueAtomはその場合のみabstractメソッドにする意味がある。
	 * 逆に、独立させないならdequeueAtomはマクロ（private final）でよい。 */
	void removeAtom(Atom atom) {
		atoms.remove(atom);
		//if (atom.isQueued()) { // dequeueAtom内に移動しました→水野君は確認後コメントを消して下さい
			dequeueAtom(atom);
		//}
	}
	/** removeAtomを呼び出すマクロ */
	final void removeAtoms(ArrayList atomlist) {
		// atoms.removeAll(atomlist);
		Iterator it = atomlist.iterator();
		while (it.hasNext()) {
			removeAtom((Atom)it.next());
		}
	}

	/** 
	 * この膜にあるアトムatomがこの計算ノードが実行するマシンにある膜の実行スタック内にあれば、除去する。
	 * 他の計算ノードが実行するマシンにある膜の実行スタック内のとき（システムコール）は、この膜は
	 * ロックされていないので何もしないでよいが、その場合は実行スタック内にないので既に対応できている。
	 * <p><strike>「この膜の実行スタックに入っているアトムatomを実行スタックから除去する」</strike>
	 * ←現在のデータ構造では、どの膜の実行スタックに入っているか調べることができないため却下された。
	 */
	protected final void dequeueAtom(Atom atom) {
		if (atom.isQueued()) {
			atom.dequeue();
		}
	}

	// 操作3 - 子膜の操作
	
	/** 新しい子膜を作成する */
	abstract AbstractMembrane newMem();
	/** 指定された膜をこの膜の子膜として追加するための内部命令 */
	protected final void addMem(AbstractMembrane mem) {
		mems.add(mem);
	}
	/** 指定された膜をこの膜から除去する */
	void removeMem(AbstractMembrane mem) {
		mems.remove(mem);
	}	
	/** 指定された計算ノードで実行されるルート膜を作成し、この膜の子膜にする。
	 * このメソッドは使わないかもしれないが、一応作っておく。
	 * @return 作成されたルート膜
	 */
	abstract AbstractMembrane newRoot(AbstractLMNtalRuntime runtime);

	// 操作4 - リンクの操作
	
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数を接続する。
	 * 接続するアトムは、
	 * <ol><li>この膜のアトム同士
	 *     <li>この膜のoutside_proxyと子膜のinside_proxy
	 * </ol>
	 * の2通りに限られる。
	 * <p>
	 * <b>注意</b>　
	 * newLinkはRuby版では片方向ずつリンクを生成する命令であったが、
	 * Java版では両方向を一度に生成するように仕様が変更されている。
	 */
	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);
	}
	/** atom1の第pos1引数と、atom2の第pos2引数のリンク先を接続する。
	 * 実行後、atom2の第pos2引数は廃棄しなければならない。
	 */
	void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		// TODO cloneは使わないくてよいはず。ただし当面はデバッグを容易にするためこのままでよい。
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/** atom1の第pos1引数のリンク先と、atom2の第pos2引数のリンク先を接続する。
	 */
	void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}

	// TODO relinkLocalAtomArgsとunifyLocalAtomArgsはLocalでないメソッドと同じなので何とかする

	/** relinkAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。
	 */
	protected final void relinkLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/** unifyAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。
	 */
	protected final void unifyLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}

	// 操作5 - 膜自身や移動に関する操作
	
	/** 活性化 */
	abstract void activate();
	/** この膜を親膜から除去する */
	void remove() {
		parent.removeMem(this);
		parent = null;
		removeProxies();
	}

	/** 除去された膜srcMemにある全てのアトムおよび膜をこの膜に移動する。
	 * 膜srcMemの子孫のうちルート膜の手前までの全ての膜を、この膜と同じマシンの管理にする。
	 * srcMemはこのメソッド実行後、このまま廃棄しなければならない。
	 */
	void pour(AbstractMembrane srcMem) {
		if (srcMem.machine.getRuntime() != machine.getRuntime()) {
			throw new RuntimeException("cross-site process fusion not implemented");
		}
		atoms.addAll(srcMem.atoms);
		mems.addAll(srcMem.mems);
		Iterator it = srcMem.memIterator();
		while (it.hasNext()) {
			((AbstractMembrane)it.next()).parent = this;
		}
		if (srcMem.machine != machine) {
			srcMem.setMachine(machine);
		}
	}
	
//	/** この膜の複製を生成する */
//	Membrane copy() {
//		
//	}
	
	/** この膜をdstMemに移動する。parent!=nullを仮定する。 */
	void moveTo(AbstractMembrane dstMem) {
		if (dstMem.machine.getRuntime() != machine.getRuntime()) {
			parent = dstMem;
			//((RemoteMembrane)dstMem).send("ADDROOT",getMemID());
			throw new RuntimeException("cross-site process migration not implemented");
		}
		parent.removeMem(this);
		dstMem.addMem(this);
		parent = dstMem;
		if (dstMem.machine != machine) {
			setMachine(dstMem.machine);
		}
		enqueueAllAtoms();
	}
	/** この膜とその子孫を管理するマシンを更新するために呼ばれる内部命令 */
	private void setMachine(AbstractMachine newMachine) {
		if (isRoot()) return;
		this.machine = newMachine;
		Iterator it = memIterator();
		while (it.hasNext()) {
			((AbstractMembrane)it.next()).setMachine(newMachine);
		}
	}
	/** この膜（ルート膜）の親膜を変更する。
	 * <p>いずれ、
	 * AbstractMembrane#newRootおよびAbstractLMNtalRuntime#newMachineの引数に親膜を渡すようにし、
	 * AbstractMembrane#moveToを使って親膜を変更することにより、
	 * TODO この問題のあるメソッドは廃止しなければならない */
	void setParent(AbstractMembrane mem) {
		if (!isRoot()) {
			throw new RuntimeException("setParent requires this be a root membrane");
		}
		parent = mem;
	}
	// 操作6 - ロックに関する操作
	
	/**
	 * この膜をロックする。
	 * @param mem ロックを要求しているルールがある膜
	 * @return ロックに成功した場合はtrue
	 */
	synchronized boolean lock(AbstractMembrane mem) {
		if (locked) {
			//todo:キューに記録
			return false;
		} else {
			//todo:計算ノードの記録、キャッシュの更新
			locked = true;
			return true;
		}
	}
	/**
	 * この膜とその子孫を再帰的にロックする
	 * @param mem ルールのある膜
	 * @return ロックに成功した場合はtrue
	 */
	boolean recursiveLock(AbstractMembrane mem) {
		// 実装する
		return false;
	}
	/** ロックを解放する */
	void unlock() {
		locked = false;
		// TODO 実装する
	}
	void recursive() {
		// 実装する
	}
	
	///////////////////////////////
	// 自由リンク管理アトムの張り替えをするための操作（RemoteMembraneはオーバーライドしない）
	//
	// TODO starをキューで管理することにより、alterAtomFunctorの回数を減らすとよいかも知れない。
	// キューはLinkedListオブジェクトとし、react内を生存期間とし、star関連のメソッドの引数に渡される。
	// $pを含む全ての膜の本膜からの相対関係がルール適用で不変な場合、
	// $pの先祖の全ての膜をうまく再利用することによって、star関連の処理を全く呼ぶ必要がなくなる。
	// ただしこれを行う場合、removeProxiesはremoveから分離した単独のボディ命令にする必要がある。
	
	/** この膜がremoveされた直後に呼ばれる。
	 * なおremoveは、ルール左辺に書かれたアトムを除去した後、
	 * ルール左辺に書かれた膜のうち$pを持つものに対して内側の膜から呼ばれる。
	 * <p>この膜に対して
	 * <ol>
	 * <li>この膜の自由/局所リンクでないにもかかわらずこの膜内を通過しているリンクを除去し
	 * <li>この膜の自由リンクが出現するアトムの名前をstarに変える。
	 * </ol>
	 * <p>すべてのremoveProxiesの呼び出しが終了すると
	 * <ul>
	 * <li>$pにマッチしたプロセスの自由リンクは$pが書かれた膜のstarアトムに出現するようになり、
	 * <li>starアトムのリンク先は、starアトムまたは本膜のoutside_proxyの第1引数になっている。
	 *     このうち後者は、removeToplevelProxiesで除去される。
	 * </ul>
	 */
	void removeProxies() {
		// TODO atomsへの操作が必要になるので、Setのクローンを取得してその反復子を使った方が
		//      読みやすい＆効率が良いかもしれない
		ArrayList changeList = new ArrayList();	// star化するアトムのリスト
		ArrayList removeList = new ArrayList();
		Iterator it = atoms.iteratorOfFunctor(Functor.OUTSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			Atom a0 = outside.args[0].getAtom();
			// outsideのリンク先が子膜でない場合			
			if (a0.getMem().getParent() != this) {
				Atom a1 = outside.args[1].getAtom();
				// この膜を通過して親膜に出ていくリンクを除去
				if (a1.getFunctor().equals(Functor.INSIDE_PROXY)) {
					unifyLocalAtomArgs(outside, 0, a1, 0);
					removeList.add(outside);
					removeList.add(a1);
				}
				else {
					// この膜を通過して無関係な膜に入っていくリンクを除去
					if (a1.getFunctor().equals(Functor.OUTSIDE_PROXY)
					 && a1.args[0].getAtom().getMem().getParent() != this) {
						if (!removeList.contains(outside)) {
							unifyLocalAtomArgs(outside, 0, a1, 0);
							removeList.add(outside);
							removeList.add(a1);
						}
					}
					// それ以外のリンクは、この膜の自由リンクなので名前をstarに変える
					else {
						changeList.add(outside);
					}
				}
			}
		}
		removeAtoms(removeList);
		// この膜のinside_proxyアトムの名前をstarに変える
		it = atoms.iteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			changeList.add(it.next());
		}
		// star化を実行する
		it = changeList.iterator();
		while (it.hasNext()) {
			alterAtomFunctor((Atom)it.next(), Functor.STAR);
		}
	}
	/** 左辺に$pが2個以上あるルールで、左辺の全てのremoveProxiesが呼ばれた後に
	 * 本膜に対して呼ぶことができる（呼ばなくてもよい）。
	 * <p>この膜を通過して無関係な膜に入っていくリンクを除去する。
	 */
	void removeToplevelProxies() {
		ArrayList removeList = new ArrayList();
		Iterator it = atoms.iteratorOfFunctor(Functor.OUTSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			// outsideのリンク先が子膜でない場合			
			if (outside.args[0].getAtom().getMem().getParent() != this) {
				if (!removeList.contains(outside)) {
					Atom a1 = outside.args[1].getAtom();
					unifyLocalAtomArgs(outside, 0, a1, 0);
					removeList.add(outside);
					removeList.add(a1);
				}
			}
		}
		atoms.removeAll(removeList);
	}
	/** 右辺の膜構造および$pの内容を配置した後で、
	 * ルール右辺に書かれた膜と本膜に対して内側の膜から呼ばれる。
	 * <p>子膜childMemWithStarにあるstarアトム（子膜の自由リンクがつながっている）に対して
	 * <ol>
	 * <li>名前をinside_proxyに変え
	 * <li>自由リンクの反対側の出現がこの膜のstarアトムならば、
	 *     後者の名前をoutside_proxyに変える。
	 *     また、分散実行のために、このリンクを張りなおす。
	 * <li>自由リンクの反対側の出現がこの膜（本膜）に残ったoutside_proxyアトムならば、何もしない。
	 * <li>自由リンクの反対側の出現がこの膜以外にあるアトムならば、
	 *     自由リンクがこの膜を通過するようにする。
	 *     このとき、この膜に作成するoutside_proxyでない方のアトムの名前はstarにする。
	 * </ol>
	 * @param childMemWithStar （自由リンクを持つ）子膜
	 */
	void insertProxies(AbstractMembrane childMemWithStar) {
		ArrayList changeList = new ArrayList();	// inside_proxy化するアトムのリスト
		Iterator it = childMemWithStar.atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next(); // n
			changeList.add(star);
			// 自由リンクの反対側の出現がこの膜のアトムならば、後者の名前をoutside_proxyに変える。
			// このときstarが消えるかもしれないので、starをキューで実装するときはバグに注意。
			if (star.args[0].getAtom().getMem() == this) {
				alterAtomFunctor(star.args[0].getAtom(), Functor.OUTSIDE_PROXY);
			} else {
				Atom outside = newAtom(Functor.OUTSIDE_PROXY); // o
				Atom newstar = newAtom(Functor.STAR); // m
				newLink(newstar, 1, outside, 1);
				relinkLocalAtomArgs(newstar, 0, star, 0); // これによりstar[0]が無効になる
				newLink(star, 0, outside, 0);
			}
		}
		it = changeList.iterator();
		while (it.hasNext()) {
			alterAtomFunctor((Atom)it.next(), Functor.INSIDE_PROXY);
		}
	}
	/** 右辺のトップレベルに$pがあるルールの実行時、最後に本膜に残ったstarを処理するために呼ばれる。
	 * <p>この膜にあるstarに対して、
	 * 反対側の出現であるoutside_proxyとともに除去し第2引数同士を直結する。
	 */
	void removeTemporaryProxies() {
		ArrayList removeList = new ArrayList();
		Iterator it = atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next();
			Atom outside = star.args[0].getAtom();
			unifyLocalAtomArgs(star,1,outside,1);
			removeList.add(star);
			removeList.add(outside);
		}
		removeAtoms(removeList);
	}
}

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * @author Mizuno
 */
final class Membrane extends AbstractMembrane {
	/** 実行スタック */
	private Stack ready = new Stack();
	/**
	 * 指定されたマシンに所属する膜を作成する。
	 * newMem/newRoot メソッド内で呼ばれる。
	 */
	private Membrane(AbstractMachine machine, AbstractMembrane parent) {
		super(machine, parent);
	}
	/**
	 * 親膜を持たない膜を作成し、指定されたマシンのルート膜にする。
	 */
	Membrane(Machine machine) {
		super(machine, null);
	}

	String getMemID() { return getLocalID(); }
	String getAtomID(Atom atom) { return atom.getLocalID(); }
	
	///////////////////////////////
	// 操作

	/** 実行スタックの先頭のアトムを取得し、実行スタックから除去 */
	Atom popReadyAtom() {
		return (Atom)ready.pop();
	}
	/** 
	 * 指定されたアトムを実行スタックに追加する。
	 * @param atom 実行スタックに追加するアトム。アクティブアトムでなければならない。
	 */
	protected void enqueueAtom(Atom atom) {
		ready.push(atom);
	}
	/** 膜の活性化 */
	void activate() {
		if (!isQueued()) {
			return;
		}
		if (!isRoot()) {
			((Membrane)parent).activate();
		}
		((Machine)machine).memStack.push(this);
	}
	/** 
	 * 移動された後、この膜のアクティブアトムを実行スタックに入れるために呼び出される。
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。
	 */
	protected void enqueueAllAtoms() {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (f.isActive()) {
				Iterator i2 = atoms.iteratorOfFunctor(f);
				while (i2.hasNext()) {
					Atom a = (Atom)i2.next();
					dequeueAtom(a);
					ready.push(a);
				}
			}
		}
	}
	AbstractMembrane newMem() {
		Membrane m = new Membrane(machine, this);
		mems.add(m);
		return m;
	}
	AbstractMembrane newRoot(AbstractLMNtalRuntime runtime) {
		AbstractMachine mach = runtime.newMachine();
		mach.getRoot().setParent(this);
		return mach.getRoot();
	}
}

package runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.QueuedEntity;
import util.Stack;

// TODO AbstractMachine AbstractMembrane.machine; は廃止して、
// Machine Membrane.machine および RemoteMachine RemoteMembrane.machine にする？

/**
 * 抽象膜クラス。ローカル膜クラスとリモート膜クラス（旧：膜キャッシュクラス；未実装）の親クラス
 * @author Mizuno
 */
abstract class AbstractMembrane extends QueuedEntity {
	/** この膜を管理するマシン */
	protected AbstractMachine machine;
	/** 親膜。ルート膜ならばnull */
	protected AbstractMembrane parent;
	/** アトムの集合 */
	protected AtomSet atoms = new AtomSet();
	/** 子膜の集合 */
	protected Set mems = new HashSet();
	/** この膜にあるproxy以外のアトムの数。 */
//	protected int atomCount = 0;
	/** このセルの自由リンクの数 */
//	protected int freeLinkCount = 0;
	/** ルールセットの集合。 */
	protected List rulesets = new ArrayList();
	/** trueならばこの膜以下に適用できるルールが無い */
	protected boolean stable = false;
	/** ロックされている時にtrue */
	protected boolean locked = false;
//	/** 最後にロックした計算ノード */
//	protected CalcNode lastLockNode;

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

	abstract void activate();
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
	/** アトムの追加 */
	Atom newAtom(Functor functor) {
		Atom a = new Atom(this, functor);
		atoms.add(a);
		if (a.isActive()) {
			enqueueAtom(a);
		}
//		atomCount++;
		return a;
	}
	Atom newAtom(String name, int arity) {
		return newAtom(new Functor(name, arity));
	}
	/** 指定されたアトムを実行スタックに積む */
	abstract protected void enqueueAtom(Atom atom);
	/** 膜の追加 */
	abstract AbstractMembrane newMem();

//	廃止。newAtom/newMemを使用する。
//	/** アトムの追加。アクティブアトムの場合には実行スタックに追加する。 */
//	void addAtom(Atom atom) {
//		atoms.add(atom);
//		activateAtom(atom);
//	}
	/** dstMemに移動する */
	void moveTo(AbstractMembrane dstMem) {
		parent.removeMem(this);
		dstMem.addMem(this);
		parent = dstMem;
//		movedTo(machine, dstMem);
		enqueueAllAtoms();
	}
	/** 移動された後、アクティブアトムを実行スタックに入れるために呼び出される */
//	protected void movedTo(AbstractMachine machine, AbstractMembrane dstMem) {
	abstract protected void enqueueAllAtoms();

//	// $pの移動のためのメソッドの仕様を選ぶ→案3に決定
//	// 案1：remove/pourで自由リンク管理アトムを追加・削除
//	// 案2：自由リンク管理アトム追加・削除のための専用メソッドの追加
//	// 案3：「removememで削除＆★化/pourで移動のみ/afterpourで追加」に分けて行う（by n-kato）
//	//		afterpour m,n ボディ命令は内側から再帰的に呼ばれ、一段ずつ追加する

	/** アトムの名前を変える */
	void alterAtomFunctor(Atom atom, Functor func) {
		atoms.remove(atom);
		atom.changeFunctor(func);
		atoms.add(atom);
	}
	/** 指定されたアトムをこの膜から除去する。 */
	void removeAtom(Atom atom) {
		atoms.remove(atom);
		if (atom.isQueued()) {
			dequeueAtom(atom);
		}
	}
	abstract protected void dequeueAtom(Atom atom);

	void removeAtoms(ArrayList atomlist) {
		// atoms.removeAll(atomlist);
		Iterator it = atomlist.iterator();
		while (it.hasNext()) {
			removeAtom((Atom)it.next());
		}
	}
	/** 指定された膜をこの膜から除去する */
	void removeMem(AbstractMembrane mem) {
		mems.remove(mem);
	}
	/** この膜を親膜から切り離す（detachという名前の方が正しいかもしれない） */
	void remove() {
		parent.removeMem(this);
		parent = null;
		//案3
		removeProxies();
	}
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
	final void removeProxies() {
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
					unifyAtomArgs(outside, 0, a1, 0);
					removeList.add(outside);
					removeList.add(a1);
				}
				else {
					// この膜を通過して無関係な膜に入っていくリンクを除去
					if (a1.getFunctor().equals(Functor.OUTSIDE_PROXY)
					 && a1.args[0].getAtom().getMem().getParent() != this) {
						if (!removeList.contains(outside)) {
							unifyAtomArgs(outside, 0, a1, 0);
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
	final void removeToplevelProxies() {
		ArrayList removeList = new ArrayList();
		Iterator it = atoms.iteratorOfFunctor(Functor.OUTSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			// outsideのリンク先が子膜でない場合			
			if (outside.args[0].getAtom().getMem().getParent() != this) {
				if (!removeList.contains(outside)) {
					Atom a1 = outside.args[1].getAtom();
					unifyAtomArgs(outside, 0, a1, 0);
					removeList.add(outside);
					removeList.add(a1);
				}
			}
		}
		atoms.removeAll(removeList);
	}
	/**
	 * srcMemの全ての内容をこの膜に移動する。
	 * TODO このままだとリモート膜のローカルキャッシュに対する処理のときに、
	 *       《「コミット」時にリモートにローカルキャッシュの内容をすべて転送しない限り》
	 *       正しく動作しない。これはaddAllを使うにあたっての全般的な問題でもある。
	 */
	void pour(AbstractMembrane srcMem) {
		atoms.addAll(srcMem.atoms);
		mems.addAll(srcMem.mems);
	}
	/** 右辺の膜構造および$pの内容を配置した後で、
	 * ルール右辺に書かれた膜と本膜に対して内側の膜から呼ばれる。
	 * <p>子膜childMemWithStarにあるstarアトム（子膜の自由リンクがつながっている）に対して
	 * <ol>
	 * <li>名前をinside_proxyに変え
	 * <li>自由リンクの反対側の出現がこの膜のstarアトムならば、
	 *     後者の名前をoutside_proxyに変える。
	 * <li>自由リンクの反対側の出現がこの膜（本膜）に残ったoutside_proxyアトムならば、何もしない。
	 * <li>自由リンクの反対側の出現がこの膜以外にあるアトムならば、
	 *     自由リンクがこの膜を通過するようにする。
	 *     このとき、この膜に作成するoutside_proxyでない方のアトムの名前はstarにする。
	 * </ol>
	 * @param childMemWithStar （自由リンクを持つ）子膜
	 */
	final void insertProxies(AbstractMembrane childMemWithStar) {
		ArrayList changeList = new ArrayList();	// inside_proxy化するアトムのリスト
		Iterator it = childMemWithStar.atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom inside = (Atom)it.next(); // n
			changeList.add(inside);
			if (inside.args[0].getAtom().getMem() == this) {
				alterAtomFunctor(inside.args[0].getAtom(), Functor.OUTSIDE_PROXY);
			} else {
				Atom outside = newAtom(Functor.OUTSIDE_PROXY); // o
				Atom newstar = newAtom(Functor.STAR); // m
				newLink(newstar, 1, outside, 1);
				relinkAtomArgs(newstar, 0, inside, 0);	// inside[0]が無効になる
				newLink(inside, 0, outside, 0);
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
	final void removeTemporaryProxies() {
		ArrayList removeList = new ArrayList();
		Iterator it = atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next();
			Atom outside = star.args[0].getAtom();
			unifyAtomArgs(star,1,outside,1);
			removeList.add(star);
			removeList.add(outside);
		}
		removeAtoms(removeList);
	}
	//////////////////////
	
	/** 膜の追加 */
	protected void addMem(AbstractMembrane mem) {
		mems.add(mem);
	}
	
	////////////////////////////////
	// ロック
	
	/**
	 * この膜をロックする
	 * @param mem ルールのある膜
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
		// TODO 実装する
		return false;
	}
	
//	/** この膜の複製を生成する */
//	Membrane copy() {
//		
//	}
	
	/** ロックを解放する */
	void unlock() {
		locked = false;
		// TODO 実装する
	}
	void recursiveUnlock() {
		// TODO 実装する
	}
	
	///////////////////////
	// リンクの操作
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数を接続する。
	 * 接続するアトムは、
	 * <ol><li>この膜のアトム同士
	 *     <li>この膜のinside_proxyと親膜のoutside_proxy
	 *     <li>この膜のoutside_proxyと子膜のinside_proxy
	 * </ol>
	 * の3通りの場合がある。
	 * <br>
	 * newLinkはRuby版では片方向ずつ行う方が生成が便利だったので
	 * 片方向ずつ分けており、ここもそれに合わせて修正しておきました (n-kato)
	 * newLinkの仕様を両方向一度に生成するように変更してもいいと思います。
	 * その場合、ボディ命令の仕様と同時に変更する必要がありますので、
	 * 原君と中島君に連絡してください。＞水野君
	 * <br>
	 * これに関連して、方法4の文書のinsertproxiesの部分でnewlinkがリンクにつき
	 * 1回しか呼ばれていませんが、これは現状の仕様では2回でなければなりません。
	 * 仕様変更しない場合は逆向きも書いて修正しておいてください。＞水野君
	 */
	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数のリンク先を接続する。
	 */
	void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数のリンク先と、atom2の第pos2引数のリンク先を接続する。
	 */
	void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
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
	 * newMemメソッド内で呼ばれる。
	 */
	private Membrane(AbstractMachine machine, AbstractMembrane parent) {
		super(machine, parent);
	}
	/**
	 * 指定されたマシンのルート膜を作成する。
	 */
	Membrane(Machine machine) {
		super(machine, null);
	}

	///////////////////////////////
	// 操作

	/** 実行スタックの先頭のアトムを取得し、実行スタックから除去 */
	Atom popReadyAtom() {
		return (Atom)ready.pop();
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
	protected void dequeueAtom(Atom atom) {
		atom.dequeue();
	}
	/** 
	 * 指定されたアトムを実行スタックに追加する。
	 * @param atom 実行スタックに追加するアトム。アクティブアトムでなければならない。
	 */
	protected void enqueueAtom(Atom atom) {
		ready.push(atom);
	}
	/** 
	 * 移動された後、アクティブアトムを実行スタックに入れるために呼び出される。
	 */
//	protected void movedTo(AbstractMachine machine, AbstractMembrane dstMem) {
	protected void enqueueAllAtoms() {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (true) { // f がアクティブの場合
				Iterator i2 = atoms.iteratorOfFunctor(f);
				while (i2.hasNext()) {
					ready.push((Atom)i2.next());
				}
			}
		}
	}
	/**
	 * 子膜を生成する。
	 */
	AbstractMembrane newMem() {
		Membrane m = new Membrane(machine, this);
		mems.add(m);
		return m;
	}
}

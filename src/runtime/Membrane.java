package runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.QueuedEntity;
import util.Stack;
import util.Util;


/**
 * 抽象膜クラス。ローカル膜クラスと膜キャッシュ（未実装）の親クラス
 * @author Mizuno
 */
abstract class AbstractMembrane extends QueuedEntity {
	/** この膜を管理するマシン */
	protected AbstractMachine machine;
	/** 親膜 */
	protected AbstractMembrane mem;
	/** アトムの集合 */
	protected AtomSet atoms = new AtomSet();;
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

	private static int lastId = 0;
	private int id;
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定されたマシンに所属する膜を作成する。
	 */
	protected AbstractMembrane(AbstractMachine machine, AbstractMembrane mem) {
		this.machine = machine;
		this.mem = mem;
		id = lastId++;
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
	AbstractMembrane getMem() {
		return mem;
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
		return atoms.getAtomCountOfFunctor(Functor.OUTSIDE_PROXY);
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
	// 操作

	abstract void activate();
	
	/** ルールを全てクリアする */
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
		enqueueAtom(a);
//		atomCount++;
		return a;
	}
	Atom newAtom(String name, int arity) {
		return newAtom(new Functor(name, arity));
	}
	/** 指定されたアトムを実行スタックに積む */
	abstract protected void enqueueAtom(Atom atom);
//	/** 膜の追加 */
	abstract AbstractMembrane newMem();

//	廃止。newAtom/newMemを使用する。
//	/** アトムの追加。アクティブアトムの場合には実行スタックに追加する。 */
//	void addAtom(Atom atom) {
//		atoms.add(atom);
//		activateAtom(atom);
//	}
	/** dstMemに移動 */
	void moveTo(AbstractMembrane dstMem) {
		mem.removeMem(this);
		dstMem.addMem(this);
		mem = dstMem;
//		movedTo(machine, dstMem);
		enqueueAllAtoms();
	}
	/** 膜の追加 */
	protected void addMem(AbstractMembrane mem) {
		mems.add(mem);
	}
	/** 移動された後、アクティブアトムを実行スタックに入れるために呼び出される */
//	protected void movedTo(AbstractMachine machine, AbstractMembrane dstMem) {
	abstract protected void enqueueAllAtoms();

	// TODO $pの移動のためのメソッドの仕様を選ぶ
	// 案1：remove/pourで自由リンク管理アトムを追加・削除
	// 案2：自由リンク管理アトム追加・削除のための専用メソッドの追加
	
	///////////////////
	// 案1
	/**
	 * この膜を親膜から除去する。
	 * その際、この膜の自由リンクを管理している自由リンク管理アトムを
	 * baseMem内のoutside_proxyまで削除する。
	 * @throws RuntimeException この膜にあるinside_proxyと、対応するbaseMemのoutside_proxyの間に
	 * 					自由リンク管理アトム以外のアトムが接続していた場合
	 */
	void remove(AbstractMembrane baseMem) {
		remove();
		Iterator it = atomIteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			removeProxyAtoms((Atom)it.next(), baseMem);
		}
	}
	/**
	 * inside0が管理しているリンクと同じリンクを管理する自由リンク管理アトムを、
	 * baseMem内のoutside_proxyまで削除する。
	 * @throws RuntimeException inside0とbaseMemのoutside_proxyの間に
	 * 					自由リンク管理アトム以外のアトムが接続していた場合
	 */
	protected void removeProxyAtoms(Atom inside0, AbstractMembrane baseMem) {
		Atom inside;
		Atom outside = inside0.args[0].getAtom();
		outside.remove();
		AbstractMembrane current = mem;
		try {
			while (current != baseMem) {
				if (current == null) {
					//TODO SystemError用の例外クラスを投げる
					throw new RuntimeException("SYSTEM ERROR: baseMem is not ancester");
				}
				//curentの自由リンクを管理するproxyを除去
				inside = outside.args[1].getAtom();
				outside = inside.args[0].getAtom();
				inside.remove();
				outside.remove();
				current = current.mem;
			}
		} catch (IndexOutOfBoundsException e) {
			//途中で自由リンク管理アトム以外のアトムに接続していた場合に発生する可能性がある
			//TODO SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: inconsistent proxy");
		}
		if (outside.getMem() != current) {
			//TODO SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: inconsistent proxy");
		}
		//TODO relinkAtomArgsの引数条件を確認
		current.relinkAtomArgs(inside0, 0, outside, 1);
	}
	
	/**
	 * srcMemの内容を全て移動する。
	 * その際、baseMemまでの膜に自由リンク管理アトムを追加する。
	 */
	void pour(AbstractMembrane srcMem, AbstractMembrane baseMem) {
		pour(srcMem);
		Iterator it = atomIteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			addProxyAtoms((Atom)it.next(), baseMem);
		}
	}

	protected void addProxyAtoms(Atom inside0, AbstractMembrane baseMem) {
		AbstractMembrane m = mem;
		Atom inside;
		Atom outside = m.newAtom(Functor.OUTSIDE_PROXY);
		m.newLink(inside0, 0, outside, 0);
		while (m != baseMem) {
			inside = m.newAtom(Functor.INSIDE_PROXY);
			m.newLink(outside, 1, inside, 1);
			m = m.mem;
			outside = m.newAtom(Functor.OUTSIDE_PROXY);
			m.newLink(inside, 0, outside, 0);
		}
		m.relinkAtomArgs(outside, 1, inside0, 0);
	}
	// 案1ここまで
	//////////////////////
    
	/**
	 * srcMemの内容を全て移動する。
	 * 案1ではおそらく不要
	 */
	void pour(AbstractMembrane srcMem) {
		atoms.addAll(srcMem.atoms);
		mems.addAll(srcMem.mems);
	}

	
	/** 指定されたアトムをこの膜から除去する。 */
	void removeAtom(Atom atom) {
		atoms.remove(atom);
//		atomCount--;
//		if (atomCount < 0) {
//			Util.systemError("Membrane.atomCount is pisitive value");
//		}
	}
	/** 指定された膜をこの膜から除去する */
	void removeMem(AbstractMembrane mem) {
		mems.remove(mem);
	}
	void remove() {
		mem.removeMem(this);
		mem = null;
	}
	
	/**
	 * この膜をロックする
	 * @param mem ルールのある膜
	 * @return ロックに成功した場合はtrue
	 */
	boolean lock(AbstractMembrane mem) {
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
		return false;
	}
	
//	/** この膜の複製を生成する */
//	Membrane copy() {
//		
//	}
	
	/** ロックを解除する */
	void unlock() {
		
	}
	void recursiveUnlock() {
	}
	
	///////////////////////
	// リンクの操作
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数を接続する。
	 * 接続するアトムは、
	 * <ol><li>この膜のアトム同士
	 *     <li>この膜のproxy_outと親膜のproxy_in
	 *     <li>この膜のproxy_inと子膜のproxy_out
	 * </ol>
	 * の3通りの場合がある。
	 */
	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数のリンク先を接続する。
	 */
	void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].set(atom2.args[pos2]);
		atom2.args[pos2].set(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数のリンク先と、atom2の第pos2引数のリンク先を接続する。
	 */
	void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].set(atom2.args[pos2]);
		atom2.args[pos2].set(atom1.args[pos1]);
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
	private Membrane(AbstractMachine machine, AbstractMembrane mem) {
		super(machine, mem);
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
		if (this.isQueued()) {
			return;
		}
		if (!isRoot()) {
			((Membrane)mem).activate();
		}
		((Machine)machine).memStack.push(this);
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

package runtime;

import java.util.*;

import util.*;
import runtime.stack.Stack;
import runtime.stack.QueuedEntity;

/**
 * ローカルアトムクラス。実行時の、自計算ノード内にあるアトムを表す。
 * @author Mizuno
 */
final class Atom extends AbstractAtom {
	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * 親膜の状態は未定。実際に使用する前に親膜をmem変数に明示的に代入する必要がある。
	 * @param name アトムの名前
	 * @param arity リンク数
	 */
	Atom(String name, int arity) {
		super(name, arity);
	}
//	/**
//	 * 指定された名前とリンク数を持つアトムを作成し、指定された親膜を設定する。
//	 * @param mem 所属膜
//	 * @param name アトムの名前
//	 * @param arity リンク数
//	 */
//	Atom(Membrane mem, String name, int arity) {
//		super((AbstractMembrane)mem, name, arity);
//	}
}

/**
 * 抽象アトムクラス。ローカルアトムクラスとアトムキャッシュ（未実装）の親クラス
 * @author Mizuno
 */
class AbstractAtom extends QueuedEntity {
	/** 所属膜 */
	AbstractMembrane mem;
	/** 名前 */
	private Functor functor;
	/** リンク */
	Link[] args;
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * 親膜の状態は未定。実際に使用する前に親膜をmem変数に明示的に代入する必要がある。
	 * @param name アトムの名前
	 * @param arity リンク数
	 */
	AbstractAtom(String name, int arity) {
		functor = new Functor(name, arity);
		mem = null;
	}
//	/**
//	 * 指定された名前とリンク数を持つアトムを作成し、指定された親膜を設定する。
//	 * @param mem 所属膜
//	 * @param name アトムの名前
//	 * @param arity リンク数
//	 */
//	AbstractAtom(AbstractMembrane mem, String name, int arity) {
//		functor = new Functor(name, arity);
//		this.mem = mem;
//	}

	///////////////////////////////
	// 情報の取得

	/** 名前の取得 */
	Functor getFunctor(){
		return functor;
	}
	/** リンク数を取得 */
	int getArity() {
		return functor.getArity();
	}
}

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * @author Mizuno
 */
final class Membrane extends AbstractMembrane {
	/**
	 * 指定されたマシンに所属する膜を作成する。
	 */
	Membrane(AbstractMachine machine) {
		super(machine);
	}
}
/**
 * 抽象膜クラス。ローカル膜クラスと膜キャッシュ（未実装）の親クラス
 * @author Mizuno
 */
class AbstractMembrane extends QueuedEntity {
	/** この膜を管理するマシン */
	private AbstractMachine machine;
	/** 親膜 */
	private AbstractMembrane mem;
	/** アトムの集合 */
	private AtomSet atoms;
	/** 子膜の集合 */
	private Set mems;
	/** この膜にあるproxy以外のアトムの数。 */
	private int natom;
	/** 実行スタック */
	private Stack ready;
	/** ルールセットの集合。 */
	private List rulesets;
	/** この膜以下に適用できるルールが無いときにtrue */
	boolean stable;
	/** ロックされている時にtrue */
	private boolean locked = false;
//	/** 最後にロックした計算ノード */
//	private CalcNode lastLockNode;

	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定されたマシンに所属する膜を作成する。
	 */
	AbstractMembrane(AbstractMachine machine) {
		this.machine = machine;
	}

	///////////////////////////////
	// 情報の取得

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
		return natom;
	}
	/** この膜にルールがあればtrue */
	boolean hasRule() {
		return rulesets.size() > 0;
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
	Iterator atomIteratorOfFuncor(Functor functor) {
		return atoms.iteratorOfFunctor(functor);
	}
	/** この膜にあるルールセットの反復子を返す */
	Iterator rulesetIterator() {
		return rulesets.iterator();
	}


	///////////////////////////////
	// 操作

	/** 実行スタックの先頭のアトムを取得し、実行スタックから除去 */
	AbstractAtom popReadyAtom() {
		return (AbstractAtom)ready.pop();
	}

	/** 膜の活性化 */
	void activate() {
		if (this.isQueued()) {
			return;
		}
		mem.activate();
		machine.memStack.push(this);
	}
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
	/** アトムの追加。アクティブアトムの場合には実行スタックに追加する。 */
	void addAtom(AbstractAtom atom) {
		atoms.add(atom);
		if (true) { //アクティブの場合
			ready.push(atom);
		}
	}
	/** 膜の追加 */
	void addMem(AbstractMembrane mem) {
		mems.add(mem);
	}
	/** dstMemに移動 */
	void moveTo(AbstractMembrane dstMem) {
		mem.removeMem(this);
		dstMem.addMem(this);
		mem = dstMem;
		movedTo(machine, dstMem); //?
	}
	/** 移動された後、アクティブアトムを実行スタックに入れるために呼び出される */
	private void movedTo(Machine machine, AbstractMembrane dstMem) {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (true) { // f がアクティブの場合
				ready.pushAll((Set)atoms.getAtomsOfFunctor(f));
			}
		}
	}
	/** srcMemの内容を全て移動する */
	void pour(AbstractMembrane srcMem) {
		atoms.addAll(srcMem.atoms);
		mems.addAll(srcMem.mems);
		//ルールの移動？
	}
	
	/** 指定されたアトムをこの膜から除去する。 */
	void removeAtom(AbstractAtom atom) {
		atoms.remove(atom);
	}
	/** 指定された膜をこの膜から除去する */
	void removeMem(AbstractMembrane mem) {
		mems.remove(mem);
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
	
}


/**
 * リンク<br>
 * リンクの接続先を、アトムと引数番号の組として表す。LMNtalのリンクには方向が無いため、
 * １つのリンクに対してこのクラスのインスタンスが２つ使われることになる。
 */
final class Link {
	/** リンク先のアトム */
	private AbstractAtom atom;
	/** リンク先が第何引数か */
	private int pos;
	
	///////////////////////////////
	// コンストラクタ
	
	Link(AbstractAtom atom, int pos) {
		set(atom, pos);
	}

	///////////////////////////////
	// 情報の取得
		
	/** リンク先のアトムを取得する */
	AbstractAtom getAtom() {
		return atom;
	}
	/** リンク先の引数番号を取得する */
	int getPos() {
		return pos;
	}
	/** このリンクと対をなす逆向きのリンクを取得する */
	Link getBuddy() {
		return atom.args[pos];
	}
	/** リンク先が最終リンクの場合にtrueを返す */
	boolean isFuncRef() {
		return atom.getArity() == pos;
	}

	///////////////////////////////
	// 操作
	
	/** 接続先を設定する */
	void set(AbstractAtom atom, int pos) {
		this.atom = atom;
		this.pos = pos;
	}
	/** このリンクのリンク先を、linkのリンク先に設定する */
	void relink(Link link) {
		this.atom = link.atom;
		this.pos = link.pos;
	}
}

final class Machine extends AbstractMachine {
	/** 実行膜スタック */
	Stack memStack;
	/** ルート膜 */
	private Membrane root;
	void exec() {
		
	}
}
abstract class AbstractMachine {
}

/** 計算ノード */
final class LMNtalRuntime {
	List machines;
	void exec() {
		Atom a = new Atom("a", 1);
		a.dequeue();
	}
	Machine newMachine() {
		return new Machine();
	}
}

/** なぜかこれが無いとjavadocを作成できない */
class DataStructure {}

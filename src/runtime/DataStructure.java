package runtime;

import java.util.*;

import util.*;
import runtime.stack.Stack;
import runtime.stack.QueuedEntity;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
class Atom extends QueuedEntity {
	/** 親膜。MembraneクラスのaddAtomメソッド内で更新する。 */
	private AbstractMembrane mem;
	/** 名前 */
	private Functor functor;
	/** リンク */
	Link[] args;
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * 親膜の状態は未定。実際に使用する前に親膜をmem変数に明示的に代入する必要がある。
	 * @param mem 親膜
	 * @param name アトムの名前
	 * @param arity リンク数
	 */
	Atom(AbstractMembrane mem, String name, int arity) {
		this.mem = mem;
		functor = new Functor(name, arity);
		args = new Link[arity];
	}

	///////////////////////////////
	// 情報の取得

	/** 名前の取得 */
	Functor getFunctor(){
		return functor;
	}
	String getName() {
		return functor.getName();
	}
	public String toString() {
		return functor.getName();
	}
	/** リンク数を取得 */
	int getArity() {
		return functor.getArity();
	}
	Link getLastArg() {
		return args[getArity() - 1];
	}
	AbstractMembrane getMem() {
		return mem;
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
	 */
	Membrane(AbstractMachine machine, AbstractMembrane mem) {
		super(machine, mem);
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
	protected void activateAtom(Atom atom) {
		ready.push(atom);
	}
	/** 
	 * 移動された後、アクティブアトムを実行スタックに入れるために呼び出される。
	 * AbstractMembraneクラスで宣言されている抽象メソッドの実装です。
	 */
//	protected void movedTo(AbstractMachine machine, AbstractMembrane dstMem) {
	protected void activateAllAtoms() {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (true) { // f がアクティブの場合
				ready.pushAll((Set)atoms.getAtomsOfFunctor(f));
			}
		}
	}
}
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
	protected int natom = 0;
	/** ルールセットの集合。 */
	protected List rulesets = new ArrayList();
	/** この膜以下に適用できるルールが無いときにtrue */
	boolean stable = false;
	/** ロックされている時にtrue */
	protected boolean locked = false;
//	/** 最後にロックした計算ノード */
//	protected CalcNode lastLockNode;

	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定されたマシンに所属する膜を作成する。
	 */
	AbstractMembrane(AbstractMachine machine, AbstractMembrane mem) {
		this.machine = machine;
		this.mem = mem;
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
	Iterator atomIteratorOfFuncor(Functor functor) {
		return atoms.iteratorOfFunctor(functor);
	}
	/** この膜にあるルールセットの反復子を返す */
	Iterator rulesetIterator() {
		return rulesets.iterator();
	}


	///////////////////////////////
	// 操作

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
	void addAtom(Atom atom) {
		atoms.add(atom);
		activateAtom(atom);
	}
	abstract protected void activateAtom(Atom atom);
	
	/** 膜の追加 */
	void addMem(AbstractMembrane mem) {
		mems.add(mem);
	}
	/** dstMemに移動 */
	void moveTo(AbstractMembrane dstMem) {
		mem.removeMem(this);
		dstMem.addMem(this);
		mem = dstMem;
//		movedTo(machine, dstMem);
		activateAllAtoms();
	}
	/** 移動された後、アクティブアトムを実行スタックに入れるために呼び出される */
//	protected void movedTo(AbstractMachine machine, AbstractMembrane dstMem) {
	abstract protected void activateAllAtoms();
	
	/** srcMemの内容を全て移動する */
	void pour(AbstractMembrane srcMem) {
		atoms.addAll(srcMem.atoms);
		mems.addAll(srcMem.mems);
		//ルールの移動？
	}
	
	/** 指定されたアトムをこの膜から除去する。 */
	void removeAtom(Atom atom) {
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
	/**
	 * atom1の第pos1引数と、atom2の第2引数を接続する。
	 */
	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数と、atom2の第2引数のリンク先を接続する。
	 */
	void relinkAtomArg(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].set(atom2.args[pos2]);
		atom2.args[pos2].set(atom1, pos1);
	}
	/**
	 * atom1の第pos1引数のリンク先と、atom2の第2引数のリンク先を接続する。
	 */
	void unifyLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].set(atom2.args[pos2]);
		atom2.args[pos2].set(atom1.args[pos1]);
	}
}


/**
 * リンクの接続先を、アトムと引数番号の組として表す。LMNtalのリンクには方向が無いので、
 * １つのリンクに対してこのクラスのインスタンスを２つ使用する。
 */
final class Link {
	/** リンク先のアトム */
	private Atom atom;
	/** リンク先が第何引数か */
	private int pos;

	private static int lastId = 0;
	private int id;
	///////////////////////////////
	// コンストラクタ
	
	Link(Atom atom, int pos) {
		set(atom, pos);
		id = lastId++;
	}

	///////////////////////////////
	// 情報の取得

	public String toString() {
		int i;
		if (this.id < atom.args[pos].id) {
			i = this.id;
		} else {
			i = atom.args[pos].id;
		}
		return "_" + i;
	}
				
	/** リンク先のアトムを取得する */
	Atom getAtom() {
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
		return atom.getArity() - 1 == pos;
	}

	///////////////////////////////
	// 操作
	/**
	 * 接続先を設定する。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Atom atom, int pos) {
		this.atom = atom;
		this.pos = pos;
	}
	/**
	 * このリンクの接続先を、与えられたリンクの接続先と同じにする。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Link link) {
		this.atom = link.atom;
		this.pos = link.pos;
	}
}

final class Machine extends AbstractMachine {
	/** 実行膜スタック */
	Stack memStack = new Stack();
	Machine() {
		root = new Membrane(this, null);
		memStack.push(root);
	}
	
	void exec() {
		
	}
}
abstract class AbstractMachine {
	/** ルート膜 */
	protected AbstractMembrane root;
	/** ルート膜の取得 */
	AbstractMembrane getRoot() {
		return root;
	}
}

/** 計算ノード */
final class LMNtalRuntime {
	List machines = new ArrayList();
	void exec() {
	}
	Machine newMachine() {
		return new Machine();
	}
}

/** なぜかこれが無いとjavadocを作成できない */
class DataStructure {}

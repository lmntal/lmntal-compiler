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
	
	private static int lastId = 0;
	private int id;
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * AbstractMembraneのnewAtomメソッド内で呼ばれる。
	 * @param mem 親膜
	 */
	Atom(AbstractMembrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		args = new Link[functor.getArity()];
		id = lastId++;
	}

	///////////////////////////////
	// 情報の取得

	public String toString() {
		return functor.getName();
	}
	/**
	 * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
	 * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
	 */
	public int hashCode() {
		return id;
	}
	/** 名前の取得 */
	Functor getFunctor(){
		return functor;
	}
	String getName() {
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
	
	void remove() {
		mem.removeAtom(this);
		mem = null;
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
	protected int atomCount = 0;
	/** このセルの自由リンクの数 */
	protected int freeLinkCount = 0;
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
		return atomCount;
	}
	/** このセルの自由リンクの数を取得 */
	int getFreeLinkCount() {
		return freeLinkCount;
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
		atomCount++;
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
// 	/** アトムの追加。アクティブアトムの場合には実行スタックに追加する。 */
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
	 * その際、baseMemまでの膜にある自由リンク管理アトムを除去する。
	 */
	void remove(Membrane baseMem) {
		remove();
		Iterator it = atomIteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = ((Atom)it.next()).args[0].getAtom();
			Atom inside;
			while (outside.getMem() != baseMem) {
				inside = outside.args[1].getAtom();
				outside = inside.args[0].getAtom();
				inside.remove();
				outside.remove();
			}
		}
	}
	
	/**
	 * srcMemの内容を全て移動する。
	 * その際、baseMemまでの膜に自由リンク管理アトムを追加する。
	 */
	void pour(AbstractMembrane srcMem, Membrane baseMem) {
		pour(srcMem);
		Iterator it = atomIteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			AbstractMembrane m = mem;
			Atom inside = (Atom)it.next();
			Atom outside = m.newAtom(Functor.OUTSIDE_PROXY);
			m.newLink(inside, 0, outside, 0);
			while (m != baseMem) {
				inside = m.newAtom(Functor.INSIDE_PROXY);
				m.newLink(outside, 1, inside, 1);
				m = m.mem;
				outside = m.newAtom(Functor.OUTSIDE_PROXY);
				m.newLink(inside, 0, outside, 0);
			}
		}
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
		atomCount--;
		if (atomCount < 0) {
			Util.systemError("Membrane.atomCount is pisitive value");
		}
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

	/** 対になる２つのリンクのidのうち、若い方をリンクの番号として使用する。 */
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
	boolean idle;
	static final int maxLoop = 10;
	
	Machine() {
		root = new Membrane(this);
		memStack.push(root);
		idle = false;
	}
	
	boolean isIdle(){
		return idle;
	}
	void exec() {
		if(memStack.isEmpty()){ // 空ならidleにする。
			idle = true;
			return;
		}
		// 実行膜スタックが空でない
		Membrane mem = (Membrane)memStack.peek();
		if(!mem.lock(mem)) return; // ロック失敗
		
		Atom a;
		for(int i=0; i < maxLoop && mem == memStack.peek(); i++){
			// 本膜が変わらない間 & ループ回数を越えない間
			
			a = mem.popReadyAtom();
			Iterator it = mem.rulesetIterator();
			boolean flag;
			if(a != null){ // 実行膜スタックが空でないとき
				flag = false;
				while(it.hasNext()){ // 本膜のもつルールをaに適用
					if(((Ruleset)it.next()).react(mem, a)) flag = true;
				}
				if(flag == false){ // ルールが適用できなかった時
					if(!mem.isRoot()) mem.getMem().enqueueAtom(a);
				}
				else {}// システムコールアトムなら親膜につみ、親膜を活性化
			}else{ // 実行膜スタックが空の時
				flag = false;
				while(it.hasNext()){ // 膜主導テストを行う
					if(((Ruleset)it.next()).react(mem)) flag = true;
				}
				if(flag == false){ // ルールが適用できなかった時
					memStack.pop(); // 本膜をpop
					// 本膜がroot膜かつ親膜を持つなら、親膜を活性化
					if(mem.isRoot() && mem.getMem() != null)
						((Membrane)mem.getMem()).activate();
					it = mem.memIterator();
					// 子膜が全てstableなら、この膜をstableにする。
					flag = false;
					while(it.hasNext()){
						if(((Membrane)it.next()).isStable() == false)
								flag = true;
					}
					if(flag == false) mem.toStable();
				}
			}
		}
		// 本膜が変わったor指定回数繰り返したら、ロックを解放して終了
		mem.unlock();
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
	
	/** 計算ノードが持つマシン全てがidleになるまで実行。<br>
	 *  machinesに積まれた順に実行する。親マシン優先にするためには
	 *  マシンが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */
	void exec() {
		boolean allIdle;
		Iterator it;
		Machine m;
		do{
			allIdle = true; // idleでないマシンが見つかったらfalseになる。
			it = machines.iterator();
			while(it.hasNext()){
				m = (Machine)it.next();
				if(!m.isIdle()){ // idleでないマシンがあったら
					m.exec(); // ひとしきり実行
					allIdle = false; // idleでないマシンがある
					break;
				}
			}
		}while(!allIdle);
	}
	
	Machine newMachine() {
		Machine m = new Machine();
		machines.add(m);
		return m;
	}
}

/** なぜかこれが無いとjavadocを作成できない */
class DataStructure {}

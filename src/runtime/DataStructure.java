package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.QueuedEntity;
import util.Stack;

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

package runtime;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import util.QueuedEntity;
import util.RandomIterator;

/**
 * ■ ホストをまたがる自由リンクの同一性の解決法
 * [1] 親ホストが、自由リンク出力管理アトムのグローバルIDを付ける方法
 *  - newFreeLink命令を作る方法
 *      命令の種類が増える
 *  - newFreeLink命令を作らない場合
 *      newAtom時に毎回functorを検査→遅い？
 * [2] 親ホストが、自由リンク出力管理アトムのグローバルIDを付けない方法
 *  - コミット時に新規アトムの仮IDを破棄する場合
 *    作成した自由リンク出力管理アトムの識別ができなくなる→NG
 *  - コミット時に新規アトムの仮IDを破棄しない場合
 *    <strike>次回のキャッシュ更新時に</strike>
 *      <ins>命令ブロックに対する返答として</ins>
 *      仮IDを正しいグローバルIDに変更するメッセージを送信する→遅いけど正しい
 *      * 問題：このメッセージをどうやって処理するのか？
 *        NEW_からアトムへのマップを保持しなければならない（今はない）→作る
 *        ただし実際には$inだけ登録しておけば十分。
 * 
 * 最初の結論：newFreeLink命令は廃止。
 *
 * 新しい膜を作った場合も、同じように命令ブロックに対する返答として返信する。
 *
 * 問題は、newmemは親膜が発行するので返答を受け取れるが、
 * newatom $in の場合、子膜が発行するので受け取れないという点。
 *  - 3ホストにまたがる場合に問題が発生する
 *   → newFreeLink命令が欲しい？
 *
 * ■ ホストをまたがる子膜参照の同一性の解決法
 * [1] 親ホストが、子膜のグローバルIDを付ける方法
 *  - NEWMEM/NEWROOT分散命令の引数で渡す
 *  - 子膜側でIDの管理に余計な手間がかかる→あまりよくない
 * 
 * [2] 親ホストが、子膜のグローバルIDを付けない方法
 *  - コミット時に新規子膜の仮IDを廃棄する場合はNG
 *  - コミット時に新規子膜の仮IDを廃棄しない場合、
 *    命令ブロックに対する返答として
 *    仮IDを正しいグローバルIDに変更するメッセージを送信する
 */


/**
 * 抽象膜クラス。ローカル膜クラスおよびリモート膜クラスの親クラス
 * @author Mizuno, n-kato
 */
abstract public class AbstractMembrane extends QueuedEntity {
	/** この膜を管理するタスク */
	protected AbstractTask task;
	/** 親膜。リモートにあるならばRemoteMembraneオブジェクトを参照する。GlobalRootならばnull */
	protected AbstractMembrane parent;
	/** アトムの集合 */
	protected AtomSet atoms = new AtomSet();
	/** 子膜の集合 */
	protected Set mems = new RandomSet();
//	/** このセルの自由リンクの数 */
//	protected int freeLinkCount = 0;
	/** ルールセットの集合。 */
	protected List rulesets = new ArrayList();
	/** trueならばこの膜以下に適用できるルールが無い */
	protected boolean stable = false;
	/** 永続フラグ（trueならばルール適用できなくてもstableにならない）*/
	public boolean perpetual = false;
	/** ロックされている時にtrue */
	protected boolean locked = false;
	/** リモート（リモート膜のときにボディ命令メソッド呼び出しの転送先となるリモートタスクまたはnull）
	 * <p>ロック期間中のみ有効。ロック取得時に設定され、ロック解放時にnullに設定される。
	 * ロックを必要とするランタイムのみが使用し、転送するランタイムは使用しない。*/
	public RemoteTask remote = null; // publicは仮
	/** この膜の名前（internされた文字列またはnull） */
	String name;

	private static int nextId = 0;
	private int id;

	public String getName() { return name; }
	void setName(String name) { this.name = name; } // 仕様が固まったらコンストラクタで渡すようにすべきかも
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定されたタスクに所属する膜を作成する。
	 */
	protected AbstractMembrane(AbstractTask task, AbstractMembrane parent) {
		this.task = task;
		this.parent = parent;
		id = nextId++;
		
		if(Env.gui!=null) {
			Rectangle r = Env.gui.lmnPanel.getGraphLayout().getAtomsBound();
			rect = new Rectangle2D.Double(Math.random()*r.width + r.x, Math.random()*r.height + r.y, 10.0, 10.0);
//			System.out.println(rect);
		}
	}
//	/**
//	 * 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。
//	 */
//	Membrane(Task task) {
//		super(task, null);
//	}

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
	public String getLocalID() {  //publicなのはLMNtalDaemonから呼んでいるから→呼ばなくなったのでprotectedでよい
		return Integer.toString(id);
	}
	/** この膜のグローバルIDを取得する */
	public abstract String getGlobalMemID();
	/** この膜が所属する計算ノードにおける、この膜の指定されたアトムのIDを取得する */
	public abstract String getAtomID(Atom atom);
	
	//
	
	/** この膜を管理するタスクの取得 */
	public AbstractTask getTask() {
		return task;
	}
	/** 親膜の取得 */
	public AbstractMembrane getParent() {
		return parent;
	}
	public int getMemCount() {
		return mems.size();
	}
	/** proxy以外のアトムの数を取得
	 * todo この名前でいいのかどうか */
	public int getAtomCount() {
		return atoms.getNormalAtomCount();
	}
	/** このセルの自由リンクの数を取得 */
	public int getFreeLinkCount() {
		return atoms.getAtomCountOfFunctor(Functor.INSIDE_PROXY);
	}
	/** この膜とその子孫に適用できるルールがない場合にtrue */
	boolean isStable() {
		return stable;
	}
	/** stableフラグをONにする 10/26矢島 Task#exec()内で使う*/
	void toStable(){
		stable = true;
	}
	/** 永続フラグをONにする */
	public void makePerpetual() {
		perpetual = true;
	}
//	/** 永続フラグをOFFにする */
//	public void makeNotPerpetual() {
//		AbstractLMNtalRuntime machine = getTask().getMachine();
//		synchronized(machine) {
//			perpetual = false;
//			machine.notify();
//		}
//	}
	/** この膜にルールがあればtrue */
	public boolean hasRules() {
		return rulesets.size() > 0;
	}
	public boolean isRoot() {
		return task.getRoot() == this;
	}
	
	// 反復子
	
	public Object[] getAtomArray() {
		return atoms.toArray();
	}
	public Object[] getMemArray() {
		return mems.toArray();
	}
	/** この膜にあるアトムの反復子を取得する */
	public Iterator atomIterator() {
		return atoms.iterator();
	}
	/** この膜にある子膜の反復子を取得する */
	public Iterator memIterator() {
		return mems.iterator();
	}
	/** 名前funcを持つアトムの反復子を取得する */
	public Iterator atomIteratorOfFunctor(Functor functor) {
		return atoms.iteratorOfFunctor(functor);
	}
	/** この膜にあるルールセットの反復子を返す */
	public Iterator rulesetIterator() {
		if (Env.shuffle >= Env.SHUFFLE_RULES) {
			return new RandomIterator(rulesets);
		} else {
			return rulesets.iterator();
		}
	}

	///////////////////////////////
	// ボディ操作（RemoteMembraneではオーバーライドされる）

	// ボディ操作1 - ルールの操作
	
	/** ルールを全て消去する */
	public void clearRules() {
		rulesets.clear();
	}
	/** srcMemにあるルールをこの膜にコピーする。 */
	public void copyRulesFrom(AbstractMembrane srcMem) {
		rulesets.addAll(srcMem.rulesets);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		if(rulesets.contains(srcRuleset)) return;
		rulesets.add(srcRuleset);
	}

	// ボディ操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加する。*/
	public Atom newAtom(Functor functor) {
		Atom atom = new Atom(this, functor);
		onAddAtom(atom);
		return atom;
	}
	/** （所属膜を持たない）アトムをこの膜に追加する。*/
	public void addAtom(Atom atom) {
		atom.mem = this;
		onAddAtom(atom);
	}
	/** 指定されたアトムの名前を変える */
	public void alterAtomFunctor(Atom atom, Functor func) {
		atoms.remove(atom);
		atom.setFunctor(func);
		atoms.add(atom);
	}

	/** 指定されたアトムをこの膜の実行アトムスタックに積む。
	 * すでにスタックに積まれている場合の動作は未定義とする。*/
	public abstract void enqueueAtom(Atom atom);
	/** この膜が移動された後、アクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p>Ruby版ではmovedTo(task,dstMem)を再帰呼び出ししていたが、
	 * キューし直すべきかどうかの判断の手間が掛かりすぎるため子孫の膜に対する処理は廃止された。 */
	public abstract void enqueueAllAtoms();

	/** 指定されたアトムをこの膜から除去する。
	 * <strike>実行アトムスタックに入っている場合、スタックから取り除く。</strike>*/
	public void removeAtom(Atom atom) {
		if(Env.fGUI) {
			Env.gui.lmnPanel.getGraphLayout().removedAtomPos.add(atom.getPosition());
		}
		atoms.remove(atom);
		atom.mem = null;
	}
	
	// 以下は AbstractMembrane の final メソッド
	
	/** [final] 1引数のnewAtomを呼び出すマクロ */
	final Atom newAtom(String name, int arity) {
		return newAtom(new Functor(name, arity));
	}	
	/** [final] この膜にアトムを追加するための内部命令 */
	protected final void onAddAtom(Atom atom) {
		atoms.add(atom);
//		if (atom.getFunctor().isActive()) {
//			enqueueAtom(atom);
//		}
	}
	/** [final] removeAtomを呼び出すマクロ */
	final void removeAtoms(List atomlist) {
		// atoms.removeAll(atomlist);
		Iterator it = atomlist.iterator();
		while (it.hasNext()) {
			removeAtom((Atom)it.next());
		}
	}

//	/** 
//	 * この膜にあるアトムatomがこの計算ノードが実行するタスクにある膜の実行アトムスタック内にあれば、除去する。
//	 * 他の計算ノードが実行するタスクにある膜の実行アトムスタック内のとき（システムコール）は、この膜は
//	 * ロックされていないので何もしないでよいが、その場合は実行アトムスタック内にないので既に対応できている。*/
//	public final void dequeueAtom(Atom atom) {
//		if (atom.isQueued()) {
//			atom.dequeue();
//		}
//	}

	// ボディ操作3 - 子膜の操作
	
	/** 新しい子膜を作成し、活性化する */
	public abstract AbstractMembrane newMem();
	/** [final] 指定された（親膜の無い）膜をこの膜の子膜として追加する。
	 * 実行膜スタックは操作しない。子膜のタスクについては何もしない。*/
	public final void addMem(AbstractMembrane mem) {
		mems.add(mem);
		mem.parent = this;
	}
	/** 指定された子膜をこの膜から除去する。
	 * <strike>実行膜スタックは操作しない。</strike>
	 * 実行膜スタックに積まれていれば取り除く。 */
	public void removeMem(AbstractMembrane mem) {
		mems.remove(mem);
		mem.dequeue();
		mem.parent = null;
	}
	/** 指定されたノードで実行されるロックされたルート膜を作成し、この膜の子膜にし、活性化する。
	 * @param nodedesc ノード名を表す文字列
	 * @return 作成されたルート膜 */
	public AbstractMembrane newRoot(String nodedesc) {
		if(Env.debug > 0)System.out.println("AbstraceMembrane.newRoot(" + nodedesc + ")");
		// ↓TODO (効率改善)connectRuntimeはガードですでに呼ばれているので冗長かもしれないのを何とかする？
		AbstractLMNtalRuntime machine = LMNtalRuntimeManager.connectRuntime(nodedesc);
		return machine.newTask(this).getRoot();
	}
	
	// ボディ操作4 - リンクの操作
	
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数を接続する。
	 * 接続するアトムは、
	 * <ol><li>この膜のアトム同士
	 *     <li>この膜のoutside_proxy（atom1）と子膜のinside_proxy（atom2）
	 * </ol>
	 * の2通りに限られる。
	 * <p>
	 * <b>注意</b>　
	 * newLinkはRuby版では片方向ずつリンクを生成する命令であったが、
	 * Java版では両方向を一度に生成するように仕様が変更されている。
	 */
	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);
	}
	/** atom1の第pos1引数と、atom2の第pos2引数のリンク先を接続する。
	 * 実行後、atom2の第pos2引数は廃棄しなければならない。
	 * <p><font color=red><b>
	 * cloneは使わないくてよいはず。ただし当面はデバッグを容易にするためこのままでよい。
	 * </b></font>
	 */
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/** atom1の第pos1引数のリンク先と、atom2の第pos2引数のリンク先を接続する。*/
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}
	
	// 拡張
	
	/**@deprecated*/
	public void relink(Atom atom1, int pos1, Atom atom2, int pos2) {
		relinkAtomArgs(atom1, pos1, atom2, pos2);
	}
	/** link1の指すアトム引数とlink2の指すアトム引数の間に、双方向のリンクを張る。
	 * <p>実行後link1およびlink2自身は無効なリンクオブジェクトになるため、参照を使用してはならない。*/
	public void unifyLinkBuddies(Link link1, Link link2) {
		link1.getBuddy().set(link2);
		link2.getBuddy().set(link1);
	}
	/** atom1の第pos1引数と、リンクlink2のリンク先を接続する。
	 * <p>link2は再利用されるため、実行後link2の参照を使用してはならない。*/
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		link2.getBuddy().set(atom1, pos1);
		atom1.args[pos1] = link2;
	}

	// 以下は AbstractMembrane の final メソッド
	
	/** [final] atom2の第pos2引数に格納されたリンクオブジェクトへの参照を取得する。*/
	public final Link getAtomArg(Atom atom2, int pos2) {
		return atom2.args[pos2];
	}	
	/** [final] relinkAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。*/
	protected final void relinkLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/** [final] unifyAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。*/
	protected final void unifyLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}

	// ボディ操作5 - 膜自身や移動に関する操作
	
	/** 活性化する。
	 * <p>すでにスタックに積まれていれば何もしない。
	 * スタックに積まれていないならば、<dl>
	 * <dt><b>ルート膜の場合</b>:<dd>
	 * このタスクの仮の実行膜スタックの唯一の要素として積む。
	 * <dt><b>ルート膜でない場合</b>:<dd>
	 * 親膜を活性化した後、親膜と同じスタックに入れる。
	 * すなわち、仮の実行膜スタックが空でなければ仮の実行膜スタックに積み、
	 * 空ならば実行膜スタックに積む。
	 * </dl>*/
	public abstract void activate();

//	/** この膜を親膜から除去する
//	 * @deprecated */
//	public void remove() {
//		parent.removeMem(this);
//	}

	/** 除去された膜srcMemにある全てのアトムおよび膜をこの膜に移動する。
	 * 膜srcMemの子孫のうちルート膜の手前までの全ての膜を、この膜と同じタスクの管理にする。
	 * srcMemはこのメソッド実行後、このまま廃棄しなければならない。
	 */
	public void moveCellsFrom(AbstractMembrane srcMem) {
		if (this == srcMem) return;
		if (srcMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site process fusion not implemented");
		}
		mems.addAll(srcMem.mems);
		Iterator it = srcMem.atomIterator();
		while (it.hasNext()) {
			addAtom((Atom)it.next());
		}
		it = srcMem.memIterator();
		while (it.hasNext()) {
			((AbstractMembrane)it.next()).parent = this;
		}
		if (srcMem.task != task) {
			srcMem.setTask(task);
		}
	}
	
//	/** この膜の複製を生成する */
//	Membrane copy() {
//		
//	}
	
	/** この膜をdstMemに移動し、活性化する。parent==nullを仮定する。*/
	public void moveTo(AbstractMembrane dstMem) {
		if (parent != null) {
			System.out.println("Warning: membrane with parent was moved");
			parent.removeMem(this);
		} 
		dstMem.addMem(this);
		if (dstMem.task != task) {
			setTask(dstMem.task);
		}
//		activate();
		//enqueueAllAtoms();
	}
	
	/** この膜とその子孫を管理するタスクを更新するために呼ばれる内部命令 */
	private void setTask(AbstractTask newTask) {
		if (isRoot()) return;
		task = newTask;
		Iterator it = memIterator();
		while (it.hasNext()) {
			((AbstractMembrane)it.next()).setTask(newTask);
		}
	}
//	/** この膜（ルート膜）の親膜を変更する。LocalLMNtalRuntime（計算ノード）のみが呼ぶことができる。
//	 * <p>いずれ、
//	 * AbstractMembrane#newRootおよびAbstractMachine#newTaskの引数に親膜を渡すようにし、
//	 * AbstractMembrane#moveToを使って親膜を変更することにより、
//	 * todo この問題のあるメソッドは廃止しなければならない */
//	void setParent(AbstractMembrane mem) {
//		if (!isRoot()) {
//			throw new RuntimeException("setParent requires this be a root membrane");
//		}
//		parent = mem;
//	}

	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	// - ガード命令
	
	/**
	 * この膜のロック取得を試みる。
	 * <p>ルールスレッドがこの膜のロックを取得するときに使用する。
	 * <p>ロック解放にはunlock()を使用する。
	 * @return ロックの取得に成功したかどうか */
	public abstract boolean lock();
	/**
	 * この膜のロック取得を試みる。
	 * 失敗した場合、この膜を管理するタスクのルールスレッドに停止要求を送る。その後、
	 * このタスクがシグナルを発行するのを待ってから、再びロック取得を試みることを繰り返す。
	 * <p>ルールスレッド以外のスレッドがこの膜のロックを取得するときに使用する。
	 * <p>ロック解放にはunlock()を使用する。
	 * <p>ローカル膜の場合、ロック取得に成功するまで戻らない。
	 * @return ロックの取得に成功したかどうか */
	public abstract boolean blockingLock();
	/**
	 * この膜からこの膜を管理するタスクのルート膜までの全ての膜のロックをブロッキングで取得し、
	 * 実行膜スタックから除去する。ルート膜ならばblockingLock()と同じになる。
	 * <p>ルールスレッド以外のスレッドが最初のロックとしてこの膜のロックを取得するときに使用する。
	 * <p>ロック解放にはasyncUnlock()を使用する。
	 * <p>ローカル膜の場合、ロック取得に成功するまで戻らない。
	 * @return ロックの取得に成功したかどうか */
	public abstract boolean asyncLock();

	/** このロックした膜の全ての子孫の膜のロックを再帰的にブロッキングで取得する。
	 * キャッシュは更新しない。
	 * <p>ロック解放にはrecursiveUnlock()を使用する。
	 * @return ロックの取得に成功したかどうか */
	public abstract boolean recursiveLock();
	
	// - ボディ命令
	
	/**
	 * 取得したこの膜のロックを解放する。ルート膜の場合、
	 * 仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。
	 * <p>ただしこの膜がリモート膜の場合は仮解放するだけで何もしないかも知れない。
	 * <p><strike>todo unlock は weakUnlock に名称変更する</strike> */
	public abstract void unlock();
	
	/** この膜のロックを強制的に解放する。リモート膜の場合も仮解放しない。
	 * ローカル膜の場合はunlock()と同じ。*/
	public abstract void forceUnlock();

	/** この膜からこの膜を管理するタスクのルート膜までの全ての膜の取得したロックを解放し、この膜を活性化する。
	 * 仮の実行膜スタックの内容を実行膜スタックに転送する。ルート膜の場合はunlock()と同じになる。
	 * <p>ルールスレッド以外のスレッドが最初に取得した膜のロックを解放するときに使用する。*/
	public abstract void asyncUnlock();

	/** 取得したこの膜の全ての子孫の膜のロックを再帰的に解放する。*/
	public abstract void recursiveUnlock();

	///////////////////////////////
	// 自由リンク管理アトムの張り替えをするための操作（RemoteMembraneはオーバーライドしない）
	//
	// TODO （効率改善）starをキューで管理することにより、alterAtomFunctorの回数を減らすとよいかも知れない。
	// キューはLinkedListオブジェクトとし、react内を生存期間とし、star関連のメソッドの引数に渡される。
	// $pを含む全ての膜の本膜からの相対関係がルール適用で不変な場合、
	// $pの先祖の全ての膜をうまく再利用することによって、star関連の処理を全く呼ぶ必要がなくなる。
	
	// todo （効率改善）LinkedListオブジェクトに対してcontainsを呼んでいるのを何とかする
	
	/** この膜がremoveされた直後に呼ばれる。
	 * なおremoveProxiesは、ルール左辺に書かれたアトムを除去した後、
	 * ルール左辺に書かれた膜のうち$pを持つものに対して内側の膜から呼ばれる。
	 * <p>この膜に対して
	 * <ol>
	 * <li>この膜の自由/局所リンクでないにもかかわらずこの膜内を通過しているリンクを除去し（例:V=A）
	 * <li>この膜の自由リンクが出現するアトムの名前をstarに変える。
	 * </ol>
	 * <p>すべてのremoveProxiesの呼び出しが終了すると
	 * <ul>
	 * <li>$pにマッチしたプロセスの自由リンクは$pが書かれた膜のstarアトムに出現するようになり、
	 * <li>starアトムのリンク先は、starアトムまたは本膜のoutside_proxyの第1引数になっている。
	 * </ul>
	 * <pre>
	 * ( {{$p},$q},{$r} :- ... )
	 *     {{a(i(A),i(X))},  b(i(B),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(B)=o(W)
	 * --> *{a(s(A),s(X))}; {b(i(B),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(B)=o(W)
	 * -->  {a(s(A),s(X))};*{b(s(B),s(X))          }; {d(i(W))}, c(o(A)),o(B)=o(W)
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X))          };*{d(s(W))}; c(o(A)),o(B)=o(W)
	 * 
	 * ( {$p} :- ... )
	 *      {a(i(X))}, b(o(X))
	 * --> *{a(s(X))}; b(o(X))
	 * </pre>
	 */
	public void removeProxies() {
		// NOTE atomsへの操作が必要になるので、Setのクローンを取得してその反復子を使った方が
		//      読みやすい＆効率が良いかもしれない ←リモートの場合に適用するのは難しいかも知れない
		LinkedList changeList = new LinkedList();	// star化するアトムのリスト
		LinkedList removeList = new LinkedList();
		Iterator it = atoms.iteratorOfFunctor(Functor.OUTSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			Atom a0 = outside.args[0].getAtom();
			// outsideのリンク先が子膜でない場合【この検査のためにremoveで parent=null; が必要】			
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
	 * <p>この膜を（2つのoutside経由で）通過して無関係な膜に入っていくリンクを除去する。
	 * <pre>
	 *      {a(s(A),s(X))}; {b(s(B),s(X))          }; {d(s(W))}; c(o(A)),o(B)=o(W)
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X))          }; {d(s(B))}; c(o(A))
	 * 
	 *      {a(s(X))}; b(o(X))
	 * -->  {a(s(X))}; b(o(X))
	 * </pre>
	 */
	public void removeToplevelProxies() {
		ArrayList removeList = new ArrayList();
		Iterator it = atoms.iteratorOfFunctor(Functor.OUTSIDE_PROXY);
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			// outsideの第1引数のリンク先が子膜でない場合			
			if (outside.args[0].getAtom().getMem().getParent() != this) {
				// outsideの第2引数のリンク先がoutsideの場合
				Atom a1 = outside.args[1].getAtom();
				if (a1.getFunctor().equals(Functor.OUTSIDE_PROXY)) {
					// 2つめのoutsideの第1引数のリンク先が子膜でない場合
					if (a1.args[0].getAtom().getMem().getParent() != this) {
						if (!removeList.contains(outside)) {
							unifyLocalAtomArgs(outside, 0, a1, 0);
							removeList.add(outside);
							removeList.add(a1);
						}
					}
				}
			}
		}
		removeAtoms(removeList);
	}
	/** 右辺の（引数に指定した子膜セルの）膜構造および$pの内容を配置した後で、
	 * ルール右辺に書かれた膜と本膜に対して内側の膜から呼ばれる。
	 * <p>子膜childMemWithStarにあるstarアトム（子膜の自由リンクがつながっている）に対して
	 * <ol>
	 * <li>名前をinside_proxyに変え
	 * <li>自由リンクの反対側のアトムの出現する膜の位置にしたがって次のように場合分けをする：
	 *   <ul>
	 *   <li>この膜のstarアトムならば、
	 *       後者の名前をoutside_proxyに変える（最初のstarアトムと対応させる）。
	 *       また、分散実行のために、このリンクを張りなおす。（例:X,Y）
	 *   <li>この膜（本膜）に残ったoutside_proxyアトムならば、何もしない。
	 *       これはリンクがルールの左辺にマッチしたプロセスの自由リンクのときに起こる。（例:V）
	 *   <li>同じ子膜にある（star）アトムならば、2つのstarを削除する。（例:B,C）（追加 2004/1/18）
	 *   <li>それ以外の膜にあるアトムならば、
	 *       自由リンクがこの膜を通過するようにする。（例:A->V,E->W）
	 *       このとき、この膜に作成するoutside_proxyでない方のアトムの名前はstarにする。
	 *   </ul>
	 * </ol>
	 * @param childMemWithStar （自由リンクを持つ）子膜
	 * <pre>
	 * ( ... :- {{$p},$q,$r} )
	 *      {a(s(A),s(X))}; {b(s(B),s(X)),         }; {d(s(B))}; c(o(A))
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X)),             d(s(B))}, c(o(A))
	 * -->{*{a(i(A),i(X))},  b(s(B),o(X)),s(V)=o(A),   d(s(B))}, c(o(V))
	 * -->*{{a(i(A),i(X))},  b(  B ,o(X)),i(V)=o(A),   d(  B )}, c(o(V))
	 * 
	 * ( ... :- {{$p},$q},{$r} )
	 *      {a(s(A),s(X))}; {b(s(Y),s(X)),         }; {d(s(E))}; c(o(A))
	 * -->  {a(s(A),s(X))}; {b(s(Y),s(X)),         },*{d(i(W))}, c(o(A)),s(E)=o(W)
	 * -->{*{a(i(A),i(X))},  b(s(Y),o(X)),s(V)=o(A)}, {d(i(W))}, c(o(V)),s(E)=o(W)
	 * -->*{{a(i(A),i(X))},  b(i(Y),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(Y)=o(W)
	 * 
	 * ( ... :- {$p,$q,$r} )
	 *      {a(s(V),s(B))}; {b(s(C),s(B)),         }; {d(s(C))}; c(o(V))
	 * -->  {a(s(V),s(B)),   b(s(C),s(B)),             d(s(C))}, c(o(V))
	 * -->  {a(s(V),  B ),   b(  C ,  B ),             d(  C )}, c(o(V))
	 *
	 * ( ... :- {$p} )
	 *      {a(s(V))}; b(o(V))
	 * -->  {a(i(V))}, b(o(V))
	 * </pre>
	 */
	public void insertProxies(AbstractMembrane childMemWithStar) {
		LinkedList changeList = new LinkedList();	// inside_proxy化するアトムのリスト
		LinkedList removeList = new LinkedList();
		Iterator it = childMemWithStar.atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next(); // n
			Atom oldstar = star.args[0].getAtom();
			if (oldstar.getMem() == childMemWithStar) { // 膜内の新しい局所リンクの場合
				if (!removeList.contains(star)) {
					childMemWithStar.unifyAtomArgs(star,1,oldstar,1);
					removeList.add(star);
					removeList.add(oldstar);
				}
			} else {
				changeList.add(star);
				// 自由リンクの反対側の出現がこの膜のアトムならば、後者の名前をoutside_proxyに変える。
				// このときstarが消えるかもしれないので、starをキューで実装するときはバグに注意。
				if (oldstar.getMem() == this) {
					//changeList.add(star);
					alterAtomFunctor(oldstar, Functor.OUTSIDE_PROXY);
					newLink(oldstar, 0, star, 0);
				} else {
					Atom outside = newAtom(Functor.OUTSIDE_PROXY); // o
					Atom newstar = newAtom(Functor.STAR); // m
					newLink(newstar, 1, outside, 1);
					relinkAtomArgs(newstar, 0, star, 0); // これによりstar[0]が無効になる
					newLink(star, 0, outside, 0);
				}
			}
		}
		it = changeList.iterator();
		while (it.hasNext()) {
			childMemWithStar.alterAtomFunctor((Atom)it.next(), Functor.INSIDE_PROXY);
		}
		childMemWithStar.removeAtoms(removeList);		
	}
	/** 右辺のトップレベルに$pがあるルールの実行時、最後に本膜に残ったstarを処理するために呼ばれる。
	 * <p>この膜にあるstarに対して、
	 * 反対側の出現であるoutside_proxyまたはstarとともに除去し第2引数同士を直結する。
	 * このうち前者は、リンクがルールの左辺にマッチしたプロセスの自由リンクのときに起こる。（例:V）
	 * <pre>
	 * ( ... :-  $p,$q,$r  )
	 *      {a(s(V),s(B))}; {b(s(C),s(B)),         }; {d(s(C))}; c(o(V))
	 * -->   a(s(V),s(B)),   b(s(C),s(B)),             d(s(C)),  c(o(V))
	 * -->   a(  V ,  B ),   b(  C ,  B ),             d(  C ),  c(  V )
	 * 
	 * ( ... :- $p )
	 *      {a(s(V))}; b(o(V))
	 * -->   a(s(V)),  b(o(V))
	 * -->   a(  V ),  b(  V )
	 * </pre>
	 */
	public void removeTemporaryProxies() {
		LinkedList removeList = new LinkedList();
		Iterator it = atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next();
			Atom outside = star.args[0].getAtom();
			if (!removeList.contains(star)) {
				unifyLocalAtomArgs(star,1,outside,1);
				removeList.add(star);
				removeList.add(outside);
			}
		}
		removeAtoms(removeList);
	}
	
	/**
	 * {} なしで出力する。
	 * 
	 * // ルールの出力の際、{} アリだと
	 * // (a:-b) が ({a}:-{b}) になっちゃうから。
	 * 
	 * @return String 
	 * @deprecated
	 */
	public String toStringWithoutBrace() {
		return Dumper.dump(this);		
	}
	
	public String toString() {
		return "{ " + toStringWithoutBrace() + " }";
	}
	
	/* *** *** *** *** *** BEGIN GUI *** *** *** *** *** */
	public java.awt.geom.Rectangle2D.Double rect;
	
	
	
	/* *** *** *** *** *** END GUI *** *** *** *** *** */
}

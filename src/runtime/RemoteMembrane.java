package runtime;

//import java.util.HashMap;
import java.util.Iterator;

//import daemon.IDConverter;

/**
 * リモート膜クラス
 * @author n-kato
 */
public final class RemoteMembrane extends AbstractMembrane {
	/** この膜のグローバルIDまたはNEW_n（ルート膜でない場合、親膜のロック取得時のみ有効）*/
	protected String globalid;
	
//	/** この膜のアトムからリモートIDまたはNEW_nへの写像 (Atom -> String) */
//	protected HashMap atomids = new HashMap();
//	/** この膜の子膜からリモートIDへの写像 (AbstractMembrane -> String) */
//	protected HashMap memids = new HashMap();

	/** 仮ロック状態かどうか。簡単のため、ルート膜のみが仮ロック状態になれるものとする。
	 * （リモートではロックされていると見なし、ローカルではロック解放されているとみなす状態のこと）*/
	private boolean fUnlockDeferred = false;
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * コンストラクタ。globalidは初期化しない（困った話）
	 */
	public RemoteMembrane(RemoteTask task, AbstractMembrane parent) {
		super(task, parent);
	}
	/**
	 * コンストラクタ。remoteidは明示的に渡す。
	 */
	public RemoteMembrane(RemoteTask task, RemoteMembrane parent, String remoteid) {
		super(task, parent);
		this.globalid = remoteid;
	}
	/**
	 * 親膜を持たない膜を作成する。RemoteLMNtalRuntime.createPseudoMembrane から呼ばれる。
	 * @see RemoteLMNtalRuntime#createPseudoMembrane() */
	protected RemoteMembrane(RemoteTask task) {
		super(task, null);
	}
	
	///////////////////////////////
	// 情報の取得

	public String getGlobalMemID() {
		return globalid;
	}
	public String getAtomID(Atom atom) {
		//return (String) atomids.get(atom);
		return atom.remoteid;
	}

	///////////////////////////////
	// ボディ操作

	// ボディ操作1 - ルールの操作

	public void clearRules() {
		task.remote.send("CLEARRULES",this);
		super.clearRules();
	}

	public void copyRulesFrom(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset) it.next();
			task.remote.send("LOADRULESET", this, rs.getGlobalRulesetID());
		}
		super.copyRulesFrom(srcMem);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		task.remote.send("LOADRULESET", this, srcRuleset.getGlobalRulesetID());
		super.loadRuleset(srcRuleset);
	}

	// ボディ操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行アトムスタックに入れる。 */
	public Atom newAtom(Functor func) {
		Atom atom = super.newAtom(func);
		String atomid = task.remote.generateNewID();
		//atomids.put(atom,atomid);
		atom.remoteid = atomid;
		task.remote.send("NEWATOM", atomid, this, func.serialize());
		return atom;
	}
	public void alterAtomFunctor(Atom atom, Functor func) {
		task.remote.send("ALTERATOMFUNCTOR", this, getAtomID(atom) + " " + func.serialize());
		super.alterAtomFunctor(atom, func);
	}
	public void removeAtom(Atom atom) {
		task.remote.send("REMOVEATOM", this, getAtomID(atom));
		super.removeAtom(atom);
	}
	/** 指定されたアトムをこの膜の実行アトムスタックに積む */
	public void enqueueAtom(Atom atom) {
		String atomid = getAtomID(atom);
		if (atomid != null) { // AbstractMembrane#addAtomからの呼び出しは無視する
			task.remote.send("ENQUEUEATOM", this, atomid);
		}
	}
	/** リモートのmoveCellsFromで行われるため何もしなくてよい */
	public void enqueueAllAtoms() {
	}

	// ボディ操作3 - 子膜の操作

	/** 新しい子膜を作成する */
	public AbstractMembrane newMem() {
		String newglobalid = task.remote.generateNewID();
		RemoteMembrane submem = new RemoteMembrane((RemoteTask)task, this, newglobalid);
		//memids.put(submem.globalid, newglobalid);
		mems.add(submem);
		task.remote.send("NEWMEM", newglobalid, this);
		//task.registerMem(newglobalid, submem.globalid);//←転送先が行うのであるから不要
		return submem;
	}

	public void removeMem(AbstractMembrane mem) {
		task.remote.send("REMOVEMEM", this, mem.getGlobalMemID());
		super.removeMem(mem);
	}

	// ボディ操作4 - リンクの操作

	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		task.remote.send("NEWLINK", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.newLink(atom1, pos1, atom2, pos2);
	}
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		task.remote.send("RELINKATOMARGS", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.relinkAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		task.remote.send("INHERITLINK", this,
			getAtomID(atom1), pos1,
			getAtomID(link2.getAtom()), link2.getPos());
		super.inheritLink(atom1, pos1, link2);
	}
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		task.remote.send("UNIFYATOMARGS", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.unifyAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void unifyLinkBuddies(Link link1, Link link2) {
		task.remote.send("UNIFYLINKBUDDIES", this,
			getAtomID(link1.getAtom()), link1.getPos(),
			getAtomID(link2.getAtom()), link2.getPos());
		super.unifyLinkBuddies(link1, link2);
	}

	// ボディ操作5 - 膜自身や移動に関する操作

	public void activate() {
		task.remote.send("ACTIVATE",this);
	}

	public void moveCellsFrom(AbstractMembrane srcMem) {
		//todo 実装
		
		if (srcMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process fusion not implemented");
		}
		task.remote.send("MOVECELLSFROM", this, srcMem.getGlobalMemID());
	}

	/** dstMemに移動 */
	public void moveTo(AbstractMembrane dstMem) {
		//todo 実装
		
		if (dstMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process migration not implemented");
		}
		// remote call of a local process migration
		task.remote.send("MOVETO", this, dstMem.getGlobalMemID());
		super.moveTo(dstMem);
	}

	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	// - ガード命令
	
	synchronized public boolean lock() {
		if (locked) return false;
		if (fUnlockDeferred) {
			fUnlockDeferred = false;
		} else {
			if (!doLock("LOCK")) return false;
		}
		onLock(false);
		return true;
	}
	public boolean blockingLock() {
		//todo:locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
		// [ロック解放要求の処理方法]
		// * 仮ロックされた本膜のロックを解放してもらうときに必要。
		// ルールスレッドがロックしていた場合、タスクのIDをリモートに渡す？【todo 本当に必要か？】
		// 非ルールスレッドがロックしていた場合、タスクは存在しないが、
		// 優先度無限大と見なすのでロックは解放できないことになっているので大丈夫。
		if (!doLock("BLOCKINGLOCK")) return false;
		onLock(false);
		return true;	}
	/** 非同期的にロックする。ルート膜の親膜を活性化するときなどに使用される。
	 * 先祖膜のキャッシュは更新しない。*/
	public boolean asyncLock() {
		if (!doLock("ASYNCLOCK")) return false;
		onLock(true);
		return true;
	}
	public boolean recursiveLock() {
		return sendWait("RECURSIVELOCK");
	}
	
	// - ボディ命令
	
	public void unlock() {
		if (false && isRoot() && task.remote.cmdbuffer.length() == 0) {
			fUnlockDeferred = true;
		}
		else {
			task.remote.send("UNLOCK",this);
			onUnlock(false);
		}
	}
	public void forceUnlock() {
		task.remote.send("UNLOCK",this);
		onUnlock(false);
	}
	public void asyncUnlock() {
		task.remote.send("ASYNCUNLOCK",this);
		onUnlock(true);
	}
	public void recursiveUnlock() {
		task.remote.send("RECURSIVEUNLOCK",this);
	}

	///////////////////////////////	
	// RemoteMembrane で定義されるメソッド
	
	private boolean doLock(String cmd) {
		Object obj = sendWaitObject(cmd);
		if (obj instanceof byte[]) { // ロック取得成功
			updateCache((byte[])obj);
		}
		else if (obj instanceof String) {
			String response = (String)obj;
			if (response.equalsIgnoreCase("UNCHANGED")) {
				// ロック取得成功
			} else { // ロック取得失敗
				return false;
			}
		}
		return true;
	}

	private void onLock(boolean signal) {
		locked = true;
		if (signal || isRoot()) {
			if (parent == null || parent.task.remote == null) {
				task.remote = (RemoteTask)task;
				task.remote.init();							// 命令ブロックの積み上げを開始する
			}
		}
	}
	private void onUnlock(boolean signal) {
		if (signal || isRoot()) {
			if (task.remote == task) task.remote.flush();	// 命令ブロックの積み上げを終了する
			task.remote = null;
		}
	}
	/** キャッシュを更新する
	 * @see Membrane#cache() */
	protected void updateCache(byte[] data) {
		// TODO 【実装】（有志A）2/2
		
		// アトム      ->
		// 子膜        -> daemon.IDConverter.registerGlobalMembrane()；自由リンクを接続
		// ルールセット -> 直ちに現物を取得（ストリームを占有するとこれができないので困ったね）

	}

	///////////////////////////////
	// 送信用
	
	/** メッセージを直接送信し、返答を待ってブロックする。（仮）*/
	boolean sendWait(String cmd) {
		String host = task.runtime.hostname;
		String msg = cmd + " " + getGlobalMemID();
		return LMNtalRuntimeManager.daemon.sendWait(host,msg);
	}
	/** メッセージを直接送信し、返答を待ってブロックする。（仮）*/
	Object sendWaitObject(String cmd) {
		String host = task.runtime.hostname;
		String msg = cmd + " " + getGlobalMemID();
		return LMNtalRuntimeManager.daemon.sendWaitObject(host,msg);
	}
	
}
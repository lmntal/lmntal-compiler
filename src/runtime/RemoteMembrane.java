package runtime;

import java.util.HashMap;
import java.util.Iterator;

import daemon.IDConverter;

/**
 * リモート膜クラス
 * @author n-kato
 */
final class RemoteMembrane extends AbstractMembrane {
	/** この膜を管理する計算ノードにおけるこの膜のID */
	protected String remoteid;
	/** この膜のアトムのローカルIDからリモートIDへの写像 */
	protected HashMap atomids = new HashMap();
	/** この膜の子膜のローカルIDからリモートIDへの写像 */
	protected HashMap memids = new HashMap();

	/** 仮ロック状態かどうか。
	 * （リモートではロックされていると見なし、ローカルではロック解放されているとみなす状態のこと）*/
	protected boolean fUnlockDeferred = false;

	/*
	 * コンストラクタ。remoteidはLMNtalDaemon.getGlobalMembraneIDによって生成される。
	 */
	public RemoteMembrane(RemoteTask task, RemoteMembrane parent) {
		super(task, parent);
//		this.remoteid = LMNtalDaemon.getGlobalMembraneID(this);
		this.remoteid = IDConverter.getGlobalMembraneID(this);
	}

	/*
	 * コンストラクタ。remoteidは明示的に渡す。
	 */
	public RemoteMembrane(
		RemoteTask task,
		RemoteMembrane parent,
		String remoteid) {
		super(task, parent);
		this.remoteid = remoteid;
	}

/*
 * コマンドを送信する。
 * 
 * （注）send()の引数の個数を増やす場合は、LMNtalDaemonMessageProcessor.run()のBEGIN処理部分の配列commandInsideBeginの要素数を増やす事。
 *      20040712現在はString[4]でベタ打ち。
 */
	void send(String cmd) {
		((RemoteTask) task).send(cmd + " " + remoteid);
	}
	void send(String cmd, String args) {
		((RemoteTask) task).send(cmd + " " + remoteid + " " + args);
	}
	void send(String cmd, String arg1, String arg2) {
		((RemoteTask) task).send(
			cmd + " " + remoteid + " " + arg1 + " " + arg2);
	}
	void send(String cmd, String arg1, String arg2, String arg3, String arg4) {
		((RemoteTask) task).send(
			cmd
				+ " "
				+ remoteid
				+ " "
				+ arg1
				+ " "
				+ arg2
				+ " "
				+ arg3
				+ " "
				+ arg4);
	}
	///////////////////////////////
	// 情報の取得

	String getMemID() {
		return remoteid;
	}
	String getAtomID(Atom atom) {
		return (String) atomids.get(atom);
	}

	///////////////////////////////
	// 操作

	// 操作1 - ルールの操作

	public void clearRules() {
		send("CLEARRULES");
		super.clearRules();
	}

	public void copyRulesFrom(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset) it.next();
			send("LOADRULESET", rs.getGlobalRulesetID());
		}
		super.copyRulesFrom(srcMem);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		send("LOADRULESET", srcRuleset.getGlobalRulesetID());
		super.loadRuleset(srcRuleset);
	}

	// 操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行アトムスタックに入れる。 */
	public Atom newAtom(Functor functor) {
		Atom a = super.newAtom(functor);
		String atomid = ((RemoteTask) task).getNextAtomID(); //NEW_1とかが入ってしまう
		atomids.put(a, atomid);
		send("NEWATOM", atomid);
		return a;
	}
	public void alterAtomFunctor(Atom atom, Functor func) {
		send("ALTERATOMFUNCTOR", getAtomID(atom), func.getName());
		// getNameでは、正確な転送は期待できない
		super.alterAtomFunctor(atom, func);
	}
	public void removeAtom(Atom atom) {
		send("REMOVEATOM", getAtomID(atom));
		super.removeAtom(atom);
	}
	/** 指定されたアトムをこの膜の実行アトムスタックに積む */
	public void enqueueAtom(Atom atom) {
		// TODO リモートのアトムを積む場合があるが、実装可能かどうか調べる
		//String atomid = getAtomID(atom);
		//if (atomid != null) { // AbstractMembrane#addAtomからの呼び出しは無視する
		//	send("ENQUEUEATOM",atomid);
		//}
	}
	/** リモートのmoveCellsFromで行われるため何もしなくてよい */
	public void enqueueAllAtoms() {
	}

	// 操作3 - 子膜の操作

	/** 新しい子膜を作成する */
	public AbstractMembrane newMem() {
		//String newremoteid = ((RemoteTask)task).getNextMemID();
		//RemoteMembrane m = new RemoteMembrane((RemoteTask)task, this, newremoteid);
		//m.remoteid = newremoteid;
		//mems.add(m);
		//send("NEWMEM",newremoteid);
		//return m;

		String newremoteid = ((RemoteTask) task).getNextMemID();
		RemoteMembrane m = new RemoteMembrane((RemoteTask) task, this);
		memids.put(m.remoteid, newremoteid);
		mems.add(m);
		send("NEWMEM", newremoteid);
		((RemoteTask) task).registerMem(newremoteid, m.remoteid);

		return m;
	}

	public void removeMem(AbstractMembrane mem) {
		send("REMOVEMEM", mem.getMemID());
		super.removeMem(mem);
	}

	// 操作4 - リンクの操作

	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		send(
			"NEWLINK",
			""
				+ getAtomID(atom1)
				+ " "
				+ pos1
				+ " "
				+ getAtomID(atom2)
				+ " "
				+ pos2);
		super.newLink(atom1, pos1, atom2, pos2);
	}
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send(
			"RELINKATOMARGS",
			""
				+ getAtomID(atom1)
				+ " "
				+ pos1
				+ " "
				+ getAtomID(atom2)
				+ " "
				+ pos2);
		super.relinkAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		send(
			"RELINKATOMARGS",
			""
				+ getAtomID(atom1)
				+ " "
				+ pos1
				+ " "
				+ getAtomID(link2.getAtom())
				+ " "
				+ link2.getPos());
		super.inheritLink(atom1, pos1, link2);
	}
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send(
			"UNIFYATOMARGS",
			""
				+ getAtomID(atom1)
				+ " "
				+ pos1
				+ " "
				+ getAtomID(atom2)
				+ " "
				+ pos2);
		super.unifyAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void unifyLinkBuddies(Link link1, Link link2) {
		send(
			"NEWLINK",
			""
				+ getAtomID(link1.getAtom())
				+ " "
				+ link1.getPos()
				+ " "
				+ getAtomID(link2.getAtom())
				+ " "
				+ link2.getPos());
		super.unifyLinkBuddies(link1, link2);
	}

	// 操作5 - 膜自身や移動に関する操作

	public void activate() {
		send("ACTIVATE");
	}
	//	public void remove() {
	//		send("REMOVE");
	//		super.remove();
	//	}

	public void moveCellsFrom(AbstractMembrane srcMem) {
		//TODO 実装
		
		if (srcMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process fusion not implemented");
		}
//		send("POUR", srcMem.getMemID()); //名称変更、Instructionクラスに合わせる
		send("MOVECELLS", srcMem.getMemID());
	}

	/** dstMemに移動 */
	public void moveTo(AbstractMembrane dstMem) {
		//TODO 実装
		
		if (dstMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process migration not implemented");
		}
		// remote call of a local process migration
		send("MOVETO", dstMem.getMemID());
		super.moveTo(dstMem);
	}

	// 操作6 - ロックに関する操作

	synchronized public boolean lock() {
		if (locked) {
			return false;
		} else {
			if (fUnlockDeferred) {
				locked = true;
				fUnlockDeferred = false; // ?
			} else {
				send("LOCK");
				//wait();
				if (true) { // ロック取得成功
					//todo:キャッシュの更新
				} else { // ロック取得失敗
					return false;
				}
			}
			locked = true;
			return true;
		}
	}
	public void blockingLock() {
		//todo:locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
		// [ロック解放要求の処理方法]
		// * 仮ロックされた本膜のロックを解放してもらうときに必要。
		// ルールスレッドがロックしていた場合、タスクのIDをリモートに渡す？【TODO 本当に必要か？】
		// 非ルールスレッドがロックしていた場合、タスクは存在しないが、
		// 優先度無限大と見なすのでロックは解放できないことになっているので大丈夫。
		send("BLOCKINGLOCK");
		//wait;
		// todo:キャッシュの更新
	}
	public void asyncLock() {
		send("ASYNCLOCK");
	}
	public void unlock(boolean signal) {
		fUnlockDeferred = true;
		//send("");
	}
	public void forceUnlock() {
		send("UNLOCK", "" + false); // あとでよく考えること
	}
	public void asyncUnlock() {
		send("ASYNCUNLOCK");
	}
	public void recursiveLock() {
		send("RECURSIVELOCK");
	}
	public void recursiveUnlock() {
		send("RECURSIVEUNLOCK");
	}
}
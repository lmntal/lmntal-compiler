package runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import util.Stack;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * todo （効率改善） 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * @author Mizuno, n-kato
 */
public final class Membrane extends AbstractMembrane {
	/** 実行アトムスタック */
	private Stack ready = new Stack();
	
	/** リモートホストとの通信でこの膜のアトムを同定するときに使用されるatomidの表。
	 * <p>atomid (String) -> Atom
	 * <p>この膜のキャッシュ送信後、この膜の連続するロック期間中のみ有効。
	 * キャッシュ送信時に初期化され、引き続くリモートホストからの要求を解釈するために使用される。
	 * リモートホストからの要求で新しくアトムが作成されると、受信したNEW_をキーとするエントリが追加される。
	 * $inside_proxyアトムの場合、命令ブロックの返答のタイミングでローカルIDで上書きされる。
	 * $inside_proxy以外のアトムの場合、ロック解除までNEW_のまま放置される。
	 * @see Atom.remoteid */
	protected HashMap atomTable = new HashMap();

	//
	
	/** 指定されたタスクに所属する膜を作成する。newMem/newRoot から呼ばれる。*/
	private Membrane(AbstractTask task, AbstractMembrane parent) {
		super(task, parent);
		daemon.IDConverter.registerGlobalMembrane(getGlobalMemID(), this); // TODO （効率改善）free時に消す
	}
	/** 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。*/
	protected Membrane(Task task) {
		super(task, null);
	}

	public String getGlobalMemID() { return task.runtime.hostname + ":" + getLocalID(); }
	public String getAtomID(Atom atom) { return atom.getLocalID(); }

	///////////////////////////////
	// ボディ操作

	// ボディ操作1 - ルールの操作
	
//	/** ルールを全て消去する */
//	public void clearRules() {
//		if (remote == null) super.clearRules();
//		else remote.send("CLEARRULES",this);
//	}
//	/** srcMemにあるルールをこの膜にコピーする。 */
//	public void copyRulesFrom(AbstractMembrane srcMem) {
//		if (remote == null) super.copyRulesFrom(srcMem);
//		else remote.send("COPYRULESFROM",this,srcMem.getGlobalMemID());
//		// todo RemoteMembraneのやり方（最初の段階でLOADRULESETに展開する方式）に合わせる
//	}
//	/** ルールセットを追加 */
//	public void loadRuleset(Ruleset srcRuleset) {
//		if (remote == null) super.loadRuleset(srcRuleset);
//		else remote.send("LOADRULESET",this,srcRuleset.getGlobalRulesetID());
//	}

	// ボディ操作2 - アトムの操作

//	/** 新しいアトムを作成し、この膜に追加する。*/
//	public Atom newAtom(Functor functor) {
//		if (remote == null) return super.newAtom(functor);
//		else remote.send("NEWATOM",this,functor.toString());
//		return null;	// todo なんとかする（local-remote-local 問題）
//	}
//	/** （所属膜を持たない）アトムをこの膜に追加する。*/
//	public void addAtom(Atom atom) {
//		if (remote == null) super.addAtom(atom);
//		else remote.send("ADDATOM", this);
//	}
//	/** 指定されたアトムの名前を変える */
//	public void alterAtomFunctor(Atom atom, Functor func) {
//		if (remote == null) super.alterAtomFunctor(atom,func);
//		else remote.send("ALTERATOMFUNCTOR", this, atom + " " + func.serialize());
//	}

	/** 
	 * 指定されたアトムをこの膜の実行アトムスタックに追加する。
	 * @param atom 実行アトムスタックに追加するアトム
	 */
	public void enqueueAtom(Atom atom) {
		ready.push(atom);
	}
	/** この膜が移動された後、アクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p>Ruby版ではmovedTo(task,dstMem)を再帰呼び出ししていたが、
	 * キューし直すべきかどうかの判断の手間が掛かりすぎるため子孫の膜に対する処理は廃止された。 
	 * <p>
	 * <p>
	 * 移動された後、この膜のアクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。*/
	public void enqueueAllAtoms() {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (f.isActive()) {
				Iterator i2 = atoms.iteratorOfFunctor(f);
				while (i2.hasNext()) {
					Atom a = (Atom)i2.next();
					a.dequeue();
					ready.push(a);
				}
			}
		}
	}

	/** 指定されたアトムをこの膜から除去する。
	 * <strike>実行アトムスタックに入っている場合、スタックから取り除く。</strike>*/
	public void removeAtom(Atom atom) {
		if(Env.fGUI) {
			Env.gui.lmnPanel.getGraphLayout().removedAtomPos.add(atom.getPosition());
		}
		atoms.remove(atom);
		atom.mem = null;
	}

	/** この膜をdstMemに移動し、活性化する。*/
	public void moveTo(AbstractMembrane dstMem) {
		if (dstMem.task.getMachine() != task.getMachine()) {
			parent = dstMem;
			//((RemoteMembrane)dstMem).send("ADDMEM",getGlobalMemID());
			throw new RuntimeException("cross-site process migration not implemented");
		}
		super.moveTo(dstMem);
	}

	// ボディ操作3 - 子膜の操作

	/** 新しい子膜を作成し、活性化する */
	public AbstractMembrane newMem() {
//		if (remote != null) {
//			remote.send("NEWMEM",this);
//			return null; // todo
//		}
		Membrane m = new Membrane(task, this);
		mems.add(m);
		// 親膜と同じ実行膜スタックに積む
		Task t = (Task)task;
		if (t.bufferedStack.isEmpty()) {
			t.memStack.push(m);
		}
		else {
			t.bufferedStack.push(m);
		}		
		return m;
	}
	/** newMemと同じ。ただし親膜（メソッドが呼ばれたこの膜）は仮でない実行膜スタックに積まれている。
	 * <p>最適化用。しかし実際には最適化の効果は無い気がする。*/
	public AbstractMembrane newLocalMembrane() {
		Membrane m = new Membrane(task, this);
		mems.add(m);
		((Task)task).memStack.push(m);
		return m;		
	}
	
//	/** 指定された子膜をこの膜から除去する。
//	 * <strike>実行膜スタックは操作しない。</strike>
//	 * 実行膜スタックに積まれていれば取り除く。 */
//	public void removeMem(AbstractMembrane mem) {
//		if (remote == null) super.removeMem(mem);
//		else remote.send("REMOVEMEM", this, mem.getGlobalMemID());
//	}
//	/** 指定されたノードで実行されるロックされたルート膜を作成し、この膜の子膜にし、活性化する。
//	 * @param node ノード名を表す文字列
//	 * @return 作成されたルート膜
//	 */
//	public AbstractMembrane newRoot(String node) {
//		if (remote == null) return super.newRoot(node);
//		else remote.send("NEWROOT", this, node);
//		return null;	// todo なんとかする（local-remote-local 問題）
//	}
	
	// ボディ操作5 - 膜自身や移動に関する操作

	/** 膜の活性化 */
	public void activate() {
		if (isQueued()) {
			return;
		}
		Task t = (Task)task;
		if (!isRoot()) {
			((Membrane)parent).activate();
			synchronized(task) {
				if (t.bufferedStack.isEmpty()) {
					t.memStack.push(this);
				}
				else {
					t.bufferedStack.push(this);
				}
			}
		}
		else {
			// ASSERT(t.bufferedStack.isEmpty());
			t.bufferedStack.push(this);
		}
	}	
	
	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	// - ガード命令
	
	/**
	 * この膜のロック取得を試みる。
	 * <p>ルールスレッドがこの膜のロックを取得するときに使用する。
	 * <p>成功したら親膜のリモートを継承する。
	 * したがってルールスレッドは、本膜をロックした場合ただちにリモートをnullに設定すること。
	 * @return ロックの取得に成功したかどうか */
	synchronized public boolean lock() {
		if (locked) {
			return false;
		} else {
			locked = true;
			if (parent != null) remote = parent.remote;
			return true;
		}
	}
	/**
	 * この膜のロック取得を試みる。
	 * 失敗した場合、この膜を管理するタスクのルールスレッドに停止要求を送る。その後、
	 * このタスクがシグナルを発行するのを待ってから、再びロック取得を試みることを繰り返す。
	 * <p>ルールスレッド以外のスレッドが2つ目以降のロックとしてこの膜のロックを取得するときに使用する。
	 * <p>成功したら親膜のリモートを継承する。
	 * @return つねにtrue */
	public boolean blockingLock() {
		if (lock()) return true;
		synchronized(task) {
			((Task)task).requestLock();
			do {
				try {
					task.wait();
				}
				catch (InterruptedException e) {}
			}
			while (!lock());
			((Task)task).retractLock();
		}
		return true;
	}
	/**
	 * この膜からこの膜を管理するタスクのルート膜までの全ての膜のロックを取得し、実行膜スタックから除去する。
	 * <p>ルールスレッド以外のスレッドが最初のロックとしてこの膜のロックを取得するときに使用する。
	 * <p>成功したらリモートをnullに設定する。
	 * @return つねにtrue */
	public boolean asyncLock() {
		if (!isRoot()) parent.asyncLock();
		blockingLock();
		remote = null;
		if (isRoot()) {
			// task.async = new Async();
		}
		dequeue();
		return true;
	}

	/** このロックした膜の全ての子孫の膜のロックを再帰的にブロッキングで取得する。
	 * @return ロックの取得に成功したかどうか */
	public boolean recursiveLock() {
		Iterator it = memIterator();
		LinkedList lockedmems = new LinkedList();
		boolean result = true;
		while (it.hasNext()) {
			AbstractMembrane mem = (AbstractMembrane)it.next();
			if (!mem.blockingLock()) {
				result = false;
				break;
			}
			if (!mem.recursiveLock()) {
				mem.unlock();
				result = false;
				break;
			}
			lockedmems.add(mem);
		}
		if (result) return true;
		it = lockedmems.iterator();
		while (it.hasNext()) {
			AbstractMembrane mem = (AbstractMembrane)it.next();
			mem.recursiveUnlock();
			mem.unlock();
		}
		return false;
	}

	// - ボディ命令
	
	/**
	 * 取得したこの膜のロックを解放する。ルート膜の場合、
	 * 仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。
	 * <p>lockおよびblockingLockの呼び出しに対応する。asyncLockにはasyncUnlockが対応する。*/
	public void unlock() {
		Task task = (Task)getTask();
		if (isRoot()) {
			synchronized(task) {
				task.memStack.moveFrom(task.bufferedStack);
			}
			task.idle = false;
		}
		locked = false;
		if (isRoot()) {
			// このタスクのルールスレッドまたはその停止を待ってブロックしているスレッドを再開する。
			task.signal();
		}
	}
	public void forceUnlock() {
		unlock();
	}
	/** この膜からこの膜を管理するタスクのルート膜までの全ての膜の取得したロックを解放し、この膜を活性化する。
	 * 仮の実行膜スタックの内容を実行膜スタックに転送する。ルート膜の場合はunlock()と同じになる。
	 * <p>ルールスレッド以外のスレッドが最初に取得した膜のロックを解放するときに使用する。*/
	public void asyncUnlock() {
		activate();
		AbstractMembrane mem = this;
		while (!mem.isRoot()) {
			mem.locked = false;
			mem = mem.parent;
		}
		// task.async = null;
		mem.unlock();
	}
	/** 取得したこの膜の全ての子孫の膜のロックを再帰的に解放する。*/
	public void recursiveUnlock() {
		Iterator it = memIterator();
		while (it.hasNext()) {
			AbstractMembrane mem = (AbstractMembrane)it.next();
			mem.recursiveUnlock();
			mem.unlock();
		}
	}

	///////////////////////////////	
	// Membrane で定義されるメソッド
	
//	// 本膜かどうか
//	boolean isCurrent() { return getTask().memStack.peek() == this; }
	
	/** デバッグ用 */
	String getReadyStackStatus() { return ready.toString(); }

	/** 実行アトムスタックの先頭のアトムを取得し、実行アトムスタックから除去 */
	Atom popReadyAtom() {
		return (Atom)ready.pop();
	}

//	/** 膜の活性化。ただしこの膜はルート膜ではなく、スタックに積まれておらず、
//	 * しかも親膜は仮でない実行膜スタックに積まれている。
//	 * → newMem / newLocalMembrane に移動しました */
//	public void activateThis() {
//		((Task)task).memStack.push(this);
//	}

	/** この膜のキャッシュを表すバイト列を取得する。
	 * @see RemoteMembrane#updateCache(byte[]) */
	public byte[] cache() {
		
		// atomTableを更新する // 子膜の自由リンクについては要検討
		atomTable.clear();
		Iterator it = atomIterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atomTable.put(atom.getLocalID(), atom);
		}

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);

			//子膜
			out.writeInt(mems.size());
			it = memIterator();
			while (it.hasNext()) {
				AbstractMembrane m = (AbstractMembrane)it.next();
				out.writeObject(m.getTask().getMachine().hostname);
				out.writeObject(m.getLocalID());
			}
			//アトム
			out.writeInt(atoms.size());
			it = atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
				out.writeObject(a);
			}
			//ルールセット
			out.writeInt(rulesets.size());
			it = rulesetIterator();
			while (it.hasNext()) {
				Ruleset r = (Ruleset)it.next();
				out.writeObject(r.getGlobalRulesetID());
			}
			//todo nameは不要？

			out.close();
			return bout.toByteArray();
		} catch (IOException e) {
			//ByteArrayOutputStreamなので、発生するはずがない
			throw new RuntimeException("Unwxpected Exception", e);
		}
	}

	/** アトムIDに対応するアトムを取得する */
	public Atom lookupAtom(String atomid) {
		return (Atom)atomTable.get(atomid);
	}
	/** アトムIDに対応するアトムを登録する */
	public void registerAtom(String atomid, Atom atom) {
		atomTable.put(atomid, atom);
	}
}
// todo 【検証】local-remote-local 問題が解決したかどうか調べる

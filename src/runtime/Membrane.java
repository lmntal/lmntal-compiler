package runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import util.Stack;
import daemon.IDConverter;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * todo （効率改善） 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * <p><b>排他制御</b></p>
 * <p>lockThread フィールドへの代入（すなわちロックの取得・解放）と、ロック解放の待ち合わせのために
 * このクラスのインスタンスに関する synchronized 節を利用する。
 * @author Mizuno, n-kato
 */
public final class Membrane extends AbstractMembrane {
	/** 実行アトムスタック。
	 * 操作する際にこの膜のロックを取得している必要はない。
	 * 排他制御には、Stack インスタンスに関する synchronized 節を利用している。 */
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
		daemon.IDConverter.registerGlobalMembrane(getGlobalMemID(), this);
	}
	/** 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。*/
	protected Membrane(Task task) {
		super(task, null);
	}
	public Membrane() {
		super(null, null);
	}

	public String getGlobalMemID() { return (task==null ? "":task.runtime.hostname) + ":" + getLocalID(); }
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
	 * 現在このメソッドを使っている場所はない。(2005/11/30 mizuno)
	 * <p>
	 * 移動された後、この膜のアクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。*/
	public void enqueueAllAtoms() {
		Iterator i = atoms.activeFunctorIterator();
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
//
//	/** 指定されたアトムをこの膜から除去する。
//	 * <strike>実行アトムスタックに入っている場合、スタックから取り除く。</strike>*/
//	public void removeAtom(Atom atom) {
//		if(Env.fGUI) {
//			Env.gui.lmnPanel.getGraphLayout().removedAtomPos.add(atom.getPosition());
//		}
//		atoms.remove(atom);
//		atom.mem = null;
//	}

	// ボディ操作3 - 子膜の操作

	/** 新しいタイプがkの子膜を作成し、活性化する */
	public AbstractMembrane newMem(int k){
	//		if (remote != null) {
	//		remote.send("NEWMEM",this);
	//		return null; // todo
	//	}
		Membrane m = new Membrane(task, this);
		m.changeKind(k);
		mems.add(m);
		// 親膜と同じ実行膜スタックに積む
		stack.push(m);
		return m;
	}
	/** 新しいデフォルトタイプの子膜を作成し、活性化する */
	public AbstractMembrane newMem() {
		return newMem(0);
	}
	
	/** newMemと同じ。ただし親膜（メソッドが呼ばれたこの膜）は仮でない実行膜スタックに積まれている。
	 * <p>最適化用。しかし実際には最適化の効果は無い気がする。*/
	public AbstractMembrane newLocalMembrane(int k) {
		Membrane m = new Membrane(task, this);
		m.changeKind(k);
		mems.add(m);
		((Task)task).memStack.push(m);
		return m;		
	}
	public AbstractMembrane newLocalMembrane() {
		return newLocalMembrane(0);
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

	/**
	 * 膜の活性化。
	 * @inheritDoc AbstractMembrane.activate()
	 */
	public void activate() {
		if (isNondeterministic()) return;
		stable = false;
		Task t = (Task)task;
		if (isRoot()) {
			dequeue();
			t.bufferedStack.push(this);
		} else {
			Stack s = ((Membrane)parent).activate2();
			if (s == t.bufferedStack) {
				dequeue();
				s.push(this);
			} else {
				//方法7では、この膜をロックしていれば非同期にpushされている事はない。
				//方法8では、下2行をsynchronized(memStack)内に入れる。
				if (!isQueued())
					s.push(this);
			}
		}
	}
	/** 
	 * activate 内で親膜を活性化するときに利用する。
	 * すでにスタックに積まれているときは何しない。
	 * @return この要素が積まれているスタック。task.memStack か task.bufferedStack の一方。
	 */
	private Stack activate2() {
		Task t = (Task)task;
		//方法7では、この膜をロックしていれば非同期にpushされている事はない。
		//方法8では、この下を全てsynchronized(memStack)内に入れる。
		if (isQueued())
			return stack;

		if (isRoot()) {
			// ASSERT(t.bufferedStack.isEmpty());
			t.bufferedStack.push(this);
			return t.bufferedStack;
		} else {
			Stack s = ((Membrane)parent).activate2();
			s.push(this);
			return s;
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
		if (lockThread != null) {
			return false;
		} else {
			lockThread = Thread.currentThread();
			//除去された膜はロックされているので、parent==null になるのはグローバルルートのみ
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
	 * 親膜のロックはすでに取得している必要がある。
	 * @return 常にtrue */
	public boolean blockingLock() {
		//親膜のロックを取得しているので、タスクが変化したりこの膜が除去されたりする事はない。
		Task t = (Task)task;
		boolean stopped = false;
		while (true) {
			if (lockThread == t.thread) {
				//管理タスクのルールスレッドがロックしているかもしれない時は停止要求を送る
				//suspend を呼んだときはすでに解放済みかもしれないが、問題はない。
				//タスクに関する synchronized 節を利用するので、下の synchronized 節に入れる事はできない。
				t.suspend();
				stopped = true;
			}
			synchronized(this) {
				if (lock()) {
					break;
				} else {
					//非ルールスレッドや親タスクなら解放するのを待つ
					try {
						wait();
					} catch (InterruptedException e) {}
				}
			}
		}
		if (stopped)
			t.resume();
		return true;
	}
	/**
	 * この膜からこの膜を管理するタスクのルート膜までの全ての膜のロックを取得し、実行膜スタックから除去する。
	 * <p>ルールスレッド以外のスレッドが最初のロックとしてこの膜のロックを取得するときに使用する。
	 * <p>成功したらリモートをnullに設定する。
	 * @return 成功したら true。失敗するのは、この膜がすでにremoveされている場合のみ。 */
	public boolean asyncLock() {
		Task t = (Task)task;
		AbstractMembrane root = t.getRoot();;
		//blockingLock と違い、停止するまで待つ必要がある。
		t.suspend();
		synchronized(this) {
			while (true) {
				boolean ret = root.lock();
				if (parent == null || t != task) {
					//状況が変化していた場合のキャンセル処理
					if (ret) {
						root.activate();
						root.unlock();
					}
					t.resume();
	
					if (parent == null) {
						//この膜が除去された
						return false;
					} else {
						//所属タスクが変化していた
						t = (Task)task;
						root = t.getRoot();
						t.suspend();
					}
				} else if (ret) {
					//ルート膜のロックに成功。この膜からルート膜までの間を全てロックする。
					for (AbstractMembrane mem = this; mem != root; mem = mem.parent) {
						ret = mem.lock();
						if (!ret)
							throw new RuntimeException("SYSTEM ERROR : failed to asyncLock" + mem.lockThread + mem.task);
					}
					t.resume();
					return true;
				} else {
					//ルート膜のロックが解放されるのを待つ。
					//所属タスクが変化していないかを時々検査するために、タイムアウトを設定している。
					try {
						wait(1);
					} catch (InterruptedException e) {}
				}
			}
		}
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
	 * 取得したこの膜のロックを解放する。
	 * 実行膜スタックは操作しない。
	 * ルート膜の場合、この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。
	 */
	public void quietUnlock() {
		Task task = (Task)getTask();
		synchronized(this) {
			lockThread = null;
			//blockingLock でブロックしているスレッドを起こす。
			//一つ起こせば十分。
			notify();
		}
		if (isRoot()) {
			// このタスクのルールスレッドを再開する。
			// このタスク以外のスレッドは必ずルート膜からロックしているので、ルート膜のロック解放時に起こせば十分。
			// ロックを解放してからここに来るまでの間に所属タスクが変わって場合があるが、特に問題はない。
			synchronized(task) {
				// タスク以外にも、suspend メソッド中で wait しているスレッドがあるかもしれないので、全て起こす。
				task.notifyAll();
			}
		}
	}
	
	/**
	 * 取得したこの膜のロックを解放する。ルート膜の場合、
	 * 仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * この膜を管理するタスクに対してシグナルを発行する。
	 * <p>lockおよびblockingLockの呼び出しに対応する。asyncLockにはasyncUnlockが対応する。*/
	public void unlock() {
		unlock(false);
	}
	
	public void unlock(boolean changed) {
		Task task = (Task)getTask();
		if (isRoot()) {
			task.memStack.moveFrom(task.bufferedStack);
		}
		quietUnlock();
		if(changed & Env.LMNgraphic != null)
			Env.LMNgraphic.setmem(this);
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
			mem.lockThread = null;
			mem = mem.parent;
		}
		task.asyncFlag = true;
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
				out.writeObject(new Boolean(m.isRoot()));
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
			//out.writeObject(name);
			out.writeObject(new Boolean(stable));

			out.close();
			return bout.toByteArray();
		} catch (IOException e) {
			//ByteArrayOutputStreamなので、発生するはずがない
			throw new RuntimeException("Unexpected Exception", e);
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
	
	/** この膜を削除する */
	public void free() {
		IDConverter.unregisterGlobalMembrane(getGlobalMemID());
	}
	
	
	/** インライン用マクロ Any=_old/1 :- Any=_new/1 */
	public void replace1by1(Atom _old, Atom _new) {
		relink(_old, 0, _new, 0);
		removeAtom(_old);
	}
}
// todo 【検証】local-remote-local 問題が解決したかどうか調べる

package runtime;

import java.util.Iterator;
import util.Stack;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * TODO 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * @author Mizuno, n-kato
 */
public final class Membrane extends AbstractMembrane {
	/** 実行アトムスタック */
	private Stack ready = new Stack();
	
	/**
	 * 指定されたタスクに所属する膜を作成する。
	 * newMem/newRoot メソッド内で呼ばれる。
	 */
	private Membrane(AbstractTask task, AbstractMembrane parent) {
		super(task, parent);
	}
	/**
	 * 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。
	 */
	Membrane(Task task) {
		super(task, null);
	}

	String getMemID() { return getLocalID(); }
	String getAtomID(Atom atom) { return atom.getLocalID(); }
	
//	boolean isCurrent() { return getTask().memStack.peek() == this; }
	
	/** デバッグ用 */
	String getReadyStackStatus() { return ready.toString(); }

	///////////////////////////////
	// 操作

	/** 実行アトムスタックの先頭のアトムを取得し、実行アトムスタックから除去 */
	Atom popReadyAtom() {
		return (Atom)ready.pop();
	}
	/** 
	 * 指定されたアトムをこの膜の実行アトムスタックに追加する。
	 * @param atom 実行アトムスタックに追加するアトム
	 */
	public void enqueueAtom(Atom atom) {
		ready.push(atom);
	}

//	/** 膜の活性化。ただしこの膜はルート膜ではなく、スタックに積まれておらず、
//	 * しかも親膜は仮でない実行膜スタックに積まれている。
//   * → newMem / newLocalMembrane に移動しました */
//	public void activateThis() {
//		((Task)task).memStack.push(this);
//	}

	/** 膜の活性化 */
	public void activate() {
		if (isQueued()) {
			return;
		}
		Task t = (Task)task;
		if (!isRoot()) {
			((Membrane)parent).activate();
			synchronized(task.getMachine()) {
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

	/** この膜をdstMemに移動し、活性化する。*/
	public void moveTo(AbstractMembrane dstMem) {
		if (dstMem.task.getMachine() != task.getMachine()) {
			parent = dstMem;
			//((RemoteMembrane)dstMem).send("ADDMEM",getMemID());
			throw new RuntimeException("cross-site process migration not implemented");
		}
		super.moveTo(dstMem);
	}
	/** 
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
	public AbstractMembrane newMem() {
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
	/** newMemと同じ。ただし親膜（メソッドが呼ばれたこの膜）は仮でない実行膜スタックに積まれている。 */
	public AbstractMembrane newLocalMembrane() {
		Membrane m = new Membrane(task, this);
		mems.add(m);
		((Task)task).memStack.push(m);
		return m;		
	}
	public AbstractMembrane newRoot(AbstractMachine runtime) {
		AbstractTask task = runtime.newTask(this);
		return task.getRoot();
	}

	// ロック
	
	/**
	 * この膜のロック取得を試みる。
	 * <p>ルールスレッドがこの膜のロックを取得するときに使用する。
	 * @return ロックの取得に成功したかどうか */
	synchronized public boolean lock() {
		if (locked) {
			return false;
		} else {
			locked = true;
			return true;
		}
	}
	/**
	 * この膜のロック取得を試みる。
	 * 失敗した場合、この膜を管理するタスクのルールスレッドに停止要求を送る。その後、
	 * このタスクがシグナルを発行するのを待ってから、再びロック取得を試みることを繰り返す。
	 * <p>ルールスレッド以外のスレッドがこの膜のロックを取得するときに使用する。*/
	public void blockingLock() {
		if (lock()) return;
		AbstractMachine mach = task.getMachine();
		synchronized(mach) {
			((Task)task).requestLock();
			do {
				try {
					mach.wait();
				}
				catch (InterruptedException e) {}
			}
			while (!lock());
			((Task)task).retractLock();
		}
	}
	/**
	 * この膜からこの膜を管理するタスクのルート膜までの全ての膜のロックを取得し、実行膜スタックから除去する。
	 * <p>ルールスレッド以外のスレッドがこの膜のロックを取得するときに使用する。*/
	public void asyncLock() {
		if (!isRoot()) parent.asyncLock();
		blockingLock();
		dequeue();
	}

	/**
	 * 取得したこの膜のロックを解放する。
	 * ルート膜の場合またはsignal引数がtrueの場合、
	 * 仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。*/
	public void unlock(boolean signal) {
		if (isRoot()) signal = true;
		if (signal) {
			Task t = (Task)task;
			synchronized(task) {
				t.memStack.moveFrom(t.bufferedStack);
			}
			t.idle = false;
		}
		locked = false;
		if (signal) {
			// このタスクのルールスレッドまたはその停止を待ってブロックしているスレッドを再開する。
			getTask().signal();
		}
	}
	/** この膜からこの膜を管理するタスクのルート膜までの全ての膜の取得したロックを解放し、この膜を活性化する。
	 * 仮の実行膜スタックの内容を実行膜スタックに転送する。
	 * <p>ルールスレッド以外のスレッドが最初に取得した膜のロックを解放するときに使用する。*/
	public void asyncUnlock() {
		activate();
		AbstractMembrane mem = this;
		while (!mem.isRoot()) {
			mem.locked = false;
			mem = mem.parent;
		}
		mem.unlock();
	}
	
	/** この膜の全ての子孫の膜のロックを再帰的にブロッキングで取得する。*/
	public void recursiveLock() {
		Iterator it = memIterator();
		while (it.hasNext()) {
			AbstractMembrane mem = (AbstractMembrane)it.next();
			mem.blockingLock();
			mem.recursiveLock();
		}
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
}

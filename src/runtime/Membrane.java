package runtime;

import java.util.Iterator;
import util.Stack;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * TODO 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * @author Mizuno
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
	 * 指定されたアトムを実行アトムスタックに追加する。
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
			if (t.bufferedStack.isEmpty()) {
				t.memStack.push(this);
			}
			else {
				t.bufferedStack.push(this);
			}			
		}
		else {
			// ASSERT(t.bufferedStack.isEmpty());
			t.bufferedStack.push(this);
		}
	}

	/** dstMemに移動 */
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
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。
	 */
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
	 * この膜を管理するタスクのロックを取得した後、この膜のロックを取得する。
	 * <p>ルールスレッド以外のスレッドが膜のロックを取得するときに使用する。*/
	public void blockingLock() {
		((Task)task).lock();
		while (!lock()) {
			AbstractMachine mach = task.getMachine();
			synchronized(mach) {
				try {
					mach.wait();
				}
				catch (InterruptedException e) {}
			}
		}
	}

	/**
	 * この膜をロックする。
	 * <p>ルールスレッドが膜のロックをするときに使用する。
	 * @return ロックに成功した場合はtrue */
	synchronized public boolean lock() {
		if (locked) {
			return false;
		} else {
			locked = true;
			return true;
		}
	}
	/**
	 * この膜とその子孫を再帰的にロックする。
	 * todo プロセス文脈のコピーという使用目的から考えて、ブロッキングで行うべきであると思われる。
	 * @return ロックに成功した場合はtrue */
	public boolean recursiveLock() {
		// 実装する
		return false;
	}
	/** 
	 * 取得した膜のロックを解放し、この膜を管理するタスクのロックカウントが正ならば1減らす。
	 * <p>タスクのロックが解放されたかまたはルート膜の場合、さらに以下の順番で処理を行う:
	 * <ul>
	 * <li>仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * <li>タスクをアイドル状態でなくし、
	 * <li>タスクを実行する物理マシンにシグナルを発行する。
	 * </ul>*/
	public void unlock() {
		boolean signal = ( ((Task)task).unlock() || isRoot() );
		if (signal) {
			Task t = (Task)task;
			t.memStack.moveFrom(t.bufferedStack);
			((Task)task).idle = false;
		}
		locked = false;
		if (signal) {
			AbstractMachine machine = getTask().getMachine();
			synchronized(machine) {
				machine.notify();
			}
		}
	}
	public void recursiveUnlock() {
		// 実装する
	}
}


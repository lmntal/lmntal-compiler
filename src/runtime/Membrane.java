package runtime;

import java.util.Iterator;
import util.Stack;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * TODO 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * @author Mizuno
 */
public final class Membrane extends AbstractMembrane {
	/** 実行スタック */
	private Stack ready = new Stack();
	/**
	 * 指定されたタスクに所属する膜を作成する。
	 * newMem/newRoot メソッド内で呼ばれる。
	 */
	private Membrane(AbstractTask task, AbstractMembrane parent) {
		super(task, parent);
	}
	/**
	 * 親膜を持たない膜を作成し、指定されたタスクのルート膜にする。
	 */
	Membrane(Task task) {
		super(task, null);
	}

	String getMemID() { return getLocalID(); }
	String getAtomID(Atom atom) { return atom.getLocalID(); }
	
	///////////////////////////////
	// 操作

	/** 実行スタックの先頭のアトムを取得し、実行スタックから除去 */
	Atom popReadyAtom() {
		return (Atom)ready.pop();
	}
	/** 
	 * 指定されたアトムを実行スタックに追加する。
	 * @param atom 実行スタックに追加するアトム。アクティブアトムでなければならない。
	 */
	protected void enqueueAtom(Atom atom) {
		ready.push(atom);
	}
	/** 膜の活性化 */
	public void activate() {
		if (!isQueued()) {
			return;
		}
		if (!isRoot()) {
			((Membrane)parent).activate();
		}
		((Task)task).memStack.push(this);
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
	 * 移動された後、この膜のアクティブアトムを実行スタックに入れるために呼び出される。
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。
	 */
	protected void enqueueAllAtoms() {
		Iterator i = atoms.functorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (f.isActive()) {
				Iterator i2 = atoms.iteratorOfFunctor(f);
				while (i2.hasNext()) {
					Atom a = (Atom)i2.next();
					dequeueAtom(a);
					ready.push(a);
				}
			}
		}
	}
	public AbstractMembrane newMem() {
		Membrane m = new Membrane(task, this);
		mems.add(m);
		return m;
	}
	public AbstractMembrane newRoot(AbstractMachine runtime) {
		AbstractTask task = runtime.newTask();
		task.getRoot().setParent(this);
		return task.getRoot();
	}
}


package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 抽象物理マシン（抽象計算ノード）クラス */
abstract class AbstractMachine {
	protected String runtimeid;
	/** この物理マシンに親膜を持たないロックされていないルート膜を作成し、仮でない実行膜スタックに積む。*/
	abstract AbstractTask newTask();
	/** この物理マシンに指定の親膜を持つロックされたルート膜を作成し、仮の実行膜スタックに積む。*/
	abstract AbstractTask newTask(AbstractMembrane parent);
	/** この物理マシンの実行を終了する */
	abstract public void terminate();
	
	/** この計算ノードのルールスレッドに対して再実行を要求する。*/
	abstract public void awake();
}

/** 物理マシン */
class Machine extends AbstractMachine implements Runnable {
	List tasks = new ArrayList();
	protected Thread thread = new Thread(this);
	
	AbstractTask newTask() {
		Task t = new Task(this);
		tasks.add(t);
		return t;
	}
	AbstractTask newTask(AbstractMembrane parent) {
		Task t = new Task(this,parent);
		tasks.add(t);
		return t;
	}
	/** この物理マシンのルールスレッドの再実行が要求されたかどうか */
	protected boolean awakened = false;
	/** （マスター計算ノードによって）この物理マシンの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** この物理マシンのルールスレッドの再実行を要求する */
	synchronized public void awake() {
		awakened = true;
		notify();
	}
	/** この物理マシンの終了を要求する */
	synchronized public void terminate() {
		terminated = true;
		notify();
	}
	/** 物理マシンが持つタスク全てがidleになるまで実行。<br>
	 *  Tasksに積まれた順に実行する。親タスク優先にするためには
	 *  タスクが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */
	protected void localExec() {
		boolean allIdle;
		do {
			allIdle = true; // idleでないタスクが見つかったらfalseになる。
			Iterator it = tasks.iterator();
			while (it.hasNext()) {
				Task task = (Task)it.next();
				if (!task.isIdle()) { // idleでないタスクがあったら
					task.exec(); // ひとしきり実行
					allIdle = false; // idleでないタスクがある
				//	break;
				}
			}
		} while(!allIdle);
	}
	/** スレーブ計算ノードとして実行する */
	public void run() {
		while (true) {
			localExec();
			synchronized(this) {
				if (terminated) break;
				if (awakened) {
					awakened = false;
					continue;
				}
				try {
					wait();
				}
				catch (InterruptedException e) {}
			}
		}
	}
	/** この物理マシンを実行する */
	public void exec() {
		thread.start();
		try {
			thread.join();
		}
		catch (InterruptedException e) {}
	}
}

/** グローバルルートを管理する物理マシン */
public final class LMNtalRuntime extends Machine {
	protected Membrane globalRoot;
	
	public LMNtalRuntime(){
		AbstractTask t = newTask();
		globalRoot = (Membrane)t.getRoot();
		// Inline
		Inline.initInline();
	}

//	/**
//	 * １回だけ適用するルールをglobalRoot膜に適用する。
//	 * 初期化ルール、およびREPLが１行入力毎に生成するルールを適用するために使用する。
//	 * @deprecated
//	 */
//	public void applyRulesetOnce(Ruleset r){
//		r.react(globalRoot);
//	}
	
	public Membrane getGlobalRoot(){
		return globalRoot;
	}
//	/**@deprecated*/
//	public Membrane getRoot(){
//		return globalRoot;
//	}
	/** マスター計算ノードとして実行する */
	public void run() {
		RemoteMachine.init();
		while (true) {
			localExec();
			if (globalRoot.isStable()) break;
			synchronized(this) {
				try {
					if (terminated) break;
					wait();
				}
				catch (InterruptedException e) {}
			}
		}
		RemoteMachine.terminateAll();
	}
}


package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 抽象ランタイム（旧：抽象物理マシン、旧々：抽象計算ノード）クラス。*/

abstract class AbstractLMNtalRuntime {
	protected String runtimeid;
	/** このランタイムに親膜を持たないロックされていないルート膜を作成し、仮でない実行膜スタックに積む。*/
	abstract AbstractTask newTask(AbstractMembrane parent);
	/** このランタイムの実行を終了する */
	abstract public void terminate();
	
//	/** ランタイムのルールスレッドに対して再実行を要求する。*/
//	abstract public void awake();
}

/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラスのサブクラスのインスタンスは、1つの Java VM につき高々1つしか存在しない。
*/
class LocalLMNtalRuntime extends AbstractLMNtalRuntime /*implements Runnable */{
	List tasks = new ArrayList();
//	protected Thread thread = new Thread(this);
	

	AbstractTask newTask(AbstractMembrane parent) {
		Task t = new Task(this,parent);
		tasks.add(t);
		return t;
	}
	/** （マスタタスクによって）このランタイムの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** このランタイムの終了を要求する。
	 * 具体的には、この物理マシンのterminatedフラグをONにし、
	 * 各タスクのルールスレッドに終わるように言う */
	synchronized public void terminate() {
		terminated = true;
		Iterator it = tasks.iterator();
		while (it.hasNext()) {
			((Task)it.next()).signal();
		}
		// TODO joinする
		// スレーブランタイムならば、VMを終了する。
		// if (!(this instanceof MasterLMNtalRuntime)) System.exit(0);
	}
	/** このランタイムの終了が要求されたかどうか */
	public boolean isTerminated() {
		return terminated;
	}
//	/** 物理マシンが持つタスク全てがidleになるまで実行。<br>
//	 *  Tasksに積まれた順に実行する。親タスク優先にするためには
//	 *  タスクが木構造になっていないと出来ない。優先度はしばらく未実装。
//	 */
//	protected void localExec() {
//		boolean allIdle;
//		do {
//			allIdle = true; // idleでないタスクが見つかったらfalseになる。
//			Iterator it = tasks.iterator();
//			while (it.hasNext()) {
//				Task task = (Task)it.next();
//				if (!task.isIdle()) { // idleでないタスクがあったら
//					task.exec(); // ひとしきり実行
//					allIdle = false; // idleでないタスクがある
//				//	break;
//				}
//			}
//		} while(!allIdle);
//	}
}

/** ランタイムグループおよびグローバルルート膜を生成し、管理するランタイムのクラス */

public final class MasterLMNtalRuntime extends LocalLMNtalRuntime {
	private Membrane globalRoot;	// masterTaskへの参照を持つ方が分かりやすいかもしれない

	public MasterLMNtalRuntime(){
		Task masterTask = new Task(this);
		tasks.add(masterTask);
		globalRoot = (Membrane)masterTask.getRoot();
		// Inline
		Inline.initInline();
		Env.theRuntime = this;
	}

//	/**
//	 * １回だけ適用するルールをglobalRoot膜に適用する。
//	 * 初期化ルール、およびREPLが１行入力毎に生成するルールを適用するために使用する。
//	 * @deprecated
//	 */
//	public void applyRulesetOnce(Ruleset r){
//		r.react(globalRoot);
//	}
	
	public final Membrane getGlobalRoot(){
		return globalRoot;
	}
	public final Task getMasterTask(){
		return (Task)globalRoot.getTask();
	}
//	/** マスタランタイムとして実行する */
//	public void run() {
//		RemoteLMNtalRuntime.init();
//		while (true) {
//			if (Env.fTrace) {
//				Env.p( Dumper.dump(getGlobalRoot()) );
//			}
//			localExec();
//			if (globalRoot.isStable()) break;
//			synchronized(this) {
//				try {
//					if (terminated) break;
//					wait();
//				}
//				catch (InterruptedException e) {}
//			}
//		}
//		RemoteLMNtalRuntime.terminateAll();
//	}
}


package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラスのサブクラスのインスタンスは、1つの Java VM につき高々1つしか存在しない。
*/
public class LocalLMNtalRuntime extends AbstractLMNtalRuntime /*implements Runnable */{
	List tasks = new ArrayList();
//	protected Thread thread = new Thread(this);
	LocalLMNtalRuntime(){
		Env.theRuntime = this;
	}

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
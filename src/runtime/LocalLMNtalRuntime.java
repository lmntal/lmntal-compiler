package runtime;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import daemon.LMNtalDaemon;

/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラスのサブクラスのインスタンスは、1つの Java VM につき高々1つしか存在しない。
 * @author n-kato, nakajima
 */
public class LocalLMNtalRuntime extends AbstractLMNtalRuntime {
	List tasks = new ArrayList();
	
	////////////////////////////////////////////////////////////////	

	public LocalLMNtalRuntime(){
		Env.theRuntime = this;
		this.runtimeid = LMNtalDaemon.makeID();	// ここで生成する
			// NICがあがってないとここで死ぬ（分散使いたくない時）→ 回避済 2004-11-12
		this.hostname = LMNtalDaemon.getLocalHostName();
	}

	public static LocalLMNtalRuntime getInstance() {
		return Env.theRuntime;
	}
	
	/**
	/* 指定した膜を親膜とするルート膜を持つタスクをこのランタイムに作成する。
	 * @param parent ルート膜の親膜
	 */
	AbstractTask newTask(AbstractMembrane parent) {
		Task t = new Task(this, parent);
		tasks.add(t);
		return t;
	}
	
	////////////////////////////////////////////////////////////////
	
	/** （マスタタスクによって）このランタイムの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** このランタイムの終了が要求されたかどうか */
	public boolean isTerminated() {
		return terminated;
	}
	/** このランタイムの終了を要求する。
	 * 具体的には、この物理マシンのterminatedフラグをONにし、
	 * 各タスクのルールスレッドが終わるまで待つ。*/
	synchronized public void terminate() {
//		if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate()");
		terminated = true;
		Iterator it = tasks.iterator();
		while (it.hasNext()) {
			Task task = (Task)it.next();
//			if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): sending signal to " + task); //todo Env
			task.signal();
			try {
//				if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): now going to wait for thread " + task); //todo Env
				task.thread.join();
//				if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): " + task + " has finished!"); //todo Env
			} catch (InterruptedException e) {}
		}
		tasks.clear();	// 追加 n-kato 2004-10-30
	}

//	/** terminateフラグがONになるまで待つ。
//	 * <p>スレーブランタイムとして実行するときに使用する。*/
//	public void waitForTermination() {
//		while (!terminated) {
//			try {
//				synchronized(this) {
//					wait();
//				}
//			}
//			catch (InterruptedException e) {}
//		}
//	}
	
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
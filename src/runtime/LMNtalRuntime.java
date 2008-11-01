package runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラス（またはサブクラス）のインスタンスは、1つの Java VM につき高々1つしか存在しない。
 * @author n-kato, nakajima
 */
public class LMNtalRuntime{

	// 061129 okabe 廃止
//	/** ランタイムID。このVMのグローバルな識別子。ルールセットのIDの一部として使用される */
//	protected String runtimeid;
//	/** ランタイムIDを取得する */
//	public String getRuntimeID() {
//		return runtimeid;
//	}
	
	/** 世界的ルート膜 */
	private Membrane globalRoot;
	/** 世界的ルート膜を取得する */
	public final Membrane getGlobalRoot(){
		return globalRoot;
	}
	
	/** 全てのタスク */
	private List<Task> tasks = new ArrayList<Task>();
	
	////////////////////////////////////////////////////////////////	

	public LMNtalRuntime(){
		Env.theRuntime = this;
		Task masterTask = new Task(this);
		tasks.add(masterTask);
		globalRoot = (Membrane)masterTask.getRoot();
		// Inline
		Inline.initInline(); // TODO 適切な場所に移動する
	}

//	public static LMNtalRuntime getInstance() {
//		return Env.theRuntime;
//	}

	public final Task getMasterTask(){
		return (Task)globalRoot.getTask();
	}
	
	/**
	/* 指定した膜を親膜とするルート膜を持つタスクをこのランタイムに作成する。
	 * @param parent ルート膜の親膜
	 * @return 作成したタスク
	 */
	Task newTask(Membrane parent) {
		Task t = new Task(this, parent);
		tasks.add(t);
		return t;
	}
	
	////////////////////////////////////////////////////////////////
	
	/** このランタイムの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** このランタイムの終了が要求されたかどうか */
	public boolean isTerminated() {
		return terminated;
	}
	/** このランタイムの終了を要求する。
	 * 具体的には、この物理マシンのterminatedフラグをONにし、
	 * 各タスクのルールスレッドが終わるまで待つ。*/
	synchronized public void terminate() {
		terminated = true;
		Iterator<Task> it = tasks.iterator();
		while (it.hasNext()) {
			Task task = it.next();
			synchronized(task) {
				task.notifyAll();
			}
			try {
				if(Env.profile == Env.PROFILE_BYDRIVEN)
					task.outTime();
				task.thread.join();
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

//	/**
//	 * １回だけ適用するルールをglobalRoot膜に適用する。
//	 * 初期化ルール、およびREPLが１行入力毎に生成するルールを適用するために使用する。
//	 * @deprecated
//	 */
//	public void applyRulesetOnce(Ruleset r){
//		r.react(globalRoot);
//	}
	
}
package runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import daemon.LMNtalDaemon;

/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラスのサブクラスのインスタンスは、1つの Java VM につき高々1つしか存在しない。
 * @author n-kato, nakajima
 */
public class LocalLMNtalRuntime extends AbstractLMNtalRuntime {
	List tasks = new ArrayList();
//	protected Thread thread = new Thread(this);

	/*
	 * global ruleset id --> ruleset objectな表
	 */
	HashMap rulesetIDMap = new HashMap();


	public LocalLMNtalRuntime(){
		Env.theRuntime = this;
		this.runtimeid = LMNtalDaemon.makeID();	// ここで生成する
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

	/** （マスタタスクによって）このランタイムの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** このランタイムの終了が要求されたかどうか */
	public boolean isTerminated() {
		return terminated;
	}
	/** このランタイムの終了を要求する。
	 * 具体的には、この物理マシンのterminatedフラグをONにし、
	 * 各タスクのルールスレッドに終わるように言う。*/
	synchronized public void terminate() {
		terminated = true;
		Iterator it = tasks.iterator();
		while (it.hasNext()) {
			((Task)it.next()).signal();
		}
		// TODO ルールスレッドに対してjoinする（以下のコードは仮）
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}

	/** terminateフラグがONになるまで待つ。
	 * <p>スレーブランタイムとして実行するときに使用する。*/
	public void waitForTermination() {
		while (!terminated) {
			try {
				synchronized(this) {
					wait();
				}
			}
			catch (InterruptedException e) {}
		}
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

	/**
	 * global ruleset id --> ルールセットオブジェクトな表に登録
	 * @deprecated
	 */
	boolean registerRuleset(Ruleset rs){
		String globalid = rs.getGlobalRulesetID();
		
		if(globalid != null) {
			rulesetIDMap.put(rs.getGlobalRulesetID(), rs);
			return true;
		}
		
		return false;
	}

	/**
	 * global ruleset id --> rulset object
	 */
	Ruleset getRulset(String globalRulesetID){
		Ruleset rs = (Ruleset)rulesetIDMap.get(globalRulesetID);
		
		if(rs != null) return rs;
		
		return null;
	}
}
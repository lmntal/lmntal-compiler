package runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import daemon.LMNtalDaemon;

/** このVMで実行するランタイム（旧：物理マシン、旧々：計算ノード）
 * このクラス（またはサブクラス）のインスタンスは、1つの Java VM につき高々1つしか存在しない。
 * @author n-kato, nakajima
 */
public class LocalLMNtalRuntime extends AbstractLMNtalRuntime implements Runnable {
	/** 全てのタスク */
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
	
	/** （マスタランタイムなどによって）このランタイムの終了が要求されたかどうか */
	protected boolean terminated = false;
	/** このランタイムの終了が要求されたかどうか */
	public boolean isTerminated() {
		return terminated;
	}
	/** このランタイムの終了を要求する。
	 * 具体的には、このランタイムのterminatedフラグをONにし、
	 * ルールスレッドが停止するまで待つ。*/
	synchronized public void terminate() {
//		if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate()");
		terminated = true;
//		if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): sending signal");
		interrupted = true;
		try {
//			if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): wait for thread");
			thread.join();
//			if(Env.debug > 0)System.out.println("LocalLMNtalRuntime.terminate(): joined");
		} catch (InterruptedException e) {}
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
	
	////////////////////////////////////////////////////////////////
	// ルールスレッド（ランタイムにつき1つ）
	
	/** このランタイムのルールスレッド */
	Thread thread = new Thread(this,"RuleThread");
	/** このランタイムのルールスレッドの再実行が要求されたかどうか。
	 * 読み取りおよびfalseの書き込みはsynchronized(this)内に限る。*/
	protected boolean awakened = false;	
	/** （他のスレッドによって）このランタイムのルールスレッドの実行停止が要求されたかどうか。
	 * falseの書き込みはこのランタイムのルールスレッドに限る。*/
	protected boolean interrupted = false;
	/** asyncUnlockされたときにtrueになる（trueならばシグナルで復帰時にトレースdumpする）*/
	protected boolean asyncFlag = false;
	/** タスクキュー。synchronized(this)内で読み書きすること。
	 * TODO 優先度付きFIFOキューに移行（要求に符合するクラスが無いので新規に作るのか？） */
	LinkedList taskQueue = new LinkedList();
	
	/** 指定のタスクをタスクキューに入れる。
	 * すでに入っているときは何もしないのが理想だが、現在の実装では重複して入り、かつ正しく動作する。*/
	synchronized public void activateTask(Task task) {
		taskQueue.addLast(task);
		awakened = true;
		notify();
	}
	/** タスクキューが空かどうかを返す。呼出し後に空でなくなることもあるので注意すること。*/
	synchronized public boolean isIdle() {
		return taskQueue.isEmpty();
	}
	/** タスクキューの先頭の要素を取り除いて返す。空のときはnullが戻るようにしたいが現在は例外が発生。*/
	synchronized public Task getNextTask() {
		return (Task)taskQueue.removeFirst();
	}
	
	/** ルールスレッドの実行コード */
	public void run() {
		Membrane root = null; // マスタランタイムのときのみ世界的ルート膜が入る。それ以外はnull
		if (this instanceof MasterLMNtalRuntime) {
			root = ((MasterLMNtalRuntime)this).getGlobalRoot();
		}
		if (root != null) { 	
			if (Env.fTrace) {
				Env.p( Dumper.dump(root) );
			}
		}
		while (true) {
			while (!interrupted) {
				if (isTerminated()) return;
				if (isIdle()) break;
				Task task = getNextTask();
				task.exec();
				if (!task.isIdle()) taskQueue.addLast(task);
			}
			interrupted = false;
			if (root != null && root.isStable()) return;
			if (isTerminated()) return;
			synchronized(this) {
				if (awakened) {
					awakened = false;
					continue;
				}
				try {
					//System.out.println("RuleThread suspended");
					wait();
					//System.out.println("RuleThread resumed");
				}
				catch (InterruptedException e) {}
				awakened = false;
			}
			if (root != null) { 	
				if (Env.fTrace) {
					if (asyncFlag) {
						asyncFlag = false;
						Env.p( " ==>* \n" + Dumper.dump(root) );
					}
				}
			}	
		}
	}
}
package runtime;

/** 抽象タスク */
abstract public class AbstractTask {
	/** 物理マシン */
	protected AbstractLMNtalRuntime runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	/** メソッド呼び出しの転送先のリモートタスクまたはnull */
	protected RemoteTask remote = null;
	/** コンストラクタ
	 * @param runtime 所属するランタイム */
	AbstractTask(AbstractLMNtalRuntime runtime) {
		this.runtime = runtime;
	}
	/** ランタイムの取得 */
	public AbstractLMNtalRuntime getMachine() {
		return runtime;
	}
	/** ルート膜の取得 */
	public AbstractMembrane getRoot() {
		return root;
	}
	
	/** この抽象タスクのルールスレッドの再実行が要求されたかどうか */
	protected boolean awakened = false;

	/** このタスクに対してシグナルを発行する。
	 * すなわち、このタスクのルート膜のロックの取得をするためにブロックしているスレッドが存在するならば
	 * そのスレッドを再開してロックの取得を試みることを要求し、
	 * 存在しないならばこのタスクのルールスレッドの再実行を要求する。*/
	synchronized public final void signal() {
		awakened = true;
		notify();
	}
}

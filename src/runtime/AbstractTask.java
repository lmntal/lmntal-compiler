package runtime;

/** 抽象タスク */
abstract public class AbstractTask {
	/** 物理マシン */
	protected AbstractLMNtalRuntime runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	
	/** タスクの優先度（正確には、このタスクのルールスレッドの優先度）
	 * <p>ロックの制御に使用する予定。タスクのスケジューリングにも使用されている。
	 * <p>HIGHEST_PRIORITYを下回らない低い値でなければならない。*/
	int priority;
	/** ランタイムで最初に作る膜のデフォルト優先度 */
	public static final int ROOT_PRIORITY = 4096;
	/** 子タスクの優先度のデフォルト差分（子の方が若い値・高い優先度になる） */
	public static final int PRIORITY_DELTA = 4;
	/** 膜の最も高い優先度 */
	public static final int HIGHEST_PRIORITY = 10;
	/** ＠ホスト指定で生成された膜をルート膜とするタスクのデフォルト優先度（適当）*/
	public static final int PSEUDOTASK_PRIORITY = 8192 + PRIORITY_DELTA;
	/** タスクの優先度を取得 */
	public int getPriority() {
		return priority;
	}

	/** コンストラクタ
	 * @param runtime 所属するランタイム
	 * @param priority ルールスレッドの優先度 */
	AbstractTask(AbstractLMNtalRuntime runtime, int priority) {
		this.runtime = runtime;
		if (priority < HIGHEST_PRIORITY) priority = HIGHEST_PRIORITY;
		this.priority = priority;
	}
	/** ランタイムの取得 */
	public AbstractLMNtalRuntime getMachine() {
		return runtime;
	}
	/** ルート膜の取得 */
	public AbstractMembrane getRoot() {
		return root;
	}
}

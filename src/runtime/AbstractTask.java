package runtime;

/** 抽象タスク */
abstract public class AbstractTask {
	/** 物理マシン */
	protected AbstractLMNtalRuntime runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	/** asyncUnlockされたときにtrueになる
	 * （trueならばシグナルで復帰時にトレースdumpする）*/
	protected boolean asyncFlag = false;
	
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
}

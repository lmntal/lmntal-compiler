package runtime;

/** 抽象ランタイム（旧：抽象物理マシン、旧々：抽象計算ノード）クラス。
 * @author n-kato
 */
abstract class AbstractLMNtalRuntime {
	protected String runtimeid;
	protected String runtimeGroupID;
	public String getRuntimeID() {
		return runtimeid;
	}
	public String getRuntimeGroupID() {
		return runtimeGroupID;
	}
	/** 指定した膜を親膜とするルート膜を持つタスクをこのランタイムに作成する。
	 * @param parent ルート膜の親膜
	 * @return 作成したタスク */
	abstract AbstractTask newTask(AbstractMembrane parent);
	/** このランタイムの終了を要求する */
	abstract public void terminate();
	
//	/** ランタイムのルールスレッドに対して再実行を要求する。*/
//	abstract public void awake();
}

package runtime;

/** 抽象ランタイム（旧：抽象物理マシン、旧々：抽象計算ノード）クラス。
 * @author n-kato
 */
abstract class AbstractLMNtalRuntime {
	/** ランタイムID。このVMのグローバルな識別子。ルールセットのIDの一部として使用される */
	protected String runtimeid;
	/** リモート側のホスト名。Fully Qualified Domain Nameである必要がある。
	 * <p>RemoteLMNtalRuntimeから移動してみた */
	protected String hostname;

	/** ランタイムIDを取得する */
	public String getRuntimeID() {
		return runtimeid;
	}
	/** 指定した膜を親膜とするルート膜を持つタスクをこのランタイムに作成する。
	 * @param parent ルート膜の親膜
	 * @return 作成したタスク */
	abstract AbstractTask newTask(AbstractMembrane parent);
	/** このランタイムの終了を要求する。他のランタイムのことは考えなくてよい。*/
	abstract public void terminate();
	
//	/** ランタイムのルールスレッドに対して再実行を要求する。*/
//	abstract public void awake();
}

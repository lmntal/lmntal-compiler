package runtime;

//import daemon.Cache;
//import daemon.LMNtalDaemon;
//import daemon.LMNtalNode;

/**
 * リモート計算ノード
 * <p>
 * リモートに存在するLMNtalRuntimeを表すクラス。
 * 
 * @author n-kato, nakajima
 * 
 */
final class RemoteLMNtalRuntime extends AbstractLMNtalRuntime {
//	/**
//	 * リモート側のホスト名。Fully Qualified Domain Nameである必要がある。
//	 */
//	protected String hostname; →AbstractLMNtalRuntimeに移動してみた
	
	/**
	 * コンストラクタ
	 * 
	 * @param hostname 計算ノードが存在するホスト名（fqdn）
	 */
	protected RemoteLMNtalRuntime(String hostname) {
//		this.runtimeid = runtimeid;  //TODO 接続先からIDを受け取って代入する（今は未使用なので問題が発生しない）
		this.hostname = hostname;
	}

	/**
	 * リモートにタスクを作成する。
	 * 
	 * @param AbstractMembrane 親膜
	 * @return AbstractTask
	 */
	public AbstractTask newTask(AbstractMembrane parent) {
		RemoteTask task = new RemoteTask(this, parent);
		if (parent.task.remote == null) {
			task.remote = task;
			task.init();
		}
		else {
			task.remote = parent.task.remote;
		}
		String newmemid = parent.task.remote.getNextMemID();
		((RemoteMembrane)task.root).globalid = newmemid;
		//String parentmemid = daemon.IDConverter.getGlobalMembraneID(parent);
		task.remote.send("NEWTASK",newmemid,parent);
		return task;
	}
	
	////////////////////////////////
	// RemoteLMNtalRuntime で定義されるメソッド

	/**
	 * リモートホストに対して接続確認を行う。最初に接続するときにも使用される。
	 * @return 生存が確認できたかどうか
	 */
	public boolean connect() {
		return LMNtalRuntimeManager.daemon.sendWait(hostname,
			"CONNECT \"" + hostname + "\" \""
				+ daemon.LMNtalDaemonMessageProcessor.getLocalHostName() + "\"" );
	}
}
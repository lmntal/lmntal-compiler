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
public final class RemoteLMNtalRuntime extends AbstractLMNtalRuntime {
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
//		this.runtimeid = runtimeid;  // todo 接続先からIDを受け取って代入する（今は未使用なので問題が発生しない）
		this.hostname = hostname;
	}

	/**
	 * リモートにタスクを作成する。
	 * 
	 * @param AbstractMembrane 親膜
	 * @return AbstractTask
	 */
	public AbstractTask newTask(AbstractMembrane parent) {
		if(Env.debug > 0 )System.out.println("RemoteLMNtalRuntime.newTask()");
		RemoteTask task = new RemoteTask(this, parent);
		RemoteMembrane newroot = (RemoteMembrane)task.getRoot();
		if (newroot.remote == null) {
			// 新しいタスクをしばらく転送先として使用するために初期化する
			newroot.remote = task;
			task.init();
		}
		String newmemid = newroot.remote.generateNewID();
		newroot.globalid = newmemid;
		newroot.remote.send("NEWROOT",newmemid,parent,hostname); // 親膜への命令を子ホストに送る
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
				+ daemon.LMNtalDaemon.getLocalHostName() + "\"" );
	}
	
	/** このリモートランタイム（ローカル内のみ）に擬似タスクおよび擬似膜を作成して返す。
	 * 擬似膜は、あるルート膜の親膜のプロキシとして使用される。
	 * <br>擬似タスク ＝ ルート膜を持たないリモートタスク
	 * <br>擬似膜 ＝ 擬似タスクが管理するリモート膜（親膜を持たない）
	 * @return 作成した擬似膜 */
	public RemoteMembrane createPseudoMembrane(String globalid) {
		RemoteTask task = new RemoteTask(this);	// 擬似タスクを作成
		RemoteMembrane mem = new RemoteMembrane(task);
		mem.globalid= globalid;
		return mem;
	}

}
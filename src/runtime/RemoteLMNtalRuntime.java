package runtime;

import daemon.LMNtalDaemon;
import daemon.LMNtalNode;

/**
 * リモート計算ノード
 * 
 * 手元にあって、リモート側（ネットワークの向こう側）の代理人として存在する。
 * やってる事は命令をリモートへ転送する役目。
 * 
 * @author n-kato
 * 
 */
final class RemoteLMNtalRuntime extends AbstractLMNtalRuntime {
	boolean result;

	/*
	 * リモート側のホスト名。Fully Qualified Domain Nameである必要がある。
	 */
	protected String hostname;
	
	/*
	 * hostnameに対応するLMNtalNode。実際はLMNtalDaemon.getLMNtalNodeFromFQDN()でとってきてるだけ。
	 */
	protected LMNtalNode lmnNode;

	/*
	 * コンストラクタ
	 * 
	 * @param hostname つなげたいホストのホスト名。Fully Qualified Domain Nameである必要がある。
	 */
	protected RemoteLMNtalRuntime(String hostname) {
		//runtimeidの中にはfqdnが入っている（とみなす）

		this.hostname = hostname;
	}

	public AbstractTask newTask() {
		// todo 下と同じ
		return (AbstractTask) null;
	}

	/*
	 * タスクを作る命令を発行する。
	 * 実際にタスクが作られるのはリモート側。
	 * 
	 * @param AbstractMembrane 親膜
	 * @return AbstractTask
	 */
	public AbstractTask newTask(AbstractMembrane parent) {
		// TODO コネクションの管理をRemoteTaskからこのクラスに移した後でsendを発行するコードを書く

		return (AbstractTask) null;
	}

	/*
	 * TERMINATEを発行。
	 */
	public void terminate() {
		//TODO 実装@LMNtalDaemon(or MessageProcessor
		//send("TERMINATE");
	}

	/*
	 * AWAKEを発行
	 */
	public void awake() {
		//TODO 実装
		//send("AWAKE");
	}

	/*
	 * リモート側に接続する。
	 * 実際はLMNtalDaemon.connet(hostname)を呼出しているだけ。
	 * 
	 * @return 接続成功したらtrue、失敗したらfalse。接続の成功判定はLMNtalDaemon.connect()が返すbooleanと、
	 */
	public boolean connect() {
		//TODO 単体テスト
		result = LMNtalDaemon.connect(hostname);
		lmnNode = LMNtalDaemon.getLMNtalNodeFromFQDN(hostname);
		if (lmnNode != null && result == true) {
			return true;
		} else {
			return false;
		}
	}

}
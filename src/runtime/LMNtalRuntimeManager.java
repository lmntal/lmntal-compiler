package runtime;

import java.util.HashMap;
import java.util.Iterator;

import daemon.LMNtalNode;
import daemon.LMNtalDaemon;
import daemon.LMNtalRuntimeMessageProcessor;
import java.net.Socket;


/**
 * 計算ノード管理クラス
 * @author n-kato, nakajima
 */

public final class LMNtalRuntimeManager {
	/** ローカルのデーモンとの通信路 */
	public static LMNtalRuntimeMessageProcessor daemon = null;
	/** 計算ノード表: nodedesc (String) -> RemoteLMNtalRuntime */
	static HashMap runtimeids = new HashMap();

	/** 計算ノード表を利用開始する */
	public static void init() {}
	
	/** 指定されたホストに接続し、計算ノード表に登録する。
	 *  すでに登録されている場合は生存を確認する。生存を確認できない場合はnullを返す。
	 *  初めての分散呼び出しならば、ローカルのデーモンに接続する。
	 *  <p>現在の実装では、確認中にブロックする。
	 * しかしルールスレッドが長期間ブロックするのでよくないという本質的な問題がある。
	 * 
	 * @param nodedesc ノード識別子（現在はfqdnのみ） */
	public static AbstractLMNtalRuntime connectRuntime(String nodedesc) {
		String fqdn = nodedesc;
		//宛て先がlocalhostなら  自分自身を返す
		if(LMNtalNode.isMyself(fqdn)){
			return Env.theRuntime;
		}
		//以下は宛て先がremoteにある場合
		if (!connectToDaemon()) return null;			
		
		RemoteLMNtalRuntime ret = (RemoteLMNtalRuntime)runtimeids.get(fqdn);			

		if (ret == null) {
			ret = new RemoteLMNtalRuntime(fqdn);
			runtimeids.put(fqdn, ret);
		}
		
		if (ret.connect()){
			//生きている
			return ret;			
		} else {
			//失敗したらnull
			return null;
		}
	}
	
	/** リモートホストから接続があったときに呼ばれる。
	 * 対応するRemoteLMNtalRuntimeが存在しなければ作成する。
	 * @param nodedesc リモートホストが名乗ったノード識別子（現在はfqdnのみ） */
	public static AbstractLMNtalRuntime connectedFromRemoteRuntime(String nodedesc) {
		RemoteLMNtalRuntime ret = (RemoteLMNtalRuntime)runtimeids.get(nodedesc);
		if (ret == null) {
			ret = new RemoteLMNtalRuntime(nodedesc);
			runtimeids.put(nodedesc, ret);
		}
		return ret;
	}
	
	/** ローカルのデーモンに接続する。すでに接続している場合は何もしない。*/
	public static boolean connectToDaemon() {
		if (daemon != null) return true;
		try {
			// このVMはマスタノードである
			Socket socket = new Socket("localhost", LMNtalDaemon.DEFAULT_PORT);
			String rgid = Env.theRuntime.runtimeid;
			daemon = new LMNtalRuntimeMessageProcessor(socket,rgid);
			Thread t = new Thread(daemon);
			t.start();
			if (!daemon.sendWaitRegisterLocal("master")) {
				throw new Exception("cannot connect to daemon");
			}
			return true;
		}
		catch (Exception e) {
			System.out.println("Cannot connect to LMNtal deamon (not started?)");
			if (daemon != null) {
				daemon.close();
				daemon = null;
			}
			return false;
		}
	}
	
	private static Object terminateLock = "";
	/** 登録されている全てのRemoteLMNtalRuntimeを終了し、計算ノード表の登録を削除する。
	 *  Env.theRuntime は terminate しない。*/
	public static void terminateAllNeighbors() {
		synchronized(terminateLock) { // 重複転送防止のため（仮）
			Iterator it = runtimeids.keySet().iterator();
			while (it.hasNext()) {
				AbstractLMNtalRuntime machine = (AbstractLMNtalRuntime)runtimeids.get(it.next());
				machine.terminate();
			}
			runtimeids.clear();
		}
	}
}

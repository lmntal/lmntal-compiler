package runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import daemon.LMNtalNode;
import daemon.LMNtalDaemon;
import daemon.LMNtalRuntimeMessageProcessor;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 計算ノード管理クラス
 * @author n-kato, nakajima
 */

public final class LMNtalRuntimeManager {
	/** ローカルのデーモンとの通信路 */
	public static LMNtalRuntimeMessageProcessor daemon = null;
	/** 計算ノード表: nodedesc (String) -> RemoteLMNtalRuntime */
	static HashMap runtimeids = new HashMap();
	/** TERMINATE時にspanning treeを作成するが、その時に作った子を入れておく */
	static ArrayList childNode = new ArrayList();

	/** 計算ノード表を利用開始する */
	public static void init() {}
	
	/** ノード識別子nodedescを通信用ノード名（現在はglobalIPAddressのテキスト表現）に変換する。
	 * 失敗した場合nullを返す。*/
	public static String nodedescToFQDN(String nodedesc) {
		try {
			String fqdn = java.net.InetAddress.getByName(nodedesc).getHostAddress();
			return fqdn;
		} catch (UnknownHostException e) {
			return null;
		}
	}
	/** 指定されたホストに接続し、計算ノード表に登録する。
	 *  すでに登録されている場合は生存を確認する。生存を確認できない場合はnullを返す。
	 *  初めての分散呼び出しならば、ローカルのデーモンに接続する。
	 *  <p>現在の実装では、生存の確認中にブロックする。
	 * しかしルールスレッドが長期間ブロックするのでよくないという本質的な問題がある。
	 * 
	 * @param nodedesc ノード識別子 */
	public static AbstractLMNtalRuntime connectRuntime(String nodedesc) {
		if(true)System.out.println("LMNtalRuntimeManager.connectRuntime(" + nodedesc +")"); //todo use Env
		
		String fqdn = nodedescToFQDN(nodedesc);
		if (fqdn == null) return null;
		//宛て先がlocalhostなら  自分自身を返す
		if(LMNtalNode.isMyself(fqdn)){
			//if(Env.debug > 0)System.out.println("LMNtalRuntimeManager.connectRuntime(): 宛て先がlocalhostだから自分自身を返す");
			return Env.theRuntime;
		}
		
		//if(Env.debug > 0)System.out.println("LMNtalRuntimeManager.connectRuntime(): 宛て先がremoteにある場合");
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
		if(Env.debug > 0)System.out.println("LMNtalRuntimeManager.connectToDaemon()");
		
		if (daemon != null) return true;
		try {
			// このVMはマスタノードである
			Socket socket = new Socket("localhost", LMNtalDaemon.DEFAULT_PORT);
			String rgid = Env.theRuntime.runtimeid;
			daemon = new LMNtalRuntimeMessageProcessor(socket,rgid);
			Thread t = new Thread(daemon, "LMNtalRuntimeMessageProcessor");
			t.start();
			if (!daemon.sendWaitRegisterLocal("MASTER")) {
				throw new Exception("LMNtalRuntimeManager.connectToDaemon(): cannot connect to daemon");
			}
			return true;
		}
		catch (Exception e) {
			System.out.println("LMNtalRuntimeManager.connectToDaemon(): Cannot connect to LMNtal deamon (not started?)");
			e.printStackTrace();
			if (daemon != null) {
				daemon.close();
				daemon = null;
			}
			return false;
		}
	}
	public static void disconnectFromDaemon() {
		if (daemon != null) {
			if(true)System.out.println("LMNtalRuntimeManager.disconnectFromDaemon()");
			// 【質問】(n-kato) unregisterlocalを使わずに問答無用で切ればいいと思っていたんですけど、ダメなんですか？ 切れたらdaemon側で勝手にMASTER表から除去して向こうのスレッドが終わるという風に。2004-08-27
			// 【回答】(mizuno)
			//     LMNtalRuntimeMessageProcessorがin.readLine()でブロックしている最中にソケットを閉じるとSocketExceptionが発生してしまうので、その回避方法として、中島さんと相談してこうしてみました。
			//     unregisterlocalによってデーモン側でOutputStreamを閉じてもらえば、EOFが送られてくるのでreadLineがnullを返してくれます。
			//     最も、この方法でもデーモン側がOutputStreamを閉じる前にcloseが呼ばれてしまうと同じ事なのでもうひと工夫必要なようです。（…ということに今気がつきました。）
			//     「もうひと工夫」の案 : readLine()からnullが帰ってきたらcloseする
			//     unregisterlocalを使わずに、readLine()でSocketExceptionが発生したら黙って終了する、という方法ももちろんありますが、
			//     本当に問題が起きたときの例外と区別ができないのがいやだ、という理由でこの方法を選択しました。
			
			//(nakajima 2004-11-01) やっぱりいらないような気がします
			//daemon.sendWaitUnregisterLocal();
			
			daemon.close();
			if(true)System.out.println("LMNtalRuntimeManager.disconnectFromDaemon(): the socket has closed.");
			daemon = null;
		}
	}
	private static Object terminateLock = "";
	/** 登録されている全てのRemoteLMNtalRuntimeを終了し、計算ノード表の登録を削除する。
	 *  Env.theRuntime も terminate する (n-kato) 2004-09-17 */
	public static boolean terminateAll() {
		System.out.println("LMNtalRuntimeManager.terminateAll() entered");
		
		synchronized(terminateLock) { // 重複転送防止のため（仮）		
			if(Env.theRuntime.isTerminated()){
				System.out.println("LMNtalRuntimeManager.terminateAll(): Env.theRuntime.isTerminated() is true");
				return false;
			} else {
				System.out.println("LMNtalRuntimeManager.terminateAll(): Env.theRuntime.isTerminated() is false");
				Env.theRuntime.terminate();
			}
		}

		System.out.println("LMNtalRuntimeManager.terminateAll(): Env.theRuntime is terminated");
		
		childNode.clear();
		Iterator it = runtimeids.keySet().iterator();
		while (it.hasNext()) {
			RemoteLMNtalRuntime machine = (RemoteLMNtalRuntime)runtimeids.get(it.next());

			System.out.println("LMNtalRuntimeManager.terminateAll(): now sending TERMINATE to " + machine.hostname);
			
			if(daemon.sendWait(machine.hostname,"TERMINATE")){
				childNode.add(machine);
			}
		}
		runtimeids.clear();
		return true;
	}
	
	/**
	 * DISCONNECTRUNTIMEの中の人の中の人
	 * @author nakajima
	 */
	public static void disconnectAll(){
		Iterator it = childNode.iterator();
		while(it.hasNext()){
			RemoteLMNtalRuntime machine = (RemoteLMNtalRuntime)runtimeids.get(it.next());
			daemon.sendWait(machine.hostname,"DISCONNECTRUNTIME");
		}
		daemon.sendMessage("UNREGISTERLOCAL");
		childNode.clear();
	}
}

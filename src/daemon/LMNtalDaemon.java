package daemon;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
import java.net.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.io.*;

//TODO 実装

/**
 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
 * @author nakajima
 *
 */
public class LMNtalDaemon implements Runnable {
	/*
	 * 例(略記):    a :- { b }@"banon" 
	 * 例:          a :- connectruntime("banon", B) | {b}@B.
	 *              a :- connectruntimefailure("banon") | fail("banon").
	 * 
	 * 
	 * とりあえず考えてみたプロトコル
	 * 
	 * 凡例： rgid  … runtime group id
	 * 
	 * 登録関係
	 * HELO …（ノﾟДﾟ）おはよう
	 * READY …ack
	 * REGISTERLOCAL rgid …ローカルにあるマスタランタイムを登録
	 * OK, msgid … 成功
	 * FAIL, msgid … 失敗
	 * REGISTERREMOTE, rgid …手元に登録してあるマスタランタイムを相手に送る
	 * REGISTERFINISHED, rgid …黒を作成できたことをマスタランタイムがある計算機のdaemonに送る
	 * CONNECTRUNTIME, msgid, "banon"
	 * TERMINATE, rgid 
	 * 
	 * 実行関係
	 * COPYRULESET …ルールセットを全て送る
	 * COPYRULE …ルールを一つ送る
	 * COPYPROCESSCONTEXT …$pを送る
	 * COPYFREELINK
	 * COPYATOM
	 * 
	 *
	 * 
	 */

	/*【n-katoからのコメント】
	 * - REGIST は REGISTER が正しい。(済: 2004-05-18 nakajima)
	 * - REGISTREMOTE は、runtimegroupid（＝マスタランタイムの(runtime)id）を送る必要がある。
	 * - REGISTFINISHED は、マスタランタイムがある計算機ではなく、REGISTREMOTEを発行した計算機に送り返す。
	 *   したがって、runtimegroupid を引数に持つ必要がある。
	 * - TERMINATE runtimegroupid が必要。受信したら自分が知っている全てのランタイムに同じメッセージを送る。
	 */

	/**
	 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
	 * @author nakajima
	 *
	 */

	ServerSocket servSocket = null;
	static HashMap nodeTable = new HashMap();
	static HashMap registedRuntimeTable = new HashMap();
	static HashMap msgTable = new HashMap();
	static int id = 0;
	static Random r = new Random();
	static Process remoteRuntime;

	public LMNtalDaemon() {
		try {
			servSocket = new ServerSocket(60000);

		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}

	public LMNtalDaemon(int portnum) {
		try {
			servSocket = new ServerSocket(portnum);
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}

	//メイソはテスト用。自分自身（スレッド）をあげるのみ。
	public static void main(String args[]) {
		Thread t = new Thread(new LMNtalDaemon());
		t.start();
	}

	public void run() {
		System.out.println("LMNtalDaemon.run()");

		Socket tmpSocket;
		BufferedReader tmpInStream;
		BufferedWriter tmpOutStream;

		while (true) {
			try {
				tmpSocket = servSocket.accept();
				System.out.println("accepted socket: " + tmpSocket);

				//入力stream			
				tmpInStream =
					new BufferedReader(
						new InputStreamReader(tmpSocket.getInputStream()));

				//出力stream
				tmpOutStream =
					new BufferedWriter(
						new OutputStreamWriter(tmpSocket.getOutputStream()));

				if (register(tmpSocket,
					new LMNtalNode(
						tmpSocket.getInetAddress(),
						tmpInStream,
						tmpOutStream))) {
					//登録成功。
					Thread t2 =
						new Thread(
							new LMNtalDaemonMessageProcessor(
								tmpSocket,
								tmpInStream,
								tmpOutStream));
					t2.start();
				} else {
					//登録失敗。糸冬
					tmpInStream.close();
					tmpOutStream.close();
					tmpSocket.close();
				}

			} catch (IOException e) {
				System.out.println(
					"ERROR in LMNtalDaemon.run() " + e.toString());
				break;
			}
		}
	}

	static boolean register(Socket socket, LMNtalNode node) {
		System.out.println(
			"register(" + socket.toString() + ", " + node.toString() + ")");

		synchronized (nodeTable) {
			if (nodeTable.containsKey(socket)) {
				return false;
			}

			nodeTable.put(socket, node);
		}
		return true;
	}

	public static void createRemoteRuntime(int msgid) {
		String cmdLine =
			new String(
				"java daemon/SlaveLMNtalRuntime "
					+ LMNtalDaemon.makeID()
					+ " "
					+ msgid);
		System.out.println(cmdLine);

		try {
			remoteRuntime = Runtime.getRuntime().exec(cmdLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean registerLocal(Integer rgid, Socket socket) {
		System.out.println(
			"registerLocal(" + rgid + ", " + socket.toString() + ")");

		synchronized (registedRuntimeTable) {
			if (registedRuntimeTable.containsKey(rgid)) {
				System.out.println("registerLocal failed");
				return false;
			}

			registedRuntimeTable.put(rgid, socket);
		}

		System.out.println("registerLocal succeeded");
		return true;
	}

	public static boolean registerMessage(Integer msgid, LMNtalNode node) {
		System.out.println(
			"registerMessage(" + msgid + ", " + node.toString() + ")");

		synchronized (msgTable) {
			if (msgTable.containsKey(msgid)) {
				return false;
			}

			msgTable.put(msgid, node);
		}

		return true;
	}

	public static LMNtalNode getNodeFromMsgId(Integer msgid) {
		System.out.println("getNodeFromMsgId(" + msgid + ")");

		synchronized (msgTable) {
			return (LMNtalNode) msgTable.get(msgid);
		}
	}

	/* 
	 *  fqdn上のLMNtalDaemonが既に登録されているかどうか確認する
	 */
	public static boolean isRegisted(String fqdn) {
		//System.out.println("now in LMNtalDaemon.isRegisted(" + fqdn + ")");

		Collection c = nodeTable.values();
		Iterator it = c.iterator();

		while (it.hasNext()) {
			if (((LMNtalNode) (it.next()))
				.getInetAddress()
				.getCanonicalHostName()
				.equalsIgnoreCase(fqdn)) {
				//System.out.println("LMNtalDaemon.isRegisted("  + fqdn + ") is true!" );
				return true;
			}
		}

		//System.out.println("LMNtalDaemon.isRegisted("  + fqdn + ") is false!" );
		return false;
	}

	public static LMNtalNode getLMNtalNodeFromFQDN(String fqdn) {
		//System.out.println("now in LMNtalDaemon.getLMNtalNodeFromFQDN(" + fqdn + ")");

		Collection c = nodeTable.values();
		Iterator it = c.iterator();

		//			while (it.hasNext()) {
		//				if (((LMNtalNode) (it.next()))
		//					.getInetAddress()
		//					.getCanonicalHostName()
		//					.equalsIgnoreCase(fqdn)) {
		//						System.out.println("LMNtalDaemon.isRegisted("  + fqdn + ") is true!" );
		//						return (LMNtalNode)(it.next());
		//				}
		//			}

		try {
			InetAddress ip = InetAddress.getByName(fqdn);
			String ipstr = ip.getHostAddress();
			LMNtalNode node;

			while (it.hasNext()) {
				node = (LMNtalNode) (it.next());

				if (node.getInetAddress().getHostAddress().equals(ipstr)) {
					return node;
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 *  fqdn上のLMNtalDaemonを登録する＆登録してもらう
	 */
	public static boolean connect(String fqdn) {
		System.out.println("now in LMNtalDaemon.connect(" + fqdn + ")");
		//TODO firewallにひっかかってパケットが消滅した時をどうするか？

		if (isRegisted(fqdn)) {
			//すでに接続済みの場合
			return true;
		} else {
			try {
				//新規接続の場合
				InetAddress ip = InetAddress.getByName(fqdn);

				Socket socket = new Socket(fqdn, 60000);

				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				BufferedWriter out =
					new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));

				Thread t = new Thread(new LMNtalDaemonMessageProcessor(socket, in, out));
				t.start();

				LMNtalNode node = new LMNtalNode(ip, in, out);
				return register(socket, node);
			} catch (Exception e) {
				System.out.println(
					"ERROR in LMNtalDaemon.connect(" + fqdn + ")");
				e.printStackTrace();
				return false;
			}
		}
	}

	//		public static boolean sendMessage(String fqdn, String message){
	//			LMNtalNode target = getLMNtalNodeFromFQDN(fqdn);
	//
	//			try{
	//				BufferedWriter out = target.out;
	//				
	//				out.write(message);
	//				out.flush();
	//				return true;
	//			} catch (Exception e){
	//				System.out.println("ERROR in LMNtalDaemon.sendMessage: " + e.toString());
	//			}
	//		
	//			return false;
	//		}

	public static boolean sendMessage(LMNtalNode target, String message) {
		try {
			target.out.write(message);
			target.out.flush();

			return true;
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.sendMessage: " + e.toString());
			e.printStackTrace();
		}

		return false;
	}

	boolean disconnect(Socket socket) {
		LMNtalNode node = (LMNtalNode) nodeTable.get(socket);

		try {
			node.getInputStream().close();
			node.getOutputStream().close();
			socket.close();

			return true;
		} catch (Exception e) {
			System.out.println(
				"LMNtalDaemon.disconnect() failed!!! " + e.toString());
		}

		return false;
	}

	static void dumpHashMap() {
		Set tmpSet;

		tmpSet = nodeTable.entrySet();
		System.out.println("Dump nodeTable");
		System.out.println(tmpSet);

		tmpSet = registedRuntimeTable.entrySet();
		System.out.println("Dump registedRuntimeTable");
		System.out.println(tmpSet);

		tmpSet = msgTable.entrySet();
		System.out.println("Dump msgTable");
		System.out.println(tmpSet);
	}

	public static int makeID() {
		//一意なintを返す．
		//rgidとかmsgidとかに使う。
		//でも作り方がわからないのでとりあえずインクリメントして返す。
		return r.nextInt();
	}
}

package daemon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
 * 通信部分の処理を行う。
 * 
 * @author nakajima
 *
 */

//TODO 耐故障性　セキュリティ

/*
 * このスレッドがデーモンとして物理的な計算機の境界にあり、
 * tcp60000番に居座っている。コネクションが来たらLMNtalMessageProcessorスレッドをあげて待ちに入る。
 * LMNtalDaemonは一つの物理的な計算機に1スレッドしかあがらない。
 * 
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
	static boolean DEBUG = true;

	ServerSocket servSocket = null;
	static HashMap nodeTable = new HashMap();
	static HashMap registedRuntimeTable = new HashMap();
	static HashMap msgTable = new HashMap();
	static int id = 0;
	static Random r = new Random();
	static Process remoteRuntime;

	/*
	 * コンストラクタ。 tcp60000番にServerSocketを開くだけ。
	 */
	public LMNtalDaemon() {
		try {
			servSocket = new ServerSocket(60000);
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * コンストラクタ。 tcpのportnum番ポートにServerSocketを開くだけ。
	 * @param portnum ServerSocketに渡す
	 */
	public LMNtalDaemon(int portnum) {
		try {
			servSocket = new ServerSocket(portnum);
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}

	/*
	 * メイソはテスト用。自分自身（スレッド）を1つあげるのみ。
	 * @author nakajima
	 *
	 */
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
				tmpSocket = servSocket.accept(); //コネクションがくるまで待つ

				if (DEBUG) {
					System.out.println("accepted socket: " + tmpSocket);
				}

				//入力stream			
				tmpInStream =
					new BufferedReader(
						new InputStreamReader(tmpSocket.getInputStream()));

				//出力stream
				tmpOutStream =
					new BufferedWriter(
						new OutputStreamWriter(tmpSocket.getOutputStream()));

				//登録する
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
				e.printStackTrace();
				break;
			}
		}
	}

	/*
	 * Socketをkey, LMNtalNodeをvalueとするHashMapに登録する
	 * 
	 * @param socket ソケット
	 * @param node LMNtalノード
	 * 
	 * @return socketというキーが既に存在していたらfalse
	 */
	static boolean register(Socket socket, LMNtalNode node) {
		if (DEBUG) {
			System.out.println(
				"register(" + socket.toString() + ", " + node.toString() + ")");
		}

		synchronized (nodeTable) {
			if (nodeTable.containsKey(socket)) {
				return false;
			}

			nodeTable.put(socket, node);
		}
		return true;
	}

	/*
	 * リモート側で使われるスレーブランタイムを生成する。
	 * 
	 * @param msgid スレーブランタイムが返事を返す時に使うメッセージID
	 */
	public static void createRemoteRuntime(int msgid) {
		String cmdLine =
			new String(
				"java daemon/SlaveLMNtalRuntimeLauncher "
					+ LMNtalDaemon.makeID()
					+ " "
					+ msgid);
		if (DEBUG) {
			System.out.println(cmdLine);
		}

		try {
			remoteRuntime = Runtime.getRuntime().exec(cmdLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ローカルランタイムをHashMapに登録する。keyはrgid, valueはSocket。
	 * 
	 * @param rgid runtime group id
	 * @param socket rgidを持つラインタイムが持つソケット
	 * @return rgidなキーが存在する時はfalse
	 */
	public static boolean registerLocal(Integer rgid, Socket socket) {
		if (DEBUG) {
			System.out.println(
				"registerLocal(" + rgid + ", " + socket.toString() + ")");
		}

		synchronized (registedRuntimeTable) {
			if (registedRuntimeTable.containsKey(rgid)) {
				if (DEBUG) {
					System.out.println("registerLocal failed");
				}
				return false;
			}

			registedRuntimeTable.put(rgid, socket);
		}

		if (DEBUG) {
			System.out.println("registerLocal succeeded");
		}

		return true;
	}

	/*
	 * メッセージをHashMapに登録する。keyはmsgid, valueはLMNtalNode。
	 * 
	 * @param msgid メッセージID。intじゃなくてIntegerなのは仕様です。
	 * @param node msgidなメッセージを発行したLMNtalNode。
	 * @return msgidなキーが存在していたらfalse
	 */
	public static boolean registerMessage(Integer msgid, LMNtalNode node) {
		if (DEBUG) {
			System.out.println(
				"registerMessage(" + msgid + ", " + node.toString() + ")");
		}

		synchronized (msgTable) {
			if (msgTable.containsKey(msgid)) {
				return false;
			}

			msgTable.put(msgid, node);
		}

		if (DEBUG) {
			System.out.println("registerMessage succeeded");
		}

		return true;
	}

	/* 
	 *  fqdn上のLMNtalDaemonが既に登録されているかどうか確認する
	 *  @param fqdn Fully Qualified Domain Nameなホスト名
	 *  @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらtrue。それ以外はfalse。
	 */
	public static boolean isRegisted(String fqdn) {
		if (DEBUG) {
			System.out.println("now in LMNtalDaemon.isRegisted(" + fqdn + ")");
		}

		Collection c = nodeTable.values();
		Iterator it = c.iterator();

		while (it.hasNext()) {
			if (((LMNtalNode) (it.next()))
				.getInetAddress()
				.getCanonicalHostName()
				.equalsIgnoreCase(fqdn)) {
				if (DEBUG) {
					System.out.println(
						"LMNtalDaemon.isRegisted(" + fqdn + ") is true!");
				}
				return true;
			}
		}

		if (DEBUG) {
			System.out.println(
				"LMNtalDaemon.isRegisted(" + fqdn + ") is false!");
		}

		return false;
	}

	/*
	 * メッセージmsgidを発行したノードを探したい時に使う
	 * 
	 * @param msgid メッセージID。intじゃなくてIntegerなのは仕様です。
	 * @return メッセージmsgidを発行したLMNtalNode。見つからなかったらnull。
	 *   
	 */
	public static LMNtalNode getNodeFromMsgId(Integer msgid) {
		if (DEBUG) {
			System.out.println("getNodeFromMsgId(" + msgid + ")");
		}

		synchronized (msgTable) {
			return (LMNtalNode) msgTable.get(msgid);
		}
	}

	/*
	 * Fully Qualified Domain Name fqdnに対応するLMNtalNodeを探す。
	 * 
	 * @param fqdn ホスト名。Fully Qualified Domain Nameである事。 
	 * @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらそのLMNtalNode。それ以外はnull。
	 */
	public static LMNtalNode getLMNtalNodeFromFQDN(String fqdn) {
		if (DEBUG) {
			System.out.println(
				"now in LMNtalDaemon.getLMNtalNodeFromFQDN(" + fqdn + ")");
		}

		Collection c = nodeTable.values();
		Iterator it = c.iterator();

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
	 * 
	 * @param fqdn ホスト名。Fully Qualified Domain Nameである事。 
	 * @return ソケットが開けてregister(socket,node)が成功したらtrue。それ以外はfalse。
	 */
	public static boolean connect(String fqdn) {
		System.out.println("now in LMNtalDaemon.connect(" + fqdn + ")");
		//TODO firewallにひっかかってパケットが消滅した時をどうするか？

		if (isRegisted(fqdn)) {
			//すでに接続済みの場合

			//TODO 実装：生きているかを確認する

			//Socketをとってくる

			//connectを送る

			//OKがきたらtrueを返す

			//返事がなかったらfalseを返す

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

				Thread t =
					new Thread(
						new LMNtalDaemonMessageProcessor(socket, in, out));
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

	/*
	 * メッセージmessageをtarget宛に転送する
	 * 
	 * @param target 転送先
	 * @param message メッセージ
	 * @return BufferedWriter.write()を呼んだらtrueを返している。
	 */
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

	/*
	 * 接続を切る。
	 * 
	 * @param socket 接続を切りたいソケット。
	 * @return socket.close()したらtrue。それ以外はfalse。  
	 */
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
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * デバッグ用。nodeTable, registedRuntimeTable, msgTableを出力する。 
	 */
	static void dumpHashMap() {
		Set tmpSet;

		tmpSet = nodeTable.entrySet();
		System.out.println("Dump nodeTable: ");
		System.out.println(tmpSet);

		tmpSet = registedRuntimeTable.entrySet();
		System.out.println("Dump registedRuntimeTable: ");
		System.out.println(tmpSet);

		tmpSet = msgTable.entrySet();
		System.out.println("Dump msgTable: ");
		System.out.println(tmpSet);
	}

	/*
	 * 一意なintを返す。rgidとかmsgidとかに使う。Randmom.nextInt()を返しているだけ…。
	 */
	public static int makeID() {
		return r.nextInt();
	}
}

package daemon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
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
	 * 凡例： rgid  … runtime group id
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
	
	/*
	 * ソケットと接続元の表
	 */
	static HashMap nodeTable = new HashMap();
	
	/*
	 * ローカルにあるruntimeの表
	 */
	static HashMap registedLocalRuntimeTable = new HashMap();
	
	/*
	 *  接続元rgidの表
	 */
	static HashMap registedRemoteRuntimeTable = new HashMap();
	
	/*
	 * メッセージの表
	 */
	static HashMap msgTable = new HashMap();

	/*
	 * 起動されたLocalLMNtalRuntimeの表
	 */
	//static HashMap slaveRuntimeTable = new HashMap();

	/*
	 * この計算機にある膜のグローバルなIDを管理
	 */
	//static HashMap localMemTable = new HashMap();


	/*
	 * idを作るのに使うランダムオブジェクト
	 */
	static Random r = new Random();

	/*
	 * 自ホストのfqdn。中身はInetAddress.getLocalHost()
	 */
	static String myhostname;
	
	/*
	 * コンストラクタ。 tcp60000番にServerSocketを開くだけ。
	 */
	public LMNtalDaemon() {
		try {
			servSocket = new ServerSocket(60000);
			myhostname = InetAddress.getLocalHost().toString();
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * コンストラクタ。 tcpのportnum番ポートにServerSocketを開くだけ。
	 * @param portnum ServerSocketに渡すtcpポート番号
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

/*
 * 接続を待ち、接続が来たらLMNtalNodeを作成して登録、そしてLMntalDaemonMessageProcessorスレッドを起動する。
 * 
 *  (non-Javadoc)
 * @see java.lang.Runnable#run()
 */
	public void run() {
		if(DEBUG)System.out.println("LMNtalDaemon.run()");

		Socket tmpSocket;
		BufferedReader tmpInStream;
		BufferedWriter tmpOutStream;

		while (true) {
			try {
				tmpSocket = servSocket.accept(); //コネクションがくるまで待つ

				if (DEBUG)System.out.println("accepted socket: " + tmpSocket);

				//入力stream			
				tmpInStream =
					new BufferedReader(
						new InputStreamReader(tmpSocket.getInputStream()));

				//出力stream
				tmpOutStream =
					new BufferedWriter(
						new OutputStreamWriter(tmpSocket.getOutputStream()));

				//登録する
				if (registerNode(tmpSocket,
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
	 * Socketをkey, LMNtalNodeをvalueとするHashMap(nodeTable)に登録する
	 * 
	 * @param socket ソケット
	 * @param node LMNtalノード
	 * 
	 * @return socketというキーが既に存在していたらfalse
	 */
	static boolean registerNode(Socket socket, LMNtalNode node) {
		if (DEBUG)System.out.println("registerNode(" + socket.toString() + ", " + node.toString() + ")");

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
	 *  LMNtalDaemonMessageProcessor.run()内に移動 20040706 nakajima
	 *  
	 * @param msgid スレーブランタイムが返事を返す時に使うメッセージID
	 */
//	public static void createRemoteRuntime(int msgid) {
//		String cmdLine =
//			new String(
//				"java daemon/SlaveLMNtalRuntimeLauncher "
//					+ LMNtalDaemon.makeID()
//					+ " "
//					+ msgid);
//		if (DEBUG) {
//			System.out.println(cmdLine);
//		}
//
//		try {
//			remoteRuntime = Runtime.getRuntime().exec(cmdLine);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/*
	 * createRemoteRuntimeで生成されたスレーブランタイム(LocalLMNtalRuntime)を登録する
	 * 
	 * LMNtalDaemonMessageProcessor.run()内に移動: 20040706 nakajima
	 * 
	 * @param socket 
	 * @param rgid 生成されたLocalLMNtalRuntimeのrgid。Integerなのは仕様です。
	 */
//	boolean registerSlaveRuntime(Socket socket, Integer rgid){
//		if (DEBUG)System.out.println("registerSlaveRuntime(" + socket.toString() + ", " + rgid.toString() + ")");
//		
//		synchronized (slaveRuntimeTable){
//			if(slaveRuntimeTable.containsKey(socket)) return false;
//			
//			slaveRuntimeTable.put(socket, rgid);
//		}
//		return true;
//	}

	/*
	 * ローカルランタイムをHashMapに登録する。keyはrgid, valueはSocket。
	 * 同時に外からのsocket -> 内同士のsocketというHashMapにも登録する。
	 * 
	 * @param rgid runtime group id
	 * @param socket rgidを持つラインタイムが持つソケット
	 * @return rgidなキーが存在する時はfalse
	 */
	public static boolean registerLocal(String rgid, Socket socket) {
		if (DEBUG)System.out.println("registerLocal(" + rgid + ", " + socket.toString() + ")");

		//rgid -> socket
		synchronized (registedLocalRuntimeTable) {
			if (registedLocalRuntimeTable.containsKey(rgid)) {
				if (DEBUG)System.out.println("registerLocal failed");
				
				return false;
			}

			registedLocalRuntimeTable.put(rgid, socket);
		}

		if (DEBUG)System.out.println("registerLocal succeeded");
		return true;
	}

	public static boolean registerRemote(String rgid, Socket socket){
		if (DEBUG)System.out.println("registerRemote(" + rgid + ", " + socket.toString() + ")");
		
		synchronized(registedRemoteRuntimeTable){
			if(registedRemoteRuntimeTable.containsKey(rgid)){
				if (DEBUG)System.out.println("registerRemote failed");
				return false;
			}
			registedRemoteRuntimeTable.put(rgid, socket);
		}
		if (DEBUG)System.out.println("registerRemote succeeded");
		return true;
	}
	
	/*
	 * メッセージをHashMapに登録する。keyはmsgid, valueはLMNtalNode。
	 * 
	 * @param msgid メッセージID。intじゃなくてIntegerなのは仕様です。
	 * @param node msgidなメッセージを発行したLMNtalNode。
	 * @return msgidなキーが存在していたらfalse
	 */
	public static boolean registerMessage(String msgid, LMNtalNode node) {
		if (DEBUG)System.out.println("registerMessage(" + msgid + ", " + node.toString() + ")");

		synchronized (msgTable) {
			if (msgTable.containsKey(msgid)) {
				return false;
			}

			msgTable.put(msgid, node);
		}

		if (DEBUG)System.out.println("registerMessage succeeded");

		return true;
	}

	/* 
	 *  fqdn上のLMNtalDaemonが既に登録されているかどうか確認する
	 *  @param fqdn Fully Qualified Domain Nameなホスト名
	 *  @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらtrue。それ以外はfalse。
	 */
	public static boolean isRegisted(String fqdn) {
		if (DEBUG) System.out.println("now in LMNtalDaemon.isRegisted(" + fqdn + ")");
		

		Collection c = nodeTable.values();
		Iterator it = c.iterator();

		while (it.hasNext()) {
			if (((LMNtalNode) (it.next()))
				.getInetAddress()
				.getCanonicalHostName()
				.equalsIgnoreCase(fqdn)) {
				if (DEBUG) System.out.println("LMNtalDaemon.isRegisted(" + fqdn + ") is true!");
				return true;
			}
		}

		if (DEBUG) System.out.println("LMNtalDaemon.isRegisted(" + fqdn + ") is false!");
		

		return false;
	}

	public static LMNtalNode getNode(Socket socket){
		return (LMNtalNode) nodeTable.get(socket);
	}

	public static Socket getRemoteSocket(String rgid){
		return (Socket)registedRemoteRuntimeTable.get(rgid);
	}
	
	public static Socket getLocalSocket(String rgid){
		return (Socket)registedLocalRuntimeTable.get(rgid);
	}
	
	/*
	 * メッセージmsgidを発行したノードを探したい時に使う
	 * 
	 * @param msgid メッセージID。intじゃなくてIntegerなのは仕様です。
	 * @return メッセージmsgidを発行したLMNtalNode。見つからなかったらnull。
	 *   
	 */
	public static LMNtalNode getNodeFromMsgId(String msgid) {
		if (DEBUG)System.out.println("getNodeFromMsgId(" + msgid + ")");

		synchronized (msgTable) {
			return (LMNtalNode)msgTable.get(msgid);
		}
	}

	/*
	 * Fully Qualified Domain Name fqdnに対応するLMNtalNodeを探す。
	 * 
	 * @param fqdn ホスト名。Fully Qualified Domain Nameである事。 
	 * @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらそのLMNtalNode。それ以外はnull。
	 */
	public static LMNtalNode getLMNtalNodeFromFQDN(String fqdn) {
		if (DEBUG)System.out.println("now in LMNtalDaemon.getLMNtalNodeFromFQDN(" + fqdn + ")");
		

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
	 * 既に登録済みの場合は、メッセージを送って生きているか検査する。返事がきたらtrue、来なかったらfalse。
	 */
	public static boolean connect(String fqdn, String rgid) {
		if(DEBUG)System.out.println("now in LMNtalDaemon.connect(" + fqdn + ", rgid:" + rgid +  ")");
		//todo firewallにひっかかってパケットが消滅した時をどうするか？

		if (isRegisted(fqdn)) {
			//すでに接続済みの場合

			//todo このif文の中身だけ単体テスト

			LMNtalNode target = getLMNtalNodeFromFQDN(fqdn);

			//connectを送る
			send(target.out, fqdn, rgid, "connect");

			return true;

			//返事を待つ  
//			try {
//				String ans = target.in.readLine(); //todo この入力はconnectに対する返事とは限らないので間違っている
//
//				if(ans != null){
//					return true; //何か帰ってきていれば生きている。例えfailでも。
//				} else {
//					return false;
//				}
//			} catch (IOException e1) {
//				e1.printStackTrace();
//				return false; //終了
//			}
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
				return registerNode(socket, node);
			} catch (Exception e) {
				System.out.println(
					"ERROR in LMNtalDaemon.connect(" + fqdn + ", rgid: " + rgid + ")");
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
				"ERROR in LMNtalDaemon.sendMessage()");
			e.printStackTrace();
		}

		return false;
	}

	static void respond(BufferedWriter out, String msgid, String message){
		try {
			out.write("res " + msgid + " " + message + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respond()");
			e.printStackTrace();
		}
	}

	static void respondAsOK(BufferedWriter out, String msgid){
		try {
			out.write("res " + msgid + " ok\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respondAsOK()");
			e.printStackTrace();
		}
	}

	
	static void respondAsFail(BufferedWriter out, String msgid){
		try {
			out.write("res " + msgid + " fail\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respondAsFail()");
			e.printStackTrace();
		}
	}
	
	static void send(BufferedWriter out, String fqdn, String rgid, String command){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	static void send(BufferedWriter out, String fqdn, String rgid, String command, String arg1){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}

	static void send(BufferedWriter out, String fqdn, String rgid, String command, String arg1, String arg2){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	static void send(BufferedWriter out, String fqdn, String rgid, String command, String arg1, String arg2, String arg3){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	static void send(BufferedWriter out, String fqdn, String rgid, String command, String arg1, String arg2, String arg3, String arg4){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	static void send(BufferedWriter out, String fqdn, String rgid, String command, String arg1, String arg2, String arg3, String arg4, String arg5){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	
	//TODO localhost宛のメッセージのフォーマット
	static void sendLocal(BufferedWriter out, String rgid, String command){
			
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
		System.out.println("Dump nodeTable: ");
		System.out.println(nodeTable.entrySet());

		System.out.println("Dump registedLocalRuntimeTable: ");
		System.out.println(registedLocalRuntimeTable.entrySet());

		System.out.println("Dump registedRemoteRuntimeTable: ");
		System.out.println(registedRemoteRuntimeTable.entrySet());

		System.out.println("Dump msgTable: ");
		System.out.println(msgTable.entrySet());
	}

	/*
	 * 一意なintを返す。rgidとかmsgidとかに使う。いまところはInetAddress.getLocalHost()+";"+Randmom.nextLong()の返り値を返しているだけ。
	 *  todo 一意なIDを作る
	 */
	public static String makeID() {
		return myhostname+ ":" + r.nextLong();
	}
}
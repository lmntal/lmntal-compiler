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
 * @author nakajima, n-kato
 *
 */

//todo 耐故障性　セキュリティ

/**
 * このスレッドがデーモンとして物理的な計算機の境界にあり、
 * tcp60000番に居座っている。コネクションが来たらLMNtalMessageProcessorスレッドをあげて待ちに入る。
 * LMNtalDaemonは一つの物理的な計算機に1スレッドしかあがらない。
 * 
 * @author nakajima, n-kato
 *
 */
public class LMNtalDaemon implements Runnable {
	/*
	 * 凡例： rgid  … runtime group id
	 */
	static boolean DEBUG = true; //LMNtalDaemonは単体で起動するのでEnv.debugが0より大きくならない...
	
	public static final int DEFAULT_PORT = 60000;
	
	int portnum = DEFAULT_PORT;
	
	/** listenするソケット */
	ServerSocket servSocket = null;
	
	/**
	 * リモートのデーモンとの接続表: InetAddress -> LMNtalNode
	 */
	static HashMap remoteHostTable = new HashMap();
	
	/**
	 * ローカルにあるランタイムの表: rgid (String) -> LMNtalNode
	 */
	static HashMap runtimeGroupTable = new HashMap();

	// todo msgTable と msgTagTable は LMNtalNode に移管する
	
	/**
	 * メッセージの表: msgid (String) -> LMNtalNode
	 */
	static HashMap msgTable = new HashMap();
	/**
	 * msgid (String) -> tag (String)
	 */
	static HashMap msgTagTable = new HashMap();

	/*
	 * idを作るのに使うランダムオブジェクト
	 */
	static Random r = new Random();

	/**
	 * 自ホストのfqdn。中身はInetAddress.getLocalHost()
	 */
	static String myhostname;
	
	/**
	 * コンストラクタ。 DEFAULT_PORTで指定されたポート番号にServerSocketを開くだけ。
	 */
	public LMNtalDaemon() {
		this(DEFAULT_PORT);
	}
	
	/**
	 * コンストラクタ。 tcpのportnum番ポートにServerSocketを開くだけ。
	 * @param portnum ServerSocketに渡すtcpポート番号
	 */
	public LMNtalDaemon(int portnum) {
		this.portnum = portnum;
		try {
			servSocket = new ServerSocket(portnum);
//			myhostname = InetAddress.getLocalHost().toString();
		} catch (IOException e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
			e.printStackTrace();
		}
	}
	static {
		try {
			myhostname = InetAddress.getLocalHost().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * メイソはテスト用。自分自身（スレッド）を1つあげるのみ。
	 * @author nakajima
	 *
	 */
	public static void main(String args[]) {
		Thread t = new Thread(new LMNtalDaemon(), "LMNtalDaemon");
		t.start();
	}

	/**
	 * 接続を待ち、接続が来たらLMNtalNodeを作成して登録、そしてLMNtalDaemonMessageProcessorスレッドを起動する。
	 */
	public void run() {
		if(DEBUG)System.out.println("LMNtalDaemon.run()");

		while (true) {
			try {
				Socket socket = servSocket.accept(); //コネクションがくるまで待つ

				if (DEBUG)System.out.println("accepted socket: " + socket);
				LMNtalDaemonMessageProcessor node = new LMNtalDaemonMessageProcessor(socket);
				
				//登録する
				if (registerRemoteHostNode(node)) {
					//登録成功。
					Thread t2 = new Thread(node, "LMNalDaemonMessageProcessor");
					t2.start();
				} else {
					//登録失敗。糸冬了
					node.close();
				}
			} catch (IOException e) {
				System.out.println("ERROR in LMNtalDaemon.run(): ");
				e.printStackTrace();
				break;
			}
		}
	}

	////////////////////////////////////////////////////////////////
	
	/**
	 * リモートホストのノードをremoteHostTableに登録する
	 * @param node LMNtalNode
	 * @return このホストが既に登録されていたらfalse
	 */
	static boolean registerRemoteHostNode(LMNtalDaemonMessageProcessor node) {
		if (DEBUG)System.out.println("registerNode(" + node.toString() + ")");
		
		synchronized (remoteHostTable) {
			if (remoteHostTable.containsKey(node.getInetAddress())) {
				return false;
			}
			remoteHostTable.put(node.getInetAddress(), node);
		}
		return true;
	}
	
	/* 
	 *  fqdn上のLMNtalDaemonが既に登録されているかどうか確認する
	 *  @param fqdn Fully Qualified Domain Nameなホスト名
	 *  @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらtrue。それ以外はfalse。
	 */
	public static boolean isHostRegistered(String fqdn) {
		// return (getLMNtalNodeFromFQDN(fqdn) != null);
		
		if (DEBUG) System.out.println("now in LMNtalDaemon.isRegisted(" + fqdn + ")");
		

		Collection c = remoteHostTable.values();
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
	
	/**
	 * Fully Qualified Domain Name fqdnに対応するLMNtalNodeを探す。
	 * 
	 * @param fqdn ホスト名。Fully Qualified Domain Nameである事。 
	 * @return nodeTableに登録されているLMNtalNodeのInetAddressからホスト名を引いてStringで比較する。合ってたらそのLMNtalNode。それ以外はnull。
	 */
	public static LMNtalNode getLMNtalNodeFromFQDN(String fqdn) {
		if (DEBUG)System.out.println("now in LMNtalDaemon.getLMNtalNodeFromFQDN(" + fqdn + ")");
		
		Collection c = remoteHostTable.values();
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

	/**
	 * fqdn上のLMNtalDaemonに接続する。
	 * @param fqdn ホスト名。Fully Qualified Domain Nameである事。 
	 */
	public static boolean makeRemoteConnection(String fqdn) {
		//「ブロックしないようにする」
		//todo 3分間誰も通信できなくなるのを回避するために専用スレッドを作る（後回しでよい） n-kato 2004-08-20

		if(DEBUG)System.out.println("LMNtalDaemon.makeRemoteConnection(" + fqdn + ")");
		
		if (isHostRegistered(fqdn)) return true;
		
		Socket socket;
		try {
			//新規接続の場合
			socket = new Socket(fqdn, DEFAULT_PORT);
			//socket.setSoTimeout(180000); //とりあえず3分
			//↑一旦つないで3分通信がないときもtimeoutが発生するのでとりあえずコメントアウト(2004-08-22 nakajima
			//todo 「接続しようとしたときからn分だめだったらtimeout」にする
			LMNtalDaemonMessageProcessor node = new LMNtalDaemonMessageProcessor(socket);
			if (registerRemoteHostNode(node)) {
				Thread t = new Thread(node, "LMNtalDaemonMessageProcessor");
				t.start();
				return true;
			}
			node.close();
			return false;
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.makeRemoteConnection(" + fqdn + ")");
			System.out.println("If java.net.SocketTimeoutException has raised, open TCP " + DEFAULT_PORT);
			e.printStackTrace();
			//todo  SocketTimeoutExceptionのときはここでsocketを閉じるべきか？
			//"If the timeout expires, a java.net.SocketTimeoutException is raised, though the Socket is still valid." (1.4.1 api specitifation.)
			
			return false;
		}
	}	

	////////////////////////////////////////////////////////////////

	public static boolean isRuntimeGroupRegistered(String rgid) {
		return runtimeGroupTable.containsKey(rgid);
	}
	public static boolean registerRuntimeGroup(String rgid, LMNtalNode node){
		if (DEBUG)System.out.println("registerRuntimeGroup(" + rgid + ", " + node.toString() + ")");
		
		synchronized(runtimeGroupTable){
			if(runtimeGroupTable.containsKey(rgid)){
				if (DEBUG)System.out.println("registerRuntimeGroup failed");
				return false;
			}
			runtimeGroupTable.put(rgid, node);
		}
		if (DEBUG)System.out.println("registerRuntimeGroup succeeded");
		return true;
	}

	public static boolean unregisterRuntimeGroup(String rgid){
		if (DEBUG)System.out.println("unregisterRuntimeGroup(" + rgid +  ")");
		
		synchronized(runtimeGroupTable){
			if(runtimeGroupTable.containsKey(rgid)){
				runtimeGroupTable.remove(rgid);
				if (DEBUG)System.out.println("unregisterRuntimeGroup succeeded");
				return true;
			}
		}
		return false;
	}

	public static LMNtalNode getRuntimeGroupNode(String rgid){
		return (LMNtalNode)runtimeGroupTable.get(rgid);
	}

	////////////////////////////////////////////////////////////////

	/**
	 * メッセージをHashMapに登録する。keyはmsgid, valueはLMNtalNode。
	 * <p>
	 * メッセージに対する応答メッセージの送信先を記録する。
	 * todo 応答メッセージの送信後、対応は削除すべきである。
	 * 
	 * @param msgid メッセージID
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
	/** タグ付きでmsgTableに登録する */
	public static boolean registerMessageWithTag(String msgid, LMNtalNode node, String tag) {
		synchronized(msgTagTable) {
			if (msgTagTable.containsKey(msgid)) return false;
			if (!registerMessage(msgid,node)) return false;
			msgTagTable.put(msgid, tag);
			return true;
		}
	}

	/**
	 * メッセージmsgidの返却先を取得する
	 * @param msgid メッセージID
	 * @return メッセージmsgidを返却するLMNtalNode。見つからなかったらnull。
	 */
	public static LMNtalNode unregisterMessage(String msgid) {
		synchronized (msgTable) {
			return (LMNtalNode)msgTable.remove(msgid);
		}
	}

	/**
	 * メッセージmsgidを発行したノードを探したい時に使う
	 * 
	 * @param msgid メッセージID
	 * @return メッセージmsgidを発行したLMNtalNode。見つからなかったらnull。
	 * @deprecated
	 */
	public static LMNtalNode getNodeFromMsgId(String msgid) {
		if (DEBUG)System.out.println("getNodeFromMsgId(" + msgid + ")");

		synchronized (msgTable) {
			return (LMNtalNode)msgTable.get(msgid);
		}
	}

	public static String getTagForMsgId(String msgid) {
		synchronized (msgTagTable) {
			if (!msgTagTable.containsKey(msgid)) return null;
			return (String)msgTagTable.remove(msgid);
		}
	}

	////////////////////////////////////////////////////////////////

	/*
	 * デバッグ用。nodeTable, registedRuntimeTable, msgTableを出力する。 
	 */
	static void dumpHashMap() {
		System.out.println("Dump nodeTable: ");
		System.out.println(remoteHostTable.entrySet());

//		System.out.println("Dump registedLocalRuntimeTable: ");
//		System.out.println(registedLocalRuntimeTable.entrySet());

		System.out.println("Dump registedRuntimeGroupTable: ");
		System.out.println(runtimeGroupTable.entrySet());

		System.out.println("Dump msgTable: ");
		System.out.println(msgTable.entrySet());
	}

	/*
	 * 一意なintを返す。rgidとかmsgidとかに使う。いまところはInetAddress.getLocalHost()+":"+Randmom.nextLong()の返り値を返しているだけ。
	 *  todo 一意なIDを作る
	 */
	public static String makeID() {
		return myhostname+ ":" + r.nextLong();
	}
	public static String getLocalHostName() {
		return myhostname;
	}
}
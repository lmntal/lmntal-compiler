package test.distribute;

//import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//TODO 終了するようにする

public class LMNtalDaemonTest {
	public static void main(String args[]) {
		Thread t1 = new Thread(new LMNtalDaemon(60000));
		t1.start();

		//Thread t2 = new Thread(new LMNtalDaemon(60001));
		//t2.start();

	}
}

class GlobalConstants {
	public static int LMNTAL_DAEMON_PORT = 60000;
}

/**
 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
 * @author nakajima
 *
 */
class LMNtalDaemon implements Runnable {

	ServerSocket servSocket = null;
	static HashMap nodeTable = new HashMap();
	static HashMap registedRuntimeTable = new HashMap();
	static HashMap msgTable = new HashMap();

	public LMNtalDaemon() {
		try {
			servSocket = new ServerSocket(GlobalConstants.LMNTAL_DAEMON_PORT);

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
							new LMNtalDaemonThread2(
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

			registedRuntimeTable.put(msgid, node);
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
		System.out.println("now in LMNtalDaemon.isRegisted(" + fqdn + ")");
		
		//TODO 単体テスト
		Collection c = nodeTable.values();
		Iterator it = c.iterator();

		while (it.hasNext()) {
			if (((LMNtalNode) (it.next()))
				.getInetAddress()
				.getCanonicalHostName()
				.equalsIgnoreCase(fqdn)) {
					System.out.println("LMNtalDaemon.isRegisted("  + fqdn + ") is true!" );
					return true;
			}
		}

		System.out.println("LMNtalDaemon.isRegisted("  + fqdn + ") is false!" );
		return false;
	}

	/*
	 *  fqdn上のLMNtalDaemonを登録する＆登録してもらう
	 */
	public static boolean connect(String fqdn) {
		System.out.println("now in LMNtalDaemon.connect(" + fqdn + ")");

		//TODO コネクションがfirewallにひっかかってパケットが消滅した時をどうするか？
		
		boolean result = false;

		if (isRegisted(fqdn)) {
			return result;
		}

		try {
			InetAddress ip = InetAddress.getByName(fqdn);

			Socket socket =
				new Socket(fqdn, GlobalConstants.LMNTAL_DAEMON_PORT);

			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));

			LMNtalNode node = new LMNtalNode(ip, in, out);
			result = register(socket, node);
		} catch (Exception e) {
			System.out.println("ERROR in connect処理: " + e.toString());
		}

		return result;
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
}

class LMNtalNode {
	InetAddress ip;
	BufferedReader in;
	BufferedWriter out;

	LMNtalNode(
		InetAddress tmpIp,
		BufferedReader tmpInStream,
		BufferedWriter tmpOutStream) {
		ip = tmpIp;
		in = tmpInStream;
		out = tmpOutStream;
	}

	BufferedReader getInputStream() {
		return in;
	}

	BufferedWriter getOutputStream() {
		return out;
	}

	InetAddress getInetAddress() {
		return ip;
	}

	public String toString() {
		return "LMNtalNode[IP:"
			+ ip
			+ ", "
			+ in.toString()
			+ ", "
			+ out.toString()
			+ "]";
	}
}

class LMNtalDaemonThread2 implements Runnable {
	BufferedReader in;
	BufferedWriter out;
	Socket socket;

	public LMNtalDaemonThread2(
		Socket tmpSocket,
		BufferedReader inTmp,
		BufferedWriter outTmp) {
		in = inTmp;
		out = outTmp;
		socket = tmpSocket;
	}

	public void run() {
		System.out.println("LMNtalDaemonThread2.run()");

		while (true) {
			try {
				String input = in.readLine();
				//テスト用：
				//String input = new String("msgid \"localhost\" runtimegroupid connect\n");
				System.out.println("input: " + input);
				if (input == null) {
					break;
				}

				//ここからメッセージ処理部分
				Integer msgid;
				Integer rgid;
				boolean result;
				String[] tmpString = new String[3];

				tmpString = input.split(" ", 3);

				if (tmpString[0].equalsIgnoreCase("res")) {
					//res msgid 結果
					//直接戻せばよい

					//TODO 単体テスト
					msgid = new Integer(tmpString[1]);

					LMNtalNode returnNode =
						LMNtalDaemon.getNodeFromMsgId(msgid);

					returnNode.getOutputStream().write(input);
					returnNode.getOutputStream().flush();
				} else if (tmpString[0].equalsIgnoreCase("registerlocal")) {
					//registerlocal rgid
					//rgidとソケットを登録
					rgid = new Integer(tmpString[1]);
					result = LMNtalDaemon.registerLocal(rgid, socket);
					if (result == true) {
						//成功
						out.write("ok\n");
						out.flush();
					} else {
						//失敗
						out.write("fail\n");
						out.flush();
					}
				} else if (tmpString[0].equalsIgnoreCase("dumphash")) {
					//dumphash
					LMNtalDaemon.dumpHashMap();
				} else {
					//msgidからつづく命令列とみなす
					//msgid "FQDN" rgid メッセージ
					msgid = new Integer(tmpString[0]);

					//メッセージを登録
					LMNtalNode returnNode =
						new LMNtalNode(socket.getInetAddress(), in, out);
					result = LMNtalDaemon.registerMessage(msgid, returnNode);

					if (result == true) {
						//登録成功
						boolean result2;

						//接続しに行く
						result2 =
							LMNtalDaemon.connect(
								(tmpString[1].split("\"", 3))[1]);

						

						if (result2 == true) {
							//接続成功
							out.write("OK\n");
							out.flush();
						} else {
							//接続失敗
							out.write("fail\n");
							out.flush();
						}
					} else {
						//既にmsgTableに登録されている時 or 通信失敗時
						out.write("fail\n");
						out.flush();
					}

				}

			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません! " + e.toString());
				break;
			} catch (ArrayIndexOutOfBoundsException ae) {
				//送られてきたメッセージが短かすぎるとき（＝不正な時
				//'hoge' とかそういう時
				System.out.println("Invalid Message: " + ae.toString());
				break;
			}
		}
	}

}

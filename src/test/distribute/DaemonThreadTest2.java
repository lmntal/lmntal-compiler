package test.distribute;

//import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

//TODO 終了するようにする
//TODO TmpRuntimeのテスト

public class DaemonThreadTest2 {
	public static void main(String args[]) {
		Thread t1 = new Thread(new DaemonHontai2(60000));
		t1.start();
		
		Thread t2 = new Thread(new DaemonHontai2(60001));
		t2.start();
		
//		Thread r1 = new Thread(new TmpRuntime(100));
//		r1.start();
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
class DaemonHontai2 implements Runnable {

	ServerSocket servSocket = null;
	HashMap nodeTable = new HashMap();
	static HashMap registedRuntimeTable = new HashMap();
	static HashMap msgTable = new HashMap();

	public DaemonHontai2() {
		try {
			servSocket = new ServerSocket(GlobalConstants.LMNTAL_DAEMON_PORT);
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}
	
	public DaemonHontai2(int portnum) {
		try {
			servSocket = new ServerSocket(portnum);
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}

	public void run() {
		Socket tmpSocket;
		BufferedReader tmpInStream;
		BufferedWriter tmpOutStream;

		while (true) {
			try {
				tmpSocket = servSocket.accept();

				//入力stream			
				tmpInStream =
					new BufferedReader(
						new InputStreamReader(tmpSocket.getInputStream()));

				//出力stream
				tmpOutStream =
					new BufferedWriter(
						new OutputStreamWriter(tmpSocket.getOutputStream()));

				if (register(tmpSocket,
					new LMNtalNode(tmpInStream, tmpOutStream))) {
					//登録成功。
					Thread t2 =
						new Thread(
							new LMNtalDaemonThread2(tmpSocket, tmpInStream, tmpOutStream));
					t2.start();
				} else {
					//登録失敗。糸冬
					tmpInStream.close();
					tmpOutStream.close();
					tmpSocket.close();
				}

			} catch (IOException e) {
				System.out.println(
					"ERROR in DaemonHontai2.run() " + e.toString());
				break;
			}
		}
	}

	boolean register(Socket socket, LMNtalNode node) {
		System.out.println("register(" + socket.toString() + ", " + node.toString() + ")");
		
		synchronized(nodeTable){
			if (nodeTable.containsKey(socket)) {
				return false;
			}

			nodeTable.put(socket, node);
		}
		return true;
	}

	public static boolean registerLocal(Integer rgid, Socket socket){
		System.out.println("registerLocal(" + rgid + ", " + socket.toString() + ")");
		
		synchronized(registedRuntimeTable){
			if (registedRuntimeTable.containsKey(rgid)) {
				return false;
			}

			registedRuntimeTable.put(rgid, socket);
		}
		return true;
	}
	
	public static boolean registerMessage(Integer msgid, String msg){
		System.out.println("registerLocal(" + msgid + ", " + msg + ")");
		
		synchronized(msgTable){
			if (msgTable.containsKey(msgid)) {
				return false;
			}

			registedRuntimeTable.put(msgid, msg);
		}
		
		return true;
	}

	boolean disconnect(Socket socket) {
		LMNtalNode node = (LMNtalNode) nodeTable.get(socket);

		try {
			node.tmpInStream.close();
			node.tmpOutStream.close();
			socket.close();

			return true;
		} catch (Exception e) {

		}

		return false;
	}
}

class LMNtalDaemonThread2 implements Runnable {
	BufferedReader in;
	BufferedWriter out;
	Socket socket;

	public LMNtalDaemonThread2(Socket tmpSocket, BufferedReader inTmp, BufferedWriter outTmp) {
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
				//String input = new String("msgid \"localhost\" runtimegroupid connect");
				System.out.println("input: " + input);
				if (input == null) {
					break;
				}

				//ここからメッセージ処理部分
				Integer msgid;
				Integer rgid;
				boolean result;
				String command;
				String[] tmpString = new String[3];
				
				tmpString = input.split(" ", 3);
				
				if (tmpString[0].equalsIgnoreCase("res")){
					//res msgid 結果
					//直接戻せばよい
					
					
				} else if(tmpString[0].equalsIgnoreCase("registerlocal")){
					//registerlocal rgid
					//rgidとソケットを登録
					rgid = new Integer(tmpString[1]);
					result = DaemonHontai2.registerLocal(rgid, socket);
					if(result == true){
						//成功
						out.write("ok\n");
						out.flush();
					} else {
						//失敗
						out.write("fail\n");
						out.flush();
					}
				} else  {
					//msgidとみなす
					//msgid "FQDN" rgid メッセージ
					msgid = new Integer(tmpString[0]);
					
					
					//メッセージを登録
					result = DaemonHontai2.registerMessage(msgid, input);
					
					if(result == true){
						//登録成功
						
						//接続しに行く
						String fqdnNoQuote;

						StringTokenizer tokens = new StringTokenizer(tmpString[1], "\"");
						fqdnNoQuote = tokens.nextToken();
						System.out.println("fqdnNoQuote: " + fqdnNoQuote);

						try {
							Socket socket =
								new Socket(fqdnNoQuote, GlobalConstants.LMNTAL_DAEMON_PORT);

							//出力stream
							BufferedWriter out =
								new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream()));

							out.write(input);
							out.flush();
							out.close();
						} catch (Exception e) {
							System.out.println("ERROR in connect処理: " + e.toString());
						}
						
					} else {
						//既にmsgTableに登録されている時（とみなしていいのかな？）
						//FQDN == 自分自身 の時？
						
						//登録済みという事を返す
					}
					

					
					
					
				}
				

			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません! " + e.toString());
				break;
			} catch (ArrayIndexOutOfBoundsException ae){
				//送られてきたメッセージが短かすぎるとき（＝不正な時
				//'hoge' とかそういう時
				System.out.println("Invalid Message: " + ae.toString());
				break;
			}
		}
	}

}

class LMNtalNode {
	BufferedReader tmpInStream;
	BufferedWriter tmpOutStream;

	LMNtalNode(BufferedReader in, BufferedWriter out) {
		tmpInStream = in;
		tmpOutStream = out;
	}
}


class TmpRuntime implements Runnable {
	//テスト用ランタイムもどき
	//デーモンにつなぐだけ
	int rgid;
	
	TmpRuntime(int tmpRgid){
		rgid = tmpRgid;
	}
	
	public void run(){
		try{
			Socket socket = new Socket("localhost", GlobalConstants.LMNTAL_DAEMON_PORT);
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			regist(out);
			
			connect(out);
			
			Thread.sleep(1000);
			
			System.out.println(in.readLine());
			
		} catch (Exception e){
			System.out.println("ERROR in TmpRuntime.run()" + e.toString());
		}
		
		System.out.println("TmpRuntime.run() ーーーーーーー糸冬ーーーーーーー");
	}
	
	void regist(BufferedWriter out){
		//REGSITERLOCAL rgid
		String command = new String("registerlocal " + rgid);

		System.out.println("TmpRuntime.regist(): now omitting: " + command);
		try{
			out.write(command);
			out.flush();
		} catch (Exception e){
			System.out.println("ERROR in TmpRuntime.regist()" + e.toString());
		}
	}
	
	void connect(BufferedWriter out){
		//msgid "localhost" rgid connect
		int msgid = 10000;
		
		String command = new String(msgid + " \"localhost\" " + rgid + " connect");
		
		System.out.println("TmpRuntime.connect(): now omitting: " + command);
		try{
			out.write(command);
			out.flush();
		} catch (Exception e){
			System.out.println("ERROR in TmpRuntime.connect()" + e.toString());
		}
	}
}
package test.distribute;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
import java.net.*;
import java.io.*;

public class DaemonThreadTest {
	public static void main(String args[]){
			Thread t1 = new Thread(new DaemonHontai());
			t1.start();
	}
}


/**
 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
 * @author nakajima
 *
 */
class DaemonHontai implements Runnable {

	static int LMNTAL_DAEMON_PORT = 60000;
	ServerSocket servSocket = null;

	public DaemonHontai() {
		try {
			servSocket = new ServerSocket(LMNTAL_DAEMON_PORT);
		} catch (Exception e) {
			System.out.println("ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
		}
	}

	public void run() {
		try {
			while (true) {
				Thread t2 =
					new Thread(new LMNtalDaemonThread(servSocket.accept()));
				t2.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR in LMNtalDaemon.run() " + e.toString());
		}
	}
}

class LMNtalDaemonThread implements Runnable {
	Socket socket;

	public LMNtalDaemonThread(Socket tmpSocket) {
		socket = tmpSocket;
		System.out.println(
			"new LMNtalDaemonThread created using " + socket.toString());
	}

	public void run() {
		try {
			//入力stream			
			BufferedReader inStream =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			//出力stream
			BufferedWriter outStream =
				new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));

			//1周目
			outStream.write("LMNtalDaemon @ " + InetAddress.getLocalHost().toString());
			outStream.write("enter 'quit' to terminate the programme.\n");
			outStream.flush();

			//2週目以降。"quit"で終了。
			while (true) {
				String input = inStream.readLine();
				if (input.equals("quit")) {
					break;
				}
				System.out.println("input: " + input);

				outStream.write(input);
				outStream.flush();
			}

			//切断処理
			inStream.close();
			outStream.close();
			socket.close();
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemonThread.run()!!! " + e.toString());
		}
	}
}

package runtime;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
import java.net.*;
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
	 
	static int LMNTAL_DAEMON_PORT = 60000;
	ServerSocket servSocket = null;

	public LMNtalDaemon() {
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

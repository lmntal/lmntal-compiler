package test.distribute;

//import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class DummyRuntime implements Runnable {
	//テスト用ランタイムもどき
	//デーモンにつなぐだけ
	int rgid;

	DummyRuntime(int tmpRgid) {
		rgid = tmpRgid;
	}

	public void run() {
		try {
			Socket socket =
				new Socket("localhost", 60000);

			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			Thread.sleep(300);

			//REGISTERLOCAL rgid
			regist(out);
			Thread.sleep(300);
			System.out.println(in.readLine());

			//Thread.sleep(300);
			//msgid "localhost" rgid connect
			//
//			connect(out);
//			Thread.sleep(300);
//			System.out.println(in.readLine());
			
			//msgid "cure.ueda.info.waseda.ac.jp" rgid connect
//			connectCure(out);
//			Thread.sleep(300);
//			System.out.println(in.readLine());
			
			//msgid "cure.ueda.info.waseda.ac.jp" rgid connect
			connectHost(out, "banon.ueda.info.waseda.ac.jp");
			Thread.sleep(300);
			System.out.println(in.readLine());
			
			
			//hashmapの中身を吐く
			dumpHash(out);
			
		} catch (Exception e) {
			System.out.println("ERROR in TmpRuntime.run()" + e.toString());
		}

		System.out.println("TmpRuntime.run() ーーーーーーー糸冬ーーーーーーー");
	}

	void regist(BufferedWriter out) {
		//REGSITERLOCAL rgid
		String command = new String("registerlocal " + rgid + "\n");

		System.out.println("TmpRuntime.regist(): now omitting: " + command);
		try {
			out.write(command);
			out.flush();
		} catch (Exception e) {
			System.out.println("ERROR in TmpRuntime.regist()" + e.toString());
		}
	}

	void connect(BufferedWriter out) {
		//msgid "localhost" rgid connect
		int msgid = 10000;

		String command =
			new String(msgid + " \"localhost\" " + rgid + " connect\n");

		System.out.println("TmpRuntime.connect(): now omitting: " + command);
		try {
			out.write(command);
			out.flush();
		} catch (Exception e) {
			System.out.println("ERROR in TmpRuntime.connect()" + e.toString());
		}
	}

	void connectHost(BufferedWriter out, String fqdn) {
		//eg. msgid "cure.ueda.info.waseda.ac.jp" rgid connect
		int msgid = 10000;

		String command =
			new String(msgid + " \"" + fqdn + "\" " + rgid + " connect\n");

		System.out.println("TmpRuntime.connect(): now omitting: " + command);
		try {
			out.write(command);
			out.flush();
		} catch (Exception e) {
			System.out.println("ERROR in TmpRuntime.connect()" + e.toString());
		}
	}
	
	void dumpHash(BufferedWriter out){
		String command =
			new String("dumphash\n");

		System.out.println("TmpRuntime.connect(): now omitting: " + command);
		try {
			out.write(command);
			out.flush();
		} catch (Exception e) {
			System.out.println("ERROR in TmpRuntime.connect()" + e.toString());
		}
	}
}
package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import runtime.LocalLMNtalRuntime;
//import runtime.LMNtalRuntimeManager;

class SlaveLMNtalRuntimeLauncher {
	//TODO 名称変更。ここはLocalLMNtalRuntimeとLMNtalDaemonの間にいて、ローカルホスト内TCP通信の面倒を接続元がソケットを閉じるまで見続ける。
	
	static boolean DEBUG = true;
	
	static LocalLMNtalRuntime runtime;
	static String rgid;
	
	public static void main(String[] args){
		try {
			rgid = args[0];
			String callerMsgid = args[1];

			Socket socket = new Socket("localhost", 60000);

			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			//REGSITERLOCAL rgid
			String command = new String("registerlocal " + rgid + "\n");

			if(DEBUG)System.out.println("DummyRemoteRuntime.run(): now omitting: " + command);

			out.write(command);
			out.flush();

			String input;

			//最初の1回。
			input = in.readLine();
				
			if(input.equalsIgnoreCase("ok")){
				//connectを発行した元に対してres msgid okを返す
				command = "res " + callerMsgid + " ok\n";
				out.write(command);
				out.flush();
				
				//LocalLMNtalRuntimeを起動
				runtime = new LocalLMNtalRuntime();
		
				//メッセージ待ちに入る。
				//processMessage(in, out);
				//LMNtalRuntimeManager.terminateAll();
			} else {
				//connectを発行した元に対してres msgid failを返す
				command = "res " + callerMsgid + " fail\n";
				out.write(command);
				out.flush();
			}

		} catch (Exception e) {
			System.out.println("ERROR in DummyRemoteRuntime.run()" + e.toString());
			e.printStackTrace();
		}
	}
	
	static void processMessage(BufferedReader in, BufferedWriter out){
		String input = "";
		String inputParsed[] = new String[3];
		
		while(true){ 
			 //TODO 接続元がコネクションを切るまでまわりつづける(停止判定をする)
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				continue; //todo これでいいのかな
			}
			
			inputParsed = input.split(" ",3);
			
			if(inputParsed[0].equalsIgnoreCase("connect")){
				//CONNECT msgid
				//CONNECTが来たらokを返す
				try {
					out.write("res " + inputParsed[1] + " ok\n"); //todo inputParsed[1]が空だった時の対策
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Error in CONNECT");
				}
				
			//} else if(inputParsed[0].equalsIgnoreCase("")){
				
			} else {
				//cannot parse
				System.out.println("Error in SlaveLMNtalRuntimeLauncher.processMessage(): cannot parse input");
				continue;
			}
		}
	}
}

package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

import runtime.LocalLMNtalRuntime;
import runtime.LMNtalRuntimeManager;

class SlaveLMNtalRuntimeLauncher {
	//TODO 名称変更。ここはLocalLMNtalRuntimeとLMNtalDaemonの間にいて、ローカルホスト内TCP通信の面倒を接続元がソケットを閉じるまで見続ける。
	
	static boolean DEBUG = true;
	
	public static void main(String[] args){
		try {
			String callerMsgid = args[0];
			String rgid = args[1];

			Socket socket = new Socket("localhost", LMNtalDaemon.DEFAULT_PORT);
			LMNtalRuntimeMessageProcessor node = new LMNtalRuntimeMessageProcessor(socket,rgid);
			LMNtalRuntimeManager.daemon = node;
			
			Thread nodeThread = new Thread(node);
			nodeThread.start();
			if (node.sendWaitRegisterLocal("remote")) {
				//LocalLMNtalRuntimeを起動				
				LocalLMNtalRuntime runtime = new LocalLMNtalRuntime();
				node.respondAsOK(callerMsgid);	// node.runtimeidを返す場合、このresの引数にする
				//socketが切断するまで待つ
				nodeThread.join();
				//LMNtalRuntimeManager.terminateAll();
			}
		} catch (Exception e) {
			System.out.println("ERROR in DummyRemoteRuntime.run()" + e.toString());
			e.printStackTrace();
		}
	}
	/**@deprecated*/
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

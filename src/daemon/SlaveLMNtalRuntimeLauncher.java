package daemon;

import java.net.Socket;

import runtime.LocalLMNtalRuntime;
import runtime.LMNtalRuntimeManager;

class SlaveLMNtalRuntimeLauncher {
	//todo 名称変更。ここはLocalLMNtalRuntimeとLMNtalDaemonの間にいて、ローカルホスト内TCP通信の面倒を接続元がソケットを閉じるまで見続ける。
	
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
			if (node.sendWaitRegisterLocal("REMOTE")) {
				//LocalLMNtalRuntimeを起動				
				LocalLMNtalRuntime runtime = new LocalLMNtalRuntime();
				node.respondAsOK(callerMsgid);	// node.runtimeidを返す場合、このresの引数にする
				nodeThread.join(); //socketが切断するまで待つ
				LMNtalRuntimeManager.terminateAllNeighbors();
				LMNtalRuntimeManager.disconnectFromDaemon();
			}
		} catch (Exception e) {
			System.out.println("ERROR in DummyRemoteRuntime.run()" + e.toString());
			e.printStackTrace();
		}
	}
}

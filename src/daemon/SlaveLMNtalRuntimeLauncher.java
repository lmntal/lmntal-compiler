package daemon;

import java.net.Socket;

import runtime.Env;
import runtime.LocalLMNtalRuntime;
import runtime.LMNtalRuntimeManager;

class SlaveLMNtalRuntimeLauncher {
	//todo 名称変更。ここはLocalLMNtalRuntimeとLMNtalDaemonの間にいて、ローカルホスト内TCP通信の面倒を接続元がソケットを閉じるまで見続ける。
	
	public static void main(String[] args){
		try {
			//java -classpath classpath daemon.SlaveLMNtalRuntimeLauncher msgid rgid Env.debugDaemon 
			if(args.length < 2){
				System.out.println("Invalid option");
				System.exit(-1);
			}

			String callerMsgid = args[0];
			String rgid = args[1];
			try{
				int debug = Integer.parseInt(args[2]);
				Env.debugDaemon = debug;
			} catch (Exception e){
				System.out.println("Cannot parse as integer");
				e.printStackTrace();
				System.exit(-1);
			}

			Socket socket = new Socket("localhost", Env.daemonListenPort);
			LMNtalRuntimeMessageProcessor node = new LMNtalRuntimeMessageProcessor(socket,rgid);
			LMNtalRuntimeManager.daemon = node;
			
			Thread nodeThread = new Thread(node, "LMNtalRuntimeMessageProcessor");
			nodeThread.start();
			if (node.sendWaitRegisterLocal("SLAVE")) {
				//LocalLMNtalRuntimeを起動				
				LocalLMNtalRuntime runtime = new LocalLMNtalRuntime();
				node.respondAsOK(callerMsgid);	// node.runtimeidを返す場合、このresの引数にする
				nodeThread.join(); //socketが切断するまで待つ
				//LMNtalRuntimeManager.terminateAll(); //TODO (nakajima)計算が終了したらちゃんと終了するようにする
				//LMNtalRuntimeManager.disconnectFromDaemon();
			}
		} catch (Exception e) {
			System.out.println("ERROR in SlaveLMNtalRuntimeLauncher.run()" + e.toString());
			e.printStackTrace();
		}
	}
}

package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import runtime.LocalLMNtalRuntime;

class SlaveLMNtalRuntimeLauncher {
	static boolean DEBUG = true;
	
	static LocalLMNtalRuntime runtime;
	
	public static void main(String[] args){
		try {
			int rgid = Integer.parseInt(args[0]);
			int callerMsgid = Integer.parseInt(args[1]);
			
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
			} else {
				//TODO この部分実装
			}

		} catch (Exception e) {
			System.out.println("ERROR in DummyRemoteRuntime.run()" + e.toString());
			e.printStackTrace();
		}
		
		//LocalLMNtalRuntimeを起動
		runtime = new LocalLMNtalRuntime();
			
		//TODO この先何をすればいいのか
		
	}
}

package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class DummyRemoteRuntime{
	public static void main(String[] args){
		try {
			int rgid = Integer.parseInt(args[0]);
			
			Socket socket = new Socket("localhost", 60000);

			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			//REGSITERLOCAL rgid
			String command = new String("registerlocal " + rgid + "\n");

			System.out.println("DummyRemoteRuntime.run(): now omitting: " + command);

			out.write(command);
			out.flush();

			String input;
			while(true){
				input = in.readLine();
								
				
				if(input.equalsIgnoreCase("ok")){
					
				} else if(input.equalsIgnoreCase("res")){
										
				}
				
				
			}
		} catch (Exception e) {
			System.out.println("ERROR in DummyRemoteRuntime.run()" + e.toString());
		}
	}
}

package test;

import java.io.*;
import java.net.*;

class DaemonTest{
	
	public static void main(String args[]){
		try{
			ServerSocket servSocket = new ServerSocket(60000);
			Socket socket;

			socket = servSocket.accept();
			//接続があるとこの行以下に処理が進む
			System.out.println("ACK");
			
			
			//入力stream			
			BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//出力stream
			BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			StringBuffer inString = new StringBuffer();
			StringBuffer outString = new StringBuffer();
			
			outString.append("hello!\n\n");
			outStream.write(outString.toString());
			outString.delete(0, outString.length());
			
			
			inString.append(inStream.readLine());
			System.out.println("input: " + inString.toString());
			
			outString.append(inString.toString());
			outStream.write(outString.toString());
			
			outStream.flush();
			
		} catch (Exception e){
			System.out.println("ERROR!!!");
		}
	}

	
	
}
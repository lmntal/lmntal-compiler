package test.distribute;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashMap;

import runtime.Env;
import util.StreamDumper;

class Hoge {
	static String synchronizedTestObj = "hoge00";
	
	public static void main(String args[]){
		//TestStringTokenizer();
//		TestStringSplit();
//		TestStringSplit2();
//		DumpHashMap();
		//DumpHashMapNeat();
//		RuntimeBootTest();
//		privateIPhanteiTest();
		//privateIPhanteiTest2();
		//testLabel();
		//testProperty();
		//testChildProcessStream();
		//testInetAddress();
		//ServerSocketTest();
		//RuntimeTest();
		//UnknownCommandTest();
		SynchronizeTest();
	}

	
	
	/**
	 * synchronizedの中で、同じオブジェクトをsynchronizedしているメソッドを呼び出したらどうなるかやってみるテスト。
	 * デッドロックしないといいなorz
	 */
	private static void SynchronizeTest() {
		synchronized(synchronizedTestObj){
			SynchronizeTest2();
			synchronizedTestObj = "hoge02";
			System.out.println(synchronizedTestObj);
		}
	}

	/**
	 * 
	 */
	private static void SynchronizeTest2() {
		synchronized(synchronizedTestObj){
			System.out.println(synchronizedTestObj);
			synchronizedTestObj = "hoge01";
		}
	}



	/**
	 * Runtime.exec()でパスが通ってないコマンドを起動したらどうなるのかテスト
	 * 追記:IOExceptionが出ました
	 */
	private static void UnknownCommandTest() {
		String newCmdLine = new String("ghoehoeufejrlejrelkh");

		System.out.println("now trying to exec " + newCmdLine);
		
		Process slave;
		try {
			slave = Runtime.getRuntime().exec(newCmdLine);
			Thread dumpErr = new Thread(new StreamDumper(
					"slave runtime.error", slave
							.getErrorStream()));
			Thread dumpOut = new Thread(new StreamDumper(
					"slave runtime.stdout", slave
							.getInputStream()));
			dumpErr.start();
			dumpOut.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("command " +  newCmdLine +" should be running now");
	}
	/**
	 * Runtimeをいろいろ試してみる
	 */
	private static void RuntimeTest() {
		System.out.println(Runtime.getRuntime());
		
		String classpath = System.getProperty("java.class.path");
		String newCmdLine =
			new String(
					"java -classpath"   //todo javaコマンドにパスが通ってなかった時にエラー吐いて死ぬように
					+ " "
					+ classpath
					+ " "
					+"test.distribute.Hoge");

		Process slave;
		try {
			slave = Runtime.getRuntime().exec(newCmdLine);
			Thread dumpErr = new Thread(new StreamDumper("slave runtime.error", slave.getErrorStream()));
			Thread dumpOut = new Thread(new StreamDumper("slave runtime.stdout", slave.getInputStream()));
			dumpErr.start();
			dumpOut.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *	 *ソケットをいろいろ試してみる
	 */
	private static void ServerSocketTest() {
		ServerSocket servSocket;
		int portnum = 60000;
		try {
			servSocket = new ServerSocket(portnum);
			
			while (true) {
				Socket socket = servSocket.accept();
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getLocalSocketAddress());
				System.out.println(InetAddress.getLocalHost());
				break;
			}
			
		} catch (IOException e) {
			System.out.println(
				"ERROR in LMNtalDaemon.LMNtalDaemon() " + e.toString());
			e.printStackTrace();
		}
		
		
	}



	/**
	 * InetAddressを比較する方法はどれが一番いいか
	 * 
	 */
	private static void testInetAddress() {
		//InetAddress.equals()の動作を調べる。
		
		try {
			String ip1 = InetAddress.getLocalHost().getHostAddress();
			
			String ip2 =  "192.168.1.209";
			
			if(ip1.equals(ip2)){
				System.out.println("使える");
			} else {
				System.out.println("使えねー");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 子プロセスをあげて、それに対してgetOutputStream, getInputStream, getErrorStreamをやってみる。
	 */
	private static void testChildProcessStream() {
		try {
			Process childProcess = Runtime.getRuntime().exec("java test/distribute/HogeChild");

			InputStream childIn = childProcess.getInputStream();
			OutputStream childOut = childProcess.getOutputStream();
			InputStream childErr = childProcess.getErrorStream();
 
			System.out.println("fuga");
			
			int buffErr;
			int buffIn;
			while(true){
				//buff =  childIn.read(); //buff is -1 or 0-255(byteのint表現)
				buffErr = childErr.read();
				buffIn = childIn.read();
				if (buffErr == -1 && buffIn == -1){
					break;
				} else {
					System.out.print((char)buffErr);
					System.out.print((char)buffIn);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 */
	private static void testProperty() {
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("os.version"));
		
	}

	/**
	 * 
	 */
	private static void testLabel() {
		boolean flag = false;
		
		loop0:while(true){
			flag = true;
			
			loop1:while(true){
				flag = false;
								
				loop2:while(true){
					flag = true;
					
					if(flag){
						continue loop1;
					}
				}
			}
		}
		
	}

	/**
	 * 
	 */
	private static void privateIPhanteiTest2() {
		try {
			InetAddress test = InetAddress.getLocalHost();
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void privateIPhanteiTest() {
		try {
			System.out.println("The result of InetAddress.getLocalHost(): " + InetAddress.getLocalHost());
			System.out.println("The result of getHostAddress(): " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 */
	private static void RuntimeBootTest() {
		Process hoge;
		String tmp;

		try{
//			hoge = Runtime.getRuntime().exec("java -version");
//			hoge = Runtime.getRuntime().exec("echo hoge\n");
			//hoge = Runtime.getRuntime().exec("dir");
			hoge = Runtime.getRuntime().exec("help");
			BufferedReader in = new BufferedReader(new InputStreamReader(hoge.getInputStream()));

			while(true){
				tmp = in.readLine();
				if(tmp == null){
					break;
				} else {
					System.out.println(tmp);
				}
			}
			
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}

	static void DumpHashMapNeat(){
		HashMap map = new HashMap();

		for(int i = 0; i < 15; i++){
			map.put(new String("key" + i), new String("value" + i));
		}

		Set set = map.entrySet();		
		Iterator it = set.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());	
		}
	}
	
	static void DumpHashMap(){
		HashMap map = new HashMap();

		for(int i = 0; i < 15; i++){
			map.put(new String("key" + i), new String("value" + i));
		}

		Set set = map.entrySet();		
		System.out.println(set);
	}

	
	static void TestStringSplit2(){
		String testStr = "REGISTERLOCAL 20";

		String result[] = new String[3];

		try{
			result = testStr.split(" ", 3);

			for(int i = 0 ; i < result.length; i++){
				if(result[i] == null){
					System.out.println("null");
				} else {
					System.out.println("result[" + i + "] is: " + result[i]);
				}
			}
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	static void TestStringSplit(){
		String testStr = "10 \"banon\" 9 connect";
		
		String result[] = new String[3];
		
		result = testStr.split(" ", 3);
		
		for(int i = 0 ; i < result.length; i++){
			System.out.println(result[i]);
		}
		
	}
	
	static void TestStringTokenizer(){
		String testStr = new String("msgid \"banon\" rgid connect");
		
		StringTokenizer tokens = new StringTokenizer(testStr, " ");
		int msgid;
		
		while(true){
			if(tokens.hasMoreTokens()){
				System.out.println("token: " + tokens.nextToken());
			} else {
				break;
			}
		}
		
	}
	
}
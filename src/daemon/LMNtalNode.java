package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.net.Socket;

/**
 *  LMNtalにおけるノードを表現するクラス
 * 
 * とりあえずreader, writer, socket, ipを持っておく
 * 
 *  @author nakajima, n-kato 
 * */
public class LMNtalNode {
	Socket socket = null;
	InetAddress ip = null;
	BufferedReader in;
	BufferedWriter out;
	
//	public static LMNtalNode connect(String hostname, int port) {
//		try {
//			Socket socket = new Socket(hostname, port);
//			InetAddress ip = InetAddress.getByName(hostname);
//			return new LMNtalNode(socket, ip);
//		} catch (Exception e) {
//			return null;
//		}
//	}

	/** 通常のコンストラクタ */
	public LMNtalNode(Socket socket) {
		this(socket, socket.getInetAddress());		
	}

	public LMNtalNode(Socket socket, InetAddress ip) {
		try {
			this.ip = ip;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.socket = socket;
		} catch (Exception e) {}
	}
	
	private LMNtalNode(
		Socket socket,
		InetAddress ip,
		BufferedReader in,
		BufferedWriter out
		) {
		this.socket = socket;
		this.ip = ip;
		this.in = in;
		this.out = out;
	}

	public BufferedReader getInputStream() {
		return in;
	}

	public BufferedWriter getOutputStream() {
		return out;
	}

	public Socket getSocket() {
		return socket;
	}

	public InetAddress getInetAddress() {
		return ip;
	}

	public String toString() {
		return "LMNtalNode[IP:"
			+ ip
			+ ", "
			+ in.toString()
			+ ", "
			+ out.toString()
			+ "]";
	}
	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {}
	}
	
	////////////////////////////////////////////////////////////////
	

	/**
	 * ホストfqdnが自分自身か判定。
	 * 
	 * @param fqdn Fully Qualified Domain Name @return
	 * 自分自身に割り振られているIPアドレスからホスト名を引いて、fqdnとstringで比較。同じだったらtrue、それ以外はfalse。
	 */
	public static boolean isMyself(String fqdn) {
		try {
			return InetAddress.getLocalHost().getHostAddress().equals(
				InetAddress.getByName(fqdn).getHostAddress());
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "???";
		}
	}
	
	////////////////////////////////////////////////////////////////
	
	/*
	 * メッセージmessageを転送する
	 * 
	 * @param message メッセージ
	 * @return <strike>BufferedWriter.write()を呼んだらtrueを返している。</strike>
	 */
	public boolean sendMessage(String message) {
		try {
			out.write(message);
			out.flush();

			return true;
		} catch (Exception e) {
			System.out.println(
				"ERROR in LMNtalDaemon.sendMessage()");
			e.printStackTrace();
		}

		return false;
	}

	void respond(String msgid, String message){
		try {
			out.write("res " + msgid + " " + message + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respond()");
			e.printStackTrace();
		}
	}
	void respond(String msgid, boolean value) {
		respond(msgid, value ? "ok" : "fail");
	}
	void respondAsOK(String msgid){
		try {
			out.write("res " + msgid + " ok\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respondAsOK()");
			e.printStackTrace();
		}
	}

	
	void respondAsFail(String msgid){
		try {
			out.write("res " + msgid + " fail\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.respondAsFail()");
			e.printStackTrace();
		}
	}
	/*
	void send(String fqdn, String rgid, String command, String arg1){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}

	void send(String fqdn, String rgid, String command, String arg1, String arg2){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	void send(String fqdn, String rgid, String command, String arg1, String arg2, String arg3){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	void send(String fqdn, String rgid, String command, String arg1, String arg2, String arg3, String arg4){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	void send(String fqdn, String rgid, String command, String arg1, String arg2, String arg3, String arg4, String arg5){
		try {
			out.write(LMNtalDaemon.makeID() + " \"" + fqdn + "\" " + rgid + " " + command + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5 + "\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
		}
	}
	

	*/

}
package daemon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import runtime.Env;
import util.HybridInputStream;
import util.HybridOutputStream;

/** ソケット通信路を表すクラス。
 * <p>LMNtalDaemonMessageProcessor および LMNtalRuntimeMessageProcessor の親クラス。
 * @author nakajima, n-kato */

public class LMNtalNode {
	private Socket socket = null;
	private InetAddress ip = null;
	private HybridInputStream in;
	private HybridOutputStream out;
	
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
			in = new HybridInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new HybridOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			this.socket = socket;
		} catch (Exception e) {}
	}
	
	//
	
	public void close() {
		if(Env.debug > 0)System.out.println("LMNtalNode.close(): "+ socket);
		try {
			in.close();
			out.close();
			if(Env.debug > 0)System.out.println("LMNtalNode.close(): BufferedStreams closed.");
			socket.close();
			if(Env.debug > 0)System.out.println("LMNtalNode.close(): socket has closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////////////////////
	// 情報の取得
	
	public HybridInputStream getInputStream() {
		return in;
	}
	public HybridOutputStream getOutputStream() {
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
		
	/**
	 * ホストfqdnが自分自身か判定。
	 * 
	 * @param fqdn Fully Qualified Domain Name
	 * @return 自分自身に割り振られているIPアドレスからホスト名を引いて、fqdnと文字列比較した結果
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "???";
		}
	}
	
	////////////////////////////////
	// 送信用
	
	/**
	 * このLMNtalNodeが表すホストにメッセージを送信する
	 * @param message メッセージ
	 */
	public boolean sendMessage(String message) {
		try {
			out.write(message);
			out.flush();
			return true;
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalNode.sendMessage()");
			e.printStackTrace();
		}
		return false;
	}

	void respond(String msgid, String message){
		sendMessage("RES " + msgid + " " + message + "\n");
	}
	void respond(String msgid, boolean value) {
		respond(msgid, value ? "OK" : "FAIL");
	}
	void respondAsOK(String msgid){
		respond(msgid,"OK");
	}	
	void respondAsFail(String msgid){
		respond(msgid,"FAIL");
	}
	/** (n-kato)このメソッドを使わないように書き換えてもよい（仮）*/
	void respondRawData(String msgid, byte[] data) {
		respond(msgid, "RAW " + data.length + "\n" + data); // TODO 文字列結合していいのか調べる
	}
	////////////////////////////////
	// 受信用
	
	protected String readLine() throws IOException {
		return in.readLine();
	}
	protected byte[] readBytes() throws IOException {
		return in.readBytes();
	}
	
/*
 *  @author nakajima
 *  @return true if the socket has been closed 
 */
	protected boolean isSocketClosed() {
		return socket.isClosed();
	}
	
}
/*
 * 作成日: 2004/01/05
 *
 */
package util;

import java.net.*;
import java.io.*;
import runtime.Env;

/**
 * 簡易通信クラス。
 * 
 * @author hara
 *
 */
public class Net {
	static final int default_port = 10001;
	protected Socket sock;
	
	/**
	 * デフォルトポートに送信する。
	 * 
	 * @param addr サーバのアドレス
	 * @param s    送る内容
	 */
	public void send(String addr, String s) {
		send(addr, default_port, s);
	}
	/**
	 * 送信する。
	 * 
	 * @param addr サーバのアドレス
	 * @param port サーバのポート
	 * @param s    送る内容
	 */
	public void send(String addr, int port, String s) {
		try {
			sock = new Socket(addr, port);
			PrintWriter pw = new PrintWriter(sock.getOutputStream());
			pw.print(s);
			pw.close();
		} catch (Exception e) {
			Env.e(e);
		}
	}
	
	/**
	 * デフォルトポートから同期的に受信する。
	 * 
	 * @return
	 */
	public String recv() {
		return recv(default_port);
	}
	
	/**
	 * 同期的に受信する。
	 * 
	 * @param port 待ちうけポート
	 * @return
	 */
	public String recv(int port) {
		StringBuffer buf = new StringBuffer();
		try {
			ServerSocket ss = new ServerSocket(port);
			sock = ss.accept();
			BufferedReader br = new BufferedReader(
				new InputStreamReader(sock.getInputStream()));
			String res;
			while(null!=(res = br.readLine())) {
				recvHandler(res);
				buf.append(res).append("\n");
				//Env.p("<<< " + res);
			}
			return buf.toString();
		} catch(Exception e) {
			Env.e(e);
		}
		return buf.toString();
	}
	
	/** u can override */
	public void recvHandler(String line) {
	}
}


/*
 * 作成日: 2004/01/10
 *
 */
package test;

import java.io.PrintWriter;

import runtime.Env;
import util.Net;

/**
 * @author pa
 *
 */
public class NetTest {
	/** なぜかサーバ */
	public static void main(String[] args) {
		Net net=new Net() {
			public void recvHandler(String line) {
				Env.p(line);
				if(line.equals("")) {
					try {
						PrintWriter pw = new PrintWriter(sock.getOutputStream());
						pw.println("<html>That's all</html>");
						pw.close();
					} catch (Exception e) {
					}
				}
			}
		};
		if(args.length>0) {
			net.send("localhost", args[0]);
		} else {
			Env.p("received : "+net.recv());
		}
	}
}

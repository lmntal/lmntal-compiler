/*
 * Created on 2004/07/26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test.distribute;

import java.io.*;
import java.net.*;
import runtime.*;

/**
 * @author Ken
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RulesetReceiver {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(1234);
		Socket socket = ss.accept();
//		InputStream in = socket.getInputStream();
//		int a;
//		while ((a = in.read()) != -1) {
//			System.out.print((char)a);
//		}

		Env.verbose = Env.VERBOSE_SHOWRULES;
		Env.debug = 3;

		DataInputStream in = new DataInputStream(socket.getInputStream());
		while (true) {
			int size = in.readInt();
			if (size == 0) {
				break;
			}
			byte[] data = new byte[size];
			in.read(data);
			
			ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(data));
			InterpretedRuleset ruleset = (InterpretedRuleset)Ruleset.deserialize(in2);
			ruleset.showDetail();
		}
	}
}

package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;

/** @author nakajima */
public class LMNtalNode {
	InetAddress ip;
	BufferedReader in;
	BufferedWriter out;
	
	LMNtalNode(
		InetAddress ip,
		BufferedReader in,
		BufferedWriter out
		) {
		this.ip = ip;
		this.in = in;
		this.out = out;
	}

	BufferedReader getInputStream() {
		return in;
	}

	BufferedWriter getOutputStream() {
		return out;
	}

	InetAddress getInetAddress() {
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
}
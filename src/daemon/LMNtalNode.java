package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;

class LMNtalNode {
	InetAddress ip;
	BufferedReader in;
	BufferedWriter out;

	LMNtalNode(
		InetAddress tmpIp,
		BufferedReader tmpInStream,
		BufferedWriter tmpOutStream) {
		ip = tmpIp;
		in = tmpInStream;
		out = tmpOutStream;
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
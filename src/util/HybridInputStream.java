package util;
import java.io.*;
import java.util.StringTokenizer;

public class HybridInputStream {
	InputStream in;
	StringTokenizer bufTokenizer;

	public HybridInputStream(InputStream in) throws IOException {
		this.in = in;
	}
	
	public Object readObject() throws IOException, ClassNotFoundException {
		if (bufTokenizer != null && bufTokenizer.hasMoreTokens()) {
			//未読み込みの文字列データが残っている
			throw new IOException();
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(readByteArray());
		ObjectInputStream oin = new ObjectInputStream(bin);
		return oin.readObject();
	}
	
	public void close() throws IOException {
		in.close();
	}
	
	public String readLine() throws IOException {
		if (bufTokenizer == null || !bufTokenizer.hasMoreTokens()) {
			bufTokenizer = new StringTokenizer(new String(readByteArray()), "\r\n");
		}
		return bufTokenizer.nextToken();
	}
	
	private byte[] readByteArray() throws IOException {
		int size = readInt();
		byte[] data = new byte[size];
		in.read(data);
		return data;
	}
	private int readInt() throws IOException {
		int a1 = in.read();
		int a2 = in.read();
		int a3 = in.read();
		int a4 = in.read();
		return (a1 << 24) + (a2 << 16) + (a3 << 8) + a4;
	}
}

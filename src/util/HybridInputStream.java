package util;
import java.io.*;

public class HybridInputStream {
	InputStream in;
	String[] lines;
	int nextLine, lineCount;
	
	public HybridInputStream(InputStream in) throws IOException {
		this.in = in;
	}
	
	public Object readObject() throws IOException, ClassNotFoundException {
		if (lines != null && nextLine < lineCount) {
			//未読み込みの文字列データが残っている
			throw new IOException();
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(readBytes());
		ObjectInputStream oin = new ObjectInputStream(bin);
		return oin.readObject();
	}
	
	public void close() throws IOException {
		in.close();
	}
	
	public String readLine() throws IOException {
		if (lines == null || nextLine == lineCount) {
			lines = new String(readBytes()).split("\n", -1);
			nextLine = 0;
			lineCount = lines.length;
			if (lines[lineCount-1].equals("")) {
				//改行で終わっている場合、最後の改行の後は無視
				lineCount--;
			}
		}
		return lines[nextLine++];
	}
	
	public byte[] readBytes() throws IOException {
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

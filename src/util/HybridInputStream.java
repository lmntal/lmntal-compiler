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
		byte[] bytes = readBytes();
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ObjectInputStream oin = new ObjectInputStream(bin);
		return oin.readObject();
	}
	
	public void close() throws IOException {
		in.close();
	}
	
	public String readLine() throws IOException {
		if (lines == null || nextLine == lineCount) {
			byte[] bytes = readBytes();
			if (bytes == null) {
				return null;
			}
			lines = new String(bytes).split("\n", -1);
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
		if (size == -1) {
			return null;
		}
		byte[] data = new byte[size];
		int count = in.read(data);
		if (count != size) {
			throw new RuntimeException("failed to read all data");
		}
		return data;
	}
	private int readInt() throws IOException {
		int a1 = in.read();
		if (a1 == -1) {
System.out.println("end of stream");
			return -1;
		}
		int a2 = in.read();
		int a3 = in.read();
		int a4 = in.read();
		return (a1 << 24) + (a2 << 16) + (a3 << 8) + a4;
	}
}

package util;
import java.io.*;

public class HybridOutputStream {
	OutputStream out;

	public HybridOutputStream(OutputStream out) throws IOException {
		this.out = out;
	}
	
	public void writeObject(Object o) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(bout);
		oout.writeObject(o);
		oout.close();
		writeBytes(bout.toByteArray());
	}
	
	public void write(String str) throws IOException {
		writeBytes(str.getBytes());
	}

	public void flush() throws IOException {
		out.flush();
	}
	public void close() throws IOException {
		out.close();
	}
	public void writeBytes(byte[] data) throws IOException {
		writeInt(data.length);
		out.write(data);
	}
	
	private void writeInt(int val) throws IOException {
		//out.write()では、上位24ビットは無視される
		out.write(val >> 24);
		out.write(val >> 16);
		out.write(val >> 8);
		out.write(val);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		HybridOutputStream writer = new HybridOutputStream(new BufferedOutputStream(bout));

		String data = "12345678901234567890123456789012345678901234567890\n";
		writer.write(data);
		writer.writeObject(data);
		writer.write(data);
		writer.write(data);
		writer.writeObject(data);
		writer.writeObject(data);
		
		data = data + data;
		data = data + data;
		data = data + data;
		data = data + data;
		data = data + data;
		writer.write(data);
		writer.writeObject(data);
		
		writer.close();
		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		HybridInputStream reader = new HybridInputStream(new BufferedInputStream(bin));
		System.out.println(reader.readLine());
		System.out.println(reader.readObject());
		System.out.println(reader.readLine());
		System.out.println(reader.readLine());
		System.out.println(reader.readObject());
		System.out.println(reader.readObject());
		for (int i = 0; i < 32; i++) {
			System.out.println(reader.readLine());
		}
		System.out.println(reader.readObject());

		System.out.println(reader.readLine());
		System.out.println(reader.readLine());
	}
}

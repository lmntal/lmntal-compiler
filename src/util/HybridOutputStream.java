package util;
import java.io.*;

/**
 * バイト列と文字列の両方を１つのストリームに書き込むためのクラス。
 * 
 * @author Mizuno
 */
public class HybridOutputStream {
	OutputStream out;

	/**
	 * 指定されたストリームにデータを書き込むためのインスタンスを生成します。
	 * @param out データを書き込むOutputStream
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public HybridOutputStream(OutputStream out) throws IOException {
		this.out = out;
	}
	
	/**
	 * ストリームにオブジェクトを書き込みます。
	 * @param o 書き込むオブジェクト
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized void writeObject(Object o) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(bout);
		oout.writeObject(o);
		oout.close();
		writeBytes(bout.toByteArray());
		out.write('\n');
	}

	/**
	 * ストリームに文字列データを書き込みます。
	 * @param str 書き込む文字列。
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public void write(String str) throws IOException {
		writeBytes(str.getBytes());
	}

	/**
	 * ストリームをフラッシュします。
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized void flush() throws IOException {
		out.flush();
	}
	/**
	 * ストリームを閉じます。
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized void close() throws IOException {
		out.close();
	}
	/**
	 * ストリームにバイト列を書き込みます。
	 * @param data 書き込むバイト列
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized void writeBytes(byte[] data) throws IOException {
		writeInt(data.length);
		out.write(data);
	}
	
	/* writeBytesのなかでのみ呼ばれるのでsyncrhronizedはなくても良いはずだが、念のため*/
	private synchronized void writeInt(int val) throws IOException {
		//out.write()では、上位24ビットは無視される
		out.write(val >> 24);
		out.write(val >> 16);
		out.write(val >> 8);
		out.write(val);
	}

	/**
	 * テスト用エントリポイント。
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
		Util.println(reader.readLine());
		Util.println(reader.readObject());
		Util.println(reader.readLine());
		Util.println(reader.readLine());
		Util.println(reader.readObject());
		Util.println(reader.readObject());
		for (int i = 0; i < 32; i++) {
			Util.println(reader.readLine());
		}
		Util.println(reader.readObject());

		Util.println(reader.readLine());
		Util.println(reader.readLine());
	}
}

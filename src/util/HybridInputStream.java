package util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * バイト列と文字列の両方を１つのストリームから読み取るためのクラス。
 * 
 * @author Mizuno
 */
public class HybridInputStream {
	private InputStream in;
	private String[] lines;
	private int nextLine, lineCount;

	/**
	 * 指定されたストリームからデータを読み込むためのインスタンスを生成します。
	 * @param in データを読み込むInputStream
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public HybridInputStream(InputStream in) throws IOException {
		this.in = in;
	}

	/**
	 * ストリームからオブジェクトを読み取ります。
	 * @return 読み取ったオブジェクト
	 * @throws IOException 入出力エラーが発生した場合。読み取ったデータが文字列だった場合を含む。
	 * @throws ClassNotFoundException 読み取ったオブジェクトのクラスが見つからなかった場合。
	 */
	public synchronized Object readObject() throws IOException, ClassNotFoundException {
		if (lines != null && nextLine < lineCount) {
			//未読み込みの文字列データが残っている
			throw new IOException();
		}
		byte[] bytes = readBytes();
		if (bytes == null) {
			return null;
		}
		if (in.read() != '\n') {
			throw new IOException("\\n is expected after Object data");
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ObjectInputStream oin = new ObjectInputStream(bin);
		return oin.readObject();
	}

	/**
	 * ストリームを閉じます。
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized void close() throws IOException {
		System.out.println("HybridInputStream.close() entered");
		in.close();
	}

	/**
	 * ストリームから、１行分の文字列データを読み取ります。
	 * @return 読み取ったデータ。ストリームの終わりに達していた場合はnull
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized String readLine() throws IOException {
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

	/**
	 * ストリームから、１つのデータを表すバイト列を読み込みます。
	 * 「１つのデータ」とは、HybridOutputStreamクラスのwrite, writeObject, writeBytesのいずれかのメソッドを用いて１回で書き込んだデータです。
	 * @return 読み取ったバイト列
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	public synchronized byte[] readBytes() throws IOException {
		if (lines != null && nextLine < lineCount) {
			//未読み込みの文字列データが残っている
			throw new IOException();
		}
		int size = readInt();
		if (size == -1) {
			return null;
		}
		byte[] data = new byte[size];
		//in.readは読み込みがブロックすると途中で戻ってきてしまうので、
		//最後まで読むためにループをまわす必要がある。
		int index = 0;
		while (size != 0) {
			int count = in.read(data, index, size);
			size -= count;
			index += count;
		}
		return data;
	}
	/* readBytesの中でのみ呼ぶのでsyncronizedでなくても良いはずだが、念のため*/
	private synchronized int readInt() throws IOException {
		int a1 = in.read();
		if (a1 == -1) {
			//このメソッドで読むデータはバイト数を表す値なので、負になる事はない
			return -1;
		}
		int a2 = in.read();
		int a3 = in.read();
		int a4 = in.read();
		return (a1 << 24) + (a2 << 16) + (a3 << 8) + a4;
	}
}

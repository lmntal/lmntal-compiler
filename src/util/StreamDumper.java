package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

/*
 *  ストリームを受け取ってコンソールに出力するクラス。
 * Runtime.exec()で別プロセスにしてしまうと、コンソールに出なくなってデバッグに困るので。
 * 
 * @author nakajima
 *
 */
public class StreamDumper implements Runnable {
	private InputStream childIn;
	private String processName;
	private String[] lines;
	private int nextLine, lineCount;
	
	public StreamDumper(String processName, InputStream in){
		this.childIn = in;
		this.processName = processName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println("StreamDumper: now starting dumping the console log of: " + processName);
		
		String input;
		
		while(true){
			try {
				input = readLine();

				if (input == null){
				} else {
					System.out.println(processName + " : " +  input);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//pakuri from mizuno-kun's HybridInputStream
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
	public synchronized byte[] readBytes() throws IOException {
//		if (lines != null && nextLine < lineCount) {
//			//未読み込みの文字列データが残っている
//			throw new IOException();
//		}

		ArrayList data = new ArrayList();
		
		byte[] retByteArr;
		
		byte ret;

		while(true){
			ret = (byte)childIn.read();
			if(ret == -1 ) break;
			data.add(new Byte(ret));
		}
		
		if(data.size() == 0){
			return null;
		}
		
		retByteArr = new byte[data.size()];
		
		
		for ( int i = 0; i <  data.size();  i++ ){
			retByteArr[i] = ((Byte)data.get(i)).byteValue();
		}
		
		return retByteArr;
	}
}
package compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import runtime.Env;

/**
 * Translatorが吐くソースが一定以上になった場合に外部クラスとして
 * 分離するためのクラス。
 * @author Nakano
 *
 */
public class Ejector{
	///////////////////////////////////////////////////////////////////////////
	// 定数宣言
	
	/** 外部ファイルの上限サイズ */
	static final
	private int MAX_BUF = 51200;
	
	///////////////////////////////////////////////////////////////////////////

	/** 変換したファイルをおくディレクトリ */
	private File dir;
	/** 変換したファイルのパッケージ名 */
	private String packageName;
	private BufferedWriter masterWriter;
	private String className;
	private File outputFile;
	static
	private int serial;
	private int mySerial;
	private StringBuffer buf = new StringBuffer(MAX_BUF);
	private StringBuffer tmpbuf = new StringBuffer(MAX_BUF);
	
	///////////////////////////////////////////////////////////////////////////
	
	public Ejector(String cn, File d, BufferedWriter w, String p){
		packageName = p;
		className = cn;
		dir = d;
		masterWriter = w;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public void commit(){
			try {
				masterWriter.write("			//"+ className + "_" + mySerial +"\n");
				if(buf.length() == 0){
					serial++;
					mySerial = serial;
					masterWriter.write("			" + className + "_" + mySerial + ".exec(var, f);\n");
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		/** サイズが上限に達してなければバッファに追加 */
		if(buf.length() + tmpbuf.length() < MAX_BUF){
			buf.append(tmpbuf);
			tmpbuf = new StringBuffer(MAX_BUF / 10);
		}
		/** サイズが上限に達してれば外部ファイル生成後バッファに追加 */
		else{
			makeOutput();
			buf = new StringBuffer(MAX_BUF);
			commit();
		}
		
	}
	
	
	/**
	 * 書き出したい情報を渡すと、必要に応じて外部ファイルを生成する。
	 * @param data 書き出したい情報
	 */
	public void write(String data){
		tmpbuf.append(data);
	}
	
	/**
	 * 外部ファイルを生成する
	 */
	private void makeOutput(){
		if(buf.length() == 0){ return; }
		
		outputFile = new File(dir, className + "_" + mySerial + ".java");
		try {
			//System.out.println("eject(" + outputFile.getName() + "):" + buf.length());
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile), MAX_BUF);
			writer.write("package " + packageName + ";\n");
			writer.write("import runtime.*;\n");
			writer.write("import java.util.*;\n");
			writer.write("import java.io.*;\n");
			writer.write("import daemon.IDConverter;\n");
			writer.write("import module.*;\n");
			if(Env.profile == Env.PROFILE_ALL){ writer.write("import util.Util;\n"); }
			writer.write("public class " + className + "_" + serial + " {\n");
//			writer.write("	public " + className + "_" + serial + " (Object[] var, Functor[] f) {\n");
			writer.write("	static public void exec(Object[] var, Functor[] f) {\n");
			writer.write("		Atom atom;\n");
			writer.write("		Functor func;\n");
			writer.write("		Link link;\n");
			writer.write("		AbstractMembrane mem;\n");
			writer.write("		int x, y;\n");
			writer.write("		double u, v;\n");
			writer.write("		int isground_ret;\n");
			writer.write("		boolean eqground_ret;\n");
			writer.write("		boolean guard_inline_ret;\n");
			writer.write("		ArrayList guard_inline_gvar2;\n");
			writer.write("		Iterator it_guard_inline;\n");
			writer.write("		Set insset;\n");
			writer.write("		Set delset;\n");
			writer.write("		Map srcmap;\n");
			writer.write("		Map delmap;\n");
			writer.write("		Atom orig;\n");
			writer.write("		Atom copy;\n");
			writer.write("		Link a;\n");
			writer.write("		Link b;\n");
			writer.write("		Iterator it_deleteconnectors;\n");
			
			writer.flush();
			// データ書き出し
			writer.write(buf.toString(), 0, buf.length());
			writer.flush();
//			if(serial == 9)
//				System.out.println(buf.toString());
			
			writer.write("	}\n");
			writer.write("}\n");
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/**
	 * 最後の処理。
	 * バッファに残っているデータを書き出す。
	 *
	 */
	public void close(){
		if(buf.length() == 0){
			return;
		}
		
		makeOutput();
		serial++;
		mySerial = serial;
		buf = new StringBuffer(MAX_BUF);
		tmpbuf = new StringBuffer(MAX_BUF / 10);
		
	}
}
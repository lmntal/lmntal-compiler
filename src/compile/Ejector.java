package compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import compile.Translator.TranslatorWriter;

import runtime.Env;

/**
 * Translatorが吐くソースが一定以上になった場合に外部クラスとして
 * 分離するためのクラス。
 * <p>
 * Translatorが吐くソースのメソッド部分が、長くなりすぎないように、
 * 外部クラスとして分離する。ただし、分離できる命令と分離できない命令が
 * あるので、分離できない命令が出現した時点で一旦吐き出す。
 * </p>
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
	private String className;
	private File outputFile;
	static
	private int serial;
	private StringBuffer buf = new StringBuffer(MAX_BUF);
	private StringBuffer tmpbuf = new StringBuffer(MAX_BUF);
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	
	/**
	 * @param cn 生成するクラス名
	 * @param d ファイルを生成するディレクトリ
	 * @param p 生成するクラスのパッケージ名
	 */
	public Ejector(String cn, File d, String p){
		packageName = p;
		className = cn;
		dir = d;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 出力(分離)する命令に一旦区切りを付ける。
	 * @param myWriter 分離される元のファイルへ記述するためのwriter。
	 * 
	 * <p>
	 * 分離する命令に一旦区切りをつけ、外部ファイルとして出力するかどうか
	 * 判断する。Ejecotrのバッファに一定以上溜っていれば、外部ファイルを
	 * 出力し、次の入力を受け付ける準備を行う。
	 * </p>
	 */
	public void commit(TranslatorWriter myWriter){
		/** サイズが上限に達してなければバッファに追加 */
		if(buf.length() + tmpbuf.length() < MAX_BUF){
			buf.append(tmpbuf);
			tmpbuf = new StringBuffer(MAX_BUF / 10);
		}
		/** サイズが上限に達してれば外部ファイル生成後バッファに追加 */
		else{
			makeOutput(myWriter);
			buf = new StringBuffer(MAX_BUF);
			commit(myWriter);
		}
		
	}
	
	
	/**
	 * 書き出したい情報を渡す。
	 * @param data 書き出したい情報
	 * 
	 * <p>
	 * commitメソッドが呼ばれた時に出力されるクラスに記述される
	 * 命令を受け取る。
	 * </p>
	 */
	public void write(String data){
		tmpbuf.append(data);
	}
	
	/**
	 * 外部ファイルを生成する
	 */
	private void makeOutput(TranslatorWriter myWriter){
		if(buf.length() == 0){ return; }
		
		try {
			serial++;
			myWriter.superWrite("			//"+ className + "_" + serial +"\n");
			myWriter.superWrite("			" + className + "_" + serial + ".exec(var, f);\n");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		outputFile = new File(dir, className + "_" + serial + ".java");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile), MAX_BUF);
			writer.write("package " + packageName + ";\n");
			writer.write("import runtime.*;\n");
			writer.write("import java.util.*;\n");
			writer.write("import java.io.*;\n");
			writer.write("import daemon.IDConverter;\n");
			writer.write("import module.*;\n");
			if(Env.profile == Env.PROFILE_ALL){ writer.write("import util.Util;\n"); }
			writer.write("public class " + className + "_" + serial + " {\n");
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
	public void close(TranslatorWriter myWriter){
		if(buf.length() <= 0){
			return;
		}
		makeOutput(myWriter);
		buf = new StringBuffer(MAX_BUF);
		tmpbuf = new StringBuffer(MAX_BUF / 10);
		
	}
}
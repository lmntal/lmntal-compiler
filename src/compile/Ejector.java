package compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import runtime.Env;

import compile.Translator.TranslatorWriter;

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
public class Ejector
{
	///////////////////////////////////////////////////////////////////////////
	// 定数宣言

	/** 外部ファイルの上限サイズ */
	private static final int MAX_BUF = 51200;

	///////////////////////////////////////////////////////////////////////////

	private static int serial;

	/** 変換したファイルをおくディレクトリ */
	private File dir;
	/** 変換したファイルのパッケージ名 */
	private String packageName;
	private String className;
	private File outputFile;
	private StringBuilder buf = new StringBuilder(MAX_BUF);
	private StringBuilder tmpbuf = new StringBuilder(MAX_BUF);

	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ

	/**
	 * @param cn 生成するクラス名
	 * @param d ファイルを生成するディレクトリ
	 * @param p 生成するクラスのパッケージ名
	 */
	public Ejector(String cn, File d, String p)
	{
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
	public void commit(TranslatorWriter myWriter)
	{
		if (buf.length() + tmpbuf.length() < MAX_BUF)
		{
			// サイズが上限に達してなければバッファに追加
			buf.append(tmpbuf);
			tmpbuf = new StringBuilder(MAX_BUF / 10);
		}
		else
		{
			// サイズが上限に達してれば外部ファイル生成後バッファに追加
			makeOutput(myWriter);
			buf = new StringBuilder(MAX_BUF);
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
	public void write(String data)
	{
		tmpbuf.append(data);
	}

	/**
	 * 外部ファイルを生成する
	 */
	private void makeOutput(TranslatorWriter myWriter)
	{
		if (buf.length() == 0)
		{
			return;
		}

		try
		{
			serial++;
			myWriter.superWrite("\t\t\t//"+ className + "_" + serial +"\n");
			myWriter.superWrite("\t\t\t" + className + "_" + serial + ".exec(var, f);\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		outputFile = new File(dir, className + "_" + serial + ".java");
		try
		{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile), MAX_BUF));
			writer.println("package " + packageName + ";");
			writer.println("import runtime.*;");
			writer.println("import java.util.*;");
			writer.println("import java.io.*;");
//			writer.println("import daemon.IDConverter;\n");
			writer.println("import module.*;");
			if (Env.profile == Env.PROFILE_ALL)
			{
				writer.println("import util.Util;");
			}
			writer.println("@SuppressWarnings(\"unused\")");
			writer.println("public class " + className + "_" + serial + " {");
			writer.println("\tstatic public void exec(Object[] var, Functor[] f) {");
			writer.println("\t\tAtom atom;");
			writer.println("\t\tFunctor func;");
			writer.println("\t\tLink link;");
			writer.println("\t\tMembrane mem;");
			writer.println("\t\tint x, y;");
			writer.println("\t\tdouble u, v;");
			writer.println("\t\tint isground_ret;");
			writer.println("\t\tboolean eqground_ret;");
			writer.println("\t\tboolean guard_inline_ret;");
			writer.println("\t\tArrayList guard_inline_gvar2;");
			writer.println("\t\tIterator it_guard_inline;");
			writer.println("\t\tSet insset;");
			writer.println("\t\tSet delset;");
			writer.println("\t\tMap srcmap;");
			writer.println("\t\tMap delmap;");
			writer.println("\t\tAtom orig;");
			writer.println("\t\tAtom copy;");
			writer.println("\t\tLink a;");
			writer.println("\t\tLink b;");
			writer.println("\t\tIterator it_deleteconnectors;");

			writer.flush();
			// データ書き出し
			writer.write(buf.toString());
			writer.flush();

			writer.println("\t}");
			writer.println("}");
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 最後の処理。
	 * バッファに残っているデータを書き出す。
	 */
	public void close(TranslatorWriter myWriter)
	{
		if (buf.length() <= 0)
		{
			return;
		}
		makeOutput(myWriter);
		buf = new StringBuilder(MAX_BUF);
		tmpbuf = new StringBuilder(MAX_BUF / 10);
	}
}

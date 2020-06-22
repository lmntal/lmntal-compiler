package runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Util;

public final class Env
{
	/**
	 * LMNtal Compiler のバージョン (M.mm)
	 */
	public static final String LMNTAL_COMPILER_VERSION = "1.50";

	/**
	 * このバージョンをリリースした日付 (yyyy-MM-dd)
	 */
	public static final String RELEASE_DATE = "2019-07-10";

	/** -dオプション指定時のデフォルトのデバッグレベル */
	static final int DEBUG_DEFAULT = 1;
	/** 内部命令の実行をトレースするデバッグレベル */
	static final int DEBUG_TRACE = 2;
	/** システムルールセットの命令列実行を表示するデバッグレベル（仮） */
	static final int DEBUG_SYSTEMRULESET = 7;
	/** Debug level. */
	public static int debug = 0;

	////////////////////////////////////////////////////////////////

	/** 中間命令列を出力するモード。Java への変換や実行は行わない。 */
	//public static boolean compileonly = true;

	/** 履歴つきfindatomを含む中間命令列を出力するモード。 */
//	public static boolean findatom2 = false;

	/** 膜主導テストオンリーのモード。 */
//	public static boolean memtestonly = false;

	/** メモリ使用量を最小化する */
	public static boolean fMemory = true;

	public static boolean oneLine = false;
	public static String oneLineCode = "";

	/**
	 * <p>初期生成ルール以外のルールについて空ヘッド警告を出す。</p>
	 */
	public static boolean warnEmptyHead = false;

	/**
	 * <p>リンク操作に{@code swaplink}命令を使用する。</p>
	 */
	public static boolean useSwapLink = false;

	/**
	 * <p>リンク操作に{@code cyclelinks}命令を使用する。</p>
	 */
	public static boolean useCycleLinks = false;
	/**
	 * <p>SLIM内のデータ構造atomlistを動的に変化させて最適化を行う。</p>
	 */
	public static boolean useAtomListOP = false;
	/**
	 * <p>{@code swaplink/cyclelinks} 命令を使用する場合、コンパイル情報を標準エラー出力に出力する。</p>
	 */
	public static boolean verboseLinkExt = false;

	////////////////////////////////////////////////////////////////

	/** リンクの表示をL[数字]で表示する冗長表示レベル　<pre> a(_2) {@literal -->} a(L2) </pre> */
	public static final int VERBOSE_SIMPLELINK = 1;
	/** ルールセットの内容を1回だけ表示する冗長表示レベル　*/
	public static final int VERBOSE_SHOWRULES = 3;
//	/** 自由リンク管理アトムを表示する冗長表示レベル（EXPANDATOMS未満に限る）*/
//	public static final int VERBOSE_EXPANDPROXIES = 3;
	/** 演算子を展開する冗長表示レベル（EXPANDATOMS未満に限る） <pre> X+Y {@literal -->} '+'(X,Y) </pre> */
	public static final int VERBOSE_EXPANDOPS = 4;
	/** アトム引数を展開する冗長表示レベル <pre> a(b) {@literal -->} a(_2),b(_2) </pre> */
	public static final int VERBOSE_EXPANDATOMS = 5;
	/** ルールセットの内容を展開する冗長表示レベル */
	public static final int VERBOSE_EXPANDRULES = 6;

	/** -vオプション無指定時の冗長表示レベル */
	public static final int VERBOSE_INIT = 1;
	/** -vオプション指定時のデフォルトの冗長表示レベル */
	public static final int VERBOSE_DEFAULT = 5;
	/** verbose level. */
	public static int verbose = VERBOSE_INIT;

	public static boolean showlongrulename = false;

	/**
	 * プログラムに与える引数
	 */
	public static List<String> argv = new ArrayList<>();

	/**
	 * ソースファイル
	 */
	public static List<String> srcs = new ArrayList<>();

	/** デバッグ実行オプションの有無 by inui */
	public static boolean debugOption = false;

	/** 標準入力から LMNtal プログラムを読み込むオプション 2006.07.11 inui */
	public static boolean stdinLMN = false;

	/**
	 * ルール左辺に出現する、スレッド数の上限を設定
	 */
	public static int threadMax = 128;

	/**
	 * スレッド曖昧指定を使用したときの、変換後のルールをダンプ
	 */
	public static boolean dumpConvertedRules = false;

	public static boolean preProcess0 = false;

	/** アトム名の表示する長さ */
	public static int printLength = 14;

	// 以下に出力系の一文字メソッドが並んでいるが、これはデバッグ用と考えるべきではないか？
	// コンパイラのコード生成出力等で度々これらのメソッドが使われているが、それは本来好ましくない。

	/**
	 * General error report
	 * @param o
	 */
	public static void e(Object o) {
		e(o, 0);
	}

	/**
	 * General error report with indent
	 * @param o
	 * @param depth インデントの深さ
	 */
	public static void e(Object o, int depth) {
		Util.errPrintln(getIndent(depth) + o);
	}
	public static void e(Exception e) {
		e.printStackTrace(System.err);
	}

	/**
	 * General dumper for debug
	 * @param o Object to print
	 */
	public static void d(Object o) {
		d(o, 0);
	}

	/**
	 * Exception dumper for debug
	 * @param e
	 */
	public static void d(Exception e) {
		if(debug > 0) e.printStackTrace();
	}

	/**
	 * General dumper for debug with indent
	 * @param o
	 * @param depth インデントの深さ
	 */
	public static void d(Object o, int depth) {
		if(debug > 0) Util.println(getIndent(depth) + o);
	}

	/**
	 * General dumper
	 * @param o Object to print
	 */
	public static void p(Object o) {
		p(o, 0);
	}

	/**
	 * General dumper with indent
	 * @param o
	 * @param depth インデントの深さ
	 */
	public static void p(Object o, int depth) {
		Util.println(getIndent(depth) + o);
	}

	/**
	 * Debug output when some method called
	 * @param o method name
	 */
	public static void c(Object o) {
		//d(">>> "+o);
	}
	/**
	 * Debug output when new some object: write at constructor.
	 * @param o Class name
	 */
	public static void n(Object o) {
		d(">>> new "+o);
	}

	/**
	 * 走査可能なコレクションの各要素の文字列表現を区切り文字 {@code delim} で連結した文字列を返します。
	 */
	public static <E> String parray(Iterable<E> l, String delim)
	{
		StringBuilder s = new StringBuilder();
		boolean first = true;
		for (E e : l)
		{
			if (!first)
			{
				s.append(delim);
			}
			first = false;
			s.append(e);
		}
		return s.toString();
	}

	/**
	 * 走査可能なコレクションの各要素の文字列表現を半角空白で連結した文字列を返します。
	 */
	public static <E> String parray(Iterable<E> l)
	{
		return parray(l, " ");
	}

	/**
	 * 指定した数のインデントを返す
	 * @param depth
	 * @return
	 */
	public static String getIndent(int depth) {
		String indent="";
		for(int i=0;i<depth;i++) {
			indent += "\t";
		}
		return indent;
	}

	/**
	 * 拡張コマンドライン引数をこれに格納する
	 */
	public static Map<String, String> extendedOption = new HashMap<>();
	public static String getExtendedOption(String key) {
		if(!extendedOption.containsKey(key)) return "";
		return extendedOption.get(key);
	}

	////////////////////////////////////////////////////////////////
	/* type inference 関係 by kudo */
	/** 型システム on/off */
	public static boolean fType = false;

	/** 各推論の有効／無効 */
	public static boolean flgOccurrenceInference = false;
	public static boolean flgQuantityInference = true;
	public static boolean flgArgumentInference = true;

	/** 各生成膜を混ぜて効果を適用 */
	public static final int COUNT_MERGEANDAPPLY = 0;
	/** 各生成膜ごとに効果を適用してから混ぜる */
	public static final int COUNT_APPLYANDMERGE = 1;
	/** 各生成膜ごとに適用回数を解析 */
	public static final int COUNT_APPLYANDMERGEDETAIL = 2;
	/** default */
	public static final int COUNT_DEFAULT = COUNT_APPLYANDMERGEDETAIL;

	/** 個数の解析のレベル */
	public static int quantityInferenceLevel = COUNT_DEFAULT;

	/** 推論結果を表示 */
	public static boolean flgShowConstraints = false;

	////////////////////////////////////////////////////////////////

	private static int nErrors = 0;
	private static int nWarnings = 0;

	public static void clearErrors()
	{
		nErrors = 0;
		nWarnings = 0;
	}

	public static void error(String text)
	{
		Util.errPrintln(text);
		nErrors++;
	}

	public static void warning(String text)
	{
		Util.errPrintln(text);
		nWarnings++;
	}

	public static int getErrorCount()
	{
		return nErrors;
	}

	public static int getWarningCount()
	{
		return nWarnings;
	}

	/** 一つのルールのコンパイルを行う (for SLIM model checking mode) */
	public static boolean compileRule = false;

	/** hyperlink */
	public static boolean hyperLink    = false;//seiji
	public static boolean hyperLinkOpt = false;//seiji
}

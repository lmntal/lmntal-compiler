/*
 * 作成日: 2003/10/24
 *
 */
package runtime;
import graphic.LMNtalGFrame;
import toolkit.LMNtalTFrame; //todo

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 環境。デバッグ用。
 * @author hara
 */
public final class Env {

	public static final String LMNTAL_VERSION = "0.86.20061218";

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
	public static boolean compileonly = false;
	
	/** メモリ使用量を最小化する */
	public static boolean fMemory = true;

	/** 通常の実行 */
	public static final int ND_MODE_D = 0;
	/** 全履歴を管理する */
	public static final int ND_MODE_ND_ALL = 1;
	/** 先祖とその兄弟のみ */
	public static final int ND_MODE_ND_ANSCESTOR = 2;
	/** 一切行わない */
	public static final int ND_MODE_ND_NOTHING= 3;
	/** 非決定的LMNtalモード */
	public static int ndMode = ND_MODE_D;
	/** 非決定的LMNtalにおけるインタラクティブモード*/
	public static boolean fInteractive = false;
	
	public static boolean fThread = true;
	
	////////////////////////////////////////////////////////////////
	
	/** リンクの表示をL[数字]で表示する冗長表示レベル　<pre> a(_2) --> a(L2) </pre> */
	public static final int VERBOSE_SIMPLELINK = 1;
	/** ルールセットの内容を1回だけ表示する冗長表示レベル　*/
	public static final int VERBOSE_SHOWRULES = 3;
//	/** 自由リンク管理アトムを表示する冗長表示レベル（EXPANDATOMS未満に限る）*/
//	public static final int VERBOSE_EXPANDPROXIES = 3;
	/** 演算子を展開する冗長表示レベル（EXPANDATOMS未満に限る） <pre> X+Y --> '+'(X,Y) </pre> */
	public static final int VERBOSE_EXPANDOPS = 4;
	/** アトム引数を展開する冗長表示レベル <pre> a(b) --> a(_2),b(_2) </pre> */
	public static final int VERBOSE_EXPANDATOMS = 5;
	/** ルールセットの内容を展開する冗長表示レベル */
	public static final int VERBOSE_EXPANDRULES = 6;

	/** -vオプション無指定時の冗長表示レベル */
	public static final int VERBOSE_INIT = 1;
	/** -vオプション指定時のデフォルトの冗長表示レベル */
	public static final int VERBOSE_DEFAULT = 5;
	/** verbose level. */
	public static int verbose = VERBOSE_INIT;
	
	public static int indent = 0;
	
	public static boolean showrule = true;

	public static boolean showruleset = true;

	// PROXY を表示させない 2005/02/03 T.Nagata オプション --hideproxy
	// デフォルトで有効 2005/10/14 mizuno
	public static boolean hideProxy = true;
	
	////////////////////////////////////////////////////////////////

	/** 実行アトムスタックを使わないランダム実行レベル */
	public static final int SHUFFLE_DONTUSEATOMSTACKS = 1;
	/** ルールにマッチするアトムの選択をランダムにするランダム実行レベル */
	public static final int SHUFFLE_ATOMS = 2;
	/** ルールにマッチする膜の選択をランダムにするランダム実行レベル */
	public static final int SHUFFLE_MEMS = 2;
	/** 膜内のルールの選択をランダムにするランダム実行レベル */
	public static final int SHUFFLE_RULES = 3;
	// ** 全ての膜をルート膜にするランダム実行レベル（未実装。SHUFFLE_TASKS参照）
	//public static final int SHUFFLE_EVERYMEMISROOT = 4;
	// ** タスクをシャッフルするランダム実行レベル。
	// * 全ての膜がルート膜ならば、ルールを探しに行く膜の選択がランダムになる。
	// * しかし、各膜にあるルールの数を考慮に入れないと、ルールの選択はランダムにはならない。
	// *（未実装。というか、どのように実装すべきか不明）
	//public static final int SHUFFLE_TASKS = 5;

	/** -sオプション無指定時のランダム実行レベル */
	public static final int SHUFFLE_INIT  = 0;
	/** -sオプション指定時のデフォルトのランダム実行レベル */
	public static final int SHUFFLE_DEFAULT = 3;
	/** ランダム実行レベル */
	public static int shuffle = SHUFFLE_INIT;
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * プログラムに与える引数
	 */
	public static List<String> argv = new ArrayList<String>();
	
	/**
	 * ソースファイル
	 */
	public static List<String> srcs = new ArrayList<String>();
	
	/**
	 * 解釈実行
	 */
	public static boolean fInterpret = false;

	/**
	 * ライブラリ用Jarファイル生成
	 */
	public static boolean fLibrary = false;
	
	/**
	 * 未コンパイルライブラリを利用する
	 */
	public static boolean fUseSourceLibrary = false;
	
	/**
	 * トレース実行
	 */
	public static boolean fTrace = false;
	
	/** デバッグ実行オプションの有無 by inui */
	public static boolean debugOption = false;
	
	/** 標準入力から LMNtal プログラムを読み込むオプション 2006.07.11 inui */
	public static boolean stdinLMN = false;
	
	/** 標準入力から 中間命令列を読み込むオプション 2006.07.11 inui */
	public static boolean stdinTAL = false;
		
	/**
	 * REPL で、文を実行するためのアクション
	 *  null_line : null 行がきたときに実行（Enter を２回押すことになる）
	 *  immediate : 文の行がきたときに実行（Enter を１回押すことになる）
	 * hara
	 */
	public static String replTerm = "null_line";
	
	/**
	 * REPL で、特殊コマンドにつけるべきプレフィックス
	 * 例：この値 + "q" で終了
	 * hara
	 */
	public static String replCommandPrefix = ":";
	
	/**
	 * one liner
	 */
	public static String oneLiner;
	
	/** dumpをカラーにするモード */
	public static boolean colorMode = false;//2006.11.13 inui
	
	////////////////////////////////////////////////////////////////
	
	/** スレッドごとのアトム主導テスト、膜主導テストの実行時間測定 */
	public static final int PROFILE_BYDRIVEN = 0;
	/** ルールごとの実行時間、試行回数、適用回数を測定 */
	public static final int PROFILE_BYRULE = 1;
	/** ルールごとの実行時間、試行回数、適用回数、バックトラック回数、膜ロック失敗回数を測定 */
	public static final int PROFILE_BYRULEDETAIL = 2;
	/** ルールごとに、スレッド毎、テストの種類毎に測定 */
	public static final int PROFILE_ALL = 3;

	/** -profileオプション無指定時のプロファイル詳細度レベル */
	public static final int PROFILE_INIT  = -1;
	/** -profileオプション指定時のデフォルトのプロファイル詳細度レベル */
	public static final int PROFILE_DEFAULT = 0;
	/** ランダム実行レベル */
	public static int profile = PROFILE_INIT;

	////////////////////////////////////////////////////////////////

	public static int majorVersion = 0;
	public static int minorVersion = 0;
	
	/**
	 * ルール左辺に出現する、スレッド数の上限を設定
	 */
	public static int threadMax = 128;
	
	/**
	 * スレッド曖昧指定を使用したときの、変換後のルールをダンプ
	 */
	public static boolean dumpConvertedRules = false;
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static boolean fTool = false;
	public static LMNtalTFrame LMNtool;
	
	public static void initTool(){
		if(!Env.fTool) return;
		LMNtool = new LMNtalTFrame();
	}
	
	
	
	/**
	 * Graphic Mode 有効　nakano 
	 */
	public static boolean fGraphic = false;
	public static LMNtalGFrame LMNgraphic;
	
	public static void initGraphic(){
		if(!Env.fGraphic) return;
		LMNgraphic = new LMNtalGFrame();
	}
	
	/**
	 * 新 GUI モード。
	 */
	public static boolean fGUI = false;
	public static gui.LMNtalFrame gui;
	public static void initGUI(){
		if(!Env.fGUI){ return; }
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		gui = new gui.LMNtalFrame();
	}
	
	/**
	 * CGI モード
	 */
	public static boolean fCGI = false;
	
	/**
	 * REMAIN モード
	 */
	public static boolean fREMAIN = false;
	public static LMNtalRuntime remainedRuntime;
	
	/**
	 * REPL モード
	 */
	public static boolean fREPL = false;
	
	/**
	 * 060804
	 * safe mode
	 */
	public static boolean safe = false;
	
	/**
	 * 060804
	 * safe mode
	 */
	public static int maxStep = 1000;
	
	/**
	 * 060804
	 * safe mode
	 */
	public static int counter = 0;

	public static boolean preProcess0 = false;
	
	/** アトム名の表示する長さ */
	public static int printLength = 14;
	
	////////////////////////////////////////////////////////////////
	// 分散
	
	/** start LMNtalDaemon.*/
	public static boolean startDaemon = false;
	
	/**The debug level of LMNtalDaemon.*/
	public static int debugDaemon = 0;
	
	/** The default port number that LMNtalDaemon listens on.*/
	static final int DAEMON_DEFAULT_LISTENPORT = 60000;
	
	/** The port number that LMNtalDaemon listens on.*/
	public static int daemonListenPort = DAEMON_DEFAULT_LISTENPORT;
	
	////////////////////////////////////////////////////////////////
	
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
		System.err.println(getIndent(depth) + o);
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
		if(debug > 0) System.out.println(getIndent(depth) + o);
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
		System.out.println(getIndent(depth) + o);
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
	 * Better list dumper : No comma output
	 */
	public static String parray(Collection l, String delim) {
		StringBuffer s = new StringBuffer();
		for(Iterator i=l.iterator();i.hasNext();) {
			s.append( i.next().toString()+(i.hasNext() ? delim:"") );
		}
		return s.toString();
	}
	public static String parray(Collection l) {
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
	
	/** LMNtalRuntimeのインスタンス */
	public static LMNtalRuntime theRuntime;

	/** @return ルールスレッドの実行を継続してよいかどうか */
	public static boolean guiTrace() {
		if(gui == null) return true;
		if(null != gui){
			gui.onTrace();
		}
		
		return true;
	}

	/**
	 * 拡張コマンドライン引数をこれに格納する
	 */
	public static Map extendedOption = new HashMap();
	public static String getExtendedOption(Object key) {
		if(!extendedOption.containsKey(key)) return "";
		return extendedOption.get(key).toString();
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
	
	/** 推論された全ての情報を表示 */
	public static boolean flgShowAllConstraints = false;
	
	/** 推論結果を表示 */
	public static boolean flgShowConstraints = false;
	
	////////////////////////////////////////////////////////////////
	
	public static int nErrors = 0;
	public static int nWarnings = 0;
	public static void clearErrors() {
		nErrors = 0;
		nWarnings = 0;
	}
	public static void error(String text) {
		System.err.println(text);
		nErrors++;
	}
	public static void warning(String text) {
		System.err.println(text);
		nWarnings++;
	}
	
	//編み上げ
	public static boolean fMerging = false;
}

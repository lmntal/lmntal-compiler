/*
 * 作成日: 2003/10/24
 *
 */
package runtime;
import java.util.*;

import test.GUI.*;

/**
 * 環境。デバッグ用。
 * @author hara
 */
public final class Env {

	/** -dオプション指定時のデフォルトのデバッグレベル */
	static final int DEBUG_DEFAULT = 1;
	/** 内部命令の実行をトレースするデバッグレベル */
	static final int DEBUG_TRACE = 2;
	/** システムルールセットの命令列実行を表示するデバッグレベル（仮） */
	static final int DEBUG_SYSTEMRULESET = 7;
	/** Debug level. */
	public static int debug = 0;

	////////////////////////////////////////////////////////////////

	/** 命令列のインライニングを行う最適化レベル */
	public static final int OPTIMIZE_INLINING = 1;
	/** 冗長な命令を除去する最適化レベル */
	public static final int OPTIMIZE_RE = 1;
	/** 検査命令を持ち上げる最適化レベル */
	public static final int OPTIMIZE_FF = 1;
	/** Optimization level. */
	public static int optimize = 0;
	
	////////////////////////////////////////////////////////////////
	
	/** ルールセットの内容を1回だけ表示する冗長表示レベル　*/
	public static final int VERBOSE_SHOWRULES = 3;
	/** 自由リンク管理アトムを表示する冗長表示レベル（EXPANDATOMS未満に限る）*/
	public static final int VERBOSE_EXPANDPROXIES = 3;
	/** 演算子を展開する冗長表示レベル（EXPANDATOMS未満に限る） <pre> X+Y --> '+'(X,Y) </pre> */
	public static final int VERBOSE_EXPANDOPS = 4;
	/** アトム引数を展開する冗長表示レベル <pre> a(b) --> a(_2),b(_2) </pre> */
	public static final int VERBOSE_EXPANDATOMS = 5;
	/** ルールセットの内容を展開する冗長表示レベル */
	public static final int VERBOSE_EXPANDRULES = 6;

	/** -vオプション無指定時の冗長表示レベル */
	public static final int VERBOSE_INIT = 2;
	/** -vオプション指定時のデフォルトの冗長表示レベル */
	public static final int VERBOSE_DEFAULT = 5;
	/** verbose level. */
	public static int verbose = VERBOSE_INIT;

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
	public static final int SHUFFLE_INIT  = 1;
	/** -sオプション指定時のデフォルトのランダム実行レベル */
	public static final int SHUFFLE_DEFAULT = 3;
	/** ランダム実行レベル */
	public static int shuffle = SHUFFLE_INIT;
	
	////////////////////////////////////////////////////////////////
	
	public static List argv = new ArrayList();
	
	/**
	 * トレース実行
	 */
	public static boolean fTrace = false;
	
	/**
	 * one liner
	 */
	public static String oneLiner;
	
	/**
	 * GUI 有効。仮仮仮仮
	 */
	public static boolean fGUI = false;
	public static LMNtalFrame gui;
	public static boolean fDEMO;
	public static int atomSize;
	
	public static void initGUI() {
		if(!Env.fGUI) return;
		atomSize = Env.fDEMO ? 40 : 16;
		Env.gui = new LMNtalFrame();
	}
	
	/**
	 * CGI モード
	 */
	public static boolean fCGI = false;
	
	/** アトム名の表示する長さ */
	public static int printLength = 14;
	
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
	
	/** LocalLMNtalRuntimeのインスタンス */
	public static LocalLMNtalRuntime theRuntime;

	public static void guiTrace() {
		if(gui==null) return;
		gui.onTrace();
	}
	
	/**
	 * 拡張コマンドライン引数をこれに格納する
	 */
	public static Map extendedOption = new HashMap();
	public static String getExtendedOption(Object key) {
		if(!extendedOption.containsKey(key)) return "";
		return extendedOption.get(key).toString();
	}
}

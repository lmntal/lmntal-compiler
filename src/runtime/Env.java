/*
 * 作成日: 2003/10/24
 *
 */
package runtime;
import java.util.*;

/**
 * 環境。デバッグ用。
 * @author hara
 */
public final class Env {
	/**
	 * Debug level.
	 */
	public static int debug = 0;
	
	/** システムルールセットの命令列実行を表示する */
	static final int DEBUG_SYSTEMRULESET = 7;
	/** -dオプションして維持のデフォルトのデバッグレベル */
	static final int DEBUG_DEFAULT = 1;
	/** 内部命令の実行をトレースするデバッグレベル */
	static final int DEBUG_TRACE = 2;
	/**
	 * Optimization level.
	 */
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

	public static final int SHUFFLE_DONTUSEATOMSTACKS = 1;
	public static final int SHUFFLE_ATOMS = 2;
	public static final int SHUFFLE_RULES = 3;

	public static final int SHUFFLE_INIT  = 1;
	public static final int SHUFFLE_DEFAULT = 3;
	/** ランダム実行（レベル）*/
	public static int shuffle = SHUFFLE_INIT;

	////////////////////////////////////////////////////////////////

	/**
	 * トレース実行
	 */
	public static boolean fTrace = false;
		
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
	
	/**
	 * General dumper for debug
	 * @param o Object to print
	 */
	public static void d(Object o) {
		d(o, 0);
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
}

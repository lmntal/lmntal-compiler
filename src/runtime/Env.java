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
	/** デフォルトのデバッグレベル */
	static final int DEBUG_DEFAULT = 1;
	
	/**
	 * Optimization level.
	 */
	public static int optimize = 0;
	
	public static final int VERBOSE_DEFAULT = 2;
	/**
	 * アトム引数を展開する冗長レベル */
	public static final int VERBOSE_EXPANDATOMS = 3;
	/**
	 * ルールセットの内容を展開する冗長レベル */
	public static final int VERBOSE_EXPANDRULES = 4;
	/**
	 * verbose level.
	 */
	public static int verbose = VERBOSE_DEFAULT;
	
	/**
	 * ランダム実行
	 */
	public static boolean fRandom = false;

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

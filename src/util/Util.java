package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import runtime.Atom;
import runtime.Env;
import runtime.Link;
import runtime.functor.Functor;
import runtime.functor.IntegerFunctor;
import runtime.functor.SymbolFunctor;

/**
 * @author mizuno
 * 汎用ユーティリティメソッド・定数を集めたクラス
 */
abstract public class Util
{
	public static Functor DOT = new SymbolFunctor(".", 3);
	public static Functor NIL = new SymbolFunctor("[]", 1);
	public static final Iterator NULL_ITERATOR = Collections.EMPTY_SET.iterator();
	private Util(){}
	public static void systemError(String msg) {
		System.err.println(msg);
		System.exit(1);
	}
	
	public static void errPrint(String msg){
		System.err.print(msg);
	}
	
	public static void errPrintln(Object msg)
	{
		System.err.println(msg);
	}
	
	public static void println(Object msg)
	{
		System.out.println(msg);
	}
	
	public static void print(Object msg)
	{
		System.out.print(msg);
	}
	
	/**
	 * LMNtal リストをうけとり、Object配列にして返す。リストは消さない。
	 * @param link
	 * @return
	 */
	public static String[] arrayOfList(Link link, String s) {
		Object v [] = arrayOfList(link);
		String vs[] = new String[v.length];
		for(int i=0;i<v.length;i++) {
			vs[i] = (String)v[i];
		}
		return vs;
	}
	public static Object[] arrayOfList(Link link) {
		List<Object> l = new ArrayList<Object>();
		while(true) {
			Atom a = link.getAtom();
			if(!a.getFunctor().equals(DOT)) break;
//			System.out.println(a);
//			System.out.println(a.getArg(0).getAtom().getFunctor().getValue().getClass());
			l.add(a.getArg(0).getAtom().getFunctor().getValue());
			link = a.getArg(1);
		}
//		System.out.println("list = "+l);
		return l.toArray();
	}
	
	/**
	 * リストかどうかを返す
	 * @param link
	 * @return
	 */
	public static boolean isList(Link link) {
		Atom a;
		while(true) {
			a = link.getAtom();
			if(a.getFunctor().equals(NIL)) return true;
			if(!a.getFunctor().equals(DOT)) return false;
			link = a.getArg(1);
		}
	}
	
	/**
	 * リスト中の最大値を求め、result に IntegerFunctor としてセットする
	 * @param link
	 * @param result
	 * @return
	 */
	public static boolean listMax(Link link, Atom result) {
		int max = Integer.MIN_VALUE;
		boolean b=true;
		Atom a;
		while(true) {
			a = link.getAtom();
			if(a.getFunctor().equals(NIL)) {
				break;
			}
			if(!a.getFunctor().equals(DOT)) {
				b=false;
				break;
			}
			if(!(a.nthAtom(0).getFunctor() instanceof IntegerFunctor)) {
				b=false;
				break;
			}
			int v = ((IntegerFunctor)a.nthAtom(0).getFunctor()).intValue();
			if(max < v) max=v;
			link = a.getArg(1);
		}
		result.setFunctor(new IntegerFunctor(max));
		return b;
	}
	
	/**
	 * リスト中の最小値を求め、result に IntegerFunctor としてセットする
	 * @param link
	 * @param result
	 * @return
	 */
	public static boolean listMin(Link link, Atom result) {
		int min = Integer.MAX_VALUE;
		boolean b=true;
		Atom a;
		while(true) {
			a = link.getAtom();
			if(a.getFunctor().equals(NIL)) {
				break;
			}
			if(!a.getFunctor().equals(DOT)) {
				b=false;
				break;
			}
			if(!(a.nthAtom(0).getFunctor() instanceof IntegerFunctor)) {
				b=false;
				break;
			}
			int v = ((IntegerFunctor)a.nthAtom(0).getFunctor()).intValue();
			if(min > v) min=v;
			link = a.getArg(1);
		}
		result.setFunctor(new IntegerFunctor(min));
		return b;
	}
	
	/**
	 * アトムbase が link1 のリスト中に含まれるかどうかを返す 
	 * @param link
	 * @param result
	 * @return
	 */
	public static boolean listMember(Atom base, Link link1) {
		Functor v = base.getFunctor();
		boolean b=false;
		Atom a;
		while(true) {
			a = link1.getAtom();
			if(a.getFunctor().equals(NIL)) {
				break;
			}
			if(!a.getFunctor().equals(DOT)) {
				b=false;
				break;
			}
			if(a.nthAtom(0).getFunctor().equals(v)) {
				b=true;
				break;
			}
			link1 = a.getArg(1);
		}
		return b;
	}
	
	/**
	 * 指定された文字列を表す文字列リテラルのテキスト表現を取得する。
	 * 特殊文字と quoter を、バックスラッシュでエスケープする。
	 * @param text 変換する文字列
	 * @param quoter クォート文字。普通は " や ' を使う。
	 * @return 変換後の文字列
	 */ 
	public static String quoteString(String text, char quoter) {
		text = text.replaceAll("\\\\", "\\\\\\\\");
		text = text.replaceAll("" + quoter, "\\\\" + quoter);
		text = text.replaceAll("\n", "\\\\n");
		text = text.replaceAll("\t", "\\\\t");
		text = text.replaceAll("\f", "\\\\f");
		text = text.replaceAll("\r", "\\\\r");
		return quoter + text + quoter;
	}
	
	public static long getTime(){
		return System.nanoTime();
	}
}

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
	public static Machine machine = new Machine();
	public static Membrane rootMembrane = new Membrane(machine);
	
	/**
	 * General dumper
	 * @param o Object to print
	 */
	public static void p(Object o) {
		System.out.println(o);
	}
	/**
	 * Debug output when some method called
	 * @param o method name
	 */
	public static void c(Object o) {
		p(">>> "+o);
	}
	/**
	 * Debug output when new some object: write at constructor.
	 * @param o Class name
	 */
	public static void n(Object o) {
		p(">>> new "+o);
	}
	/** Better list dumper : No comma */
	public static String parray(List l) {
		StringBuffer s = new StringBuffer();
		for(ListIterator li=l.listIterator();li.hasNext();) {
			s.append( li.next().toString()+(li.hasNext() ? " ":"") );
		}
		return s.toString();
	}
	public static String getIndent(int indents) {
		String indent="";
		for(int i=0;i<indents;i++) {
			indent += "\t";
		}
		return indent;
	}
}

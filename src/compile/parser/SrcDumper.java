/**
 * Src*.javaの内容を出力する
 */

package compile.parser;
import java.util.LinkedList;

public class SrcDumper {
	
	protected SrcDumper() {} //インスタンスはない
	
	public static String dumpLink(SrcLink link) {
		return link.getName();
	}
	
	public static String dumpAtom(SrcAtom atom) {
		String s = atom.getSourceName();
		if (atom.getProcess().size() > 0) {
			s += "(" + dumpLinkedList(atom.getProcess()) + ")";
		}
		return s;
	}

	public static String dumpLinkedList(LinkedList list) {
		if (list == null) return "";
		String s ="";
		s += dump(list.get(0));
		for (int i=1;i<list.size();i++) s += ", " + dump(list.get(i));
		return s;
		
	}
	
	public static String dumpMembrane(SrcMembrane mem) {
		String s = "{";
		if (mem.getProcess().size() > 0)
			s += dumpLinkedList(mem.getProcess());
		s += "}";
		if (mem.kind==1) s += "_";
		if (mem.stable) s += "/";
		if (mem.nondeterministic) s += "*";
		if (mem.pragma != null)  s += dump(mem.pragma);
		return s;
	}
	
	public static String dumpRule(SrcRule rule) {
		String s = "(";
		if (rule.name != null) s += rule.name + " @@ ";
		if (rule.getHead() != null && rule.getHead().size() > 0)
			s += dumpLinkedList(rule.getHead());
		s += " :- ";
		if (rule.getGuard().size() > 0)
			s += dumpLinkedList(rule.getGuard()) + " | ";
		s += dumpLinkedList(rule.getBody()) + ")";
		return s;
	}
	
	public static String dumpProcessContext(SrcProcessContext p) {
		String s = "$"+p.getName();
		if (p.args != null || p.bundle != null) {
			s += "[";
			if (p.args.size() > 0) s += dumpLinkedList(p.args);
			if (p.bundle != null)  s += "|" + p.bundle.getQualifiedName();
			s += "]";
		}
		return s;
	}
	
	public static String dumpRuleContext(SrcRuleContext p) {
		String s = "@"+p.getName();
		return s;
	}

	public static String dump(Object obj) {
		if (obj instanceof SrcLink) {
			return dumpLink((SrcLink)obj);
		}
		else if (obj instanceof SrcAtom) {
			return dumpAtom((SrcAtom)obj);
		}
		else if (obj instanceof SrcMembrane) {
			return dumpMembrane((SrcMembrane)obj);
		}
		else if (obj instanceof SrcRule) {
			return dumpRule((SrcRule)obj);
		}
		else if (obj instanceof SrcProcessContext) {
			return dumpProcessContext((SrcProcessContext)obj);
		}
		else if (obj instanceof SrcRuleContext) {
			return dumpRuleContext((SrcRuleContext)obj);
		}
		else if (obj instanceof LinkedList) {
			return dumpLinkedList((LinkedList)obj);
		}
		return "";
	}
}
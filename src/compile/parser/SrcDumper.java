/**
 * Src*.javaの内容を出力する
 */

package compile.parser;
import java.util.LinkedList;

public class SrcDumper {
	
	private static final String INDENT_STRING = "    ";
	private static final String BR = "\n";
	
	protected SrcDumper() {
			
	}
	
	public static String dumpIndent(int indent) {
		String s = "";
		for (int i=0;i<indent;i++) s += INDENT_STRING;
		return s;
	}
	
	public static String dumpSpace(int indent) {
		String s = "";
		for (int i=0;i<indent;i++) s += " ";
		return s;
	}
	
	public static String dumpLink(SrcLink link,int indent) {
		return dumpIndent(indent)+link.getName()+BR;
	}
	
	public static String dumpAtom(SrcAtom atom, int indent) {
		String s = (dumpIndent(indent)+atom.getName() + "(" + BR);
		s += dumpLinkedList(atom.getProcess(), indent+1);
		s += (dumpIndent(indent)+")"+BR);
		return s;
	}

	public static String dumpLinkedList(LinkedList list,int indent) {
		if (list == null) return "";
		String s ="";
		for (int i=0;i<list.size();i++) s += dump(list.get(i),indent+1);
		return s;
		
	}
	
	public static String dumpMembrane(SrcMembrane mem, int indent) {
		String s = (dumpIndent(indent)+"{" + BR);
		s += dumpLinkedList(mem.getProcess(), indent+1);
		s += (dumpIndent(indent)+"}"+BR);
		return s;
	}
	
	public static String dumpRule(SrcRule rule, int indent) {
		String s = (dumpIndent(indent)+"("+BR);
		s += (dumpLinkedList(rule.getHead(),indent+1));
		s += (dumpIndent(indent+1)+":-"+BR);
		s += (dumpLinkedList(rule.getBody(),indent+1));
		s += (dumpIndent(indent)+")"+BR);
		return s;
	}
	
	public static String dumpProcessContext(SrcProcessContext p, int indent) {
		String s = (dumpIndent(indent)+"$"+p.getName()+BR);
		return s;
	}
	
	public static String dumpRuleContext(SrcRuleContext p, int indent) {
		String s = (dumpIndent(indent)+"@"+p.getName()+BR);
		return s;
	}

	public static String dump(Object obj,int indent) {
		if (obj instanceof SrcLink) {
			return dumpLink((SrcLink)obj,indent);
		}
		else if (obj instanceof SrcAtom) {
			return dumpAtom((SrcAtom)obj, indent);
		}
		else if (obj instanceof SrcMembrane) {
			return dumpMembrane((SrcMembrane)obj, indent);
		}
		else if (obj instanceof SrcRule) {
			return dumpRule((SrcRule)obj, indent);
		}
		else if (obj instanceof SrcProcessContext) {
			return dumpProcessContext((SrcProcessContext)obj, indent);
		}
		else if (obj instanceof SrcRuleContext) {
			return dumpRuleContext((SrcRuleContext)obj, indent);
		}
		else if (obj instanceof LinkedList) {
			return dumpLinkedList((LinkedList)obj, indent);
		}
		return "";
	}
}
package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import runtime.AbstractMembrane;
import runtime.Atom;
import runtime.Functor;
import runtime.Link;

/**
 * @author mizuno
 * 汎用ユーティリティメソッド・定数を集めたクラス
 */
abstract public class Util {
	public static Functor DOT = new Functor(".", 3);
	public static final Iterator NULL_ITERATOR = Collections.EMPTY_SET.iterator();
	public static void systemError(String msg) {
		System.err.println(msg);
		System.exit(-1);
	}
	
	/**
	 * 与えられたリストから LMNtal リストを生成して与えられたリンク先につなげる
	 * 
	 * @param parent
	 * @param pos
	 * @param l
	 */
	public static void makeList(Link link, List l) {
		Iterator it = l.iterator();
		AbstractMembrane mem = link.getAtom().getMem();
		Atom parent=null;
		boolean first=true;
		while(it.hasNext()) {
			Atom c = mem.newAtom(new Functor(".", 3));  // .(Value Next Parent)
			Atom v = mem.newAtom(new Functor(it.next().toString(), 1)); // value(Value)
			mem.newLink(c, 0, v, 0);
			if(first) {
				mem.inheritLink(c, 2, link);
			} else {
				mem.newLink(c, 2, parent, 1);
			}
			parent = c;
			first=false;
		}
		Atom nil = mem.newAtom(new Functor("[]", 1));
		if(first) {
			mem.inheritLink(nil, 0, link);
		} else {
			mem.newLink(nil, 0, parent, 1);
		}
	}
	/**
	 * LMNtal リストをうけとり、Object配列にして返す。リストは消さない。
	 * @param link
	 * @return
	 */
	public static Object[] arrayOfList(Link link) {
		List l = new ArrayList();
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
}

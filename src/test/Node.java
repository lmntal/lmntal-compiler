/* 
 Èó·èÄê¤È¡¢¤³¤Î¥é¥¤¥Ö¥é¥ê¤òÁÈ¤ß¹ç¤ï¤»¤Æ
 on-the-fly Model Checking ¤ò¹Ô¤¦¤³¤È¤òÌÜÉ¸¤È¤¹¤ë¡¥
 --Java ¤Ç½ñ¤¤¤¿¥Ð¡¼¥¸¥ç¥ó--
 1. translate LTL into Automata
 2. generate system Automata
 3. DDFS

 struct node = (ID: int, Incoming: Membrane, Old: Membrane, New: Membrane, Next: Membrane)
 */

/*
 % example
 a = until(a,until(b,c)).

 % put the formula into negational normal form
 // F ¦× ¢Î True U ¦×
 H = future(Psi) :- H = until(true,Psi).

 // G ¦× ¢Î False R ¦×
 H = global(Psi) :- H = release(false,Psi).

 // ¢Ì(¦Ì U ¦Ç) = (¢Ì¦Ì) R (¢Ì¦Ç)
 H = not(until(Mu,Eta)) :- H = release(not(Mu,Eta)).

 // ¢Ì(¦Ì R ¦Ç) = (¢Ì¦Ì) U (¢Ì¦Ç)
 H = not(release(Mu,Eta)) :- H = until(not(Mu),not(Eta)).

 // ¢ÌX¦Ì = X¢Ì¦Ì
 H = not(next(Mu)) :- H = next(not(Mu)).

 % translate LTL into Automata
 */

//[:/*inline_define*/
package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import runtime.Atom;
import runtime.Link;
import util.Util;

//struct node
public class Node {
	public final int id;

	public ArrayList<Integer> incoming;

	HashSet<Link> older;

	HashSet<Link> newer;

	HashSet<Link> next;

	public Node(int id, ArrayList<Integer> incoming, HashSet<Link> older,
			HashSet<Link> newer, HashSet<Link> next) {
		this.id = id;
		this.incoming = incoming;
		this.older = older;
		this.newer = newer;
		this.next = next;
	}

	public void print() {
		Util.println("ID: " + this.id);
		Util.println("Incoming: " + this.incoming);
		Iterator<Link> oldi = this.older.iterator();
		Util.print("Old:");
		while (oldi.hasNext())
			Util.print(" " + oldi.next().getAtom());
		Util.print("\n");
		Iterator<Link> newi = this.newer.iterator();
		Util.print("New:");
		while (newi.hasNext())
			Util.print(" " + newi.next().getAtom());
		Util.print("\n");
		Iterator<Link> nexti = this.next.iterator();
		Util.print("Next:");
		while (nexti.hasNext())
			Util.print(" " + nexti.next().getAtom());
		Util.print("\n");
	}

	public void printv() {
		Util.println("---\n" + "ID: " + this.id);
		Util.println("Incoming: " + this.incoming);
		Iterator<Link> oldi = this.older.iterator();
		Util.println("Old:");
		while (oldi.hasNext())
			Util.println(" " + traverse(oldi.next()));
		Iterator<Link> newi = this.newer.iterator();
		Util.println("New:");
		while (newi.hasNext())
			Util.println(" " + traverse(newi.next()));
		Iterator<Link> nexti = this.next.iterator();
		Util.println("Next:");
		while (nexti.hasNext())
			Util.println(" " + traverse(nexti.next()));
	}

	public String traverse(Link root) {
		Atom now = root.getAtom();
		int arity = now.getEdgeCount();
		StringBuffer buf = new StringBuffer();
		buf.append(now.getName());
		if (arity == 1) // leaf
			return buf.toString();
		else {
			buf.append("(");
			for (int i = 0; i < arity - 1; i++) {
				buf.append(traverse(now.getArg(i)));
				if (i < arity - 2)
					buf.append(",");
			}
			buf.append(")");
			return buf.toString();
		}
	}
}

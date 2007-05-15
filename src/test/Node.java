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
		System.out.println("ID: " + this.id);
		System.out.println("Incoming: " + this.incoming);
		Iterator<Link> oldi = this.older.iterator();
		System.out.print("Old:");
		while (oldi.hasNext())
			System.out.print(" " + oldi.next().getAtom());
		System.out.print("\n");
		Iterator<Link> newi = this.newer.iterator();
		System.out.print("New:");
		while (newi.hasNext())
			System.out.print(" " + newi.next().getAtom());
		System.out.print("\n");
		Iterator<Link> nexti = this.next.iterator();
		System.out.print("Next:");
		while (nexti.hasNext())
			System.out.print(" " + nexti.next().getAtom());
		System.out.print("\n");
	}

	public void printv() {
		System.out.println("---\n" + "ID: " + this.id);
		System.out.println("Incoming: " + this.incoming);
		Iterator<Link> oldi = this.older.iterator();
		System.out.println("Old:");
		while (oldi.hasNext())
			System.out.println(" " + traverse(oldi.next()));
		Iterator<Link> newi = this.newer.iterator();
		System.out.println("New:");
		while (newi.hasNext())
			System.out.println(" " + traverse(newi.next()));
		Iterator<Link> nexti = this.next.iterator();
		System.out.println("Next:");
		while (nexti.hasNext())
			System.out.println(" " + traverse(nexti.next()));
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

package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import runtime.*;

import runtime.Link;

//function expand
public class Tableau {
	public static int counter = 0;

	public static HashSet<Node> expand(Node q, HashSet<Node> nodes) {

		if (q.newer.isEmpty()) { // New(q) is empty
			Iterator<Node> it = nodes.iterator();
			while (it.hasNext()) {
				Node r = it.next();
				if (r.older.equals(q.older) && r.next.equals(q.next)) {
					r.incoming.addAll(q.incoming);
					return nodes;
				}
			}
			ArrayList<Integer> incoming = new ArrayList<Integer>();
			incoming.add(q.id);
			HashSet<Link> older = new HashSet<Link>();
			HashSet<Link> newer = new HashSet<Link>(q.next);
			HashSet<Link> next = new HashSet<Link>();
			Node newNode = new Node(counter++, incoming, older, newer, next);
			nodes.add(q);
			return expand(newNode, nodes);
		} else { // New(q) is not empty
			Iterator<Link> it = q.newer.iterator();
			Link eta = it.next();
			it.remove();
			/** * eta is in Old(q) or not ** */
			if (q.older.contains(eta))
				return expand(q, nodes);
			Atom etaAtom = eta.getAtom();
			/** * case 1: eta is a proposition or the negation of a proposition ** */
			if (etaAtom.getFunctor().getArity() == 1
					|| etaAtom.getName().equals("not")) { // proposition
				if (etaAtom.getFunctor().getArity() == 1) {
					if (etaAtom.getName().equals("false"))
						return (nodes);
					Iterator<Link> oldIte = q.older.iterator();
					while (oldIte.hasNext()) {
						Atom oldAtom = oldIte.next().getAtom();
						if (oldAtom.getName().equals("not"))
							if (oldAtom.nthAtom(0).getFunctor().equals(
									etaAtom.getFunctor()))
								return (nodes);
					}
				} else { // negation of a proposition
					if (etaAtom.nthAtom(0).getName().equals("true"))
						return (nodes);
					Iterator<Link> oldIte = q.older.iterator();
					while (oldIte.hasNext()) {
						Atom oldAtom = oldIte.next().getAtom();
						if (oldAtom.getFunctor().equals(
								etaAtom.nthAtom(0).getFunctor()))
							return (nodes);
					}
				}
				ArrayList<Integer> incoming = new ArrayList<Integer>(q.incoming);
				HashSet<Link> older = new HashSet<Link>(q.older);
				older.add(eta);
				HashSet<Link> newer = new HashSet<Link>(q.newer);
				HashSet<Link> next = new HashSet<Link>(q.next);
				Node newNode = new Node(counter++, incoming, older, newer, next);
				nodes.remove(q);
				return expand(newNode, nodes);
			}
			/** * case2: eta is of the form until(mu,psi) ** */
			else if (etaAtom.getName().equals("until")) {
				// q1
				ArrayList<Integer> incoming1 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> old1 = new HashSet<Link>(q.older);
				old1.add(eta);
				HashSet<Link> newer1 = new HashSet<Link>(q.newer);
				newer1.add(etaAtom.getArg(0));
				HashSet<Link> next1 = new HashSet<Link>(q.next);
				next1.add(eta);
				Node newNode1 = new Node(counter++, incoming1, old1, newer1,
						next1);
				// q2
				ArrayList<Integer> incoming2 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> old2 = new HashSet<Link>(q.older);
				old2.add(eta);
				HashSet<Link> newer2 = new HashSet<Link>(q.newer);
				newer2.add(etaAtom.getArg(1));
				HashSet<Link> next2 = new HashSet<Link>(q.next);
				Node newNode2 = new Node(counter++, incoming2, old2, newer2,
						next2);
				nodes.remove(q);
				return expand(newNode2, expand(newNode1, nodes));
			}
			/** * case3: eta is of the form release(mu,psi) ** */
			else if (etaAtom.getName().equals("release")) {
				// q1
				ArrayList<Integer> incoming1 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> older1 = new HashSet<Link>(q.older);
				older1.add(eta);
				HashSet<Link> newer1 = new HashSet<Link>(q.newer);
				newer1.add(etaAtom.getArg(0));
				newer1.add(etaAtom.getArg(1));
				HashSet<Link> next1 = new HashSet<Link>(q.next);
				Node newNode1 = new Node(counter++, incoming1, older1, newer1,
						next1);
				// q2
				ArrayList<Integer> incoming2 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> older2 = new HashSet<Link>(q.older);
				older2.add(eta);
				HashSet<Link> newer2 = new HashSet<Link>(q.newer);
				newer2.add(etaAtom.getArg(1));
				HashSet<Link> next2 = new HashSet<Link>(q.next);
				next2.add(eta);
				Node newNode2 = new Node(counter++, incoming2, older2, newer2,
						next2);
				nodes.remove(q);
				return expand(newNode2, expand(newNode1, nodes));
			}
			/** * case4: eta is of the form or(mu,psi) ** */
			else if (etaAtom.getName().equals("or")) {
				// q1
				ArrayList<Integer> incoming1 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> older1 = new HashSet<Link>(q.older);
				older1.add(eta);
				HashSet<Link> newer1 = new HashSet<Link>(q.newer);
				newer1.add(etaAtom.getArg(0));
				HashSet<Link> next1 = new HashSet<Link>(q.next);
				Node newNode1 = new Node(counter++, incoming1, older1, newer1,
						next1);
				// q2
				ArrayList<Integer> incoming2 = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> older2 = new HashSet<Link>(q.older);
				older2.add(eta);
				HashSet<Link> newer2 = new HashSet<Link>(q.newer);
				newer2.add(etaAtom.getArg(1));
				HashSet<Link> next2 = new HashSet<Link>(q.next);
				Node newNode2 = new Node(counter++, incoming2, older2, newer2,
						next2);
				nodes.remove(q);
				return expand(newNode2, expand(newNode1, nodes));
			}
			/** * case5: eta is of the form and(mu,psi) ** */
			else if (etaAtom.getName().equals("and")) {
				ArrayList<Integer> incoming = new ArrayList<Integer>(q.incoming);
				HashSet<Link> older = new HashSet<Link>(q.older);
				older.add(eta);
				HashSet<Link> newer = new HashSet<Link>(q.newer);
				newer.add(etaAtom.getArg(0));
				newer.add(etaAtom.getArg(1));
				HashSet<Link> next = new HashSet<Link>(q.next);
				Node newNode = new Node(counter++, incoming, older, newer, next);
				nodes.remove(q);
				return expand(newNode, nodes);
			}
			/** * case6: eta is of the form next(mu) ** */
			else /* if (etaAtom.getName().equals("next")) */{
				ArrayList<Integer> incoming = new ArrayList<Integer>(
						q.incoming);
				HashSet<Link> older = new HashSet<Link>(q.older);
				older.add(eta);
				HashSet<Link> newer = new HashSet<Link>(q.newer);
				HashSet<Link> next = new HashSet<Link>(q.next);
				next.add(etaAtom.getArg(0));
				Node newNode = new Node(counter++, incoming, older, newer, next);
				nodes.remove(q);
				return expand(newNode, nodes);
			}
		}
	}

	public static void toAutomaton(Membrane mem, HashSet<Node> nodes) {
		Iterator<Node> nodeIte = nodes.iterator();
		while (nodeIte.hasNext()) {
			Node tmpNode = nodeIte.next();
			int tmpNodeId = tmpNode.id;
			boolean existsSrc = false;
			boolean existsDst = false;

			Atom srcIdNameAtom = null;
			Iterator idNameAtomIte = mem
					.atomIteratorOfFunctor(new SymbolFunctor("id", 2));
			while (idNameAtomIte.hasNext()) { // src stateが存在するかどうか
				srcIdNameAtom = (Atom) idNameAtomIte.next();
				if (((IntegerFunctor) srcIdNameAtom.nthAtom(1).getFunctor())
						.intValue() == tmpNodeId) {
					existsSrc = true;
					break;
				}
			}
			Membrane srcMem;
			if (!existsSrc) {
				// src stateが存在しなかった場合
				srcMem = mem.newMem();
				srcIdNameAtom = mem.newAtom(new SymbolFunctor("id", 2));
				Atom newSrcIdAtom = mem.newAtom(new IntegerFunctor(tmpNodeId));
				Atom out = mem.newAtom(Functor.OUTSIDE_PROXY);
				Atom in = srcMem.newAtom(Functor.INSIDE_PROXY);
				Atom plus = srcMem.newAtom(new SymbolFunctor("+", 1));
				mem.newLink(newSrcIdAtom, 0, srcIdNameAtom, 1);
				mem.newLink(srcIdNameAtom, 0, out, 1);
				mem.newLink(out, 0, in, 0);
				srcMem.newLink(in, 1, plus, 0);
				// node.older
				Iterator<Link> oldIte = tmpNode.older.iterator();
				while (oldIte.hasNext()) {
					srcMem.newAtom(new SymbolFunctor(oldIte.next().getAtom()
							.getFunctor().getName(), 0));
				}
			} else {
				// src stateが存在する場合
				srcMem = srcIdNameAtom.nthAtom(0).nthAtom(0).getMem();
			}
			Iterator<Integer> incomingIte = tmpNode.incoming.iterator();
			while (incomingIte.hasNext()) {
				int incomingId = incomingIte.next();
				// System.out.println("edge: " + incomingId + "->" + tmpNodeId);
				Atom newDstIdNameAtom = null;
				Iterator dstIdNameAtomIte = mem
						.atomIteratorOfFunctor(new SymbolFunctor("id", 2));
				while (dstIdNameAtomIte.hasNext()) { // dst state が存在するかどうか
					newDstIdNameAtom = (Atom) dstIdNameAtomIte.next();
					if (((IntegerFunctor) newDstIdNameAtom.nthAtom(1)
							.getFunctor()).intValue() == incomingId) {
						existsDst = true;
						// System.out.println("exists: " + incomingId + "->" +
						// tmpNodeId);
						break;
					}
				}
				Membrane dstMem;
				if (!existsDst) {
					// dst stateが存在しなかった場合
					dstMem = mem.newMem();
					newDstIdNameAtom = mem.newAtom(new SymbolFunctor("id", 2));
					Atom newDstIdAtom = mem.newAtom(new IntegerFunctor(
							incomingId));
					Atom out = mem.newAtom(Functor.OUTSIDE_PROXY);
					Atom in = dstMem.newAtom(Functor.INSIDE_PROXY);
					Atom plus = dstMem.newAtom(new SymbolFunctor("+", 1));
					mem.newLink(newDstIdAtom, 0, newDstIdNameAtom, 1);
					mem.newLink(newDstIdNameAtom, 0, out, 1);
					mem.newLink(out, 0, in, 0);
					dstMem.newLink(in, 1, plus, 0);
				} else {
					dstMem = newDstIdNameAtom.nthAtom(0).nthAtom(0).getMem();
				}
				// System.out.println("make: " + incomingId + "->" + tmpNodeId);
				makeEdge(mem, srcMem, dstMem);
			}
		}
	}

	// refactor ok
	private static void makeEdge(Membrane mem, Membrane srcMem, Membrane destMem) {
		Atom to = srcMem.newAtom(new SymbolFunctor("to", 1));
		Atom from = destMem.newAtom(new SymbolFunctor("from", 1));
		Atom srcOut = srcMem.newAtom(Functor.OUTSIDE_PROXY);
		Atom srcIn = srcMem.newAtom(Functor.INSIDE_PROXY);
		Atom dstOut = destMem.newAtom(Functor.OUTSIDE_PROXY);
		Atom dstIn = destMem.newAtom(Functor.INSIDE_PROXY);
		srcMem.newLink(to, 0, srcIn, 1);
		mem.newLink(srcIn, 0, srcOut, 0);
		mem.newLink(srcOut, 1, dstOut, 1);
		mem.newLink(dstOut, 0, dstIn, 0);
		destMem.newLink(dstIn, 1, from, 0);
	}
}
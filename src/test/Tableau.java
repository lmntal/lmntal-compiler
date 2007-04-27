package test;

import java.util.HashSet;
import java.util.Iterator;
import runtime.*;

import runtime.Link;

//function expand
public class Tableau {
	public static int counter = 0;

	public static HashSet<Node> expand(Node node, HashSet<Node> nodes) {
		 System.out.println("--expand--");
		 node.printv();

		if (node.newer.isEmpty()) {
			Iterator<Node> it = nodes.iterator();
			while (it.hasNext()) {
				Node r = it.next();
				if (r.older.equals(node.older) && r.next.equals(node.next)) {
					r.incoming.addAll(node.incoming);
					return nodes;
				}
			}
			HashSet<Integer> newIncoming = new HashSet<Integer>();
			newIncoming.add(node.id);
			HashSet<Link> newNew = new HashSet<Link>();
			newNew.addAll(node.next);
			Node newNode = new Node(counter++, newIncoming,
					new HashSet<Link>(), newNew, new HashSet<Link>());
			nodes.add(node);
			return expand(newNode, nodes);
		} else { // New(q) is not empty
			Iterator<Link> it = node.newer.iterator();
			Link eta = it.next();
			it.remove();
			// if eta already belongs to Old
			if (node.older.contains(eta))
				return expand(node, nodes);
			if (eta.getAtom().getFunctor().getArity() == 1) {
				// proposition
				// System.out.println("eta is a proposition: " + eta.getAtom());
				if (eta.getAtom().getFunctor().getName().equals("false")) // contradiction
					return (nodes);
				Iterator<Link> oldIte = node.older.iterator();
				while (oldIte.hasNext()) {
					if (oldIte.next().getAtom().getName().equals("not"))
						if (oldIte.next().getAtom().nthAtom(0).getFunctor()
								.equals(eta.getAtom().getFunctor()))
							return (nodes); // contradiction
				}
				HashSet<Integer> newIncoming = new HashSet<Integer>();
				newIncoming.addAll(node.incoming);
				HashSet<Link> newOld = new HashSet<Link>();
				newOld.addAll(node.older);
				newOld.add(eta);
				HashSet<Link> newNew = new HashSet<Link>();
				newNew.addAll(node.newer);
				HashSet<Link> newNext = new HashSet<Link>();
				newNext.addAll(node.next);
				Node newNode = new Node(counter++, newIncoming, newOld, newNew,
						newNext);
				nodes.remove(node);
				return expand(newNode, nodes);
			}
			if (eta.getAtom().getFunctor().getName().equals("not")) {
				// the negation of a proposition
				if (eta.getAtom().nthAtom(0).getFunctor().getName().equals(
						"true")) // contradiction
					return (nodes);
				Iterator<Link> oldIte = node.older.iterator();
				while (oldIte.hasNext()) {
					if (oldIte.next().getAtom().getFunctor().equals(
							eta.getAtom().nthAtom(0).getFunctor()))
						return (nodes); // contradiction
				}
				HashSet<Integer> newIncoming = new HashSet<Integer>();
				newIncoming.addAll(node.incoming);
				HashSet<Link> newOld = new HashSet<Link>();
				newOld.addAll(node.older);
				newOld.add(eta);
				HashSet<Link> newNew = new HashSet<Link>();
				newNew.addAll(node.newer);
				HashSet<Link> newNext = new HashSet<Link>();
				newNext.addAll(node.next);
				Node newNode = new Node(counter++, newIncoming, newOld, newNew,
						newNext);
				nodes.remove(node);
				return expand(newNode, nodes);
			}
			if (eta.getAtom().getFunctor().getName().equals("until")) {
				// System.out.println("Tableau: until");
				HashSet<Integer> newIncoming1 = new HashSet<Integer>();
				newIncoming1.addAll(node.incoming);
				HashSet<Link> newOld1 = new HashSet<Link>();
				newOld1.addAll(node.older);
				newOld1.add(eta);
				HashSet<Link> newNew1 = new HashSet<Link>();
				newNew1.addAll(node.newer);
				newNew1.add(eta.getAtom().getArg(0));
				HashSet<Link> newNext1 = new HashSet<Link>();
				newNext1.addAll(node.next);
				newNext1.add(eta);
				Node newNode1 = new Node(counter++, newIncoming1, newOld1,
						newNew1, newNext1);
				HashSet<Integer> newIncoming2 = new HashSet<Integer>();
				newIncoming2.addAll(node.incoming);
				HashSet<Link> newOld2 = new HashSet<Link>();
				newOld2.addAll(node.older);
				newOld2.add(eta);
				HashSet<Link> newNew2 = new HashSet<Link>();
				newNew2.addAll(node.newer);
				newNew2.add(eta.getAtom().getArg(1));
				HashSet<Link> newNext2 = new HashSet<Link>();
				newNext2.addAll(node.next);
				Node newNode2 = new Node(counter++, newIncoming2, newOld2,
						newNew2, newNext2);
				nodes.remove(node);
				return expand(newNode2, expand(newNode1, nodes));
			}
			if (eta.getAtom().getFunctor().getName().equals("release")) {
				HashSet<Integer> newIncoming1 = new HashSet<Integer>();
				newIncoming1.addAll(node.incoming);
				HashSet<Link> newOld1 = new HashSet<Link>();
				newOld1.addAll(node.older);
				newOld1.add(eta);
				HashSet<Link> newNew1 = new HashSet<Link>();
				newNew1.addAll(node.newer);
				newNew1.add(eta.getAtom().getArg(0));
				newNew1.add(eta.getAtom().getArg(1));
				HashSet<Link> newNext1 = new HashSet<Link>();
				newNext1.addAll(node.next);
				Node newNode1 = new Node(counter++, newIncoming1, newOld1,
						newNew1, newNext1);
				HashSet<Integer> newIncoming2 = new HashSet<Integer>();
				newIncoming2.addAll(node.incoming);
				HashSet<Link> newOld2 = new HashSet<Link>();
				newOld2.addAll(node.older);
				newOld2.add(eta);
				HashSet<Link> newNew2 = new HashSet<Link>();
				newNew2.addAll(node.newer);
				newNew2.add(eta.getAtom().getArg(1));
				HashSet<Link> newNext2 = new HashSet<Link>();
				newNext2.addAll(node.next);
				newNext2.add(eta);
				Node newNode2 = new Node(counter++, newIncoming2, newOld2,
						newNew2, newNext2);
				nodes.remove(node);
				return expand(newNode2, expand(newNode1, nodes));
			}
			if (eta.getAtom().getFunctor().getName().equals("or")) {
				HashSet<Integer> newIncoming1 = new HashSet<Integer>();
				newIncoming1.addAll(node.incoming);
				HashSet<Link> newOld1 = new HashSet<Link>();
				newOld1.addAll(node.older);
				newOld1.add(eta);
				HashSet<Link> newNew1 = new HashSet<Link>();
				newNew1.addAll(node.newer);
				newNew1.add(eta.getAtom().getArg(0));
				HashSet<Link> newNext1 = new HashSet<Link>();
				newNext1.addAll(node.next);
				Node newNode1 = new Node(counter++, newIncoming1, newOld1,
						newNew1, newNext1);
				HashSet<Integer> newIncoming2 = new HashSet<Integer>();
				newIncoming2.addAll(node.incoming);
				HashSet<Link> newOld2 = new HashSet<Link>();
				newOld2.addAll(node.older);
				newOld2.add(eta);
				HashSet<Link> newNew2 = new HashSet<Link>();
				newNew2.addAll(node.newer);
				newNew2.add(eta.getAtom().getArg(1));
				HashSet<Link> newNext2 = new HashSet<Link>();
				newNext2.addAll(node.next);
				Node newNode2 = new Node(counter++, newIncoming2, newOld2,
						newNew2, newNext2);
				nodes.remove(node);
				return expand(newNode2, expand(newNode1, nodes));
			}
			if (eta.getAtom().getFunctor().getName().equals("and")) {
				System.out.println("and");
				HashSet<Integer> newIncoming = new HashSet<Integer>();
				newIncoming.addAll(node.incoming);
				HashSet<Link> newOld = new HashSet<Link>();
				newOld.addAll(node.older);
				newOld.add(eta);
				HashSet<Link> newNew = new HashSet<Link>();
				newNew.addAll(node.newer);
				newNew.add(eta.getAtom().getArg(0));
				newNew.add(eta.getAtom().getArg(1));
				HashSet<Link> newNext = new HashSet<Link>();
				newNext.addAll(node.next);
				Node newNode = new Node(counter++, newIncoming, newOld, newNew,
						newNext);
				nodes.remove(node);
				return expand(newNode, nodes);
			}
			if (eta.getAtom().getFunctor().getName().equals("next")) {
				HashSet<Integer> newIncoming = new HashSet<Integer>();
				newIncoming.addAll(node.incoming);
				HashSet<Link> newOld = new HashSet<Link>();
				newOld.addAll(node.older);
				newOld.add(eta);
				HashSet<Link> newNew = new HashSet<Link>();
				newNew.addAll(node.newer);
				HashSet<Link> newNext = new HashSet<Link>();
				newNext.addAll(node.next);
				newNext.add(eta.getAtom().getArg(0));
				Node newNode = new Node(counter++, newIncoming, newOld, newNew,
						newNext);
				nodes.remove(node);
				return expand(newNode, nodes);
			}
		}
		return nodes;
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
				while(oldIte.hasNext()) {
					srcMem.newAtom(new SymbolFunctor(oldIte.next().getAtom().getFunctor().getName(),0));
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
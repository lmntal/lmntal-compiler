package runtime;

import java.util.*;

class Dumper {
	static String dump(AbstractMembrane mem) {
		StringBuffer buf = new StringBuffer();
		List predAtoms = new ArrayList();
		Set atoms = new HashSet(mem.getAtomCount());

		Iterator it = mem.atomIterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			atoms.add(a);
			if (a.getArity() == 0 ||
				a.getLastArg().getAtom().getMem() != mem ||
				a.getLastArg().isFuncRef() ) {
				
				predAtoms.add(a);
			}
		}
		
		it = predAtoms.iterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			if (atoms.contains(a)) {
				buf.append(dumpAtomGroup(a, atoms));
				buf.append(", ");
			}
		}
		while (true) {
			it = atoms.iterator();
			if (!it.hasNext()) {
				break;
			}
			buf.append(dumpAtomGroup((Atom)it.next(), atoms));
			buf.append(", ");
		}
		
		it = mem.memIterator();
		while (it.hasNext()) {
			buf.append("{");
			buf.append(dump((Membrane)it.next()));
			buf.append("}, ");
		}
		
		it = mem.rulesetIterator();
		while (it.hasNext()) {
			buf.append((Ruleset)it.next());
			buf.append(", ");
		}

		return buf.toString();
	}
	private static String dumpAtomGroup(Atom a, Set atoms) {
		StringBuffer buf = new StringBuffer();
		buf.append(a.getName());
		atoms.remove(a);
		int arity = a.getArity();
		if (arity > 0) {
			buf.append("(");
			buf.append(dumpLink(a.args[0], atoms));
			for (int i = 1; i < arity; i++) {
				buf.append(",");
				buf.append(dumpLink(a.args[i], atoms));
			}
			buf.append(")");
		}
		return buf.toString();
	}
	private static String dumpAtomGroupWithoutLastArg(Atom a, Set atoms) {
		StringBuffer buf = new StringBuffer();
		buf.append(a.getName());
		atoms.remove(a);
		int arity = a.getArity();
		if (arity > 1) {
			buf.append("(");
			buf.append(dumpLink(a.args[0], atoms));
			//ÅIˆø”ˆÈŠO‚ğo—Í
			for (int i = 1; i < arity - 1; i++) {
				buf.append(",");
				buf.append(dumpLink(a.args[i], atoms));
			}
			buf.append(")");
		}
		return buf.toString();
	}
	private static String dumpLink(Link l, Set atoms) {
		if (l.isFuncRef() && atoms.contains(l.getAtom())) {
			return dumpAtomGroupWithoutLastArg(l.getAtom(), atoms);
		} else {
			return l.toString();
		}
	}
}

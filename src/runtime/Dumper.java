package runtime;

import java.util.*;

class Dumper {
	static String dump(AbstractMembrane mem) {
		StringBuffer buf = new StringBuffer();
		//まだ出力されていないアトムの集合
		Set atoms = new HashSet();

		Iterator it = mem.atomIterator();
		while (it.hasNext()) {
			atoms.add(it.next());
		}
		
		while (true) {
			it = atoms.iterator();
			while (true) {
				Atom a = (Atom)it.next();
				if (a.getArity() == 0 ||
					a.getLastArg().getAtom().getMem() != mem ||
					a.getLastArg().isFuncRef()) {

					buf.append(dumpAtomGroup(a, atoms));
					break;
				}
			}
			if (atoms.isEmpty()) {
				break;
			}
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
			//最終引数以外を出力
			for (int i = 1; i < arity - 1; i++) {
				buf.append(",");
				buf.append(dumpLink(a.args[i], atoms));
			}
			buf.append(")");
		}
		return buf.toString();
	}
	private static int lastLinkID;
	private static Map linkID;
	public static void resetId() {
		lastLinkID = 0;
		linkID = new HashMap();
	}
	private static String dumpLink(Link l, Set atoms) {
		if (l.isFuncRef()) {
			return dumpAtomGroupWithoutLastArg(l.getAtom(), atoms);
		} else {
			int id;
			Integer ido = (Integer)linkID.get(l);
			if (ido == null) {
				id = lastLinkID++;
				linkID.put(l, new Integer(id));
			} else {
				id = ido.intValue();
			}
			return "_" + id;
		}
	}
}

package runtime;

import java.util.*;

public class Dumper {
	/** 膜の中身を出力する。出力形式の指定はまだできない。 */
	public static String dump(AbstractMembrane mem) {
		StringBuffer buf = new StringBuffer();
		List predAtoms = new ArrayList();
		Set atoms = new HashSet(mem.getAtomCount());

		// アトムの出力
		Iterator it = mem.atomIterator();
		while (it.hasNext()) {


			Atom a = (Atom)it.next();
			atoms.add(a);
			//これらのアトムを起点にする。
			//最終引数同士がつながっている場合、arityが1の物を優先した方がいいかも。
			if (a.getArity() == 0 ||						//リンクのない場合
				a.getLastArg().getAtom().getMem() != mem ||	//最終引数が膜の自由リンクの場合
				a.getLastArg().isFuncRef() ) {				//最終引数同士がつながっている場合
				predAtoms.add(a);
			}
		}

		//predAtoms内のアトムを起点に出力
		it = predAtoms.iterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			//すでに出力されてしまっている場合もある
			if (atoms.contains(a)) {
				buf.append(dumpAtomGroup(a, atoms));
				buf.append(", ");
			}
		}
		//閉路がある場合にはまだ残っているので、適当な所から出力。
		//閉路の部分を探した方がいいが、とりあえずこのまま。
		while (true) {
			it = atoms.iterator();
			if (!it.hasNext()) {
				break;
			}
			buf.append(dumpAtomGroup((Atom)it.next(), atoms));
			buf.append(", ");
		}

		//子膜の出力		
		it = mem.memIterator();
		while (it.hasNext()) {
			buf.append("{");
			buf.append(dump((Membrane)it.next()));
			buf.append("}, ");
		}
		
		//ルールの出力
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
			//最終引数以外を出力
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

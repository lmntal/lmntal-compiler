package runtime;

import java.util.*;

public class Dumper {
	/** todo このクラスにあるのはおかしいので適切な場所に移動する */
	static boolean isInfixOperator(String name) {
		return name.equals("=");
	}
	/** 膜の中身を出力する。出力形式の指定はまだできない。 */
	public static String dump(AbstractMembrane mem) {
		StringBuffer buf = new StringBuffer();
		List predAtoms[] = {new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList()};
		//Set atoms = new HashSet(mem.getAtomCount());
		Set atoms = new HashSet(mem.atoms.size()); // 今はproxyを表示しているため。いずれ上に戻す
		boolean commaFlag = false;
		
		// #1 - アトムの出力
		
		Iterator it = mem.atomIterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			atoms.add(a);
		}
		
		// 起点にするアトムとその優先順位:
		//  0. 引数なしのアトム、および最終引数がこの膜以外へのリンクであるアトム
		//  1. 2引数演算子のアトム
		//  2. 整数以外の1引数でリンク先が最終引数のアトム
		//  3. 整数以外でリンク先が最終引数のアトム

		it = mem.atomIterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			if (a.getArity() == 0 || a.getLastArg().getAtom().getMem() != mem) {
				predAtoms[0].add(a);
			}
			else if (a.getArity() == 2 && isInfixOperator(a.getName())) {
				predAtoms[1].add(a);
			}
			else if (a.getLastArg().isFuncRef()
				// todo コードが気持ち悪いのでなんとかする
			    && !a.getFunctor().getInternalName().equals("")	// 通常のファンクタを起点にしたい
			//	&& !a.getName().matches("-?[0-9]+|^[A-Z]")) {	// IntegerFunctor未使用時の古いコード
				&& !a.getName().matches("^[A-Z]")) {			// 保管された自由リンクは引数に置きたい
				predAtoms[a.getArity() == 1 ? 2 : 3].add(a);
			}
		}
		//predAtoms内のアトムを起点に出力
		for (int phase = 0; phase < predAtoms.length; phase++) {
			it = predAtoms[phase].iterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
				if (atoms.contains(a)) { // まだ出力されていない場合
					if(commaFlag) buf.append(", "); else commaFlag = true;
					buf.append(dumpAtomGroup(a, atoms));
				}
			}
		}
		//閉路がある場合にはまだ残っているので、適当な所から出力。
		//閉路の部分を探した方がいいが、とりあえずこのまま。
		while (true) {
			it = atoms.iterator();
			if (!it.hasNext()) {
				break;
			}
			if(commaFlag) buf.append(", "); else commaFlag = true;
			buf.append(dumpAtomGroup((Atom)it.next(), atoms));
		}

		// #2 - 子膜の出力		
		it = mem.memIterator();
		while (it.hasNext()) {
			Membrane m = (Membrane)it.next();
			if(commaFlag) buf.append(", "); else commaFlag = true;
			if(m.name!=null) buf.append(m.name+":");
			buf.append("{");
			buf.append(dump(m));
			buf.append("}");
		}
		
		// #3 - ルールの出力
		it = mem.rulesetIterator();
		while (it.hasNext()) {
			if(commaFlag) buf.append(", "); else commaFlag = true;
			buf.append((Ruleset)it.next());
		}

		return buf.toString();
	}
	private static String dumpAtomGroup(Atom a, Set atoms) {
		StringBuffer buf = new StringBuffer();
		buf.append(a.getAbbrName());
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
		buf.append(a.getAbbrName());
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

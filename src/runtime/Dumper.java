package runtime;

import java.util.*;

public class Dumper {
	/** todo このクラスにあるのはおかしいので適切な場所に移動する */
	static HashMap binops = new HashMap();
	static final int xfy = 0;
	static final int yfx = 1;
	static final int xfx = 2;
	static {
		binops.put("*",   new int[]{yfx,400});
		binops.put("/",   new int[]{yfx,400});
		binops.put("*.",  new int[]{yfx,400});
		binops.put("/.",  new int[]{yfx,400});
		binops.put("+",   new int[]{yfx,500});
		binops.put("-",   new int[]{yfx,500});
		binops.put("+.",  new int[]{yfx,500});
		binops.put("-.",  new int[]{yfx,500});
		binops.put("=",   new int[]{xfx,700});
		binops.put("==",  new int[]{xfx,700});
		binops.put("=:=", new int[]{xfx,700});
		binops.put("=\\=",new int[]{xfx,700});
		binops.put(">",   new int[]{xfx,700});
		binops.put(">=",  new int[]{xfx,700});
		binops.put("<",   new int[]{xfx,700});
		binops.put("=<",  new int[]{xfx,700});
		binops.put("!=",  new int[]{xfx,700});
		binops.put("=:=.",new int[]{xfx,700});
		binops.put(">.",  new int[]{xfx,700});
		binops.put(">=.", new int[]{xfx,700});
		binops.put("<.",  new int[]{xfx,700});
		binops.put("=<.", new int[]{xfx,700});
		binops.put("!=.", new int[]{xfx,700});
	}
	static boolean isInfixOperator(String name) {
		return binops.containsKey(name);
	}
	static int getBinopType(String name) {
		return ((int[])binops.get(name))[0];
	}
	static int getBinopPrio(String name) {
		return ((int[])binops.get(name))[1];
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
		
		// 起点にしないアトム（EXPANDATOMS無指定時のみ）
		//  - 現在は無し
		
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
			    && a.getFunctor().isSymbol()					// 通常のファンクタを起点にしたい
			//	&& !a.getName().matches("-?[0-9]+|^[A-Z]")) {	// IntegerFunctor未使用時の古いコード
				&& !a.getName().matches("^[A-Z].*")				// 補完された自由リンクは引数に置きたい
				&& !a.getFunctor().equals(Functor.INSIDE_PROXY)
				&& !a.getFunctor().equals(Functor.OUTSIDE_PROXY) ) {
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
		return dumpAtomGroup(a,atoms,0,999);
	}
	private static String dumpAtomGroupWithoutLastArg(Atom a, Set atoms, int outerprio) {
		return dumpAtomGroup(a,atoms,1,outerprio);
	}
	/** アトムの引数を展開しながら文字列に変換する。
	 * ただし、アトムaの最後のreducedArgCount個の引数は出力しない。
	 * <p>
	 * 出力したアトムはatomsから除去される。
	 * 出力するアトムはatomsの要素でなければならない。
	 * @param a 出力するアトム
	 * @param atoms まだ出力していないアトムの集合 [in,out]
	 * @param reducedArgCount aのうち出力しない最後の引数の長さ
	 */
	private static String dumpAtomGroup(Atom a, Set atoms, int reducedArgCount, int outerprio) {
		atoms.remove(a);
		Functor func = a.getFunctor();
		int arity = func.getArity() - reducedArgCount;
		if (arity == 0) {
			return func.getQuotedAtomName(); // func.getAbbrName();
		}
		StringBuffer buf = new StringBuffer();
		if (arity == 2 && isInfixOperator(func.getName())) {
			int type = getBinopType(func.getName());
			int prio = getBinopPrio(func.getName());
			int innerleftprio  = prio + ( type == yfx ? 1 : 0 );
			int innerrightprio = prio + ( type == xfy ? 1 : 0 );
			boolean needpar = (outerprio < innerleftprio || outerprio < innerrightprio);
			if (needpar) buf.append("(");
			buf.append(dumpLink(a.args[0], atoms, innerleftprio));
			buf.append(" ");
			buf.append(func.getName());
			buf.append(" ");
			buf.append(dumpLink(a.args[1], atoms, innerrightprio));
			if (needpar) buf.append(")");
			return buf.toString();
		}
		if (arity == 2 && func.equals(new Functor(".",3))) {
			buf.append("[");
			buf.append(dumpLink(a.args[0], atoms, outerprio));
			buf.append(dumpListCdr(a.args[1], atoms));
			buf.append("]");
			return buf.toString();
		}
		buf.append(func.getQuotedFunctorName());
		buf.append("(");
		buf.append(dumpLink(a.args[0], atoms));
		for (int i = 1; i < arity; i++) {
			buf.append(",");
			buf.append(dumpLink(a.args[i], atoms));
		}
		buf.append(")");
		return buf.toString();
	}
	private static String dumpListCdr(Link l, Set atoms) {
		StringBuffer buf = new StringBuffer();
		while (true) {		
			if (!( l.isFuncRef() && atoms.contains(l.getAtom()) )) break;
			Atom a = l.getAtom();
			if (!a.getFunctor().equals(new Functor(".",3))) break;
			atoms.remove(a);
			buf.append(",");
			buf.append(dumpLink(a.args[0], atoms));
			l = a.args[1];
		}
		if (l.getAtom().getFunctor().equals(new Functor("[]",1))) {
			atoms.remove(l.getAtom());
		} else {
			buf.append("|");
			buf.append(dumpLink(l, atoms));
		}
		return buf.toString();
	}
	private static String dumpLink(Link l, Set atoms) {
		return dumpLink(l,atoms,999);
	}
	private static String dumpLink(Link l, Set atoms, int outerprio) {
		if (Env.verbose < Env.VERBOSE_EXPANDATOMS && l.isFuncRef() && atoms.contains(l.getAtom())) {
			return dumpAtomGroupWithoutLastArg(l.getAtom(), atoms, outerprio);
		} else {
			return l.toString();
		}
	}
}

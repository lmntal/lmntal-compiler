package runtime;

import java.util.*;

final class Unlexer {
	private StringBuffer buf = new StringBuffer();
	private String last = " ";
	public void append(String text) {
		if (!last.matches(".*['\"\\[\\]|,(){} ]")
		 && !text.matches("['\"\\[\\]|,(){} ].*")
		 &&  last.matches(".*[0-9A-Za-z_]") == text.matches("[0-9A-Za-z_].*")) {
		 	buf.append(" ");
		}
		buf.append(text); 
		last = text;
	}
	public String toString() {
		return buf.toString();
	}
}

public class Dumper {
	static HashMap binops = new HashMap();
	private static final int xfy = 0;
	private static final int yfx = 1;
	private static final int xfx = 2;
	static {
		binops.put("^",   new int[]{xfy,200});
		binops.put("**",  new int[]{xfy,300});
		binops.put("mod", new int[]{xfx,300});
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
	
	static Functor FUNC_CONS = new Functor(".",3);
	static Functor FUNC_NIL  = new Functor("[]",1);
	
	/** 膜の中身を出力する。出力形式の指定はまだできない。 */
	public static String dump(AbstractMembrane mem) {
		Unlexer buf = new Unlexer();
		List predAtoms[] = {new LinkedList(),new LinkedList(),new LinkedList(),new LinkedList(),
			new LinkedList()};
		//Set atoms = new HashSet(mem.getAtomCount());
		Set atoms = new HashSet(mem.atoms.size()); // 今はproxyを表示しているため。いずれ上に戻す
		boolean commaFlag = false;
		
		// #1 - アトムの出力
		
		Iterator it = mem.atomIterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			atoms.add(a);
		}

		if (Env.verbose < Env.VERBOSE_EXPANDATOMS) {
			
			// 起点にするアトムとその優先順位:
			//  0. 引数なしのアトム、および最終引数がこの膜以外へのリンクであるアトム
			//  1. 結合度が = 以下の2引数演算子のアトム
			//  2. 通常のシンボル名でリンク先が最終引数の1引数アトム
			//  3. 通常のシンボル名で最終引数のリンク先が最終引数の2引数以上のアトム
			//  4. 第3引数のリンク先が最終引数のconsアトム
			
			// 起点にしないアトム
			//  - $in,$out,[],整数,実数,およびA-Zで始まるアトム
			
			it = mem.atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
				if (a.getArity() == 0 || a.getLastArg().getAtom().getMem() != mem) {
					predAtoms[0].add(a);
				}
				else if (a.getArity() == 2 && isInfixOperator(a.getName()) 
				 && getBinopPrio(a.getName()) >= 700 ) {
					predAtoms[1].add(a);
				}
				else if (a.getLastArg().isFuncRef()) {
					// todo コードが気持ち悪いのでなんとかする (1)
				    if (!a.getFunctor().isSymbol()) continue; // 通常のファンクタを起点にしたい
					if (a.getName().matches("^[A-Z].*")) continue; // 補完された自由リンクは引数に置きたい
					if (a.getFunctor().equals(Functor.INSIDE_PROXY)) continue;
					if (a.getFunctor().equals(Functor.OUTSIDE_PROXY)) continue;
					if (a.getFunctor().equals(FUNC_NIL) ) continue; // []は整数と同じ表示的な扱い
					if (a.getArity() == 1) {
						predAtoms[2].add(a);
					}
					else if (!a.getFunctor().equals(FUNC_CONS)) {
						predAtoms[3].add(a);
					}
					else { // consはできるだけデータとして扱う
						predAtoms[4].add(a);
					}
				}
			}
			//predAtoms内のアトムを起点に出力
			for (int phase = 0; phase < predAtoms.length; phase++) {
				it = predAtoms[phase].iterator();
				while (it.hasNext()) {
					Atom a = (Atom)it.next();
					if (atoms.contains(a)) { // まだ出力されていない場合
						if(commaFlag) buf.append(", "); else commaFlag = true;
						// consは演算子と同じ表示的な扱い
						if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
							if (a.getFunctor().equals(FUNC_CONS)
							 || (a.getArity() == 3 && isInfixOperator(a.getName()))) {
								buf.append(dumpLink(a.getLastArg(), atoms, 700));
								buf.append("=");
								buf.append(dumpAtomGroupWithoutLastArg(a, atoms, 700));
								continue;
							}
						}
						buf.append(dumpAtomGroup(a, atoms));
					}
				}
			}
		}
		
		//閉路がある場合にはまだ残っているので、適当な所から出力。
		//閉路の部分を探した方がいいが、とりあえずこのまま。
		boolean changed;
		do {
			changed = false;
			it = atoms.iterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
				if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
					// todo コードが気持ち悪いのでなんとかする (2)
					if (!a.getFunctor().isSymbol()) continue;
					if (a.getName().matches("^[A-Z].*")) continue;
					if (a.getFunctor().equals(Functor.INSIDE_PROXY)) continue;
					if (a.getFunctor().equals(Functor.OUTSIDE_PROXY)) continue;
					if (a.getFunctor().equals(FUNC_NIL)) continue;
				}
				if(commaFlag) buf.append(", "); else commaFlag = true;
				buf.append(dumpAtomGroup(a, atoms));
				changed = true;
				break;
			}
		}
		while (changed);
		
		// 残ったアトムを s=t の形式で出力する
		while (!atoms.isEmpty()) {
			it = atoms.iterator();
			Atom a = (Atom)it.next();
			if(commaFlag) buf.append(", "); else commaFlag = true;
			buf.append(dumpAtomGroupWithoutLastArg(a, atoms, 700));
			buf.append("=");
			buf.append(dumpAtomGroupWithoutLastArg(a.getLastArg().getAtom(), atoms, 700));
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
			buf.append(((Ruleset)it.next()).toString());
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
		Unlexer buf = new Unlexer();
		if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
			if (arity == 2 && isInfixOperator(func.getName())) {
				int type = getBinopType(func.getName());
				int prio = getBinopPrio(func.getName());
				int innerleftprio  = prio + ( type == yfx ? 1 : 0 );
				int innerrightprio = prio + ( type == xfy ? 1 : 0 );
				boolean needpar = (outerprio < innerleftprio || outerprio < innerrightprio);
				if (needpar) buf.append("(");
				buf.append(dumpLink(a.args[0], atoms, innerleftprio));
				buf.append(func.getName());
				buf.append(dumpLink(a.args[1], atoms, innerrightprio));
				if (needpar) buf.append(")");
				return buf.toString();
			}
			if (arity == 2 && func.getName().equals(".")) {
				buf.append("[");
				buf.append(dumpLink(a.args[0], atoms, outerprio));
				buf.append(dumpListCdr(a.args[1], atoms));
				buf.append("]");
				return buf.toString();
			}
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
		Unlexer buf = new Unlexer();
		while (true) {		
			if (!( l.isFuncRef() && atoms.contains(l.getAtom()) )) break;
			Atom a = l.getAtom();
			if (!a.getFunctor().equals(FUNC_CONS)) break;
			atoms.remove(a);
			buf.append(",");
			buf.append(dumpLink(a.args[0], atoms));
			l = a.args[1];
		}
		if (l.getAtom().getFunctor().equals(FUNC_NIL)) {
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

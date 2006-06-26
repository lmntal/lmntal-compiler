package runtime;

import java.util.*;

final class Unlexer {
	private StringBuffer buf = new StringBuffer();

	private String last = " ";

	public void append(String text) {
		if (!last.matches(".*['\"\\[\\]|,(){} ]")
				&& !text.matches("['\"\\[\\]|,(){} ].*")
				&& last.matches(".*[0-9A-Za-z_]") == text
						.matches("[0-9A-Za-z_].*")) {
			// if (! (last.matches(".*=") && text.matches("\\$.*")) )
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
		binops.put("^", new int[] { xfy, 200 });
		binops.put("**", new int[] { xfy, 300 });
		binops.put("mod", new int[] { xfx, 300 });
		binops.put("*", new int[] { yfx, 400 });
		binops.put("/", new int[] { yfx, 400 });
		binops.put("*.", new int[] { yfx, 400 });
		binops.put("/.", new int[] { yfx, 400 });
		binops.put("+", new int[] { yfx, 500 });
		binops.put("-", new int[] { yfx, 500 });
		binops.put("+.", new int[] { yfx, 500 });
		binops.put("-.", new int[] { yfx, 500 });
		binops.put("=", new int[] { xfx, 700 });
		binops.put("==", new int[] { xfx, 700 });
		binops.put("=:=", new int[] { xfx, 700 });
		binops.put("=\\=", new int[] { xfx, 700 });
		binops.put(">", new int[] { xfx, 700 });
		binops.put(">=", new int[] { xfx, 700 });
		binops.put("<", new int[] { xfx, 700 });
		binops.put("=<", new int[] { xfx, 700 });
		binops.put("=:=.", new int[] { xfx, 700 });
		binops.put("=\\=.", new int[] { xfx, 700 });
		binops.put(">.", new int[] { xfx, 700 });
		binops.put(">=.", new int[] { xfx, 700 });
		binops.put("<.", new int[] { xfx, 700 });
		binops.put("=<.", new int[] { xfx, 700 });
		binops.put(":", new int[] { xfy, 800 }); // ただし : の左辺が
		// [a-z][A-Za-z0-9_]* のときのみ
	}

	static boolean isInfixOperator(String name) {
		return binops.containsKey(name);
	}

	static int getBinopType(String name) {
		return ((int[]) binops.get(name))[0];
	}

	static int getBinopPrio(String name) {
		return ((int[]) binops.get(name))[1];
	}

	static Functor FUNC_CONS = new Functor(".", 3);

	static Functor FUNC_NIL = new Functor("[]", 1);

	/** 膜の中身を出力する。出力形式の指定はまだできない。 */
	public static String dump(AbstractMembrane mem) {
		return dump(mem, true);
	}

	public static String dump(AbstractMembrane mem, boolean doLock) {
		boolean locked = false;
		if (doLock) {
			if (mem.getLockThread() != Thread.currentThread()) {
				if (!mem.lock()) {
					return "...";
				}
				locked = true;
			}
		}

		Unlexer buf = new Unlexer();
		boolean commaFlag = false;

		// #1 - アトムの出力

		// Set atoms = new HashSet(mem.getAtomCount());
		Set atoms = new HashSet(mem.atoms.size()); // 今はproxyを表示しているため。いずれ上に戻す

		Iterator it = mem.atomIterator();
		while (it.hasNext()) {
			Atom a = (Atom) it.next();
			if (Env.hideProxy && !a.isVisible()) {
				// PROXYを表示させない 2005/02/03 T.Nagata
				continue;
			}
			atoms.add(a);
		}
		if (Env.verbose >= Env.VERBOSE_EXPANDATOMS) {
			it = mem.atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
				if(!atoms.contains(a))continue;
				if (commaFlag)
					buf.append(", ");
				else
					commaFlag = true;
				buf.append(dumpAtomGroup(a, atoms, false));
			}
		} else {
			List predAtoms[] = { new LinkedList(), new LinkedList(),
					new LinkedList(), new LinkedList(), new LinkedList() };

			// 起点にするアトムとその優先順位:
			// 0. 引数なしのアトム、および最終引数がこの膜以外へのリンクであるアトム
			// 1. 結合度が = 以下の2引数演算子のアトム（臨時：およびinlineアトム）
			// 2. 通常のシンボル名でリンク先が最終引数の1引数アトム
			// 3. 通常のシンボル名で最終引数のリンク先が最終引数の2引数以上のアトム
			// 4. 第3引数のリンク先が最終引数のconsアトム

			// 通常でないアトム名（起点にしないアトムの名前）:
			// - $in,$out,[],整数,実数,およびA-Zで始まるアトム

			it = mem.atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom) it.next();
				if (a.getArity() == 0
						|| a.getLastArg().getAtom().getMem() != mem) {
					predAtoms[0].add(a);
				} else if (a.getArity() == 2 && isInfixOperator(a.getName())
						&& getBinopPrio(a.getName()) >= 700
						|| a.getName().startsWith("/*inline*/")) {
					predAtoms[1].add(a);
				} else if (a.getLastArg().isFuncRef()) {
					// todo コードが気持ち悪いのでなんとかする (1)
					if (!a.getFunctor().isSymbol())
						continue; // 通常のファンクタを起点にしたい
					if (a.getName().matches("^[A-Z].*"))
						continue; // 補完された自由リンクは引数に置きたい
					if (a.getFunctor().equals(Functor.INSIDE_PROXY))
						continue;
					if (a.getFunctor().isOUTSIDE_PROXY())
						continue;
					if (a.getFunctor().equals(FUNC_NIL))
						continue; // []は整数と同じ表示的な扱い
					if (a.getArity() == 1) {
						predAtoms[2].add(a);
					} else if (!a.getFunctor().equals(FUNC_CONS)) {
						predAtoms[3].add(a);
					} else { // consはできるだけデータとして扱う
						predAtoms[4].add(a);
					}
				}
			}

			// predAtoms内のアトムを起点に出力
			for (int phase = 0; phase < predAtoms.length; phase++) {
				it = predAtoms[phase].iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					if (atoms.contains(a)) { // まだ出力されていない場合
						if (commaFlag)
							buf.append(", ");
						else
							commaFlag = true;
						// 3引数演算子の強制 s=t 表示は、演算子展開表示しないときのみ行う
						if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
							// consは演算子と同じ表示的な扱い
							if (a.getFunctor().equals(FUNC_CONS)
									|| (a.getArity() == 3 && isInfixOperator(a
											.getName()))) {
								buf
										.append(dumpLink(a.getLastArg(), atoms,
												700));
								buf.append("=");
								buf.append(dumpAtomGroupWithoutLastArg(a,
										atoms, 700, false));
								continue;
							}
						}
						buf.append(dumpAtomGroup(a, atoms, false));
					}
				}
			}

			// todo このchangedループもpredAtomsに統合する

			// 閉路がある場合にはまだ残っているので、適当な所から出力。
			// 閉路の部分を探した方がいいが、とりあえずこのまま。
			boolean changed;
			do {
				changed = false;
				it = atoms.iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					// 演算子表示できるときは、データができるだけ引数に来るようにする
					if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
						// todo コードが気持ち悪いのでなんとかする (2)
						if (!a.getFunctor().isSymbol())
							continue;
						if (a.getName().matches("^[A-Z].*"))
							continue;
						if (a.getFunctor().equals(Functor.INSIDE_PROXY))
							continue;
						if (a.getFunctor().isOUTSIDE_PROXY())
							continue;
						if (a.getFunctor().equals(FUNC_NIL))
							continue;
					}
					// プロキシを省略できるときは、プロキシができるだけ引数に来るようにする
					if (Env.hideProxy/*Env.verbose < Env.VERBOSE_EXPANDPROXIES*/) {
						if (a.getFunctor().equals(Functor.INSIDE_PROXY))
							continue;
						if (a.getFunctor().isOUTSIDE_PROXY())
							continue;
					}
					// ここまで残った1引数のアトムはデータの可能性が高いので、できるだけ引数に来るようにする
					if (a.getArity() == 1)
						continue;
					//
					if (commaFlag)
						buf.append(", ");
					else
						commaFlag = true;
					buf.append(dumpAtomGroup(a, atoms, false));
					changed = true;
					break;
				}
			} while (changed);

			// 残った1引数のアトム（データだと思って保留していた整数など）を起点にして出力する。
			// ただしリンク先が自由リンク管理アトムのときに限る
			do {
				changed = false;
				it = atoms.iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					if (a.getArity() == 1) {
						if (a.getLastArg().getAtom().getFunctor() == Functor.INSIDE_PROXY
								|| a.getLastArg().getAtom().getFunctor()
										.isOUTSIDE_PROXY()) {
							if (commaFlag)
								buf.append(", ");
							else
								commaFlag = true;
							buf.append(dumpAtomGroup(a, atoms, false));
							changed = true;
							break;
						}
					}
				}
			} while (changed);

			// 残ったアトムを s=t の形式で出力する。
			while (!atoms.isEmpty()) {
				it = atoms.iterator();
				Atom a = (Atom) it.next();
				if (commaFlag)
					buf.append(", ");
				else
					commaFlag = true;
				buf.append(dumpAtomGroupWithoutLastArg(a, atoms, 700, false));
				buf.append("=");
				buf.append(dumpAtomGroupWithoutLastArg(
						a.getLastArg().getAtom(), atoms, 700, false));
			}
		}

		// #2 - 子膜の出力
		it = mem.memIterator();
		while (it.hasNext()) {
			AbstractMembrane m = (AbstractMembrane) it.next();
			if (commaFlag) {
				buf.append(", ");
				if (Env.getExtendedOption("dump").equals("1"))
					buf.append("\n");
			} else
				commaFlag = true;
			if (Env.getExtendedOption("dump").equals("1"))
				Env.indent++;
			if (Env.getExtendedOption("dump").equals("1"))
				for (int k = 0; k < Env.indent; k++)
					buf.append("  ");
			if (m.name != null)
				buf.append(m.name + ":");
			buf.append("{");
			buf.append(dump(m));
			buf.append("}");
			if (m.kind == 1)
				buf.append("_");
			else if (m.kind == Membrane.KIND_ND)
				buf.append("*");
			if (Env.getExtendedOption("dump").equals("1"))
				Env.indent--;
		}

		// #3 - ルールの出力
		if(Env.showruleset){
			it = mem.rulesetIterator();
			while (it.hasNext()) {
				if (commaFlag)
					buf.append(", ");
				else
					commaFlag = true;
				buf.append(((Ruleset) it.next()).toString());
			}
		}
		if(Env.showrule){
			it = mem.rulesetIterator();
			while (it.hasNext()) {
				Ruleset rs = (Ruleset) it.next();
				List rules;
				if(rs instanceof InterpretedRuleset)
					rules = ((InterpretedRuleset)rs).rules;
				else
					rules = rs.compiledRules;
				if(rules != null){
					Iterator it2 = rules.iterator();
					while (it2.hasNext()) {
						Rule r = (Rule) it2.next();
						if (r.name != null) {
							if (commaFlag)
								buf.append(", ");
							else
								commaFlag = true;
							buf.append("@" + r.toString() + "@");
							if (Env.profile) {
								if (Env.majorVersion == 1 && Env.minorVersion > 4)
									buf.append("_" + r.succeed + "/" + r.apply + "/" + r.backtracks + "/" + r.lockfailure
											+ "(" + r.time / 1000000 + "msec)");
								else
									buf.append("_" + r.succeed + "/" + r.apply + "/" + r.backtracks + "/" + r.lockfailure
											+ "(" + r.time + "msec)");
							}
						}
					}					
				}
			}
		}

		if (locked) {
			mem.quietUnlock();
		}

		return buf.toString();
	}

	/**
	 * @param AbstractMembrane
	 *            mem
	 * @param boolean
	 *            doLock
	 * @param int
	 *            mode
	 * @return String 膜のコンパイル可能な文字列表現
	 */
	public static String encode(AbstractMembrane mem, boolean doLock, int mode) {
		boolean locked = false;
		if (doLock) {
			if (mem.getLockThread() != Thread.currentThread()) {
				if (!mem.lock()) {
					return "";
				}
				locked = true;
			}
		}

		Unlexer buf = new Unlexer();
		boolean commaFlag = false;

		if(mode==0 | mode==2) {
		// #1 - アトムの出力

			// proxyを表示しないのでこれで十分
			Set atoms = new HashSet(mem.getAtomCount());

			Iterator it = mem.atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom) it.next();
				if (!a.isVisible()) {
					// PROXYを表示させない
					continue;
				}
				atoms.add(a);
			}

			List predAtoms[] = { new LinkedList(), new LinkedList(),
					new LinkedList(), new LinkedList(), new LinkedList() };

			// 起点にするアトムとその優先順位:
			// 0. 引数なしのアトム、および最終引数がこの膜以外へのリンクであるアトム
			// 1. 結合度が = 以下の2引数演算子のアトム（臨時：およびinlineアトム）
			// 2. 通常のシンボル名でリンク先が最終引数の1引数アトム
			// 3. 通常のシンボル名で最終引数のリンク先が最終引数の2引数以上のアトム
			// 4. 第3引数のリンク先が最終引数のconsアトム

			// 通常でないアトム名（起点にしないアトムの名前）:
			// - $in,$out,[],整数,実数,およびA-Zで始まるアトム

			it = mem.atomIterator();
			while (it.hasNext()) {
				Atom a = (Atom) it.next();
				if (a.getArity() == 0
						|| a.getLastArg().getAtom().getMem() != mem) {
					predAtoms[0].add(a);
				} else if (a.getArity() == 2 && isInfixOperator(a.getName())
						&& getBinopPrio(a.getName()) >= 700
						|| a.getName().startsWith("/*inline*/")) {
					predAtoms[1].add(a);
				} else if (a.getLastArg().isFuncRef()) {
					// todo コードが気持ち悪いのでなんとかする (1)
					if (!a.getFunctor().isSymbol())
						continue; // 通常のファンクタを起点にしたい
					if (a.getName().matches("^[A-Z].*"))
						continue; // 補完された自由リンクは引数に置きたい
					if (a.getFunctor().equals(Functor.INSIDE_PROXY))
						continue;
					if (a.getFunctor().isOUTSIDE_PROXY())
						continue;
					if (a.getFunctor().equals(FUNC_NIL))
						continue; // []は整数と同じ表示的な扱い
					if (a.getArity() == 1) {
						predAtoms[2].add(a);
					} else if (!a.getFunctor().equals(FUNC_CONS)) {
						predAtoms[3].add(a);
					} else { // consはできるだけデータとして扱う
						predAtoms[4].add(a);
					}
				}
			}

			// predAtoms内のアトムを起点に出力
			for (int phase = 0; phase < predAtoms.length; phase++) {
				it = predAtoms[phase].iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					if (atoms.contains(a)) { // まだ出力されていない場合
						if (commaFlag)
							buf.append(", ");
						else
							commaFlag = true;
						// 3引数演算子の強制 s=t 表示は、演算子展開表示しないときのみ行う
						// consは演算子と同じ表示的な扱い
						if (a.getFunctor().equals(FUNC_CONS)
								|| (a.getArity() == 3 && isInfixOperator(a
										.getName()))) {
							buf.append(dumpLink(a.getLastArg(), atoms, 700));
							buf.append("=");
							buf.append(dumpAtomGroupWithoutLastArg(a, atoms,
									700, true));
							continue;
						}
						buf.append(dumpAtomGroup(a, atoms, true));
					}
				}
			}

			// todo このchangedループもpredAtomsに統合する

			// 閉路がある場合にはまだ残っているので、適当な所から出力。
			// 閉路の部分を探した方がいいが、とりあえずこのまま。
			boolean changed;
			do {
				changed = false;
				it = atoms.iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					// todo コードが気持ち悪いのでなんとかする (2)
					if (!a.getFunctor().isSymbol())
						continue;
					if (a.getName().matches("^[A-Z].*"))
						continue;
					if (a.getFunctor().equals(Functor.INSIDE_PROXY))
						continue;
					if (a.getFunctor().isOUTSIDE_PROXY())
						continue;
					if (a.getFunctor().equals(FUNC_NIL))
						continue;
					// ここまで残った1引数のアトムはデータの可能性が高いので、できるだけ引数に来るようにする
					if (a.getArity() == 1)
						continue;
					if (commaFlag)
						buf.append(", ");
					else
						commaFlag = true;
					buf.append(dumpAtomGroup(a, atoms, true));
					changed = true;
					break;
				}
			} while (changed);

			// 残った1引数のアトム（データだと思って保留していた整数など）を起点にして出力する。
			// ただしリンク先が自由リンク管理アトムのときに限る
			do {
				changed = false;
				it = atoms.iterator();
				while (it.hasNext()) {
					Atom a = (Atom) it.next();
					if (a.getArity() == 1) {
						if (a.getLastArg().getAtom().getFunctor() == Functor.INSIDE_PROXY
								|| a.getLastArg().getAtom().getFunctor()
										.isOUTSIDE_PROXY()) {
							if (commaFlag)
								buf.append(", ");
							else
								commaFlag = true;
							buf.append(dumpAtomGroup(a, atoms, true));
							changed = true;
							break;
						}
					}
				}
			} while (changed);

			// 残ったアトムを s=t の形式で出力する。
			while (!atoms.isEmpty()) {
				it = atoms.iterator();
				Atom a = (Atom) it.next();
				if (commaFlag)
					buf.append(", ");
				else
					commaFlag = true;
				buf.append(dumpAtomGroupWithoutLastArg(a, atoms, 700, true));
				buf.append("=");
				buf.append(dumpAtomGroupWithoutLastArg(
						a.getLastArg().getAtom(), atoms, 700, true));
			}
		}

		// #2 子膜の出力
		Iterator it = mem.memIterator();
		while (it.hasNext()) {
			AbstractMembrane m = (AbstractMembrane) it.next();
			if (commaFlag) {
				buf.append(", ");
			} else
				commaFlag = true;
			buf.append("{");
			buf.append(encode(m, true, mode));
			buf.append("}");
			if (m.kind == 1)
				buf.append("_");
		}

		if(mode <= 1) {
		// #3 ルールの出力
			it = mem.rulesetIterator();
			while (it.hasNext()) {
				if (commaFlag)
					buf.append(", ");
				else
					commaFlag = true;
				// Translator が作成したファイルのencode()メソッドを呼び出す
				buf.append(((Ruleset) it.next()).encode());
			}
		}
		
		if (locked) {
			mem.quietUnlock();
		}

		return buf.toString();
	}

	private static String dumpAtomGroup(Atom a, Set atoms, boolean fully) {
		return dumpAtomGroup(a, atoms, 0, 999, fully);
	}

	private static String dumpAtomGroupWithoutLastArg(Atom a, Set atoms,
			int outerprio, boolean fully) {
		return dumpAtomGroup(a, atoms, 1, outerprio, fully);
	}

	/**
	 * アトムの引数を展開しながら文字列に変換する。 ただし、アトムaの最後のreducedArgCount個の引数は出力しない。
	 * <p>
	 * 出力したアトムはatomsから除去される。 出力するアトムはatomsの要素でなければならない。
	 * 
	 * @param a
	 *            出力するアトム
	 * @param atoms
	 *            まだ出力していないアトムの集合 [in,out]
	 * @param reducedArgCount
	 *            aのうち出力しない最後の引数の長さ
	 * @param fully
	 *            trueならファンクタ名やアトム名を省略しない
	 */
	private static String dumpAtomGroup(Atom a, Set atoms, int reducedArgCount,
			int outerprio, boolean fully) {
		atoms.remove(a);
		Functor func = a.getFunctor();
		int arity = func.getArity() - reducedArgCount;
		if (arity == 0) {
			if (!fully)
				return func.getQuotedAtomName();
			return func.getQuotedFullyAtomName();
		}
		if (Env.hideProxy //Env.verbose < Env.VERBOSE_EXPANDPROXIES
				&& arity == 1
				&& (func.equals(Functor.INSIDE_PROXY) || func.isOUTSIDE_PROXY())) {
			return dumpLink(a.args[0], atoms, outerprio);
		}
		Unlexer buf = new Unlexer();
		if (Env.verbose < Env.VERBOSE_EXPANDOPS) {
			if (arity == 2 && isInfixOperator(func.getName())) {
				if (func.getName().equals(":")
						&& (a.args[0].getAtom().getArity() != 1 || !a.args[0]
								.getAtom().getName().matches(
										"[a-z][A-Za-z0-9_]*"))) {
				} else {
					int type = getBinopType(func.getName());
					int prio = getBinopPrio(func.getName());
					int innerleftprio = prio + (type == yfx ? 1 : 0);
					int innerrightprio = prio + (type == xfy ? 1 : 0);
					boolean needpar = (outerprio < innerleftprio || outerprio < innerrightprio);
					if (needpar)
						buf.append("(");
					buf.append(dumpLink(a.args[0], atoms, innerleftprio));
					buf.append(func.getName());
					buf.append(dumpLink(a.args[1], atoms, innerrightprio));
					if (needpar)
						buf.append(")");
					return buf.toString();
				}
			}
			if (arity == 2 && func.getName().equals(".")) {
				buf.append("[");
				buf.append(dumpLink(a.args[0], atoms, outerprio));
				buf.append(dumpListCdr(a.args[1], atoms));
				buf.append("]");
				return buf.toString();
			}
		}
		if (!fully)
			buf.append(func.getQuotedFunctorName());
		else
			buf.append(func.getQuotedFullyFunctorName());
		if (Env.verbose > Env.VERBOSE_SIMPLELINK || !func.getName().matches("[-\\+]"))
			buf.append("(");
		buf.append(dumpLink(a.args[0], atoms));
		for (int i = 1; i < arity; i++) {
			buf.append(",");
			buf.append(dumpLink(a.args[i], atoms));
		}
		if (Env.verbose > Env.VERBOSE_SIMPLELINK || !func.getName().matches("[-\\+]"))
			buf.append(")");
		return buf.toString();
	}

	private static String dumpListCdr(Link l, Set atoms) {
		Unlexer buf = new Unlexer();
		while (true) {
			if (!(l.isFuncRef() && atoms.contains(l.getAtom())))
				break;
			Atom a = l.getAtom();
			if (!a.getFunctor().equals(FUNC_CONS))
				break;
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
		return dumpLink(l, atoms, 999);
	}

	private static String dumpLink(Link l, Set atoms, int outerprio) {
		// PROXYを表示しない 2005/02/03 T.Nagata
		if (Env.hideProxy && !l.getAtom().isVisible()) {
			Atom tmp_a = l.getAtom();
			Link tmp_l = l;
			while (!tmp_a.isVisible()) {
				tmp_l = tmp_a.args[tmp_l.getPos() == 0 ? 1 : 0];
				tmp_a = tmp_l.getAtom();
			}
			return l.toString().compareTo(tmp_l.getBuddy().toString()) >= 0 ? l
					.toString() : tmp_l.getBuddy().toString();
		}
		if (Env.verbose < Env.VERBOSE_EXPANDATOMS && l.isFuncRef()
				&& atoms.contains(l.getAtom())) {
			return dumpAtomGroupWithoutLastArg(l.getAtom(), atoms, outerprio,
					false);
		} else {
			return l.toString();
		}
	}
}

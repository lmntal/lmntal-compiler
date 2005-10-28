package translated;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset601 extends Ruleset {
	private static final Ruleset601 theInstance = new Ruleset601();
	private Ruleset601() {}
	public static Ruleset601 getInstance() {
		return theInstance;
	}
	private int id = 601;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL102(mem, atom)) {
			return true;
		}
		if (execL109(mem, atom)) {
			return true;
		}
		if (execL117(mem, atom)) {
			return true;
		}
		if (execL127(mem, atom)) {
			return true;
		}
		if (execL135(mem, atom)) {
			return true;
		}
		if (execL144(mem, atom)) {
			return true;
		}
		if (execL153(mem, atom)) {
			return true;
		}
		if (execL161(mem, atom)) {
			return true;
		}
		if (execL170(mem, atom)) {
			return true;
		}
		if (execL179(mem, atom)) {
			return true;
		}
		if (execL187(mem, atom)) {
			return true;
		}
		if (execL195(mem, atom)) {
			return true;
		}
		if (execL204(mem, atom)) {
			return true;
		}
		if (execL213(mem, atom)) {
			return true;
		}
		if (execL221(mem, atom)) {
			return true;
		}
		if (execL229(mem, atom)) {
			return true;
		}
		if (execL237(mem, atom)) {
			return true;
		}
		if (execL245(mem, atom)) {
			return true;
		}
		if (execL253(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL105(mem)) {
			return true;
		}
		if (execL113(mem)) {
			return true;
		}
		if (execL123(mem)) {
			return true;
		}
		if (execL131(mem)) {
			return true;
		}
		if (execL140(mem)) {
			return true;
		}
		if (execL149(mem)) {
			return true;
		}
		if (execL157(mem)) {
			return true;
		}
		if (execL166(mem)) {
			return true;
		}
		if (execL175(mem)) {
			return true;
		}
		if (execL183(mem)) {
			return true;
		}
		if (execL191(mem)) {
			return true;
		}
		if (execL200(mem)) {
			return true;
		}
		if (execL209(mem)) {
			return true;
		}
		if (execL217(mem)) {
			return true;
		}
		if (execL225(mem)) {
			return true;
		}
		if (execL233(mem)) {
			return true;
		}
		if (execL241(mem)) {
			return true;
		}
		if (execL249(mem)) {
			return true;
		}
		if (execL257(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL257(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L257:
		{
// spec           [1, 3]
// findatom    [1, 0, append_4]
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// jump           [L251, [0], [1, 2], []]
						if (execL251(var0,var1,var2)) {
							ret = true;
							break L257;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL251(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L251:
		{
// spec           [3, 3]
// jump           [L252, [0], [1, 2], []]
			if (execL252(var0,var1,var2)) {
				ret = true;
				break L251;
			}
		}
		return ret;
	}
	public boolean execL252(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L252:
		{
// spec           [3, 5]
// commit         [append]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, append_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, ._3]
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [4, 0, append_4]
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 2, 0, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 0 );
// newlink        [3, 1, 4, 2, 0]
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var4), 2 );
// relink         [3, 2, 1, 2, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 2,
				((Atom)var1), 2 );
// relink         [4, 0, 2, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var2), 1 );
// relink         [4, 1, 1, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
// relink         [4, 3, 1, 3, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 3,
				((Atom)var1), 3 );
// enqueueatom    [4]
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L252;
		}
		return ret;
	}
	public boolean execL253(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L253:
		{
// spec           [2, 10]
// branch         [ ... ]
			if (execL255(var0, var1)) {
				ret = true;
				break L253;
			}
		}
		return ret;
	}
	public boolean execL255(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L255:
		{
// spec           [2, 10]
// func           [1, append_4]
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// jump           [L251, [0], [1, 6], []]
							if (execL251(var0,var1,var6)) {
								ret = true;
								break L255;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL249(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L249:
		{
// spec           [1, 3]
// findatom    [1, 0, append_4]
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L243, [0], [1, 2], []]
						if (execL243(var0,var1,var2)) {
							ret = true;
							break L249;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL243(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L243:
		{
// spec           [3, 3]
// jump           [L244, [0], [1, 2], []]
			if (execL244(var0,var1,var2)) {
				ret = true;
				break L243;
			}
		}
		return ret;
	}
	public boolean execL244(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L244:
		{
// spec           [3, 4]
// commit         [append]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, append_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// unify          [1, 2, 1, 1, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 1 );
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 3 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L244;
		}
		return ret;
	}
	public boolean execL245(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L245:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL247(var0, var1)) {
				ret = true;
				break L245;
			}
		}
		return ret;
	}
	public boolean execL247(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L247:
		{
// spec           [2, 8]
// func           [1, append_4]
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L243, [0], [1, 6], []]
							if (execL243(var0,var1,var6)) {
								ret = true;
								break L247;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL241(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L241:
		{
// spec           [1, 3]
// findatom    [1, 0, merge_4]
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 1, 2]
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// jump           [L235, [0], [1, 2], []]
						if (execL235(var0,var1,var2)) {
							ret = true;
							break L241;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL235(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L235:
		{
// spec           [3, 3]
// jump           [L236, [0], [1, 2], []]
			if (execL236(var0,var1,var2)) {
				ret = true;
				break L235;
			}
		}
		return ret;
	}
	public boolean execL236(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L236:
		{
// spec           [3, 5]
// commit         [merge]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, merge_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, ._3]
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [4, 0, merge_4]
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 2, 0, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 0 );
// newlink        [3, 1, 4, 2, 0]
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var4), 2 );
// relink         [3, 2, 1, 2, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 2,
				((Atom)var1), 2 );
// relink         [4, 0, 1, 0, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 0 );
// relink         [4, 1, 2, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var2), 1 );
// relink         [4, 3, 1, 3, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 3,
				((Atom)var1), 3 );
// enqueueatom    [4]
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L236;
		}
		return ret;
	}
	public boolean execL237(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L237:
		{
// spec           [2, 10]
// branch         [ ... ]
			if (execL239(var0, var1)) {
				ret = true;
				break L237;
			}
		}
		return ret;
	}
	public boolean execL239(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L239:
		{
// spec           [2, 10]
// func           [1, merge_4]
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 1, 2]
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// jump           [L235, [0], [1, 6], []]
							if (execL235(var0,var1,var6)) {
								ret = true;
								break L239;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL233(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L233:
		{
// spec           [1, 3]
// findatom    [1, 0, merge_4]
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// jump           [L227, [0], [1, 2], []]
						if (execL227(var0,var1,var2)) {
							ret = true;
							break L233;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL227(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L227:
		{
// spec           [3, 3]
// jump           [L228, [0], [1, 2], []]
			if (execL228(var0,var1,var2)) {
				ret = true;
				break L227;
			}
		}
		return ret;
	}
	public boolean execL228(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L228:
		{
// spec           [3, 5]
// commit         [merge]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, merge_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, ._3]
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [4, 0, merge_4]
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 2, 0, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 0 );
// newlink        [3, 1, 4, 2, 0]
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var4), 2 );
// relink         [3, 2, 1, 2, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 2,
				((Atom)var1), 2 );
// relink         [4, 0, 2, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var2), 1 );
// relink         [4, 1, 1, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
// relink         [4, 3, 1, 3, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 3,
				((Atom)var1), 3 );
// enqueueatom    [4]
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L228;
		}
		return ret;
	}
	public boolean execL229(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L229:
		{
// spec           [2, 10]
// branch         [ ... ]
			if (execL231(var0, var1)) {
				ret = true;
				break L229;
			}
		}
		return ret;
	}
	public boolean execL231(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L231:
		{
// spec           [2, 10]
// func           [1, merge_4]
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// jump           [L227, [0], [1, 6], []]
							if (execL227(var0,var1,var6)) {
								ret = true;
								break L231;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL225(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L225:
		{
// spec           [1, 3]
// findatom    [1, 0, merge_4]
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 1, 0]
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L219, [0], [1, 2], []]
						if (execL219(var0,var1,var2)) {
							ret = true;
							break L225;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL219(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L219:
		{
// spec           [3, 3]
// jump           [L220, [0], [1, 2], []]
			if (execL220(var0,var1,var2)) {
				ret = true;
				break L219;
			}
		}
		return ret;
	}
	public boolean execL220(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L220:
		{
// spec           [3, 4]
// commit         [merge]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, merge_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// unify          [1, 2, 1, 0, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 0 );
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 3 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L220;
		}
		return ret;
	}
	public boolean execL221(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L221:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL223(var0, var1)) {
				ret = true;
				break L221;
			}
		}
		return ret;
	}
	public boolean execL223(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L223:
		{
// spec           [2, 8]
// func           [1, merge_4]
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 1, 0]
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L219, [0], [1, 6], []]
							if (execL219(var0,var1,var6)) {
								ret = true;
								break L223;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL217(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L217:
		{
// spec           [1, 3]
// findatom    [1, 0, merge_4]
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L211, [0], [1, 2], []]
						if (execL211(var0,var1,var2)) {
							ret = true;
							break L217;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL211(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L211:
		{
// spec           [3, 3]
// jump           [L212, [0], [1, 2], []]
			if (execL212(var0,var1,var2)) {
				ret = true;
				break L211;
			}
		}
		return ret;
	}
	public boolean execL212(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L212:
		{
// spec           [3, 4]
// commit         [merge]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, merge_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// unify          [1, 2, 1, 1, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 1 );
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 3 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L212;
		}
		return ret;
	}
	public boolean execL213(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L213:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL215(var0, var1)) {
				ret = true;
				break L213;
			}
		}
		return ret;
	}
	public boolean execL215(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L215:
		{
// spec           [2, 8]
// func           [1, merge_4]
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L211, [0], [1, 6], []]
							if (execL211(var0,var1,var6)) {
								ret = true;
								break L215;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL209(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L209:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeTwo_4]
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 1, 1, 2]
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
// func           [3, ._3]
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
// jump           [L202, [0], [1, 2, 3], []]
								if (execL202(var0,var1,var2,var3)) {
									ret = true;
									break L209;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL202(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L202:
		{
// spec           [4, 6]
// derefatom   [4, 2, 0]
			link = ((Atom)var2).getArg(0);
			var4 = link.getAtom();
// derefatom   [5, 3, 0]
			link = ((Atom)var3).getArg(0);
			var5 = link.getAtom();
// isint          [4]
			if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
// isint          [5]
				if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
// igt            [4, 5]
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x > y))) {
// jump           [L203, [0], [1, 2, 3, 4, 5], []]
						if (execL203(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L202;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL203(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L203:
		{
// spec           [6, 11]
// commit         [mergeTwo]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeTwo_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// dequeueatom    [4]
			atom = ((Atom)var4);
			atom.dequeue();
// removeatom     [4, 0]
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
// dequeueatom    [5]
			atom = ((Atom)var5);
			atom.dequeue();
// removeatom     [5, 0]
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
// copyatom    [6, 0, 4]
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
// copyatom    [7, 0, 5]
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
// newatom     [8, 0, ._3]
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [9, 0, ._3]
			func = f1;
			var9 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [10, 0, mergeTwo_4]
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
// newlink        [8, 0, 7, 0, 0]
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var7), 0 );
// newlink        [8, 1, 10, 2, 0]
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var10), 2 );
// relink         [8, 2, 1, 2, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
// newlink        [9, 0, 6, 0, 0]
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var6), 0 );
// relink         [9, 1, 2, 1, 0]
			((Atom)var9).getMem().relinkAtomArgs(
				((Atom)var9), 1,
				((Atom)var2), 1 );
// newlink        [9, 2, 10, 0, 0]
			((Atom)var9).getMem().newLink(
				((Atom)var9), 2,
				((Atom)var10), 0 );
// relink         [10, 1, 3, 1, 0]
			((Atom)var10).getMem().relinkAtomArgs(
				((Atom)var10), 1,
				((Atom)var3), 1 );
// relink         [10, 3, 1, 3, 0]
			((Atom)var10).getMem().relinkAtomArgs(
				((Atom)var10), 3,
				((Atom)var1), 3 );
// enqueueatom    [10]
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// freeatom       [4]
// freeatom       [5]
// proceed        []
			ret = true;
			break L203;
		}
		return ret;
	}
	public boolean execL204(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L204:
		{
// spec           [2, 14]
// branch         [ ... ]
			if (execL206(var0, var1)) {
				ret = true;
				break L204;
			}
		}
		return ret;
	}
	public boolean execL206(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L206:
		{
// spec           [2, 14]
// func           [1, mergeTwo_4]
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 1, 1, 2]
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
// func           [10, ._3]
								if (!(!(f1).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// getlink    [12, 10, 1]
									link = ((Atom)var10).getArg(1);
									var12 = link;
// getlink    [13, 10, 2]
									link = ((Atom)var10).getArg(2);
									var13 = link;
// jump           [L202, [0], [1, 6, 10], []]
									if (execL202(var0,var1,var6,var10)) {
										ret = true;
										break L206;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL200(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L200:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeTwo_4]
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 1, 1, 2]
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
// func           [3, ._3]
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
// jump           [L193, [0], [1, 2, 3], []]
								if (execL193(var0,var1,var2,var3)) {
									ret = true;
									break L200;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL193(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L193:
		{
// spec           [4, 6]
// derefatom   [4, 2, 0]
			link = ((Atom)var2).getArg(0);
			var4 = link.getAtom();
// derefatom   [5, 3, 0]
			link = ((Atom)var3).getArg(0);
			var5 = link.getAtom();
// isint          [4]
			if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
// isint          [5]
				if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
// ile            [4, 5]
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x <= y))) {
// jump           [L194, [0], [1, 2, 3, 4, 5], []]
						if (execL194(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L193;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL194(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L194:
		{
// spec           [6, 11]
// commit         [mergeTwo]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeTwo_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// dequeueatom    [4]
			atom = ((Atom)var4);
			atom.dequeue();
// removeatom     [4, 0]
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
// dequeueatom    [5]
			atom = ((Atom)var5);
			atom.dequeue();
// removeatom     [5, 0]
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
// copyatom    [6, 0, 4]
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
// copyatom    [7, 0, 5]
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
// newatom     [8, 0, ._3]
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [9, 0, ._3]
			func = f1;
			var9 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [10, 0, mergeTwo_4]
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
// newlink        [8, 0, 6, 0, 0]
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
// newlink        [8, 1, 10, 2, 0]
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var10), 2 );
// relink         [8, 2, 1, 2, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
// newlink        [9, 0, 7, 0, 0]
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var7), 0 );
// relink         [9, 1, 3, 1, 0]
			((Atom)var9).getMem().relinkAtomArgs(
				((Atom)var9), 1,
				((Atom)var3), 1 );
// newlink        [9, 2, 10, 1, 0]
			((Atom)var9).getMem().newLink(
				((Atom)var9), 2,
				((Atom)var10), 1 );
// relink         [10, 0, 2, 1, 0]
			((Atom)var10).getMem().relinkAtomArgs(
				((Atom)var10), 0,
				((Atom)var2), 1 );
// relink         [10, 3, 1, 3, 0]
			((Atom)var10).getMem().relinkAtomArgs(
				((Atom)var10), 3,
				((Atom)var1), 3 );
// enqueueatom    [10]
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// freeatom       [4]
// freeatom       [5]
// proceed        []
			ret = true;
			break L194;
		}
		return ret;
	}
	public boolean execL195(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L195:
		{
// spec           [2, 14]
// branch         [ ... ]
			if (execL197(var0, var1)) {
				ret = true;
				break L195;
			}
		}
		return ret;
	}
	public boolean execL197(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L197:
		{
// spec           [2, 14]
// func           [1, mergeTwo_4]
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 1, 1, 2]
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
// func           [10, ._3]
								if (!(!(f1).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// getlink    [12, 10, 1]
									link = ((Atom)var10).getArg(1);
									var12 = link;
// getlink    [13, 10, 2]
									link = ((Atom)var10).getArg(2);
									var13 = link;
// jump           [L193, [0], [1, 6, 10], []]
									if (execL193(var0,var1,var6,var10)) {
										ret = true;
										break L197;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL191(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L191:
		{
// spec           [1, 3]
// findatom    [1, 0, mergeTwo_4]
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 1, 0]
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L185, [0], [1, 2], []]
						if (execL185(var0,var1,var2)) {
							ret = true;
							break L191;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL185(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L185:
		{
// spec           [3, 3]
// jump           [L186, [0], [1, 2], []]
			if (execL186(var0,var1,var2)) {
				ret = true;
				break L185;
			}
		}
		return ret;
	}
	public boolean execL186(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L186:
		{
// spec           [3, 4]
// commit         [mergeTwo]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, mergeTwo_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// unify          [1, 2, 1, 0, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 0 );
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 3 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L186;
		}
		return ret;
	}
	public boolean execL187(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L187:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL189(var0, var1)) {
				ret = true;
				break L187;
			}
		}
		return ret;
	}
	public boolean execL189(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L189:
		{
// spec           [2, 8]
// func           [1, mergeTwo_4]
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 1, 0]
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L185, [0], [1, 6], []]
							if (execL185(var0,var1,var6)) {
								ret = true;
								break L189;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL183(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L183:
		{
// spec           [1, 3]
// findatom    [1, 0, mergeTwo_4]
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L177, [0], [1, 2], []]
						if (execL177(var0,var1,var2)) {
							ret = true;
							break L183;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL177(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L177:
		{
// spec           [3, 3]
// jump           [L178, [0], [1, 2], []]
			if (execL178(var0,var1,var2)) {
				ret = true;
				break L177;
			}
		}
		return ret;
	}
	public boolean execL178(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L178:
		{
// spec           [3, 4]
// commit         [mergeTwo]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, mergeTwo_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// unify          [1, 2, 1, 1, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 1 );
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// relink         [3, 0, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 3 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L178;
		}
		return ret;
	}
	public boolean execL179(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L179:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL181(var0, var1)) {
				ret = true;
				break L179;
			}
		}
		return ret;
	}
	public boolean execL181(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L181:
		{
// spec           [2, 8]
// func           [1, mergeTwo_4]
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L177, [0], [1, 6], []]
							if (execL177(var0,var1,var6)) {
								ret = true;
								break L181;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL175(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L175:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeOneLevel_4]
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 2, 1, 2]
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
// func           [3, ._3]
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
// jump           [L168, [0], [1, 3, 2], []]
								if (execL168(var0,var1,var3,var2)) {
									ret = true;
									break L175;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL168(Object var0, Object var1, Object var2, Object var3) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L168:
		{
// spec           [4, 4]
// jump           [L169, [0], [1, 2, 3], []]
			if (execL169(var0,var1,var2,var3)) {
				ret = true;
				break L168;
			}
		}
		return ret;
	}
	public boolean execL169(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L169:
		{
// spec           [4, 8]
// commit         [mergeOneLevel]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeOneLevel_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// newatom     [4, 0, mergeTwo_4]
			func = f4;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [5, 0, ._3]
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [6, 0, ._3]
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [7, 0, mergeOneLevel_4]
			func = f5;
			var7 = ((AbstractMembrane)var0).newAtom(func);
// relink         [4, 0, 3, 0, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 0 );
// relink         [4, 1, 2, 0, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var2), 0 );
// newlink        [4, 2, 5, 0, 0]
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var5), 0 );
// newlink        [4, 3, 6, 0, 0]
			((Atom)var4).getMem().newLink(
				((Atom)var4), 3,
				((Atom)var6), 0 );
// newlink        [5, 1, 7, 1, 0]
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var7), 1 );
// relink         [5, 2, 1, 1, 0]
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 1 );
// newlink        [6, 1, 7, 2, 0]
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var7), 2 );
// relink         [6, 2, 1, 2, 0]
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
// relink         [7, 0, 2, 1, 0]
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var2), 1 );
// relink         [7, 3, 1, 3, 0]
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 3,
				((Atom)var1), 3 );
// enqueueatom    [7]
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [4]
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// proceed        []
			ret = true;
			break L169;
		}
		return ret;
	}
	public boolean execL170(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L170:
		{
// spec           [2, 14]
// branch         [ ... ]
			if (execL172(var0, var1)) {
				ret = true;
				break L170;
			}
		}
		return ret;
	}
	public boolean execL172(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L172:
		{
// spec           [2, 14]
// func           [1, mergeOneLevel_4]
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 6, 1, 2]
							link = ((Atom)var6).getArg(1);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
// func           [10, ._3]
								if (!(!(f1).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// getlink    [12, 10, 1]
									link = ((Atom)var10).getArg(1);
									var12 = link;
// getlink    [13, 10, 2]
									link = ((Atom)var10).getArg(2);
									var13 = link;
// jump           [L168, [0], [1, 10, 6], []]
									if (execL168(var0,var1,var10,var6)) {
										ret = true;
										break L172;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL166(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L166:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeOneLevel_4]
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 2, 1, 0]
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
// func           [3, []_1]
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
// jump           [L159, [0], [1, 3, 2], []]
								if (execL159(var0,var1,var3,var2)) {
									ret = true;
									break L166;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL159(Object var0, Object var1, Object var2, Object var3) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L159:
		{
// spec           [4, 4]
// jump           [L160, [0], [1, 2, 3], []]
			if (execL160(var0,var1,var2,var3)) {
				ret = true;
				break L159;
			}
		}
		return ret;
	}
	public boolean execL160(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L160:
		{
// spec           [4, 6]
// commit         [mergeOneLevel]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeOneLevel_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// newatom     [4, 0, []_1]
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [5, 0, ._3]
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// unify          [1, 2, 1, 3, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 3 );
// newlink        [4, 0, 5, 1, 0]
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 1 );
// relink         [5, 0, 3, 0, 0]
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var3), 0 );
// relink         [5, 2, 1, 1, 0]
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 1 );
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// proceed        []
			ret = true;
			break L160;
		}
		return ret;
	}
	public boolean execL161(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L161:
		{
// spec           [2, 12]
// branch         [ ... ]
			if (execL163(var0, var1)) {
				ret = true;
				break L161;
			}
		}
		return ret;
	}
	public boolean execL163(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L163:
		{
// spec           [2, 12]
// func           [1, mergeOneLevel_4]
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 6, 1, 0]
							link = ((Atom)var6).getArg(1);
							if (!(link.getPos() != 0)) {
								var10 = link.getAtom();
// func           [10, []_1]
								if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// jump           [L159, [0], [1, 10, 6], []]
									if (execL159(var0,var1,var10,var6)) {
										ret = true;
										break L163;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL157(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L157:
		{
// spec           [1, 3]
// findatom    [1, 0, mergeOneLevel_4]
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L151, [0], [1, 2], []]
						if (execL151(var0,var1,var2)) {
							ret = true;
							break L157;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL151(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L151:
		{
// spec           [3, 3]
// jump           [L152, [0], [1, 2], []]
			if (execL152(var0,var1,var2)) {
				ret = true;
				break L151;
			}
		}
		return ret;
	}
	public boolean execL152(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L152:
		{
// spec           [3, 4]
// commit         [mergeOneLevel]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, mergeOneLevel_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// unify          [1, 2, 1, 3, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 3 );
// relink         [3, 0, 1, 1, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 1 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L152;
		}
		return ret;
	}
	public boolean execL153(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L153:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL155(var0, var1)) {
				ret = true;
				break L153;
			}
		}
		return ret;
	}
	public boolean execL155(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L155:
		{
// spec           [2, 8]
// func           [1, mergeOneLevel_4]
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L151, [0], [1, 6], []]
							if (execL151(var0,var1,var6)) {
								ret = true;
								break L155;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL149(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L149:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeMany_4]
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 2, 1, 2]
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
// func           [3, ._3]
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
// jump           [L142, [0], [1, 3, 2], []]
								if (execL142(var0,var1,var3,var2)) {
									ret = true;
									break L149;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL142(Object var0, Object var1, Object var2, Object var3) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L142:
		{
// spec           [4, 4]
// jump           [L143, [0], [1, 2, 3], []]
			if (execL143(var0,var1,var2,var3)) {
				ret = true;
				break L142;
			}
		}
		return ret;
	}
	public boolean execL143(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L143:
		{
// spec           [4, 9]
// commit         [mergeMany]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeMany_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// newatom     [4, 0, ._3]
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [5, 0, ._3]
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [6, 0, mergeOneLevel_4]
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [7, 0, mergeMany_4]
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [8, 0, merge_4]
			func = f3;
			var8 = ((AbstractMembrane)var0).newAtom(func);
// relink         [4, 0, 2, 0, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var2), 0 );
// relink         [4, 1, 2, 1, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var2), 1 );
// newlink        [4, 2, 5, 1, 0]
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var5), 1 );
// relink         [5, 0, 3, 0, 0]
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var3), 0 );
// newlink        [5, 2, 6, 0, 0]
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var6), 0 );
// newlink        [6, 1, 7, 0, 0]
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var7), 0 );
// newlink        [6, 2, 8, 0, 0]
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var8), 0 );
// newlink        [6, 3, 8, 3, 0]
			((Atom)var6).getMem().newLink(
				((Atom)var6), 3,
				((Atom)var8), 3 );
// relink         [7, 1, 1, 1, 0]
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var1), 1 );
// newlink        [7, 2, 8, 1, 0]
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 1 );
// relink         [7, 3, 1, 3, 0]
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 3,
				((Atom)var1), 3 );
// relink         [8, 2, 1, 2, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
// enqueueatom    [8]
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [7]
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [6]
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// proceed        []
			ret = true;
			break L143;
		}
		return ret;
	}
	public boolean execL144(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L144:
		{
// spec           [2, 14]
// branch         [ ... ]
			if (execL146(var0, var1)) {
				ret = true;
				break L144;
			}
		}
		return ret;
	}
	public boolean execL146(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L146:
		{
// spec           [2, 14]
// func           [1, mergeMany_4]
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 6, 1, 2]
							link = ((Atom)var6).getArg(1);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
// func           [10, ._3]
								if (!(!(f1).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// getlink    [12, 10, 1]
									link = ((Atom)var10).getArg(1);
									var12 = link;
// getlink    [13, 10, 2]
									link = ((Atom)var10).getArg(2);
									var13 = link;
// jump           [L142, [0], [1, 10, 6], []]
									if (execL142(var0,var1,var10,var6)) {
										ret = true;
										break L146;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL140(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L140:
		{
// spec           [1, 4]
// findatom    [1, 0, mergeMany_4]
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 2, 1, 0]
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
// func           [3, []_1]
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
// jump           [L133, [0], [1, 3, 2], []]
								if (execL133(var0,var1,var3,var2)) {
									ret = true;
									break L140;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL133(Object var0, Object var1, Object var2, Object var3) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L133:
		{
// spec           [4, 4]
// jump           [L134, [0], [1, 2, 3], []]
			if (execL134(var0,var1,var2,var3)) {
				ret = true;
				break L133;
			}
		}
		return ret;
	}
	public boolean execL134(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L134:
		{
// spec           [4, 6]
// commit         [mergeMany]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// removeatom     [1, 0, mergeMany_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// unify          [1, 1, 3, 0, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 1,
				((Atom)var3), 0 );
// newatom     [4, 0, ._3]
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [5, 0, []_1]
			func = f2;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// newlink        [4, 0, 5, 0, 0]
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 0 );
// relink         [4, 1, 1, 3, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 3 );
// relink         [4, 2, 1, 2, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 2 );
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// proceed        []
			ret = true;
			break L134;
		}
		return ret;
	}
	public boolean execL135(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L135:
		{
// spec           [2, 12]
// branch         [ ... ]
			if (execL137(var0, var1)) {
				ret = true;
				break L135;
			}
		}
		return ret;
	}
	public boolean execL137(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L137:
		{
// spec           [2, 12]
// func           [1, mergeMany_4]
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 6, 1, 0]
							link = ((Atom)var6).getArg(1);
							if (!(link.getPos() != 0)) {
								var10 = link.getAtom();
// func           [10, []_1]
								if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// jump           [L133, [0], [1, 10, 6], []]
									if (execL133(var0,var1,var10,var6)) {
										ret = true;
										break L137;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL131(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L131:
		{
// spec           [1, 3]
// findatom    [1, 0, mergeMany_4]
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L125, [0], [1, 2], []]
						if (execL125(var0,var1,var2)) {
							ret = true;
							break L131;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL125(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L125:
		{
// spec           [3, 3]
// jump           [L126, [0], [1, 2], []]
			if (execL126(var0,var1,var2)) {
				ret = true;
				break L125;
			}
		}
		return ret;
	}
	public boolean execL126(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L126:
		{
// spec           [3, 4]
// commit         [mergeMany]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, mergeMany_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// unify          [1, 2, 1, 3, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 3 );
// relink         [3, 0, 1, 1, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 1 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L126;
		}
		return ret;
	}
	public boolean execL127(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L127:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL129(var0, var1)) {
				ret = true;
				break L127;
			}
		}
		return ret;
	}
	public boolean execL129(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L129:
		{
// spec           [2, 8]
// func           [1, mergeMany_4]
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L125, [0], [1, 6], []]
							if (execL125(var0,var1,var6)) {
								ret = true;
								break L129;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL123(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L123:
		{
// spec           [1, 5]
// findatom    [1, 0, wrapElem_4]
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 2]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
// func           [2, ._3]
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
// deref       [3, 1, 1, 2]
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
// func           [3, ._3]
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
// deref       [4, 3, 0, 0]
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
// func           [4, []_1]
									if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
// jump           [L115, [0], [1, 2, 3, 4], []]
										if (execL115(var0,var1,var2,var3,var4)) {
											ret = true;
											break L123;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL115(Object var0, Object var1, Object var2, Object var3, Object var4) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L115:
		{
// spec           [5, 5]
// jump           [L116, [0], [1, 2, 3, 4], []]
			if (execL116(var0,var1,var2,var3,var4)) {
				ret = true;
				break L115;
			}
		}
		return ret;
	}
	public boolean execL116(Object var0, Object var1, Object var2, Object var3, Object var4) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L116:
		{
// spec           [5, 9]
// commit         [wrapElem]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// dequeueatom    [3]
			atom = ((Atom)var3);
			atom.dequeue();
// dequeueatom    [4]
			atom = ((Atom)var4);
			atom.dequeue();
// removeatom     [1, 0, wrapElem_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, ._3]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// removeatom     [3, 0, ._3]
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
// removeatom     [4, 0, []_1]
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
// newatom     [5, 0, ._3]
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [6, 0, []_1]
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [7, 0, ._3]
			func = f1;
			var7 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [8, 0, wrapElem_4]
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
// newlink        [5, 0, 7, 2, 0]
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var7), 2 );
// newlink        [5, 1, 8, 2, 0]
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var8), 2 );
// relink         [5, 2, 1, 2, 0]
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 2 );
// newlink        [6, 0, 7, 1, 0]
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
// relink         [7, 0, 2, 0, 0]
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var2), 0 );
// relink         [8, 0, 2, 1, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 0,
				((Atom)var2), 1 );
// relink         [8, 1, 3, 1, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 1,
				((Atom)var3), 1 );
// relink         [8, 3, 1, 3, 0]
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 3,
				((Atom)var1), 3 );
// enqueueatom    [8]
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// freeatom       [2]
// freeatom       [3]
// freeatom       [4]
// proceed        []
			ret = true;
			break L116;
		}
		return ret;
	}
	public boolean execL117(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L117:
		{
// spec           [2, 16]
// branch         [ ... ]
			if (execL119(var0, var1)) {
				ret = true;
				break L117;
			}
		}
		return ret;
	}
	public boolean execL119(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L119:
		{
// spec           [2, 16]
// func           [1, wrapElem_4]
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 2]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
// func           [6, ._3]
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// getlink     [8, 6, 1]
							link = ((Atom)var6).getArg(1);
							var8 = link;
// getlink     [9, 6, 2]
							link = ((Atom)var6).getArg(2);
							var9 = link;
// deref      [10, 1, 1, 2]
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
// func           [10, ._3]
								if (!(!(f1).equals(((Atom)var10).getFunctor()))) {
// getlink    [11, 10, 0]
									link = ((Atom)var10).getArg(0);
									var11 = link;
// getlink    [12, 10, 1]
									link = ((Atom)var10).getArg(1);
									var12 = link;
// getlink    [13, 10, 2]
									link = ((Atom)var10).getArg(2);
									var13 = link;
// deref      [14, 10, 0, 0]
									link = ((Atom)var10).getArg(0);
									if (!(link.getPos() != 0)) {
										var14 = link.getAtom();
// func           [14, []_1]
										if (!(!(f2).equals(((Atom)var14).getFunctor()))) {
// getlink    [15, 14, 0]
											link = ((Atom)var14).getArg(0);
											var15 = link;
// jump           [L115, [0], [1, 6, 10, 14], []]
											if (execL115(var0,var1,var6,var10,var14)) {
												ret = true;
												break L119;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL113(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L113:
		{
// spec           [1, 3]
// findatom    [1, 0, wrapElem_4]
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// deref       [2, 1, 0, 0]
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
// func           [2, []_1]
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
// jump           [L107, [0], [1, 2], []]
						if (execL107(var0,var1,var2)) {
							ret = true;
							break L113;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL107(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L107:
		{
// spec           [3, 3]
// jump           [L108, [0], [1, 2], []]
			if (execL108(var0,var1,var2)) {
				ret = true;
				break L107;
			}
		}
		return ret;
	}
	public boolean execL108(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L108:
		{
// spec           [3, 4]
// commit         [wrapElem]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// dequeueatom    [2]
			atom = ((Atom)var2);
			atom.dequeue();
// removeatom     [1, 0, wrapElem_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// removeatom     [2, 0, []_1]
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
// newatom     [3, 0, []_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// unify          [1, 3, 1, 1, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyAtomArgs(
				((Atom)var1), 3,
				((Atom)var1), 1 );
// relink         [3, 0, 1, 2, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 2 );
// freeatom       [1]
// freeatom       [2]
// proceed        []
			ret = true;
			break L108;
		}
		return ret;
	}
	public boolean execL109(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L109:
		{
// spec           [2, 8]
// branch         [ ... ]
			if (execL111(var0, var1)) {
				ret = true;
				break L109;
			}
		}
		return ret;
	}
	public boolean execL111(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L111:
		{
// spec           [2, 8]
// func           [1, wrapElem_4]
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// deref       [6, 1, 0, 0]
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
// func           [6, []_1]
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
// getlink     [7, 6, 0]
							link = ((Atom)var6).getArg(0);
							var7 = link;
// jump           [L107, [0], [1, 6], []]
							if (execL107(var0,var1,var6)) {
								ret = true;
								break L111;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL105(Object var0) {
		Object var1 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L105:
		{
// spec           [1, 2]
// findatom    [1, 0, mergesort_4]
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
// jump           [L100, [0], [1], []]
				if (execL100(var0,var1)) {
					ret = true;
					break L105;
				}
			}
		}
		return ret;
	}
	public boolean execL100(Object var0, Object var1) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L100:
		{
// spec           [2, 2]
// jump           [L101, [0], [1], []]
			if (execL101(var0,var1)) {
				ret = true;
				break L100;
			}
		}
		return ret;
	}
	public boolean execL101(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L101:
		{
// spec           [2, 5]
// commit         [mergesort]
// dequeueatom    [1]
			atom = ((Atom)var1);
			atom.dequeue();
// removeatom     [1, 0, mergesort_4]
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
// newatom     [2, 0, append_4]
			func = f0;
			var2 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [3, 0, wrapElem_4]
			func = f7;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [4, 0, mergeMany_4]
			func = f6;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// relink         [2, 0, 1, 1, 0]
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 1 );
// newlink        [2, 1, 4, 2, 0]
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var4), 2 );
// newlink        [2, 2, 3, 1, 0]
			((Atom)var2).getMem().newLink(
				((Atom)var2), 2,
				((Atom)var3), 1 );
// newlink        [2, 3, 4, 3, 0]
			((Atom)var2).getMem().newLink(
				((Atom)var2), 3,
				((Atom)var4), 3 );
// relink         [3, 0, 1, 0, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
// newlink        [3, 2, 4, 0, 0]
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 0 );
// relink         [3, 3, 1, 3, 0]
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 3,
				((Atom)var1), 3 );
// relink         [4, 1, 1, 2, 0]
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 2 );
// enqueueatom    [4]
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [3]
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [2]
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
// freeatom       [1]
// proceed        []
			ret = true;
			break L101;
		}
		return ret;
	}
	public boolean execL102(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L102:
		{
// spec           [2, 6]
// branch         [ ... ]
			if (execL104(var0, var1)) {
				ret = true;
				break L102;
			}
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L104:
		{
// spec           [2, 6]
// func           [1, mergesort_4]
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
// testmem        [0, 1]
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
// getlink     [2, 1, 0]
					link = ((Atom)var1).getArg(0);
					var2 = link;
// getlink     [3, 1, 1]
					link = ((Atom)var1).getArg(1);
					var3 = link;
// getlink     [4, 1, 2]
					link = ((Atom)var1).getArg(2);
					var4 = link;
// getlink     [5, 1, 3]
					link = ((Atom)var1).getArg(3);
					var5 = link;
// jump           [L100, [0], [1], []]
					if (execL100(var0,var1)) {
						ret = true;
						break L104;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f6 = new Functor("mergeMany", 4, null);
	private static final Functor f0 = new Functor("append", 4, null);
	private static final Functor f8 = new Functor("mergesort", 4, null);
	private static final Functor f4 = new Functor("mergeTwo", 4, null);
	private static final Functor f1 = new Functor(".", 3, null);
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f5 = new Functor("mergeOneLevel", 4, null);
	private static final Functor f7 = new Functor("wrapElem", 4, null);
	private static final Functor f3 = new Functor("merge", 4, null);
}

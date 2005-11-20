package translated.module_array;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":array" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@array" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL102(mem, atom)) {
			return true;
		}
		if (execL109(mem, atom)) {
			return true;
		}
		if (execL118(mem, atom)) {
			return true;
		}
		if (execL127(mem, atom)) {
			return true;
		}
		if (execL136(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL105(mem)) {
			return true;
		}
		if (execL114(mem)) {
			return true;
		}
		if (execL123(mem)) {
			return true;
		}
		if (execL132(mem)) {
			return true;
		}
		if (execL141(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL141(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L141:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL134(var0,var1,var2)) {
							ret = true;
							break L141;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL134(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L134:
		{
			link = ((Atom)var2).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var2).getArg(2);
				var4 = link.getAtom();
				if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
					if (execL135(var0,var1,var2,var3,var4)) {
						ret = true;
						break L134;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL135(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L135:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 3 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var2), 3 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 0,
				((Atom)var1), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var5), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var8, 4);
			ret = true;
			break L135;
		}
		return ret;
	}
	public boolean execL136(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L136:
		{
			if (execL138(var0, var1)) {
				ret = true;
				break L136;
			}
			if (execL140(var0, var1)) {
				ret = true;
				break L136;
			}
		}
		return ret;
	}
	public boolean execL140(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L140:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var6 = link.getAtom();
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							if (execL134(var0,var6,var1)) {
								ret = true;
								break L140;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL138(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L138:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(3);
							var8 = link;
							if (execL134(var0,var1,var4)) {
								ret = true;
								break L138;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL132(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L132:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL125(var0,var1,var2)) {
							ret = true;
							break L132;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL125(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L125:
		{
			link = ((Atom)var2).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				if (execL126(var0,var1,var2,var3)) {
					ret = true;
					break L125;
				}
			}
		}
		return ret;
	}
	public boolean execL126(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L126:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f0;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 3 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var2), 3 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var2), 2 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 3);
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
		Object var8 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L127:
		{
			if (execL129(var0, var1)) {
				ret = true;
				break L127;
			}
			if (execL131(var0, var1)) {
				ret = true;
				break L127;
			}
		}
		return ret;
	}
	public boolean execL131(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L131:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var6 = link.getAtom();
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							if (execL125(var0,var6,var1)) {
								ret = true;
								break L131;
							}
						}
					}
				}
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
		Object var8 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L129:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(3);
							var8 = link;
							if (execL125(var0,var1,var4)) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L123:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f6).equals(((Atom)var2).getFunctor()))) {
						if (execL116(var0,var1,var2)) {
							ret = true;
							break L123;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL116(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L116:
		{
			link = ((Atom)var2).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				if (execL117(var0,var1,var2,var3)) {
					ret = true;
					break L116;
				}
			}
		}
		return ret;
	}
	public boolean execL117(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L117:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 4 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var2), 4 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var2), 2 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 3,
				((Atom)var2), 3 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 2);
			ret = true;
			break L117;
		}
		return ret;
	}
	public boolean execL118(Object var0, Object var1) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L118:
		{
			if (execL120(var0, var1)) {
				ret = true;
				break L118;
			}
			if (execL122(var0, var1)) {
				ret = true;
				break L118;
			}
		}
		return ret;
	}
	public boolean execL122(Object var0, Object var1) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L122:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var7 = link.getAtom();
						if (!(!(f5).equals(((Atom)var7).getFunctor()))) {
							link = ((Atom)var7).getArg(0);
							var8 = link;
							link = ((Atom)var7).getArg(1);
							var9 = link;
							if (execL116(var0,var7,var1)) {
								ret = true;
								break L122;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL120(Object var0, Object var1) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L120:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f6).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(3);
							var8 = link;
							link = ((Atom)var4).getArg(4);
							var9 = link;
							if (execL116(var0,var1,var4)) {
								ret = true;
								break L120;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL114(Object var0) {
		Object var1 = null;
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L114:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f6).equals(((Atom)var2).getFunctor()))) {
						if (execL107(var0,var1,var2)) {
							ret = true;
							break L114;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL107(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L107:
		{
			link = ((Atom)var2).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				if (execL108(var0,var1,var2,var3)) {
					ret = true;
					break L107;
				}
			}
		}
		return ret;
	}
	public boolean execL108(Object var0, Object var1, Object var2, Object var3) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L108:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 4 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var2), 4 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var2), 2 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 3,
				((Atom)var2), 3 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 1);
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
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L109:
		{
			if (execL111(var0, var1)) {
				ret = true;
				break L109;
			}
			if (execL113(var0, var1)) {
				ret = true;
				break L109;
			}
		}
		return ret;
	}
	public boolean execL113(Object var0, Object var1) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L113:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var7 = link.getAtom();
						if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
							link = ((Atom)var7).getArg(0);
							var8 = link;
							link = ((Atom)var7).getArg(1);
							var9 = link;
							if (execL107(var0,var7,var1)) {
								ret = true;
								break L113;
							}
						}
					}
				}
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
		Object var8 = null;
		Object var9 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L111:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f6).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(3);
							var8 = link;
							link = ((Atom)var4).getArg(4);
							var9 = link;
							if (execL107(var0,var1,var4)) {
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
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L105:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL100(var0,var1)) {
					ret = true;
					break L105;
				}
			}
		}
		return ret;
	}
	public boolean execL100(Object var0, Object var1) {
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L100:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				if (execL101(var0,var1,var2)) {
					ret = true;
					break L100;
				}
			}
		}
		return ret;
	}
	public boolean execL101(Object var0, Object var1, Object var2) {
		Object var3 = null;
		Object var4 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L101:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f10;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodearray.run((Atom)var4, 0);
			ret = true;
			break L101;
		}
		return ret;
	}
	public boolean execL102(Object var0, Object var1) {
		Object var2 = null;
		Object var3 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L102:
		{
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L104:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL100(var0,var1)) {
						ret = true;
						break L104;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f0 = new Functor("int_array", 2, null);
	private static final Functor f3 = new Functor("get", 4, "array");
	private static final Functor f1 = new Functor("put", 4, "array");
	private static final Functor f4 = new Functor("/*inline*/\\r\\n		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		int len = me.nthAtom(0).getFunctor().getArity()-1;\\r\\n		i = (i+len) % len;\\r\\n		\\r\\n	  int v = ((IntegerFunctor)me.nthAtom(0).nthAtom(i).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new IntegerFunctor(v));\\r\\n		/* Functor f = me.nthAtom(0).nthAtom(i).getFunctor(); */\\r\\n		/* Atom result = mem.newAtom(f); */\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		mem.unifyAtomArgs(me, 3, me, 0);\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n		", 4, null);
	private static final Functor f6 = new Functor("update", 5, "array");
	private static final Functor f5 = new Functor("array", 2, null);
	private static final Functor f7 = new Functor("/*inline*/\\r\\n	  int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		int len = me.nthAtom(0).getFunctor().getArity()-1;\\r\\n		i = (i+len) % len;\\r\\n		\\r\\n		mem.swapAtomArgs(me.nthAtom(0), i, me, 3);\\r\\n		mem.unifyAtomArgs(me, 4, me, 0);\\r\\n		mem.unifyAtomArgs(me, 3, me, 2);\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n		", 5, null);
	private static final Functor f8 = new Functor("/*inline*/\\r\\n		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		int len = me.nthAtom(0).getFunctor().getArity()-1;\\r\\n		i = (i+len) % len;\\r\\n		\\r\\n		mem.swapAtomArgs(me.nthAtom(0), i, me, 3);\\r\\n		mem.unifyAtomArgs(me, 4, me, 0);\\r\\n		mem.unifyAtomArgs(me, 3, me, 2);\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n		", 5, null);
	private static final Functor f10 = new Functor("/*inline*/\\r\\n		int l = ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new Functor(\"int_array\", 2));\\r\\n		Atom result2 = mem.newAtom(new Functor(\"@\", l+1));\\r\\n		for(int i=0;i<l;i++) {\\r\\n			Atom el = mem.newAtom(new IntegerFunctor(0));\\r\\n			mem.newLink(el, 0, result2, i);\\r\\n		}\\r\\n		mem.newLink(result, 0, result2, l);\\r\\n		mem.relink(result, 1, me, 1);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n		", 2, null);
	private static final Functor f2 = new Functor("/*inline*/\\r\\n		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		int len = me.nthAtom(0).getFunctor().getArity()-1;\\r\\n		i = (i+len) % len;\\r\\n		\\r\\n		me.nthAtom(0).nthAtom(i).remove();\\r\\n		mem.relink(me.nthAtom(0), i, me, 2);\\r\\n		mem.unifyAtomArgs(me, 3, me, 0);\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n		", 4, null);
	private static final Functor f9 = new Functor("new", 2, "array");
}

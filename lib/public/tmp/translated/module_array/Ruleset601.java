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
		if (execL104(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "new");
			return true;
		}
		if (execL113(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "int_array");
			return true;
		}
		if (execL124(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "array");
			return true;
		}
		if (execL135(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "int_array");
			return true;
		}
		if (execL146(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "int_array");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL107(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "new");
			return true;
		}
		if (execL118(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "int_array");
			return true;
		}
		if (execL129(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "array");
			return true;
		}
		if (execL140(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "int_array");
			return true;
		}
		if (execL151(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "int_array");
			return true;
		}
		return result;
	}
	public boolean execL151(Object var0, boolean nondeterministic) {
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
L151:
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
						link = ((Atom)var2).getArg(1);
						var3 = link.getAtom();
						link = ((Atom)var2).getArg(2);
						var4 = link.getAtom();
						if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
								if (execL145(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L151;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL145(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
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
L145:
		{
			link = ((Atom)var2).getArg(3);
			var9 = link;
			link = ((Atom)var1).getArg(0);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
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
			func = f2;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var8), 3 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var9 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 0,
				(Link)var10 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var5), 0 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var8, 4);
			ret = true;
			break L145;
		}
		return ret;
	}
	public boolean execL146(Object var0, Object var1, boolean nondeterministic) {
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
L146:
		{
			if (execL148(var0, var1, nondeterministic)) {
				ret = true;
				break L146;
			}
			if (execL150(var0, var1, nondeterministic)) {
				ret = true;
				break L146;
			}
		}
		return ret;
	}
	public boolean execL150(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L150:
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
						link = ((Atom)var1).getArg(1);
						var9 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var10 = link.getAtom();
						if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
								if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									if (execL145(var0,var6,var1,var9,var10,nondeterministic)) {
										ret = true;
										break L150;
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
	public boolean execL148(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L148:
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
							link = ((Atom)var4).getArg(1);
							var9 = link.getAtom();
							link = ((Atom)var4).getArg(2);
							var10 = link.getAtom();
							if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
								if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
									if (execL145(var0,var1,var4,var9,var10,nondeterministic)) {
										ret = true;
										break L148;
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
	public boolean execL140(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L140:
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
						link = ((Atom)var2).getArg(1);
						var3 = link.getAtom();
						if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
							if (execL134(var0,var1,var2,var3,nondeterministic)) {
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
	public boolean execL134(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L134:
		{
			link = ((Atom)var2).getArg(3);
			var7 = link;
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var2).getArg(2);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 3 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var7 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var9 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 3);
			ret = true;
			break L134;
		}
		return ret;
	}
	public boolean execL135(Object var0, Object var1, boolean nondeterministic) {
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
L135:
		{
			if (execL137(var0, var1, nondeterministic)) {
				ret = true;
				break L135;
			}
			if (execL139(var0, var1, nondeterministic)) {
				ret = true;
				break L135;
			}
		}
		return ret;
	}
	public boolean execL139(Object var0, Object var1, boolean nondeterministic) {
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
L139:
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
						link = ((Atom)var1).getArg(1);
						var9 = link.getAtom();
						if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
								link = ((Atom)var6).getArg(0);
								var7 = link;
								link = ((Atom)var6).getArg(1);
								var8 = link;
								if (execL134(var0,var6,var1,var9,nondeterministic)) {
									ret = true;
									break L139;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL137(Object var0, Object var1, boolean nondeterministic) {
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
L137:
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
							link = ((Atom)var4).getArg(1);
							var9 = link.getAtom();
							if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
								if (execL134(var0,var1,var4,var9,nondeterministic)) {
									ret = true;
									break L137;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL129(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L129:
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
						link = ((Atom)var2).getArg(1);
						var3 = link.getAtom();
						if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
							if (execL123(var0,var1,var2,var3,nondeterministic)) {
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
	public boolean execL123(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
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
			link = ((Atom)var2).getArg(4);
			var7 = link;
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var2).getArg(2);
			var9 = link;
			link = ((Atom)var2).getArg(3);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f7;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 4 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var7 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var9 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 3,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 2);
			ret = true;
			break L123;
		}
		return ret;
	}
	public boolean execL124(Object var0, Object var1, boolean nondeterministic) {
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
L124:
		{
			if (execL126(var0, var1, nondeterministic)) {
				ret = true;
				break L124;
			}
			if (execL128(var0, var1, nondeterministic)) {
				ret = true;
				break L124;
			}
		}
		return ret;
	}
	public boolean execL128(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L128:
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
						link = ((Atom)var1).getArg(1);
						var10 = link.getAtom();
						if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f5).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								if (execL123(var0,var7,var1,var10,nondeterministic)) {
									ret = true;
									break L128;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL126(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
							link = ((Atom)var4).getArg(1);
							var10 = link.getAtom();
							if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
								if (execL123(var0,var1,var4,var10,nondeterministic)) {
									ret = true;
									break L126;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL118(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L118:
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
						link = ((Atom)var2).getArg(1);
						var3 = link.getAtom();
						if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
							if (execL112(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L118;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL112(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
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
L112:
		{
			link = ((Atom)var2).getArg(4);
			var7 = link;
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var2).getArg(2);
			var9 = link;
			link = ((Atom)var2).getArg(3);
			var10 = link;
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
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var7 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var9 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 3,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var6, 1);
			ret = true;
			break L112;
		}
		return ret;
	}
	public boolean execL113(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL115(var0, var1, nondeterministic)) {
				ret = true;
				break L113;
			}
			if (execL117(var0, var1, nondeterministic)) {
				ret = true;
				break L113;
			}
		}
		return ret;
	}
	public boolean execL117(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
						link = ((Atom)var1).getArg(1);
						var10 = link.getAtom();
						if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								if (execL112(var0,var7,var1,var10,nondeterministic)) {
									ret = true;
									break L117;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL115(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L115:
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
							link = ((Atom)var4).getArg(1);
							var10 = link.getAtom();
							if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
								if (execL112(var0,var1,var4,var10,nondeterministic)) {
									ret = true;
									break L115;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL107(Object var0, boolean nondeterministic) {
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
L107:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL103(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L107;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL103(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L103:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
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
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodearray.run((Atom)var4, 0);
			ret = true;
			break L103;
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL106(var0, var1, nondeterministic)) {
				ret = true;
				break L104;
			}
		}
		return ret;
	}
	public boolean execL106(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
L106:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL103(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L106;
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f0 = new Functor("int_array", 2, null);
	private static final Functor f6 = new Functor("update", 5, "array");
	private static final Functor f4 = new Functor("/*inline*/\r\n\t\tint i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tint len = me.nthAtom(0).getFunctor().getArity()-1;\r\n\t\ti = (i+len) % len;\r\n\t\t\r\n\t  int v = ((IntegerFunctor)me.nthAtom(0).nthAtom(i).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new IntegerFunctor(v));\r\n\t\t/* Functor f = me.nthAtom(0).nthAtom(i).getFunctor(); */\r\n\t\t/* Atom result = mem.newAtom(f); */\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tmem.unifyAtomArgs(me, 3, me, 0);\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\t", 4, null);
	private static final Functor f5 = new Functor("array", 2, null);
	private static final Functor f1 = new Functor("put", 4, "array");
	private static final Functor f7 = new Functor("/*inline*/\r\n\t  int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tint len = me.nthAtom(0).getFunctor().getArity()-1;\r\n\t\ti = (i+len) % len;\r\n\t\t\r\n\t\tmem.swapAtomArgs(me.nthAtom(0), i, me, 3);\r\n\t\tmem.unifyAtomArgs(me, 4, me, 0);\r\n\t\tmem.unifyAtomArgs(me, 3, me, 2);\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\t", 5, null);
	private static final Functor f9 = new Functor("new", 2, "array");
	private static final Functor f3 = new Functor("get", 4, "array");
	private static final Functor f8 = new Functor("/*inline*/\r\n\t\tint i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tint len = me.nthAtom(0).getFunctor().getArity()-1;\r\n\t\ti = (i+len) % len;\r\n\t\t\r\n\t\tmem.swapAtomArgs(me.nthAtom(0), i, me, 3);\r\n\t\tmem.unifyAtomArgs(me, 4, me, 0);\r\n\t\tmem.unifyAtomArgs(me, 3, me, 2);\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\t", 5, null);
	private static final Functor f10 = new Functor("/*inline*/\r\n\t\tint l = ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new Functor(\"int_array\", 2));\r\n\t\tAtom result2 = mem.newAtom(new Functor(\"@\", l+1));\r\n\t\tfor(int i=0;i<l;i++) {\r\n\t\t\tAtom el = mem.newAtom(new IntegerFunctor(0));\r\n\t\t\tmem.newLink(el, 0, result2, i);\r\n\t\t}\r\n\t\tmem.newLink(result, 0, result2, l);\r\n\t\tmem.relink(result, 1, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t\t", 2, null);
	private static final Functor f2 = new Functor("/*inline*/\r\n\t\tint i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tint len = me.nthAtom(0).getFunctor().getArity()-1;\r\n\t\ti = (i+len) % len;\r\n\t\t\r\n\t\tme.nthAtom(0).nthAtom(i).remove();\r\n\t\tmem.relink(me.nthAtom(0), i, me, 2);\r\n\t\tmem.unifyAtomArgs(me, 3, me, 0);\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\t", 4, null);
}

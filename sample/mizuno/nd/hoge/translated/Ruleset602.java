package translated;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset602 extends Ruleset {
	private static final Ruleset602 theInstance = new Ruleset602();
	private Ruleset602() {}
	public static Ruleset602 getInstance() {
		return theInstance;
	}
	private int id = 602;
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
		return react(mem, atom, false);
	}
	public boolean react(Membrane mem, Atom atom, boolean nondeterministic) {
		boolean result = false;
		if (execL109(mem, atom, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL110(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL110(Object var0, boolean nondeterministic) {
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
L110:
		{
			if (execL107(var0,nondeterministic)) {
				ret = true;
				break L110;
			}
		}
		return ret;
	}
	public boolean execL107(Object var0, boolean nondeterministic) {
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
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "L108",var0});
			} else if (execL108(var0,nondeterministic)) {
				ret = true;
				break L107;
			}
		}
		return ret;
	}
	public boolean execL108(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
		Object var22 = null;
		Object var23 = null;
		Object var24 = null;
		Object var25 = null;
		Object var26 = null;
		Object var27 = null;
		Object var28 = null;
		Object var29 = null;
		Object var30 = null;
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
		Object var36 = null;
		Object var37 = null;
		Object var38 = null;
		Object var39 = null;
		Object var40 = null;
		Object var41 = null;
		Object var42 = null;
		Object var43 = null;
		Object var44 = null;
		Object var45 = null;
		Object var46 = null;
		Object var47 = null;
		Object var48 = null;
		Object var49 = null;
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
			mem = ((AbstractMembrane)var0).newMem();
			var1 = mem;
			mem = ((AbstractMembrane)var1).newMem();
			var2 = mem;
			((AbstractMembrane)var2).setNondeterministic(true);
			((AbstractMembrane)var2).loadRuleset(Ruleset601.getInstance());
			func = f0;
			var3 = ((AbstractMembrane)var2).newAtom(func);
			func = f0;
			var4 = ((AbstractMembrane)var2).newAtom(func);
			func = f0;
			var5 = ((AbstractMembrane)var2).newAtom(func);
			func = f0;
			var6 = ((AbstractMembrane)var2).newAtom(func);
			func = f1;
			var7 = ((AbstractMembrane)var2).newAtom(func);
			func = f2;
			var8 = ((AbstractMembrane)var2).newAtom(func);
			func = f2;
			var9 = ((AbstractMembrane)var2).newAtom(func);
			func = f2;
			var10 = ((AbstractMembrane)var2).newAtom(func);
			func = f2;
			var11 = ((AbstractMembrane)var2).newAtom(func);
			func = f3;
			var12 = ((AbstractMembrane)var2).newAtom(func);
			func = f4;
			var13 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var14 = ((AbstractMembrane)var1).newAtom(func);
			func = f5;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var17 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var3), 0);
			var20 = link;
			link = new Link(((Atom)var4), 0);
			var21 = link;
			link = new Link(((Atom)var5), 0);
			var22 = link;
			link = new Link(((Atom)var6), 0);
			var23 = link;
			link = new Link(((Atom)var7), 0);
			var24 = link;
			link = new Link(((Atom)var8), 0);
			var25 = link;
			link = new Link(((Atom)var8), 1);
			var26 = link;
			link = new Link(((Atom)var8), 2);
			var27 = link;
			link = new Link(((Atom)var9), 0);
			var28 = link;
			link = new Link(((Atom)var9), 1);
			var29 = link;
			link = new Link(((Atom)var9), 2);
			var30 = link;
			link = new Link(((Atom)var10), 0);
			var31 = link;
			link = new Link(((Atom)var10), 1);
			var32 = link;
			link = new Link(((Atom)var10), 2);
			var33 = link;
			link = new Link(((Atom)var11), 0);
			var34 = link;
			link = new Link(((Atom)var11), 1);
			var35 = link;
			link = new Link(((Atom)var11), 2);
			var36 = link;
			link = new Link(((Atom)var12), 0);
			var37 = link;
			link = new Link(((Atom)var13), 0);
			var38 = link;
			link = new Link(((Atom)var14), 0);
			var39 = link;
			link = new Link(((Atom)var14), 1);
			var40 = link;
			link = new Link(((Atom)var15), 0);
			var41 = link;
			link = new Link(((Atom)var16), 0);
			var42 = link;
			link = new Link(((Atom)var16), 1);
			var43 = link;
			link = new Link(((Atom)var17), 0);
			var44 = link;
			link = new Link(((Atom)var18), 0);
			var45 = link;
			link = new Link(((Atom)var18), 1);
			var46 = link;
			link = new Link(((Atom)var18), 2);
			var47 = link;
			link = new Link(((Atom)var19), 0);
			var48 = link;
			link = new Link(((Atom)var19), 1);
			var49 = link;
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var20),
				((Link)var34));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var21),
				((Link)var31));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var22),
				((Link)var28));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var23),
				((Link)var25));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var24),
				((Link)var26));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var27),
				((Link)var29));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var30),
				((Link)var32));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var33),
				((Link)var35));
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var36),
				((Link)var37));
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var38),
				((Link)var40));
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var41),
				((Link)var42));
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var43),
				((Link)var46));
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var44),
				((Link)var47));
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var45),
				((Link)var49));
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var48),
				((Link)var39));
			atom = ((Atom)var18);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var17);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var16);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var13);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_java");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module java");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module java");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module java");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module java");
				}
				try {
					Class c = Class.forName("translated.Module_nd");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				}
			ret = true;
			break L108;
		}
		return ret;
	}
	public boolean execL109(Object var0, Object var1, boolean nondeterministic) {
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
		}
		return ret;
	}
	private static final Functor f8 = new Functor("reduct", 3, "nd");
	private static final Functor f1 = new Functor("[]", 1, null);
	private static final Functor f7 = new Functor("map", 1, null);
	private static final Functor f6 = new Functor("new", 2, "java");
	private static final Functor f5 = new StringFunctor("java.util.HashMap");
	private static final Functor f0 = new Functor("a", 1, null);
	private static final Functor f3 = new Functor("r", 1, null);
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f4 = new Functor("+", 1, null);
}

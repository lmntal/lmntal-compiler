package translated.module_thread;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset612 extends Ruleset {
	private static final Ruleset612 theInstance = new Ruleset612();
	private Ruleset612() {}
	public static Ruleset612 getInstance() {
		return theInstance;
	}
	private int id = 612;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":thread" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@thread" + id;
	}
	private String encodedRuleset = 
"(thread.create(N) :- '>'(N, 0) | thread.num(N), thread.cre(N))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL748(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@612", "create");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL751(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@612", "create");
			return true;
		}
		return result;
	}
	public boolean execL751(Object var0, boolean nondeterministic) {
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
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
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
L751:
		{
			var2 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "create", "L747",var0,var1,var2,var3});
						} else if (execL747(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L751;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL747(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
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
L747:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_thread");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				}
				try {
					Class c = Class.forName("translated.Module_thread");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				}
			ret = true;
			break L747;
		}
		return ret;
	}
	public boolean execL748(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
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
L748:
		{
			if (execL750(var0, var1, nondeterministic)) {
				ret = true;
				break L748;
			}
		}
		return ret;
	}
	public boolean execL750(Object var0, Object var1, boolean nondeterministic) {
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
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
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
L750:
		{
			var3 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "create", "L747",var0,var1,var3,var4});
							} else if (execL747(var0,var1,var3,var4,nondeterministic)) {
								ret = true;
								break L750;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f1 = new Functor("create", 1, "thread");
	private static final Functor f3 = new Functor("cre", 1, "thread");
	private static final Functor f2 = new Functor("num", 1, "thread");
	private static final Functor f0 = new IntegerFunctor(0);
}

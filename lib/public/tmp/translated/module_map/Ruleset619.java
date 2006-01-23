package translated.module_map;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset619 extends Ruleset {
	private static final Ruleset619 theInstance = new Ruleset619();
	private Ruleset619() {}
	public static Ruleset619 getInstance() {
		return theInstance;
	}
	private int id = 619;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":map" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@map" + id;
	}
	private String encodedRuleset = 
"('='(H, map.new) :- '='(H, map.new(X)), {obj(X)}), ('='(H, put(map.new(X), Key, Value)), {obj(X), '.'(E_Key, E_Value), $o} :- unary(Key), unary(Value), '='(Key, E_Key), unary(E_Value) | '='(H, map.new(X)), {obj(X), '.'(Key, Value), $o}), ('='(H, put(map.new(X), Key, Value)), {obj(X), $o} :- unary(Key), unary(Value) | '='(H, map.new(X)), {obj(X), '.'(Key, Value), $o}), ('='(H, get(map.new(X), Key, Return)), {obj(X), '.'(E_Key, E_Value), $o} :- unary(Key), unary(E_Value), '='(Key, E_Key) | '='(H, map.new(X)), {obj(X), '.'(E_Key, E_Value), $o}, '='(Return, E_Value)), ('='(H, get(map.new(X), Key)), {obj(X), '.'(E_Key, E_Value), $o} :- unary(Key), unary(E_Value), '='(Key, E_Key) | '='(H, map.new(X)), {obj(X), $o}), ('='(H, is_empty(map.new(X), Return)), {obj(X)} :- '='(H, map.new(X)), {obj(X)}, '='(Return, true)), ('='(H, map.of_queue(queue.new(Head, Tail))) :- '='(H, map.of_queue_s0(queue.new(Head, Tail), map.new))), ('='(H, map.of_queue_s0(Queue, Map)) :- '='(H, map.of_queue_s0(is_empty(Queue, IsEmpty), Map, IsEmpty))), ('='(H, map.of_queue_s0(Queue, Map, true)) :- '='(H, Map), nil(Queue)), ('='(H, map.of_queue_s0(Queue, Map, false)) :- '='(H, map.of_queue_s0(shift(shift(Queue, K), V), put(Map, K, V)))), (map.test :- '='(t0, get(put(put(put(map.new, jack, enigma), nina, myers), jack, bauer), jack, jack_is)), '='(t1, map.of_queue(push(push(push(push(queue.new, k0), v0), k1), v1))))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1572(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1581(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1598(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1613(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1630(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1647(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1662(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "new");
			return true;
		}
		if (execL1673(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "of_queue_s0");
			return true;
		}
		if (execL1682(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "true");
			return true;
		}
		if (execL1693(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "false");
			return true;
		}
		if (execL1704(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "test");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1575(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1592(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1607(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1624(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1641(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1656(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1667(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "new");
			return true;
		}
		if (execL1676(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "of_queue_s0");
			return true;
		}
		if (execL1687(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "true");
			return true;
		}
		if (execL1698(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "false");
			return true;
		}
		if (execL1707(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "test");
			return true;
		}
		return result;
	}
	public boolean execL1707(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1707:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "test", "L1703",var0,var1});
				} else if (execL1703(var0,var1,nondeterministic)) {
					ret = true;
					break L1707;
				}
			}
		}
		return ret;
	}
	public boolean execL1703(Object var0, Object var1, boolean nondeterministic) {
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
L1703:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f9;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f10;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var17 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f15;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var25 = ((AbstractMembrane)var0).newAtom(func);
			func = f18;
			var26 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var15), 3 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var6), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var6), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 2 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 3,
				((Atom)var9), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var9), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 2 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 3,
				((Atom)var12), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var12), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var12), 2 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 3,
				((Atom)var15), 0 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 0,
				((Atom)var15), 1 );
			((Atom)var14).getMem().newLink(
				((Atom)var14), 0,
				((Atom)var15), 2 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 0,
				((Atom)var26), 1 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 0,
				((Atom)var19), 0 );
			((Atom)var18).getMem().newLink(
				((Atom)var18), 0,
				((Atom)var19), 1 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 2,
				((Atom)var21), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 0,
				((Atom)var21), 1 );
			((Atom)var21).getMem().newLink(
				((Atom)var21), 2,
				((Atom)var23), 0 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 0,
				((Atom)var23), 1 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 2,
				((Atom)var25), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 0,
				((Atom)var25), 1 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 2,
				((Atom)var26), 0 );
			atom = ((Atom)var26);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var25);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var24);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var23);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var22);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var21);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var20);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var19);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var18);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var17);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var16);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var15);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var14);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var13);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
				try {
					Class c = Class.forName("translated.Module_queue");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				}
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			ret = true;
			break L1703;
		}
		return ret;
	}
	public boolean execL1704(Object var0, Object var1, boolean nondeterministic) {
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
L1704:
		{
			if (execL1706(var0, var1, nondeterministic)) {
				ret = true;
				break L1704;
			}
		}
		return ret;
	}
	public boolean execL1706(Object var0, Object var1, boolean nondeterministic) {
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
L1706:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "test", "L1703",var0,var1});
					} else if (execL1703(var0,var1,nondeterministic)) {
						ret = true;
						break L1706;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1698(Object var0, boolean nondeterministic) {
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
L1698:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f20).equals(((Atom)var2).getFunctor()))) {
						if (execL1692(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1698;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1692(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1692:
		{
			link = ((Atom)var2).getArg(0);
			var7 = link;
			link = ((Atom)var2).getArg(1);
			var8 = link;
			link = ((Atom)var2).getArg(3);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f21;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f21;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f22;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var7 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var5), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 2 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var6), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var8 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 3,
				((Atom)var6), 1 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var9 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			ret = true;
			break L1692;
		}
		return ret;
	}
	public boolean execL1693(Object var0, Object var1, boolean nondeterministic) {
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
L1693:
		{
			if (execL1695(var0, var1, nondeterministic)) {
				ret = true;
				break L1693;
			}
			if (execL1697(var0, var1, nondeterministic)) {
				ret = true;
				break L1693;
			}
		}
		return ret;
	}
	public boolean execL1697(Object var0, Object var1, boolean nondeterministic) {
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
L1697:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f19).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							if (execL1692(var0,var6,var1,nondeterministic)) {
								ret = true;
								break L1697;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1695(Object var0, Object var1, boolean nondeterministic) {
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
L1695:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f20).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							if (execL1692(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1695;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1687(Object var0, boolean nondeterministic) {
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
L1687:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f20).equals(((Atom)var2).getFunctor()))) {
						if (execL1681(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1687;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1681(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1681:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 3,
				((Atom)var2), 1 );
			link = ((Atom)var2).getArg(0);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f24;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L1681;
		}
		return ret;
	}
	public boolean execL1682(Object var0, Object var1, boolean nondeterministic) {
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
L1682:
		{
			if (execL1684(var0, var1, nondeterministic)) {
				ret = true;
				break L1682;
			}
			if (execL1686(var0, var1, nondeterministic)) {
				ret = true;
				break L1682;
			}
		}
		return ret;
	}
	public boolean execL1686(Object var0, Object var1, boolean nondeterministic) {
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
L1686:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f23).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							if (execL1681(var0,var6,var1,nondeterministic)) {
								ret = true;
								break L1686;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1684(Object var0, Object var1, boolean nondeterministic) {
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
L1684:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f20).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							if (execL1681(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1684;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1676(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1676:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1672(var0,var1,nondeterministic)) {
					ret = true;
					break L1676;
				}
			}
		}
		return ret;
	}
	public boolean execL1672(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
L1672:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f25;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f20;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var4 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var3), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 2,
				((Atom)var3), 0 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var6 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			ret = true;
			break L1672;
		}
		return ret;
	}
	public boolean execL1673(Object var0, Object var1, boolean nondeterministic) {
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
L1673:
		{
			if (execL1675(var0, var1, nondeterministic)) {
				ret = true;
				break L1673;
			}
		}
		return ret;
	}
	public boolean execL1675(Object var0, Object var1, boolean nondeterministic) {
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
L1675:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL1672(var0,var1,nondeterministic)) {
						ret = true;
						break L1675;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1667(Object var0, boolean nondeterministic) {
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
L1667:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f18).equals(((Atom)var2).getFunctor()))) {
						if (execL1661(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1667;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1661(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1661:
		{
			link = ((Atom)var2).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f22;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var5), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 1 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 2,
				(Link)var8 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_queue");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				}
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			ret = true;
			break L1661;
		}
		return ret;
	}
	public boolean execL1662(Object var0, Object var1, boolean nondeterministic) {
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
L1662:
		{
			if (execL1664(var0, var1, nondeterministic)) {
				ret = true;
				break L1662;
			}
			if (execL1666(var0, var1, nondeterministic)) {
				ret = true;
				break L1662;
			}
		}
		return ret;
	}
	public boolean execL1666(Object var0, Object var1, boolean nondeterministic) {
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
L1666:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f26).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL1661(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L1666;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1664(Object var0, Object var1, boolean nondeterministic) {
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
L1664:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f18).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL1661(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1664;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1656(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L1656:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f25).equals(((Atom)var3).getFunctor()))) {
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
								link = ((Atom)var2).getArg(0);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										mem = ((Atom)var4).getMem();
										if (mem.lock()) {
											var5 = mem;
											if (!(((AbstractMembrane)var5).getAtomCount() != 1)) {
												if (!(((AbstractMembrane)var5).getMemCount() != 0)) {
													if (!(((AbstractMembrane)var5).hasRules())) {
														link = ((Atom)var4).getArg(1);
														if (!(link.getPos() != 0)) {
															var6 = link.getAtom();
															if (!(!(f28).equals(((Atom)var6).getFunctor()))) {
																if (execL1646(var0,var5,var1,var3,var2,var6,var4,nondeterministic)) {
																	ret = true;
																	break L1656;
																}
															}
														}
													}
												}
											}
											((AbstractMembrane)var5).unlock();
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
	public boolean execL1646(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
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
L1646:
		{
			link = ((Atom)var3).getArg(2);
			var13 = link;
			link = ((Atom)var3).getArg(1);
			var14 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).activate();
			func = Functor.INSIDE_PROXY;
			var9 = ((AbstractMembrane)var1).newAtom(func);
			func = f23;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var9), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var12), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var13 );
			((Atom)var11).getMem().inheritLink(
				((Atom)var11), 0,
				(Link)var14 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var9), 0 );
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1646;
		}
		return ret;
	}
	public boolean execL1647(Object var0, Object var1, boolean nondeterministic) {
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
L1647:
		{
			if (execL1649(var0, var1, nondeterministic)) {
				ret = true;
				break L1647;
			}
			if (execL1651(var0, var1, nondeterministic)) {
				ret = true;
				break L1647;
			}
			if (execL1654(var0, var1, nondeterministic)) {
				ret = true;
				break L1647;
			}
		}
		return ret;
	}
	public boolean execL1654(Object var0, Object var1, boolean nondeterministic) {
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
L1654:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f27).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f25).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													mem = ((AbstractMembrane)var2);
													if (mem.lock()) {
														mem = ((AbstractMembrane)var2).getParent();
														if (!(mem == null)) {
															var3 = mem;
															if (!(((AbstractMembrane)var2).getAtomCount() != 1)) {
																if (!(((AbstractMembrane)var2).getMemCount() != 0)) {
																	if (!(((AbstractMembrane)var2).hasRules())) {
																		if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																			if (execL1646(var0,var2,var11,var14,var8,var1,var5,nondeterministic)) {
																				ret = true;
																				break L1654;
																			}
																		}
																	}
																}
															}
														}
														((AbstractMembrane)var2).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1651(Object var0, Object var1, boolean nondeterministic) {
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
L1651:
		{
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(f27).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											mem = ((Atom)var11).getMem();
											if (mem.lock()) {
												var12 = mem;
												if (!(((AbstractMembrane)var12).getAtomCount() != 1)) {
													if (!(((AbstractMembrane)var12).getMemCount() != 0)) {
														if (!(((AbstractMembrane)var12).hasRules())) {
															link = ((Atom)var11).getArg(0);
															var13 = link;
															link = ((Atom)var11).getArg(1);
															var14 = link;
															link = ((Atom)var11).getArg(1);
															if (!(link.getPos() != 0)) {
																var15 = link.getAtom();
																if (!(!(f28).equals(((Atom)var15).getFunctor()))) {
																	link = ((Atom)var15).getArg(0);
																	var16 = link;
																	if (execL1646(var0,var12,var5,var1,var8,var15,var11,nondeterministic)) {
																		ret = true;
																		break L1651;
																	}
																}
															}
														}
													}
												}
												((AbstractMembrane)var12).unlock();
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
	public boolean execL1649(Object var0, Object var1, boolean nondeterministic) {
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
L1649:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 0)) {
							var7 = link.getAtom();
							if (!(!(f25).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								link = ((Atom)var7).getArg(2);
								var10 = link;
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
									link = ((Atom)var4).getArg(0);
									var5 = link;
									link = ((Atom)var4).getArg(1);
									var6 = link;
									link = ((Atom)var4).getArg(0);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											mem = ((Atom)var11).getMem();
											if (mem.lock()) {
												var12 = mem;
												if (!(((AbstractMembrane)var12).getAtomCount() != 1)) {
													if (!(((AbstractMembrane)var12).getMemCount() != 0)) {
														if (!(((AbstractMembrane)var12).hasRules())) {
															link = ((Atom)var11).getArg(0);
															var13 = link;
															link = ((Atom)var11).getArg(1);
															var14 = link;
															link = ((Atom)var11).getArg(1);
															if (!(link.getPos() != 0)) {
																var15 = link.getAtom();
																if (!(!(f28).equals(((Atom)var15).getFunctor()))) {
																	link = ((Atom)var15).getArg(0);
																	var16 = link;
																	if (execL1646(var0,var12,var1,var7,var4,var15,var11,nondeterministic)) {
																		ret = true;
																		break L1649;
																	}
																}
															}
														}
													}
												}
												((AbstractMembrane)var12).unlock();
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
	public boolean execL1641(Object var0, boolean nondeterministic) {
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
L1641:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f29).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(1);
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
									link = ((Atom)var2).getArg(0);
									if (!(link.getPos() != 0)) {
										var4 = link.getAtom();
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
											mem = ((Atom)var4).getMem();
											if (mem.lock()) {
												var5 = mem;
												link = ((Atom)var4).getArg(1);
												if (!(link.getPos() != 0)) {
													var6 = link.getAtom();
													if (!(!(f28).equals(((Atom)var6).getFunctor()))) {
														func = f30;
														Iterator it2 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
														while (it2.hasNext()) {
															atom = (Atom) it2.next();
															var7 = atom;
															if (!(((AbstractMembrane)var5).hasRules())) {
																link = ((Atom)var7).getArg(1);
																var9 = link.getAtom();
																link = ((Atom)var7).getArg(0);
																var10 = link.getAtom();
																if (!(!((Atom)var8).getFunctor().equals(((Atom)var10).getFunctor()))) {
																	func = ((Atom)var9).getFunctor();
																	if (!(func.getArity() != 1)) {
																		if (execL1629(var0,var5,var1,var3,var2,var6,var7,var4,var8,var9,var10,nondeterministic)) {
																			ret = true;
																			break L1641;
																		}
																	}
																}
															}
														}
													}
												}
												((AbstractMembrane)var5).unlock();
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
	public boolean execL1629(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, boolean nondeterministic) {
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Object var16 = null;
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
L1629:
		{
			link = ((Atom)var3).getArg(2);
			var16 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			func = Functor.INSIDE_PROXY;
			var13 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var13), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var15), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var16 );
			((Atom)var15).getMem().newLink(
				((Atom)var15), 0,
				((Atom)var13), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1629;
		}
		return ret;
	}
	public boolean execL1630(Object var0, Object var1, boolean nondeterministic) {
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
L1630:
		{
			if (execL1632(var0, var1, nondeterministic)) {
				ret = true;
				break L1630;
			}
			if (execL1634(var0, var1, nondeterministic)) {
				ret = true;
				break L1630;
			}
			if (execL1637(var0, var1, nondeterministic)) {
				ret = true;
				break L1630;
			}
			if (execL1639(var0, var1, nondeterministic)) {
				ret = true;
				break L1630;
			}
		}
		return ret;
	}
	public boolean execL1639(Object var0, Object var1, boolean nondeterministic) {
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
L1639:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var22 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var23 = link.getAtom();
					func = ((Atom)var22).getFunctor();
					if (!(func.getArity() != 1)) {
						mem = ((AbstractMembrane)var2);
						if (mem.lock()) {
							mem = ((AbstractMembrane)var2).getParent();
							if (!(mem == null)) {
								var3 = mem;
								if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
									func = f28;
									Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var6 = atom;
										if (!(((AbstractMembrane)var2).hasRules())) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											link = ((Atom)var6).getArg(0);
											if (!(link.getPos() != 1)) {
												var8 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
													link = ((Atom)var8).getArg(0);
													var9 = link;
													link = ((Atom)var8).getArg(1);
													var10 = link;
													link = ((Atom)var8).getArg(0);
													if (!(link.getPos() != 0)) {
														var11 = link.getAtom();
														if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
															link = ((Atom)var11).getArg(0);
															var12 = link;
															link = ((Atom)var11).getArg(1);
															var13 = link;
															link = ((Atom)var11).getArg(1);
															if (!(link.getPos() != 0)) {
																var14 = link.getAtom();
																if (!(!(f27).equals(((Atom)var14).getFunctor()))) {
																	link = ((Atom)var14).getArg(0);
																	var15 = link;
																	link = ((Atom)var14).getArg(1);
																	var16 = link;
																	link = ((Atom)var14).getArg(1);
																	if (!(link.getPos() != 0)) {
																		var17 = link.getAtom();
																		if (!(!(f29).equals(((Atom)var17).getFunctor()))) {
																			link = ((Atom)var17).getArg(0);
																			var18 = link;
																			link = ((Atom)var17).getArg(1);
																			var19 = link;
																			link = ((Atom)var17).getArg(2);
																			var20 = link;
																			link = ((Atom)var17).getArg(1);
																			var21 = link.getAtom();
																			func = ((Atom)var21).getFunctor();
																			if (!(func.getArity() != 1)) {
																				if (!(!((Atom)var21).getFunctor().equals(((Atom)var23).getFunctor()))) {
																					if (execL1629(var0,var2,var14,var17,var11,var6,var1,var8,var21,var22,var23,nondeterministic)) {
																						ret = true;
																						break L1639;
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
											}
										}
									}
								}
							}
							((AbstractMembrane)var2).unlock();
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1637(Object var0, Object var1, boolean nondeterministic) {
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
L1637:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f27).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f29).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(1);
													var21 = link.getAtom();
													func = ((Atom)var21).getFunctor();
													if (!(func.getArity() != 1)) {
														mem = ((AbstractMembrane)var2);
														if (mem.lock()) {
															mem = ((AbstractMembrane)var2).getParent();
															if (!(mem == null)) {
																var3 = mem;
																if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																	func = f30;
																	Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																	while (it1.hasNext()) {
																		atom = (Atom) it1.next();
																		var18 = atom;
																		if (!(((AbstractMembrane)var2).hasRules())) {
																			link = ((Atom)var18).getArg(0);
																			var19 = link;
																			link = ((Atom)var18).getArg(1);
																			var20 = link;
																			link = ((Atom)var18).getArg(1);
																			var22 = link.getAtom();
																			link = ((Atom)var18).getArg(0);
																			var23 = link.getAtom();
																			if (!(!((Atom)var21).getFunctor().equals(((Atom)var23).getFunctor()))) {
																				func = ((Atom)var22).getFunctor();
																				if (!(func.getArity() != 1)) {
																					if (execL1629(var0,var2,var11,var14,var8,var1,var18,var5,var21,var22,var23,nondeterministic)) {
																						ret = true;
																						break L1637;
																					}
																				}
																			}
																		}
																	}
																}
															}
															((AbstractMembrane)var2).unlock();
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
				}
			}
		}
		return ret;
	}
	public boolean execL1634(Object var0, Object var1, boolean nondeterministic) {
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
L1634:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var20 = link.getAtom();
						func = ((Atom)var20).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f27).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(0);
								if (!(link.getPos() != 1)) {
									var8 = link.getAtom();
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
										link = ((Atom)var8).getArg(0);
										var9 = link;
										link = ((Atom)var8).getArg(1);
										var10 = link;
										link = ((Atom)var8).getArg(0);
										if (!(link.getPos() != 0)) {
											var11 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
												mem = ((Atom)var11).getMem();
												if (mem.lock()) {
													var12 = mem;
													link = ((Atom)var11).getArg(0);
													var13 = link;
													link = ((Atom)var11).getArg(1);
													var14 = link;
													link = ((Atom)var11).getArg(1);
													if (!(link.getPos() != 0)) {
														var15 = link.getAtom();
														if (!(!(f28).equals(((Atom)var15).getFunctor()))) {
															link = ((Atom)var15).getArg(0);
															var16 = link;
															func = f30;
															Iterator it1 = ((AbstractMembrane)var12).atomIteratorOfFunctor(func);
															while (it1.hasNext()) {
																atom = (Atom) it1.next();
																var17 = atom;
																if (!(((AbstractMembrane)var12).hasRules())) {
																	link = ((Atom)var17).getArg(0);
																	var18 = link;
																	link = ((Atom)var17).getArg(1);
																	var19 = link;
																	link = ((Atom)var17).getArg(1);
																	var21 = link.getAtom();
																	link = ((Atom)var17).getArg(0);
																	var22 = link.getAtom();
																	if (!(!((Atom)var20).getFunctor().equals(((Atom)var22).getFunctor()))) {
																		func = ((Atom)var21).getFunctor();
																		if (!(func.getArity() != 1)) {
																			if (execL1629(var0,var12,var5,var1,var8,var15,var17,var11,var20,var21,var22,nondeterministic)) {
																				ret = true;
																				break L1634;
																			}
																		}
																	}
																}
															}
														}
													}
													((AbstractMembrane)var12).unlock();
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
		}
		return ret;
	}
	public boolean execL1632(Object var0, Object var1, boolean nondeterministic) {
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
L1632:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 0)) {
							var7 = link.getAtom();
							if (!(!(f29).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								link = ((Atom)var7).getArg(2);
								var10 = link;
								link = ((Atom)var7).getArg(1);
								var20 = link.getAtom();
								func = ((Atom)var20).getFunctor();
								if (!(func.getArity() != 1)) {
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										link = ((Atom)var4).getArg(0);
										var5 = link;
										link = ((Atom)var4).getArg(1);
										var6 = link;
										link = ((Atom)var4).getArg(0);
										if (!(link.getPos() != 0)) {
											var11 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
												mem = ((Atom)var11).getMem();
												if (mem.lock()) {
													var12 = mem;
													link = ((Atom)var11).getArg(0);
													var13 = link;
													link = ((Atom)var11).getArg(1);
													var14 = link;
													link = ((Atom)var11).getArg(1);
													if (!(link.getPos() != 0)) {
														var15 = link.getAtom();
														if (!(!(f28).equals(((Atom)var15).getFunctor()))) {
															link = ((Atom)var15).getArg(0);
															var16 = link;
															func = f30;
															Iterator it1 = ((AbstractMembrane)var12).atomIteratorOfFunctor(func);
															while (it1.hasNext()) {
																atom = (Atom) it1.next();
																var17 = atom;
																if (!(((AbstractMembrane)var12).hasRules())) {
																	link = ((Atom)var17).getArg(0);
																	var18 = link;
																	link = ((Atom)var17).getArg(1);
																	var19 = link;
																	link = ((Atom)var17).getArg(1);
																	var21 = link.getAtom();
																	link = ((Atom)var17).getArg(0);
																	var22 = link.getAtom();
																	if (!(!((Atom)var20).getFunctor().equals(((Atom)var22).getFunctor()))) {
																		func = ((Atom)var21).getFunctor();
																		if (!(func.getArity() != 1)) {
																			if (execL1629(var0,var12,var1,var7,var4,var15,var17,var11,var20,var21,var22,nondeterministic)) {
																				ret = true;
																				break L1632;
																			}
																		}
																	}
																}
															}
														}
													}
													((AbstractMembrane)var12).unlock();
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
		}
		return ret;
	}
	public boolean execL1624(Object var0, boolean nondeterministic) {
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
L1624:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f10).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(1);
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
									link = ((Atom)var2).getArg(0);
									if (!(link.getPos() != 0)) {
										var4 = link.getAtom();
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
											mem = ((Atom)var4).getMem();
											if (mem.lock()) {
												var5 = mem;
												link = ((Atom)var4).getArg(1);
												if (!(link.getPos() != 0)) {
													var6 = link.getAtom();
													if (!(!(f28).equals(((Atom)var6).getFunctor()))) {
														func = f30;
														Iterator it2 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
														while (it2.hasNext()) {
															atom = (Atom) it2.next();
															var7 = atom;
															if (!(((AbstractMembrane)var5).hasRules())) {
																link = ((Atom)var7).getArg(1);
																var9 = link.getAtom();
																link = ((Atom)var7).getArg(0);
																var10 = link.getAtom();
																if (!(!((Atom)var8).getFunctor().equals(((Atom)var10).getFunctor()))) {
																	func = ((Atom)var9).getFunctor();
																	if (!(func.getArity() != 1)) {
																		if (execL1612(var0,var5,var1,var3,var2,var6,var7,var4,var8,var9,var10,nondeterministic)) {
																			ret = true;
																			break L1624;
																		}
																	}
																}
															}
														}
													}
												}
												((AbstractMembrane)var5).unlock();
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
	public boolean execL1612(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, boolean nondeterministic) {
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
L1612:
		{
			link = ((Atom)var3).getArg(3);
			var20 = link;
			link = ((Atom)var3).getArg(2);
			var21 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var12 = ((AbstractMembrane)var1).newAtom(((Atom)var10).getFunctor());
			var13 = ((AbstractMembrane)var1).newAtom(((Atom)var9).getFunctor());
			var14 = ((AbstractMembrane)var0).newAtom(((Atom)var9).getFunctor());
			func = Functor.INSIDE_PROXY;
			var17 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var17), 1 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var12), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var13), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var19), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var20 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 0,
				((Atom)var17), 0 );
			((Atom)var14).getMem().inheritLink(
				((Atom)var14), 0,
				(Link)var21 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1612;
		}
		return ret;
	}
	public boolean execL1613(Object var0, Object var1, boolean nondeterministic) {
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
L1613:
		{
			if (execL1615(var0, var1, nondeterministic)) {
				ret = true;
				break L1613;
			}
			if (execL1617(var0, var1, nondeterministic)) {
				ret = true;
				break L1613;
			}
			if (execL1620(var0, var1, nondeterministic)) {
				ret = true;
				break L1613;
			}
			if (execL1622(var0, var1, nondeterministic)) {
				ret = true;
				break L1613;
			}
		}
		return ret;
	}
	public boolean execL1622(Object var0, Object var1, boolean nondeterministic) {
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
L1622:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var23 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var24 = link.getAtom();
					func = ((Atom)var23).getFunctor();
					if (!(func.getArity() != 1)) {
						mem = ((AbstractMembrane)var2);
						if (mem.lock()) {
							mem = ((AbstractMembrane)var2).getParent();
							if (!(mem == null)) {
								var3 = mem;
								if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
									func = f28;
									Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var6 = atom;
										if (!(((AbstractMembrane)var2).hasRules())) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											link = ((Atom)var6).getArg(0);
											if (!(link.getPos() != 1)) {
												var8 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
													link = ((Atom)var8).getArg(0);
													var9 = link;
													link = ((Atom)var8).getArg(1);
													var10 = link;
													link = ((Atom)var8).getArg(0);
													if (!(link.getPos() != 0)) {
														var11 = link.getAtom();
														if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
															link = ((Atom)var11).getArg(0);
															var12 = link;
															link = ((Atom)var11).getArg(1);
															var13 = link;
															link = ((Atom)var11).getArg(1);
															if (!(link.getPos() != 0)) {
																var14 = link.getAtom();
																if (!(!(f27).equals(((Atom)var14).getFunctor()))) {
																	link = ((Atom)var14).getArg(0);
																	var15 = link;
																	link = ((Atom)var14).getArg(1);
																	var16 = link;
																	link = ((Atom)var14).getArg(1);
																	if (!(link.getPos() != 0)) {
																		var17 = link.getAtom();
																		if (!(!(f10).equals(((Atom)var17).getFunctor()))) {
																			link = ((Atom)var17).getArg(0);
																			var18 = link;
																			link = ((Atom)var17).getArg(1);
																			var19 = link;
																			link = ((Atom)var17).getArg(2);
																			var20 = link;
																			link = ((Atom)var17).getArg(3);
																			var21 = link;
																			link = ((Atom)var17).getArg(1);
																			var22 = link.getAtom();
																			func = ((Atom)var22).getFunctor();
																			if (!(func.getArity() != 1)) {
																				if (!(!((Atom)var22).getFunctor().equals(((Atom)var24).getFunctor()))) {
																					if (execL1612(var0,var2,var14,var17,var11,var6,var1,var8,var22,var23,var24,nondeterministic)) {
																						ret = true;
																						break L1622;
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
											}
										}
									}
								}
							}
							((AbstractMembrane)var2).unlock();
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1620(Object var0, Object var1, boolean nondeterministic) {
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
L1620:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f27).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f10).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(3);
													var18 = link;
													link = ((Atom)var14).getArg(1);
													var22 = link.getAtom();
													func = ((Atom)var22).getFunctor();
													if (!(func.getArity() != 1)) {
														mem = ((AbstractMembrane)var2);
														if (mem.lock()) {
															mem = ((AbstractMembrane)var2).getParent();
															if (!(mem == null)) {
																var3 = mem;
																if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																	func = f30;
																	Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																	while (it1.hasNext()) {
																		atom = (Atom) it1.next();
																		var19 = atom;
																		if (!(((AbstractMembrane)var2).hasRules())) {
																			link = ((Atom)var19).getArg(0);
																			var20 = link;
																			link = ((Atom)var19).getArg(1);
																			var21 = link;
																			link = ((Atom)var19).getArg(1);
																			var23 = link.getAtom();
																			link = ((Atom)var19).getArg(0);
																			var24 = link.getAtom();
																			if (!(!((Atom)var22).getFunctor().equals(((Atom)var24).getFunctor()))) {
																				func = ((Atom)var23).getFunctor();
																				if (!(func.getArity() != 1)) {
																					if (execL1612(var0,var2,var11,var14,var8,var1,var19,var5,var22,var23,var24,nondeterministic)) {
																						ret = true;
																						break L1620;
																					}
																				}
																			}
																		}
																	}
																}
															}
															((AbstractMembrane)var2).unlock();
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
				}
			}
		}
		return ret;
	}
	public boolean execL1617(Object var0, Object var1, boolean nondeterministic) {
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
L1617:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
						var21 = link.getAtom();
						func = ((Atom)var21).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f27).equals(((Atom)var6).getFunctor()))) {
								link = ((Atom)var6).getArg(0);
								var7 = link;
								link = ((Atom)var6).getArg(1);
								var8 = link;
								link = ((Atom)var6).getArg(0);
								if (!(link.getPos() != 1)) {
									var9 = link.getAtom();
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
										link = ((Atom)var9).getArg(0);
										var10 = link;
										link = ((Atom)var9).getArg(1);
										var11 = link;
										link = ((Atom)var9).getArg(0);
										if (!(link.getPos() != 0)) {
											var12 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
												mem = ((Atom)var12).getMem();
												if (mem.lock()) {
													var13 = mem;
													link = ((Atom)var12).getArg(0);
													var14 = link;
													link = ((Atom)var12).getArg(1);
													var15 = link;
													link = ((Atom)var12).getArg(1);
													if (!(link.getPos() != 0)) {
														var16 = link.getAtom();
														if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
															link = ((Atom)var16).getArg(0);
															var17 = link;
															func = f30;
															Iterator it1 = ((AbstractMembrane)var13).atomIteratorOfFunctor(func);
															while (it1.hasNext()) {
																atom = (Atom) it1.next();
																var18 = atom;
																if (!(((AbstractMembrane)var13).hasRules())) {
																	link = ((Atom)var18).getArg(0);
																	var19 = link;
																	link = ((Atom)var18).getArg(1);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var22 = link.getAtom();
																	link = ((Atom)var18).getArg(0);
																	var23 = link.getAtom();
																	if (!(!((Atom)var21).getFunctor().equals(((Atom)var23).getFunctor()))) {
																		func = ((Atom)var22).getFunctor();
																		if (!(func.getArity() != 1)) {
																			if (execL1612(var0,var13,var6,var1,var9,var16,var18,var12,var21,var22,var23,nondeterministic)) {
																				ret = true;
																				break L1617;
																			}
																		}
																	}
																}
															}
														}
													}
													((AbstractMembrane)var13).unlock();
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
		}
		return ret;
	}
	public boolean execL1615(Object var0, Object var1, boolean nondeterministic) {
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
L1615:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 0)) {
							var7 = link.getAtom();
							if (!(!(f10).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								link = ((Atom)var7).getArg(2);
								var10 = link;
								link = ((Atom)var7).getArg(3);
								var11 = link;
								link = ((Atom)var7).getArg(1);
								var21 = link.getAtom();
								func = ((Atom)var21).getFunctor();
								if (!(func.getArity() != 1)) {
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										link = ((Atom)var4).getArg(0);
										var5 = link;
										link = ((Atom)var4).getArg(1);
										var6 = link;
										link = ((Atom)var4).getArg(0);
										if (!(link.getPos() != 0)) {
											var12 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
												mem = ((Atom)var12).getMem();
												if (mem.lock()) {
													var13 = mem;
													link = ((Atom)var12).getArg(0);
													var14 = link;
													link = ((Atom)var12).getArg(1);
													var15 = link;
													link = ((Atom)var12).getArg(1);
													if (!(link.getPos() != 0)) {
														var16 = link.getAtom();
														if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
															link = ((Atom)var16).getArg(0);
															var17 = link;
															func = f30;
															Iterator it1 = ((AbstractMembrane)var13).atomIteratorOfFunctor(func);
															while (it1.hasNext()) {
																atom = (Atom) it1.next();
																var18 = atom;
																if (!(((AbstractMembrane)var13).hasRules())) {
																	link = ((Atom)var18).getArg(0);
																	var19 = link;
																	link = ((Atom)var18).getArg(1);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var22 = link.getAtom();
																	link = ((Atom)var18).getArg(0);
																	var23 = link.getAtom();
																	if (!(!((Atom)var21).getFunctor().equals(((Atom)var23).getFunctor()))) {
																		func = ((Atom)var22).getFunctor();
																		if (!(func.getArity() != 1)) {
																			if (execL1612(var0,var13,var1,var7,var4,var16,var18,var12,var21,var22,var23,nondeterministic)) {
																				ret = true;
																				break L1615;
																			}
																		}
																	}
																}
															}
														}
													}
													((AbstractMembrane)var13).unlock();
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
		}
		return ret;
	}
	public boolean execL1607(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1607:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(1);
							var7 = link.getAtom();
							link = ((Atom)var3).getArg(2);
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var7).getFunctor();
								if (!(func.getArity() != 1)) {
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
										link = ((Atom)var2).getArg(0);
										if (!(link.getPos() != 0)) {
											var4 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
												mem = ((Atom)var4).getMem();
												if (mem.lock()) {
													var5 = mem;
													if (!(((AbstractMembrane)var5).hasRules())) {
														link = ((Atom)var4).getArg(1);
														if (!(link.getPos() != 0)) {
															var6 = link.getAtom();
															if (!(!(f28).equals(((Atom)var6).getFunctor()))) {
																if (execL1597(var0,var5,var1,var3,var2,var6,var4,var7,var8,nondeterministic)) {
																	ret = true;
																	break L1607;
																}
															}
														}
													}
													((AbstractMembrane)var5).unlock();
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
		}
		return ret;
	}
	public boolean execL1597(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, boolean nondeterministic) {
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Object var16 = null;
		Object var17 = null;
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
L1597:
		{
			link = ((Atom)var3).getArg(3);
			var17 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var10 = ((AbstractMembrane)var1).newAtom(((Atom)var8).getFunctor());
			var11 = ((AbstractMembrane)var1).newAtom(((Atom)var7).getFunctor());
			func = f30;
			var13 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var14 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var14), 1 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 0,
				((Atom)var11), 0 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 1,
				((Atom)var10), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var16), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var17 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 0,
				((Atom)var14), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var13);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1597;
		}
		return ret;
	}
	public boolean execL1598(Object var0, Object var1, boolean nondeterministic) {
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
L1598:
		{
			if (execL1600(var0, var1, nondeterministic)) {
				ret = true;
				break L1598;
			}
			if (execL1602(var0, var1, nondeterministic)) {
				ret = true;
				break L1598;
			}
			if (execL1605(var0, var1, nondeterministic)) {
				ret = true;
				break L1598;
			}
		}
		return ret;
	}
	public boolean execL1605(Object var0, Object var1, boolean nondeterministic) {
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
L1605:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f27).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f5).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(3);
													var18 = link;
													link = ((Atom)var14).getArg(1);
													var19 = link.getAtom();
													link = ((Atom)var14).getArg(2);
													var20 = link.getAtom();
													func = ((Atom)var20).getFunctor();
													if (!(func.getArity() != 1)) {
														func = ((Atom)var19).getFunctor();
														if (!(func.getArity() != 1)) {
															mem = ((AbstractMembrane)var2);
															if (mem.lock()) {
																mem = ((AbstractMembrane)var2).getParent();
																if (!(mem == null)) {
																	var3 = mem;
																	if (!(((AbstractMembrane)var2).hasRules())) {
																		if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																			if (execL1597(var0,var2,var11,var14,var8,var1,var5,var19,var20,nondeterministic)) {
																				ret = true;
																				break L1605;
																			}
																		}
																	}
																}
																((AbstractMembrane)var2).unlock();
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
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1602(Object var0, Object var1, boolean nondeterministic) {
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
L1602:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
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
						var18 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var19 = link.getAtom();
						func = ((Atom)var19).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var18).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(f27).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(0);
									if (!(link.getPos() != 1)) {
										var9 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
											link = ((Atom)var9).getArg(0);
											var10 = link;
											link = ((Atom)var9).getArg(1);
											var11 = link;
											link = ((Atom)var9).getArg(0);
											if (!(link.getPos() != 0)) {
												var12 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
													mem = ((Atom)var12).getMem();
													if (mem.lock()) {
														var13 = mem;
														if (!(((AbstractMembrane)var13).hasRules())) {
															link = ((Atom)var12).getArg(0);
															var14 = link;
															link = ((Atom)var12).getArg(1);
															var15 = link;
															link = ((Atom)var12).getArg(1);
															if (!(link.getPos() != 0)) {
																var16 = link.getAtom();
																if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
																	link = ((Atom)var16).getArg(0);
																	var17 = link;
																	if (execL1597(var0,var13,var6,var1,var9,var16,var12,var18,var19,nondeterministic)) {
																		ret = true;
																		break L1602;
																	}
																}
															}
														}
														((AbstractMembrane)var13).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1600(Object var0, Object var1, boolean nondeterministic) {
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
L1600:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 0)) {
							var7 = link.getAtom();
							if (!(!(f5).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								link = ((Atom)var7).getArg(2);
								var10 = link;
								link = ((Atom)var7).getArg(3);
								var11 = link;
								link = ((Atom)var7).getArg(1);
								var18 = link.getAtom();
								link = ((Atom)var7).getArg(2);
								var19 = link.getAtom();
								func = ((Atom)var19).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var18).getFunctor();
									if (!(func.getArity() != 1)) {
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
											link = ((Atom)var4).getArg(0);
											var5 = link;
											link = ((Atom)var4).getArg(1);
											var6 = link;
											link = ((Atom)var4).getArg(0);
											if (!(link.getPos() != 0)) {
												var12 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
													mem = ((Atom)var12).getMem();
													if (mem.lock()) {
														var13 = mem;
														if (!(((AbstractMembrane)var13).hasRules())) {
															link = ((Atom)var12).getArg(0);
															var14 = link;
															link = ((Atom)var12).getArg(1);
															var15 = link;
															link = ((Atom)var12).getArg(1);
															if (!(link.getPos() != 0)) {
																var16 = link.getAtom();
																if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
																	link = ((Atom)var16).getArg(0);
																	var17 = link;
																	if (execL1597(var0,var13,var1,var7,var4,var16,var12,var18,var19,nondeterministic)) {
																		ret = true;
																		break L1600;
																	}
																}
															}
														}
														((AbstractMembrane)var13).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1592(Object var0, boolean nondeterministic) {
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
L1592:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(1);
							var8 = link.getAtom();
							link = ((Atom)var3).getArg(2);
							var9 = link.getAtom();
							func = ((Atom)var9).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var8).getFunctor();
								if (!(func.getArity() != 1)) {
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
										link = ((Atom)var2).getArg(0);
										if (!(link.getPos() != 0)) {
											var4 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
												mem = ((Atom)var4).getMem();
												if (mem.lock()) {
													var5 = mem;
													link = ((Atom)var4).getArg(1);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f28).equals(((Atom)var6).getFunctor()))) {
															func = f30;
															Iterator it2 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
															while (it2.hasNext()) {
																atom = (Atom) it2.next();
																var7 = atom;
																if (!(((AbstractMembrane)var5).hasRules())) {
																	link = ((Atom)var7).getArg(1);
																	var10 = link.getAtom();
																	link = ((Atom)var7).getArg(0);
																	var11 = link.getAtom();
																	if (!(!((Atom)var8).getFunctor().equals(((Atom)var11).getFunctor()))) {
																		func = ((Atom)var10).getFunctor();
																		if (!(func.getArity() != 1)) {
																			if (execL1580(var0,var5,var1,var3,var2,var6,var7,var4,var8,var9,var10,var11,nondeterministic)) {
																				ret = true;
																				break L1592;
																			}
																		}
																	}
																}
															}
														}
													}
													((AbstractMembrane)var5).unlock();
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
		}
		return ret;
	}
	public boolean execL1580(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, boolean nondeterministic) {
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
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
L1580:
		{
			link = ((Atom)var3).getArg(3);
			var20 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var11);
			atom.dequeue();
			atom = ((Atom)var11);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var13 = ((AbstractMembrane)var1).newAtom(((Atom)var9).getFunctor());
			var14 = ((AbstractMembrane)var1).newAtom(((Atom)var8).getFunctor());
			func = Functor.INSIDE_PROXY;
			var17 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var17), 1 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var14), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var13), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var19), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var20 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 0,
				((Atom)var17), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1580;
		}
		return ret;
	}
	public boolean execL1581(Object var0, Object var1, boolean nondeterministic) {
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
L1581:
		{
			if (execL1583(var0, var1, nondeterministic)) {
				ret = true;
				break L1581;
			}
			if (execL1585(var0, var1, nondeterministic)) {
				ret = true;
				break L1581;
			}
			if (execL1588(var0, var1, nondeterministic)) {
				ret = true;
				break L1581;
			}
			if (execL1590(var0, var1, nondeterministic)) {
				ret = true;
				break L1581;
			}
		}
		return ret;
	}
	public boolean execL1590(Object var0, Object var1, boolean nondeterministic) {
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
L1590:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var24 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var25 = link.getAtom();
					func = ((Atom)var24).getFunctor();
					if (!(func.getArity() != 1)) {
						mem = ((AbstractMembrane)var2);
						if (mem.lock()) {
							mem = ((AbstractMembrane)var2).getParent();
							if (!(mem == null)) {
								var3 = mem;
								if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
									func = f28;
									Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var6 = atom;
										if (!(((AbstractMembrane)var2).hasRules())) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											link = ((Atom)var6).getArg(0);
											if (!(link.getPos() != 1)) {
												var8 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
													link = ((Atom)var8).getArg(0);
													var9 = link;
													link = ((Atom)var8).getArg(1);
													var10 = link;
													link = ((Atom)var8).getArg(0);
													if (!(link.getPos() != 0)) {
														var11 = link.getAtom();
														if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
															link = ((Atom)var11).getArg(0);
															var12 = link;
															link = ((Atom)var11).getArg(1);
															var13 = link;
															link = ((Atom)var11).getArg(1);
															if (!(link.getPos() != 0)) {
																var14 = link.getAtom();
																if (!(!(f27).equals(((Atom)var14).getFunctor()))) {
																	link = ((Atom)var14).getArg(0);
																	var15 = link;
																	link = ((Atom)var14).getArg(1);
																	var16 = link;
																	link = ((Atom)var14).getArg(1);
																	if (!(link.getPos() != 0)) {
																		var17 = link.getAtom();
																		if (!(!(f5).equals(((Atom)var17).getFunctor()))) {
																			link = ((Atom)var17).getArg(0);
																			var18 = link;
																			link = ((Atom)var17).getArg(1);
																			var19 = link;
																			link = ((Atom)var17).getArg(2);
																			var20 = link;
																			link = ((Atom)var17).getArg(3);
																			var21 = link;
																			link = ((Atom)var17).getArg(1);
																			var22 = link.getAtom();
																			link = ((Atom)var17).getArg(2);
																			var23 = link.getAtom();
																			func = ((Atom)var23).getFunctor();
																			if (!(func.getArity() != 1)) {
																				func = ((Atom)var22).getFunctor();
																				if (!(func.getArity() != 1)) {
																					if (!(!((Atom)var22).getFunctor().equals(((Atom)var25).getFunctor()))) {
																						if (execL1580(var0,var2,var14,var17,var11,var6,var1,var8,var22,var23,var24,var25,nondeterministic)) {
																							ret = true;
																							break L1590;
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
												}
											}
										}
									}
								}
							}
							((AbstractMembrane)var2).unlock();
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1588(Object var0, Object var1, boolean nondeterministic) {
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
L1588:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f27).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f5).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(3);
													var18 = link;
													link = ((Atom)var14).getArg(1);
													var22 = link.getAtom();
													link = ((Atom)var14).getArg(2);
													var23 = link.getAtom();
													func = ((Atom)var23).getFunctor();
													if (!(func.getArity() != 1)) {
														func = ((Atom)var22).getFunctor();
														if (!(func.getArity() != 1)) {
															mem = ((AbstractMembrane)var2);
															if (mem.lock()) {
																mem = ((AbstractMembrane)var2).getParent();
																if (!(mem == null)) {
																	var3 = mem;
																	if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																		func = f30;
																		Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																		while (it1.hasNext()) {
																			atom = (Atom) it1.next();
																			var19 = atom;
																			if (!(((AbstractMembrane)var2).hasRules())) {
																				link = ((Atom)var19).getArg(0);
																				var20 = link;
																				link = ((Atom)var19).getArg(1);
																				var21 = link;
																				link = ((Atom)var19).getArg(1);
																				var24 = link.getAtom();
																				link = ((Atom)var19).getArg(0);
																				var25 = link.getAtom();
																				if (!(!((Atom)var22).getFunctor().equals(((Atom)var25).getFunctor()))) {
																					func = ((Atom)var24).getFunctor();
																					if (!(func.getArity() != 1)) {
																						if (execL1580(var0,var2,var11,var14,var8,var1,var19,var5,var22,var23,var24,var25,nondeterministic)) {
																							ret = true;
																							break L1588;
																						}
																					}
																				}
																			}
																		}
																	}
																}
																((AbstractMembrane)var2).unlock();
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
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1585(Object var0, Object var1, boolean nondeterministic) {
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
L1585:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
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
						var21 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var22 = link.getAtom();
						func = ((Atom)var22).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var21).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(f27).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(0);
									if (!(link.getPos() != 1)) {
										var9 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
											link = ((Atom)var9).getArg(0);
											var10 = link;
											link = ((Atom)var9).getArg(1);
											var11 = link;
											link = ((Atom)var9).getArg(0);
											if (!(link.getPos() != 0)) {
												var12 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
													mem = ((Atom)var12).getMem();
													if (mem.lock()) {
														var13 = mem;
														link = ((Atom)var12).getArg(0);
														var14 = link;
														link = ((Atom)var12).getArg(1);
														var15 = link;
														link = ((Atom)var12).getArg(1);
														if (!(link.getPos() != 0)) {
															var16 = link.getAtom();
															if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
																link = ((Atom)var16).getArg(0);
																var17 = link;
																func = f30;
																Iterator it1 = ((AbstractMembrane)var13).atomIteratorOfFunctor(func);
																while (it1.hasNext()) {
																	atom = (Atom) it1.next();
																	var18 = atom;
																	if (!(((AbstractMembrane)var13).hasRules())) {
																		link = ((Atom)var18).getArg(0);
																		var19 = link;
																		link = ((Atom)var18).getArg(1);
																		var20 = link;
																		link = ((Atom)var18).getArg(1);
																		var23 = link.getAtom();
																		link = ((Atom)var18).getArg(0);
																		var24 = link.getAtom();
																		if (!(!((Atom)var21).getFunctor().equals(((Atom)var24).getFunctor()))) {
																			func = ((Atom)var23).getFunctor();
																			if (!(func.getArity() != 1)) {
																				if (execL1580(var0,var13,var6,var1,var9,var16,var18,var12,var21,var22,var23,var24,nondeterministic)) {
																					ret = true;
																					break L1585;
																				}
																			}
																		}
																	}
																}
															}
														}
														((AbstractMembrane)var13).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1583(Object var0, Object var1, boolean nondeterministic) {
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
L1583:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 0)) {
							var7 = link.getAtom();
							if (!(!(f5).equals(((Atom)var7).getFunctor()))) {
								link = ((Atom)var7).getArg(0);
								var8 = link;
								link = ((Atom)var7).getArg(1);
								var9 = link;
								link = ((Atom)var7).getArg(2);
								var10 = link;
								link = ((Atom)var7).getArg(3);
								var11 = link;
								link = ((Atom)var7).getArg(1);
								var21 = link.getAtom();
								link = ((Atom)var7).getArg(2);
								var22 = link.getAtom();
								func = ((Atom)var22).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var21).getFunctor();
									if (!(func.getArity() != 1)) {
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
											link = ((Atom)var4).getArg(0);
											var5 = link;
											link = ((Atom)var4).getArg(1);
											var6 = link;
											link = ((Atom)var4).getArg(0);
											if (!(link.getPos() != 0)) {
												var12 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
													mem = ((Atom)var12).getMem();
													if (mem.lock()) {
														var13 = mem;
														link = ((Atom)var12).getArg(0);
														var14 = link;
														link = ((Atom)var12).getArg(1);
														var15 = link;
														link = ((Atom)var12).getArg(1);
														if (!(link.getPos() != 0)) {
															var16 = link.getAtom();
															if (!(!(f28).equals(((Atom)var16).getFunctor()))) {
																link = ((Atom)var16).getArg(0);
																var17 = link;
																func = f30;
																Iterator it1 = ((AbstractMembrane)var13).atomIteratorOfFunctor(func);
																while (it1.hasNext()) {
																	atom = (Atom) it1.next();
																	var18 = atom;
																	if (!(((AbstractMembrane)var13).hasRules())) {
																		link = ((Atom)var18).getArg(0);
																		var19 = link;
																		link = ((Atom)var18).getArg(1);
																		var20 = link;
																		link = ((Atom)var18).getArg(1);
																		var23 = link.getAtom();
																		link = ((Atom)var18).getArg(0);
																		var24 = link.getAtom();
																		if (!(!((Atom)var21).getFunctor().equals(((Atom)var24).getFunctor()))) {
																			func = ((Atom)var23).getFunctor();
																			if (!(func.getArity() != 1)) {
																				if (execL1580(var0,var13,var1,var7,var4,var16,var18,var12,var21,var22,var23,var24,nondeterministic)) {
																					ret = true;
																					break L1583;
																				}
																			}
																		}
																	}
																}
															}
														}
														((AbstractMembrane)var13).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1575(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1575:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1571(var0,var1,nondeterministic)) {
					ret = true;
					break L1575;
				}
			}
		}
		return ret;
	}
	public boolean execL1571(Object var0, Object var1, boolean nondeterministic) {
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
L1571:
		{
			link = ((Atom)var1).getArg(0);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var0).newMem(0);
			var2 = mem;
			func = f28;
			var3 = ((AbstractMembrane)var2).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var4 = ((AbstractMembrane)var2).newAtom(func);
			func = f27;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 1 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var7 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_map");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module map");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module map");
				}
			ret = true;
			break L1571;
		}
		return ret;
	}
	public boolean execL1572(Object var0, Object var1, boolean nondeterministic) {
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
L1572:
		{
			if (execL1574(var0, var1, nondeterministic)) {
				ret = true;
				break L1572;
			}
		}
		return ret;
	}
	public boolean execL1574(Object var0, Object var1, boolean nondeterministic) {
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
L1574:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL1571(var0,var1,nondeterministic)) {
						ret = true;
						break L1574;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f25 = new Functor("is_empty", 3, null);
	private static final Functor f24 = new Functor("nil", 1, null);
	private static final Functor f30 = new Functor(".", 2, null);
	private static final Functor f2 = new Functor("new", 1, "map");
	private static final Functor f28 = new Functor("obj", 1, null);
	private static final Functor f0 = new Functor("test", 0, "map");
	private static final Functor f11 = new Functor("t1", 1, null);
	private static final Functor f20 = new Functor("of_queue_s0", 4, "map");
	private static final Functor f27 = new Functor("new", 2, "map");
	private static final Functor f3 = new Functor("jack", 1, null);
	private static final Functor f8 = new Functor("bauer", 1, null);
	private static final Functor f9 = new Functor("jack_is", 1, null);
	private static final Functor f16 = new Functor("k1", 1, null);
	private static final Functor f23 = new Functor("true", 1, null);
	private static final Functor f5 = new Functor("put", 4, null);
	private static final Functor f10 = new Functor("get", 4, null);
	private static final Functor f26 = new Functor("new", 3, "queue");
	private static final Functor f1 = new Functor("t0", 1, null);
	private static final Functor f13 = new Functor("k0", 1, null);
	private static final Functor f19 = new Functor("false", 1, null);
	private static final Functor f21 = new Functor("shift", 3, null);
	private static final Functor f22 = new Functor("of_queue_s0", 3, "map");
	private static final Functor f6 = new Functor("nina", 1, null);
	private static final Functor f14 = new Functor("push", 3, null);
	private static final Functor f15 = new Functor("v0", 1, null);
	private static final Functor f12 = new Functor("new", 1, "queue");
	private static final Functor f4 = new Functor("enigma", 1, null);
	private static final Functor f17 = new Functor("v1", 1, null);
	private static final Functor f18 = new Functor("of_queue", 2, "map");
	private static final Functor f29 = new Functor("get", 3, null);
	private static final Functor f7 = new Functor("myers", 1, null);
}

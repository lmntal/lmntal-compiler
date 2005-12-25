package translated.module_java;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset609 extends Ruleset {
	private static final Ruleset609 theInstance = new Ruleset609();
	private Ruleset609() {}
	public static Ruleset609 getInstance() {
		return theInstance;
	}
	private int id = 609;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":java" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@java" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL725(mem, atom, false)) {
			return true;
		}
		if (execL732(mem, atom, false)) {
			return true;
		}
		if (execL739(mem, atom, false)) {
			return true;
		}
		if (execL746(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL728(mem, nondeterministic)) {
			return true;
		}
		if (execL735(mem, nondeterministic)) {
			return true;
		}
		if (execL742(mem, nondeterministic)) {
			return true;
		}
		if (execL749(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL749(Object var0, boolean nondeterministic) {
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
L749:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					func = ((Atom)var2).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL745(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L749;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL745(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L745:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			link = ((Atom)var1).getArg(3);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var5), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 3,
				(Link)var8 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodejava.run((Atom)var6, 1);
			ret = true;
			break L745;
		}
		return ret;
	}
	public boolean execL746(Object var0, Object var1, boolean nondeterministic) {
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
L746:
		{
			if (execL748(var0, var1, nondeterministic)) {
				ret = true;
				break L746;
			}
		}
		return ret;
	}
	public boolean execL748(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					func = ((Atom)var7).getFunctor();
					if (!(func.getArity() != 1)) {
						func = ((Atom)var6).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL745(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L748;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL742(Object var0, boolean nondeterministic) {
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
L742:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL738(var0,var1,nondeterministic)) {
					ret = true;
					break L742;
				}
			}
		}
		return ret;
	}
	public boolean execL738(Object var0, Object var1, boolean nondeterministic) {
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
L738:
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
			func = f3;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 2 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var6 );
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
			ret = true;
			break L738;
		}
		return ret;
	}
	public boolean execL739(Object var0, Object var1, boolean nondeterministic) {
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
L739:
		{
			if (execL741(var0, var1, nondeterministic)) {
				ret = true;
				break L739;
			}
		}
		return ret;
	}
	public boolean execL741(Object var0, Object var1, boolean nondeterministic) {
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
L741:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL738(var0,var1,nondeterministic)) {
						ret = true;
						break L741;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL735(Object var0, boolean nondeterministic) {
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
L735:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL731(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L735;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL731(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L731:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodejava.run((Atom)var4, 0);
			ret = true;
			break L731;
		}
		return ret;
	}
	public boolean execL732(Object var0, Object var1, boolean nondeterministic) {
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
L732:
		{
			if (execL734(var0, var1, nondeterministic)) {
				ret = true;
				break L732;
			}
		}
		return ret;
	}
	public boolean execL734(Object var0, Object var1, boolean nondeterministic) {
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
L734:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL731(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L734;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL728(Object var0, boolean nondeterministic) {
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
L728:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL724(var0,var1,nondeterministic)) {
					ret = true;
					break L728;
				}
			}
		}
		return ret;
	}
	public boolean execL724(Object var0, Object var1, boolean nondeterministic) {
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
L724:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f3;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 1 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 2,
				(Link)var5 );
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
			ret = true;
			break L724;
		}
		return ret;
	}
	public boolean execL725(Object var0, Object var1, boolean nondeterministic) {
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
L725:
		{
			if (execL727(var0, var1, nondeterministic)) {
				ret = true;
				break L725;
			}
		}
		return ret;
	}
	public boolean execL727(Object var0, Object var1, boolean nondeterministic) {
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
L727:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL724(var0,var1,nondeterministic)) {
						ret = true;
						break L727;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f3 = new Functor("[]", 1, null);
	private static final Functor f0 = new Functor("invoke", 4, "java");
	private static final Functor f6 = new Functor("new", 2, "java");
	private static final Functor f5 = new Functor("/*inline*/\r\n//\tSystem.out.println(me.nth(0));\r\n\tObject obj = Java.doNew(me.nth(0), util.Util.arrayOfList(me.getArg(1)));\r\n//\tSystem.out.println(obj);\r\n\tAtom n = mem.newAtom(new ObjectFunctor(obj));\r\n\tAtom nil = mem.newAtom(new Functor(\"nil\", 1));\r\n\tmem.relink(nil, 0, me, 1);\r\n\tmem.relink(n, 0, me, 2);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t", 3, null);
	private static final Functor f2 = new Functor("invoke", 3, "java");
	private static final Functor f4 = new Functor("new", 3, "java");
	private static final Functor f1 = new Functor("/*inline*/\r\n//\t\tSystem.out.println(me.nth(0));\r\n\tObject obj = me.nthAtom(0).getFunctor().getValue();\r\n\tObject res = Java.doInvoke(obj, me.nth(1), util.Util.arrayOfList(me.getArg(2)));\r\n\tif(res==null) res = \"nil\";\r\n\tAtom n = mem.newAtom(new ObjectFunctor(res));\r\n\tAtom nil = mem.newAtom(new Functor(\"nil\", 1));\r\n\tmem.relink(nil, 0, me, 2);\r\n\tmem.relink(n, 0, me, 3);\r\n\tme.nthAtom(0).remove();\r\n\tme.nthAtom(1).remove();\r\n\tme.remove();\r\n\t", 4, null);
}

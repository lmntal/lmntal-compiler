package translated.module_java;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset615 extends Ruleset {
	private static final Ruleset615 theInstance = new Ruleset615();
	private Ruleset615() {}
	public static Ruleset615 getInstance() {
		return theInstance;
	}
	private int id = 615;
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
	private String encodedRuleset = 
"('='(H, java.new(Class)) :- '='(H, java.new(Class, '[]'))), ('='(H, java.new(Class, List)) :- unary(Class) | '='(H, [:/*inline*///\tSystem.out.println(me.nth(0));\tObject obj = Java.doNew(me.nth(0), util.Util.arrayOfList(me.getArg(1)));//\tSystem.out.println(obj);\tAtom n = mem.newAtom(new ObjectFunctor(obj));\tAtom nil = mem.newAtom(new Functor(\"nil\", 1));\tmem.relink(nil, 0, me, 1);\tmem.relink(n, 0, me, 2);\tme.nthAtom(0).remove();\tme.remove();\t:](Class, List))), ('='(H, java.invoke(Object, Method)) :- '='(H, java.invoke(Object, Method, '[]'))), ('='(H, java.invoke(Object, Method, List)) :- unary(Method), unary(Object) | '='(H, [:/*inline*///\t\tSystem.out.println(me.nth(0));\tObject obj = me.nthAtom(0).getFunctor().getValue();\tObject res = Java.doInvoke(obj, me.nth(1), util.Util.arrayOfList(me.getArg(2)));\tif(res==null) res = \"nil\";\tAtom n = mem.newAtom(new ObjectFunctor(res));\tAtom nil = mem.newAtom(new Functor(\"nil\", 1));\tmem.relink(nil, 0, me, 2);\tmem.relink(n, 0, me, 3);\tme.nthAtom(0).remove();\tme.nthAtom(1).remove();\tme.remove();\t:](Object, Method, List)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1182(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "new");
			return true;
		}
		if (execL1191(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "new");
			return true;
		}
		if (execL1200(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "invoke");
			return true;
		}
		if (execL1209(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "invoke");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1185(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "new");
			return true;
		}
		if (execL1194(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "new");
			return true;
		}
		if (execL1203(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "invoke");
			return true;
		}
		if (execL1212(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "invoke");
			return true;
		}
		return result;
	}
	public boolean execL1212(Object var0, boolean nondeterministic) {
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
L1212:
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
						if (execL1208(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1212;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1208(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1208:
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
			break L1208;
		}
		return ret;
	}
	public boolean execL1209(Object var0, Object var1, boolean nondeterministic) {
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
L1209:
		{
			if (execL1211(var0, var1, nondeterministic)) {
				ret = true;
				break L1209;
			}
		}
		return ret;
	}
	public boolean execL1211(Object var0, Object var1, boolean nondeterministic) {
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
L1211:
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
							if (execL1208(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L1211;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1203(Object var0, boolean nondeterministic) {
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
L1203:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1199(var0,var1,nondeterministic)) {
					ret = true;
					break L1203;
				}
			}
		}
		return ret;
	}
	public boolean execL1199(Object var0, Object var1, boolean nondeterministic) {
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
L1199:
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
			break L1199;
		}
		return ret;
	}
	public boolean execL1200(Object var0, Object var1, boolean nondeterministic) {
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
L1200:
		{
			if (execL1202(var0, var1, nondeterministic)) {
				ret = true;
				break L1200;
			}
		}
		return ret;
	}
	public boolean execL1202(Object var0, Object var1, boolean nondeterministic) {
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
L1202:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL1199(var0,var1,nondeterministic)) {
						ret = true;
						break L1202;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1194(Object var0, boolean nondeterministic) {
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
L1194:
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
					if (execL1190(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1194;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1190(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1190:
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
			break L1190;
		}
		return ret;
	}
	public boolean execL1191(Object var0, Object var1, boolean nondeterministic) {
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
L1191:
		{
			if (execL1193(var0, var1, nondeterministic)) {
				ret = true;
				break L1191;
			}
		}
		return ret;
	}
	public boolean execL1193(Object var0, Object var1, boolean nondeterministic) {
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
L1193:
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
						if (execL1190(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1193;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1185(Object var0, boolean nondeterministic) {
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
L1185:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1181(var0,var1,nondeterministic)) {
					ret = true;
					break L1185;
				}
			}
		}
		return ret;
	}
	public boolean execL1181(Object var0, Object var1, boolean nondeterministic) {
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
L1181:
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
			break L1181;
		}
		return ret;
	}
	public boolean execL1182(Object var0, Object var1, boolean nondeterministic) {
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
L1182:
		{
			if (execL1184(var0, var1, nondeterministic)) {
				ret = true;
				break L1182;
			}
		}
		return ret;
	}
	public boolean execL1184(Object var0, Object var1, boolean nondeterministic) {
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
L1184:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL1181(var0,var1,nondeterministic)) {
						ret = true;
						break L1184;
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

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
		if (execL682(mem, atom)) {
			return true;
		}
		if (execL689(mem, atom)) {
			return true;
		}
		if (execL696(mem, atom)) {
			return true;
		}
		if (execL703(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL685(mem)) {
			return true;
		}
		if (execL692(mem)) {
			return true;
		}
		if (execL699(mem)) {
			return true;
		}
		if (execL706(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL706(Object var0) {
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
L706:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL701(var0,var1)) {
					ret = true;
					break L706;
				}
			}
		}
		return ret;
	}
	public boolean execL701(Object var0, Object var1) {
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
L701:
		{
			link = ((Atom)var1).getArg(1);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL702(var0,var1,var2,var3)) {
						ret = true;
						break L701;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL702(Object var0, Object var1, Object var2, Object var3) {
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
L702:
		{
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
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 3,
				((Atom)var1), 3 );
			SomeInlineCodejava.run((Atom)var6, 1);
			ret = true;
			break L702;
		}
		return ret;
	}
	public boolean execL703(Object var0, Object var1) {
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
L703:
		{
			if (execL705(var0, var1)) {
				ret = true;
				break L703;
			}
		}
		return ret;
	}
	public boolean execL705(Object var0, Object var1) {
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
L705:
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
					if (execL701(var0,var1)) {
						ret = true;
						break L705;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL699(Object var0) {
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
L699:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL694(var0,var1)) {
					ret = true;
					break L699;
				}
			}
		}
		return ret;
	}
	public boolean execL694(Object var0, Object var1) {
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
L694:
		{
			if (execL695(var0,var1)) {
				ret = true;
				break L694;
			}
		}
		return ret;
	}
	public boolean execL695(Object var0, Object var1) {
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
L695:
		{
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
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var1), 1 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 3,
				((Atom)var1), 2 );
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
			break L695;
		}
		return ret;
	}
	public boolean execL696(Object var0, Object var1) {
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
L696:
		{
			if (execL698(var0, var1)) {
				ret = true;
				break L696;
			}
		}
		return ret;
	}
	public boolean execL698(Object var0, Object var1) {
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
L698:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL694(var0,var1)) {
						ret = true;
						break L698;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL692(Object var0) {
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
L692:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL687(var0,var1)) {
					ret = true;
					break L692;
				}
			}
		}
		return ret;
	}
	public boolean execL687(Object var0, Object var1) {
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
L687:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL688(var0,var1,var2)) {
					ret = true;
					break L687;
				}
			}
		}
		return ret;
	}
	public boolean execL688(Object var0, Object var1, Object var2) {
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
L688:
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
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 2 );
			SomeInlineCodejava.run((Atom)var4, 0);
			ret = true;
			break L688;
		}
		return ret;
	}
	public boolean execL689(Object var0, Object var1) {
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
L689:
		{
			if (execL691(var0, var1)) {
				ret = true;
				break L689;
			}
		}
		return ret;
	}
	public boolean execL691(Object var0, Object var1) {
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
L691:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL687(var0,var1)) {
						ret = true;
						break L691;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL685(Object var0) {
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
L685:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL680(var0,var1)) {
					ret = true;
					break L685;
				}
			}
		}
		return ret;
	}
	public boolean execL680(Object var0, Object var1) {
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
L680:
		{
			if (execL681(var0,var1)) {
				ret = true;
				break L680;
			}
		}
		return ret;
	}
	public boolean execL681(Object var0, Object var1) {
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
L681:
		{
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
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 2,
				((Atom)var1), 1 );
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
			break L681;
		}
		return ret;
	}
	public boolean execL682(Object var0, Object var1) {
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
L682:
		{
			if (execL684(var0, var1)) {
				ret = true;
				break L682;
			}
		}
		return ret;
	}
	public boolean execL684(Object var0, Object var1) {
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
L684:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL680(var0,var1)) {
						ret = true;
						break L684;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f6 = new Functor("new", 2, "java");
	private static final Functor f3 = new Functor("[]", 1, null);
	private static final Functor f2 = new Functor("invoke", 3, "java");
	private static final Functor f5 = new Functor("/*inline*/\\r\\n//	System.out.println(me.nth(0));\\r\\n	Object obj = Java.doNew(me.nth(0), util.Util.arrayOfList(me.getArg(1)));\\r\\n//	System.out.println(obj);\\r\\n	Atom n = mem.newAtom(new ObjectFunctor(obj));\\r\\n	Atom nil = mem.newAtom(new Functor(\"nil\", 1));\\r\\n	mem.relink(nil, 0, me, 1);\\r\\n	mem.relink(n, 0, me, 2);\\r\\n	me.nthAtom(0).remove();\\r\\n	me.remove();\\r\\n	", 3, null);
	private static final Functor f0 = new Functor("invoke", 4, "java");
	private static final Functor f4 = new Functor("new", 3, "java");
	private static final Functor f1 = new Functor("/*inline*/\\r\\n//		System.out.println(me.nth(0));\\r\\n	Object obj = me.nthAtom(0).getFunctor().getValue();\\r\\n	Object res = Java.doInvoke(obj, me.nth(1), util.Util.arrayOfList(me.getArg(2)));\\r\\n	if(res==null) res = \"nil\";\\r\\n	Atom n = mem.newAtom(new ObjectFunctor(res));\\r\\n	Atom nil = mem.newAtom(new Functor(\"nil\", 1));\\r\\n	mem.relink(nil, 0, me, 2);\\r\\n	mem.relink(n, 0, me, 3);\\r\\n	me.nthAtom(0).remove();\\r\\n	me.nthAtom(1).remove();\\r\\n	me.remove();\\r\\n	", 4, null);
}

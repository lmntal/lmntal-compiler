package translated.module_io;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset607 extends Ruleset {
	private static final Ruleset607 theInstance = new Ruleset607();
	private Ruleset607() {}
	public static Ruleset607 getInstance() {
		return theInstance;
	}
	private int id = 607;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":io" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@io" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL547(mem, atom, false)) {
			return true;
		}
		if (execL554(mem, atom, false)) {
			return true;
		}
		if (execL561(mem, atom, false)) {
			return true;
		}
		if (execL568(mem, atom, false)) {
			return true;
		}
		if (execL575(mem, atom, false)) {
			return true;
		}
		if (execL582(mem, atom, false)) {
			return true;
		}
		if (execL589(mem, atom, false)) {
			return true;
		}
		if (execL596(mem, atom, false)) {
			return true;
		}
		if (execL603(mem, atom, false)) {
			return true;
		}
		if (execL610(mem, atom, false)) {
			return true;
		}
		if (execL619(mem, atom, false)) {
			return true;
		}
		if (execL626(mem, atom, false)) {
			return true;
		}
		if (execL635(mem, atom, false)) {
			return true;
		}
		if (execL642(mem, atom, false)) {
			return true;
		}
		if (execL649(mem, atom, false)) {
			return true;
		}
		if (execL658(mem, atom, false)) {
			return true;
		}
		if (execL665(mem, atom, false)) {
			return true;
		}
		if (execL672(mem, atom, false)) {
			return true;
		}
		if (execL681(mem, atom, false)) {
			return true;
		}
		if (execL688(mem, atom, false)) {
			return true;
		}
		if (execL696(mem, atom, false)) {
			return true;
		}
		if (execL704(mem, atom, false)) {
			return true;
		}
		if (execL711(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL550(mem, nondeterministic)) {
			return true;
		}
		if (execL557(mem, nondeterministic)) {
			return true;
		}
		if (execL564(mem, nondeterministic)) {
			return true;
		}
		if (execL571(mem, nondeterministic)) {
			return true;
		}
		if (execL578(mem, nondeterministic)) {
			return true;
		}
		if (execL585(mem, nondeterministic)) {
			return true;
		}
		if (execL592(mem, nondeterministic)) {
			return true;
		}
		if (execL599(mem, nondeterministic)) {
			return true;
		}
		if (execL606(mem, nondeterministic)) {
			return true;
		}
		if (execL615(mem, nondeterministic)) {
			return true;
		}
		if (execL622(mem, nondeterministic)) {
			return true;
		}
		if (execL631(mem, nondeterministic)) {
			return true;
		}
		if (execL638(mem, nondeterministic)) {
			return true;
		}
		if (execL645(mem, nondeterministic)) {
			return true;
		}
		if (execL654(mem, nondeterministic)) {
			return true;
		}
		if (execL661(mem, nondeterministic)) {
			return true;
		}
		if (execL668(mem, nondeterministic)) {
			return true;
		}
		if (execL677(mem, nondeterministic)) {
			return true;
		}
		if (execL684(mem, nondeterministic)) {
			return true;
		}
		if (execL692(mem, nondeterministic)) {
			return true;
		}
		if (execL700(mem, nondeterministic)) {
			return true;
		}
		if (execL707(mem, nondeterministic)) {
			return true;
		}
		if (execL716(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL716(Object var0, boolean nondeterministic) {
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
L716:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL710(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L716;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL710(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L710:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
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
				((Atom)var7), 1 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var9 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var2), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var2), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L710;
		}
		return ret;
	}
	public boolean execL711(Object var0, Object var1, boolean nondeterministic) {
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
L711:
		{
			if (execL713(var0, var1, nondeterministic)) {
				ret = true;
				break L711;
			}
			if (execL715(var0, var1, nondeterministic)) {
				ret = true;
				break L711;
			}
		}
		return ret;
	}
	public boolean execL715(Object var0, Object var1, boolean nondeterministic) {
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
L715:
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
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var8 = link.getAtom();
						func = ((Atom)var8).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
								link = ((Atom)var6).getArg(0);
								var7 = link;
								if (execL710(var0,var6,var1,var8,nondeterministic)) {
									ret = true;
									break L715;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL713(Object var0, Object var1, boolean nondeterministic) {
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
L713:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(0);
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL710(var0,var1,var3,var8,nondeterministic)) {
									ret = true;
									break L713;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL707(Object var0, boolean nondeterministic) {
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
L707:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL703(var0,var1,nondeterministic)) {
					ret = true;
					break L707;
				}
			}
		}
		return ret;
	}
	public boolean execL703(Object var0, Object var1, boolean nondeterministic) {
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
L703:
		{
			link = ((Atom)var1).getArg(0);
			var5 = link;
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var4), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 2 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 3,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L703;
		}
		return ret;
	}
	public boolean execL704(Object var0, Object var1, boolean nondeterministic) {
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
L704:
		{
			if (execL706(var0, var1, nondeterministic)) {
				ret = true;
				break L704;
			}
		}
		return ret;
	}
	public boolean execL706(Object var0, Object var1, boolean nondeterministic) {
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
L706:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL703(var0,var1,nondeterministic)) {
						ret = true;
						break L706;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL700(Object var0, boolean nondeterministic) {
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
L700:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL695(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L700;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL695(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L695:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(2);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f7;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var5 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L695;
		}
		return ret;
	}
	public boolean execL696(Object var0, Object var1, boolean nondeterministic) {
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
L696:
		{
			if (execL698(var0, var1, nondeterministic)) {
				ret = true;
				break L696;
			}
		}
		return ret;
	}
	public boolean execL698(Object var0, Object var1, boolean nondeterministic) {
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
L698:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL695(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L698;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL692(Object var0, boolean nondeterministic) {
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
L692:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL687(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L692;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL687(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L687:
		{
			link = ((Atom)var1).getArg(0);
			var7 = link;
			link = ((Atom)var2).getArg(1);
			var8 = link;
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
			func = f8;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var7 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var4), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var1), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var8 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L687;
		}
		return ret;
	}
	public boolean execL688(Object var0, Object var1, boolean nondeterministic) {
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
L688:
		{
			if (execL690(var0, var1, nondeterministic)) {
				ret = true;
				break L688;
			}
		}
		return ret;
	}
	public boolean execL690(Object var0, Object var1, boolean nondeterministic) {
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
L690:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							link = ((Atom)var5).getArg(0);
							var9 = link.getAtom();
							func = ((Atom)var9).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL687(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L690;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL684(Object var0, boolean nondeterministic) {
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
L684:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL680(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L684;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL680(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L680:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(2);
			var7 = link;
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
			func = f6;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 2,
				(Link)var7 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L680;
		}
		return ret;
	}
	public boolean execL681(Object var0, Object var1, boolean nondeterministic) {
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
L681:
		{
			if (execL683(var0, var1, nondeterministic)) {
				ret = true;
				break L681;
			}
		}
		return ret;
	}
	public boolean execL683(Object var0, Object var1, boolean nondeterministic) {
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
L683:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (execL680(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L683;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL677(Object var0, boolean nondeterministic) {
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
L677:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f12).equals(((Atom)var2).getFunctor()))) {
						if (execL671(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L677;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL671(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L671:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 2,
				((Atom)var2), 1 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L671;
		}
		return ret;
	}
	public boolean execL672(Object var0, Object var1, boolean nondeterministic) {
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
L672:
		{
			if (execL674(var0, var1, nondeterministic)) {
				ret = true;
				break L672;
			}
			if (execL676(var0, var1, nondeterministic)) {
				ret = true;
				break L672;
			}
		}
		return ret;
	}
	public boolean execL676(Object var0, Object var1, boolean nondeterministic) {
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
L676:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f11).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL671(var0,var5,var1,nondeterministic)) {
								ret = true;
								break L676;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL674(Object var0, Object var1, boolean nondeterministic) {
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
L674:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f12).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							if (execL671(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L674;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL668(Object var0, boolean nondeterministic) {
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
L668:
		{
			var2 = new Atom(null, f13);
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
					{
						Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
						String className = obj.getClass().getName();
						className = className.replaceAll("translated.module_io.", "");
						var4 = new Atom(null, new StringFunctor( className ));
					}
					if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL664(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L668;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL664(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L664:
		{
			link = ((Atom)var1).getArg(2);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f3;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var7), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var1), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var7), 1 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var9 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L664;
		}
		return ret;
	}
	public boolean execL665(Object var0, Object var1, boolean nondeterministic) {
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
L665:
		{
			if (execL667(var0, var1, nondeterministic)) {
				ret = true;
				break L665;
			}
		}
		return ret;
	}
	public boolean execL667(Object var0, Object var1, boolean nondeterministic) {
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
L667:
		{
			var5 = new Atom(null, f13);
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var7 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
							if (execL664(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L667;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL661(Object var0, boolean nondeterministic) {
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
L661:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL657(var0,var1,nondeterministic)) {
					ret = true;
					break L661;
				}
			}
		}
		return ret;
	}
	public boolean execL657(Object var0, Object var1, boolean nondeterministic) {
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
L657:
		{
			link = ((Atom)var1).getArg(0);
			var5 = link;
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f15;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var5 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var4), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 1 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L657;
		}
		return ret;
	}
	public boolean execL658(Object var0, Object var1, boolean nondeterministic) {
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
L658:
		{
			if (execL660(var0, var1, nondeterministic)) {
				ret = true;
				break L658;
			}
		}
		return ret;
	}
	public boolean execL660(Object var0, Object var1, boolean nondeterministic) {
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
L660:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL657(var0,var1,nondeterministic)) {
						ret = true;
						break L660;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL654(Object var0, boolean nondeterministic) {
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
L654:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f17;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var3 = link.getAtom();
					func = ((Atom)var3).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL648(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L654;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL648(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L648:
		{
			link = ((Atom)var1).getArg(0);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var9 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L648;
		}
		return ret;
	}
	public boolean execL649(Object var0, Object var1, boolean nondeterministic) {
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
L649:
		{
			if (execL651(var0, var1, nondeterministic)) {
				ret = true;
				break L649;
			}
			if (execL653(var0, var1, nondeterministic)) {
				ret = true;
				break L649;
			}
		}
		return ret;
	}
	public boolean execL653(Object var0, Object var1, boolean nondeterministic) {
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
L653:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						func = f16;
						Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it1.hasNext()) {
							atom = (Atom) it1.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (execL648(var0,var3,var1,var5,nondeterministic)) {
								ret = true;
								break L653;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL651(Object var0, Object var1, boolean nondeterministic) {
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
L651:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f17;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(0);
						var5 = link.getAtom();
						func = ((Atom)var5).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL648(var0,var1,var3,var5,nondeterministic)) {
								ret = true;
								break L651;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL645(Object var0, boolean nondeterministic) {
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
L645:
		{
			var2 = new Atom(null, f18);
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
					{
						Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
						String className = obj.getClass().getName();
						className = className.replaceAll("translated.module_io.", "");
						var4 = new Atom(null, new StringFunctor( className ));
					}
					if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL641(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L645;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL641(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L641:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f19;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var5, 12);
			ret = true;
			break L641;
		}
		return ret;
	}
	public boolean execL642(Object var0, Object var1, boolean nondeterministic) {
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
L642:
		{
			if (execL644(var0, var1, nondeterministic)) {
				ret = true;
				break L642;
			}
		}
		return ret;
	}
	public boolean execL644(Object var0, Object var1, boolean nondeterministic) {
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
L644:
		{
			var4 = new Atom(null, f18);
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var6 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
							if (execL641(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L644;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL638(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L638:
		{
			var2 = new Atom(null, f18);
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var5 = link.getAtom();
				func = ((Atom)var5).getFunctor();
				if (!(func.getArity() != 1)) {
					if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var4 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
							if (execL634(var0,var1,var2,var3,var5,nondeterministic)) {
								ret = true;
								break L638;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL634(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L634:
		{
			link = ((Atom)var1).getArg(2);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f20;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var6), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var8 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var7, 11);
			ret = true;
			break L634;
		}
		return ret;
	}
	public boolean execL635(Object var0, Object var1, boolean nondeterministic) {
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
L635:
		{
			if (execL637(var0, var1, nondeterministic)) {
				ret = true;
				break L635;
			}
		}
		return ret;
	}
	public boolean execL637(Object var0, Object var1, boolean nondeterministic) {
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
L637:
		{
			var5 = new Atom(null, f18);
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var8 = link.getAtom();
					func = ((Atom)var8).getFunctor();
					if (!(func.getArity() != 1)) {
						if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var7 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
								if (execL634(var0,var1,var5,var6,var8,nondeterministic)) {
									ret = true;
									break L637;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL631(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L631:
		{
			var3 = new Atom(null, f18);
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f22;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (execL625(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L631;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL625(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L625:
		{
			link = ((Atom)var1).getArg(0);
			var9 = link;
			link = ((Atom)var1).getArg(1);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 1,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var10 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L625;
		}
		return ret;
	}
	public boolean execL626(Object var0, Object var1, boolean nondeterministic) {
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
L626:
		{
			if (execL628(var0, var1, nondeterministic)) {
				ret = true;
				break L626;
			}
			if (execL630(var0, var1, nondeterministic)) {
				ret = true;
				break L626;
			}
		}
		return ret;
	}
	public boolean execL630(Object var0, Object var1, boolean nondeterministic) {
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
L630:
		{
			var6 = new Atom(null, f18);
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f21;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								if (execL625(var0,var3,var1,var6,var7,nondeterministic)) {
									ret = true;
									break L630;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL628(Object var0, Object var1, boolean nondeterministic) {
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
L628:
		{
			var6 = new Atom(null, f18);
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f22;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						link = ((Atom)var4).getArg(0);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var8 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
								if (execL625(var0,var1,var4,var6,var7,nondeterministic)) {
									ret = true;
									break L628;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL622(Object var0, boolean nondeterministic) {
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
L622:
		{
			var2 = new Atom(null, f13);
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
					{
						Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
						String className = obj.getClass().getName();
						className = className.replaceAll("translated.module_io.", "");
						var4 = new Atom(null, new StringFunctor( className ));
					}
					if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL618(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L622;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL618(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L618:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f23;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 2,
				(Link)var7 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var5, 10);
			ret = true;
			break L618;
		}
		return ret;
	}
	public boolean execL619(Object var0, Object var1, boolean nondeterministic) {
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
L619:
		{
			if (execL621(var0, var1, nondeterministic)) {
				ret = true;
				break L619;
			}
		}
		return ret;
	}
	public boolean execL621(Object var0, Object var1, boolean nondeterministic) {
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
L621:
		{
			var5 = new Atom(null, f13);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var7 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
							if (execL618(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L621;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL615(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L615:
		{
			var3 = new Atom(null, f13);
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f17;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (execL609(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L615;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL609(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L609:
		{
			link = ((Atom)var1).getArg(0);
			var9 = link;
			link = ((Atom)var1).getArg(1);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 1,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var10 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L609;
		}
		return ret;
	}
	public boolean execL610(Object var0, Object var1, boolean nondeterministic) {
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
L610:
		{
			if (execL612(var0, var1, nondeterministic)) {
				ret = true;
				break L610;
			}
			if (execL614(var0, var1, nondeterministic)) {
				ret = true;
				break L610;
			}
		}
		return ret;
	}
	public boolean execL614(Object var0, Object var1, boolean nondeterministic) {
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
L614:
		{
			var6 = new Atom(null, f13);
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f24;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								if (execL609(var0,var3,var1,var6,var7,nondeterministic)) {
									ret = true;
									break L614;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL612(Object var0, Object var1, boolean nondeterministic) {
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
L612:
		{
			var6 = new Atom(null, f13);
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f17;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						link = ((Atom)var4).getArg(0);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var8 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
								if (execL609(var0,var1,var4,var6,var7,nondeterministic)) {
									ret = true;
									break L612;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL606(Object var0, boolean nondeterministic) {
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
L606:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL602(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L606;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL602(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L602:
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
			func = f26;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 9);
			ret = true;
			break L602;
		}
		return ret;
	}
	public boolean execL603(Object var0, Object var1, boolean nondeterministic) {
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
L603:
		{
			if (execL605(var0, var1, nondeterministic)) {
				ret = true;
				break L603;
			}
		}
		return ret;
	}
	public boolean execL605(Object var0, Object var1, boolean nondeterministic) {
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
L605:
		{
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL602(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L605;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL599(Object var0, boolean nondeterministic) {
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
L599:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL595(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L599;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL595(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L595:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 8);
			ret = true;
			break L595;
		}
		return ret;
	}
	public boolean execL596(Object var0, Object var1, boolean nondeterministic) {
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
L596:
		{
			if (execL598(var0, var1, nondeterministic)) {
				ret = true;
				break L596;
			}
		}
		return ret;
	}
	public boolean execL598(Object var0, Object var1, boolean nondeterministic) {
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
L598:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL595(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L598;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL592(Object var0, boolean nondeterministic) {
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
L592:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL588(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L592;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL588(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L588:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 7);
			ret = true;
			break L588;
		}
		return ret;
	}
	public boolean execL589(Object var0, Object var1, boolean nondeterministic) {
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
L589:
		{
			if (execL591(var0, var1, nondeterministic)) {
				ret = true;
				break L589;
			}
		}
		return ret;
	}
	public boolean execL591(Object var0, Object var1, boolean nondeterministic) {
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
L591:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL588(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L591;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL585(Object var0, boolean nondeterministic) {
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
L585:
		{
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "L581",var0,var1,var2});
					} else if (execL581(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L585;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL581(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L581:
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
			func = f30;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			SomeInlineCodeio.run((Atom)var4, 6);
			ret = true;
			break L581;
		}
		return ret;
	}
	public boolean execL582(Object var0, Object var1, boolean nondeterministic) {
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
L582:
		{
			if (execL584(var0, var1, nondeterministic)) {
				ret = true;
				break L582;
			}
		}
		return ret;
	}
	public boolean execL584(Object var0, Object var1, boolean nondeterministic) {
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
L584:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "L581",var0,var1,var3});
						} else if (execL581(var0,var1,var3,nondeterministic)) {
							ret = true;
							break L584;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL578(Object var0, boolean nondeterministic) {
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
L578:
		{
			func = f31;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL574(var0,var1,nondeterministic)) {
					ret = true;
					break L578;
				}
			}
		}
		return ret;
	}
	public boolean execL574(Object var0, Object var1, boolean nondeterministic) {
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
L574:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f32;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var4 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 5);
			ret = true;
			break L574;
		}
		return ret;
	}
	public boolean execL575(Object var0, Object var1, boolean nondeterministic) {
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
L575:
		{
			if (execL577(var0, var1, nondeterministic)) {
				ret = true;
				break L575;
			}
		}
		return ret;
	}
	public boolean execL577(Object var0, Object var1, boolean nondeterministic) {
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
L577:
		{
			if (!(!(f31).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL574(var0,var1,nondeterministic)) {
						ret = true;
						break L577;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL571(Object var0, boolean nondeterministic) {
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
L571:
		{
			func = f33;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "L567",var0,var1});
				} else if (execL567(var0,var1,nondeterministic)) {
					ret = true;
					break L571;
				}
			}
		}
		return ret;
	}
	public boolean execL567(Object var0, Object var1, boolean nondeterministic) {
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
L567:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f34;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 4);
			ret = true;
			break L567;
		}
		return ret;
	}
	public boolean execL568(Object var0, Object var1, boolean nondeterministic) {
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
L568:
		{
			if (execL570(var0, var1, nondeterministic)) {
				ret = true;
				break L568;
			}
		}
		return ret;
	}
	public boolean execL570(Object var0, Object var1, boolean nondeterministic) {
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
L570:
		{
			if (!(!(f33).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "L567",var0,var1});
					} else if (execL567(var0,var1,nondeterministic)) {
						ret = true;
						break L570;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL564(Object var0, boolean nondeterministic) {
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
L564:
		{
			func = f35;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL560(var0,var1,nondeterministic)) {
					ret = true;
					break L564;
				}
			}
		}
		return ret;
	}
	public boolean execL560(Object var0, Object var1, boolean nondeterministic) {
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
L560:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f36;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var4 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 3);
			ret = true;
			break L560;
		}
		return ret;
	}
	public boolean execL561(Object var0, Object var1, boolean nondeterministic) {
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
L561:
		{
			if (execL563(var0, var1, nondeterministic)) {
				ret = true;
				break L561;
			}
		}
		return ret;
	}
	public boolean execL563(Object var0, Object var1, boolean nondeterministic) {
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
L563:
		{
			if (!(!(f35).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL560(var0,var1,nondeterministic)) {
						ret = true;
						break L563;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL557(Object var0, boolean nondeterministic) {
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
L557:
		{
			func = f37;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL553(var0,var1,nondeterministic)) {
					ret = true;
					break L557;
				}
			}
		}
		return ret;
	}
	public boolean execL553(Object var0, Object var1, boolean nondeterministic) {
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
L553:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f38;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodeio.run((Atom)var2, 2);
			ret = true;
			break L553;
		}
		return ret;
	}
	public boolean execL554(Object var0, Object var1, boolean nondeterministic) {
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
L554:
		{
			if (execL556(var0, var1, nondeterministic)) {
				ret = true;
				break L554;
			}
		}
		return ret;
	}
	public boolean execL556(Object var0, Object var1, boolean nondeterministic) {
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
L556:
		{
			if (!(!(f37).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL553(var0,var1,nondeterministic)) {
						ret = true;
						break L556;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL550(Object var0, boolean nondeterministic) {
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
L550:
		{
			func = f39;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "L546",var0,var1});
				} else if (execL546(var0,var1,nondeterministic)) {
					ret = true;
					break L550;
				}
			}
		}
		return ret;
	}
	public boolean execL546(Object var0, Object var1, boolean nondeterministic) {
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
L546:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f17;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f40;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f41;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var3, 0);
			SomeInlineCodeio.run((Atom)var5, 1);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L546;
		}
		return ret;
	}
	public boolean execL547(Object var0, Object var1, boolean nondeterministic) {
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
L547:
		{
			if (execL549(var0, var1, nondeterministic)) {
				ret = true;
				break L547;
			}
		}
		return ret;
	}
	public boolean execL549(Object var0, Object var1, boolean nondeterministic) {
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
L549:
		{
			if (!(!(f39).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "L546",var0,var1});
					} else if (execL546(var0,var1,nondeterministic)) {
						ret = true;
						break L549;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f22 = new Functor("stdout", 1, "io");
	private static final Functor f16 = new Functor("list_of_stdin", 1, "io");
	private static final Functor f23 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.BufferedReader br = (java.io.BufferedReader) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tString s = br.readLine();\r\n\t\t\tAtom result = mem.newAtom(new Functor(s==null?\"\":s, 1));\r\n\t\t\tmem.relink(result, 0, me, 1);\r\n\t\t\tAtom res = mem.newAtom(new Functor(s==null ? \"nil\" : \"done\", 1));\r\n\t\t\tmem.relink(res, 0, me, 2);\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {Env.e(e);}\r\n\t", 3, null);
	private static final Functor f11 = new Functor("nil", 1, null);
	private static final Functor f24 = new Functor("readline", 2, null);
	private static final Functor f30 = new StringFunctor("/*inline*/\r\n\tjavax.swing.JOptionPane.showMessageDialog(null, me.nth(0));\r\n\tmem.newAtom(new Functor(\"done\",0));\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t");
	private static final Functor f35 = new Functor("input", 2, "io");
	private static final Functor f18 = new StringFunctor("java.io.PrintWriter");
	private static final Functor f28 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom br = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.FileReader(me.nth(0)))));\r\n//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\r\n\t\tmem.relink(br, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {}\r\n\t", 2, null);
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f39 = new Functor("use", 0, "io");
	private static final Functor f5 = new Functor("[]", 1, null);
	private static final Functor f1 = new Functor("eager", 4, "io");
	private static final Functor f21 = new Functor("print", 2, null);
	private static final Functor f27 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom pw = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(new java.io.FileWriter(me.nth(0)))));\r\n//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\r\n\t\tmem.relink(pw, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {}\r\n\t", 2, null);
	private static final Functor f36 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tme.setName(\"done\");\r\n\tme.nthAtom(0).setName(s);\r\n\t", 2, null);
	private static final Functor f38 = new StringFunctor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tme.setName(s);\r\n\tme.nthAtom(0).setName(\"done\");\r\n\t");
	private static final Functor f12 = new Functor("list_of_dev_s0", 3, null);
	private static final Functor f6 = new Functor("toFile_s0", 3, null);
	private static final Functor f40 = new StringFunctor("/*inline*/\r\n\t\tAtom stdin = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.InputStreamReader(System.in))));\r\n\t\tmem.relink(stdin, 0, me, 0);\r\n\t\tme.remove();\r\n\t\t");
	private static final Functor f9 = new Functor("toFile", 3, "io");
	private static final Functor f17 = new Functor("stdin", 1, "io");
	private static final Functor f31 = new Functor("inputInteger", 2, "io");
	private static final Functor f29 = new Functor("popup", 1, "io");
	private static final Functor f3 = new Functor("readline", 3, null);
	private static final Functor f4 = new Functor("eager", 2, "io");
	private static final Functor f15 = new Functor("fileReader", 2, "io");
	private static final Functor f26 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tObject obj = ((ObjectFunctor)(me.nthAtom(0).getFunctor())).getValue();\r\n\t\tif(!(obj instanceof Process)) break;\r\n\t\tAtom r = mem.newAtom(new ObjectFunctor(\r\n\t\t  new java.io.BufferedReader( new java.io.InputStreamReader(\r\n\t\t    ((Process)obj).getInputStream()\r\n\t\t  ))\r\n\t\t));\r\n//\t\tSystem.out.println(r);\r\n\t\tmem.relink(r, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {e.printStackTrace();}\r\n\t", 2, null);
	private static final Functor f41 = new StringFunctor("/*inline*/\r\n\t\tAtom stdout = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(System.out, true)));\r\n\t\tmem.relink(stdout, 0, me, 0);\r\n\t\tme.remove();\r\n\t\t");
	private static final Functor f13 = new StringFunctor("java.io.BufferedReader");
	private static final Functor f20 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tAtom done = mem.newAtom(new Functor(\"done\", 1));\r\n\t\t\tif(pw!=null) {\r\n\t\t\t\tpw.println(me.nth(1));\r\n\t\t\t}\r\n\t\t\tmem.relink(done, 0, me, 2);\r\n\t\t\tme.nthAtom(1).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {e.printStackTrace();}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("print", 3, null);
	private static final Functor f33 = new Functor("input", 0, "io");
	private static final Functor f10 = new Functor("fileWriter", 2, "io");
	private static final Functor f0 = new Functor("done", 1, null);
	private static final Functor f32 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(Integer.parseInt(s)));\r\n\tmem.relink(atom,0,me,1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f25 = new Functor("reader", 2, "io");
	private static final Functor f14 = new Functor("list_of_file", 2, "io");
	private static final Functor f37 = new Functor("input", 1, "io");
	private static final Functor f34 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, \"Input text.\");\r\n\tme.setName(s);\r\n\t", 0, null);
	private static final Functor f7 = new Functor("close", 2, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tpw.close();\r\n\t\t\tmem.relink(me.nthAtom(0), 0, me, 1);\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {Env.e(e);}\r\n\t", 2, null);
}

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
		if (execL504(mem, atom)) {
			return true;
		}
		if (execL511(mem, atom)) {
			return true;
		}
		if (execL518(mem, atom)) {
			return true;
		}
		if (execL525(mem, atom)) {
			return true;
		}
		if (execL532(mem, atom)) {
			return true;
		}
		if (execL539(mem, atom)) {
			return true;
		}
		if (execL546(mem, atom)) {
			return true;
		}
		if (execL553(mem, atom)) {
			return true;
		}
		if (execL560(mem, atom)) {
			return true;
		}
		if (execL567(mem, atom)) {
			return true;
		}
		if (execL576(mem, atom)) {
			return true;
		}
		if (execL583(mem, atom)) {
			return true;
		}
		if (execL592(mem, atom)) {
			return true;
		}
		if (execL599(mem, atom)) {
			return true;
		}
		if (execL606(mem, atom)) {
			return true;
		}
		if (execL615(mem, atom)) {
			return true;
		}
		if (execL622(mem, atom)) {
			return true;
		}
		if (execL629(mem, atom)) {
			return true;
		}
		if (execL638(mem, atom)) {
			return true;
		}
		if (execL645(mem, atom)) {
			return true;
		}
		if (execL653(mem, atom)) {
			return true;
		}
		if (execL661(mem, atom)) {
			return true;
		}
		if (execL668(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL507(mem)) {
			return true;
		}
		if (execL514(mem)) {
			return true;
		}
		if (execL521(mem)) {
			return true;
		}
		if (execL528(mem)) {
			return true;
		}
		if (execL535(mem)) {
			return true;
		}
		if (execL542(mem)) {
			return true;
		}
		if (execL549(mem)) {
			return true;
		}
		if (execL556(mem)) {
			return true;
		}
		if (execL563(mem)) {
			return true;
		}
		if (execL572(mem)) {
			return true;
		}
		if (execL579(mem)) {
			return true;
		}
		if (execL588(mem)) {
			return true;
		}
		if (execL595(mem)) {
			return true;
		}
		if (execL602(mem)) {
			return true;
		}
		if (execL611(mem)) {
			return true;
		}
		if (execL618(mem)) {
			return true;
		}
		if (execL625(mem)) {
			return true;
		}
		if (execL634(mem)) {
			return true;
		}
		if (execL641(mem)) {
			return true;
		}
		if (execL649(mem)) {
			return true;
		}
		if (execL657(mem)) {
			return true;
		}
		if (execL664(mem)) {
			return true;
		}
		if (execL673(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL673(Object var0) {
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
L673:
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
						if (execL666(var0,var1,var2)) {
							ret = true;
							break L673;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL666(Object var0, Object var1, Object var2) {
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
L666:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL667(var0,var1,var2,var3)) {
					ret = true;
					break L666;
				}
			}
		}
		return ret;
	}
	public boolean execL667(Object var0, Object var1, Object var2, Object var3) {
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
L667:
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var2), 1 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var8), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 3,
				((Atom)var2), 3 );
			atom = ((Atom)var8);
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
			break L667;
		}
		return ret;
	}
	public boolean execL668(Object var0, Object var1) {
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
L668:
		{
			if (execL670(var0, var1)) {
				ret = true;
				break L668;
			}
			if (execL672(var0, var1)) {
				ret = true;
				break L668;
			}
		}
		return ret;
	}
	public boolean execL672(Object var0, Object var1) {
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
L672:
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
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							if (execL666(var0,var6,var1)) {
								ret = true;
								break L672;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL670(Object var0, Object var1) {
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
L670:
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
							if (execL666(var0,var1,var3)) {
								ret = true;
								break L670;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL664(Object var0) {
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
L664:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL659(var0,var1)) {
					ret = true;
					break L664;
				}
			}
		}
		return ret;
	}
	public boolean execL659(Object var0, Object var1) {
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
L659:
		{
			if (execL660(var0,var1)) {
				ret = true;
				break L659;
			}
		}
		return ret;
	}
	public boolean execL660(Object var0, Object var1) {
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
L660:
		{
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
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 3,
				((Atom)var1), 1 );
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
			break L660;
		}
		return ret;
	}
	public boolean execL661(Object var0, Object var1) {
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
L661:
		{
			if (execL663(var0, var1)) {
				ret = true;
				break L661;
			}
		}
		return ret;
	}
	public boolean execL663(Object var0, Object var1) {
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
L663:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL659(var0,var1)) {
						ret = true;
						break L663;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL657(Object var0) {
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
L657:
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
						if (execL651(var0,var1,var2)) {
							ret = true;
							break L657;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL651(Object var0, Object var1, Object var2) {
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
			if (execL652(var0,var1,var2)) {
				ret = true;
				break L651;
			}
		}
		return ret;
	}
	public boolean execL652(Object var0, Object var1, Object var2) {
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
L652:
		{
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
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var1), 2 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L652;
		}
		return ret;
	}
	public boolean execL653(Object var0, Object var1) {
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
L653:
		{
			if (execL655(var0, var1)) {
				ret = true;
				break L653;
			}
		}
		return ret;
	}
	public boolean execL655(Object var0, Object var1) {
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
L655:
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
							if (execL651(var0,var1,var5)) {
								ret = true;
								break L655;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL649(Object var0) {
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
L649:
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
						if (execL643(var0,var1,var2)) {
							ret = true;
							break L649;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL643(Object var0, Object var1, Object var2) {
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
L643:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL644(var0,var1,var2,var3)) {
					ret = true;
					break L643;
				}
			}
		}
		return ret;
	}
	public boolean execL644(Object var0, Object var1, Object var2, Object var3) {
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
			func = f8;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var1), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var4), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var6), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var2), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L644;
		}
		return ret;
	}
	public boolean execL645(Object var0, Object var1) {
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
L645:
		{
			if (execL647(var0, var1)) {
				ret = true;
				break L645;
			}
		}
		return ret;
	}
	public boolean execL647(Object var0, Object var1) {
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
L647:
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
							if (execL643(var0,var1,var5)) {
								ret = true;
								break L647;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL641(Object var0) {
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
L641:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL636(var0,var1)) {
					ret = true;
					break L641;
				}
			}
		}
		return ret;
	}
	public boolean execL636(Object var0, Object var1) {
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
L636:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL637(var0,var1,var2)) {
					ret = true;
					break L636;
				}
			}
		}
		return ret;
	}
	public boolean execL637(Object var0, Object var1, Object var2) {
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
L637:
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
			func = f6;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var1), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 2 );
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
			break L637;
		}
		return ret;
	}
	public boolean execL638(Object var0, Object var1) {
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
L638:
		{
			if (execL640(var0, var1)) {
				ret = true;
				break L638;
			}
		}
		return ret;
	}
	public boolean execL640(Object var0, Object var1) {
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
L640:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL636(var0,var1)) {
						ret = true;
						break L640;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL634(Object var0) {
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
L634:
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
						if (execL627(var0,var1,var2)) {
							ret = true;
							break L634;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL627(Object var0, Object var1, Object var2) {
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
L627:
		{
			if (execL628(var0,var1,var2)) {
				ret = true;
				break L627;
			}
		}
		return ret;
	}
	public boolean execL628(Object var0, Object var1, Object var2) {
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
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 2,
				((Atom)var2), 1 );
			ret = true;
			break L628;
		}
		return ret;
	}
	public boolean execL629(Object var0, Object var1) {
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
L629:
		{
			if (execL631(var0, var1)) {
				ret = true;
				break L629;
			}
			if (execL633(var0, var1)) {
				ret = true;
				break L629;
			}
		}
		return ret;
	}
	public boolean execL633(Object var0, Object var1) {
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
L633:
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
							if (execL627(var0,var5,var1)) {
								ret = true;
								break L633;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL631(Object var0, Object var1) {
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
L631:
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
							if (execL627(var0,var1,var3)) {
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
	public boolean execL625(Object var0) {
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
L625:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL620(var0,var1)) {
					ret = true;
					break L625;
				}
			}
		}
		return ret;
	}
	public boolean execL620(Object var0, Object var1) {
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
L620:
		{
			var2 = new Atom(null, f13);
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
					if (execL621(var0,var1,var2,var3)) {
						ret = true;
						break L620;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL621(Object var0, Object var1, Object var2, Object var3) {
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
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f3;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var6 = ((AbstractMembrane)var0).newAtom(func);
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
				((Atom)var6), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var1), 1 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var7), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L621;
		}
		return ret;
	}
	public boolean execL622(Object var0, Object var1) {
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
			if (execL624(var0, var1)) {
				ret = true;
				break L622;
			}
		}
		return ret;
	}
	public boolean execL624(Object var0, Object var1) {
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
L624:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL620(var0,var1)) {
						ret = true;
						break L624;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL618(Object var0) {
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
L618:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL613(var0,var1)) {
					ret = true;
					break L618;
				}
			}
		}
		return ret;
	}
	public boolean execL613(Object var0, Object var1) {
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
L613:
		{
			if (execL614(var0,var1)) {
				ret = true;
				break L613;
			}
		}
		return ret;
	}
	public boolean execL614(Object var0, Object var1) {
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
L614:
		{
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
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var4), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 1 );
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
			break L614;
		}
		return ret;
	}
	public boolean execL615(Object var0, Object var1) {
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
L615:
		{
			if (execL617(var0, var1)) {
				ret = true;
				break L615;
			}
		}
		return ret;
	}
	public boolean execL617(Object var0, Object var1) {
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
L617:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL613(var0,var1)) {
						ret = true;
						break L617;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL611(Object var0) {
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
L611:
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
					if (execL604(var0,var1,var2)) {
						ret = true;
						break L611;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL604(Object var0, Object var1, Object var2) {
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
L604:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL605(var0,var1,var2,var3)) {
					ret = true;
					break L604;
				}
			}
		}
		return ret;
	}
	public boolean execL605(Object var0, Object var1, Object var2, Object var3) {
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
L605:
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 2,
				((Atom)var1), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var8);
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
			break L605;
		}
		return ret;
	}
	public boolean execL606(Object var0, Object var1) {
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
L606:
		{
			if (execL608(var0, var1)) {
				ret = true;
				break L606;
			}
			if (execL610(var0, var1)) {
				ret = true;
				break L606;
			}
		}
		return ret;
	}
	public boolean execL610(Object var0, Object var1) {
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
L610:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f16;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						if (execL604(var0,var3,var1)) {
							ret = true;
							break L610;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL608(Object var0, Object var1) {
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
L608:
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
						if (execL604(var0,var1,var3)) {
							ret = true;
							break L608;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL602(Object var0) {
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
L602:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL597(var0,var1)) {
					ret = true;
					break L602;
				}
			}
		}
		return ret;
	}
	public boolean execL597(Object var0, Object var1) {
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
L597:
		{
			var2 = new Atom(null, f18);
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
					if (execL598(var0,var1,var2,var3)) {
						ret = true;
						break L597;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL598(Object var0, Object var1, Object var2, Object var3) {
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
L598:
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
			func = f19;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var5, 12);
			ret = true;
			break L598;
		}
		return ret;
	}
	public boolean execL599(Object var0, Object var1) {
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
L599:
		{
			if (execL601(var0, var1)) {
				ret = true;
				break L599;
			}
		}
		return ret;
	}
	public boolean execL601(Object var0, Object var1) {
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
L601:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL597(var0,var1)) {
						ret = true;
						break L601;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL595(Object var0) {
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
L595:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL590(var0,var1)) {
					ret = true;
					break L595;
				}
			}
		}
		return ret;
	}
	public boolean execL590(Object var0, Object var1) {
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
L590:
		{
			var2 = new Atom(null, f18);
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
					link = ((Atom)var1).getArg(1);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL591(var0,var1,var2,var3,var5)) {
							ret = true;
							break L590;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL591(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L591:
		{
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
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 2,
				((Atom)var1), 2 );
			SomeInlineCodeio.run((Atom)var7, 11);
			ret = true;
			break L591;
		}
		return ret;
	}
	public boolean execL592(Object var0, Object var1) {
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
L592:
		{
			if (execL594(var0, var1)) {
				ret = true;
				break L592;
			}
		}
		return ret;
	}
	public boolean execL594(Object var0, Object var1) {
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
L594:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL590(var0,var1)) {
						ret = true;
						break L594;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL588(Object var0) {
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
L588:
		{
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
					if (execL581(var0,var1,var2)) {
						ret = true;
						break L588;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL581(Object var0, Object var1, Object var2) {
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
L581:
		{
			var3 = new Atom(null, f18);
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
					if (execL582(var0,var1,var2,var3,var4)) {
						ret = true;
						break L581;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL582(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L582:
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f22;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var1), 0 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 2,
				((Atom)var1), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var8);
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
			break L582;
		}
		return ret;
	}
	public boolean execL583(Object var0, Object var1) {
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
L583:
		{
			if (execL585(var0, var1)) {
				ret = true;
				break L583;
			}
			if (execL587(var0, var1)) {
				ret = true;
				break L583;
			}
		}
		return ret;
	}
	public boolean execL587(Object var0, Object var1) {
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
L587:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f21;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(1);
						var5 = link;
						if (execL581(var0,var3,var1)) {
							ret = true;
							break L587;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL585(Object var0, Object var1) {
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
L585:
		{
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
						if (execL581(var0,var1,var4)) {
							ret = true;
							break L585;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL579(Object var0) {
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
L579:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL574(var0,var1)) {
					ret = true;
					break L579;
				}
			}
		}
		return ret;
	}
	public boolean execL574(Object var0, Object var1) {
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
			var2 = new Atom(null, f13);
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
					if (execL575(var0,var1,var2,var3)) {
						ret = true;
						break L574;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL575(Object var0, Object var1, Object var2, Object var3) {
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
L575:
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
			func = f23;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var1), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 2 );
			SomeInlineCodeio.run((Atom)var5, 10);
			ret = true;
			break L575;
		}
		return ret;
	}
	public boolean execL576(Object var0, Object var1) {
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
L576:
		{
			if (execL578(var0, var1)) {
				ret = true;
				break L576;
			}
		}
		return ret;
	}
	public boolean execL578(Object var0, Object var1) {
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
L578:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL574(var0,var1)) {
						ret = true;
						break L578;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL572(Object var0) {
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
L572:
		{
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
					if (execL565(var0,var1,var2)) {
						ret = true;
						break L572;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL565(Object var0, Object var1, Object var2) {
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
L565:
		{
			var3 = new Atom(null, f13);
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
					if (execL566(var0,var1,var2,var3,var4)) {
						ret = true;
						break L565;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL566(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L566:
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var1), 0 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 2,
				((Atom)var1), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var8);
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
			break L566;
		}
		return ret;
	}
	public boolean execL567(Object var0, Object var1) {
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
L567:
		{
			if (execL569(var0, var1)) {
				ret = true;
				break L567;
			}
			if (execL571(var0, var1)) {
				ret = true;
				break L567;
			}
		}
		return ret;
	}
	public boolean execL571(Object var0, Object var1) {
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
L571:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f24;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(1);
						var5 = link;
						if (execL565(var0,var3,var1)) {
							ret = true;
							break L571;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL569(Object var0, Object var1) {
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
L569:
		{
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
						if (execL565(var0,var1,var4)) {
							ret = true;
							break L569;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL563(Object var0) {
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
L563:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL558(var0,var1)) {
					ret = true;
					break L563;
				}
			}
		}
		return ret;
	}
	public boolean execL558(Object var0, Object var1) {
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
L558:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL559(var0,var1,var2)) {
					ret = true;
					break L558;
				}
			}
		}
		return ret;
	}
	public boolean execL559(Object var0, Object var1, Object var2) {
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
L559:
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
			func = f26;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var4, 9);
			ret = true;
			break L559;
		}
		return ret;
	}
	public boolean execL560(Object var0, Object var1) {
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
L560:
		{
			if (execL562(var0, var1)) {
				ret = true;
				break L560;
			}
		}
		return ret;
	}
	public boolean execL562(Object var0, Object var1) {
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
L562:
		{
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL558(var0,var1)) {
						ret = true;
						break L562;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL556(Object var0) {
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
L556:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL551(var0,var1)) {
					ret = true;
					break L556;
				}
			}
		}
		return ret;
	}
	public boolean execL551(Object var0, Object var1) {
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
L551:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL552(var0,var1,var2)) {
					ret = true;
					break L551;
				}
			}
		}
		return ret;
	}
	public boolean execL552(Object var0, Object var1, Object var2) {
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
L552:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var4, 8);
			ret = true;
			break L552;
		}
		return ret;
	}
	public boolean execL553(Object var0, Object var1) {
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
			if (execL555(var0, var1)) {
				ret = true;
				break L553;
			}
		}
		return ret;
	}
	public boolean execL555(Object var0, Object var1) {
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
L555:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL551(var0,var1)) {
						ret = true;
						break L555;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL549(Object var0) {
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
L549:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL544(var0,var1)) {
					ret = true;
					break L549;
				}
			}
		}
		return ret;
	}
	public boolean execL544(Object var0, Object var1) {
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
L544:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL545(var0,var1,var2)) {
					ret = true;
					break L544;
				}
			}
		}
		return ret;
	}
	public boolean execL545(Object var0, Object var1, Object var2) {
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
L545:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var4, 7);
			ret = true;
			break L545;
		}
		return ret;
	}
	public boolean execL546(Object var0, Object var1) {
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
L546:
		{
			if (execL548(var0, var1)) {
				ret = true;
				break L546;
			}
		}
		return ret;
	}
	public boolean execL548(Object var0, Object var1) {
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
L548:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL544(var0,var1)) {
						ret = true;
						break L548;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL542(Object var0) {
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
L542:
		{
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL537(var0,var1)) {
					ret = true;
					break L542;
				}
			}
		}
		return ret;
	}
	public boolean execL537(Object var0, Object var1) {
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
L537:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
			    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
				if (execL538(var0,var1,var2)) {
					ret = true;
					break L537;
				}
			}
		}
		return ret;
	}
	public boolean execL538(Object var0, Object var1, Object var2) {
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
L538:
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
			break L538;
		}
		return ret;
	}
	public boolean execL539(Object var0, Object var1) {
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
L539:
		{
			if (execL541(var0, var1)) {
				ret = true;
				break L539;
			}
		}
		return ret;
	}
	public boolean execL541(Object var0, Object var1) {
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
L541:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL537(var0,var1)) {
						ret = true;
						break L541;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL535(Object var0) {
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
L535:
		{
			func = f31;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL530(var0,var1)) {
					ret = true;
					break L535;
				}
			}
		}
		return ret;
	}
	public boolean execL530(Object var0, Object var1) {
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
L530:
		{
			if (execL531(var0,var1)) {
				ret = true;
				break L530;
			}
		}
		return ret;
	}
	public boolean execL531(Object var0, Object var1) {
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
L531:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f32;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var2, 5);
			ret = true;
			break L531;
		}
		return ret;
	}
	public boolean execL532(Object var0, Object var1) {
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
L532:
		{
			if (execL534(var0, var1)) {
				ret = true;
				break L532;
			}
		}
		return ret;
	}
	public boolean execL534(Object var0, Object var1) {
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
L534:
		{
			if (!(!(f31).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL530(var0,var1)) {
						ret = true;
						break L534;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL528(Object var0) {
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
L528:
		{
			func = f33;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL523(var0,var1)) {
					ret = true;
					break L528;
				}
			}
		}
		return ret;
	}
	public boolean execL523(Object var0, Object var1) {
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
L523:
		{
			if (execL524(var0,var1)) {
				ret = true;
				break L523;
			}
		}
		return ret;
	}
	public boolean execL524(Object var0, Object var1) {
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
L524:
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
			break L524;
		}
		return ret;
	}
	public boolean execL525(Object var0, Object var1) {
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
L525:
		{
			if (execL527(var0, var1)) {
				ret = true;
				break L525;
			}
		}
		return ret;
	}
	public boolean execL527(Object var0, Object var1) {
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
L527:
		{
			if (!(!(f33).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (execL523(var0,var1)) {
						ret = true;
						break L527;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL521(Object var0) {
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
L521:
		{
			func = f35;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL516(var0,var1)) {
					ret = true;
					break L521;
				}
			}
		}
		return ret;
	}
	public boolean execL516(Object var0, Object var1) {
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
L516:
		{
			if (execL517(var0,var1)) {
				ret = true;
				break L516;
			}
		}
		return ret;
	}
	public boolean execL517(Object var0, Object var1) {
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
L517:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f36;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 1,
				((Atom)var1), 1 );
			SomeInlineCodeio.run((Atom)var2, 3);
			ret = true;
			break L517;
		}
		return ret;
	}
	public boolean execL518(Object var0, Object var1) {
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
L518:
		{
			if (execL520(var0, var1)) {
				ret = true;
				break L518;
			}
		}
		return ret;
	}
	public boolean execL520(Object var0, Object var1) {
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
L520:
		{
			if (!(!(f35).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL516(var0,var1)) {
						ret = true;
						break L520;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL514(Object var0) {
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
L514:
		{
			func = f37;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL509(var0,var1)) {
					ret = true;
					break L514;
				}
			}
		}
		return ret;
	}
	public boolean execL509(Object var0, Object var1) {
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
L509:
		{
			if (execL510(var0,var1)) {
				ret = true;
				break L509;
			}
		}
		return ret;
	}
	public boolean execL510(Object var0, Object var1) {
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
L510:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f38;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			SomeInlineCodeio.run((Atom)var2, 2);
			ret = true;
			break L510;
		}
		return ret;
	}
	public boolean execL511(Object var0, Object var1) {
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
L511:
		{
			if (execL513(var0, var1)) {
				ret = true;
				break L511;
			}
		}
		return ret;
	}
	public boolean execL513(Object var0, Object var1) {
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
L513:
		{
			if (!(!(f37).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL509(var0,var1)) {
						ret = true;
						break L513;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL507(Object var0) {
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
L507:
		{
			func = f39;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL502(var0,var1)) {
					ret = true;
					break L507;
				}
			}
		}
		return ret;
	}
	public boolean execL502(Object var0, Object var1) {
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
L502:
		{
			if (execL503(var0,var1)) {
				ret = true;
				break L502;
			}
		}
		return ret;
	}
	public boolean execL503(Object var0, Object var1) {
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
L503:
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
			break L503;
		}
		return ret;
	}
	public boolean execL504(Object var0, Object var1) {
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
L504:
		{
			if (execL506(var0, var1)) {
				ret = true;
				break L504;
			}
		}
		return ret;
	}
	public boolean execL506(Object var0, Object var1) {
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
L506:
		{
			if (!(!(f39).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (execL502(var0,var1)) {
						ret = true;
						break L506;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f15 = new Functor("fileReader", 2, "io");
	private static final Functor f37 = new Functor("input", 1, "io");
	private static final Functor f1 = new Functor("eager", 4, "io");
	private static final Functor f23 = new Functor("/*inline*/\\r\\n		try {\\r\\n			java.io.BufferedReader br = (java.io.BufferedReader) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n			String s = br.readLine();\\r\\n			Atom result = mem.newAtom(new Functor(s==null?\"\":s, 1));\\r\\n			mem.relink(result, 0, me, 1);\\r\\n			Atom res = mem.newAtom(new Functor(s==null ? \"nil\" : \"done\", 1));\\r\\n			mem.relink(res, 0, me, 2);\\r\\n			me.nthAtom(0).remove();\\r\\n			me.remove();\\r\\n		} catch(Exception e) {Env.e(e);}\\r\\n	", 3, null);
	private static final Functor f35 = new Functor("input", 2, "io");
	private static final Functor f11 = new Functor("nil", 1, null);
	private static final Functor f17 = new Functor("stdin", 1, "io");
	private static final Functor f33 = new Functor("input", 0, "io");
	private static final Functor f24 = new Functor("readline", 2, null);
	private static final Functor f4 = new Functor("eager", 2, "io");
	private static final Functor f30 = new StringFunctor("/*inline*/\\r\\n	javax.swing.JOptionPane.showMessageDialog(null, me.nth(0));\\r\\n	mem.newAtom(new Functor(\"done\",0));\\r\\n	mem.removeAtom(me.nthAtom(0));\\r\\n	mem.removeAtom(me);\\r\\n	");
	private static final Functor f18 = new StringFunctor("java.io.PrintWriter");
	private static final Functor f22 = new Functor("stdout", 1, "io");
	private static final Functor f28 = new Functor("/*inline*/\\r\\n	try {\\r\\n		Atom br = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.FileReader(me.nth(0)))));\\r\\n//		System.out.println(\"FILE=\"+me.nth(0));\\r\\n		mem.relink(br, 0, me, 1);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	} catch(Exception e) {}\\r\\n	", 2, null);
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f5 = new Functor("[]", 1, null);
	private static final Functor f14 = new Functor("list_of_file", 2, "io");
	private static final Functor f29 = new Functor("popup", 1, "io");
	private static final Functor f16 = new Functor("list_of_stdin", 1, "io");
	private static final Functor f9 = new Functor("toFile", 3, "io");
	private static final Functor f21 = new Functor("print", 2, null);
	private static final Functor f27 = new Functor("/*inline*/\\r\\n	try {\\r\\n		Atom pw = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(new java.io.FileWriter(me.nth(0)))));\\r\\n//		System.out.println(\"FILE=\"+me.nth(0));\\r\\n		mem.relink(pw, 0, me, 1);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	} catch(Exception e) {}\\r\\n	", 2, null);
	private static final Functor f39 = new Functor("use", 0, "io");
	private static final Functor f36 = new Functor("/*inline*/\\r\\n	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\\r\\n	me.setName(\"done\");\\r\\n	me.nthAtom(0).setName(s);\\r\\n	", 2, null);
	private static final Functor f38 = new StringFunctor("/*inline*/\\r\\n	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\\r\\n	me.setName(s);\\r\\n	me.nthAtom(0).setName(\"done\");\\r\\n	");
	private static final Functor f12 = new Functor("list_of_dev_s0", 3, null);
	private static final Functor f6 = new Functor("toFile_s0", 3, null);
	private static final Functor f40 = new StringFunctor("/*inline*/\\r\\n		Atom stdin = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.InputStreamReader(System.in))));\\r\\n		mem.relink(stdin, 0, me, 0);\\r\\n		me.remove();\\r\\n		");
	private static final Functor f3 = new Functor("readline", 3, null);
	private static final Functor f31 = new Functor("inputInteger", 2, "io");
	private static final Functor f26 = new Functor("/*inline*/\\r\\n	try {\\r\\n		Object obj = ((ObjectFunctor)(me.nthAtom(0).getFunctor())).getValue();\\r\\n		if(!(obj instanceof Process)) break;\\r\\n		Atom r = mem.newAtom(new ObjectFunctor(\\r\\n		  new java.io.BufferedReader( new java.io.InputStreamReader(\\r\\n		    ((Process)obj).getInputStream()\\r\\n		  ))\\r\\n		));\\r\\n//		System.out.println(r);\\r\\n		mem.relink(r, 0, me, 1);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	} catch(Exception e) {e.printStackTrace();}\\r\\n	", 2, null);
	private static final Functor f41 = new StringFunctor("/*inline*/\\r\\n		Atom stdout = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(System.out, true)));\\r\\n		mem.relink(stdout, 0, me, 0);\\r\\n		me.remove();\\r\\n		");
	private static final Functor f13 = new StringFunctor("java.io.BufferedReader");
	private static final Functor f20 = new Functor("/*inline*/\\r\\n		try {\\r\\n			java.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n			Atom done = mem.newAtom(new Functor(\"done\", 1));\\r\\n			if(pw!=null) {\\r\\n				pw.println(me.nth(1));\\r\\n			}\\r\\n			mem.relink(done, 0, me, 2);\\r\\n			me.nthAtom(1).remove();\\r\\n			me.remove();\\r\\n		} catch(Exception e) {e.printStackTrace();}\\r\\n	", 3, null);
	private static final Functor f8 = new Functor("print", 3, null);
	private static final Functor f10 = new Functor("fileWriter", 2, "io");
	private static final Functor f0 = new Functor("done", 1, null);
	private static final Functor f25 = new Functor("reader", 2, "io");
	private static final Functor f32 = new Functor("/*inline*/\\r\\n	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\\r\\n	Atom atom = mem.newAtom(new IntegerFunctor(Integer.parseInt(s)));\\r\\n	mem.relink(atom,0,me,1);\\r\\n	mem.removeAtom(me.nthAtom(0));\\r\\n	mem.removeAtom(me);\\r\\n	", 2, null);
	private static final Functor f34 = new Functor("/*inline*/\\r\\n	String s = javax.swing.JOptionPane.showInputDialog(null, \"Input text.\");\\r\\n	me.setName(s);\\r\\n	", 0, null);
	private static final Functor f7 = new Functor("close", 2, null);
	private static final Functor f19 = new Functor("/*inline*/\\r\\n		try {\\r\\n			java.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n			pw.close();\\r\\n			mem.relink(me.nthAtom(0), 0, me, 1);\\r\\n			me.remove();\\r\\n		} catch(Exception e) {Env.e(e);}\\r\\n	", 2, null);
}

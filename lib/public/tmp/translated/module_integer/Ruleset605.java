package translated.module_integer;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset605 extends Ruleset {
	private static final Ruleset605 theInstance = new Ruleset605();
	private Ruleset605() {}
	public static Ruleset605 getInstance() {
		return theInstance;
	}
	private int id = 605;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":integer" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@integer" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL288(mem, atom, false)) {
			return true;
		}
		if (execL295(mem, atom, false)) {
			return true;
		}
		if (execL302(mem, atom, false)) {
			return true;
		}
		if (execL309(mem, atom, false)) {
			return true;
		}
		if (execL316(mem, atom, false)) {
			return true;
		}
		if (execL323(mem, atom, false)) {
			return true;
		}
		if (execL330(mem, atom, false)) {
			return true;
		}
		if (execL337(mem, atom, false)) {
			return true;
		}
		if (execL344(mem, atom, false)) {
			return true;
		}
		if (execL351(mem, atom, false)) {
			return true;
		}
		if (execL358(mem, atom, false)) {
			return true;
		}
		if (execL365(mem, atom, false)) {
			return true;
		}
		if (execL372(mem, atom, false)) {
			return true;
		}
		if (execL379(mem, atom, false)) {
			return true;
		}
		if (execL386(mem, atom, false)) {
			return true;
		}
		if (execL393(mem, atom, false)) {
			return true;
		}
		if (execL400(mem, atom, false)) {
			return true;
		}
		if (execL407(mem, atom, false)) {
			return true;
		}
		if (execL414(mem, atom, false)) {
			return true;
		}
		if (execL421(mem, atom, false)) {
			return true;
		}
		if (execL428(mem, atom, false)) {
			return true;
		}
		if (execL435(mem, atom, false)) {
			return true;
		}
		if (execL442(mem, atom, false)) {
			return true;
		}
		if (execL450(mem, atom, false)) {
			return true;
		}
		if (execL457(mem, atom, false)) {
			return true;
		}
		if (execL465(mem, atom, false)) {
			return true;
		}
		if (execL472(mem, atom, false)) {
			return true;
		}
		if (execL479(mem, atom, false)) {
			return true;
		}
		if (execL486(mem, atom, false)) {
			return true;
		}
		if (execL493(mem, atom, false)) {
			return true;
		}
		if (execL500(mem, atom, false)) {
			return true;
		}
		if (execL507(mem, atom, false)) {
			return true;
		}
		if (execL514(mem, atom, false)) {
			return true;
		}
		if (execL521(mem, atom, false)) {
			return true;
		}
		if (execL528(mem, atom, false)) {
			return true;
		}
		if (execL535(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL291(mem, nondeterministic)) {
			return true;
		}
		if (execL298(mem, nondeterministic)) {
			return true;
		}
		if (execL305(mem, nondeterministic)) {
			return true;
		}
		if (execL312(mem, nondeterministic)) {
			return true;
		}
		if (execL319(mem, nondeterministic)) {
			return true;
		}
		if (execL326(mem, nondeterministic)) {
			return true;
		}
		if (execL333(mem, nondeterministic)) {
			return true;
		}
		if (execL340(mem, nondeterministic)) {
			return true;
		}
		if (execL347(mem, nondeterministic)) {
			return true;
		}
		if (execL354(mem, nondeterministic)) {
			return true;
		}
		if (execL361(mem, nondeterministic)) {
			return true;
		}
		if (execL368(mem, nondeterministic)) {
			return true;
		}
		if (execL375(mem, nondeterministic)) {
			return true;
		}
		if (execL382(mem, nondeterministic)) {
			return true;
		}
		if (execL389(mem, nondeterministic)) {
			return true;
		}
		if (execL396(mem, nondeterministic)) {
			return true;
		}
		if (execL403(mem, nondeterministic)) {
			return true;
		}
		if (execL410(mem, nondeterministic)) {
			return true;
		}
		if (execL417(mem, nondeterministic)) {
			return true;
		}
		if (execL424(mem, nondeterministic)) {
			return true;
		}
		if (execL431(mem, nondeterministic)) {
			return true;
		}
		if (execL438(mem, nondeterministic)) {
			return true;
		}
		if (execL446(mem, nondeterministic)) {
			return true;
		}
		if (execL453(mem, nondeterministic)) {
			return true;
		}
		if (execL461(mem, nondeterministic)) {
			return true;
		}
		if (execL468(mem, nondeterministic)) {
			return true;
		}
		if (execL475(mem, nondeterministic)) {
			return true;
		}
		if (execL482(mem, nondeterministic)) {
			return true;
		}
		if (execL489(mem, nondeterministic)) {
			return true;
		}
		if (execL496(mem, nondeterministic)) {
			return true;
		}
		if (execL503(mem, nondeterministic)) {
			return true;
		}
		if (execL510(mem, nondeterministic)) {
			return true;
		}
		if (execL517(mem, nondeterministic)) {
			return true;
		}
		if (execL524(mem, nondeterministic)) {
			return true;
		}
		if (execL531(mem, nondeterministic)) {
			return true;
		}
		if (execL538(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL538(Object var0, boolean nondeterministic) {
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
L538:
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
					if (!(!(x >= y))) {
						if (execL534(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L538;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL534(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L534:
		{
			link = ((Atom)var1).getArg(1);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
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
			func = f4;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var1), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var9), 1 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().inheritLink(
				((Atom)var9), 2,
				(Link)var10 );
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_integer");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				}
			ret = true;
			break L534;
		}
		return ret;
	}
	public boolean execL535(Object var0, Object var1, boolean nondeterministic) {
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
L535:
		{
			if (execL537(var0, var1, nondeterministic)) {
				ret = true;
				break L535;
			}
		}
		return ret;
	}
	public boolean execL537(Object var0, Object var1, boolean nondeterministic) {
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
L537:
		{
			var4 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
						if (!(!(x >= y))) {
							if (execL534(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L537;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL531(Object var0, boolean nondeterministic) {
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
L531:
		{
			var2 = new Atom(null, f2);
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
					if (!(!(x <= y))) {
						if (execL527(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L531;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL527(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L527:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			ret = true;
			break L527;
		}
		return ret;
	}
	public boolean execL528(Object var0, Object var1, boolean nondeterministic) {
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
L528:
		{
			if (execL530(var0, var1, nondeterministic)) {
				ret = true;
				break L528;
			}
		}
		return ret;
	}
	public boolean execL530(Object var0, Object var1, boolean nondeterministic) {
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
L530:
		{
			var4 = new Atom(null, f2);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
						if (!(!(x <= y))) {
							if (execL527(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L530;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL524(Object var0, boolean nondeterministic) {
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
L524:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL520(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L524;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL520(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Set insset;
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
			link = ((Atom)var1).getArg(2);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f4;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var4), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var10), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 1,
				((Atom)var7), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 2,
				((Atom)var10), 1 );
			((Atom)var10).getMem().inheritLink(
				((Atom)var10), 2,
				(Link)var11 );
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_integer");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				}
			ret = true;
			break L520;
		}
		return ret;
	}
	public boolean execL521(Object var0, Object var1, boolean nondeterministic) {
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
L521:
		{
			if (execL523(var0, var1, nondeterministic)) {
				ret = true;
				break L521;
			}
		}
		return ret;
	}
	public boolean execL523(Object var0, Object var1, boolean nondeterministic) {
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
L523:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL520(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L523;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL517(Object var0, boolean nondeterministic) {
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
		Set insset;
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
			var4 = new HashSet();
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link;
				link = ((Atom)var1).getArg(1);
				var3 = link;
				((Set)var4).add(((Atom)var1));
				isground_ret = ((Link)var3).isGround(((Set)var4));
				if (!(isground_ret == -1)) {
					var5 = new IntegerFunctor(isground_ret);
					isground_ret = ((Link)var2).isGround(((Set)var4));
					if (!(isground_ret == -1)) {
						var6 = new IntegerFunctor(isground_ret);
						eqground_ret = ((Link)var3).eqGround(((Link)var2));
						if (!(!eqground_ret)) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L513",var0,var1});
							} else if (execL513(var0,var1,nondeterministic)) {
								ret = true;
								break L517;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL513(Object var0, Object var1, boolean nondeterministic) {
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
L513:
		{
			link = ((Atom)var1).getArg(1);
			var2 = link;
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var3));
			((AbstractMembrane)var0).removeGround(((Link)var2));
			var4 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			link = ((Atom)var1).getArg(2);
			var5 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var4),
				((Link)var5));
			ret = true;
			break L513;
		}
		return ret;
	}
	public boolean execL514(Object var0, Object var1, boolean nondeterministic) {
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
L514:
		{
			if (execL516(var0, var1, nondeterministic)) {
				ret = true;
				break L514;
			}
		}
		return ret;
	}
	public boolean execL516(Object var0, Object var1, boolean nondeterministic) {
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
L516:
		{
			var7 = new HashSet();
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var6 = link;
					((Set)var7).add(((Atom)var1));
					isground_ret = ((Link)var6).isGround(((Set)var7));
					if (!(isground_ret == -1)) {
						var8 = new IntegerFunctor(isground_ret);
						isground_ret = ((Link)var5).isGround(((Set)var7));
						if (!(isground_ret == -1)) {
							var9 = new IntegerFunctor(isground_ret);
							eqground_ret = ((Link)var6).eqGround(((Link)var5));
							if (!(!eqground_ret)) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L513",var0,var1});
								} else if (execL513(var0,var1,nondeterministic)) {
									ret = true;
									break L516;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL510(Object var0, boolean nondeterministic) {
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
L510:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L506",var0,var1,var2,var3});
							} else if (execL506(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L510;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL506(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L506:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var6), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var1), 1 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_integer");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				}
			ret = true;
			break L506;
		}
		return ret;
	}
	public boolean execL507(Object var0, Object var1, boolean nondeterministic) {
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
L507:
		{
			if (execL509(var0, var1, nondeterministic)) {
				ret = true;
				break L507;
			}
		}
		return ret;
	}
	public boolean execL509(Object var0, Object var1, boolean nondeterministic) {
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
L509:
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
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						link = ((Atom)var1).getArg(0);
						var6 = link.getAtom();
						if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L506",var0,var1,var5,var6});
								} else if (execL506(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L509;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL503(Object var0, boolean nondeterministic) {
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
L503:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L499",var0,var1,var2,var3});
							} else if (execL499(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L503;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL499(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L499:
		{
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
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var1), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var6), 0 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_integer");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module integer");
				}
			ret = true;
			break L499;
		}
		return ret;
	}
	public boolean execL500(Object var0, Object var1, boolean nondeterministic) {
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
L500:
		{
			if (execL502(var0, var1, nondeterministic)) {
				ret = true;
				break L500;
			}
		}
		return ret;
	}
	public boolean execL502(Object var0, Object var1, boolean nondeterministic) {
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
L502:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						link = ((Atom)var1).getArg(1);
						var6 = link.getAtom();
						if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L499",var0,var1,var5,var6});
								} else if (execL499(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L502;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL496(Object var0, boolean nondeterministic) {
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
L496:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL492(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L496;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL492(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L492:
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
			func = f9;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var4, 4);
			ret = true;
			break L492;
		}
		return ret;
	}
	public boolean execL493(Object var0, Object var1, boolean nondeterministic) {
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
L493:
		{
			if (execL495(var0, var1, nondeterministic)) {
				ret = true;
				break L493;
			}
		}
		return ret;
	}
	public boolean execL495(Object var0, Object var1, boolean nondeterministic) {
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
L495:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL492(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L495;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL489(Object var0, boolean nondeterministic) {
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
L489:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL485(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L489;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL485(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L485:
		{
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f11;
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
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var6, 3);
			ret = true;
			break L485;
		}
		return ret;
	}
	public boolean execL486(Object var0, Object var1, boolean nondeterministic) {
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
L486:
		{
			if (execL488(var0, var1, nondeterministic)) {
				ret = true;
				break L486;
			}
		}
		return ret;
	}
	public boolean execL488(Object var0, Object var1, boolean nondeterministic) {
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
L488:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL485(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L488;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL482(Object var0, boolean nondeterministic) {
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
L482:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL478(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L482;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL478(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L478:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f13;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var6, 2);
			ret = true;
			break L478;
		}
		return ret;
	}
	public boolean execL479(Object var0, Object var1, boolean nondeterministic) {
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
L479:
		{
			if (execL481(var0, var1, nondeterministic)) {
				ret = true;
				break L479;
			}
		}
		return ret;
	}
	public boolean execL481(Object var0, Object var1, boolean nondeterministic) {
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
L481:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL478(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L481;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL475(Object var0, boolean nondeterministic) {
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
L475:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL471(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L475;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL471(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L471:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f15;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var6, 1);
			ret = true;
			break L471;
		}
		return ret;
	}
	public boolean execL472(Object var0, Object var1, boolean nondeterministic) {
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
L472:
		{
			if (execL474(var0, var1, nondeterministic)) {
				ret = true;
				break L472;
			}
		}
		return ret;
	}
	public boolean execL474(Object var0, Object var1, boolean nondeterministic) {
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
L474:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL471(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L474;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL468(Object var0, boolean nondeterministic) {
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
L468:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL464(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L468;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL464(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L464:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f17;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var6, 0);
			ret = true;
			break L464;
		}
		return ret;
	}
	public boolean execL465(Object var0, Object var1, boolean nondeterministic) {
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
L465:
		{
			if (execL467(var0, var1, nondeterministic)) {
				ret = true;
				break L465;
			}
		}
		return ret;
	}
	public boolean execL467(Object var0, Object var1, boolean nondeterministic) {
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
L467:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL464(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L467;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL461(Object var0, boolean nondeterministic) {
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
L461:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f19).equals(((Atom)var2).getFunctor()))) {
						if (execL456(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L461;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L456:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 0 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L456;
		}
		return ret;
	}
	public boolean execL457(Object var0, Object var1, boolean nondeterministic) {
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
L457:
		{
			if (execL459(var0, var1, nondeterministic)) {
				ret = true;
				break L457;
			}
		}
		return ret;
	}
	public boolean execL459(Object var0, Object var1, boolean nondeterministic) {
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
L459:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f19).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL456(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L459;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL453(Object var0, boolean nondeterministic) {
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
L453:
		{
			var4 = new Atom(null, f19);
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L449",var0,var1,var2,var3,var4});
							} else if (execL449(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L453;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL449(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L449:
		{
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var1), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var1), 1 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L449;
		}
		return ret;
	}
	public boolean execL450(Object var0, Object var1, boolean nondeterministic) {
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
L450:
		{
			if (execL452(var0, var1, nondeterministic)) {
				ret = true;
				break L450;
			}
		}
		return ret;
	}
	public boolean execL452(Object var0, Object var1, boolean nondeterministic) {
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
L452:
		{
			var7 = new Atom(null, f19);
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L449",var0,var1,var5,var6,var7});
								} else if (execL449(var0,var1,var5,var6,var7,nondeterministic)) {
									ret = true;
									break L452;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL446(Object var0, boolean nondeterministic) {
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
L446:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f19).equals(((Atom)var2).getFunctor()))) {
						if (execL441(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L446;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL441(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L441:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 0 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L441;
		}
		return ret;
	}
	public boolean execL442(Object var0, Object var1, boolean nondeterministic) {
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
L442:
		{
			if (execL444(var0, var1, nondeterministic)) {
				ret = true;
				break L442;
			}
		}
		return ret;
	}
	public boolean execL444(Object var0, Object var1, boolean nondeterministic) {
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
L444:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f19).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL441(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L444;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL438(Object var0, boolean nondeterministic) {
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
L438:
		{
			var4 = new Atom(null, f19);
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L434",var0,var1,var2,var3,var4});
							} else if (execL434(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L438;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL434(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L434:
		{
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var1), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var1), 1 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L434;
		}
		return ret;
	}
	public boolean execL435(Object var0, Object var1, boolean nondeterministic) {
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
L435:
		{
			if (execL437(var0, var1, nondeterministic)) {
				ret = true;
				break L435;
			}
		}
		return ret;
	}
	public boolean execL437(Object var0, Object var1, boolean nondeterministic) {
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
L437:
		{
			var7 = new Atom(null, f19);
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L434",var0,var1,var5,var6,var7});
								} else if (execL434(var0,var1,var5,var6,var7,nondeterministic)) {
									ret = true;
									break L437;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL431(Object var0, boolean nondeterministic) {
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
L431:
		{
			var5 = new Atom(null, f19);
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL427(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L431;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL427(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L427:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f22;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L427;
		}
		return ret;
	}
	public boolean execL428(Object var0, Object var1, boolean nondeterministic) {
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
L428:
		{
			if (execL430(var0, var1, nondeterministic)) {
				ret = true;
				break L428;
			}
		}
		return ret;
	}
	public boolean execL430(Object var0, Object var1, boolean nondeterministic) {
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
L430:
		{
			var8 = new Atom(null, f19);
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x < y))) {
								if (execL427(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L430;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL424(Object var0, boolean nondeterministic) {
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
L424:
		{
			var5 = new Atom(null, f19);
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL420(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L424;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL420(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L420:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f22;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L420;
		}
		return ret;
	}
	public boolean execL421(Object var0, Object var1, boolean nondeterministic) {
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
L421:
		{
			if (execL423(var0, var1, nondeterministic)) {
				ret = true;
				break L421;
			}
		}
		return ret;
	}
	public boolean execL423(Object var0, Object var1, boolean nondeterministic) {
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
L423:
		{
			var8 = new Atom(null, f19);
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL420(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L423;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL417(Object var0, boolean nondeterministic) {
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
L417:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (!(!((Atom)var3).getFunctor().equals(((Atom)var2).getFunctor()))) {
							if (execL413(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L417;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL413(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L413:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L413;
		}
		return ret;
	}
	public boolean execL414(Object var0, Object var1, boolean nondeterministic) {
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
L414:
		{
			if (execL416(var0, var1, nondeterministic)) {
				ret = true;
				break L414;
			}
		}
		return ret;
	}
	public boolean execL416(Object var0, Object var1, boolean nondeterministic) {
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
L416:
		{
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var5).getFunctor()))) {
								if (execL413(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L416;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL410(Object var0, boolean nondeterministic) {
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
L410:
		{
			var5 = new Atom(null, f19);
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL406(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L410;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL406(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L406:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f23;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L406;
		}
		return ret;
	}
	public boolean execL407(Object var0, Object var1, boolean nondeterministic) {
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
L407:
		{
			if (execL409(var0, var1, nondeterministic)) {
				ret = true;
				break L407;
			}
		}
		return ret;
	}
	public boolean execL409(Object var0, Object var1, boolean nondeterministic) {
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
L409:
		{
			var8 = new Atom(null, f19);
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x < y))) {
								if (execL406(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L409;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL403(Object var0, boolean nondeterministic) {
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
L403:
		{
			var5 = new Atom(null, f19);
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL399(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L403;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL399(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L399:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f23;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L399;
		}
		return ret;
	}
	public boolean execL400(Object var0, Object var1, boolean nondeterministic) {
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
L400:
		{
			if (execL402(var0, var1, nondeterministic)) {
				ret = true;
				break L400;
			}
		}
		return ret;
	}
	public boolean execL402(Object var0, Object var1, boolean nondeterministic) {
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
L402:
		{
			var8 = new Atom(null, f19);
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL399(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L402;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL396(Object var0, boolean nondeterministic) {
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
L396:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (!(!((Atom)var3).getFunctor().equals(((Atom)var2).getFunctor()))) {
							if (execL392(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L396;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL392(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L392:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L392;
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1, boolean nondeterministic) {
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
L393:
		{
			if (execL395(var0, var1, nondeterministic)) {
				ret = true;
				break L393;
			}
		}
		return ret;
	}
	public boolean execL395(Object var0, Object var1, boolean nondeterministic) {
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
L395:
		{
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var5).getFunctor()))) {
								if (execL392(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L395;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL389(Object var0, boolean nondeterministic) {
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
L389:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x <= y))) {
							if (execL385(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L389;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL385(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L385:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L385;
		}
		return ret;
	}
	public boolean execL386(Object var0, Object var1, boolean nondeterministic) {
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
L386:
		{
			if (execL388(var0, var1, nondeterministic)) {
				ret = true;
				break L386;
			}
		}
		return ret;
	}
	public boolean execL388(Object var0, Object var1, boolean nondeterministic) {
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
L388:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x <= y))) {
								if (execL385(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L388;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL382(Object var0, boolean nondeterministic) {
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
L382:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL378(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L382;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL378(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L378:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L378;
		}
		return ret;
	}
	public boolean execL379(Object var0, Object var1, boolean nondeterministic) {
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
L379:
		{
			if (execL381(var0, var1, nondeterministic)) {
				ret = true;
				break L379;
			}
		}
		return ret;
	}
	public boolean execL381(Object var0, Object var1, boolean nondeterministic) {
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
L381:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL378(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L381;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL375(Object var0, boolean nondeterministic) {
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
L375:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL371(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L375;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL371(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L371:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L371;
		}
		return ret;
	}
	public boolean execL372(Object var0, Object var1, boolean nondeterministic) {
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
L372:
		{
			if (execL374(var0, var1, nondeterministic)) {
				ret = true;
				break L372;
			}
		}
		return ret;
	}
	public boolean execL374(Object var0, Object var1, boolean nondeterministic) {
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
L374:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x < y))) {
								if (execL371(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L374;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL368(Object var0, boolean nondeterministic) {
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
L368:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x >= y))) {
							if (execL364(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L368;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL364(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L364:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L364;
		}
		return ret;
	}
	public boolean execL365(Object var0, Object var1, boolean nondeterministic) {
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
L365:
		{
			if (execL367(var0, var1, nondeterministic)) {
				ret = true;
				break L365;
			}
		}
		return ret;
	}
	public boolean execL367(Object var0, Object var1, boolean nondeterministic) {
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
L367:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x >= y))) {
								if (execL364(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L367;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL361(Object var0, boolean nondeterministic) {
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
L361:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL357(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L361;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL357(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L357:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L357;
		}
		return ret;
	}
	public boolean execL358(Object var0, Object var1, boolean nondeterministic) {
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
L358:
		{
			if (execL360(var0, var1, nondeterministic)) {
				ret = true;
				break L358;
			}
		}
		return ret;
	}
	public boolean execL360(Object var0, Object var1, boolean nondeterministic) {
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
L360:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x < y))) {
								if (execL357(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L360;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL354(Object var0, boolean nondeterministic) {
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
L354:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x >= y))) {
							if (execL350(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L354;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL350(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L350:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L350;
		}
		return ret;
	}
	public boolean execL351(Object var0, Object var1, boolean nondeterministic) {
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
L351:
		{
			if (execL353(var0, var1, nondeterministic)) {
				ret = true;
				break L351;
			}
		}
		return ret;
	}
	public boolean execL353(Object var0, Object var1, boolean nondeterministic) {
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
L353:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x >= y))) {
								if (execL350(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L353;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL347(Object var0, boolean nondeterministic) {
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
L347:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x <= y))) {
							if (execL343(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L347;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL343(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L343:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L343;
		}
		return ret;
	}
	public boolean execL344(Object var0, Object var1, boolean nondeterministic) {
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
L344:
		{
			if (execL346(var0, var1, nondeterministic)) {
				ret = true;
				break L344;
			}
		}
		return ret;
	}
	public boolean execL346(Object var0, Object var1, boolean nondeterministic) {
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
L346:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x <= y))) {
								if (execL343(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L346;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL340(Object var0, boolean nondeterministic) {
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
L340:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL336(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L340;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL336(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L336:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L336;
		}
		return ret;
	}
	public boolean execL337(Object var0, Object var1, boolean nondeterministic) {
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
L337:
		{
			if (execL339(var0, var1, nondeterministic)) {
				ret = true;
				break L337;
			}
		}
		return ret;
	}
	public boolean execL339(Object var0, Object var1, boolean nondeterministic) {
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
L339:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL336(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L339;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL333(Object var0, boolean nondeterministic) {
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
L333:
		{
			var3 = new Atom(null, f19);
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x >= y))) {
						if (execL329(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L333;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL329(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L329:
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
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			ret = true;
			break L329;
		}
		return ret;
	}
	public boolean execL330(Object var0, Object var1, boolean nondeterministic) {
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
L330:
		{
			if (execL332(var0, var1, nondeterministic)) {
				ret = true;
				break L330;
			}
		}
		return ret;
	}
	public boolean execL332(Object var0, Object var1, boolean nondeterministic) {
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
L332:
		{
			var5 = new Atom(null, f19);
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x >= y))) {
							if (execL329(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L332;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL326(Object var0, boolean nondeterministic) {
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
L326:
		{
			var3 = new Atom(null, f19);
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x < y))) {
						if (execL322(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L326;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL322(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L322:
		{
			link = ((Atom)var1).getArg(1);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f30;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L322;
		}
		return ret;
	}
	public boolean execL323(Object var0, Object var1, boolean nondeterministic) {
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
L323:
		{
			if (execL325(var0, var1, nondeterministic)) {
				ret = true;
				break L323;
			}
		}
		return ret;
	}
	public boolean execL325(Object var0, Object var1, boolean nondeterministic) {
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
L325:
		{
			var5 = new Atom(null, f19);
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL322(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L325;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL319(Object var0, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L319:
		{
			func = f31;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						if (!(y == 0)) {
							func = new IntegerFunctor(x % y);
							var4 = new Atom(null, func);						
							var5 =  ((Atom)var4).getFunctor();
							var6 = new Atom(null, (Functor)(var5));
							if (execL315(var0,var1,var2,var3,var4,var6,nondeterministic)) {
								ret = true;
								break L319;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL315(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L315:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L315;
		}
		return ret;
	}
	public boolean execL316(Object var0, Object var1, boolean nondeterministic) {
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
L316:
		{
			if (execL318(var0, var1, nondeterministic)) {
				ret = true;
				break L316;
			}
		}
		return ret;
	}
	public boolean execL318(Object var0, Object var1, boolean nondeterministic) {
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
L318:
		{
			if (!(!(f31).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							if (!(y == 0)) {
								func = new IntegerFunctor(x % y);
								var7 = new Atom(null, func);						
								var8 =  ((Atom)var7).getFunctor();
								var9 = new Atom(null, (Functor)(var8));
								if (execL315(var0,var1,var5,var6,var7,var9,nondeterministic)) {
									ret = true;
									break L318;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL312(Object var0, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L312:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						if (!(y == 0)) {
							func = new IntegerFunctor(x / y);
							var4 = new Atom(null, func);				
							var5 =  ((Atom)var4).getFunctor();
							var6 = new Atom(null, (Functor)(var5));
							if (execL308(var0,var1,var2,var3,var4,var6,nondeterministic)) {
								ret = true;
								break L312;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL308(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L308:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L308;
		}
		return ret;
	}
	public boolean execL309(Object var0, Object var1, boolean nondeterministic) {
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
L309:
		{
			if (execL311(var0, var1, nondeterministic)) {
				ret = true;
				break L309;
			}
		}
		return ret;
	}
	public boolean execL311(Object var0, Object var1, boolean nondeterministic) {
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
L311:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							if (!(y == 0)) {
								func = new IntegerFunctor(x / y);
								var7 = new Atom(null, func);				
								var8 =  ((Atom)var7).getFunctor();
								var9 = new Atom(null, (Functor)(var8));
								if (execL308(var0,var1,var5,var6,var7,var9,nondeterministic)) {
									ret = true;
									break L311;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL305(Object var0, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L305:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x * y));	
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL301(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L305;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL301(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L301:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L301;
		}
		return ret;
	}
	public boolean execL302(Object var0, Object var1, boolean nondeterministic) {
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
L302:
		{
			if (execL304(var0, var1, nondeterministic)) {
				ret = true;
				break L302;
			}
		}
		return ret;
	}
	public boolean execL304(Object var0, Object var1, boolean nondeterministic) {
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
L304:
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
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x * y));	
							var8 =  ((Atom)var7).getFunctor();
							var9 = new Atom(null, (Functor)(var8));
							if (execL301(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L304;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL298(Object var0, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L298:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL294(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L298;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL294(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L294:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L294;
		}
		return ret;
	}
	public boolean execL295(Object var0, Object var1, boolean nondeterministic) {
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
L295:
		{
			if (execL297(var0, var1, nondeterministic)) {
				ret = true;
				break L295;
			}
		}
		return ret;
	}
	public boolean execL297(Object var0, Object var1, boolean nondeterministic) {
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
L297:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							var8 =  ((Atom)var7).getFunctor();
							var9 = new Atom(null, (Functor)(var8));
							if (execL294(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L297;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL291(Object var0, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L291:
		{
			func = f32;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						var4 = new Atom(null, new IntegerFunctor(x+y));
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL287(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L291;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL287(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L287:
		{
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L287;
		}
		return ret;
	}
	public boolean execL288(Object var0, Object var1, boolean nondeterministic) {
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
L288:
		{
			if (execL290(var0, var1, nondeterministic)) {
				ret = true;
				break L288;
			}
		}
		return ret;
	}
	public boolean execL290(Object var0, Object var1, boolean nondeterministic) {
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
L290:
		{
			if (!(!(f32).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							var7 = new Atom(null, new IntegerFunctor(x+y));
							var8 =  ((Atom)var7).getFunctor();
							var9 = new Atom(null, (Functor)(var8));
							if (execL287(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L290;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f26 = new Functor(">=", 3, null);
	private static final Functor f24 = new Functor("==", 3, null);
	private static final Functor f3 = new Functor("-", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a | b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f17 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a & b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f31 = new Functor("mod", 3, null);
	private static final Functor f16 = new Functor("&", 3, null);
	private static final Functor f28 = new Functor(">", 3, null);
	private static final Functor f12 = new Functor("^", 3, null);
	private static final Functor f13 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a ^ b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f8 = new Functor("rnd", 2, "integer");
	private static final Functor f2 = new IntegerFunctor(1);
	private static final Functor f29 = new Functor("abs", 2, "integer");
	private static final Functor f21 = new Functor("!=", 3, null);
	private static final Functor f22 = new Functor("true", 1, null);
	private static final Functor f20 = new Functor("<<", 3, null);
	private static final Functor f6 = new Functor("gcd", 3, "integer");
	private static final Functor f10 = new Functor("power", 3, "integer");
	private static final Functor f23 = new Functor("false", 1, null);
	private static final Functor f7 = new Functor("/", 3, null);
	private static final Functor f14 = new Functor("|", 3, null);
	private static final Functor f18 = new Functor(">>", 3, null);
	private static final Functor f30 = new IntegerFunctor(-1);
	private static final Functor f27 = new Functor("<", 3, null);
	private static final Functor f9 = new Functor("/*inline*/\r\n  int n = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  Random rand = new Random();\r\n  int rn = (int)(n*Math.random());\r\n  Atom result = mem.newAtom(new IntegerFunctor(rn));\r\n  mem.relink(result, 0, me, 1);\r\n  me.nthAtom(0).remove();\r\n  me.remove();\r\n  ", 2, null);
	private static final Functor f5 = new Functor("lcm", 3, "integer");
	private static final Functor f4 = new Functor("*", 3, null);
	private static final Functor f0 = new IntegerFunctor(2);
	private static final Functor f1 = new Functor("factorial", 2, "integer");
	private static final Functor f11 = new Functor("/*inline*/\r\n  int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int n =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  int r = 1;  \r\n  for(int i=n;i>0;i--) r*=a;\r\n  Atom result = mem.newAtom(new IntegerFunctor(r));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f32 = new Functor("+", 3, null);
	private static final Functor f25 = new Functor("=<", 3, null);
	private static final Functor f19 = new IntegerFunctor(0);
}

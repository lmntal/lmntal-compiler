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
		if (execL270(mem, atom)) {
			return true;
		}
		if (execL276(mem, atom)) {
			return true;
		}
		if (execL282(mem, atom)) {
			return true;
		}
		if (execL288(mem, atom)) {
			return true;
		}
		if (execL294(mem, atom)) {
			return true;
		}
		if (execL301(mem, atom)) {
			return true;
		}
		if (execL308(mem, atom)) {
			return true;
		}
		if (execL315(mem, atom)) {
			return true;
		}
		if (execL321(mem, atom)) {
			return true;
		}
		if (execL327(mem, atom)) {
			return true;
		}
		if (execL333(mem, atom)) {
			return true;
		}
		if (execL339(mem, atom)) {
			return true;
		}
		if (execL345(mem, atom)) {
			return true;
		}
		if (execL351(mem, atom)) {
			return true;
		}
		if (execL357(mem, atom)) {
			return true;
		}
		if (execL363(mem, atom)) {
			return true;
		}
		if (execL369(mem, atom)) {
			return true;
		}
		if (execL375(mem, atom)) {
			return true;
		}
		if (execL381(mem, atom)) {
			return true;
		}
		if (execL387(mem, atom)) {
			return true;
		}
		if (execL393(mem, atom)) {
			return true;
		}
		if (execL399(mem, atom)) {
			return true;
		}
		if (execL405(mem, atom)) {
			return true;
		}
		if (execL412(mem, atom)) {
			return true;
		}
		if (execL418(mem, atom)) {
			return true;
		}
		if (execL425(mem, atom)) {
			return true;
		}
		if (execL431(mem, atom)) {
			return true;
		}
		if (execL437(mem, atom)) {
			return true;
		}
		if (execL443(mem, atom)) {
			return true;
		}
		if (execL450(mem, atom)) {
			return true;
		}
		if (execL457(mem, atom)) {
			return true;
		}
		if (execL464(mem, atom)) {
			return true;
		}
		if (execL471(mem, atom)) {
			return true;
		}
		if (execL478(mem, atom)) {
			return true;
		}
		if (execL485(mem, atom)) {
			return true;
		}
		if (execL492(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL272(mem)) {
			return true;
		}
		if (execL278(mem)) {
			return true;
		}
		if (execL284(mem)) {
			return true;
		}
		if (execL290(mem)) {
			return true;
		}
		if (execL297(mem)) {
			return true;
		}
		if (execL304(mem)) {
			return true;
		}
		if (execL311(mem)) {
			return true;
		}
		if (execL317(mem)) {
			return true;
		}
		if (execL323(mem)) {
			return true;
		}
		if (execL329(mem)) {
			return true;
		}
		if (execL335(mem)) {
			return true;
		}
		if (execL341(mem)) {
			return true;
		}
		if (execL347(mem)) {
			return true;
		}
		if (execL353(mem)) {
			return true;
		}
		if (execL359(mem)) {
			return true;
		}
		if (execL365(mem)) {
			return true;
		}
		if (execL371(mem)) {
			return true;
		}
		if (execL377(mem)) {
			return true;
		}
		if (execL383(mem)) {
			return true;
		}
		if (execL389(mem)) {
			return true;
		}
		if (execL395(mem)) {
			return true;
		}
		if (execL401(mem)) {
			return true;
		}
		if (execL408(mem)) {
			return true;
		}
		if (execL414(mem)) {
			return true;
		}
		if (execL421(mem)) {
			return true;
		}
		if (execL427(mem)) {
			return true;
		}
		if (execL433(mem)) {
			return true;
		}
		if (execL439(mem)) {
			return true;
		}
		if (execL446(mem)) {
			return true;
		}
		if (execL453(mem)) {
			return true;
		}
		if (execL460(mem)) {
			return true;
		}
		if (execL467(mem)) {
			return true;
		}
		if (execL474(mem)) {
			return true;
		}
		if (execL481(mem)) {
			return true;
		}
		if (execL488(mem)) {
			return true;
		}
		if (execL495(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL495(Object var0) {
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
L495:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL490(var0,var1)) {
					ret = true;
					break L495;
				}
			}
		}
		return ret;
	}
	public boolean execL490(Object var0, Object var1) {
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
L490:
		{
			var2 = new Atom(null, f1);
			link = ((Atom)var1).getArg(0);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
				y = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();	
				if (!(!(x >= y))) {
					if (execL491(var0,var1,var2,var3)) {
						ret = true;
						break L490;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL491(Object var0, Object var1, Object var2, Object var3) {
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
L491:
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
			func = f0;
			var8 = ((AbstractMembrane)var0).newAtom(func);
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
				((Atom)var8), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var9), 1 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().relinkAtomArgs(
				((Atom)var9), 2,
				((Atom)var1), 1 );
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
			break L491;
		}
		return ret;
	}
	public boolean execL492(Object var0, Object var1) {
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
L492:
		{
			if (execL494(var0, var1)) {
				ret = true;
				break L492;
			}
		}
		return ret;
	}
	public boolean execL494(Object var0, Object var1) {
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
L494:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL490(var0,var1)) {
						ret = true;
						break L494;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL488(Object var0) {
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
L488:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL483(var0,var1)) {
					ret = true;
					break L488;
				}
			}
		}
		return ret;
	}
	public boolean execL483(Object var0, Object var1) {
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
L483:
		{
			var2 = new Atom(null, f2);
			link = ((Atom)var1).getArg(0);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
				y = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();	
				if (!(!(x <= y))) {
					if (execL484(var0,var1,var2,var3)) {
						ret = true;
						break L483;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL484(Object var0, Object var1, Object var2, Object var3) {
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
L484:
		{
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
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			ret = true;
			break L484;
		}
		return ret;
	}
	public boolean execL485(Object var0, Object var1) {
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
L485:
		{
			if (execL487(var0, var1)) {
				ret = true;
				break L485;
			}
		}
		return ret;
	}
	public boolean execL487(Object var0, Object var1) {
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
L487:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL483(var0,var1)) {
						ret = true;
						break L487;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL481(Object var0) {
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
L481:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL476(var0,var1)) {
					ret = true;
					break L481;
				}
			}
		}
		return ret;
	}
	public boolean execL476(Object var0, Object var1) {
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
L476:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL477(var0,var1,var2,var3)) {
						ret = true;
						break L476;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL477(Object var0, Object var1, Object var2, Object var3) {
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
L477:
		{
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
			((Atom)var10).getMem().relinkAtomArgs(
				((Atom)var10), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var9);
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
			break L477;
		}
		return ret;
	}
	public boolean execL478(Object var0, Object var1) {
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
L478:
		{
			if (execL480(var0, var1)) {
				ret = true;
				break L478;
			}
		}
		return ret;
	}
	public boolean execL480(Object var0, Object var1) {
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
L480:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL476(var0,var1)) {
						ret = true;
						break L480;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL474(Object var0) {
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
L474:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL469(var0,var1)) {
					ret = true;
					break L474;
				}
			}
		}
		return ret;
	}
	public boolean execL469(Object var0, Object var1) {
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
L469:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link;
			link = ((Atom)var1).getArg(1);
			var3 = link;
			var4 = new HashSet();
			((Set)var4).add(((Atom)var1));
			isground_ret = ((Link)var3).isGround(((Set)var4));
			if (!(isground_ret == -1)) {
				var5 = new IntegerFunctor(isground_ret);
				isground_ret = ((Link)var2).isGround(((Set)var4));
				if (!(isground_ret == -1)) {
					var6 = new IntegerFunctor(isground_ret);
					eqground_ret = ((Link)var3).eqGround(((Link)var2));
					if (!(!eqground_ret)) {
						if (execL470(var0,var1)) {
							ret = true;
							break L469;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL470(Object var0, Object var1) {
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
L470:
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
			break L470;
		}
		return ret;
	}
	public boolean execL471(Object var0, Object var1) {
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
L471:
		{
			if (execL473(var0, var1)) {
				ret = true;
				break L471;
			}
		}
		return ret;
	}
	public boolean execL473(Object var0, Object var1) {
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
L473:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL469(var0,var1)) {
						ret = true;
						break L473;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL467(Object var0) {
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
L467:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL462(var0,var1)) {
					ret = true;
					break L467;
				}
			}
		}
		return ret;
	}
	public boolean execL462(Object var0, Object var1) {
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
L462:
		{
			link = ((Atom)var1).getArg(1);
			var2 = link.getAtom();
			link = ((Atom)var1).getArg(0);
			var3 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL463(var0,var1,var2,var3)) {
							ret = true;
							break L462;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL463(Object var0, Object var1, Object var2, Object var3) {
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
L463:
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var6), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
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
			break L463;
		}
		return ret;
	}
	public boolean execL464(Object var0, Object var1) {
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
L464:
		{
			if (execL466(var0, var1)) {
				ret = true;
				break L464;
			}
		}
		return ret;
	}
	public boolean execL466(Object var0, Object var1) {
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
L466:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL462(var0,var1)) {
						ret = true;
						break L466;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL460(Object var0) {
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
L460:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL455(var0,var1)) {
					ret = true;
					break L460;
				}
			}
		}
		return ret;
	}
	public boolean execL455(Object var0, Object var1) {
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
L455:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			link = ((Atom)var1).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL456(var0,var1,var2,var3)) {
							ret = true;
							break L455;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, Object var1, Object var2, Object var3) {
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
L456:
		{
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
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
			break L456;
		}
		return ret;
	}
	public boolean execL457(Object var0, Object var1) {
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
L457:
		{
			if (execL459(var0, var1)) {
				ret = true;
				break L457;
			}
		}
		return ret;
	}
	public boolean execL459(Object var0, Object var1) {
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
L459:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL455(var0,var1)) {
						ret = true;
						break L459;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL453(Object var0) {
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
L453:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL448(var0,var1)) {
					ret = true;
					break L453;
				}
			}
		}
		return ret;
	}
	public boolean execL448(Object var0, Object var1) {
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
L448:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				if (execL449(var0,var1,var2)) {
					ret = true;
					break L448;
				}
			}
		}
		return ret;
	}
	public boolean execL449(Object var0, Object var1, Object var2) {
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
L449:
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
			func = f9;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodeinteger.run((Atom)var4, 4);
			ret = true;
			break L449;
		}
		return ret;
	}
	public boolean execL450(Object var0, Object var1) {
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
L450:
		{
			if (execL452(var0, var1)) {
				ret = true;
				break L450;
			}
		}
		return ret;
	}
	public boolean execL452(Object var0, Object var1) {
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
L452:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL448(var0,var1)) {
						ret = true;
						break L452;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL446(Object var0) {
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
L446:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL441(var0,var1)) {
					ret = true;
					break L446;
				}
			}
		}
		return ret;
	}
	public boolean execL441(Object var0, Object var1) {
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
L441:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL442(var0,var1,var2,var3)) {
						ret = true;
						break L441;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL442(Object var0, Object var1, Object var2, Object var3) {
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
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			SomeInlineCodeinteger.run((Atom)var6, 3);
			ret = true;
			break L442;
		}
		return ret;
	}
	public boolean execL443(Object var0, Object var1) {
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
L443:
		{
			if (execL445(var0, var1)) {
				ret = true;
				break L443;
			}
		}
		return ret;
	}
	public boolean execL445(Object var0, Object var1) {
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
L445:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL441(var0,var1)) {
						ret = true;
						break L445;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL439(Object var0) {
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
L439:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL435(var0,var1)) {
					ret = true;
					break L439;
				}
			}
		}
		return ret;
	}
	public boolean execL435(Object var0, Object var1) {
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
L435:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL436(var0,var1,var2,var3)) {
						ret = true;
						break L435;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL436(Object var0, Object var1, Object var2, Object var3) {
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
L436:
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
			func = f13;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			SomeInlineCodeinteger.run((Atom)var6, 2);
			ret = true;
			break L436;
		}
		return ret;
	}
	public boolean execL437(Object var0, Object var1) {
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
L437:
		{
		}
		return ret;
	}
	public boolean execL433(Object var0) {
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
L433:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL429(var0,var1)) {
					ret = true;
					break L433;
				}
			}
		}
		return ret;
	}
	public boolean execL429(Object var0, Object var1) {
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
L429:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL430(var0,var1,var2,var3)) {
						ret = true;
						break L429;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL430(Object var0, Object var1, Object var2, Object var3) {
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
L430:
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
			func = f15;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			SomeInlineCodeinteger.run((Atom)var6, 1);
			ret = true;
			break L430;
		}
		return ret;
	}
	public boolean execL431(Object var0, Object var1) {
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
L431:
		{
		}
		return ret;
	}
	public boolean execL427(Object var0) {
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
L427:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL423(var0,var1)) {
					ret = true;
					break L427;
				}
			}
		}
		return ret;
	}
	public boolean execL423(Object var0, Object var1) {
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
L423:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL424(var0,var1,var2,var3)) {
						ret = true;
						break L423;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL424(Object var0, Object var1, Object var2, Object var3) {
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
L424:
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
			func = f17;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 2 );
			SomeInlineCodeinteger.run((Atom)var6, 0);
			ret = true;
			break L424;
		}
		return ret;
	}
	public boolean execL425(Object var0, Object var1) {
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
L425:
		{
		}
		return ret;
	}
	public boolean execL421(Object var0) {
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
L421:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f19).equals(((Atom)var2).getFunctor()))) {
						if (execL416(var0,var1,var2)) {
							ret = true;
							break L421;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL416(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
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
			if (execL417(var0,var1,var2)) {
				ret = true;
				break L416;
			}
		}
		return ret;
	}
	public boolean execL417(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
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
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 2,
				((Atom)var2), 0 );
			ret = true;
			break L417;
		}
		return ret;
	}
	public boolean execL418(Object var0, Object var1) {
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
L418:
		{
		}
		return ret;
	}
	public boolean execL414(Object var0) {
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
L414:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL410(var0,var1)) {
					ret = true;
					break L414;
				}
			}
		}
		return ret;
	}
	public boolean execL410(Object var0, Object var1) {
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
L410:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					var4 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL411(var0,var1,var2,var3,var4)) {
							ret = true;
							break L410;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL411(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L411:
		{
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f1;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var11), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var11), 1 );
			((Atom)var11).getMem().relinkAtomArgs(
				((Atom)var11), 2,
				((Atom)var1), 2 );
			ret = true;
			break L411;
		}
		return ret;
	}
	public boolean execL412(Object var0, Object var1) {
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
L412:
		{
		}
		return ret;
	}
	public boolean execL408(Object var0) {
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
L408:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f20).equals(((Atom)var2).getFunctor()))) {
						if (execL403(var0,var1,var2)) {
							ret = true;
							break L408;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL403(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
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
			if (execL404(var0,var1,var2)) {
				ret = true;
				break L403;
			}
		}
		return ret;
	}
	public boolean execL404(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L404:
		{
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 2,
				((Atom)var2), 0 );
			ret = true;
			break L404;
		}
		return ret;
	}
	public boolean execL405(Object var0, Object var1) {
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
L405:
		{
		}
		return ret;
	}
	public boolean execL401(Object var0) {
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
L401:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL397(var0,var1)) {
					ret = true;
					break L401;
				}
			}
		}
		return ret;
	}
	public boolean execL397(Object var0, Object var1) {
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
L397:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					var4 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL398(var0,var1,var2,var3,var4)) {
							ret = true;
							break L397;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL398(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L398:
		{
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f1;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f20;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var11), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var11), 1 );
			((Atom)var11).getMem().relinkAtomArgs(
				((Atom)var11), 2,
				((Atom)var1), 2 );
			ret = true;
			break L398;
		}
		return ret;
	}
	public boolean execL399(Object var0, Object var1) {
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
L399:
		{
		}
		return ret;
	}
	public boolean execL395(Object var0) {
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
L395:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL391(var0,var1)) {
					ret = true;
					break L395;
				}
			}
		}
		return ret;
	}
	public boolean execL391(Object var0, Object var1) {
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
L391:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x-y));	
					var5 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x < y))) {
						if (execL392(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L391;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL392(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L392:
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
			func = f22;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L392;
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1) {
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
		}
		return ret;
	}
	public boolean execL389(Object var0) {
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
L389:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL385(var0,var1)) {
					ret = true;
					break L389;
				}
			}
		}
		return ret;
	}
	public boolean execL385(Object var0, Object var1) {
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
L385:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x-y));	
					var5 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL386(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L385;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL386(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L386:
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
			func = f22;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L386;
		}
		return ret;
	}
	public boolean execL387(Object var0, Object var1) {
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
L387:
		{
		}
		return ret;
	}
	public boolean execL383(Object var0) {
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
L383:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL379(var0,var1)) {
					ret = true;
					break L383;
				}
			}
		}
		return ret;
	}
	public boolean execL379(Object var0, Object var1) {
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
L379:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!((Atom)var3).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL380(var0,var1,var2,var3)) {
							ret = true;
							break L379;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL380(Object var0, Object var1, Object var2, Object var3) {
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
L380:
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L380;
		}
		return ret;
	}
	public boolean execL381(Object var0, Object var1) {
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
L381:
		{
		}
		return ret;
	}
	public boolean execL377(Object var0) {
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
L377:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL373(var0,var1)) {
					ret = true;
					break L377;
				}
			}
		}
		return ret;
	}
	public boolean execL373(Object var0, Object var1) {
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
L373:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x-y));	
					var5 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x < y))) {
						if (execL374(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L373;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL374(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L374;
		}
		return ret;
	}
	public boolean execL375(Object var0, Object var1) {
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
L375:
		{
		}
		return ret;
	}
	public boolean execL371(Object var0) {
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
L371:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL367(var0,var1)) {
					ret = true;
					break L371;
				}
			}
		}
		return ret;
	}
	public boolean execL367(Object var0, Object var1) {
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
L367:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x-y));	
					var5 = new Atom(null, f18);
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL368(var0,var1,var2,var3,var4,var5)) {
							ret = true;
							break L367;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL368(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L368:
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
			func = f23;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L368;
		}
		return ret;
	}
	public boolean execL369(Object var0, Object var1) {
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
L369:
		{
		}
		return ret;
	}
	public boolean execL365(Object var0) {
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
L365:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL361(var0,var1)) {
					ret = true;
					break L365;
				}
			}
		}
		return ret;
	}
	public boolean execL361(Object var0, Object var1) {
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
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!((Atom)var3).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL362(var0,var1,var2,var3)) {
							ret = true;
							break L361;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL362(Object var0, Object var1, Object var2, Object var3) {
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
L362:
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L362;
		}
		return ret;
	}
	public boolean execL363(Object var0, Object var1) {
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
L363:
		{
		}
		return ret;
	}
	public boolean execL359(Object var0) {
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
L359:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL355(var0,var1)) {
					ret = true;
					break L359;
				}
			}
		}
		return ret;
	}
	public boolean execL355(Object var0, Object var1) {
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
L355:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x <= y))) {
						if (execL356(var0,var1,var2,var3)) {
							ret = true;
							break L355;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL356(Object var0, Object var1, Object var2, Object var3) {
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
L356:
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L356;
		}
		return ret;
	}
	public boolean execL357(Object var0, Object var1) {
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
L357:
		{
		}
		return ret;
	}
	public boolean execL353(Object var0) {
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
L353:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL349(var0,var1)) {
					ret = true;
					break L353;
				}
			}
		}
		return ret;
	}
	public boolean execL349(Object var0, Object var1) {
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
L349:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL350(var0,var1,var2,var3)) {
							ret = true;
							break L349;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL350(Object var0, Object var1, Object var2, Object var3) {
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
L350:
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L350;
		}
		return ret;
	}
	public boolean execL351(Object var0, Object var1) {
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
		}
		return ret;
	}
	public boolean execL347(Object var0) {
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
L347:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL343(var0,var1)) {
					ret = true;
					break L347;
				}
			}
		}
		return ret;
	}
	public boolean execL343(Object var0, Object var1) {
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
L343:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x < y))) {
						if (execL344(var0,var1,var2,var3)) {
							ret = true;
							break L343;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL344(Object var0, Object var1, Object var2, Object var3) {
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
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L344;
		}
		return ret;
	}
	public boolean execL345(Object var0, Object var1) {
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
L345:
		{
		}
		return ret;
	}
	public boolean execL341(Object var0) {
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
L341:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL337(var0,var1)) {
					ret = true;
					break L341;
				}
			}
		}
		return ret;
	}
	public boolean execL337(Object var0, Object var1) {
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
L337:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x >= y))) {
						if (execL338(var0,var1,var2,var3)) {
							ret = true;
							break L337;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL338(Object var0, Object var1, Object var2, Object var3) {
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
L338:
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L338;
		}
		return ret;
	}
	public boolean execL339(Object var0, Object var1) {
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
L339:
		{
		}
		return ret;
	}
	public boolean execL335(Object var0) {
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
L335:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL331(var0,var1)) {
					ret = true;
					break L335;
				}
			}
		}
		return ret;
	}
	public boolean execL331(Object var0, Object var1) {
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
L331:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x < y))) {
						if (execL332(var0,var1,var2,var3)) {
							ret = true;
							break L331;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL332(Object var0, Object var1, Object var2, Object var3) {
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
L332:
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L332;
		}
		return ret;
	}
	public boolean execL333(Object var0, Object var1) {
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
L333:
		{
		}
		return ret;
	}
	public boolean execL329(Object var0) {
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
L329:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL325(var0,var1)) {
					ret = true;
					break L329;
				}
			}
		}
		return ret;
	}
	public boolean execL325(Object var0, Object var1) {
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
L325:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x >= y))) {
						if (execL326(var0,var1,var2,var3)) {
							ret = true;
							break L325;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL326(Object var0, Object var1, Object var2, Object var3) {
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
L326:
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L326;
		}
		return ret;
	}
	public boolean execL327(Object var0, Object var1) {
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
L327:
		{
		}
		return ret;
	}
	public boolean execL323(Object var0) {
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
L323:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL319(var0,var1)) {
					ret = true;
					break L323;
				}
			}
		}
		return ret;
	}
	public boolean execL319(Object var0, Object var1) {
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
L319:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x <= y))) {
						if (execL320(var0,var1,var2,var3)) {
							ret = true;
							break L319;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL320(Object var0, Object var1, Object var2, Object var3) {
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
L320:
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L320;
		}
		return ret;
	}
	public boolean execL321(Object var0, Object var1) {
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
L321:
		{
		}
		return ret;
	}
	public boolean execL317(Object var0) {
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
L317:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL313(var0,var1)) {
					ret = true;
					break L317;
				}
			}
		}
		return ret;
	}
	public boolean execL313(Object var0, Object var1) {
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
L313:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (execL314(var0,var1,var2,var3)) {
							ret = true;
							break L313;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL314(Object var0, Object var1, Object var2, Object var3) {
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
L314:
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
			func = f22;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L314;
		}
		return ret;
	}
	public boolean execL315(Object var0, Object var1) {
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
L315:
		{
		}
		return ret;
	}
	public boolean execL311(Object var0) {
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
L311:
		{
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL306(var0,var1)) {
					ret = true;
					break L311;
				}
			}
		}
		return ret;
	}
	public boolean execL306(Object var0, Object var1) {
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
L306:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				var3 = new Atom(null, f18);
				x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
				y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
				if (!(!(x >= y))) {
					if (execL307(var0,var1,var2,var3)) {
						ret = true;
						break L306;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL307(Object var0, Object var1, Object var2, Object var3) {
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
L307:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			ret = true;
			break L307;
		}
		return ret;
	}
	public boolean execL308(Object var0, Object var1) {
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
L308:
		{
			if (execL310(var0, var1)) {
				ret = true;
				break L308;
			}
		}
		return ret;
	}
	public boolean execL310(Object var0, Object var1) {
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
L310:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL306(var0,var1)) {
						ret = true;
						break L310;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL304(Object var0) {
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
L304:
		{
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL299(var0,var1)) {
					ret = true;
					break L304;
				}
			}
		}
		return ret;
	}
	public boolean execL299(Object var0, Object var1) {
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
L299:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				var3 = new Atom(null, f18);
				x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
				y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
				if (!(!(x < y))) {
					if (execL300(var0,var1,var2,var3)) {
						ret = true;
						break L299;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL300(Object var0, Object var1, Object var2, Object var3) {
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
L300:
		{
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
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var1), 1 );
			ret = true;
			break L300;
		}
		return ret;
	}
	public boolean execL301(Object var0, Object var1) {
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
L301:
		{
			if (execL303(var0, var1)) {
				ret = true;
				break L301;
			}
		}
		return ret;
	}
	public boolean execL303(Object var0, Object var1) {
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
L303:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL299(var0,var1)) {
						ret = true;
						break L303;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL297(Object var0) {
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
L297:
		{
			func = f31;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL292(var0,var1)) {
					ret = true;
					break L297;
				}
			}
		}
		return ret;
	}
	public boolean execL292(Object var0, Object var1) {
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
L292:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					if (!(y == 0)) {
						func = new IntegerFunctor(x % y);
						var4 = new Atom(null, func);						
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL293(var0,var1,var2,var3,var4,var6)) {
							ret = true;
							break L292;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL293(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L293:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			ret = true;
			break L293;
		}
		return ret;
	}
	public boolean execL294(Object var0, Object var1) {
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
L294:
		{
			if (execL296(var0, var1)) {
				ret = true;
				break L294;
			}
		}
		return ret;
	}
	public boolean execL296(Object var0, Object var1) {
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
L296:
		{
			if (!(!(f31).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL292(var0,var1)) {
						ret = true;
						break L296;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL290(Object var0) {
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
L290:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL286(var0,var1)) {
					ret = true;
					break L290;
				}
			}
		}
		return ret;
	}
	public boolean execL286(Object var0, Object var1) {
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
L286:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					if (!(y == 0)) {
						func = new IntegerFunctor(x / y);
						var4 = new Atom(null, func);				
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL287(var0,var1,var2,var3,var4,var6)) {
							ret = true;
							break L286;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL287(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L287:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			ret = true;
			break L287;
		}
		return ret;
	}
	public boolean execL288(Object var0, Object var1) {
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
		}
		return ret;
	}
	public boolean execL284(Object var0) {
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
L284:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL280(var0,var1)) {
					ret = true;
					break L284;
				}
			}
		}
		return ret;
	}
	public boolean execL280(Object var0, Object var1) {
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
L280:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x * y));	
					var5 =  ((Atom)var4).getFunctor();
					var6 = new Atom(null, (Functor)(var5));
					if (execL281(var0,var1,var2,var3,var4,var6)) {
						ret = true;
						break L280;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL281(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L281:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			ret = true;
			break L281;
		}
		return ret;
	}
	public boolean execL282(Object var0, Object var1) {
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
L282:
		{
		}
		return ret;
	}
	public boolean execL278(Object var0) {
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
L278:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL274(var0,var1)) {
					ret = true;
					break L278;
				}
			}
		}
		return ret;
	}
	public boolean execL274(Object var0, Object var1) {
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
L274:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x-y));	
					var5 =  ((Atom)var4).getFunctor();
					var6 = new Atom(null, (Functor)(var5));
					if (execL275(var0,var1,var2,var3,var4,var6)) {
						ret = true;
						break L274;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL275(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L275:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			ret = true;
			break L275;
		}
		return ret;
	}
	public boolean execL276(Object var0, Object var1) {
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
L276:
		{
		}
		return ret;
	}
	public boolean execL272(Object var0) {
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
L272:
		{
			func = f32;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL268(var0,var1)) {
					ret = true;
					break L272;
				}
			}
		}
		return ret;
	}
	public boolean execL268(Object var0, Object var1) {
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
L268:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var4 = new Atom(null, new IntegerFunctor(x+y));
					var5 =  ((Atom)var4).getFunctor();
					var6 = new Atom(null, (Functor)(var5));
					if (execL269(var0,var1,var2,var3,var4,var6)) {
						ret = true;
						break L268;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL269(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L269:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var1), 2 );
			ret = true;
			break L269;
		}
		return ret;
	}
	public boolean execL270(Object var0, Object var1) {
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
L270:
		{
		}
		return ret;
	}
	private static final Functor f26 = new Functor(">=", 3, null);
	private static final Functor f24 = new Functor("==", 3, null);
	private static final Functor f3 = new Functor("-", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\\r\\n int a = \\r\\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n  int b =\\r\\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n  Atom result = mem.newAtom(new IntegerFunctor(a | b));\\r\\n  mem.relink(result, 0, me, 2);\\r\\n  me.nthAtom(0).remove();\\r\\n  me.nthAtom(1).remove();\\r\\n  me.remove();\\r\\n ", 3, null);
	private static final Functor f17 = new Functor("/*inline*/\\r\\n int a = \\r\\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n  int b =\\r\\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n  Atom result = mem.newAtom(new IntegerFunctor(a & b));\\r\\n  mem.relink(result, 0, me, 2);\\r\\n  me.nthAtom(0).remove();\\r\\n  me.nthAtom(1).remove();\\r\\n  me.remove();\\r\\n ", 3, null);
	private static final Functor f31 = new Functor("mod", 3, null);
	private static final Functor f29 = new Functor("abs", 2, "integer");
	private static final Functor f8 = new Functor("rnd", 2, "integer");
	private static final Functor f16 = new Functor("&", 3, null);
	private static final Functor f28 = new Functor(">", 3, null);
	private static final Functor f12 = new Functor("^", 3, null);
	private static final Functor f0 = new Functor("factorial", 2, "integer");
	private static final Functor f13 = new Functor("/*inline*/\\r\\n int a = \\r\\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n  int b =\\r\\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n  Atom result = mem.newAtom(new IntegerFunctor(a ^ b));\\r\\n  mem.relink(result, 0, me, 2);\\r\\n  me.nthAtom(0).remove();\\r\\n  me.nthAtom(1).remove();\\r\\n  me.remove();\\r\\n ", 3, null);
	private static final Functor f2 = new IntegerFunctor(1);
	private static final Functor f21 = new Functor("!=", 3, null);
	private static final Functor f22 = new Functor("true", 1, null);
	private static final Functor f20 = new Functor("<<", 3, null);
	private static final Functor f10 = new Functor("power", 3, "integer");
	private static final Functor f23 = new Functor("false", 1, null);
	private static final Functor f7 = new Functor("/", 3, null);
	private static final Functor f14 = new Functor("|", 3, null);
	private static final Functor f19 = new Functor(">>", 3, null);
	private static final Functor f30 = new IntegerFunctor(-1);
	private static final Functor f27 = new Functor("<", 3, null);
	private static final Functor f9 = new Functor("/*inline*/\\r\\n  int n = \\r\\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n  Random rand = new Random();\\r\\n  int rn = (int)(n*Math.random());\\r\\n  Atom result = mem.newAtom(new IntegerFunctor(rn));\\r\\n  mem.relink(result, 0, me, 1);\\r\\n  me.nthAtom(0).remove();\\r\\n  me.remove();\\r\\n  ", 2, null);
	private static final Functor f4 = new Functor("*", 3, null);
	private static final Functor f1 = new IntegerFunctor(2);
	private static final Functor f5 = new Functor("lcm", 3, "integer");
	private static final Functor f11 = new Functor("/*inline*/\\r\\n  int a = \\r\\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n  int n =\\r\\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n  int r = 1;  \\r\\n  for(int i=n;i>0;i--) r*=a;\\r\\n  Atom result = mem.newAtom(new IntegerFunctor(r));\\r\\n  mem.relink(result, 0, me, 2);\\r\\n  me.nthAtom(0).remove();\\r\\n  me.nthAtom(1).remove();\\r\\n  me.remove();\\r\\n ", 3, null);
	private static final Functor f6 = new Functor("gcd", 3, "integer");
	private static final Functor f32 = new Functor("+", 3, null);
	private static final Functor f25 = new Functor("=<", 3, null);
	private static final Functor f18 = new IntegerFunctor(0);
}

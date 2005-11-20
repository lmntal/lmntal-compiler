package translated.module_list;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset611 extends Ruleset {
	private static final Ruleset611 theInstance = new Ruleset611();
	private Ruleset611() {}
	public static Ruleset611 getInstance() {
		return theInstance;
	}
	private int id = 611;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":list" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@list" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL715(mem, atom)) {
			return true;
		}
		if (execL722(mem, atom)) {
			return true;
		}
		if (execL730(mem, atom)) {
			return true;
		}
		if (execL738(mem, atom)) {
			return true;
		}
		if (execL746(mem, atom)) {
			return true;
		}
		if (execL754(mem, atom)) {
			return true;
		}
		if (execL762(mem, atom)) {
			return true;
		}
		if (execL770(mem, atom)) {
			return true;
		}
		if (execL778(mem, atom)) {
			return true;
		}
		if (execL786(mem, atom)) {
			return true;
		}
		if (execL795(mem, atom)) {
			return true;
		}
		if (execL804(mem, atom)) {
			return true;
		}
		if (execL811(mem, atom)) {
			return true;
		}
		if (execL824(mem, atom)) {
			return true;
		}
		if (execL837(mem, atom)) {
			return true;
		}
		if (execL845(mem, atom)) {
			return true;
		}
		if (execL852(mem, atom)) {
			return true;
		}
		if (execL860(mem, atom)) {
			return true;
		}
		if (execL868(mem, atom)) {
			return true;
		}
		if (execL875(mem, atom)) {
			return true;
		}
		if (execL884(mem, atom)) {
			return true;
		}
		if (execL893(mem, atom)) {
			return true;
		}
		if (execL902(mem, atom)) {
			return true;
		}
		if (execL911(mem, atom)) {
			return true;
		}
		if (execL920(mem, atom)) {
			return true;
		}
		if (execL927(mem, atom)) {
			return true;
		}
		if (execL934(mem, atom)) {
			return true;
		}
		if (execL942(mem, atom)) {
			return true;
		}
		if (execL950(mem, atom)) {
			return true;
		}
		if (execL958(mem, atom)) {
			return true;
		}
		if (execL966(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL718(mem)) {
			return true;
		}
		if (execL726(mem)) {
			return true;
		}
		if (execL734(mem)) {
			return true;
		}
		if (execL742(mem)) {
			return true;
		}
		if (execL750(mem)) {
			return true;
		}
		if (execL758(mem)) {
			return true;
		}
		if (execL766(mem)) {
			return true;
		}
		if (execL774(mem)) {
			return true;
		}
		if (execL782(mem)) {
			return true;
		}
		if (execL791(mem)) {
			return true;
		}
		if (execL800(mem)) {
			return true;
		}
		if (execL807(mem)) {
			return true;
		}
		if (execL820(mem)) {
			return true;
		}
		if (execL833(mem)) {
			return true;
		}
		if (execL841(mem)) {
			return true;
		}
		if (execL848(mem)) {
			return true;
		}
		if (execL856(mem)) {
			return true;
		}
		if (execL864(mem)) {
			return true;
		}
		if (execL871(mem)) {
			return true;
		}
		if (execL880(mem)) {
			return true;
		}
		if (execL889(mem)) {
			return true;
		}
		if (execL898(mem)) {
			return true;
		}
		if (execL907(mem)) {
			return true;
		}
		if (execL916(mem)) {
			return true;
		}
		if (execL923(mem)) {
			return true;
		}
		if (execL930(mem)) {
			return true;
		}
		if (execL938(mem)) {
			return true;
		}
		if (execL946(mem)) {
			return true;
		}
		if (execL954(mem)) {
			return true;
		}
		if (execL962(mem)) {
			return true;
		}
		if (execL970(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL970(Object var0) {
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
L970:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL964(var0,var1,var2)) {
							ret = true;
							break L970;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL964(Object var0, Object var1, Object var2) {
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
L964:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			var4 = new HashSet();
			((Set)var4).add(((Atom)var1));
			((Set)var4).add(((Atom)var2));
			isground_ret = ((Link)var3).isGround(((Set)var4));
			if (!(isground_ret == -1)) {
				var5 = new IntegerFunctor(isground_ret);
				if (execL965(var0,var1,var2)) {
					ret = true;
					break L964;
				}
			}
		}
		return ret;
	}
	public boolean execL965(Object var0, Object var1, Object var2) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L965:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var3));
			var4 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			var5 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var6), 0);
			var9 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var9),
				((Link)var4));
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var2), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var8), 0 );
			link = new Link(((Atom)var7), 0);
			var10 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var10),
				((Link)var5));
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var2), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 1 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L965;
		}
		return ret;
	}
	public boolean execL966(Object var0, Object var1) {
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
L966:
		{
			if (execL968(var0, var1)) {
				ret = true;
				break L966;
			}
		}
		return ret;
	}
	public boolean execL968(Object var0, Object var1) {
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
L968:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL964(var0,var1,var5)) {
								ret = true;
								break L968;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL962(Object var0) {
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
L962:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL956(var0,var1,var2)) {
							ret = true;
							break L962;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL956(Object var0, Object var1, Object var2) {
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
L956:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			var4 = new HashSet();
			((Set)var4).add(((Atom)var1));
			((Set)var4).add(((Atom)var2));
			isground_ret = ((Link)var3).isGround(((Set)var4));
			if (!(isground_ret == -1)) {
				var5 = new IntegerFunctor(isground_ret);
				if (execL957(var0,var1,var2)) {
					ret = true;
					break L956;
				}
			}
		}
		return ret;
	}
	public boolean execL957(Object var0, Object var1, Object var2) {
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
L957:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var3));
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L957;
		}
		return ret;
	}
	public boolean execL958(Object var0, Object var1) {
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
L958:
		{
			if (execL960(var0, var1)) {
				ret = true;
				break L958;
			}
		}
		return ret;
	}
	public boolean execL960(Object var0, Object var1) {
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
L960:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL956(var0,var1,var5)) {
								ret = true;
								break L960;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL954(Object var0) {
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
L954:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL948(var0,var1,var2)) {
							ret = true;
							break L954;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL948(Object var0, Object var1, Object var2) {
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
L948:
		{
			link = ((Atom)var2).getArg(1);
			var3 = link;
			var4 = new Atom(null, f4);
			link = ((Atom)var1).getArg(1);
			var5 = link.getAtom();
			if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
				x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
				y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
				if (!(!(x > y))) {
					var6 = new HashSet();
					((Set)var6).add(((Atom)var1));
					((Set)var6).add(((Atom)var2));
					isground_ret = ((Link)var3).isGround(((Set)var6));
					if (!(isground_ret == -1)) {
						var7 = new IntegerFunctor(isground_ret);
						if (execL949(var0,var1,var2,var4,var5)) {
							ret = true;
							break L948;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL949(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L949:
		{
			link = ((Atom)var2).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var5));
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var5));
			var7 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var5));
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var11), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var8), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 2,
				((Atom)var12), 1 );
			link = new Link(((Atom)var12), 0);
			var16 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var16),
				((Link)var6));
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var13), 1 );
			((Atom)var13).getMem().relinkAtomArgs(
				((Atom)var13), 0,
				((Atom)var2), 0 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 2,
				((Atom)var15), 0 );
			link = new Link(((Atom)var14), 0);
			var17 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var17),
				((Link)var7));
			((Atom)var14).getMem().newLink(
				((Atom)var14), 1,
				((Atom)var9), 0 );
			((Atom)var14).getMem().newLink(
				((Atom)var14), 2,
				((Atom)var15), 1 );
			((Atom)var15).getMem().relinkAtomArgs(
				((Atom)var15), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var15);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var14);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var13);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L949;
		}
		return ret;
	}
	public boolean execL950(Object var0, Object var1) {
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
L950:
		{
			if (execL952(var0, var1)) {
				ret = true;
				break L950;
			}
		}
		return ret;
	}
	public boolean execL952(Object var0, Object var1) {
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
L952:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL948(var0,var1,var5)) {
								ret = true;
								break L952;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL946(Object var0) {
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
L946:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL940(var0,var1,var2)) {
							ret = true;
							break L946;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL940(Object var0, Object var1, Object var2) {
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
L940:
		{
			var3 = new Atom(null, f4);
			link = ((Atom)var1).getArg(1);
			var4 = link.getAtom();
			if (!(!((Atom)var3).getFunctor().equals(((Atom)var4).getFunctor()))) {
				if (execL941(var0,var1,var2,var3,var4)) {
					ret = true;
					break L940;
				}
			}
		}
		return ret;
	}
	public boolean execL941(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L941:
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
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var2), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var9), 0 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 0,
				((Atom)var2), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var9), 1 );
			((Atom)var9).getMem().relinkAtomArgs(
				((Atom)var9), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L941;
		}
		return ret;
	}
	public boolean execL942(Object var0, Object var1) {
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
L942:
		{
			if (execL944(var0, var1)) {
				ret = true;
				break L942;
			}
		}
		return ret;
	}
	public boolean execL944(Object var0, Object var1) {
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
L944:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL940(var0,var1,var5)) {
								ret = true;
								break L944;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL938(Object var0) {
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
L938:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL932(var0,var1,var2)) {
							ret = true;
							break L938;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL932(Object var0, Object var1, Object var2) {
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
L932:
		{
			link = ((Atom)var1).getArg(1);
			var3 = link.getAtom();
			if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
				if (execL933(var0,var1,var2,var3)) {
					ret = true;
					break L932;
				}
			}
		}
		return ret;
	}
	public boolean execL933(Object var0, Object var1, Object var2, Object var3) {
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
L933:
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
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L933;
		}
		return ret;
	}
	public boolean execL934(Object var0, Object var1) {
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
L934:
		{
			if (execL936(var0, var1)) {
				ret = true;
				break L934;
			}
		}
		return ret;
	}
	public boolean execL936(Object var0, Object var1) {
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
L936:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL932(var0,var1,var5)) {
								ret = true;
								break L936;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL930(Object var0) {
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
L930:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL925(var0,var1)) {
					ret = true;
					break L930;
				}
			}
		}
		return ret;
	}
	public boolean execL925(Object var0, Object var1) {
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
L925:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link;
			var3 = new Atom(null, f7);
			link = ((Atom)var1).getArg(1);
			var4 = link.getAtom();
			if (!(!((Atom)var3).getFunctor().equals(((Atom)var4).getFunctor()))) {
				var5 = new HashSet();
				((Set)var5).add(((Atom)var1));
				isground_ret = ((Link)var2).isGround(((Set)var5));
				if (!(isground_ret == -1)) {
					var6 = new IntegerFunctor(isground_ret);
					if (execL926(var0,var1,var3,var4)) {
						ret = true;
						break L925;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL926(Object var0, Object var1, Object var2, Object var3) {
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
L926:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var4));
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			func = f2;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var1), 2 );
			ret = true;
			break L926;
		}
		return ret;
	}
	public boolean execL927(Object var0, Object var1) {
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
L927:
		{
			if (execL929(var0, var1)) {
				ret = true;
				break L927;
			}
		}
		return ret;
	}
	public boolean execL929(Object var0, Object var1) {
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
L929:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL925(var0,var1)) {
						ret = true;
						break L929;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL923(Object var0) {
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
L923:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL918(var0,var1)) {
					ret = true;
					break L923;
				}
			}
		}
		return ret;
	}
	public boolean execL918(Object var0, Object var1) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L918:
		{
			if (execL919(var0,var1)) {
				ret = true;
				break L918;
			}
		}
		return ret;
	}
	public boolean execL919(Object var0, Object var1) {
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
L919:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f3;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 1,
				((Atom)var1), 1 );
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L919;
		}
		return ret;
	}
	public boolean execL920(Object var0, Object var1) {
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
L920:
		{
			if (execL922(var0, var1)) {
				ret = true;
				break L920;
			}
		}
		return ret;
	}
	public boolean execL922(Object var0, Object var1) {
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
L922:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL918(var0,var1)) {
						ret = true;
						break L922;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL916(Object var0) {
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
L916:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f10).equals(((Atom)var2).getFunctor()))) {
						if (execL909(var0,var1,var2)) {
							ret = true;
							break L916;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL909(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L909:
		{
			if (execL910(var0,var1,var2)) {
				ret = true;
				break L909;
			}
		}
		return ret;
	}
	public boolean execL910(Object var0, Object var1, Object var2) {
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
L910:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f10;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f10;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 1 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var2), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var2), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L910;
		}
		return ret;
	}
	public boolean execL911(Object var0, Object var1) {
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
L911:
		{
			if (execL913(var0, var1)) {
				ret = true;
				break L911;
			}
			if (execL915(var0, var1)) {
				ret = true;
				break L911;
			}
		}
		return ret;
	}
	public boolean execL915(Object var0, Object var1) {
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
L915:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f9).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL909(var0,var5,var1)) {
								ret = true;
								break L915;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL913(Object var0, Object var1) {
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
L913:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f10).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL909(var0,var1,var5)) {
								ret = true;
								break L913;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL907(Object var0) {
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
L907:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f10).equals(((Atom)var2).getFunctor()))) {
						if (execL900(var0,var1,var2)) {
							ret = true;
							break L907;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL900(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L900:
		{
			if (execL901(var0,var1,var2)) {
				ret = true;
				break L900;
			}
		}
		return ret;
	}
	public boolean execL901(Object var0, Object var1, Object var2) {
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
L901:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f11;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f9;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var4), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var2), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var2), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L901;
		}
		return ret;
	}
	public boolean execL902(Object var0, Object var1) {
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
L902:
		{
			if (execL904(var0, var1)) {
				ret = true;
				break L902;
			}
			if (execL906(var0, var1)) {
				ret = true;
				break L902;
			}
		}
		return ret;
	}
	public boolean execL906(Object var0, Object var1) {
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
L906:
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
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(f11).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL900(var0,var5,var1)) {
								ret = true;
								break L906;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL904(Object var0, Object var1) {
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
L904:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f10).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL900(var0,var1,var4)) {
								ret = true;
								break L904;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL898(Object var0) {
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
L898:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f10).equals(((Atom)var2).getFunctor()))) {
						if (execL891(var0,var1,var2)) {
							ret = true;
							break L898;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL891(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L891:
		{
			if (execL892(var0,var1,var2)) {
				ret = true;
				break L891;
			}
		}
		return ret;
	}
	public boolean execL892(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L892:
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
			break L892;
		}
		return ret;
	}
	public boolean execL893(Object var0, Object var1) {
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
L893:
		{
			if (execL895(var0, var1)) {
				ret = true;
				break L893;
			}
			if (execL897(var0, var1)) {
				ret = true;
				break L893;
			}
		}
		return ret;
	}
	public boolean execL897(Object var0, Object var1) {
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
L897:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f12).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL891(var0,var5,var1)) {
								ret = true;
								break L897;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL895(Object var0, Object var1) {
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
L895:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f10).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							if (execL891(var0,var1,var3)) {
								ret = true;
								break L895;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL889(Object var0) {
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
L889:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f14).equals(((Atom)var2).getFunctor()))) {
						if (execL882(var0,var1,var2)) {
							ret = true;
							break L889;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL882(Object var0, Object var1, Object var2) {
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
L882:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var2).getArg(1);
				var4 = link.getAtom();
				func = ((Atom)var4).getFunctor();
				if (!(func.getArity() != 1)) {
					link = ((Atom)var2).getArg(2);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						link = ((Atom)var2).getArg(3);
						var6 = link.getAtom();
						func = ((Atom)var6).getFunctor();
						if (!(func.getArity() != 1)) {
							link = ((Atom)var2).getArg(4);
							var7 = link.getAtom();
							func = ((Atom)var7).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL883(var0,var1,var2,var3,var4,var5,var6,var7)) {
									ret = true;
									break L882;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL883(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L883:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f2;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var11), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var8), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 2,
				((Atom)var12), 1 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var9), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var14), 0 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 0,
				((Atom)var14), 1 );
			((Atom)var14).getMem().relinkAtomArgs(
				((Atom)var14), 2,
				((Atom)var2), 6 );
			ret = true;
			break L883;
		}
		return ret;
	}
	public boolean execL884(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L884:
		{
			if (execL886(var0, var1)) {
				ret = true;
				break L884;
			}
			if (execL888(var0, var1)) {
				ret = true;
				break L884;
			}
		}
		return ret;
	}
	public boolean execL888(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L888:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					link = ((Atom)var1).getArg(6);
					var8 = link;
					link = ((Atom)var1).getArg(5);
					if (!(link.getPos() != 0)) {
						var9 = link.getAtom();
						if (!(!(f13).equals(((Atom)var9).getFunctor()))) {
							link = ((Atom)var9).getArg(0);
							var10 = link;
							if (execL882(var0,var9,var1)) {
								ret = true;
								break L888;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL886(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L886:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f14).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(5);
							var9 = link;
							link = ((Atom)var3).getArg(6);
							var10 = link;
							if (execL882(var0,var1,var3)) {
								ret = true;
								break L886;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL880(Object var0) {
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
L880:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f14).equals(((Atom)var2).getFunctor()))) {
						if (execL873(var0,var1,var2)) {
							ret = true;
							break L880;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL873(Object var0, Object var1, Object var2) {
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
L873:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var2).getArg(1);
				var4 = link.getAtom();
				func = ((Atom)var4).getFunctor();
				if (!(func.getArity() != 1)) {
					link = ((Atom)var2).getArg(2);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						link = ((Atom)var2).getArg(3);
						var6 = link.getAtom();
						func = ((Atom)var6).getFunctor();
						if (!(func.getArity() != 1)) {
							link = ((Atom)var2).getArg(4);
							var7 = link.getAtom();
							func = ((Atom)var7).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL874(var0,var1,var2,var3,var4,var5,var6,var7)) {
									ret = true;
									break L873;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL874(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L874:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var11 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var12 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var14 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var15 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var16 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var17 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var25 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var26 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var27 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var28 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var18).getMem().newLink(
				((Atom)var18), 0,
				((Atom)var19), 1 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 0,
				((Atom)var8), 0 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 2,
				((Atom)var20), 1 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 0,
				((Atom)var14), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 2,
				((Atom)var28), 0 );
			((Atom)var21).getMem().newLink(
				((Atom)var21), 0,
				((Atom)var22), 1 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 0,
				((Atom)var9), 0 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 2,
				((Atom)var23), 1 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 0,
				((Atom)var11), 0 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 2,
				((Atom)var27), 3 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 0,
				((Atom)var25), 1 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 0,
				((Atom)var10), 0 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 2,
				((Atom)var26), 1 );
			((Atom)var26).getMem().newLink(
				((Atom)var26), 0,
				((Atom)var16), 0 );
			((Atom)var26).getMem().newLink(
				((Atom)var26), 2,
				((Atom)var27), 5 );
			((Atom)var27).getMem().newLink(
				((Atom)var27), 0,
				((Atom)var17), 0 );
			((Atom)var27).getMem().newLink(
				((Atom)var27), 1,
				((Atom)var15), 0 );
			((Atom)var27).getMem().newLink(
				((Atom)var27), 2,
				((Atom)var12), 0 );
			((Atom)var27).getMem().newLink(
				((Atom)var27), 4,
				((Atom)var13), 0 );
			((Atom)var27).getMem().newLink(
				((Atom)var27), 6,
				((Atom)var28), 1 );
			((Atom)var28).getMem().relinkAtomArgs(
				((Atom)var28), 2,
				((Atom)var2), 6 );
			atom = ((Atom)var27);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L874;
		}
		return ret;
	}
	public boolean execL875(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L875:
		{
			if (execL877(var0, var1)) {
				ret = true;
				break L875;
			}
			if (execL879(var0, var1)) {
				ret = true;
				break L875;
			}
		}
		return ret;
	}
	public boolean execL879(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L879:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					link = ((Atom)var1).getArg(6);
					var8 = link;
					link = ((Atom)var1).getArg(5);
					if (!(link.getPos() != 0)) {
						var9 = link.getAtom();
						if (!(!(f15).equals(((Atom)var9).getFunctor()))) {
							link = ((Atom)var9).getArg(0);
							var10 = link;
							if (execL873(var0,var9,var1)) {
								ret = true;
								break L879;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL877(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L877:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f14).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(5);
							var9 = link;
							link = ((Atom)var3).getArg(6);
							var10 = link;
							if (execL873(var0,var1,var3)) {
								ret = true;
								break L877;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL871(Object var0) {
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
L871:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL866(var0,var1)) {
					ret = true;
					break L871;
				}
			}
		}
		return ret;
	}
	public boolean execL866(Object var0, Object var1) {
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
L866:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					link = ((Atom)var1).getArg(2);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						link = ((Atom)var1).getArg(3);
						var5 = link.getAtom();
						func = ((Atom)var5).getFunctor();
						if (!(func.getArity() != 1)) {
							link = ((Atom)var1).getArg(4);
							var6 = link.getAtom();
							func = ((Atom)var6).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL867(var0,var1,var2,var3,var4,var5,var6)) {
									ret = true;
									break L866;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL867(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L867:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var11 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var12 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f2;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var17 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var14).getMem().newLink(
				((Atom)var14), 0,
				((Atom)var15), 1 );
			((Atom)var15).getMem().newLink(
				((Atom)var15), 0,
				((Atom)var7), 0 );
			((Atom)var15).getMem().newLink(
				((Atom)var15), 2,
				((Atom)var16), 1 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 0,
				((Atom)var12), 0 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 2,
				((Atom)var17), 5 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 0,
				((Atom)var13), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 1,
				((Atom)var11), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 2,
				((Atom)var9), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 3,
				((Atom)var8), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 4,
				((Atom)var10), 0 );
			((Atom)var17).getMem().relinkAtomArgs(
				((Atom)var17), 6,
				((Atom)var1), 5 );
			atom = ((Atom)var17);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L867;
		}
		return ret;
	}
	public boolean execL868(Object var0, Object var1) {
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
L868:
		{
			if (execL870(var0, var1)) {
				ret = true;
				break L868;
			}
		}
		return ret;
	}
	public boolean execL870(Object var0, Object var1) {
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
L870:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					if (execL866(var0,var1)) {
						ret = true;
						break L870;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL864(Object var0) {
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
L864:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL858(var0,var1,var2)) {
							ret = true;
							break L864;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL858(Object var0, Object var1, Object var2) {
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
L858:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var1).getArg(1);
				var4 = link.getAtom();
				func = ((Atom)var4).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL859(var0,var1,var2,var3,var4)) {
						ret = true;
						break L858;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL859(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L859:
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 1 );
			((Atom)var9).getMem().relinkAtomArgs(
				((Atom)var9), 0,
				((Atom)var2), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 2,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var12), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var7), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 1,
				((Atom)var5), 0 );
			((Atom)var11).getMem().relinkAtomArgs(
				((Atom)var11), 2,
				((Atom)var2), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 3,
				((Atom)var12), 1 );
			((Atom)var12).getMem().relinkAtomArgs(
				((Atom)var12), 2,
				((Atom)var1), 3 );
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L859;
		}
		return ret;
	}
	public boolean execL860(Object var0, Object var1) {
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
L860:
		{
			if (execL862(var0, var1)) {
				ret = true;
				break L860;
			}
		}
		return ret;
	}
	public boolean execL862(Object var0, Object var1) {
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
L862:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
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
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
						if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							link = ((Atom)var6).getArg(2);
							var9 = link;
							if (execL858(var0,var1,var6)) {
								ret = true;
								break L862;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL856(Object var0) {
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
L856:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL850(var0,var1,var2)) {
							ret = true;
							break L856;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL850(Object var0, Object var1, Object var2) {
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
L850:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link.getAtom();
			func = ((Atom)var3).getFunctor();
			if (!(func.getArity() != 1)) {
				link = ((Atom)var1).getArg(1);
				var4 = link.getAtom();
				func = ((Atom)var4).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL851(var0,var1,var2,var3,var4)) {
						ret = true;
						break L850;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL851(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L851:
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var1), 3 );
			ret = true;
			break L851;
		}
		return ret;
	}
	public boolean execL852(Object var0, Object var1) {
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
L852:
		{
			if (execL854(var0, var1)) {
				ret = true;
				break L852;
			}
		}
		return ret;
	}
	public boolean execL854(Object var0, Object var1) {
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
L854:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							if (execL850(var0,var1,var6)) {
								ret = true;
								break L854;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL848(Object var0) {
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
L848:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL843(var0,var1)) {
					ret = true;
					break L848;
				}
			}
		}
		return ret;
	}
	public boolean execL843(Object var0, Object var1) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L843:
		{
			if (execL844(var0,var1)) {
				ret = true;
				break L843;
			}
		}
		return ret;
	}
	public boolean execL844(Object var0, Object var1) {
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
L844:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f2;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
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
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 3,
				((Atom)var1), 2 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L844;
		}
		return ret;
	}
	public boolean execL845(Object var0, Object var1) {
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
L845:
		{
			if (execL847(var0, var1)) {
				ret = true;
				break L845;
			}
		}
		return ret;
	}
	public boolean execL847(Object var0, Object var1) {
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
L847:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL843(var0,var1)) {
						ret = true;
						break L847;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL841(Object var0) {
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
L841:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL835(var0,var1,var2)) {
							ret = true;
							break L841;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL835(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L835:
		{
			if (execL836(var0,var1,var2)) {
				ret = true;
				break L835;
			}
		}
		return ret;
	}
	public boolean execL836(Object var0, Object var1, Object var2) {
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
L836:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 1 );
			ret = true;
			break L836;
		}
		return ret;
	}
	public boolean execL837(Object var0, Object var1) {
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
L837:
		{
			if (execL839(var0, var1)) {
				ret = true;
				break L837;
			}
		}
		return ret;
	}
	public boolean execL839(Object var0, Object var1) {
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
L839:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL835(var0,var1,var4)) {
								ret = true;
								break L839;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL833(Object var0) {
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
L833:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var2).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(((Atom)var4) == ((Atom)var3))) {
										if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
											link = ((Atom)var3).getArg(1);
											if (!(link.getPos() != 0)) {
												var5 = link.getAtom();
												if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f19).equals(((Atom)var6).getFunctor()))) {
															if (execL822(var0,var1,var6,var5,var3,var2,var4)) {
																ret = true;
																break L833;
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
	public boolean execL822(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L822:
		{
			if (execL823(var0,var1,var2,var3,var4,var5,var6)) {
				ret = true;
				break L822;
			}
		}
		return ret;
	}
	public boolean execL823(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
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
L823:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			func = f19;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var6), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 1,
				((Atom)var2), 1 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 0,
				((Atom)var4), 0 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L823;
		}
		return ret;
	}
	public boolean execL824(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L824:
		{
			if (execL826(var0, var1)) {
				ret = true;
				break L824;
			}
			if (execL828(var0, var1)) {
				ret = true;
				break L824;
			}
		}
		return ret;
	}
	public boolean execL828(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L828:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										if (!(!(f13).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var8).getArg(1);
											if (!(link.getPos() != 2)) {
												var14 = link.getAtom();
												if (!(!(f1).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(1);
													if (!(link.getPos() != 0)) {
														var18 = link.getAtom();
														if (!(!(f2).equals(((Atom)var18).getFunctor()))) {
															link = ((Atom)var18).getArg(0);
															var19 = link;
															if (execL822(var0,var12,var1,var18,var14,var8,var4)) {
																ret = true;
																break L828;
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
	public boolean execL826(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L826:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var3).getArg(2);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(((Atom)var11) == ((Atom)var7))) {
											if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
												link = ((Atom)var11).getArg(0);
												var12 = link;
												link = ((Atom)var11).getArg(1);
												var13 = link;
												link = ((Atom)var11).getArg(2);
												var14 = link;
												link = ((Atom)var7).getArg(1);
												if (!(link.getPos() != 0)) {
													var15 = link.getAtom();
													if (!(!(f2).equals(((Atom)var15).getFunctor()))) {
														link = ((Atom)var15).getArg(0);
														var16 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f19).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL822(var0,var1,var17,var15,var7,var3,var11)) {
																	ret = true;
																	break L826;
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
		return ret;
	}
	public boolean execL820(Object var0) {
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
L820:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var2).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(((Atom)var4) == ((Atom)var3))) {
										if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
											link = ((Atom)var3).getArg(1);
											if (!(link.getPos() != 0)) {
												var5 = link.getAtom();
												if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f19).equals(((Atom)var6).getFunctor()))) {
															if (execL809(var0,var1,var6,var5,var3,var2,var4)) {
																ret = true;
																break L820;
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
	public boolean execL809(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L809:
		{
			if (execL810(var0,var1,var2,var3,var4,var5,var6)) {
				ret = true;
				break L809;
			}
		}
		return ret;
	}
	public boolean execL810(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
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
L810:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			func = f19;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var6), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var8), 1 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 0,
				((Atom)var4), 0 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 2,
				((Atom)var2), 1 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L810;
		}
		return ret;
	}
	public boolean execL811(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L811:
		{
			if (execL813(var0, var1)) {
				ret = true;
				break L811;
			}
			if (execL815(var0, var1)) {
				ret = true;
				break L811;
			}
		}
		return ret;
	}
	public boolean execL815(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L815:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										if (!(!(f15).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var8).getArg(1);
											if (!(link.getPos() != 2)) {
												var14 = link.getAtom();
												if (!(!(f1).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													link = ((Atom)var14).getArg(1);
													var16 = link;
													link = ((Atom)var14).getArg(2);
													var17 = link;
													link = ((Atom)var14).getArg(1);
													if (!(link.getPos() != 0)) {
														var18 = link.getAtom();
														if (!(!(f2).equals(((Atom)var18).getFunctor()))) {
															link = ((Atom)var18).getArg(0);
															var19 = link;
															if (execL809(var0,var12,var1,var18,var14,var8,var4)) {
																ret = true;
																break L815;
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
	public boolean execL813(Object var0, Object var1) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L813:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var3).getArg(2);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(((Atom)var11) == ((Atom)var7))) {
											if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
												link = ((Atom)var11).getArg(0);
												var12 = link;
												link = ((Atom)var11).getArg(1);
												var13 = link;
												link = ((Atom)var11).getArg(2);
												var14 = link;
												link = ((Atom)var7).getArg(1);
												if (!(link.getPos() != 0)) {
													var15 = link.getAtom();
													if (!(!(f2).equals(((Atom)var15).getFunctor()))) {
														link = ((Atom)var15).getArg(0);
														var16 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f19).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL809(var0,var1,var17,var15,var7,var3,var11)) {
																	ret = true;
																	break L813;
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
		return ret;
	}
	public boolean execL807(Object var0) {
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
L807:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL802(var0,var1)) {
					ret = true;
					break L807;
				}
			}
		}
		return ret;
	}
	public boolean execL802(Object var0, Object var1) {
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
L802:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			func = ((Atom)var2).getFunctor();
			if (!(func.getArity() != 1)) {
				if (execL803(var0,var1,var2)) {
					ret = true;
					break L802;
				}
			}
		}
		return ret;
	}
	public boolean execL803(Object var0, Object var1, Object var2) {
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
L803:
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
			func = f18;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var5), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var1), 2 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L803;
		}
		return ret;
	}
	public boolean execL804(Object var0, Object var1) {
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
L804:
		{
			if (execL806(var0, var1)) {
				ret = true;
				break L804;
			}
		}
		return ret;
	}
	public boolean execL806(Object var0, Object var1) {
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
L806:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL802(var0,var1)) {
						ret = true;
						break L806;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL800(Object var0) {
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
L800:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f22).equals(((Atom)var2).getFunctor()))) {
						if (execL793(var0,var1,var2)) {
							ret = true;
							break L800;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL793(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L793:
		{
			if (execL794(var0,var1,var2)) {
				ret = true;
				break L793;
			}
		}
		return ret;
	}
	public boolean execL794(Object var0, Object var1, Object var2) {
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
L794:
		{
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
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f22;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var1), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var6), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var5), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var6), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 2,
				((Atom)var2), 1 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
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
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L794;
		}
		return ret;
	}
	public boolean execL795(Object var0, Object var1) {
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
L795:
		{
			if (execL797(var0, var1)) {
				ret = true;
				break L795;
			}
			if (execL799(var0, var1)) {
				ret = true;
				break L795;
			}
		}
		return ret;
	}
	public boolean execL799(Object var0, Object var1) {
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
L799:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f21).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL793(var0,var4,var1)) {
								ret = true;
								break L799;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL797(Object var0, Object var1) {
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
L797:
		{
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f22).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL793(var0,var1,var5)) {
								ret = true;
								break L797;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL791(Object var0) {
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
L791:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(((Atom)var2) != ((Atom)var1))) {
						link = ((Atom)var1).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f22).equals(((Atom)var3).getFunctor()))) {
								if (execL784(var0,var1,var3)) {
									ret = true;
									break L791;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL784(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L784:
		{
			if (execL785(var0,var1,var2)) {
				ret = true;
				break L784;
			}
		}
		return ret;
	}
	public boolean execL785(Object var0, Object var1, Object var2) {
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
L785:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 1 );
			ret = true;
			break L785;
		}
		return ret;
	}
	public boolean execL786(Object var0, Object var1) {
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
L786:
		{
			if (execL788(var0, var1)) {
				ret = true;
				break L786;
			}
			if (execL790(var0, var1)) {
				ret = true;
				break L786;
			}
		}
		return ret;
	}
	public boolean execL790(Object var0, Object var1) {
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
L790:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f21).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(((Atom)var8) != ((Atom)var4))) {
									if (execL784(var0,var4,var1)) {
										ret = true;
										break L790;
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
	public boolean execL788(Object var0, Object var1) {
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
L788:
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
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(((Atom)var5) != ((Atom)var1))) {
							link = ((Atom)var1).getArg(2);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(f22).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									if (execL784(var0,var1,var6)) {
										ret = true;
										break L788;
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
	public boolean execL782(Object var0) {
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
L782:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL776(var0,var1,var2)) {
							ret = true;
							break L782;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL776(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L776:
		{
			if (execL777(var0,var1,var2)) {
				ret = true;
				break L776;
			}
		}
		return ret;
	}
	public boolean execL777(Object var0, Object var1, Object var2) {
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
L777:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f6;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 1 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var1), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var2), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 2 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L777;
		}
		return ret;
	}
	public boolean execL778(Object var0, Object var1) {
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
L778:
		{
			if (execL780(var0, var1)) {
				ret = true;
				break L778;
			}
		}
		return ret;
	}
	public boolean execL780(Object var0, Object var1) {
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
L780:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL776(var0,var1,var5)) {
								ret = true;
								break L780;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL774(Object var0) {
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
L774:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL768(var0,var1,var2)) {
							ret = true;
							break L774;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL768(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L768:
		{
			if (execL769(var0,var1,var2)) {
				ret = true;
				break L768;
			}
		}
		return ret;
	}
	public boolean execL769(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L769:
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
				((Atom)var1), 2,
				((Atom)var1), 1 );
			ret = true;
			break L769;
		}
		return ret;
	}
	public boolean execL770(Object var0, Object var1) {
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
L770:
		{
			if (execL772(var0, var1)) {
				ret = true;
				break L770;
			}
		}
		return ret;
	}
	public boolean execL772(Object var0, Object var1) {
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
L772:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL768(var0,var1,var5)) {
								ret = true;
								break L772;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL766(Object var0) {
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
L766:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL760(var0,var1,var2)) {
							ret = true;
							break L766;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL760(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L760:
		{
			if (execL761(var0,var1,var2)) {
				ret = true;
				break L760;
			}
		}
		return ret;
	}
	public boolean execL761(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L761:
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
				((Atom)var1), 2,
				((Atom)var2), 1 );
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 1,
				((Atom)var2), 0 );
			ret = true;
			break L761;
		}
		return ret;
	}
	public boolean execL762(Object var0, Object var1) {
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
L762:
		{
			if (execL764(var0, var1)) {
				ret = true;
				break L762;
			}
		}
		return ret;
	}
	public boolean execL764(Object var0, Object var1) {
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
L764:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL760(var0,var1,var5)) {
								ret = true;
								break L764;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL758(Object var0) {
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
L758:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL752(var0,var1,var2)) {
							ret = true;
							break L758;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL752(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L752:
		{
			if (execL753(var0,var1,var2)) {
				ret = true;
				break L752;
			}
		}
		return ret;
	}
	public boolean execL753(Object var0, Object var1, Object var2) {
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
L753:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 2 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L753;
		}
		return ret;
	}
	public boolean execL754(Object var0, Object var1) {
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
L754:
		{
			if (execL756(var0, var1)) {
				ret = true;
				break L754;
			}
		}
		return ret;
	}
	public boolean execL756(Object var0, Object var1) {
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
L756:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL752(var0,var1,var5)) {
								ret = true;
								break L756;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL750(Object var0) {
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
L750:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL744(var0,var1,var2)) {
							ret = true;
							break L750;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL744(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L744:
		{
			if (execL745(var0,var1,var2)) {
				ret = true;
				break L744;
			}
		}
		return ret;
	}
	public boolean execL745(Object var0, Object var1, Object var2) {
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
L745:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var2), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var4), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 2 );
			ret = true;
			break L745;
		}
		return ret;
	}
	public boolean execL746(Object var0, Object var1) {
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
L746:
		{
			if (execL748(var0, var1)) {
				ret = true;
				break L746;
			}
		}
		return ret;
	}
	public boolean execL748(Object var0, Object var1) {
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
L748:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL744(var0,var1,var5)) {
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
	public boolean execL742(Object var0) {
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
L742:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL736(var0,var1,var2)) {
							ret = true;
							break L742;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL736(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L736:
		{
			if (execL737(var0,var1,var2)) {
				ret = true;
				break L736;
			}
		}
		return ret;
	}
	public boolean execL737(Object var0, Object var1, Object var2) {
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
L737:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 2,
				((Atom)var1), 2 );
			ret = true;
			break L737;
		}
		return ret;
	}
	public boolean execL738(Object var0, Object var1) {
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
			if (execL740(var0, var1)) {
				ret = true;
				break L738;
			}
		}
		return ret;
	}
	public boolean execL740(Object var0, Object var1) {
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
L740:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL736(var0,var1,var5)) {
								ret = true;
								break L740;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL734(Object var0) {
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
L734:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL728(var0,var1,var2)) {
							ret = true;
							break L734;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL728(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
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
			if (execL729(var0,var1,var2)) {
				ret = true;
				break L728;
			}
		}
		return ret;
	}
	public boolean execL729(Object var0, Object var1, Object var2) {
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
L729:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 0 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 1,
				((Atom)var2), 1 );
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 2,
				((Atom)var1), 2 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L729;
		}
		return ret;
	}
	public boolean execL730(Object var0, Object var1) {
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
L730:
		{
			if (execL732(var0, var1)) {
				ret = true;
				break L730;
			}
		}
		return ret;
	}
	public boolean execL732(Object var0, Object var1) {
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
L732:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL728(var0,var1,var5)) {
								ret = true;
								break L732;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL726(Object var0) {
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
L726:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL720(var0,var1,var2)) {
							ret = true;
							break L726;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL720(Object var0, Object var1, Object var2) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L720:
		{
			if (execL721(var0,var1,var2)) {
				ret = true;
				break L720;
			}
		}
		return ret;
	}
	public boolean execL721(Object var0, Object var1, Object var2) {
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
L721:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f15;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var1), 2 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 1 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L721;
		}
		return ret;
	}
	public boolean execL722(Object var0, Object var1) {
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
L722:
		{
			if (execL724(var0, var1)) {
				ret = true;
				break L722;
			}
		}
		return ret;
	}
	public boolean execL724(Object var0, Object var1) {
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
L724:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL720(var0,var1,var5)) {
								ret = true;
								break L724;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL718(Object var0) {
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
L718:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL713(var0,var1)) {
					ret = true;
					break L718;
				}
			}
		}
		return ret;
	}
	public boolean execL713(Object var0, Object var1) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
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
			if (execL714(var0,var1)) {
				ret = true;
				break L713;
			}
		}
		return ret;
	}
	public boolean execL714(Object var0, Object var1) {
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
L714:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f2;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			ret = true;
			break L714;
		}
		return ret;
	}
	public boolean execL715(Object var0, Object var1) {
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
L715:
		{
			if (execL717(var0, var1)) {
				ret = true;
				break L715;
			}
		}
		return ret;
	}
	public boolean execL717(Object var0, Object var1) {
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
L717:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL713(var0,var1)) {
						ret = true;
						break L717;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f11 = new Functor("int", 2, null);
	private static final Functor f25 = new Functor("is_empty", 3, null);
	private static final Functor f12 = new Functor("nil", 1, null);
	private static final Functor f26 = new Functor("new", 1, "list");
	private static final Functor f9 = new Functor("cons", 3, null);
	private static final Functor f5 = new Functor("-", 3, null);
	private static final Functor f18 = new Functor("map", 3, "list");
	private static final Functor f3 = new Functor("choose_k", 3, null);
	private static final Functor f1 = new Functor(".", 3, null);
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f17 = new Functor("fold", 4, "list");
	private static final Functor f21 = new Functor("new", 3, "queue");
	private static final Functor f4 = new IntegerFunctor(1);
	private static final Functor f15 = new Functor("true", 1, null);
	private static final Functor f20 = new Functor("grep", 3, "list");
	private static final Functor f8 = new Functor("choose_k", 3, "list");
	private static final Functor f0 = new Functor("dist", 3, null);
	private static final Functor f10 = new Functor("flatten", 3, "list");
	private static final Functor f13 = new Functor("false", 1, null);
	private static final Functor f23 = new Functor("shift", 3, null);
	private static final Functor f6 = new Functor("append", 3, "list");
	private static final Functor f19 = new Functor("grep_s0", 2, null);
	private static final Functor f14 = new Functor("unfold_s0", 7, "list");
	private static final Functor f22 = new Functor("of_queue", 2, "list");
	private static final Functor f24 = new Functor("unshift", 3, null);
	private static final Functor f16 = new Functor("unfold", 6, "list");
	private static final Functor f7 = new IntegerFunctor(0);
}

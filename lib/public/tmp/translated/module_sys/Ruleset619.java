package translated.module_sys;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":sys" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@sys" + id;
	}
	private String encodedRuleset = 
"(sys.dump :- [:/*inline*/\tme.setName(\"nil\");\tSystem.out.println(Dumper.dump(mem));\t:]), (sys.trace(on) :- [:/*inline*/\tEnv.fTrace=true;\tme.setName(\"nil\");\t:]), (sys.trace(off) :- [:/*inline*/\tEnv.fTrace=false;\tme.setName(\"nil\");\t:]), ('='(H, sys.argv) :- '='(H, [:/*inline*/\tutil.Util.makeList(me.getArg(0), Env.argv);\tme.remove();\t:])), ('='(H, sys.exec(Cmd, Argv)) :- unary(Cmd) | '='(H, [:/*inline*/\ttry {\t\tAtom p = mem.newAtom(new ObjectFunctor(\t\t\tRuntime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), \"str\") )));//\tSystem.out.println(p);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tmem.relink(p, 0, me, 2);\t\tme.remove();\t} catch (Exception e) {\t\te.printStackTrace();\t}\t:](Cmd, Argv))), (sys.perpetual(on) :- [:/*inline*/\tmem.makePerpetual();\tme.setName(\"nil\");\t:]), (sys.perpetual(off) :- [:/*inline*/\tmem.perpetual = false;\tme.setName(\"nil\");\t:]), ('='(H, sys.atomCount(Name, Arity)) :- unary(Name), int(Arity) | '='(H, [:/*inline*/\ttry {\t\tint count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));\t\tAtom p = mem.newAtom(new IntegerFunctor(count));\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tmem.relink(p, 0, me, 2);\t\tme.remove();\t} catch (Exception e) {\t\te.printStackTrace();\t}\t:](Name, Arity))), ('='(R, sys.usage(_S, '[]')) :- string(_S) | '='(R, nil), io.use, '='(r, print(_S))), ('='(R, sys.usage(_S, '.'(H, T))) :- string(_S) | '='(R, '.'(H, T)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1766(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "dump");
			return true;
		}
		if (execL1775(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "on");
			return true;
		}
		if (execL1786(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "off");
			return true;
		}
		if (execL1797(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "argv");
			return true;
		}
		if (execL1806(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "exec");
			return true;
		}
		if (execL1815(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "on");
			return true;
		}
		if (execL1826(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "off");
			return true;
		}
		if (execL1837(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "atomCount");
			return true;
		}
		if (execL1846(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "usage");
			return true;
		}
		if (execL1856(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "usage");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1769(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "dump");
			return true;
		}
		if (execL1780(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "on");
			return true;
		}
		if (execL1791(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "off");
			return true;
		}
		if (execL1800(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "argv");
			return true;
		}
		if (execL1809(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "exec");
			return true;
		}
		if (execL1820(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "on");
			return true;
		}
		if (execL1831(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "off");
			return true;
		}
		if (execL1840(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "atomCount");
			return true;
		}
		if (execL1850(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "usage");
			return true;
		}
		if (execL1860(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "usage");
			return true;
		}
		return result;
	}
	public boolean execL1860(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1860:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
							if (execL1855(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1860;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1855(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1855:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
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
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var7 );
			ret = true;
			break L1855;
		}
		return ret;
	}
	public boolean execL1856(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1856:
		{
			if (execL1858(var0, var1, nondeterministic)) {
				ret = true;
				break L1856;
			}
		}
		return ret;
	}
	public boolean execL1858(Object var0, Object var1, boolean nondeterministic) {
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
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1858:
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
						link = ((Atom)var1).getArg(0);
						var9 = link.getAtom();
						if (((Atom)var9).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var9).getFunctor()).getObject() instanceof String) {
							if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								if (execL1855(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L1858;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1850(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1850:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
							if (execL1845(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1850;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1845(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1845:
		{
			link = ((Atom)var1).getArg(2);
			var9 = link;
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
			func = f3;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var9 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var4), 0 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
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
			break L1845;
		}
		return ret;
	}
	public boolean execL1846(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1846:
		{
			if (execL1848(var0, var1, nondeterministic)) {
				ret = true;
				break L1846;
			}
		}
		return ret;
	}
	public boolean execL1848(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1848:
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
						link = ((Atom)var1).getArg(0);
						var7 = link.getAtom();
						if (((Atom)var7).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var7).getFunctor()).getObject() instanceof String) {
							if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								if (execL1845(var0,var1,var5,var7,nondeterministic)) {
									ret = true;
									break L1848;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1840(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1840:
		{
			func = f7;
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
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL1836(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1840;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1836(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1836:
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
			func = f8;
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
			SomeInlineCodesys.run((Atom)var6, 7);
			ret = true;
			break L1836;
		}
		return ret;
	}
	public boolean execL1837(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1837:
		{
			if (execL1839(var0, var1, nondeterministic)) {
				ret = true;
				break L1837;
			}
		}
		return ret;
	}
	public boolean execL1839(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1839:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					func = ((Atom)var6).getFunctor();
					if (!(func.getArity() != 1)) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL1836(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1839;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1831(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1831:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f10).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "off", "L1825",var0,var1,var2});
						} else if (execL1825(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1831;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1825(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1825:
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
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 6);
			ret = true;
			break L1825;
		}
		return ret;
	}
	public boolean execL1826(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1826:
		{
			if (execL1828(var0, var1, nondeterministic)) {
				ret = true;
				break L1826;
			}
			if (execL1830(var0, var1, nondeterministic)) {
				ret = true;
				break L1826;
			}
		}
		return ret;
	}
	public boolean execL1830(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1830:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f9).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L1825",var0,var3,var1});
							} else if (execL1825(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1830;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1828(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1828:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f10).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L1825",var0,var1,var3});
							} else if (execL1825(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1828;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1820(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1820:
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
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "on", "L1814",var0,var1,var2});
						} else if (execL1814(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1820;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1814(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1814:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f13;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 5);
			ret = true;
			break L1814;
		}
		return ret;
	}
	public boolean execL1815(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1815:
		{
			if (execL1817(var0, var1, nondeterministic)) {
				ret = true;
				break L1815;
			}
			if (execL1819(var0, var1, nondeterministic)) {
				ret = true;
				break L1815;
			}
		}
		return ret;
	}
	public boolean execL1819(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1819:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f12).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L1814",var0,var3,var1});
							} else if (execL1814(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1819;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1817(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1817:
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
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L1814",var0,var1,var3});
							} else if (execL1814(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1817;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1809(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1809:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1805(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1809;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1805(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1805:
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
			func = f15;
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
			SomeInlineCodesys.run((Atom)var4, 4);
			ret = true;
			break L1805;
		}
		return ret;
	}
	public boolean execL1806(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1806:
		{
			if (execL1808(var0, var1, nondeterministic)) {
				ret = true;
				break L1806;
			}
		}
		return ret;
	}
	public boolean execL1808(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1808:
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
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1805(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1808;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1800(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1800:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1796(var0,var1,nondeterministic)) {
					ret = true;
					break L1800;
				}
			}
		}
		return ret;
	}
	public boolean execL1796(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1796:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f17;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodesys.run((Atom)var2, 3);
			ret = true;
			break L1796;
		}
		return ret;
	}
	public boolean execL1797(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1797:
		{
			if (execL1799(var0, var1, nondeterministic)) {
				ret = true;
				break L1797;
			}
		}
		return ret;
	}
	public boolean execL1799(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1799:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL1796(var0,var1,nondeterministic)) {
						ret = true;
						break L1799;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1791(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1791:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f18).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "off", "L1785",var0,var1,var2});
						} else if (execL1785(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1791;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1785(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1785:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f19;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 2);
			ret = true;
			break L1785;
		}
		return ret;
	}
	public boolean execL1786(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1786:
		{
			if (execL1788(var0, var1, nondeterministic)) {
				ret = true;
				break L1786;
			}
			if (execL1790(var0, var1, nondeterministic)) {
				ret = true;
				break L1786;
			}
		}
		return ret;
	}
	public boolean execL1790(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1790:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f9).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L1785",var0,var3,var1});
							} else if (execL1785(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1790;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1788(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1788:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f18).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L1785",var0,var1,var3});
							} else if (execL1785(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1788;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1780(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1780:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f18).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "on", "L1774",var0,var1,var2});
						} else if (execL1774(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1780;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1774(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1774:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f20;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 1);
			ret = true;
			break L1774;
		}
		return ret;
	}
	public boolean execL1775(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1775:
		{
			if (execL1777(var0, var1, nondeterministic)) {
				ret = true;
				break L1775;
			}
			if (execL1779(var0, var1, nondeterministic)) {
				ret = true;
				break L1775;
			}
		}
		return ret;
	}
	public boolean execL1779(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1779:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f12).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L1774",var0,var3,var1});
							} else if (execL1774(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1779;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1777(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1777:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f18).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L1774",var0,var1,var3});
							} else if (execL1774(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1777;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1769(Object var0, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1769:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "dump", "L1765",var0,var1});
				} else if (execL1765(var0,var1,nondeterministic)) {
					ret = true;
					break L1769;
				}
			}
		}
		return ret;
	}
	public boolean execL1765(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1765:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f22;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var2, 0);
			ret = true;
			break L1765;
		}
		return ret;
	}
	public boolean execL1766(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1766:
		{
			if (execL1768(var0, var1, nondeterministic)) {
				ret = true;
				break L1766;
			}
		}
		return ret;
	}
	public boolean execL1768(Object var0, Object var1, boolean nondeterministic) {
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
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1768:
		{
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "dump", "L1765",var0,var1});
					} else if (execL1765(var0,var1,nondeterministic)) {
						ret = true;
						break L1768;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f16 = new Functor("argv", 1, "sys");
	private static final Functor f18 = new Functor("trace", 1, "sys");
	private static final Functor f11 = new Functor("/*inline*/\r\n\tmem.perpetual = false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f9 = new Functor("off", 1, null);
	private static final Functor f10 = new Functor("perpetual", 1, "sys");
	private static final Functor f12 = new Functor("on", 1, null);
	private static final Functor f3 = new Functor("nil", 1, null);
	private static final Functor f0 = new Functor("usage", 3, "sys");
	private static final Functor f17 = new StringFunctor("/*inline*/\r\n\tutil.Util.makeList(me.getArg(0), Env.argv);\r\n\tme.remove();\r\n\t");
	private static final Functor f15 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom p = mem.newAtom(new ObjectFunctor(\r\n\t\t\tRuntime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), \"str\") )));\r\n//\tSystem.out.println(p);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tint count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));\r\n\t\tAtom p = mem.newAtom(new IntegerFunctor(count));\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f14 = new Functor("exec", 3, "sys");
	private static final Functor f1 = new Functor(".", 3, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n\tEnv.fTrace=false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f4 = new Functor("use", 0, "io");
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f21 = new Functor("dump", 0, "sys");
	private static final Functor f7 = new Functor("atomCount", 3, "sys");
	private static final Functor f20 = new Functor("/*inline*/\r\n\tEnv.fTrace=true;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f6 = new Functor("print", 2, null);
	private static final Functor f22 = new Functor("/*inline*/\r\n\tme.setName(\"nil\");\r\n\tSystem.out.println(Dumper.dump(mem));\r\n\t", 0, null);
	private static final Functor f5 = new Functor("r", 1, null);
	private static final Functor f13 = new Functor("/*inline*/\r\n\tmem.makePerpetual();\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
}

package translated.module_sys;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset629 extends Ruleset {
	private static final Ruleset629 theInstance = new Ruleset629();
	private Ruleset629() {}
	public static Ruleset629 getInstance() {
		return theInstance;
	}
	private int id = 629;
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
"(sys.dump :- [:/*inline*/\tme.setName(\"nil\");\tSystem.out.println(Dumper.dump(mem));\t:]), (sys.trace(on) :- [:/*inline*/\tEnv.fTrace=true;\tme.setName(\"nil\");\t:]), (sys.trace(off) :- [:/*inline*/\tEnv.fTrace=false;\tme.setName(\"nil\");\t:]), ('='(H, sys.argv) :- '='(H, [:/*inline*/\tutil.Util.makeList(me.getArg(0), Env.argv);\tme.remove();\t:])), ('='(H, sys.exec(Cmd, Argv)) :- unary(Cmd) | '='(H, [:/*inline*/\ttry {\t\tAtom p = mem.newAtom(new ObjectFunctor(\t\t\tRuntime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), \"str\") )));//\tSystem.out.println(p);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tmem.relink(p, 0, me, 2);\t\tme.remove();\t} catch (Exception e) {\t\te.printStackTrace();\t}\t:](Cmd, Argv))), (sys.perpetual(on) :- [:/*inline*/\tmem.makePerpetual();\tme.setName(\"nil\");\t:]), (sys.perpetual(off) :- [:/*inline*/\tmem.perpetual = false;\tme.setName(\"nil\");\t:]), ('='(H, sys.atomCount(Name, Arity)) :- unary(Name), int(Arity) | '='(H, [:/*inline*/\ttry {\t\tint count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));\t\tAtom p = mem.newAtom(new IntegerFunctor(count));\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tmem.relink(p, 0, me, 2);\t\tme.remove();\t} catch (Exception e) {\t\te.printStackTrace();\t}\t:](Name, Arity)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL2136(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "dump");
			return true;
		}
		if (execL2145(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "on");
			return true;
		}
		if (execL2156(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "off");
			return true;
		}
		if (execL2167(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "argv");
			return true;
		}
		if (execL2176(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "exec");
			return true;
		}
		if (execL2185(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "on");
			return true;
		}
		if (execL2196(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "off");
			return true;
		}
		if (execL2207(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@629", "atomCount");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL2139(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "dump");
			return true;
		}
		if (execL2150(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "on");
			return true;
		}
		if (execL2161(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "off");
			return true;
		}
		if (execL2170(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "argv");
			return true;
		}
		if (execL2179(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "exec");
			return true;
		}
		if (execL2190(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "on");
			return true;
		}
		if (execL2201(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "off");
			return true;
		}
		if (execL2210(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@629", "atomCount");
			return true;
		}
		return result;
	}
	public boolean execL2210(Object var0, boolean nondeterministic) {
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
L2210:
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
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL2206(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L2210;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2206(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L2206:
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
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var6, 7);
			ret = true;
			break L2206;
		}
		return ret;
	}
	public boolean execL2207(Object var0, Object var1, boolean nondeterministic) {
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
L2207:
		{
			if (execL2209(var0, var1, nondeterministic)) {
				ret = true;
				break L2207;
			}
		}
		return ret;
	}
	public boolean execL2209(Object var0, Object var1, boolean nondeterministic) {
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
L2209:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					func = ((Atom)var6).getFunctor();
					if (!(func.getArity() != 1)) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL2206(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L2209;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2201(Object var0, boolean nondeterministic) {
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
L2201:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "off", "L2195",var0,var1,var2});
						} else if (execL2195(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L2201;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2195(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2195:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f4;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 6);
			ret = true;
			break L2195;
		}
		return ret;
	}
	public boolean execL2196(Object var0, Object var1, boolean nondeterministic) {
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
L2196:
		{
			if (execL2198(var0, var1, nondeterministic)) {
				ret = true;
				break L2196;
			}
			if (execL2200(var0, var1, nondeterministic)) {
				ret = true;
				break L2196;
			}
		}
		return ret;
	}
	public boolean execL2200(Object var0, Object var1, boolean nondeterministic) {
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
L2200:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L2195",var0,var3,var1});
							} else if (execL2195(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L2200;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2198(Object var0, Object var1, boolean nondeterministic) {
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
L2198:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L2195",var0,var1,var3});
							} else if (execL2195(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L2198;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2190(Object var0, boolean nondeterministic) {
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
L2190:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "on", "L2184",var0,var1,var2});
						} else if (execL2184(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L2190;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2184(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2184:
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
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 5);
			ret = true;
			break L2184;
		}
		return ret;
	}
	public boolean execL2185(Object var0, Object var1, boolean nondeterministic) {
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
L2185:
		{
			if (execL2187(var0, var1, nondeterministic)) {
				ret = true;
				break L2185;
			}
			if (execL2189(var0, var1, nondeterministic)) {
				ret = true;
				break L2185;
			}
		}
		return ret;
	}
	public boolean execL2189(Object var0, Object var1, boolean nondeterministic) {
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
L2189:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L2184",var0,var3,var1});
							} else if (execL2184(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L2189;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2187(Object var0, Object var1, boolean nondeterministic) {
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
L2187:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L2184",var0,var1,var3});
							} else if (execL2184(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L2187;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2179(Object var0, boolean nondeterministic) {
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
L2179:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL2175(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L2179;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2175(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2175:
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
			func = f8;
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
			break L2175;
		}
		return ret;
	}
	public boolean execL2176(Object var0, Object var1, boolean nondeterministic) {
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
L2176:
		{
			if (execL2178(var0, var1, nondeterministic)) {
				ret = true;
				break L2176;
			}
		}
		return ret;
	}
	public boolean execL2178(Object var0, Object var1, boolean nondeterministic) {
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
L2178:
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
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL2175(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L2178;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2170(Object var0, boolean nondeterministic) {
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
L2170:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL2166(var0,var1,nondeterministic)) {
					ret = true;
					break L2170;
				}
			}
		}
		return ret;
	}
	public boolean execL2166(Object var0, Object var1, boolean nondeterministic) {
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
L2166:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f10;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodesys.run((Atom)var2, 3);
			ret = true;
			break L2166;
		}
		return ret;
	}
	public boolean execL2167(Object var0, Object var1, boolean nondeterministic) {
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
L2167:
		{
			if (execL2169(var0, var1, nondeterministic)) {
				ret = true;
				break L2167;
			}
		}
		return ret;
	}
	public boolean execL2169(Object var0, Object var1, boolean nondeterministic) {
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
L2169:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL2166(var0,var1,nondeterministic)) {
						ret = true;
						break L2169;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2161(Object var0, boolean nondeterministic) {
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
L2161:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "off", "L2155",var0,var1,var2});
						} else if (execL2155(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L2161;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2155(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2155:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f12;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 2);
			ret = true;
			break L2155;
		}
		return ret;
	}
	public boolean execL2156(Object var0, Object var1, boolean nondeterministic) {
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
L2156:
		{
			if (execL2158(var0, var1, nondeterministic)) {
				ret = true;
				break L2156;
			}
			if (execL2160(var0, var1, nondeterministic)) {
				ret = true;
				break L2156;
			}
		}
		return ret;
	}
	public boolean execL2160(Object var0, Object var1, boolean nondeterministic) {
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
L2160:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L2155",var0,var3,var1});
							} else if (execL2155(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L2160;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2158(Object var0, Object var1, boolean nondeterministic) {
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
L2158:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "off", "L2155",var0,var1,var3});
							} else if (execL2155(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L2158;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2150(Object var0, boolean nondeterministic) {
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
L2150:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "on", "L2144",var0,var1,var2});
						} else if (execL2144(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L2150;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2144(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2144:
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
			SomeInlineCodesys.run((Atom)var3, 1);
			ret = true;
			break L2144;
		}
		return ret;
	}
	public boolean execL2145(Object var0, Object var1, boolean nondeterministic) {
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
L2145:
		{
			if (execL2147(var0, var1, nondeterministic)) {
				ret = true;
				break L2145;
			}
			if (execL2149(var0, var1, nondeterministic)) {
				ret = true;
				break L2145;
			}
		}
		return ret;
	}
	public boolean execL2149(Object var0, Object var1, boolean nondeterministic) {
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
L2149:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L2144",var0,var3,var1});
							} else if (execL2144(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L2149;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2147(Object var0, Object var1, boolean nondeterministic) {
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
L2147:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "on", "L2144",var0,var1,var3});
							} else if (execL2144(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L2147;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2139(Object var0, boolean nondeterministic) {
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
L2139:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "dump", "L2135",var0,var1});
				} else if (execL2135(var0,var1,nondeterministic)) {
					ret = true;
					break L2139;
				}
			}
		}
		return ret;
	}
	public boolean execL2135(Object var0, Object var1, boolean nondeterministic) {
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
L2135:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f15;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var2, 0);
			ret = true;
			break L2135;
		}
		return ret;
	}
	public boolean execL2136(Object var0, Object var1, boolean nondeterministic) {
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
L2136:
		{
			if (execL2138(var0, var1, nondeterministic)) {
				ret = true;
				break L2136;
			}
		}
		return ret;
	}
	public boolean execL2138(Object var0, Object var1, boolean nondeterministic) {
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
L2138:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "dump", "L2135",var0,var1});
					} else if (execL2135(var0,var1,nondeterministic)) {
						ret = true;
						break L2138;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f9 = new Functor("argv", 1, "sys");
	private static final Functor f11 = new Functor("trace", 1, "sys");
	private static final Functor f4 = new Functor("/*inline*/\r\n\tmem.perpetual = false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f2 = new Functor("off", 1, null);
	private static final Functor f3 = new Functor("perpetual", 1, "sys");
	private static final Functor f5 = new Functor("on", 1, null);
	private static final Functor f1 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tint count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));\r\n\t\tAtom p = mem.newAtom(new IntegerFunctor(count));\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom p = mem.newAtom(new ObjectFunctor(\r\n\t\t\tRuntime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), \"str\") )));\r\n//\tSystem.out.println(p);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f10 = new StringFunctor("/*inline*/\r\n\tutil.Util.makeList(me.getArg(0), Env.argv);\r\n\tme.remove();\r\n\t");
	private static final Functor f7 = new Functor("exec", 3, "sys");
	private static final Functor f12 = new Functor("/*inline*/\r\n\tEnv.fTrace=false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f14 = new Functor("dump", 0, "sys");
	private static final Functor f0 = new Functor("atomCount", 3, "sys");
	private static final Functor f13 = new Functor("/*inline*/\r\n\tEnv.fTrace=true;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n\tme.setName(\"nil\");\r\n\tSystem.out.println(Dumper.dump(mem));\r\n\t", 0, null);
	private static final Functor f6 = new Functor("/*inline*/\r\n\tmem.makePerpetual();\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
}

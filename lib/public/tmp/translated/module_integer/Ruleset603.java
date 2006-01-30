package translated.module_integer;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset603 extends Ruleset {
	private static final Ruleset603 theInstance = new Ruleset603();
	private Ruleset603() {}
	public static Ruleset603 getInstance() {
		return theInstance;
	}
	private int id = 603;
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
	private String encodedRuleset = 
"('='(H, '+'(A, B)) :- int(A), int(B), '='('+'(A, B), C) | '='(H, C)), ('='(H, '-'(A, B)) :- int(A), int(B), '='('-'(A, B), C) | '='(H, C)), ('='(H, '*'(A, B)) :- int(A), int(B), '='('*'(A, B), C) | '='(H, C)), ('='(H, '/'(A, B)) :- int(A), int(B), '='('/'(A, B), C) | '='(H, C)), ('='(H, 'mod'(A, B)) :- int(A), int(B), '='(mod(A, B), C) | '='(H, C)), ('='(H, integer.abs(N)) :- int(N), '<'(N, 0) | '='(H, '*'('-'(1), N))), ('='(H, integer.abs(N)) :- int(N), '>='(N, 0) | '='(H, N)), ('='(H, '>'(A, B)) :- int(A), int(B), '>'(A, B) | '='(H, true)), ('='(H, '>'(A, B)) :- int(A), int(B), '=<'(A, B) | '='(H, false)), ('='(H, '<'(A, B)) :- int(A), int(B), '>='(A, B) | '='(H, false)), ('='(H, '<'(A, B)) :- int(A), int(B), '<'(A, B) | '='(H, true)), ('='(H, '>='(A, B)) :- int(A), int(B), '>='(A, B) | '='(H, true)), ('='(H, '>='(A, B)) :- int(A), int(B), '<'(A, B) | '='(H, false)), ('='(H, '=<'(A, B)) :- int(A), int(B), '>'(A, B) | '='(H, false)), ('='(H, '=<'(A, B)) :- int(A), int(B), '=<'(A, B) | '='(H, true)), ('='(H, '=='(A, B)) :- int(A), int(B), '='(A, B) | '='(H, true)), ('='(H, '=='(A, B)) :- int(A), int(B), '>'('-'(A, B), 0) | '='(H, false)), ('='(H, '=='(A, B)) :- int(A), int(B), '<'('-'(A, B), 0) | '='(H, false)), ('='(H, '!='(A, B)) :- int(A), int(B), '='(A, B) | '='(H, false)), ('='(H, '!='(A, B)) :- int(A), int(B), '>'('-'(A, B), 0) | '='(H, true)), ('='(H, '!='(A, B)) :- int(A), int(B), '<'('-'(A, B), 0) | '='(H, true)), ('='(H, '<<'(A, N)) :- int(A), int(N), '>'(N, 0) | '='(H, '<<'('*'(A, 2), '-'(N, 1)))), ('='(H, '<<'(A, 0)) :- '='(H, A)), ('='(H, '>>'(A, N)) :- int(A), int(N), '>'(N, 0) | '='(H, '>>'('/'(A, 2), '-'(N, 1)))), ('='(H, '>>'(A, 0)) :- '='(H, A)), ('='(H, '&'(A, B)) :- int(A), int(B) | '='(H, [:/*inline*/ int a =   ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();  int b =  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();  Atom result = mem.newAtom(new IntegerFunctor(a & b));  mem.relink(result, 0, me, 2);  me.nthAtom(0).remove();  me.nthAtom(1).remove();  me.remove(); :](A, B))), ('='(H, '|'(A, B)) :- int(A), int(B) | '='(H, [:/*inline*/ int a =   ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();  int b =  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();  Atom result = mem.newAtom(new IntegerFunctor(a | b));  mem.relink(result, 0, me, 2);  me.nthAtom(0).remove();  me.nthAtom(1).remove();  me.remove(); :](A, B))), ('='(H, '^'(A, B)) :- int(A), int(B) | '='(H, [:/*inline*/ int a =   ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();  int b =  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();  Atom result = mem.newAtom(new IntegerFunctor(a ^ b));  mem.relink(result, 0, me, 2);  me.nthAtom(0).remove();  me.nthAtom(1).remove();  me.remove(); :](A, B))), ('='(H, integer.power(A, N)) :- int(A), int(N) | '='(H, [:/*inline*/  int a =   ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();  int n =  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();  int r = 1;    for(int i=n;i>0;i--) r*=a;  Atom result = mem.newAtom(new IntegerFunctor(r));  mem.relink(result, 0, me, 2);  me.nthAtom(0).remove();  me.nthAtom(1).remove();  me.remove(); :](A, N))), ('='(H, integer.rnd(N)) :- int(N) | '='(H, [:/*inline*/  int n =   ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();  Random rand = new Random();  int rn = (int)(n*Math.random());  Atom result = mem.newAtom(new IntegerFunctor(rn));  mem.relink(result, 0, me, 1);  me.nthAtom(0).remove();  me.remove();  :](N))), ('='(H, integer.gcd(M, N)) :- '>'(M, N) | '='(H, integer.gcd('-'(M, N), N))), ('='(H, integer.gcd(M, N)) :- '>'(N, M) | '='(H, integer.gcd(M, '-'(N, M)))), ('='(H, integer.gcd(M, N)) :- '='(N, M) | '='(H, M)), ('='(H, integer.lcm(M, N)) :- int(M), int(N) | '='(H, '/'('*'(M, N), integer.gcd(M, N)))), ('='(H, integer.factorial(N)) :- '=<'(N, 1) | '='(H, 1)), ('='(H, integer.factorial(N)) :- '>='(N, 2) | '='(H, '*'(N, integer.factorial('-'(N, 1))))), ('='(H, integer.parse(S)) :- string(S) | '='(H, [:/*inline*/\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\tRandom rand = new Random();\tint v=0;\ttry{\t\tv = Integer.parseInt(s);\t} catch(NumberFormatException e) {\t}\tAtom result = mem.newAtom(new IntegerFunctor(v));\tmem.relink(result, 0, me, 1);\tme.nthAtom(0).remove();\tme.remove();\t:](S))), ('='(H, integer.rndList(_IMax, Count)) :- '='(Count, 0), int(_IMax) | '='(H, '[]')), ('='(H, integer.rndList(_IMax, Count)) :- '>'(Count, 0), int(_IMax) | '='(H, '.'(integer.rnd(_IMax), integer.rndList(_IMax, '-'(Count, 1)))))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL164(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "+");
			return true;
		}
		if (execL173(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "-");
			return true;
		}
		if (execL182(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "*");
			return true;
		}
		if (execL191(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "/");
			return true;
		}
		if (execL200(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "mod");
			return true;
		}
		if (execL209(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "abs");
			return true;
		}
		if (execL218(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "abs");
			return true;
		}
		if (execL227(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">");
			return true;
		}
		if (execL236(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">");
			return true;
		}
		if (execL245(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "<");
			return true;
		}
		if (execL254(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "<");
			return true;
		}
		if (execL263(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">=");
			return true;
		}
		if (execL272(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">=");
			return true;
		}
		if (execL281(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "=<");
			return true;
		}
		if (execL290(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "=<");
			return true;
		}
		if (execL299(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "==");
			return true;
		}
		if (execL308(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "==");
			return true;
		}
		if (execL317(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "==");
			return true;
		}
		if (execL326(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "!=");
			return true;
		}
		if (execL335(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "!=");
			return true;
		}
		if (execL344(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "!=");
			return true;
		}
		if (execL353(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "<<");
			return true;
		}
		if (execL362(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "<<");
			return true;
		}
		if (execL372(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">>");
			return true;
		}
		if (execL381(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">>");
			return true;
		}
		if (execL391(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "&");
			return true;
		}
		if (execL400(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "|");
			return true;
		}
		if (execL409(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "^");
			return true;
		}
		if (execL418(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "power");
			return true;
		}
		if (execL427(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "rnd");
			return true;
		}
		if (execL436(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "gcd");
			return true;
		}
		if (execL445(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "gcd");
			return true;
		}
		if (execL454(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "gcd");
			return true;
		}
		if (execL463(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "lcm");
			return true;
		}
		if (execL472(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "factorial");
			return true;
		}
		if (execL481(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "factorial");
			return true;
		}
		if (execL490(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "parse");
			return true;
		}
		if (execL499(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "rndList");
			return true;
		}
		if (execL508(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "rndList");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL167(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "+");
			return true;
		}
		if (execL176(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "-");
			return true;
		}
		if (execL185(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "*");
			return true;
		}
		if (execL194(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "/");
			return true;
		}
		if (execL203(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "mod");
			return true;
		}
		if (execL212(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "abs");
			return true;
		}
		if (execL221(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "abs");
			return true;
		}
		if (execL230(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">");
			return true;
		}
		if (execL239(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">");
			return true;
		}
		if (execL248(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "<");
			return true;
		}
		if (execL257(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "<");
			return true;
		}
		if (execL266(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">=");
			return true;
		}
		if (execL275(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">=");
			return true;
		}
		if (execL284(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "=<");
			return true;
		}
		if (execL293(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "=<");
			return true;
		}
		if (execL302(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "==");
			return true;
		}
		if (execL311(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "==");
			return true;
		}
		if (execL320(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "==");
			return true;
		}
		if (execL329(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "!=");
			return true;
		}
		if (execL338(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "!=");
			return true;
		}
		if (execL347(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "!=");
			return true;
		}
		if (execL356(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "<<");
			return true;
		}
		if (execL366(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "<<");
			return true;
		}
		if (execL375(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">>");
			return true;
		}
		if (execL385(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">>");
			return true;
		}
		if (execL394(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "&");
			return true;
		}
		if (execL403(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "|");
			return true;
		}
		if (execL412(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "^");
			return true;
		}
		if (execL421(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "power");
			return true;
		}
		if (execL430(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "rnd");
			return true;
		}
		if (execL439(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "gcd");
			return true;
		}
		if (execL448(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "gcd");
			return true;
		}
		if (execL457(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "gcd");
			return true;
		}
		if (execL466(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "lcm");
			return true;
		}
		if (execL475(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "factorial");
			return true;
		}
		if (execL484(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "factorial");
			return true;
		}
		if (execL493(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "parse");
			return true;
		}
		if (execL502(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "rndList");
			return true;
		}
		if (execL511(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "rndList");
			return true;
		}
		return result;
	}
	public boolean execL511(Object var0, boolean nondeterministic) {
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
L511:
		{
			var2 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				link = ((Atom)var1).getArg(0);
				var4 = link.getAtom();
				if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL507(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L511;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL507(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
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
L507:
		{
			link = ((Atom)var1).getArg(2);
			var13 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var12), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var7), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var1), 1 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var12), 1 );
			((Atom)var12).getMem().inheritLink(
				((Atom)var12), 2,
				(Link)var13 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
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
			break L507;
		}
		return ret;
	}
	public boolean execL508(Object var0, Object var1, boolean nondeterministic) {
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
L508:
		{
			if (execL510(var0, var1, nondeterministic)) {
				ret = true;
				break L508;
			}
		}
		return ret;
	}
	public boolean execL510(Object var0, Object var1, boolean nondeterministic) {
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
L510:
		{
			var5 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL507(var0,var1,var5,var6,var7,nondeterministic)) {
									ret = true;
									break L510;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL502(Object var0, boolean nondeterministic) {
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
L502:
		{
			var2 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var4 = link.getAtom();
				if (!(!((Atom)var2).getFunctor().equals(((Atom)var4).getFunctor()))) {
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						if (execL498(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L502;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL498(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L498:
		{
			link = ((Atom)var1).getArg(2);
			var6 = link;
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
			func = f6;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var6 );
			ret = true;
			break L498;
		}
		return ret;
	}
	public boolean execL499(Object var0, Object var1, boolean nondeterministic) {
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
L499:
		{
			if (execL501(var0, var1, nondeterministic)) {
				ret = true;
				break L499;
			}
		}
		return ret;
	}
	public boolean execL501(Object var0, Object var1, boolean nondeterministic) {
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
L501:
		{
			var5 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
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
					var7 = link.getAtom();
					if (!(!((Atom)var5).getFunctor().equals(((Atom)var7).getFunctor()))) {
						if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
							if (execL498(var0,var1,var5,var6,var7,nondeterministic)) {
								ret = true;
								break L501;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL493(Object var0, boolean nondeterministic) {
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
L493:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (execL489(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L493;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL489(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L489:
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
			func = f8;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeinteger.run((Atom)var4, 5);
			ret = true;
			break L489;
		}
		return ret;
	}
	public boolean execL490(Object var0, Object var1, boolean nondeterministic) {
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
L490:
		{
			if (execL492(var0, var1, nondeterministic)) {
				ret = true;
				break L490;
			}
		}
		return ret;
	}
	public boolean execL492(Object var0, Object var1, boolean nondeterministic) {
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
L492:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
						if (execL489(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L492;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL484(Object var0, boolean nondeterministic) {
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
L484:
		{
			var2 = new Atom(null, f9);
			func = f10;
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
						if (execL480(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L484;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL480(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L480:
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
			func = f3;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
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
			break L480;
		}
		return ret;
	}
	public boolean execL481(Object var0, Object var1, boolean nondeterministic) {
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
L481:
		{
			if (execL483(var0, var1, nondeterministic)) {
				ret = true;
				break L481;
			}
		}
		return ret;
	}
	public boolean execL483(Object var0, Object var1, boolean nondeterministic) {
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
L483:
		{
			var4 = new Atom(null, f9);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
							if (execL480(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L483;
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
L475:
		{
			var2 = new Atom(null, f3);
			func = f10;
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
L471:
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
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			ret = true;
			break L471;
		}
		return ret;
	}
	public boolean execL472(Object var0, Object var1, boolean nondeterministic) {
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
L474:
		{
			var4 = new Atom(null, f3);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
							if (execL471(var0,var1,var4,var5,nondeterministic)) {
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
	public boolean execL466(Object var0, boolean nondeterministic) {
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
L466:
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
						if (execL462(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L466;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL462(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L462:
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
			func = f11;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
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
			break L462;
		}
		return ret;
	}
	public boolean execL463(Object var0, Object var1, boolean nondeterministic) {
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
L463:
		{
			if (execL465(var0, var1, nondeterministic)) {
				ret = true;
				break L463;
			}
		}
		return ret;
	}
	public boolean execL465(Object var0, Object var1, boolean nondeterministic) {
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
L465:
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
							if (execL462(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L465;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL457(Object var0, boolean nondeterministic) {
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
L457:
		{
			var4 = new HashSet();
			func = f13;
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
								Task.states.add(new Object[] {theInstance, "gcd", "L453",var0,var1});
							} else if (execL453(var0,var1,nondeterministic)) {
								ret = true;
								break L457;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL453(Object var0, Object var1, boolean nondeterministic) {
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
L453:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link;
			link = ((Atom)var1).getArg(1);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var2));
			((AbstractMembrane)var0).removeGround(((Link)var3));
			var4 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var2));
			link = ((Atom)var1).getArg(2);
			var5 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var4),
				((Link)var5));
			ret = true;
			break L453;
		}
		return ret;
	}
	public boolean execL454(Object var0, Object var1, boolean nondeterministic) {
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
L454:
		{
			if (execL456(var0, var1, nondeterministic)) {
				ret = true;
				break L454;
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
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
			var7 = new HashSet();
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
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
									Task.states.add(new Object[] {theInstance, "gcd", "L453",var0,var1});
								} else if (execL453(var0,var1,nondeterministic)) {
									ret = true;
									break L456;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL448(Object var0, boolean nondeterministic) {
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
L448:
		{
			func = f13;
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
								Task.states.add(new Object[] {theInstance, "gcd", "L444",var0,var1,var2,var3});
							} else if (execL444(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L448;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL444(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Set insset;
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
			func = f4;
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
			break L444;
		}
		return ret;
	}
	public boolean execL445(Object var0, Object var1, boolean nondeterministic) {
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
L445:
		{
			if (execL447(var0, var1, nondeterministic)) {
				ret = true;
				break L445;
			}
		}
		return ret;
	}
	public boolean execL447(Object var0, Object var1, boolean nondeterministic) {
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
L447:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
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
									Task.states.add(new Object[] {theInstance, "gcd", "L444",var0,var1,var5,var6});
								} else if (execL444(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L447;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL439(Object var0, boolean nondeterministic) {
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
L439:
		{
			func = f13;
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
								Task.states.add(new Object[] {theInstance, "gcd", "L435",var0,var1,var2,var3});
							} else if (execL435(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L439;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL435(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Set insset;
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
			func = f4;
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
			break L435;
		}
		return ret;
	}
	public boolean execL436(Object var0, Object var1, boolean nondeterministic) {
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
L436:
		{
			if (execL438(var0, var1, nondeterministic)) {
				ret = true;
				break L436;
			}
		}
		return ret;
	}
	public boolean execL438(Object var0, Object var1, boolean nondeterministic) {
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
L438:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
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
									Task.states.add(new Object[] {theInstance, "gcd", "L435",var0,var1,var5,var6});
								} else if (execL435(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L438;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL430(Object var0, boolean nondeterministic) {
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
L430:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL426(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L430;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL426(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L426:
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
			func = f15;
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
			break L426;
		}
		return ret;
	}
	public boolean execL427(Object var0, Object var1, boolean nondeterministic) {
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
L427:
		{
			if (execL429(var0, var1, nondeterministic)) {
				ret = true;
				break L427;
			}
		}
		return ret;
	}
	public boolean execL429(Object var0, Object var1, boolean nondeterministic) {
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
L429:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL426(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L429;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL421(Object var0, boolean nondeterministic) {
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
L421:
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
						if (execL417(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L421;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL417(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L417:
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
			func = f17;
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
			break L417;
		}
		return ret;
	}
	public boolean execL418(Object var0, Object var1, boolean nondeterministic) {
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
L418:
		{
			if (execL420(var0, var1, nondeterministic)) {
				ret = true;
				break L418;
			}
		}
		return ret;
	}
	public boolean execL420(Object var0, Object var1, boolean nondeterministic) {
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
L420:
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
							if (execL417(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L420;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL412(Object var0, boolean nondeterministic) {
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
L412:
		{
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
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL408(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L412;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL408(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L408:
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
			func = f19;
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
			break L408;
		}
		return ret;
	}
	public boolean execL409(Object var0, Object var1, boolean nondeterministic) {
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
L409:
		{
			if (execL411(var0, var1, nondeterministic)) {
				ret = true;
				break L409;
			}
		}
		return ret;
	}
	public boolean execL411(Object var0, Object var1, boolean nondeterministic) {
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
L411:
		{
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
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL408(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L411;
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
L403:
		{
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
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL399(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L403;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL399(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f21;
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
L402:
		{
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
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL399(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L402;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL394(Object var0, boolean nondeterministic) {
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
L394:
		{
			func = f22;
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
						if (execL390(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L394;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL390(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L390:
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
			func = f23;
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
			break L390;
		}
		return ret;
	}
	public boolean execL391(Object var0, Object var1, boolean nondeterministic) {
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
L391:
		{
			if (execL393(var0, var1, nondeterministic)) {
				ret = true;
				break L391;
			}
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1, boolean nondeterministic) {
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
L393:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
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
							if (execL390(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L393;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL385(Object var0, boolean nondeterministic) {
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
L385:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f0).equals(((Atom)var2).getFunctor()))) {
						if (execL380(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L385;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL380(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L380:
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
			break L380;
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
L381:
		{
			if (execL383(var0, var1, nondeterministic)) {
				ret = true;
				break L381;
			}
		}
		return ret;
	}
	public boolean execL383(Object var0, Object var1, boolean nondeterministic) {
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
L383:
		{
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL380(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L383;
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
L375:
		{
			var4 = new Atom(null, f0);
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
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, ">>", "L371",var0,var1,var2,var3,var4});
							} else if (execL371(var0,var1,var2,var3,var4,nondeterministic)) {
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
	public boolean execL371(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L371:
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
			func = f9;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
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
L374:
		{
			var7 = new Atom(null, f0);
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
						x = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, ">>", "L371",var0,var1,var5,var6,var7});
								} else if (execL371(var0,var1,var5,var6,var7,nondeterministic)) {
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
	public boolean execL366(Object var0, boolean nondeterministic) {
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
L366:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f0).equals(((Atom)var2).getFunctor()))) {
						if (execL361(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L366;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL361(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L361:
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
			break L361;
		}
		return ret;
	}
	public boolean execL362(Object var0, Object var1, boolean nondeterministic) {
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
L362:
		{
			if (execL364(var0, var1, nondeterministic)) {
				ret = true;
				break L362;
			}
		}
		return ret;
	}
	public boolean execL364(Object var0, Object var1, boolean nondeterministic) {
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
L364:
		{
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL361(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L364;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL356(Object var0, boolean nondeterministic) {
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
L356:
		{
			var4 = new Atom(null, f0);
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
					x = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
					if (!(!(x > y))) {
						if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "<<", "L352",var0,var1,var2,var3,var4});
							} else if (execL352(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L356;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL352(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L352:
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
			func = f9;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
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
			break L352;
		}
		return ret;
	}
	public boolean execL353(Object var0, Object var1, boolean nondeterministic) {
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
L353:
		{
			if (execL355(var0, var1, nondeterministic)) {
				ret = true;
				break L353;
			}
		}
		return ret;
	}
	public boolean execL355(Object var0, Object var1, boolean nondeterministic) {
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
L355:
		{
			var7 = new Atom(null, f0);
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
						x = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "<<", "L352",var0,var1,var5,var6,var7});
								} else if (execL352(var0,var1,var5,var6,var7,nondeterministic)) {
									ret = true;
									break L355;
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
L347:
		{
			var5 = new Atom(null, f0);
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
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x < y))) {
							if (execL343(var0,var1,var2,var3,var4,var5,nondeterministic)) {
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
	public boolean execL343(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L343:
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
			func = f27;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
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
L346:
		{
			var8 = new Atom(null, f0);
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
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x < y))) {
								if (execL343(var0,var1,var5,var6,var7,var8,nondeterministic)) {
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
	public boolean execL338(Object var0, boolean nondeterministic) {
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
L338:
		{
			var5 = new Atom(null, f0);
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
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (execL334(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L338;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL334(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L334:
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
			func = f27;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L334;
		}
		return ret;
	}
	public boolean execL335(Object var0, Object var1, boolean nondeterministic) {
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
L335:
		{
			if (execL337(var0, var1, nondeterministic)) {
				ret = true;
				break L335;
			}
		}
		return ret;
	}
	public boolean execL337(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
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
			var8 = new Atom(null, f0);
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
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							x = ((IntegerFunctor)((Atom)var7).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var8).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (execL334(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L337;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL329(Object var0, boolean nondeterministic) {
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
L329:
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
						if (!(!((Atom)var3).getFunctor().equals(((Atom)var2).getFunctor()))) {
							if (execL325(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L329;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL325(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L325:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L325;
		}
		return ret;
	}
	public boolean execL326(Object var0, Object var1, boolean nondeterministic) {
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
L326:
		{
			if (execL328(var0, var1, nondeterministic)) {
				ret = true;
				break L326;
			}
		}
		return ret;
	}
	public boolean execL328(Object var0, Object var1, boolean nondeterministic) {
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
L328:
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
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var5).getFunctor()))) {
								if (execL325(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L328;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL320(Object var0, boolean nondeterministic) {
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
L320:
		{
			var5 = new Atom(null, f0);
			func = f29;
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
							if (execL316(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L320;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL316(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L316:
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
			func = f28;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L316;
		}
		return ret;
	}
	public boolean execL317(Object var0, Object var1, boolean nondeterministic) {
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
L317:
		{
			if (execL319(var0, var1, nondeterministic)) {
				ret = true;
				break L317;
			}
		}
		return ret;
	}
	public boolean execL319(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
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
			var8 = new Atom(null, f0);
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
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
								if (execL316(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L319;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL311(Object var0, boolean nondeterministic) {
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
L311:
		{
			var5 = new Atom(null, f0);
			func = f29;
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
							if (execL307(var0,var1,var2,var3,var4,var5,nondeterministic)) {
								ret = true;
								break L311;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL307(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L307:
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
			func = f28;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L307;
		}
		return ret;
	}
	public boolean execL308(Object var0, Object var1, boolean nondeterministic) {
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
L308:
		{
			if (execL310(var0, var1, nondeterministic)) {
				ret = true;
				break L308;
			}
		}
		return ret;
	}
	public boolean execL310(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
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
			var8 = new Atom(null, f0);
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
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
								if (execL307(var0,var1,var5,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L310;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL302(Object var0, boolean nondeterministic) {
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
L302:
		{
			func = f29;
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
							if (execL298(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L302;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL298(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L298:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L298;
		}
		return ret;
	}
	public boolean execL299(Object var0, Object var1, boolean nondeterministic) {
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
L299:
		{
			if (execL301(var0, var1, nondeterministic)) {
				ret = true;
				break L299;
			}
		}
		return ret;
	}
	public boolean execL301(Object var0, Object var1, boolean nondeterministic) {
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
L301:
		{
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
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
								if (execL298(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L301;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL293(Object var0, boolean nondeterministic) {
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
L293:
		{
			func = f30;
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
							if (execL289(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L293;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL289(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L289:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L289;
		}
		return ret;
	}
	public boolean execL290(Object var0, Object var1, boolean nondeterministic) {
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
L290:
		{
			if (execL292(var0, var1, nondeterministic)) {
				ret = true;
				break L290;
			}
		}
		return ret;
	}
	public boolean execL292(Object var0, Object var1, boolean nondeterministic) {
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
L292:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
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
								if (execL289(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L292;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL284(Object var0, boolean nondeterministic) {
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
L284:
		{
			func = f30;
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
							if (execL280(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L284;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL280(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L280:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L280;
		}
		return ret;
	}
	public boolean execL281(Object var0, Object var1, boolean nondeterministic) {
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
L281:
		{
			if (execL283(var0, var1, nondeterministic)) {
				ret = true;
				break L281;
			}
		}
		return ret;
	}
	public boolean execL283(Object var0, Object var1, boolean nondeterministic) {
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
L283:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
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
								if (execL280(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L283;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL275(Object var0, boolean nondeterministic) {
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
L275:
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
						if (!(!(x < y))) {
							if (execL271(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L275;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL271(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L271:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L271;
		}
		return ret;
	}
	public boolean execL272(Object var0, Object var1, boolean nondeterministic) {
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
L272:
		{
			if (execL274(var0, var1, nondeterministic)) {
				ret = true;
				break L272;
			}
		}
		return ret;
	}
	public boolean execL274(Object var0, Object var1, boolean nondeterministic) {
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
L274:
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
							if (!(!(x < y))) {
								if (execL271(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L274;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL266(Object var0, boolean nondeterministic) {
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
L266:
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
						if (!(!(x >= y))) {
							if (execL262(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L266;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL262(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L262:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L262;
		}
		return ret;
	}
	public boolean execL263(Object var0, Object var1, boolean nondeterministic) {
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
L263:
		{
			if (execL265(var0, var1, nondeterministic)) {
				ret = true;
				break L263;
			}
		}
		return ret;
	}
	public boolean execL265(Object var0, Object var1, boolean nondeterministic) {
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
L265:
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
							if (!(!(x >= y))) {
								if (execL262(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L265;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL257(Object var0, boolean nondeterministic) {
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
L257:
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
						if (!(!(x < y))) {
							if (execL253(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L257;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL253(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L253:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L253;
		}
		return ret;
	}
	public boolean execL254(Object var0, Object var1, boolean nondeterministic) {
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
L254:
		{
			if (execL256(var0, var1, nondeterministic)) {
				ret = true;
				break L254;
			}
		}
		return ret;
	}
	public boolean execL256(Object var0, Object var1, boolean nondeterministic) {
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
L256:
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
							if (!(!(x < y))) {
								if (execL253(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L256;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL248(Object var0, boolean nondeterministic) {
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
L248:
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
						if (!(!(x >= y))) {
							if (execL244(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L248;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL244(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L244:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L244;
		}
		return ret;
	}
	public boolean execL245(Object var0, Object var1, boolean nondeterministic) {
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
L245:
		{
			if (execL247(var0, var1, nondeterministic)) {
				ret = true;
				break L245;
			}
		}
		return ret;
	}
	public boolean execL247(Object var0, Object var1, boolean nondeterministic) {
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
L247:
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
							if (!(!(x >= y))) {
								if (execL244(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L247;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL239(Object var0, boolean nondeterministic) {
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
L239:
		{
			func = f33;
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
							if (execL235(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L239;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL235(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L235:
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
			func = f28;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L235;
		}
		return ret;
	}
	public boolean execL236(Object var0, Object var1, boolean nondeterministic) {
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
L236:
		{
			if (execL238(var0, var1, nondeterministic)) {
				ret = true;
				break L236;
			}
		}
		return ret;
	}
	public boolean execL238(Object var0, Object var1, boolean nondeterministic) {
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
L238:
		{
			if (!(!(f33).equals(((Atom)var1).getFunctor()))) {
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
								if (execL235(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L238;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL230(Object var0, boolean nondeterministic) {
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
L230:
		{
			func = f33;
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
							if (execL226(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L230;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL226(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L226:
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
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L226;
		}
		return ret;
	}
	public boolean execL227(Object var0, Object var1, boolean nondeterministic) {
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
L227:
		{
			if (execL229(var0, var1, nondeterministic)) {
				ret = true;
				break L227;
			}
		}
		return ret;
	}
	public boolean execL229(Object var0, Object var1, boolean nondeterministic) {
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
L229:
		{
			if (!(!(f33).equals(((Atom)var1).getFunctor()))) {
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
								if (execL226(var0,var1,var5,var6,nondeterministic)) {
									ret = true;
									break L229;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL221(Object var0, boolean nondeterministic) {
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
L221:
		{
			var3 = new Atom(null, f0);
			func = f34;
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
						if (execL217(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L221;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL217(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L217:
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
			break L217;
		}
		return ret;
	}
	public boolean execL218(Object var0, Object var1, boolean nondeterministic) {
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
L218:
		{
			if (execL220(var0, var1, nondeterministic)) {
				ret = true;
				break L218;
			}
		}
		return ret;
	}
	public boolean execL220(Object var0, Object var1, boolean nondeterministic) {
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
L220:
		{
			var5 = new Atom(null, f0);
			if (!(!(f34).equals(((Atom)var1).getFunctor()))) {
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
							if (execL217(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L220;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL212(Object var0, boolean nondeterministic) {
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
L212:
		{
			var3 = new Atom(null, f0);
			func = f34;
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
						if (execL208(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L212;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL208(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L208:
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
			func = f35;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
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
			break L208;
		}
		return ret;
	}
	public boolean execL209(Object var0, Object var1, boolean nondeterministic) {
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
L209:
		{
			if (execL211(var0, var1, nondeterministic)) {
				ret = true;
				break L209;
			}
		}
		return ret;
	}
	public boolean execL211(Object var0, Object var1, boolean nondeterministic) {
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
L211:
		{
			var5 = new Atom(null, f0);
			if (!(!(f34).equals(((Atom)var1).getFunctor()))) {
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
							if (execL208(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L211;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL203(Object var0, boolean nondeterministic) {
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
L203:
		{
			func = f36;
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
							if (execL199(var0,var1,var2,var3,var4,var6,nondeterministic)) {
								ret = true;
								break L203;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL199(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L199:
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
			break L199;
		}
		return ret;
	}
	public boolean execL200(Object var0, Object var1, boolean nondeterministic) {
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
L200:
		{
			if (execL202(var0, var1, nondeterministic)) {
				ret = true;
				break L200;
			}
		}
		return ret;
	}
	public boolean execL202(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L202:
		{
			if (!(!(f36).equals(((Atom)var1).getFunctor()))) {
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
								if (execL199(var0,var1,var5,var6,var7,var9,nondeterministic)) {
									ret = true;
									break L202;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL194(Object var0, boolean nondeterministic) {
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
L194:
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
						x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
						if (!(y == 0)) {
							func = new IntegerFunctor(x / y);
							var4 = new Atom(null, func);				
							var5 =  ((Atom)var4).getFunctor();
							var6 = new Atom(null, (Functor)(var5));
							if (execL190(var0,var1,var2,var3,var4,var6,nondeterministic)) {
								ret = true;
								break L194;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL190(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L190:
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
			break L190;
		}
		return ret;
	}
	public boolean execL191(Object var0, Object var1, boolean nondeterministic) {
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
L191:
		{
			if (execL193(var0, var1, nondeterministic)) {
				ret = true;
				break L191;
			}
		}
		return ret;
	}
	public boolean execL193(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L193:
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
							x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var6).getFunctor()).intValue();
							if (!(y == 0)) {
								func = new IntegerFunctor(x / y);
								var7 = new Atom(null, func);				
								var8 =  ((Atom)var7).getFunctor();
								var9 = new Atom(null, (Functor)(var8));
								if (execL190(var0,var1,var5,var6,var7,var9,nondeterministic)) {
									ret = true;
									break L193;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL185(Object var0, boolean nondeterministic) {
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
L185:
		{
			func = f11;
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
						if (execL181(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L185;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL181(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L181:
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
			break L181;
		}
		return ret;
	}
	public boolean execL182(Object var0, Object var1, boolean nondeterministic) {
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
L182:
		{
			if (execL184(var0, var1, nondeterministic)) {
				ret = true;
				break L182;
			}
		}
		return ret;
	}
	public boolean execL184(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L184:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
							if (execL181(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L184;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL176(Object var0, boolean nondeterministic) {
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
L176:
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
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						var5 =  ((Atom)var4).getFunctor();
						var6 = new Atom(null, (Functor)(var5));
						if (execL172(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L176;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL172(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L172:
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
			break L172;
		}
		return ret;
	}
	public boolean execL173(Object var0, Object var1, boolean nondeterministic) {
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
L173:
		{
			if (execL175(var0, var1, nondeterministic)) {
				ret = true;
				break L173;
			}
		}
		return ret;
	}
	public boolean execL175(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L175:
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
							var7 = new Atom(null, new IntegerFunctor(x-y));	
							var8 =  ((Atom)var7).getFunctor();
							var9 = new Atom(null, (Functor)(var8));
							if (execL172(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L175;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL167(Object var0, boolean nondeterministic) {
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
L167:
		{
			func = f37;
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
						if (execL163(var0,var1,var2,var3,var4,var6,nondeterministic)) {
							ret = true;
							break L167;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL163(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L163:
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
			break L163;
		}
		return ret;
	}
	public boolean execL164(Object var0, Object var1, boolean nondeterministic) {
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
L164:
		{
			if (execL166(var0, var1, nondeterministic)) {
				ret = true;
				break L164;
			}
		}
		return ret;
	}
	public boolean execL166(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L166:
		{
			if (!(!(f37).equals(((Atom)var1).getFunctor()))) {
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
							if (execL163(var0,var1,var5,var6,var7,var9,nondeterministic)) {
								ret = true;
								break L166;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f31 = new Functor(">=", 3, null);
	private static final Functor f29 = new Functor("==", 3, null);
	private static final Functor f4 = new Functor("-", 3, null);
	private static final Functor f21 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a | b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f23 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a & b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f36 = new Functor("mod", 3, null);
	private static final Functor f5 = new Functor(".", 3, null);
	private static final Functor f6 = new Functor("[]", 1, null);
	private static final Functor f22 = new Functor("&", 3, null);
	private static final Functor f33 = new Functor(">", 3, null);
	private static final Functor f18 = new Functor("^", 3, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int b =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  Atom result = mem.newAtom(new IntegerFunctor(a ^ b));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f3 = new IntegerFunctor(1);
	private static final Functor f2 = new Functor("rnd", 2, "integer");
	private static final Functor f34 = new Functor("abs", 2, "integer");
	private static final Functor f26 = new Functor("!=", 3, null);
	private static final Functor f27 = new Functor("true", 1, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\r\n\tRandom rand = new Random();\r\n\tint v=0;\r\n\ttry{\r\n\t\tv = Integer.parseInt(s);\r\n\t} catch(NumberFormatException e) {\r\n\t}\r\n\tAtom result = mem.newAtom(new IntegerFunctor(v));\r\n\tmem.relink(result, 0, me, 1);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t", 2, null);
	private static final Functor f25 = new Functor("<<", 3, null);
	private static final Functor f1 = new Functor("rndList", 3, "integer");
	private static final Functor f13 = new Functor("gcd", 3, "integer");
	private static final Functor f28 = new Functor("false", 1, null);
	private static final Functor f16 = new Functor("power", 3, "integer");
	private static final Functor f14 = new Functor("/", 3, null);
	private static final Functor f20 = new Functor("|", 3, null);
	private static final Functor f24 = new Functor(">>", 3, null);
	private static final Functor f35 = new IntegerFunctor(-1);
	private static final Functor f32 = new Functor("<", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n  int n = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  Random rand = new Random();\r\n  int rn = (int)(n*Math.random());\r\n  Atom result = mem.newAtom(new IntegerFunctor(rn));\r\n  mem.relink(result, 0, me, 1);\r\n  me.nthAtom(0).remove();\r\n  me.remove();\r\n  ", 2, null);
	private static final Functor f12 = new Functor("lcm", 3, "integer");
	private static final Functor f11 = new Functor("*", 3, null);
	private static final Functor f9 = new IntegerFunctor(2);
	private static final Functor f10 = new Functor("factorial", 2, "integer");
	private static final Functor f17 = new Functor("/*inline*/\r\n  int a = \r\n  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n  int n =\r\n  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n  int r = 1;  \r\n  for(int i=n;i>0;i--) r*=a;\r\n  Atom result = mem.newAtom(new IntegerFunctor(r));\r\n  mem.relink(result, 0, me, 2);\r\n  me.nthAtom(0).remove();\r\n  me.nthAtom(1).remove();\r\n  me.remove();\r\n ", 3, null);
	private static final Functor f7 = new Functor("parse", 2, "integer");
	private static final Functor f37 = new Functor("+", 3, null);
	private static final Functor f30 = new Functor("=<", 3, null);
	private static final Functor f0 = new IntegerFunctor(0);
}

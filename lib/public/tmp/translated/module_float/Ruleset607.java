package translated.module_float;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":float" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@float" + id;
	}
	private String encodedRuleset = 
"('='(H, '>'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\t\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 > a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t\t:](A, B))), ('='(H, '<'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\t\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 < a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t\t:](A, B))), ('='(H, '=<'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\t\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 <= a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t\t:](A, B))), ('='(H, '>='(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\t\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 >= a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t\t:](A, B))), ('='(H, '=='(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 == a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t\t:](A, B))), ('='(H, '!='(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(\t\tnew Functor((( a0 != a1 )?\"true\":\"false\"), 1)\t\t);\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, float.abs(A)) :- >=.'>=.'(A, 0.0) | '='(H, A)), ('='(H, float.abs(A)) :- <.'<.'(A, 0.0) | '='(H, '-.'(A))), ('='(H, '+'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '+'(A, B)) :- float(A), int(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '+'(A, B)) :- int(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '-'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '-'(A, B)) :- float(A), int(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '-'(A, B)) :- int(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '*'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '*'(A, B)) :- float(A), int(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '*'(A, B)) :- int(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '/'(A, B)) :- float(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '/'(A, B)) :- float(A), int(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\t\tdouble a1=\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B))), ('='(H, '/'(A, B)) :- int(A), float(B) | '='(H, [:/*inline*/\t\tdouble a0=\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\t\tdouble a1=\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\t\tmem.relink(result, 0, me, 2);\t\tme.nthAtom(0).remove();\t\tme.nthAtom(1).remove();\t\tme.remove();\t:](A, B)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL304(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", ">");
			return true;
		}
		if (execL313(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "<");
			return true;
		}
		if (execL322(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "=<");
			return true;
		}
		if (execL331(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", ">=");
			return true;
		}
		if (execL340(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "==");
			return true;
		}
		if (execL349(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "!=");
			return true;
		}
		if (execL358(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "abs");
			return true;
		}
		if (execL367(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "abs");
			return true;
		}
		if (execL376(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "+");
			return true;
		}
		if (execL385(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "+");
			return true;
		}
		if (execL394(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "+");
			return true;
		}
		if (execL403(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "-");
			return true;
		}
		if (execL412(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "-");
			return true;
		}
		if (execL421(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "-");
			return true;
		}
		if (execL430(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "*");
			return true;
		}
		if (execL439(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "*");
			return true;
		}
		if (execL448(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "*");
			return true;
		}
		if (execL457(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "/");
			return true;
		}
		if (execL466(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "/");
			return true;
		}
		if (execL475(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "/");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL307(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", ">");
			return true;
		}
		if (execL316(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "<");
			return true;
		}
		if (execL325(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "=<");
			return true;
		}
		if (execL334(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", ">=");
			return true;
		}
		if (execL343(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "==");
			return true;
		}
		if (execL352(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "!=");
			return true;
		}
		if (execL361(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "abs");
			return true;
		}
		if (execL370(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "abs");
			return true;
		}
		if (execL379(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "+");
			return true;
		}
		if (execL388(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "+");
			return true;
		}
		if (execL397(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "+");
			return true;
		}
		if (execL406(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "-");
			return true;
		}
		if (execL415(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "-");
			return true;
		}
		if (execL424(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "-");
			return true;
		}
		if (execL433(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "*");
			return true;
		}
		if (execL442(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "*");
			return true;
		}
		if (execL451(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "*");
			return true;
		}
		if (execL460(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "/");
			return true;
		}
		if (execL469(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "/");
			return true;
		}
		if (execL478(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "/");
			return true;
		}
		return result;
	}
	public boolean execL478(Object var0, boolean nondeterministic) {
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
L478:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL474(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L478;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL474(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L474:
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
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodefloat.run((Atom)var6, 17);
			ret = true;
			break L474;
		}
		return ret;
	}
	public boolean execL475(Object var0, Object var1, boolean nondeterministic) {
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
L475:
		{
			if (execL477(var0, var1, nondeterministic)) {
				ret = true;
				break L475;
			}
		}
		return ret;
	}
	public boolean execL477(Object var0, Object var1, boolean nondeterministic) {
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
L477:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL474(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L477;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL469(Object var0, boolean nondeterministic) {
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
L469:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL465(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L469;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL465(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L465:
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
			func = f2;
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
			SomeInlineCodefloat.run((Atom)var6, 16);
			ret = true;
			break L465;
		}
		return ret;
	}
	public boolean execL466(Object var0, Object var1, boolean nondeterministic) {
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
L466:
		{
			if (execL468(var0, var1, nondeterministic)) {
				ret = true;
				break L466;
			}
		}
		return ret;
	}
	public boolean execL468(Object var0, Object var1, boolean nondeterministic) {
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
L468:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL465(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L468;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL460(Object var0, boolean nondeterministic) {
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
L460:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL456(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L460;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L456:
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
			func = f3;
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
			SomeInlineCodefloat.run((Atom)var6, 15);
			ret = true;
			break L456;
		}
		return ret;
	}
	public boolean execL457(Object var0, Object var1, boolean nondeterministic) {
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
L459:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL456(var0,var1,var5,var6,nondeterministic)) {
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
	public boolean execL451(Object var0, boolean nondeterministic) {
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
L451:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL447(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L451;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL447(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L447:
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
			func = f5;
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
			SomeInlineCodefloat.run((Atom)var6, 14);
			ret = true;
			break L447;
		}
		return ret;
	}
	public boolean execL448(Object var0, Object var1, boolean nondeterministic) {
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
L448:
		{
			if (execL450(var0, var1, nondeterministic)) {
				ret = true;
				break L448;
			}
		}
		return ret;
	}
	public boolean execL450(Object var0, Object var1, boolean nondeterministic) {
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
L450:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL447(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L450;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL442(Object var0, boolean nondeterministic) {
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
L442:
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
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL438(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L442;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL438(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L438:
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
			func = f6;
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
			SomeInlineCodefloat.run((Atom)var6, 13);
			ret = true;
			break L438;
		}
		return ret;
	}
	public boolean execL439(Object var0, Object var1, boolean nondeterministic) {
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
L439:
		{
			if (execL441(var0, var1, nondeterministic)) {
				ret = true;
				break L439;
			}
		}
		return ret;
	}
	public boolean execL441(Object var0, Object var1, boolean nondeterministic) {
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
L441:
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
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL438(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L441;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL433(Object var0, boolean nondeterministic) {
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
L433:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL429(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L433;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL429(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L429:
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
			func = f7;
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
			SomeInlineCodefloat.run((Atom)var6, 12);
			ret = true;
			break L429;
		}
		return ret;
	}
	public boolean execL430(Object var0, Object var1, boolean nondeterministic) {
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
L430:
		{
			if (execL432(var0, var1, nondeterministic)) {
				ret = true;
				break L430;
			}
		}
		return ret;
	}
	public boolean execL432(Object var0, Object var1, boolean nondeterministic) {
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
L432:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL429(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L432;
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
L424:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL420(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L424;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL420(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f9;
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
			SomeInlineCodefloat.run((Atom)var6, 11);
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
L423:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL420(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L423;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL415(Object var0, boolean nondeterministic) {
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
L415:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL411(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L415;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL411(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L411:
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
			func = f10;
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
			SomeInlineCodefloat.run((Atom)var6, 10);
			ret = true;
			break L411;
		}
		return ret;
	}
	public boolean execL412(Object var0, Object var1, boolean nondeterministic) {
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
L412:
		{
			if (execL414(var0, var1, nondeterministic)) {
				ret = true;
				break L412;
			}
		}
		return ret;
	}
	public boolean execL414(Object var0, Object var1, boolean nondeterministic) {
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
L414:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL411(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L414;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL406(Object var0, boolean nondeterministic) {
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
L406:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL402(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L406;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL402(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L402:
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
			func = f11;
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
			SomeInlineCodefloat.run((Atom)var6, 9);
			ret = true;
			break L402;
		}
		return ret;
	}
	public boolean execL403(Object var0, Object var1, boolean nondeterministic) {
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
L403:
		{
			if (execL405(var0, var1, nondeterministic)) {
				ret = true;
				break L403;
			}
		}
		return ret;
	}
	public boolean execL405(Object var0, Object var1, boolean nondeterministic) {
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
L405:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL402(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L405;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL397(Object var0, boolean nondeterministic) {
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
L397:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL393(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L397;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L393:
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
			SomeInlineCodefloat.run((Atom)var6, 8);
			ret = true;
			break L393;
		}
		return ret;
	}
	public boolean execL394(Object var0, Object var1, boolean nondeterministic) {
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
L394:
		{
			if (execL396(var0, var1, nondeterministic)) {
				ret = true;
				break L394;
			}
		}
		return ret;
	}
	public boolean execL396(Object var0, Object var1, boolean nondeterministic) {
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
L396:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL393(var0,var1,var5,var6,nondeterministic)) {
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
	public boolean execL388(Object var0, boolean nondeterministic) {
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
L388:
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
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL384(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L388;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL384(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L384:
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
			func = f14;
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
			SomeInlineCodefloat.run((Atom)var6, 7);
			ret = true;
			break L384;
		}
		return ret;
	}
	public boolean execL385(Object var0, Object var1, boolean nondeterministic) {
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
L385:
		{
			if (execL387(var0, var1, nondeterministic)) {
				ret = true;
				break L385;
			}
		}
		return ret;
	}
	public boolean execL387(Object var0, Object var1, boolean nondeterministic) {
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
L387:
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
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL384(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L387;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL379(Object var0, boolean nondeterministic) {
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
L379:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL375(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L379;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL375(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L375:
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
			SomeInlineCodefloat.run((Atom)var6, 6);
			ret = true;
			break L375;
		}
		return ret;
	}
	public boolean execL376(Object var0, Object var1, boolean nondeterministic) {
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
L376:
		{
			if (execL378(var0, var1, nondeterministic)) {
				ret = true;
				break L376;
			}
		}
		return ret;
	}
	public boolean execL378(Object var0, Object var1, boolean nondeterministic) {
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
L378:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL375(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L378;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL370(Object var0, boolean nondeterministic) {
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
L370:
		{
			var2 = new Atom(null, f16);
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
					v = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();	
					if (!(!(u < v))) {
						if (execL366(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L370;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL366(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L366:
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
			func = f18;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L366;
		}
		return ret;
	}
	public boolean execL367(Object var0, Object var1, boolean nondeterministic) {
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
L367:
		{
			if (execL369(var0, var1, nondeterministic)) {
				ret = true;
				break L367;
			}
		}
		return ret;
	}
	public boolean execL369(Object var0, Object var1, boolean nondeterministic) {
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
L369:
		{
			var4 = new Atom(null, f16);
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var5).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var4).getFunctor()).floatValue();	
						if (!(!(u < v))) {
							if (execL366(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L369;
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
			var2 = new Atom(null, f16);
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
					v = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();	
					if (!(!(u >= v))) {
						if (execL357(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L361;
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
L357:
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
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			ret = true;
			break L357;
		}
		return ret;
	}
	public boolean execL358(Object var0, Object var1, boolean nondeterministic) {
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
L360:
		{
			var4 = new Atom(null, f16);
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var5).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var4).getFunctor()).floatValue();	
						if (!(!(u >= v))) {
							if (execL357(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L360;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL352(Object var0, boolean nondeterministic) {
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
L352:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL348(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L352;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL348(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L348:
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
			func = f20;
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
			SomeInlineCodefloat.run((Atom)var6, 5);
			ret = true;
			break L348;
		}
		return ret;
	}
	public boolean execL349(Object var0, Object var1, boolean nondeterministic) {
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
L349:
		{
			if (execL351(var0, var1, nondeterministic)) {
				ret = true;
				break L349;
			}
		}
		return ret;
	}
	public boolean execL351(Object var0, Object var1, boolean nondeterministic) {
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
L351:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL348(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L351;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL343(Object var0, boolean nondeterministic) {
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
L343:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL339(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L343;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL339(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L339:
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
			func = f22;
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
			SomeInlineCodefloat.run((Atom)var6, 4);
			ret = true;
			break L339;
		}
		return ret;
	}
	public boolean execL340(Object var0, Object var1, boolean nondeterministic) {
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
L340:
		{
			if (execL342(var0, var1, nondeterministic)) {
				ret = true;
				break L340;
			}
		}
		return ret;
	}
	public boolean execL342(Object var0, Object var1, boolean nondeterministic) {
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
L342:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL339(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L342;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL334(Object var0, boolean nondeterministic) {
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
L334:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL330(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L334;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL330(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L330:
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
			func = f24;
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
			SomeInlineCodefloat.run((Atom)var6, 3);
			ret = true;
			break L330;
		}
		return ret;
	}
	public boolean execL331(Object var0, Object var1, boolean nondeterministic) {
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
L331:
		{
			if (execL333(var0, var1, nondeterministic)) {
				ret = true;
				break L331;
			}
		}
		return ret;
	}
	public boolean execL333(Object var0, Object var1, boolean nondeterministic) {
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
L333:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL330(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L333;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL325(Object var0, boolean nondeterministic) {
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
L325:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL321(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L325;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL321(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L321:
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
			func = f26;
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
			SomeInlineCodefloat.run((Atom)var6, 2);
			ret = true;
			break L321;
		}
		return ret;
	}
	public boolean execL322(Object var0, Object var1, boolean nondeterministic) {
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
L322:
		{
			if (execL324(var0, var1, nondeterministic)) {
				ret = true;
				break L322;
			}
		}
		return ret;
	}
	public boolean execL324(Object var0, Object var1, boolean nondeterministic) {
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
L324:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL321(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L324;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL316(Object var0, boolean nondeterministic) {
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
L316:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL312(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L316;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL312(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L312:
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
			func = f28;
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
			SomeInlineCodefloat.run((Atom)var6, 1);
			ret = true;
			break L312;
		}
		return ret;
	}
	public boolean execL313(Object var0, Object var1, boolean nondeterministic) {
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
L313:
		{
			if (execL315(var0, var1, nondeterministic)) {
				ret = true;
				break L313;
			}
		}
		return ret;
	}
	public boolean execL315(Object var0, Object var1, boolean nondeterministic) {
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
L315:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL312(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L315;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL307(Object var0, boolean nondeterministic) {
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
L307:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL303(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L307;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL303(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L303:
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
			func = f30;
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
			SomeInlineCodefloat.run((Atom)var6, 0);
			ret = true;
			break L303;
		}
		return ret;
	}
	public boolean execL304(Object var0, Object var1, boolean nondeterministic) {
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
L304:
		{
			if (execL306(var0, var1, nondeterministic)) {
				ret = true;
				break L304;
			}
		}
		return ret;
	}
	public boolean execL306(Object var0, Object var1, boolean nondeterministic) {
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
L306:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL303(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L306;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f5 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f17 = new Functor("abs", 2, "float");
	private static final Functor f28 = new Functor("/*inline*/\t\t\n\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 < a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t\n\t", 3, null);
	private static final Functor f23 = new Functor(">=", 3, null);
	private static final Functor f20 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 != a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f9 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f21 = new Functor("==", 3, null);
	private static final Functor f22 = new Functor("/*inline*/\n\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 == a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t\n\t", 3, null);
	private static final Functor f8 = new Functor("-", 3, null);
	private static final Functor f24 = new Functor("/*inline*/\t\t\n\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 >= a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t\n\t", 3, null);
	private static final Functor f26 = new Functor("/*inline*/\t\t\n\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 <= a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t\n\t", 3, null);
	private static final Functor f29 = new Functor(">", 3, null);
	private static final Functor f19 = new Functor("!=", 3, null);
	private static final Functor f3 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f18 = new Functor("-.", 2, null);
	private static final Functor f11 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f2 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f0 = new Functor("/", 3, null);
	private static final Functor f27 = new Functor("<", 3, null);
	private static final Functor f30 = new Functor("/*inline*/\t\t\n\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\n\t\tAtom result = mem.newAtom(\n\t\tnew Functor((( a0 > a1 )?\"true\":\"false\"), 1)\n\t\t);\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t\n\t", 3, null);
	private static final Functor f4 = new Functor("*", 3, null);
	private static final Functor f6 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f14 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f13 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f10 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f12 = new Functor("+", 3, null);
	private static final Functor f1 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f25 = new Functor("=<", 3, null);
	private static final Functor f7 = new Functor("/*inline*/\n\t\tdouble a0=\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\n\t\tdouble a1=\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\n\t\tmem.relink(result, 0, me, 2);\n\t\tme.nthAtom(0).remove();\n\t\tme.nthAtom(1).remove();\n\t\tme.remove();\n\t", 3, null);
	private static final Functor f16 = new FloatingFunctor(0.0);
}

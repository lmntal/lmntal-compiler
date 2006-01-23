package translated.module_float;
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
		if (execL164(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">");
			return true;
		}
		if (execL173(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "<");
			return true;
		}
		if (execL182(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "=<");
			return true;
		}
		if (execL191(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", ">=");
			return true;
		}
		if (execL200(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "==");
			return true;
		}
		if (execL209(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "!=");
			return true;
		}
		if (execL218(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "abs");
			return true;
		}
		if (execL227(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "abs");
			return true;
		}
		if (execL236(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "+");
			return true;
		}
		if (execL245(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "+");
			return true;
		}
		if (execL254(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "+");
			return true;
		}
		if (execL263(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "-");
			return true;
		}
		if (execL272(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "-");
			return true;
		}
		if (execL281(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "-");
			return true;
		}
		if (execL290(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "*");
			return true;
		}
		if (execL299(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "*");
			return true;
		}
		if (execL308(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "*");
			return true;
		}
		if (execL317(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "/");
			return true;
		}
		if (execL326(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "/");
			return true;
		}
		if (execL335(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "/");
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
				Task.trace("==>", "@603", ">");
			return true;
		}
		if (execL176(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "<");
			return true;
		}
		if (execL185(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "=<");
			return true;
		}
		if (execL194(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", ">=");
			return true;
		}
		if (execL203(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "==");
			return true;
		}
		if (execL212(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "!=");
			return true;
		}
		if (execL221(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "abs");
			return true;
		}
		if (execL230(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "abs");
			return true;
		}
		if (execL239(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "+");
			return true;
		}
		if (execL248(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "+");
			return true;
		}
		if (execL257(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "+");
			return true;
		}
		if (execL266(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "-");
			return true;
		}
		if (execL275(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "-");
			return true;
		}
		if (execL284(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "-");
			return true;
		}
		if (execL293(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "*");
			return true;
		}
		if (execL302(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "*");
			return true;
		}
		if (execL311(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "*");
			return true;
		}
		if (execL320(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "/");
			return true;
		}
		if (execL329(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "/");
			return true;
		}
		if (execL338(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "/");
			return true;
		}
		return result;
	}
	public boolean execL338(Object var0, boolean nondeterministic) {
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
L338:
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
						if (execL334(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L338;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL334(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL334(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L337;
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
						if (execL325(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L329;
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
L325:
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
							if (execL325(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L328;
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL316(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L320;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL316(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL316(var0,var1,var5,var6,nondeterministic)) {
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
	public boolean execL311(Object var0, boolean nondeterministic) {
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
L311:
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
						if (execL307(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L311;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL307(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL307(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L310;
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
						if (execL298(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L302;
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
L298:
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
							if (execL298(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L301;
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
						if (execL289(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L293;
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
L289:
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
							if (execL289(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L292;
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
						if (execL280(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L284;
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
L280:
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
							if (execL280(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L283;
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
						if (execL271(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L275;
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
L271:
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
							if (execL271(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L274;
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
						if (execL262(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L266;
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
L262:
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
							if (execL262(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L265;
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
						if (execL253(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L257;
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
L253:
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
							if (execL253(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L256;
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
						if (execL244(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L248;
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
L244:
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
							if (execL244(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L247;
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
						if (execL235(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L239;
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
L235:
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
							if (execL235(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L238;
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
						if (execL226(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L230;
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
L226:
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
			break L226;
		}
		return ret;
	}
	public boolean execL227(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL226(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L229;
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
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
			break L208;
		}
		return ret;
	}
	public boolean execL209(Object var0, Object var1, boolean nondeterministic) {
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
L211:
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
							if (execL208(var0,var1,var5,var6,nondeterministic)) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL199(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L203;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL199(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL199(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L202;
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL190(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L194;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL190(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL190(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L193;
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL181(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L185;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL181(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL181(var0,var1,var5,var6,nondeterministic)) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL172(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L176;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL172(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL172(var0,var1,var5,var6,nondeterministic)) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
						if (execL163(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L167;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL163(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Set insset;
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
							if (execL163(var0,var1,var5,var6,nondeterministic)) {
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

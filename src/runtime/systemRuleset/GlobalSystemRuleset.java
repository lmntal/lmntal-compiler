//GlobalSystemRulesetGeneratorによって自動生成されたファイル

package runtime.systemRuleset;
import runtime.*;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.math.BigDecimal;

import module.*;

/**
 * コンパイル済みシステムルールセット。GlobalSystemRulesetGenerator によって生成される。
 * このファイルは直接修正しないでください。
 */
public class GlobalSystemRuleset extends Ruleset {
	private static final GlobalSystemRuleset theInstance = new GlobalSystemRuleset();
	private GlobalSystemRuleset() {
	}
	public static GlobalSystemRuleset getInstance() {
		return theInstance;
	}
	private int id = 601;
	public String getGlobalRulesetID() {
		return "$systemruleset";
	}
	public String toString() {
		return "System Ruleset Object";
	}
	private String encodedRuleset = "";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL100(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "proxy");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL102(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "proxy");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL104(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL107(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL110(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "*");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL113(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "/");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL116(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "mod");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL119(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL122(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL125(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "*.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL128(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "/.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL131(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL134(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL137(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL140(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL143(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "int");
			return true;
		}
		}
		{
		Object[] argVar = new Object[2];
		argVar[0] = mem;
		argVar[1] = atom;
		if (execL146(argVar, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "float");
			return true;
		}
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL101(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "proxy");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL103(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "proxy");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL105(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL108(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL111(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "*");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL114(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "/");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL117(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "mod");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL120(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL123(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL126(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "*.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL129(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "/.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL132(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL135(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL138(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL141(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-.");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL144(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "int");
			return true;
		}
		}
		{
		Object[] argVar = new Object[1];
		argVar[0] = mem;
		if (execL147(argVar, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "float");
			return true;
		}
		}
		return result;
	}
	public boolean execL147(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L147:
		{
			func = f[0];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
					var[3] = new Atom(null, new FloatingFunctor((double)x));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "float", "L148",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL148(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L147;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL148(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L148:
		{
			//GlobalSystemRuleset_1
			GlobalSystemRuleset_1.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_2
			GlobalSystemRuleset_2.exec(var, f);
			ret = true;
			break L148;
		}
		return ret;
	}
	public boolean execL146(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL144(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L144:
		{
			func = f[1];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
					var[3] = new Atom(null, new IntegerFunctor((int)u));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "int", "L145",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL145(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L144;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL145(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L145:
		{
			//GlobalSystemRuleset_3
			GlobalSystemRuleset_3.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_4
			GlobalSystemRuleset_4.exec(var, f);
			ret = true;
			break L145;
		}
		return ret;
	}
	public boolean execL143(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL141(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L141:
		{
			func = f[2];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
					var[3] = new Atom(null, new FloatingFunctor(-u));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "-.", "L142",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL142(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L141;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL142(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L142:
		{
			//GlobalSystemRuleset_5
			GlobalSystemRuleset_5.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_6
			GlobalSystemRuleset_6.exec(var, f);
			ret = true;
			break L142;
		}
		return ret;
	}
	public boolean execL140(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL138(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L138:
		{
			func = f[3];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
					var[3] = new Atom(null, new IntegerFunctor(-x));				
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "-", "L139",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL139(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L138;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL139(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L139:
		{
			//GlobalSystemRuleset_7
			GlobalSystemRuleset_7.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_8
			GlobalSystemRuleset_8.exec(var, f);
			ret = true;
			break L139;
		}
		return ret;
	}
	public boolean execL137(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL135(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L135:
		{
			func = f[4];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					var[4] =  ((Atom)var[2]).getFunctor();
					var[3] = new Atom(null, (Functor)(var[4]));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "+.", "L136",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL136(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L135;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL136(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L136:
		{
			//GlobalSystemRuleset_9
			GlobalSystemRuleset_9.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_10
			GlobalSystemRuleset_10.exec(var, f);
			ret = true;
			break L136;
		}
		return ret;
	}
	public boolean execL134(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL132(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L132:
		{
			func = f[5];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					var[4] =  ((Atom)var[2]).getFunctor();
					var[3] = new Atom(null, (Functor)(var[4]));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "+", "L133",new Object[] {var[0],var[1],var[2],var[3]}});
					} else if (execL133(new Object[] {var[0],var[1],var[2],var[3]},nondeterministic)) {
						ret = true;
						break L132;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL133(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[4];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L133:
		{
			//GlobalSystemRuleset_11
			GlobalSystemRuleset_11.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[3]));
			//GlobalSystemRuleset_12
			GlobalSystemRuleset_12.exec(var, f);
			ret = true;
			break L133;
		}
		return ret;
	}
	public boolean execL131(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL129(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L129:
		{
			func = f[6];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var[3]).getFunctor()).floatValue();
						func = new FloatingFunctor(u / v);
						var[4] = new Atom(null, func);				
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "/.", "L130",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL130(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L129;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL130(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L130:
		{
			//GlobalSystemRuleset_13
			GlobalSystemRuleset_13.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_14
			GlobalSystemRuleset_14.exec(var, f);
			ret = true;
			break L130;
		}
		return ret;
	}
	public boolean execL128(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL126(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L126:
		{
			func = f[7];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var[3]).getFunctor()).floatValue();
						var[4] = new Atom(null, new FloatingFunctor(u * v));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "*.", "L127",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL127(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L126;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL127(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L127:
		{
			//GlobalSystemRuleset_15
			GlobalSystemRuleset_15.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_16
			GlobalSystemRuleset_16.exec(var, f);
			ret = true;
			break L127;
		}
		return ret;
	}
	public boolean execL125(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL123(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L123:
		{
			func = f[8];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var[3]).getFunctor()).floatValue();
						var[4] = new Atom(null, new FloatingFunctor(u-v));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "-.", "L124",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL124(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L123;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL124(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L124:
		{
			//GlobalSystemRuleset_17
			GlobalSystemRuleset_17.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_18
			GlobalSystemRuleset_18.exec(var, f);
			ret = true;
			break L124;
		}
		return ret;
	}
	public boolean execL122(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL120(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L120:
		{
			func = f[9];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var[2]).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var[3]).getFunctor()).floatValue();
						var[4] = new Atom(null, new FloatingFunctor(u+v));
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "+.", "L121",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL121(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L120;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL121(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L121:
		{
			//GlobalSystemRuleset_19
			GlobalSystemRuleset_19.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_20
			GlobalSystemRuleset_20.exec(var, f);
			ret = true;
			break L121;
		}
		return ret;
	}
	public boolean execL119(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL117(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L117:
		{
			func = f[10];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var[3]).getFunctor()).intValue();
						if (!(y == 0)) {
							func = new IntegerFunctor(x % y);
							var[4] = new Atom(null, func);						
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "mod", "L118",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
							} else if (execL118(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
								ret = true;
								break L117;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL118(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L118:
		{
			//GlobalSystemRuleset_21
			GlobalSystemRuleset_21.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_22
			GlobalSystemRuleset_22.exec(var, f);
			ret = true;
			break L118;
		}
		return ret;
	}
	public boolean execL116(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL114(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L114:
		{
			func = f[11];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var[3]).getFunctor()).intValue();
						if (!(y == 0)) {
							func = new IntegerFunctor(x / y);
							var[4] = new Atom(null, func);				
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "/", "L115",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
							} else if (execL115(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
								ret = true;
								break L114;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL115(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L115:
		{
			//GlobalSystemRuleset_23
			GlobalSystemRuleset_23.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_24
			GlobalSystemRuleset_24.exec(var, f);
			ret = true;
			break L115;
		}
		return ret;
	}
	public boolean execL113(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL111(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L111:
		{
			func = f[12];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var[3]).getFunctor()).intValue();
						var[4] = new Atom(null, new IntegerFunctor(x * y));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "*", "L112",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL112(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L111;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL112(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L112:
		{
			//GlobalSystemRuleset_25
			GlobalSystemRuleset_25.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_26
			GlobalSystemRuleset_26.exec(var, f);
			ret = true;
			break L112;
		}
		return ret;
	}
	public boolean execL110(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL108(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L108:
		{
			func = f[13];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var[3]).getFunctor()).intValue();
						var[4] = new Atom(null, new IntegerFunctor(x-y));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "-", "L109",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL109(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L108;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL109(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L109:
		{
			//GlobalSystemRuleset_27
			GlobalSystemRuleset_27.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_28
			GlobalSystemRuleset_28.exec(var, f);
			ret = true;
			break L109;
		}
		return ret;
	}
	public boolean execL107(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL105(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L105:
		{
			func = f[14];
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				if (!(!(((Atom)var[2]).getFunctor() instanceof IntegerFunctor))) {
					link = ((Atom)var[1]).getArg(1);
					var[3] = link.getAtom();
					if (!(!(((Atom)var[3]).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var[2]).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var[3]).getFunctor()).intValue();
						var[4] = new Atom(null, new IntegerFunctor(x+y));
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "+", "L106",new Object[] {var[0],var[1],var[2],var[3],var[4]}});
						} else if (execL106(new Object[] {var[0],var[1],var[2],var[3],var[4]},nondeterministic)) {
							ret = true;
							break L105;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL106(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[5];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L106:
		{
			//GlobalSystemRuleset_29
			GlobalSystemRuleset_29.exec(var, f);
			((Membrane)var[0]).addAtom(((Atom)var[4]));
			//GlobalSystemRuleset_30
			GlobalSystemRuleset_30.exec(var, f);
			ret = true;
			break L106;
		}
		return ret;
	}
	public boolean execL104(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL103(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[6];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L103:
		{
			func = Functor.OUTSIDE_PROXY;
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(1);
				var[2] = link.getAtom();
				if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var[2]).getFunctor()))) {
					link = ((Atom)var[2]).getArg(0);
					var[3] = link.getAtom();
					mem = ((Atom)var[3]).getMem();
					if (mem.lock()) {
						var[4] = mem;
						link = ((Atom)var[1]).getArg(0);
						var[5] = link.getAtom();
						if (!(((Membrane)var[4]) != ((Atom)var[5]).getMem())) {
			//GlobalSystemRuleset_31
			GlobalSystemRuleset_31.exec(var, f);
							mem = ((Membrane)var[4]);
							mem.unifyAtomArgs(
								((Atom)var[3]), 1,
								((Atom)var[5]), 1 );
			//GlobalSystemRuleset_32
			GlobalSystemRuleset_32.exec(var, f);
							ret = true;
							break L103;
						}
						((Membrane)var[4]).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL102(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	public boolean execL101(Object[] vartmp, boolean nondeterministic) {
		Object var[] = new Object[6];
		for(int i = 0; i < vartmp.length; i++){;
			if(i == var.length){ break; }
			var[i] = vartmp[i];
		};
		Atom atom;
		Functor func = null;
		Link link;
		Membrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		String s1, s2;
		BigInteger bx, by;
		BigDecimal bu, bv;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		Object ejector;
		boolean ret = false;
L101:
		{
			func = Functor.OUTSIDE_PROXY;
			Iterator it1 = ((Membrane)var[0]).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var[1] = atom;
				link = ((Atom)var[1]).getArg(0);
				var[2] = link.getAtom();
				mem = ((Atom)var[2]).getMem();
				if (mem.lock()) {
					var[3] = mem;
					link = ((Atom)var[2]).getArg(1);
					var[4] = link.getAtom();
					if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var[4]).getFunctor()))) {
						link = ((Atom)var[4]).getArg(0);
						var[5] = link.getAtom();
			//GlobalSystemRuleset_33
			GlobalSystemRuleset_33.exec(var, f);
						((Membrane)var[0]).unifyAtomArgs(
							((Atom)var[1]), 1,
							((Atom)var[5]), 1 );
			//GlobalSystemRuleset_34
			GlobalSystemRuleset_34.exec(var, f);
						ret = true;
						break L101;
					}
					((Membrane)var[3]).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL100(Object[] vartmp, boolean nondeterministic) {
		return false;
	}
	private static final Functor[] f = new Functor[15];
	static{
	f[3] = new Functor("-", 2, null);
	f[8] = new Functor("-.", 3, null);
	f[1] = new Functor("int", 2, null);
	f[6] = new Functor("/.", 3, null);
	f[11] = new Functor("/", 3, null);
	f[13] = new Functor("-", 3, null);
	f[4] = new Functor("+.", 2, null);
	f[10] = new Functor("mod", 3, null);
	f[5] = new Functor("+", 2, null);
	f[12] = new Functor("*", 3, null);
	f[0] = new Functor("float", 2, null);
	f[9] = new Functor("+.", 3, null);
	f[7] = new Functor("*.", 3, null);
	f[14] = new Functor("+", 3, null);
	f[2] = new Functor("-.", 2, null);
	}
}

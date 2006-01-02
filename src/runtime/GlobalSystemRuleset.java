//GlobalSystemRulesetGeneratorによって自動生成されたファイル

package runtime;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

/**
 * コンパイル済みシステムルールセット。GlobalSystemRulesetGenerator によって生成される。
 */
public class GlobalSystemRuleset extends Ruleset {
	private static final GlobalSystemRuleset theInstance = new GlobalSystemRuleset();
	private GlobalSystemRuleset() {}
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
	// 2006.01.02 okabe
	public String encode() {
		return "";
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL100(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "proxy");
			return true;
		}
		if (execL102(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "proxy");
			return true;
		}
		if (execL104(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+");
			return true;
		}
		if (execL107(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-");
			return true;
		}
		if (execL110(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "*");
			return true;
		}
		if (execL113(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "/");
			return true;
		}
		if (execL116(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "mod");
			return true;
		}
		if (execL119(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+.");
			return true;
		}
		if (execL122(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-.");
			return true;
		}
		if (execL125(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "*.");
			return true;
		}
		if (execL128(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "/.");
			return true;
		}
		if (execL131(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+");
			return true;
		}
		if (execL134(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "+.");
			return true;
		}
		if (execL137(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-");
			return true;
		}
		if (execL140(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "-.");
			return true;
		}
		if (execL143(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "int");
			return true;
		}
		if (execL146(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@system", "float");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL101(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "proxy");
			return true;
		}
		if (execL103(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "proxy");
			return true;
		}
		if (execL105(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+");
			return true;
		}
		if (execL108(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-");
			return true;
		}
		if (execL111(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "*");
			return true;
		}
		if (execL114(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "/");
			return true;
		}
		if (execL117(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "mod");
			return true;
		}
		if (execL120(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+.");
			return true;
		}
		if (execL123(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-.");
			return true;
		}
		if (execL126(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "*.");
			return true;
		}
		if (execL129(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "/.");
			return true;
		}
		if (execL132(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+");
			return true;
		}
		if (execL135(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "+.");
			return true;
		}
		if (execL138(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-");
			return true;
		}
		if (execL141(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "-.");
			return true;
		}
		if (execL144(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "int");
			return true;
		}
		if (execL147(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@system", "float");
			return true;
		}
		return result;
	}
	public boolean execL147(Object var0, boolean nondeterministic) {
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
L147:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					var3 = new Atom(null, new FloatingFunctor((double)x));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "float", "L148",var0,var1,var2,var3});
					} else if (execL148(var0,var1,var2,var3,nondeterministic)) {
						ret = true;
						break L147;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var3));
				((Atom)var3).getMem().relinkAtomArgs(
					((Atom)var3), 0,
					((Atom)var1), 1 );
				ret = true;
				break L147;
			}
		}
		return ret;
	}
	public boolean execL148(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L148:
		{
		}
		return ret;
	}
	public boolean execL146(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL144(Object var0, boolean nondeterministic) {
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
L144:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
					var3 = new Atom(null, new IntegerFunctor((int)u));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "int", "L145",var0,var1,var2,var3});
					} else if (execL145(var0,var1,var2,var3,nondeterministic)) {
						ret = true;
						break L144;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var3));
				((Atom)var3).getMem().relinkAtomArgs(
					((Atom)var3), 0,
					((Atom)var1), 1 );
				ret = true;
				break L144;
			}
		}
		return ret;
	}
	public boolean execL145(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L145:
		{
		}
		return ret;
	}
	public boolean execL143(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL141(Object var0, boolean nondeterministic) {
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
L141:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
					var3 = new Atom(null, new FloatingFunctor(-u));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "-.", "L142",var0,var1,var2,var3});
					} else if (execL142(var0,var1,var2,var3,nondeterministic)) {
						ret = true;
						break L141;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var3));
				((Atom)var3).getMem().relinkAtomArgs(
					((Atom)var3), 0,
					((Atom)var1), 1 );
				ret = true;
				break L141;
			}
		}
		return ret;
	}
	public boolean execL142(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L142:
		{
		}
		return ret;
	}
	public boolean execL140(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL138(Object var0, boolean nondeterministic) {
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
L138:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					var3 = new Atom(null, new IntegerFunctor(-x));				
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "-", "L139",var0,var1,var2,var3});
					} else if (execL139(var0,var1,var2,var3,nondeterministic)) {
						ret = true;
						break L138;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var3));
				((Atom)var3).getMem().relinkAtomArgs(
					((Atom)var3), 0,
					((Atom)var1), 1 );
				ret = true;
				break L138;
			}
		}
		return ret;
	}
	public boolean execL139(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L139:
		{
		}
		return ret;
	}
	public boolean execL137(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL135(Object var0, boolean nondeterministic) {
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
L135:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					var4 =  ((Atom)var2).getFunctor();
					var3 = new Atom(null, (Functor)(var4));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "+.", "L136",var0,var1,var2,var3,var4});
					} else if (execL136(var0,var1,var2,var3,var4,nondeterministic)) {
						ret = true;
						break L135;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var4));
				((Atom)var4).getMem().relinkAtomArgs(
					((Atom)var4), 0,
					((Atom)var1), 1 );
				ret = true;
				break L135;
			}
		}
		return ret;
	}
	public boolean execL136(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L136:
		{
		}
		return ret;
	}
	public boolean execL134(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL132(Object var0, boolean nondeterministic) {
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
L132:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					var4 =  ((Atom)var2).getFunctor();
					var3 = new Atom(null, (Functor)(var4));
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "+", "L133",var0,var1,var2,var3,var4});
					} else if (execL133(var0,var1,var2,var3,var4,nondeterministic)) {
						ret = true;
						break L132;
					}
				}
				atom = ((Atom)var1);
				atom.dequeue();
				atom = ((Atom)var1);
				atom.getMem().removeAtom(atom);
				atom = ((Atom)var2);
				atom.getMem().removeAtom(atom);
				((AbstractMembrane)var0).addAtom(((Atom)var4));
				((Atom)var4).getMem().relinkAtomArgs(
					((Atom)var4), 0,
					((Atom)var1), 1 );
				ret = true;
				break L132;
			}
		}
		return ret;
	}
	public boolean execL133(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L133:
		{
		}
		return ret;
	}
	public boolean execL131(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL129(Object var0, boolean nondeterministic) {
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
L129:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
						func = new FloatingFunctor(u / v);
						var4 = new Atom(null, func);				
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "/.", "L130",var0,var1,var2,var3,var4});
						} else if (execL130(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L129;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL130(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L130:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L130;
		}
		return ret;
	}
	public boolean execL128(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL126(Object var0, boolean nondeterministic) {
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
L126:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
						var4 = new Atom(null, new FloatingFunctor(u * v));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "*.", "L127",var0,var1,var2,var3,var4});
						} else if (execL127(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L126;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL127(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L127:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L127;
		}
		return ret;
	}
	public boolean execL125(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL123(Object var0, boolean nondeterministic) {
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
L123:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
						var4 = new Atom(null, new FloatingFunctor(u-v));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "-.", "L124",var0,var1,var2,var3,var4});
						} else if (execL124(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L123;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL124(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L124:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L124;
		}
		return ret;
	}
	public boolean execL122(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL120(Object var0, boolean nondeterministic) {
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
L120:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
						u = ((FloatingFunctor)((Atom)var2).getFunctor()).floatValue();
						v = ((FloatingFunctor)((Atom)var3).getFunctor()).floatValue();
						var4 = new Atom(null, new FloatingFunctor(u+v));
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "+.", "L121",var0,var1,var2,var3,var4});
						} else if (execL121(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L120;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL121(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L121:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L121;
		}
		return ret;
	}
	public boolean execL119(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL117(Object var0, boolean nondeterministic) {
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
L117:
		{
			func = f10;
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
						if (!(y == 0)) {
							func = new IntegerFunctor(x % y);
							var4 = new Atom(null, func);						
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "mod", "L118",var0,var1,var2,var3,var4});
							} else if (execL118(var0,var1,var2,var3,var4,nondeterministic)) {
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
	public boolean execL118(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L118:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L118;
		}
		return ret;
	}
	public boolean execL116(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL114(Object var0, boolean nondeterministic) {
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
L114:
		{
			func = f11;
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
						if (!(y == 0)) {
							func = new IntegerFunctor(x / y);
							var4 = new Atom(null, func);				
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "/", "L115",var0,var1,var2,var3,var4});
							} else if (execL115(var0,var1,var2,var3,var4,nondeterministic)) {
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
	public boolean execL115(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L115:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L115;
		}
		return ret;
	}
	public boolean execL113(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL111(Object var0, boolean nondeterministic) {
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
L111:
		{
			func = f12;
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
						var4 = new Atom(null, new IntegerFunctor(x * y));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "*", "L112",var0,var1,var2,var3,var4});
						} else if (execL112(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L111;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL112(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L112:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L112;
		}
		return ret;
	}
	public boolean execL110(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL108(Object var0, boolean nondeterministic) {
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
L108:
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
						var4 = new Atom(null, new IntegerFunctor(x-y));	
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "-", "L109",var0,var1,var2,var3,var4});
						} else if (execL109(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L108;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL109(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L109:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L109;
		}
		return ret;
	}
	public boolean execL107(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL105(Object var0, boolean nondeterministic) {
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
L105:
		{
			func = f14;
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
						var4 = new Atom(null, new IntegerFunctor(x+y));
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "+", "L106",var0,var1,var2,var3,var4});
						} else if (execL106(var0,var1,var2,var3,var4,nondeterministic)) {
							ret = true;
							break L105;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL106(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L106:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).addAtom(((Atom)var4));
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 2 );
			ret = true;
			break L106;
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL103(Object var0, boolean nondeterministic) {
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
L103:
		{
			func = Functor.OUTSIDE_PROXY;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var2 = link.getAtom();
				if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
					link = ((Atom)var2).getArg(0);
					var3 = link.getAtom();
					mem = ((Atom)var3).getMem();
					if (mem.lock()) {
						var4 = mem;
						link = ((Atom)var1).getArg(0);
						var5 = link.getAtom();
						if (!(((AbstractMembrane)var4) != ((Atom)var5).getMem())) {
							atom = ((Atom)var1);
							atom.getMem().removeAtom(atom);
							atom = ((Atom)var2);
							atom.getMem().removeAtom(atom);
							atom = ((Atom)var3);
							atom.getMem().removeAtom(atom);
							atom = ((Atom)var5);
							atom.getMem().removeAtom(atom);
							mem = ((AbstractMembrane)var4);
							mem.unifyAtomArgs(
								((Atom)var3), 1,
								((Atom)var5), 1 );
							((AbstractMembrane)var4).activate();
							((AbstractMembrane)var4).forceUnlock();
							ret = true;
							break L103;
						}
						((AbstractMembrane)var4).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL102(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	public boolean execL101(Object var0, boolean nondeterministic) {
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
L101:
		{
			func = Functor.OUTSIDE_PROXY;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				mem = ((Atom)var2).getMem();
				if (mem.lock()) {
					var3 = mem;
					link = ((Atom)var2).getArg(1);
					var4 = link.getAtom();
					if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
						link = ((Atom)var4).getArg(0);
						var5 = link.getAtom();
						atom = ((Atom)var1);
						atom.getMem().removeAtom(atom);
						atom = ((Atom)var2);
						atom.getMem().removeAtom(atom);
						atom = ((Atom)var4);
						atom.getMem().removeAtom(atom);
						atom = ((Atom)var5);
						atom.getMem().removeAtom(atom);
						((AbstractMembrane)var0).unifyAtomArgs(
							((Atom)var1), 1,
							((Atom)var5), 1 );
						((AbstractMembrane)var3).forceUnlock();
						ret = true;
						break L101;
					}
					((AbstractMembrane)var3).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL100(Object var0, Object var1, boolean nondeterministic) {
		return false;
	}
	private static final Functor f3 = new Functor("-", 2, null);
	private static final Functor f8 = new Functor("-.", 3, null);
	private static final Functor f1 = new Functor("int", 2, null);
	private static final Functor f6 = new Functor("/.", 3, null);
	private static final Functor f11 = new Functor("/", 3, null);
	private static final Functor f13 = new Functor("-", 3, null);
	private static final Functor f4 = new Functor("+.", 2, null);
	private static final Functor f10 = new Functor("mod", 3, null);
	private static final Functor f5 = new Functor("+", 2, null);
	private static final Functor f12 = new Functor("*", 3, null);
	private static final Functor f0 = new Functor("float", 2, null);
	private static final Functor f9 = new Functor("+.", 3, null);
	private static final Functor f7 = new Functor("*.", 3, null);
	private static final Functor f14 = new Functor("+", 3, null);
	private static final Functor f2 = new Functor("-.", 2, null);
}

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
		return "0.66.20051105$systemruleset";
	}
	public String toString() {
		return "System Ruleset Object";
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL100(mem, atom)) {
			return true;
		}
		if (execL102(mem, atom)) {
			return true;
		}
		if (execL104(mem, atom)) {
			return true;
		}
		if (execL106(mem, atom)) {
			return true;
		}
		if (execL108(mem, atom)) {
			return true;
		}
		if (execL110(mem, atom)) {
			return true;
		}
		if (execL112(mem, atom)) {
			return true;
		}
		if (execL114(mem, atom)) {
			return true;
		}
		if (execL116(mem, atom)) {
			return true;
		}
		if (execL118(mem, atom)) {
			return true;
		}
		if (execL120(mem, atom)) {
			return true;
		}
		if (execL122(mem, atom)) {
			return true;
		}
		if (execL124(mem, atom)) {
			return true;
		}
		if (execL126(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL101(mem)) {
			return true;
		}
		if (execL103(mem)) {
			return true;
		}
		if (execL105(mem)) {
			return true;
		}
		if (execL107(mem)) {
			return true;
		}
		if (execL109(mem)) {
			return true;
		}
		if (execL111(mem)) {
			return true;
		}
		if (execL113(mem)) {
			return true;
		}
		if (execL115(mem)) {
			return true;
		}
		if (execL117(mem)) {
			return true;
		}
		if (execL119(mem)) {
			return true;
		}
		if (execL121(mem)) {
			return true;
		}
		if (execL123(mem)) {
			return true;
		}
		if (execL125(mem)) {
			return true;
		}
		if (execL127(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL127(Object var0) {
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
L127:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					atom = ((Atom)var1);
					atom.dequeue();
					atom = ((Atom)var1);
					atom.getMem().removeAtom(atom);
					var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
					((Atom)var2).getMem().relinkAtomArgs(
						((Atom)var2), 0,
						((Atom)var1), 1 );
					((Atom)var3).getMem().relinkAtomArgs(
						((Atom)var3), 0,
						((Atom)var1), 2 );
					ret = true;
					break L127;
				}
			}
		}
		return ret;
	}
	public boolean execL126(Object var0, Object var1) {
		return false;
	}
	public boolean execL125(Object var0) {
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
L125:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var2).getFunctor()).intValue();
					var3 = new Atom(null, new FloatingFunctor((double)x));
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
					break L125;
				}
			}
		}
		return ret;
	}
	public boolean execL124(Object var0, Object var1) {
		return false;
	}
	public boolean execL123(Object var0) {
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
L123:
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
					var3 = new Atom(null, new IntegerFunctor((int)u));
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
					break L123;
				}
			}
		}
		return ret;
	}
	public boolean execL122(Object var0, Object var1) {
		return false;
	}
	public boolean execL121(Object var0) {
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
L121:
		{
			func = f3;
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
				}
			}
		}
		return ret;
	}
	public boolean execL120(Object var0, Object var1) {
		return false;
	}
	public boolean execL119(Object var0) {
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
L119:
		{
			func = f4;
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
						break L119;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL118(Object var0, Object var1) {
		return false;
	}
	public boolean execL117(Object var0) {
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
			func = f5;
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
						break L117;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL116(Object var0, Object var1) {
		return false;
	}
	public boolean execL115(Object var0) {
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
L115:
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
						var4 = new Atom(null, new FloatingFunctor(u+v));
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
				}
			}
		}
		return ret;
	}
	public boolean execL114(Object var0, Object var1) {
		return false;
	}
	public boolean execL113(Object var0) {
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
L113:
		{
			func = f7;
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
							break L113;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL112(Object var0, Object var1) {
		return false;
	}
	public boolean execL111(Object var0) {
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
			func = f8;
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
							break L111;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL110(Object var0, Object var1) {
		return false;
	}
	public boolean execL109(Object var0) {
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
L109:
		{
			func = f9;
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
				}
			}
		}
		return ret;
	}
	public boolean execL108(Object var0, Object var1) {
		return false;
	}
	public boolean execL107(Object var0) {
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
L107:
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
						var4 = new Atom(null, new IntegerFunctor(x-y));	
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
						break L107;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL106(Object var0, Object var1) {
		return false;
	}
	public boolean execL105(Object var0) {
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
						var4 = new Atom(null, new IntegerFunctor(x+y));
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
						break L105;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1) {
		return false;
	}
	public boolean execL103(Object var0) {
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
	public boolean execL102(Object var0, Object var1) {
		return false;
	}
	public boolean execL101(Object var0) {
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
	public boolean execL100(Object var0, Object var1) {
		return false;
	}
	private static final Functor f9 = new Functor("*", 3, null);
	private static final Functor f6 = new Functor("+.", 3, null);
	private static final Functor f1 = new Functor("float", 2, null);
	private static final Functor f5 = new Functor("-.", 3, null);
	private static final Functor f2 = new Functor("int", 2, null);
	private static final Functor f3 = new Functor("/.", 3, null);
	private static final Functor f8 = new Functor("/", 3, null);
	private static final Functor f4 = new Functor("*.", 3, null);
	private static final Functor f0 = new Functor("cp", 3, null);
	private static final Functor f10 = new Functor("-", 3, null);
	private static final Functor f11 = new Functor("+", 3, null);
	private static final Functor f7 = new Functor("mod", 3, null);
}

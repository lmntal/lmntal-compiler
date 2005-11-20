package translated.module_double;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":double" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@double" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL150(mem, atom)) {
			return true;
		}
		if (execL156(mem, atom)) {
			return true;
		}
		if (execL162(mem, atom)) {
			return true;
		}
		if (execL168(mem, atom)) {
			return true;
		}
		if (execL174(mem, atom)) {
			return true;
		}
		if (execL180(mem, atom)) {
			return true;
		}
		if (execL186(mem, atom)) {
			return true;
		}
		if (execL193(mem, atom)) {
			return true;
		}
		if (execL199(mem, atom)) {
			return true;
		}
		if (execL205(mem, atom)) {
			return true;
		}
		if (execL211(mem, atom)) {
			return true;
		}
		if (execL217(mem, atom)) {
			return true;
		}
		if (execL223(mem, atom)) {
			return true;
		}
		if (execL229(mem, atom)) {
			return true;
		}
		if (execL235(mem, atom)) {
			return true;
		}
		if (execL241(mem, atom)) {
			return true;
		}
		if (execL247(mem, atom)) {
			return true;
		}
		if (execL253(mem, atom)) {
			return true;
		}
		if (execL259(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL152(mem)) {
			return true;
		}
		if (execL158(mem)) {
			return true;
		}
		if (execL164(mem)) {
			return true;
		}
		if (execL170(mem)) {
			return true;
		}
		if (execL176(mem)) {
			return true;
		}
		if (execL182(mem)) {
			return true;
		}
		if (execL189(mem)) {
			return true;
		}
		if (execL195(mem)) {
			return true;
		}
		if (execL201(mem)) {
			return true;
		}
		if (execL207(mem)) {
			return true;
		}
		if (execL213(mem)) {
			return true;
		}
		if (execL219(mem)) {
			return true;
		}
		if (execL225(mem)) {
			return true;
		}
		if (execL231(mem)) {
			return true;
		}
		if (execL237(mem)) {
			return true;
		}
		if (execL243(mem)) {
			return true;
		}
		if (execL249(mem)) {
			return true;
		}
		if (execL255(mem)) {
			return true;
		}
		if (execL261(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL261(Object var0) {
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
L261:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL257(var0,var1)) {
					ret = true;
					break L261;
				}
			}
		}
		return ret;
	}
	public boolean execL257(Object var0, Object var1) {
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
L257:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL258(var0,var1,var2,var3)) {
						ret = true;
						break L257;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL258(Object var0, Object var1, Object var2, Object var3) {
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
L258:
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
			func = f1;
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
			SomeInlineCodedouble.run((Atom)var6, 18);
			ret = true;
			break L258;
		}
		return ret;
	}
	public boolean execL259(Object var0, Object var1) {
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
L259:
		{
		}
		return ret;
	}
	public boolean execL255(Object var0) {
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
L255:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL251(var0,var1)) {
					ret = true;
					break L255;
				}
			}
		}
		return ret;
	}
	public boolean execL251(Object var0, Object var1) {
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
L251:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL252(var0,var1,var2,var3)) {
						ret = true;
						break L251;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL252(Object var0, Object var1, Object var2, Object var3) {
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
L252:
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
			func = f2;
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
			SomeInlineCodedouble.run((Atom)var6, 17);
			ret = true;
			break L252;
		}
		return ret;
	}
	public boolean execL253(Object var0, Object var1) {
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
L253:
		{
		}
		return ret;
	}
	public boolean execL249(Object var0) {
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
L249:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL245(var0,var1)) {
					ret = true;
					break L249;
				}
			}
		}
		return ret;
	}
	public boolean execL245(Object var0, Object var1) {
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
L245:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL246(var0,var1,var2,var3)) {
						ret = true;
						break L245;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL246(Object var0, Object var1, Object var2, Object var3) {
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
L246:
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
			func = f3;
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
			SomeInlineCodedouble.run((Atom)var6, 16);
			ret = true;
			break L246;
		}
		return ret;
	}
	public boolean execL247(Object var0, Object var1) {
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
L247:
		{
		}
		return ret;
	}
	public boolean execL243(Object var0) {
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
L243:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL239(var0,var1)) {
					ret = true;
					break L243;
				}
			}
		}
		return ret;
	}
	public boolean execL239(Object var0, Object var1) {
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
L239:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL240(var0,var1,var2,var3)) {
						ret = true;
						break L239;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL240(Object var0, Object var1, Object var2, Object var3) {
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
L240:
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
			func = f5;
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
			SomeInlineCodedouble.run((Atom)var6, 15);
			ret = true;
			break L240;
		}
		return ret;
	}
	public boolean execL241(Object var0, Object var1) {
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
L241:
		{
		}
		return ret;
	}
	public boolean execL237(Object var0) {
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
L237:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL233(var0,var1)) {
					ret = true;
					break L237;
				}
			}
		}
		return ret;
	}
	public boolean execL233(Object var0, Object var1) {
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
L233:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL234(var0,var1,var2,var3)) {
						ret = true;
						break L233;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL234(Object var0, Object var1, Object var2, Object var3) {
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
L234:
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
			func = f6;
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
			SomeInlineCodedouble.run((Atom)var6, 14);
			ret = true;
			break L234;
		}
		return ret;
	}
	public boolean execL235(Object var0, Object var1) {
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
L235:
		{
		}
		return ret;
	}
	public boolean execL231(Object var0) {
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
L231:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL227(var0,var1)) {
					ret = true;
					break L231;
				}
			}
		}
		return ret;
	}
	public boolean execL227(Object var0, Object var1) {
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
L227:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL228(var0,var1,var2,var3)) {
						ret = true;
						break L227;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL228(Object var0, Object var1, Object var2, Object var3) {
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
L228:
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
			func = f7;
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
			SomeInlineCodedouble.run((Atom)var6, 13);
			ret = true;
			break L228;
		}
		return ret;
	}
	public boolean execL229(Object var0, Object var1) {
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
L229:
		{
		}
		return ret;
	}
	public boolean execL225(Object var0) {
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
L225:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL221(var0,var1)) {
					ret = true;
					break L225;
				}
			}
		}
		return ret;
	}
	public boolean execL221(Object var0, Object var1) {
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
L221:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL222(var0,var1,var2,var3)) {
						ret = true;
						break L221;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL222(Object var0, Object var1, Object var2, Object var3) {
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
L222:
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
			func = f9;
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
			SomeInlineCodedouble.run((Atom)var6, 12);
			ret = true;
			break L222;
		}
		return ret;
	}
	public boolean execL223(Object var0, Object var1) {
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
L223:
		{
		}
		return ret;
	}
	public boolean execL219(Object var0) {
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
L219:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL215(var0,var1)) {
					ret = true;
					break L219;
				}
			}
		}
		return ret;
	}
	public boolean execL215(Object var0, Object var1) {
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
L215:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL216(var0,var1,var2,var3)) {
						ret = true;
						break L215;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL216(Object var0, Object var1, Object var2, Object var3) {
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
L216:
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
			func = f10;
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
			SomeInlineCodedouble.run((Atom)var6, 11);
			ret = true;
			break L216;
		}
		return ret;
	}
	public boolean execL217(Object var0, Object var1) {
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
L217:
		{
		}
		return ret;
	}
	public boolean execL213(Object var0) {
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
L213:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL209(var0,var1)) {
					ret = true;
					break L213;
				}
			}
		}
		return ret;
	}
	public boolean execL209(Object var0, Object var1) {
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
L209:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL210(var0,var1,var2,var3)) {
						ret = true;
						break L209;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL210(Object var0, Object var1, Object var2, Object var3) {
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
L210:
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
			func = f11;
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
			SomeInlineCodedouble.run((Atom)var6, 10);
			ret = true;
			break L210;
		}
		return ret;
	}
	public boolean execL211(Object var0, Object var1) {
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
L211:
		{
		}
		return ret;
	}
	public boolean execL207(Object var0) {
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
L207:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL203(var0,var1)) {
					ret = true;
					break L207;
				}
			}
		}
		return ret;
	}
	public boolean execL203(Object var0, Object var1) {
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
L203:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL204(var0,var1,var2,var3)) {
						ret = true;
						break L203;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL204(Object var0, Object var1, Object var2, Object var3) {
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
L204:
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
			SomeInlineCodedouble.run((Atom)var6, 9);
			ret = true;
			break L204;
		}
		return ret;
	}
	public boolean execL205(Object var0, Object var1) {
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
L205:
		{
		}
		return ret;
	}
	public boolean execL201(Object var0) {
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
L201:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL197(var0,var1)) {
					ret = true;
					break L201;
				}
			}
		}
		return ret;
	}
	public boolean execL197(Object var0, Object var1) {
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
L197:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (execL198(var0,var1,var2,var3)) {
						ret = true;
						break L197;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL198(Object var0, Object var1, Object var2, Object var3) {
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
L198:
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
			func = f14;
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
			SomeInlineCodedouble.run((Atom)var6, 8);
			ret = true;
			break L198;
		}
		return ret;
	}
	public boolean execL199(Object var0, Object var1) {
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
L199:
		{
		}
		return ret;
	}
	public boolean execL195(Object var0) {
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
L195:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL191(var0,var1)) {
					ret = true;
					break L195;
				}
			}
		}
		return ret;
	}
	public boolean execL191(Object var0, Object var1) {
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
L191:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL192(var0,var1,var2,var3)) {
						ret = true;
						break L191;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL192(Object var0, Object var1, Object var2, Object var3) {
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
L192:
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
			SomeInlineCodedouble.run((Atom)var6, 7);
			ret = true;
			break L192;
		}
		return ret;
	}
	public boolean execL193(Object var0, Object var1) {
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
L193:
		{
		}
		return ret;
	}
	public boolean execL189(Object var0) {
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
L189:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL184(var0,var1)) {
					ret = true;
					break L189;
				}
			}
		}
		return ret;
	}
	public boolean execL184(Object var0, Object var1) {
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
L184:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				if (execL185(var0,var1,var2)) {
					ret = true;
					break L184;
				}
			}
		}
		return ret;
	}
	public boolean execL185(Object var0, Object var1, Object var2) {
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
L185:
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
			func = f17;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			SomeInlineCodedouble.run((Atom)var4, 6);
			ret = true;
			break L185;
		}
		return ret;
	}
	public boolean execL186(Object var0, Object var1) {
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
L186:
		{
			if (execL188(var0, var1)) {
				ret = true;
				break L186;
			}
		}
		return ret;
	}
	public boolean execL188(Object var0, Object var1) {
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
L188:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL184(var0,var1)) {
						ret = true;
						break L188;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL182(Object var0) {
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
L182:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL178(var0,var1)) {
					ret = true;
					break L182;
				}
			}
		}
		return ret;
	}
	public boolean execL178(Object var0, Object var1) {
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
L178:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL179(var0,var1,var2,var3)) {
						ret = true;
						break L178;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL179(Object var0, Object var1, Object var2, Object var3) {
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
L179:
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
			func = f19;
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
			SomeInlineCodedouble.run((Atom)var6, 5);
			ret = true;
			break L179;
		}
		return ret;
	}
	public boolean execL180(Object var0, Object var1) {
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
L180:
		{
		}
		return ret;
	}
	public boolean execL176(Object var0) {
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
L176:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL172(var0,var1)) {
					ret = true;
					break L176;
				}
			}
		}
		return ret;
	}
	public boolean execL172(Object var0, Object var1) {
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
L172:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL173(var0,var1,var2,var3)) {
						ret = true;
						break L172;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL173(Object var0, Object var1, Object var2, Object var3) {
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
L173:
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
			func = f21;
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
			SomeInlineCodedouble.run((Atom)var6, 4);
			ret = true;
			break L173;
		}
		return ret;
	}
	public boolean execL174(Object var0, Object var1) {
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
L174:
		{
		}
		return ret;
	}
	public boolean execL170(Object var0) {
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
L170:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL166(var0,var1)) {
					ret = true;
					break L170;
				}
			}
		}
		return ret;
	}
	public boolean execL166(Object var0, Object var1) {
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
L166:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL167(var0,var1,var2,var3)) {
						ret = true;
						break L166;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL167(Object var0, Object var1, Object var2, Object var3) {
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
L167:
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
			func = f23;
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
			SomeInlineCodedouble.run((Atom)var6, 3);
			ret = true;
			break L167;
		}
		return ret;
	}
	public boolean execL168(Object var0, Object var1) {
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
L168:
		{
		}
		return ret;
	}
	public boolean execL164(Object var0) {
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
L164:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL160(var0,var1)) {
					ret = true;
					break L164;
				}
			}
		}
		return ret;
	}
	public boolean execL160(Object var0, Object var1) {
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
L160:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL161(var0,var1,var2,var3)) {
						ret = true;
						break L160;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL161(Object var0, Object var1, Object var2, Object var3) {
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
L161:
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
			func = f25;
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
			SomeInlineCodedouble.run((Atom)var6, 2);
			ret = true;
			break L161;
		}
		return ret;
	}
	public boolean execL162(Object var0, Object var1) {
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
L162:
		{
		}
		return ret;
	}
	public boolean execL158(Object var0) {
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
L158:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL154(var0,var1)) {
					ret = true;
					break L158;
				}
			}
		}
		return ret;
	}
	public boolean execL154(Object var0, Object var1) {
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
L154:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL155(var0,var1,var2,var3)) {
						ret = true;
						break L154;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL155(Object var0, Object var1, Object var2, Object var3) {
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
L155:
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
			func = f27;
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
			SomeInlineCodedouble.run((Atom)var6, 1);
			ret = true;
			break L155;
		}
		return ret;
	}
	public boolean execL156(Object var0, Object var1) {
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
L156:
		{
		}
		return ret;
	}
	public boolean execL152(Object var0) {
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
L152:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL148(var0,var1)) {
					ret = true;
					break L152;
				}
			}
		}
		return ret;
	}
	public boolean execL148(Object var0, Object var1) {
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
L148:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (execL149(var0,var1,var2,var3)) {
						ret = true;
						break L148;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL149(Object var0, Object var1, Object var2, Object var3) {
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
L149:
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
			func = f29;
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
			SomeInlineCodedouble.run((Atom)var6, 0);
			ret = true;
			break L149;
		}
		return ret;
	}
	public boolean execL150(Object var0, Object var1) {
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
L150:
		{
		}
		return ret;
	}
	private static final Functor f11 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f21 = new Functor("/*inline*/\\r\\n\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 == a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	\\r\\n	", 3, null);
	private static final Functor f22 = new Functor(">=", 3, null);
	private static final Functor f20 = new Functor("==", 3, null);
	private static final Functor f8 = new Functor("-", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f27 = new Functor("/*inline*/		\\r\\n\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 < a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	\\r\\n	", 3, null);
	private static final Functor f1 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f28 = new Functor(">", 3, null);
	private static final Functor f5 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f16 = new Functor("abs", 2, null);
	private static final Functor f14 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f6 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f18 = new Functor("!=", 3, null);
	private static final Functor f3 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f25 = new Functor("/*inline*/		\\r\\n\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 <= a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	\\r\\n	", 3, null);
	private static final Functor f13 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f7 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f0 = new Functor("/", 3, null);
	private static final Functor f26 = new Functor("<", 3, null);
	private static final Functor f2 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f17 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor((a0 >= 0 )?a0:-a0));\\r\\n		mem.relink(result, 0, me, 1);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	", 2, null);
	private static final Functor f19 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 != a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f10 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f23 = new Functor("/*inline*/		\\r\\n\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 >= a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	\\r\\n	", 3, null);
	private static final Functor f4 = new Functor("*", 3, null);
	private static final Functor f9 = new Functor("/*inline*/\\r\\n		double a0=\\r\\n		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	", 3, null);
	private static final Functor f29 = new Functor("/*inline*/		\\r\\n\\r\\n		double a0=\\r\\n		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\\r\\n		double a1=\\r\\n		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\\r\\n\\r\\n		Atom result = mem.newAtom(\\r\\n		new Functor((( a0 > a1 )?\"true\":\"false\"), 1)\\r\\n		);\\r\\n		mem.relink(result, 0, me, 2);\\r\\n		me.nthAtom(0).remove();\\r\\n		me.nthAtom(1).remove();\\r\\n		me.remove();\\r\\n	\\r\\n	", 3, null);
	private static final Functor f12 = new Functor("+", 3, null);
	private static final Functor f24 = new Functor("=<", 3, null);
}

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
		if (execL150(mem, atom, false)) {
			return true;
		}
		if (execL157(mem, atom, false)) {
			return true;
		}
		if (execL164(mem, atom, false)) {
			return true;
		}
		if (execL171(mem, atom, false)) {
			return true;
		}
		if (execL178(mem, atom, false)) {
			return true;
		}
		if (execL185(mem, atom, false)) {
			return true;
		}
		if (execL192(mem, atom, false)) {
			return true;
		}
		if (execL199(mem, atom, false)) {
			return true;
		}
		if (execL206(mem, atom, false)) {
			return true;
		}
		if (execL213(mem, atom, false)) {
			return true;
		}
		if (execL220(mem, atom, false)) {
			return true;
		}
		if (execL227(mem, atom, false)) {
			return true;
		}
		if (execL234(mem, atom, false)) {
			return true;
		}
		if (execL241(mem, atom, false)) {
			return true;
		}
		if (execL248(mem, atom, false)) {
			return true;
		}
		if (execL255(mem, atom, false)) {
			return true;
		}
		if (execL262(mem, atom, false)) {
			return true;
		}
		if (execL269(mem, atom, false)) {
			return true;
		}
		if (execL276(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL153(mem, nondeterministic)) {
			return true;
		}
		if (execL160(mem, nondeterministic)) {
			return true;
		}
		if (execL167(mem, nondeterministic)) {
			return true;
		}
		if (execL174(mem, nondeterministic)) {
			return true;
		}
		if (execL181(mem, nondeterministic)) {
			return true;
		}
		if (execL188(mem, nondeterministic)) {
			return true;
		}
		if (execL195(mem, nondeterministic)) {
			return true;
		}
		if (execL202(mem, nondeterministic)) {
			return true;
		}
		if (execL209(mem, nondeterministic)) {
			return true;
		}
		if (execL216(mem, nondeterministic)) {
			return true;
		}
		if (execL223(mem, nondeterministic)) {
			return true;
		}
		if (execL230(mem, nondeterministic)) {
			return true;
		}
		if (execL237(mem, nondeterministic)) {
			return true;
		}
		if (execL244(mem, nondeterministic)) {
			return true;
		}
		if (execL251(mem, nondeterministic)) {
			return true;
		}
		if (execL258(mem, nondeterministic)) {
			return true;
		}
		if (execL265(mem, nondeterministic)) {
			return true;
		}
		if (execL272(mem, nondeterministic)) {
			return true;
		}
		if (execL279(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL279(Object var0, boolean nondeterministic) {
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
L279:
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
						if (execL275(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L279;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL275(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L275:
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
			SomeInlineCodedouble.run((Atom)var6, 18);
			ret = true;
			break L275;
		}
		return ret;
	}
	public boolean execL276(Object var0, Object var1, boolean nondeterministic) {
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
L276:
		{
			if (execL278(var0, var1, nondeterministic)) {
				ret = true;
				break L276;
			}
		}
		return ret;
	}
	public boolean execL278(Object var0, Object var1, boolean nondeterministic) {
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
L278:
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
							if (execL275(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L278;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL272(Object var0, boolean nondeterministic) {
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
L272:
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
						if (execL268(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L272;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL268(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L268:
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
			SomeInlineCodedouble.run((Atom)var6, 17);
			ret = true;
			break L268;
		}
		return ret;
	}
	public boolean execL269(Object var0, Object var1, boolean nondeterministic) {
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
L269:
		{
			if (execL271(var0, var1, nondeterministic)) {
				ret = true;
				break L269;
			}
		}
		return ret;
	}
	public boolean execL271(Object var0, Object var1, boolean nondeterministic) {
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
L271:
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
							if (execL268(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L271;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL265(Object var0, boolean nondeterministic) {
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
L265:
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
						if (execL261(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L265;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL261(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L261:
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
			SomeInlineCodedouble.run((Atom)var6, 16);
			ret = true;
			break L261;
		}
		return ret;
	}
	public boolean execL262(Object var0, Object var1, boolean nondeterministic) {
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
L262:
		{
			if (execL264(var0, var1, nondeterministic)) {
				ret = true;
				break L262;
			}
		}
		return ret;
	}
	public boolean execL264(Object var0, Object var1, boolean nondeterministic) {
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
L264:
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
							if (execL261(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L264;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL258(Object var0, boolean nondeterministic) {
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
L258:
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
						if (execL254(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L258;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL254(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L254:
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
			SomeInlineCodedouble.run((Atom)var6, 15);
			ret = true;
			break L254;
		}
		return ret;
	}
	public boolean execL255(Object var0, Object var1, boolean nondeterministic) {
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
L255:
		{
			if (execL257(var0, var1, nondeterministic)) {
				ret = true;
				break L255;
			}
		}
		return ret;
	}
	public boolean execL257(Object var0, Object var1, boolean nondeterministic) {
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
L257:
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
							if (execL254(var0,var1,var5,var6,nondeterministic)) {
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
	public boolean execL251(Object var0, boolean nondeterministic) {
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
L251:
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
						if (execL247(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L251;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL247(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L247:
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
			SomeInlineCodedouble.run((Atom)var6, 14);
			ret = true;
			break L247;
		}
		return ret;
	}
	public boolean execL248(Object var0, Object var1, boolean nondeterministic) {
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
L248:
		{
			if (execL250(var0, var1, nondeterministic)) {
				ret = true;
				break L248;
			}
		}
		return ret;
	}
	public boolean execL250(Object var0, Object var1, boolean nondeterministic) {
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
L250:
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
							if (execL247(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L250;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL244(Object var0, boolean nondeterministic) {
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
L244:
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
						if (execL240(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L244;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL240(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L240:
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
			SomeInlineCodedouble.run((Atom)var6, 13);
			ret = true;
			break L240;
		}
		return ret;
	}
	public boolean execL241(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL243(var0, var1, nondeterministic)) {
				ret = true;
				break L241;
			}
		}
		return ret;
	}
	public boolean execL243(Object var0, Object var1, boolean nondeterministic) {
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
L243:
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
							if (execL240(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L243;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL237(Object var0, boolean nondeterministic) {
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
L237:
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
						if (execL233(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L237;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL233(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L233:
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
			SomeInlineCodedouble.run((Atom)var6, 12);
			ret = true;
			break L233;
		}
		return ret;
	}
	public boolean execL234(Object var0, Object var1, boolean nondeterministic) {
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
L234:
		{
			if (execL236(var0, var1, nondeterministic)) {
				ret = true;
				break L234;
			}
		}
		return ret;
	}
	public boolean execL236(Object var0, Object var1, boolean nondeterministic) {
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
L236:
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
							if (execL233(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L236;
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
		Set insset;
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
L226:
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
			SomeInlineCodedouble.run((Atom)var6, 11);
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
		Set insset;
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
		Set insset;
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
							if (execL226(var0,var1,var5,var6,nondeterministic)) {
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
	public boolean execL223(Object var0, boolean nondeterministic) {
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
L223:
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
						if (execL219(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L223;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL219(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L219:
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
			SomeInlineCodedouble.run((Atom)var6, 10);
			ret = true;
			break L219;
		}
		return ret;
	}
	public boolean execL220(Object var0, Object var1, boolean nondeterministic) {
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
L220:
		{
			if (execL222(var0, var1, nondeterministic)) {
				ret = true;
				break L220;
			}
		}
		return ret;
	}
	public boolean execL222(Object var0, Object var1, boolean nondeterministic) {
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
L222:
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
							if (execL219(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L222;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL216(Object var0, boolean nondeterministic) {
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
L216:
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
						if (execL212(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L216;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL212(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L212:
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
			SomeInlineCodedouble.run((Atom)var6, 9);
			ret = true;
			break L212;
		}
		return ret;
	}
	public boolean execL213(Object var0, Object var1, boolean nondeterministic) {
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
L213:
		{
			if (execL215(var0, var1, nondeterministic)) {
				ret = true;
				break L213;
			}
		}
		return ret;
	}
	public boolean execL215(Object var0, Object var1, boolean nondeterministic) {
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
L215:
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
							if (execL212(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L215;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL209(Object var0, boolean nondeterministic) {
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
L209:
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
						if (execL205(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L209;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL205(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L205:
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
			SomeInlineCodedouble.run((Atom)var6, 8);
			ret = true;
			break L205;
		}
		return ret;
	}
	public boolean execL206(Object var0, Object var1, boolean nondeterministic) {
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
L206:
		{
			if (execL208(var0, var1, nondeterministic)) {
				ret = true;
				break L206;
			}
		}
		return ret;
	}
	public boolean execL208(Object var0, Object var1, boolean nondeterministic) {
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
L208:
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
							if (execL205(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L208;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL202(Object var0, boolean nondeterministic) {
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
L202:
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
						if (execL198(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L202;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL198(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L198:
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
			SomeInlineCodedouble.run((Atom)var6, 7);
			ret = true;
			break L198;
		}
		return ret;
	}
	public boolean execL199(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL201(var0, var1, nondeterministic)) {
				ret = true;
				break L199;
			}
		}
		return ret;
	}
	public boolean execL201(Object var0, Object var1, boolean nondeterministic) {
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
L201:
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
							if (execL198(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L201;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL195(Object var0, boolean nondeterministic) {
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
L195:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					if (execL191(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L195;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL191(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L191:
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
			func = f17;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedouble.run((Atom)var4, 6);
			ret = true;
			break L191;
		}
		return ret;
	}
	public boolean execL192(Object var0, Object var1, boolean nondeterministic) {
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
L192:
		{
			if (execL194(var0, var1, nondeterministic)) {
				ret = true;
				break L192;
			}
		}
		return ret;
	}
	public boolean execL194(Object var0, Object var1, boolean nondeterministic) {
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
L194:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof FloatingFunctor))) {
						if (execL191(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L194;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL188(Object var0, boolean nondeterministic) {
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
L188:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL184(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L188;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL184(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L184:
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
			SomeInlineCodedouble.run((Atom)var6, 5);
			ret = true;
			break L184;
		}
		return ret;
	}
	public boolean execL185(Object var0, Object var1, boolean nondeterministic) {
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
L185:
		{
			if (execL187(var0, var1, nondeterministic)) {
				ret = true;
				break L185;
			}
		}
		return ret;
	}
	public boolean execL187(Object var0, Object var1, boolean nondeterministic) {
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
L187:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL184(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L187;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL181(Object var0, boolean nondeterministic) {
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
L181:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL177(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L181;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL177(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L177:
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
			SomeInlineCodedouble.run((Atom)var6, 4);
			ret = true;
			break L177;
		}
		return ret;
	}
	public boolean execL178(Object var0, Object var1, boolean nondeterministic) {
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
L178:
		{
			if (execL180(var0, var1, nondeterministic)) {
				ret = true;
				break L178;
			}
		}
		return ret;
	}
	public boolean execL180(Object var0, Object var1, boolean nondeterministic) {
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
L180:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL177(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L180;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL174(Object var0, boolean nondeterministic) {
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
L174:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL170(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L174;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL170(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L170:
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
			SomeInlineCodedouble.run((Atom)var6, 3);
			ret = true;
			break L170;
		}
		return ret;
	}
	public boolean execL171(Object var0, Object var1, boolean nondeterministic) {
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
L171:
		{
			if (execL173(var0, var1, nondeterministic)) {
				ret = true;
				break L171;
			}
		}
		return ret;
	}
	public boolean execL173(Object var0, Object var1, boolean nondeterministic) {
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
L173:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL170(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L173;
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
		Set insset;
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
			func = f24;
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
		Set insset;
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
			func = f25;
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
			SomeInlineCodedouble.run((Atom)var6, 2);
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
		Set insset;
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
		Set insset;
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
	public boolean execL160(Object var0, boolean nondeterministic) {
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
L160:
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
				if (!(!(((Atom)var3).getFunctor() instanceof FloatingFunctor))) {
					if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
						if (execL156(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L160;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL156(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L156:
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
			func = f27;
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
			SomeInlineCodedouble.run((Atom)var6, 1);
			ret = true;
			break L156;
		}
		return ret;
	}
	public boolean execL157(Object var0, Object var1, boolean nondeterministic) {
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
L157:
		{
			if (execL159(var0, var1, nondeterministic)) {
				ret = true;
				break L157;
			}
		}
		return ret;
	}
	public boolean execL159(Object var0, Object var1, boolean nondeterministic) {
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
L159:
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
					if (!(!(((Atom)var6).getFunctor() instanceof FloatingFunctor))) {
						if (!(!(((Atom)var5).getFunctor() instanceof FloatingFunctor))) {
							if (execL156(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L159;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL153(Object var0, boolean nondeterministic) {
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
L153:
		{
			func = f28;
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
						if (execL149(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L153;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL149(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L149:
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
			func = f29;
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
			SomeInlineCodedouble.run((Atom)var6, 0);
			ret = true;
			break L149;
		}
		return ret;
	}
	public boolean execL150(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL152(var0, var1, nondeterministic)) {
				ret = true;
				break L150;
			}
		}
		return ret;
	}
	public boolean execL152(Object var0, Object var1, boolean nondeterministic) {
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
L152:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
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
							if (execL149(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L152;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f11 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f21 = new Functor("/*inline*/\r\n\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 == a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\r\n\t", 3, null);
	private static final Functor f22 = new Functor(">=", 3, null);
	private static final Functor f20 = new Functor("==", 3, null);
	private static final Functor f8 = new Functor("-", 3, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f27 = new Functor("/*inline*/\t\t\r\n\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 < a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\r\n\t", 3, null);
	private static final Functor f1 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f28 = new Functor(">", 3, null);
	private static final Functor f5 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f16 = new Functor("abs", 2, null);
	private static final Functor f14 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f6 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f18 = new Functor("!=", 3, null);
	private static final Functor f3 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f25 = new Functor("/*inline*/\t\t\r\n\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 <= a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\r\n\t", 3, null);
	private static final Functor f13 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 + a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f7 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 * a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f0 = new Functor("/", 3, null);
	private static final Functor f26 = new Functor("<", 3, null);
	private static final Functor f2 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 / a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f17 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor((a0 >= 0 )?a0:-a0));\r\n\t\tmem.relink(result, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t", 2, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 != a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f10 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f23 = new Functor("/*inline*/\t\t\r\n\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 >= a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\r\n\t", 3, null);
	private static final Functor f4 = new Functor("*", 3, null);
	private static final Functor f9 = new Functor("/*inline*/\r\n\t\tdouble a0=\r\n\t\t((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\t\tAtom result = mem.newAtom(new FloatingFunctor(a0 - a1));\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t", 3, null);
	private static final Functor f29 = new Functor("/*inline*/\t\t\r\n\r\n\t\tdouble a0=\r\n\t\t((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();\r\n\t\tdouble a1=\r\n\t\t((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();\r\n\r\n\t\tAtom result = mem.newAtom(\r\n\t\tnew Functor((( a0 > a1 )?\"true\":\"false\"), 1)\r\n\t\t);\r\n\t\tmem.relink(result, 0, me, 2);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tme.remove();\r\n\t\r\n\t", 3, null);
	private static final Functor f12 = new Functor("+", 3, null);
	private static final Functor f24 = new Functor("=<", 3, null);
}

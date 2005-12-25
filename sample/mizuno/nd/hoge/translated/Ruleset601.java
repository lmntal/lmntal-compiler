package translated;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset601 extends Ruleset {
	private static final Ruleset601 theInstance = new Ruleset601();
	private Ruleset601() {}
	public static Ruleset601 getInstance() {
		return theInstance;
	}
	private int id = 601;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		return react(mem, atom, false);
	}
	public boolean react(Membrane mem, Atom atom, boolean nondeterministic) {
		boolean result = false;
		if (execL102(mem, atom, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL105(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL105(Object var0, boolean nondeterministic) {
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
L105:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL100(var0,var1,nondeterministic)) {
					ret = true;
					break L105;
				}
			}
		}
		return ret;
	}
	public boolean execL100(Object var0, Object var1, boolean nondeterministic) {
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
L100:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "L101",var0,var1});
			} else if (execL101(var0,var1,nondeterministic)) {
				ret = true;
				break L100;
			}
		}
		return ret;
	}
	public boolean execL101(Object var0, Object var1, boolean nondeterministic) {
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
L101:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().relinkAtomArgs(
				((Atom)var2), 0,
				((Atom)var1), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L101;
		}
		return ret;
	}
	public boolean execL102(Object var0, Object var1, boolean nondeterministic) {
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
L102:
		{
			if (execL104(var0, var1, nondeterministic)) {
				ret = true;
				break L102;
			}
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1, boolean nondeterministic) {
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
L104:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL100(var0,var1,nondeterministic)) {
						ret = true;
						break L104;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f1 = new Functor("b", 1, null);
	private static final Functor f0 = new Functor("a", 1, null);
}

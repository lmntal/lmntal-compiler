package translated.module_if;
import runtime.*;
import java.util.*;
import java.io.*;
import java.math.*;
import daemon.IDConverter;
import module.*;

public class Ruleset606 extends Ruleset {
	private static final Ruleset606 theInstance = new Ruleset606();
	private Ruleset606() {}
	public static Ruleset606 getInstance() {
		return theInstance;
	}
	private int id = 606;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":if" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@if" + id;
	}
	private String encodedRuleset = 
"(initial rule)";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL365(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@606", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL366(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@606", "null");
			return true;
		}
		return result;
	}
	public boolean execL366(Object var0, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
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
			if (execL363(var0,nondeterministic)) {
				ret = true;
				break L366;
			}
		}
		return ret;
	}
	public boolean execL363(Object var0, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L363:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L364",var0});
			} else if (execL364(var0,nondeterministic)) {
				ret = true;
				break L363;
			}
		}
		return ret;
	}
	public boolean execL364(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
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
			mem = ((AbstractMembrane)var0).newMem(0);
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset605.getInstance());
			func = f0;
			var2 = ((AbstractMembrane)var1).newAtom(func);
			func = f1;
			var3 = ((AbstractMembrane)var1).newAtom(func);
			link = new Link(((Atom)var2), 0);
			var4 = link;
			link = new Link(((Atom)var3), 0);
			var5 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var4),
				((Link)var5));
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L364;
		}
		return ret;
	}
	public boolean execL365(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L365:
		{
		}
		return ret;
	}
	private static final Functor f0 = new Functor("if", 1, null);
	private static final Functor f1 = new Functor("module", 1, null);
}

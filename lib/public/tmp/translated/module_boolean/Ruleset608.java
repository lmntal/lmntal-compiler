package translated.module_boolean;
import runtime.*;
import java.util.*;
import java.io.*;
import java.math.*;
import daemon.IDConverter;
import module.*;

public class Ruleset608 extends Ruleset {
	private static final Ruleset608 theInstance = new Ruleset608();
	private Ruleset608() {}
	public static Ruleset608 getInstance() {
		return theInstance;
	}
	private int id = 608;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":boolean" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@boolean" + id;
	}
	private String encodedRuleset = 
"(initial rule)";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL498(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@608", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL499(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@608", "null");
			return true;
		}
		return result;
	}
	public boolean execL499(Object var0, boolean nondeterministic) {
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
L499:
		{
			if (execL496(var0,nondeterministic)) {
				ret = true;
				break L499;
			}
		}
		return ret;
	}
	public boolean execL496(Object var0, boolean nondeterministic) {
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
L496:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L497",var0});
			} else if (execL497(var0,nondeterministic)) {
				ret = true;
				break L496;
			}
		}
		return ret;
	}
	public boolean execL497(Object var0, boolean nondeterministic) {
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
L497:
		{
			mem = ((AbstractMembrane)var0).newMem(0);
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset607.getInstance());
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
			break L497;
		}
		return ret;
	}
	public boolean execL498(Object var0, Object var1, boolean nondeterministic) {
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
L498:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f0 = new Functor("boolean", 1, null);
}

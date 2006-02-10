package translated.module_thread;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset619 extends Ruleset {
	private static final Ruleset619 theInstance = new Ruleset619();
	private Ruleset619() {}
	public static Ruleset619 getInstance() {
		return theInstance;
	}
	private int id = 619;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":thread" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@thread" + id;
	}
	private String encodedRuleset = 
"(initial rule)";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL869(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@619", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL870(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@619", "null");
			return true;
		}
		return result;
	}
	public boolean execL870(Object var0, boolean nondeterministic) {
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
L870:
		{
			if (execL867(var0,nondeterministic)) {
				ret = true;
				break L870;
			}
		}
		return ret;
	}
	public boolean execL867(Object var0, boolean nondeterministic) {
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
L867:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L868",var0});
			} else if (execL868(var0,nondeterministic)) {
				ret = true;
				break L867;
			}
		}
		return ret;
	}
	public boolean execL868(Object var0, boolean nondeterministic) {
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
L868:
		{
			((AbstractMembrane)var0).loadRuleset(Ruleset618.getInstance());
			ret = true;
			break L868;
		}
		return ret;
	}
	public boolean execL869(Object var0, Object var1, boolean nondeterministic) {
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
L869:
		{
		}
		return ret;
	}
}

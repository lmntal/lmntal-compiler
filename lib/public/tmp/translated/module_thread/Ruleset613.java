package translated.module_thread;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset613 extends Ruleset {
	private static final Ruleset613 theInstance = new Ruleset613();
	private Ruleset613() {}
	public static Ruleset613 getInstance() {
		return theInstance;
	}
	private int id = 613;
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
		if (execL757(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL758(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "null");
			return true;
		}
		return result;
	}
	public boolean execL758(Object var0, boolean nondeterministic) {
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
L758:
		{
			if (execL755(var0,nondeterministic)) {
				ret = true;
				break L758;
			}
		}
		return ret;
	}
	public boolean execL755(Object var0, boolean nondeterministic) {
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
L755:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L756",var0});
			} else if (execL756(var0,nondeterministic)) {
				ret = true;
				break L755;
			}
		}
		return ret;
	}
	public boolean execL756(Object var0, boolean nondeterministic) {
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
L756:
		{
			((AbstractMembrane)var0).loadRuleset(Ruleset612.getInstance());
			ret = true;
			break L756;
		}
		return ret;
	}
	public boolean execL757(Object var0, Object var1, boolean nondeterministic) {
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
L757:
		{
		}
		return ret;
	}
}

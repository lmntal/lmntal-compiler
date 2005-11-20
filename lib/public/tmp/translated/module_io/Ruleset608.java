package translated.module_io;
import runtime.*;
import java.util.*;
import java.io.*;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":io" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@io" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL677(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL678(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL678(Object var0) {
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
L678:
		{
			if (execL675(var0)) {
				ret = true;
				break L678;
			}
		}
		return ret;
	}
	public boolean execL675(Object var0) {
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
L675:
		{
			if (execL676(var0)) {
				ret = true;
				break L675;
			}
		}
		return ret;
	}
	public boolean execL676(Object var0) {
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
L676:
		{
			mem = ((AbstractMembrane)var0).newMem();
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
			break L676;
		}
		return ret;
	}
	public boolean execL677(Object var0, Object var1) {
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
L677:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f0 = new Functor("io", 1, null);
}

package translated.module_wt;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset618 extends Ruleset {
	private static final Ruleset618 theInstance = new Ruleset618();
	private Ruleset618() {}
	public static Ruleset618 getInstance() {
		return theInstance;
	}
	private int id = 618;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":wt" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@wt" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1277(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1278(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL1278(Object var0, boolean nondeterministic) {
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
L1278:
		{
			if (execL1275(var0,nondeterministic)) {
				ret = true;
				break L1278;
			}
		}
		return ret;
	}
	public boolean execL1275(Object var0, boolean nondeterministic) {
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
L1275:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "L1276",var0});
			} else if (execL1276(var0,nondeterministic)) {
				ret = true;
				break L1275;
			}
		}
		return ret;
	}
	public boolean execL1276(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1276:
		{
			mem = ((AbstractMembrane)var0).newMem();
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset617.getInstance());
			func = f0;
			var2 = ((AbstractMembrane)var1).newAtom(func);
			func = f1;
			var3 = ((AbstractMembrane)var1).newAtom(func);
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var2), 0);
			var5 = link;
			link = new Link(((Atom)var3), 0);
			var6 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var5),
				((Link)var6));
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L1276;
		}
		return ret;
	}
	public boolean execL1277(Object var0, Object var1, boolean nondeterministic) {
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
L1277:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\r\nimport javax.swing.*;\r\nimport java.awt.*;\r\nimport java.awt.event.*;\r\nimport java.util.*;\r\n\r\nclass LMNtalFrame extends JFrame{\r\n  final Membrane mem;\r\n\r\n  public LMNtalFrame(Membrane targetMem){\r\n    this.mem = targetMem;\r\n    addWindowListener(new WindowAdapter(){\r\n      public void windowClosing(WindowEvent e){\r\n        mem.asyncLock();\r\n        mem.newAtom(new Functor(\"terminate\",0));\r\n        mem.asyncUnlock();\r\n      }\r\n    });\r\n  }\r\n}\r\n\r\nclass PriorityQueue{\r\n  ArrayList list = new ArrayList();\r\n  \r\n  public int insert(int priority){\r\n    int i;\r\n    for(i=0;i<list.size()&&priority>((Integer)list.get(i)).intValue();i++);\r\n    list.add(i,new Integer(priority));\r\n    return(i);\r\n  }\r\n}\r\n", 0, null);
	private static final Functor f0 = new Functor("wt", 1, null);
}

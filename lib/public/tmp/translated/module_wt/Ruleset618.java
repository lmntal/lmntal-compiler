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
		if (execL1224(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL1225(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL1225(Object var0) {
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
L1225:
		{
			if (execL1222(var0)) {
				ret = true;
				break L1225;
			}
		}
		return ret;
	}
	public boolean execL1222(Object var0) {
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
L1222:
		{
			if (execL1223(var0)) {
				ret = true;
				break L1222;
			}
		}
		return ret;
	}
	public boolean execL1223(Object var0) {
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
L1223:
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
			break L1223;
		}
		return ret;
	}
	public boolean execL1224(Object var0, Object var1) {
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
L1224:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\\r\\nimport javax.swing.*;\\r\\nimport java.awt.*;\\r\\nimport java.awt.event.*;\\r\\nimport java.util.*;\\r\\n\\r\\nclass LMNtalFrame extends JFrame{\\r\\n  final Membrane mem;\\r\\n\\r\\n  public LMNtalFrame(Membrane targetMem){\\r\\n    this.mem = targetMem;\\r\\n    addWindowListener(new WindowAdapter(){\\r\\n      public void windowClosing(WindowEvent e){\\r\\n        mem.asyncLock();\\r\\n        mem.newAtom(new Functor(\"terminate\",0));\\r\\n        mem.asyncUnlock();\\r\\n      }\\r\\n    });\\r\\n  }\\r\\n}\\r\\n\\r\\nclass PriorityQueue{\\r\\n  ArrayList list = new ArrayList();\\r\\n  \\r\\n  public int insert(int priority){\\r\\n    int i;\\r\\n    for(i=0;i<list.size()&&priority>((Integer)list.get(i)).intValue();i++);\\r\\n    list.add(i,new Integer(priority));\\r\\n    return(i);\\r\\n  }\\r\\n}\\r\\n", 0, null);
	private static final Functor f0 = new Functor("wt", 1, null);
}

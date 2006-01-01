package translated.module_socket;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset614 extends Ruleset {
	private static final Ruleset614 theInstance = new Ruleset614();
	private Ruleset614() {}
	public static Ruleset614 getInstance() {
		return theInstance;
	}
	private int id = 614;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":socket" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@socket" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1379(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@614", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1380(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@614", "null");
			return true;
		}
		return result;
	}
	public boolean execL1380(Object var0, boolean nondeterministic) {
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
L1380:
		{
			if (execL1377(var0,nondeterministic)) {
				ret = true;
				break L1380;
			}
		}
		return ret;
	}
	public boolean execL1377(Object var0, boolean nondeterministic) {
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
L1377:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L1378",var0});
			} else if (execL1378(var0,nondeterministic)) {
				ret = true;
				break L1377;
			}
		}
		return ret;
	}
	public boolean execL1378(Object var0, boolean nondeterministic) {
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
L1378:
		{
			mem = ((AbstractMembrane)var0).newMem();
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset613.getInstance());
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
			break L1378;
		}
		return ret;
	}
	public boolean execL1379(Object var0, Object var1, boolean nondeterministic) {
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
L1379:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\r\nimport java.net.*;\r\nimport java.io.*;\r\n\r\nclass AcceptThread extends Thread {\r\n\tServerSocket ss;\r\n\tAtom ssAtom;\r\n\tAcceptThread(ServerSocket ss, Atom ssAtom) {\r\n\t\tthis.ss = ss;\r\n\t\tthis.ssAtom = ssAtom;\r\n\t}\r\n\tpublic void run() {\r\n\t\ttry {\r\n\t\t\tSocket soc = ss.accept();\r\n\t\t\tAbstractMembrane mem = ssAtom.getMem();\r\n\t\t\tmem.asyncLock();\r\n\t\t\tAtom dot = ssAtom.nthAtom(1);\r\n\t\t\tAtom acceptingAtom = dot.nthAtom(0);\r\n\t\t\tReadThread sr = new ReadThread(soc);\r\n\t\t\t//make client socket\r\n\t\t\tAtom s = mem.newAtom(new Functor(\"socket\", 4, \"socket\"));\r\n\t\t\tAtom o = mem.newAtom(new ObjectFunctor(sr));\r\n\t\t\tsr.me = o;\r\n\t\t\tAtom nil1 = mem.newAtom(new Functor(\"nil\", 1));\r\n\t\t\tAtom nil2 = mem.newAtom(new Functor(\"[]\", 1));\r\n\t\t\tmem.newLink(s, 0, o, 0);\r\n\t\t\tmem.newLink(s, 1, nil1, 0); \r\n\t\t\tmem.newLink(s, 2, nil2, 0); \r\n\t\t\tmem.relink(s, 3, acceptingAtom, 0);\r\n\t\t\t\r\n\t\t\t//relink command list\r\n\t\t\tmem.unifyAtomArgs(dot, 1, dot, 2);\r\n\t\t\tdot.remove();\r\n\t\t\tacceptingAtom.remove();\r\n\r\n\t\t\tmem.asyncUnlock();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t}\r\n}\r\n\r\nclass ReadThread extends Thread {\r\n\tSocket socket;\r\n\tAtom me;\r\n\tboolean flgClosing = false;\r\n\tReadThread(Socket socket) {\r\n\t\tthis.socket = socket;\r\n\t\tthis.me = me;\r\n\t}\r\n\tReadThread(String host, int port) throws IOException {\r\n\t\tthis.socket = new Socket(host, port);\r\n\t\tthis.me = me;\r\n\t}\r\n\tpublic void run() {\r\n\t\ttry {\r\n\t\t\tBufferedReader reader = new BufferedReader(\r\n\t\t\t\tnew InputStreamReader(socket.getInputStream()));\r\nL:\r\n\t\t\twhile (true) {\r\n\t\t\t\tString data;\r\n\t\t\t\twhile (true) {\r\n\t\t\t\t\tif (flgClosing) {\r\n\t\t\t\t\t\tbreak L;\r\n\t\t\t\t\t}\r\n\t\t\t\t\tif (reader.ready()) {\r\n\t\t\t\t\t\tdata = reader.readLine();\r\n\t\t\t\t\t\tbreak;\r\n\t\t\t\t\t}\r\n\t\t\t\t\tThread.sleep(50);\r\n\t\t\t\t}\r\n\t\t\t\tAbstractMembrane mem = me.getMem();\r\n\t\t\t\tmem.asyncLock();\r\n\t\t\t\tAtom socAtom = me.nthAtom(0);\r\n\t\t\t\tAtom dataAtom = mem.newAtom(new StringFunctor(data));\r\n\t\t\t\tAtom dot = mem.newAtom(new Functor(\".\", 3));\r\n\t\t\t\tmem.newLink(dot, 0, dataAtom, 0);\r\n\t\t\t\tmem.relink(dot, 2, socAtom, 1);\r\n\t\t\t\tmem.newLink(dot, 1, socAtom, 1);\r\n\t\t\t\tmem.asyncUnlock();\r\n\t\t\t}\r\n\t\t\tAbstractMembrane mem = me.getMem();\r\n\t\t\tmem.asyncLock();\r\n\t\t\tAtom socAtom = me.nthAtom(0);\r\n\t\t\tAtom nil = mem.newAtom(new Functor(\"[]\", 1));\r\n\t\t\tmem.relink(nil, 0, socAtom, 1);\r\n\t\t\tAtom closed = mem.newAtom(new Functor(\"nil\", 1));\r\n\t\t\tmem.newLink(closed, 0, socAtom, 1);\r\n\t\t\tmem.asyncUnlock();\r\n\t\t} catch (Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t}\r\n}\r\n", 0, null);
	private static final Functor f0 = new Functor("socket", 1, null);
}

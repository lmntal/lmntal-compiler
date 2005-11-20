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
		if (execL1068(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL1069(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL1069(Object var0) {
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
L1069:
		{
			if (execL1066(var0)) {
				ret = true;
				break L1069;
			}
		}
		return ret;
	}
	public boolean execL1066(Object var0) {
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
L1066:
		{
			if (execL1067(var0)) {
				ret = true;
				break L1066;
			}
		}
		return ret;
	}
	public boolean execL1067(Object var0) {
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
L1067:
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
			break L1067;
		}
		return ret;
	}
	public boolean execL1068(Object var0, Object var1) {
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
L1068:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\\r\\nimport java.net.*;\\r\\nimport java.io.*;\\r\\n\\r\\nclass AcceptThread extends Thread {\\r\\n	ServerSocket ss;\\r\\n	Atom ssAtom;\\r\\n	AcceptThread(ServerSocket ss, Atom ssAtom) {\\r\\n		this.ss = ss;\\r\\n		this.ssAtom = ssAtom;\\r\\n	}\\r\\n	public void run() {\\r\\n		try {\\r\\n			Socket soc = ss.accept();\\r\\n			AbstractMembrane mem = ssAtom.getMem();\\r\\n			mem.asyncLock();\\r\\n			Atom dot = ssAtom.nthAtom(1);\\r\\n			Atom acceptingAtom = dot.nthAtom(0);\\r\\n			ReadThread sr = new ReadThread(soc);\\r\\n			//make client socket\\r\\n			Atom s = mem.newAtom(new Functor(\"socket\", 4, \"socket\"));\\r\\n			Atom o = mem.newAtom(new ObjectFunctor(sr));\\r\\n			sr.me = o;\\r\\n			Atom nil1 = mem.newAtom(new Functor(\"nil\", 1));\\r\\n			Atom nil2 = mem.newAtom(new Functor(\"[]\", 1));\\r\\n			mem.newLink(s, 0, o, 0);\\r\\n			mem.newLink(s, 1, nil1, 0); \\r\\n			mem.newLink(s, 2, nil2, 0); \\r\\n			mem.relink(s, 3, acceptingAtom, 0);\\r\\n			\\r\\n			//relink command list\\r\\n			mem.unifyAtomArgs(dot, 1, dot, 2);\\r\\n			dot.remove();\\r\\n			acceptingAtom.remove();\\r\\n\\r\\n			mem.asyncUnlock();\\r\\n		} catch (IOException e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n	}\\r\\n}\\r\\n\\r\\nclass ReadThread extends Thread {\\r\\n	Socket socket;\\r\\n	Atom me;\\r\\n	boolean flgClosing = false;\\r\\n	ReadThread(Socket socket) {\\r\\n		this.socket = socket;\\r\\n		this.me = me;\\r\\n	}\\r\\n	ReadThread(String host, int port) throws IOException {\\r\\n		this.socket = new Socket(host, port);\\r\\n		this.me = me;\\r\\n	}\\r\\n	public void run() {\\r\\n		try {\\r\\n			BufferedReader reader = new BufferedReader(\\r\\n				new InputStreamReader(socket.getInputStream()));\\r\\nL:\\r\\n			while (true) {\\r\\n				String data;\\r\\n				while (true) {\\r\\n					if (flgClosing) {\\r\\n						break L;\\r\\n					}\\r\\n					if (reader.ready()) {\\r\\n						data = reader.readLine();\\r\\n						break;\\r\\n					}\\r\\n					Thread.sleep(50);\\r\\n				}\\r\\n				AbstractMembrane mem = me.getMem();\\r\\n				mem.asyncLock();\\r\\n				Atom socAtom = me.nthAtom(0);\\r\\n				Atom dataAtom = mem.newAtom(new StringFunctor(data));\\r\\n				Atom dot = mem.newAtom(new Functor(\".\", 3));\\r\\n				mem.newLink(dot, 0, dataAtom, 0);\\r\\n				mem.relink(dot, 2, socAtom, 1);\\r\\n				mem.newLink(dot, 1, socAtom, 1);\\r\\n				mem.asyncUnlock();\\r\\n			}\\r\\n			AbstractMembrane mem = me.getMem();\\r\\n			mem.asyncLock();\\r\\n			Atom socAtom = me.nthAtom(0);\\r\\n			Atom nil = mem.newAtom(new Functor(\"[]\", 1));\\r\\n			mem.relink(nil, 0, socAtom, 1);\\r\\n			Atom closed = mem.newAtom(new Functor(\"nil\", 1));\\r\\n			mem.newLink(closed, 0, socAtom, 1);\\r\\n			mem.asyncUnlock();\\r\\n		} catch (Exception e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n	}\\r\\n}\\r\\n", 0, null);
	private static final Functor f0 = new Functor("socket", 1, null);
}

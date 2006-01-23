package translated.module_dlmntal;
import runtime.*;
import java.util.*;
import java.io.*;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":dlmntal" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@dlmntal" + id;
	}
	private String encodedRuleset = 
"(initial rule)";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL297(mem, atom, false)) {
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
		if (execL298(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@606", "null");
			return true;
		}
		return result;
	}
	public boolean execL298(Object var0, boolean nondeterministic) {
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
L298:
		{
			if (execL295(var0,nondeterministic)) {
				ret = true;
				break L298;
			}
		}
		return ret;
	}
	public boolean execL295(Object var0, boolean nondeterministic) {
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
L295:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L296",var0});
			} else if (execL296(var0,nondeterministic)) {
				ret = true;
				break L295;
			}
		}
		return ret;
	}
	public boolean execL296(Object var0, boolean nondeterministic) {
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
L296:
		{
			mem = ((AbstractMembrane)var0).newMem(0);
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset605.getInstance());
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
			break L296;
		}
		return ret;
	}
	public boolean execL297(Object var0, Object var1, boolean nondeterministic) {
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
L297:
		{
		}
		return ret;
	}
	private static final Functor f0 = new Functor("dlmntal", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\r\nimport java.io.*;\r\nimport java.net.*;\r\n\r\n/**\r\n *  class: AcceptThread\r\n */\r\nclass AcceptThread extends Thread {\r\n  private ServerSocket servSock;\r\n  private final Membrane mem;\r\n  private Atom ssAtom = null;\r\n\r\n  /**\r\n   *  AcceptThread#AcceptThread()\r\n   *  @param servSock\r\n   *  @param mem\r\n   *  @param ssAtom\r\n   */\r\n  AcceptThread(ServerSocket servSock, Membrane mem, Atom ssAtom) {\r\n    this.servSock = servSock;\r\n    this.mem = mem;\r\n    this.ssAtom = ssAtom;\r\n  }\r\n  \r\n  /**\r\n   *  AcceptThread#run()\r\n   */\r\n  public void run() {\r\n    try {\r\n      Socket sock = servSock.accept();\r\n      BufferedReader reader = \r\n        new BufferedReader(new InputStreamReader(sock.getInputStream()));\r\n      PrintWriter writer = new PrintWriter(sock.getOutputStream());\r\n      ReaderThread rt = new ReaderThread(sock,reader,mem);\r\n      Vector obj = new Vector(3);\r\n      obj.add(sock);\r\n      obj.add(writer);\r\n      obj.add(rt);\r\n      Functor objFunc = new ObjectFunctor(obj);\r\n      mem.asyncLock();\r\n      Atom objAtom = mem.newAtom(objFunc);\r\n      rt.setObjAtom(objAtom);\r\n      Atom sockAtom = mem.newAtom(new Functor(\"socket\",3,\"dlmntal\"));\r\n      Atom consAtom = ssAtom.nthAtom(0).nthAtom(0);\r\n      Atom cmdAtom = consAtom.nthAtom(0);\r\n      mem.relinkAtomArgs(sockAtom,0,cmdAtom,0);\r\n      mem.relinkAtomArgs(sockAtom,1,cmdAtom,1);\r\n      mem.newLink(sockAtom,2,objAtom,0);\r\n      mem.unifyAtomArgs(consAtom,1,consAtom,2);\r\n      consAtom.remove();\r\n      cmdAtom.remove();\r\n      mem.asyncUnlock();\r\n      Thread t = new Thread(rt,\"rt\");\r\n      rt.start();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n    }\r\n  }\r\n}\r\n\r\n/** \r\n *  class: ReaderThread\r\n */\r\nclass ReaderThread implements Runnable {\r\n  volatile Thread th = null;\r\n  private Socket sock;\r\n  private BufferedReader reader;\r\n  private final Membrane mem;\r\n  private Atom objAtom = null;\r\n  \r\n  /**\r\n   *  ReaderThread#ReaderThread()\r\n   *  @param sock\r\n   *  @param reader\r\n   *  @param mem\r\n   */\r\n  ReaderThread(Socket sock, BufferedReader reader, Membrane mem) {\r\n    this.sock = sock;\r\n    this.reader = reader;\r\n    this.mem = mem;\r\n  }\r\n  \r\n  /**\r\n   *  ReaderThread#setObjAtom()\r\n   *  @param objAtom\r\n   */\r\n  public void setObjAtom(Atom objAtom) {\r\n    this.objAtom = objAtom;\r\n  }\r\n  \r\n  /**\r\n   *  ReaderThread#start()\r\n   */\r\n  public void start() {\r\n    if(th == null) {\r\n      th = new Thread(this);\r\n      th.start();\r\n    }\r\n  }\r\n  \r\n  /**\r\n   *  ReaderThread#run()\r\n   */\r\n  public void run(){\r\n    Thread thisThread = Thread.currentThread();\r\n    String data;\r\n    while(th == thisThread) {\r\n      try {\r\n        while(reader.ready()) {\r\n          data = reader.readLine();\r\n          Atom sockAtom = objAtom.nthAtom(0);\r\n          mem.asyncLock();\r\n          Atom consAtom = mem.newAtom(new Functor(\".\",3));\r\n          if(data.equals(\"CLOSE\")) {\r\n            Atom closeAtom = mem.newAtom(new Functor(\"close\",1));\r\n            mem.newLink(closeAtom,0,consAtom,0);\r\n            mem.relinkAtomArgs(consAtom,1,sockAtom,0);\r\n            mem.newLink(consAtom,2,sockAtom,0);\r\n          } else {\r\n            Atom dataAtom = mem.newAtom(new StringFunctor(data));\r\n            mem.newLink(dataAtom,0,consAtom,0);\r\n            mem.relinkAtomArgs(consAtom,2,sockAtom,1);\r\n            mem.newLink(consAtom,1,sockAtom,1);\r\n          }\r\n          mem.asyncUnlock();\r\n        }\r\n      } catch(IOException e) {\r\n        e.printStackTrace();\r\n      }\r\n      try {\r\n        th.sleep(50);\r\n      } catch(InterruptedException e) {\r\n        e.printStackTrace();\r\n      }\r\n    }\r\n    th = null;\r\n  }\r\n  \r\n  /**\r\n   *  ReaderThread#stop()\r\n   */\r\n  public void stop() {\r\n    th = null;\r\n    try {\r\n      reader.close();\r\n      sock.close();\r\n      System.out.println(\"ReaderThread: stopped\");\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n    }\r\n  }\r\n}\r\n", 0, null);
	private static final Functor f1 = new Functor("module", 1, null);
}

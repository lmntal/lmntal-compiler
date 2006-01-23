package translated.module_dlmntal;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset605 extends Ruleset {
	private static final Ruleset605 theInstance = new Ruleset605();
	private Ruleset605() {}
	public static Ruleset605 getInstance() {
		return theInstance;
	}
	private int id = 605;
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
"(dlmntal.bind(Port, Cmd) :- int(Port) | dlmntal.bound(Cmd, Obj), '='(Obj, [:/*inline*/    // throws NumberFormatException     int port = Integer.parseInt(me.nth(0));    try {      // throws IOException and SecurityException      ServerSocket servSock = new ServerSocket(port);      Functor ssFunc = new ObjectFunctor(servSock);      Atom ssAtom = mem.newAtom(ssFunc);      mem.relinkAtomArgs(ssAtom,0,me,1);      me.nthAtom(0).remove();      me.remove();    } catch(IOException e) {      e.printStackTrace();      Atom failedAtom = mem.newAtom(new Functor(\"failed\",2));      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);      me.nthAtom(1).remove();      me.remove();    }  :](Port))), (dlmntal.bound('.'(accept(Send, Recv), T), Obj) :- dlmntal.bound('.'(accepting(Send, Recv), T), O), '='(O, [:/*inline*/    mem.makePerpetual();    ObjectFunctor ssFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    ServerSocket servSock = (ServerSocket)ssFunc.getObject();    AcceptThread at =       new AcceptThread(servSock,(Membrane)mem,me.nthAtom(0));    at.setName(\"at\");    mem.unifyAtomArgs(me,0,me,1);    me.remove();    at.start();  :](Obj))), (close @@ dlmntal.bound('.'(close, T), Obj) :- class(Obj, \"java.net.ServerSocket\") | closed(T), [:/*inline*/    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    ServerSocket servSock = (ServerSocket)objFunc.getObject();    try {      servSock.close();      System.out.println(\"ServerSocket: closed\");    } catch(IOException e) {      e.printStackTrace();    }    me.nthAtom(0).remove();    me.remove();  :](Obj)), (dlmntal.connect(Host, Port, Send, Recv) :- string(Host), int(Port) | dlmntal.socket(Send, Recv, Obj), '='(Obj, [:/*inline*/    String host = me.nth(0);    // throws NumberFormatException     int port = Integer.parseInt(me.nth(1));    try {      // throws UnknownHostException and SecurityException      InetAddress ip = InetAddress.getByName(host);      // throws IOException and SecurityException      Socket sock = new Socket(ip,port);      // throws IOException      BufferedReader reader =         new BufferedReader(new InputStreamReader(sock.getInputStream()));      // throws IOException      PrintWriter writer = new PrintWriter(sock.getOutputStream());      ReaderThread rt = new ReaderThread(sock,reader,(Membrane)mem);      Vector obj = new Vector(3);      obj.add(sock);      obj.add(writer);      obj.add(rt);      Functor objFunc = new ObjectFunctor(obj);      Atom objAtom = mem.newAtom(objFunc);      rt.setObjAtom(objAtom);      mem.relinkAtomArgs(objAtom,0,me,2);      mem.makePerpetual();      Thread t = new Thread(rt,\"rt\");      rt.start();      me.nthAtom(0).remove();      me.nthAtom(1).remove();      me.remove();    } catch(IOException e) {      e.printStackTrace();      Atom failedAtom = mem.newAtom(new Functor(\"failed\",4));      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);      mem.relinkAtomArgs(failedAtom,2,me.nthAtom(2),0);      mem.relinkAtomArgs(failedAtom,3,me.nthAtom(2),1);      me.nthAtom(2).remove();      me.remove();    }  :](Host, Port))), (send @@ dlmntal.socket('.'(H, T), Recv, Obj) :- string(H) | dlmntal.socket(T, Recv, O), '='(O, [:/*inline*/    String data = me.nth(0);    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(1).getFunctor();    Vector obj = (Vector)objFunc.getObject();    PrintWriter writer = (PrintWriter)obj.get(1);    writer.println(data);    writer.flush();    mem.unifyAtomArgs(me,1,me,2);    me.nthAtom(0).remove();    me.remove();  :](H, Obj))), (close @@ dlmntal.socket('.'(close, T), Recv, Obj) :- class(Obj, \"java.util.Vector\") | closed(T, Recv), [:/*inline*/    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    Vector obj = (Vector)objFunc.getObject();    PrintWriter writer = (PrintWriter)obj.get(1);    writer.println(\"CLOSE\");    writer.close();    ((ReaderThread)obj.get(2)).stop();    me.nthAtom(0).remove();    me.remove();  :](Obj)), (terminate @@ dlmntal.terminate :- [:/*inline*/    ((Membrane)mem).perpetual = false;    me.remove();  :])";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL224(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "bind");
			return true;
		}
		if (execL233(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "accept");
			return true;
		}
		if (execL245(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "close");
			return true;
		}
		if (execL257(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "connect");
			return true;
		}
		if (execL266(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "send");
			return true;
		}
		if (execL276(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "close");
			return true;
		}
		if (execL288(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "terminate");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL227(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "bind");
			return true;
		}
		if (execL239(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "accept");
			return true;
		}
		if (execL251(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "close");
			return true;
		}
		if (execL260(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "connect");
			return true;
		}
		if (execL270(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "send");
			return true;
		}
		if (execL282(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "close");
			return true;
		}
		if (execL291(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "terminate");
			return true;
		}
		return result;
	}
	public boolean execL291(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L291:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "terminate", "L287",var0,var1});
				} else if (execL287(var0,var1,nondeterministic)) {
					ret = true;
					break L291;
				}
			}
		}
		return ret;
	}
	public boolean execL287(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
L287:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var2, 6);
			ret = true;
			break L287;
		}
		return ret;
	}
	public boolean execL288(Object var0, Object var1, boolean nondeterministic) {
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
L288:
		{
			if (execL290(var0, var1, nondeterministic)) {
				ret = true;
				break L288;
			}
		}
		return ret;
	}
	public boolean execL290(Object var0, Object var1, boolean nondeterministic) {
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
L290:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "terminate", "L287",var0,var1});
					} else if (execL287(var0,var1,nondeterministic)) {
						ret = true;
						break L290;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL282(Object var0, boolean nondeterministic) {
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
L282:
		{
			var4 = new Atom(null, f2);
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								var5 = link.getAtom();
								if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_dlmntal.", "");
										var6 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
										if (execL275(var0,var1,var3,var2,var4,var5,nondeterministic)) {
											ret = true;
											break L282;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL275(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
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
L275:
		{
			link = ((Atom)var3).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(1);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 1,
				(Link)var10 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var8, 5);
			ret = true;
			break L275;
		}
		return ret;
	}
	public boolean execL276(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
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
L276:
		{
			if (execL278(var0, var1, nondeterministic)) {
				ret = true;
				break L276;
			}
			if (execL280(var0, var1, nondeterministic)) {
				ret = true;
				break L276;
			}
		}
		return ret;
	}
	public boolean execL280(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
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
L280:
		{
			var11 = new Atom(null, f2);
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var12 = link.getAtom();
						if (!(!(((Atom)var12).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var12).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_dlmntal.", "");
								var13 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var13).getFunctor().equals(((Atom)var11).getFunctor()))) {
								if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(1);
									var7 = link;
									link = ((Atom)var5).getArg(2);
									var8 = link;
									link = ((Atom)var5).getArg(0);
									if (!(link.getPos() != 0)) {
										var9 = link.getAtom();
										if (!(!(f3).equals(((Atom)var9).getFunctor()))) {
											link = ((Atom)var9).getArg(0);
											var10 = link;
											if (execL275(var0,var9,var1,var5,var11,var12,nondeterministic)) {
												ret = true;
												break L280;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL278(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
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
L278:
		{
			var11 = new Atom(null, f2);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f4).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f5).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(2);
									var12 = link.getAtom();
									if (!(!(((Atom)var12).getFunctor() instanceof ObjectFunctor))) {
										{
											Object obj = ((ObjectFunctor)((Atom)var12).getFunctor()).getObject();
											String className = obj.getClass().getName();
											className = className.replaceAll("translated.module_dlmntal.", "");
											var13 = new Atom(null, new StringFunctor( className ));
										}
										if (!(!((Atom)var13).getFunctor().equals(((Atom)var11).getFunctor()))) {
											if (execL275(var0,var1,var7,var3,var11,var12,nondeterministic)) {
												ret = true;
												break L278;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL270(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L270:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
							if (execL265(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L270;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL265(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
L265:
		{
			link = ((Atom)var2).getArg(1);
			var7 = link;
			link = ((Atom)var1).getArg(2);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f8;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var7 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var6), 2 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var9 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var6, 4);
				try {
					Class c = Class.forName("translated.Module_dlmntal");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				}
			ret = true;
			break L265;
		}
		return ret;
	}
	public boolean execL266(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
L266:
		{
			if (execL268(var0, var1, nondeterministic)) {
				ret = true;
				break L266;
			}
		}
		return ret;
	}
	public boolean execL268(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
L268:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							link = ((Atom)var5).getArg(0);
							var9 = link.getAtom();
							if (((Atom)var9).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var9).getFunctor()).getObject() instanceof String) {
								if (execL265(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L268;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL260(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L260:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL256(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L260;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL256(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
L256:
		{
			link = ((Atom)var1).getArg(2);
			var8 = link;
			link = ((Atom)var1).getArg(3);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f10;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var9 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var7), 2 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var5), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var7, 3);
				try {
					Class c = Class.forName("translated.Module_dlmntal");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				}
			ret = true;
			break L256;
		}
		return ret;
	}
	public boolean execL257(Object var0, Object var1, boolean nondeterministic) {
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
L257:
		{
			if (execL259(var0, var1, nondeterministic)) {
				ret = true;
				break L257;
			}
		}
		return ret;
	}
	public boolean execL259(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
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
L259:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
						if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
							if (execL256(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L259;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL251(Object var0, boolean nondeterministic) {
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
L251:
		{
			var4 = new Atom(null, f11);
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f12).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(1);
								var5 = link.getAtom();
								if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_dlmntal.", "");
										var6 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
										if (execL244(var0,var1,var3,var2,var4,var5,nondeterministic)) {
											ret = true;
											break L251;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL244(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
L244:
		{
			link = ((Atom)var3).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f13;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var9 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var8, 2);
			ret = true;
			break L244;
		}
		return ret;
	}
	public boolean execL245(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
L245:
		{
			if (execL247(var0, var1, nondeterministic)) {
				ret = true;
				break L245;
			}
			if (execL249(var0, var1, nondeterministic)) {
				ret = true;
				break L245;
			}
		}
		return ret;
	}
	public boolean execL249(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
L249:
		{
			var10 = new Atom(null, f11);
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var11 = link.getAtom();
						if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_dlmntal.", "");
								var12 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
								if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
									link = ((Atom)var4).getArg(0);
									var5 = link;
									link = ((Atom)var4).getArg(1);
									var6 = link;
									link = ((Atom)var4).getArg(2);
									var7 = link;
									link = ((Atom)var4).getArg(0);
									if (!(link.getPos() != 0)) {
										var8 = link.getAtom();
										if (!(!(f3).equals(((Atom)var8).getFunctor()))) {
											link = ((Atom)var8).getArg(0);
											var9 = link;
											if (execL244(var0,var8,var1,var4,var10,var11,nondeterministic)) {
												ret = true;
												break L249;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL247(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
L247:
		{
			var10 = new Atom(null, f11);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f4).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f12).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(1);
									var11 = link.getAtom();
									if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
										{
											Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
											String className = obj.getClass().getName();
											className = className.replaceAll("translated.module_dlmntal.", "");
											var12 = new Atom(null, new StringFunctor( className ));
										}
										if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
											if (execL244(var0,var1,var7,var3,var10,var11,nondeterministic)) {
												ret = true;
												break L247;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL239(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L239:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f12).equals(((Atom)var3).getFunctor()))) {
								if (execL232(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L239;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL232(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
L232:
		{
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var1).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(1);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f16;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var8 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var9 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var3), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var2), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var7), 1 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var11 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var7, 1);
				try {
					Class c = Class.forName("translated.Module_dlmntal");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				}
			ret = true;
			break L232;
		}
		return ret;
	}
	public boolean execL233(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
L233:
		{
			if (execL235(var0, var1, nondeterministic)) {
				ret = true;
				break L233;
			}
			if (execL237(var0, var1, nondeterministic)) {
				ret = true;
				break L233;
			}
		}
		return ret;
	}
	public boolean execL237(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
L237:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f15).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									if (execL232(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L237;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL235(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
L235:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							link = ((Atom)var5).getArg(2);
							if (!(link.getPos() != 0)) {
								var9 = link.getAtom();
								if (!(!(f12).equals(((Atom)var9).getFunctor()))) {
									link = ((Atom)var9).getArg(0);
									var10 = link;
									link = ((Atom)var9).getArg(1);
									var11 = link;
									if (execL232(var0,var1,var9,var5,nondeterministic)) {
										ret = true;
										break L235;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL227(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
L227:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL223(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L227;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL223(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L223:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f12;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var6 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodedlmntal.run((Atom)var5, 0);
				try {
					Class c = Class.forName("translated.Module_dlmntal");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module dlmntal");
				}
			ret = true;
			break L223;
		}
		return ret;
	}
	public boolean execL224(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
L224:
		{
			if (execL226(var0, var1, nondeterministic)) {
				ret = true;
				break L224;
			}
		}
		return ret;
	}
	public boolean execL226(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
L226:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL223(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L226;
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f10 = new Functor("/*inline*/\r\n    String host = me.nth(0);\r\n    // throws NumberFormatException \r\n    int port = Integer.parseInt(me.nth(1));\r\n    try {\r\n      // throws UnknownHostException and SecurityException\r\n      InetAddress ip = InetAddress.getByName(host);\r\n      // throws IOException and SecurityException\r\n      Socket sock = new Socket(ip,port);\r\n      // throws IOException\r\n      BufferedReader reader = \r\n        new BufferedReader(new InputStreamReader(sock.getInputStream()));\r\n      // throws IOException\r\n      PrintWriter writer = new PrintWriter(sock.getOutputStream());\r\n      ReaderThread rt = new ReaderThread(sock,reader,(Membrane)mem);\r\n      Vector obj = new Vector(3);\r\n      obj.add(sock);\r\n      obj.add(writer);\r\n      obj.add(rt);\r\n      Functor objFunc = new ObjectFunctor(obj);\r\n      Atom objAtom = mem.newAtom(objFunc);\r\n      rt.setObjAtom(objAtom);\r\n      mem.relinkAtomArgs(objAtom,0,me,2);\r\n      mem.makePerpetual();\r\n      Thread t = new Thread(rt,\"rt\");\r\n      rt.start();\r\n      me.nthAtom(0).remove();\r\n      me.nthAtom(1).remove();\r\n      me.remove();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n      Atom failedAtom = mem.newAtom(new Functor(\"failed\",4));\r\n      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);\r\n      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);\r\n      mem.relinkAtomArgs(failedAtom,2,me.nthAtom(2),0);\r\n      mem.relinkAtomArgs(failedAtom,3,me.nthAtom(2),1);\r\n      me.nthAtom(2).remove();\r\n      me.remove();\r\n    }\r\n  ", 3, null);
	private static final Functor f12 = new Functor("bound", 2, "dlmntal");
	private static final Functor f14 = new StringFunctor("/*inline*/\r\n    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    ServerSocket servSock = (ServerSocket)objFunc.getObject();\r\n    try {\r\n      servSock.close();\r\n      System.out.println(\"ServerSocket: closed\");\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n    }\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ");
	private static final Functor f18 = new Functor("bind", 2, "dlmntal");
	private static final Functor f3 = new Functor("close", 1, null);
	private static final Functor f11 = new StringFunctor("java.net.ServerSocket");
	private static final Functor f1 = new Functor("/*inline*/\r\n    ((Membrane)mem).perpetual = false;\r\n    me.remove();\r\n  ", 0, null);
	private static final Functor f9 = new Functor("connect", 4, "dlmntal");
	private static final Functor f15 = new Functor("accept", 3, null);
	private static final Functor f0 = new Functor("terminate", 0, "dlmntal");
	private static final Functor f6 = new Functor("closed", 2, null);
	private static final Functor f4 = new Functor(".", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n    String data = me.nth(0);\r\n    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(1).getFunctor();\r\n    Vector obj = (Vector)objFunc.getObject();\r\n    PrintWriter writer = (PrintWriter)obj.get(1);\r\n    writer.println(data);\r\n    writer.flush();\r\n    mem.unifyAtomArgs(me,1,me,2);\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ", 3, null);
	private static final Functor f13 = new Functor("closed", 1, null);
	private static final Functor f7 = new StringFunctor("/*inline*/\r\n    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    Vector obj = (Vector)objFunc.getObject();\r\n    PrintWriter writer = (PrintWriter)obj.get(1);\r\n    writer.println(\"CLOSE\");\r\n    writer.close();\r\n    ((ReaderThread)obj.get(2)).stop();\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ");
	private static final Functor f17 = new Functor("/*inline*/\r\n    mem.makePerpetual();\r\n    ObjectFunctor ssFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    ServerSocket servSock = (ServerSocket)ssFunc.getObject();\r\n    AcceptThread at = \r\n      new AcceptThread(servSock,(Membrane)mem,me.nthAtom(0));\r\n    at.setName(\"at\");\r\n    mem.unifyAtomArgs(me,0,me,1);\r\n    me.remove();\r\n    at.start();\r\n  ", 2, null);
	private static final Functor f16 = new Functor("accepting", 3, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n    // throws NumberFormatException \r\n    int port = Integer.parseInt(me.nth(0));\r\n    try {\r\n      // throws IOException and SecurityException\r\n      ServerSocket servSock = new ServerSocket(port);\r\n      Functor ssFunc = new ObjectFunctor(servSock);\r\n      Atom ssAtom = mem.newAtom(ssFunc);\r\n      mem.relinkAtomArgs(ssAtom,0,me,1);\r\n      me.nthAtom(0).remove();\r\n      me.remove();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n      Atom failedAtom = mem.newAtom(new Functor(\"failed\",2));\r\n      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);\r\n      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);\r\n      me.nthAtom(1).remove();\r\n      me.remove();\r\n    }\r\n  ", 2, null);
	private static final Functor f2 = new StringFunctor("java.util.Vector");
	private static final Functor f5 = new Functor("socket", 3, "dlmntal");
}

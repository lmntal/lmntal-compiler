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
"(bind @@ dlmntal.bind(Port, Cmd) :- int(Port) | dlmntal.bound(Cmd, Obj), '='(Obj, [:/*inline*/    // throws NumberFormatException     int port = Integer.parseInt(me.nth(0));    try {      // throws IOException and SecurityException      ServerSocket servSock = new ServerSocket(port);      Functor ssFunc = new ObjectFunctor(servSock);      Atom ssAtom = mem.newAtom(ssFunc);      mem.relinkAtomArgs(ssAtom,0,me,1);      me.nthAtom(0).remove();      me.remove();    } catch(IOException e) {      e.printStackTrace();      Atom failedAtom = mem.newAtom(new Functor(\"failed\",2));      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);      me.nthAtom(1).remove();      me.remove();    }  :](Port))), (bound @@ dlmntal.bound('.'(accept(Send, Recv), T), Obj) :- dlmntal.bound('.'(accepting(Send, Recv), T), O), '='(O, [:/*inline*/    mem.makePerpetual();    ObjectFunctor ssFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    ServerSocket servSock = (ServerSocket)ssFunc.getObject();    AcceptThread at =       new AcceptThread(servSock,(Membrane)mem,me.nthAtom(0));    at.setName(\"at\");    mem.unifyAtomArgs(me,0,me,1);    me.remove();    at.start();  :](Obj))), (close @@ dlmntal.bound('.'(close, T), Obj) :- class(Obj, \"java.net.ServerSocket\") | closed(T), [:/*inline*/    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    ServerSocket servSock = (ServerSocket)objFunc.getObject();    try {      servSock.close();    } catch(IOException e) {      e.printStackTrace();    }    me.nthAtom(0).remove();    me.remove();  :](Obj)), (connect @@ dlmntal.connect(Host, Port, Send, Recv) :- string(Host), int(Port) | dlmntal.socket(Send, Recv, Obj), '='(Obj, [:/*inline*/    String host = me.nth(0);    // throws NumberFormatException     int port = Integer.parseInt(me.nth(1));    try {      // throws UnknownHostException and SecurityException      InetAddress ip = InetAddress.getByName(host);      // throws IOException and SecurityException      Socket sock = new Socket(ip,port);      // throws IOException      BufferedReader reader =         new BufferedReader(new InputStreamReader(sock.getInputStream()));      // throws IOException      PrintWriter writer = new PrintWriter(sock.getOutputStream());      ReaderThread rt = new ReaderThread(sock,reader,(Membrane)mem);      Vector obj = new Vector(3);      obj.add(sock);      obj.add(writer);      obj.add(rt);      Functor objFunc = new ObjectFunctor(obj);      Atom objAtom = mem.newAtom(objFunc);      rt.setObjAtom(objAtom);      mem.relinkAtomArgs(objAtom,0,me,2);      mem.makePerpetual();      Thread t = new Thread(rt,\"rt\");      rt.start();      me.nthAtom(0).remove();      me.nthAtom(1).remove();      me.remove();    } catch(IOException e) {      e.printStackTrace();      Atom failedAtom = mem.newAtom(new Functor(\"failed\",4));      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);      mem.relinkAtomArgs(failedAtom,2,me.nthAtom(2),0);      mem.relinkAtomArgs(failedAtom,3,me.nthAtom(2),1);      me.nthAtom(2).remove();      me.remove();    }  :](Host, Port))), (send @@ dlmntal.socket('.'(H, T), Recv, Obj) :- string(H) | dlmntal.socket(T, Recv, O), '='(O, [:/*inline*/    String data = me.nth(0);    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(1).getFunctor();    Vector obj = (Vector)objFunc.getObject();    PrintWriter writer = (PrintWriter)obj.get(1);    writer.println(data);    writer.flush();    mem.unifyAtomArgs(me,1,me,2);    me.nthAtom(0).remove();    me.remove();  :](H, Obj))), (close @@ dlmntal.socket('.'(close, T), Recv, Obj) :- class(Obj, \"java.util.Vector\") | closed(T, Recv), [:/*inline*/    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    Vector obj = (Vector)objFunc.getObject();    PrintWriter writer = (PrintWriter)obj.get(1);    writer.println(\"CLOSE\");    writer.close();    ((ReaderThread)obj.get(2)).stop();    me.nthAtom(0).remove();    me.remove();  :](Obj)), (terminate @@ dlmntal.terminate :- [:/*inline*/    ((Membrane)mem).perpetual = false;    me.remove();  :])";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL292(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "bind");
			return true;
		}
		if (execL301(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "bound");
			return true;
		}
		if (execL313(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "close");
			return true;
		}
		if (execL325(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "connect");
			return true;
		}
		if (execL334(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "send");
			return true;
		}
		if (execL344(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "close");
			return true;
		}
		if (execL356(mem, atom, false)) {
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
		if (execL295(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "bind");
			return true;
		}
		if (execL307(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "bound");
			return true;
		}
		if (execL319(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "close");
			return true;
		}
		if (execL328(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "connect");
			return true;
		}
		if (execL338(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "send");
			return true;
		}
		if (execL350(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "close");
			return true;
		}
		if (execL359(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "terminate");
			return true;
		}
		return result;
	}
	public boolean execL359(Object var0, boolean nondeterministic) {
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
L359:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "terminate", "L355",var0,var1});
				} else if (execL355(var0,var1,nondeterministic)) {
					ret = true;
					break L359;
				}
			}
		}
		return ret;
	}
	public boolean execL355(Object var0, Object var1, boolean nondeterministic) {
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
L355:
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
			break L355;
		}
		return ret;
	}
	public boolean execL356(Object var0, Object var1, boolean nondeterministic) {
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
L356:
		{
			if (execL358(var0, var1, nondeterministic)) {
				ret = true;
				break L356;
			}
		}
		return ret;
	}
	public boolean execL358(Object var0, Object var1, boolean nondeterministic) {
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
L358:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "terminate", "L355",var0,var1});
					} else if (execL355(var0,var1,nondeterministic)) {
						ret = true;
						break L358;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL350(Object var0, boolean nondeterministic) {
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
L350:
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
										if (execL343(var0,var1,var3,var2,var4,var5,nondeterministic)) {
											ret = true;
											break L350;
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
	public boolean execL343(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L343:
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
			break L343;
		}
		return ret;
	}
	public boolean execL344(Object var0, Object var1, boolean nondeterministic) {
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
L344:
		{
			if (execL346(var0, var1, nondeterministic)) {
				ret = true;
				break L344;
			}
			if (execL348(var0, var1, nondeterministic)) {
				ret = true;
				break L344;
			}
		}
		return ret;
	}
	public boolean execL348(Object var0, Object var1, boolean nondeterministic) {
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
L348:
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
											if (execL343(var0,var9,var1,var5,var11,var12,nondeterministic)) {
												ret = true;
												break L348;
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
	public boolean execL346(Object var0, Object var1, boolean nondeterministic) {
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
L346:
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
											if (execL343(var0,var1,var7,var3,var11,var12,nondeterministic)) {
												ret = true;
												break L346;
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
	public boolean execL338(Object var0, boolean nondeterministic) {
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
L338:
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
							if (execL333(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L338;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL333(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L333:
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
			break L333;
		}
		return ret;
	}
	public boolean execL334(Object var0, Object var1, boolean nondeterministic) {
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
L334:
		{
			if (execL336(var0, var1, nondeterministic)) {
				ret = true;
				break L334;
			}
		}
		return ret;
	}
	public boolean execL336(Object var0, Object var1, boolean nondeterministic) {
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
L336:
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
								if (execL333(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L336;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL328(Object var0, boolean nondeterministic) {
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
L328:
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
						if (execL324(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L328;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL324(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L324:
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
			break L324;
		}
		return ret;
	}
	public boolean execL325(Object var0, Object var1, boolean nondeterministic) {
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
L325:
		{
			if (execL327(var0, var1, nondeterministic)) {
				ret = true;
				break L325;
			}
		}
		return ret;
	}
	public boolean execL327(Object var0, Object var1, boolean nondeterministic) {
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
L327:
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
							if (execL324(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L327;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL319(Object var0, boolean nondeterministic) {
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
L319:
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
										if (execL312(var0,var1,var3,var2,var4,var5,nondeterministic)) {
											ret = true;
											break L319;
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
	public boolean execL312(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L312:
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
			break L312;
		}
		return ret;
	}
	public boolean execL313(Object var0, Object var1, boolean nondeterministic) {
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
L313:
		{
			if (execL315(var0, var1, nondeterministic)) {
				ret = true;
				break L313;
			}
			if (execL317(var0, var1, nondeterministic)) {
				ret = true;
				break L313;
			}
		}
		return ret;
	}
	public boolean execL317(Object var0, Object var1, boolean nondeterministic) {
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
L317:
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
											if (execL312(var0,var8,var1,var4,var10,var11,nondeterministic)) {
												ret = true;
												break L317;
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
	public boolean execL315(Object var0, Object var1, boolean nondeterministic) {
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
L315:
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
											if (execL312(var0,var1,var7,var3,var10,var11,nondeterministic)) {
												ret = true;
												break L315;
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
	public boolean execL307(Object var0, boolean nondeterministic) {
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
L307:
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
								if (execL300(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L307;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL300(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L300:
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
			break L300;
		}
		return ret;
	}
	public boolean execL301(Object var0, Object var1, boolean nondeterministic) {
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
L301:
		{
			if (execL303(var0, var1, nondeterministic)) {
				ret = true;
				break L301;
			}
			if (execL305(var0, var1, nondeterministic)) {
				ret = true;
				break L301;
			}
		}
		return ret;
	}
	public boolean execL305(Object var0, Object var1, boolean nondeterministic) {
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
L305:
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
									if (execL300(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L305;
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
	public boolean execL303(Object var0, Object var1, boolean nondeterministic) {
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
L303:
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
									if (execL300(var0,var1,var9,var5,nondeterministic)) {
										ret = true;
										break L303;
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
	public boolean execL295(Object var0, boolean nondeterministic) {
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
L295:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL291(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L295;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL291(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L291:
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
			break L291;
		}
		return ret;
	}
	public boolean execL292(Object var0, Object var1, boolean nondeterministic) {
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
L292:
		{
			if (execL294(var0, var1, nondeterministic)) {
				ret = true;
				break L292;
			}
		}
		return ret;
	}
	public boolean execL294(Object var0, Object var1, boolean nondeterministic) {
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
L294:
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
						if (execL291(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L294;
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f10 = new Functor("/*inline*/\r\n    String host = me.nth(0);\r\n    // throws NumberFormatException \r\n    int port = Integer.parseInt(me.nth(1));\r\n    try {\r\n      // throws UnknownHostException and SecurityException\r\n      InetAddress ip = InetAddress.getByName(host);\r\n      // throws IOException and SecurityException\r\n      Socket sock = new Socket(ip,port);\r\n      // throws IOException\r\n      BufferedReader reader = \r\n        new BufferedReader(new InputStreamReader(sock.getInputStream()));\r\n      // throws IOException\r\n      PrintWriter writer = new PrintWriter(sock.getOutputStream());\r\n      ReaderThread rt = new ReaderThread(sock,reader,(Membrane)mem);\r\n      Vector obj = new Vector(3);\r\n      obj.add(sock);\r\n      obj.add(writer);\r\n      obj.add(rt);\r\n      Functor objFunc = new ObjectFunctor(obj);\r\n      Atom objAtom = mem.newAtom(objFunc);\r\n      rt.setObjAtom(objAtom);\r\n      mem.relinkAtomArgs(objAtom,0,me,2);\r\n      mem.makePerpetual();\r\n      Thread t = new Thread(rt,\"rt\");\r\n      rt.start();\r\n      me.nthAtom(0).remove();\r\n      me.nthAtom(1).remove();\r\n      me.remove();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n      Atom failedAtom = mem.newAtom(new Functor(\"failed\",4));\r\n      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);\r\n      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);\r\n      mem.relinkAtomArgs(failedAtom,2,me.nthAtom(2),0);\r\n      mem.relinkAtomArgs(failedAtom,3,me.nthAtom(2),1);\r\n      me.nthAtom(2).remove();\r\n      me.remove();\r\n    }\r\n  ", 3, null);
	private static final Functor f12 = new Functor("bound", 2, "dlmntal");
	private static final Functor f14 = new StringFunctor("/*inline*/\r\n    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    ServerSocket servSock = (ServerSocket)objFunc.getObject();\r\n    try {\r\n      servSock.close();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n    }\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ");
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

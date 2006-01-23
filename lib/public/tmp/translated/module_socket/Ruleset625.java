package translated.module_socket;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset625 extends Ruleset {
	private static final Ruleset625 theInstance = new Ruleset625();
	private Ruleset625() {}
	public static Ruleset625 getInstance() {
		return theInstance;
	}
	private int id = 625;
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
	private String encodedRuleset = 
"(socket.create(Port, C) :- int(Port) | socket.serversocket(O, C), '='(O, [:/*inline*/\t\ttry {\t\t\tServerSocket ss = new ServerSocket(Integer.parseInt(me.nth(0)));\t\t\tAtom o = mem.newAtom(new ObjectFunctor(ss));\t\t\tmem.relink(o, 0, me, 1);\t\t\t\t\t\tme.nthAtom(0).remove();\t\t\tme.remove();\t\t} catch (IOException e) {\t\t\te.printStackTrace();\t\t}\t:](Port))), (socket.serversocket(O, '.'(accept(A), C)) :- socket.serversocket(O2, '.'(socket.accepting(A), C)), '='(O2, [:/*inline*/\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\tAcceptThread t = new AcceptThread(ss,me.nthAtom(1));\t\tmem.makePerpetual();\t\tt.start();\t\tmem.unifyAtomArgs(me, 0, me, 1);\t\tme.remove();\t:](O))), (socket.serversocket(O, '.'(close, C)) :- '='(C, closed), [:/*inline*/\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\ttry {\t\t\tss.close();\t\t} catch (IOException e) {\t\t\te.printStackTrace();\t\t}\t\tme.nthAtom(0).remove();\t\tme.remove();\t:](O)), ('='(S, socket.connect(Addr, Port)) :- unary(Addr), int(Port) | '='(S, socket.socket(SO, nil, '[]')), '='(SO, [:/*inline*/\t\ttry {\t\t\tString addr = me.nth(0);\t\t\tint port = Integer.parseInt(me.nth(1));\t\t\tReadThread sr = new ReadThread(addr, port);\t\t\tFunctor func = new ObjectFunctor(sr);\t\t\tAtom so = mem.newAtom(func);\t\t\tsr.me = so;\t\t\tmem.relink(so, 0, me, 2);\t\t\tme.nthAtom(0).remove();\t\t\tme.nthAtom(1).remove();\t\t\tme.remove();\t\t} catch(Exception e) {\t\t\te.printStackTrace();\t\t}\t:](Addr, Port))), (socket.socket(SO, nil, Out, '.'(is(IS), C)) :- socket.socket(SS, IS, Out, C), '='(SS, [:/*inline*/\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\tsr.start();\t\tmem.unifyAtomArgs(me, 0, me, 1);\t\tme.remove();\t:](SO))), (socket.socket(SO, In, '[]', '.'(os(OS), C)) :- socket.socket(SO, In, OS, C)), (socket.socket(SO, In, '.'(D, Out), C) :- string(D) | socket.socket(SS, In, Out, C), '='(SS, [:/*inline*/\t\ttry {\t\t\tString data = (String)((StringFunctor)me.nthAtom(0).getFunctor()).getObject();\t\t\tSocket soc = ((ReadThread)((ObjectFunctor)me.nthAtom(1).getFunctor()).getObject()).socket;\t\t\tBufferedWriter writer = new BufferedWriter(\t\t\t\t\tnew OutputStreamWriter(soc.getOutputStream()));\t\t\twriter.write(data);\t\t\twriter.write(\"\\n\");\t\t\twriter.flush();\t\t\tmem.unifyAtomArgs(me, 1, me, 2);\t\t\tme.nthAtom(0).remove();\t\t\tme.remove();\t\t} catch (Exception e) {\t\t\te.printStackTrace();\t\t}\t:](D, SO))), (socket.socket(SO, In, '[]', '.'(close_is, C)) :- socket.socket(SS, In, '[]', C), '='(SS, [:/*inline*/\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\tsr.flgClosing = true;\t\tmem.unifyAtomArgs(me, 0, me, 1);\t\tme.remove();\t:](SO))), (socket.socket(SO, nil, '[]', '.'(close, C)) :- '='(C, closed), [:/*inline*/\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\tSocket soc = sr.socket;\t\ttry {\t\t\tsoc.close();\t\t} catch (IOException e) {\t\t\te.printStackTrace();\t\t}\t\tme.nthAtom(0).remove();\t\tme.remove();\t:](SO))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1885(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "create");
			return true;
		}
		if (execL1894(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "accept");
			return true;
		}
		if (execL1906(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "close");
			return true;
		}
		if (execL1918(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "connect");
			return true;
		}
		if (execL1927(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "nil");
			return true;
		}
		if (execL1941(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "os");
			return true;
		}
		if (execL1954(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "socket");
			return true;
		}
		if (execL1964(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "close_is");
			return true;
		}
		if (execL1977(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@625", "nil");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1888(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "create");
			return true;
		}
		if (execL1900(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "accept");
			return true;
		}
		if (execL1912(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "close");
			return true;
		}
		if (execL1921(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "connect");
			return true;
		}
		if (execL1935(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "nil");
			return true;
		}
		if (execL1948(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "os");
			return true;
		}
		if (execL1958(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "socket");
			return true;
		}
		if (execL1971(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "close_is");
			return true;
		}
		if (execL1986(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@625", "nil");
			return true;
		}
		return result;
	}
	public boolean execL1986(Object var0, boolean nondeterministic) {
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
L1986:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(3);
							if (!(link.getPos() != 2)) {
								var4 = link.getAtom();
								if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
									link = ((Atom)var4).getArg(0);
									if (!(link.getPos() != 0)) {
										var5 = link.getAtom();
										if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
											if (!(!(f4).equals(((Atom)var3).getFunctor()))) {
												if (execL1976(var0,var1,var5,var2,var3,var4,nondeterministic)) {
													ret = true;
													break L1986;
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
		}
		return ret;
	}
	public boolean execL1976(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L1976:
		{
			link = ((Atom)var5).getArg(1);
			var8 = link;
			link = ((Atom)var3).getArg(0);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var9 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 7);
			ret = true;
			break L1976;
		}
		return ret;
	}
	public boolean execL1977(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
		Object var15 = null;
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
L1977:
		{
			if (execL1979(var0, var1, nondeterministic)) {
				ret = true;
				break L1977;
			}
			if (execL1981(var0, var1, nondeterministic)) {
				ret = true;
				break L1977;
			}
			if (execL1983(var0, var1, nondeterministic)) {
				ret = true;
				break L1977;
			}
		}
		return ret;
	}
	public boolean execL1983(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
		Object var15 = null;
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
L1983:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						if (!(link.getPos() != 0)) {
							var8 = link.getAtom();
							link = ((Atom)var1).getArg(3);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
								if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
									link = ((Atom)var10).getArg(0);
									var11 = link;
									link = ((Atom)var10).getArg(1);
									var12 = link;
									link = ((Atom)var10).getArg(2);
									var13 = link;
									link = ((Atom)var10).getArg(0);
									if (!(link.getPos() != 0)) {
										var14 = link.getAtom();
										if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
											link = ((Atom)var14).getArg(0);
											var15 = link;
											if (!(!(f4).equals(((Atom)var8).getFunctor()))) {
												link = ((Atom)var8).getArg(0);
												var9 = link;
												if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
													link = ((Atom)var6).getArg(0);
													var7 = link;
													if (execL1976(var0,var6,var14,var1,var8,var10,nondeterministic)) {
														ret = true;
														break L1983;
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
			}
		}
		return ret;
	}
	public boolean execL1981(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
		Object var15 = null;
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
L1981:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 3)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(3);
									var11 = link;
									link = ((Atom)var7).getArg(1);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										link = ((Atom)var7).getArg(2);
										if (!(link.getPos() != 0)) {
											var14 = link.getAtom();
											if (!(!(f4).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												if (!(!(f0).equals(((Atom)var12).getFunctor()))) {
													link = ((Atom)var12).getArg(0);
													var13 = link;
													if (execL1976(var0,var12,var1,var7,var14,var3,nondeterministic)) {
														ret = true;
														break L1981;
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
			}
		}
		return ret;
	}
	public boolean execL1979(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
		Object var15 = null;
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
L1979:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								link = ((Atom)var3).getArg(3);
								if (!(link.getPos() != 2)) {
									var10 = link.getAtom();
									if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
										link = ((Atom)var10).getArg(0);
										var11 = link;
										link = ((Atom)var10).getArg(1);
										var12 = link;
										link = ((Atom)var10).getArg(2);
										var13 = link;
										link = ((Atom)var10).getArg(0);
										if (!(link.getPos() != 0)) {
											var14 = link.getAtom();
											if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												if (!(!(f4).equals(((Atom)var8).getFunctor()))) {
													link = ((Atom)var8).getArg(0);
													var9 = link;
													if (execL1976(var0,var1,var14,var3,var8,var10,nondeterministic)) {
														ret = true;
														break L1979;
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
			}
		}
		return ret;
	}
	public boolean execL1971(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1971:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
										if (execL1963(var0,var1,var3,var4,var2,nondeterministic)) {
											ret = true;
											break L1971;
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
	public boolean execL1963(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1963:
		{
			link = ((Atom)var4).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(0);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var2), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 3,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var10 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 6);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1963;
		}
		return ret;
	}
	public boolean execL1964(Object var0, Object var1, boolean nondeterministic) {
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
L1964:
		{
			if (execL1966(var0, var1, nondeterministic)) {
				ret = true;
				break L1964;
			}
			if (execL1968(var0, var1, nondeterministic)) {
				ret = true;
				break L1964;
			}
		}
		return ret;
	}
	public boolean execL1968(Object var0, Object var1, boolean nondeterministic) {
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
L1968:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						if (!(link.getPos() != 2)) {
							var8 = link.getAtom();
							if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(2);
								var11 = link;
								link = ((Atom)var8).getArg(0);
								if (!(link.getPos() != 0)) {
									var12 = link.getAtom();
									if (!(!(f7).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										if (!(!(f4).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1963(var0,var12,var1,var6,var8,nondeterministic)) {
												ret = true;
												break L1968;
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
	public boolean execL1966(Object var0, Object var1, boolean nondeterministic) {
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
L1966:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 3)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(3);
									var11 = link;
									link = ((Atom)var7).getArg(2);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										if (!(!(f4).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											if (execL1963(var0,var1,var7,var12,var3,nondeterministic)) {
												ret = true;
												break L1966;
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
	public boolean execL1958(Object var0, boolean nondeterministic) {
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
L1958:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
							if (execL1953(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1958;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1953(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1953:
		{
			link = ((Atom)var2).getArg(1);
			var8 = link;
			link = ((Atom)var1).getArg(0);
			var10 = link;
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
			func = f9;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 2 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 2,
				(Link)var8 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var6, 5);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1953;
		}
		return ret;
	}
	public boolean execL1954(Object var0, Object var1, boolean nondeterministic) {
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
L1954:
		{
			if (execL1956(var0, var1, nondeterministic)) {
				ret = true;
				break L1954;
			}
		}
		return ret;
	}
	public boolean execL1956(Object var0, Object var1, boolean nondeterministic) {
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
L1956:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							link = ((Atom)var6).getArg(2);
							var9 = link;
							link = ((Atom)var6).getArg(0);
							var10 = link.getAtom();
							if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
								if (execL1953(var0,var1,var6,var10,nondeterministic)) {
									ret = true;
									break L1956;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1948(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1948:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
										if (execL1940(var0,var1,var3,var4,var2,nondeterministic)) {
											ret = true;
											break L1948;
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
	public boolean execL1940(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1940:
		{
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var4).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var8 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 3,
				(Link)var9 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1940;
		}
		return ret;
	}
	public boolean execL1941(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1941:
		{
			if (execL1943(var0, var1, nondeterministic)) {
				ret = true;
				break L1941;
			}
			if (execL1945(var0, var1, nondeterministic)) {
				ret = true;
				break L1941;
			}
		}
		return ret;
	}
	public boolean execL1945(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1945:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						if (!(link.getPos() != 2)) {
							var8 = link.getAtom();
							if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(2);
								var11 = link;
								link = ((Atom)var8).getArg(0);
								if (!(link.getPos() != 1)) {
									var12 = link.getAtom();
									if (!(!(f10).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										link = ((Atom)var12).getArg(1);
										var14 = link;
										if (!(!(f4).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1940(var0,var12,var1,var6,var8,nondeterministic)) {
												ret = true;
												break L1945;
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
	public boolean execL1943(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1943:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 3)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(3);
									var12 = link;
									link = ((Atom)var8).getArg(2);
									if (!(link.getPos() != 0)) {
										var13 = link.getAtom();
										if (!(!(f4).equals(((Atom)var13).getFunctor()))) {
											link = ((Atom)var13).getArg(0);
											var14 = link;
											if (execL1940(var0,var1,var8,var13,var4,nondeterministic)) {
												ret = true;
												break L1943;
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
	public boolean execL1935(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L1935:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(3);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 1)) {
									var4 = link.getAtom();
									if (!(!(f11).equals(((Atom)var4).getFunctor()))) {
										if (execL1926(var0,var1,var4,var2,var3,nondeterministic)) {
											ret = true;
											break L1935;
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
	public boolean execL1926(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1926:
		{
			link = ((Atom)var2).getArg(0);
			var7 = link;
			link = ((Atom)var4).getArg(1);
			var9 = link;
			link = ((Atom)var3).getArg(0);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f12;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var6), 1 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var7 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var9 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var6, 4);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1926;
		}
		return ret;
	}
	public boolean execL1927(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1927:
		{
			if (execL1929(var0, var1, nondeterministic)) {
				ret = true;
				break L1927;
			}
			if (execL1931(var0, var1, nondeterministic)) {
				ret = true;
				break L1927;
			}
			if (execL1933(var0, var1, nondeterministic)) {
				ret = true;
				break L1927;
			}
		}
		return ret;
	}
	public boolean execL1933(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1933:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						if (!(link.getPos() != 2)) {
							var8 = link.getAtom();
							if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(2);
								var11 = link;
								link = ((Atom)var8).getArg(0);
								if (!(link.getPos() != 1)) {
									var12 = link.getAtom();
									if (!(!(f11).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										link = ((Atom)var12).getArg(1);
										var14 = link;
										if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1926(var0,var6,var12,var1,var8,nondeterministic)) {
												ret = true;
												break L1933;
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
	public boolean execL1931(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1931:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 3)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(3);
									var12 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var13 = link.getAtom();
										if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
											link = ((Atom)var13).getArg(0);
											var14 = link;
											if (execL1926(var0,var13,var1,var8,var4,nondeterministic)) {
												ret = true;
												break L1931;
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
	public boolean execL1929(Object var0, Object var1, boolean nondeterministic) {
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
		Object var14 = null;
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
L1929:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(3);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 1)) {
										var12 = link.getAtom();
										if (!(!(f11).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var12).getArg(1);
											var14 = link;
											if (execL1926(var0,var1,var12,var3,var8,nondeterministic)) {
												ret = true;
												break L1929;
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
	public boolean execL1921(Object var0, boolean nondeterministic) {
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
L1921:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL1917(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1921;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1917(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1917:
		{
			link = ((Atom)var1).getArg(2);
			var10 = link;
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
			func = f0;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var8), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 2 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 3,
				(Link)var10 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 1,
				((Atom)var4), 0 );
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var9, 3);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1917;
		}
		return ret;
	}
	public boolean execL1918(Object var0, Object var1, boolean nondeterministic) {
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
L1918:
		{
			if (execL1920(var0, var1, nondeterministic)) {
				ret = true;
				break L1918;
			}
		}
		return ret;
	}
	public boolean execL1920(Object var0, Object var1, boolean nondeterministic) {
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
L1920:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					func = ((Atom)var6).getFunctor();
					if (!(func.getArity() != 1)) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL1917(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1920;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1912(Object var0, boolean nondeterministic) {
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
L1912:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL1905(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1912;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1905(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1905:
		{
			link = ((Atom)var3).getArg(1);
			var6 = link;
			link = ((Atom)var2).getArg(0);
			var7 = link;
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
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var7 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var5, 2);
			ret = true;
			break L1905;
		}
		return ret;
	}
	public boolean execL1906(Object var0, Object var1, boolean nondeterministic) {
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
L1906:
		{
			if (execL1908(var0, var1, nondeterministic)) {
				ret = true;
				break L1906;
			}
			if (execL1910(var0, var1, nondeterministic)) {
				ret = true;
				break L1906;
			}
		}
		return ret;
	}
	public boolean execL1910(Object var0, Object var1, boolean nondeterministic) {
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
L1910:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1905(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L1910;
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
	public boolean execL1908(Object var0, Object var1, boolean nondeterministic) {
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
L1908:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 1)) {
								var7 = link.getAtom();
								if (!(!(f15).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									if (execL1905(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L1908;
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
	public boolean execL1900(Object var0, boolean nondeterministic) {
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
L1900:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL1893(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1900;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1893(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1893:
		{
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var2).getArg(0);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f18;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var8 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var3), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var10 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 1);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1893;
		}
		return ret;
	}
	public boolean execL1894(Object var0, Object var1, boolean nondeterministic) {
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
L1894:
		{
			if (execL1896(var0, var1, nondeterministic)) {
				ret = true;
				break L1894;
			}
			if (execL1898(var0, var1, nondeterministic)) {
				ret = true;
				break L1894;
			}
		}
		return ret;
	}
	public boolean execL1898(Object var0, Object var1, boolean nondeterministic) {
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
L1898:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(f17).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									if (execL1893(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L1898;
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
	public boolean execL1896(Object var0, Object var1, boolean nondeterministic) {
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
L1896:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(f15).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									if (execL1893(var0,var1,var8,var4,nondeterministic)) {
										ret = true;
										break L1896;
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
	public boolean execL1888(Object var0, boolean nondeterministic) {
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
L1888:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL1884(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1888;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1884(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1884:
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
			func = f15;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f21;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 1 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var6 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var5, 0);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1884;
		}
		return ret;
	}
	public boolean execL1885(Object var0, Object var1, boolean nondeterministic) {
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
L1885:
		{
			if (execL1887(var0, var1, nondeterministic)) {
				ret = true;
				break L1885;
			}
		}
		return ret;
	}
	public boolean execL1887(Object var0, Object var1, boolean nondeterministic) {
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
L1887:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL1884(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1887;
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f21 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tServerSocket ss = new ServerSocket(Integer.parseInt(me.nth(0)));\r\n\t\t\tAtom o = mem.newAtom(new ObjectFunctor(ss));\r\n\t\t\tmem.relink(o, 0, me, 1);\r\n\t\t\t\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 2, null);
	private static final Functor f18 = new Functor("accepting", 2, "socket");
	private static final Functor f7 = new Functor("close_is", 1, null);
	private static final Functor f9 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tString data = (String)((StringFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tSocket soc = ((ReadThread)((ObjectFunctor)me.nthAtom(1).getFunctor()).getObject()).socket;\r\n\t\t\tBufferedWriter writer = new BufferedWriter(\r\n\t\t\t\t\tnew OutputStreamWriter(soc.getOutputStream()));\r\n\t\t\twriter.write(data);\r\n\t\t\twriter.write(\"\\n\");\r\n\t\t\twriter.flush();\r\n\t\t\tmem.unifyAtomArgs(me, 1, me, 2);\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch (Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 3, null);
	private static final Functor f12 = new Functor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tsr.start();\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
	private static final Functor f10 = new Functor("os", 2, null);
	private static final Functor f17 = new Functor("accept", 2, null);
	private static final Functor f3 = new Functor("close", 1, null);
	private static final Functor f20 = new Functor("create", 2, "socket");
	private static final Functor f0 = new Functor("nil", 1, null);
	private static final Functor f1 = new Functor("socket", 4, "socket");
	private static final Functor f6 = new StringFunctor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tSocket soc = sr.socket;\r\n\t\ttry {\r\n\t\t\tsoc.close();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t");
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f11 = new Functor("is", 2, null);
	private static final Functor f15 = new Functor("serversocket", 2, "socket");
	private static final Functor f4 = new Functor("[]", 1, null);
	private static final Functor f5 = new Functor("closed", 1, null);
	private static final Functor f16 = new StringFunctor("/*inline*/\r\n\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\ttry {\r\n\t\t\tss.close();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t");
	private static final Functor f19 = new Functor("/*inline*/\r\n\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tAcceptThread t = new AcceptThread(ss,me.nthAtom(1));\r\n\t\tmem.makePerpetual();\r\n\t\tt.start();\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
	private static final Functor f13 = new Functor("connect", 3, "socket");
	private static final Functor f14 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tString addr = me.nth(0);\r\n\t\t\tint port = Integer.parseInt(me.nth(1));\r\n\t\t\tReadThread sr = new ReadThread(addr, port);\r\n\r\n\t\t\tFunctor func = new ObjectFunctor(sr);\r\n\t\t\tAtom so = mem.newAtom(func);\r\n\t\t\tsr.me = so;\r\n\t\t\tmem.relink(so, 0, me, 2);\r\n\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.nthAtom(1).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tsr.flgClosing = true;\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
}

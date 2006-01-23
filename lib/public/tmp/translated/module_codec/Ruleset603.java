package translated.module_codec;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset603 extends Ruleset {
	private static final Ruleset603 theInstance = new Ruleset603();
	private Ruleset603() {}
	public static Ruleset603 getInstance() {
		return theInstance;
	}
	private int id = 603;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":codec" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@codec" + id;
	}
	private String encodedRuleset = 
"(memEncoder @@ '='(Ret, codec.memEncoder({$p, @p})) :- '='(Ret, codec.encoded(X)), {'='(X, codec.encode), $p, @p}), (encode @@ {'='(Ret, codec.encode), $p, @p} :- {'='(Ret, [:/*inline*/    me.remove();    StringFunctor sFunc =      new StringFunctor(((Membrane)mem).encode());    Atom sAtom = mem.newAtom(sFunc);    mem.relinkAtomArgs(sAtom,0,me,0);  :]), $p, @p}), (encoded @@ '='(Ret, codec.encoded(X)), {'='(X, S), $p, @p} :- string(S) | '='(Ret, S), {$p, @p}), (strDecoder @@ codec.strDecoder(S) :- string(S) | [:/*inline*/    String str = me.nth(0);    LMNParser lp;    compile.structure.Membrane m;    Ruleset rs;    try {      lp = new LMNParser(new StringReader(str));      m = lp.parse();      rs = RulesetCompiler.compileMembrane(m);      rs.react((Membrane)mem);    } catch(ParseException e) {      e.printStackTrace();    }    me.nthAtom(0).remove();    me.remove();  :](S)), (fileDecoder @@ codec.fileDecoder(S) :- string(S) | [:/*inline*/    try {      String filename = me.nth(0);      FileInputStream fis = new FileInputStream(filename);      InputStreamReader isr = new InputStreamReader(fis);      BufferedReader br = new BufferedReader(isr);      LMNParser lp;      compile.structure.Membrane m;      Ruleset rs;      lp = new LMNParser(br);      m = lp.parse();      rs = RulesetCompiler.compileMembrane(m);      rs.react((Membrane)mem);      br.close();    } catch(IOException e) {      e.printStackTrace();    } catch(ParseException e) {      e.printStackTrace();    }    me.nthAtom(0).remove();    me.remove();  :](S))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL164(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "memEncoder");
			return true;
		}
		if (execL177(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "encode");
			return true;
		}
		if (execL188(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "encoded");
			return true;
		}
		if (execL199(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "strDecoder");
			return true;
		}
		if (execL208(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@603", "fileDecoder");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL171(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "memEncoder");
			return true;
		}
		if (execL182(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "encode");
			return true;
		}
		if (execL193(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "encoded");
			return true;
		}
		if (execL202(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "strDecoder");
			return true;
		}
		if (execL211(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@603", "fileDecoder");
			return true;
		}
		return result;
	}
	public boolean execL211(Object var0, boolean nondeterministic) {
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
L211:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "fileDecoder", "L207",var0,var1,var2});
					} else if (execL207(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L211;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL207(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L207:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			SomeInlineCodecodec.run((Atom)var4, 2);
			ret = true;
			break L207;
		}
		return ret;
	}
	public boolean execL208(Object var0, Object var1, boolean nondeterministic) {
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
L208:
		{
			if (execL210(var0, var1, nondeterministic)) {
				ret = true;
				break L208;
			}
		}
		return ret;
	}
	public boolean execL210(Object var0, Object var1, boolean nondeterministic) {
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
L210:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "fileDecoder", "L207",var0,var1,var3});
						} else if (execL207(var0,var1,var3,nondeterministic)) {
							ret = true;
							break L210;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL202(Object var0, boolean nondeterministic) {
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
L202:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "strDecoder", "L198",var0,var1,var2});
					} else if (execL198(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L202;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL198(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L198:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			SomeInlineCodecodec.run((Atom)var4, 1);
			ret = true;
			break L198;
		}
		return ret;
	}
	public boolean execL199(Object var0, Object var1, boolean nondeterministic) {
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
L199:
		{
			if (execL201(var0, var1, nondeterministic)) {
				ret = true;
				break L199;
			}
		}
		return ret;
	}
	public boolean execL201(Object var0, Object var1, boolean nondeterministic) {
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
L201:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "strDecoder", "L198",var0,var1,var3});
						} else if (execL198(var0,var1,var3,nondeterministic)) {
							ret = true;
							break L201;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL193(Object var0, boolean nondeterministic) {
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
L193:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								mem = ((Atom)var3).getMem();
								if (mem.lock()) {
									var4 = mem;
									link = ((Atom)var3).getArg(1);
									var5 = link.getAtom();
									if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
										if (execL187(var0,var4,var1,var2,var3,var5,nondeterministic)) {
											ret = true;
											break L193;
										}
									}
									((AbstractMembrane)var4).unlock();
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL187(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L187:
		{
			link = ((Atom)var2).getArg(1);
			var8 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var8 );
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L187;
		}
		return ret;
	}
	public boolean execL188(Object var0, Object var1, boolean nondeterministic) {
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
L188:
		{
			if (execL190(var0, var1, nondeterministic)) {
				ret = true;
				break L188;
			}
		}
		return ret;
	}
	public boolean execL190(Object var0, Object var1, boolean nondeterministic) {
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
L190:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
									mem = ((Atom)var7).getMem();
									if (mem.lock()) {
										var8 = mem;
										link = ((Atom)var7).getArg(0);
										var9 = link;
										link = ((Atom)var7).getArg(1);
										var10 = link;
										link = ((Atom)var7).getArg(1);
										var11 = link.getAtom();
										if (((Atom)var11).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var11).getFunctor()).getObject() instanceof String) {
											if (execL187(var0,var8,var1,var4,var7,var11,nondeterministic)) {
												ret = true;
												break L190;
											}
										}
										((AbstractMembrane)var8).unlock();
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
	public boolean execL182(Object var0, boolean nondeterministic) {
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
L182:
		{
			Iterator it1 = ((AbstractMembrane)var0).memIterator();
			while (it1.hasNext()) {
				mem = (AbstractMembrane) it1.next();
				if ((mem.getKind() != 0))
					continue;
				if (mem.lock()) {
					var1 = mem;
					func = f5;
					Iterator it2 = ((AbstractMembrane)var1).atomIteratorOfFunctor(func);
					while (it2.hasNext()) {
						atom = (Atom) it2.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										if (execL176(var0,var1,var4,var2,var3,nondeterministic)) {
											ret = true;
											break L182;
										}
									}
								}
							}
						}
					}
					((AbstractMembrane)var1).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL176(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L176:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			func = f6;
			var6 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var7 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var7), 0 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 1,
				(Link)var9 );
			SomeInlineCodecodec.run((Atom)var6, 0);
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L176;
		}
		return ret;
	}
	public boolean execL177(Object var0, Object var1, boolean nondeterministic) {
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
L177:
		{
			if (execL180(var0, var1, nondeterministic)) {
				ret = true;
				break L177;
			}
		}
		return ret;
	}
	public boolean execL180(Object var0, Object var1, boolean nondeterministic) {
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
L180:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									mem = ((AbstractMembrane)var2);
									if (mem.lock()) {
										mem = ((AbstractMembrane)var2).getParent();
										if (!(mem == null)) {
											var3 = mem;
											if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
												if (execL176(var0,var2,var8,var1,var5,nondeterministic)) {
													ret = true;
													break L180;
												}
											}
										}
										((AbstractMembrane)var2).unlock();
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
	public boolean execL171(Object var0, boolean nondeterministic) {
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
L171:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								mem = ((Atom)var3).getMem();
								if (mem.lock()) {
									var4 = mem;
									link = ((Atom)var3).getArg(1);
									if (!(link.getPos() != 0)) {
										var5 = link.getAtom();
										if (!(!(f8).equals(((Atom)var5).getFunctor()))) {
											if (execL163(var0,var4,var1,var2,var5,var3,nondeterministic)) {
												ret = true;
												break L171;
											}
										}
									}
									((AbstractMembrane)var4).unlock();
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL163(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L163:
		{
			link = ((Atom)var2).getArg(1);
			var11 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			func = f5;
			var7 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var8 = ((AbstractMembrane)var1).newAtom(func);
			func = f4;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 1 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var10), 1 );
			((Atom)var9).getMem().inheritLink(
				((Atom)var9), 1,
				(Link)var11 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var8), 0 );
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_codec");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var1).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				}
				try {
					Class c = Class.forName("translated.Module_codec");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module codec");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L163;
		}
		return ret;
	}
	public boolean execL164(Object var0, Object var1, boolean nondeterministic) {
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
L164:
		{
			if (execL166(var0, var1, nondeterministic)) {
				ret = true;
				break L164;
			}
			if (execL169(var0, var1, nondeterministic)) {
				ret = true;
				break L164;
			}
		}
		return ret;
	}
	public boolean execL169(Object var0, Object var1, boolean nondeterministic) {
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
L169:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f7).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											mem = ((AbstractMembrane)var2);
											if (mem.lock()) {
												mem = ((AbstractMembrane)var2).getParent();
												if (!(mem == null)) {
													var3 = mem;
													if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
														if (execL163(var0,var2,var11,var8,var1,var5,nondeterministic)) {
															ret = true;
															break L169;
														}
													}
												}
												((AbstractMembrane)var2).unlock();
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
	public boolean execL166(Object var0, Object var1, boolean nondeterministic) {
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
L166:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
									mem = ((Atom)var7).getMem();
									if (mem.lock()) {
										var8 = mem;
										link = ((Atom)var7).getArg(0);
										var9 = link;
										link = ((Atom)var7).getArg(1);
										var10 = link;
										link = ((Atom)var7).getArg(1);
										if (!(link.getPos() != 0)) {
											var11 = link.getAtom();
											if (!(!(f8).equals(((Atom)var11).getFunctor()))) {
												link = ((Atom)var11).getArg(0);
												var12 = link;
												if (execL163(var0,var8,var1,var4,var11,var7,nondeterministic)) {
													ret = true;
													break L166;
												}
											}
										}
										((AbstractMembrane)var8).unlock();
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
	private static final Functor f5 = new Functor("encode", 1, "codec");
	private static final Functor f1 = new StringFunctor("/*inline*/\r\n    try {\r\n      String filename = me.nth(0);\r\n      FileInputStream fis = new FileInputStream(filename);\r\n      InputStreamReader isr = new InputStreamReader(fis);\r\n      BufferedReader br = new BufferedReader(isr);\r\n      LMNParser lp;\r\n      compile.structure.Membrane m;\r\n      Ruleset rs;\r\n      lp = new LMNParser(br);\r\n      m = lp.parse();\r\n      rs = RulesetCompiler.compileMembrane(m);\r\n      rs.react((Membrane)mem);\r\n      br.close();\r\n    } catch(IOException e) {\r\n      e.printStackTrace();\r\n    } catch(ParseException e) {\r\n      e.printStackTrace();\r\n    }\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ");
	private static final Functor f6 = new StringFunctor("/*inline*/\r\n    me.remove();\r\n    StringFunctor sFunc =\r\n      new StringFunctor(((Membrane)mem).encode());\r\n    Atom sAtom = mem.newAtom(sFunc);\r\n    mem.relinkAtomArgs(sAtom,0,me,0);\r\n  ");
	private static final Functor f7 = new Functor("memEncoder", 2, "codec");
	private static final Functor f2 = new Functor("strDecoder", 1, "codec");
	private static final Functor f8 = new Functor("+", 1, null);
	private static final Functor f4 = new Functor("encoded", 2, "codec");
	private static final Functor f3 = new StringFunctor("/*inline*/\r\n    String str = me.nth(0);\r\n    LMNParser lp;\r\n    compile.structure.Membrane m;\r\n    Ruleset rs;\r\n    try {\r\n      lp = new LMNParser(new StringReader(str));\r\n      m = lp.parse();\r\n      rs = RulesetCompiler.compileMembrane(m);\r\n      rs.react((Membrane)mem);\r\n    } catch(ParseException e) {\r\n      e.printStackTrace();\r\n    }\r\n    me.nthAtom(0).remove();\r\n    me.remove();\r\n  ");
	private static final Functor f0 = new Functor("fileDecoder", 1, "codec");
}

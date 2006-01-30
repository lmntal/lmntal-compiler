package translated.module_nd;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":nd" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@nd" + id;
	}
	private String encodedRuleset = 
"(nd.exec({$p, @p, {$q, @q}*}) :- [:/*inline*/\t\t\tAtom out = me.nthAtom(0);\t\t\tAtom in = out.nthAtom(0);\t\t\tAtom plus = in.nthAtom(1);\t\t\tMembrane mem1 = (Membrane)in.getMem();\t\t\tIterator it = mem1.memIterator();\t\t\tMembrane mem2 = (Membrane)it.next();\t\t\t((Task)mem.getTask()).nondeterministicExec(mem2);\t\t\tmem.removeAtom(me);\t\t\tmem.removeAtom(out);\t\t\tmem1.removeAtom(in);\t\t\tplus.dequeue();\t\t\tmem1.removeAtom(plus);\t\t:]({$p, @p, {$q, @q}*})), (nd.genid :- nd.nextid(0)), (nd.nextid(N), {$p, @p} :- '\\\\+'('='($p, id(X), $pp)), '='(N2, '+'(N, 1)) | nd.nextid(N2), {id(N), $p, @p})";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL524(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "exec");
			return true;
		}
		if (execL537(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "genid");
			return true;
		}
		if (execL546(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "nextid");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL531(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "exec");
			return true;
		}
		if (execL540(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "genid");
			return true;
		}
		if (execL549(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "nextid");
			return true;
		}
		return result;
	}
	public boolean execL549(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L549:
		{
			var3 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var4 = link.getAtom();
				if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
					x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
					y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();
					var5 = new Atom(null, new IntegerFunctor(x+y));
					var6 =  ((Atom)var5).getFunctor();
					var7 = new Atom(null, (Functor)(var6));
					Iterator it2 = ((AbstractMembrane)var0).memIterator();
					while (it2.hasNext()) {
						mem = (AbstractMembrane) it2.next();
						if ((mem.getKind() != 0))
							continue;
						if (mem.lock()) {
							var2 = mem;
L552:
							{
								func = f2;
								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it3.hasNext()) {
									atom = (Atom) it3.next();
									var8 = atom;
									link = ((Atom)var8).getArg(0);
									var9 = link;
									ret = true;
									break L552;
								}
							}
							if (ret) {
								ret = false;
							} else {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "nextid", "L545",var0,var2,var1,var3,var4,var5,var7});
								} else if (execL545(var0,var2,var1,var3,var4,var5,var7,nondeterministic)) {
									ret = true;
									break L549;
								}
							}
							((AbstractMembrane)var2).unlock();
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL545(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L545:
		{
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var1).newAtom(((Atom)var4).getFunctor());
			func = f2;
			var10 = ((AbstractMembrane)var1).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var9), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var8), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_nd");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L545;
		}
		return ret;
	}
	public boolean execL546(Object var0, Object var1, boolean nondeterministic) {
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
L546:
		{
			if (execL548(var0, var1, nondeterministic)) {
				ret = true;
				break L546;
			}
		}
		return ret;
	}
	public boolean execL548(Object var0, Object var1, boolean nondeterministic) {
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
L548:
		{
			var4 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						var6 = new Atom(null, new IntegerFunctor(x+y));
						var7 =  ((Atom)var6).getFunctor();
						var8 = new Atom(null, (Functor)(var7));
						Iterator it1 = ((AbstractMembrane)var0).memIterator();
						while (it1.hasNext()) {
							mem = (AbstractMembrane) it1.next();
							if ((mem.getKind() != 0))
								continue;
							if (mem.lock()) {
								var3 = mem;
L553:
								{
									func = f2;
									Iterator it2 = ((AbstractMembrane)var3).atomIteratorOfFunctor(func);
									while (it2.hasNext()) {
										atom = (Atom) it2.next();
										var9 = atom;
										link = ((Atom)var9).getArg(0);
										var10 = link;
										ret = true;
										break L553;
									}
								}
								if (ret) {
									ret = false;
								} else {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "nextid", "L545",var0,var3,var1,var4,var5,var6,var8});
									} else if (execL545(var0,var3,var1,var4,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L548;
									}
								}
								((AbstractMembrane)var3).unlock();
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL540(Object var0, boolean nondeterministic) {
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
L540:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "genid", "L536",var0,var1});
				} else if (execL536(var0,var1,nondeterministic)) {
					ret = true;
					break L540;
				}
			}
		}
		return ret;
	}
	public boolean execL536(Object var0, Object var1, boolean nondeterministic) {
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
L536:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f4;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_nd");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module nd");
				}
			ret = true;
			break L536;
		}
		return ret;
	}
	public boolean execL537(Object var0, Object var1, boolean nondeterministic) {
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
L537:
		{
			if (execL539(var0, var1, nondeterministic)) {
				ret = true;
				break L537;
			}
		}
		return ret;
	}
	public boolean execL539(Object var0, Object var1, boolean nondeterministic) {
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
L539:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "genid", "L536",var0,var1});
					} else if (execL536(var0,var1,nondeterministic)) {
						ret = true;
						break L539;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL531(Object var0, boolean nondeterministic) {
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
L531:
		{
			func = f5;
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
										if (!(!(f6).equals(((Atom)var5).getFunctor()))) {
											Iterator it2 = ((AbstractMembrane)var4).memIterator();
											while (it2.hasNext()) {
												mem = (AbstractMembrane) it2.next();
												if ((mem.getKind() != 2))
													continue;
												if (mem.lock()) {
													var6 = mem;
													if (nondeterministic) {
														Task.states.add(new Object[] {theInstance, "exec", "L523",var0,var4,var6,var1,var2,var5,var3});
													} else if (execL523(var0,var4,var6,var1,var2,var5,var3,nondeterministic)) {
														ret = true;
														break L531;
													}
													((AbstractMembrane)var6).unlock();
												}
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
	public boolean execL523(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L523:
		{
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var2).activate();
			((AbstractMembrane)var1).insertProxies(((AbstractMembrane)var2));
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			func = Functor.INSIDE_PROXY;
			var10 = ((AbstractMembrane)var1).newAtom(func);
			func = f7;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var10), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var12), 1 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var10), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodend.run((Atom)var11, 0);
			((AbstractMembrane)var2).forceUnlock();
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L523;
		}
		return ret;
	}
	public boolean execL524(Object var0, Object var1, boolean nondeterministic) {
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
L524:
		{
			if (execL526(var0, var1, nondeterministic)) {
				ret = true;
				break L524;
			}
			if (execL529(var0, var1, nondeterministic)) {
				ret = true;
				break L524;
			}
		}
		return ret;
	}
	public boolean execL529(Object var0, Object var1, boolean nondeterministic) {
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
L529:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
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
										if (!(!(f5).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											mem = ((AbstractMembrane)var2);
											if (mem.lock()) {
												mem = ((AbstractMembrane)var2).getParent();
												if (!(mem == null)) {
													var3 = mem;
													if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
														Iterator it1 = ((AbstractMembrane)var2).memIterator();
														while (it1.hasNext()) {
															mem = (AbstractMembrane) it1.next();
															if ((mem.getKind() != 2))
																continue;
															if (mem.lock()) {
																var13 = mem;
																if (nondeterministic) {
																	Task.states.add(new Object[] {theInstance, "exec", "L523",var0,var2,var13,var11,var8,var1,var5});
																} else if (execL523(var0,var2,var13,var11,var8,var1,var5,nondeterministic)) {
																	ret = true;
																	break L529;
																}
																((AbstractMembrane)var13).unlock();
															}
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
	public boolean execL526(Object var0, Object var1, boolean nondeterministic) {
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
L526:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var6).getFunctor()))) {
									mem = ((Atom)var6).getMem();
									if (mem.lock()) {
										var7 = mem;
										link = ((Atom)var6).getArg(0);
										var8 = link;
										link = ((Atom)var6).getArg(1);
										var9 = link;
										link = ((Atom)var6).getArg(1);
										if (!(link.getPos() != 0)) {
											var10 = link.getAtom();
											if (!(!(f6).equals(((Atom)var10).getFunctor()))) {
												link = ((Atom)var10).getArg(0);
												var11 = link;
												Iterator it1 = ((AbstractMembrane)var7).memIterator();
												while (it1.hasNext()) {
													mem = (AbstractMembrane) it1.next();
													if ((mem.getKind() != 2))
														continue;
													if (mem.lock()) {
														var12 = mem;
														if (nondeterministic) {
															Task.states.add(new Object[] {theInstance, "exec", "L523",var0,var7,var12,var1,var3,var10,var6});
														} else if (execL523(var0,var7,var12,var1,var3,var10,var6,nondeterministic)) {
															ret = true;
															break L526;
														}
														((AbstractMembrane)var12).unlock();
													}
												}
											}
										}
										((AbstractMembrane)var7).unlock();
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
	private static final Functor f5 = new Functor("exec", 1, "nd");
	private static final Functor f7 = new StringFunctor("/*inline*/\r\n\t\t\tAtom out = me.nthAtom(0);\r\n\t\t\tAtom in = out.nthAtom(0);\r\n\t\t\tAtom plus = in.nthAtom(1);\r\n\t\t\tMembrane mem1 = (Membrane)in.getMem();\r\n\t\t\tIterator it = mem1.memIterator();\r\n\t\t\tMembrane mem2 = (Membrane)it.next();\r\n\t\t\t((Task)mem.getTask()).nondeterministicExec(mem2);\r\n\t\t\tmem.removeAtom(me);\r\n\t\t\tmem.removeAtom(out);\r\n\t\t\tmem1.removeAtom(in);\r\n\t\t\tplus.dequeue();\r\n\t\t\tmem1.removeAtom(plus);\r\n\t\t");
	private static final Functor f3 = new Functor("genid", 0, "nd");
	private static final Functor f2 = new Functor("id", 1, null);
	private static final Functor f0 = new IntegerFunctor(1);
	private static final Functor f1 = new Functor("nextid", 1, "nd");
	private static final Functor f6 = new Functor("+", 1, null);
	private static final Functor f4 = new IntegerFunctor(0);
}

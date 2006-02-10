package translated.module_thread;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset616 extends Ruleset {
	private static final Ruleset616 theInstance = new Ruleset616();
	private Ruleset616() {}
	public static Ruleset616 getInstance() {
		return theInstance;
	}
	private int id = 616;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":thread" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@thread" + id;
	}
	private String encodedRuleset = 
"(thread_at_0 @@ thread.at(N, {$p, @p}) :- int(N) | thread.at2(N, {{$p, @p}}))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL813(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@616", "thread_at_0");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL820(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@616", "thread_at_0");
			return true;
		}
		return result;
	}
	public boolean execL820(Object var0, boolean nondeterministic) {
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
L820:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
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
											if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
												if (nondeterministic) {
													Task.states.add(new Object[] {theInstance, "thread_at_0", "L812",var0,var4,var1,var2,var5,var3,var6});
												} else if (execL812(var0,var4,var1,var2,var5,var3,var6,nondeterministic)) {
													ret = true;
													break L820;
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
		}
		return ret;
	}
	public boolean execL812(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L812:
		{
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
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			mem = ((AbstractMembrane)var0).newMem(0);
			var7 = mem;
			var1 = ((AbstractMembrane)var1).moveTo(((AbstractMembrane)var7));
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var7).insertProxies(((AbstractMembrane)var1));
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var7));
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			func = f1;
			var10 = ((AbstractMembrane)var7).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var11 = ((AbstractMembrane)var7).newAtom(func);
			func = f2;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var11), 1 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var9), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 1,
				((Atom)var13), 1 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 0,
				((Atom)var11), 0 );
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_thread");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module thread");
				}
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L812;
		}
		return ret;
	}
	public boolean execL813(Object var0, Object var1, boolean nondeterministic) {
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
L813:
		{
			if (execL815(var0, var1, nondeterministic)) {
				ret = true;
				break L813;
			}
			if (execL818(var0, var1, nondeterministic)) {
				ret = true;
				break L813;
			}
		}
		return ret;
	}
	public boolean execL818(Object var0, Object var1, boolean nondeterministic) {
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
L818:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
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
									if (!(link.getPos() != 1)) {
										var11 = link.getAtom();
										if (!(!(f0).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(0);
											var14 = link.getAtom();
											if (!(!(((Atom)var14).getFunctor() instanceof IntegerFunctor))) {
												mem = ((AbstractMembrane)var2);
												if (mem.lock()) {
													mem = ((AbstractMembrane)var2).getParent();
													if (!(mem == null)) {
														var3 = mem;
														if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
															if (nondeterministic) {
																Task.states.add(new Object[] {theInstance, "thread_at_0", "L812",var0,var2,var11,var8,var1,var5,var14});
															} else if (execL812(var0,var2,var11,var8,var1,var5,var14,nondeterministic)) {
																ret = true;
																break L818;
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
		}
		return ret;
	}
	public boolean execL815(Object var0, Object var1, boolean nondeterministic) {
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
L815:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var13 = link.getAtom();
						if (!(!(((Atom)var13).getFunctor() instanceof IntegerFunctor))) {
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
												if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
													link = ((Atom)var11).getArg(0);
													var12 = link;
													if (nondeterministic) {
														Task.states.add(new Object[] {theInstance, "thread_at_0", "L812",var0,var8,var1,var4,var11,var7,var13});
													} else if (execL812(var0,var8,var1,var4,var11,var7,var13,nondeterministic)) {
														ret = true;
														break L815;
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
		}
		return ret;
	}
	private static final Functor f0 = new Functor("at", 2, "thread");
	private static final Functor f2 = new Functor("at2", 2, "thread");
	private static final Functor f1 = new Functor("+", 1, null);
}

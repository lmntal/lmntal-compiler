package translated.module_thread;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":thread" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@thread" + id;
	}
	private String encodedRuleset = 
"(thread_at2_0 @@ thread.at2(N, {{$p, @p}}), thread(M, {$q, @q}) :- int(N), int(M), '=:='(M, '+'(N, 1)) | thread(M, {$p, $q, @p, @q}))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL327(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@614", "thread_at2_0");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL339(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@614", "thread_at2_0");
			return true;
		}
		return result;
	}
	public boolean execL339(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L339:
		{
			var14 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var12 = link.getAtom();
					if (!(!(((Atom)var12).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var12).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var14).getFunctor()).intValue();
						var15 = new Atom(null, new IntegerFunctor(x+y));
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
											if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
												Iterator it2 = ((AbstractMembrane)var0).memIterator();
												while (it2.hasNext()) {
													mem = (AbstractMembrane) it2.next();
													if ((mem.getKind() != 0))
														continue;
													if (mem.lock()) {
														var6 = mem;
														func = f2;
														Iterator it3 = ((AbstractMembrane)var6).atomIteratorOfFunctor(func);
														while (it3.hasNext()) {
															atom = (Atom) it3.next();
															var7 = atom;
															link = ((Atom)var7).getArg(0);
															if (!(link.getPos() != 1)) {
																var8 = link.getAtom();
																if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
																	link = ((Atom)var8).getArg(0);
																	if (!(link.getPos() != 0)) {
																		var9 = link.getAtom();
																		if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
																			link = ((Atom)var9).getArg(1);
																			if (!(link.getPos() != 1)) {
																				var10 = link.getAtom();
																				if (!(!(f3).equals(((Atom)var10).getFunctor()))) {
																					link = ((Atom)var10).getArg(0);
																					var13 = link.getAtom();
																					if (!(!(((Atom)var13).getFunctor() instanceof IntegerFunctor))) {
																						x = ((IntegerFunctor)((Atom)var13).getFunctor()).intValue();
																						y = ((IntegerFunctor)((Atom)var15).getFunctor()).intValue();	
																						if (!(!(x == y))) {
																							Iterator it4 = ((AbstractMembrane)var4).memIterator();
																							while (it4.hasNext()) {
																								mem = (AbstractMembrane) it4.next();
																								if ((mem.getKind() != 0))
																									continue;
																								if (mem.lock()) {
																									var11 = mem;
																									if (!(((AbstractMembrane)var4).getAtomCount() != 1)) {
																										if (!(((AbstractMembrane)var4).getMemCount() != 1)) {
																											if (!(((AbstractMembrane)var4).hasRules())) {
																												if (nondeterministic) {
																													Task.states.add(new Object[] {theInstance, "thread_at2_0", "L326",var0,var4,var11,var6,var1,var10,var2,var9,var5,var3,var7,var8,var12,var13,var14,var15});
																												} else if (execL326(var0,var4,var11,var6,var1,var10,var2,var9,var5,var3,var7,var8,var12,var13,var14,var15,nondeterministic)) {
																													ret = true;
																													break L339;
																												}
																											}
																										}
																									}
																									((AbstractMembrane)var11).unlock();
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
		}
		return ret;
	}
	public boolean execL326(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, Object var13, Object var14, Object var15, boolean nondeterministic) {
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
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
L326:
		{
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var13);
			atom.dequeue();
			atom = ((Atom)var13);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var12);
			atom.dequeue();
			atom = ((Atom)var12);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var2);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var2).removeProxies();
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var3).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var3).activate();
			((AbstractMembrane)var3).moveCellsFrom(((AbstractMembrane)var2));
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var3));
			((AbstractMembrane)var3).copyRulesFrom(((AbstractMembrane)var2));
			var17 = ((AbstractMembrane)var0).newAtom(((Atom)var13).getFunctor());
			func = Functor.INSIDE_PROXY;
			var19 = ((AbstractMembrane)var3).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var19), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var17), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var21), 1 );
			((Atom)var21).getMem().newLink(
				((Atom)var21), 0,
				((Atom)var19), 0 );
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			((AbstractMembrane)var2).free();
			((AbstractMembrane)var1).free();
			((AbstractMembrane)var3).forceUnlock();
			ret = true;
			break L326;
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
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
		Object var22 = null;
		Object var23 = null;
		Object var24 = null;
		Object var25 = null;
		Object var26 = null;
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
L327:
		{
			if (execL329(var0, var1, nondeterministic)) {
				ret = true;
				break L327;
			}
			if (execL334(var0, var1, nondeterministic)) {
				ret = true;
				break L327;
			}
			if (execL337(var0, var1, nondeterministic)) {
				ret = true;
				break L327;
			}
		}
		return ret;
	}
	public boolean execL337(Object var0, Object var1, boolean nondeterministic) {
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
		Object var22 = null;
		Object var23 = null;
		Object var24 = null;
		Object var25 = null;
		Object var26 = null;
		Object var27 = null;
		Object var28 = null;
		Object var29 = null;
		Object var30 = null;
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
L337:
		{
			var29 = new Atom(null, f0);
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
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
										if (!(!(f3).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(0);
											var28 = link.getAtom();
											if (!(!(((Atom)var28).getFunctor() instanceof IntegerFunctor))) {
												mem = ((AbstractMembrane)var2);
												if (mem.lock()) {
													mem = ((AbstractMembrane)var2).getParent();
													if (!(mem == null)) {
														var3 = mem;
														if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
															func = f1;
															Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
															while (it1.hasNext()) {
																atom = (Atom) it1.next();
																var14 = atom;
																link = ((Atom)var14).getArg(0);
																var15 = link;
																link = ((Atom)var14).getArg(1);
																var16 = link;
																link = ((Atom)var14).getArg(1);
																if (!(link.getPos() != 1)) {
																	var17 = link.getAtom();
																	link = ((Atom)var14).getArg(0);
																	var27 = link.getAtom();
																	if (!(!(((Atom)var27).getFunctor() instanceof IntegerFunctor))) {
																		x = ((IntegerFunctor)((Atom)var27).getFunctor()).intValue();
																		y = ((IntegerFunctor)((Atom)var29).getFunctor()).intValue();
																		var30 = new Atom(null, new IntegerFunctor(x+y));
																		x = ((IntegerFunctor)((Atom)var28).getFunctor()).intValue();
																		y = ((IntegerFunctor)((Atom)var30).getFunctor()).intValue();	
																		if (!(!(x == y))) {
																			if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var17).getFunctor()))) {
																				link = ((Atom)var17).getArg(0);
																				var18 = link;
																				link = ((Atom)var17).getArg(1);
																				var19 = link;
																				link = ((Atom)var17).getArg(0);
																				if (!(link.getPos() != 0)) {
																					var20 = link.getAtom();
																					if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var20).getFunctor()))) {
																						mem = ((Atom)var20).getMem();
																						if (mem.lock()) {
																							var21 = mem;
																							link = ((Atom)var20).getArg(0);
																							var22 = link;
																							link = ((Atom)var20).getArg(1);
																							var23 = link;
																							link = ((Atom)var20).getArg(1);
																							if (!(link.getPos() != 0)) {
																								var24 = link.getAtom();
																								if (!(!(f2).equals(((Atom)var24).getFunctor()))) {
																									link = ((Atom)var24).getArg(0);
																									var25 = link;
																									Iterator it2 = ((AbstractMembrane)var21).memIterator();
																									while (it2.hasNext()) {
																										mem = (AbstractMembrane) it2.next();
																										if ((mem.getKind() != 0))
																											continue;
																										if (mem.lock()) {
																											var26 = mem;
																											if (!(((AbstractMembrane)var21).getAtomCount() != 1)) {
																												if (!(((AbstractMembrane)var21).getMemCount() != 1)) {
																													if (!(((AbstractMembrane)var21).hasRules())) {
																														if (nondeterministic) {
																															Task.states.add(new Object[] {theInstance, "thread_at2_0", "L326",var0,var21,var26,var2,var14,var11,var17,var8,var24,var20,var1,var5,var27,var28,var29,var30});
																														} else if (execL326(var0,var21,var26,var2,var14,var11,var17,var8,var24,var20,var1,var5,var27,var28,var29,var30,nondeterministic)) {
																															ret = true;
																															break L337;
																														}
																													}
																												}
																											}
																											((AbstractMembrane)var26).unlock();
																										}
																									}
																								}
																							}
																							((AbstractMembrane)var21).unlock();
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
	public boolean execL334(Object var0, Object var1, boolean nondeterministic) {
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
		Object var22 = null;
		Object var23 = null;
		Object var24 = null;
		Object var25 = null;
		Object var26 = null;
		Object var27 = null;
		Object var28 = null;
		Object var29 = null;
		Object var30 = null;
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
L334:
		{
			var29 = new Atom(null, f0);
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
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
										if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(0);
											var27 = link.getAtom();
											if (!(!(((Atom)var27).getFunctor() instanceof IntegerFunctor))) {
												x = ((IntegerFunctor)((Atom)var27).getFunctor()).intValue();
												y = ((IntegerFunctor)((Atom)var29).getFunctor()).intValue();
												var30 = new Atom(null, new IntegerFunctor(x+y));
												mem = ((AbstractMembrane)var2);
												if (mem.lock()) {
													mem = ((AbstractMembrane)var2).getParent();
													if (!(mem == null)) {
														var3 = mem;
														if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
															Iterator it1 = ((AbstractMembrane)var0).memIterator();
															while (it1.hasNext()) {
																mem = (AbstractMembrane) it1.next();
																if ((mem.getKind() != 0))
																	continue;
																if (mem.lock()) {
																	var14 = mem;
																	func = f2;
																	Iterator it2 = ((AbstractMembrane)var14).atomIteratorOfFunctor(func);
																	while (it2.hasNext()) {
																		atom = (Atom) it2.next();
																		var15 = atom;
																		link = ((Atom)var15).getArg(0);
																		var16 = link;
																		link = ((Atom)var15).getArg(0);
																		if (!(link.getPos() != 1)) {
																			var17 = link.getAtom();
																			if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var17).getFunctor()))) {
																				link = ((Atom)var17).getArg(0);
																				var18 = link;
																				link = ((Atom)var17).getArg(1);
																				var19 = link;
																				link = ((Atom)var17).getArg(0);
																				if (!(link.getPos() != 0)) {
																					var20 = link.getAtom();
																					if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var20).getFunctor()))) {
																						link = ((Atom)var20).getArg(0);
																						var21 = link;
																						link = ((Atom)var20).getArg(1);
																						var22 = link;
																						link = ((Atom)var20).getArg(1);
																						if (!(link.getPos() != 1)) {
																							var23 = link.getAtom();
																							if (!(!(f3).equals(((Atom)var23).getFunctor()))) {
																								link = ((Atom)var23).getArg(0);
																								var24 = link;
																								link = ((Atom)var23).getArg(1);
																								var25 = link;
																								link = ((Atom)var23).getArg(0);
																								var28 = link.getAtom();
																								if (!(!(((Atom)var28).getFunctor() instanceof IntegerFunctor))) {
																									x = ((IntegerFunctor)((Atom)var28).getFunctor()).intValue();
																									y = ((IntegerFunctor)((Atom)var30).getFunctor()).intValue();	
																									if (!(!(x == y))) {
																										Iterator it3 = ((AbstractMembrane)var2).memIterator();
																										while (it3.hasNext()) {
																											mem = (AbstractMembrane) it3.next();
																											if ((mem.getKind() != 0))
																												continue;
																											if (mem.lock()) {
																												var26 = mem;
																												if (!(((AbstractMembrane)var2).getAtomCount() != 1)) {
																													if (!(((AbstractMembrane)var2).getMemCount() != 1)) {
																														if (!(((AbstractMembrane)var2).hasRules())) {
																															if (nondeterministic) {
																																Task.states.add(new Object[] {theInstance, "thread_at2_0", "L326",var0,var2,var26,var14,var11,var23,var8,var20,var1,var5,var15,var17,var27,var28,var29,var30});
																															} else if (execL326(var0,var2,var26,var14,var11,var23,var8,var20,var1,var5,var15,var17,var27,var28,var29,var30,nondeterministic)) {
																																ret = true;
																																break L334;
																															}
																														}
																													}
																												}
																												((AbstractMembrane)var26).unlock();
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
																	((AbstractMembrane)var14).unlock();
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
		}
		return ret;
	}
	public boolean execL329(Object var0, Object var1, boolean nondeterministic) {
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Object var20 = null;
		Object var21 = null;
		Object var22 = null;
		Object var23 = null;
		Object var24 = null;
		Object var25 = null;
		Object var26 = null;
		Object var27 = null;
		Object var28 = null;
		Object var29 = null;
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
L329:
		{
			var28 = new Atom(null, f0);
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 1)) {
						var4 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var26 = link.getAtom();
						if (!(!(((Atom)var26).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var26).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var28).getFunctor()).intValue();
							var29 = new Atom(null, new IntegerFunctor(x+y));
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
												if (!(!(f2).equals(((Atom)var11).getFunctor()))) {
													link = ((Atom)var11).getArg(0);
													var12 = link;
													Iterator it1 = ((AbstractMembrane)var0).memIterator();
													while (it1.hasNext()) {
														mem = (AbstractMembrane) it1.next();
														if ((mem.getKind() != 0))
															continue;
														if (mem.lock()) {
															var13 = mem;
															func = f2;
															Iterator it2 = ((AbstractMembrane)var13).atomIteratorOfFunctor(func);
															while (it2.hasNext()) {
																atom = (Atom) it2.next();
																var14 = atom;
																link = ((Atom)var14).getArg(0);
																var15 = link;
																link = ((Atom)var14).getArg(0);
																if (!(link.getPos() != 1)) {
																	var16 = link.getAtom();
																	if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var16).getFunctor()))) {
																		link = ((Atom)var16).getArg(0);
																		var17 = link;
																		link = ((Atom)var16).getArg(1);
																		var18 = link;
																		link = ((Atom)var16).getArg(0);
																		if (!(link.getPos() != 0)) {
																			var19 = link.getAtom();
																			if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var19).getFunctor()))) {
																				link = ((Atom)var19).getArg(0);
																				var20 = link;
																				link = ((Atom)var19).getArg(1);
																				var21 = link;
																				link = ((Atom)var19).getArg(1);
																				if (!(link.getPos() != 1)) {
																					var22 = link.getAtom();
																					if (!(!(f3).equals(((Atom)var22).getFunctor()))) {
																						link = ((Atom)var22).getArg(0);
																						var23 = link;
																						link = ((Atom)var22).getArg(1);
																						var24 = link;
																						link = ((Atom)var22).getArg(0);
																						var27 = link.getAtom();
																						if (!(!(((Atom)var27).getFunctor() instanceof IntegerFunctor))) {
																							x = ((IntegerFunctor)((Atom)var27).getFunctor()).intValue();
																							y = ((IntegerFunctor)((Atom)var29).getFunctor()).intValue();	
																							if (!(!(x == y))) {
																								Iterator it3 = ((AbstractMembrane)var8).memIterator();
																								while (it3.hasNext()) {
																									mem = (AbstractMembrane) it3.next();
																									if ((mem.getKind() != 0))
																										continue;
																									if (mem.lock()) {
																										var25 = mem;
																										if (!(((AbstractMembrane)var8).getAtomCount() != 1)) {
																											if (!(((AbstractMembrane)var8).getMemCount() != 1)) {
																												if (!(((AbstractMembrane)var8).hasRules())) {
																													if (nondeterministic) {
																														Task.states.add(new Object[] {theInstance, "thread_at2_0", "L326",var0,var8,var25,var13,var1,var22,var4,var19,var11,var7,var14,var16,var26,var27,var28,var29});
																													} else if (execL326(var0,var8,var25,var13,var1,var22,var4,var19,var11,var7,var14,var16,var26,var27,var28,var29,nondeterministic)) {
																														ret = true;
																														break L329;
																													}
																												}
																											}
																										}
																										((AbstractMembrane)var25).unlock();
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
															((AbstractMembrane)var13).unlock();
														}
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
	private static final Functor f1 = new Functor("at2", 2, "thread");
	private static final Functor f0 = new IntegerFunctor(1);
	private static final Functor f3 = new Functor("thread", 2, null);
	private static final Functor f2 = new Functor("+", 1, null);
}

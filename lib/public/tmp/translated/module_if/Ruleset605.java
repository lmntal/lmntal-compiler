package translated.module_if;
import runtime.*;
import java.util.*;
import java.io.*;
import java.math.*;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":if" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@if" + id;
	}
	private String encodedRuleset = 
"(if.use :- boolean.use), ('='(H, if(true, T, F)), {$t[T]}, {$f[F]} :- $t[H]), ('='(H, if(false, T, F)), {$t[T]}, {$f[F]} :- $f[H])";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL326(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "use");
			return true;
		}
		if (execL335(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "true");
			return true;
		}
		if (execL350(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "false");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL329(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "use");
			return true;
		}
		if (execL344(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "true");
			return true;
		}
		if (execL359(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "false");
			return true;
		}
		return result;
	}
	public boolean execL359(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		BigInteger x, y;
		BigDecimal u, v;
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
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var2).getArg(2);
								if (!(link.getPos() != 1)) {
									var4 = link.getAtom();
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										link = ((Atom)var3).getArg(0);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
												mem = ((Atom)var5).getMem();
												if (mem.lock()) {
													var6 = mem;
													link = ((Atom)var4).getArg(0);
													if (!(link.getPos() != 0)) {
														var7 = link.getAtom();
														if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
															mem = ((Atom)var7).getMem();
															if (mem.lock()) {
																var8 = mem;
																mem = ((AbstractMembrane)var6);
																if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																	mem = ((AbstractMembrane)var8);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		if (execL348(var0,var6,var8,var1,var2,var3,var4,var5,var7,nondeterministic)) {
																			ret = true;
																			break L359;
																		}
																	}
																}
																((AbstractMembrane)var8).unlock();
															}
														}
													}
													((AbstractMembrane)var6).unlock();
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
	public boolean execL348(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			if (!(((AbstractMembrane)var1).hasRules())) {
				if (!(((AbstractMembrane)var2).hasRules())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "false", "L349",var0,var1,var2,var3,var4,var5,var6,var7,var8});
					} else if (execL349(var0,var1,var2,var3,var4,var5,var6,var7,var8,nondeterministic)) {
						ret = true;
						break L348;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL349(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L349:
		{
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			mem = ((AbstractMembrane)var2);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var1).recursiveLock();
			((AbstractMembrane)var0).moveCellsFrom(((AbstractMembrane)var2));
			((AbstractMembrane)var0).removeTemporaryProxies();
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var8), 1,
				((Atom)var4), 3 );
			((AbstractMembrane)var1).drop();
			((AbstractMembrane)var1).free();
			((AbstractMembrane)var2).free();
			ret = true;
			break L349;
		}
		return ret;
	}
	public boolean execL350(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			if (execL352(var0, var1, nondeterministic)) {
				ret = true;
				break L350;
			}
			if (execL354(var0, var1, nondeterministic)) {
				ret = true;
				break L350;
			}
		}
		return ret;
	}
	public boolean execL354(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L354:
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
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var1).getArg(2);
									if (!(link.getPos() != 1)) {
										var11 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var8).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var14).getFunctor()))) {
													mem = ((Atom)var14).getMem();
													if (mem.lock()) {
														var15 = mem;
														link = ((Atom)var14).getArg(0);
														var16 = link;
														link = ((Atom)var14).getArg(1);
														var17 = link;
														link = ((Atom)var11).getArg(0);
														if (!(link.getPos() != 0)) {
															var18 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var18).getFunctor()))) {
																mem = ((Atom)var18).getMem();
																if (mem.lock()) {
																	var19 = mem;
																	link = ((Atom)var18).getArg(0);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var21 = link;
																	mem = ((AbstractMembrane)var15);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		mem = ((AbstractMembrane)var19);
																		if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																			if (execL348(var0,var15,var19,var6,var1,var8,var11,var14,var18,nondeterministic)) {
																				ret = true;
																				break L354;
																			}
																		}
																	}
																	((AbstractMembrane)var19).unlock();
																}
															}
														}
														((AbstractMembrane)var15).unlock();
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
	public boolean execL352(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L352:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var3).getArg(2);
									if (!(link.getPos() != 1)) {
										var11 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var8).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var14).getFunctor()))) {
													mem = ((Atom)var14).getMem();
													if (mem.lock()) {
														var15 = mem;
														link = ((Atom)var14).getArg(0);
														var16 = link;
														link = ((Atom)var14).getArg(1);
														var17 = link;
														link = ((Atom)var11).getArg(0);
														if (!(link.getPos() != 0)) {
															var18 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var18).getFunctor()))) {
																mem = ((Atom)var18).getMem();
																if (mem.lock()) {
																	var19 = mem;
																	link = ((Atom)var18).getArg(0);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var21 = link;
																	mem = ((AbstractMembrane)var15);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		mem = ((AbstractMembrane)var19);
																		if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																			if (execL348(var0,var15,var19,var1,var3,var8,var11,var14,var18,nondeterministic)) {
																				ret = true;
																				break L352;
																			}
																		}
																	}
																	((AbstractMembrane)var19).unlock();
																}
															}
														}
														((AbstractMembrane)var15).unlock();
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
	public boolean execL344(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		BigInteger x, y;
		BigDecimal u, v;
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
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var2).getArg(2);
								if (!(link.getPos() != 1)) {
									var4 = link.getAtom();
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
										link = ((Atom)var3).getArg(0);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
												mem = ((Atom)var5).getMem();
												if (mem.lock()) {
													var6 = mem;
													link = ((Atom)var4).getArg(0);
													if (!(link.getPos() != 0)) {
														var7 = link.getAtom();
														if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
															mem = ((Atom)var7).getMem();
															if (mem.lock()) {
																var8 = mem;
																mem = ((AbstractMembrane)var6);
																if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																	mem = ((AbstractMembrane)var8);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		if (execL333(var0,var6,var8,var1,var2,var3,var4,var5,var7,nondeterministic)) {
																			ret = true;
																			break L344;
																		}
																	}
																}
																((AbstractMembrane)var8).unlock();
															}
														}
													}
													((AbstractMembrane)var6).unlock();
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
	public boolean execL333(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			if (!(((AbstractMembrane)var1).hasRules())) {
				if (!(((AbstractMembrane)var2).hasRules())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "true", "L334",var0,var1,var2,var3,var4,var5,var6,var7,var8});
					} else if (execL334(var0,var1,var2,var3,var4,var5,var6,var7,var8,nondeterministic)) {
						ret = true;
						break L333;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL334(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			mem = ((AbstractMembrane)var2);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var2).recursiveLock();
			((AbstractMembrane)var0).moveCellsFrom(((AbstractMembrane)var1));
			((AbstractMembrane)var0).removeTemporaryProxies();
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var7), 1,
				((Atom)var4), 3 );
			((AbstractMembrane)var2).drop();
			((AbstractMembrane)var1).free();
			((AbstractMembrane)var2).free();
			ret = true;
			break L334;
		}
		return ret;
	}
	public boolean execL335(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L335:
		{
			if (execL337(var0, var1, nondeterministic)) {
				ret = true;
				break L335;
			}
			if (execL339(var0, var1, nondeterministic)) {
				ret = true;
				break L335;
			}
		}
		return ret;
	}
	public boolean execL339(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L339:
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
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var1).getArg(2);
									if (!(link.getPos() != 1)) {
										var11 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var8).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var14).getFunctor()))) {
													mem = ((Atom)var14).getMem();
													if (mem.lock()) {
														var15 = mem;
														link = ((Atom)var14).getArg(0);
														var16 = link;
														link = ((Atom)var14).getArg(1);
														var17 = link;
														link = ((Atom)var11).getArg(0);
														if (!(link.getPos() != 0)) {
															var18 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var18).getFunctor()))) {
																mem = ((Atom)var18).getMem();
																if (mem.lock()) {
																	var19 = mem;
																	link = ((Atom)var18).getArg(0);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var21 = link;
																	mem = ((AbstractMembrane)var15);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		mem = ((AbstractMembrane)var19);
																		if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																			if (execL333(var0,var15,var19,var6,var1,var8,var11,var14,var18,nondeterministic)) {
																				ret = true;
																				break L339;
																			}
																		}
																	}
																	((AbstractMembrane)var19).unlock();
																}
															}
														}
														((AbstractMembrane)var15).unlock();
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L337:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var3).getArg(2);
									if (!(link.getPos() != 1)) {
										var11 = link.getAtom();
										if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var8).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var14).getFunctor()))) {
													mem = ((Atom)var14).getMem();
													if (mem.lock()) {
														var15 = mem;
														link = ((Atom)var14).getArg(0);
														var16 = link;
														link = ((Atom)var14).getArg(1);
														var17 = link;
														link = ((Atom)var11).getArg(0);
														if (!(link.getPos() != 0)) {
															var18 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var18).getFunctor()))) {
																mem = ((Atom)var18).getMem();
																if (mem.lock()) {
																	var19 = mem;
																	link = ((Atom)var18).getArg(0);
																	var20 = link;
																	link = ((Atom)var18).getArg(1);
																	var21 = link;
																	mem = ((AbstractMembrane)var15);
																	if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																		mem = ((AbstractMembrane)var19);
																		if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 1)) {
																			if (execL333(var0,var15,var19,var1,var3,var8,var11,var14,var18,nondeterministic)) {
																				ret = true;
																				break L337;
																			}
																		}
																	}
																	((AbstractMembrane)var19).unlock();
																}
															}
														}
														((AbstractMembrane)var15).unlock();
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
	public boolean execL329(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L329:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL324(var0,var1,nondeterministic)) {
					ret = true;
					break L329;
				}
			}
		}
		return ret;
	}
	public boolean execL324(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "use", "L325",var0,var1});
			} else if (execL325(var0,var1,nondeterministic)) {
				ret = true;
				break L324;
			}
		}
		return ret;
	}
	public boolean execL325(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f4;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_boolean");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module boolean");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module boolean");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module boolean");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module boolean");
				}
			ret = true;
			break L325;
		}
		return ret;
	}
	public boolean execL326(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
L326:
		{
			if (execL328(var0, var1, nondeterministic)) {
				ret = true;
				break L326;
			}
		}
		return ret;
	}
	public boolean execL328(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		BigInteger x, y;
		BigDecimal u, v;
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
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (execL324(var0,var1,nondeterministic)) {
						ret = true;
						break L328;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f0 = new Functor("false", 1, null);
	private static final Functor f3 = new Functor("use", 0, "if");
	private static final Functor f1 = new Functor("if", 4, null);
	private static final Functor f4 = new Functor("use", 0, "boolean");
	private static final Functor f2 = new Functor("true", 1, null);
}

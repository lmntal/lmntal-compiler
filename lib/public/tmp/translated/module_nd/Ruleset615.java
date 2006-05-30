package translated.module_nd;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset615 extends Ruleset {
	private static final Ruleset615 theInstance = new Ruleset615();
	private Ruleset615() {}
	public static Ruleset615 getInstance() {
		return theInstance;
	}
	private int id = 615;
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
"(nd.exec({$p, @p, {$q, @q}*}) :- [:/*inline*/\t\t\tAtom out = me.nthAtom(0);\t\t\tAtom in = out.nthAtom(0);\t\t\tAtom plus = in.nthAtom(1);\t\t\tMembrane mem1 = (Membrane)in.getMem();\t\t\tIterator it = mem1.memIterator();\t\t\tMembrane mem2 = (Membrane)it.next();\t\t\t((Task)mem.getTask()).nondeterministicExec(mem2);\t\t\tmem.removeAtom(me);\t\t\tmem.removeAtom(out);\t\t\tmem1.removeAtom(in);\t\t\tplus.dequeue();\t\t\tmem1.removeAtom(plus);\t\t:]({$p, @p, {$q, @q}*})), (nd.genid :- nd.nextid(0)), (nd.nextid(N), {$p, @p} :- '\\\\+'('='($p, id(X), $pp)), '='(N2, '+'(N, 1)) | nd.nextid(N2), {id(N), $p, @p}), (nd.gen_from_list, {id(N), $p, @p} :- uniq(N) | nd.gen_from_list, {id(N), fl('[]'), $p, @p}), (reduce(F, T, R), {id(N), from(F), fl(FL), $p[FL|*W], @p}, {id(N2), to(T), $q, @q} :- uniq(N, N2) | reduce(F, T, R), {id(N), from(F), fl('.'(N2, FL)), $p[FL|*W], @p}, {id(N2), to(T), $q, @q}), (nd.gen_to_list, {id(N), $p, @p} :- uniq(N) | nd.gen_to_list, {id(N), tl('[]'), $p, @p}), (reduce(F, T, R), {id(NF), from(F), $p, @p}, {id(NT), to(T), tl(TL), $q[TL|*W], @q} :- uniq(NF, NT) | reduce(F, T, R), {id(NF), from(F), $p, @p}, {id(NT), to(T), tl('.'(NF, TL)), $q[TL|*W], @q})";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1511(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "exec");
			return true;
		}
		if (execL1524(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "genid");
			return true;
		}
		if (execL1533(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "nextid");
			return true;
		}
		if (execL1545(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "gen_from_list");
			return true;
		}
		if (execL1556(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "reduce");
			return true;
		}
		if (execL1579(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "gen_to_list");
			return true;
		}
		if (execL1590(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@615", "reduce");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1518(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "exec");
			return true;
		}
		if (execL1527(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "genid");
			return true;
		}
		if (execL1536(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "nextid");
			return true;
		}
		if (execL1550(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "gen_from_list");
			return true;
		}
		if (execL1573(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "reduce");
			return true;
		}
		if (execL1584(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "gen_to_list");
			return true;
		}
		if (execL1607(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@615", "reduce");
			return true;
		}
		return result;
	}
	public boolean execL1607(Object var0, boolean nondeterministic) {
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
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
L1607:
		{
			var17 = new HashSet();
			var15 = new HashSet();
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var6).getFunctor()))) {
									mem = ((Atom)var6).getMem();
									if (mem.lock()) {
										var7 = mem;
										link = ((Atom)var6).getArg(1);
										if (!(link.getPos() != 0)) {
											var9 = link.getAtom();
											if (!(!(f1).equals(((Atom)var9).getFunctor()))) {
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
													link = ((Atom)var2).getArg(0);
													if (!(link.getPos() != 0)) {
														var4 = link.getAtom();
														if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
															mem = ((Atom)var4).getMem();
															if (mem.lock()) {
																var5 = mem;
																link = ((Atom)var4).getArg(1);
																if (!(link.getPos() != 0)) {
																	var8 = link.getAtom();
																	if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
																		func = f3;
																		Iterator it2 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
																		while (it2.hasNext()) {
																			atom = (Atom) it2.next();
																			var10 = atom;
																			link = ((Atom)var10).getArg(0);
																			var13 = link;
																			((Set)var15).add(((Atom)var10));
																			((Set)var15).add(((Atom)var8));
																			((Set)var15).add(((Atom)var4));
																			isground_ret = ((Link)var13).isGround(((Set)var15));
																			if (!(isground_ret == -1)) {
																				var16 = new IntegerFunctor(isground_ret);
																				func = f3;
																				Iterator it3 = ((AbstractMembrane)var7).atomIteratorOfFunctor(func);
																				while (it3.hasNext()) {
																					atom = (Atom) it3.next();
																					var11 = atom;
																					link = ((Atom)var11).getArg(0);
																					var14 = link;
																					((Set)var17).add(((Atom)var11));
																					((Set)var17).add(((Atom)var9));
																					func = f4;
																					Iterator it4 = ((AbstractMembrane)var7).atomIteratorOfFunctor(func);
																					while (it4.hasNext()) {
																						atom = (Atom) it4.next();
																						var12 = atom;
																						((Set)var17).add(((Atom)var12));
																						((Set)var17).add(((Atom)var6));
																						isground_ret = ((Link)var14).isGround(((Set)var17));
																						if (!(isground_ret == -1)) {
																							var18 = new IntegerFunctor(isground_ret);
																							boolean goAhead = uniq0.check(new Link[] {(Link)var13,(Link)var14																							});
																							if(goAhead) {
																								if (execL1589(var0,var5,var7,var1,var2,var3,var10,var8,var4,var11,var9,var12,var6,nondeterministic)) {
																									ret = true;
																									break L1607;
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
																((AbstractMembrane)var5).unlock();
															}
														}
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
	public boolean execL1589(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1589:
		{
			link = ((Atom)var11).getArg(0);
			var34 = link;
			link = ((Atom)var6).getArg(0);
			var13 = link;
			link = ((Atom)var9).getArg(0);
			var14 = link;
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var11);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeGround(((Link)var13));
			((AbstractMembrane)var2).removeGround(((Link)var14));
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			((AbstractMembrane)var2).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var2));
			var17 = ((AbstractMembrane)var1).copyGroundFrom(((Link)var13));
			var18 = ((AbstractMembrane)var2).copyGroundFrom(((Link)var13));
			var19 = ((AbstractMembrane)var2).copyGroundFrom(((Link)var14));
			func = Functor.INSIDE_PROXY;
			var22 = ((AbstractMembrane)var1).newAtom(func);
			func = f5;
			var25 = ((AbstractMembrane)var2).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var27 = ((AbstractMembrane)var2).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var29 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var30 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var6), 0);
			var31 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var31),
				((Link)var17));
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var22), 1 );
			link = new Link(((Atom)var9), 0);
			var32 = link;
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var32),
				((Link)var19));
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var27), 1 );
			link = new Link(((Atom)var25), 0);
			var33 = link;
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var33),
				((Link)var18));
			((Atom)var25).getMem().inheritLink(
				((Atom)var25), 1,
				(Link)var34 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 2,
				((Atom)var11), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var29), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var30), 1 );
			((Atom)var29).getMem().newLink(
				((Atom)var29), 0,
				((Atom)var22), 0 );
			((Atom)var30).getMem().newLink(
				((Atom)var30), 0,
				((Atom)var27), 0 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			((AbstractMembrane)var1).forceUnlock();
			((AbstractMembrane)var2).forceUnlock();
			ret = true;
			break L1589;
		}
		return ret;
	}
	public boolean execL1590(Object var0, Object var1, boolean nondeterministic) {
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
L1590:
		{
			if (execL1592(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
			if (execL1596(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
			if (execL1598(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
			if (execL1601(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
			if (execL1603(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
			if (execL1605(var0, var1, nondeterministic)) {
				ret = true;
				break L1590;
			}
		}
		return ret;
	}
	public boolean execL1605(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1605:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f3;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									var31 = link;
									((Set)var34).add(((Atom)var5));
									func = f1;
									Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
									while (it2.hasNext()) {
										atom = (Atom) it2.next();
										var7 = atom;
										link = ((Atom)var7).getArg(0);
										var8 = link;
										link = ((Atom)var7).getArg(0);
										if (!(link.getPos() != 1)) {
											var9 = link.getAtom();
											((Set)var34).add(((Atom)var7));
											((Set)var34).add(((Atom)var1));
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												link = ((Atom)var9).getArg(1);
												var11 = link;
												link = ((Atom)var9).getArg(0);
												if (!(link.getPos() != 0)) {
													var12 = link.getAtom();
													((Set)var34).add(((Atom)var9));
													isground_ret = ((Link)var31).isGround(((Set)var34));
													if (!(isground_ret == -1)) {
														var35 = new IntegerFunctor(isground_ret);
														if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															link = ((Atom)var12).getArg(1);
															var14 = link;
															link = ((Atom)var12).getArg(1);
															if (!(link.getPos() != 1)) {
																var15 = link.getAtom();
																if (!(!(f0).equals(((Atom)var15).getFunctor()))) {
																	link = ((Atom)var15).getArg(0);
																	var16 = link;
																	link = ((Atom)var15).getArg(1);
																	var17 = link;
																	link = ((Atom)var15).getArg(2);
																	var18 = link;
																	link = ((Atom)var15).getArg(0);
																	if (!(link.getPos() != 1)) {
																		var19 = link.getAtom();
																		if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var19).getFunctor()))) {
																			link = ((Atom)var19).getArg(0);
																			var20 = link;
																			link = ((Atom)var19).getArg(1);
																			var21 = link;
																			link = ((Atom)var19).getArg(0);
																			if (!(link.getPos() != 0)) {
																				var22 = link.getAtom();
																				if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var22).getFunctor()))) {
																					mem = ((Atom)var22).getMem();
																					if (mem.lock()) {
																						var23 = mem;
																						link = ((Atom)var22).getArg(0);
																						var24 = link;
																						link = ((Atom)var22).getArg(1);
																						var25 = link;
																						link = ((Atom)var22).getArg(1);
																						if (!(link.getPos() != 0)) {
																							var26 = link.getAtom();
																							if (!(!(f2).equals(((Atom)var26).getFunctor()))) {
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								func = f3;
																								Iterator it3 = ((AbstractMembrane)var23).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									link = ((Atom)var28).getArg(0);
																									var30 = link;
																									((Set)var32).add(((Atom)var28));
																									((Set)var32).add(((Atom)var26));
																									((Set)var32).add(((Atom)var22));
																									isground_ret = ((Link)var30).isGround(((Set)var32));
																									if (!(isground_ret == -1)) {
																										var33 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq0.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1589(var0,var23,var2,var15,var19,var12,var28,var26,var22,var5,var7,var1,var9,nondeterministic)) {
																												ret = true;
																												break L1605;
																											}
																										}
																									}
																								}
																							}
																						}
																						((AbstractMembrane)var23).unlock();
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
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1603(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1603:
		{
			var34 = new HashSet();
			var32 = new HashSet();
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
											link = ((Atom)var11).getArg(2);
											var14 = link;
											link = ((Atom)var11).getArg(0);
											if (!(link.getPos() != 1)) {
												var15 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													link = ((Atom)var15).getArg(1);
													var17 = link;
													link = ((Atom)var15).getArg(0);
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
																link = ((Atom)var18).getArg(1);
																if (!(link.getPos() != 0)) {
																	var22 = link.getAtom();
																	if (!(!(f2).equals(((Atom)var22).getFunctor()))) {
																		link = ((Atom)var22).getArg(0);
																		var23 = link;
																		mem = ((AbstractMembrane)var2);
																		if (mem.lock()) {
																			mem = ((AbstractMembrane)var2).getParent();
																			if (!(mem == null)) {
																				var3 = mem;
																				if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																					func = f3;
																					Iterator it1 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																					while (it1.hasNext()) {
																						atom = (Atom) it1.next();
																						var24 = atom;
																						link = ((Atom)var24).getArg(0);
																						var25 = link;
																						link = ((Atom)var24).getArg(0);
																						var30 = link;
																						((Set)var32).add(((Atom)var24));
																						((Set)var32).add(((Atom)var22));
																						((Set)var32).add(((Atom)var18));
																						isground_ret = ((Link)var30).isGround(((Set)var32));
																						if (!(isground_ret == -1)) {
																							var33 = new IntegerFunctor(isground_ret);
																							func = f3;
																							Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																							while (it2.hasNext()) {
																								atom = (Atom) it2.next();
																								var26 = atom;
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								link = ((Atom)var26).getArg(0);
																								var31 = link;
																								((Set)var34).add(((Atom)var26));
																								((Set)var34).add(((Atom)var1));
																								func = f4;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var34).add(((Atom)var28));
																									((Set)var34).add(((Atom)var5));
																									isground_ret = ((Link)var31).isGround(((Set)var34));
																									if (!(isground_ret == -1)) {
																										var35 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq0.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1589(var0,var19,var2,var11,var15,var8,var24,var22,var18,var26,var1,var28,var5,nondeterministic)) {
																												ret = true;
																												break L1603;
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
																((AbstractMembrane)var19).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1601(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1601:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var31 = link;
					((Set)var34).add(((Atom)var1));
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f1;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									if (!(link.getPos() != 1)) {
										var7 = link.getAtom();
										((Set)var34).add(((Atom)var5));
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
											link = ((Atom)var7).getArg(0);
											var8 = link;
											link = ((Atom)var7).getArg(1);
											var9 = link;
											link = ((Atom)var7).getArg(0);
											if (!(link.getPos() != 0)) {
												var10 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var10).getFunctor()))) {
													link = ((Atom)var10).getArg(0);
													var11 = link;
													link = ((Atom)var10).getArg(1);
													var12 = link;
													link = ((Atom)var10).getArg(1);
													if (!(link.getPos() != 1)) {
														var13 = link.getAtom();
														if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
															link = ((Atom)var13).getArg(0);
															var14 = link;
															link = ((Atom)var13).getArg(1);
															var15 = link;
															link = ((Atom)var13).getArg(2);
															var16 = link;
															link = ((Atom)var13).getArg(0);
															if (!(link.getPos() != 1)) {
																var17 = link.getAtom();
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
																						func = f3;
																						Iterator it2 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																						while (it2.hasNext()) {
																							atom = (Atom) it2.next();
																							var26 = atom;
																							link = ((Atom)var26).getArg(0);
																							var27 = link;
																							link = ((Atom)var26).getArg(0);
																							var30 = link;
																							((Set)var32).add(((Atom)var26));
																							((Set)var32).add(((Atom)var24));
																							((Set)var32).add(((Atom)var20));
																							isground_ret = ((Link)var30).isGround(((Set)var32));
																							if (!(isground_ret == -1)) {
																								var33 = new IntegerFunctor(isground_ret);
																								func = f4;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var34).add(((Atom)var28));
																									((Set)var34).add(((Atom)var7));
																									isground_ret = ((Link)var31).isGround(((Set)var34));
																									if (!(isground_ret == -1)) {
																										var35 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq0.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1589(var0,var21,var2,var13,var17,var10,var26,var24,var20,var1,var5,var28,var7,nondeterministic)) {
																												ret = true;
																												break L1601;
																											}
																										}
																									}
																								}
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
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1598(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1598:
		{
			var34 = new HashSet();
			var32 = new HashSet();
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
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f0).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(2);
											var14 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 1)) {
												var15 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													link = ((Atom)var15).getArg(1);
													var17 = link;
													link = ((Atom)var15).getArg(0);
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
																link = ((Atom)var18).getArg(1);
																if (!(link.getPos() != 0)) {
																	var22 = link.getAtom();
																	if (!(!(f1).equals(((Atom)var22).getFunctor()))) {
																		link = ((Atom)var22).getArg(0);
																		var23 = link;
																		mem = ((AbstractMembrane)var2);
																		if (mem.lock()) {
																			mem = ((AbstractMembrane)var2).getParent();
																			if (!(mem == null)) {
																				var3 = mem;
																				if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																					func = f3;
																					Iterator it1 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																					while (it1.hasNext()) {
																						atom = (Atom) it1.next();
																						var24 = atom;
																						link = ((Atom)var24).getArg(0);
																						var25 = link;
																						link = ((Atom)var24).getArg(0);
																						var31 = link;
																						((Set)var34).add(((Atom)var24));
																						((Set)var34).add(((Atom)var22));
																						func = f4;
																						Iterator it2 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																						while (it2.hasNext()) {
																							atom = (Atom) it2.next();
																							var26 = atom;
																							link = ((Atom)var26).getArg(0);
																							var27 = link;
																							((Set)var34).add(((Atom)var26));
																							((Set)var34).add(((Atom)var18));
																							isground_ret = ((Link)var31).isGround(((Set)var34));
																							if (!(isground_ret == -1)) {
																								var35 = new IntegerFunctor(isground_ret);
																								func = f3;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									link = ((Atom)var28).getArg(0);
																									var30 = link;
																									((Set)var32).add(((Atom)var28));
																									((Set)var32).add(((Atom)var1));
																									((Set)var32).add(((Atom)var5));
																									isground_ret = ((Link)var30).isGround(((Set)var32));
																									if (!(isground_ret == -1)) {
																										var33 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq0.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1589(var0,var2,var19,var11,var8,var15,var28,var1,var5,var24,var22,var26,var18,nondeterministic)) {
																												ret = true;
																												break L1598;
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
																((AbstractMembrane)var19).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1596(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1596:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var30 = link;
					((Set)var32).add(((Atom)var1));
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f2;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									if (!(link.getPos() != 1)) {
										var7 = link.getAtom();
										((Set)var32).add(((Atom)var5));
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
											link = ((Atom)var7).getArg(0);
											var8 = link;
											link = ((Atom)var7).getArg(1);
											var9 = link;
											link = ((Atom)var7).getArg(0);
											if (!(link.getPos() != 0)) {
												var10 = link.getAtom();
												((Set)var32).add(((Atom)var7));
												isground_ret = ((Link)var30).isGround(((Set)var32));
												if (!(isground_ret == -1)) {
													var33 = new IntegerFunctor(isground_ret);
													if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var10).getFunctor()))) {
														link = ((Atom)var10).getArg(0);
														var11 = link;
														link = ((Atom)var10).getArg(1);
														var12 = link;
														link = ((Atom)var10).getArg(1);
														if (!(link.getPos() != 0)) {
															var13 = link.getAtom();
															if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
																link = ((Atom)var13).getArg(0);
																var14 = link;
																link = ((Atom)var13).getArg(1);
																var15 = link;
																link = ((Atom)var13).getArg(2);
																var16 = link;
																link = ((Atom)var13).getArg(1);
																if (!(link.getPos() != 1)) {
																	var17 = link.getAtom();
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
																						if (!(!(f1).equals(((Atom)var24).getFunctor()))) {
																							link = ((Atom)var24).getArg(0);
																							var25 = link;
																							func = f3;
																							Iterator it2 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																							while (it2.hasNext()) {
																								atom = (Atom) it2.next();
																								var26 = atom;
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								link = ((Atom)var26).getArg(0);
																								var31 = link;
																								((Set)var34).add(((Atom)var26));
																								((Set)var34).add(((Atom)var24));
																								func = f4;
																								Iterator it3 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var34).add(((Atom)var28));
																									((Set)var34).add(((Atom)var20));
																									isground_ret = ((Link)var31).isGround(((Set)var34));
																									if (!(isground_ret == -1)) {
																										var35 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq0.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1589(var0,var2,var21,var13,var10,var17,var1,var5,var7,var26,var24,var28,var20,nondeterministic)) {
																												ret = true;
																												break L1596;
																											}
																										}
																									}
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
		return ret;
	}
	public boolean execL1592(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
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
L1592:
		{
			var33 = new HashSet();
			var31 = new HashSet();
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 1)) {
							var8 = link.getAtom();
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(0);
								if (!(link.getPos() != 0)) {
									var15 = link.getAtom();
									if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
										mem = ((Atom)var15).getMem();
										if (mem.lock()) {
											var16 = mem;
											link = ((Atom)var15).getArg(0);
											var17 = link;
											link = ((Atom)var15).getArg(1);
											var18 = link;
											link = ((Atom)var15).getArg(1);
											if (!(link.getPos() != 0)) {
												var21 = link.getAtom();
												if (!(!(f1).equals(((Atom)var21).getFunctor()))) {
													link = ((Atom)var21).getArg(0);
													var22 = link;
													if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
														link = ((Atom)var5).getArg(0);
														var6 = link;
														link = ((Atom)var5).getArg(1);
														var7 = link;
														link = ((Atom)var5).getArg(0);
														if (!(link.getPos() != 0)) {
															var11 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
																mem = ((Atom)var11).getMem();
																if (mem.lock()) {
																	var12 = mem;
																	link = ((Atom)var11).getArg(0);
																	var13 = link;
																	link = ((Atom)var11).getArg(1);
																	var14 = link;
																	link = ((Atom)var11).getArg(1);
																	if (!(link.getPos() != 0)) {
																		var19 = link.getAtom();
																		if (!(!(f2).equals(((Atom)var19).getFunctor()))) {
																			link = ((Atom)var19).getArg(0);
																			var20 = link;
																			func = f3;
																			Iterator it1 = ((AbstractMembrane)var12).atomIteratorOfFunctor(func);
																			while (it1.hasNext()) {
																				atom = (Atom) it1.next();
																				var23 = atom;
																				link = ((Atom)var23).getArg(0);
																				var24 = link;
																				link = ((Atom)var23).getArg(0);
																				var29 = link;
																				((Set)var31).add(((Atom)var23));
																				((Set)var31).add(((Atom)var19));
																				((Set)var31).add(((Atom)var11));
																				isground_ret = ((Link)var29).isGround(((Set)var31));
																				if (!(isground_ret == -1)) {
																					var32 = new IntegerFunctor(isground_ret);
																					func = f3;
																					Iterator it2 = ((AbstractMembrane)var16).atomIteratorOfFunctor(func);
																					while (it2.hasNext()) {
																						atom = (Atom) it2.next();
																						var25 = atom;
																						link = ((Atom)var25).getArg(0);
																						var26 = link;
																						link = ((Atom)var25).getArg(0);
																						var30 = link;
																						((Set)var33).add(((Atom)var25));
																						((Set)var33).add(((Atom)var21));
																						func = f4;
																						Iterator it3 = ((AbstractMembrane)var16).atomIteratorOfFunctor(func);
																						while (it3.hasNext()) {
																							atom = (Atom) it3.next();
																							var27 = atom;
																							link = ((Atom)var27).getArg(0);
																							var28 = link;
																							((Set)var33).add(((Atom)var27));
																							((Set)var33).add(((Atom)var15));
																							isground_ret = ((Link)var30).isGround(((Set)var33));
																							if (!(isground_ret == -1)) {
																								var34 = new IntegerFunctor(isground_ret);
																								boolean goAhead = uniq0.check(new Link[] {(Link)var29,(Link)var30																								});
																								if(goAhead) {
																									if (execL1589(var0,var12,var16,var1,var5,var8,var23,var19,var11,var25,var21,var27,var15,nondeterministic)) {
																										ret = true;
																										break L1592;
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																	((AbstractMembrane)var12).unlock();
																}
															}
														}
													}
												}
											}
											((AbstractMembrane)var16).unlock();
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
	public boolean execL1584(Object var0, boolean nondeterministic) {
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
L1584:
		{
			var5 = new HashSet();
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				Iterator it2 = ((AbstractMembrane)var0).memIterator();
				while (it2.hasNext()) {
					mem = (AbstractMembrane) it2.next();
					if ((mem.getKind() != 0))
						continue;
					if (mem.lock()) {
						var2 = mem;
						func = f3;
						Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
						while (it3.hasNext()) {
							atom = (Atom) it3.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							((Set)var5).add(((Atom)var3));
							isground_ret = ((Link)var4).isGround(((Set)var5));
							if (!(isground_ret == -1)) {
								var6 = new IntegerFunctor(isground_ret);
								boolean goAhead = uniq1.check(new Link[] {(Link)var4								});
								if(goAhead) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "gen_to_list", "L1578",var0,var2,var1,var3});
									} else if (execL1578(var0,var2,var1,var3,nondeterministic)) {
										ret = true;
										break L1584;
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1578(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1578:
		{
			link = ((Atom)var3).getArg(0);
			var4 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			((AbstractMembrane)var1).removeGround(((Link)var4));
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var6 = ((AbstractMembrane)var1).copyGroundFrom(((Link)var4));
			func = f7;
			var8 = ((AbstractMembrane)var1).newAtom(func);
			func = f4;
			var9 = ((AbstractMembrane)var1).newAtom(func);
			link = new Link(((Atom)var3), 0);
			var11 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var11),
				((Link)var6));
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
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
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1578;
		}
		return ret;
	}
	public boolean execL1579(Object var0, Object var1, boolean nondeterministic) {
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
L1579:
		{
			if (execL1581(var0, var1, nondeterministic)) {
				ret = true;
				break L1579;
			}
			if (execL1583(var0, var1, nondeterministic)) {
				ret = true;
				break L1579;
			}
		}
		return ret;
	}
	public boolean execL1583(Object var0, Object var1, boolean nondeterministic) {
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
L1583:
		{
			var7 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link;
					((Set)var7).add(((Atom)var1));
					isground_ret = ((Link)var6).isGround(((Set)var7));
					if (!(isground_ret == -1)) {
						var8 = new IntegerFunctor(isground_ret);
						mem = ((AbstractMembrane)var2);
						if (mem.lock()) {
							mem = ((AbstractMembrane)var2).getParent();
							if (!(mem == null)) {
								var3 = mem;
								if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
									func = f6;
									Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var5 = atom;
										boolean goAhead = uniq1.check(new Link[] {(Link)var6										});
										if(goAhead) {
											if (nondeterministic) {
												Task.states.add(new Object[] {theInstance, "gen_to_list", "L1578",var0,var2,var5,var1});
											} else if (execL1578(var0,var2,var5,var1,nondeterministic)) {
												ret = true;
												break L1583;
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
		return ret;
	}
	public boolean execL1581(Object var0, Object var1, boolean nondeterministic) {
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
L1581:
		{
			var6 = new HashSet();
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					Iterator it1 = ((AbstractMembrane)var0).memIterator();
					while (it1.hasNext()) {
						mem = (AbstractMembrane) it1.next();
						if ((mem.getKind() != 0))
							continue;
						if (mem.lock()) {
							var2 = mem;
							func = f3;
							Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
							while (it2.hasNext()) {
								atom = (Atom) it2.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(0);
								var5 = link;
								((Set)var6).add(((Atom)var3));
								isground_ret = ((Link)var5).isGround(((Set)var6));
								if (!(isground_ret == -1)) {
									var7 = new IntegerFunctor(isground_ret);
									boolean goAhead = uniq1.check(new Link[] {(Link)var5									});
									if(goAhead) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "gen_to_list", "L1578",var0,var2,var1,var3});
										} else if (execL1578(var0,var2,var1,var3,nondeterministic)) {
											ret = true;
											break L1581;
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
		return ret;
	}
	public boolean execL1573(Object var0, boolean nondeterministic) {
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
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
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
L1573:
		{
			var17 = new HashSet();
			var15 = new HashSet();
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var6).getFunctor()))) {
									mem = ((Atom)var6).getMem();
									if (mem.lock()) {
										var7 = mem;
										link = ((Atom)var6).getArg(1);
										if (!(link.getPos() != 0)) {
											var9 = link.getAtom();
											if (!(!(f1).equals(((Atom)var9).getFunctor()))) {
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
													link = ((Atom)var2).getArg(0);
													if (!(link.getPos() != 0)) {
														var4 = link.getAtom();
														if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
															mem = ((Atom)var4).getMem();
															if (mem.lock()) {
																var5 = mem;
																link = ((Atom)var4).getArg(1);
																if (!(link.getPos() != 0)) {
																	var8 = link.getAtom();
																	if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
																		func = f3;
																		Iterator it2 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
																		while (it2.hasNext()) {
																			atom = (Atom) it2.next();
																			var10 = atom;
																			link = ((Atom)var10).getArg(0);
																			var13 = link;
																			((Set)var15).add(((Atom)var10));
																			((Set)var15).add(((Atom)var8));
																			func = f8;
																			Iterator it3 = ((AbstractMembrane)var5).atomIteratorOfFunctor(func);
																			while (it3.hasNext()) {
																				atom = (Atom) it3.next();
																				var11 = atom;
																				((Set)var15).add(((Atom)var11));
																				((Set)var15).add(((Atom)var4));
																				isground_ret = ((Link)var13).isGround(((Set)var15));
																				if (!(isground_ret == -1)) {
																					var16 = new IntegerFunctor(isground_ret);
																					func = f3;
																					Iterator it4 = ((AbstractMembrane)var7).atomIteratorOfFunctor(func);
																					while (it4.hasNext()) {
																						atom = (Atom) it4.next();
																						var12 = atom;
																						link = ((Atom)var12).getArg(0);
																						var14 = link;
																						((Set)var17).add(((Atom)var12));
																						((Set)var17).add(((Atom)var9));
																						((Set)var17).add(((Atom)var6));
																						isground_ret = ((Link)var14).isGround(((Set)var17));
																						if (!(isground_ret == -1)) {
																							var18 = new IntegerFunctor(isground_ret);
																							boolean goAhead = uniq2.check(new Link[] {(Link)var13,(Link)var14																							});
																							if(goAhead) {
																								if (execL1555(var0,var5,var7,var1,var2,var3,var10,var8,var11,var4,var12,var9,var6,nondeterministic)) {
																									ret = true;
																									break L1573;
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
																((AbstractMembrane)var5).unlock();
															}
														}
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
	public boolean execL1555(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1555:
		{
			link = ((Atom)var8).getArg(0);
			var34 = link;
			link = ((Atom)var10).getArg(0);
			var13 = link;
			link = ((Atom)var6).getArg(0);
			var14 = link;
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var11);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var2).removeGround(((Link)var13));
			((AbstractMembrane)var1).removeGround(((Link)var14));
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			((AbstractMembrane)var2).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var2));
			var17 = ((AbstractMembrane)var1).copyGroundFrom(((Link)var13));
			var18 = ((AbstractMembrane)var2).copyGroundFrom(((Link)var13));
			var19 = ((AbstractMembrane)var1).copyGroundFrom(((Link)var14));
			func = f5;
			var22 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var24 = ((AbstractMembrane)var1).newAtom(func);
			func = Functor.INSIDE_PROXY;
			var27 = ((AbstractMembrane)var2).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var29 = ((AbstractMembrane)var0).newAtom(func);
			func = Functor.OUTSIDE_PROXY;
			var30 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var6), 0);
			var31 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var31),
				((Link)var19));
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var24), 1 );
			link = new Link(((Atom)var22), 0);
			var32 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var32),
				((Link)var17));
			((Atom)var22).getMem().inheritLink(
				((Atom)var22), 1,
				(Link)var34 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 2,
				((Atom)var8), 0 );
			link = new Link(((Atom)var10), 0);
			var33 = link;
			mem = ((AbstractMembrane)var2);
			mem.unifyLinkBuddies(
				((Link)var33),
				((Link)var18));
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var27), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var29), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 1,
				((Atom)var30), 1 );
			((Atom)var29).getMem().newLink(
				((Atom)var29), 0,
				((Atom)var24), 0 );
			((Atom)var30).getMem().newLink(
				((Atom)var30), 0,
				((Atom)var27), 0 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			((AbstractMembrane)var1).forceUnlock();
			((AbstractMembrane)var2).forceUnlock();
			ret = true;
			break L1555;
		}
		return ret;
	}
	public boolean execL1556(Object var0, Object var1, boolean nondeterministic) {
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
L1556:
		{
			if (execL1558(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
			if (execL1562(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
			if (execL1564(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
			if (execL1566(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
			if (execL1569(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
			if (execL1571(var0, var1, nondeterministic)) {
				ret = true;
				break L1556;
			}
		}
		return ret;
	}
	public boolean execL1571(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1571:
		{
			var34 = new HashSet();
			var32 = new HashSet();
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
											link = ((Atom)var11).getArg(2);
											var14 = link;
											link = ((Atom)var11).getArg(0);
											if (!(link.getPos() != 1)) {
												var15 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													link = ((Atom)var15).getArg(1);
													var17 = link;
													link = ((Atom)var15).getArg(0);
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
																link = ((Atom)var18).getArg(1);
																if (!(link.getPos() != 0)) {
																	var22 = link.getAtom();
																	if (!(!(f2).equals(((Atom)var22).getFunctor()))) {
																		link = ((Atom)var22).getArg(0);
																		var23 = link;
																		mem = ((AbstractMembrane)var2);
																		if (mem.lock()) {
																			mem = ((AbstractMembrane)var2).getParent();
																			if (!(mem == null)) {
																				var3 = mem;
																				if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																					func = f3;
																					Iterator it1 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																					while (it1.hasNext()) {
																						atom = (Atom) it1.next();
																						var24 = atom;
																						link = ((Atom)var24).getArg(0);
																						var25 = link;
																						link = ((Atom)var24).getArg(0);
																						var30 = link;
																						((Set)var32).add(((Atom)var24));
																						((Set)var32).add(((Atom)var22));
																						func = f8;
																						Iterator it2 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																						while (it2.hasNext()) {
																							atom = (Atom) it2.next();
																							var26 = atom;
																							link = ((Atom)var26).getArg(0);
																							var27 = link;
																							((Set)var32).add(((Atom)var26));
																							((Set)var32).add(((Atom)var18));
																							isground_ret = ((Link)var30).isGround(((Set)var32));
																							if (!(isground_ret == -1)) {
																								var33 = new IntegerFunctor(isground_ret);
																								func = f3;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									link = ((Atom)var28).getArg(0);
																									var31 = link;
																									((Set)var34).add(((Atom)var28));
																									((Set)var34).add(((Atom)var1));
																									((Set)var34).add(((Atom)var5));
																									isground_ret = ((Link)var31).isGround(((Set)var34));
																									if (!(isground_ret == -1)) {
																										var35 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq2.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1555(var0,var19,var2,var11,var15,var8,var24,var22,var26,var18,var28,var1,var5,nondeterministic)) {
																												ret = true;
																												break L1571;
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
																((AbstractMembrane)var19).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1569(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1569:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var31 = link;
					((Set)var34).add(((Atom)var1));
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f1;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									if (!(link.getPos() != 1)) {
										var7 = link.getAtom();
										((Set)var34).add(((Atom)var5));
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
											link = ((Atom)var7).getArg(0);
											var8 = link;
											link = ((Atom)var7).getArg(1);
											var9 = link;
											link = ((Atom)var7).getArg(0);
											if (!(link.getPos() != 0)) {
												var10 = link.getAtom();
												((Set)var34).add(((Atom)var7));
												isground_ret = ((Link)var31).isGround(((Set)var34));
												if (!(isground_ret == -1)) {
													var35 = new IntegerFunctor(isground_ret);
													if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var10).getFunctor()))) {
														link = ((Atom)var10).getArg(0);
														var11 = link;
														link = ((Atom)var10).getArg(1);
														var12 = link;
														link = ((Atom)var10).getArg(1);
														if (!(link.getPos() != 1)) {
															var13 = link.getAtom();
															if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
																link = ((Atom)var13).getArg(0);
																var14 = link;
																link = ((Atom)var13).getArg(1);
																var15 = link;
																link = ((Atom)var13).getArg(2);
																var16 = link;
																link = ((Atom)var13).getArg(0);
																if (!(link.getPos() != 1)) {
																	var17 = link.getAtom();
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
																							func = f3;
																							Iterator it2 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																							while (it2.hasNext()) {
																								atom = (Atom) it2.next();
																								var26 = atom;
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								link = ((Atom)var26).getArg(0);
																								var30 = link;
																								((Set)var32).add(((Atom)var26));
																								((Set)var32).add(((Atom)var24));
																								func = f8;
																								Iterator it3 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var32).add(((Atom)var28));
																									((Set)var32).add(((Atom)var20));
																									isground_ret = ((Link)var30).isGround(((Set)var32));
																									if (!(isground_ret == -1)) {
																										var33 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq2.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1555(var0,var21,var2,var13,var17,var10,var26,var24,var28,var20,var1,var5,var7,nondeterministic)) {
																												ret = true;
																												break L1569;
																											}
																										}
																									}
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
		return ret;
	}
	public boolean execL1566(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1566:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f3;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									var30 = link;
									((Set)var32).add(((Atom)var5));
									func = f2;
									Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
									while (it2.hasNext()) {
										atom = (Atom) it2.next();
										var7 = atom;
										link = ((Atom)var7).getArg(0);
										var8 = link;
										link = ((Atom)var7).getArg(0);
										if (!(link.getPos() != 1)) {
											var9 = link.getAtom();
											((Set)var32).add(((Atom)var7));
											((Set)var32).add(((Atom)var1));
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												link = ((Atom)var9).getArg(1);
												var11 = link;
												link = ((Atom)var9).getArg(0);
												if (!(link.getPos() != 0)) {
													var12 = link.getAtom();
													((Set)var32).add(((Atom)var9));
													isground_ret = ((Link)var30).isGround(((Set)var32));
													if (!(isground_ret == -1)) {
														var33 = new IntegerFunctor(isground_ret);
														if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															link = ((Atom)var12).getArg(1);
															var14 = link;
															link = ((Atom)var12).getArg(1);
															if (!(link.getPos() != 0)) {
																var15 = link.getAtom();
																if (!(!(f0).equals(((Atom)var15).getFunctor()))) {
																	link = ((Atom)var15).getArg(0);
																	var16 = link;
																	link = ((Atom)var15).getArg(1);
																	var17 = link;
																	link = ((Atom)var15).getArg(2);
																	var18 = link;
																	link = ((Atom)var15).getArg(1);
																	if (!(link.getPos() != 1)) {
																		var19 = link.getAtom();
																		if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var19).getFunctor()))) {
																			link = ((Atom)var19).getArg(0);
																			var20 = link;
																			link = ((Atom)var19).getArg(1);
																			var21 = link;
																			link = ((Atom)var19).getArg(0);
																			if (!(link.getPos() != 0)) {
																				var22 = link.getAtom();
																				if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var22).getFunctor()))) {
																					mem = ((Atom)var22).getMem();
																					if (mem.lock()) {
																						var23 = mem;
																						link = ((Atom)var22).getArg(0);
																						var24 = link;
																						link = ((Atom)var22).getArg(1);
																						var25 = link;
																						link = ((Atom)var22).getArg(1);
																						if (!(link.getPos() != 0)) {
																							var26 = link.getAtom();
																							if (!(!(f1).equals(((Atom)var26).getFunctor()))) {
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								func = f3;
																								Iterator it3 = ((AbstractMembrane)var23).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									link = ((Atom)var28).getArg(0);
																									var31 = link;
																									((Set)var34).add(((Atom)var28));
																									((Set)var34).add(((Atom)var26));
																									((Set)var34).add(((Atom)var22));
																									isground_ret = ((Link)var31).isGround(((Set)var34));
																									if (!(isground_ret == -1)) {
																										var35 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq2.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1555(var0,var2,var23,var15,var12,var19,var5,var7,var1,var9,var28,var26,var22,nondeterministic)) {
																												ret = true;
																												break L1566;
																											}
																										}
																									}
																								}
																							}
																						}
																						((AbstractMembrane)var23).unlock();
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
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1564(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1564:
		{
			var34 = new HashSet();
			var32 = new HashSet();
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
									if (!(link.getPos() != 0)) {
										var11 = link.getAtom();
										if (!(!(f0).equals(((Atom)var11).getFunctor()))) {
											link = ((Atom)var11).getArg(0);
											var12 = link;
											link = ((Atom)var11).getArg(1);
											var13 = link;
											link = ((Atom)var11).getArg(2);
											var14 = link;
											link = ((Atom)var11).getArg(1);
											if (!(link.getPos() != 1)) {
												var15 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													link = ((Atom)var15).getArg(1);
													var17 = link;
													link = ((Atom)var15).getArg(0);
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
																link = ((Atom)var18).getArg(1);
																if (!(link.getPos() != 0)) {
																	var22 = link.getAtom();
																	if (!(!(f1).equals(((Atom)var22).getFunctor()))) {
																		link = ((Atom)var22).getArg(0);
																		var23 = link;
																		mem = ((AbstractMembrane)var2);
																		if (mem.lock()) {
																			mem = ((AbstractMembrane)var2).getParent();
																			if (!(mem == null)) {
																				var3 = mem;
																				if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
																					func = f3;
																					Iterator it1 = ((AbstractMembrane)var19).atomIteratorOfFunctor(func);
																					while (it1.hasNext()) {
																						atom = (Atom) it1.next();
																						var24 = atom;
																						link = ((Atom)var24).getArg(0);
																						var25 = link;
																						link = ((Atom)var24).getArg(0);
																						var31 = link;
																						((Set)var34).add(((Atom)var24));
																						((Set)var34).add(((Atom)var22));
																						((Set)var34).add(((Atom)var18));
																						isground_ret = ((Link)var31).isGround(((Set)var34));
																						if (!(isground_ret == -1)) {
																							var35 = new IntegerFunctor(isground_ret);
																							func = f3;
																							Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																							while (it2.hasNext()) {
																								atom = (Atom) it2.next();
																								var26 = atom;
																								link = ((Atom)var26).getArg(0);
																								var27 = link;
																								link = ((Atom)var26).getArg(0);
																								var30 = link;
																								((Set)var32).add(((Atom)var26));
																								((Set)var32).add(((Atom)var1));
																								func = f8;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var32).add(((Atom)var28));
																									((Set)var32).add(((Atom)var5));
																									isground_ret = ((Link)var30).isGround(((Set)var32));
																									if (!(isground_ret == -1)) {
																										var33 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq2.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1555(var0,var2,var19,var11,var8,var15,var26,var1,var28,var5,var24,var22,var18,nondeterministic)) {
																												ret = true;
																												break L1564;
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
																((AbstractMembrane)var19).unlock();
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
			}
		}
		return ret;
	}
	public boolean execL1562(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
		Object var35 = null;
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
L1562:
		{
			var34 = new HashSet();
			var32 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var30 = link;
					((Set)var32).add(((Atom)var1));
					mem = ((AbstractMembrane)var2);
					if (mem.lock()) {
						mem = ((AbstractMembrane)var2).getParent();
						if (!(mem == null)) {
							var3 = mem;
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								func = f2;
								Iterator it1 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									if (!(link.getPos() != 1)) {
										var7 = link.getAtom();
										((Set)var32).add(((Atom)var5));
										if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var7).getFunctor()))) {
											link = ((Atom)var7).getArg(0);
											var8 = link;
											link = ((Atom)var7).getArg(1);
											var9 = link;
											link = ((Atom)var7).getArg(0);
											if (!(link.getPos() != 0)) {
												var10 = link.getAtom();
												if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var10).getFunctor()))) {
													link = ((Atom)var10).getArg(0);
													var11 = link;
													link = ((Atom)var10).getArg(1);
													var12 = link;
													link = ((Atom)var10).getArg(1);
													if (!(link.getPos() != 0)) {
														var13 = link.getAtom();
														if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
															link = ((Atom)var13).getArg(0);
															var14 = link;
															link = ((Atom)var13).getArg(1);
															var15 = link;
															link = ((Atom)var13).getArg(2);
															var16 = link;
															link = ((Atom)var13).getArg(1);
															if (!(link.getPos() != 1)) {
																var17 = link.getAtom();
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
																					if (!(!(f1).equals(((Atom)var24).getFunctor()))) {
																						link = ((Atom)var24).getArg(0);
																						var25 = link;
																						func = f3;
																						Iterator it2 = ((AbstractMembrane)var21).atomIteratorOfFunctor(func);
																						while (it2.hasNext()) {
																							atom = (Atom) it2.next();
																							var26 = atom;
																							link = ((Atom)var26).getArg(0);
																							var27 = link;
																							link = ((Atom)var26).getArg(0);
																							var31 = link;
																							((Set)var34).add(((Atom)var26));
																							((Set)var34).add(((Atom)var24));
																							((Set)var34).add(((Atom)var20));
																							isground_ret = ((Link)var31).isGround(((Set)var34));
																							if (!(isground_ret == -1)) {
																								var35 = new IntegerFunctor(isground_ret);
																								func = f8;
																								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
																								while (it3.hasNext()) {
																									atom = (Atom) it3.next();
																									var28 = atom;
																									link = ((Atom)var28).getArg(0);
																									var29 = link;
																									((Set)var32).add(((Atom)var28));
																									((Set)var32).add(((Atom)var7));
																									isground_ret = ((Link)var30).isGround(((Set)var32));
																									if (!(isground_ret == -1)) {
																										var33 = new IntegerFunctor(isground_ret);
																										boolean goAhead = uniq2.check(new Link[] {(Link)var30,(Link)var31																										});
																										if(goAhead) {
																											if (execL1555(var0,var2,var21,var13,var10,var17,var1,var5,var28,var7,var26,var24,var20,nondeterministic)) {
																												ret = true;
																												break L1562;
																											}
																										}
																									}
																								}
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
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1558(Object var0, Object var1, boolean nondeterministic) {
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
		Object var31 = null;
		Object var32 = null;
		Object var33 = null;
		Object var34 = null;
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
L1558:
		{
			var33 = new HashSet();
			var31 = new HashSet();
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						if (!(link.getPos() != 1)) {
							var8 = link.getAtom();
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(0);
								if (!(link.getPos() != 0)) {
									var15 = link.getAtom();
									if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var15).getFunctor()))) {
										mem = ((Atom)var15).getMem();
										if (mem.lock()) {
											var16 = mem;
											link = ((Atom)var15).getArg(0);
											var17 = link;
											link = ((Atom)var15).getArg(1);
											var18 = link;
											link = ((Atom)var15).getArg(1);
											if (!(link.getPos() != 0)) {
												var21 = link.getAtom();
												if (!(!(f1).equals(((Atom)var21).getFunctor()))) {
													link = ((Atom)var21).getArg(0);
													var22 = link;
													if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
														link = ((Atom)var5).getArg(0);
														var6 = link;
														link = ((Atom)var5).getArg(1);
														var7 = link;
														link = ((Atom)var5).getArg(0);
														if (!(link.getPos() != 0)) {
															var11 = link.getAtom();
															if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var11).getFunctor()))) {
																mem = ((Atom)var11).getMem();
																if (mem.lock()) {
																	var12 = mem;
																	link = ((Atom)var11).getArg(0);
																	var13 = link;
																	link = ((Atom)var11).getArg(1);
																	var14 = link;
																	link = ((Atom)var11).getArg(1);
																	if (!(link.getPos() != 0)) {
																		var19 = link.getAtom();
																		if (!(!(f2).equals(((Atom)var19).getFunctor()))) {
																			link = ((Atom)var19).getArg(0);
																			var20 = link;
																			func = f3;
																			Iterator it1 = ((AbstractMembrane)var12).atomIteratorOfFunctor(func);
																			while (it1.hasNext()) {
																				atom = (Atom) it1.next();
																				var23 = atom;
																				link = ((Atom)var23).getArg(0);
																				var24 = link;
																				link = ((Atom)var23).getArg(0);
																				var29 = link;
																				((Set)var31).add(((Atom)var23));
																				((Set)var31).add(((Atom)var19));
																				func = f8;
																				Iterator it2 = ((AbstractMembrane)var12).atomIteratorOfFunctor(func);
																				while (it2.hasNext()) {
																					atom = (Atom) it2.next();
																					var25 = atom;
																					link = ((Atom)var25).getArg(0);
																					var26 = link;
																					((Set)var31).add(((Atom)var25));
																					((Set)var31).add(((Atom)var11));
																					isground_ret = ((Link)var29).isGround(((Set)var31));
																					if (!(isground_ret == -1)) {
																						var32 = new IntegerFunctor(isground_ret);
																						func = f3;
																						Iterator it3 = ((AbstractMembrane)var16).atomIteratorOfFunctor(func);
																						while (it3.hasNext()) {
																							atom = (Atom) it3.next();
																							var27 = atom;
																							link = ((Atom)var27).getArg(0);
																							var28 = link;
																							link = ((Atom)var27).getArg(0);
																							var30 = link;
																							((Set)var33).add(((Atom)var27));
																							((Set)var33).add(((Atom)var21));
																							((Set)var33).add(((Atom)var15));
																							isground_ret = ((Link)var30).isGround(((Set)var33));
																							if (!(isground_ret == -1)) {
																								var34 = new IntegerFunctor(isground_ret);
																								boolean goAhead = uniq2.check(new Link[] {(Link)var29,(Link)var30																								});
																								if(goAhead) {
																									if (execL1555(var0,var12,var16,var1,var5,var8,var23,var19,var25,var11,var27,var21,var15,nondeterministic)) {
																										ret = true;
																										break L1558;
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																	((AbstractMembrane)var12).unlock();
																}
															}
														}
													}
												}
											}
											((AbstractMembrane)var16).unlock();
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
	public boolean execL1550(Object var0, boolean nondeterministic) {
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
L1550:
		{
			var5 = new HashSet();
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				Iterator it2 = ((AbstractMembrane)var0).memIterator();
				while (it2.hasNext()) {
					mem = (AbstractMembrane) it2.next();
					if ((mem.getKind() != 0))
						continue;
					if (mem.lock()) {
						var2 = mem;
						func = f3;
						Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
						while (it3.hasNext()) {
							atom = (Atom) it3.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							((Set)var5).add(((Atom)var3));
							isground_ret = ((Link)var4).isGround(((Set)var5));
							if (!(isground_ret == -1)) {
								var6 = new IntegerFunctor(isground_ret);
								boolean goAhead = uniq3.check(new Link[] {(Link)var4								});
								if(goAhead) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "gen_from_list", "L1544",var0,var2,var1,var3});
									} else if (execL1544(var0,var2,var1,var3,nondeterministic)) {
										ret = true;
										break L1550;
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1544(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1544:
		{
			link = ((Atom)var3).getArg(0);
			var4 = link;
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			((AbstractMembrane)var1).removeGround(((Link)var4));
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			var6 = ((AbstractMembrane)var1).copyGroundFrom(((Link)var4));
			func = f7;
			var8 = ((AbstractMembrane)var1).newAtom(func);
			func = f8;
			var9 = ((AbstractMembrane)var1).newAtom(func);
			link = new Link(((Atom)var3), 0);
			var11 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var11),
				((Link)var6));
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
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
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L1544;
		}
		return ret;
	}
	public boolean execL1545(Object var0, Object var1, boolean nondeterministic) {
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
L1545:
		{
			if (execL1547(var0, var1, nondeterministic)) {
				ret = true;
				break L1545;
			}
			if (execL1549(var0, var1, nondeterministic)) {
				ret = true;
				break L1545;
			}
		}
		return ret;
	}
	public boolean execL1549(Object var0, Object var1, boolean nondeterministic) {
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
L1549:
		{
			var7 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link;
					((Set)var7).add(((Atom)var1));
					isground_ret = ((Link)var6).isGround(((Set)var7));
					if (!(isground_ret == -1)) {
						var8 = new IntegerFunctor(isground_ret);
						mem = ((AbstractMembrane)var2);
						if (mem.lock()) {
							mem = ((AbstractMembrane)var2).getParent();
							if (!(mem == null)) {
								var3 = mem;
								if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
									func = f9;
									Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var5 = atom;
										boolean goAhead = uniq3.check(new Link[] {(Link)var6										});
										if(goAhead) {
											if (nondeterministic) {
												Task.states.add(new Object[] {theInstance, "gen_from_list", "L1544",var0,var2,var5,var1});
											} else if (execL1544(var0,var2,var5,var1,nondeterministic)) {
												ret = true;
												break L1549;
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
		return ret;
	}
	public boolean execL1547(Object var0, Object var1, boolean nondeterministic) {
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
L1547:
		{
			var6 = new HashSet();
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					Iterator it1 = ((AbstractMembrane)var0).memIterator();
					while (it1.hasNext()) {
						mem = (AbstractMembrane) it1.next();
						if ((mem.getKind() != 0))
							continue;
						if (mem.lock()) {
							var2 = mem;
							func = f3;
							Iterator it2 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
							while (it2.hasNext()) {
								atom = (Atom) it2.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(0);
								var5 = link;
								((Set)var6).add(((Atom)var3));
								isground_ret = ((Link)var5).isGround(((Set)var6));
								if (!(isground_ret == -1)) {
									var7 = new IntegerFunctor(isground_ret);
									boolean goAhead = uniq3.check(new Link[] {(Link)var5									});
									if(goAhead) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "gen_from_list", "L1544",var0,var2,var1,var3});
										} else if (execL1544(var0,var2,var1,var3,nondeterministic)) {
											ret = true;
											break L1547;
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
		return ret;
	}
	public boolean execL1536(Object var0, boolean nondeterministic) {
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
L1536:
		{
			var3 = new Atom(null, f10);
			func = f11;
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
L1539:
							{
								func = f3;
								Iterator it3 = ((AbstractMembrane)var2).atomIteratorOfFunctor(func);
								while (it3.hasNext()) {
									atom = (Atom) it3.next();
									var8 = atom;
									link = ((Atom)var8).getArg(0);
									var9 = link;
									ret = true;
									break L1539;
								}
							}
							if (ret) {
								ret = false;
							} else {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "nextid", "L1532",var0,var2,var1,var3,var4,var5,var7});
								} else if (execL1532(var0,var2,var1,var3,var4,var5,var7,nondeterministic)) {
									ret = true;
									break L1536;
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
	public boolean execL1532(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1532:
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
			func = f3;
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
			break L1532;
		}
		return ret;
	}
	public boolean execL1533(Object var0, Object var1, boolean nondeterministic) {
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
L1533:
		{
			if (execL1535(var0, var1, nondeterministic)) {
				ret = true;
				break L1533;
			}
		}
		return ret;
	}
	public boolean execL1535(Object var0, Object var1, boolean nondeterministic) {
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
L1535:
		{
			var4 = new Atom(null, f10);
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
L1540:
								{
									func = f3;
									Iterator it2 = ((AbstractMembrane)var3).atomIteratorOfFunctor(func);
									while (it2.hasNext()) {
										atom = (Atom) it2.next();
										var9 = atom;
										link = ((Atom)var9).getArg(0);
										var10 = link;
										ret = true;
										break L1540;
									}
								}
								if (ret) {
									ret = false;
								} else {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "nextid", "L1532",var0,var3,var1,var4,var5,var6,var8});
									} else if (execL1532(var0,var3,var1,var4,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L1535;
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
	public boolean execL1527(Object var0, boolean nondeterministic) {
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
L1527:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "genid", "L1523",var0,var1});
				} else if (execL1523(var0,var1,nondeterministic)) {
					ret = true;
					break L1527;
				}
			}
		}
		return ret;
	}
	public boolean execL1523(Object var0, Object var1, boolean nondeterministic) {
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
L1523:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f13;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
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
			break L1523;
		}
		return ret;
	}
	public boolean execL1524(Object var0, Object var1, boolean nondeterministic) {
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
L1524:
		{
			if (execL1526(var0, var1, nondeterministic)) {
				ret = true;
				break L1524;
			}
		}
		return ret;
	}
	public boolean execL1526(Object var0, Object var1, boolean nondeterministic) {
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
L1526:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "genid", "L1523",var0,var1});
					} else if (execL1523(var0,var1,nondeterministic)) {
						ret = true;
						break L1526;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1518(Object var0, boolean nondeterministic) {
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
L1518:
		{
			func = f14;
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
										if (!(!(f15).equals(((Atom)var5).getFunctor()))) {
											Iterator it2 = ((AbstractMembrane)var4).memIterator();
											while (it2.hasNext()) {
												mem = (AbstractMembrane) it2.next();
												if ((mem.getKind() != 2))
													continue;
												if (mem.lock()) {
													var6 = mem;
													if (nondeterministic) {
														Task.states.add(new Object[] {theInstance, "exec", "L1510",var0,var4,var6,var1,var2,var5,var3});
													} else if (execL1510(var0,var4,var6,var1,var2,var5,var3,nondeterministic)) {
														ret = true;
														break L1518;
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
	public boolean execL1510(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1510:
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
			func = f16;
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
			break L1510;
		}
		return ret;
	}
	public boolean execL1511(Object var0, Object var1, boolean nondeterministic) {
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
L1511:
		{
			if (execL1513(var0, var1, nondeterministic)) {
				ret = true;
				break L1511;
			}
			if (execL1516(var0, var1, nondeterministic)) {
				ret = true;
				break L1511;
			}
		}
		return ret;
	}
	public boolean execL1516(Object var0, Object var1, boolean nondeterministic) {
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
L1516:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
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
										if (!(!(f14).equals(((Atom)var11).getFunctor()))) {
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
																	Task.states.add(new Object[] {theInstance, "exec", "L1510",var0,var2,var13,var11,var8,var1,var5});
																} else if (execL1510(var0,var2,var13,var11,var8,var1,var5,nondeterministic)) {
																	ret = true;
																	break L1516;
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
	public boolean execL1513(Object var0, Object var1, boolean nondeterministic) {
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
L1513:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
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
											if (!(!(f15).equals(((Atom)var10).getFunctor()))) {
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
															Task.states.add(new Object[] {theInstance, "exec", "L1510",var0,var7,var12,var1,var3,var10,var6});
														} else if (execL1510(var0,var7,var12,var1,var3,var10,var6,nondeterministic)) {
															ret = true;
															break L1513;
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
	private static final Functor f4 = new Functor("tl", 1, null);
	private static final Functor f3 = new Functor("id", 1, null);
	private static final Functor f9 = new Functor("gen_from_list", 0, "nd");
	private static final Functor f11 = new Functor("nextid", 1, "nd");
	private static final Functor f5 = new Functor(".", 3, null);
	private static final Functor f15 = new Functor("+", 1, null);
	private static final Functor f14 = new Functor("exec", 1, "nd");
	private static final Functor f2 = new Functor("from", 1, null);
	private static final Functor f7 = new Functor("[]", 1, null);
	private static final Functor f16 = new StringFunctor("/*inline*/\r\n\t\t\tAtom out = me.nthAtom(0);\r\n\t\t\tAtom in = out.nthAtom(0);\r\n\t\t\tAtom plus = in.nthAtom(1);\r\n\t\t\tMembrane mem1 = (Membrane)in.getMem();\r\n\t\t\tIterator it = mem1.memIterator();\r\n\t\t\tMembrane mem2 = (Membrane)it.next();\r\n\t\t\t((Task)mem.getTask()).nondeterministicExec(mem2);\r\n\t\t\tmem.removeAtom(me);\r\n\t\t\tmem.removeAtom(out);\r\n\t\t\tmem1.removeAtom(in);\r\n\t\t\tplus.dequeue();\r\n\t\t\tmem1.removeAtom(plus);\r\n\t\t");
	private static final Functor f6 = new Functor("gen_to_list", 0, "nd");
	private static final Functor f8 = new Functor("fl", 1, null);
	private static final Functor f12 = new Functor("genid", 0, "nd");
	private static final Functor f10 = new IntegerFunctor(1);
	private static final Functor f1 = new Functor("to", 1, null);
	private static final Functor f0 = new Functor("reduce", 3, null);
	private static final Functor f13 = new IntegerFunctor(0);
	private Uniq uniq0 = new Uniq();
	private Uniq uniq1 = new Uniq();
	private Uniq uniq2 = new Uniq();
	private Uniq uniq3 = new Uniq();
}

package translated.module_thread;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset609 extends Ruleset {
	private static final Ruleset609 theInstance = new Ruleset609();
	private Ruleset609() {}
	public static Ruleset609 getInstance() {
		return theInstance;
	}
	private int id = 609;
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
"({thread(N, {$p, @p}), $q, @q}/ :- int(N) | {$p, $q, @p, @q})";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL720(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "thread");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL726(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "thread");
			return true;
		}
		return result;
	}
	public boolean execL726(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L726:
		{
			Iterator it1 = ((AbstractMembrane)var0).memIterator();
			while (it1.hasNext()) {
				mem = (AbstractMembrane) it1.next();
				if ((mem.getKind() != 0))
					continue;
				if (mem.lock()) {
					var1 = mem;
					func = f0;
					Iterator it2 = ((AbstractMembrane)var1).atomIteratorOfFunctor(func);
					while (it2.hasNext()) {
						atom = (Atom) it2.next();
						var2 = atom;
						if (!(!((AbstractMembrane)var1).isStable())) {
							link = ((Atom)var2).getArg(1);
							if (!(link.getPos() != 1)) {
								var3 = link.getAtom();
								link = ((Atom)var2).getArg(0);
								var7 = link.getAtom();
								if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
									if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
										link = ((Atom)var3).getArg(0);
										if (!(link.getPos() != 0)) {
											var4 = link.getAtom();
											if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var4).getFunctor()))) {
												mem = ((Atom)var4).getMem();
												if (mem.lock()) {
													var5 = mem;
													link = ((Atom)var4).getArg(1);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
															if (nondeterministic) {
																Task.states.add(new Object[] {theInstance, "thread", "L719",var0,var1,var5,var2,var3,var6,var4,var7});
															} else if (execL719(var0,var1,var5,var2,var3,var6,var4,var7,nondeterministic)) {
																ret = true;
																break L726;
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
						}
					}
					((AbstractMembrane)var1).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL719(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L719:
		{
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var5);
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
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var2);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var2).removeProxies();
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var0).removeToplevelProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var1).moveCellsFrom(((AbstractMembrane)var2));
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			((AbstractMembrane)var1).copyRulesFrom(((AbstractMembrane)var2));
			((AbstractMembrane)var2).free();
			((AbstractMembrane)var1).forceUnlock();
			ret = true;
			break L719;
		}
		return ret;
	}
	public boolean execL720(Object var0, Object var1, boolean nondeterministic) {
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
L720:
		{
			if (execL724(var0, var1, nondeterministic)) {
				ret = true;
				break L720;
			}
		}
		return ret;
	}
	public boolean execL724(Object var0, Object var1, boolean nondeterministic) {
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
L724:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
					var2 = ((Atom)var1).getMem();
					link = ((Atom)var1).getArg(0);
					var5 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var6 = link.getAtom();
						if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							link = ((Atom)var6).getArg(0);
							if (!(link.getPos() != 0)) {
								var9 = link.getAtom();
								if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var9).getFunctor()))) {
									link = ((Atom)var9).getArg(0);
									var10 = link;
									link = ((Atom)var9).getArg(1);
									var11 = link;
									link = ((Atom)var9).getArg(1);
									if (!(link.getPos() != 1)) {
										var12 = link.getAtom();
										if (!(!(f0).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var12).getArg(1);
											var14 = link;
											link = ((Atom)var12).getArg(0);
											var15 = link.getAtom();
											if (!(!(((Atom)var15).getFunctor() instanceof IntegerFunctor))) {
												mem = ((AbstractMembrane)var2);
												if (mem.lock()) {
													mem = ((AbstractMembrane)var2).getParent();
													if (!(mem == null)) {
														var3 = mem;
														mem = ((AbstractMembrane)var3);
														if (mem.lock()) {
															mem = ((AbstractMembrane)var3).getParent();
															if (!(mem == null)) {
																var4 = mem;
																if (!(!((AbstractMembrane)var3).isStable())) {
																	if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var4))) {
																		if (nondeterministic) {
																			Task.states.add(new Object[] {theInstance, "thread", "L719",var0,var3,var2,var12,var9,var1,var6,var15});
																		} else if (execL719(var0,var3,var2,var12,var9,var1,var6,var15,nondeterministic)) {
																			ret = true;
																			break L724;
																		}
																	}
																}
															}
															((AbstractMembrane)var3).unlock();
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
	private static final Functor f1 = new Functor("+", 1, null);
	private static final Functor f0 = new Functor("thread", 2, null);
}

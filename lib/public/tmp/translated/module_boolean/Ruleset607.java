package translated.module_boolean;
import runtime.*;
import java.util.*;
import java.io.*;
import java.math.*;
import daemon.IDConverter;
import module.*;

public class Ruleset607 extends Ruleset {
	private static final Ruleset607 theInstance = new Ruleset607();
	private Ruleset607() {}
	public static Ruleset607 getInstance() {
		return theInstance;
	}
	private int id = 607;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":boolean" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@boolean" + id;
	}
	private String encodedRuleset = 
"('='(H, boolean.not(true)) :- '='(H, false)), ('='(H, boolean.not(false)) :- '='(H, true)), ('='(H, '&&'(true, true)) :- '='(H, true)), ('='(H, '&&'(true, false)) :- '='(H, false)), ('='(H, '&&'(false, true)) :- '='(H, false)), ('='(H, '&&'(false, false)) :- '='(H, false)), ('='(H, '||'(true, true)) :- '='(H, true)), ('='(H, '||'(true, false)) :- '='(H, true)), ('='(H, '||'(false, true)) :- '='(H, true)), ('='(H, '||'(false, false)) :- '='(H, false))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL372(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL383(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL394(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL407(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL420(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL433(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL446(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL459(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL472(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL485(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL377(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL388(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL401(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL414(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL427(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL440(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL453(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL466(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL479(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL492(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		return result;
	}
	public boolean execL492(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L492:
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
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f0).equals(((Atom)var3).getFunctor()))) {
								if (execL483(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L492;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL483(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L483:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "false", "L484",var0,var1,var2,var3});
			} else if (execL484(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L483;
			}
		}
		return ret;
	}
	public boolean execL484(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L484:
		{
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
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L484;
		}
		return ret;
	}
	public boolean execL485(Object var0, Object var1, boolean nondeterministic) {
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
L485:
		{
			if (execL487(var0, var1, nondeterministic)) {
				ret = true;
				break L485;
			}
			if (execL489(var0, var1, nondeterministic)) {
				ret = true;
				break L485;
			}
			if (execL491(var0, var1, nondeterministic)) {
				ret = true;
				break L485;
			}
		}
		return ret;
	}
	public boolean execL491(Object var0, Object var1, boolean nondeterministic) {
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
L491:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL483(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L491;
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
	public boolean execL489(Object var0, Object var1, boolean nondeterministic) {
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
L489:
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
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL483(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L489;
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
	public boolean execL487(Object var0, Object var1, boolean nondeterministic) {
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
L487:
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL483(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L487;
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
	public boolean execL479(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L479:
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
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								if (execL470(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L479;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL470(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L470:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "false", "L471",var0,var1,var2,var3});
			} else if (execL471(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L470;
			}
		}
		return ret;
	}
	public boolean execL471(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L471:
		{
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
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L471;
		}
		return ret;
	}
	public boolean execL472(Object var0, Object var1, boolean nondeterministic) {
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
L472:
		{
			if (execL474(var0, var1, nondeterministic)) {
				ret = true;
				break L472;
			}
			if (execL476(var0, var1, nondeterministic)) {
				ret = true;
				break L472;
			}
			if (execL478(var0, var1, nondeterministic)) {
				ret = true;
				break L472;
			}
		}
		return ret;
	}
	public boolean execL478(Object var0, Object var1, boolean nondeterministic) {
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
L478:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL470(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L478;
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
	public boolean execL476(Object var0, Object var1, boolean nondeterministic) {
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
L476:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
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
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL470(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L476;
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
	public boolean execL474(Object var0, Object var1, boolean nondeterministic) {
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
L474:
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL470(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L474;
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
	public boolean execL466(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L466:
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
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f0).equals(((Atom)var3).getFunctor()))) {
								if (execL457(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L466;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL457(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L457:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "true", "L458",var0,var1,var2,var3});
			} else if (execL458(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L457;
			}
		}
		return ret;
	}
	public boolean execL458(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L458:
		{
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
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L458;
		}
		return ret;
	}
	public boolean execL459(Object var0, Object var1, boolean nondeterministic) {
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
L459:
		{
			if (execL461(var0, var1, nondeterministic)) {
				ret = true;
				break L459;
			}
			if (execL463(var0, var1, nondeterministic)) {
				ret = true;
				break L459;
			}
			if (execL465(var0, var1, nondeterministic)) {
				ret = true;
				break L459;
			}
		}
		return ret;
	}
	public boolean execL465(Object var0, Object var1, boolean nondeterministic) {
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
L465:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL457(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L465;
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
	public boolean execL463(Object var0, Object var1, boolean nondeterministic) {
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
L463:
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
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL457(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L463;
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
	public boolean execL461(Object var0, Object var1, boolean nondeterministic) {
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
L461:
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL457(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L461;
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
	public boolean execL453(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L453:
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
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								if (execL444(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L453;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL444(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L444:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "true", "L445",var0,var1,var2,var3});
			} else if (execL445(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L444;
			}
		}
		return ret;
	}
	public boolean execL445(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L445:
		{
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
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L445;
		}
		return ret;
	}
	public boolean execL446(Object var0, Object var1, boolean nondeterministic) {
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
L446:
		{
			if (execL448(var0, var1, nondeterministic)) {
				ret = true;
				break L446;
			}
			if (execL450(var0, var1, nondeterministic)) {
				ret = true;
				break L446;
			}
			if (execL452(var0, var1, nondeterministic)) {
				ret = true;
				break L446;
			}
		}
		return ret;
	}
	public boolean execL452(Object var0, Object var1, boolean nondeterministic) {
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
L452:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL444(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L452;
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
	public boolean execL450(Object var0, Object var1, boolean nondeterministic) {
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
L450:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
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
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL444(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L450;
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
	public boolean execL448(Object var0, Object var1, boolean nondeterministic) {
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
L448:
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
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL444(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L448;
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
	public boolean execL440(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L440:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f0).equals(((Atom)var3).getFunctor()))) {
								if (execL431(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L440;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL431(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L431:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "false", "L432",var0,var1,var2,var3});
			} else if (execL432(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L431;
			}
		}
		return ret;
	}
	public boolean execL432(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L432:
		{
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
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L432;
		}
		return ret;
	}
	public boolean execL433(Object var0, Object var1, boolean nondeterministic) {
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
L433:
		{
			if (execL435(var0, var1, nondeterministic)) {
				ret = true;
				break L433;
			}
			if (execL437(var0, var1, nondeterministic)) {
				ret = true;
				break L433;
			}
			if (execL439(var0, var1, nondeterministic)) {
				ret = true;
				break L433;
			}
		}
		return ret;
	}
	public boolean execL439(Object var0, Object var1, boolean nondeterministic) {
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
L439:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL431(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L439;
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
	public boolean execL437(Object var0, Object var1, boolean nondeterministic) {
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
L437:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL431(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L437;
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
	public boolean execL435(Object var0, Object var1, boolean nondeterministic) {
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
L435:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL431(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L435;
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
	public boolean execL427(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L427:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								if (execL418(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L427;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL418(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L418:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "false", "L419",var0,var1,var2,var3});
			} else if (execL419(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L418;
			}
		}
		return ret;
	}
	public boolean execL419(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L419:
		{
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
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L419;
		}
		return ret;
	}
	public boolean execL420(Object var0, Object var1, boolean nondeterministic) {
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
L420:
		{
			if (execL422(var0, var1, nondeterministic)) {
				ret = true;
				break L420;
			}
			if (execL424(var0, var1, nondeterministic)) {
				ret = true;
				break L420;
			}
			if (execL426(var0, var1, nondeterministic)) {
				ret = true;
				break L420;
			}
		}
		return ret;
	}
	public boolean execL426(Object var0, Object var1, boolean nondeterministic) {
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
L426:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f0).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL418(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L426;
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
	public boolean execL424(Object var0, Object var1, boolean nondeterministic) {
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
L424:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL418(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L424;
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
	public boolean execL422(Object var0, Object var1, boolean nondeterministic) {
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
L422:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL418(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L422;
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
	public boolean execL414(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L414:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f0).equals(((Atom)var3).getFunctor()))) {
								if (execL405(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L414;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL405(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L405:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "true", "L406",var0,var1,var2,var3});
			} else if (execL406(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L405;
			}
		}
		return ret;
	}
	public boolean execL406(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L406:
		{
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
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L406;
		}
		return ret;
	}
	public boolean execL407(Object var0, Object var1, boolean nondeterministic) {
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
L407:
		{
			if (execL409(var0, var1, nondeterministic)) {
				ret = true;
				break L407;
			}
			if (execL411(var0, var1, nondeterministic)) {
				ret = true;
				break L407;
			}
			if (execL413(var0, var1, nondeterministic)) {
				ret = true;
				break L407;
			}
		}
		return ret;
	}
	public boolean execL413(Object var0, Object var1, boolean nondeterministic) {
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
L413:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL405(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L413;
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
	public boolean execL411(Object var0, Object var1, boolean nondeterministic) {
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
L411:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL405(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L411;
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
	public boolean execL409(Object var0, Object var1, boolean nondeterministic) {
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
L409:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL405(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L409;
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
	public boolean execL401(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
L401:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								if (execL392(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L401;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL392(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L392:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "true", "L393",var0,var1,var2,var3});
			} else if (execL393(var0,var1,var2,var3,nondeterministic)) {
				ret = true;
				break L392;
			}
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
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
L393:
		{
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
			func = f2;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 2 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L393;
		}
		return ret;
	}
	public boolean execL394(Object var0, Object var1, boolean nondeterministic) {
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
L394:
		{
			if (execL396(var0, var1, nondeterministic)) {
				ret = true;
				break L394;
			}
			if (execL398(var0, var1, nondeterministic)) {
				ret = true;
				break L394;
			}
			if (execL400(var0, var1, nondeterministic)) {
				ret = true;
				break L394;
			}
		}
		return ret;
	}
	public boolean execL400(Object var0, Object var1, boolean nondeterministic) {
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
L400:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var1).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL392(var0,var5,var7,var1,nondeterministic)) {
										ret = true;
										break L400;
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
	public boolean execL398(Object var0, Object var1, boolean nondeterministic) {
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
L398:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL392(var0,var7,var1,var3,nondeterministic)) {
										ret = true;
										break L398;
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
	public boolean execL396(Object var0, Object var1, boolean nondeterministic) {
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
L396:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(1);
							if (!(link.getPos() != 0)) {
								var7 = link.getAtom();
								if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									if (execL392(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L396;
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
	public boolean execL388(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L388:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL381(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L388;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL381(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L381:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "false", "L382",var0,var1,var2});
			} else if (execL382(var0,var1,var2,nondeterministic)) {
				ret = true;
				break L381;
			}
		}
		return ret;
	}
	public boolean execL382(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
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
L382:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 1 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L382;
		}
		return ret;
	}
	public boolean execL383(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L383:
		{
			if (execL385(var0, var1, nondeterministic)) {
				ret = true;
				break L383;
			}
			if (execL387(var0, var1, nondeterministic)) {
				ret = true;
				break L383;
			}
		}
		return ret;
	}
	public boolean execL387(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L387:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f0).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL381(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L387;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL385(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L385:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
							if (execL381(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L385;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL377(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
L377:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL370(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L377;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL370(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L370:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "true", "L371",var0,var1,var2});
			} else if (execL371(var0,var1,var2,nondeterministic)) {
				ret = true;
				break L370;
			}
		}
		return ret;
	}
	public boolean execL371(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
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
L371:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f0;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().relinkAtomArgs(
				((Atom)var3), 0,
				((Atom)var2), 1 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L371;
		}
		return ret;
	}
	public boolean execL372(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L372:
		{
			if (execL374(var0, var1, nondeterministic)) {
				ret = true;
				break L372;
			}
			if (execL376(var0, var1, nondeterministic)) {
				ret = true;
				break L372;
			}
		}
		return ret;
	}
	public boolean execL376(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L376:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL370(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L376;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL374(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
L374:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
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
							if (execL370(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L374;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f3 = new Functor("&&", 3, null);
	private static final Functor f0 = new Functor("false", 1, null);
	private static final Functor f4 = new Functor("not", 2, "boolean");
	private static final Functor f1 = new Functor("||", 3, null);
	private static final Functor f2 = new Functor("true", 1, null);
}

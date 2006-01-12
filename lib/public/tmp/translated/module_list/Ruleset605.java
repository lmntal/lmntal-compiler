package translated.module_list;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":list" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@list" + id;
	}
	private String encodedRuleset = 
"('='(H, list.new) :- '='(H, '[]')), ('='(H, is_empty('[]', Return)) :- '='(H, '[]'), '='(Return, true)), ('='(H, is_empty('.'(Value, Next), Return)) :- '='(H, '.'(Value, Next)), '='(Return, false)), ('='(H, unshift('[]', Obj)) :- '='(H, '.'(Obj, '[]'))), ('='(H, unshift('.'(Value, Next), Obj)) :- '='(H, '.'(Obj, '.'(Value, Next)))), ('='(H, shift('[]', Return)) :- '='(H, '[]'), '='(Return, nil)), ('='(H, shift('.'(Value, Next), Return)) :- '='(H, Next), '='(Return, Value)), ('='(H, list.append('[]', B)) :- '='(H, B)), ('='(H, list.append('.'(Value, Next), B)) :- '='(H, '.'(Value, list.append(Next, B)))), ('='(H, list.of_queue(queue.new(Head, Head))) :- '='(H, '[]')), ('='(H, list.of_queue(queue.new(Head, Tail))) :- '='(H, '.'(El, list.of_queue(shift(queue.new(Head, Tail), El))))), ('='(H, list.grep(F, List)) :- unary(F) | '='(H, grep_s0(list.map(F, List)))), ('='(H, grep_s0('.'('.'(true, '.'(El, '[]')), CDR))) :- '='(H, '.'(El, grep_s0(CDR)))), ('='(H, grep_s0('.'('.'(false, '.'(El, '[]')), CDR))) :- '='(H, grep_s0(CDR)), nil(El)), ('='(H, grep_s0('[]')) :- '='(H, '[]')), ('='(H, list.map(F, List)) :- '='(H, list.fold(F, '[]', List))), ('='(H, list.fold(F, I, '[]')) :- unary(F), unary(I) | '='(H, I)), ('='(H, list.fold(F, I, '.'(CAR, CDR))) :- unary(F), unary(I) | '='(H, '.'('.'(F, '.'(CAR, '[]')), list.fold(F, I, CDR)))), ('='(H, list.unfold(P, F, G, Seed, Tailgen)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, list.unfold_s0(P, F, G, Seed, Tailgen, '.'(P, '.'(Seed, '[]'))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, true)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), list.unfold_s0(P, F, G, '.'(G, '.'(Seed, '[]')), Tailgen, '.'(P, '.'(Seed, '[]')))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, false)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), '[]'))), ('='(Res, list.flatten(nil, R)) :- '='(Res, R)), ('='(Res, list.flatten(int(N), R)) :- '='(Res, cons(int(N), R))), ('='(Res, list.flatten(cons(A, B), R)) :- '='(Res, list.flatten(A, list.flatten(B, R)))), ('='(H, list.choose_k(L, K)) :- '='(H, choose_k(L, K))), ('='(H, choose_k(L, K)) :- ground(L), '='(K, 0) | '='(H, '[]')), ('='(H, choose_k('[]', K)) :- int(K) | '='(H, '[]')), ('='(H, choose_k('.'(Hd, R), K)) :- '='(K, 1) | '='(H, '.'('.'(Hd, '[]'), choose_k(R, K)))), ('='(H, choose_k('.'(Hd, Tl), K)) :- '>'(K, 1), ground(Tl) | '='(H, list.append(dist(Hd, choose_k(Tl, '-'(K, 1))), choose_k(Tl, K)))), ('='(H, dist(A, '[]')) :- ground(A) | '='(H, '[]')), ('='(H, dist(A, '.'(Hd, Tl))) :- ground(A) | '='(H, '.'('.'(A, Hd), dist(A, Tl)))), ('='(H, list.uniq('[]')) :- '='(H, '[]')), ('='(H, list.uniq('.'(Hd, Tl))) :- uniq(Hd) | '='(H, '.'(Hd, list.uniq(Tl)))), ('='(H, list.uniq('.'(Hd, Tl))) :- not_uniq(Hd) | '='(H, list.uniq(Tl)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL411(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "new");
			return true;
		}
		if (execL420(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "is_empty");
			return true;
		}
		if (execL430(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "is_empty");
			return true;
		}
		if (execL440(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "unshift");
			return true;
		}
		if (execL450(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "unshift");
			return true;
		}
		if (execL460(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "shift");
			return true;
		}
		if (execL470(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "shift");
			return true;
		}
		if (execL480(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "append");
			return true;
		}
		if (execL490(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "append");
			return true;
		}
		if (execL500(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "new");
			return true;
		}
		if (execL511(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "new");
			return true;
		}
		if (execL522(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "grep");
			return true;
		}
		if (execL531(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "true");
			return true;
		}
		if (execL546(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "false");
			return true;
		}
		if (execL561(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "grep_s0");
			return true;
		}
		if (execL571(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "map");
			return true;
		}
		if (execL580(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "fold");
			return true;
		}
		if (execL590(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "fold");
			return true;
		}
		if (execL600(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "unfold");
			return true;
		}
		if (execL609(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "true");
			return true;
		}
		if (execL620(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "false");
			return true;
		}
		if (execL631(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "nil");
			return true;
		}
		if (execL642(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "int");
			return true;
		}
		if (execL653(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "cons");
			return true;
		}
		if (execL664(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "choose_k");
			return true;
		}
		if (execL673(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "choose_k");
			return true;
		}
		if (execL682(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "choose_k");
			return true;
		}
		if (execL692(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "choose_k");
			return true;
		}
		if (execL702(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "choose_k");
			return true;
		}
		if (execL712(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "dist");
			return true;
		}
		if (execL722(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "dist");
			return true;
		}
		if (execL732(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "uniq");
			return true;
		}
		if (execL742(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "uniq");
			return true;
		}
		if (execL752(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "uniq");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL414(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "new");
			return true;
		}
		if (execL424(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "is_empty");
			return true;
		}
		if (execL434(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "is_empty");
			return true;
		}
		if (execL444(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "unshift");
			return true;
		}
		if (execL454(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "unshift");
			return true;
		}
		if (execL464(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "shift");
			return true;
		}
		if (execL474(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "shift");
			return true;
		}
		if (execL484(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "append");
			return true;
		}
		if (execL494(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "append");
			return true;
		}
		if (execL505(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "new");
			return true;
		}
		if (execL516(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "new");
			return true;
		}
		if (execL525(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "grep");
			return true;
		}
		if (execL540(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "true");
			return true;
		}
		if (execL555(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "false");
			return true;
		}
		if (execL565(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "grep_s0");
			return true;
		}
		if (execL574(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "map");
			return true;
		}
		if (execL584(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "fold");
			return true;
		}
		if (execL594(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "fold");
			return true;
		}
		if (execL603(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "unfold");
			return true;
		}
		if (execL614(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "true");
			return true;
		}
		if (execL625(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "false");
			return true;
		}
		if (execL636(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "nil");
			return true;
		}
		if (execL647(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "int");
			return true;
		}
		if (execL658(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "cons");
			return true;
		}
		if (execL667(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "choose_k");
			return true;
		}
		if (execL676(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "choose_k");
			return true;
		}
		if (execL686(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "choose_k");
			return true;
		}
		if (execL696(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "choose_k");
			return true;
		}
		if (execL706(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "choose_k");
			return true;
		}
		if (execL716(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "dist");
			return true;
		}
		if (execL726(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "dist");
			return true;
		}
		if (execL736(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "uniq");
			return true;
		}
		if (execL746(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "uniq");
			return true;
		}
		if (execL756(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "uniq");
			return true;
		}
		return result;
	}
	public boolean execL756(Object var0, boolean nondeterministic) {
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
L756:
		{
			boolean goAhead = uniq0.check(new Link[] {(Link)var3			});
			goAhead = !goAhead;
			if(!goAhead) {
				var4 = new HashSet();
				func = f0;
				Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it1.hasNext()) {
					atom = (Atom) it1.next();
					var1 = atom;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var2 = link.getAtom();
						((Set)var4).add(((Atom)var1));
						if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
							link = ((Atom)var2).getArg(0);
							var3 = link;
							((Set)var4).add(((Atom)var2));
							isground_ret = ((Link)var3).isGround(((Set)var4));
							if (!(isground_ret == -1)) {
								var5 = new IntegerFunctor(isground_ret);
								if (execL751(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L756;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL751(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L751:
		{
			link = ((Atom)var2).getArg(1);
			var5 = link;
			link = ((Atom)var2).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var3));
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var5 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L751;
		}
		return ret;
	}
	public boolean execL752(Object var0, Object var1, boolean nondeterministic) {
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
L752:
		{
			if (execL754(var0, var1, nondeterministic)) {
				ret = true;
				break L752;
			}
		}
		return ret;
	}
	public boolean execL754(Object var0, Object var1, boolean nondeterministic) {
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
L754:
		{
			boolean goAhead = uniq0.check(new Link[] {(Link)var8			});
			goAhead = !goAhead;
			if(!goAhead) {
				var9 = new HashSet();
				if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
					if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
						link = ((Atom)var1).getArg(0);
						var2 = link;
						link = ((Atom)var1).getArg(1);
						var3 = link;
						link = ((Atom)var1).getArg(0);
						if (!(link.getPos() != 2)) {
							var4 = link.getAtom();
							((Set)var9).add(((Atom)var1));
							if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
								link = ((Atom)var4).getArg(0);
								var5 = link;
								link = ((Atom)var4).getArg(1);
								var6 = link;
								link = ((Atom)var4).getArg(2);
								var7 = link;
								link = ((Atom)var4).getArg(0);
								var8 = link;
								((Set)var9).add(((Atom)var4));
								isground_ret = ((Link)var8).isGround(((Set)var9));
								if (!(isground_ret == -1)) {
									var10 = new IntegerFunctor(isground_ret);
									if (execL751(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L754;
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
	public boolean execL746(Object var0, boolean nondeterministic) {
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
L746:
		{
			boolean goAhead = uniq1.check(new Link[] {(Link)var3			});
			if(!goAhead) {
				var4 = new HashSet();
				func = f0;
				Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it1.hasNext()) {
					atom = (Atom) it1.next();
					var1 = atom;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var2 = link.getAtom();
						((Set)var4).add(((Atom)var1));
						if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
							link = ((Atom)var2).getArg(0);
							var3 = link;
							((Set)var4).add(((Atom)var2));
							isground_ret = ((Link)var3).isGround(((Set)var4));
							if (!(isground_ret == -1)) {
								var5 = new IntegerFunctor(isground_ret);
								if (execL741(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L746;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL741(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L741:
		{
			link = ((Atom)var2).getArg(1);
			var8 = link;
			link = ((Atom)var1).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			((AbstractMembrane)var0).removeGround(((Link)var3));
			var4 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var8 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var2), 1 );
			link = new Link(((Atom)var2), 0);
			var7 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var7),
				((Link)var4));
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var9 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L741;
		}
		return ret;
	}
	public boolean execL742(Object var0, Object var1, boolean nondeterministic) {
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
L742:
		{
			if (execL744(var0, var1, nondeterministic)) {
				ret = true;
				break L742;
			}
		}
		return ret;
	}
	public boolean execL744(Object var0, Object var1, boolean nondeterministic) {
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
L744:
		{
			boolean goAhead = uniq1.check(new Link[] {(Link)var8			});
			if(!goAhead) {
				var9 = new HashSet();
				if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
					if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
						link = ((Atom)var1).getArg(0);
						var2 = link;
						link = ((Atom)var1).getArg(1);
						var3 = link;
						link = ((Atom)var1).getArg(0);
						if (!(link.getPos() != 2)) {
							var4 = link.getAtom();
							((Set)var9).add(((Atom)var1));
							if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
								link = ((Atom)var4).getArg(0);
								var5 = link;
								link = ((Atom)var4).getArg(1);
								var6 = link;
								link = ((Atom)var4).getArg(2);
								var7 = link;
								link = ((Atom)var4).getArg(0);
								var8 = link;
								((Set)var9).add(((Atom)var4));
								isground_ret = ((Link)var8).isGround(((Set)var9));
								if (!(isground_ret == -1)) {
									var10 = new IntegerFunctor(isground_ret);
									if (execL741(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L744;
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
	public boolean execL736(Object var0, boolean nondeterministic) {
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
L736:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL731(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L736;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL731(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L731:
		{
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var4 );
			ret = true;
			break L731;
		}
		return ret;
	}
	public boolean execL732(Object var0, Object var1, boolean nondeterministic) {
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
L732:
		{
			if (execL734(var0, var1, nondeterministic)) {
				ret = true;
				break L732;
			}
		}
		return ret;
	}
	public boolean execL734(Object var0, Object var1, boolean nondeterministic) {
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
L734:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
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
							if (execL731(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L734;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL726(Object var0, boolean nondeterministic) {
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
			var4 = new HashSet();
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link;
					((Set)var4).add(((Atom)var1));
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL721(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L726;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL721(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L721:
		{
			link = ((Atom)var2).getArg(0);
			var11 = link;
			link = ((Atom)var2).getArg(1);
			var12 = link;
			link = ((Atom)var1).getArg(2);
			var13 = link;
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			((AbstractMembrane)var0).removeGround(((Link)var3));
			var4 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			var5 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var3));
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			link = new Link(((Atom)var6), 0);
			var9 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var9),
				((Link)var4));
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var11 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var2), 0 );
			link = new Link(((Atom)var1), 0);
			var10 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var10),
				((Link)var5));
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var12 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var13 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L721;
		}
		return ret;
	}
	public boolean execL722(Object var0, Object var1, boolean nondeterministic) {
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
L722:
		{
			if (execL724(var0, var1, nondeterministic)) {
				ret = true;
				break L722;
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			var10 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var9 = link;
						((Set)var10).add(((Atom)var1));
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							((Set)var10).add(((Atom)var5));
							isground_ret = ((Link)var9).isGround(((Set)var10));
							if (!(isground_ret == -1)) {
								var11 = new IntegerFunctor(isground_ret);
								if (execL721(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L724;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL716(Object var0, boolean nondeterministic) {
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
L716:
		{
			var4 = new HashSet();
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link;
					((Set)var4).add(((Atom)var1));
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL711(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L716;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL711(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L711:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var3));
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var5 );
			ret = true;
			break L711;
		}
		return ret;
	}
	public boolean execL712(Object var0, Object var1, boolean nondeterministic) {
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
L712:
		{
			if (execL714(var0, var1, nondeterministic)) {
				ret = true;
				break L712;
			}
		}
		return ret;
	}
	public boolean execL714(Object var0, Object var1, boolean nondeterministic) {
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
L714:
		{
			var8 = new HashSet();
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var7 = link;
						((Set)var8).add(((Atom)var1));
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							((Set)var8).add(((Atom)var5));
							isground_ret = ((Link)var7).isGround(((Set)var8));
							if (!(isground_ret == -1)) {
								var9 = new IntegerFunctor(isground_ret);
								if (execL711(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L714;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL706(Object var0, boolean nondeterministic) {
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
L706:
		{
			var6 = new HashSet();
			var4 = new Atom(null, f4);
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var5 = link.getAtom();
					((Set)var6).add(((Atom)var1));
					if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var5).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
								link = ((Atom)var2).getArg(1);
								var3 = link;
								((Set)var6).add(((Atom)var2));
								isground_ret = ((Link)var3).isGround(((Set)var6));
								if (!(isground_ret == -1)) {
									var7 = new IntegerFunctor(isground_ret);
									if (execL701(var0,var1,var2,var4,var5,nondeterministic)) {
										ret = true;
										break L706;
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
	public boolean execL701(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L701:
		{
			link = ((Atom)var2).getArg(0);
			var18 = link;
			link = ((Atom)var1).getArg(2);
			var19 = link;
			link = ((Atom)var2).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var5));
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var5));
			var7 = ((AbstractMembrane)var0).copyGroundFrom(((Link)var5));
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var11), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var8), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 2,
				((Atom)var12), 1 );
			link = new Link(((Atom)var12), 0);
			var16 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var16),
				((Link)var6));
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var13), 1 );
			((Atom)var13).getMem().inheritLink(
				((Atom)var13), 0,
				(Link)var18 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 2,
				((Atom)var15), 0 );
			link = new Link(((Atom)var1), 0);
			var17 = link;
			mem = ((AbstractMembrane)var0);
			mem.unifyLinkBuddies(
				((Link)var17),
				((Link)var7));
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var9), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var15), 1 );
			((Atom)var15).getMem().inheritLink(
				((Atom)var15), 2,
				(Link)var19 );
			atom = ((Atom)var15);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var13);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L701;
		}
		return ret;
	}
	public boolean execL702(Object var0, Object var1, boolean nondeterministic) {
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
L702:
		{
			if (execL704(var0, var1, nondeterministic)) {
				ret = true;
				break L702;
			}
		}
		return ret;
	}
	public boolean execL704(Object var0, Object var1, boolean nondeterministic) {
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
L704:
		{
			var12 = new HashSet();
			var10 = new Atom(null, f4);
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
						link = ((Atom)var1).getArg(1);
						var11 = link.getAtom();
						((Set)var12).add(((Atom)var1));
						if (!(!(((Atom)var11).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var11).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var10).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(1);
									var7 = link;
									link = ((Atom)var5).getArg(2);
									var8 = link;
									link = ((Atom)var5).getArg(1);
									var9 = link;
									((Set)var12).add(((Atom)var5));
									isground_ret = ((Link)var9).isGround(((Set)var12));
									if (!(isground_ret == -1)) {
										var13 = new IntegerFunctor(isground_ret);
										if (execL701(var0,var1,var5,var10,var11,nondeterministic)) {
											ret = true;
											break L704;
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
	public boolean execL696(Object var0, boolean nondeterministic) {
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
L696:
		{
			var3 = new Atom(null, f4);
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var4 = link.getAtom();
					if (!(!((Atom)var3).getFunctor().equals(((Atom)var4).getFunctor()))) {
						if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
							if (execL691(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L696;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL691(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L691:
		{
			link = ((Atom)var2).getArg(0);
			var10 = link;
			link = ((Atom)var2).getArg(1);
			var11 = link;
			link = ((Atom)var1).getArg(2);
			var12 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var10 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var2), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var11 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var5), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var12 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L691;
		}
		return ret;
	}
	public boolean execL692(Object var0, Object var1, boolean nondeterministic) {
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
L692:
		{
			if (execL694(var0, var1, nondeterministic)) {
				ret = true;
				break L692;
			}
		}
		return ret;
	}
	public boolean execL694(Object var0, Object var1, boolean nondeterministic) {
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
L694:
		{
			var9 = new Atom(null, f4);
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
						link = ((Atom)var1).getArg(1);
						var10 = link.getAtom();
						if (!(!((Atom)var9).getFunctor().equals(((Atom)var10).getFunctor()))) {
							if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								if (execL691(var0,var1,var5,var9,var10,nondeterministic)) {
									ret = true;
									break L694;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL686(Object var0, boolean nondeterministic) {
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
L686:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var3 = link.getAtom();
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
							if (execL681(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L686;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL681(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L681:
		{
			link = ((Atom)var1).getArg(2);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var5 );
			ret = true;
			break L681;
		}
		return ret;
	}
	public boolean execL682(Object var0, Object var1, boolean nondeterministic) {
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
L682:
		{
			if (execL684(var0, var1, nondeterministic)) {
				ret = true;
				break L682;
			}
		}
		return ret;
	}
	public boolean execL684(Object var0, Object var1, boolean nondeterministic) {
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
L684:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								if (execL681(var0,var1,var5,var7,nondeterministic)) {
									ret = true;
									break L684;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL676(Object var0, boolean nondeterministic) {
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
L676:
		{
			var5 = new HashSet();
			var3 = new Atom(null, f8);
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link;
				link = ((Atom)var1).getArg(1);
				var4 = link.getAtom();
				((Set)var5).add(((Atom)var1));
				isground_ret = ((Link)var2).isGround(((Set)var5));
				if (!(isground_ret == -1)) {
					var6 = new IntegerFunctor(isground_ret);
					if (!(!((Atom)var3).getFunctor().equals(((Atom)var4).getFunctor()))) {
						if (execL672(var0,var1,var3,var4,nondeterministic)) {
							ret = true;
							break L676;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL672(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L672:
		{
			link = ((Atom)var1).getArg(2);
			var6 = link;
			link = ((Atom)var1).getArg(0);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var0).removeGround(((Link)var4));
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			func = f2;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var6 );
			ret = true;
			break L672;
		}
		return ret;
	}
	public boolean execL673(Object var0, Object var1, boolean nondeterministic) {
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
L673:
		{
			if (execL675(var0, var1, nondeterministic)) {
				ret = true;
				break L673;
			}
		}
		return ret;
	}
	public boolean execL675(Object var0, Object var1, boolean nondeterministic) {
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
L675:
		{
			var8 = new HashSet();
			var6 = new Atom(null, f8);
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link;
					link = ((Atom)var1).getArg(1);
					var7 = link.getAtom();
					((Set)var8).add(((Atom)var1));
					isground_ret = ((Link)var5).isGround(((Set)var8));
					if (!(isground_ret == -1)) {
						var9 = new IntegerFunctor(isground_ret);
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var7).getFunctor()))) {
							if (execL672(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L675;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL667(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L667:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL663(var0,var1,nondeterministic)) {
					ret = true;
					break L667;
				}
			}
		}
		return ret;
	}
	public boolean execL663(Object var0, Object var1, boolean nondeterministic) {
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
L663:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			link = ((Atom)var1).getArg(1);
			var4 = link;
			link = ((Atom)var1).getArg(2);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var4 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var5 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L663;
		}
		return ret;
	}
	public boolean execL664(Object var0, Object var1, boolean nondeterministic) {
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
L664:
		{
			if (execL666(var0, var1, nondeterministic)) {
				ret = true;
				break L664;
			}
		}
		return ret;
	}
	public boolean execL666(Object var0, Object var1, boolean nondeterministic) {
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
L666:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL663(var0,var1,nondeterministic)) {
						ret = true;
						break L666;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL658(Object var0, boolean nondeterministic) {
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
L658:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (execL652(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L658;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL652(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L652:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var2).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(0);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f11;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var6 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var7 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L652;
		}
		return ret;
	}
	public boolean execL653(Object var0, Object var1, boolean nondeterministic) {
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
L653:
		{
			if (execL655(var0, var1, nondeterministic)) {
				ret = true;
				break L653;
			}
			if (execL657(var0, var1, nondeterministic)) {
				ret = true;
				break L653;
			}
		}
		return ret;
	}
	public boolean execL657(Object var0, Object var1, boolean nondeterministic) {
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
L657:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f10).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL652(var0,var5,var1,nondeterministic)) {
								ret = true;
								break L657;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL655(Object var0, Object var1, boolean nondeterministic) {
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
L655:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f11).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL652(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L655;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL647(Object var0, boolean nondeterministic) {
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
L647:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (execL641(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L647;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL641(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L641:
		{
			link = ((Atom)var2).getArg(1);
			var6 = link;
			link = ((Atom)var2).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f10;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var4), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var6 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var7 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L641;
		}
		return ret;
	}
	public boolean execL642(Object var0, Object var1, boolean nondeterministic) {
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
L642:
		{
			if (execL644(var0, var1, nondeterministic)) {
				ret = true;
				break L642;
			}
			if (execL646(var0, var1, nondeterministic)) {
				ret = true;
				break L642;
			}
		}
		return ret;
	}
	public boolean execL646(Object var0, Object var1, boolean nondeterministic) {
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
L646:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f12).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL641(var0,var5,var1,nondeterministic)) {
								ret = true;
								break L646;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL644(Object var0, Object var1, boolean nondeterministic) {
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
L644:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f11).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL641(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L644;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL636(Object var0, boolean nondeterministic) {
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
L636:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (execL630(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L636;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL630(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L630:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 2,
				((Atom)var2), 1 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L630;
		}
		return ret;
	}
	public boolean execL631(Object var0, Object var1, boolean nondeterministic) {
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
L631:
		{
			if (execL633(var0, var1, nondeterministic)) {
				ret = true;
				break L631;
			}
			if (execL635(var0, var1, nondeterministic)) {
				ret = true;
				break L631;
			}
		}
		return ret;
	}
	public boolean execL635(Object var0, Object var1, boolean nondeterministic) {
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
L635:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f13).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL630(var0,var5,var1,nondeterministic)) {
								ret = true;
								break L635;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL633(Object var0, Object var1, boolean nondeterministic) {
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
L633:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							if (execL630(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L633;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL625(Object var0, boolean nondeterministic) {
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
L625:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f15).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						link = ((Atom)var2).getArg(1);
						var4 = link.getAtom();
						link = ((Atom)var2).getArg(2);
						var5 = link.getAtom();
						link = ((Atom)var2).getArg(3);
						var6 = link.getAtom();
						link = ((Atom)var2).getArg(4);
						var7 = link.getAtom();
						func = ((Atom)var7).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var6).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var5).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var4).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var3).getFunctor();
										if (!(func.getArity() != 1)) {
											if (execL619(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L625;
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
	public boolean execL619(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L619:
		{
			link = ((Atom)var2).getArg(6);
			var15 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f2;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var11), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var8), 0 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 2,
				((Atom)var12), 1 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var9), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var14), 0 );
			((Atom)var13).getMem().newLink(
				((Atom)var13), 0,
				((Atom)var14), 1 );
			((Atom)var14).getMem().inheritLink(
				((Atom)var14), 2,
				(Link)var15 );
			ret = true;
			break L619;
		}
		return ret;
	}
	public boolean execL620(Object var0, Object var1, boolean nondeterministic) {
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
L620:
		{
			if (execL622(var0, var1, nondeterministic)) {
				ret = true;
				break L620;
			}
			if (execL624(var0, var1, nondeterministic)) {
				ret = true;
				break L620;
			}
		}
		return ret;
	}
	public boolean execL624(Object var0, Object var1, boolean nondeterministic) {
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
L624:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					link = ((Atom)var1).getArg(6);
					var8 = link;
					link = ((Atom)var1).getArg(5);
					if (!(link.getPos() != 0)) {
						var9 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var11 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var12 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var13 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						var14 = link.getAtom();
						link = ((Atom)var1).getArg(4);
						var15 = link.getAtom();
						func = ((Atom)var15).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var14).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var13).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var12).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var11).getFunctor();
										if (!(func.getArity() != 1)) {
											if (!(!(f14).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL619(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L624;
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
	public boolean execL622(Object var0, Object var1, boolean nondeterministic) {
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
L622:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(5);
							var9 = link;
							link = ((Atom)var3).getArg(6);
							var10 = link;
							link = ((Atom)var3).getArg(0);
							var11 = link.getAtom();
							link = ((Atom)var3).getArg(1);
							var12 = link.getAtom();
							link = ((Atom)var3).getArg(2);
							var13 = link.getAtom();
							link = ((Atom)var3).getArg(3);
							var14 = link.getAtom();
							link = ((Atom)var3).getArg(4);
							var15 = link.getAtom();
							func = ((Atom)var15).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var14).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var13).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var12).getFunctor();
										if (!(func.getArity() != 1)) {
											func = ((Atom)var11).getFunctor();
											if (!(func.getArity() != 1)) {
												if (execL619(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L622;
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
	public boolean execL614(Object var0, boolean nondeterministic) {
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
L614:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f15).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						link = ((Atom)var2).getArg(1);
						var4 = link.getAtom();
						link = ((Atom)var2).getArg(2);
						var5 = link.getAtom();
						link = ((Atom)var2).getArg(3);
						var6 = link.getAtom();
						link = ((Atom)var2).getArg(4);
						var7 = link.getAtom();
						func = ((Atom)var7).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var6).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var5).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var4).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var3).getFunctor();
										if (!(func.getArity() != 1)) {
											if (execL608(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L614;
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
	public boolean execL608(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L608:
		{
			link = ((Atom)var2).getArg(6);
			var29 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var11 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var12 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var14 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var15 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var16 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var17 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var25 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var26 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var28 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var18).getMem().newLink(
				((Atom)var18), 0,
				((Atom)var19), 1 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 0,
				((Atom)var8), 0 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 2,
				((Atom)var20), 1 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 0,
				((Atom)var14), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 2,
				((Atom)var28), 0 );
			((Atom)var21).getMem().newLink(
				((Atom)var21), 0,
				((Atom)var22), 1 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 0,
				((Atom)var9), 0 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 2,
				((Atom)var23), 1 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 0,
				((Atom)var11), 0 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 2,
				((Atom)var2), 3 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 0,
				((Atom)var25), 1 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 0,
				((Atom)var10), 0 );
			((Atom)var25).getMem().newLink(
				((Atom)var25), 2,
				((Atom)var26), 1 );
			((Atom)var26).getMem().newLink(
				((Atom)var26), 0,
				((Atom)var16), 0 );
			((Atom)var26).getMem().newLink(
				((Atom)var26), 2,
				((Atom)var2), 5 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var17), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var15), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 2,
				((Atom)var12), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 4,
				((Atom)var13), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 6,
				((Atom)var28), 1 );
			((Atom)var28).getMem().inheritLink(
				((Atom)var28), 2,
				(Link)var29 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L608;
		}
		return ret;
	}
	public boolean execL609(Object var0, Object var1, boolean nondeterministic) {
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
L609:
		{
			if (execL611(var0, var1, nondeterministic)) {
				ret = true;
				break L609;
			}
			if (execL613(var0, var1, nondeterministic)) {
				ret = true;
				break L609;
			}
		}
		return ret;
	}
	public boolean execL613(Object var0, Object var1, boolean nondeterministic) {
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
L613:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					link = ((Atom)var1).getArg(6);
					var8 = link;
					link = ((Atom)var1).getArg(5);
					if (!(link.getPos() != 0)) {
						var9 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var11 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var12 = link.getAtom();
						link = ((Atom)var1).getArg(2);
						var13 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						var14 = link.getAtom();
						link = ((Atom)var1).getArg(4);
						var15 = link.getAtom();
						func = ((Atom)var15).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var14).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var13).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var12).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var11).getFunctor();
										if (!(func.getArity() != 1)) {
											if (!(!(f16).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL608(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L613;
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
	public boolean execL611(Object var0, Object var1, boolean nondeterministic) {
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
L611:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(5);
							var9 = link;
							link = ((Atom)var3).getArg(6);
							var10 = link;
							link = ((Atom)var3).getArg(0);
							var11 = link.getAtom();
							link = ((Atom)var3).getArg(1);
							var12 = link.getAtom();
							link = ((Atom)var3).getArg(2);
							var13 = link.getAtom();
							link = ((Atom)var3).getArg(3);
							var14 = link.getAtom();
							link = ((Atom)var3).getArg(4);
							var15 = link.getAtom();
							func = ((Atom)var15).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var14).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var13).getFunctor();
									if (!(func.getArity() != 1)) {
										func = ((Atom)var12).getFunctor();
										if (!(func.getArity() != 1)) {
											func = ((Atom)var11).getFunctor();
											if (!(func.getArity() != 1)) {
												if (execL608(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L611;
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
	public boolean execL603(Object var0, boolean nondeterministic) {
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
L603:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				link = ((Atom)var1).getArg(2);
				var4 = link.getAtom();
				link = ((Atom)var1).getArg(3);
				var5 = link.getAtom();
				link = ((Atom)var1).getArg(4);
				var6 = link.getAtom();
				func = ((Atom)var6).getFunctor();
				if (!(func.getArity() != 1)) {
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						func = ((Atom)var4).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var3).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var2).getFunctor();
								if (!(func.getArity() != 1)) {
									if (execL599(var0,var1,var2,var3,var4,var5,var6,nondeterministic)) {
										ret = true;
										break L603;
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
	public boolean execL599(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L599:
		{
			link = ((Atom)var1).getArg(5);
			var18 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var11 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var12 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f2;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f15;
			var17 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var14).getMem().newLink(
				((Atom)var14), 0,
				((Atom)var15), 1 );
			((Atom)var15).getMem().newLink(
				((Atom)var15), 0,
				((Atom)var7), 0 );
			((Atom)var15).getMem().newLink(
				((Atom)var15), 2,
				((Atom)var16), 1 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 0,
				((Atom)var12), 0 );
			((Atom)var16).getMem().newLink(
				((Atom)var16), 2,
				((Atom)var17), 5 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 0,
				((Atom)var13), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 1,
				((Atom)var11), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 2,
				((Atom)var9), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 3,
				((Atom)var8), 0 );
			((Atom)var17).getMem().newLink(
				((Atom)var17), 4,
				((Atom)var10), 0 );
			((Atom)var17).getMem().inheritLink(
				((Atom)var17), 6,
				(Link)var18 );
			atom = ((Atom)var17);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L599;
		}
		return ret;
	}
	public boolean execL600(Object var0, Object var1, boolean nondeterministic) {
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
L600:
		{
			if (execL602(var0, var1, nondeterministic)) {
				ret = true;
				break L600;
			}
		}
		return ret;
	}
	public boolean execL602(Object var0, Object var1, boolean nondeterministic) {
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
L602:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(5);
					var7 = link;
					link = ((Atom)var1).getArg(0);
					var8 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var9 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					var10 = link.getAtom();
					link = ((Atom)var1).getArg(3);
					var11 = link.getAtom();
					link = ((Atom)var1).getArg(4);
					var12 = link.getAtom();
					func = ((Atom)var12).getFunctor();
					if (!(func.getArity() != 1)) {
						func = ((Atom)var11).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var10).getFunctor();
							if (!(func.getArity() != 1)) {
								func = ((Atom)var9).getFunctor();
								if (!(func.getArity() != 1)) {
									func = ((Atom)var8).getFunctor();
									if (!(func.getArity() != 1)) {
										if (execL599(var0,var1,var8,var9,var10,var11,var12,nondeterministic)) {
											ret = true;
											break L602;
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
	public boolean execL594(Object var0, boolean nondeterministic) {
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
L594:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
								if (execL589(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L594;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL589(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L589:
		{
			link = ((Atom)var2).getArg(1);
			var14 = link;
			link = ((Atom)var1).getArg(3);
			var15 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var2), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 2,
				((Atom)var10), 1 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var12), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var7), 0 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var5), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 2,
				(Link)var14 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 3,
				((Atom)var12), 1 );
			((Atom)var12).getMem().inheritLink(
				((Atom)var12), 2,
				(Link)var15 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L589;
		}
		return ret;
	}
	public boolean execL590(Object var0, Object var1, boolean nondeterministic) {
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
L590:
		{
			if (execL592(var0, var1, nondeterministic)) {
				ret = true;
				break L590;
			}
		}
		return ret;
	}
	public boolean execL592(Object var0, Object var1, boolean nondeterministic) {
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
L592:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						link = ((Atom)var1).getArg(0);
						var10 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var11 = link.getAtom();
						func = ((Atom)var11).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var10).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(f1).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(2);
									var9 = link;
									if (execL589(var0,var1,var6,var10,var11,nondeterministic)) {
										ret = true;
										break L592;
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
	public boolean execL584(Object var0, boolean nondeterministic) {
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
L584:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
								if (execL579(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L584;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL579(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L579:
		{
			link = ((Atom)var1).getArg(3);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var6 );
			ret = true;
			break L579;
		}
		return ret;
	}
	public boolean execL580(Object var0, Object var1, boolean nondeterministic) {
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
L580:
		{
			if (execL582(var0, var1, nondeterministic)) {
				ret = true;
				break L580;
			}
		}
		return ret;
	}
	public boolean execL582(Object var0, Object var1, boolean nondeterministic) {
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
L582:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						link = ((Atom)var1).getArg(0);
						var8 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var9 = link.getAtom();
						func = ((Atom)var9).getFunctor();
						if (!(func.getArity() != 1)) {
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									if (execL579(var0,var1,var6,var8,var9,nondeterministic)) {
										ret = true;
										break L582;
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
	public boolean execL574(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L574:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL570(var0,var1,nondeterministic)) {
					ret = true;
					break L574;
				}
			}
		}
		return ret;
	}
	public boolean execL570(Object var0, Object var1, boolean nondeterministic) {
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
L570:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f2;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f18;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 1 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 2,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var6 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L570;
		}
		return ret;
	}
	public boolean execL571(Object var0, Object var1, boolean nondeterministic) {
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
L571:
		{
			if (execL573(var0, var1, nondeterministic)) {
				ret = true;
				break L571;
			}
		}
		return ret;
	}
	public boolean execL573(Object var0, Object var1, boolean nondeterministic) {
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
L573:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL570(var0,var1,nondeterministic)) {
						ret = true;
						break L573;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL565(Object var0, boolean nondeterministic) {
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
L565:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL560(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L565;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL560(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L560:
		{
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var4 );
			ret = true;
			break L560;
		}
		return ret;
	}
	public boolean execL561(Object var0, Object var1, boolean nondeterministic) {
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
L561:
		{
			if (execL563(var0, var1, nondeterministic)) {
				ret = true;
				break L561;
			}
		}
		return ret;
	}
	public boolean execL563(Object var0, Object var1, boolean nondeterministic) {
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
L563:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
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
							if (execL560(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L563;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL555(Object var0, boolean nondeterministic) {
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
L555:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f20).equals(((Atom)var6).getFunctor()))) {
															if (execL545(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L555;
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
			link = ((Atom)var6).getArg(1);
			var9 = link;
			link = ((Atom)var4).getArg(0);
			var11 = link;
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
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			func = f13;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var9 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 0,
				(Link)var11 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L545;
		}
		return ret;
	}
	public boolean execL546(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (execL550(var0, var1, nondeterministic)) {
				ret = true;
				break L546;
			}
		}
		return ret;
	}
	public boolean execL550(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L550:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										link = ((Atom)var8).getArg(1);
										if (!(link.getPos() != 2)) {
											var14 = link.getAtom();
											if (!(!(f1).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f2).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f14).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL545(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L550;
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
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
		Object var15 = null;
		Object var16 = null;
		Object var17 = null;
		Object var18 = null;
		Object var19 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
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
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var11 = link.getAtom();
									if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
										link = ((Atom)var7).getArg(0);
										var8 = link;
										link = ((Atom)var7).getArg(1);
										var9 = link;
										link = ((Atom)var7).getArg(2);
										var10 = link;
										if (!(((Atom)var11) == ((Atom)var7))) {
											link = ((Atom)var7).getArg(1);
											if (!(link.getPos() != 0)) {
												var15 = link.getAtom();
												if (!(!(f2).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f20).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL545(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L548;
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
		return ret;
	}
	public boolean execL540(Object var0, boolean nondeterministic) {
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
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f20).equals(((Atom)var6).getFunctor()))) {
															if (execL530(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L540;
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
	public boolean execL530(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L530:
		{
			link = ((Atom)var6).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(1);
			var11 = link;
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
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var9 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var4), 1 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var11 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L530;
		}
		return ret;
	}
	public boolean execL531(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (execL533(var0, var1, nondeterministic)) {
				ret = true;
				break L531;
			}
			if (execL535(var0, var1, nondeterministic)) {
				ret = true;
				break L531;
			}
		}
		return ret;
	}
	public boolean execL535(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L535:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f1).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(0);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										link = ((Atom)var8).getArg(1);
										if (!(link.getPos() != 2)) {
											var14 = link.getAtom();
											if (!(!(f1).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f2).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f16).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL530(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L535;
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
	public boolean execL533(Object var0, Object var1, boolean nondeterministic) {
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L533:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var11 = link.getAtom();
									if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
										link = ((Atom)var7).getArg(0);
										var8 = link;
										link = ((Atom)var7).getArg(1);
										var9 = link;
										link = ((Atom)var7).getArg(2);
										var10 = link;
										if (!(((Atom)var11) == ((Atom)var7))) {
											link = ((Atom)var7).getArg(1);
											if (!(link.getPos() != 0)) {
												var15 = link.getAtom();
												if (!(!(f2).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f1).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f20).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL530(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L533;
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
		return ret;
	}
	public boolean execL525(Object var0, boolean nondeterministic) {
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
L525:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL521(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L525;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL521(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L521:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f19;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f20;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var6 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var5), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var7 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L521;
		}
		return ret;
	}
	public boolean execL522(Object var0, Object var1, boolean nondeterministic) {
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
L522:
		{
			if (execL524(var0, var1, nondeterministic)) {
				ret = true;
				break L522;
			}
		}
		return ret;
	}
	public boolean execL524(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL521(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L524;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL516(Object var0, boolean nondeterministic) {
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
L516:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f23).equals(((Atom)var2).getFunctor()))) {
						if (execL510(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L516;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL510(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L510:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			func = f24;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var4), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var6), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var2), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var6), 1 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var9 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_queue");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module queue");
				}
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L510;
		}
		return ret;
	}
	public boolean execL511(Object var0, Object var1, boolean nondeterministic) {
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
L511:
		{
			if (execL513(var0, var1, nondeterministic)) {
				ret = true;
				break L511;
			}
			if (execL515(var0, var1, nondeterministic)) {
				ret = true;
				break L511;
			}
		}
		return ret;
	}
	public boolean execL515(Object var0, Object var1, boolean nondeterministic) {
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
L515:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f22).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL510(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L515;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL513(Object var0, Object var1, boolean nondeterministic) {
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
L513:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f23).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL510(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L513;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL505(Object var0, boolean nondeterministic) {
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
L505:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(((Atom)var2) != ((Atom)var1))) {
						link = ((Atom)var1).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f23).equals(((Atom)var3).getFunctor()))) {
								if (execL499(var0,var1,var3,nondeterministic)) {
									ret = true;
									break L505;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL499(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L499:
		{
			link = ((Atom)var2).getArg(1);
			var4 = link;
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
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			ret = true;
			break L499;
		}
		return ret;
	}
	public boolean execL500(Object var0, Object var1, boolean nondeterministic) {
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
L500:
		{
			if (execL502(var0, var1, nondeterministic)) {
				ret = true;
				break L500;
			}
			if (execL504(var0, var1, nondeterministic)) {
				ret = true;
				break L500;
			}
		}
		return ret;
	}
	public boolean execL504(Object var0, Object var1, boolean nondeterministic) {
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
L504:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f22).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(((Atom)var8) != ((Atom)var4))) {
									if (execL499(var0,var4,var1,nondeterministic)) {
										ret = true;
										break L504;
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
	public boolean execL502(Object var0, Object var1, boolean nondeterministic) {
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
L502:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
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
						if (!(((Atom)var5) != ((Atom)var1))) {
							link = ((Atom)var1).getArg(2);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(f23).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									if (execL499(var0,var1,var6,nondeterministic)) {
										ret = true;
										break L502;
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
	public boolean execL494(Object var0, boolean nondeterministic) {
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
L494:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL489(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L494;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL489(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
			link = ((Atom)var2).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var5 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var8 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_list");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module list");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module list");
				}
			ret = true;
			break L489;
		}
		return ret;
	}
	public boolean execL490(Object var0, Object var1, boolean nondeterministic) {
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
L490:
		{
			if (execL492(var0, var1, nondeterministic)) {
				ret = true;
				break L490;
			}
		}
		return ret;
	}
	public boolean execL492(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL489(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L492;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL484(Object var0, boolean nondeterministic) {
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
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL479(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L484;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL479(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 1 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L479;
		}
		return ret;
	}
	public boolean execL480(Object var0, Object var1, boolean nondeterministic) {
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
L480:
		{
			if (execL482(var0, var1, nondeterministic)) {
				ret = true;
				break L480;
			}
		}
		return ret;
	}
	public boolean execL482(Object var0, Object var1, boolean nondeterministic) {
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
L482:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
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
							if (execL479(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L482;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL474(Object var0, boolean nondeterministic) {
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
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL469(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L474;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL469(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L469:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var2), 1 );
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 1,
				((Atom)var2), 0 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L469;
		}
		return ret;
	}
	public boolean execL470(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL472(var0, var1, nondeterministic)) {
				ret = true;
				break L470;
			}
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
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL469(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L472;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL464(Object var0, boolean nondeterministic) {
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
L464:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL459(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L464;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL459(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
			link = ((Atom)var1).getArg(2);
			var5 = link;
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f13;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L459;
		}
		return ret;
	}
	public boolean execL460(Object var0, Object var1, boolean nondeterministic) {
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
L460:
		{
			if (execL462(var0, var1, nondeterministic)) {
				ret = true;
				break L460;
			}
		}
		return ret;
	}
	public boolean execL462(Object var0, Object var1, boolean nondeterministic) {
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
L462:
		{
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
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
							if (execL459(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L462;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL454(Object var0, boolean nondeterministic) {
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
L454:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL449(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L454;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL449(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L449:
		{
			link = ((Atom)var2).getArg(0);
			var5 = link;
			link = ((Atom)var2).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(1);
			var7 = link;
			link = ((Atom)var1).getArg(2);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var6 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var7 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var8 );
			ret = true;
			break L449;
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
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (execL452(var0, var1, nondeterministic)) {
				ret = true;
				break L450;
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
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL449(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L452;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL444(Object var0, boolean nondeterministic) {
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
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL439(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L444;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL439(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var4), 1 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var6 );
			ret = true;
			break L439;
		}
		return ret;
	}
	public boolean execL440(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL442(var0, var1, nondeterministic)) {
				ret = true;
				break L440;
			}
		}
		return ret;
	}
	public boolean execL442(Object var0, Object var1, boolean nondeterministic) {
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
L442:
		{
			if (!(!(f25).equals(((Atom)var1).getFunctor()))) {
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
							if (execL439(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L442;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL434(Object var0, boolean nondeterministic) {
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
L434:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL429(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L434;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL429(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L429:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			link = ((Atom)var1).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f14;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var7 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var8 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L429;
		}
		return ret;
	}
	public boolean execL430(Object var0, Object var1, boolean nondeterministic) {
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
L430:
		{
			if (execL432(var0, var1, nondeterministic)) {
				ret = true;
				break L430;
			}
		}
		return ret;
	}
	public boolean execL432(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL429(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L432;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL424(Object var0, boolean nondeterministic) {
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
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL419(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L424;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL419(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
			link = ((Atom)var1).getArg(2);
			var5 = link;
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f16;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var6 );
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
		}
		return ret;
	}
	public boolean execL422(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
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
							if (execL419(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L422;
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
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL410(var0,var1,nondeterministic)) {
					ret = true;
					break L414;
				}
			}
		}
		return ret;
	}
	public boolean execL410(Object var0, Object var1, boolean nondeterministic) {
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
L410:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f2;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			ret = true;
			break L410;
		}
		return ret;
	}
	public boolean execL411(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (execL413(var0, var1, nondeterministic)) {
				ret = true;
				break L411;
			}
		}
		return ret;
	}
	public boolean execL413(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL410(var0,var1,nondeterministic)) {
						ret = true;
						break L413;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f18 = new Functor("fold", 4, "list");
	private static final Functor f12 = new Functor("int", 2, null);
	private static final Functor f26 = new Functor("is_empty", 3, null);
	private static final Functor f13 = new Functor("nil", 1, null);
	private static final Functor f10 = new Functor("cons", 3, null);
	private static final Functor f6 = new Functor("-", 3, null);
	private static final Functor f21 = new Functor("grep", 3, "list");
	private static final Functor f0 = new Functor("uniq", 2, "list");
	private static final Functor f5 = new Functor("choose_k", 3, null);
	private static final Functor f1 = new Functor(".", 3, null);
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f4 = new IntegerFunctor(1);
	private static final Functor f27 = new Functor("new", 1, "list");
	private static final Functor f19 = new Functor("map", 3, "list");
	private static final Functor f16 = new Functor("true", 1, null);
	private static final Functor f7 = new Functor("append", 3, "list");
	private static final Functor f23 = new Functor("of_queue", 2, "list");
	private static final Functor f22 = new Functor("new", 3, "queue");
	private static final Functor f3 = new Functor("dist", 3, null);
	private static final Functor f14 = new Functor("false", 1, null);
	private static final Functor f24 = new Functor("shift", 3, null);
	private static final Functor f11 = new Functor("flatten", 3, "list");
	private static final Functor f17 = new Functor("unfold", 6, "list");
	private static final Functor f20 = new Functor("grep_s0", 2, null);
	private static final Functor f9 = new Functor("choose_k", 3, "list");
	private static final Functor f15 = new Functor("unfold_s0", 7, "list");
	private static final Functor f25 = new Functor("unshift", 3, null);
	private static final Functor f8 = new IntegerFunctor(0);
	private Uniq uniq0 = new Uniq();
	private Uniq uniq1 = new Uniq();
}

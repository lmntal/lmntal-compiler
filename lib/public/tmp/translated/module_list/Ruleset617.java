package translated.module_list;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset617 extends Ruleset {
	private static final Ruleset617 theInstance = new Ruleset617();
	private Ruleset617() {}
	public static Ruleset617 getInstance() {
		return theInstance;
	}
	private int id = 617;
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
"('='(H, list.new) :- '='(H, '[]')), ('='(H, list.is_empty('[]', Return)) :- '='(H, '[]'), '='(Return, true)), ('='(H, list.is_empty('.'(Value, Next), Return)) :- '='(H, '.'(Value, Next)), '='(Return, false)), ('='(H, list.unshift('[]', Obj)) :- '='(H, '.'(Obj, '[]'))), ('='(H, list.unshift('.'(Value, Next), Obj)) :- '='(H, '.'(Obj, '.'(Value, Next)))), ('='(H, list.shift('[]', Return)) :- '='(H, '[]'), '='(Return, nil)), ('='(H, list.shift('.'(Value, Next), Return)) :- '='(H, Next), '='(Return, Value)), ('='(H, list.append('[]', B)) :- '='(H, B)), ('='(H, list.append('.'(Value, Next), B)) :- '='(H, '.'(Value, list.append(Next, B)))), ('='(H, list.of_queue(queue.new(Head, Head))) :- '='(H, '[]')), ('='(H, list.of_queue(queue.new(Head, Tail))) :- '='(H, '.'(El, list.of_queue(list.shift(queue.new(Head, Tail), El))))), ('='(H, list.grep(F, List)) :- unary(F) | '='(H, list.grep_s0(list.map(F, List)))), ('='(H, list.grep_s0('.'('.'(true, '.'(El, '[]')), CDR))) :- '='(H, '.'(El, list.grep_s0(CDR)))), ('='(H, list.grep_s0('.'('.'(false, '.'(El, '[]')), CDR))) :- '='(H, list.grep_s0(CDR)), nil(El)), ('='(H, list.grep_s0('[]')) :- '='(H, '[]')), ('='(H, list.map(F, List)) :- '='(H, list.fold(F, '[]', List))), ('='(H, list.fold(F, I, '[]')) :- unary(F), unary(I) | '='(H, I)), ('='(H, list.fold(F, I, '.'(CAR, CDR))) :- unary(F), unary(I) | '='(H, '.'('.'(F, '.'(CAR, '[]')), list.fold(F, I, CDR)))), ('='(H, list.unfold(P, F, G, Seed, Tailgen)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, list.unfold_s0(P, F, G, Seed, Tailgen, '.'(P, '.'(Seed, '[]'))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, true)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), list.unfold_s0(P, F, G, '.'(G, '.'(Seed, '[]')), Tailgen, '.'(P, '.'(Seed, '[]')))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, false)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), '[]'))), ('='(H, list.flatten('[]')) :- '='(H, '[]')), ('='(H, list.flatten('.'(CAR, CDR))) :- unary(CAR) | '='(H, '.'(CAR, list.flatten(CDR)))), ('='(H, list.flatten('.'('.'(CAR, CADR), CDR))) :- '='(H, list.flatten('.'(CAR, list.append(CADR, CDR))))), ('='(H, list.choose_k(L, K)) :- ground(L), '='(K, 0) | '='(H, '[]')), ('='(H, list.choose_k('[]', K)) :- int(K) | '='(H, '[]')), ('='(H, list.choose_k('.'(Hd, R), K)) :- '='(K, 1) | '='(H, '.'('.'(Hd, '[]'), list.choose_k(R, K)))), ('='(H, list.choose_k('.'(Hd, Tl), K)) :- '>'(K, 1), ground(Tl) | '='(H, list.append(list.dist(Hd, list.choose_k(Tl, '-'(K, 1))), list.choose_k(Tl, K)))), ('='(H, list.dist(A, '[]')) :- ground(A) | '='(H, '[]')), ('='(H, list.dist(A, '.'(Hd, Tl))) :- ground(A) | '='(H, '.'('.'(A, Hd), list.dist(A, Tl)))), ('='(H, list.uniq('[]')) :- '='(H, '[]')), ('='(H, list.uniq('.'(Hd, Tl))) :- uniq(Hd) | '='(H, '.'(Hd, list.uniq(Tl)))), ('='(H, list.uniq('.'(Hd, Tl))) :- not_uniq(Hd) | '='(H, list.uniq(Tl)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1225(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "new");
			return true;
		}
		if (execL1234(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "is_empty");
			return true;
		}
		if (execL1244(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "is_empty");
			return true;
		}
		if (execL1254(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "unshift");
			return true;
		}
		if (execL1264(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "unshift");
			return true;
		}
		if (execL1274(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "shift");
			return true;
		}
		if (execL1284(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "shift");
			return true;
		}
		if (execL1294(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "append");
			return true;
		}
		if (execL1304(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "append");
			return true;
		}
		if (execL1314(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "new");
			return true;
		}
		if (execL1325(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "new");
			return true;
		}
		if (execL1336(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "grep");
			return true;
		}
		if (execL1345(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "true");
			return true;
		}
		if (execL1360(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "false");
			return true;
		}
		if (execL1375(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "grep_s0");
			return true;
		}
		if (execL1385(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "map");
			return true;
		}
		if (execL1394(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "fold");
			return true;
		}
		if (execL1404(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "fold");
			return true;
		}
		if (execL1414(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "unfold");
			return true;
		}
		if (execL1423(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "true");
			return true;
		}
		if (execL1434(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "false");
			return true;
		}
		if (execL1445(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "flatten");
			return true;
		}
		if (execL1455(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "flatten");
			return true;
		}
		if (execL1465(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "flatten");
			return true;
		}
		if (execL1476(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "choose_k");
			return true;
		}
		if (execL1485(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "choose_k");
			return true;
		}
		if (execL1495(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "choose_k");
			return true;
		}
		if (execL1505(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "choose_k");
			return true;
		}
		if (execL1515(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "dist");
			return true;
		}
		if (execL1525(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "dist");
			return true;
		}
		if (execL1535(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "uniq");
			return true;
		}
		if (execL1545(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "uniq");
			return true;
		}
		if (execL1555(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "uniq");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1228(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "new");
			return true;
		}
		if (execL1238(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "is_empty");
			return true;
		}
		if (execL1248(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "is_empty");
			return true;
		}
		if (execL1258(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "unshift");
			return true;
		}
		if (execL1268(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "unshift");
			return true;
		}
		if (execL1278(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "shift");
			return true;
		}
		if (execL1288(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "shift");
			return true;
		}
		if (execL1298(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "append");
			return true;
		}
		if (execL1308(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "append");
			return true;
		}
		if (execL1319(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "new");
			return true;
		}
		if (execL1330(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "new");
			return true;
		}
		if (execL1339(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "grep");
			return true;
		}
		if (execL1354(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "true");
			return true;
		}
		if (execL1369(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "false");
			return true;
		}
		if (execL1379(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "grep_s0");
			return true;
		}
		if (execL1388(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "map");
			return true;
		}
		if (execL1398(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "fold");
			return true;
		}
		if (execL1408(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "fold");
			return true;
		}
		if (execL1417(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "unfold");
			return true;
		}
		if (execL1428(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "true");
			return true;
		}
		if (execL1439(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "false");
			return true;
		}
		if (execL1449(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "flatten");
			return true;
		}
		if (execL1459(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "flatten");
			return true;
		}
		if (execL1470(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "flatten");
			return true;
		}
		if (execL1479(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "choose_k");
			return true;
		}
		if (execL1489(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "choose_k");
			return true;
		}
		if (execL1499(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "choose_k");
			return true;
		}
		if (execL1509(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "choose_k");
			return true;
		}
		if (execL1519(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "dist");
			return true;
		}
		if (execL1529(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "dist");
			return true;
		}
		if (execL1539(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "uniq");
			return true;
		}
		if (execL1549(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "uniq");
			return true;
		}
		if (execL1559(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "uniq");
			return true;
		}
		return result;
	}
	public boolean execL1559(Object var0, boolean nondeterministic) {
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
L1559:
		{
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
							boolean goAhead = uniq0.check(new Link[] {(Link)var3							});
							goAhead = !goAhead;
							if(goAhead) {
								if (execL1554(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L1559;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1554(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1554:
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
			break L1554;
		}
		return ret;
	}
	public boolean execL1555(Object var0, Object var1, boolean nondeterministic) {
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
L1555:
		{
			if (execL1557(var0, var1, nondeterministic)) {
				ret = true;
				break L1555;
			}
		}
		return ret;
	}
	public boolean execL1557(Object var0, Object var1, boolean nondeterministic) {
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
L1557:
		{
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
								boolean goAhead = uniq0.check(new Link[] {(Link)var8								});
								goAhead = !goAhead;
								if(goAhead) {
									if (execL1554(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L1557;
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
	public boolean execL1549(Object var0, boolean nondeterministic) {
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
L1549:
		{
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
							boolean goAhead = uniq1.check(new Link[] {(Link)var3							});
							if(goAhead) {
								if (execL1544(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L1549;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1544(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1544:
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
			break L1544;
		}
		return ret;
	}
	public boolean execL1545(Object var0, Object var1, boolean nondeterministic) {
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
L1545:
		{
			if (execL1547(var0, var1, nondeterministic)) {
				ret = true;
				break L1545;
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
L1547:
		{
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
								boolean goAhead = uniq1.check(new Link[] {(Link)var8								});
								if(goAhead) {
									if (execL1544(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L1547;
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
	public boolean execL1539(Object var0, boolean nondeterministic) {
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
L1539:
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
						if (execL1534(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1539;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1534(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1534:
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
			break L1534;
		}
		return ret;
	}
	public boolean execL1535(Object var0, Object var1, boolean nondeterministic) {
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
L1535:
		{
			if (execL1537(var0, var1, nondeterministic)) {
				ret = true;
				break L1535;
			}
		}
		return ret;
	}
	public boolean execL1537(Object var0, Object var1, boolean nondeterministic) {
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
L1537:
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
							if (execL1534(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1537;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1529(Object var0, boolean nondeterministic) {
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
L1529:
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
							if (execL1524(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L1529;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1524(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1524:
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
			break L1524;
		}
		return ret;
	}
	public boolean execL1525(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1525:
		{
			if (execL1527(var0, var1, nondeterministic)) {
				ret = true;
				break L1525;
			}
		}
		return ret;
	}
	public boolean execL1527(Object var0, Object var1, boolean nondeterministic) {
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
L1527:
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
								if (execL1524(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L1527;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1519(Object var0, boolean nondeterministic) {
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
L1519:
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
							if (execL1514(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L1519;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1514(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1514:
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
			break L1514;
		}
		return ret;
	}
	public boolean execL1515(Object var0, Object var1, boolean nondeterministic) {
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
L1515:
		{
			if (execL1517(var0, var1, nondeterministic)) {
				ret = true;
				break L1515;
			}
		}
		return ret;
	}
	public boolean execL1517(Object var0, Object var1, boolean nondeterministic) {
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
L1517:
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
								if (execL1514(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L1517;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1509(Object var0, boolean nondeterministic) {
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
L1509:
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
									if (execL1504(var0,var1,var2,var4,var5,nondeterministic)) {
										ret = true;
										break L1509;
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
	public boolean execL1504(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1504:
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
			break L1504;
		}
		return ret;
	}
	public boolean execL1505(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1505:
		{
			if (execL1507(var0, var1, nondeterministic)) {
				ret = true;
				break L1505;
			}
		}
		return ret;
	}
	public boolean execL1507(Object var0, Object var1, boolean nondeterministic) {
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
L1507:
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
										if (execL1504(var0,var1,var5,var10,var11,nondeterministic)) {
											ret = true;
											break L1507;
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
	public boolean execL1499(Object var0, boolean nondeterministic) {
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
L1499:
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
							if (execL1494(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L1499;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1494(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1494:
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
			break L1494;
		}
		return ret;
	}
	public boolean execL1495(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1495:
		{
			if (execL1497(var0, var1, nondeterministic)) {
				ret = true;
				break L1495;
			}
		}
		return ret;
	}
	public boolean execL1497(Object var0, Object var1, boolean nondeterministic) {
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
L1497:
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
								if (execL1494(var0,var1,var5,var9,var10,nondeterministic)) {
									ret = true;
									break L1497;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1489(Object var0, boolean nondeterministic) {
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
L1489:
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
							if (execL1484(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1489;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1484(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1484:
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
			break L1484;
		}
		return ret;
	}
	public boolean execL1485(Object var0, Object var1, boolean nondeterministic) {
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
L1485:
		{
			if (execL1487(var0, var1, nondeterministic)) {
				ret = true;
				break L1485;
			}
		}
		return ret;
	}
	public boolean execL1487(Object var0, Object var1, boolean nondeterministic) {
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
L1487:
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
								if (execL1484(var0,var1,var5,var7,nondeterministic)) {
									ret = true;
									break L1487;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1479(Object var0, boolean nondeterministic) {
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
L1479:
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
						if (execL1475(var0,var1,var3,var4,nondeterministic)) {
							ret = true;
							break L1479;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1475(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1475:
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
			break L1475;
		}
		return ret;
	}
	public boolean execL1476(Object var0, Object var1, boolean nondeterministic) {
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
L1476:
		{
			if (execL1478(var0, var1, nondeterministic)) {
				ret = true;
				break L1476;
			}
		}
		return ret;
	}
	public boolean execL1478(Object var0, Object var1, boolean nondeterministic) {
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
L1478:
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
							if (execL1475(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L1478;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1470(Object var0, boolean nondeterministic) {
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
L1470:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								if (execL1464(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1470;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1464(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1464:
		{
			link = ((Atom)var2).getArg(1);
			var7 = link;
			link = ((Atom)var3).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			func = f7;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var7 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var8 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 2,
				((Atom)var1), 0 );
			atom = ((Atom)var1);
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
			break L1464;
		}
		return ret;
	}
	public boolean execL1465(Object var0, Object var1, boolean nondeterministic) {
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
L1465:
		{
			if (execL1467(var0, var1, nondeterministic)) {
				ret = true;
				break L1465;
			}
		}
		return ret;
	}
	public boolean execL1467(Object var0, Object var1, boolean nondeterministic) {
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
L1467:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
									if (execL1464(var0,var1,var8,var4,nondeterministic)) {
										ret = true;
										break L1467;
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
	public boolean execL1459(Object var0, boolean nondeterministic) {
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
L1459:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1454(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1459;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1454(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1454:
		{
			link = ((Atom)var2).getArg(1);
			var7 = link;
			link = ((Atom)var1).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var7 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 1,
				((Atom)var2), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var4), 0 );
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
			break L1454;
		}
		return ret;
	}
	public boolean execL1455(Object var0, Object var1, boolean nondeterministic) {
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
L1455:
		{
			if (execL1457(var0, var1, nondeterministic)) {
				ret = true;
				break L1455;
			}
		}
		return ret;
	}
	public boolean execL1457(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1457:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL1454(var0,var1,var4,var8,nondeterministic)) {
									ret = true;
									break L1457;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1449(Object var0, boolean nondeterministic) {
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
L1449:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1444(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1449;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1444(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1444:
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
			break L1444;
		}
		return ret;
	}
	public boolean execL1445(Object var0, Object var1, boolean nondeterministic) {
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
L1445:
		{
			if (execL1447(var0, var1, nondeterministic)) {
				ret = true;
				break L1445;
			}
		}
		return ret;
	}
	public boolean execL1447(Object var0, Object var1, boolean nondeterministic) {
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
L1447:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1444(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1447;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1439(Object var0, boolean nondeterministic) {
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
L1439:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
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
											if (execL1433(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L1439;
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
	public boolean execL1433(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L1433:
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
			break L1433;
		}
		return ret;
	}
	public boolean execL1434(Object var0, Object var1, boolean nondeterministic) {
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
L1434:
		{
			if (execL1436(var0, var1, nondeterministic)) {
				ret = true;
				break L1434;
			}
			if (execL1438(var0, var1, nondeterministic)) {
				ret = true;
				break L1434;
			}
		}
		return ret;
	}
	public boolean execL1438(Object var0, Object var1, boolean nondeterministic) {
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
L1438:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
											if (!(!(f10).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL1433(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1438;
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
	public boolean execL1436(Object var0, Object var1, boolean nondeterministic) {
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
L1436:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
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
												if (execL1433(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1436;
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
	public boolean execL1428(Object var0, boolean nondeterministic) {
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
L1428:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
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
											if (execL1422(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L1428;
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
	public boolean execL1422(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L1422:
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
			break L1422;
		}
		return ret;
	}
	public boolean execL1423(Object var0, Object var1, boolean nondeterministic) {
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
L1423:
		{
			if (execL1425(var0, var1, nondeterministic)) {
				ret = true;
				break L1423;
			}
			if (execL1427(var0, var1, nondeterministic)) {
				ret = true;
				break L1423;
			}
		}
		return ret;
	}
	public boolean execL1427(Object var0, Object var1, boolean nondeterministic) {
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
L1427:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
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
											if (!(!(f12).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL1422(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1427;
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
	public boolean execL1425(Object var0, Object var1, boolean nondeterministic) {
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
L1425:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
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
												if (execL1422(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1425;
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
	public boolean execL1417(Object var0, boolean nondeterministic) {
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
L1417:
		{
			func = f13;
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
									if (execL1413(var0,var1,var2,var3,var4,var5,var6,nondeterministic)) {
										ret = true;
										break L1417;
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
	public boolean execL1413(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1413:
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
			func = f11;
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
			break L1413;
		}
		return ret;
	}
	public boolean execL1414(Object var0, Object var1, boolean nondeterministic) {
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
L1414:
		{
			if (execL1416(var0, var1, nondeterministic)) {
				ret = true;
				break L1414;
			}
		}
		return ret;
	}
	public boolean execL1416(Object var0, Object var1, boolean nondeterministic) {
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
L1416:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
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
										if (execL1413(var0,var1,var8,var9,var10,var11,var12,nondeterministic)) {
											ret = true;
											break L1416;
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
	public boolean execL1408(Object var0, boolean nondeterministic) {
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
L1408:
		{
			func = f14;
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
								if (execL1403(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L1408;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1403(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1403:
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
			break L1403;
		}
		return ret;
	}
	public boolean execL1404(Object var0, Object var1, boolean nondeterministic) {
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
L1404:
		{
			if (execL1406(var0, var1, nondeterministic)) {
				ret = true;
				break L1404;
			}
		}
		return ret;
	}
	public boolean execL1406(Object var0, Object var1, boolean nondeterministic) {
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
L1406:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
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
									if (execL1403(var0,var1,var6,var10,var11,nondeterministic)) {
										ret = true;
										break L1406;
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
	public boolean execL1398(Object var0, boolean nondeterministic) {
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
L1398:
		{
			func = f14;
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
								if (execL1393(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L1398;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1393(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1393:
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
			break L1393;
		}
		return ret;
	}
	public boolean execL1394(Object var0, Object var1, boolean nondeterministic) {
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
L1394:
		{
			if (execL1396(var0, var1, nondeterministic)) {
				ret = true;
				break L1394;
			}
		}
		return ret;
	}
	public boolean execL1396(Object var0, Object var1, boolean nondeterministic) {
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
L1396:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
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
									if (execL1393(var0,var1,var6,var8,var9,nondeterministic)) {
										ret = true;
										break L1396;
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
	public boolean execL1388(Object var0, boolean nondeterministic) {
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
L1388:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1384(var0,var1,nondeterministic)) {
					ret = true;
					break L1388;
				}
			}
		}
		return ret;
	}
	public boolean execL1384(Object var0, Object var1, boolean nondeterministic) {
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
L1384:
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
			func = f14;
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
			break L1384;
		}
		return ret;
	}
	public boolean execL1385(Object var0, Object var1, boolean nondeterministic) {
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
L1385:
		{
			if (execL1387(var0, var1, nondeterministic)) {
				ret = true;
				break L1385;
			}
		}
		return ret;
	}
	public boolean execL1387(Object var0, Object var1, boolean nondeterministic) {
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
L1387:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL1384(var0,var1,nondeterministic)) {
						ret = true;
						break L1387;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1379(Object var0, boolean nondeterministic) {
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
L1379:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1374(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1379;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1374(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1374:
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
			break L1374;
		}
		return ret;
	}
	public boolean execL1375(Object var0, Object var1, boolean nondeterministic) {
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
L1375:
		{
			if (execL1377(var0, var1, nondeterministic)) {
				ret = true;
				break L1375;
			}
		}
		return ret;
	}
	public boolean execL1377(Object var0, Object var1, boolean nondeterministic) {
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
L1377:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1374(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1377;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1369(Object var0, boolean nondeterministic) {
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
L1369:
		{
			func = f10;
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
														if (!(!(f16).equals(((Atom)var6).getFunctor()))) {
															if (execL1359(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L1369;
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
	public boolean execL1359(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1359:
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
			func = f17;
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
			break L1359;
		}
		return ret;
	}
	public boolean execL1360(Object var0, Object var1, boolean nondeterministic) {
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
L1360:
		{
			if (execL1362(var0, var1, nondeterministic)) {
				ret = true;
				break L1360;
			}
			if (execL1364(var0, var1, nondeterministic)) {
				ret = true;
				break L1360;
			}
		}
		return ret;
	}
	public boolean execL1364(Object var0, Object var1, boolean nondeterministic) {
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
L1364:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
														if (!(!(f10).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL1359(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L1364;
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
	public boolean execL1362(Object var0, Object var1, boolean nondeterministic) {
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
L1362:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
															if (!(!(f16).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL1359(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L1362;
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
	public boolean execL1354(Object var0, boolean nondeterministic) {
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
L1354:
		{
			func = f12;
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
														if (!(!(f16).equals(((Atom)var6).getFunctor()))) {
															if (execL1344(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L1354;
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
	public boolean execL1344(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1344:
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
			break L1344;
		}
		return ret;
	}
	public boolean execL1345(Object var0, Object var1, boolean nondeterministic) {
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
L1345:
		{
			if (execL1347(var0, var1, nondeterministic)) {
				ret = true;
				break L1345;
			}
			if (execL1349(var0, var1, nondeterministic)) {
				ret = true;
				break L1345;
			}
		}
		return ret;
	}
	public boolean execL1349(Object var0, Object var1, boolean nondeterministic) {
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
L1349:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
														if (!(!(f12).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL1344(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L1349;
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
	public boolean execL1347(Object var0, Object var1, boolean nondeterministic) {
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
L1347:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
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
															if (!(!(f16).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL1344(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L1347;
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
	public boolean execL1339(Object var0, boolean nondeterministic) {
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
L1339:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1335(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1339;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1335(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1335:
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
			func = f15;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
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
			break L1335;
		}
		return ret;
	}
	public boolean execL1336(Object var0, Object var1, boolean nondeterministic) {
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
L1336:
		{
			if (execL1338(var0, var1, nondeterministic)) {
				ret = true;
				break L1336;
			}
		}
		return ret;
	}
	public boolean execL1338(Object var0, Object var1, boolean nondeterministic) {
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
L1338:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						if (execL1335(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1338;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1330(Object var0, boolean nondeterministic) {
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
L1330:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f20).equals(((Atom)var2).getFunctor()))) {
						if (execL1324(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1330;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1324(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1324:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			func = f21;
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
			break L1324;
		}
		return ret;
	}
	public boolean execL1325(Object var0, Object var1, boolean nondeterministic) {
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
L1325:
		{
			if (execL1327(var0, var1, nondeterministic)) {
				ret = true;
				break L1325;
			}
			if (execL1329(var0, var1, nondeterministic)) {
				ret = true;
				break L1325;
			}
		}
		return ret;
	}
	public boolean execL1329(Object var0, Object var1, boolean nondeterministic) {
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
L1329:
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
						if (!(!(f19).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL1324(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L1329;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1327(Object var0, Object var1, boolean nondeterministic) {
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
L1327:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f20).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL1324(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1327;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1319(Object var0, boolean nondeterministic) {
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
L1319:
		{
			func = f19;
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
							if (!(!(f20).equals(((Atom)var3).getFunctor()))) {
								if (execL1313(var0,var1,var3,nondeterministic)) {
									ret = true;
									break L1319;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1313(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1313:
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
			break L1313;
		}
		return ret;
	}
	public boolean execL1314(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1314:
		{
			if (execL1316(var0, var1, nondeterministic)) {
				ret = true;
				break L1314;
			}
			if (execL1318(var0, var1, nondeterministic)) {
				ret = true;
				break L1314;
			}
		}
		return ret;
	}
	public boolean execL1318(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1318:
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
						if (!(!(f19).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1313(var0,var4,var1,nondeterministic)) {
										ret = true;
										break L1318;
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
	public boolean execL1316(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1316:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
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
								if (!(!(f20).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									if (execL1313(var0,var1,var6,nondeterministic)) {
										ret = true;
										break L1316;
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
	public boolean execL1308(Object var0, boolean nondeterministic) {
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
L1308:
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
						if (execL1303(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1308;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1303(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1303:
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
			break L1303;
		}
		return ret;
	}
	public boolean execL1304(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1304:
		{
			if (execL1306(var0, var1, nondeterministic)) {
				ret = true;
				break L1304;
			}
		}
		return ret;
	}
	public boolean execL1306(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1306:
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
							if (execL1303(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1306;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1298(Object var0, boolean nondeterministic) {
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
L1298:
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
						if (execL1293(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1298;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1293(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1293:
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
			break L1293;
		}
		return ret;
	}
	public boolean execL1294(Object var0, Object var1, boolean nondeterministic) {
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
L1294:
		{
			if (execL1296(var0, var1, nondeterministic)) {
				ret = true;
				break L1294;
			}
		}
		return ret;
	}
	public boolean execL1296(Object var0, Object var1, boolean nondeterministic) {
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
L1296:
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
							if (execL1293(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1296;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1288(Object var0, boolean nondeterministic) {
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
L1288:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL1283(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1288;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1283(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1283:
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
			break L1283;
		}
		return ret;
	}
	public boolean execL1284(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1284:
		{
			if (execL1286(var0, var1, nondeterministic)) {
				ret = true;
				break L1284;
			}
		}
		return ret;
	}
	public boolean execL1286(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1286:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1283(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1286;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1278(Object var0, boolean nondeterministic) {
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
L1278:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1273(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1278;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1273(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1273:
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
			func = f17;
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
			break L1273;
		}
		return ret;
	}
	public boolean execL1274(Object var0, Object var1, boolean nondeterministic) {
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
L1274:
		{
			if (execL1276(var0, var1, nondeterministic)) {
				ret = true;
				break L1274;
			}
		}
		return ret;
	}
	public boolean execL1276(Object var0, Object var1, boolean nondeterministic) {
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
L1276:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1273(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1276;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1268(Object var0, boolean nondeterministic) {
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
L1268:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL1263(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1268;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1263(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1263:
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
			break L1263;
		}
		return ret;
	}
	public boolean execL1264(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1264:
		{
			if (execL1266(var0, var1, nondeterministic)) {
				ret = true;
				break L1264;
			}
		}
		return ret;
	}
	public boolean execL1266(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1266:
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
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f1).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1263(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1266;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1258(Object var0, boolean nondeterministic) {
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
L1258:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1253(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1258;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1253(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1253:
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
			break L1253;
		}
		return ret;
	}
	public boolean execL1254(Object var0, Object var1, boolean nondeterministic) {
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
L1254:
		{
			if (execL1256(var0, var1, nondeterministic)) {
				ret = true;
				break L1254;
			}
		}
		return ret;
	}
	public boolean execL1256(Object var0, Object var1, boolean nondeterministic) {
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
L1256:
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
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1253(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1256;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1248(Object var0, boolean nondeterministic) {
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
L1248:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						if (execL1243(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1248;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1243(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1243:
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
			func = f10;
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
			break L1243;
		}
		return ret;
	}
	public boolean execL1244(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1244:
		{
			if (execL1246(var0, var1, nondeterministic)) {
				ret = true;
				break L1244;
			}
		}
		return ret;
	}
	public boolean execL1246(Object var0, Object var1, boolean nondeterministic) {
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
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
		boolean ret = false;
L1246:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1243(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1246;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1238(Object var0, boolean nondeterministic) {
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
L1238:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1233(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1238;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1233(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1233:
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
			func = f12;
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
			break L1233;
		}
		return ret;
	}
	public boolean execL1234(Object var0, Object var1, boolean nondeterministic) {
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
L1234:
		{
			if (execL1236(var0, var1, nondeterministic)) {
				ret = true;
				break L1234;
			}
		}
		return ret;
	}
	public boolean execL1236(Object var0, Object var1, boolean nondeterministic) {
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
L1236:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1233(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1236;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1228(Object var0, boolean nondeterministic) {
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
L1228:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1224(var0,var1,nondeterministic)) {
					ret = true;
					break L1228;
				}
			}
		}
		return ret;
	}
	public boolean execL1224(Object var0, Object var1, boolean nondeterministic) {
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
L1224:
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
			break L1224;
		}
		return ret;
	}
	public boolean execL1225(Object var0, Object var1, boolean nondeterministic) {
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
L1225:
		{
			if (execL1227(var0, var1, nondeterministic)) {
				ret = true;
				break L1225;
			}
		}
		return ret;
	}
	public boolean execL1227(Object var0, Object var1, boolean nondeterministic) {
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
L1227:
		{
			if (!(!(f24).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL1224(var0,var1,nondeterministic)) {
						ret = true;
						break L1227;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f14 = new Functor("fold", 4, "list");
	private static final Functor f3 = new Functor("dist", 3, "list");
	private static final Functor f17 = new Functor("nil", 1, null);
	private static final Functor f9 = new Functor("flatten", 2, "list");
	private static final Functor f6 = new Functor("-", 3, null);
	private static final Functor f18 = new Functor("grep", 3, "list");
	private static final Functor f0 = new Functor("uniq", 2, "list");
	private static final Functor f1 = new Functor(".", 3, null);
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f23 = new Functor("is_empty", 3, "list");
	private static final Functor f4 = new IntegerFunctor(1);
	private static final Functor f16 = new Functor("grep_s0", 2, "list");
	private static final Functor f24 = new Functor("new", 1, "list");
	private static final Functor f15 = new Functor("map", 3, "list");
	private static final Functor f12 = new Functor("true", 1, null);
	private static final Functor f22 = new Functor("unshift", 3, "list");
	private static final Functor f7 = new Functor("append", 3, "list");
	private static final Functor f20 = new Functor("of_queue", 2, "list");
	private static final Functor f19 = new Functor("new", 3, "queue");
	private static final Functor f10 = new Functor("false", 1, null);
	private static final Functor f21 = new Functor("shift", 3, "list");
	private static final Functor f13 = new Functor("unfold", 6, "list");
	private static final Functor f5 = new Functor("choose_k", 3, "list");
	private static final Functor f11 = new Functor("unfold_s0", 7, "list");
	private static final Functor f8 = new IntegerFunctor(0);
	private Uniq uniq0 = new Uniq();
	private Uniq uniq1 = new Uniq();
}

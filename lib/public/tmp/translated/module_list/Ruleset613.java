package translated.module_list;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset613 extends Ruleset {
	private static final Ruleset613 theInstance = new Ruleset613();
	private Ruleset613() {}
	public static Ruleset613 getInstance() {
		return theInstance;
	}
	private int id = 613;
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
"('='(H, list.new) :- '='(H, '[]')), ('='(H, list.is_empty('[]', Return)) :- '='(H, '[]'), '='(Return, true)), ('='(H, list.is_empty('.'(Value, Next), Return)) :- '='(H, '.'(Value, Next)), '='(Return, false)), ('='(H, list.unshift('[]', Obj)) :- '='(H, '.'(Obj, '[]'))), ('='(H, list.unshift('.'(Value, Next), Obj)) :- '='(H, '.'(Obj, '.'(Value, Next)))), ('='(H, list.shift('[]', Return)) :- '='(H, '[]'), '='(Return, nil)), ('='(H, list.shift('.'(Value, Next), Return)) :- '='(H, Next), '='(Return, Value)), ('='(H, list.append('[]', B)) :- '='(H, B)), ('='(H, list.append('.'(Value, Next), B)) :- '='(H, '.'(Value, list.append(Next, B)))), ('='(H, list.of_queue(queue.new(Head, Head))) :- '='(H, '[]')), ('='(H, list.of_queue(queue.new(Head, Tail))) :- '='(H, '.'(El, list.of_queue(list.shift(queue.new(Head, Tail), El))))), ('='(H, list.grep(F, List)) :- unary(F) | '='(H, list.grep_s0(list.map(F, List)))), ('='(H, list.grep_s0('.'('.'(true, '.'(El, '[]')), CDR))) :- '='(H, '.'(El, list.grep_s0(CDR)))), ('='(H, list.grep_s0('.'('.'(false, '.'(El, '[]')), CDR))) :- '='(H, list.grep_s0(CDR)), nil(El)), ('='(H, list.grep_s0('[]')) :- '='(H, '[]')), ('='(H, list.map(F, List)) :- '='(H, list.fold(F, '[]', List))), ('='(H, list.fold(F, I, '[]')) :- unary(F), unary(I) | '='(H, I)), ('='(H, list.fold(F, I, '.'(CAR, CDR))) :- unary(F), unary(I) | '='(H, '.'('.'(F, '.'(CAR, '[]')), list.fold(F, I, CDR)))), ('='(H, list.unfold(P, F, G, Seed, Tailgen)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, list.unfold_s0(P, F, G, Seed, Tailgen, '.'(P, '.'(Seed, '[]'))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, true)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), list.unfold_s0(P, F, G, '.'(G, '.'(Seed, '[]')), Tailgen, '.'(P, '.'(Seed, '[]')))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, false)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), '[]'))), ('='(H, list.flatten('[]')) :- '='(H, '[]')), ('='(H, list.flatten('.'(CAR, CDR))) :- unary(CAR) | '='(H, '.'(CAR, list.flatten(CDR)))), ('='(H, list.flatten('.'('.'(CAR, CADR), CDR))) :- '='(H, list.flatten('.'(CAR, list.append(CADR, CDR))))), ('='(H, list.choose_k(L, K)) :- ground(L), '='(K, 0) | '='(H, '[]')), ('='(H, list.choose_k('[]', K)) :- int(K) | '='(H, '[]')), ('='(H, list.choose_k('.'(Hd, R), K)) :- '='(K, 1) | '='(H, '.'('.'(Hd, '[]'), list.choose_k(R, K)))), ('='(H, list.choose_k('.'(Hd, Tl), K)) :- '>'(K, 1), ground(Tl) | '='(H, list.append(list.dist(Hd, list.choose_k(Tl, '-'(K, 1))), list.choose_k(Tl, K)))), ('='(H, list.dist(A, '[]')) :- ground(A) | '='(H, '[]')), ('='(H, list.dist(A, '.'(Hd, Tl))) :- ground(A) | '='(H, '.'('.'(A, Hd), list.dist(A, Tl)))), ('='(H, list.uniq('[]')) :- '='(H, '[]')), ('='(H, list.uniq('.'(Hd, Tl))) :- uniq(Hd) | '='(H, '.'(Hd, list.uniq(Tl)))), ('='(H, list.uniq('.'(Hd, Tl))) :- not_uniq(Hd) | '='(H, list.uniq(Tl))), (list.use_guard :- [:/*inline_define*///#/*__UNITNAME__*/CustomGuardImpl.java/*__PACKAGE__*/import java.util.*;import runtime.*;import util.Util;public class /*__UNITNAME__*/CustomGuardImpl implements CustomGuard {\tpublic boolean run(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {//\t\tSystem.out.println(\"guardID \"+guardID);\t\tArrayList ary = (ArrayList)obj;//\t\tfor(int i=0;i<ary.size();i++) {//\t\t\tSystem.out.println(ary.get(i).getClass());//\t\t}//\t\tSystem.out.println(\"CustomGuardImpl \"+ary);\t\t\t\tif(guardID.equals(\"is_list\")) {\t\t\treturn Util.isList((Link)ary.get(0));\t\t}\t\telse if(guardID.equals(\"list_max\")) {\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\t\t\tboolean b = Util.listMax((Link)ary.get(0), a);\t\t\tary.set(1, a);\t\t\treturn b;\t\t}\t\telse if(guardID.equals(\"list_min\")) {\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\t\t\tary.set(1, a);\t\t\treturn b;\t\t}\t\telse if(guardID.equals(\"member\")) {\t\t\treturn Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\t\t}\t\telse if(guardID.equals(\"not_member\")) {\t\t\treturn !Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\t\t}\t\telse if(guardID.equals(\"test\")) {//\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\t\t\tAtom aa = mem.newAtom(new IntegerFunctor(777));\t\t\tLink link = (Link)ary.get(0);//\t\t\tlink.getAtom().remove();\t\t\tmem.inheritLink(aa, 0, link);\t\t\treturn true;\t\t}\t\tthrow new GuardNotFoundException();\t}}//#:]), ('='(H, list.split(List, X, Tail)) :- '='(X, 0) | '='(H, '[]'), '='(Tail, List)), ('='(H, list.split('[]', X, Tail)) :- int(X) | '='(H, '[]'), '='(Tail, '[]')), ('='(H, list.split('.'(Y, Z), X, Tail)) :- '>'(X, 0) | '='(H, '.'(Y, list.split(Z, '-'(X, 1), Tail))))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1126(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "new");
			return true;
		}
		if (execL1135(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "is_empty");
			return true;
		}
		if (execL1145(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "is_empty");
			return true;
		}
		if (execL1155(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "unshift");
			return true;
		}
		if (execL1165(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "unshift");
			return true;
		}
		if (execL1175(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "shift");
			return true;
		}
		if (execL1185(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "shift");
			return true;
		}
		if (execL1195(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "append");
			return true;
		}
		if (execL1205(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "append");
			return true;
		}
		if (execL1215(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "new");
			return true;
		}
		if (execL1226(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "new");
			return true;
		}
		if (execL1237(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "grep");
			return true;
		}
		if (execL1246(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "true");
			return true;
		}
		if (execL1261(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "false");
			return true;
		}
		if (execL1276(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "grep_s0");
			return true;
		}
		if (execL1286(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "map");
			return true;
		}
		if (execL1295(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "fold");
			return true;
		}
		if (execL1305(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "fold");
			return true;
		}
		if (execL1315(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "unfold");
			return true;
		}
		if (execL1324(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "true");
			return true;
		}
		if (execL1335(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "false");
			return true;
		}
		if (execL1346(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "flatten");
			return true;
		}
		if (execL1356(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "flatten");
			return true;
		}
		if (execL1366(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "flatten");
			return true;
		}
		if (execL1377(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "choose_k");
			return true;
		}
		if (execL1386(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "choose_k");
			return true;
		}
		if (execL1396(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "choose_k");
			return true;
		}
		if (execL1406(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "choose_k");
			return true;
		}
		if (execL1416(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "dist");
			return true;
		}
		if (execL1426(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "dist");
			return true;
		}
		if (execL1436(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "uniq");
			return true;
		}
		if (execL1446(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "uniq");
			return true;
		}
		if (execL1456(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "uniq");
			return true;
		}
		if (execL1466(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "use_guard");
			return true;
		}
		if (execL1475(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "split");
			return true;
		}
		if (execL1484(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "split");
			return true;
		}
		if (execL1494(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@613", "split");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1129(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "new");
			return true;
		}
		if (execL1139(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "is_empty");
			return true;
		}
		if (execL1149(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "is_empty");
			return true;
		}
		if (execL1159(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "unshift");
			return true;
		}
		if (execL1169(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "unshift");
			return true;
		}
		if (execL1179(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "shift");
			return true;
		}
		if (execL1189(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "shift");
			return true;
		}
		if (execL1199(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "append");
			return true;
		}
		if (execL1209(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "append");
			return true;
		}
		if (execL1220(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "new");
			return true;
		}
		if (execL1231(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "new");
			return true;
		}
		if (execL1240(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "grep");
			return true;
		}
		if (execL1255(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "true");
			return true;
		}
		if (execL1270(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "false");
			return true;
		}
		if (execL1280(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "grep_s0");
			return true;
		}
		if (execL1289(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "map");
			return true;
		}
		if (execL1299(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "fold");
			return true;
		}
		if (execL1309(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "fold");
			return true;
		}
		if (execL1318(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "unfold");
			return true;
		}
		if (execL1329(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "true");
			return true;
		}
		if (execL1340(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "false");
			return true;
		}
		if (execL1350(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "flatten");
			return true;
		}
		if (execL1360(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "flatten");
			return true;
		}
		if (execL1371(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "flatten");
			return true;
		}
		if (execL1380(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "choose_k");
			return true;
		}
		if (execL1390(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "choose_k");
			return true;
		}
		if (execL1400(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "choose_k");
			return true;
		}
		if (execL1410(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "choose_k");
			return true;
		}
		if (execL1420(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "dist");
			return true;
		}
		if (execL1430(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "dist");
			return true;
		}
		if (execL1440(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "uniq");
			return true;
		}
		if (execL1450(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "uniq");
			return true;
		}
		if (execL1460(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "uniq");
			return true;
		}
		if (execL1469(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "use_guard");
			return true;
		}
		if (execL1478(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "split");
			return true;
		}
		if (execL1488(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "split");
			return true;
		}
		if (execL1498(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@613", "split");
			return true;
		}
		return result;
	}
	public boolean execL1498(Object var0, boolean nondeterministic) {
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
L1498:
		{
			var3 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						x = ((IntegerFunctor)((Atom)var4).getFunctor()).intValue();
						y = ((IntegerFunctor)((Atom)var3).getFunctor()).intValue();	
						if (!(!(x > y))) {
							if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
								if (execL1493(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L1498;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1493(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1493:
		{
			link = ((Atom)var2).getArg(1);
			var10 = link;
			link = ((Atom)var1).getArg(3);
			var13 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f3;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var1), 1 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 0,
				(Link)var10 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 3,
				((Atom)var2), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var13 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
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
			break L1493;
		}
		return ret;
	}
	public boolean execL1494(Object var0, Object var1, boolean nondeterministic) {
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
L1494:
		{
			if (execL1496(var0, var1, nondeterministic)) {
				ret = true;
				break L1494;
			}
		}
		return ret;
	}
	public boolean execL1496(Object var0, Object var1, boolean nondeterministic) {
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
L1496:
		{
			var10 = new Atom(null, f0);
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
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(1);
						var11 = link.getAtom();
						if (!(!(((Atom)var11).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var11).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var10).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(2);
									var9 = link;
									if (execL1493(var0,var1,var6,var10,var11,nondeterministic)) {
										ret = true;
										break L1496;
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
	public boolean execL1488(Object var0, boolean nondeterministic) {
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
L1488:
		{
			func = f1;
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
						if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
							if (execL1483(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1488;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1483(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1483:
		{
			link = ((Atom)var1).getArg(3);
			var6 = link;
			link = ((Atom)var1).getArg(2);
			var7 = link;
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
			func = f5;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var7 );
			ret = true;
			break L1483;
		}
		return ret;
	}
	public boolean execL1484(Object var0, Object var1, boolean nondeterministic) {
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
L1484:
		{
			if (execL1486(var0, var1, nondeterministic)) {
				ret = true;
				break L1484;
			}
		}
		return ret;
	}
	public boolean execL1486(Object var0, Object var1, boolean nondeterministic) {
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
L1486:
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
						link = ((Atom)var1).getArg(1);
						var8 = link.getAtom();
						if (!(!(((Atom)var8).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f5).equals(((Atom)var6).getFunctor()))) {
								link = ((Atom)var6).getArg(0);
								var7 = link;
								if (execL1483(var0,var1,var6,var8,nondeterministic)) {
									ret = true;
									break L1486;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1478(Object var0, boolean nondeterministic) {
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
L1478:
		{
			var2 = new Atom(null, f0);
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!((Atom)var2).getFunctor().equals(((Atom)var3).getFunctor()))) {
					if (execL1474(var0,var1,var2,var3,nondeterministic)) {
						ret = true;
						break L1478;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1474(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1474:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var1), 2,
				((Atom)var1), 0 );
			link = ((Atom)var1).getArg(3);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			ret = true;
			break L1474;
		}
		return ret;
	}
	public boolean execL1475(Object var0, Object var1, boolean nondeterministic) {
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
L1475:
		{
			if (execL1477(var0, var1, nondeterministic)) {
				ret = true;
				break L1475;
			}
		}
		return ret;
	}
	public boolean execL1477(Object var0, Object var1, boolean nondeterministic) {
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
L1477:
		{
			var6 = new Atom(null, f0);
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
					link = ((Atom)var1).getArg(1);
					var7 = link.getAtom();
					if (!(!((Atom)var6).getFunctor().equals(((Atom)var7).getFunctor()))) {
						if (execL1474(var0,var1,var6,var7,nondeterministic)) {
							ret = true;
							break L1477;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1469(Object var0, boolean nondeterministic) {
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
L1469:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use_guard", "L1465",var0,var1});
				} else if (execL1465(var0,var1,nondeterministic)) {
					ret = true;
					break L1469;
				}
			}
		}
		return ret;
	}
	public boolean execL1465(Object var0, Object var1, boolean nondeterministic) {
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
L1465:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f7;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L1465;
		}
		return ret;
	}
	public boolean execL1466(Object var0, Object var1, boolean nondeterministic) {
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
L1466:
		{
			if (execL1468(var0, var1, nondeterministic)) {
				ret = true;
				break L1466;
			}
		}
		return ret;
	}
	public boolean execL1468(Object var0, Object var1, boolean nondeterministic) {
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
L1468:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use_guard", "L1465",var0,var1});
					} else if (execL1465(var0,var1,nondeterministic)) {
						ret = true;
						break L1468;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1460(Object var0, boolean nondeterministic) {
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
L1460:
		{
			var4 = new HashSet();
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					((Set)var4).add(((Atom)var1));
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link;
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							boolean goAhead = uniq0.check(new Link[] {(Link)var3							});
							goAhead = !goAhead;
							if(goAhead) {
								if (execL1455(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L1460;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1455(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1455:
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
			break L1455;
		}
		return ret;
	}
	public boolean execL1456(Object var0, Object var1, boolean nondeterministic) {
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
L1456:
		{
			if (execL1458(var0, var1, nondeterministic)) {
				ret = true;
				break L1456;
			}
		}
		return ret;
	}
	public boolean execL1458(Object var0, Object var1, boolean nondeterministic) {
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
L1458:
		{
			var9 = new HashSet();
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						((Set)var9).add(((Atom)var1));
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1455(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L1458;
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
	public boolean execL1450(Object var0, boolean nondeterministic) {
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
L1450:
		{
			var4 = new HashSet();
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					((Set)var4).add(((Atom)var1));
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link;
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							boolean goAhead = uniq1.check(new Link[] {(Link)var3							});
							if(goAhead) {
								if (execL1445(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L1450;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1445(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1445:
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
			break L1445;
		}
		return ret;
	}
	public boolean execL1446(Object var0, Object var1, boolean nondeterministic) {
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
L1446:
		{
			if (execL1448(var0, var1, nondeterministic)) {
				ret = true;
				break L1446;
			}
		}
		return ret;
	}
	public boolean execL1448(Object var0, Object var1, boolean nondeterministic) {
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
L1448:
		{
			var9 = new HashSet();
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						((Set)var9).add(((Atom)var1));
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1445(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L1448;
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
	public boolean execL1440(Object var0, boolean nondeterministic) {
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
L1440:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1435(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1440;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1435(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1435:
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
			break L1435;
		}
		return ret;
	}
	public boolean execL1436(Object var0, Object var1, boolean nondeterministic) {
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
L1436:
		{
			if (execL1438(var0, var1, nondeterministic)) {
				ret = true;
				break L1436;
			}
		}
		return ret;
	}
	public boolean execL1438(Object var0, Object var1, boolean nondeterministic) {
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
L1438:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f5).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL1435(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1438;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1430(Object var0, boolean nondeterministic) {
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
L1430:
		{
			var4 = new HashSet();
			func = f9;
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
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL1425(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L1430;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1425(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1425:
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
			func = f2;
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
			break L1425;
		}
		return ret;
	}
	public boolean execL1426(Object var0, Object var1, boolean nondeterministic) {
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
L1426:
		{
			if (execL1428(var0, var1, nondeterministic)) {
				ret = true;
				break L1426;
			}
		}
		return ret;
	}
	public boolean execL1428(Object var0, Object var1, boolean nondeterministic) {
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
L1428:
		{
			var10 = new HashSet();
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
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
								if (execL1425(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L1428;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1420(Object var0, boolean nondeterministic) {
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
L1420:
		{
			var4 = new HashSet();
			func = f9;
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
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL1415(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L1420;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1415(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1415:
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
			break L1415;
		}
		return ret;
	}
	public boolean execL1416(Object var0, Object var1, boolean nondeterministic) {
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
L1416:
		{
			if (execL1418(var0, var1, nondeterministic)) {
				ret = true;
				break L1416;
			}
		}
		return ret;
	}
	public boolean execL1418(Object var0, Object var1, boolean nondeterministic) {
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
L1418:
		{
			var8 = new HashSet();
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							((Set)var8).add(((Atom)var5));
							isground_ret = ((Link)var7).isGround(((Set)var8));
							if (!(isground_ret == -1)) {
								var9 = new IntegerFunctor(isground_ret);
								if (execL1415(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L1418;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1410(Object var0, boolean nondeterministic) {
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
L1410:
		{
			var6 = new HashSet();
			var4 = new Atom(null, f3);
			func = f10;
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
							if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
								link = ((Atom)var2).getArg(1);
								var3 = link;
								((Set)var6).add(((Atom)var2));
								isground_ret = ((Link)var3).isGround(((Set)var6));
								if (!(isground_ret == -1)) {
									var7 = new IntegerFunctor(isground_ret);
									if (execL1405(var0,var1,var2,var4,var5,nondeterministic)) {
										ret = true;
										break L1410;
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
	public boolean execL1405(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1405:
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
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f10;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f9;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
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
			break L1405;
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
L1406:
		{
			if (execL1408(var0, var1, nondeterministic)) {
				ret = true;
				break L1406;
			}
		}
		return ret;
	}
	public boolean execL1408(Object var0, Object var1, boolean nondeterministic) {
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
L1408:
		{
			var12 = new HashSet();
			var10 = new Atom(null, f3);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
								if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
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
										if (execL1405(var0,var1,var5,var10,var11,nondeterministic)) {
											ret = true;
											break L1408;
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
	public boolean execL1400(Object var0, boolean nondeterministic) {
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
L1400:
		{
			var3 = new Atom(null, f3);
			func = f10;
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
						if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
							if (execL1395(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L1400;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1395(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1395:
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
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
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
			break L1395;
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
L1396:
		{
			if (execL1398(var0, var1, nondeterministic)) {
				ret = true;
				break L1396;
			}
		}
		return ret;
	}
	public boolean execL1398(Object var0, Object var1, boolean nondeterministic) {
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
L1398:
		{
			var9 = new Atom(null, f3);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
							if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								if (execL1395(var0,var1,var5,var9,var10,nondeterministic)) {
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
	public boolean execL1390(Object var0, boolean nondeterministic) {
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
L1390:
		{
			func = f10;
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
						if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
							if (execL1385(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1390;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1385(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1385:
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
			break L1385;
		}
		return ret;
	}
	public boolean execL1386(Object var0, Object var1, boolean nondeterministic) {
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
L1386:
		{
			if (execL1388(var0, var1, nondeterministic)) {
				ret = true;
				break L1386;
			}
		}
		return ret;
	}
	public boolean execL1388(Object var0, Object var1, boolean nondeterministic) {
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
L1388:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
							if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								if (execL1385(var0,var1,var5,var7,nondeterministic)) {
									ret = true;
									break L1388;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1380(Object var0, boolean nondeterministic) {
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
L1380:
		{
			var5 = new HashSet();
			var3 = new Atom(null, f0);
			func = f10;
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
						if (execL1376(var0,var1,var3,var4,nondeterministic)) {
							ret = true;
							break L1380;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1376(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1376:
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
			func = f5;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var6 );
			ret = true;
			break L1376;
		}
		return ret;
	}
	public boolean execL1377(Object var0, Object var1, boolean nondeterministic) {
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
L1377:
		{
			if (execL1379(var0, var1, nondeterministic)) {
				ret = true;
				break L1377;
			}
		}
		return ret;
	}
	public boolean execL1379(Object var0, Object var1, boolean nondeterministic) {
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
L1379:
		{
			var8 = new HashSet();
			var6 = new Atom(null, f0);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1376(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L1379;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1371(Object var0, boolean nondeterministic) {
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
L1371:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								if (execL1365(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1371;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1365(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1365:
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
			func = f11;
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
			break L1365;
		}
		return ret;
	}
	public boolean execL1366(Object var0, Object var1, boolean nondeterministic) {
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
L1366:
		{
			if (execL1368(var0, var1, nondeterministic)) {
				ret = true;
				break L1366;
			}
		}
		return ret;
	}
	public boolean execL1368(Object var0, Object var1, boolean nondeterministic) {
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
L1368:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									if (execL1365(var0,var1,var8,var4,nondeterministic)) {
										ret = true;
										break L1368;
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
	public boolean execL1360(Object var0, boolean nondeterministic) {
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
L1360:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1355(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1360;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1355(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1355:
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
			break L1355;
		}
		return ret;
	}
	public boolean execL1356(Object var0, Object var1, boolean nondeterministic) {
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
L1356:
		{
			if (execL1358(var0, var1, nondeterministic)) {
				ret = true;
				break L1356;
			}
		}
		return ret;
	}
	public boolean execL1358(Object var0, Object var1, boolean nondeterministic) {
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
L1358:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
								if (execL1355(var0,var1,var4,var8,nondeterministic)) {
									ret = true;
									break L1358;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1350(Object var0, boolean nondeterministic) {
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
L1350:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1345(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1350;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1345(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1345:
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
			break L1345;
		}
		return ret;
	}
	public boolean execL1346(Object var0, Object var1, boolean nondeterministic) {
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
L1346:
		{
			if (execL1348(var0, var1, nondeterministic)) {
				ret = true;
				break L1346;
			}
		}
		return ret;
	}
	public boolean execL1348(Object var0, Object var1, boolean nondeterministic) {
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
L1348:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f5).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL1345(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1348;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1340(Object var0, boolean nondeterministic) {
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
L1340:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f14).equals(((Atom)var2).getFunctor()))) {
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
											if (execL1334(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L1340;
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
	public boolean execL1334(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L1334:
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
			func = f5;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
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
			break L1334;
		}
		return ret;
	}
	public boolean execL1335(Object var0, Object var1, boolean nondeterministic) {
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
L1335:
		{
			if (execL1337(var0, var1, nondeterministic)) {
				ret = true;
				break L1335;
			}
			if (execL1339(var0, var1, nondeterministic)) {
				ret = true;
				break L1335;
			}
		}
		return ret;
	}
	public boolean execL1339(Object var0, Object var1, boolean nondeterministic) {
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
L1339:
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
											if (!(!(f13).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL1334(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1339;
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
	public boolean execL1337(Object var0, Object var1, boolean nondeterministic) {
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
L1337:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f14).equals(((Atom)var3).getFunctor()))) {
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
												if (execL1334(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1337;
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
	public boolean execL1329(Object var0, boolean nondeterministic) {
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
L1329:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f14).equals(((Atom)var2).getFunctor()))) {
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
											if (execL1323(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L1329;
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
	public boolean execL1323(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L1323:
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
			func = f5;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var25 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var26 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
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
			break L1323;
		}
		return ret;
	}
	public boolean execL1324(Object var0, Object var1, boolean nondeterministic) {
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
L1324:
		{
			if (execL1326(var0, var1, nondeterministic)) {
				ret = true;
				break L1324;
			}
			if (execL1328(var0, var1, nondeterministic)) {
				ret = true;
				break L1324;
			}
		}
		return ret;
	}
	public boolean execL1328(Object var0, Object var1, boolean nondeterministic) {
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
L1328:
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
											if (!(!(f15).equals(((Atom)var9).getFunctor()))) {
												link = ((Atom)var9).getArg(0);
												var10 = link;
												if (execL1323(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1328;
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
	public boolean execL1326(Object var0, Object var1, boolean nondeterministic) {
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
L1326:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f14).equals(((Atom)var3).getFunctor()))) {
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
												if (execL1323(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L1326;
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
	public boolean execL1318(Object var0, boolean nondeterministic) {
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
L1318:
		{
			func = f16;
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
									if (execL1314(var0,var1,var2,var3,var4,var5,var6,nondeterministic)) {
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
	public boolean execL1314(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1314:
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
			func = f5;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
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
			break L1314;
		}
		return ret;
	}
	public boolean execL1315(Object var0, Object var1, boolean nondeterministic) {
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
L1315:
		{
			if (execL1317(var0, var1, nondeterministic)) {
				ret = true;
				break L1315;
			}
		}
		return ret;
	}
	public boolean execL1317(Object var0, Object var1, boolean nondeterministic) {
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
L1317:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
										if (execL1314(var0,var1,var8,var9,var10,var11,var12,nondeterministic)) {
											ret = true;
											break L1317;
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
	public boolean execL1309(Object var0, boolean nondeterministic) {
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
L1309:
		{
			func = f17;
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
							if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
								if (execL1304(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L1309;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1304(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1304:
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
			func = f5;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
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
			break L1304;
		}
		return ret;
	}
	public boolean execL1305(Object var0, Object var1, boolean nondeterministic) {
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
L1305:
		{
			if (execL1307(var0, var1, nondeterministic)) {
				ret = true;
				break L1305;
			}
		}
		return ret;
	}
	public boolean execL1307(Object var0, Object var1, boolean nondeterministic) {
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
L1307:
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
								if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(2);
									var9 = link;
									if (execL1304(var0,var1,var6,var10,var11,nondeterministic)) {
										ret = true;
										break L1307;
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
	public boolean execL1299(Object var0, boolean nondeterministic) {
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
L1299:
		{
			func = f17;
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
							if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
								if (execL1294(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L1299;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1294(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1294:
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
			break L1294;
		}
		return ret;
	}
	public boolean execL1295(Object var0, Object var1, boolean nondeterministic) {
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
L1295:
		{
			if (execL1297(var0, var1, nondeterministic)) {
				ret = true;
				break L1295;
			}
		}
		return ret;
	}
	public boolean execL1297(Object var0, Object var1, boolean nondeterministic) {
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
L1297:
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
								if (!(!(f5).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									if (execL1294(var0,var1,var6,var8,var9,nondeterministic)) {
										ret = true;
										break L1297;
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
	public boolean execL1289(Object var0, boolean nondeterministic) {
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
L1289:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1285(var0,var1,nondeterministic)) {
					ret = true;
					break L1289;
				}
			}
		}
		return ret;
	}
	public boolean execL1285(Object var0, Object var1, boolean nondeterministic) {
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
L1285:
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
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
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
			break L1285;
		}
		return ret;
	}
	public boolean execL1286(Object var0, Object var1, boolean nondeterministic) {
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
L1286:
		{
			if (execL1288(var0, var1, nondeterministic)) {
				ret = true;
				break L1286;
			}
		}
		return ret;
	}
	public boolean execL1288(Object var0, Object var1, boolean nondeterministic) {
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
L1288:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL1285(var0,var1,nondeterministic)) {
						ret = true;
						break L1288;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1280(Object var0, boolean nondeterministic) {
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
L1280:
		{
			func = f19;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1275(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1280;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1275(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1275:
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
			break L1275;
		}
		return ret;
	}
	public boolean execL1276(Object var0, Object var1, boolean nondeterministic) {
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
L1276:
		{
			if (execL1278(var0, var1, nondeterministic)) {
				ret = true;
				break L1276;
			}
		}
		return ret;
	}
	public boolean execL1278(Object var0, Object var1, boolean nondeterministic) {
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
L1278:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f5).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL1275(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L1278;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1270(Object var0, boolean nondeterministic) {
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
L1270:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f19).equals(((Atom)var6).getFunctor()))) {
															if (execL1260(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L1270;
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
	public boolean execL1260(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1260:
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
			func = f20;
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
			break L1260;
		}
		return ret;
	}
	public boolean execL1261(Object var0, Object var1, boolean nondeterministic) {
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
L1261:
		{
			if (execL1263(var0, var1, nondeterministic)) {
				ret = true;
				break L1261;
			}
			if (execL1265(var0, var1, nondeterministic)) {
				ret = true;
				break L1261;
			}
		}
		return ret;
	}
	public boolean execL1265(Object var0, Object var1, boolean nondeterministic) {
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
L1265:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
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
											if (!(!(f2).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f5).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f13).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL1260(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L1265;
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
	public boolean execL1263(Object var0, Object var1, boolean nondeterministic) {
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
L1263:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
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
									if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
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
												if (!(!(f5).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f2).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f19).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL1260(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L1263;
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
	public boolean execL1255(Object var0, boolean nondeterministic) {
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
L1255:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f19).equals(((Atom)var6).getFunctor()))) {
															if (execL1245(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L1255;
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
	public boolean execL1245(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1245:
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
			break L1245;
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
L1246:
		{
			if (execL1248(var0, var1, nondeterministic)) {
				ret = true;
				break L1246;
			}
			if (execL1250(var0, var1, nondeterministic)) {
				ret = true;
				break L1246;
			}
		}
		return ret;
	}
	public boolean execL1250(Object var0, Object var1, boolean nondeterministic) {
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
L1250:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
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
											if (!(!(f2).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f5).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f15).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL1245(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
																ret = true;
																break L1250;
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
	public boolean execL1248(Object var0, Object var1, boolean nondeterministic) {
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
L1248:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
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
									if (!(!(f2).equals(((Atom)var7).getFunctor()))) {
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
												if (!(!(f5).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f2).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f19).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL1245(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
																	ret = true;
																	break L1248;
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
	public boolean execL1240(Object var0, boolean nondeterministic) {
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
L1240:
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
					if (execL1236(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1240;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1236(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1236:
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
			func = f18;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
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
			break L1236;
		}
		return ret;
	}
	public boolean execL1237(Object var0, Object var1, boolean nondeterministic) {
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
L1237:
		{
			if (execL1239(var0, var1, nondeterministic)) {
				ret = true;
				break L1237;
			}
		}
		return ret;
	}
	public boolean execL1239(Object var0, Object var1, boolean nondeterministic) {
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
L1239:
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
						if (execL1236(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1239;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1231(Object var0, boolean nondeterministic) {
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
L1231:
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
						if (execL1225(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1231;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1225(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1225:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			func = f24;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
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
			break L1225;
		}
		return ret;
	}
	public boolean execL1226(Object var0, Object var1, boolean nondeterministic) {
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
L1226:
		{
			if (execL1228(var0, var1, nondeterministic)) {
				ret = true;
				break L1226;
			}
			if (execL1230(var0, var1, nondeterministic)) {
				ret = true;
				break L1226;
			}
		}
		return ret;
	}
	public boolean execL1230(Object var0, Object var1, boolean nondeterministic) {
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
L1230:
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
							if (execL1225(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L1230;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1228(Object var0, Object var1, boolean nondeterministic) {
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
L1228:
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
							if (execL1225(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1228;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1220(Object var0, boolean nondeterministic) {
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
L1220:
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
								if (execL1214(var0,var1,var3,nondeterministic)) {
									ret = true;
									break L1220;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1214(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1214:
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
			func = f5;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			ret = true;
			break L1214;
		}
		return ret;
	}
	public boolean execL1215(Object var0, Object var1, boolean nondeterministic) {
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
L1215:
		{
			if (execL1217(var0, var1, nondeterministic)) {
				ret = true;
				break L1215;
			}
			if (execL1219(var0, var1, nondeterministic)) {
				ret = true;
				break L1215;
			}
		}
		return ret;
	}
	public boolean execL1219(Object var0, Object var1, boolean nondeterministic) {
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
L1219:
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
									if (execL1214(var0,var4,var1,nondeterministic)) {
										ret = true;
										break L1219;
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
	public boolean execL1217(Object var0, Object var1, boolean nondeterministic) {
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
L1217:
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
									if (execL1214(var0,var1,var6,nondeterministic)) {
										ret = true;
										break L1217;
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
	public boolean execL1209(Object var0, boolean nondeterministic) {
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
L1209:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1204(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1209;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1204(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1204:
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
			break L1204;
		}
		return ret;
	}
	public boolean execL1205(Object var0, Object var1, boolean nondeterministic) {
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
L1205:
		{
			if (execL1207(var0, var1, nondeterministic)) {
				ret = true;
				break L1205;
			}
		}
		return ret;
	}
	public boolean execL1207(Object var0, Object var1, boolean nondeterministic) {
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
L1207:
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1204(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1207;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1199(Object var0, boolean nondeterministic) {
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
L1199:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1194(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1199;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1194(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1194:
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
			break L1194;
		}
		return ret;
	}
	public boolean execL1195(Object var0, Object var1, boolean nondeterministic) {
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
L1195:
		{
			if (execL1197(var0, var1, nondeterministic)) {
				ret = true;
				break L1195;
			}
		}
		return ret;
	}
	public boolean execL1197(Object var0, Object var1, boolean nondeterministic) {
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
L1197:
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
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1194(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1197;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1189(Object var0, boolean nondeterministic) {
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
L1189:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1184(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1189;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1184(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1184:
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
			break L1184;
		}
		return ret;
	}
	public boolean execL1185(Object var0, Object var1, boolean nondeterministic) {
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
L1185:
		{
			if (execL1187(var0, var1, nondeterministic)) {
				ret = true;
				break L1185;
			}
		}
		return ret;
	}
	public boolean execL1187(Object var0, Object var1, boolean nondeterministic) {
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
L1187:
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1184(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1187;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1179(Object var0, boolean nondeterministic) {
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
L1179:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1174(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1179;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1174(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1174:
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
			func = f20;
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
			break L1174;
		}
		return ret;
	}
	public boolean execL1175(Object var0, Object var1, boolean nondeterministic) {
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
L1175:
		{
			if (execL1177(var0, var1, nondeterministic)) {
				ret = true;
				break L1175;
			}
		}
		return ret;
	}
	public boolean execL1177(Object var0, Object var1, boolean nondeterministic) {
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
L1177:
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
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1174(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1177;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1169(Object var0, boolean nondeterministic) {
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
L1169:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1164(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1169;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1164(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1164:
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
			func = f2;
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
			break L1164;
		}
		return ret;
	}
	public boolean execL1165(Object var0, Object var1, boolean nondeterministic) {
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
L1165:
		{
			if (execL1167(var0, var1, nondeterministic)) {
				ret = true;
				break L1165;
			}
		}
		return ret;
	}
	public boolean execL1167(Object var0, Object var1, boolean nondeterministic) {
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
L1167:
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1164(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1167;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1159(Object var0, boolean nondeterministic) {
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
L1159:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1154(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1159;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1154(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1154:
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
			func = f2;
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
			break L1154;
		}
		return ret;
	}
	public boolean execL1155(Object var0, Object var1, boolean nondeterministic) {
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
L1155:
		{
			if (execL1157(var0, var1, nondeterministic)) {
				ret = true;
				break L1155;
			}
		}
		return ret;
	}
	public boolean execL1157(Object var0, Object var1, boolean nondeterministic) {
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
L1157:
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
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1154(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1157;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1149(Object var0, boolean nondeterministic) {
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
L1149:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						if (execL1144(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1149;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1144(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1144:
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
			func = f13;
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
			break L1144;
		}
		return ret;
	}
	public boolean execL1145(Object var0, Object var1, boolean nondeterministic) {
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
L1145:
		{
			if (execL1147(var0, var1, nondeterministic)) {
				ret = true;
				break L1145;
			}
		}
		return ret;
	}
	public boolean execL1147(Object var0, Object var1, boolean nondeterministic) {
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
L1147:
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
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL1144(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1147;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1139(Object var0, boolean nondeterministic) {
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
L1139:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1134(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1139;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1134(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1134:
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
			func = f15;
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
			break L1134;
		}
		return ret;
	}
	public boolean execL1135(Object var0, Object var1, boolean nondeterministic) {
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
L1135:
		{
			if (execL1137(var0, var1, nondeterministic)) {
				ret = true;
				break L1135;
			}
		}
		return ret;
	}
	public boolean execL1137(Object var0, Object var1, boolean nondeterministic) {
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
L1137:
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
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1134(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1137;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1129(Object var0, boolean nondeterministic) {
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
L1129:
		{
			func = f27;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1125(var0,var1,nondeterministic)) {
					ret = true;
					break L1129;
				}
			}
		}
		return ret;
	}
	public boolean execL1125(Object var0, Object var1, boolean nondeterministic) {
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
L1125:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			ret = true;
			break L1125;
		}
		return ret;
	}
	public boolean execL1126(Object var0, Object var1, boolean nondeterministic) {
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
L1126:
		{
			if (execL1128(var0, var1, nondeterministic)) {
				ret = true;
				break L1126;
			}
		}
		return ret;
	}
	public boolean execL1128(Object var0, Object var1, boolean nondeterministic) {
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
L1128:
		{
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL1125(var0,var1,nondeterministic)) {
						ret = true;
						break L1128;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f6 = new Functor("use_guard", 0, "list");
	private static final Functor f17 = new Functor("fold", 4, "list");
	private static final Functor f9 = new Functor("dist", 3, "list");
	private static final Functor f20 = new Functor("nil", 1, null);
	private static final Functor f12 = new Functor("flatten", 2, "list");
	private static final Functor f4 = new Functor("-", 3, null);
	private static final Functor f1 = new Functor("split", 4, "list");
	private static final Functor f21 = new Functor("grep", 3, "list");
	private static final Functor f8 = new Functor("uniq", 2, "list");
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f5 = new Functor("[]", 1, null);
	private static final Functor f26 = new Functor("is_empty", 3, "list");
	private static final Functor f3 = new IntegerFunctor(1);
	private static final Functor f19 = new Functor("grep_s0", 2, "list");
	private static final Functor f27 = new Functor("new", 1, "list");
	private static final Functor f18 = new Functor("map", 3, "list");
	private static final Functor f15 = new Functor("true", 1, null);
	private static final Functor f25 = new Functor("unshift", 3, "list");
	private static final Functor f11 = new Functor("append", 3, "list");
	private static final Functor f23 = new Functor("of_queue", 2, "list");
	private static final Functor f22 = new Functor("new", 3, "queue");
	private static final Functor f7 = new Functor("/*inline_define*/\r\n//#/*__UNITNAME__*/CustomGuardImpl.java\r\n/*__PACKAGE__*/\r\nimport java.util.*;\r\nimport runtime.*;\r\nimport util.Util;\r\npublic class /*__UNITNAME__*/CustomGuardImpl implements CustomGuard {\r\n\tpublic boolean run(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {\r\n//\t\tSystem.out.println(\"guardID \"+guardID);\r\n\t\tArrayList ary = (ArrayList)obj;\r\n//\t\tfor(int i=0;i<ary.size();i++) {\r\n//\t\t\tSystem.out.println(ary.get(i).getClass());\r\n//\t\t}\r\n//\t\tSystem.out.println(\"CustomGuardImpl \"+ary);\r\n\t\t\r\n\t\tif(guardID.equals(\"is_list\")) {\r\n\t\t\treturn Util.isList((Link)ary.get(0));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"list_max\")) {\r\n\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\r\n\t\t\tboolean b = Util.listMax((Link)ary.get(0), a);\r\n\t\t\tary.set(1, a);\r\n\t\t\treturn b;\r\n\t\t}\r\n\t\telse if(guardID.equals(\"list_min\")) {\r\n\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\r\n\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\r\n\t\t\tary.set(1, a);\r\n\t\t\treturn b;\r\n\t\t}\r\n\t\telse if(guardID.equals(\"member\")) {\r\n\t\t\treturn Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"not_member\")) {\r\n\t\t\treturn !Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"test\")) {\r\n//\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\r\n\t\t\tAtom aa = mem.newAtom(new IntegerFunctor(777));\r\n\t\t\tLink link = (Link)ary.get(0);\r\n//\t\t\tlink.getAtom().remove();\r\n\t\t\tmem.inheritLink(aa, 0, link);\r\n\t\t\treturn true;\r\n\t\t}\r\n\t\tthrow new GuardNotFoundException();\r\n\t}\r\n}\r\n//#\r\n", 0, null);
	private static final Functor f13 = new Functor("false", 1, null);
	private static final Functor f24 = new Functor("shift", 3, "list");
	private static final Functor f16 = new Functor("unfold", 6, "list");
	private static final Functor f10 = new Functor("choose_k", 3, "list");
	private static final Functor f14 = new Functor("unfold_s0", 7, "list");
	private static final Functor f0 = new IntegerFunctor(0);
	private Uniq uniq0 = new Uniq();
	private Uniq uniq1 = new Uniq();
}

package translated.module_list;
import runtime.*;
import java.util.*;
import java.io.*;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":list" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@list" + id;
	}
	private String encodedRuleset = 
"('='(H, list.new) :- '='(H, '[]')), ('='(H, list.is_empty('[]', Return)) :- '='(H, '[]'), '='(Return, true)), ('='(H, list.is_empty('.'(Value, Next), Return)) :- '='(H, '.'(Value, Next)), '='(Return, false)), ('='(H, list.unshift('[]', Obj)) :- '='(H, '.'(Obj, '[]'))), ('='(H, list.unshift('.'(Value, Next), Obj)) :- '='(H, '.'(Obj, '.'(Value, Next)))), ('='(H, list.shift('[]', Return)) :- '='(H, '[]'), '='(Return, nil)), ('='(H, list.shift('.'(Value, Next), Return)) :- '='(H, Next), '='(Return, Value)), ('='(H, list.append('[]', B)) :- '='(H, B)), ('='(H, list.append('.'(Value, Next), B)) :- '='(H, '.'(Value, list.append(Next, B)))), ('='(H, list.of_queue(queue.new(Head, Head))) :- '='(H, '[]')), ('='(H, list.of_queue(queue.new(Head, Tail))) :- '='(H, '.'(El, list.of_queue(list.shift(queue.new(Head, Tail), El))))), ('='(H, list.grep(F, List)) :- unary(F) | '='(H, list.grep_s0(list.map(F, List)))), ('='(H, list.grep_s0('.'('.'(true, '.'(El, '[]')), CDR))) :- '='(H, '.'(El, list.grep_s0(CDR)))), ('='(H, list.grep_s0('.'('.'(false, '.'(El, '[]')), CDR))) :- '='(H, list.grep_s0(CDR)), nil(El)), ('='(H, list.grep_s0('[]')) :- '='(H, '[]')), ('='(H, list.map(F, List)) :- '='(H, list.fold(F, '[]', List))), ('='(H, list.fold(F, I, '[]')) :- unary(F), unary(I) | '='(H, I)), ('='(H, list.fold(F, I, '.'(CAR, CDR))) :- unary(F), unary(I) | '='(H, '.'('.'(F, '.'(CAR, '[]')), list.fold(F, I, CDR)))), ('='(H, list.unfold(P, F, G, Seed, Tailgen)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, list.unfold_s0(P, F, G, Seed, Tailgen, '.'(P, '.'(Seed, '[]'))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, true)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), list.unfold_s0(P, F, G, '.'(G, '.'(Seed, '[]')), Tailgen, '.'(P, '.'(Seed, '[]')))))), ('='(H, list.unfold_s0(P, F, G, Seed, Tailgen, false)) :- unary(P), unary(F), unary(G), unary(Seed), unary(Tailgen) | '='(H, '.'('.'(F, '.'(Seed, '[]')), '[]'))), ('='(H, list.flatten('[]')) :- '='(H, '[]')), ('='(H, list.flatten('.'(CAR, CDR))) :- unary(CAR) | '='(H, '.'(CAR, list.flatten(CDR)))), ('='(H, list.flatten('.'('.'(CAR, CADR), CDR))) :- '='(H, list.flatten('.'(CAR, list.append(CADR, CDR))))), ('='(H, list.choose_k(L, K)) :- ground(L), '='(K, 0) | '='(H, '[]')), ('='(H, list.choose_k('[]', K)) :- int(K) | '='(H, '[]')), ('='(H, list.choose_k('.'(Hd, R), K)) :- '='(K, 1) | '='(H, '.'('.'(Hd, '[]'), list.choose_k(R, K)))), ('='(H, list.choose_k('.'(Hd, Tl), K)) :- '>'(K, 1), ground(Tl) | '='(H, list.append(list.dist(Hd, list.choose_k(Tl, '-'(K, 1))), list.choose_k(Tl, K)))), ('='(H, list.dist(A, '[]')) :- ground(A) | '='(H, '[]')), ('='(H, list.dist(A, '.'(Hd, Tl))) :- ground(A) | '='(H, '.'('.'(A, Hd), list.dist(A, Tl)))), ('='(H, list.uniq('[]')) :- '='(H, '[]')), ('='(H, list.uniq('.'(Hd, Tl))) :- uniq(Hd) | '='(H, '.'(Hd, list.uniq(Tl)))), ('='(H, list.uniq('.'(Hd, Tl))) :- not_uniq(Hd) | '='(H, list.uniq(Tl))), (list.use_guard :- [:/*inline_define*/import util.Util;class CustomGuardImpl implements CustomGuard {\tpublic boolean run(String guardID, Membrane mem, Object obj) {//\t\tSystem.out.println(\"guardID \"+guardID);\t\tArrayList ary = (ArrayList)obj;//\t\tfor(int i=0;i<ary.size();i++) {//\t\t\tSystem.out.println(ary.get(i).getClass());//\t\t}//\t\tSystem.out.println(\"CustomGuardImpl \"+ary);\t\t\t\tif(guardID.equals(\"is_list\")) {\t\t\treturn Util.isList((Link)ary.get(0));\t\t}\t\telse if(guardID.equals(\"list_max\")) {\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\t\t\tboolean b = Util.listMax((Link)ary.get(0), a);\t\t\tary.set(1, a);\t\t\treturn b;\t\t}\t\telse if(guardID.equals(\"list_min\")) {\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\t\t\tary.set(1, a);\t\t\treturn b;\t\t}\t\telse if(guardID.equals(\"member\")) {\t\t\treturn Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\t\t}\t\telse if(guardID.equals(\"not_member\")) {\t\t\treturn !Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\t\t}\t\telse if(guardID.equals(\"test\")) {//\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\t\t\tAtom aa = mem.newAtom(new IntegerFunctor(777));\t\t\tLink link = (Link)ary.get(0);//\t\t\tlink.getAtom().remove();\t\t\tmem.inheritLink(aa, 0, link);\t\t\treturn true;\t\t}\t\treturn false;\t}}:])";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL352(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "new");
			return true;
		}
		if (execL361(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "is_empty");
			return true;
		}
		if (execL371(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "is_empty");
			return true;
		}
		if (execL381(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "unshift");
			return true;
		}
		if (execL391(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "unshift");
			return true;
		}
		if (execL401(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "shift");
			return true;
		}
		if (execL411(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "shift");
			return true;
		}
		if (execL421(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "append");
			return true;
		}
		if (execL431(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "append");
			return true;
		}
		if (execL441(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "new");
			return true;
		}
		if (execL452(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "new");
			return true;
		}
		if (execL463(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "grep");
			return true;
		}
		if (execL472(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL487(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL502(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "grep_s0");
			return true;
		}
		if (execL512(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "map");
			return true;
		}
		if (execL521(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "fold");
			return true;
		}
		if (execL531(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "fold");
			return true;
		}
		if (execL541(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "unfold");
			return true;
		}
		if (execL550(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "true");
			return true;
		}
		if (execL561(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "false");
			return true;
		}
		if (execL572(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "flatten");
			return true;
		}
		if (execL582(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "flatten");
			return true;
		}
		if (execL592(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "flatten");
			return true;
		}
		if (execL603(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "choose_k");
			return true;
		}
		if (execL612(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "choose_k");
			return true;
		}
		if (execL622(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "choose_k");
			return true;
		}
		if (execL632(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "choose_k");
			return true;
		}
		if (execL642(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "dist");
			return true;
		}
		if (execL652(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "dist");
			return true;
		}
		if (execL662(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "uniq");
			return true;
		}
		if (execL672(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "uniq");
			return true;
		}
		if (execL682(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "uniq");
			return true;
		}
		if (execL692(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "use_guard");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL355(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "new");
			return true;
		}
		if (execL365(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "is_empty");
			return true;
		}
		if (execL375(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "is_empty");
			return true;
		}
		if (execL385(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "unshift");
			return true;
		}
		if (execL395(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "unshift");
			return true;
		}
		if (execL405(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "shift");
			return true;
		}
		if (execL415(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "shift");
			return true;
		}
		if (execL425(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "append");
			return true;
		}
		if (execL435(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "append");
			return true;
		}
		if (execL446(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "new");
			return true;
		}
		if (execL457(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "new");
			return true;
		}
		if (execL466(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "grep");
			return true;
		}
		if (execL481(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL496(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL506(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "grep_s0");
			return true;
		}
		if (execL515(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "map");
			return true;
		}
		if (execL525(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "fold");
			return true;
		}
		if (execL535(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "fold");
			return true;
		}
		if (execL544(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "unfold");
			return true;
		}
		if (execL555(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "true");
			return true;
		}
		if (execL566(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "false");
			return true;
		}
		if (execL576(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "flatten");
			return true;
		}
		if (execL586(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "flatten");
			return true;
		}
		if (execL597(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "flatten");
			return true;
		}
		if (execL606(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "choose_k");
			return true;
		}
		if (execL616(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "choose_k");
			return true;
		}
		if (execL626(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "choose_k");
			return true;
		}
		if (execL636(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "choose_k");
			return true;
		}
		if (execL646(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "dist");
			return true;
		}
		if (execL656(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "dist");
			return true;
		}
		if (execL666(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "uniq");
			return true;
		}
		if (execL676(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "uniq");
			return true;
		}
		if (execL686(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "uniq");
			return true;
		}
		if (execL695(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "use_guard");
			return true;
		}
		return result;
	}
	public boolean execL695(Object var0, boolean nondeterministic) {
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
L695:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use_guard", "L691",var0,var1});
				} else if (execL691(var0,var1,nondeterministic)) {
					ret = true;
					break L695;
				}
			}
		}
		return ret;
	}
	public boolean execL691(Object var0, Object var1, boolean nondeterministic) {
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
L691:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L691;
		}
		return ret;
	}
	public boolean execL692(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
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
		Atom atom;
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
L694:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use_guard", "L691",var0,var1});
					} else if (execL691(var0,var1,nondeterministic)) {
						ret = true;
						break L694;
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
L686:
		{
			var4 = new HashSet();
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					((Set)var4).add(((Atom)var1));
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link;
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							boolean goAhead = uniq0.check(new Link[] {(Link)var3							});
							goAhead = !goAhead;
							if(goAhead) {
								if (execL681(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L686;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL681(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L681:
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
L684:
		{
			var9 = new HashSet();
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						((Set)var9).add(((Atom)var1));
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
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
									if (execL681(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L684;
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
	public boolean execL676(Object var0, boolean nondeterministic) {
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
L676:
		{
			var4 = new HashSet();
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					((Set)var4).add(((Atom)var1));
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link;
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							boolean goAhead = uniq1.check(new Link[] {(Link)var3							});
							if(goAhead) {
								if (execL671(var0,var1,var2,nondeterministic)) {
									ret = true;
									break L676;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL671(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L671:
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
			break L671;
		}
		return ret;
	}
	public boolean execL672(Object var0, Object var1, boolean nondeterministic) {
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
L672:
		{
			if (execL674(var0, var1, nondeterministic)) {
				ret = true;
				break L672;
			}
		}
		return ret;
	}
	public boolean execL674(Object var0, Object var1, boolean nondeterministic) {
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
L674:
		{
			var9 = new HashSet();
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						((Set)var9).add(((Atom)var1));
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
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
									if (execL671(var0,var1,var4,nondeterministic)) {
										ret = true;
										break L674;
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
	public boolean execL666(Object var0, boolean nondeterministic) {
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
L666:
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
						if (execL661(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L666;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL661(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L661:
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
			break L661;
		}
		return ret;
	}
	public boolean execL662(Object var0, Object var1, boolean nondeterministic) {
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
L662:
		{
			if (execL664(var0, var1, nondeterministic)) {
				ret = true;
				break L662;
			}
		}
		return ret;
	}
	public boolean execL664(Object var0, Object var1, boolean nondeterministic) {
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
L664:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL661(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L664;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL656(Object var0, boolean nondeterministic) {
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
L656:
		{
			var4 = new HashSet();
			func = f5;
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
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL651(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L656;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL651(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L651:
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
			func = f3;
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
			break L651;
		}
		return ret;
	}
	public boolean execL652(Object var0, Object var1, boolean nondeterministic) {
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
L652:
		{
			if (execL654(var0, var1, nondeterministic)) {
				ret = true;
				break L652;
			}
		}
		return ret;
	}
	public boolean execL654(Object var0, Object var1, boolean nondeterministic) {
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
L654:
		{
			var10 = new HashSet();
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
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
								if (execL651(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L654;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL646(Object var0, boolean nondeterministic) {
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
L646:
		{
			var4 = new HashSet();
			func = f5;
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
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						((Set)var4).add(((Atom)var2));
						isground_ret = ((Link)var3).isGround(((Set)var4));
						if (!(isground_ret == -1)) {
							var5 = new IntegerFunctor(isground_ret);
							if (execL641(var0,var1,var2,nondeterministic)) {
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
	public boolean execL641(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L641:
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
		Atom atom;
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
L642:
		{
			if (execL644(var0, var1, nondeterministic)) {
				ret = true;
				break L642;
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
L644:
		{
			var8 = new HashSet();
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							((Set)var8).add(((Atom)var5));
							isground_ret = ((Link)var7).isGround(((Set)var8));
							if (!(isground_ret == -1)) {
								var9 = new IntegerFunctor(isground_ret);
								if (execL641(var0,var1,var5,nondeterministic)) {
									ret = true;
									break L644;
								}
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
L636:
		{
			var6 = new HashSet();
			var4 = new Atom(null, f6);
			func = f7;
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
							if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
								link = ((Atom)var2).getArg(1);
								var3 = link;
								((Set)var6).add(((Atom)var2));
								isground_ret = ((Link)var3).isGround(((Set)var6));
								if (!(isground_ret == -1)) {
									var7 = new IntegerFunctor(isground_ret);
									if (execL631(var0,var1,var2,var4,var5,nondeterministic)) {
										ret = true;
										break L636;
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
	public boolean execL631(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L631:
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
			func = f6;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f7;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f9;
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
			break L631;
		}
		return ret;
	}
	public boolean execL632(Object var0, Object var1, boolean nondeterministic) {
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
L632:
		{
			if (execL634(var0, var1, nondeterministic)) {
				ret = true;
				break L632;
			}
		}
		return ret;
	}
	public boolean execL634(Object var0, Object var1, boolean nondeterministic) {
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
L634:
		{
			var12 = new HashSet();
			var10 = new Atom(null, f6);
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
						link = ((Atom)var1).getArg(1);
						var11 = link.getAtom();
						((Set)var12).add(((Atom)var1));
						if (!(!(((Atom)var11).getFunctor() instanceof IntegerFunctor))) {
							x = ((IntegerFunctor)((Atom)var11).getFunctor()).intValue();
							y = ((IntegerFunctor)((Atom)var10).getFunctor()).intValue();	
							if (!(!(x > y))) {
								if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
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
										if (execL631(var0,var1,var5,var10,var11,nondeterministic)) {
											ret = true;
											break L634;
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
	public boolean execL626(Object var0, boolean nondeterministic) {
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
L626:
		{
			var3 = new Atom(null, f6);
			func = f7;
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
						if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
							if (execL621(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L626;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL621(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L621:
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
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
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
			break L621;
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
		Atom atom;
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
L622:
		{
			if (execL624(var0, var1, nondeterministic)) {
				ret = true;
				break L622;
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
		Atom atom;
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
L624:
		{
			var9 = new Atom(null, f6);
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
						link = ((Atom)var1).getArg(1);
						var10 = link.getAtom();
						if (!(!((Atom)var9).getFunctor().equals(((Atom)var10).getFunctor()))) {
							if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								if (execL621(var0,var1,var5,var9,var10,nondeterministic)) {
									ret = true;
									break L624;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL616(Object var0, boolean nondeterministic) {
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
L616:
		{
			func = f7;
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
						if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
							if (execL611(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L616;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL611(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L611:
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
			break L611;
		}
		return ret;
	}
	public boolean execL612(Object var0, Object var1, boolean nondeterministic) {
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
L612:
		{
			if (execL614(var0, var1, nondeterministic)) {
				ret = true;
				break L612;
			}
		}
		return ret;
	}
	public boolean execL614(Object var0, Object var1, boolean nondeterministic) {
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
L614:
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
						link = ((Atom)var1).getArg(1);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
							if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								if (execL611(var0,var1,var5,var7,nondeterministic)) {
									ret = true;
									break L614;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL606(Object var0, boolean nondeterministic) {
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
L606:
		{
			var5 = new HashSet();
			var3 = new Atom(null, f10);
			func = f7;
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
						if (execL602(var0,var1,var3,var4,nondeterministic)) {
							ret = true;
							break L606;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL602(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L602:
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
			func = f4;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var6 );
			ret = true;
			break L602;
		}
		return ret;
	}
	public boolean execL603(Object var0, Object var1, boolean nondeterministic) {
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
L603:
		{
			if (execL605(var0, var1, nondeterministic)) {
				ret = true;
				break L603;
			}
		}
		return ret;
	}
	public boolean execL605(Object var0, Object var1, boolean nondeterministic) {
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
L605:
		{
			var8 = new HashSet();
			var6 = new Atom(null, f10);
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
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
							if (execL602(var0,var1,var6,var7,nondeterministic)) {
								ret = true;
								break L605;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL597(Object var0, boolean nondeterministic) {
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
L597:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
								if (execL591(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L597;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL591(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L591:
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
			func = f9;
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
			break L591;
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
L592:
		{
			if (execL594(var0, var1, nondeterministic)) {
				ret = true;
				break L592;
			}
		}
		return ret;
	}
	public boolean execL594(Object var0, Object var1, boolean nondeterministic) {
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
L594:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f3).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									if (execL591(var0,var1,var8,var4,nondeterministic)) {
										ret = true;
										break L594;
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
	public boolean execL586(Object var0, boolean nondeterministic) {
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
L586:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL581(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L586;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL581(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L581:
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
			break L581;
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
		Atom atom;
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
L582:
		{
			if (execL584(var0, var1, nondeterministic)) {
				ret = true;
				break L582;
			}
		}
		return ret;
	}
	public boolean execL584(Object var0, Object var1, boolean nondeterministic) {
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
L584:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
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
								if (execL581(var0,var1,var4,var8,nondeterministic)) {
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
	public boolean execL576(Object var0, boolean nondeterministic) {
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
L576:
		{
			func = f11;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL571(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L576;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL571(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L571:
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
			break L571;
		}
		return ret;
	}
	public boolean execL572(Object var0, Object var1, boolean nondeterministic) {
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
L572:
		{
			if (execL574(var0, var1, nondeterministic)) {
				ret = true;
				break L572;
			}
		}
		return ret;
	}
	public boolean execL574(Object var0, Object var1, boolean nondeterministic) {
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
L574:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL571(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L574;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL566(Object var0, boolean nondeterministic) {
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
L566:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f13).equals(((Atom)var2).getFunctor()))) {
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
											if (execL560(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
												ret = true;
												break L566;
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
	public boolean execL560(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L560:
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
			func = f4;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var13 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
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
			break L560;
		}
		return ret;
	}
	public boolean execL561(Object var0, Object var1, boolean nondeterministic) {
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
L561:
		{
			if (execL563(var0, var1, nondeterministic)) {
				ret = true;
				break L561;
			}
			if (execL565(var0, var1, nondeterministic)) {
				ret = true;
				break L561;
			}
		}
		return ret;
	}
	public boolean execL565(Object var0, Object var1, boolean nondeterministic) {
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
L565:
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
												if (execL560(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L565;
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
	public boolean execL563(Object var0, Object var1, boolean nondeterministic) {
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
L563:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f13).equals(((Atom)var3).getFunctor()))) {
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
												if (execL560(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L563;
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
	public boolean execL555(Object var0, boolean nondeterministic) {
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
L555:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 5)) {
					var2 = link.getAtom();
					if (!(!(f13).equals(((Atom)var2).getFunctor()))) {
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
											if (execL549(var0,var1,var2,var3,var4,var5,var6,var7,nondeterministic)) {
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
		return ret;
	}
	public boolean execL549(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, boolean nondeterministic) {
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
L549:
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
			func = f4;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var21 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f4;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var25 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var26 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
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
			break L549;
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
		Atom atom;
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
L550:
		{
			if (execL552(var0, var1, nondeterministic)) {
				ret = true;
				break L550;
			}
			if (execL554(var0, var1, nondeterministic)) {
				ret = true;
				break L550;
			}
		}
		return ret;
	}
	public boolean execL554(Object var0, Object var1, boolean nondeterministic) {
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
L554:
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
												if (execL549(var0,var9,var1,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L554;
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
	public boolean execL552(Object var0, Object var1, boolean nondeterministic) {
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
L552:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 5)) {
						var3 = link.getAtom();
						if (!(!(f13).equals(((Atom)var3).getFunctor()))) {
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
												if (execL549(var0,var1,var3,var11,var12,var13,var14,var15,nondeterministic)) {
													ret = true;
													break L552;
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
	public boolean execL544(Object var0, boolean nondeterministic) {
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
L544:
		{
			func = f15;
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
									if (execL540(var0,var1,var2,var3,var4,var5,var6,nondeterministic)) {
										ret = true;
										break L544;
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
	public boolean execL540(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L540:
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
			func = f4;
			var14 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var15 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var16 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
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
			break L540;
		}
		return ret;
	}
	public boolean execL541(Object var0, Object var1, boolean nondeterministic) {
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
L541:
		{
			if (execL543(var0, var1, nondeterministic)) {
				ret = true;
				break L541;
			}
		}
		return ret;
	}
	public boolean execL543(Object var0, Object var1, boolean nondeterministic) {
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
L543:
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
										if (execL540(var0,var1,var8,var9,var10,var11,var12,nondeterministic)) {
											ret = true;
											break L543;
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
	public boolean execL535(Object var0, boolean nondeterministic) {
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
L535:
		{
			func = f16;
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
							if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
								if (execL530(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L535;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL530(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L530:
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
			func = f4;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
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
		Atom atom;
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
			if (execL533(var0, var1, nondeterministic)) {
				ret = true;
				break L531;
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
		Atom atom;
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
L533:
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
								if (!(!(f3).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									link = ((Atom)var6).getArg(2);
									var9 = link;
									if (execL530(var0,var1,var6,var10,var11,nondeterministic)) {
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
		return ret;
	}
	public boolean execL525(Object var0, boolean nondeterministic) {
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
L525:
		{
			func = f16;
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
							if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
								if (execL520(var0,var1,var2,var3,var4,nondeterministic)) {
									ret = true;
									break L525;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL520(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L520:
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
			break L520;
		}
		return ret;
	}
	public boolean execL521(Object var0, Object var1, boolean nondeterministic) {
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
L521:
		{
			if (execL523(var0, var1, nondeterministic)) {
				ret = true;
				break L521;
			}
		}
		return ret;
	}
	public boolean execL523(Object var0, Object var1, boolean nondeterministic) {
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
L523:
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
								if (!(!(f4).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									if (execL520(var0,var1,var6,var8,var9,nondeterministic)) {
										ret = true;
										break L523;
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
	public boolean execL515(Object var0, boolean nondeterministic) {
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
L515:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL511(var0,var1,nondeterministic)) {
					ret = true;
					break L515;
				}
			}
		}
		return ret;
	}
	public boolean execL511(Object var0, Object var1, boolean nondeterministic) {
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
L511:
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
			func = f4;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
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
			break L511;
		}
		return ret;
	}
	public boolean execL512(Object var0, Object var1, boolean nondeterministic) {
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
L512:
		{
			if (execL514(var0, var1, nondeterministic)) {
				ret = true;
				break L512;
			}
		}
		return ret;
	}
	public boolean execL514(Object var0, Object var1, boolean nondeterministic) {
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
L514:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL511(var0,var1,nondeterministic)) {
						ret = true;
						break L514;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL506(Object var0, boolean nondeterministic) {
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
L506:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL501(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L506;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL501(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L501:
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
			break L501;
		}
		return ret;
	}
	public boolean execL502(Object var0, Object var1, boolean nondeterministic) {
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
L502:
		{
			if (execL504(var0, var1, nondeterministic)) {
				ret = true;
				break L502;
			}
		}
		return ret;
	}
	public boolean execL504(Object var0, Object var1, boolean nondeterministic) {
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
L504:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							if (execL501(var0,var1,var4,nondeterministic)) {
								ret = true;
								break L504;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL496(Object var0, boolean nondeterministic) {
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
L496:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f18).equals(((Atom)var6).getFunctor()))) {
															if (execL486(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L496;
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
	public boolean execL486(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L486:
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
			func = f19;
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
			break L486;
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
L487:
		{
			if (execL489(var0, var1, nondeterministic)) {
				ret = true;
				break L487;
			}
			if (execL491(var0, var1, nondeterministic)) {
				ret = true;
				break L487;
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
L491:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f3).equals(((Atom)var8).getFunctor()))) {
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
											if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f4).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f12).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL486(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
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
L489:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
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
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var11 = link.getAtom();
									if (!(!(f3).equals(((Atom)var7).getFunctor()))) {
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
												if (!(!(f4).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f3).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f18).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL486(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
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
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL481(Object var0, boolean nondeterministic) {
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
L481:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(1);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							link = ((Atom)var2).getArg(2);
							if (!(link.getPos() != 0)) {
								var4 = link.getAtom();
								if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
									if (!(((Atom)var4) == ((Atom)var3))) {
										link = ((Atom)var3).getArg(1);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
												if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
													link = ((Atom)var4).getArg(2);
													if (!(link.getPos() != 0)) {
														var6 = link.getAtom();
														if (!(!(f18).equals(((Atom)var6).getFunctor()))) {
															if (execL471(var0,var1,var6,var5,var3,var2,var4,nondeterministic)) {
																ret = true;
																break L481;
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
	public boolean execL471(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L471:
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
L476:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 2)) {
								var8 = link.getAtom();
								if (!(!(f3).equals(((Atom)var8).getFunctor()))) {
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
											if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												link = ((Atom)var14).getArg(1);
												var16 = link;
												link = ((Atom)var14).getArg(2);
												var17 = link;
												link = ((Atom)var14).getArg(1);
												if (!(link.getPos() != 0)) {
													var18 = link.getAtom();
													if (!(!(f4).equals(((Atom)var18).getFunctor()))) {
														link = ((Atom)var18).getArg(0);
														var19 = link;
														if (!(!(f14).equals(((Atom)var12).getFunctor()))) {
															link = ((Atom)var12).getArg(0);
															var13 = link;
															if (execL471(var0,var12,var1,var18,var14,var8,var4,nondeterministic)) {
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
L474:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
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
							if (!(link.getPos() != 2)) {
								var7 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var11 = link.getAtom();
									if (!(!(f3).equals(((Atom)var7).getFunctor()))) {
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
												if (!(!(f4).equals(((Atom)var15).getFunctor()))) {
													link = ((Atom)var15).getArg(0);
													var16 = link;
													if (!(!(f3).equals(((Atom)var11).getFunctor()))) {
														link = ((Atom)var11).getArg(0);
														var12 = link;
														link = ((Atom)var11).getArg(1);
														var13 = link;
														link = ((Atom)var11).getArg(2);
														var14 = link;
														link = ((Atom)var11).getArg(2);
														if (!(link.getPos() != 0)) {
															var17 = link.getAtom();
															if (!(!(f18).equals(((Atom)var17).getFunctor()))) {
																link = ((Atom)var17).getArg(0);
																var18 = link;
																link = ((Atom)var17).getArg(1);
																var19 = link;
																if (execL471(var0,var1,var17,var15,var7,var3,var11,nondeterministic)) {
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
		Atom atom;
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
L466:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL462(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L466;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL462(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L462:
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
			func = f17;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f18;
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
			break L462;
		}
		return ret;
	}
	public boolean execL463(Object var0, Object var1, boolean nondeterministic) {
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
L463:
		{
			if (execL465(var0, var1, nondeterministic)) {
				ret = true;
				break L463;
			}
		}
		return ret;
	}
	public boolean execL465(Object var0, Object var1, boolean nondeterministic) {
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
L465:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
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
						if (execL462(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L465;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL457(Object var0, boolean nondeterministic) {
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
L457:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f22).equals(((Atom)var2).getFunctor()))) {
						if (execL451(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L457;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL451(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L451:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			func = f23;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
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
			break L451;
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
		Atom atom;
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
L452:
		{
			if (execL454(var0, var1, nondeterministic)) {
				ret = true;
				break L452;
			}
			if (execL456(var0, var1, nondeterministic)) {
				ret = true;
				break L452;
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, Object var1, boolean nondeterministic) {
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
L456:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f21).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							if (execL451(var0,var4,var1,nondeterministic)) {
								ret = true;
								break L456;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL454(Object var0, Object var1, boolean nondeterministic) {
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
L454:
		{
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f22).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							if (execL451(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L454;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL446(Object var0, boolean nondeterministic) {
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
L446:
		{
			func = f21;
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
							if (!(!(f22).equals(((Atom)var3).getFunctor()))) {
								if (execL440(var0,var1,var3,nondeterministic)) {
									ret = true;
									break L446;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL440(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L440:
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
			func = f4;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			ret = true;
			break L440;
		}
		return ret;
	}
	public boolean execL441(Object var0, Object var1, boolean nondeterministic) {
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
L441:
		{
			if (execL443(var0, var1, nondeterministic)) {
				ret = true;
				break L441;
			}
			if (execL445(var0, var1, nondeterministic)) {
				ret = true;
				break L441;
			}
		}
		return ret;
	}
	public boolean execL445(Object var0, Object var1, boolean nondeterministic) {
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
L445:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f21).equals(((Atom)var4).getFunctor()))) {
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
									if (execL440(var0,var4,var1,nondeterministic)) {
										ret = true;
										break L445;
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
	public boolean execL443(Object var0, Object var1, boolean nondeterministic) {
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
L443:
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
					if (!(link.getPos() != 1)) {
						var5 = link.getAtom();
						if (!(((Atom)var5) != ((Atom)var1))) {
							link = ((Atom)var1).getArg(2);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(f22).equals(((Atom)var6).getFunctor()))) {
									link = ((Atom)var6).getArg(0);
									var7 = link;
									link = ((Atom)var6).getArg(1);
									var8 = link;
									if (execL440(var0,var1,var6,nondeterministic)) {
										ret = true;
										break L443;
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
	public boolean execL435(Object var0, boolean nondeterministic) {
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
L435:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL430(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L435;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL430(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L430:
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
			break L430;
		}
		return ret;
	}
	public boolean execL431(Object var0, Object var1, boolean nondeterministic) {
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
L431:
		{
			if (execL433(var0, var1, nondeterministic)) {
				ret = true;
				break L431;
			}
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
L433:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL430(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L433;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL425(Object var0, boolean nondeterministic) {
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
L425:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL420(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L425;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL420(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
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
L420:
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
			break L420;
		}
		return ret;
	}
	public boolean execL421(Object var0, Object var1, boolean nondeterministic) {
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
L421:
		{
			if (execL423(var0, var1, nondeterministic)) {
				ret = true;
				break L421;
			}
		}
		return ret;
	}
	public boolean execL423(Object var0, Object var1, boolean nondeterministic) {
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
L423:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
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
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL420(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L423;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL415(Object var0, boolean nondeterministic) {
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
L415:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL410(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L415;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL410(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Atom atom;
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
L410:
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
			break L410;
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
L413:
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
						if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL410(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L413;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL405(Object var0, boolean nondeterministic) {
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
L405:
		{
			func = f23;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL400(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L405;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL400(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L400:
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
			func = f19;
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
			break L400;
		}
		return ret;
	}
	public boolean execL401(Object var0, Object var1, boolean nondeterministic) {
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
L401:
		{
			if (execL403(var0, var1, nondeterministic)) {
				ret = true;
				break L401;
			}
		}
		return ret;
	}
	public boolean execL403(Object var0, Object var1, boolean nondeterministic) {
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
L403:
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
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL400(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L403;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL395(Object var0, boolean nondeterministic) {
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
L395:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL390(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L395;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL390(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L390:
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
			func = f3;
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
			break L390;
		}
		return ret;
	}
	public boolean execL391(Object var0, Object var1, boolean nondeterministic) {
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
L391:
		{
			if (execL393(var0, var1, nondeterministic)) {
				ret = true;
				break L391;
			}
		}
		return ret;
	}
	public boolean execL393(Object var0, Object var1, boolean nondeterministic) {
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
L393:
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
						if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL390(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L393;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL385(Object var0, boolean nondeterministic) {
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
L385:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL380(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L385;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL380(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L380:
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
			func = f3;
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
			break L380;
		}
		return ret;
	}
	public boolean execL381(Object var0, Object var1, boolean nondeterministic) {
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
L381:
		{
			if (execL383(var0, var1, nondeterministic)) {
				ret = true;
				break L381;
			}
		}
		return ret;
	}
	public boolean execL383(Object var0, Object var1, boolean nondeterministic) {
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
L383:
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
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL380(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L383;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL375(Object var0, boolean nondeterministic) {
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
L375:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL370(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L375;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL370(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L370:
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
			func = f12;
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
			break L370;
		}
		return ret;
	}
	public boolean execL371(Object var0, Object var1, boolean nondeterministic) {
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
L371:
		{
			if (execL373(var0, var1, nondeterministic)) {
				ret = true;
				break L371;
			}
		}
		return ret;
	}
	public boolean execL373(Object var0, Object var1, boolean nondeterministic) {
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
L373:
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
						if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							if (execL370(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L373;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL365(Object var0, boolean nondeterministic) {
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
L365:
		{
			func = f25;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f4).equals(((Atom)var2).getFunctor()))) {
						if (execL360(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L365;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL360(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L360:
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
			func = f14;
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
			break L360;
		}
		return ret;
	}
	public boolean execL361(Object var0, Object var1, boolean nondeterministic) {
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
L361:
		{
			if (execL363(var0, var1, nondeterministic)) {
				ret = true;
				break L361;
			}
		}
		return ret;
	}
	public boolean execL363(Object var0, Object var1, boolean nondeterministic) {
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
L363:
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
						if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL360(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L363;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL355(Object var0, boolean nondeterministic) {
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
L355:
		{
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL351(var0,var1,nondeterministic)) {
					ret = true;
					break L355;
				}
			}
		}
		return ret;
	}
	public boolean execL351(Object var0, Object var1, boolean nondeterministic) {
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
L351:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f4;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			ret = true;
			break L351;
		}
		return ret;
	}
	public boolean execL352(Object var0, Object var1, boolean nondeterministic) {
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
L352:
		{
			if (execL354(var0, var1, nondeterministic)) {
				ret = true;
				break L352;
			}
		}
		return ret;
	}
	public boolean execL354(Object var0, Object var1, boolean nondeterministic) {
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
L354:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL351(var0,var1,nondeterministic)) {
						ret = true;
						break L354;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f0 = new Functor("use_guard", 0, "list");
	private static final Functor f16 = new Functor("fold", 4, "list");
	private static final Functor f5 = new Functor("dist", 3, "list");
	private static final Functor f19 = new Functor("nil", 1, null);
	private static final Functor f11 = new Functor("flatten", 2, "list");
	private static final Functor f8 = new Functor("-", 3, null);
	private static final Functor f20 = new Functor("grep", 3, "list");
	private static final Functor f2 = new Functor("uniq", 2, "list");
	private static final Functor f3 = new Functor(".", 3, null);
	private static final Functor f4 = new Functor("[]", 1, null);
	private static final Functor f1 = new Functor("/*inline_define*/\r\nimport util.Util;\r\nclass CustomGuardImpl implements CustomGuard {\r\n\tpublic boolean run(String guardID, Membrane mem, Object obj) {\r\n//\t\tSystem.out.println(\"guardID \"+guardID);\r\n\t\tArrayList ary = (ArrayList)obj;\r\n//\t\tfor(int i=0;i<ary.size();i++) {\r\n//\t\t\tSystem.out.println(ary.get(i).getClass());\r\n//\t\t}\r\n//\t\tSystem.out.println(\"CustomGuardImpl \"+ary);\r\n\t\t\r\n\t\tif(guardID.equals(\"is_list\")) {\r\n\t\t\treturn Util.isList((Link)ary.get(0));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"list_max\")) {\r\n\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\r\n\t\t\tboolean b = Util.listMax((Link)ary.get(0), a);\r\n\t\t\tary.set(1, a);\r\n\t\t\treturn b;\r\n\t\t}\r\n\t\telse if(guardID.equals(\"list_min\")) {\r\n\t\t\tAtom a = new Atom(null, new IntegerFunctor(0));\r\n\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\r\n\t\t\tary.set(1, a);\r\n\t\t\treturn b;\r\n\t\t}\r\n\t\telse if(guardID.equals(\"member\")) {\r\n\t\t\treturn Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"not_member\")) {\r\n\t\t\treturn !Util.listMember((Atom)ary.get(0), (Link)ary.get(1));\r\n\t\t}\r\n\t\telse if(guardID.equals(\"test\")) {\r\n//\t\t\tboolean b = Util.listMin((Link)ary.get(0), a);\r\n\t\t\tAtom aa = mem.newAtom(new IntegerFunctor(777));\r\n\t\t\tLink link = (Link)ary.get(0);\r\n//\t\t\tlink.getAtom().remove();\r\n\t\t\tmem.inheritLink(aa, 0, link);\r\n\t\t\treturn true;\r\n\t\t}\r\n\t\treturn false;\r\n\t}\r\n}\r\n", 0, null);
	private static final Functor f25 = new Functor("is_empty", 3, "list");
	private static final Functor f6 = new IntegerFunctor(1);
	private static final Functor f18 = new Functor("grep_s0", 2, "list");
	private static final Functor f26 = new Functor("new", 1, "list");
	private static final Functor f17 = new Functor("map", 3, "list");
	private static final Functor f14 = new Functor("true", 1, null);
	private static final Functor f24 = new Functor("unshift", 3, "list");
	private static final Functor f9 = new Functor("append", 3, "list");
	private static final Functor f22 = new Functor("of_queue", 2, "list");
	private static final Functor f21 = new Functor("new", 3, "queue");
	private static final Functor f12 = new Functor("false", 1, null);
	private static final Functor f23 = new Functor("shift", 3, "list");
	private static final Functor f15 = new Functor("unfold", 6, "list");
	private static final Functor f7 = new Functor("choose_k", 3, "list");
	private static final Functor f13 = new Functor("unfold_s0", 7, "list");
	private static final Functor f10 = new IntegerFunctor(0);
	private Uniq uniq0 = new Uniq();
	private Uniq uniq1 = new Uniq();
}

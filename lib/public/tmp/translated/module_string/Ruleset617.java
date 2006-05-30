package translated.module_string;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":string" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@string" + id;
	}
	private String encodedRuleset = 
"('='(H, string.times(String, Times)) :- string(String), int(Times) | '='(H, [:/*inline*/\tStringBuffer b = new StringBuffer(((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\tStringBuffer r = new StringBuffer(\"\");\tint times = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\tfor(int i=1;i<=times;i<<=1, b.append(b)) {\t\tif((i&times)>0) r.append(b);\t}\tAtom result = mem.newAtom(new StringFunctor(r.toString()));\tmem.relink(result, 0, me, 2);\tme.nthAtom(0).remove();\tme.nthAtom(1).remove();\tme.remove();\t:](String, Times))), ('='(H, string.replace(String, Regexp, Replacement)) :- string(String), string(Regexp), string(Replacement) | '='(H, [:/*inline*/\tString s=null;\ttry {\t\ts = me.nth(0).replaceAll(\t\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue(),\t\t((StringFunctor)me.nthAtom(2).getFunctor()).stringValue()\t\t);\t} catch(Exception e) {}\tif(s==null) s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\tAtom result = mem.newAtom(new Functor(s, 1));\tmem.relink(result, 0, me, 3);\tme.nthAtom(0).remove();\tme.nthAtom(1).remove();\tme.nthAtom(2).remove();\tme.remove();\t:](String, Regexp, Replacement))), ('='(H, string.match(String, Regexp)) :- string(String), string(Regexp) | '='(H, [:/*inline*/\tboolean b=false;\ttry {\t\tb = java.util.regex.Pattern.compile(\t\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ).matcher(\t\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() ).find();\t} catch(Exception e) {e.printStackTrace();}\tAtom result = mem.newAtom(new Functor(b?\"true\":\"false\", 1));\tmem.relink(result, 0, me, 2);\tme.nthAtom(0).remove();\tme.nthAtom(1).remove();\tme.remove();\t:](String, Regexp))), ('='(H, string.split(Regexp, S)) :- string(Regexp), string(S) | '='(H, [:/*inline*/\tString r[] = ((StringFunctor)me.nthAtom(1).getFunctor()).stringValue().split(\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() );//\tutil.Util.makeList(me.getArg(2), java.util.Arrays.asList(r));// util.Util.makeList¤Îcopy&paste&½¤Àµ\tList l = java.util.Arrays.asList(r);\tLink link = me.getArg(2);\tIterator it = l.iterator();\t//AbstractMembrane mem = link.getAtom().getMem();\tAtom parent=null;\tboolean first=true;\twhile(it.hasNext()) {\t\tAtom c = mem.newAtom(new Functor(\".\", 3));  // .(Value Next Parent)\t\tAtom v = mem.newAtom(new StringFunctor(it.next().toString()));\t\t//new Functor(it.next().toString(), 1)); // value(Value)\t\tmem.newLink(c, 0, v, 0);\t\tif(first) {\t\t\tmem.inheritLink(c, 2, link);\t\t} else {\t\t\tmem.newLink(c, 2, parent, 1);\t\t}\t\tparent = c;\t\tfirst=false;\t}\tAtom nil = mem.newAtom(new Functor(\"[]\", 1));\tif(first) {\t\tmem.inheritLink(nil, 0, link);\t} else {\t\tmem.newLink(nil, 0, parent, 1);\t}\t\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me.nthAtom(1));\tmem.removeAtom(me);\t:](Regexp, S))), ('='(H, string.concat(Glue0, string.join(Glue1, '[]'))) :- string(Glue0), string(Glue1) | '='(H, [::])), ('='(H, string.join(Glue, '.'(CAR, CDR))) :- string(Glue) | '='(H, string.concat(CAR, string.concat(Glue, string.join(Glue, CDR))))), ('='(H, string.concat(S1, S2)) :- string(S1), string(S2) | '='(H, [:/*inline*/\tAtom cat = mem.newAtom(new StringFunctor(\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() +\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ));\tmem.relinkAtomArgs(cat, 0, me, 2);\t\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me.nthAtom(1));\tmem.removeAtom(me);\t:](S1, S2))), ('='(H, string.substring(S, Begin)) :- string(S), int(Begin) | '='(H, [:/*inline*/\tint b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\tString sub = null;\ttry{\t\tsub =s.substring(b);\t} catch(Exception e){}\tAtom suba = mem.newAtom(new StringFunctor((sub==null)?\"\":sub));\tmem.relinkAtomArgs(suba, 0, me, 2);\t\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me.nthAtom(1));\tmem.removeAtom(me);\t:](S, Begin))), ('='(H, string.substring(S, Begin, End)) :- string(S), int(Begin), int(End) | '='(H, [:/*inline*/\tint b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\tint e = ((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\tString sub = null;\ttry{\t\tsub = s.substring(b,e);\t} catch(Exception exc) {}\tAtom suba = mem.newAtom(new StringFunctor((sub==null)?\"\":sub));\tmem.relinkAtomArgs(suba, 0, me, 3);\t\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me.nthAtom(1));\tmem.removeAtom(me.nthAtom(2));\tmem.removeAtom(me);\t:](S, Begin, End))), ('='(H, string.int_of_str(S)) :- string(S) | '='(H, [:/*inline*/\tint n=0;\tAtom res = null;\ttry{\t\tn = Integer.parseInt( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\t\tres = mem.newAtom(new IntegerFunctor(n));\t} catch(Exception e) {\t\tres = mem.newAtom(new Functor(\"nil\",1));\t}\tmem.relinkAtomArgs(res, 0, me, 1);\t\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](S))), ('='(H, string.str_of_int(I)) :- int(I) | '='(H, [:/*inline*/\tString s = null;\ttry{\t\ts = Integer.toString(((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue());\t} catch(Exception e) {}\tAtom res = mem.newAtom(new StringFunctor((s==null)?\"\":s));\tmem.relinkAtomArgs(res, 0, me, 1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](I))), ('='(H, string.str_of_float(I)) :- float(I) | '='(H, [:/*inline*/\tString s = null;\ttry{\t\ts = Double.toString(((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue());\t} catch(Exception e) {}\tAtom res = mem.newAtom(new StringFunctor((s==null)?\"\":s));\tmem.relinkAtomArgs(res, 0, me, 1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](I))), ('='(H, string.float_of_str(S)) :- string(S) | '='(H, [:/*inline*/\tdouble d = 0.0;\tAtom res = null;\ttry{\t\td = Double.parseDouble( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\t\tres = mem.newAtom(new FloatingFunctor(d));\t} catch(Exception e) {\t\tres = mem.newAtom(new Functor(\"nil\", 1));\t}\tmem.relinkAtomArgs(res, 0, me, 1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](S))), ('='(H, string.unary_to_str(I)) :- unary(I) | '='(H, [:/*inline*/    String s = me.nth(0);\tAtom res = mem.newAtom(new StringFunctor(s));\tmem.relinkAtomArgs(res, 0, me, 1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](I))), ('='(H, string.int_to_str(I)) :- int(I) | '='(H, [:/*inline*/\tString s = \"\";\tchar c;\ttry{\t\tc = (char)((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\t\ts = Character.toString(c);\t} catch(Exception e) {}\tAtom res = mem.newAtom(new StringFunctor(s));\tmem.relinkAtomArgs(res, 0, me, 1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](I)))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1620(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "times");
			return true;
		}
		if (execL1629(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "replace");
			return true;
		}
		if (execL1638(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "match");
			return true;
		}
		if (execL1647(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "split");
			return true;
		}
		if (execL1656(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "join");
			return true;
		}
		if (execL1668(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "join");
			return true;
		}
		if (execL1678(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "concat");
			return true;
		}
		if (execL1687(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "substring");
			return true;
		}
		if (execL1696(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "substring");
			return true;
		}
		if (execL1705(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "int_of_str");
			return true;
		}
		if (execL1714(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "str_of_int");
			return true;
		}
		if (execL1723(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "str_of_float");
			return true;
		}
		if (execL1732(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "float_of_str");
			return true;
		}
		if (execL1741(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "unary_to_str");
			return true;
		}
		if (execL1750(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@617", "int_to_str");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1623(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "times");
			return true;
		}
		if (execL1632(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "replace");
			return true;
		}
		if (execL1641(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "match");
			return true;
		}
		if (execL1650(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "split");
			return true;
		}
		if (execL1662(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "join");
			return true;
		}
		if (execL1672(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "join");
			return true;
		}
		if (execL1681(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "concat");
			return true;
		}
		if (execL1690(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "substring");
			return true;
		}
		if (execL1699(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "substring");
			return true;
		}
		if (execL1708(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "int_of_str");
			return true;
		}
		if (execL1717(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "str_of_int");
			return true;
		}
		if (execL1726(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "str_of_float");
			return true;
		}
		if (execL1735(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "float_of_str");
			return true;
		}
		if (execL1744(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "unary_to_str");
			return true;
		}
		if (execL1753(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@617", "int_to_str");
			return true;
		}
		return result;
	}
	public boolean execL1753(Object var0, boolean nondeterministic) {
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
L1753:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL1749(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1753;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1749(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1749:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 12);
			ret = true;
			break L1749;
		}
		return ret;
	}
	public boolean execL1750(Object var0, Object var1, boolean nondeterministic) {
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
L1750:
		{
			if (execL1752(var0, var1, nondeterministic)) {
				ret = true;
				break L1750;
			}
		}
		return ret;
	}
	public boolean execL1752(Object var0, Object var1, boolean nondeterministic) {
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
L1752:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL1749(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1752;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1744(Object var0, boolean nondeterministic) {
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
L1744:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1740(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1744;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1740(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1740:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 11);
			ret = true;
			break L1740;
		}
		return ret;
	}
	public boolean execL1741(Object var0, Object var1, boolean nondeterministic) {
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
L1741:
		{
			if (execL1743(var0, var1, nondeterministic)) {
				ret = true;
				break L1741;
			}
		}
		return ret;
	}
	public boolean execL1743(Object var0, Object var1, boolean nondeterministic) {
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
L1743:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1740(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1743;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1735(Object var0, boolean nondeterministic) {
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
L1735:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (execL1731(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1735;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1731(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1731:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 10);
			ret = true;
			break L1731;
		}
		return ret;
	}
	public boolean execL1732(Object var0, Object var1, boolean nondeterministic) {
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
L1732:
		{
			if (execL1734(var0, var1, nondeterministic)) {
				ret = true;
				break L1732;
			}
		}
		return ret;
	}
	public boolean execL1734(Object var0, Object var1, boolean nondeterministic) {
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
L1734:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
						if (execL1731(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1734;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1726(Object var0, boolean nondeterministic) {
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
L1726:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof FloatingFunctor))) {
					if (execL1722(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1726;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1722(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1722:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f7;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 9);
			ret = true;
			break L1722;
		}
		return ret;
	}
	public boolean execL1723(Object var0, Object var1, boolean nondeterministic) {
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
L1723:
		{
			if (execL1725(var0, var1, nondeterministic)) {
				ret = true;
				break L1723;
			}
		}
		return ret;
	}
	public boolean execL1725(Object var0, Object var1, boolean nondeterministic) {
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
L1725:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof FloatingFunctor))) {
						if (execL1722(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1725;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1717(Object var0, boolean nondeterministic) {
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
L1717:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL1713(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1717;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1713(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1713:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f9;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 8);
			ret = true;
			break L1713;
		}
		return ret;
	}
	public boolean execL1714(Object var0, Object var1, boolean nondeterministic) {
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
L1714:
		{
			if (execL1716(var0, var1, nondeterministic)) {
				ret = true;
				break L1714;
			}
		}
		return ret;
	}
	public boolean execL1716(Object var0, Object var1, boolean nondeterministic) {
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
L1716:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL1713(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1716;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1708(Object var0, boolean nondeterministic) {
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
L1708:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (execL1704(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1708;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1704(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1704:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f11;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var4, 7);
			ret = true;
			break L1704;
		}
		return ret;
	}
	public boolean execL1705(Object var0, Object var1, boolean nondeterministic) {
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
L1705:
		{
			if (execL1707(var0, var1, nondeterministic)) {
				ret = true;
				break L1705;
			}
		}
		return ret;
	}
	public boolean execL1707(Object var0, Object var1, boolean nondeterministic) {
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
L1707:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
						if (execL1704(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1707;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1699(Object var0, boolean nondeterministic) {
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
L1699:
		{
			func = f12;
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
				if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
						if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
							if (execL1695(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L1699;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1695(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1695:
		{
			link = ((Atom)var1).getArg(3);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f13;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var7), 0 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 3,
				(Link)var9 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var8, 6);
			ret = true;
			break L1695;
		}
		return ret;
	}
	public boolean execL1696(Object var0, Object var1, boolean nondeterministic) {
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
L1696:
		{
			if (execL1698(var0, var1, nondeterministic)) {
				ret = true;
				break L1696;
			}
		}
		return ret;
	}
	public boolean execL1698(Object var0, Object var1, boolean nondeterministic) {
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
L1698:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
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
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var7 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					var8 = link.getAtom();
					if (!(!(((Atom)var8).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
							if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
								if (execL1695(var0,var1,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L1698;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1690(Object var0, boolean nondeterministic) {
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
L1690:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL1686(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1690;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1686(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1686:
		{
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
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f15;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var5), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var6, 5);
			ret = true;
			break L1686;
		}
		return ret;
	}
	public boolean execL1687(Object var0, Object var1, boolean nondeterministic) {
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
L1687:
		{
			if (execL1689(var0, var1, nondeterministic)) {
				ret = true;
				break L1687;
			}
		}
		return ret;
	}
	public boolean execL1689(Object var0, Object var1, boolean nondeterministic) {
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
L1689:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (execL1686(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1689;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1681(Object var0, boolean nondeterministic) {
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
L1681:
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
				if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL1677(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1681;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1677(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1677:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f17;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var6, 4);
			ret = true;
			break L1677;
		}
		return ret;
	}
	public boolean execL1678(Object var0, Object var1, boolean nondeterministic) {
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
L1678:
		{
			if (execL1680(var0, var1, nondeterministic)) {
				ret = true;
				break L1678;
			}
		}
		return ret;
	}
	public boolean execL1680(Object var0, Object var1, boolean nondeterministic) {
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
L1680:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (execL1677(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1680;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1672(Object var0, boolean nondeterministic) {
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
L1672:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (!(!(f19).equals(((Atom)var2).getFunctor()))) {
							if (execL1667(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1672;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1667(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1667:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(0);
			var10 = link;
			link = ((Atom)var1).getArg(2);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f16;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var4), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var9 );
			((Atom)var1).getMem().newLink(
				((Atom)var1), 2,
				((Atom)var7), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var8), 1 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 0,
				(Link)var10 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 2,
				(Link)var11 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_string");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module string");
				}
				try {
					Class c = Class.forName("translated.Module_string");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module string");
				}
				try {
					Class c = Class.forName("translated.Module_string");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module string");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module string");
				}
			ret = true;
			break L1667;
		}
		return ret;
	}
	public boolean execL1668(Object var0, Object var1, boolean nondeterministic) {
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
L1668:
		{
			if (execL1670(var0, var1, nondeterministic)) {
				ret = true;
				break L1668;
			}
		}
		return ret;
	}
	public boolean execL1670(Object var0, Object var1, boolean nondeterministic) {
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
L1670:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						var9 = link.getAtom();
						if (((Atom)var9).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var9).getFunctor()).getObject() instanceof String) {
							if (!(!(f19).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								if (execL1667(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L1670;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1662(Object var0, boolean nondeterministic) {
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
L1662:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var5 = link.getAtom();
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (!(!(f16).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(0);
								var4 = link.getAtom();
								if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
									if (!(!(f20).equals(((Atom)var2).getFunctor()))) {
										if (execL1655(var0,var1,var3,var2,var4,var5,nondeterministic)) {
											ret = true;
											break L1662;
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
	public boolean execL1655(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L1655:
		{
			link = ((Atom)var2).getArg(2);
			var7 = link;
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
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f21;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var7 );
			ret = true;
			break L1655;
		}
		return ret;
	}
	public boolean execL1656(Object var0, Object var1, boolean nondeterministic) {
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
L1656:
		{
			if (execL1658(var0, var1, nondeterministic)) {
				ret = true;
				break L1656;
			}
			if (execL1660(var0, var1, nondeterministic)) {
				ret = true;
				break L1656;
			}
		}
		return ret;
	}
	public boolean execL1660(Object var0, Object var1, boolean nondeterministic) {
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
L1660:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
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
						var11 = link.getAtom();
						if (((Atom)var11).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var11).getFunctor()).getObject() instanceof String) {
							if (!(!(f18).equals(((Atom)var5).getFunctor()))) {
								link = ((Atom)var5).getArg(0);
								var6 = link;
								link = ((Atom)var5).getArg(1);
								var7 = link;
								link = ((Atom)var5).getArg(2);
								var8 = link;
								link = ((Atom)var5).getArg(1);
								if (!(link.getPos() != 0)) {
									var9 = link.getAtom();
									link = ((Atom)var5).getArg(0);
									var12 = link.getAtom();
									if (((Atom)var12).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var12).getFunctor()).getObject() instanceof String) {
										if (!(!(f20).equals(((Atom)var9).getFunctor()))) {
											link = ((Atom)var9).getArg(0);
											var10 = link;
											if (execL1655(var0,var5,var1,var9,var11,var12,nondeterministic)) {
												ret = true;
												break L1660;
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
	public boolean execL1658(Object var0, Object var1, boolean nondeterministic) {
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
L1658:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
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
						link = ((Atom)var1).getArg(2);
						if (!(link.getPos() != 1)) {
							var7 = link.getAtom();
							link = ((Atom)var1).getArg(0);
							var12 = link.getAtom();
							if (((Atom)var12).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var12).getFunctor()).getObject() instanceof String) {
								if (!(!(f16).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(0);
									var11 = link.getAtom();
									if (((Atom)var11).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var11).getFunctor()).getObject() instanceof String) {
										if (!(!(f20).equals(((Atom)var5).getFunctor()))) {
											link = ((Atom)var5).getArg(0);
											var6 = link;
											if (execL1655(var0,var1,var7,var5,var11,var12,nondeterministic)) {
												ret = true;
												break L1658;
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
	public boolean execL1650(Object var0, boolean nondeterministic) {
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
L1650:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL1646(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1650;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1646(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1646:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f23;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var6, 3);
			ret = true;
			break L1646;
		}
		return ret;
	}
	public boolean execL1647(Object var0, Object var1, boolean nondeterministic) {
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
L1647:
		{
			if (execL1649(var0, var1, nondeterministic)) {
				ret = true;
				break L1647;
			}
		}
		return ret;
	}
	public boolean execL1649(Object var0, Object var1, boolean nondeterministic) {
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
L1649:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (execL1646(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1649;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1641(Object var0, boolean nondeterministic) {
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
L1641:
		{
			func = f24;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL1637(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1641;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1637(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1637:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f25;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var6, 2);
			ret = true;
			break L1637;
		}
		return ret;
	}
	public boolean execL1638(Object var0, Object var1, boolean nondeterministic) {
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
L1638:
		{
			if (execL1640(var0, var1, nondeterministic)) {
				ret = true;
				break L1638;
			}
		}
		return ret;
	}
	public boolean execL1640(Object var0, Object var1, boolean nondeterministic) {
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
L1640:
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
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (execL1637(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1640;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1632(Object var0, boolean nondeterministic) {
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
L1632:
		{
			func = f26;
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
				if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
							if (execL1628(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L1632;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1628(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1628:
		{
			link = ((Atom)var1).getArg(3);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f27;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var7), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 1,
				((Atom)var6), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 2,
				((Atom)var5), 0 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 3,
				(Link)var9 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var8, 1);
			ret = true;
			break L1628;
		}
		return ret;
	}
	public boolean execL1629(Object var0, Object var1, boolean nondeterministic) {
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
L1629:
		{
			if (execL1631(var0, var1, nondeterministic)) {
				ret = true;
				break L1629;
			}
		}
		return ret;
	}
	public boolean execL1631(Object var0, Object var1, boolean nondeterministic) {
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
L1631:
		{
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
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
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var7 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					var8 = link.getAtom();
					if (((Atom)var8).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var8).getFunctor()).getObject() instanceof String) {
						if (((Atom)var7).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var7).getFunctor()).getObject() instanceof String) {
							if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
								if (execL1628(var0,var1,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L1631;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1623(Object var0, boolean nondeterministic) {
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
L1623:
		{
			func = f28;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
					if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
						if (execL1619(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1623;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1619(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1619:
		{
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f29;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 1,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 2,
				(Link)var7 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodestring.run((Atom)var6, 0);
			ret = true;
			break L1619;
		}
		return ret;
	}
	public boolean execL1620(Object var0, Object var1, boolean nondeterministic) {
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
L1620:
		{
			if (execL1622(var0, var1, nondeterministic)) {
				ret = true;
				break L1620;
			}
		}
		return ret;
	}
	public boolean execL1622(Object var0, Object var1, boolean nondeterministic) {
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
L1622:
		{
			if (!(!(f28).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
							if (execL1619(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1622;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f5 = new Functor("/*inline*/\r\n\tdouble d = 0.0;\r\n\tAtom res = null;\r\n\ttry{\r\n\t\td = Double.parseDouble( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\r\n\t\tres = mem.newAtom(new FloatingFunctor(d));\r\n\t} catch(Exception e) {\r\n\t\tres = mem.newAtom(new Functor(\"nil\", 1));\r\n\t}\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f0 = new Functor("int_to_str", 2, "string");
	private static final Functor f28 = new Functor("times", 3, "string");
	private static final Functor f14 = new Functor("substring", 3, "string");
	private static final Functor f19 = new Functor(".", 3, null);
	private static final Functor f20 = new Functor("[]", 1, null);
	private static final Functor f22 = new Functor("split", 3, "string");
	private static final Functor f26 = new Functor("replace", 4, "string");
	private static final Functor f2 = new Functor("unary_to_str", 2, "string");
	private static final Functor f3 = new Functor("/*inline*/\r\n    String s = me.nth(0);\r\n\tAtom res = mem.newAtom(new StringFunctor(s));\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f8 = new Functor("str_of_int", 2, "string");
	private static final Functor f13 = new Functor("/*inline*/\r\n\tint b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\tint e = ((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();\r\n\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\r\n\tString sub = null;\r\n\ttry{\r\n\t\tsub = s.substring(b,e);\r\n\t} catch(Exception exc) {}\r\n\tAtom suba = mem.newAtom(new StringFunctor((sub==null)?\"\":sub));\r\n\tmem.relinkAtomArgs(suba, 0, me, 3);\r\n\t\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me.nthAtom(1));\r\n\tmem.removeAtom(me.nthAtom(2));\r\n\tmem.removeAtom(me);\r\n\t", 4, null);
	private static final Functor f1 = new Functor("/*inline*/\r\n\tString s = \"\";\r\n\tchar c;\r\n\ttry{\r\n\t\tc = (char)((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();\r\n\t\ts = Character.toString(c);\r\n\t} catch(Exception e) {}\r\n\tAtom res = mem.newAtom(new StringFunctor(s));\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f29 = new Functor("/*inline*/\r\n\tStringBuffer b = new StringBuffer(((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\r\n\tStringBuffer r = new StringBuffer(\"\");\r\n\tint times = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\tfor(int i=1;i<=times;i<<=1, b.append(b)) {\r\n\t\tif((i&times)>0) r.append(b);\r\n\t}\r\n\tAtom result = mem.newAtom(new StringFunctor(r.toString()));\r\n\tmem.relink(result, 0, me, 2);\r\n\tme.nthAtom(0).remove();\r\n\tme.nthAtom(1).remove();\r\n\tme.remove();\r\n\t", 3, null);
	private static final Functor f11 = new Functor("/*inline*/\r\n\tint n=0;\r\n\tAtom res = null;\r\n\ttry{\r\n\t\tn = Integer.parseInt( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());\r\n\t\tres = mem.newAtom(new IntegerFunctor(n));\r\n\t} catch(Exception e) {\r\n\t\tres = mem.newAtom(new Functor(\"nil\",1));\r\n\t}\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\t\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f9 = new Functor("/*inline*/\r\n\tString s = null;\r\n\ttry{\r\n\t\ts = Integer.toString(((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue());\r\n\t} catch(Exception e) {}\r\n\tAtom res = mem.newAtom(new StringFunctor((s==null)?\"\":s));\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f17 = new Functor("/*inline*/\r\n\tAtom cat = mem.newAtom(new StringFunctor(\r\n\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() +\r\n\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ));\r\n\tmem.relinkAtomArgs(cat, 0, me, 2);\r\n\t\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me.nthAtom(1));\r\n\tmem.removeAtom(me);\r\n\t", 3, null);
	private static final Functor f25 = new Functor("/*inline*/\r\n\tboolean b=false;\r\n\ttry {\r\n\t\tb = java.util.regex.Pattern.compile(\r\n\t\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ).matcher(\r\n\t\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() ).find();\r\n\t} catch(Exception e) {e.printStackTrace();}\r\n\tAtom result = mem.newAtom(new Functor(b?\"true\":\"false\", 1));\r\n\tmem.relink(result, 0, me, 2);\r\n\tme.nthAtom(0).remove();\r\n\tme.nthAtom(1).remove();\r\n\tme.remove();\r\n\t", 3, null);
	private static final Functor f10 = new Functor("int_of_str", 2, "string");
	private static final Functor f27 = new Functor("/*inline*/\r\n\tString s=null;\r\n\ttry {\r\n\t\ts = me.nth(0).replaceAll(\r\n\t\t((StringFunctor)me.nthAtom(1).getFunctor()).stringValue(),\r\n\t\t((StringFunctor)me.nthAtom(2).getFunctor()).stringValue()\r\n\t\t);\r\n\t} catch(Exception e) {}\r\n\tif(s==null) s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\r\n\tAtom result = mem.newAtom(new Functor(s, 1));\r\n\tmem.relink(result, 0, me, 3);\r\n\tme.nthAtom(0).remove();\r\n\tme.nthAtom(1).remove();\r\n\tme.nthAtom(2).remove();\r\n\tme.remove();\r\n\t", 4, null);
	private static final Functor f16 = new Functor("concat", 3, "string");
	private static final Functor f23 = new Functor("/*inline*/\r\n\tString r[] = ((StringFunctor)me.nthAtom(1).getFunctor()).stringValue().split(\r\n\t((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() );\r\n\r\n//\tutil.Util.makeList(me.getArg(2), java.util.Arrays.asList(r));\r\n\r\n// util.Util.makeList¤Îcopy&paste&½¤Àµ\r\n\tList l = java.util.Arrays.asList(r);\r\n\tLink link = me.getArg(2);\r\n\r\n\tIterator it = l.iterator();\r\n\t//AbstractMembrane mem = link.getAtom().getMem();\r\n\tAtom parent=null;\r\n\tboolean first=true;\r\n\twhile(it.hasNext()) {\r\n\t\tAtom c = mem.newAtom(new Functor(\".\", 3));  // .(Value Next Parent)\r\n\t\tAtom v = mem.newAtom(new StringFunctor(it.next().toString()));\r\n\t\t//new Functor(it.next().toString(), 1)); // value(Value)\r\n\t\tmem.newLink(c, 0, v, 0);\r\n\t\tif(first) {\r\n\t\t\tmem.inheritLink(c, 2, link);\r\n\t\t} else {\r\n\t\t\tmem.newLink(c, 2, parent, 1);\r\n\t\t}\r\n\t\tparent = c;\r\n\t\tfirst=false;\r\n\t}\r\n\tAtom nil = mem.newAtom(new Functor(\"[]\", 1));\r\n\tif(first) {\r\n\t\tmem.inheritLink(nil, 0, link);\r\n\t} else {\r\n\t\tmem.newLink(nil, 0, parent, 1);\r\n\t}\r\n\t\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me.nthAtom(1));\r\n\tmem.removeAtom(me);\r\n\t", 3, null);
	private static final Functor f12 = new Functor("substring", 4, "string");
	private static final Functor f15 = new Functor("/*inline*/\r\n\tint b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();\r\n\tString s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();\r\n\tString sub = null;\r\n\ttry{\r\n\t\tsub =s.substring(b);\r\n\t} catch(Exception e){}\r\n\tAtom suba = mem.newAtom(new StringFunctor((sub==null)?\"\":sub));\r\n\tmem.relinkAtomArgs(suba, 0, me, 2);\r\n\t\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me.nthAtom(1));\r\n\tmem.removeAtom(me);\r\n\t", 3, null);
	private static final Functor f7 = new Functor("/*inline*/\r\n\tString s = null;\r\n\ttry{\r\n\t\ts = Double.toString(((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue());\r\n\t} catch(Exception e) {}\r\n\tAtom res = mem.newAtom(new StringFunctor((s==null)?\"\":s));\r\n\tmem.relinkAtomArgs(res, 0, me, 1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f18 = new Functor("join", 3, "string");
	private static final Functor f24 = new Functor("match", 3, "string");
	private static final Functor f4 = new Functor("float_of_str", 2, "string");
	private static final Functor f6 = new Functor("str_of_float", 2, "string");
	private static final Functor f21 = new StringFunctor("");
}

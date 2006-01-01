package translated.module_integer;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset602 extends Ruleset {
	private static final Ruleset602 theInstance = new Ruleset602();
	private Ruleset602() {}
	public static Ruleset602 getInstance() {
		return theInstance;
	}
	private int id = 602;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":integer" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@integer" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL356(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "(  :- { integer(_1), module(_1), ( '+'(_4,_5,H), $A[_4], $B[_5] :- int($A) int($B) '+'($A,$B,$~3) '='($~3,$C) | $C[H] ), ( '-'(_16,_17,H), $A[_16], $B[_17] :- int($A) int($B) '-'($A,$B,$~15) '='($~15,$C) | $C[H] ), ( '*'(_28,_29,H), $A[_28], $B[_29] :- int($A) int($B) '*'($A,$B,$~27) '='($~27,$C) | $C[H] ), ( '/'(_40,_41,H), $A[_40], $B[_41] :- int($A) int($B) '/'($A,$B,$~39) '='($~39,$C) | $C[H] ), ( mod(_52,_53,H), $A[_52], $B[_53] :- int($A) int($B) mod($A,$B,$~51) '='($~51,$C) | $C[H] ), ( integer.abs(_66,H), $N[_66] :- int($N) 0($~65) '<'($N,$~65) | '-1'(_64), '*'(_64,_71,H), $N[_71] ), ( integer.abs(_74,H), $N[_74] :- int($N) 0($~73) '>='($N,$~73) | $N[H] ), ( '>'(_82,_83,H), $A[_82], $B[_83] :- int($A) int($B) '>'($A,$B) | true(H) ), ( '>'(_90,_91,H), $A[_90], $B[_91] :- int($A) int($B) '=<'($A,$B) | false(H) ), ( '<'(_98,_99,H), $A[_98], $B[_99] :- int($A) int($B) '>='($A,$B) | false(H) ), ( '<'(_106,_107,H), $A[_106], $B[_107] :- int($A) int($B) '<'($A,$B) | true(H) ), ( '>='(_114,_115,H), $A[_114], $B[_115] :- int($A) int($B) '>='($A,$B) | true(H) ), ( '>='(_122,_123,H), $A[_122], $B[_123] :- int($A) int($B) '<'($A,$B) | false(H) ), ( '=<'(_130,_131,H), $A[_130], $B[_131] :- int($A) int($B) '>'($A,$B) | false(H) ), ( '=<'(_138,_139,H), $A[_138], $B[_139] :- int($A) int($B) '=<'($A,$B) | true(H) ), ( '=='(_146,_147,H), $A[_146], $B[_147] :- int($A) int($B) '='($A,$B) | true(H) ), ( '=='(_156,_157,H), $A[_156], $B[_157] :- int($A) int($B) '-'($A,$B,$~154) 0($~155) '>'($~154,$~155) | false(H) ), ( '=='(_170,_171,H), $A[_170], $B[_171] :- int($A) int($B) '-'($A,$B,$~168) 0($~169) '<'($~168,$~169) | false(H) ), ( '!='(_182,_183,H), $A[_182], $B[_183] :- int($A) int($B) '='($A,$B) | false(H) ), ( '!='(_192,_193,H), $A[_192], $B[_193] :- int($A) int($B) '-'($A,$B,$~190) 0($~191) '>'($~190,$~191) | true(H) ), ( '!='(_206,_207,H), $A[_206], $B[_207] :- int($A) int($B) '-'($A,$B,$~204) 0($~205) '<'($~204,$~205) | true(H) ), ( '<<'(_223,_224,H), $A[_223], $N[_224] :- int($A) int($N) 0($~222) '>'($N,$~222) | 2(_219), '*'(_230,_219,_218), 1(_221), '-'(_231,_221,_220), '<<'(_218,_220,H), $A[_230], $N[_231] ), ( '<<'(A,_233,H), 0(_233) :- '='(H,A) ), ( '>>'(_241,_242,H), $A[_241], $N[_242] :- int($A) int($N) 0($~240) '>'($N,$~240) | 2(_237), '/'(_248,_237,_236), 1(_239), '-'(_249,_239,_238), '>>'(_236,_238,H), $A[_248], $N[_249] ), ( '>>'(A,_251,H), 0(_251) :- '='(H,A) ), ( '&'(_254,_255,H), $A[_254], $B[_255] :- int($A) int($B) | '/*inline*/\\r\\n..'(_258,_259,H), $A[_258], $B[_259] ), ( '|'(_262,_263,H), $A[_262], $B[_263] :- int($A) int($B) | '/*inline*/\\r\\n..'(_266,_267,H), $A[_266], $B[_267] ), ( '^'(_270,_271,H), $A[_270], $B[_271] :- int($A) int($B) | '/*inline*/\\r\\n..'(_274,_275,H), $A[_274], $B[_275] ), ( integer.power(_278,_279,H), $A[_278], $N[_279] :- int($A) int($N) | '/*inline*/\\r\\n..'(_282,_283,H), $A[_282], $N[_283] ), ( integer.rnd(_286,H), $N[_286] :- int($N) | '/*inline*/\\r\\n..'(_288,H), $N[_288] ), ( integer.gcd(_292,_293,H), $M[_292], $N[_293] :- '>'($M,$N) | '-'(_296,_297,_291), integer.gcd(_291,_298,H), $M[_296], $N[_297], $N[_298] ), ( integer.gcd(_302,_303,H), $M[_302], $N[_303] :- '>'($N,$M) | '-'(_306,_307,_301), integer.gcd(_308,_301,H), $N[_306], $M[_307], $M[_308] ), ( integer.gcd(_310,_311,H), $M[_310], $N[_311] :- '='($N,$M) | $M[H] ), ( integer.lcm(_319,_320,H), $M[_319], $N[_320] :- int($M) int($N) | '*'(_323,_324,_317), integer.gcd(_325,_326,_318), '/'(_317,_318,H), $M[_323], $N[_324], $M[_325], $N[_326] ), ( integer.factorial(_330,H), $N[_330] :- 1($~329) '=<'($N,$~329) | 1(H) ), ( integer.factorial(_340,H), $N[_340] :- 2($~339) '>='($N,$~339) | 1(_338), '-'(_344,_338,_337), integer.factorial(_337,_336), '*'(_345,_336,H), $N[_344], $N[_345] ) } )");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL357(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "(  :- { integer(_1), module(_1), ( '+'(_4,_5,H), $A[_4], $B[_5] :- int($A) int($B) '+'($A,$B,$~3) '='($~3,$C) | $C[H] ), ( '-'(_16,_17,H), $A[_16], $B[_17] :- int($A) int($B) '-'($A,$B,$~15) '='($~15,$C) | $C[H] ), ( '*'(_28,_29,H), $A[_28], $B[_29] :- int($A) int($B) '*'($A,$B,$~27) '='($~27,$C) | $C[H] ), ( '/'(_40,_41,H), $A[_40], $B[_41] :- int($A) int($B) '/'($A,$B,$~39) '='($~39,$C) | $C[H] ), ( mod(_52,_53,H), $A[_52], $B[_53] :- int($A) int($B) mod($A,$B,$~51) '='($~51,$C) | $C[H] ), ( integer.abs(_66,H), $N[_66] :- int($N) 0($~65) '<'($N,$~65) | '-1'(_64), '*'(_64,_71,H), $N[_71] ), ( integer.abs(_74,H), $N[_74] :- int($N) 0($~73) '>='($N,$~73) | $N[H] ), ( '>'(_82,_83,H), $A[_82], $B[_83] :- int($A) int($B) '>'($A,$B) | true(H) ), ( '>'(_90,_91,H), $A[_90], $B[_91] :- int($A) int($B) '=<'($A,$B) | false(H) ), ( '<'(_98,_99,H), $A[_98], $B[_99] :- int($A) int($B) '>='($A,$B) | false(H) ), ( '<'(_106,_107,H), $A[_106], $B[_107] :- int($A) int($B) '<'($A,$B) | true(H) ), ( '>='(_114,_115,H), $A[_114], $B[_115] :- int($A) int($B) '>='($A,$B) | true(H) ), ( '>='(_122,_123,H), $A[_122], $B[_123] :- int($A) int($B) '<'($A,$B) | false(H) ), ( '=<'(_130,_131,H), $A[_130], $B[_131] :- int($A) int($B) '>'($A,$B) | false(H) ), ( '=<'(_138,_139,H), $A[_138], $B[_139] :- int($A) int($B) '=<'($A,$B) | true(H) ), ( '=='(_146,_147,H), $A[_146], $B[_147] :- int($A) int($B) '='($A,$B) | true(H) ), ( '=='(_156,_157,H), $A[_156], $B[_157] :- int($A) int($B) '-'($A,$B,$~154) 0($~155) '>'($~154,$~155) | false(H) ), ( '=='(_170,_171,H), $A[_170], $B[_171] :- int($A) int($B) '-'($A,$B,$~168) 0($~169) '<'($~168,$~169) | false(H) ), ( '!='(_182,_183,H), $A[_182], $B[_183] :- int($A) int($B) '='($A,$B) | false(H) ), ( '!='(_192,_193,H), $A[_192], $B[_193] :- int($A) int($B) '-'($A,$B,$~190) 0($~191) '>'($~190,$~191) | true(H) ), ( '!='(_206,_207,H), $A[_206], $B[_207] :- int($A) int($B) '-'($A,$B,$~204) 0($~205) '<'($~204,$~205) | true(H) ), ( '<<'(_223,_224,H), $A[_223], $N[_224] :- int($A) int($N) 0($~222) '>'($N,$~222) | 2(_219), '*'(_230,_219,_218), 1(_221), '-'(_231,_221,_220), '<<'(_218,_220,H), $A[_230], $N[_231] ), ( '<<'(A,_233,H), 0(_233) :- '='(H,A) ), ( '>>'(_241,_242,H), $A[_241], $N[_242] :- int($A) int($N) 0($~240) '>'($N,$~240) | 2(_237), '/'(_248,_237,_236), 1(_239), '-'(_249,_239,_238), '>>'(_236,_238,H), $A[_248], $N[_249] ), ( '>>'(A,_251,H), 0(_251) :- '='(H,A) ), ( '&'(_254,_255,H), $A[_254], $B[_255] :- int($A) int($B) | '/*inline*/\\r\\n..'(_258,_259,H), $A[_258], $B[_259] ), ( '|'(_262,_263,H), $A[_262], $B[_263] :- int($A) int($B) | '/*inline*/\\r\\n..'(_266,_267,H), $A[_266], $B[_267] ), ( '^'(_270,_271,H), $A[_270], $B[_271] :- int($A) int($B) | '/*inline*/\\r\\n..'(_274,_275,H), $A[_274], $B[_275] ), ( integer.power(_278,_279,H), $A[_278], $N[_279] :- int($A) int($N) | '/*inline*/\\r\\n..'(_282,_283,H), $A[_282], $N[_283] ), ( integer.rnd(_286,H), $N[_286] :- int($N) | '/*inline*/\\r\\n..'(_288,H), $N[_288] ), ( integer.gcd(_292,_293,H), $M[_292], $N[_293] :- '>'($M,$N) | '-'(_296,_297,_291), integer.gcd(_291,_298,H), $M[_296], $N[_297], $N[_298] ), ( integer.gcd(_302,_303,H), $M[_302], $N[_303] :- '>'($N,$M) | '-'(_306,_307,_301), integer.gcd(_308,_301,H), $N[_306], $M[_307], $M[_308] ), ( integer.gcd(_310,_311,H), $M[_310], $N[_311] :- '='($N,$M) | $M[H] ), ( integer.lcm(_319,_320,H), $M[_319], $N[_320] :- int($M) int($N) | '*'(_323,_324,_317), integer.gcd(_325,_326,_318), '/'(_317,_318,H), $M[_323], $N[_324], $M[_325], $N[_326] ), ( integer.factorial(_330,H), $N[_330] :- 1($~329) '=<'($N,$~329) | 1(H) ), ( integer.factorial(_340,H), $N[_340] :- 2($~339) '>='($N,$~339) | 1(_338), '-'(_344,_338,_337), integer.factorial(_337,_336), '*'(_345,_336,H), $N[_344], $N[_345] ) } )");
			return true;
		}
		return result;
	}
	public boolean execL357(Object var0, boolean nondeterministic) {
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
L357:
		{
			if (execL354(var0,nondeterministic)) {
				ret = true;
				break L357;
			}
		}
		return ret;
	}
	public boolean execL354(Object var0, boolean nondeterministic) {
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
L354:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "(  :- { integer(_1), module(_1), ( '+'(_4,_5,H), $A[_4], $B[_5] :- int($A) int($B) '+'($A,$B,$~3) '='($~3,$C) | $C[H] ), ( '-'(_16,_17,H), $A[_16], $B[_17] :- int($A) int($B) '-'($A,$B,$~15) '='($~15,$C) | $C[H] ), ( '*'(_28,_29,H), $A[_28], $B[_29] :- int($A) int($B) '*'($A,$B,$~27) '='($~27,$C) | $C[H] ), ( '/'(_40,_41,H), $A[_40], $B[_41] :- int($A) int($B) '/'($A,$B,$~39) '='($~39,$C) | $C[H] ), ( mod(_52,_53,H), $A[_52], $B[_53] :- int($A) int($B) mod($A,$B,$~51) '='($~51,$C) | $C[H] ), ( integer.abs(_66,H), $N[_66] :- int($N) 0($~65) '<'($N,$~65) | '-1'(_64), '*'(_64,_71,H), $N[_71] ), ( integer.abs(_74,H), $N[_74] :- int($N) 0($~73) '>='($N,$~73) | $N[H] ), ( '>'(_82,_83,H), $A[_82], $B[_83] :- int($A) int($B) '>'($A,$B) | true(H) ), ( '>'(_90,_91,H), $A[_90], $B[_91] :- int($A) int($B) '=<'($A,$B) | false(H) ), ( '<'(_98,_99,H), $A[_98], $B[_99] :- int($A) int($B) '>='($A,$B) | false(H) ), ( '<'(_106,_107,H), $A[_106], $B[_107] :- int($A) int($B) '<'($A,$B) | true(H) ), ( '>='(_114,_115,H), $A[_114], $B[_115] :- int($A) int($B) '>='($A,$B) | true(H) ), ( '>='(_122,_123,H), $A[_122], $B[_123] :- int($A) int($B) '<'($A,$B) | false(H) ), ( '=<'(_130,_131,H), $A[_130], $B[_131] :- int($A) int($B) '>'($A,$B) | false(H) ), ( '=<'(_138,_139,H), $A[_138], $B[_139] :- int($A) int($B) '=<'($A,$B) | true(H) ), ( '=='(_146,_147,H), $A[_146], $B[_147] :- int($A) int($B) '='($A,$B) | true(H) ), ( '=='(_156,_157,H), $A[_156], $B[_157] :- int($A) int($B) '-'($A,$B,$~154) 0($~155) '>'($~154,$~155) | false(H) ), ( '=='(_170,_171,H), $A[_170], $B[_171] :- int($A) int($B) '-'($A,$B,$~168) 0($~169) '<'($~168,$~169) | false(H) ), ( '!='(_182,_183,H), $A[_182], $B[_183] :- int($A) int($B) '='($A,$B) | false(H) ), ( '!='(_192,_193,H), $A[_192], $B[_193] :- int($A) int($B) '-'($A,$B,$~190) 0($~191) '>'($~190,$~191) | true(H) ), ( '!='(_206,_207,H), $A[_206], $B[_207] :- int($A) int($B) '-'($A,$B,$~204) 0($~205) '<'($~204,$~205) | true(H) ), ( '<<'(_223,_224,H), $A[_223], $N[_224] :- int($A) int($N) 0($~222) '>'($N,$~222) | 2(_219), '*'(_230,_219,_218), 1(_221), '-'(_231,_221,_220), '<<'(_218,_220,H), $A[_230], $N[_231] ), ( '<<'(A,_233,H), 0(_233) :- '='(H,A) ), ( '>>'(_241,_242,H), $A[_241], $N[_242] :- int($A) int($N) 0($~240) '>'($N,$~240) | 2(_237), '/'(_248,_237,_236), 1(_239), '-'(_249,_239,_238), '>>'(_236,_238,H), $A[_248], $N[_249] ), ( '>>'(A,_251,H), 0(_251) :- '='(H,A) ), ( '&'(_254,_255,H), $A[_254], $B[_255] :- int($A) int($B) | '/*inline*/\\r\\n..'(_258,_259,H), $A[_258], $B[_259] ), ( '|'(_262,_263,H), $A[_262], $B[_263] :- int($A) int($B) | '/*inline*/\\r\\n..'(_266,_267,H), $A[_266], $B[_267] ), ( '^'(_270,_271,H), $A[_270], $B[_271] :- int($A) int($B) | '/*inline*/\\r\\n..'(_274,_275,H), $A[_274], $B[_275] ), ( integer.power(_278,_279,H), $A[_278], $N[_279] :- int($A) int($N) | '/*inline*/\\r\\n..'(_282,_283,H), $A[_282], $N[_283] ), ( integer.rnd(_286,H), $N[_286] :- int($N) | '/*inline*/\\r\\n..'(_288,H), $N[_288] ), ( integer.gcd(_292,_293,H), $M[_292], $N[_293] :- '>'($M,$N) | '-'(_296,_297,_291), integer.gcd(_291,_298,H), $M[_296], $N[_297], $N[_298] ), ( integer.gcd(_302,_303,H), $M[_302], $N[_303] :- '>'($N,$M) | '-'(_306,_307,_301), integer.gcd(_308,_301,H), $N[_306], $M[_307], $M[_308] ), ( integer.gcd(_310,_311,H), $M[_310], $N[_311] :- '='($N,$M) | $M[H] ), ( integer.lcm(_319,_320,H), $M[_319], $N[_320] :- int($M) int($N) | '*'(_323,_324,_317), integer.gcd(_325,_326,_318), '/'(_317,_318,H), $M[_323], $N[_324], $M[_325], $N[_326] ), ( integer.factorial(_330,H), $N[_330] :- 1($~329) '=<'($N,$~329) | 1(H) ), ( integer.factorial(_340,H), $N[_340] :- 2($~339) '>='($N,$~339) | 1(_338), '-'(_344,_338,_337), integer.factorial(_337,_336), '*'(_345,_336,H), $N[_344], $N[_345] ) } )", "L355",var0});
			} else if (execL355(var0,nondeterministic)) {
				ret = true;
				break L354;
			}
		}
		return ret;
	}
	public boolean execL355(Object var0, boolean nondeterministic) {
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
L355:
		{
			mem = ((AbstractMembrane)var0).newMem();
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset601.getInstance());
			func = f0;
			var2 = ((AbstractMembrane)var1).newAtom(func);
			func = f1;
			var3 = ((AbstractMembrane)var1).newAtom(func);
			link = new Link(((Atom)var2), 0);
			var4 = link;
			link = new Link(((Atom)var3), 0);
			var5 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var4),
				((Link)var5));
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L355;
		}
		return ret;
	}
	public boolean execL356(Object var0, Object var1, boolean nondeterministic) {
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
L356:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f0 = new Functor("integer", 1, null);
}

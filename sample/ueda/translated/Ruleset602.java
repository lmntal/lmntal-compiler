package translated;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL261(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL262(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL262(Object var0) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L262:
		{
// spec           [1, 1]
// jump           [L259, [0], [], []]
			if (execL259(var0)) {
				ret = true;
				break L262;
			}
		}
		return ret;
	}
	public boolean execL259(Object var0) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L259:
		{
// spec           [1, 1]
// jump           [L260, [0], [], []]
			if (execL260(var0)) {
				ret = true;
				break L259;
			}
		}
		return ret;
	}
	public boolean execL260(Object var0) {
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
		Object var36 = null;
		Object var37 = null;
		Object var38 = null;
		Object var39 = null;
		Object var40 = null;
		Object var41 = null;
		Object var42 = null;
		Object var43 = null;
		Object var44 = null;
		Object var45 = null;
		Object var46 = null;
		Object var47 = null;
		Object var48 = null;
		Object var49 = null;
		Object var50 = null;
		Object var51 = null;
		Object var52 = null;
		Object var53 = null;
		Object var54 = null;
		Object var55 = null;
		Object var56 = null;
		Object var57 = null;
		Object var58 = null;
		Object var59 = null;
		Object var60 = null;
		Object var61 = null;
		Object var62 = null;
		Object var63 = null;
		Object var64 = null;
		Object var65 = null;
		Object var66 = null;
		Object var67 = null;
		Object var68 = null;
		Object var69 = null;
		Object var70 = null;
		Object var71 = null;
		Object var72 = null;
		Object var73 = null;
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L260:
		{
// spec           [1, 74]
// commit         [null]
// loadruleset    [0, @601]
			((AbstractMembrane)var0).loadRuleset(Ruleset601.getInstance());
// newatom     [1, 0, 9_1]
			func = f0;
			var1 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [2, 0, 6_1]
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [3, 0, 5_1]
			func = f2;
			var3 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [4, 0, 4_1]
			func = f3;
			var4 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [5, 0, 3_1]
			func = f4;
			var5 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [6, 0, 2_1]
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [7, 0, 1_1]
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [8, 0, []_1]
			func = f7;
			var8 = ((AbstractMembrane)var0).newAtom(func);
// newatom     [9, 0, ._3]
			func = f8;
			var9 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [10, 0, ._3]
			func = f8;
			var10 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [11, 0, ._3]
			func = f8;
			var11 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [12, 0, ._3]
			func = f8;
			var12 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [13, 0, ._3]
			func = f8;
			var13 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [14, 0, ._3]
			func = f8;
			var14 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [15, 0, ._3]
			func = f8;
			var15 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [16, 0, []_1]
			func = f7;
			var16 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [17, 0, []_1]
			func = f7;
			var17 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [18, 0, []_1]
			func = f7;
			var18 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [19, 0, []_1]
			func = f7;
			var19 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [20, 0, ._3]
			func = f8;
			var20 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [21, 0, ._3]
			func = f8;
			var21 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [22, 0, ._3]
			func = f8;
			var22 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [23, 0, result1_1]
			func = f9;
			var23 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [24, 0, result2_1]
			func = f10;
			var24 = ((AbstractMembrane)var0).newAtom(func);
// newatom    [25, 0, mergesort_4]
			func = f11;
			var25 = ((AbstractMembrane)var0).newAtom(func);
// alloclink  [26, 1, 0]
			link = new Link(((Atom)var1), 0);
			var26 = link;
// alloclink  [27, 2, 0]
			link = new Link(((Atom)var2), 0);
			var27 = link;
// alloclink  [28, 3, 0]
			link = new Link(((Atom)var3), 0);
			var28 = link;
// alloclink  [29, 4, 0]
			link = new Link(((Atom)var4), 0);
			var29 = link;
// alloclink  [30, 5, 0]
			link = new Link(((Atom)var5), 0);
			var30 = link;
// alloclink  [31, 6, 0]
			link = new Link(((Atom)var6), 0);
			var31 = link;
// alloclink  [32, 7, 0]
			link = new Link(((Atom)var7), 0);
			var32 = link;
// alloclink  [33, 8, 0]
			link = new Link(((Atom)var8), 0);
			var33 = link;
// alloclink  [34, 9, 0]
			link = new Link(((Atom)var9), 0);
			var34 = link;
// alloclink  [35, 9, 1]
			link = new Link(((Atom)var9), 1);
			var35 = link;
// alloclink  [36, 9, 2]
			link = new Link(((Atom)var9), 2);
			var36 = link;
// alloclink  [37, 10, 0]
			link = new Link(((Atom)var10), 0);
			var37 = link;
// alloclink  [38, 10, 1]
			link = new Link(((Atom)var10), 1);
			var38 = link;
// alloclink  [39, 10, 2]
			link = new Link(((Atom)var10), 2);
			var39 = link;
// alloclink  [40, 11, 0]
			link = new Link(((Atom)var11), 0);
			var40 = link;
// alloclink  [41, 11, 1]
			link = new Link(((Atom)var11), 1);
			var41 = link;
// alloclink  [42, 11, 2]
			link = new Link(((Atom)var11), 2);
			var42 = link;
// alloclink  [43, 12, 0]
			link = new Link(((Atom)var12), 0);
			var43 = link;
// alloclink  [44, 12, 1]
			link = new Link(((Atom)var12), 1);
			var44 = link;
// alloclink  [45, 12, 2]
			link = new Link(((Atom)var12), 2);
			var45 = link;
// alloclink  [46, 13, 0]
			link = new Link(((Atom)var13), 0);
			var46 = link;
// alloclink  [47, 13, 1]
			link = new Link(((Atom)var13), 1);
			var47 = link;
// alloclink  [48, 13, 2]
			link = new Link(((Atom)var13), 2);
			var48 = link;
// alloclink  [49, 14, 0]
			link = new Link(((Atom)var14), 0);
			var49 = link;
// alloclink  [50, 14, 1]
			link = new Link(((Atom)var14), 1);
			var50 = link;
// alloclink  [51, 14, 2]
			link = new Link(((Atom)var14), 2);
			var51 = link;
// alloclink  [52, 15, 0]
			link = new Link(((Atom)var15), 0);
			var52 = link;
// alloclink  [53, 15, 1]
			link = new Link(((Atom)var15), 1);
			var53 = link;
// alloclink  [54, 15, 2]
			link = new Link(((Atom)var15), 2);
			var54 = link;
// alloclink  [55, 16, 0]
			link = new Link(((Atom)var16), 0);
			var55 = link;
// alloclink  [56, 17, 0]
			link = new Link(((Atom)var17), 0);
			var56 = link;
// alloclink  [57, 18, 0]
			link = new Link(((Atom)var18), 0);
			var57 = link;
// alloclink  [58, 19, 0]
			link = new Link(((Atom)var19), 0);
			var58 = link;
// alloclink  [59, 20, 0]
			link = new Link(((Atom)var20), 0);
			var59 = link;
// alloclink  [60, 20, 1]
			link = new Link(((Atom)var20), 1);
			var60 = link;
// alloclink  [61, 20, 2]
			link = new Link(((Atom)var20), 2);
			var61 = link;
// alloclink  [62, 21, 0]
			link = new Link(((Atom)var21), 0);
			var62 = link;
// alloclink  [63, 21, 1]
			link = new Link(((Atom)var21), 1);
			var63 = link;
// alloclink  [64, 21, 2]
			link = new Link(((Atom)var21), 2);
			var64 = link;
// alloclink  [65, 22, 0]
			link = new Link(((Atom)var22), 0);
			var65 = link;
// alloclink  [66, 22, 1]
			link = new Link(((Atom)var22), 1);
			var66 = link;
// alloclink  [67, 22, 2]
			link = new Link(((Atom)var22), 2);
			var67 = link;
// alloclink  [68, 23, 0]
			link = new Link(((Atom)var23), 0);
			var68 = link;
// alloclink  [69, 24, 0]
			link = new Link(((Atom)var24), 0);
			var69 = link;
// alloclink  [70, 25, 0]
			link = new Link(((Atom)var25), 0);
			var70 = link;
// alloclink  [71, 25, 1]
			link = new Link(((Atom)var25), 1);
			var71 = link;
// alloclink  [72, 25, 2]
			link = new Link(((Atom)var25), 2);
			var72 = link;
// alloclink  [73, 25, 3]
			link = new Link(((Atom)var25), 3);
			var73 = link;
// unifylinks     [26, 52, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var26),
				((Link)var52));
// unifylinks     [27, 49, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var27),
				((Link)var49));
// unifylinks     [28, 46, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var28),
				((Link)var46));
// unifylinks     [29, 43, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var29),
				((Link)var43));
// unifylinks     [30, 40, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var30),
				((Link)var40));
// unifylinks     [31, 37, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var31),
				((Link)var37));
// unifylinks     [32, 34, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var32),
				((Link)var34));
// unifylinks     [33, 35, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var33),
				((Link)var35));
// unifylinks     [36, 38, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var36),
				((Link)var38));
// unifylinks     [39, 41, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var39),
				((Link)var41));
// unifylinks     [42, 44, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var42),
				((Link)var44));
// unifylinks     [45, 47, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var45),
				((Link)var47));
// unifylinks     [48, 50, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var48),
				((Link)var50));
// unifylinks     [51, 53, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var51),
				((Link)var53));
// unifylinks     [54, 70, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var54),
				((Link)var70));
// unifylinks     [55, 65, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var55),
				((Link)var65));
// unifylinks     [56, 62, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var56),
				((Link)var62));
// unifylinks     [57, 59, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var57),
				((Link)var59));
// unifylinks     [58, 60, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var58),
				((Link)var60));
// unifylinks     [61, 63, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var61),
				((Link)var63));
// unifylinks     [64, 66, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var64),
				((Link)var66));
// unifylinks     [67, 71, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var67),
				((Link)var71));
// unifylinks     [68, 72, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var68),
				((Link)var72));
// unifylinks     [69, 73, 0]
			mem = ((AbstractMembrane)var0); // 正規のコード
			mem.unifyLinkBuddies(
				((Link)var69),
				((Link)var73));
// enqueueatom    [25]
			atom = ((Atom)var25);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [24]
			atom = ((Atom)var24);
			atom.getMem().enqueueAtom(atom);
// enqueueatom    [23]
			atom = ((Atom)var23);
			atom.getMem().enqueueAtom(atom);
// proceed        []
			ret = true;
			break L260;
		}
		return ret;
	}
	public boolean execL261(Object var0, Object var1) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;		boolean eqground_ret;		boolean ret = false;
L261:
		{
// spec           [2, 2]
		}
		return ret;
	}
	private static final Functor f5 = new IntegerFunctor(2);
	private static final Functor f11 = new Functor("mergesort", 4, null);
	private static final Functor f3 = new IntegerFunctor(4);
	private static final Functor f0 = new IntegerFunctor(9);
	private static final Functor f10 = new Functor("result2", 1, null);
	private static final Functor f8 = new Functor(".", 3, null);
	private static final Functor f9 = new Functor("result1", 1, null);
	private static final Functor f1 = new IntegerFunctor(6);
	private static final Functor f6 = new IntegerFunctor(1);
	private static final Functor f7 = new Functor("[]", 1, null);
	private static final Functor f4 = new IntegerFunctor(3);
	private static final Functor f2 = new IntegerFunctor(5);
}

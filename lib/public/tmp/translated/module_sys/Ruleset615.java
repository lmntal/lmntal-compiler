package translated.module_sys;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":sys" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@sys" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1116(mem, atom, false)) {
			return true;
		}
		if (execL1123(mem, atom, false)) {
			return true;
		}
		if (execL1132(mem, atom, false)) {
			return true;
		}
		if (execL1141(mem, atom, false)) {
			return true;
		}
		if (execL1148(mem, atom, false)) {
			return true;
		}
		if (execL1155(mem, atom, false)) {
			return true;
		}
		if (execL1164(mem, atom, false)) {
			return true;
		}
		if (execL1173(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1119(mem, nondeterministic)) {
			return true;
		}
		if (execL1128(mem, nondeterministic)) {
			return true;
		}
		if (execL1137(mem, nondeterministic)) {
			return true;
		}
		if (execL1144(mem, nondeterministic)) {
			return true;
		}
		if (execL1151(mem, nondeterministic)) {
			return true;
		}
		if (execL1160(mem, nondeterministic)) {
			return true;
		}
		if (execL1169(mem, nondeterministic)) {
			return true;
		}
		if (execL1176(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL1176(Object var0, boolean nondeterministic) {
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
L1176:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
						if (execL1172(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1176;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1172(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1172:
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
			func = f1;
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
			SomeInlineCodesys.run((Atom)var6, 7);
			ret = true;
			break L1172;
		}
		return ret;
	}
	public boolean execL1173(Object var0, Object var1, boolean nondeterministic) {
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
L1173:
		{
			if (execL1175(var0, var1, nondeterministic)) {
				ret = true;
				break L1173;
			}
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
		Set insset;
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
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					var5 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					func = ((Atom)var6).getFunctor();
					if (!(func.getArity() != 1)) {
						if (!(!(((Atom)var5).getFunctor() instanceof IntegerFunctor))) {
							if (execL1172(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1175;
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
		Set insset;
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
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "L1163",var0,var1,var2});
						} else if (execL1163(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1169;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1163(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1163:
		{
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
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 6);
			ret = true;
			break L1163;
		}
		return ret;
	}
	public boolean execL1164(Object var0, Object var1, boolean nondeterministic) {
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
L1164:
		{
			if (execL1166(var0, var1, nondeterministic)) {
				ret = true;
				break L1164;
			}
			if (execL1168(var0, var1, nondeterministic)) {
				ret = true;
				break L1164;
			}
		}
		return ret;
	}
	public boolean execL1168(Object var0, Object var1, boolean nondeterministic) {
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
L1168:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1163",var0,var3,var1});
							} else if (execL1163(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1168;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1166(Object var0, Object var1, boolean nondeterministic) {
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
L1166:
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
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1163",var0,var1,var3});
							} else if (execL1163(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1166;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1160(Object var0, boolean nondeterministic) {
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
L1160:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "L1154",var0,var1,var2});
						} else if (execL1154(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1160;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1154(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1154:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f6;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 5);
			ret = true;
			break L1154;
		}
		return ret;
	}
	public boolean execL1155(Object var0, Object var1, boolean nondeterministic) {
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
L1155:
		{
			if (execL1157(var0, var1, nondeterministic)) {
				ret = true;
				break L1155;
			}
			if (execL1159(var0, var1, nondeterministic)) {
				ret = true;
				break L1155;
			}
		}
		return ret;
	}
	public boolean execL1159(Object var0, Object var1, boolean nondeterministic) {
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
L1159:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1154",var0,var3,var1});
							} else if (execL1154(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1159;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1157(Object var0, Object var1, boolean nondeterministic) {
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
L1157:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1154",var0,var1,var3});
							} else if (execL1154(var0,var1,var3,nondeterministic)) {
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
	public boolean execL1151(Object var0, boolean nondeterministic) {
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
L1151:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1147(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1151;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1147(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1147:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f8;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 2,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var4, 4);
			ret = true;
			break L1147;
		}
		return ret;
	}
	public boolean execL1148(Object var0, Object var1, boolean nondeterministic) {
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
L1148:
		{
			if (execL1150(var0, var1, nondeterministic)) {
				ret = true;
				break L1148;
			}
		}
		return ret;
	}
	public boolean execL1150(Object var0, Object var1, boolean nondeterministic) {
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
L1150:
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
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1147(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1150;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1144(Object var0, boolean nondeterministic) {
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
L1144:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1140(var0,var1,nondeterministic)) {
					ret = true;
					break L1144;
				}
			}
		}
		return ret;
	}
	public boolean execL1140(Object var0, Object var1, boolean nondeterministic) {
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
L1140:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f10;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodesys.run((Atom)var2, 3);
			ret = true;
			break L1140;
		}
		return ret;
	}
	public boolean execL1141(Object var0, Object var1, boolean nondeterministic) {
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
L1141:
		{
			if (execL1143(var0, var1, nondeterministic)) {
				ret = true;
				break L1141;
			}
		}
		return ret;
	}
	public boolean execL1143(Object var0, Object var1, boolean nondeterministic) {
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
L1143:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL1140(var0,var1,nondeterministic)) {
						ret = true;
						break L1143;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1137(Object var0, boolean nondeterministic) {
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
L1137:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "L1131",var0,var1,var2});
						} else if (execL1131(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1137;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1131(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1131:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f12;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 2);
			ret = true;
			break L1131;
		}
		return ret;
	}
	public boolean execL1132(Object var0, Object var1, boolean nondeterministic) {
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
L1132:
		{
			if (execL1134(var0, var1, nondeterministic)) {
				ret = true;
				break L1132;
			}
			if (execL1136(var0, var1, nondeterministic)) {
				ret = true;
				break L1132;
			}
		}
		return ret;
	}
	public boolean execL1136(Object var0, Object var1, boolean nondeterministic) {
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
L1136:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1131",var0,var3,var1});
							} else if (execL1131(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1136;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1134(Object var0, Object var1, boolean nondeterministic) {
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
L1134:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1131",var0,var1,var3});
							} else if (execL1131(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1134;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1128(Object var0, boolean nondeterministic) {
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
L1128:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f11).equals(((Atom)var2).getFunctor()))) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "L1122",var0,var1,var2});
						} else if (execL1122(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1128;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1122(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1122:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f13;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var3, 1);
			ret = true;
			break L1122;
		}
		return ret;
	}
	public boolean execL1123(Object var0, Object var1, boolean nondeterministic) {
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
L1123:
		{
			if (execL1125(var0, var1, nondeterministic)) {
				ret = true;
				break L1123;
			}
			if (execL1127(var0, var1, nondeterministic)) {
				ret = true;
				break L1123;
			}
		}
		return ret;
	}
	public boolean execL1127(Object var0, Object var1, boolean nondeterministic) {
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
L1127:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f5).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1122",var0,var3,var1});
							} else if (execL1122(var0,var3,var1,nondeterministic)) {
								ret = true;
								break L1127;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1125(Object var0, Object var1, boolean nondeterministic) {
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
L1125:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 0)) {
						var3 = link.getAtom();
						if (!(!(f11).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1122",var0,var1,var3});
							} else if (execL1122(var0,var1,var3,nondeterministic)) {
								ret = true;
								break L1125;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1119(Object var0, boolean nondeterministic) {
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
L1119:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "L1115",var0,var1});
				} else if (execL1115(var0,var1,nondeterministic)) {
					ret = true;
					break L1119;
				}
			}
		}
		return ret;
	}
	public boolean execL1115(Object var0, Object var1, boolean nondeterministic) {
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
L1115:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f15;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesys.run((Atom)var2, 0);
			ret = true;
			break L1115;
		}
		return ret;
	}
	public boolean execL1116(Object var0, Object var1, boolean nondeterministic) {
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
L1116:
		{
			if (execL1118(var0, var1, nondeterministic)) {
				ret = true;
				break L1116;
			}
		}
		return ret;
	}
	public boolean execL1118(Object var0, Object var1, boolean nondeterministic) {
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
L1118:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "L1115",var0,var1});
					} else if (execL1115(var0,var1,nondeterministic)) {
						ret = true;
						break L1118;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f9 = new Functor("argv", 1, "sys");
	private static final Functor f11 = new Functor("trace", 1, "sys");
	private static final Functor f4 = new Functor("/*inline*/\r\n\tmem.perpetual = false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f2 = new Functor("off", 1, null);
	private static final Functor f3 = new Functor("perpetual", 1, "sys");
	private static final Functor f5 = new Functor("on", 1, null);
	private static final Functor f1 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tint count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));\r\n\t\tAtom p = mem.newAtom(new IntegerFunctor(count));\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom p = mem.newAtom(new ObjectFunctor(\r\n\t\t\tRuntime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), \"str\") )));\r\n//\tSystem.out.println(p);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.nthAtom(1).remove();\r\n\t\tmem.relink(p, 0, me, 2);\r\n\t\tme.remove();\r\n\t} catch (Exception e) {\r\n\t\te.printStackTrace();\r\n\t}\r\n\t", 3, null);
	private static final Functor f10 = new StringFunctor("/*inline*/\r\n\tutil.Util.makeList(me.getArg(0), Env.argv);\r\n\tme.remove();\r\n\t");
	private static final Functor f7 = new Functor("exec", 3, "sys");
	private static final Functor f12 = new Functor("/*inline*/\r\n\tEnv.fTrace=false;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f14 = new Functor("dump", 0, "sys");
	private static final Functor f0 = new Functor("atomCount", 3, "sys");
	private static final Functor f13 = new Functor("/*inline*/\r\n\tEnv.fTrace=true;\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n\tme.setName(\"nil\");\r\n\tSystem.out.println(Dumper.dump(mem));\r\n\t", 0, null);
	private static final Functor f6 = new Functor("/*inline*/\r\n\tmem.makePerpetual();\r\n\tme.setName(\"nil\");\r\n\t", 0, null);
}

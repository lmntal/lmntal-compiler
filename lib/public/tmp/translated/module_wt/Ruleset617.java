package translated.module_wt;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":wt" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@wt" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL1185(mem, atom, false)) {
			return true;
		}
		if (execL1196(mem, atom, false)) {
			return true;
		}
		if (execL1203(mem, atom, false)) {
			return true;
		}
		if (execL1212(mem, atom, false)) {
			return true;
		}
		if (execL1221(mem, atom, false)) {
			return true;
		}
		if (execL1230(mem, atom, false)) {
			return true;
		}
		if (execL1239(mem, atom, false)) {
			return true;
		}
		if (execL1250(mem, atom, false)) {
			return true;
		}
		if (execL1261(mem, atom, false)) {
			return true;
		}
		if (execL1270(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1192(mem, nondeterministic)) {
			return true;
		}
		if (execL1199(mem, nondeterministic)) {
			return true;
		}
		if (execL1208(mem, nondeterministic)) {
			return true;
		}
		if (execL1217(mem, nondeterministic)) {
			return true;
		}
		if (execL1226(mem, nondeterministic)) {
			return true;
		}
		if (execL1235(mem, nondeterministic)) {
			return true;
		}
		if (execL1246(mem, nondeterministic)) {
			return true;
		}
		if (execL1257(mem, nondeterministic)) {
			return true;
		}
		if (execL1266(mem, nondeterministic)) {
			return true;
		}
		if (execL1273(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL1273(Object var0, boolean nondeterministic) {
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
L1273:
		{
			Iterator it1 = ((AbstractMembrane)var0).memIterator();
			while (it1.hasNext()) {
				mem = (AbstractMembrane) it1.next();
				if (mem.lock()) {
					var1 = mem;
					func = f0;
					Iterator it2 = ((AbstractMembrane)var1).atomIteratorOfFunctor(func);
					while (it2.hasNext()) {
						atom = (Atom) it2.next();
						var2 = atom;
						mem = ((AbstractMembrane)var1);
						if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 0)) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1269",var0,var1,var2});
							} else if (execL1269(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L1273;
							}
						}
					}
					((AbstractMembrane)var1).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL1269(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1269:
		{
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var0).moveCellsFrom(((AbstractMembrane)var1));
			((AbstractMembrane)var0).removeTemporaryProxies();
			((AbstractMembrane)var1).free();
			ret = true;
			break L1269;
		}
		return ret;
	}
	public boolean execL1270(Object var0, Object var1, boolean nondeterministic) {
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
L1270:
		{
			if (execL1272(var0, var1, nondeterministic)) {
				ret = true;
				break L1270;
			}
		}
		return ret;
	}
	public boolean execL1272(Object var0, Object var1, boolean nondeterministic) {
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
L1272:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				var2 = ((Atom)var1).getMem();
				mem = ((AbstractMembrane)var2);
				if (mem.lock()) {
					mem = ((AbstractMembrane)var2).getParent();
					if (!(mem == null)) {
						var3 = mem;
						mem = ((AbstractMembrane)var2);
						if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 0)) {
							if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1269",var0,var2,var1});
								} else if (execL1269(var0,var2,var1,nondeterministic)) {
									ret = true;
									break L1272;
								}
							}
						}
					}
					((AbstractMembrane)var2).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL1266(Object var0, boolean nondeterministic) {
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
L1266:
		{
			var3 = new Atom(null, f1);
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f3;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1260",var0,var1,var2,var3,var4});
							} else if (execL1260(var0,var1,var2,var3,var4,nondeterministic)) {
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
	public boolean execL1260(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1260:
		{
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
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f4;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var6, 7);
			ret = true;
			break L1260;
		}
		return ret;
	}
	public boolean execL1261(Object var0, Object var1, boolean nondeterministic) {
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
L1265:
		{
			var4 = new Atom(null, f1);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var6 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
							func = f2;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1260",var0,var3,var1,var4,var5});
								} else if (execL1260(var0,var3,var1,var4,var5,nondeterministic)) {
									ret = true;
									break L1265;
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
L1263:
		{
			var4 = new Atom(null, f1);
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					func = f3;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						var3 = link;
						link = ((Atom)var2).getArg(0);
						var5 = link.getAtom();
						if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_wt.", "");
								var6 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1260",var0,var1,var2,var4,var5});
								} else if (execL1260(var0,var1,var2,var4,var5,nondeterministic)) {
									ret = true;
									break L1263;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1257(Object var0, boolean nondeterministic) {
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
L1257:
		{
			var7 = new Atom(null, f1);
			var4 = new Atom(null, f5);
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var10 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var11 = link.getAtom();
				link = ((Atom)var1).getArg(2);
				var12 = link.getAtom();
				if (((Atom)var12).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var12).getFunctor()).getObject() instanceof String) {
					if (((Atom)var11).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var11).getFunctor()).getObject() instanceof String) {
						if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
							func = f7;
							Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it2.hasNext()) {
								atom = (Atom) it2.next();
								var2 = atom;
								link = ((Atom)var2).getArg(0);
								var5 = link.getAtom();
								if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_wt.", "");
										var6 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
										func = f3;
										Iterator it3 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
										while (it3.hasNext()) {
											atom = (Atom) it3.next();
											var3 = atom;
											link = ((Atom)var3).getArg(0);
											var8 = link.getAtom();
											if (!(!(((Atom)var8).getFunctor() instanceof ObjectFunctor))) {
												{
													Object obj = ((ObjectFunctor)((Atom)var8).getFunctor()).getObject();
													String className = obj.getClass().getName();
													className = className.replaceAll("translated.module_wt.", "");
													var9 = new Atom(null, new StringFunctor( className ));
												}
												if (!(!((Atom)var9).getFunctor().equals(((Atom)var7).getFunctor()))) {
													if (nondeterministic) {
														Task.states.add(new Object[] {theInstance, "L1249",var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var12});
													} else if (execL1249(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var12,nondeterministic)) {
														ret = true;
														break L1257;
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
	public boolean execL1249(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, boolean nondeterministic) {
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
L1249:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var8);
			atom.dequeue();
			atom = ((Atom)var8);
			atom.getMem().removeAtom(atom);
			var11 = ((AbstractMembrane)var0).newAtom(((Atom)var10).getFunctor());
			var12 = ((AbstractMembrane)var0).newAtom(((Atom)var9).getFunctor());
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var14 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var15 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var16 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var17 = ((AbstractMembrane)var0).newAtom(((Atom)var8).getFunctor());
			func = f8;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var13), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var15), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 0,
				((Atom)var14), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 1,
				((Atom)var17), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 2,
				((Atom)var12), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 3,
				((Atom)var11), 0 );
			((Atom)var20).getMem().newLink(
				((Atom)var20), 4,
				((Atom)var16), 0 );
			atom = ((Atom)var20);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var20, 6);
			ret = true;
			break L1249;
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
L1250:
		{
			if (execL1252(var0, var1, nondeterministic)) {
				ret = true;
				break L1250;
			}
			if (execL1254(var0, var1, nondeterministic)) {
				ret = true;
				break L1250;
			}
			if (execL1256(var0, var1, nondeterministic)) {
				ret = true;
				break L1250;
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
L1256:
		{
			var12 = new Atom(null, f1);
			var9 = new Atom(null, f5);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var13 = link.getAtom();
					if (!(!(((Atom)var13).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var13).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var14 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var14).getFunctor().equals(((Atom)var12).getFunctor()))) {
							func = f6;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(2);
								var6 = link;
								link = ((Atom)var3).getArg(0);
								var15 = link.getAtom();
								link = ((Atom)var3).getArg(1);
								var16 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								var17 = link.getAtom();
								if (((Atom)var17).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var17).getFunctor()).getObject() instanceof String) {
									if (((Atom)var16).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var16).getFunctor()).getObject() instanceof String) {
										if (((Atom)var15).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var15).getFunctor()).getObject() instanceof String) {
											func = f7;
											Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
											while (it2.hasNext()) {
												atom = (Atom) it2.next();
												var7 = atom;
												link = ((Atom)var7).getArg(0);
												var8 = link;
												link = ((Atom)var7).getArg(0);
												var10 = link.getAtom();
												if (!(!(((Atom)var10).getFunctor() instanceof ObjectFunctor))) {
													{
														Object obj = ((ObjectFunctor)((Atom)var10).getFunctor()).getObject();
														String className = obj.getClass().getName();
														className = className.replaceAll("translated.module_wt.", "");
														var11 = new Atom(null, new StringFunctor( className ));
													}
													if (!(!((Atom)var11).getFunctor().equals(((Atom)var9).getFunctor()))) {
														if (nondeterministic) {
															Task.states.add(new Object[] {theInstance, "L1249",var0,var3,var7,var1,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL1249(var0,var3,var7,var1,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L1256;
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
	public boolean execL1254(Object var0, Object var1, boolean nondeterministic) {
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
L1254:
		{
			var12 = new Atom(null, f1);
			var9 = new Atom(null, f5);
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var10 = link.getAtom();
					if (!(!(((Atom)var10).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var10).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var11 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var11).getFunctor().equals(((Atom)var9).getFunctor()))) {
							func = f6;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(2);
								var6 = link;
								link = ((Atom)var3).getArg(0);
								var15 = link.getAtom();
								link = ((Atom)var3).getArg(1);
								var16 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								var17 = link.getAtom();
								if (((Atom)var17).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var17).getFunctor()).getObject() instanceof String) {
									if (((Atom)var16).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var16).getFunctor()).getObject() instanceof String) {
										if (((Atom)var15).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var15).getFunctor()).getObject() instanceof String) {
											func = f3;
											Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
											while (it2.hasNext()) {
												atom = (Atom) it2.next();
												var7 = atom;
												link = ((Atom)var7).getArg(0);
												var8 = link;
												link = ((Atom)var7).getArg(0);
												var13 = link.getAtom();
												if (!(!(((Atom)var13).getFunctor() instanceof ObjectFunctor))) {
													{
														Object obj = ((ObjectFunctor)((Atom)var13).getFunctor()).getObject();
														String className = obj.getClass().getName();
														className = className.replaceAll("translated.module_wt.", "");
														var14 = new Atom(null, new StringFunctor( className ));
													}
													if (!(!((Atom)var14).getFunctor().equals(((Atom)var12).getFunctor()))) {
														if (nondeterministic) {
															Task.states.add(new Object[] {theInstance, "L1249",var0,var3,var1,var7,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL1249(var0,var3,var1,var7,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L1254;
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
	public boolean execL1252(Object var0, Object var1, boolean nondeterministic) {
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
L1252:
		{
			var12 = new Atom(null, f1);
			var9 = new Atom(null, f5);
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var15 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var16 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					var17 = link.getAtom();
					if (((Atom)var17).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var17).getFunctor()).getObject() instanceof String) {
						if (((Atom)var16).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var16).getFunctor()).getObject() instanceof String) {
							if (((Atom)var15).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var15).getFunctor()).getObject() instanceof String) {
								func = f7;
								Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(0);
									var10 = link.getAtom();
									if (!(!(((Atom)var10).getFunctor() instanceof ObjectFunctor))) {
										{
											Object obj = ((ObjectFunctor)((Atom)var10).getFunctor()).getObject();
											String className = obj.getClass().getName();
											className = className.replaceAll("translated.module_wt.", "");
											var11 = new Atom(null, new StringFunctor( className ));
										}
										if (!(!((Atom)var11).getFunctor().equals(((Atom)var9).getFunctor()))) {
											func = f3;
											Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
											while (it2.hasNext()) {
												atom = (Atom) it2.next();
												var7 = atom;
												link = ((Atom)var7).getArg(0);
												var8 = link;
												link = ((Atom)var7).getArg(0);
												var13 = link.getAtom();
												if (!(!(((Atom)var13).getFunctor() instanceof ObjectFunctor))) {
													{
														Object obj = ((ObjectFunctor)((Atom)var13).getFunctor()).getObject();
														String className = obj.getClass().getName();
														className = className.replaceAll("translated.module_wt.", "");
														var14 = new Atom(null, new StringFunctor( className ));
													}
													if (!(!((Atom)var14).getFunctor().equals(((Atom)var12).getFunctor()))) {
														if (nondeterministic) {
															Task.states.add(new Object[] {theInstance, "L1249",var0,var1,var5,var7,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL1249(var0,var1,var5,var7,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L1252;
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
	public boolean execL1246(Object var0, boolean nondeterministic) {
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
L1246:
		{
			var10 = new Atom(null, f1);
			var7 = new Atom(null, f9);
			var4 = new Atom(null, f5);
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var13 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var14 = link.getAtom();
				link = ((Atom)var1).getArg(2);
				var15 = link.getAtom();
				if (((Atom)var15).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var15).getFunctor()).getObject() instanceof String) {
					if (!(!(((Atom)var14).getFunctor() instanceof IntegerFunctor))) {
						if (((Atom)var13).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var13).getFunctor()).getObject() instanceof String) {
							func = f10;
							Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it2.hasNext()) {
								atom = (Atom) it2.next();
								var2 = atom;
								link = ((Atom)var2).getArg(0);
								var5 = link.getAtom();
								link = ((Atom)var2).getArg(1);
								var8 = link.getAtom();
								if (!(!(((Atom)var8).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var8).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_wt.", "");
										var9 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var9).getFunctor().equals(((Atom)var7).getFunctor()))) {
										if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
											{
												Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
												String className = obj.getClass().getName();
												className = className.replaceAll("translated.module_wt.", "");
												var6 = new Atom(null, new StringFunctor( className ));
											}
											if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
												func = f3;
												Iterator it3 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
												while (it3.hasNext()) {
													atom = (Atom) it3.next();
													var3 = atom;
													link = ((Atom)var3).getArg(0);
													var11 = link.getAtom();
													if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
														{
															Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
															String className = obj.getClass().getName();
															className = className.replaceAll("translated.module_wt.", "");
															var12 = new Atom(null, new StringFunctor( className ));
														}
														if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
															if (nondeterministic) {
																Task.states.add(new Object[] {theInstance, "L1238",var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var13,var14,var15});
															} else if (execL1238(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var13,var14,var15,nondeterministic)) {
																ret = true;
																break L1246;
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
	public boolean execL1238(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, boolean nondeterministic) {
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
L1238:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var12);
			atom.dequeue();
			atom = ((Atom)var12);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var7);
			atom.dequeue();
			atom = ((Atom)var7);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var11);
			atom.dequeue();
			atom = ((Atom)var11);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var9);
			atom.dequeue();
			atom = ((Atom)var9);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var10);
			atom.dequeue();
			atom = ((Atom)var10);
			atom.getMem().removeAtom(atom);
			var13 = ((AbstractMembrane)var0).newAtom(((Atom)var12).getFunctor());
			var14 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var15 = ((AbstractMembrane)var0).newAtom(((Atom)var7).getFunctor());
			var16 = ((AbstractMembrane)var0).newAtom(((Atom)var11).getFunctor());
			var17 = ((AbstractMembrane)var0).newAtom(((Atom)var9).getFunctor());
			var18 = ((AbstractMembrane)var0).newAtom(((Atom)var9).getFunctor());
			var19 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var20 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var21 = ((AbstractMembrane)var0).newAtom(((Atom)var10).getFunctor());
			func = f11;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var19), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var14), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var17), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 0,
				((Atom)var20), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 1,
				((Atom)var15), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 2,
				((Atom)var21), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 3,
				((Atom)var16), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 4,
				((Atom)var13), 0 );
			((Atom)var24).getMem().newLink(
				((Atom)var24), 5,
				((Atom)var18), 0 );
			atom = ((Atom)var24);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var24, 5);
			ret = true;
			break L1238;
		}
		return ret;
	}
	public boolean execL1239(Object var0, Object var1, boolean nondeterministic) {
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
L1239:
		{
			if (execL1241(var0, var1, nondeterministic)) {
				ret = true;
				break L1239;
			}
			if (execL1243(var0, var1, nondeterministic)) {
				ret = true;
				break L1239;
			}
			if (execL1245(var0, var1, nondeterministic)) {
				ret = true;
				break L1239;
			}
		}
		return ret;
	}
	public boolean execL1245(Object var0, Object var1, boolean nondeterministic) {
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
L1245:
		{
			var16 = new Atom(null, f1);
			var13 = new Atom(null, f9);
			var10 = new Atom(null, f5);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var17 = link.getAtom();
					if (!(!(((Atom)var17).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var17).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var18 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var18).getFunctor().equals(((Atom)var16).getFunctor()))) {
							func = f6;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(2);
								var6 = link;
								link = ((Atom)var3).getArg(0);
								var19 = link.getAtom();
								link = ((Atom)var3).getArg(1);
								var20 = link.getAtom();
								link = ((Atom)var3).getArg(2);
								var21 = link.getAtom();
								if (((Atom)var21).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var21).getFunctor()).getObject() instanceof String) {
									if (!(!(((Atom)var20).getFunctor() instanceof IntegerFunctor))) {
										if (((Atom)var19).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var19).getFunctor()).getObject() instanceof String) {
											func = f10;
											Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
											while (it2.hasNext()) {
												atom = (Atom) it2.next();
												var7 = atom;
												link = ((Atom)var7).getArg(0);
												var8 = link;
												link = ((Atom)var7).getArg(1);
												var9 = link;
												link = ((Atom)var7).getArg(0);
												var11 = link.getAtom();
												link = ((Atom)var7).getArg(1);
												var14 = link.getAtom();
												if (!(!(((Atom)var14).getFunctor() instanceof ObjectFunctor))) {
													{
														Object obj = ((ObjectFunctor)((Atom)var14).getFunctor()).getObject();
														String className = obj.getClass().getName();
														className = className.replaceAll("translated.module_wt.", "");
														var15 = new Atom(null, new StringFunctor( className ));
													}
													if (!(!((Atom)var15).getFunctor().equals(((Atom)var13).getFunctor()))) {
														if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
															{
																Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
																String className = obj.getClass().getName();
																className = className.replaceAll("translated.module_wt.", "");
																var12 = new Atom(null, new StringFunctor( className ));
															}
															if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
																if (nondeterministic) {
																	Task.states.add(new Object[] {theInstance, "L1238",var0,var3,var7,var1,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL1238(var0,var3,var7,var1,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L1245;
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
	public boolean execL1243(Object var0, Object var1, boolean nondeterministic) {
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
L1243:
		{
			var16 = new Atom(null, f1);
			var13 = new Atom(null, f9);
			var10 = new Atom(null, f5);
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var11 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var14 = link.getAtom();
					if (!(!(((Atom)var14).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var14).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var15 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var15).getFunctor().equals(((Atom)var13).getFunctor()))) {
							if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_wt.", "");
									var12 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
									func = f6;
									Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
									while (it1.hasNext()) {
										atom = (Atom) it1.next();
										var4 = atom;
										link = ((Atom)var4).getArg(0);
										var5 = link;
										link = ((Atom)var4).getArg(1);
										var6 = link;
										link = ((Atom)var4).getArg(2);
										var7 = link;
										link = ((Atom)var4).getArg(0);
										var19 = link.getAtom();
										link = ((Atom)var4).getArg(1);
										var20 = link.getAtom();
										link = ((Atom)var4).getArg(2);
										var21 = link.getAtom();
										if (((Atom)var21).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var21).getFunctor()).getObject() instanceof String) {
											if (!(!(((Atom)var20).getFunctor() instanceof IntegerFunctor))) {
												if (((Atom)var19).getFunctor() instanceof ObjectFunctor &&
												    ((ObjectFunctor)((Atom)var19).getFunctor()).getObject() instanceof String) {
													func = f3;
													Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
													while (it2.hasNext()) {
														atom = (Atom) it2.next();
														var8 = atom;
														link = ((Atom)var8).getArg(0);
														var9 = link;
														link = ((Atom)var8).getArg(0);
														var17 = link.getAtom();
														if (!(!(((Atom)var17).getFunctor() instanceof ObjectFunctor))) {
															{
																Object obj = ((ObjectFunctor)((Atom)var17).getFunctor()).getObject();
																String className = obj.getClass().getName();
																className = className.replaceAll("translated.module_wt.", "");
																var18 = new Atom(null, new StringFunctor( className ));
															}
															if (!(!((Atom)var18).getFunctor().equals(((Atom)var16).getFunctor()))) {
																if (nondeterministic) {
																	Task.states.add(new Object[] {theInstance, "L1238",var0,var4,var1,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL1238(var0,var4,var1,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L1243;
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
	public boolean execL1241(Object var0, Object var1, boolean nondeterministic) {
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
L1241:
		{
			var16 = new Atom(null, f1);
			var13 = new Atom(null, f9);
			var10 = new Atom(null, f5);
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var19 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var20 = link.getAtom();
					link = ((Atom)var1).getArg(2);
					var21 = link.getAtom();
					if (((Atom)var21).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var21).getFunctor()).getObject() instanceof String) {
						if (!(!(((Atom)var20).getFunctor() instanceof IntegerFunctor))) {
							if (((Atom)var19).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var19).getFunctor()).getObject() instanceof String) {
								func = f10;
								Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var5 = atom;
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(1);
									var7 = link;
									link = ((Atom)var5).getArg(0);
									var11 = link.getAtom();
									link = ((Atom)var5).getArg(1);
									var14 = link.getAtom();
									if (!(!(((Atom)var14).getFunctor() instanceof ObjectFunctor))) {
										{
											Object obj = ((ObjectFunctor)((Atom)var14).getFunctor()).getObject();
											String className = obj.getClass().getName();
											className = className.replaceAll("translated.module_wt.", "");
											var15 = new Atom(null, new StringFunctor( className ));
										}
										if (!(!((Atom)var15).getFunctor().equals(((Atom)var13).getFunctor()))) {
											if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
												{
													Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
													String className = obj.getClass().getName();
													className = className.replaceAll("translated.module_wt.", "");
													var12 = new Atom(null, new StringFunctor( className ));
												}
												if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
													func = f3;
													Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
													while (it2.hasNext()) {
														atom = (Atom) it2.next();
														var8 = atom;
														link = ((Atom)var8).getArg(0);
														var9 = link;
														link = ((Atom)var8).getArg(0);
														var17 = link.getAtom();
														if (!(!(((Atom)var17).getFunctor() instanceof ObjectFunctor))) {
															{
																Object obj = ((ObjectFunctor)((Atom)var17).getFunctor()).getObject();
																String className = obj.getClass().getName();
																className = className.replaceAll("translated.module_wt.", "");
																var18 = new Atom(null, new StringFunctor( className ));
															}
															if (!(!((Atom)var18).getFunctor().equals(((Atom)var16).getFunctor()))) {
																if (nondeterministic) {
																	Task.states.add(new Object[] {theInstance, "L1238",var0,var1,var5,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL1238(var0,var1,var5,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L1241;
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
	public boolean execL1235(Object var0, boolean nondeterministic) {
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
L1235:
		{
			var3 = new Atom(null, f1);
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f3;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (nondeterministic) {
								Task.states.add(new Object[] {theInstance, "L1229",var0,var1,var2,var3,var4});
							} else if (execL1229(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L1235;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1229(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1229:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f13;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var8, 4);
			ret = true;
			break L1229;
		}
		return ret;
	}
	public boolean execL1230(Object var0, Object var1, boolean nondeterministic) {
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
L1230:
		{
			if (execL1232(var0, var1, nondeterministic)) {
				ret = true;
				break L1230;
			}
			if (execL1234(var0, var1, nondeterministic)) {
				ret = true;
				break L1230;
			}
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
		Set insset;
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
			var4 = new Atom(null, f1);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var6 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
							func = f12;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1229",var0,var3,var1,var4,var5});
								} else if (execL1229(var0,var3,var1,var4,var5,nondeterministic)) {
									ret = true;
									break L1234;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1232(Object var0, Object var1, boolean nondeterministic) {
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
L1232:
		{
			var4 = new Atom(null, f1);
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					func = f3;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						var3 = link;
						link = ((Atom)var2).getArg(0);
						var5 = link.getAtom();
						if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_wt.", "");
								var6 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1229",var0,var1,var2,var4,var5});
								} else if (execL1229(var0,var1,var2,var4,var5,nondeterministic)) {
									ret = true;
									break L1232;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1226(Object var0, boolean nondeterministic) {
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
L1226:
		{
			var3 = new Atom(null, f1);
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var6 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var7 = link.getAtom();
				if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						func = f3;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var2 = atom;
							link = ((Atom)var2).getArg(0);
							var4 = link.getAtom();
							if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_wt.", "");
									var5 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "L1220",var0,var1,var2,var3,var4,var6,var7});
									} else if (execL1220(var0,var1,var2,var3,var4,var6,var7,nondeterministic)) {
										ret = true;
										break L1226;
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
	public boolean execL1220(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1220:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f15;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var8), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 1,
				((Atom)var10), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var9), 0 );
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var12, 3);
			ret = true;
			break L1220;
		}
		return ret;
	}
	public boolean execL1221(Object var0, Object var1, boolean nondeterministic) {
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
L1221:
		{
			if (execL1223(var0, var1, nondeterministic)) {
				ret = true;
				break L1221;
			}
			if (execL1225(var0, var1, nondeterministic)) {
				ret = true;
				break L1221;
			}
		}
		return ret;
	}
	public boolean execL1225(Object var0, Object var1, boolean nondeterministic) {
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
L1225:
		{
			var6 = new Atom(null, f1);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f14;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(0);
								var9 = link.getAtom();
								link = ((Atom)var3).getArg(1);
								var10 = link.getAtom();
								if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
									if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "L1220",var0,var3,var1,var6,var7,var9,var10});
										} else if (execL1220(var0,var3,var1,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L1225;
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
	public boolean execL1223(Object var0, Object var1, boolean nondeterministic) {
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
L1223:
		{
			var6 = new Atom(null, f1);
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var9 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var10 = link.getAtom();
					if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
							func = f3;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var4 = atom;
								link = ((Atom)var4).getArg(0);
								var5 = link;
								link = ((Atom)var4).getArg(0);
								var7 = link.getAtom();
								if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_wt.", "");
										var8 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "L1220",var0,var1,var4,var6,var7,var9,var10});
										} else if (execL1220(var0,var1,var4,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L1223;
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
	public boolean execL1217(Object var0, boolean nondeterministic) {
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
L1217:
		{
			var3 = new Atom(null, f1);
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var6 = link.getAtom();
				if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
					func = f3;
					Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it2.hasNext()) {
						atom = (Atom) it2.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						var4 = link.getAtom();
						if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_wt.", "");
								var5 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
								if (nondeterministic) {
									Task.states.add(new Object[] {theInstance, "L1211",var0,var1,var2,var3,var4,var6});
								} else if (execL1211(var0,var1,var2,var3,var4,var6,nondeterministic)) {
									ret = true;
									break L1217;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1211(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L1211:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f17;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var7), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 1,
				((Atom)var8), 0 );
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var10, 2);
			ret = true;
			break L1211;
		}
		return ret;
	}
	public boolean execL1212(Object var0, Object var1, boolean nondeterministic) {
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
L1212:
		{
			if (execL1214(var0, var1, nondeterministic)) {
				ret = true;
				break L1212;
			}
			if (execL1216(var0, var1, nondeterministic)) {
				ret = true;
				break L1212;
			}
		}
		return ret;
	}
	public boolean execL1216(Object var0, Object var1, boolean nondeterministic) {
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
L1216:
		{
			var5 = new Atom(null, f1);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var7 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
							func = f16;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(0);
								var8 = link.getAtom();
								if (((Atom)var8).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var8).getFunctor()).getObject() instanceof String) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "L1211",var0,var3,var1,var5,var6,var8});
									} else if (execL1211(var0,var3,var1,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L1216;
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
	public boolean execL1214(Object var0, Object var1, boolean nondeterministic) {
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
L1214:
		{
			var5 = new Atom(null, f1);
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var8 = link.getAtom();
					if (((Atom)var8).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var8).getFunctor()).getObject() instanceof String) {
						func = f3;
						Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it1.hasNext()) {
							atom = (Atom) it1.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(0);
							var6 = link.getAtom();
							if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_wt.", "");
									var7 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "L1211",var0,var1,var3,var5,var6,var8});
									} else if (execL1211(var0,var1,var3,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L1214;
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
	public boolean execL1208(Object var0, boolean nondeterministic) {
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
L1208:
		{
			var3 = new Atom(null, f1);
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var6 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var7 = link.getAtom();
				if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						func = f3;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var2 = atom;
							link = ((Atom)var2).getArg(0);
							var4 = link.getAtom();
							if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_wt.", "");
									var5 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
									if (nondeterministic) {
										Task.states.add(new Object[] {theInstance, "L1202",var0,var1,var2,var3,var4,var6,var7});
									} else if (execL1202(var0,var1,var2,var3,var4,var6,var7,nondeterministic)) {
										ret = true;
										break L1208;
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
	public boolean execL1202(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L1202:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var10 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f19;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 0,
				((Atom)var8), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 1,
				((Atom)var10), 0 );
			((Atom)var12).getMem().newLink(
				((Atom)var12), 2,
				((Atom)var9), 0 );
			atom = ((Atom)var12);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var12, 1);
			ret = true;
			break L1202;
		}
		return ret;
	}
	public boolean execL1203(Object var0, Object var1, boolean nondeterministic) {
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
L1203:
		{
			if (execL1205(var0, var1, nondeterministic)) {
				ret = true;
				break L1203;
			}
			if (execL1207(var0, var1, nondeterministic)) {
				ret = true;
				break L1203;
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
L1207:
		{
			var6 = new Atom(null, f1);
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_wt.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f18;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(0);
								var9 = link.getAtom();
								link = ((Atom)var3).getArg(1);
								var10 = link.getAtom();
								if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
									if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "L1202",var0,var3,var1,var6,var7,var9,var10});
										} else if (execL1202(var0,var3,var1,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L1207;
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
	public boolean execL1205(Object var0, Object var1, boolean nondeterministic) {
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
L1205:
		{
			var6 = new Atom(null, f1);
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var9 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var10 = link.getAtom();
					if (!(!(((Atom)var10).getFunctor() instanceof IntegerFunctor))) {
						if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
							func = f3;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var4 = atom;
								link = ((Atom)var4).getArg(0);
								var5 = link;
								link = ((Atom)var4).getArg(0);
								var7 = link.getAtom();
								if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_wt.", "");
										var8 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
										if (nondeterministic) {
											Task.states.add(new Object[] {theInstance, "L1202",var0,var1,var4,var6,var7,var9,var10});
										} else if (execL1202(var0,var1,var4,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L1205;
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
	public boolean execL1199(Object var0, boolean nondeterministic) {
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
L1199:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "L1195",var0,var1});
				} else if (execL1195(var0,var1,nondeterministic)) {
					ret = true;
					break L1199;
				}
			}
		}
		return ret;
	}
	public boolean execL1195(Object var0, Object var1, boolean nondeterministic) {
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
L1195:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f21;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var2, 0);
			ret = true;
			break L1195;
		}
		return ret;
	}
	public boolean execL1196(Object var0, Object var1, boolean nondeterministic) {
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
L1196:
		{
			if (execL1198(var0, var1, nondeterministic)) {
				ret = true;
				break L1196;
			}
		}
		return ret;
	}
	public boolean execL1198(Object var0, Object var1, boolean nondeterministic) {
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
L1198:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "L1195",var0,var1});
					} else if (execL1195(var0,var1,nondeterministic)) {
						ret = true;
						break L1198;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1192(Object var0, boolean nondeterministic) {
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
L1192:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
								mem = ((Atom)var3).getMem();
								if (mem.lock()) {
									var4 = mem;
									link = ((Atom)var3).getArg(1);
									if (!(link.getPos() != 0)) {
										var5 = link.getAtom();
										if (!(!(f23).equals(((Atom)var5).getFunctor()))) {
											if (nondeterministic) {
												Task.states.add(new Object[] {theInstance, "L1184",var0,var4,var1,var2,var5,var3});
											} else if (execL1184(var0,var4,var1,var2,var5,var3,nondeterministic)) {
												ret = true;
												break L1192;
											}
										}
									}
									((AbstractMembrane)var4).unlock();
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1184(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L1184:
		{
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			((AbstractMembrane)var1).removeProxies();
			((AbstractMembrane)var1).activate();
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var1));
			func = f20;
			var7 = ((AbstractMembrane)var1).newAtom(func);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_wt");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var1).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module wt");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module wt");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module wt");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module wt");
				}
			((AbstractMembrane)var1).forceUnlock();
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
L1185:
		{
			if (execL1187(var0, var1, nondeterministic)) {
				ret = true;
				break L1185;
			}
			if (execL1190(var0, var1, nondeterministic)) {
				ret = true;
				break L1185;
			}
		}
		return ret;
	}
	public boolean execL1190(Object var0, Object var1, boolean nondeterministic) {
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
L1190:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				var2 = ((Atom)var1).getMem();
				link = ((Atom)var1).getArg(0);
				var4 = link;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var5 = link.getAtom();
					if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var5).getFunctor()))) {
						link = ((Atom)var5).getArg(0);
						var6 = link;
						link = ((Atom)var5).getArg(1);
						var7 = link;
						link = ((Atom)var5).getArg(0);
						if (!(link.getPos() != 0)) {
							var8 = link.getAtom();
							if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var8).getFunctor()))) {
								link = ((Atom)var8).getArg(0);
								var9 = link;
								link = ((Atom)var8).getArg(1);
								var10 = link;
								link = ((Atom)var8).getArg(1);
								if (!(link.getPos() != 0)) {
									var11 = link.getAtom();
									if (!(!(f22).equals(((Atom)var11).getFunctor()))) {
										link = ((Atom)var11).getArg(0);
										var12 = link;
										mem = ((AbstractMembrane)var2);
										if (mem.lock()) {
											mem = ((AbstractMembrane)var2).getParent();
											if (!(mem == null)) {
												var3 = mem;
												if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
													if (nondeterministic) {
														Task.states.add(new Object[] {theInstance, "L1184",var0,var2,var11,var8,var1,var5});
													} else if (execL1184(var0,var2,var11,var8,var1,var5,nondeterministic)) {
														ret = true;
														break L1190;
													}
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
L1187:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 1)) {
						var3 = link.getAtom();
						if (!(!(Functor.OUTSIDE_PROXY).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(0);
							if (!(link.getPos() != 0)) {
								var6 = link.getAtom();
								if (!(!(Functor.INSIDE_PROXY).equals(((Atom)var6).getFunctor()))) {
									mem = ((Atom)var6).getMem();
									if (mem.lock()) {
										var7 = mem;
										link = ((Atom)var6).getArg(0);
										var8 = link;
										link = ((Atom)var6).getArg(1);
										var9 = link;
										link = ((Atom)var6).getArg(1);
										if (!(link.getPos() != 0)) {
											var10 = link.getAtom();
											if (!(!(f23).equals(((Atom)var10).getFunctor()))) {
												link = ((Atom)var10).getArg(0);
												var11 = link;
												if (nondeterministic) {
													Task.states.add(new Object[] {theInstance, "L1184",var0,var7,var1,var3,var10,var6});
												} else if (execL1184(var0,var7,var1,var3,var10,var6,nondeterministic)) {
													ret = true;
													break L1187;
												}
											}
										}
										((AbstractMembrane)var7).unlock();
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
	private static final Functor f10 = new Functor("grid", 2, null);
	private static final Functor f13 = new StringFunctor("/*inline*/\r\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n    Panel border = new Panel(new BorderLayout());\r\n    frame.getContentPane().add(border);\r\n    Atom a = mem.newAtom(new Functor(\"border\",1));\r\n    Atom b = mem.newAtom(new ObjectFunctor(border));\r\n    mem.newLink(a,0,b,0);\r\n    mem.removeAtom(me.nthAtom(0));\r\n    mem.removeAtom(me);\r\n  ");
	private static final Functor f8 = new Functor("/*inline*/\r\n        final Membrane memb = (Membrane)mem;\r\n        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n        Panel border = (Panel)panelfunc.getObject();\r\n        String title = me.nthAtom(1).toString();\r\n        String location = me.nthAtom(2).toString();\r\n        final String event = me.nthAtom(3).toString();\r\n        Button bt = new Button(title);\r\n        bt.setVisible(true);\r\n        bt.addActionListener(new ActionListener(){\r\n          public void actionPerformed(ActionEvent e){\r\n            memb.asyncLock();\r\n            memb.newAtom(new Functor(event,0));\r\n            memb.asyncUnlock();\r\n          }\r\n        });\r\n        border.add(location,bt);\r\n        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(4).getFunctor();\r\n        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n        frame.setVisible(true);\r\n        mem.removeAtom(me.nthAtom(0));\r\n        mem.removeAtom(me.nthAtom(1));\r\n        mem.removeAtom(me.nthAtom(2));\r\n        mem.removeAtom(me.nthAtom(3));\r\n        mem.removeAtom(me.nthAtom(4));\r\n        mem.removeAtom(me); \r\n      ", 5, null);
	private static final Functor f1 = new StringFunctor("LMNtalFrame");
	private static final Functor f18 = new Functor("size", 2, null);
	private static final Functor f3 = new Functor("frame", 1, null);
	private static final Functor f4 = new StringFunctor("/*inline*/\r\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n    frame.setVisible(false);\r\n    frame.dispose();\r\n    mem.removeAtom(me.nthAtom(0));\r\n    mem.removeAtom(me);\r\n  ");
	private static final Functor f0 = new Functor("terminated", 0, null);
	private static final Functor f16 = new Functor("title", 1, null);
	private static final Functor f23 = new Functor("+", 1, null);
	private static final Functor f19 = new Functor("/*inline*/\r\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n    int w = Integer.parseInt(me.nth(1));\r\n    int h = Integer.parseInt(me.nth(2));\r\n    frame.setSize(w,h);\r\n    mem.removeAtom(me.nthAtom(0));\r\n    mem.removeAtom(me.nthAtom(1));\r\n    mem.removeAtom(me.nthAtom(2));\r\n    mem.removeAtom(me);\r\n    frame.setVisible(true); \r\n  ", 3, null);
	private static final Functor f2 = new Functor("terminate", 0, null);
	private static final Functor f22 = new Functor("newFrame", 1, "wt");
	private static final Functor f20 = new Functor("createFrame", 0, "wt");
	private static final Functor f6 = new Functor("addButton", 3, null);
	private static final Functor f5 = new StringFunctor("java.awt.Panel");
	private static final Functor f11 = new Functor("/*inline*/\r\n        final Membrane memb = (Membrane)mem;\r\n        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n        Panel grid = (Panel)panelfunc.getObject();\r\n        ObjectFunctor queuefunc = (ObjectFunctor)me.nthAtom(1).getFunctor();\r\n        PriorityQueue pQueue = (PriorityQueue)queuefunc.getObject();\r\n        String title = me.nthAtom(2).toString();\r\n        int location = Integer.parseInt(me.nth(3));\r\n        final String event = me.nthAtom(4).toString();\r\n        Button bt = new Button(title);\r\n        bt.setVisible(true);\r\n        bt.addActionListener(new ActionListener(){\r\n          public void actionPerformed(ActionEvent e) {\r\n            memb.asyncLock();\r\n            memb.newAtom(new Functor(event,0));\r\n            memb.asyncUnlock();\r\n          }\r\n        });\r\n        int index = pQueue.insert(location);\r\n        grid.add(bt,index);\r\n        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(5).getFunctor();\r\n        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n        frame.setVisible(true);\r\n        mem.removeAtom(me.nthAtom(0));\r\n        mem.removeAtom(me.nthAtom(1));\r\n        mem.removeAtom(me.nthAtom(2));\r\n        mem.removeAtom(me.nthAtom(3));\r\n        mem.removeAtom(me.nthAtom(4));\r\n        mem.removeAtom(me.nthAtom(5));\r\n        mem.removeAtom(me); \r\n      ", 6, null);
	private static final Functor f7 = new Functor("border", 1, null);
	private static final Functor f14 = new Functor("gridPanel", 2, null);
	private static final Functor f9 = new StringFunctor("PriorityQueue");
	private static final Functor f17 = new Functor("/*inline*/\r\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n    String title = me.nthAtom(1).toString();\r\n    frame.setTitle(title);\r\n    mem.removeAtom(me.nthAtom(0));\r\n    mem.removeAtom(me.nthAtom(1));\r\n    mem.removeAtom(me);\r\n  ", 2, null);
	private static final Functor f21 = new Functor("/*inline*/\r\n    LMNtalFrame frame = new LMNtalFrame((Membrane)mem);\r\n    Atom a = mem.newAtom(new Functor(\"frame\",1));\r\n    Atom b = mem.newAtom(new ObjectFunctor(frame));\r\n    mem.newLink(a,0,b,0);\r\n    mem.removeAtom(me);\r\n    mem.makePerpetual();\r\n  ", 0, null);
	private static final Functor f15 = new Functor("/*inline*/\r\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\r\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\r\n    int rows = Integer.parseInt(me.nth(1));\r\n    int cols = Integer.parseInt(me.nth(2));\r\n    Panel grid = new Panel(new GridLayout(rows,cols));\r\n    frame.getContentPane().add(grid);\r\n    PriorityQueue pQueue = new PriorityQueue();\r\n    Atom a = mem.newAtom(new Functor(\"grid\",2));\r\n    Atom b = mem.newAtom(new ObjectFunctor(grid));\r\n    Atom c = mem.newAtom(new ObjectFunctor(pQueue));\r\n    mem.newLink(a,0,b,0);\r\n    mem.newLink(a,1,c,0);\r\n    mem.removeAtom(me.nthAtom(0));\r\n    mem.removeAtom(me.nthAtom(1));\r\n    mem.removeAtom(me.nthAtom(2));\r\n    mem.removeAtom(me); \r\n  ", 3, null);
	private static final Functor f12 = new Functor("borderPanel", 0, null);
}

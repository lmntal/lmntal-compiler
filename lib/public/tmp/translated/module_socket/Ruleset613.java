package translated.module_socket;
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
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":socket" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@socket" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL979(mem, atom)) {
			return true;
		}
		if (execL986(mem, atom)) {
			return true;
		}
		if (execL996(mem, atom)) {
			return true;
		}
		if (execL1006(mem, atom)) {
			return true;
		}
		if (execL1013(mem, atom)) {
			return true;
		}
		if (execL1025(mem, atom)) {
			return true;
		}
		if (execL1036(mem, atom)) {
			return true;
		}
		if (execL1044(mem, atom)) {
			return true;
		}
		if (execL1055(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL982(mem)) {
			return true;
		}
		if (execL992(mem)) {
			return true;
		}
		if (execL1002(mem)) {
			return true;
		}
		if (execL1009(mem)) {
			return true;
		}
		if (execL1021(mem)) {
			return true;
		}
		if (execL1032(mem)) {
			return true;
		}
		if (execL1040(mem)) {
			return true;
		}
		if (execL1051(mem)) {
			return true;
		}
		if (execL1064(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL1064(Object var0) {
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
L1064:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 0)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var2).getArg(3);
								if (!(link.getPos() != 2)) {
									var4 = link.getAtom();
									if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
										link = ((Atom)var4).getArg(0);
										if (!(link.getPos() != 0)) {
											var5 = link.getAtom();
											if (!(!(f4).equals(((Atom)var5).getFunctor()))) {
												if (execL1053(var0,var1,var5,var2,var3,var4)) {
													ret = true;
													break L1064;
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
	public boolean execL1053(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L1053:
		{
			if (execL1054(var0,var1,var2,var3,var4,var5)) {
				ret = true;
				break L1053;
			}
		}
		return ret;
	}
	public boolean execL1054(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L1054:
		{
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
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var5), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 7);
			ret = true;
			break L1054;
		}
		return ret;
	}
	public boolean execL1055(Object var0, Object var1) {
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
L1055:
		{
			if (execL1057(var0, var1)) {
				ret = true;
				break L1055;
			}
			if (execL1059(var0, var1)) {
				ret = true;
				break L1055;
			}
			if (execL1061(var0, var1)) {
				ret = true;
				break L1055;
			}
		}
		return ret;
	}
	public boolean execL1061(Object var0, Object var1) {
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
L1061:
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
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(2);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var1).getArg(3);
									if (!(link.getPos() != 2)) {
										var10 = link.getAtom();
										if (!(!(f3).equals(((Atom)var10).getFunctor()))) {
											link = ((Atom)var10).getArg(0);
											var11 = link;
											link = ((Atom)var10).getArg(1);
											var12 = link;
											link = ((Atom)var10).getArg(2);
											var13 = link;
											link = ((Atom)var10).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f4).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													if (execL1053(var0,var6,var14,var1,var8,var10)) {
														ret = true;
														break L1061;
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
	public boolean execL1059(Object var0, Object var1) {
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
L1059:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
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
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 3)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(3);
									var11 = link;
									link = ((Atom)var7).getArg(1);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										if (!(!(f0).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var7).getArg(2);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f2).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													if (execL1053(var0,var12,var1,var7,var14,var3)) {
														ret = true;
														break L1059;
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
	public boolean execL1057(Object var0, Object var1) {
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
L1057:
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
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var3).getArg(3);
									if (!(link.getPos() != 2)) {
										var10 = link.getAtom();
										if (!(!(f3).equals(((Atom)var10).getFunctor()))) {
											link = ((Atom)var10).getArg(0);
											var11 = link;
											link = ((Atom)var10).getArg(1);
											var12 = link;
											link = ((Atom)var10).getArg(2);
											var13 = link;
											link = ((Atom)var10).getArg(0);
											if (!(link.getPos() != 0)) {
												var14 = link.getAtom();
												if (!(!(f4).equals(((Atom)var14).getFunctor()))) {
													link = ((Atom)var14).getArg(0);
													var15 = link;
													if (execL1053(var0,var1,var14,var3,var8,var10)) {
														ret = true;
														break L1057;
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
	public boolean execL1051(Object var0) {
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
L1051:
		{
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
										if (execL1042(var0,var1,var3,var4,var2)) {
											ret = true;
											break L1051;
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
	public boolean execL1042(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1042:
		{
			if (execL1043(var0,var1,var2,var3,var4)) {
				ret = true;
				break L1042;
			}
		}
		return ret;
	}
	public boolean execL1043(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1043:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f2;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 2 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var2), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 3,
				((Atom)var4), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var2), 0 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 6);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1043;
		}
		return ret;
	}
	public boolean execL1044(Object var0, Object var1) {
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
L1044:
		{
			if (execL1046(var0, var1)) {
				ret = true;
				break L1044;
			}
			if (execL1048(var0, var1)) {
				ret = true;
				break L1044;
			}
		}
		return ret;
	}
	public boolean execL1048(Object var0, Object var1) {
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
L1048:
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
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(3);
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
										if (!(!(f7).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											if (execL1042(var0,var12,var1,var6,var8)) {
												ret = true;
												break L1048;
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
	public boolean execL1046(Object var0, Object var1) {
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
L1046:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
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
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 3)) {
								var7 = link.getAtom();
								if (!(!(f1).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									link = ((Atom)var7).getArg(2);
									var10 = link;
									link = ((Atom)var7).getArg(3);
									var11 = link;
									link = ((Atom)var7).getArg(2);
									if (!(link.getPos() != 0)) {
										var12 = link.getAtom();
										if (!(!(f2).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											if (execL1042(var0,var1,var7,var12,var3)) {
												ret = true;
												break L1046;
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
	public boolean execL1040(Object var0) {
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
L1040:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						if (execL1034(var0,var1,var2)) {
							ret = true;
							break L1040;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1034(Object var0, Object var1, Object var2) {
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
L1034:
		{
			link = ((Atom)var2).getArg(0);
			var3 = link.getAtom();
			if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
			    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
				if (execL1035(var0,var1,var2,var3)) {
					ret = true;
					break L1034;
				}
			}
		}
		return ret;
	}
	public boolean execL1035(Object var0, Object var1, Object var2, Object var3) {
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
L1035:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f9;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 2 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var1), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var2), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 3,
				((Atom)var1), 3 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 1,
				((Atom)var1), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var6, 5);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1035;
		}
		return ret;
	}
	public boolean execL1036(Object var0, Object var1) {
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
L1036:
		{
			if (execL1038(var0, var1)) {
				ret = true;
				break L1036;
			}
		}
		return ret;
	}
	public boolean execL1038(Object var0, Object var1) {
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
L1038:
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
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 2)) {
						var6 = link.getAtom();
						if (!(!(f3).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							link = ((Atom)var6).getArg(2);
							var9 = link;
							if (execL1034(var0,var1,var6)) {
								ret = true;
								break L1038;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1032(Object var0) {
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
L1032:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
										if (execL1023(var0,var1,var3,var4,var2)) {
											ret = true;
											break L1032;
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
	public boolean execL1023(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1023:
		{
			if (execL1024(var0,var1,var2,var3,var4)) {
				ret = true;
				break L1023;
			}
		}
		return ret;
	}
	public boolean execL1024(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1024:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var2), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var2), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var1), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 3,
				((Atom)var4), 1 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1024;
		}
		return ret;
	}
	public boolean execL1025(Object var0, Object var1) {
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
L1025:
		{
			if (execL1027(var0, var1)) {
				ret = true;
				break L1025;
			}
			if (execL1029(var0, var1)) {
				ret = true;
				break L1025;
			}
		}
		return ret;
	}
	public boolean execL1029(Object var0, Object var1) {
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
L1029:
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
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(3);
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
									if (!(link.getPos() != 1)) {
										var12 = link.getAtom();
										if (!(!(f10).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var12).getArg(1);
											var14 = link;
											if (execL1023(var0,var12,var1,var6,var8)) {
												ret = true;
												break L1029;
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
	public boolean execL1027(Object var0, Object var1) {
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
L1027:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 3)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(3);
									var12 = link;
									link = ((Atom)var8).getArg(2);
									if (!(link.getPos() != 0)) {
										var13 = link.getAtom();
										if (!(!(f2).equals(((Atom)var13).getFunctor()))) {
											link = ((Atom)var13).getArg(0);
											var14 = link;
											if (execL1023(var0,var1,var8,var13,var4)) {
												ret = true;
												break L1027;
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
	public boolean execL1021(Object var0) {
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
L1021:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 1)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(3);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f3).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 1)) {
									var4 = link.getAtom();
									if (!(!(f11).equals(((Atom)var4).getFunctor()))) {
										if (execL1011(var0,var1,var4,var2,var3)) {
											ret = true;
											break L1021;
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
	public boolean execL1011(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1011:
		{
			if (execL1012(var0,var1,var2,var3,var4)) {
				ret = true;
				break L1011;
			}
		}
		return ret;
	}
	public boolean execL1012(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1012:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f1;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f12;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var2), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 2,
				((Atom)var3), 2 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 3,
				((Atom)var4), 1 );
			((Atom)var6).getMem().relinkAtomArgs(
				((Atom)var6), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var6, 4);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1012;
		}
		return ret;
	}
	public boolean execL1013(Object var0, Object var1) {
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
L1013:
		{
			if (execL1015(var0, var1)) {
				ret = true;
				break L1013;
			}
			if (execL1017(var0, var1)) {
				ret = true;
				break L1013;
			}
			if (execL1019(var0, var1)) {
				ret = true;
				break L1013;
			}
		}
		return ret;
	}
	public boolean execL1019(Object var0, Object var1) {
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
L1019:
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
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var1).getArg(3);
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
									if (!(link.getPos() != 1)) {
										var12 = link.getAtom();
										if (!(!(f11).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var12).getArg(1);
											var14 = link;
											if (execL1011(var0,var6,var12,var1,var8)) {
												ret = true;
												break L1019;
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
	public boolean execL1017(Object var0, Object var1) {
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
L1017:
		{
			if (!(!(f11).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 3)) {
								var8 = link.getAtom();
								if (!(!(f1).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									link = ((Atom)var8).getArg(2);
									var11 = link;
									link = ((Atom)var8).getArg(3);
									var12 = link;
									link = ((Atom)var8).getArg(1);
									if (!(link.getPos() != 0)) {
										var13 = link.getAtom();
										if (!(!(f0).equals(((Atom)var13).getFunctor()))) {
											link = ((Atom)var13).getArg(0);
											var14 = link;
											if (execL1011(var0,var13,var1,var8,var4)) {
												ret = true;
												break L1017;
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
	public boolean execL1015(Object var0, Object var1) {
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
L1015:
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
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(3);
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
									if (!(link.getPos() != 1)) {
										var12 = link.getAtom();
										if (!(!(f11).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											link = ((Atom)var12).getArg(1);
											var14 = link;
											if (execL1011(var0,var1,var12,var3,var8)) {
												ret = true;
												break L1015;
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
	public boolean execL1009(Object var0) {
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
L1009:
		{
			func = f13;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1004(var0,var1)) {
					ret = true;
					break L1009;
				}
			}
		}
		return ret;
	}
	public boolean execL1004(Object var0, Object var1) {
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
L1004:
		{
			link = ((Atom)var1).getArg(1);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				func = ((Atom)var3).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1005(var0,var1,var2,var3)) {
						ret = true;
						break L1004;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1005(Object var0, Object var1, Object var2, Object var3) {
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
L1005:
		{
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
			func = f0;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var8), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 2 );
			((Atom)var8).getMem().relinkAtomArgs(
				((Atom)var8), 3,
				((Atom)var1), 2 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 1,
				((Atom)var4), 0 );
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var9, 3);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L1005;
		}
		return ret;
	}
	public boolean execL1006(Object var0, Object var1) {
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
L1006:
		{
			if (execL1008(var0, var1)) {
				ret = true;
				break L1006;
			}
		}
		return ret;
	}
	public boolean execL1008(Object var0, Object var1) {
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
L1008:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL1004(var0,var1)) {
						ret = true;
						break L1008;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1002(Object var0) {
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
L1002:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL994(var0,var1,var3,var2)) {
									ret = true;
									break L1002;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL994(Object var0, Object var1, Object var2, Object var3) {
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
L994:
		{
			if (execL995(var0,var1,var2,var3)) {
				ret = true;
				break L994;
			}
		}
		return ret;
	}
	public boolean execL995(Object var0, Object var1, Object var2, Object var3) {
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
L995:
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
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var3), 1 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 0,
				((Atom)var2), 0 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var5, 2);
			ret = true;
			break L995;
		}
		return ret;
	}
	public boolean execL996(Object var0, Object var1) {
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
L996:
		{
			if (execL998(var0, var1)) {
				ret = true;
				break L996;
			}
			if (execL1000(var0, var1)) {
				ret = true;
				break L996;
			}
		}
		return ret;
	}
	public boolean execL1000(Object var0, Object var1) {
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
L1000:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
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
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(f4).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									if (execL994(var0,var8,var1,var4)) {
										ret = true;
										break L1000;
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
	public boolean execL998(Object var0, Object var1) {
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
L998:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
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
							link = ((Atom)var3).getArg(2);
							if (!(link.getPos() != 1)) {
								var7 = link.getAtom();
								if (!(!(f15).equals(((Atom)var7).getFunctor()))) {
									link = ((Atom)var7).getArg(0);
									var8 = link;
									link = ((Atom)var7).getArg(1);
									var9 = link;
									if (execL994(var0,var1,var7,var3)) {
										ret = true;
										break L998;
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
	public boolean execL992(Object var0) {
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
L992:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f3).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL984(var0,var1,var3,var2)) {
									ret = true;
									break L992;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL984(Object var0, Object var1, Object var2, Object var3) {
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
L984:
		{
			if (execL985(var0,var1,var2,var3)) {
				ret = true;
				break L984;
			}
		}
		return ret;
	}
	public boolean execL985(Object var0, Object var1, Object var2, Object var3) {
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
L985:
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
			func = f18;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f15;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 0,
				((Atom)var1), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 0 );
			((Atom)var5).getMem().relinkAtomArgs(
				((Atom)var5), 1,
				((Atom)var3), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var6), 1 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().relinkAtomArgs(
				((Atom)var7), 0,
				((Atom)var2), 0 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 1);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L985;
		}
		return ret;
	}
	public boolean execL986(Object var0, Object var1) {
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
L986:
		{
			if (execL988(var0, var1)) {
				ret = true;
				break L986;
			}
			if (execL990(var0, var1)) {
				ret = true;
				break L986;
			}
		}
		return ret;
	}
	public boolean execL990(Object var0, Object var1) {
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
L990:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
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
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(f17).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									if (execL984(var0,var8,var1,var4)) {
										ret = true;
										break L990;
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
	public boolean execL988(Object var0, Object var1) {
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
L988:
		{
			if (!(!(f17).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var4 = link.getAtom();
						if (!(!(f3).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(2);
							if (!(link.getPos() != 1)) {
								var8 = link.getAtom();
								if (!(!(f15).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(1);
									var10 = link;
									if (execL984(var0,var1,var8,var4)) {
										ret = true;
										break L988;
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
	public boolean execL982(Object var0) {
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
L982:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL977(var0,var1)) {
					ret = true;
					break L982;
				}
			}
		}
		return ret;
	}
	public boolean execL977(Object var0, Object var1) {
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
L977:
		{
			link = ((Atom)var1).getArg(0);
			var2 = link.getAtom();
			if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
				if (execL978(var0,var1,var2)) {
					ret = true;
					break L977;
				}
			}
		}
		return ret;
	}
	public boolean execL978(Object var0, Object var1, Object var2) {
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
L978:
		{
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
			func = f21;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 1 );
			((Atom)var4).getMem().relinkAtomArgs(
				((Atom)var4), 1,
				((Atom)var1), 1 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var5, 0);
				try {
					Class c = Class.forName("translated.Module_socket");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module socket");
				}
			ret = true;
			break L978;
		}
		return ret;
	}
	public boolean execL979(Object var0, Object var1) {
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
L979:
		{
			if (execL981(var0, var1)) {
				ret = true;
				break L979;
			}
		}
		return ret;
	}
	public boolean execL981(Object var0, Object var1) {
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
L981:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL977(var0,var1)) {
						ret = true;
						break L981;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f21 = new Functor("/*inline*/\\r\\n		try {\\r\\n			ServerSocket ss = new ServerSocket(Integer.parseInt(me.nth(0)));\\r\\n			Atom o = mem.newAtom(new ObjectFunctor(ss));\\r\\n			mem.relink(o, 0, me, 1);\\r\\n			\\r\\n			me.nthAtom(0).remove();\\r\\n			me.remove();\\r\\n		} catch (IOException e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n	", 2, null);
	private static final Functor f7 = new Functor("close_is", 1, null);
	private static final Functor f9 = new Functor("/*inline*/\\r\\n		try {\\r\\n			String data = (String)((StringFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n			Socket soc = ((ReadThread)((ObjectFunctor)me.nthAtom(1).getFunctor()).getObject()).socket;\\r\\n			BufferedWriter writer = new BufferedWriter(\\r\\n					new OutputStreamWriter(soc.getOutputStream()));\\r\\n			writer.write(data);\\r\\n			writer.write(\"\\n\");\\r\\n			writer.flush();\\r\\n			mem.unifyAtomArgs(me, 1, me, 2);\\r\\n			me.nthAtom(0).remove();\\r\\n			me.remove();\\r\\n		} catch (Exception e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n	", 3, null);
	private static final Functor f12 = new Functor("/*inline*/\\r\\n		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n		sr.start();\\r\\n		mem.unifyAtomArgs(me, 0, me, 1);\\r\\n		me.remove();\\r\\n	", 2, null);
	private static final Functor f10 = new Functor("os", 2, null);
	private static final Functor f17 = new Functor("accept", 2, null);
	private static final Functor f20 = new Functor("create", 2, "socket");
	private static final Functor f15 = new Functor("serversocket", 2, "socket");
	private static final Functor f4 = new Functor("close", 1, null);
	private static final Functor f0 = new Functor("nil", 1, null);
	private static final Functor f6 = new StringFunctor("/*inline*/\\r\\n		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n		Socket soc = sr.socket;\\r\\n		try {\\r\\n			soc.close();\\r\\n		} catch (IOException e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	");
	private static final Functor f3 = new Functor(".", 3, null);
	private static final Functor f1 = new Functor("socket", 4, "socket");
	private static final Functor f11 = new Functor("is", 2, null);
	private static final Functor f2 = new Functor("[]", 1, null);
	private static final Functor f13 = new Functor("connect", 3, "socket");
	private static final Functor f5 = new Functor("closed", 1, null);
	private static final Functor f16 = new StringFunctor("/*inline*/\\r\\n		ServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n		try {\\r\\n			ss.close();\\r\\n		} catch (IOException e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n		me.nthAtom(0).remove();\\r\\n		me.remove();\\r\\n	");
	private static final Functor f19 = new Functor("/*inline*/\\r\\n		ServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n		AcceptThread t = new AcceptThread(ss,me.nthAtom(1));\\r\\n		mem.makePerpetual();\\r\\n		t.start();\\r\\n		mem.unifyAtomArgs(me, 0, me, 1);\\r\\n		me.remove();\\r\\n	", 2, null);
	private static final Functor f18 = new Functor("accepting", 2, "socket");
	private static final Functor f14 = new Functor("/*inline*/\\r\\n		try {\\r\\n			String addr = me.nth(0);\\r\\n			int port = Integer.parseInt(me.nth(1));\\r\\n			ReadThread sr = new ReadThread(addr, port);\\r\\n\\r\\n			Functor func = new ObjectFunctor(sr);\\r\\n			Atom so = mem.newAtom(func);\\r\\n			sr.me = so;\\r\\n			mem.relink(so, 0, me, 2);\\r\\n\\r\\n			me.nthAtom(0).remove();\\r\\n			me.nthAtom(1).remove();\\r\\n			me.remove();\\r\\n		} catch(Exception e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n	", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\\r\\n		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\\r\\n		sr.flgClosing = true;\\r\\n		mem.unifyAtomArgs(me, 0, me, 1);\\r\\n		me.remove();\\r\\n	", 2, null);
}

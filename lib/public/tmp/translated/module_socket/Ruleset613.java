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
		if (execL1022(mem, atom, false)) {
			return true;
		}
		if (execL1029(mem, atom, false)) {
			return true;
		}
		if (execL1039(mem, atom, false)) {
			return true;
		}
		if (execL1049(mem, atom, false)) {
			return true;
		}
		if (execL1056(mem, atom, false)) {
			return true;
		}
		if (execL1068(mem, atom, false)) {
			return true;
		}
		if (execL1079(mem, atom, false)) {
			return true;
		}
		if (execL1087(mem, atom, false)) {
			return true;
		}
		if (execL1098(mem, atom, false)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL1025(mem, nondeterministic)) {
			return true;
		}
		if (execL1035(mem, nondeterministic)) {
			return true;
		}
		if (execL1045(mem, nondeterministic)) {
			return true;
		}
		if (execL1052(mem, nondeterministic)) {
			return true;
		}
		if (execL1064(mem, nondeterministic)) {
			return true;
		}
		if (execL1075(mem, nondeterministic)) {
			return true;
		}
		if (execL1083(mem, nondeterministic)) {
			return true;
		}
		if (execL1094(mem, nondeterministic)) {
			return true;
		}
		if (execL1107(mem, nondeterministic)) {
			return true;
		}
		return result;
	}
	public boolean execL1107(Object var0, boolean nondeterministic) {
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
L1107:
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
							link = ((Atom)var2).getArg(3);
							if (!(link.getPos() != 2)) {
								var4 = link.getAtom();
								if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
									link = ((Atom)var4).getArg(0);
									if (!(link.getPos() != 0)) {
										var5 = link.getAtom();
										if (!(!(f3).equals(((Atom)var5).getFunctor()))) {
											if (!(!(f4).equals(((Atom)var3).getFunctor()))) {
												if (execL1097(var0,var1,var5,var2,var3,var4,nondeterministic)) {
													ret = true;
													break L1107;
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
	public boolean execL1097(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L1097:
		{
			link = ((Atom)var5).getArg(1);
			var8 = link;
			link = ((Atom)var3).getArg(0);
			var9 = link;
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
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var8 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var9 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var7, 7);
			ret = true;
			break L1097;
		}
		return ret;
	}
	public boolean execL1098(Object var0, Object var1, boolean nondeterministic) {
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
L1098:
		{
			if (execL1100(var0, var1, nondeterministic)) {
				ret = true;
				break L1098;
			}
			if (execL1102(var0, var1, nondeterministic)) {
				ret = true;
				break L1098;
			}
			if (execL1104(var0, var1, nondeterministic)) {
				ret = true;
				break L1098;
			}
		}
		return ret;
	}
	public boolean execL1104(Object var0, Object var1, boolean nondeterministic) {
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
L1104:
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
						link = ((Atom)var1).getArg(2);
						if (!(link.getPos() != 0)) {
							var8 = link.getAtom();
							link = ((Atom)var1).getArg(3);
							if (!(link.getPos() != 2)) {
								var10 = link.getAtom();
								if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
									link = ((Atom)var10).getArg(0);
									var11 = link;
									link = ((Atom)var10).getArg(1);
									var12 = link;
									link = ((Atom)var10).getArg(2);
									var13 = link;
									link = ((Atom)var10).getArg(0);
									if (!(link.getPos() != 0)) {
										var14 = link.getAtom();
										if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
											link = ((Atom)var14).getArg(0);
											var15 = link;
											if (!(!(f4).equals(((Atom)var8).getFunctor()))) {
												link = ((Atom)var8).getArg(0);
												var9 = link;
												if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
													link = ((Atom)var6).getArg(0);
													var7 = link;
													if (execL1097(var0,var6,var14,var1,var8,var10,nondeterministic)) {
														ret = true;
														break L1104;
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
	public boolean execL1102(Object var0, Object var1, boolean nondeterministic) {
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
L1102:
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
										link = ((Atom)var7).getArg(2);
										if (!(link.getPos() != 0)) {
											var14 = link.getAtom();
											if (!(!(f4).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												if (!(!(f0).equals(((Atom)var12).getFunctor()))) {
													link = ((Atom)var12).getArg(0);
													var13 = link;
													if (execL1097(var0,var12,var1,var7,var14,var3,nondeterministic)) {
														ret = true;
														break L1102;
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
	public boolean execL1100(Object var0, Object var1, boolean nondeterministic) {
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
L1100:
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
								link = ((Atom)var3).getArg(3);
								if (!(link.getPos() != 2)) {
									var10 = link.getAtom();
									if (!(!(f2).equals(((Atom)var10).getFunctor()))) {
										link = ((Atom)var10).getArg(0);
										var11 = link;
										link = ((Atom)var10).getArg(1);
										var12 = link;
										link = ((Atom)var10).getArg(2);
										var13 = link;
										link = ((Atom)var10).getArg(0);
										if (!(link.getPos() != 0)) {
											var14 = link.getAtom();
											if (!(!(f3).equals(((Atom)var14).getFunctor()))) {
												link = ((Atom)var14).getArg(0);
												var15 = link;
												if (!(!(f4).equals(((Atom)var8).getFunctor()))) {
													link = ((Atom)var8).getArg(0);
													var9 = link;
													if (execL1097(var0,var1,var14,var3,var8,var10,nondeterministic)) {
														ret = true;
														break L1100;
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
	public boolean execL1094(Object var0, boolean nondeterministic) {
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
L1094:
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
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
										if (execL1086(var0,var1,var3,var4,var2,nondeterministic)) {
											ret = true;
											break L1094;
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
	public boolean execL1086(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1086:
		{
			link = ((Atom)var4).getArg(1);
			var9 = link;
			link = ((Atom)var2).getArg(0);
			var10 = link;
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
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var2), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 1 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 3,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var10 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
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
			break L1086;
		}
		return ret;
	}
	public boolean execL1087(Object var0, Object var1, boolean nondeterministic) {
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
L1087:
		{
			if (execL1089(var0, var1, nondeterministic)) {
				ret = true;
				break L1087;
			}
			if (execL1091(var0, var1, nondeterministic)) {
				ret = true;
				break L1087;
			}
		}
		return ret;
	}
	public boolean execL1091(Object var0, Object var1, boolean nondeterministic) {
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
L1091:
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
						link = ((Atom)var1).getArg(3);
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
									if (!(!(f7).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										if (!(!(f4).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1086(var0,var12,var1,var6,var8,nondeterministic)) {
												ret = true;
												break L1091;
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
	public boolean execL1089(Object var0, Object var1, boolean nondeterministic) {
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
L1089:
		{
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
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
										if (!(!(f4).equals(((Atom)var12).getFunctor()))) {
											link = ((Atom)var12).getArg(0);
											var13 = link;
											if (execL1086(var0,var1,var7,var12,var3,nondeterministic)) {
												ret = true;
												break L1089;
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
	public boolean execL1083(Object var0, boolean nondeterministic) {
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
L1083:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
							if (execL1078(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1083;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1078(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1078:
		{
			link = ((Atom)var2).getArg(1);
			var8 = link;
			link = ((Atom)var1).getArg(0);
			var10 = link;
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
			func = f9;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 2 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 2,
				(Link)var8 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var4), 0 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
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
			break L1078;
		}
		return ret;
	}
	public boolean execL1079(Object var0, Object var1, boolean nondeterministic) {
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
L1079:
		{
			if (execL1081(var0, var1, nondeterministic)) {
				ret = true;
				break L1079;
			}
		}
		return ret;
	}
	public boolean execL1081(Object var0, Object var1, boolean nondeterministic) {
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
L1081:
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
						if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
							link = ((Atom)var6).getArg(0);
							var7 = link;
							link = ((Atom)var6).getArg(1);
							var8 = link;
							link = ((Atom)var6).getArg(2);
							var9 = link;
							link = ((Atom)var6).getArg(0);
							var10 = link.getAtom();
							if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
								if (execL1078(var0,var1,var6,var10,nondeterministic)) {
									ret = true;
									break L1081;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1075(Object var0, boolean nondeterministic) {
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
L1075:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 3)) {
							var3 = link.getAtom();
							if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(2);
								if (!(link.getPos() != 0)) {
									var4 = link.getAtom();
									if (!(!(f4).equals(((Atom)var4).getFunctor()))) {
										if (execL1067(var0,var1,var3,var4,var2,nondeterministic)) {
											ret = true;
											break L1075;
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
	public boolean execL1067(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1067:
		{
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var4).getArg(1);
			var9 = link;
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
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 2,
				(Link)var8 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 3,
				(Link)var9 );
			atom = ((Atom)var2);
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
			break L1067;
		}
		return ret;
	}
	public boolean execL1068(Object var0, Object var1, boolean nondeterministic) {
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
L1068:
		{
			if (execL1070(var0, var1, nondeterministic)) {
				ret = true;
				break L1068;
			}
			if (execL1072(var0, var1, nondeterministic)) {
				ret = true;
				break L1068;
			}
		}
		return ret;
	}
	public boolean execL1072(Object var0, Object var1, boolean nondeterministic) {
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
L1072:
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
						link = ((Atom)var1).getArg(3);
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
								if (!(link.getPos() != 1)) {
									var12 = link.getAtom();
									if (!(!(f10).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										link = ((Atom)var12).getArg(1);
										var14 = link;
										if (!(!(f4).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1067(var0,var12,var1,var6,var8,nondeterministic)) {
												ret = true;
												break L1072;
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
	public boolean execL1070(Object var0, Object var1, boolean nondeterministic) {
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
L1070:
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
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
										if (!(!(f4).equals(((Atom)var13).getFunctor()))) {
											link = ((Atom)var13).getArg(0);
											var14 = link;
											if (execL1067(var0,var1,var8,var13,var4,nondeterministic)) {
												ret = true;
												break L1070;
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
	public boolean execL1064(Object var0, boolean nondeterministic) {
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
						link = ((Atom)var2).getArg(3);
						if (!(link.getPos() != 2)) {
							var3 = link.getAtom();
							if (!(!(f2).equals(((Atom)var3).getFunctor()))) {
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 1)) {
									var4 = link.getAtom();
									if (!(!(f11).equals(((Atom)var4).getFunctor()))) {
										if (execL1055(var0,var1,var4,var2,var3,nondeterministic)) {
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
		return ret;
	}
	public boolean execL1055(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L1055:
		{
			link = ((Atom)var2).getArg(0);
			var7 = link;
			link = ((Atom)var4).getArg(1);
			var9 = link;
			link = ((Atom)var3).getArg(0);
			var10 = link;
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
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			func = f12;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var6), 1 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var7 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var9 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 0,
				(Link)var10 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
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
			break L1055;
		}
		return ret;
	}
	public boolean execL1056(Object var0, Object var1, boolean nondeterministic) {
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
L1056:
		{
			if (execL1058(var0, var1, nondeterministic)) {
				ret = true;
				break L1056;
			}
			if (execL1060(var0, var1, nondeterministic)) {
				ret = true;
				break L1056;
			}
			if (execL1062(var0, var1, nondeterministic)) {
				ret = true;
				break L1056;
			}
		}
		return ret;
	}
	public boolean execL1062(Object var0, Object var1, boolean nondeterministic) {
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
L1062:
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
						link = ((Atom)var1).getArg(3);
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
								if (!(link.getPos() != 1)) {
									var12 = link.getAtom();
									if (!(!(f11).equals(((Atom)var12).getFunctor()))) {
										link = ((Atom)var12).getArg(0);
										var13 = link;
										link = ((Atom)var12).getArg(1);
										var14 = link;
										if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
											link = ((Atom)var6).getArg(0);
											var7 = link;
											if (execL1055(var0,var6,var12,var1,var8,nondeterministic)) {
												ret = true;
												break L1062;
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
	public boolean execL1060(Object var0, Object var1, boolean nondeterministic) {
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
L1060:
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
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
											if (execL1055(var0,var13,var1,var8,var4,nondeterministic)) {
												ret = true;
												break L1060;
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
	public boolean execL1058(Object var0, Object var1, boolean nondeterministic) {
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
L1058:
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
								if (!(!(f2).equals(((Atom)var8).getFunctor()))) {
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
											if (execL1055(var0,var1,var12,var3,var8,nondeterministic)) {
												ret = true;
												break L1058;
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
	public boolean execL1052(Object var0, boolean nondeterministic) {
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
L1052:
		{
			func = f13;
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
						if (execL1048(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1052;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1048(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1048:
		{
			link = ((Atom)var1).getArg(2);
			var10 = link;
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
			func = f4;
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
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 3,
				(Link)var10 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var5), 0 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 1,
				((Atom)var4), 0 );
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
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
			break L1048;
		}
		return ret;
	}
	public boolean execL1049(Object var0, Object var1, boolean nondeterministic) {
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
L1049:
		{
			if (execL1051(var0, var1, nondeterministic)) {
				ret = true;
				break L1049;
			}
		}
		return ret;
	}
	public boolean execL1051(Object var0, Object var1, boolean nondeterministic) {
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
L1051:
		{
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
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
							if (execL1048(var0,var1,var5,var6,nondeterministic)) {
								ret = true;
								break L1051;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1045(Object var0, boolean nondeterministic) {
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
L1045:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL1038(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1045;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1038(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1038:
		{
			link = ((Atom)var3).getArg(1);
			var6 = link;
			link = ((Atom)var2).getArg(0);
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
			func = f5;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f16;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var7 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodesocket.run((Atom)var5, 2);
			ret = true;
			break L1038;
		}
		return ret;
	}
	public boolean execL1039(Object var0, Object var1, boolean nondeterministic) {
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
L1039:
		{
			if (execL1041(var0, var1, nondeterministic)) {
				ret = true;
				break L1039;
			}
			if (execL1043(var0, var1, nondeterministic)) {
				ret = true;
				break L1039;
			}
		}
		return ret;
	}
	public boolean execL1043(Object var0, Object var1, boolean nondeterministic) {
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
L1043:
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
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							if (!(link.getPos() != 0)) {
								var8 = link.getAtom();
								if (!(!(f3).equals(((Atom)var8).getFunctor()))) {
									link = ((Atom)var8).getArg(0);
									var9 = link;
									if (execL1038(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L1043;
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
	public boolean execL1041(Object var0, Object var1, boolean nondeterministic) {
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
L1041:
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
									if (execL1038(var0,var1,var7,var3,nondeterministic)) {
										ret = true;
										break L1041;
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
	public boolean execL1035(Object var0, boolean nondeterministic) {
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
L1035:
		{
			func = f17;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(2);
						if (!(link.getPos() != 1)) {
							var3 = link.getAtom();
							if (!(!(f15).equals(((Atom)var3).getFunctor()))) {
								if (execL1028(var0,var1,var3,var2,nondeterministic)) {
									ret = true;
									break L1035;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1028(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
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
L1028:
		{
			link = ((Atom)var1).getArg(0);
			var8 = link;
			link = ((Atom)var2).getArg(0);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f18;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var8 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var3), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var2), 1 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var7), 1 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 0,
				(Link)var10 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
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
			break L1028;
		}
		return ret;
	}
	public boolean execL1029(Object var0, Object var1, boolean nondeterministic) {
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
L1029:
		{
			if (execL1031(var0, var1, nondeterministic)) {
				ret = true;
				break L1029;
			}
			if (execL1033(var0, var1, nondeterministic)) {
				ret = true;
				break L1029;
			}
		}
		return ret;
	}
	public boolean execL1033(Object var0, Object var1, boolean nondeterministic) {
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
L1033:
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
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1028(var0,var8,var1,var4,nondeterministic)) {
										ret = true;
										break L1033;
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
	public boolean execL1031(Object var0, Object var1, boolean nondeterministic) {
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
L1031:
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
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
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
									if (execL1028(var0,var1,var8,var4,nondeterministic)) {
										ret = true;
										break L1031;
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
	public boolean execL1025(Object var0, boolean nondeterministic) {
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
L1025:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL1021(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1025;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1021(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L1021:
		{
			link = ((Atom)var1).getArg(1);
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
			func = f15;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f21;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 1 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var6 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var3), 0 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
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
			break L1021;
		}
		return ret;
	}
	public boolean execL1022(Object var0, Object var1, boolean nondeterministic) {
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
L1022:
		{
			if (execL1024(var0, var1, nondeterministic)) {
				ret = true;
				break L1022;
			}
		}
		return ret;
	}
	public boolean execL1024(Object var0, Object var1, boolean nondeterministic) {
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
L1024:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL1021(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L1024;
						}
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f21 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tServerSocket ss = new ServerSocket(Integer.parseInt(me.nth(0)));\r\n\t\t\tAtom o = mem.newAtom(new ObjectFunctor(ss));\r\n\t\t\tmem.relink(o, 0, me, 1);\r\n\t\t\t\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 2, null);
	private static final Functor f18 = new Functor("accepting", 2, "socket");
	private static final Functor f7 = new Functor("close_is", 1, null);
	private static final Functor f9 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tString data = (String)((StringFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tSocket soc = ((ReadThread)((ObjectFunctor)me.nthAtom(1).getFunctor()).getObject()).socket;\r\n\t\t\tBufferedWriter writer = new BufferedWriter(\r\n\t\t\t\t\tnew OutputStreamWriter(soc.getOutputStream()));\r\n\t\t\twriter.write(data);\r\n\t\t\twriter.write(\"\\n\");\r\n\t\t\twriter.flush();\r\n\t\t\tmem.unifyAtomArgs(me, 1, me, 2);\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch (Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 3, null);
	private static final Functor f12 = new Functor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tsr.start();\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
	private static final Functor f10 = new Functor("os", 2, null);
	private static final Functor f17 = new Functor("accept", 2, null);
	private static final Functor f3 = new Functor("close", 1, null);
	private static final Functor f20 = new Functor("create", 2, "socket");
	private static final Functor f0 = new Functor("nil", 1, null);
	private static final Functor f1 = new Functor("socket", 4, "socket");
	private static final Functor f6 = new StringFunctor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tSocket soc = sr.socket;\r\n\t\ttry {\r\n\t\t\tsoc.close();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t");
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f11 = new Functor("is", 2, null);
	private static final Functor f15 = new Functor("serversocket", 2, "socket");
	private static final Functor f4 = new Functor("[]", 1, null);
	private static final Functor f5 = new Functor("closed", 1, null);
	private static final Functor f16 = new StringFunctor("/*inline*/\r\n\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\ttry {\r\n\t\t\tss.close();\r\n\t\t} catch (IOException e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t");
	private static final Functor f19 = new Functor("/*inline*/\r\n\t\tServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tAcceptThread t = new AcceptThread(ss,me.nthAtom(1));\r\n\t\tmem.makePerpetual();\r\n\t\tt.start();\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
	private static final Functor f13 = new Functor("connect", 3, "socket");
	private static final Functor f14 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tString addr = me.nth(0);\r\n\t\t\tint port = Integer.parseInt(me.nth(1));\r\n\t\t\tReadThread sr = new ReadThread(addr, port);\r\n\r\n\t\t\tFunctor func = new ObjectFunctor(sr);\r\n\t\t\tAtom so = mem.newAtom(func);\r\n\t\t\tsr.me = so;\r\n\t\t\tmem.relink(so, 0, me, 2);\r\n\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.nthAtom(1).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t", 3, null);
	private static final Functor f8 = new Functor("/*inline*/\r\n\t\tReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\tsr.flgClosing = true;\r\n\t\tmem.unifyAtomArgs(me, 0, me, 1);\r\n\t\tme.remove();\r\n\t", 2, null);
}

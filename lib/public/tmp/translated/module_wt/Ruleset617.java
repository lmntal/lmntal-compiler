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
		if (execL1133(mem, atom)) {
			return true;
		}
		if (execL1143(mem, atom)) {
			return true;
		}
		if (execL1150(mem, atom)) {
			return true;
		}
		if (execL1159(mem, atom)) {
			return true;
		}
		if (execL1168(mem, atom)) {
			return true;
		}
		if (execL1177(mem, atom)) {
			return true;
		}
		if (execL1186(mem, atom)) {
			return true;
		}
		if (execL1197(mem, atom)) {
			return true;
		}
		if (execL1208(mem, atom)) {
			return true;
		}
		if (execL1217(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL1139(mem)) {
			return true;
		}
		if (execL1146(mem)) {
			return true;
		}
		if (execL1155(mem)) {
			return true;
		}
		if (execL1164(mem)) {
			return true;
		}
		if (execL1173(mem)) {
			return true;
		}
		if (execL1182(mem)) {
			return true;
		}
		if (execL1193(mem)) {
			return true;
		}
		if (execL1204(mem)) {
			return true;
		}
		if (execL1213(mem)) {
			return true;
		}
		if (execL1220(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL1220(Object var0) {
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
L1220:
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
							if (execL1215(var0,var1,var2)) {
								ret = true;
								break L1220;
							}
						}
					}
					((AbstractMembrane)var1).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL1215(Object var0, Object var1, Object var2) {
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
L1215:
		{
			if (execL1216(var0,var1,var2)) {
				ret = true;
				break L1215;
			}
		}
		return ret;
	}
	public boolean execL1216(Object var0, Object var1, Object var2) {
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
			break L1216;
		}
		return ret;
	}
	public boolean execL1217(Object var0, Object var1) {
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
L1217:
		{
			if (execL1219(var0, var1)) {
				ret = true;
				break L1217;
			}
		}
		return ret;
	}
	public boolean execL1219(Object var0, Object var1) {
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
L1219:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				var2 = ((Atom)var1).getMem();
				mem = ((AbstractMembrane)var2);
				if (mem.lock()) {
					mem = ((AbstractMembrane)var2).getParent();
					if (!(mem == null)) {
						var3 = mem;
						if (!(((AbstractMembrane)var0) != ((AbstractMembrane)var3))) {
							mem = ((AbstractMembrane)var2);
							if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != 0)) {
								if (execL1215(var0,var2,var1)) {
									ret = true;
									break L1219;
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
	public boolean execL1213(Object var0) {
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
L1213:
		{
			func = f1;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f2;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					if (execL1206(var0,var1,var2)) {
						ret = true;
						break L1213;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1206(Object var0, Object var1, Object var2) {
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
L1206:
		{
			var3 = new Atom(null, f3);
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
					if (execL1207(var0,var1,var2,var3,var4)) {
						ret = true;
						break L1206;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1207(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1207:
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
			break L1207;
		}
		return ret;
	}
	public boolean execL1208(Object var0, Object var1) {
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
L1208:
		{
			if (execL1210(var0, var1)) {
				ret = true;
				break L1208;
			}
			if (execL1212(var0, var1)) {
				ret = true;
				break L1208;
			}
		}
		return ret;
	}
	public boolean execL1212(Object var0, Object var1) {
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
L1212:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f1;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						if (execL1206(var0,var3,var1)) {
							ret = true;
							break L1212;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1210(Object var0, Object var1) {
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
L1210:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					func = f2;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						var3 = link;
						if (execL1206(var0,var1,var2)) {
							ret = true;
							break L1210;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1204(Object var0) {
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
L1204:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f6;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					func = f2;
					Iterator it3 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it3.hasNext()) {
						atom = (Atom) it3.next();
						var3 = atom;
						if (execL1195(var0,var1,var2,var3)) {
							ret = true;
							break L1204;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1195(Object var0, Object var1, Object var2, Object var3) {
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
L1195:
		{
			var4 = new Atom(null, f7);
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
					var7 = new Atom(null, f3);
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
							link = ((Atom)var1).getArg(0);
							var10 = link.getAtom();
							if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
								link = ((Atom)var1).getArg(1);
								var11 = link.getAtom();
								if (((Atom)var11).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var11).getFunctor()).getObject() instanceof String) {
									link = ((Atom)var1).getArg(2);
									var12 = link.getAtom();
									if (((Atom)var12).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var12).getFunctor()).getObject() instanceof String) {
										if (execL1196(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var12)) {
											ret = true;
											break L1195;
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
	public boolean execL1196(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10) {
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
L1196:
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
			func = f6;
			var18 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var19 = ((AbstractMembrane)var0).newAtom(func);
			func = f8;
			var20 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var18).getMem().newLink(
				((Atom)var18), 0,
				((Atom)var13), 0 );
			((Atom)var19).getMem().newLink(
				((Atom)var19), 0,
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
			atom = ((Atom)var19);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var18);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var20, 6);
			ret = true;
			break L1196;
		}
		return ret;
	}
	public boolean execL1197(Object var0, Object var1) {
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
L1197:
		{
			if (execL1199(var0, var1)) {
				ret = true;
				break L1197;
			}
			if (execL1201(var0, var1)) {
				ret = true;
				break L1197;
			}
			if (execL1203(var0, var1)) {
				ret = true;
				break L1197;
			}
		}
		return ret;
	}
	public boolean execL1203(Object var0, Object var1) {
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
L1203:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f5;
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
						func = f6;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var7 = atom;
							link = ((Atom)var7).getArg(0);
							var8 = link;
							if (execL1195(var0,var3,var7,var1)) {
								ret = true;
								break L1203;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1201(Object var0, Object var1) {
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
L1201:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f5;
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
						func = f2;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var7 = atom;
							link = ((Atom)var7).getArg(0);
							var8 = link;
							if (execL1195(var0,var3,var1,var7)) {
								ret = true;
								break L1201;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1199(Object var0, Object var1) {
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
L1199:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					func = f6;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var5 = atom;
						link = ((Atom)var5).getArg(0);
						var6 = link;
						func = f2;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var7 = atom;
							link = ((Atom)var7).getArg(0);
							var8 = link;
							if (execL1195(var0,var1,var5,var7)) {
								ret = true;
								break L1199;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1193(Object var0) {
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
L1193:
		{
			func = f5;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f9;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					func = f2;
					Iterator it3 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it3.hasNext()) {
						atom = (Atom) it3.next();
						var3 = atom;
						if (execL1184(var0,var1,var2,var3)) {
							ret = true;
							break L1193;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1184(Object var0, Object var1, Object var2, Object var3) {
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
L1184:
		{
			var4 = new Atom(null, f7);
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
					var7 = new Atom(null, f10);
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
							var10 = new Atom(null, f3);
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
									link = ((Atom)var1).getArg(0);
									var13 = link.getAtom();
									if (((Atom)var13).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var13).getFunctor()).getObject() instanceof String) {
										link = ((Atom)var1).getArg(1);
										var14 = link.getAtom();
										if (!(!(((Atom)var14).getFunctor() instanceof IntegerFunctor))) {
											link = ((Atom)var1).getArg(2);
											var15 = link.getAtom();
											if (((Atom)var15).getFunctor() instanceof ObjectFunctor &&
											    ((ObjectFunctor)((Atom)var15).getFunctor()).getObject() instanceof String) {
												if (execL1185(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var13,var14,var15)) {
													ret = true;
													break L1184;
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
	public boolean execL1185(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12) {
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
L1185:
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
			func = f9;
			var22 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var23 = ((AbstractMembrane)var0).newAtom(func);
			func = f11;
			var24 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var22).getMem().newLink(
				((Atom)var22), 0,
				((Atom)var19), 0 );
			((Atom)var22).getMem().newLink(
				((Atom)var22), 1,
				((Atom)var14), 0 );
			((Atom)var23).getMem().newLink(
				((Atom)var23), 0,
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
			atom = ((Atom)var23);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var22);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var24, 5);
			ret = true;
			break L1185;
		}
		return ret;
	}
	public boolean execL1186(Object var0, Object var1) {
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
L1186:
		{
			if (execL1188(var0, var1)) {
				ret = true;
				break L1186;
			}
			if (execL1190(var0, var1)) {
				ret = true;
				break L1186;
			}
			if (execL1192(var0, var1)) {
				ret = true;
				break L1186;
			}
		}
		return ret;
	}
	public boolean execL1192(Object var0, Object var1) {
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
L1192:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f5;
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
						func = f9;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var7 = atom;
							link = ((Atom)var7).getArg(0);
							var8 = link;
							link = ((Atom)var7).getArg(1);
							var9 = link;
							if (execL1184(var0,var3,var7,var1)) {
								ret = true;
								break L1192;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1190(Object var0, Object var1) {
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
L1190:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f5;
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
						func = f2;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var8 = atom;
							link = ((Atom)var8).getArg(0);
							var9 = link;
							if (execL1184(var0,var4,var1,var8)) {
								ret = true;
								break L1190;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1188(Object var0, Object var1) {
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
L1188:
		{
			if (!(!(f5).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					func = f9;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var5 = atom;
						link = ((Atom)var5).getArg(0);
						var6 = link;
						link = ((Atom)var5).getArg(1);
						var7 = link;
						func = f2;
						Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it2.hasNext()) {
							atom = (Atom) it2.next();
							var8 = atom;
							link = ((Atom)var8).getArg(0);
							var9 = link;
							if (execL1184(var0,var1,var5,var8)) {
								ret = true;
								break L1188;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1182(Object var0) {
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
L1182:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f2;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					if (execL1175(var0,var1,var2)) {
						ret = true;
						break L1182;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1175(Object var0, Object var1, Object var2) {
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
L1175:
		{
			var3 = new Atom(null, f3);
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
					if (execL1176(var0,var1,var2,var3,var4)) {
						ret = true;
						break L1175;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1176(Object var0, Object var1, Object var2, Object var3, Object var4) {
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
L1176:
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
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f2;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var8, 4);
			ret = true;
			break L1176;
		}
		return ret;
	}
	public boolean execL1177(Object var0, Object var1) {
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
L1177:
		{
			if (execL1179(var0, var1)) {
				ret = true;
				break L1177;
			}
			if (execL1181(var0, var1)) {
				ret = true;
				break L1177;
			}
		}
		return ret;
	}
	public boolean execL1181(Object var0, Object var1) {
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
L1181:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f12;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						if (execL1175(var0,var3,var1)) {
							ret = true;
							break L1181;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1179(Object var0, Object var1) {
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
L1179:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					func = f2;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var2 = atom;
						link = ((Atom)var2).getArg(0);
						var3 = link;
						if (execL1175(var0,var1,var2)) {
							ret = true;
							break L1179;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1173(Object var0) {
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
L1173:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f2;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					if (execL1166(var0,var1,var2)) {
						ret = true;
						break L1173;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1166(Object var0, Object var1, Object var2) {
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
L1166:
		{
			var3 = new Atom(null, f3);
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
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						link = ((Atom)var1).getArg(1);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
							if (execL1167(var0,var1,var2,var3,var4,var6,var7)) {
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
	public boolean execL1167(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
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
L1167:
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
			func = f2;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f15;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
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
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var12, 3);
			ret = true;
			break L1167;
		}
		return ret;
	}
	public boolean execL1168(Object var0, Object var1) {
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
L1168:
		{
			if (execL1170(var0, var1)) {
				ret = true;
				break L1168;
			}
			if (execL1172(var0, var1)) {
				ret = true;
				break L1168;
			}
		}
		return ret;
	}
	public boolean execL1172(Object var0, Object var1) {
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
L1172:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f14;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(1);
						var5 = link;
						if (execL1166(var0,var3,var1)) {
							ret = true;
							break L1172;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1170(Object var0, Object var1) {
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
L1170:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f2;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						if (execL1166(var0,var1,var4)) {
							ret = true;
							break L1170;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1164(Object var0) {
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
L1164:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f2;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					if (execL1157(var0,var1,var2)) {
						ret = true;
						break L1164;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1157(Object var0, Object var1, Object var2) {
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
L1157:
		{
			var3 = new Atom(null, f3);
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
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (((Atom)var6).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var6).getFunctor()).getObject() instanceof String) {
						if (execL1158(var0,var1,var2,var3,var4,var6)) {
							ret = true;
							break L1157;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1158(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L1158:
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
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f2;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var7), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 1,
				((Atom)var8), 0 );
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var10, 2);
			ret = true;
			break L1158;
		}
		return ret;
	}
	public boolean execL1159(Object var0, Object var1) {
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
			if (execL1161(var0, var1)) {
				ret = true;
				break L1159;
			}
			if (execL1163(var0, var1)) {
				ret = true;
				break L1159;
			}
		}
		return ret;
	}
	public boolean execL1163(Object var0, Object var1) {
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
L1163:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f16;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						if (execL1157(var0,var3,var1)) {
							ret = true;
							break L1163;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1161(Object var0, Object var1) {
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
L1161:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f2;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						if (execL1157(var0,var1,var3)) {
							ret = true;
							break L1161;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1155(Object var0) {
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
L1155:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f2;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					if (execL1148(var0,var1,var2)) {
						ret = true;
						break L1155;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1148(Object var0, Object var1, Object var2) {
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
L1148:
		{
			var3 = new Atom(null, f3);
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
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
						link = ((Atom)var1).getArg(1);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof IntegerFunctor))) {
							if (execL1149(var0,var1,var2,var3,var4,var6,var7)) {
								ret = true;
								break L1148;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1149(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
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
L1149:
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
			func = f2;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			func = f19;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
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
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodewt.run((Atom)var12, 1);
			ret = true;
			break L1149;
		}
		return ret;
	}
	public boolean execL1150(Object var0, Object var1) {
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
			if (execL1152(var0, var1)) {
				ret = true;
				break L1150;
			}
			if (execL1154(var0, var1)) {
				ret = true;
				break L1150;
			}
		}
		return ret;
	}
	public boolean execL1154(Object var0, Object var1) {
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
L1154:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f18;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(1);
						var5 = link;
						if (execL1148(var0,var3,var1)) {
							ret = true;
							break L1154;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1152(Object var0, Object var1) {
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
L1152:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f2;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						if (execL1148(var0,var1,var4)) {
							ret = true;
							break L1152;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1146(Object var0) {
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
L1146:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1141(var0,var1)) {
					ret = true;
					break L1146;
				}
			}
		}
		return ret;
	}
	public boolean execL1141(Object var0, Object var1) {
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
			if (execL1142(var0,var1)) {
				ret = true;
				break L1141;
			}
		}
		return ret;
	}
	public boolean execL1142(Object var0, Object var1) {
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
L1142:
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
			break L1142;
		}
		return ret;
	}
	public boolean execL1143(Object var0, Object var1) {
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
			if (execL1145(var0, var1)) {
				ret = true;
				break L1143;
			}
		}
		return ret;
	}
	public boolean execL1145(Object var0, Object var1) {
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
L1145:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (execL1141(var0,var1)) {
						ret = true;
						break L1145;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1139(Object var0) {
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
L1139:
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
											if (execL1131(var0,var4,var1,var2,var5,var3)) {
												ret = true;
												break L1139;
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
	public boolean execL1131(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
			if (execL1132(var0,var1,var2,var3,var4,var5)) {
				ret = true;
				break L1131;
			}
		}
		return ret;
	}
	public boolean execL1132(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
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
L1132:
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
			mem = ((AbstractMembrane)var1);
			mem.getParent().removeMem(mem);
			((AbstractMembrane)var1).removeProxies();
			mem = ((AbstractMembrane)var0).newMem();
			var6 = mem;
			((AbstractMembrane)var6).moveCellsFrom(((AbstractMembrane)var1));
			((AbstractMembrane)var0).insertProxies(((AbstractMembrane)var6));
			((AbstractMembrane)var6).copyRulesFrom(((AbstractMembrane)var1));
			func = f20;
			var7 = ((AbstractMembrane)var6).newAtom(func);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_wt");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var6).loadRuleset(rulesets[i]);
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
			((AbstractMembrane)var1).free();
			ret = true;
			break L1132;
		}
		return ret;
	}
	public boolean execL1133(Object var0, Object var1) {
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
L1133:
		{
			if (execL1135(var0, var1)) {
				ret = true;
				break L1133;
			}
		}
		return ret;
	}
	public boolean execL1135(Object var0, Object var1) {
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
L1135:
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
												if (execL1131(var0,var7,var1,var3,var10,var6)) {
													ret = true;
													break L1135;
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
	private static final Functor f9 = new Functor("grid", 2, null);
	private static final Functor f13 = new StringFunctor("/*inline*/\\r\\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n    Panel border = new Panel(new BorderLayout());\\r\\n    frame.getContentPane().add(border);\\r\\n    Atom a = mem.newAtom(new Functor(\"border\",1));\\r\\n    Atom b = mem.newAtom(new ObjectFunctor(border));\\r\\n    mem.newLink(a,0,b,0);\\r\\n    mem.removeAtom(me.nthAtom(0));\\r\\n    mem.removeAtom(me);\\r\\n  ");
	private static final Functor f8 = new Functor("/*inline*/\\r\\n        final Membrane memb = (Membrane)mem;\\r\\n        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n        Panel border = (Panel)panelfunc.getObject();\\r\\n        String title = me.nthAtom(1).toString();\\r\\n        String location = me.nthAtom(2).toString();\\r\\n        final String event = me.nthAtom(3).toString();\\r\\n        Button bt = new Button(title);\\r\\n        bt.setVisible(true);\\r\\n        bt.addActionListener(new ActionListener(){\\r\\n          public void actionPerformed(ActionEvent e){\\r\\n            memb.asyncLock();\\r\\n            memb.newAtom(new Functor(event,0));\\r\\n            memb.asyncUnlock();\\r\\n          }\\r\\n        });\\r\\n        border.add(location,bt);\\r\\n        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(4).getFunctor();\\r\\n        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n        frame.setVisible(true);\\r\\n        mem.removeAtom(me.nthAtom(0));\\r\\n        mem.removeAtom(me.nthAtom(1));\\r\\n        mem.removeAtom(me.nthAtom(2));\\r\\n        mem.removeAtom(me.nthAtom(3));\\r\\n        mem.removeAtom(me.nthAtom(4));\\r\\n        mem.removeAtom(me); \\r\\n      ", 5, null);
	private static final Functor f3 = new StringFunctor("LMNtalFrame");
	private static final Functor f18 = new Functor("size", 2, null);
	private static final Functor f2 = new Functor("frame", 1, null);
	private static final Functor f4 = new StringFunctor("/*inline*/\\r\\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n    frame.setVisible(false);\\r\\n    frame.dispose();\\r\\n    mem.removeAtom(me.nthAtom(0));\\r\\n    mem.removeAtom(me);\\r\\n  ");
	private static final Functor f0 = new Functor("terminated", 0, null);
	private static final Functor f22 = new Functor("newFrame", 1, "wt");
	private static final Functor f16 = new Functor("title", 1, null);
	private static final Functor f23 = new Functor("+", 1, null);
	private static final Functor f19 = new Functor("/*inline*/\\r\\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n    int w = Integer.parseInt(me.nth(1));\\r\\n    int h = Integer.parseInt(me.nth(2));\\r\\n    frame.setSize(w,h);\\r\\n    mem.removeAtom(me.nthAtom(0));\\r\\n    mem.removeAtom(me.nthAtom(1));\\r\\n    mem.removeAtom(me.nthAtom(2));\\r\\n    mem.removeAtom(me);\\r\\n    frame.setVisible(true); \\r\\n  ", 3, null);
	private static final Functor f1 = new Functor("terminate", 0, null);
	private static final Functor f5 = new Functor("addButton", 3, null);
	private static final Functor f7 = new StringFunctor("java.awt.Panel");
	private static final Functor f11 = new Functor("/*inline*/\\r\\n        final Membrane memb = (Membrane)mem;\\r\\n        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n        Panel grid = (Panel)panelfunc.getObject();\\r\\n        ObjectFunctor queuefunc = (ObjectFunctor)me.nthAtom(1).getFunctor();\\r\\n        PriorityQueue pQueue = (PriorityQueue)queuefunc.getObject();\\r\\n        String title = me.nthAtom(2).toString();\\r\\n        int location = Integer.parseInt(me.nth(3));\\r\\n        final String event = me.nthAtom(4).toString();\\r\\n        Button bt = new Button(title);\\r\\n        bt.setVisible(true);\\r\\n        bt.addActionListener(new ActionListener(){\\r\\n          public void actionPerformed(ActionEvent e) {\\r\\n            memb.asyncLock();\\r\\n            memb.newAtom(new Functor(event,0));\\r\\n            memb.asyncUnlock();\\r\\n          }\\r\\n        });\\r\\n        int index = pQueue.insert(location);\\r\\n        grid.add(bt,index);\\r\\n        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(5).getFunctor();\\r\\n        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n        frame.setVisible(true);\\r\\n        mem.removeAtom(me.nthAtom(0));\\r\\n        mem.removeAtom(me.nthAtom(1));\\r\\n        mem.removeAtom(me.nthAtom(2));\\r\\n        mem.removeAtom(me.nthAtom(3));\\r\\n        mem.removeAtom(me.nthAtom(4));\\r\\n        mem.removeAtom(me.nthAtom(5));\\r\\n        mem.removeAtom(me); \\r\\n      ", 6, null);
	private static final Functor f20 = new Functor("createFrame", 0, "wt");
	private static final Functor f6 = new Functor("border", 1, null);
	private static final Functor f14 = new Functor("gridPanel", 2, null);
	private static final Functor f10 = new StringFunctor("PriorityQueue");
	private static final Functor f17 = new Functor("/*inline*/\\r\\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n    String title = me.nthAtom(1).toString();\\r\\n    frame.setTitle(title);\\r\\n    mem.removeAtom(me.nthAtom(0));\\r\\n    mem.removeAtom(me.nthAtom(1));\\r\\n    mem.removeAtom(me);\\r\\n  ", 2, null);
	private static final Functor f21 = new Functor("/*inline*/\\r\\n    LMNtalFrame frame = new LMNtalFrame((Membrane)mem);\\r\\n    Atom a = mem.newAtom(new Functor(\"frame\",1));\\r\\n    Atom b = mem.newAtom(new ObjectFunctor(frame));\\r\\n    mem.newLink(a,0,b,0);\\r\\n    mem.removeAtom(me);\\r\\n    mem.makePerpetual();\\r\\n  ", 0, null);
	private static final Functor f15 = new Functor("/*inline*/\\r\\n    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();\\r\\n    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();\\r\\n    int rows = Integer.parseInt(me.nth(1));\\r\\n    int cols = Integer.parseInt(me.nth(2));\\r\\n    Panel grid = new Panel(new GridLayout(rows,cols));\\r\\n    frame.getContentPane().add(grid);\\r\\n    PriorityQueue pQueue = new PriorityQueue();\\r\\n    Atom a = mem.newAtom(new Functor(\"grid\",2));\\r\\n    Atom b = mem.newAtom(new ObjectFunctor(grid));\\r\\n    Atom c = mem.newAtom(new ObjectFunctor(pQueue));\\r\\n    mem.newLink(a,0,b,0);\\r\\n    mem.newLink(a,1,c,0);\\r\\n    mem.removeAtom(me.nthAtom(0));\\r\\n    mem.removeAtom(me.nthAtom(1));\\r\\n    mem.removeAtom(me.nthAtom(2));\\r\\n    mem.removeAtom(me); \\r\\n  ", 3, null);
	private static final Functor f12 = new Functor("borderPanel", 0, null);
}

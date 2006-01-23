package translated.module_graphic;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset605 extends Ruleset {
	private static final Ruleset605 theInstance = new Ruleset605();
	private Ruleset605() {}
	public static Ruleset605 getInstance() {
		return theInstance;
	}
	private int id = 605;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":graphic" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@graphic" + id;
	}
	private String encodedRuleset = 
"(graphic.use :- [:/*inline*/\tEnv.fGraphic = true;\tEnv.initGraphic();\tme.setFunctor(\"go\",0);:]), (graphic.perpetualUse :- [:/*inline*/\tEnv.fGraphic = true;\tEnv.initGraphic();\tmem.makePerpetual();\tme.setFunctor(\"go\",0);:]), ('='(H, graphic.mousePointX) :- '='(H, [:/*inline*/\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\tint point = (int)(pointerInfo.getLocation().x);\tAtom atom = mem.newAtom(new IntegerFunctor(point));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);  :])), ('='(H, graphic.mousePointY) :- '='(H, [:/*inline*/\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\tint point = (int)(pointerInfo.getLocation().y);\tAtom atom = mem.newAtom(new IntegerFunctor(point));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);  :])), ('='(H, graphic.mouseFramePointX(X)) :- int(X) | '='(H, [:/*inline*/\tint x = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\tx = (int)(p.x);\tAtom atom = mem.newAtom(new IntegerFunctor(x));\tmem.relink(atom, 0, me, 1);\tme.nthAtom(0).remove();\tme.remove();\t\t\t:](X))), ('='(H, graphic.mouseFramePointY(Y)) :- int(Y) | '='(H, [:/*inline*/\tint y = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\ty = (int)(p.y);\tAtom atom = mem.newAtom(new IntegerFunctor(y));\tmem.relink(atom, 0, me, 1);\tme.nthAtom(0).remove();\tme.remove();\t:](Y))), ('='(H, graphic.mouseFramePointX) :- '='(H, [:/*inline*/\tint x = -1;\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\tx = (int)(p.x);\tAtom atom = mem.newAtom(new IntegerFunctor(x));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);\t\t:])), ('='(H, graphic.mouseFramePointY) :- '='(H, [:/*inline*/\tint y = -1;\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\ty = (int)(p.y);\tAtom atom = mem.newAtom(new IntegerFunctor(y));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);\t:]))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL351(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "use");
			return true;
		}
		if (execL360(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "perpetualUse");
			return true;
		}
		if (execL369(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mousePointX");
			return true;
		}
		if (execL378(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mousePointY");
			return true;
		}
		if (execL387(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mouseFramePointX");
			return true;
		}
		if (execL396(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mouseFramePointY");
			return true;
		}
		if (execL405(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mouseFramePointX");
			return true;
		}
		if (execL414(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@605", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL354(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "use");
			return true;
		}
		if (execL363(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "perpetualUse");
			return true;
		}
		if (execL372(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mousePointX");
			return true;
		}
		if (execL381(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mousePointY");
			return true;
		}
		if (execL390(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mouseFramePointX");
			return true;
		}
		if (execL399(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mouseFramePointY");
			return true;
		}
		if (execL408(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mouseFramePointX");
			return true;
		}
		if (execL417(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@605", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean execL417(Object var0, boolean nondeterministic) {
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
L417:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL413(var0,var1,nondeterministic)) {
					ret = true;
					break L417;
				}
			}
		}
		return ret;
	}
	public boolean execL413(Object var0, Object var1, boolean nondeterministic) {
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
L413:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f1;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 7);
			ret = true;
			break L413;
		}
		return ret;
	}
	public boolean execL414(Object var0, Object var1, boolean nondeterministic) {
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
L414:
		{
			if (execL416(var0, var1, nondeterministic)) {
				ret = true;
				break L414;
			}
		}
		return ret;
	}
	public boolean execL416(Object var0, Object var1, boolean nondeterministic) {
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
L416:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL413(var0,var1,nondeterministic)) {
						ret = true;
						break L416;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL408(Object var0, boolean nondeterministic) {
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
L408:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL404(var0,var1,nondeterministic)) {
					ret = true;
					break L408;
				}
			}
		}
		return ret;
	}
	public boolean execL404(Object var0, Object var1, boolean nondeterministic) {
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
L404:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f3;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 6);
			ret = true;
			break L404;
		}
		return ret;
	}
	public boolean execL405(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL407(var0, var1, nondeterministic)) {
				ret = true;
				break L405;
			}
		}
		return ret;
	}
	public boolean execL407(Object var0, Object var1, boolean nondeterministic) {
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
L407:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL404(var0,var1,nondeterministic)) {
						ret = true;
						break L407;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL399(Object var0, boolean nondeterministic) {
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
L399:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL395(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L399;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL395(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L395:
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
			SomeInlineCodegraphic.run((Atom)var4, 5);
			ret = true;
			break L395;
		}
		return ret;
	}
	public boolean execL396(Object var0, Object var1, boolean nondeterministic) {
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
L396:
		{
			if (execL398(var0, var1, nondeterministic)) {
				ret = true;
				break L396;
			}
		}
		return ret;
	}
	public boolean execL398(Object var0, Object var1, boolean nondeterministic) {
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
L398:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL395(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L398;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL390(Object var0, boolean nondeterministic) {
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
L390:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL386(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L390;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL386(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L386:
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
			SomeInlineCodegraphic.run((Atom)var4, 4);
			ret = true;
			break L386;
		}
		return ret;
	}
	public boolean execL387(Object var0, Object var1, boolean nondeterministic) {
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
L387:
		{
			if (execL389(var0, var1, nondeterministic)) {
				ret = true;
				break L387;
			}
		}
		return ret;
	}
	public boolean execL389(Object var0, Object var1, boolean nondeterministic) {
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
L389:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL386(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L389;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL381(Object var0, boolean nondeterministic) {
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
L381:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL377(var0,var1,nondeterministic)) {
					ret = true;
					break L381;
				}
			}
		}
		return ret;
	}
	public boolean execL377(Object var0, Object var1, boolean nondeterministic) {
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
L377:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f9;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 3);
			ret = true;
			break L377;
		}
		return ret;
	}
	public boolean execL378(Object var0, Object var1, boolean nondeterministic) {
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
L378:
		{
			if (execL380(var0, var1, nondeterministic)) {
				ret = true;
				break L378;
			}
		}
		return ret;
	}
	public boolean execL380(Object var0, Object var1, boolean nondeterministic) {
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
L380:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL377(var0,var1,nondeterministic)) {
						ret = true;
						break L380;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL372(Object var0, boolean nondeterministic) {
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
L372:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL368(var0,var1,nondeterministic)) {
					ret = true;
					break L372;
				}
			}
		}
		return ret;
	}
	public boolean execL368(Object var0, Object var1, boolean nondeterministic) {
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
L368:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f11;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 2);
			ret = true;
			break L368;
		}
		return ret;
	}
	public boolean execL369(Object var0, Object var1, boolean nondeterministic) {
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
L369:
		{
			if (execL371(var0, var1, nondeterministic)) {
				ret = true;
				break L369;
			}
		}
		return ret;
	}
	public boolean execL371(Object var0, Object var1, boolean nondeterministic) {
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
L371:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL368(var0,var1,nondeterministic)) {
						ret = true;
						break L371;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL363(Object var0, boolean nondeterministic) {
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
L363:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "perpetualUse", "L359",var0,var1});
				} else if (execL359(var0,var1,nondeterministic)) {
					ret = true;
					break L363;
				}
			}
		}
		return ret;
	}
	public boolean execL359(Object var0, Object var1, boolean nondeterministic) {
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
L359:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f13;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 1);
			ret = true;
			break L359;
		}
		return ret;
	}
	public boolean execL360(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
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
			if (execL362(var0, var1, nondeterministic)) {
				ret = true;
				break L360;
			}
		}
		return ret;
	}
	public boolean execL362(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
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
L362:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "perpetualUse", "L359",var0,var1});
					} else if (execL359(var0,var1,nondeterministic)) {
						ret = true;
						break L362;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL354(Object var0, boolean nondeterministic) {
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
L354:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use", "L350",var0,var1});
				} else if (execL350(var0,var1,nondeterministic)) {
					ret = true;
					break L354;
				}
			}
		}
		return ret;
	}
	public boolean execL350(Object var0, Object var1, boolean nondeterministic) {
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
L350:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f15;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 0);
			ret = true;
			break L350;
		}
		return ret;
	}
	public boolean execL351(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
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
			if (execL353(var0, var1, nondeterministic)) {
				ret = true;
				break L351;
			}
		}
		return ret;
	}
	public boolean execL353(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
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
L353:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use", "L350",var0,var1});
					} else if (execL350(var0,var1,nondeterministic)) {
						ret = true;
						break L353;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f3 = new StringFunctor("/*inline*/\r\n\tint x = -1;\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\tx = (int)(p.x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(x));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n\t\r\n\t");
	private static final Functor f7 = new Functor("/*inline*/\r\n\r\n\tint x = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\tx = (int)(p.x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(x));\r\n\tmem.relink(atom, 0, me, 1);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t\r\n\t\r\n\t", 2, null);
	private static final Functor f8 = new Functor("mousePointY", 1, "graphic");
	private static final Functor f2 = new Functor("mouseFramePointX", 1, "graphic");
	private static final Functor f13 = new Functor("/*inline*/\r\n\tEnv.fGraphic = true;\r\n\tEnv.initGraphic();\r\n\tmem.makePerpetual();\r\n\tme.setFunctor(\"go\",0);\r\n", 0, null);
	private static final Functor f10 = new Functor("mousePointX", 1, "graphic");
	private static final Functor f12 = new Functor("perpetualUse", 0, "graphic");
	private static final Functor f5 = new Functor("/*inline*/\r\n\tint y = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\ty = (int)(p.y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(y));\r\n\tmem.relink(atom, 0, me, 1);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t", 2, null);
	private static final Functor f0 = new Functor("mouseFramePointY", 1, "graphic");
	private static final Functor f4 = new Functor("mouseFramePointY", 2, "graphic");
	private static final Functor f6 = new Functor("mouseFramePointX", 2, "graphic");
	private static final Functor f1 = new StringFunctor("/*inline*/\r\n\tint y = -1;\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\ty = (int)(p.y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(y));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n\t");
	private static final Functor f15 = new Functor("/*inline*/\r\n\tEnv.fGraphic = true;\r\n\tEnv.initGraphic();\r\n\tme.setFunctor(\"go\",0);\r\n", 0, null);
	private static final Functor f14 = new Functor("use", 0, "graphic");
	private static final Functor f9 = new StringFunctor("/*inline*/\r\n\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\r\n\tint point = (int)(pointerInfo.getLocation().y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(point));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n  ");
	private static final Functor f11 = new StringFunctor("/*inline*/\r\n\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\r\n\tint point = (int)(pointerInfo.getLocation().x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(point));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n  ");
}

package translated.module_graphic;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset609 extends Ruleset {
	private static final Ruleset609 theInstance = new Ruleset609();
	private Ruleset609() {}
	public static Ruleset609 getInstance() {
		return theInstance;
	}
	private int id = 609;
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
		if (execL491(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "use");
			return true;
		}
		if (execL500(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "perpetualUse");
			return true;
		}
		if (execL509(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mousePointX");
			return true;
		}
		if (execL518(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mousePointY");
			return true;
		}
		if (execL527(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mouseFramePointX");
			return true;
		}
		if (execL536(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mouseFramePointY");
			return true;
		}
		if (execL545(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mouseFramePointX");
			return true;
		}
		if (execL554(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@609", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL494(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "use");
			return true;
		}
		if (execL503(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "perpetualUse");
			return true;
		}
		if (execL512(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mousePointX");
			return true;
		}
		if (execL521(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mousePointY");
			return true;
		}
		if (execL530(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mouseFramePointX");
			return true;
		}
		if (execL539(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mouseFramePointY");
			return true;
		}
		if (execL548(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mouseFramePointX");
			return true;
		}
		if (execL557(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@609", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean execL557(Object var0, boolean nondeterministic) {
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
L557:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL553(var0,var1,nondeterministic)) {
					ret = true;
					break L557;
				}
			}
		}
		return ret;
	}
	public boolean execL553(Object var0, Object var1, boolean nondeterministic) {
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
L553:
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
			break L553;
		}
		return ret;
	}
	public boolean execL554(Object var0, Object var1, boolean nondeterministic) {
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
L554:
		{
			if (execL556(var0, var1, nondeterministic)) {
				ret = true;
				break L554;
			}
		}
		return ret;
	}
	public boolean execL556(Object var0, Object var1, boolean nondeterministic) {
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
L556:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL553(var0,var1,nondeterministic)) {
						ret = true;
						break L556;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL548(Object var0, boolean nondeterministic) {
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
L548:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL544(var0,var1,nondeterministic)) {
					ret = true;
					break L548;
				}
			}
		}
		return ret;
	}
	public boolean execL544(Object var0, Object var1, boolean nondeterministic) {
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
L544:
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
			break L544;
		}
		return ret;
	}
	public boolean execL545(Object var0, Object var1, boolean nondeterministic) {
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
L545:
		{
			if (execL547(var0, var1, nondeterministic)) {
				ret = true;
				break L545;
			}
		}
		return ret;
	}
	public boolean execL547(Object var0, Object var1, boolean nondeterministic) {
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
L547:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL544(var0,var1,nondeterministic)) {
						ret = true;
						break L547;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL539(Object var0, boolean nondeterministic) {
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
L539:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL535(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L539;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL535(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L535:
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
			break L535;
		}
		return ret;
	}
	public boolean execL536(Object var0, Object var1, boolean nondeterministic) {
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
L536:
		{
			if (execL538(var0, var1, nondeterministic)) {
				ret = true;
				break L536;
			}
		}
		return ret;
	}
	public boolean execL538(Object var0, Object var1, boolean nondeterministic) {
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
L538:
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
						if (execL535(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L538;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL530(Object var0, boolean nondeterministic) {
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
L530:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL526(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L530;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL526(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L526:
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
			break L526;
		}
		return ret;
	}
	public boolean execL527(Object var0, Object var1, boolean nondeterministic) {
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
L527:
		{
			if (execL529(var0, var1, nondeterministic)) {
				ret = true;
				break L527;
			}
		}
		return ret;
	}
	public boolean execL529(Object var0, Object var1, boolean nondeterministic) {
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
L529:
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
						if (execL526(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L529;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL521(Object var0, boolean nondeterministic) {
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
L521:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL517(var0,var1,nondeterministic)) {
					ret = true;
					break L521;
				}
			}
		}
		return ret;
	}
	public boolean execL517(Object var0, Object var1, boolean nondeterministic) {
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
L517:
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
			break L517;
		}
		return ret;
	}
	public boolean execL518(Object var0, Object var1, boolean nondeterministic) {
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
L518:
		{
			if (execL520(var0, var1, nondeterministic)) {
				ret = true;
				break L518;
			}
		}
		return ret;
	}
	public boolean execL520(Object var0, Object var1, boolean nondeterministic) {
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
L520:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL517(var0,var1,nondeterministic)) {
						ret = true;
						break L520;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL512(Object var0, boolean nondeterministic) {
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
L512:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL508(var0,var1,nondeterministic)) {
					ret = true;
					break L512;
				}
			}
		}
		return ret;
	}
	public boolean execL508(Object var0, Object var1, boolean nondeterministic) {
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
L508:
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
			break L508;
		}
		return ret;
	}
	public boolean execL509(Object var0, Object var1, boolean nondeterministic) {
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
L509:
		{
			if (execL511(var0, var1, nondeterministic)) {
				ret = true;
				break L509;
			}
		}
		return ret;
	}
	public boolean execL511(Object var0, Object var1, boolean nondeterministic) {
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
L511:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL508(var0,var1,nondeterministic)) {
						ret = true;
						break L511;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL503(Object var0, boolean nondeterministic) {
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
L503:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "perpetualUse", "L499",var0,var1});
				} else if (execL499(var0,var1,nondeterministic)) {
					ret = true;
					break L503;
				}
			}
		}
		return ret;
	}
	public boolean execL499(Object var0, Object var1, boolean nondeterministic) {
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
L499:
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
			break L499;
		}
		return ret;
	}
	public boolean execL500(Object var0, Object var1, boolean nondeterministic) {
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
L500:
		{
			if (execL502(var0, var1, nondeterministic)) {
				ret = true;
				break L500;
			}
		}
		return ret;
	}
	public boolean execL502(Object var0, Object var1, boolean nondeterministic) {
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
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "perpetualUse", "L499",var0,var1});
					} else if (execL499(var0,var1,nondeterministic)) {
						ret = true;
						break L502;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL494(Object var0, boolean nondeterministic) {
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
L494:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use", "L490",var0,var1});
				} else if (execL490(var0,var1,nondeterministic)) {
					ret = true;
					break L494;
				}
			}
		}
		return ret;
	}
	public boolean execL490(Object var0, Object var1, boolean nondeterministic) {
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
L490:
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
			break L490;
		}
		return ret;
	}
	public boolean execL491(Object var0, Object var1, boolean nondeterministic) {
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
			if (execL493(var0, var1, nondeterministic)) {
				ret = true;
				break L491;
			}
		}
		return ret;
	}
	public boolean execL493(Object var0, Object var1, boolean nondeterministic) {
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
L493:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use", "L490",var0,var1});
					} else if (execL490(var0,var1,nondeterministic)) {
						ret = true;
						break L493;
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

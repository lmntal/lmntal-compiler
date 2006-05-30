package translated.module_graphic;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset607 extends Ruleset {
	private static final Ruleset607 theInstance = new Ruleset607();
	private Ruleset607() {}
	public static Ruleset607 getInstance() {
		return theInstance;
	}
	private int id = 607;
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
"(graphic.use :- [:/*inline*/\tEnv.fGraphic = true;\tEnv.initGraphic();\tAtom atom = mem.newAtom(new Functor(\"go\", 0));\tme.remove();:]), (graphic.norepaint :- [:/*inline*/\tif(null != Env.LMNgraphic){\t\tEnv.LMNgraphic.setRepaint((Membrane)mem, false);\t\tAtom atom = mem.newAtom(new Functor(\"norepaint_go\", 0));\t\tme.remove();\t}\t:]), (graphic.repaint :- [:/*inline*/\tif(null != Env.LMNgraphic){\t\tEnv.LMNgraphic.setRepaint((Membrane)mem, true);\t\tAtom atom = mem.newAtom(new Functor(\"repaint_go\", 0));\t\tme.remove();\t}:]), (graphic.perpetualUse :- [:/*inline*/\tEnv.fGraphic = true;\tEnv.initGraphic();\tmem.makePerpetual();\tAtom atom = mem.newAtom(new Functor(\"go\", 0));\tme.remove();:]), ('='(H, graphic.mousePointX) :- '='(H, [:/*inline*/\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\tint point = (int)(pointerInfo.getLocation().x);\tAtom atom = mem.newAtom(new IntegerFunctor(point));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);  :])), ('='(H, graphic.mousePointY) :- '='(H, [:/*inline*/\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\tint point = (int)(pointerInfo.getLocation().y);\tAtom atom = mem.newAtom(new IntegerFunctor(point));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);  :])), ('='(H, graphic.mouseFramePointX(X)) :- int(X) | '='(H, [:/*inline*/\tint x = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\tx = (int)(p.x);\tAtom atom = mem.newAtom(new IntegerFunctor(x));\tmem.relink(atom, 0, me, 1);\tme.nthAtom(0).remove();\tme.remove();\t\t\t:](X))), ('='(H, graphic.mouseFramePointY(Y)) :- int(Y) | '='(H, [:/*inline*/\tint y = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\ty = (int)(p.y);\tAtom atom = mem.newAtom(new IntegerFunctor(y));\tmem.relink(atom, 0, me, 1);\tme.nthAtom(0).remove();\tme.remove();\t:](Y))), ('='(H, graphic.mouseFramePointX) :- '='(H, [:/*inline*/\tint x = -1;\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\tx = (int)(p.x);\tAtom atom = mem.newAtom(new IntegerFunctor(x));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);\t\t:])), ('='(H, graphic.mouseFramePointY) :- '='(H, [:/*inline*/\tint y = -1;\tPoint p = null;\tif(Env.LMNgraphic != null)\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\tif(p != null)\t\ty = (int)(p.y);\tAtom atom = mem.newAtom(new IntegerFunctor(y));\tmem.relink(atom,0,me,0);\tmem.removeAtom(me);\t:])), ('='(H, graphic.windowWidth) :- '='(H, [:/*inline*/\tint width = 0;\tif(Env.LMNgraphic != null){\t\tDimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);\t\tif(null != d){ width = d.width; }\t}\tAtom atom = mem.newAtom(new IntegerFunctor(width));\tmem.relink(atom,0,me,0);\tme.remove();  :])), ('='(H, graphic.windowHeight) :- '='(H, [:/*inline*/\tint height = 0;\tif(Env.LMNgraphic != null){\t\tDimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);\t\tif(null != d){ height = d.height; }\t}\tAtom atom = mem.newAtom(new IntegerFunctor(height));\tmem.relink(atom,0,me,0);\tme.remove();  :]))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL372(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "use");
			return true;
		}
		if (execL381(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "norepaint");
			return true;
		}
		if (execL390(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "repaint");
			return true;
		}
		if (execL399(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "perpetualUse");
			return true;
		}
		if (execL408(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mousePointX");
			return true;
		}
		if (execL417(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mousePointY");
			return true;
		}
		if (execL426(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mouseFramePointX");
			return true;
		}
		if (execL435(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mouseFramePointY");
			return true;
		}
		if (execL444(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mouseFramePointX");
			return true;
		}
		if (execL453(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "mouseFramePointY");
			return true;
		}
		if (execL462(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "windowWidth");
			return true;
		}
		if (execL471(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@607", "windowHeight");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL375(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "use");
			return true;
		}
		if (execL384(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "norepaint");
			return true;
		}
		if (execL393(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "repaint");
			return true;
		}
		if (execL402(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "perpetualUse");
			return true;
		}
		if (execL411(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mousePointX");
			return true;
		}
		if (execL420(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mousePointY");
			return true;
		}
		if (execL429(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mouseFramePointX");
			return true;
		}
		if (execL438(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mouseFramePointY");
			return true;
		}
		if (execL447(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mouseFramePointX");
			return true;
		}
		if (execL456(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "mouseFramePointY");
			return true;
		}
		if (execL465(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "windowWidth");
			return true;
		}
		if (execL474(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@607", "windowHeight");
			return true;
		}
		return result;
	}
	public boolean execL474(Object var0, boolean nondeterministic) {
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
L474:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL470(var0,var1,nondeterministic)) {
					ret = true;
					break L474;
				}
			}
		}
		return ret;
	}
	public boolean execL470(Object var0, Object var1, boolean nondeterministic) {
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
L470:
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
			SomeInlineCodegraphic.run((Atom)var2, 11);
			ret = true;
			break L470;
		}
		return ret;
	}
	public boolean execL471(Object var0, Object var1, boolean nondeterministic) {
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
L471:
		{
			if (execL473(var0, var1, nondeterministic)) {
				ret = true;
				break L471;
			}
		}
		return ret;
	}
	public boolean execL473(Object var0, Object var1, boolean nondeterministic) {
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
L473:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL470(var0,var1,nondeterministic)) {
						ret = true;
						break L473;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL465(Object var0, boolean nondeterministic) {
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
L465:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL461(var0,var1,nondeterministic)) {
					ret = true;
					break L465;
				}
			}
		}
		return ret;
	}
	public boolean execL461(Object var0, Object var1, boolean nondeterministic) {
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
L461:
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
			SomeInlineCodegraphic.run((Atom)var2, 10);
			ret = true;
			break L461;
		}
		return ret;
	}
	public boolean execL462(Object var0, Object var1, boolean nondeterministic) {
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
L462:
		{
			if (execL464(var0, var1, nondeterministic)) {
				ret = true;
				break L462;
			}
		}
		return ret;
	}
	public boolean execL464(Object var0, Object var1, boolean nondeterministic) {
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
L464:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL461(var0,var1,nondeterministic)) {
						ret = true;
						break L464;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL456(Object var0, boolean nondeterministic) {
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
L456:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL452(var0,var1,nondeterministic)) {
					ret = true;
					break L456;
				}
			}
		}
		return ret;
	}
	public boolean execL452(Object var0, Object var1, boolean nondeterministic) {
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
L452:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 9);
			ret = true;
			break L452;
		}
		return ret;
	}
	public boolean execL453(Object var0, Object var1, boolean nondeterministic) {
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
L453:
		{
			if (execL455(var0, var1, nondeterministic)) {
				ret = true;
				break L453;
			}
		}
		return ret;
	}
	public boolean execL455(Object var0, Object var1, boolean nondeterministic) {
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
L455:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL452(var0,var1,nondeterministic)) {
						ret = true;
						break L455;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL447(Object var0, boolean nondeterministic) {
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
L447:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL443(var0,var1,nondeterministic)) {
					ret = true;
					break L447;
				}
			}
		}
		return ret;
	}
	public boolean execL443(Object var0, Object var1, boolean nondeterministic) {
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
L443:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f7;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 8);
			ret = true;
			break L443;
		}
		return ret;
	}
	public boolean execL444(Object var0, Object var1, boolean nondeterministic) {
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
L444:
		{
			if (execL446(var0, var1, nondeterministic)) {
				ret = true;
				break L444;
			}
		}
		return ret;
	}
	public boolean execL446(Object var0, Object var1, boolean nondeterministic) {
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
L446:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL443(var0,var1,nondeterministic)) {
						ret = true;
						break L446;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL438(Object var0, boolean nondeterministic) {
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
L438:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL434(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L438;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL434(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L434:
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
			SomeInlineCodegraphic.run((Atom)var4, 7);
			ret = true;
			break L434;
		}
		return ret;
	}
	public boolean execL435(Object var0, Object var1, boolean nondeterministic) {
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
L435:
		{
			if (execL437(var0, var1, nondeterministic)) {
				ret = true;
				break L435;
			}
		}
		return ret;
	}
	public boolean execL437(Object var0, Object var1, boolean nondeterministic) {
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
L437:
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
						if (execL434(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L437;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL429(Object var0, boolean nondeterministic) {
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
L429:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL425(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L429;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL425(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L425:
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
			SomeInlineCodegraphic.run((Atom)var4, 6);
			ret = true;
			break L425;
		}
		return ret;
	}
	public boolean execL426(Object var0, Object var1, boolean nondeterministic) {
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
L426:
		{
			if (execL428(var0, var1, nondeterministic)) {
				ret = true;
				break L426;
			}
		}
		return ret;
	}
	public boolean execL428(Object var0, Object var1, boolean nondeterministic) {
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
L428:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof IntegerFunctor))) {
						if (execL425(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L428;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL420(Object var0, boolean nondeterministic) {
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
L420:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL416(var0,var1,nondeterministic)) {
					ret = true;
					break L420;
				}
			}
		}
		return ret;
	}
	public boolean execL416(Object var0, Object var1, boolean nondeterministic) {
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
L416:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f13;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 5);
			ret = true;
			break L416;
		}
		return ret;
	}
	public boolean execL417(Object var0, Object var1, boolean nondeterministic) {
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
L417:
		{
			if (execL419(var0, var1, nondeterministic)) {
				ret = true;
				break L417;
			}
		}
		return ret;
	}
	public boolean execL419(Object var0, Object var1, boolean nondeterministic) {
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
L419:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL416(var0,var1,nondeterministic)) {
						ret = true;
						break L419;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL411(Object var0, boolean nondeterministic) {
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
L411:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL407(var0,var1,nondeterministic)) {
					ret = true;
					break L411;
				}
			}
		}
		return ret;
	}
	public boolean execL407(Object var0, Object var1, boolean nondeterministic) {
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
L407:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f15;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodegraphic.run((Atom)var2, 4);
			ret = true;
			break L407;
		}
		return ret;
	}
	public boolean execL408(Object var0, Object var1, boolean nondeterministic) {
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
L408:
		{
			if (execL410(var0, var1, nondeterministic)) {
				ret = true;
				break L408;
			}
		}
		return ret;
	}
	public boolean execL410(Object var0, Object var1, boolean nondeterministic) {
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
L410:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL407(var0,var1,nondeterministic)) {
						ret = true;
						break L410;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL402(Object var0, boolean nondeterministic) {
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
L402:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "perpetualUse", "L398",var0,var1});
				} else if (execL398(var0,var1,nondeterministic)) {
					ret = true;
					break L402;
				}
			}
		}
		return ret;
	}
	public boolean execL398(Object var0, Object var1, boolean nondeterministic) {
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
L398:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f17;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 3);
			ret = true;
			break L398;
		}
		return ret;
	}
	public boolean execL399(Object var0, Object var1, boolean nondeterministic) {
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
L399:
		{
			if (execL401(var0, var1, nondeterministic)) {
				ret = true;
				break L399;
			}
		}
		return ret;
	}
	public boolean execL401(Object var0, Object var1, boolean nondeterministic) {
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
L401:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "perpetualUse", "L398",var0,var1});
					} else if (execL398(var0,var1,nondeterministic)) {
						ret = true;
						break L401;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL393(Object var0, boolean nondeterministic) {
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
L393:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "repaint", "L389",var0,var1});
				} else if (execL389(var0,var1,nondeterministic)) {
					ret = true;
					break L393;
				}
			}
		}
		return ret;
	}
	public boolean execL389(Object var0, Object var1, boolean nondeterministic) {
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
L389:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f19;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 2);
			ret = true;
			break L389;
		}
		return ret;
	}
	public boolean execL390(Object var0, Object var1, boolean nondeterministic) {
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
L390:
		{
			if (execL392(var0, var1, nondeterministic)) {
				ret = true;
				break L390;
			}
		}
		return ret;
	}
	public boolean execL392(Object var0, Object var1, boolean nondeterministic) {
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
L392:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "repaint", "L389",var0,var1});
					} else if (execL389(var0,var1,nondeterministic)) {
						ret = true;
						break L392;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL384(Object var0, boolean nondeterministic) {
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
L384:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "norepaint", "L380",var0,var1});
				} else if (execL380(var0,var1,nondeterministic)) {
					ret = true;
					break L384;
				}
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
L380:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f21;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 1);
			ret = true;
			break L380;
		}
		return ret;
	}
	public boolean execL381(Object var0, Object var1, boolean nondeterministic) {
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
L381:
		{
			if (execL383(var0, var1, nondeterministic)) {
				ret = true;
				break L381;
			}
		}
		return ret;
	}
	public boolean execL383(Object var0, Object var1, boolean nondeterministic) {
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
L383:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "norepaint", "L380",var0,var1});
					} else if (execL380(var0,var1,nondeterministic)) {
						ret = true;
						break L383;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL375(Object var0, boolean nondeterministic) {
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
L375:
		{
			func = f22;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use", "L371",var0,var1});
				} else if (execL371(var0,var1,nondeterministic)) {
					ret = true;
					break L375;
				}
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
L371:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f23;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodegraphic.run((Atom)var2, 0);
			ret = true;
			break L371;
		}
		return ret;
	}
	public boolean execL372(Object var0, Object var1, boolean nondeterministic) {
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
L372:
		{
			if (execL374(var0, var1, nondeterministic)) {
				ret = true;
				break L372;
			}
		}
		return ret;
	}
	public boolean execL374(Object var0, Object var1, boolean nondeterministic) {
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
L374:
		{
			if (!(!(f22).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use", "L371",var0,var1});
					} else if (execL371(var0,var1,nondeterministic)) {
						ret = true;
						break L374;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f19 = new Functor("/*inline*/\r\n\tif(null != Env.LMNgraphic){\r\n\t\tEnv.LMNgraphic.setRepaint((Membrane)mem, true);\r\n\t\tAtom atom = mem.newAtom(new Functor(\"repaint_go\", 0));\r\n\t\tme.remove();\r\n\t}\r\n", 0, null);
	private static final Functor f2 = new Functor("windowWidth", 1, "graphic");
	private static final Functor f21 = new Functor("/*inline*/\r\n\tif(null != Env.LMNgraphic){\r\n\t\tEnv.LMNgraphic.setRepaint((Membrane)mem, false);\r\n\t\tAtom atom = mem.newAtom(new Functor(\"norepaint_go\", 0));\r\n\t\tme.remove();\r\n\t}\t\r\n", 0, null);
	private static final Functor f7 = new StringFunctor("/*inline*/\r\n\tint x = -1;\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\tx = (int)(p.x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(x));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n\t\r\n\t");
	private static final Functor f11 = new Functor("/*inline*/\r\n\r\n\tint x = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\tx = (int)(p.x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(x));\r\n\tmem.relink(atom, 0, me, 1);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t\r\n\t\r\n\t", 2, null);
	private static final Functor f20 = new Functor("norepaint", 0, "graphic");
	private static final Functor f0 = new Functor("windowHeight", 1, "graphic");
	private static final Functor f18 = new Functor("repaint", 0, "graphic");
	private static final Functor f12 = new Functor("mousePointY", 1, "graphic");
	private static final Functor f6 = new Functor("mouseFramePointX", 1, "graphic");
	private static final Functor f17 = new Functor("/*inline*/\r\n\tEnv.fGraphic = true;\r\n\tEnv.initGraphic();\r\n\tmem.makePerpetual();\r\n\tAtom atom = mem.newAtom(new Functor(\"go\", 0));\r\n\tme.remove();\r\n", 0, null);
	private static final Functor f16 = new Functor("perpetualUse", 0, "graphic");
	private static final Functor f14 = new Functor("mousePointX", 1, "graphic");
	private static final Functor f9 = new Functor("/*inline*/\r\n\tint y = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\ty = (int)(p.y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(y));\r\n\tmem.relink(atom, 0, me, 1);\r\n\tme.nthAtom(0).remove();\r\n\tme.remove();\r\n\t", 2, null);
	private static final Functor f4 = new Functor("mouseFramePointY", 1, "graphic");
	private static final Functor f8 = new Functor("mouseFramePointY", 2, "graphic");
	private static final Functor f10 = new Functor("mouseFramePointX", 2, "graphic");
	private static final Functor f5 = new StringFunctor("/*inline*/\r\n\tint y = -1;\r\n\tPoint p = null;\r\n\tif(Env.LMNgraphic != null)\r\n\t\tp= Env.LMNgraphic.getMousePoint((Membrane)mem);\r\n\tif(p != null)\r\n\t\ty = (int)(p.y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(y));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n\t");
	private static final Functor f23 = new Functor("/*inline*/\r\n\tEnv.fGraphic = true;\r\n\tEnv.initGraphic();\r\n\tAtom atom = mem.newAtom(new Functor(\"go\", 0));\r\n\tme.remove();\r\n", 0, null);
	private static final Functor f22 = new Functor("use", 0, "graphic");
	private static final Functor f15 = new StringFunctor("/*inline*/\r\n\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\r\n\tint point = (int)(pointerInfo.getLocation().x);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(point));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n  ");
	private static final Functor f13 = new StringFunctor("/*inline*/\r\n\tPointerInfo pointerInfo = MouseInfo.getPointerInfo();\r\n\tint point = (int)(pointerInfo.getLocation().y);\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(point));\r\n\tmem.relink(atom,0,me,0);\r\n\tmem.removeAtom(me);\r\n  ");
	private static final Functor f1 = new StringFunctor("/*inline*/\r\n\tint height = 0;\r\n\tif(Env.LMNgraphic != null){\r\n\t\tDimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);\r\n\t\tif(null != d){ height = d.height; }\r\n\t}\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(height));\r\n\tmem.relink(atom,0,me,0);\r\n\tme.remove();\r\n  ");
	private static final Functor f3 = new StringFunctor("/*inline*/\r\n\tint width = 0;\r\n\tif(Env.LMNgraphic != null){\r\n\t\tDimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);\r\n\t\tif(null != d){ width = d.width; }\r\n\t}\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(width));\r\n\tmem.relink(atom,0,me,0);\r\n\tme.remove();\r\n  ");
}

package translated.module_graphic;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset601 extends Ruleset {
	private static final Ruleset601 theInstance = new Ruleset601();
	private Ruleset601() {}
	public static Ruleset601 getInstance() {
		return theInstance;
	}
	private int id = 601;
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
		if (execL104(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "use");
			return true;
		}
		if (execL113(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "perpetualUse");
			return true;
		}
		if (execL122(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mousePointX");
			return true;
		}
		if (execL131(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mousePointY");
			return true;
		}
		if (execL140(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mouseFramePointX");
			return true;
		}
		if (execL149(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mouseFramePointY");
			return true;
		}
		if (execL158(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mouseFramePointX");
			return true;
		}
		if (execL167(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@601", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL107(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "use");
			return true;
		}
		if (execL116(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "perpetualUse");
			return true;
		}
		if (execL125(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mousePointX");
			return true;
		}
		if (execL134(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mousePointY");
			return true;
		}
		if (execL143(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mouseFramePointX");
			return true;
		}
		if (execL152(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mouseFramePointY");
			return true;
		}
		if (execL161(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mouseFramePointX");
			return true;
		}
		if (execL170(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@601", "mouseFramePointY");
			return true;
		}
		return result;
	}
	public boolean execL170(Object var0, boolean nondeterministic) {
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
L170:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL166(var0,var1,nondeterministic)) {
					ret = true;
					break L170;
				}
			}
		}
		return ret;
	}
	public boolean execL166(Object var0, Object var1, boolean nondeterministic) {
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
L166:
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
			break L166;
		}
		return ret;
	}
	public boolean execL167(Object var0, Object var1, boolean nondeterministic) {
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
L167:
		{
			if (execL169(var0, var1, nondeterministic)) {
				ret = true;
				break L167;
			}
		}
		return ret;
	}
	public boolean execL169(Object var0, Object var1, boolean nondeterministic) {
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
L169:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL166(var0,var1,nondeterministic)) {
						ret = true;
						break L169;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL161(Object var0, boolean nondeterministic) {
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
L161:
		{
			func = f2;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL157(var0,var1,nondeterministic)) {
					ret = true;
					break L161;
				}
			}
		}
		return ret;
	}
	public boolean execL157(Object var0, Object var1, boolean nondeterministic) {
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
L157:
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
			break L157;
		}
		return ret;
	}
	public boolean execL158(Object var0, Object var1, boolean nondeterministic) {
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
L158:
		{
			if (execL160(var0, var1, nondeterministic)) {
				ret = true;
				break L158;
			}
		}
		return ret;
	}
	public boolean execL160(Object var0, Object var1, boolean nondeterministic) {
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
L160:
		{
			if (!(!(f2).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL157(var0,var1,nondeterministic)) {
						ret = true;
						break L160;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL152(Object var0, boolean nondeterministic) {
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
L152:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL148(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L152;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL148(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L148:
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
			break L148;
		}
		return ret;
	}
	public boolean execL149(Object var0, Object var1, boolean nondeterministic) {
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
L149:
		{
			if (execL151(var0, var1, nondeterministic)) {
				ret = true;
				break L149;
			}
		}
		return ret;
	}
	public boolean execL151(Object var0, Object var1, boolean nondeterministic) {
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
L151:
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
						if (execL148(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L151;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL143(Object var0, boolean nondeterministic) {
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
L143:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
					if (execL139(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L143;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL139(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L139:
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
			break L139;
		}
		return ret;
	}
	public boolean execL140(Object var0, Object var1, boolean nondeterministic) {
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
L140:
		{
			if (execL142(var0, var1, nondeterministic)) {
				ret = true;
				break L140;
			}
		}
		return ret;
	}
	public boolean execL142(Object var0, Object var1, boolean nondeterministic) {
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
L142:
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
						if (execL139(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L142;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL134(Object var0, boolean nondeterministic) {
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
L134:
		{
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL130(var0,var1,nondeterministic)) {
					ret = true;
					break L134;
				}
			}
		}
		return ret;
	}
	public boolean execL130(Object var0, Object var1, boolean nondeterministic) {
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
L130:
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
			break L130;
		}
		return ret;
	}
	public boolean execL131(Object var0, Object var1, boolean nondeterministic) {
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
L131:
		{
			if (execL133(var0, var1, nondeterministic)) {
				ret = true;
				break L131;
			}
		}
		return ret;
	}
	public boolean execL133(Object var0, Object var1, boolean nondeterministic) {
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
L133:
		{
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL130(var0,var1,nondeterministic)) {
						ret = true;
						break L133;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL125(Object var0, boolean nondeterministic) {
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
L125:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL121(var0,var1,nondeterministic)) {
					ret = true;
					break L125;
				}
			}
		}
		return ret;
	}
	public boolean execL121(Object var0, Object var1, boolean nondeterministic) {
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
L121:
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
			break L121;
		}
		return ret;
	}
	public boolean execL122(Object var0, Object var1, boolean nondeterministic) {
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
L122:
		{
			if (execL124(var0, var1, nondeterministic)) {
				ret = true;
				break L122;
			}
		}
		return ret;
	}
	public boolean execL124(Object var0, Object var1, boolean nondeterministic) {
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
L124:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL121(var0,var1,nondeterministic)) {
						ret = true;
						break L124;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL116(Object var0, boolean nondeterministic) {
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
L116:
		{
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "perpetualUse", "L112",var0,var1});
				} else if (execL112(var0,var1,nondeterministic)) {
					ret = true;
					break L116;
				}
			}
		}
		return ret;
	}
	public boolean execL112(Object var0, Object var1, boolean nondeterministic) {
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
L112:
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
			break L112;
		}
		return ret;
	}
	public boolean execL113(Object var0, Object var1, boolean nondeterministic) {
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
L113:
		{
			if (execL115(var0, var1, nondeterministic)) {
				ret = true;
				break L113;
			}
		}
		return ret;
	}
	public boolean execL115(Object var0, Object var1, boolean nondeterministic) {
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
L115:
		{
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "perpetualUse", "L112",var0,var1});
					} else if (execL112(var0,var1,nondeterministic)) {
						ret = true;
						break L115;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL107(Object var0, boolean nondeterministic) {
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
L107:
		{
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use", "L103",var0,var1});
				} else if (execL103(var0,var1,nondeterministic)) {
					ret = true;
					break L107;
				}
			}
		}
		return ret;
	}
	public boolean execL103(Object var0, Object var1, boolean nondeterministic) {
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
L103:
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
			break L103;
		}
		return ret;
	}
	public boolean execL104(Object var0, Object var1, boolean nondeterministic) {
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
L104:
		{
			if (execL106(var0, var1, nondeterministic)) {
				ret = true;
				break L104;
			}
		}
		return ret;
	}
	public boolean execL106(Object var0, Object var1, boolean nondeterministic) {
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
L106:
		{
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use", "L103",var0,var1});
					} else if (execL103(var0,var1,nondeterministic)) {
						ret = true;
						break L106;
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

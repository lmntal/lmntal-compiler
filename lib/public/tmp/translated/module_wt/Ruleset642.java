package translated.module_wt;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset642 extends Ruleset {
	private static final Ruleset642 theInstance = new Ruleset642();
	private Ruleset642() {}
	public static Ruleset642 getInstance() {
		return theInstance;
	}
	private int id = 642;
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
	private String encodedRuleset = 
"(wt.newFrame({$p, @r}) :- {wt.createFrame, $p, @r}), (wt.createFrame :- [:/*inline*/    LMNtalFrame frame = new LMNtalFrame((Membrane)mem);    Atom a = mem.newAtom(new Functor(\"frame\",1));    Atom b = mem.newAtom(new ObjectFunctor(frame));    mem.newLink(a,0,b,0);    mem.removeAtom(me);    mem.makePerpetual();  :]), (size(W, H), frame(F) :- class(F, \"LMNtalFrame\"), int(W), int(H) | frame(F), [:/*inline*/    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();    int w = Integer.parseInt(me.nth(1));    int h = Integer.parseInt(me.nth(2));    frame.setSize(w,h);    mem.removeAtom(me.nthAtom(0));    mem.removeAtom(me.nthAtom(1));    mem.removeAtom(me.nthAtom(2));    mem.removeAtom(me);    frame.setVisible(true);   :](F, W, H)), (title(T), frame(F) :- class(F, \"LMNtalFrame\"), string(T) | frame(F), [:/*inline*/    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();    String title = me.nthAtom(1).toString();    frame.setTitle(title);    mem.removeAtom(me.nthAtom(0));    mem.removeAtom(me.nthAtom(1));    mem.removeAtom(me);  :](F, T)), (gridPanel(R, C), frame(F) :- class(F, \"LMNtalFrame\"), int(R), int(C) | frame(F), [:/*inline*/    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();    int rows = Integer.parseInt(me.nth(1));    int cols = Integer.parseInt(me.nth(2));    Panel grid = new Panel(new GridLayout(rows,cols));    frame.getContentPane().add(grid);    PriorityQueue pQueue = new PriorityQueue();    Atom a = mem.newAtom(new Functor(\"grid\",2));    Atom b = mem.newAtom(new ObjectFunctor(grid));    Atom c = mem.newAtom(new ObjectFunctor(pQueue));    mem.newLink(a,0,b,0);    mem.newLink(a,1,c,0);    mem.removeAtom(me.nthAtom(0));    mem.removeAtom(me.nthAtom(1));    mem.removeAtom(me.nthAtom(2));    mem.removeAtom(me);   :](F, R, C)), (borderPanel, frame(F) :- class(F, \"LMNtalFrame\") | frame(F), [:/*inline*/    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();    Panel border = new Panel(new BorderLayout());    frame.getContentPane().add(border);    Atom a = mem.newAtom(new Functor(\"border\",1));    Atom b = mem.newAtom(new ObjectFunctor(border));    mem.newLink(a,0,b,0);    mem.removeAtom(me.nthAtom(0));    mem.removeAtom(me);  :](F)), (addButton(T, L, E), grid(P, Q), frame(F) :- class(P, \"java.awt.Panel\"), class(Q, \"PriorityQueue\"), class(F, \"LMNtalFrame\"), string(T), int(L), string(E) | grid(P, Q), frame(F), [:/*inline*/        final Membrane memb = (Membrane)mem;        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();        Panel grid = (Panel)panelfunc.getObject();        ObjectFunctor queuefunc = (ObjectFunctor)me.nthAtom(1).getFunctor();        PriorityQueue pQueue = (PriorityQueue)queuefunc.getObject();        String title = me.nthAtom(2).toString();        int location = Integer.parseInt(me.nth(3));        final String event = me.nthAtom(4).toString();        Button bt = new Button(title);        bt.setVisible(true);        bt.addActionListener(new ActionListener(){          public void actionPerformed(ActionEvent e) {            memb.asyncLock();            memb.newAtom(new Functor(event,0));            memb.asyncUnlock();          }        });        int index = pQueue.insert(location);        grid.add(bt,index);        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(5).getFunctor();        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();        frame.setVisible(true);        mem.removeAtom(me.nthAtom(0));        mem.removeAtom(me.nthAtom(1));        mem.removeAtom(me.nthAtom(2));        mem.removeAtom(me.nthAtom(3));        mem.removeAtom(me.nthAtom(4));        mem.removeAtom(me.nthAtom(5));        mem.removeAtom(me);       :](P, Q, T, L, E, F)), (addButton(T, L, E), border(B), frame(F) :- class(B, \"java.awt.Panel\"), class(F, \"LMNtalFrame\"), string(T), string(L), string(E) | border(B), frame(F), [:/*inline*/        final Membrane memb = (Membrane)mem;        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();        Panel border = (Panel)panelfunc.getObject();        String title = me.nthAtom(1).toString();        String location = me.nthAtom(2).toString();        final String event = me.nthAtom(3).toString();        Button bt = new Button(title);        bt.setVisible(true);        bt.addActionListener(new ActionListener(){          public void actionPerformed(ActionEvent e){            memb.asyncLock();            memb.newAtom(new Functor(event,0));            memb.asyncUnlock();          }        });        border.add(location,bt);        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(4).getFunctor();        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();        frame.setVisible(true);        mem.removeAtom(me.nthAtom(0));        mem.removeAtom(me.nthAtom(1));        mem.removeAtom(me.nthAtom(2));        mem.removeAtom(me.nthAtom(3));        mem.removeAtom(me.nthAtom(4));        mem.removeAtom(me);       :](B, T, L, E, F)), (terminate, frame(F) :- class(F, \"LMNtalFrame\") | [:/*inline*/    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();    frame.setVisible(false);    frame.dispose();    mem.removeAtom(me.nthAtom(0));    mem.removeAtom(me);  :](F), terminated), ({terminated, $p[], @r} :- $p[])";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL2404(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "newFrame");
			return true;
		}
		if (execL2417(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "createFrame");
			return true;
		}
		if (execL2426(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "size");
			return true;
		}
		if (execL2437(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "title");
			return true;
		}
		if (execL2448(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "gridPanel");
			return true;
		}
		if (execL2459(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "borderPanel");
			return true;
		}
		if (execL2470(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "addButton");
			return true;
		}
		if (execL2483(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "addButton");
			return true;
		}
		if (execL2496(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "terminate");
			return true;
		}
		if (execL2507(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@642", "terminated");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL2411(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "newFrame");
			return true;
		}
		if (execL2420(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "createFrame");
			return true;
		}
		if (execL2431(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "size");
			return true;
		}
		if (execL2442(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "title");
			return true;
		}
		if (execL2453(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "gridPanel");
			return true;
		}
		if (execL2464(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "borderPanel");
			return true;
		}
		if (execL2477(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "addButton");
			return true;
		}
		if (execL2490(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "addButton");
			return true;
		}
		if (execL2501(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "terminate");
			return true;
		}
		if (execL2510(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@642", "terminated");
			return true;
		}
		return result;
	}
	public boolean execL2510(Object var0, boolean nondeterministic) {
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
L2510:
		{
			Iterator it1 = ((AbstractMembrane)var0).memIterator();
			while (it1.hasNext()) {
				mem = (AbstractMembrane) it1.next();
				if ((mem.getKind() != 0))
					continue;
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
								Task.states.add(new Object[] {theInstance, "terminated", "L2506",var0,var1,var2});
							} else if (execL2506(var0,var1,var2,nondeterministic)) {
								ret = true;
								break L2510;
							}
						}
					}
					((AbstractMembrane)var1).unlock();
				}
			}
		}
		return ret;
	}
	public boolean execL2506(Object var0, Object var1, Object var2, boolean nondeterministic) {
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
L2506:
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
			break L2506;
		}
		return ret;
	}
	public boolean execL2507(Object var0, Object var1, boolean nondeterministic) {
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
L2507:
		{
			if (execL2509(var0, var1, nondeterministic)) {
				ret = true;
				break L2507;
			}
		}
		return ret;
	}
	public boolean execL2509(Object var0, Object var1, boolean nondeterministic) {
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
L2509:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
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
										Task.states.add(new Object[] {theInstance, "terminated", "L2506",var0,var2,var1});
									} else if (execL2506(var0,var2,var1,nondeterministic)) {
										ret = true;
										break L2509;
									}
								}
							}
						}
						((AbstractMembrane)var2).unlock();
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2501(Object var0, boolean nondeterministic) {
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
L2501:
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
								Task.states.add(new Object[] {theInstance, "terminate", "L2495",var0,var1,var2,var3,var4});
							} else if (execL2495(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L2501;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2495(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L2495:
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
			break L2495;
		}
		return ret;
	}
	public boolean execL2496(Object var0, Object var1, boolean nondeterministic) {
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
L2496:
		{
			if (execL2498(var0, var1, nondeterministic)) {
				ret = true;
				break L2496;
			}
			if (execL2500(var0, var1, nondeterministic)) {
				ret = true;
				break L2496;
			}
		}
		return ret;
	}
	public boolean execL2500(Object var0, Object var1, boolean nondeterministic) {
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
L2500:
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
									Task.states.add(new Object[] {theInstance, "terminate", "L2495",var0,var3,var1,var4,var5});
								} else if (execL2495(var0,var3,var1,var4,var5,nondeterministic)) {
									ret = true;
									break L2500;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2498(Object var0, Object var1, boolean nondeterministic) {
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
L2498:
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
									Task.states.add(new Object[] {theInstance, "terminate", "L2495",var0,var1,var2,var4,var5});
								} else if (execL2495(var0,var1,var2,var4,var5,nondeterministic)) {
									ret = true;
									break L2498;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2490(Object var0, boolean nondeterministic) {
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
L2490:
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
														Task.states.add(new Object[] {theInstance, "addButton", "L2482",var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var12});
													} else if (execL2482(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var12,nondeterministic)) {
														ret = true;
														break L2490;
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
	public boolean execL2482(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, boolean nondeterministic) {
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
L2482:
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
			break L2482;
		}
		return ret;
	}
	public boolean execL2483(Object var0, Object var1, boolean nondeterministic) {
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
L2483:
		{
			if (execL2485(var0, var1, nondeterministic)) {
				ret = true;
				break L2483;
			}
			if (execL2487(var0, var1, nondeterministic)) {
				ret = true;
				break L2483;
			}
			if (execL2489(var0, var1, nondeterministic)) {
				ret = true;
				break L2483;
			}
		}
		return ret;
	}
	public boolean execL2489(Object var0, Object var1, boolean nondeterministic) {
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
L2489:
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
															Task.states.add(new Object[] {theInstance, "addButton", "L2482",var0,var3,var7,var1,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL2482(var0,var3,var7,var1,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L2489;
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
	public boolean execL2487(Object var0, Object var1, boolean nondeterministic) {
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
L2487:
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
															Task.states.add(new Object[] {theInstance, "addButton", "L2482",var0,var3,var1,var7,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL2482(var0,var3,var1,var7,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L2487;
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
	public boolean execL2485(Object var0, Object var1, boolean nondeterministic) {
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
L2485:
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
															Task.states.add(new Object[] {theInstance, "addButton", "L2482",var0,var1,var5,var7,var9,var10,var12,var13,var15,var16,var17});
														} else if (execL2482(var0,var1,var5,var7,var9,var10,var12,var13,var15,var16,var17,nondeterministic)) {
															ret = true;
															break L2485;
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
	public boolean execL2477(Object var0, boolean nondeterministic) {
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
L2477:
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
																Task.states.add(new Object[] {theInstance, "addButton", "L2469",var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var13,var14,var15});
															} else if (execL2469(var0,var1,var2,var3,var4,var5,var7,var8,var10,var11,var13,var14,var15,nondeterministic)) {
																ret = true;
																break L2477;
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
	public boolean execL2469(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8, Object var9, Object var10, Object var11, Object var12, boolean nondeterministic) {
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
L2469:
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
			break L2469;
		}
		return ret;
	}
	public boolean execL2470(Object var0, Object var1, boolean nondeterministic) {
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
L2470:
		{
			if (execL2472(var0, var1, nondeterministic)) {
				ret = true;
				break L2470;
			}
			if (execL2474(var0, var1, nondeterministic)) {
				ret = true;
				break L2470;
			}
			if (execL2476(var0, var1, nondeterministic)) {
				ret = true;
				break L2470;
			}
		}
		return ret;
	}
	public boolean execL2476(Object var0, Object var1, boolean nondeterministic) {
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
L2476:
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
																	Task.states.add(new Object[] {theInstance, "addButton", "L2469",var0,var3,var7,var1,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL2469(var0,var3,var7,var1,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L2476;
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
	public boolean execL2474(Object var0, Object var1, boolean nondeterministic) {
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
L2474:
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
																	Task.states.add(new Object[] {theInstance, "addButton", "L2469",var0,var4,var1,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL2469(var0,var4,var1,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L2474;
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
	public boolean execL2472(Object var0, Object var1, boolean nondeterministic) {
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
L2472:
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
																	Task.states.add(new Object[] {theInstance, "addButton", "L2469",var0,var1,var5,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21});
																} else if (execL2469(var0,var1,var5,var8,var10,var11,var13,var14,var16,var17,var19,var20,var21,nondeterministic)) {
																	ret = true;
																	break L2472;
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
	public boolean execL2464(Object var0, boolean nondeterministic) {
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
L2464:
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
								Task.states.add(new Object[] {theInstance, "borderPanel", "L2458",var0,var1,var2,var3,var4});
							} else if (execL2458(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L2464;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2458(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
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
L2458:
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
			break L2458;
		}
		return ret;
	}
	public boolean execL2459(Object var0, Object var1, boolean nondeterministic) {
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
L2459:
		{
			if (execL2461(var0, var1, nondeterministic)) {
				ret = true;
				break L2459;
			}
			if (execL2463(var0, var1, nondeterministic)) {
				ret = true;
				break L2459;
			}
		}
		return ret;
	}
	public boolean execL2463(Object var0, Object var1, boolean nondeterministic) {
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
L2463:
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
									Task.states.add(new Object[] {theInstance, "borderPanel", "L2458",var0,var3,var1,var4,var5});
								} else if (execL2458(var0,var3,var1,var4,var5,nondeterministic)) {
									ret = true;
									break L2463;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2461(Object var0, Object var1, boolean nondeterministic) {
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
L2461:
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
									Task.states.add(new Object[] {theInstance, "borderPanel", "L2458",var0,var1,var2,var4,var5});
								} else if (execL2458(var0,var1,var2,var4,var5,nondeterministic)) {
									ret = true;
									break L2461;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2453(Object var0, boolean nondeterministic) {
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
L2453:
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
										Task.states.add(new Object[] {theInstance, "gridPanel", "L2447",var0,var1,var2,var3,var4,var6,var7});
									} else if (execL2447(var0,var1,var2,var3,var4,var6,var7,nondeterministic)) {
										ret = true;
										break L2453;
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
	public boolean execL2447(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L2447:
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
			break L2447;
		}
		return ret;
	}
	public boolean execL2448(Object var0, Object var1, boolean nondeterministic) {
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
L2448:
		{
			if (execL2450(var0, var1, nondeterministic)) {
				ret = true;
				break L2448;
			}
			if (execL2452(var0, var1, nondeterministic)) {
				ret = true;
				break L2448;
			}
		}
		return ret;
	}
	public boolean execL2452(Object var0, Object var1, boolean nondeterministic) {
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
L2452:
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
											Task.states.add(new Object[] {theInstance, "gridPanel", "L2447",var0,var3,var1,var6,var7,var9,var10});
										} else if (execL2447(var0,var3,var1,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L2452;
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
	public boolean execL2450(Object var0, Object var1, boolean nondeterministic) {
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
L2450:
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
											Task.states.add(new Object[] {theInstance, "gridPanel", "L2447",var0,var1,var4,var6,var7,var9,var10});
										} else if (execL2447(var0,var1,var4,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L2450;
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
	public boolean execL2442(Object var0, boolean nondeterministic) {
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
L2442:
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
									Task.states.add(new Object[] {theInstance, "title", "L2436",var0,var1,var2,var3,var4,var6});
								} else if (execL2436(var0,var1,var2,var3,var4,var6,nondeterministic)) {
									ret = true;
									break L2442;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2436(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L2436:
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
			break L2436;
		}
		return ret;
	}
	public boolean execL2437(Object var0, Object var1, boolean nondeterministic) {
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
L2437:
		{
			if (execL2439(var0, var1, nondeterministic)) {
				ret = true;
				break L2437;
			}
			if (execL2441(var0, var1, nondeterministic)) {
				ret = true;
				break L2437;
			}
		}
		return ret;
	}
	public boolean execL2441(Object var0, Object var1, boolean nondeterministic) {
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
L2441:
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
										Task.states.add(new Object[] {theInstance, "title", "L2436",var0,var3,var1,var5,var6,var8});
									} else if (execL2436(var0,var3,var1,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L2441;
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
	public boolean execL2439(Object var0, Object var1, boolean nondeterministic) {
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
L2439:
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
										Task.states.add(new Object[] {theInstance, "title", "L2436",var0,var1,var3,var5,var6,var8});
									} else if (execL2436(var0,var1,var3,var5,var6,var8,nondeterministic)) {
										ret = true;
										break L2439;
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
	public boolean execL2431(Object var0, boolean nondeterministic) {
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
L2431:
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
										Task.states.add(new Object[] {theInstance, "size", "L2425",var0,var1,var2,var3,var4,var6,var7});
									} else if (execL2425(var0,var1,var2,var3,var4,var6,var7,nondeterministic)) {
										ret = true;
										break L2431;
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
	public boolean execL2425(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
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
L2425:
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
			break L2425;
		}
		return ret;
	}
	public boolean execL2426(Object var0, Object var1, boolean nondeterministic) {
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
L2426:
		{
			if (execL2428(var0, var1, nondeterministic)) {
				ret = true;
				break L2426;
			}
			if (execL2430(var0, var1, nondeterministic)) {
				ret = true;
				break L2426;
			}
		}
		return ret;
	}
	public boolean execL2430(Object var0, Object var1, boolean nondeterministic) {
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
L2430:
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
											Task.states.add(new Object[] {theInstance, "size", "L2425",var0,var3,var1,var6,var7,var9,var10});
										} else if (execL2425(var0,var3,var1,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L2430;
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
	public boolean execL2428(Object var0, Object var1, boolean nondeterministic) {
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
L2428:
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
											Task.states.add(new Object[] {theInstance, "size", "L2425",var0,var1,var4,var6,var7,var9,var10});
										} else if (execL2425(var0,var1,var4,var6,var7,var9,var10,nondeterministic)) {
											ret = true;
											break L2428;
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
	public boolean execL2420(Object var0, boolean nondeterministic) {
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
L2420:
		{
			func = f20;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "createFrame", "L2416",var0,var1});
				} else if (execL2416(var0,var1,nondeterministic)) {
					ret = true;
					break L2420;
				}
			}
		}
		return ret;
	}
	public boolean execL2416(Object var0, Object var1, boolean nondeterministic) {
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
L2416:
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
			break L2416;
		}
		return ret;
	}
	public boolean execL2417(Object var0, Object var1, boolean nondeterministic) {
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
L2417:
		{
			if (execL2419(var0, var1, nondeterministic)) {
				ret = true;
				break L2417;
			}
		}
		return ret;
	}
	public boolean execL2419(Object var0, Object var1, boolean nondeterministic) {
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
L2419:
		{
			if (!(!(f20).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "createFrame", "L2416",var0,var1});
					} else if (execL2416(var0,var1,nondeterministic)) {
						ret = true;
						break L2419;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL2411(Object var0, boolean nondeterministic) {
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
L2411:
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
												Task.states.add(new Object[] {theInstance, "newFrame", "L2403",var0,var4,var1,var2,var5,var3});
											} else if (execL2403(var0,var4,var1,var2,var5,var3,nondeterministic)) {
												ret = true;
												break L2411;
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
	public boolean execL2403(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
L2403:
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
			break L2403;
		}
		return ret;
	}
	public boolean execL2404(Object var0, Object var1, boolean nondeterministic) {
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
L2404:
		{
			if (execL2406(var0, var1, nondeterministic)) {
				ret = true;
				break L2404;
			}
			if (execL2409(var0, var1, nondeterministic)) {
				ret = true;
				break L2404;
			}
		}
		return ret;
	}
	public boolean execL2409(Object var0, Object var1, boolean nondeterministic) {
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
L2409:
		{
			if (!(!(f23).equals(((Atom)var1).getFunctor()))) {
				if(((Atom)var1).getMem().getKind() == 0) {
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
															Task.states.add(new Object[] {theInstance, "newFrame", "L2403",var0,var2,var11,var8,var1,var5});
														} else if (execL2403(var0,var2,var11,var8,var1,var5,nondeterministic)) {
															ret = true;
															break L2409;
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
		}
		return ret;
	}
	public boolean execL2406(Object var0, Object var1, boolean nondeterministic) {
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
L2406:
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
													Task.states.add(new Object[] {theInstance, "newFrame", "L2403",var0,var7,var1,var3,var10,var6});
												} else if (execL2403(var0,var7,var1,var3,var10,var6,nondeterministic)) {
													ret = true;
													break L2406;
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

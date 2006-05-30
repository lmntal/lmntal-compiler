package translated.module_graphic;
import runtime.*;
import java.util.*;
/*inline_define*/
	import java.awt.*;
	import java.awt.event.*;
	import java.awt.MouseInfo.*;
public class SomeInlineCodegraphic {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodegraphicCustomGuardImpl";

			CustomGuard cg=(CustomGuard)Class.forName(name).newInstance();

			if(cg==null) throw new GuardNotFoundException();

			return cg.run(guardID, mem, obj);

		} catch(GuardNotFoundException e) {
			throw new GuardNotFoundException();

		} catch(ClassNotFoundException e) {
		} catch(InstantiationException e) {
		} catch(IllegalAccessException e) {
		} catch(Exception e) {

			e.printStackTrace();

		}

		throw new GuardNotFoundException();

	}
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 2: {
			/*inline*/
	if(null != Env.LMNgraphic){
		Env.LMNgraphic.setRepaint((Membrane)mem, true);
		Atom atom = mem.newAtom(new Functor("repaint_go", 0));
		me.remove();
	}

			break; }
		case 1: {
			/*inline*/
	if(null != Env.LMNgraphic){
		Env.LMNgraphic.setRepaint((Membrane)mem, false);
		Atom atom = mem.newAtom(new Functor("norepaint_go", 0));
		me.remove();
	}	

			break; }
		case 7: {
			/*inline*/
	int y = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();
	Point p = null;
	if(Env.LMNgraphic != null)
		p= Env.LMNgraphic.getMousePoint((Membrane)mem);
	if(p != null)
		y = (int)(p.y);
	Atom atom = mem.newAtom(new IntegerFunctor(y));
	mem.relink(atom, 0, me, 1);
	me.nthAtom(0).remove();
	me.remove();
	
			break; }
		case 6: {
			/*inline*/

	int x = ((IntegerFunctor)(me.nthAtom(0).getFunctor())).intValue();
	Point p = null;
	if(Env.LMNgraphic != null)
		p= Env.LMNgraphic.getMousePoint((Membrane)mem);
	if(p != null)
		x = (int)(p.x);
	Atom atom = mem.newAtom(new IntegerFunctor(x));
	mem.relink(atom, 0, me, 1);
	me.nthAtom(0).remove();
	me.remove();
	
	
	
			break; }
		case 8: {
			/*inline*/
	int x = -1;
	Point p = null;
	if(Env.LMNgraphic != null)
		p= Env.LMNgraphic.getMousePoint((Membrane)mem);
	if(p != null)
		x = (int)(p.x);
	Atom atom = mem.newAtom(new IntegerFunctor(x));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
	
	
			break; }
		case 9: {
			/*inline*/
	int y = -1;
	Point p = null;
	if(Env.LMNgraphic != null)
		p= Env.LMNgraphic.getMousePoint((Membrane)mem);
	if(p != null)
		y = (int)(p.y);
	Atom atom = mem.newAtom(new IntegerFunctor(y));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
	
			break; }
		case 0: {
			/*inline*/
	Env.fGraphic = true;
	Env.initGraphic();
	Atom atom = mem.newAtom(new Functor("go", 0));
	me.remove();

			break; }
		case 5: {
			/*inline*/
	PointerInfo pointerInfo = MouseInfo.getPointerInfo();
	int point = (int)(pointerInfo.getLocation().y);
	Atom atom = mem.newAtom(new IntegerFunctor(point));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
  
			break; }
		case 4: {
			/*inline*/
	PointerInfo pointerInfo = MouseInfo.getPointerInfo();
	int point = (int)(pointerInfo.getLocation().x);
	Atom atom = mem.newAtom(new IntegerFunctor(point));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
  
			break; }
		case 11: {
			/*inline*/
	int height = 0;
	if(Env.LMNgraphic != null){
		Dimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);
		if(null != d){ height = d.height; }
	}
	Atom atom = mem.newAtom(new IntegerFunctor(height));
	mem.relink(atom,0,me,0);
	me.remove();
  
			break; }
		case 10: {
			/*inline*/
	int width = 0;
	if(Env.LMNgraphic != null){
		Dimension d = (Dimension)Env.LMNgraphic.getWindowSize((Membrane)mem);
		if(null != d){ width = d.width; }
	}
	Atom atom = mem.newAtom(new IntegerFunctor(width));
	mem.relink(atom,0,me,0);
	me.remove();
  
			break; }
		case 3: {
			/*inline*/
	Env.fGraphic = true;
	Env.initGraphic();
	mem.makePerpetual();
	Atom atom = mem.newAtom(new Functor("go", 0));
	me.remove();

			break; }
		}
	}
}

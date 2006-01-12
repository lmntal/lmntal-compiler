package translated.module_graphic;
import runtime.*;
import java.util.*;
/*inline_define*/
	import java.awt.*;
	import java.awt.event.*;
	import java.awt.MouseInfo.*;

public class SomeInlineCodegraphic {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
	Env.fGraphic = true;
	Env.initGraphic();
	mem.makePerpetual();
	me.setFunctor("go",0);

			break; }
		case 5: {
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
		case 4: {
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
		case 6: {
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
		case 7: {
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
	me.setFunctor("go",0);

			break; }
		case 3: {
			/*inline*/
	PointerInfo pointerInfo = MouseInfo.getPointerInfo();
	int point = (int)(pointerInfo.getLocation().y);
	Atom atom = mem.newAtom(new IntegerFunctor(point));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
  
			break; }
		case 2: {
			/*inline*/
	PointerInfo pointerInfo = MouseInfo.getPointerInfo();
	int point = (int)(pointerInfo.getLocation().x);
	Atom atom = mem.newAtom(new IntegerFunctor(point));
	mem.relink(atom,0,me,0);
	mem.removeAtom(me);
  
			break; }
		}
	}
}

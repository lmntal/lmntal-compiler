package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

public class LineObj extends GraphicObj{
	final static
	private Functor POSITION_ATOM = new Functor("position",4); 
	
	private Point position2;
	
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LineObj(Membrane mem){
		super(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g){
		g.setColor(color);
		g.drawLine((int)position.getX(),(int)position.getY(), (int)position2.getX(),(int)position2.getY());		
	}
	
	public void setMembrane(Membrane mem){
		Iterator atomIte;
		Atom targetAtom;
		
		// membrane ID
		memID = mem.getGlobalMemID();

		// position atom
		atomIte= mem.atomIteratorOfFunctor(POSITION_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				position = new Point(Integer.parseInt(targetAtom.nth(0)),
						 Integer.parseInt(targetAtom.nth(1)));
				position2 = new Point(Integer.parseInt(targetAtom.nth(2)),
						 Integer.parseInt(targetAtom.nth(3)));
			}
			catch(NumberFormatException e){}
		}
		
		// color
		atomIte= mem.atomIteratorOfFunctor(COLOR_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				color = ((null != targetAtom) ?
						new Color(Integer.parseInt(targetAtom.nth(0)),
								  Integer.parseInt(targetAtom.nth(1)),
								  Integer.parseInt(targetAtom.nth(2)))
						: Color.WHITE);
			}
			catch(NumberFormatException e){}
		}
	}
}
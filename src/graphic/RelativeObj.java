package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

public class RelativeObj extends GraphicObj{

	final static
	private Functor SIZE_ATOM = new Functor("size", 2);
	
	private int sizeX;
	private int sizeY;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public RelativeObj(Membrane mem){
		super(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g, Point delta){}
	
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
				if(null != targetAtom){
				position = new Point(Integer.parseInt(targetAtom.nth(0)),
									 Integer.parseInt(targetAtom.nth(1)));
				}
			}
			catch(NumberFormatException e){}
		}
		
		// size atom
		atomIte= mem.atomIteratorOfFunctor(SIZE_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				sizeX = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : 0);
				sizeY = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : 0);
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
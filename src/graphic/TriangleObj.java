package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

public class TriangleObj extends GraphicObj{

	final static
	private Functor POSITION_ATOM = new Functor("position", 6);
	
	private int[] posX;
	private int[] posY;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public TriangleObj(Membrane mem){
		super(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g){
		g.setColor(color);
		g.drawPolygon(posX, posY, 3);	
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
				posX = new int[3];
				posY = new int[3];
				if(null != targetAtom){
					posX[0] = Integer.parseInt(targetAtom.nth(0));
					posX[1] = Integer.parseInt(targetAtom.nth(2));
					posX[2] = Integer.parseInt(targetAtom.nth(4));
					posY[0] = Integer.parseInt(targetAtom.nth(1));
					posY[1] = Integer.parseInt(targetAtom.nth(3));
					posY[2] = Integer.parseInt(targetAtom.nth(5));
				}
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
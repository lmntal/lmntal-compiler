package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

import runtime.Membrane;
import runtime.Atom;
import runtime.Functor;
import runtime.SymbolFunctor;

public class FillTriangleObj extends GraphicObj{

	final static
	private Functor POSITION_ATOM = new SymbolFunctor("position", 6);
	
	private int[] posX;
	private int[] posY;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public FillTriangleObj(Membrane mem){
		super(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g, Point delta){
		g.setColor(color);
		int[] x = new int[3];
		int[] y = new int[3];
		x[0] = posX[0] + delta.x;
		x[1] = posX[1] + delta.x;
		x[2] = posX[2] + delta.x;
		y[0] = posY[0] + delta.y;
		y[1] = posY[1] + delta.y;
		y[2] = posY[2] + delta.y;
		g.fillPolygon(x, y, 3);	
	}
	
	/**
	 * 描画する座標をを返す
	 * @return　Point
	 */
	public Point getPosition(){ return (new Point(posX[0], posY[0]));}
	
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
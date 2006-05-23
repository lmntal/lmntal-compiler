package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

import runtime.AbstractMembrane;
import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

public class StringObj extends GraphicObj{

	final static
	private Functor GETPIC_ATOM = new Functor("getpic", 1);

	final static
	private Functor STRING_ATOM = new Functor("string", 3);
	
	final static
	private int DEFAULT_SIZE = 15;
	
	private int size;
	private String msg;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public StringObj(AbstractMembrane mem){
		super(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g, Point delta){
		if(null == msg){ return; }
		g.setColor(color);
		g.setFont(new Font("myFont",Font.PLAIN,size));
		g.drawString(msg, position.x + delta.x, position.y + delta.y);
	}
	
	public void setMembrane(AbstractMembrane mem){
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
		
		// getpic
		atomIte = mem.atomIteratorOfFunctor(STRING_ATOM);
		if(atomIte.hasNext()){
			Iterator atomGetpicIte = mem.atomIteratorOfFunctor(GETPIC_ATOM);
			if(atomGetpicIte.hasNext()){
				targetAtom = (Atom)atomIte.next();
				if(targetAtom.nthAtom(2) == (Atom)atomGetpicIte.next()){
					msg = (targetAtom).nth(0);
					try{ size = Integer.parseInt(targetAtom.nth(1)); }
					catch(NumberFormatException e){ size = DEFAULT_SIZE; };
				}
			
			}
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
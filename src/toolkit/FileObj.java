package toolkit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

public class FileObj extends GraphicObj{

	final static
	private Functor SIZE_ATOM = new SymbolFunctor("size", 2);
	
	private int sizeX;
	private int sizeY;
	private Image img = null;
	private File file;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public FileObj(Membrane mem, String pass){
		super(mem);
		file = new File(pass);
		setImg();
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void drawAtom(Graphics g, Point delta){
		while(!g.drawImage(img, position.x + delta.x, position.y + delta.y, sizeX, sizeY, null)){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setImg(){
		if(file.exists()){
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			//Imageオブジェクトの生成
			img = toolkit.getImage(file.getPath());
		}
	}
	
	public void setMembrane(Membrane mem){
		Iterator atomIte;
		Atom targetAtom;
		
		
		// membrane ID
		memID = mem.getMemID();

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
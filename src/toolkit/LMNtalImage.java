package toolkit;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

/**
 * LMNtalWindowに設置するラベル
 * オブジェクト生成時に，膜を検索し，ラベル生成を行う．
 */
public class LMNtalImage extends LMNComponent {

	final static
	private Functor FILE_FUNCTOR = new SymbolFunctor("file", 1);	
	
	final static
	private Functor SCALE_FUNCTOR = new SymbolFunctor("scale", 2);	

	private String filename;
	private double scalex;
	private double scaley;
	private int clickedCounter = 0;
		
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalImage(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
		System.out.println("Image");
	}
	/////////////////////////////////////////////////////////////////

	public Component initComponent(){
		Panel panel = new Panel(filename);
		return panel;
	}
	
	class Panel extends JPanel{
		BufferedImage image;
		
		public Panel(String filename){
			try{
				File file = new File(filename);
//				String path = file.getAbsolutePath();
				image = ImageIO.read(file);
				addMouseListener(new MyMouse());
			}catch(Exception e){
				System.out.println("there is no file name.");
			}
		}
	
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			AffineTransform a = AffineTransform.getScaleInstance(scalex,scaley);
			g2.setTransform(a);
			g2.drawImage(image,0,0,this);
		}	
	}

	public void setMembrane(Membrane mem){
		filename = getFileName(mem);
		setScale(mem);
	}
	
	// file("")の中身を取得
	public static String getFileName(Membrane mem){
		String fn = null;
		Iterator fileAtomIte= mem.atomIteratorOfFunctor(FILE_FUNCTOR);
		if(fileAtomIte.hasNext()){
			Atom targetAtom = (Atom)fileAtomIte.next();
			fn = ((null != targetAtom) ? targetAtom.nth(0) : "");			
		}
		return fn;
	}
	
	// scale(x,y)を取得
	public void setScale(Membrane mem){
		double x = 1.0;
		double y = 1.0;
		Iterator scaleAtomIte = mem.atomIteratorOfFunctor(SCALE_FUNCTOR);
		if(scaleAtomIte.hasNext()){
			Atom atom = (Atom)scaleAtomIte.next();
			x = Double.parseDouble(atom.nth(0));
			y = Double.parseDouble(atom.nth(1));
		}
		scalex = x;
		scaley = y;			
	}
	
	class MyMouse extends MouseAdapter{
		public void mouseClicked(MouseEvent me){
			clickedCounter++;
			clickedImage();
			System.out.println("clicked counter : " + clickedCounter);
		}			
	}
	
	public void clickedImage(){
		LMNtalTFrame.addUpdateComponent(this);
	}
	
	public void addAtom(){
		for(;clickedCounter > 0; clickedCounter--){
			Membrane mem = getMymem();
			Functor func = new SymbolFunctor("clicked", 0);
			mem.addAtom(new Atom(mem, func));
			System.out.println("mem is " + mem);
			}
		clickedCounter = 0;
	}
}

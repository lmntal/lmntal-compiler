package gui2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import runtime.Atom;

public class GraphAtom {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public double ATOM_DEF_SIZE = 50;

	/////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	
	final static
	private Random rand = new Random();
	
	/////////////////////////////////////////////////////////////////

	private int posX;
	private int posY;
	private double dx;
	private double dy;
	private String name;
	
	final public Atom me;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public GraphAtom(Atom atom){
		posX = rand.nextInt(500);
		posY = rand.nextInt(500);
		me = atom;
		name = atom.getName();
	}
	/////////////////////////////////////////////////////////////////

	public void paint(Graphics g){
		// [0, 7] -> [128, 255] for eash R G B
		int ir = 0x7F - ((name.hashCode() & 0xF00) >> 8) * 0x08 + 0x7F;
		int ig = 0x7F - ((name.hashCode() & 0x0F0) >> 4) * 0x08 + 0x7F;
		int ib = 0x7F - ((name.hashCode() & 0x00F) >> 0) * 0x08 + 0x7F;
		
		g.setColor(new Color(ir, ig, ib));
		g.drawString(name, posX, posY);
		g.fillOval(posX,
				posY,
				(int)(ATOM_DEF_SIZE * GraphPanel.getMagnification()),
				(int)(ATOM_DEF_SIZE * GraphPanel.getMagnification()));
	}
	
	public void moveCalc(){
		posX += (int)dx;
		posY += (int)dy;
		dx = dy = 0.0;
	}
	
	public int getPosX(){ return posX; }
	
	public int getPosY(){ return posY; }
	
	public void moveDelta(double x, double y){
		dx += x;
		dy += y;
	}
	
}

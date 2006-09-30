package gui2;

import java.awt.Color;
import java.awt.Graphics;

import runtime.Atom;

public class GraphAtom {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public double ATOM_DEF_SIZE = 50;

	/////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	
	private int posX;
	private int posY;
	private double dx;
	private double dy;
	private String name;
	private boolean isHold = false;
	private boolean isClipped = false;
	
	final public Atom me;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public GraphAtom(Atom atom){
		posX = (int)(Math.random() * 500.0);
		posY = (int)(Math.random() * 500.0);
		if(atom != null){
			me = atom;
			name = atom.getName();
		} else {
			me = null;
			name = ".";
		}
	}
	/////////////////////////////////////////////////////////////////
	
	static
	public int getAtomSize(){ return (int)(ATOM_DEF_SIZE * GraphPanel.getMagnification()); }

	public boolean isDummy(){
		return (me == null) ? true : false;
	}
	
	public void paint(Graphics g){
		// [0, 7] -> [128, 255] for eash R G B
		int ir = 0x7F - ((name.hashCode() & 0xF00) >> 8) * 0x08 + 0x7F;
		int ig = 0x7F - ((name.hashCode() & 0x0F0) >> 4) * 0x08 + 0x7F;
		int ib = 0x7F - ((name.hashCode() & 0x00F) >> 0) * 0x08 + 0x7F;
		
		g.setColor(new Color(ir, ig, ib));
		g.drawString(name, posX, posY);
		g.fillOval(posX,
				posY,
				getAtomSize(),
				getAtomSize());
	}
	
	/** 実際にアトムを移動させる */
	public void moveCalc(){
		if(!isClipped && !isHold){
			posX += (int)dx;
			posY += (int)dy;
		}
		dx = dy = 0.0;
	}
	
	/** アトムのX座標を取得する */
	public int getPosX(){ return posX; }
	
	/** アトムのY座標を取得する */
	public int getPosY(){ return posY; }
	
	/** 移動距離を設定する
	 * <p>
	 * このメソッドで設定された移動距離の累積がmoveCalc()での
	 * 移動距離となる。 
	 */
	public void moveDelta(double x, double y){
		dx += x;
		dy += y;
	}
	
	/** 実際にアトムを移動させる */
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	/** アトムを固定する（ドラッグ用） */
	public void setHold(boolean hold){ isHold = hold; }
	
	/** アトムを固定する（ダブルクリック用） */
	public void flipClip(){ isClipped = !isClipped; }
	
}

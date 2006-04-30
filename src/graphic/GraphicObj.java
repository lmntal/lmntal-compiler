package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import runtime.Functor;
import runtime.Membrane;

public abstract class GraphicObj{
	final static
	protected Functor POSITION_ATOM = new Functor("position",2); 

	final static
	protected Functor COLOR_ATOM = new Functor("color",3); 
	
	protected Color color = Color.BLACK;
	protected Point position = new Point(0,0);
	protected String memID = null;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public GraphicObj(Membrane mem){
		memID = mem.getGlobalMemID();
		setMembrane(mem);
	}
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 膜のIDを返す
	 */
	public String getID(){ return memID; }
	
	/**
	 * 描画に使う色をセットする
	 * @param color
	 */
	public void setColor(Color color){ this.color = color; }
	
	/**
	 * 描画する原点をセットする
	 * @param position
	 */
	public void setPosition(Point position){ this.position = position;}
	
	/**
	 * 描画する座標をを返す
	 * @return　Point
	 */
	public Point getPosition(){ return position;}
	
	/**
	 * オブジェクトを描画する．
	 * @param g
	 * @param delta - 原点をdeltaとして描画を行う
	 */
	abstract public void drawAtom(Graphics g, Point delta);
	
	/**
	 * 膜から必要な情報を取得する．
	 * @param mem
	 */
	abstract public void setMembrane(Membrane mem);
}
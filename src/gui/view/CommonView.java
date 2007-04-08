package gui.view;

import gui.model.Node;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * 膜や、アトム以外の共通View
 * @author Nakano
 *
 */
public class CommonView {
	
	final static
	public int FIRE_SIZE = 2;
	
	///////////////////////////////////////////////////////////////////////////

	static
	public int FIRE_ID_MAX = 6;

	/** 炎のイメージ */
	static
	private Image[] fire_ = new Image[7];
	
	///////////////////////////////////////////////////////////////////////////
	public CommonView(){ }
	///////////////////////////////////////////////////////////////////////////
	
	static
	public void initView(Class resourceClass){
		fire_[0] = new ImageIcon(resourceClass.getResource("fire1.png")).getImage();
		fire_[1] = new ImageIcon(resourceClass.getResource("fire2.png")).getImage();
		fire_[2] = new ImageIcon(resourceClass.getResource("fire3.png")).getImage();
		fire_[3] = new ImageIcon(resourceClass.getResource("fire4.png")).getImage();
		fire_[4] = new ImageIcon(resourceClass.getResource("fire5.png")).getImage();
		fire_[5] = new ImageIcon(resourceClass.getResource("fire6.png")).getImage();
		fire_[6] = new ImageIcon(resourceClass.getResource("fire7.png")).getImage();
		
		AtomView.initView(resourceClass);
	}
	
	/**
	 * 炎を描画する
	 * @param g
	 * @param fireID
	 * @param x
	 * @param y
	 * @param imageObserver
	 * @return
	 */
	static
	public void drawFire(Graphics g,
			int fireID,
			int x,
			int y,
			ImageObserver imageObserver)
	{
		g.drawImage(fire_[fireID],
				x - Commons.FIRE_WIDTH_MARGIN,
				y - Commons.FIRE_HEIGHT_MARGIN,
				imageObserver);
	}
}

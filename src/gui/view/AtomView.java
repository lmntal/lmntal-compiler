package gui.view;

import gui.GraphPanel;
import gui.model.Node;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.util.Iterator;

import javax.swing.ImageIcon;

import runtime.Atom;
import runtime.Membrane;

/**
 * アトムを描画する<br/>
 * すべてのアトムはこのクラスにて描画される
 * @author Nakano
 *
 */
public class AtomView {

	final static
	private float ATOM_COMPOSITE = 0.5f;

	///////////////////////////////////////////////////////////////////////////

	static
	private Image ball_;
	
	///////////////////////////////////////////////////////////////////////////
	public AtomView(){ }
	///////////////////////////////////////////////////////////////////////////

	static
	public void initView(Class resourceClass){
		ball_ = new ImageIcon(resourceClass.getResource("ball.png")).getImage();
	}
	
	static
	public void draw(Graphics g,
			Node node,
			boolean richMode,
			ImageObserver imageObserver)
	{
		RoundRectangle2D rect = node.getRoundRectangle2D();
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		
		if(!richMode || !(node.getObject() instanceof Atom)){
			g.setColor(node.getColor());
			((Graphics2D)g).fill(rect);
		}
		g.setColor(node.getColor());
		((Graphics2D)g).fill(rect);

		// ベジエ曲線の制御点なら終了
		if(null == node.getObject()){ return; }

		if(node.isSelected()){
			Iterator<Node> nodes = node.getBeziers();
			while(nodes.hasNext()){
				Node bezierNode = nodes.next();
				draw(g, bezierNode, richMode, imageObserver);
			}

			Stroke oldStroke = ((Graphics2D)g).getStroke();
			((Graphics2D)g).setStroke(Commons.SELECTED_STROKE);
			if(richMode && node.getObject() instanceof Atom){
				g.setColor(node.getColor());
				((Graphics2D)g).setStroke(Commons.SELECTED_STROKE);
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						ATOM_COMPOSITE));
				g.drawImage(ball_, x, y, imageObserver);
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						1.0f));
			}
			g.setColor(Color.RED);
			((Graphics2D)g).draw(rect);
			((Graphics2D)g).setStroke(oldStroke);
		} else {
			if(richMode && node.getObject() instanceof Atom){
				g.setColor(node.getColor());
				Stroke oldStroke = ((Graphics2D)g).getStroke();
				((Graphics2D)g).setStroke(Commons.SELECTED_STROKE);
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						ATOM_COMPOSITE));
				g.drawImage(ball_, x, y, imageObserver);
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						1.0f));
				((Graphics2D)g).setStroke(oldStroke);
				g.setColor(Color.BLACK);
			}  else {
				g.setColor(Color.BLACK);
				((Graphics2D)g).draw(rect);
			}
		}
		///////////////////////////////////////////////////////////////
		// アトム名描画
		g.setFont(Commons.FONT);
		if(Node.getShowFullName()){
			g.drawString(node.getName(), x, y);
		} else if(0 < node.getName().length()){
			g.drawString(node.getName().substring(0, 1),
					(int)(x + (rect.getWidth() / 2) - ((g.getFontMetrics(g.getFont()).getWidths()[0]) / 2)),
					(int)(y + (rect.getHeight() / 2) + ((g.getFontMetrics(g.getFont()).getHeight()) / 4)));
		}
		// 炎の描画
		if(node.isHeating()){
			CommonView.drawFire(g, node.getNextFireID(), x, y, imageObserver);
		}
		///////////////////////////////////////////////////////////////
		

		if(node.isClipped()){
			paintPin(g, 0, y, rect, node, imageObserver);
		}
	}
	
	/**
	 * ピンの描画およびアニメーション用の計算を行う
	 * @param g
	 * @param deltaX
	 * @param deltaY
	 */
	static
	public void paintPin(Graphics g,
			int deltaX,
			int deltaY,
			RoundRectangle2D rect,
			Node node,
			ImageObserver imageObserver){
		int pinAnime = node.getPinAnimeCounter();
		double pinPosY = node.getPinPosY();
		
		if((pinAnime != 0) && (pinPosY < rect.getCenterY())){
			pinPosY += Math.abs(200 / pinAnime); 
		}
		
		if(pinPosY > rect.getCenterY()){
			node.setPinAnimeCounter(0); 
		}
		if(pinAnime == 0){
//			node.getPinPosY()pinPosY = rect.getY() - (GraphPanel.getPin().getHeight(imageObserver) / 2); 
		}

		g.drawImage(GraphPanel.getPin(),
				(int)(rect.getCenterX()),
				(int)(deltaY),
				imageObserver
				);
		
	}
}

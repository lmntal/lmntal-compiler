package gui.view;

import gui.model.Node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.util.Iterator;


public class MembraneView {
	///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////
	public MembraneView(){ }
	///////////////////////////////////////////////////////////////////////////
	
	static
	public void draw(Graphics g,
			Node node,
			boolean richMode,
			ImageObserver imageObserver)
	{
		RoundRectangle2D rect = node.getRoundRectangle2D();
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		
		synchronized (node.getChildMap()) {
			Iterator<Node> childNodes = node.getChilds();
			while(childNodes.hasNext()){
				Node childNode = childNodes.next();
				if(childNode.isAtom()){
					AtomView.draw(g, childNode, richMode, imageObserver);
				} else {
					draw(g, childNode, richMode, imageObserver);
				}
			}
		}
		if(!node.isRoot()){
			g.setColor(node.getColor());
			if(node.isSelected()){
				g.setColor(Color.RED);
				Stroke oldStroke = ((Graphics2D)g).getStroke();
				((Graphics2D)g).setStroke(Commons.SELECTED_STROKE);
				((Graphics2D)g).draw(rect);
				((Graphics2D)g).setStroke(oldStroke);
			} else {
				((Graphics2D)g).draw(rect);
			}
			g.setColor(Color.BLACK);
			g.setFont(Commons.FONT);
			g.drawString(node.getName(), x, y);
		}
	}
}

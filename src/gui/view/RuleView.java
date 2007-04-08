package gui.view;

import gui.model.Node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

public class RuleView {

	///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////
	public RuleView(){ }
	///////////////////////////////////////////////////////////////////////////
	
	static
	public void drawRule(Graphics g,
			Node node,
			boolean richMode,
			ImageObserver imageObserver)
	{
		Rectangle2D rect = node.getBounds2D();
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		
		double width = g.getFontMetrics(Commons.FONT).stringWidth(node.getName());
		g.setColor(node.getColor());
		g.fillRect(x, y, (int)width + 10, (int)rect.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(x, y, (int)width + 10, (int)rect.getHeight());
		g.setFont(Commons.FONT);
		g.drawString(node.getName(), x + 5, y + g.getFontMetrics(Commons.FONT).getHeight());
	}
}

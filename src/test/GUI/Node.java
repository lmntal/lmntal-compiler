package test.GUI;

import java.awt.*;
public interface Node {
	public void initNode();
	public String getName();
	public Point getPosition();
	public void setPosition(Point p);
	public int getEdgeCount();
	public Node getNthNode(int index);
	
	public void setMoveDelta(double dx, double dy);
	public void move(Rectangle area);
	public void paintNode(Graphics g);
	public void paintEdge(Graphics g);
}

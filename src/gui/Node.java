package gui;

import java.awt.*;
public interface Node {
	public void initNode();
	public String getName();
	public DoublePoint getPosition();
	public void setPosition(DoublePoint p);
	public int getEdgeCount();
	public Node getNthNode(int index);
	public boolean isVisible();
	public void setSelected(boolean b);//2006.5.8 by inui
	public void remove();//2006.4.29 by inui
	public void setName(String name);//2006.4.29 by inui
	
	public void setMoveDelta(double dx, double dy);
	public void initMoveDelta();
	public DoublePoint getMoveDelta();
	public void move(Rectangle area);
	public void paintNode(Graphics g);
	public void paintEdge(Graphics g);
}

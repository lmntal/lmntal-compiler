/**
 * グラフのノードを表す
 * 現在位置などを保持
 */

package test.GUI;

import java.util.*;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;

public class GraphNode implements Node {
	
	List linkedNodes = new ArrayList();
	
	public void initNode() {
	}
	public String getName() {
		return label;
	}
	public Node getNthNode(int index) {
		return (Node)linkedNodes.get(index);
	}
	public int getEdgeCount() {
		return linkedNodes.size();
	}
	
	/**
	 * 現在位置
	 */
	protected Point pos = null;
	
	/**
	 * ノードのラベル
	 */
	protected String label = null;
	
	/**
	 * 移動方向
	 */
	protected int dx = 0;
	protected int dy = 0;
	
	protected Dimension size = new Dimension(16,16);
	
	public double length_between(GraphNode n) {
		double dx,dy;
		dx=n.getPosition().x - pos.x;
		dy=n.getPosition().y - pos.y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public void addLinkedNode(GraphNode e) {
		if(this==e) return;
		Edge ie = new Edge(this, e);
		linkedNodes.add(ie);
//		System.out.println("add "+e);
	}
	
	/**
	 * 現在位置、ラベル、移動範囲を指定して初期化します
	 * @param label 表示ラベル
	 * @param pos 初期位置
	 * @param area 移動可能範囲
	 */
	public GraphNode(String label, Point pos) {
		this.label = label;
		this.pos = pos;
	}
	
	public void setMoveDelta(double dx, double dy) {
		this.dx += dx;
		this.dy += dy;
	}
	
	public boolean isFixed() {
		return false;
	}
	
	public Point getPosition() {
		return this.pos;
	}
	public void setPosition(Point p) {
		this.pos = p;
	}
	
	public void move(Rectangle area) {
		if (isFixed()) return;
		this.pos.x += Math.max(-5, Math.min(5, this.dx));
		this.pos.y += Math.max(-5, Math.min(5, this.dy));
		
		if (this.pos.x < area.getMinX()) {
			this.pos.x = (int)area.getMinX();
		} else if (this.pos.x > area.getMaxX()) {
			this.pos.x = (int)area.getMaxX();
		}
		
		if (this.pos.y < area.getMinY()) {
			this.pos.y = (int)area.getMinY();
		} else if (this.pos.y > area.getMaxY()) {
			this.pos.y = (int)area.getMaxY();
		}
//		dx=dy=0;
		this.dx /= 2;
		this.dy /= 2;
	}
	
	public void paintEdge(Graphics g) {
		Iterator it = linkedNodes.iterator();
		while(it.hasNext()) {
			Edge ie = (Edge)it.next();
			if(ie.from.hashCode() < ie.to.hashCode()) continue;
			g.drawLine((int)ie.from.getPosition().getX(), (int)ie.from.getPosition().getY(),
				(int)ie.to.getPosition().getX(), (int)ie.to.getPosition().getY());
		}
	}
	
	Color colors[] = {
		Color.BLACK,
		Color.BLUE,
		Color.CYAN,
		Color.GREEN,
		Color.MAGENTA,
		Color.ORANGE,
		Color.RED
	};
	
	public void paintNode(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(label);
		int h = fm.getHeight();
		
//		g.setColor(new Color(64,128,255));
		// 適当に色分けする！
		g.setColor(colors[ Math.abs(label.hashCode()) % colors.length ]);
		
		g.fillOval(pos.x - size.width/2, pos.y - size.height/ 2, size.width, size.height);
		
		g.setColor(Color.BLACK);
		g.drawOval(pos.x - size.width/2, pos.y - size.height/ 2, size.width, size.height);
		g.drawString(this.label, pos.x - (w-10)/2, (pos.y - (h-4)/2) + fm.getAscent()+size.height);
	}
	
	public String toString() {
		return "Node "+label;
	}
}


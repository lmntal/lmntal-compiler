/**
 * ノードのレイアウト
 */

package test.graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class GraphLayout implements Runnable {

	private Vector nodes = new Vector();
	private Vector edges = new Vector();
	private Thread th = null;
	private static final int DELAY = 100;
	private Component parent = null;
	private Rectangle area = new Rectangle(25,25,400,400);
	
	public GraphLayout(Component parent) {
		this.parent = parent;
	}
	
	public void addNode(GraphNode node) {
		nodes.add(node);
	}
	
	public void addEdge(GraphEdge edge) {
		edges.add(edge);
	}
	
	public void removeAllNodes() {
		nodes.removeAllElements();
	}
	
	public void removeAllEdges() {
		edges.removeAllElements();
	}
	
	public Rectangle getPreferredArea() {
		return this.area;
	}
	
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}
	
	public void stop() {
		th = null;
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (th == me) {
			relax();
			
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	protected synchronized void relax() {
		// 移動分の計算
		for (int i=0; i<edges.size(); i++) {
			GraphEdge edge = (GraphEdge)edges.get(i);
			if (edge.to == null || edge.from == null) continue;
			double vx = edge.to.getPosition().getX() - edge.from.getPosition().getX();
			double vy = edge.to.getPosition().getY() - edge.from.getPosition().getY();
			double len = Math.sqrt(vx*vx+vy*vy);
			if (len == 0) len = 0.0001; // f の計算でエラーにならないように

			double f = (edge.len - len) / (len * 3);
			double dx = f*vx;
			double dy = f*vy;
			
			edge.from.setMoveDelta(-dx,-dy);
			edge.to.setMoveDelta(dx,dy);
		}
		
		// 
		for (int i=0;i<nodes.size();i++) {
			GraphNode node1 = (GraphNode)nodes.get(i);
			double dx = 0;
			double dy = 0;
			
			for (int j=0;j<nodes.size();j++) {
				if (i==j) continue;
				GraphNode node2 = (GraphNode)nodes.get(j);
				double vx = node1.getPosition().getX() - node2.getPosition().getX();
				double vy = node1.getPosition().getY() - node2.getPosition().getY();
				double len = vx*vx+vy*vy;
				if (len == 0) {
					dx += Math.random();
					dy += Math.random();
				} else if (len < 10000){
					dx += vx/len;
					dy += vy/len;
				}
			}
			double dlen = dx*dx+dy*dy;
			if (dlen > 0) {
				dlen = Math.sqrt(dlen) / 2;
				node1.setMoveDelta(dx/dlen,dy/dlen);
			}
		}
		
		// 移動する
		for (int i=0;i<nodes.size();i++) {
			GraphNode node = (GraphNode)nodes.get(i);
			node.move(area);
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i=0;i<edges.size();i++) ((GraphEdge)edges.get(i)).paint(g);
		
		for (int i=0;i<nodes.size();i++) ((GraphNode)nodes.get(i)).paint(g);
	}
}
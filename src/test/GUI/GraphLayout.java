package test.GUI;

import java.util.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class GraphLayout implements Runnable {

	private Vector nodes = new Vector();
	private Thread th = null;
	private static final int DELAY = 50;
	private Component parent = null;
	private Rectangle area = new Rectangle(25,25,400,400);
	
	public GraphLayout(Component parent) {
		this.parent = parent;
	}
	
	public void addNode(GraphNode node) {
		nodes.add(node);
	}
	
	public void removeAllNodes() {
		nodes.removeAllElements();
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
	
	public void calc() {
//		if(true) return;
		for(int i=0;i<100;i++) {
			relax();
		}
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
		for (int i=0;i<nodes.size();i++) {
			GraphNode node1 = (GraphNode)nodes.get(i);
			double dx = 0;
			double dy = 0;
			
//			System.out.println(i+" "+node1.label);
			
			// angle でソート
			GraphEdge ie[] = new GraphEdge[node1.linkedNodes.size()];
			ie = (GraphEdge[])(node1.linkedNodes.toArray(ie));
			Arrays.sort(ie);
			
			for (int j=0;j<ie.length;j++) {
				GraphEdge edge = ie[j];
				
				// デフォルトの長さに伸縮する
				if(edge.from.hashCode() < edge.to.hashCode()){
					double f = (edge.getStdLen() - edge.getLen()) / (edge.getStdLen() * 1);
					double ddx = f * edge.getVx();
					double ddy = f * edge.getVy();
					
					edge.from.setMoveDelta(-ddx,-ddy);
					edge.to.setMoveDelta(ddx,ddy);
				}
				
				if(node1.linkedNodes.size()<=1) continue;
			
				// cur にかかる力を計算する
				{
					GraphEdge cur = ie[j];
	//				System.out.println(cur);
					GraphNode node2 = cur.to;
					
					GraphEdge prev = ie[(j-1+ie.length)%ie.length];
					GraphEdge next = ie[(j+1)%ie.length];
	//				System.out.println("  p : "+ prev);
	//				System.out.println("  n : "+ next);
					
					double a_p = regulate(cur.getAngle() - prev.getAngle());
					double a_n = regulate(next.getAngle() - cur.getAngle());
					double a_r = a_n-a_p;
	//				System.out.println("  a_p : "+ a_p*180/Math.PI);
	//				System.out.println("  a_n : "+ a_n*180/Math.PI);
	//				System.out.println("   a_r : "+ a_r*180/Math.PI);
					
					double vx = cur.to.getPosition().getX() - node1.getPosition().getX();
					double vy = cur.to.getPosition().getY() - node1.getPosition().getY();
					
					// next 周りを正にした、動かす対象と自分を結ぶ線分に垂直で長さ１のベクトル
					double tx = -vy;
					double ty =  vx;
	//				System.out.println("   tx : "+ tx);
	//				System.out.println("   ty : "+ ty);
					
					double len = Math.sqrt(tx*tx+ty*ty);
					
					// move = t times diff
					dx = 2 * tx / len * a_r;
					dy = 2 * ty / len * a_r;
					
					cur.to.setMoveDelta(dx,dy);
				}
			}
		}
		
//		for (int i=0;i<nodes.size();i++) {
//			GraphNode node1 = (GraphNode)nodes.get(i);
//			double dx = 0;
//			double dy = 0;
//			
//			for (int j=0;j<nodes.size();j++) {
//				if (i==j) continue;
//				GraphNode node2 = (GraphNode)nodes.get(j);
//				double vx = node1.getPosition().getX() - node2.getPosition().getX();
//				double vy = node1.getPosition().getY() - node2.getPosition().getY();
//				double len = vx*vx+vy*vy;
//				if (len == 0) {
//					dx += Math.random();
//					dy += Math.random();
//				} else if (len < 10000){
//					dx += vx/len;
//					dy += vy/len;
//				}
//			}
//			double dlen = dx*dx+dy*dy;
//			if (dlen > 0) {
//				dlen = Math.sqrt(dlen) / 2;
//				node1.setMoveDelta(dx/dlen,dy/dlen);
//			}
//		}
		
		// 実際に移動する
		for (int i=0;i<nodes.size();i++) {
			GraphNode node = (GraphNode)nodes.get(i);
			node.move(area);
		}
	}
	
	static double regulate(double a) {
		while(a<0.0) a+= Math.PI*2;
		while(a>2*Math.PI) a-= Math.PI*2;
		return a;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		
		for (int i=0;i<nodes.size();i++) ((GraphNode)nodes.get(i)).paintEdge(g);
		for (int i=0;i<nodes.size();i++) ((GraphNode)nodes.get(i)).paintNode(g);
	}
}
package test.GUI;

import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.util.Arrays;
import java.util.LinkedList;
import java.awt.*;

public class GraphLayout implements Runnable {
//	public static Color colors[] = {
//		Color.BLACK,
//		Color.BLUE,
//		Color.CYAN,
//		Color.GREEN,
//		Color.MAGENTA,
//		Color.ORANGE,
//		Color.RED
//	};
	
	private Vector nodes = new Vector();
	private Thread th = null;
	private static final int DELAY = 50;
	private Component parent = null;
	private Rectangle area;// = new Rectangle(25,25,400,400);
	runtime.Membrane rootMem;
	
	public GraphLayout(Component parent) {
		this.parent = parent;
		area = parent.getBounds();
//		System.out.println("area = "+area);
	}
	
	public void setRootMem(runtime.Membrane rootMem) {
		this.rootMem = rootMem;
//		System.out.println("setRootMem"+rootMem);
	}
	
	public Rectangle getPreferredArea() {
		return this.area;
	}
	
	public void addNode(GraphNode node) {
		nodes.add(node);
	}
	
	public void removeAllNodes() {
		nodes.removeAllElements();
	}
	
	public Node getNearestNode(Point p) {
		double min=Double.MAX_VALUE;
		Node minn=null;
		for(Iterator i=rootMem.atomIterator();i.hasNext();) {
			Node n = (Node)i.next();
			double d = p.distance(n.getPosition().toPoint());
			if(min>d) {
				min = d;
				minn = n;
			}
		}
		return minn;
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
		setAllowRelax(true);
		for(int i=0;i<100;i++) {
			relax();
		}
		setAllowRelax(false);
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
	
	public List removedAtomPos = Collections.synchronizedList(new LinkedList());
	public Rectangle getAtomsBound() {
		final int m=1;
		Rectangle r=null;
		if(!removedAtomPos.isEmpty()) {
			return new Rectangle(((DoublePoint)removedAtomPos.remove(0)).toPoint());
		}
		r = new Rectangle((parent.getWidth() - parent.getWidth()/m)/2, (parent.getHeight() - parent.getHeight()/m)/2, parent.getWidth()/m, parent.getHeight()/m);
//		if(rootMem.getAtomCount()==0) {
//		} else {
//		}
		for (Iterator i=rootMem.atomIterator();i.hasNext();) {
			Node n = (Node)i.next();
			Point p = n.getPosition().toPoint();
//			System.out.println(r+" "+p);
//			if(r==null) r = new Rectangle(p);
			if(!r.contains(p)) {
				r.add(p);
			}
		}
//		int w = r.width, h = r.height;
//		r.x += w/4;
//		r.y += h/4;
//		r.width -= w/2;
//		r.height -= h/2;
		return r;
	}
	
	private volatile boolean allowRelax;
	public synchronized void setAllowRelax(boolean v) {
		allowRelax = v;
	}
	public synchronized boolean getAllowRelax() {
		return allowRelax;
	}
	protected synchronized void relax() {
		if(!getAllowRelax()) return;
		if(rootMem==null) return;
		
		for (Iterator i=rootMem.atomIterator();i.hasNext();) {
			Node me = (Node)i.next();
			double dx = 0;
			double dy = 0;
			
//			System.out.println(i+" "+me.getName());
			
			// angle でソート
			Edge ie[] = new Edge[me.getEdgeCount()];
			for(int j=0;j<ie.length;j++) {
				ie[j]=new Edge(me, me.getNthNode(j));
			}
			Arrays.sort(ie);
			
			for (int j=0;j<ie.length;j++) {
				Edge edge = ie[j];
				
				// デフォルトの長さに伸縮する
				if(me.hashCode() < edge.to.hashCode()){
					double l = edge.getLen();
					double f = (l - edge.getStdLen());// / (edge.getStdLen() * 1);
					double ddx = 0.5 * f * edge.getVx()/l;
					double ddy = 0.5 * f * edge.getVy()/l;
					
					edge.from.setMoveDelta(ddx, ddy);
					edge.to.setMoveDelta(-ddx, -ddy);
				}
				
				if(me.getEdgeCount()<=1) continue;
				
				// cur.to にかかる力を計算する
				{
					Edge cur = ie[j];
	//				System.out.println(cur);
					Node you = cur.to;
					
					Edge prev = ie[(j-1+ie.length)%ie.length];
					Edge next = ie[(j+1)%ie.length];
	//				System.out.println("  p : "+ prev);
	//				System.out.println("  n : "+ next);
					
					// 次の辺との角度
					double a_p = regulate(cur.getAngle() - prev.getAngle());
					// 前の辺との角度
					double a_n = regulate(next.getAngle() - cur.getAngle());
					double a_r = a_n-a_p;
	//				System.out.println("  a_p : "+ a_p*180/Math.PI);
	//				System.out.println("  a_n : "+ a_n*180/Math.PI);
	//				System.out.println("   a_r : "+ a_r*180/Math.PI);
					
					double l = cur.getLen();
					
					// 時計周りを正にした、動かす対象と自分を結ぶ線分に垂直で長さ１のベクトル
					// これが you に働く力の単位ベクトルになる
					double tx = -cur.getVy() / l;
					double ty =  cur.getVx() / l;
	//				System.out.println("   tx : "+ tx);
	//				System.out.println("   ty : "+ ty);
					
					// move = t times diff
					dx = 1.5 * tx * a_r;
					dy = 1.5 * ty * a_r;
					
					me.setMoveDelta(-dx,-dy);
					you.setMoveDelta(dx,dy);
				}
			}
		}
		// 実際に移動する
		for (Iterator i=rootMem.atomIterator();i.hasNext();) {
			Node me = (Node)i.next();
			if(me==((LMNGraphPanel)parent).movingNode) continue;
			me.move(parent.getBounds());
		}
	}
	
//	protected synchronized void relax_old() {
//		for (int i=0;i<nodes.size();i++) {
//			GraphNode node1 = (GraphNode)nodes.get(i);
//			double dx = 0;
//			double dy = 0;
//		
////				System.out.println(i+" "+node1.label);
//		
//			// angle でソート
//			Edge ie[] = new Edge[node1.linkedNodes.size()];
//			ie = (Edge[])(node1.linkedNodes.toArray(ie));
//			Arrays.sort(ie);
//		
//			for (int j=0;j<ie.length;j++) {
//				Edge edge = ie[j];
//			
//				// デフォルトの長さに伸縮する
//				if(edge.from.hashCode() < edge.to.hashCode()){
//					double f = (edge.getStdLen() - edge.getLen()) / (edge.getStdLen() * 1);
//					double ddx = f * edge.getVx();
//					double ddy = f * edge.getVy();
//				
//					edge.from.setMoveDelta(-ddx,-ddy);
//					edge.to.setMoveDelta(ddx,ddy);
//				}
//			
//				if(node1.linkedNodes.size()<=1) continue;
//			
//				// cur にかかる力を計算する
//				{
//					Edge cur = ie[j];
//	//				System.out.println(cur);
//					GraphNode node2 = cur.to;
//				
//					Edge prev = ie[(j-1+ie.length)%ie.length];
//					Edge next = ie[(j+1)%ie.length];
//	//				System.out.println("  p : "+ prev);
//	//				System.out.println("  n : "+ next);
//				
//					double a_p = regulate(cur.getAngle() - prev.getAngle());
//					double a_n = regulate(next.getAngle() - cur.getAngle());
//					double a_r = a_n-a_p;
//	//				System.out.println("  a_p : "+ a_p*180/Math.PI);
//	//				System.out.println("  a_n : "+ a_n*180/Math.PI);
//	//				System.out.println("   a_r : "+ a_r*180/Math.PI);
//				
//					double vx = cur.to.getPosition().getX() - node1.getPosition().getX();
//					double vy = cur.to.getPosition().getY() - node1.getPosition().getY();
//				
//					// next 周りを正にした、動かす対象と自分を結ぶ線分に垂直で長さ１のベクトル
//					double tx = -vy;
//					double ty =  vx;
//	//				System.out.println("   tx : "+ tx);
//	//				System.out.println("   ty : "+ ty);
//				
//					double len = Math.sqrt(tx*tx+ty*ty);
//				
//					// move = t times diff
//					dx = 5 * tx / len * a_r;
//					dy = 5 * ty / len * a_r;
//				
//					cur.from.setMoveDelta(-dx,-dy);
//					cur.to.setMoveDelta(dx,dy);
//				}
//			}
//		}

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
		
//		for (int i=0;i<nodes.size();i++) {
//			GraphNode node = (GraphNode)nodes.get(i);
//			node.move(area);
//		}
//	}
	
	static double regulate(double a) {
		while(a<0.0) a+= Math.PI*2;
		while(a>2*Math.PI) a-= Math.PI*2;
		return a;
	}
	
	public void paint(Graphics g) {
		if(!getAllowRelax()) return;
		if(rootMem==null) return;
		g.setColor(Color.BLACK);
		
		// 同時アクセスで java.util.ConcurrentModificationException がでるので Iterator やめた
		// ここでの用途は readonly
		Node[] nodes = (Node[])rootMem.getAtomArray();
		for (int i=0;i<nodes.length;i++) {
			nodes[i].paintEdge(g);
		}
		for (int i=0;i<nodes.length;i++) {
			nodes[i].paintNode(g);
		}
//		for (int i=0;i<nodes.size();i++) ((GraphNode)nodes.get(i)).paintEdge(g);
//		for (int i=0;i<nodes.size();i++) ((GraphNode)nodes.get(i)).paintNode(g);
	}
}

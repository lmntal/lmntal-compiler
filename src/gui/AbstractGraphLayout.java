package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import runtime.AbstractMembrane;
import runtime.Env;

public abstract class AbstractGraphLayout implements Runnable {
	
	protected Vector nodes = new Vector();
	protected Thread th = null;
	protected static final int DELAY = 50;
	protected Component parent = null;
	protected Rectangle area;// = new Rectangle(25,25,400,400);
	protected Vector fixedNode = new Vector();
	runtime.Membrane rootMem;
	
	public AbstractGraphLayout(Component parent) {
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
	
//	public void addNode(GraphNode node) {
//		nodes.add(node);
//	}
	
	public void removeAllNodes() {
		nodes.removeAllElements();
	}
	
	class D_N_tuple {
		double d = Double.MAX_VALUE;
		Node n   = null;
	}
	
	/**
	 * PROXY を除く全アトムのうち p に最も近い Node を返す。
	 * @param p
	 * @return Node
	 */
	public Node getNearestNode(Point p) {
		D_N_tuple t = new D_N_tuple();
		getNearestNode(p, rootMem, t);
		return t.n;
	}
	
	/**
	 * 膜 m 内にある Node と t.n のうち p に最も近い Node を t.n に代入する。t.d にはその距離を代入する。
	 * @param p
	 * @param m
	 * @param t
	 */
	public void getNearestNode(Point p, runtime.AbstractMembrane m, D_N_tuple t) {
		for(Iterator i=m.atomIterator();i.hasNext();) {
			Node n = (Node)i.next();
			if(!n.isVisible()) continue;
			double d = p.distance(n.getPosition().toPoint());
			if(t.d>d) {
				t.d = d;
				t.n = n;
			}
		}
		Object[] mems = m.getMemArray();
		for(int i=0;i<mems.length;i++) {
			getNearestNode(p, (AbstractMembrane)mems[i], t);
		}
	}
	
	/**
	 * 膜に含まれるアトムをリンクでつながったまとまり毎に分ける。
	 * リンクでつながった塊ごとにVecotrに分けられ、そのVectorを要素として持つVectorが返される。
	 *
	 */
	protected Vector separateAtomGroup(AbstractMembrane m){
		Node a;
		Vector tmpSeparated;
		Iterator ite = m.atomIterator();
		LinkedList atoms = new LinkedList();
		Vector separatedAtoms = new Vector();
		LinkedList tmpQueue = new LinkedList();
		
		while(ite.hasNext()){
			atoms.add(ite.next());
		}
		
		while(atoms.size() > 0){
			a = (Node)atoms.removeFirst();
			tmpQueue.add(a);
			tmpSeparated = new Vector();
			
			while(tmpQueue.size() > 0){
				a = (Node)tmpQueue.removeFirst();
				//if(!a.isVisible())continue;
				tmpSeparated.add(a);
				
				for(int i = 0;i < a.getEdgeCount();i++){
					if(!tmpSeparated.contains(a.getNthNode(i))&&!tmpQueue.contains(a.getNthNode(i))){
						tmpQueue.add(a.getNthNode(i));
						atoms.remove(a.getNthNode(i));
					}
				}
			}
			
			separatedAtoms.add(new AtomGroup(tmpSeparated));
		}
		
		return separatedAtoms;
		
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
	/**
	 * fixedNodeに含まれているNodeなら true を返す。
	 * @param me
	 * @return
	 */
	public boolean isFixed(Node me){
		if(fixedNode.contains(me))return true;
		return false;
	}
	public boolean isFixed(AtomGroup atomgroup){
		for(Iterator it = atomgroup.atoms.iterator();it.hasNext();){
			if(isFixed((Node)it.next())){
				return true;
			}
		}
		return false;
	}
	public void nodeDoubleClick(Node me){
		if(fixedNode.contains(me)){
			fixedNode.remove(me);
		} else {
			fixedNode.add(me);
		}
	}
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
	

	/**
	 * すべてのアトムについて力を作用させる。
	 *
	 */
	protected synchronized void relax() {
		if(!getAllowRelax()) return;
		relax(rootMem);
	}
	
	protected Vector atoms;
	
	public Vector getAtoms(){
		return atoms;
	}
	
	/**
	 * 膜 m にあるアトムについて力を作用させる。
	 * @param m
	 */
	boolean testf = false;
	
	/**
	 * 継承したクラスで座標処理の内容を記述する。
	 * @param m
	 */
	abstract protected void relax(runtime.AbstractMembrane m);
	
	/**
	 * テスト用膜に含まれるアトムの名前とmoveDeltaを出力する。
	 * @param a
	 * @return
	 */
	void testPrint(AbstractMembrane m,String comment){
		System.out.println(comment+"\n\n");
		
		if(m == rootMem)System.out.println("rootMem");
		else System.out.println("Not rootMem");
		
		int i = 0;
		for(Iterator it = m.atomIterator();it.hasNext();i++){
			Node n = (Node)it.next();
			if(!n.isVisible()){
				continue;
			}
			System.out.print("Atom "+i);
			System.out.print("; "+n.getName());
//			System.out.print("; x "+ n.getMoveDelta().x+",y "+ n.getMoveDelta().y);
			System.out.print("; Position "+ n.getPosition().x + ", "+n.getPosition().y);
			System.out.println("");
		}
		System.out.println("\n");
//		
//		Object mems[] = m.getMemArray();
//		if(mems.length != 0){
//			for(int j = 0;j < mems.length;j++){
//				System.out.println("子膜" + (j+1));
//			
//				i = 0;
//				for(Iterator it = ((AbstractMembrane)mems[j]).atomIterator();it.hasNext();i++){
//					Node n = (Node)it.next();
//					System.out.print("Atom "+i);
//					System.out.print("; "+n.getName());
//					System.out.print("; x "+ n.getMoveDelta().x+",y "+ n.getMoveDelta().y);
//					System.out.print("; Position "+ n.getPosition().x + ", "+n.getPosition().y);
//					if(n.getPosition().x == 0&&n.getPosition().y == 0)System.out.print("; 画面はし");
//					System.out.println("");
//				}
//			}
//		}
//		System.out.println("\n\n\n\n");
	}
	

	/**
	 * アトムグループに膜 m の子膜に含まれるアトムがあるか
	 * @param a
	 * @return
	 */
	boolean doesCutAcrossMembrane(AtomGroup atomgroup,AbstractMembrane m){
		boolean f = false;
		runtime.Atom n;
		
		Object[] mems = m.getMemArray();
		for(Iterator it = atomgroup.atoms.iterator();it.hasNext();){
			n = (runtime.Atom)it.next();
			if(n.getMem() != m){
				for(int i = 0;i<mems.length;i++){
					if(n.getMem() == (AbstractMembrane)mems[i]){
						f = true;
					}
				}
				if(n.getMem() == m.getParent())f = true;
			}
			
		}
		
		return f;
	}
	
	/**
	 * [0, 2PI) に正規化してかえす。mod 2PI みたいなかんじ。
	 * @param a
	 * @return
	 */
	static double regulate(double a) {
		while(a<0.0) a+= Math.PI*2;
		while(a>=2*Math.PI) a-= Math.PI*2;
		return a;
	}
	
	/*
	 * 1/r,1/r^2,定数などの関数。後で名前を変更する。
	 */
	/**
	 * y1 が y2 より小さかったときに y2 より大きくなるような力
	 */
	double force(double y1,double y2){
		double f1 = 1.6,f2 = 6.0;
		double dy = 0;
		double d,f;
		f1 = 1.6;f2 = 6.0;
		d = y1 - y2;
		d = f1 + d/15;
		
		if(d <= 0){
			f = f2;
		} else {
			f = f1 / d;
			if(f > f2){
				f = f2;
			}
		}
		
		return f;
	}
	/**
	 * グループ同士の反発する力を計算する。
	 */
	 public double repulsiveForce(AbstractMembrane m1,AbstractMembrane m2){
			DoublePoint max,min,maxa,mina;
			boolean a1,a2,a3,a4,b1,b2,b3,b4;
			double f,f1,f2,l;
			double d = 0;
			double margin = Env.atomSize;
			f = 0.0;
			f1 = 1.6;
			f2 = 6.0;
			l = margin * 1.5;
			
			max = new DoublePoint(m1.rect.getMaxX(),m1.rect.getMaxY());
			min = new DoublePoint(m1.rect.getMinX(),m1.rect.getMinY());
			maxa = new DoublePoint(m2.rect.getMaxX(),m2.rect.getMaxY());
			mina = new DoublePoint(m2.rect.getMinX(),m2.rect.getMinY());
			
//			max.x += margin;max.y += margin;
//			min.x -= margin;min.y -= margin;
//			maxa.x += margin;maxa.y += margin;
//			mina.x -= margin;mina.y -= margin;
			
			if(max.x >= mina.x - l && max.x <= maxa.x + l)a1 = true;else a1 =false;
			if(min.x >= mina.x - l && min.x <= maxa.x + l)a2 = true;else a2 =false;
			if(max.y >= mina.y - l && max.y <= maxa.y + l)a3 = true;else a3 =false;
			if(min.y >= mina.y - l && min.y <= maxa.y + l)a4 = true;else a4 =false;

			if(maxa.x >= min.x - l && maxa.x <= max.x + l)b1 = true;else b1 =false;
			if(mina.x >= min.x - l && mina.x <= max.x + l)b2 = true;else b2 =false;
			if(maxa.y >= min.y - l && maxa.y <= max.y + l)b3 = true;else b3 =false;
			if(mina.y >= min.y - l && mina.y <= max.y + l)b4 = true;else b4 =false;

			//範囲が重なっていない場合は 0 を返す
			if((!a1&&!a2&&!b1&&!b2)||(!a3&&!a4&&!b3&&!b4)){
				return 0;
			}
			//重なり方を場合わけして計算
			if((a1&&a2&&a3&&a4)||(b1&&b2&&b3&&b4))return f2;
			if(((a1&&a2)&&(a3||a4))||((b1&&b2)&&(b3||b4))){
				if(a1&&a2){
					if(a3){
						d =  mina.y - max.y;
					} else if(a4){
						d =  min.y - maxa.y;
					}
				} else if(b1&&b2){
					if(b3){
						d = min.y - maxa .y;
					} else if(b4){
						d = mina .y - max.y;
					}
				}
			} else if(((a1||a2)&&(a3&&a4))||((b1||b2)&&(b3&&b4))){
				if(a3&&a4){
					if(a1){
						d = mina.x - max.x;
					} else if(a2){
						d = min.x - maxa.x;
					}
				} else if(b3&&b4){
					if(b1){
						d = min.x - maxa.x;
					} else if(b2){
						d = mina.x - max.x;
					}
				}
			} else if(((a1||a2)&&(a3||a4))||((b1||b2)&&(b3||b4))){
				double dtemp;
				if(a1){
					if(a3){
						d = mina.x - max.x;
						dtemp = mina.y - max.y;
						if(dtemp > d)d = dtemp;
					} else if(a4){
						d = mina.x - max.x;
						dtemp = min.y - maxa.y;
						if(dtemp > d)d = dtemp;
					}
				} else if(a2){
					if(a3){
						d = min.x - maxa.x;
						dtemp = mina.y - max.y;
						if(dtemp > d)d = dtemp;
					} else if(a4){
						d = min.x -maxa.x;
						dtemp = min.y - maxa.y;
						if(dtemp > d)d = dtemp;
					}
				}
			} else if(((a1||a2)&&(b3||b4))||((b1||b2)&&(a3||a4))){
				return f2;
			}
			
			return repulsiveForcef1(d,f1,f2);	

	 }
	 
	 public double repulsiveForce(Node n,AbstractMembrane m1){
		DoublePoint max,min;
		boolean b1,b2;
		double f,f1,f2,l,nx,ny;
		double d = 0,dtemp = 0;
		double margin = AtomGroup.margin * 2;
		f = 0.0;
		f1 = 1.6;
		f2 = 6.0;
		l = margin * 1.5;
		
		max = new DoublePoint(m1.rect.getMaxX(),m1.rect.getMaxY());
		min = new DoublePoint(m1.rect.getMinX(),m1.rect.getMinY());
		nx = n.getPosition().x;
		ny = n.getPosition().y;
		
//		max.x += margin;max.y += margin;
//		min.x -= margin;min.y -= margin;
//		maxa.x += margin;maxa.y += margin;
//		mina.x -= margin;mina.y -= margin;
		
		if(nx >= min.x - l - margin && nx <= max.x + l + margin)b1 = true;else b1 =false;
		if(ny >= min.y - l - margin && ny <= max.y + l + margin)b2 = true;else b2 =false;

		
		if(b1 && b2){
			if(nx - m1.rect.getCenterX() < 0){
				d = m1.rect.getMinX() - (nx + margin);
			} else {
				d = (nx - margin) - m1.rect.getMaxX();
			}
			if(ny - m1.rect.getCenterX() < 0){
				dtemp = m1.rect.getMinY() - (ny + margin);
			} else {
				dtemp = (ny - margin) - m1.rect.getMaxY();
			}
			if(d > dtemp){
				d = dtemp;
			}
			f = repulsiveForcef1(d,f1,f2);
			return f;
		} else {
			return 0;
		}
	 }
	 
	 double repulsiveForcef1(double x,double f1,double f2){
	 	double f;
	 	x = x / 15;
		if(x+f1 <= 0){
			return f2;
		}
		f = f1 / (x + f1);
		if(f > f2){
			return f2;
		}
	 	return f;
	 }
	/*
	double force1(DoublePoint d1,DoublePoint d2,double teisuu){
		return teisuu;
	}
	double force2(DoublePoint d1,DoublePoint d2,double teisuu){
		double f = teisuu / doublePointLength(d1,d2);
		return f;
	}
	double force3(DoublePoint d1,DoublePoint d2,double teisuu){
		double d = doublePointLength(d1,d2);
		double f = teisuu / d*d;
		return f;
	}
	double force4(DoublePoint d1,DoublePoint d2,double l,double teisuu){
		double d = doublePointLength(d1,d2);
		double f = teisuu * (l - d);
		return f;
	}
	double force5(DoublePoint d1,DoublePoint d2,double l,double teisuu){
		double d = doublePointLength(d1,d2);
		double f = 0;
		if(d > l)f = teisuu;
		return f;
	}
	
	double doublePointLength(DoublePoint d1,DoublePoint d2){
		double dx = d1.x - d2.x;
		double dy = d1.y - d2.y;
		
		return Math.sqrt(dx*dx + dy*dy);
	}
	*/
	/**
	 * 膜やリンクで繋がったアトムのグループに含まれるノードにまとめて移動量を設定する
	 */
	
	public void setMoveDelta(AbstractMembrane m ,double dx ,double dy){
		Iterator i = getAllNodeIterator(m);
		while(i.hasNext()){
			((Node)i.next()).setMoveDelta(dx,dy);
		}
	}
	
	public void setMoveDelta(AtomGroup a ,double dx ,double dy){
		Iterator i = a.atoms.iterator();
		while(i.hasNext()){
			((Node)i.next()).setMoveDelta(dx,dy);
		}
	}
	
	/**
	 * 膜に含まれる全てのアトムを返す Iterator を返す
	 */
	
	public Iterator getAllNodeIterator(AbstractMembrane mem){
		Iterator it;
		Vector vector = new Vector();
		Object[] mems = mem.getMemArray();
		
		if(mems.length == 0)return mem.atomIterator();
		for(int i = 0;i<mems.length;i++){
			getAllNodeIterator((AbstractMembrane)mems[i],vector);
		}
		vector.addAll(Arrays.asList(mem.getAtomArray()));
		
		return vector.iterator();
	}
	
	private void getAllNodeIterator(AbstractMembrane mem,Vector vector){
		Object[] mems = mem.getMemArray();
		
		if(mems.length == 0){
			vector.addAll(Arrays.asList(mem.getAtomArray()));
			return;
		}
		
		for(int i = 0;i < mems.length;i++){
			getAllNodeIterator((AbstractMembrane)mems[i],vector);
		}
		return;
	}
	
	/**
	 * アトムグループの輪郭を描画する。 ogawa
	 * @param g
	 */
	public void paintAtomGroup(Graphics g){
		AtomGroup atomgroup;
		DoublePoint max,min;
		if(atoms != null){
			for(Iterator it = atoms.iterator();it.hasNext();){
				atomgroup = (AtomGroup)it.next();
				max = atomgroup.getMaxPos();
				min = atomgroup.getMinPos();
				max.x += Env.atomSize;max.y += Env.atomSize;
				min.x -= Env.atomSize;min.y -= Env.atomSize;
				
				g.drawRoundRect((int)min.x,(int)min.y,(int)(max.x - min.x),(int)(max.y - min.y),10,10);
			}
		}
	}
	
	/**
	 * すべてのアトムと膜を描画する。
	 * @param g
	 */
	public void paint(Graphics g) {
		if(!getAllowRelax()) return;
		if(rootMem==null) return;
		g.setColor(Color.BLACK);
		
		paintMem(g, rootMem);
		//paintAtomGroup(g);
		
	}
	
	/**
	 * 膜 m に属するすべてのアトムと膜を描画する。
	 * @param g
	 * @param m
	 */
	public void paintMem(Graphics g, AbstractMembrane m) {
		// 同時アクセスで java.util.ConcurrentModificationException がでるので Iterator やめた
		// ここでの用途は readonly
		final double MARGIN = 15.0;
		
		Node[] nodes = (Node[])m.getAtomArray();
		m.rect.setRect(m.rect.x, m.rect.y, 0.0, 0.0);
		for(int i=0;i<nodes.length;i++) {
			if(!nodes[i].isVisible()) continue;
			double mg = MARGIN+runtime.Env.atomSize;
			if(m.rect.isEmpty()) m.rect.setRect(nodes[i].getPosition().x-mg, nodes[i].getPosition().y-mg, mg*2, mg*2);
			else                 m.rect.add(new Rectangle2D.Double(nodes[i].getPosition().x-mg, nodes[i].getPosition().y-mg, mg*2, mg*2));
			nodes[i].paintEdge(g);
		}
		for(int i=0;i<nodes.length;i++) {
			if(!nodes[i].isVisible()) continue;
			nodes[i].paintNode(g);
		}
		// 子膜
		Object[] mems = m.getMemArray();
		for(int i=0;i<mems.length;i++) {
			AbstractMembrane mm = (AbstractMembrane)mems[i];
			paintMem(g, mm);
			if(m.rect.isEmpty()) m.rect.setRect(mm.rect.x-MARGIN, mm.rect.y-MARGIN, mm.rect.width+MARGIN*2, mm.rect.height+MARGIN*2);
			else                 m.rect.add(new Rectangle2D.Double(mm.rect.x-MARGIN, mm.rect.y-MARGIN, mm.rect.width+MARGIN*2, mm.rect.height+MARGIN*2));
//			System.out.println(m.rect);
		}
		if(!m.equals(rootMem)) {
			final int ROUND=40;
			// 空の膜のばあいはここで empty になっている
			if(m.rect.isEmpty()) m.rect.width=m.rect.height=MARGIN*2;
			g.drawRoundRect((int)m.rect.x, (int)m.rect.y, (int)m.rect.width, (int)m.rect.height, ROUND, ROUND);
		}
	}
	
	//public abstract void initForces();
}


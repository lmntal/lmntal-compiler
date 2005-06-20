package test3d;


import java.util.Iterator;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import java.awt.Color;




public class Graph3DLayout implements Runnable{
	private static final int DELAY = 50;
	private static final double EDGELENGTH_MAX = 0.3;//希望の長さの上限
	private static final double EDGELENGTH_MIN = 0.1;//希望の長さの下限
	private static final double DELTAEDGE = 0.01;//一回に移動する距離
	private Thread th = null;
	runtime.Membrane rootMem = null;
	private static BranchGroup obj;
	private LMNTransformGroup obj2;
	private Atom3D atom = null;
	private LMNGraph3DPanel parent = null;
	private static int i2 =0;
	private static double i3 =0;
	public static Edge3D firstEdge = new Edge3D();
	public static Edge3D ie_tmp=null;
	private LineArray geometry;
	private boolean inited=false; 
	
	public Graph3DLayout(LMNGraph3DPanel parent){
		this.parent = parent;
	}
	
	/*
	 public void make_atom(BranchGroup objBranch){
		
		System.out.println("make_atom");
		atom=new Atom3D();
		//atom.createNode(obj);
		//System.out.println(rootMem.toString());
		relax(rootMem);
	}
	*/
	
	/*ルート膜をセット*/
	public void setRootMem(runtime.Membrane rootMem) {
		System.out.println("set m");
		this.rootMem = rootMem;
		//obj2=new BranchGroup();
	}

	private volatile boolean allowRelax = false;
	
	public synchronized void setAllowRelax(boolean v) {
		allowRelax = v;
	}
	public synchronized boolean getAllowRelax() {
		return allowRelax;
	}
	/*計算！*/
	public void init() {
		setAllowRelax(true);
		relax_init();
		setAllowRelax(false);
	}
	
	/*オブジェクトを追加するブランチをセット*/
	public void setBranch(BranchGroup objBranch){
		System.out.println("set Branch");
		obj=objBranch;
		//relax(rootMem);
		//System.out.println(obj.toString());
	}
	
	/**
	 * すべてのアトムについて力を作用させる。
	 */
	protected synchronized void relax_init() {
		//if(!getAllowRelax()) return;
		relax_init(rootMem);
		inited=true;
	}
	protected synchronized void edgerelax(Node3D me) {
		//if(!getAllowRelax()) return;
		if(!inited) return;
		edgerelax(rootMem,me);
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (th == me) {
			try {
				Thread.sleep(DELAY);
				if(rootMem!=null){
					//if(!getAllowRelax()) continue;
					if(!inited) continue;
					relax(rootMem);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	public void start() {
	  	// スレッド開始
	  	th = new Thread(this);
	  	th.start();
	}
	public void stop() {
		// スレッド終了
		th = null;
	}

	public Shape3D makeLink(Node3D me, Node3D you){
//		BranchGroup obj3 = new BranchGroup();


		Point3d[] vertex = new Point3d[2];
		
		vertex[0] = new Point3d(me.getPosition3d().x, me.getPosition3d().y, me.getPosition3d().z);
		vertex[1] = new Point3d(you.getPosition3d().x, you.getPosition3d().y, you.getPosition3d().z);
		
		/* LineArrayクラスを使って物体の形状を作成 */
		LineArray geometry = new LineArray(vertex.length, 
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		/*LineArray geometry = new LineArray(vertex.length, 
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);*/
		geometry.setCoordinates(0, vertex);
		
		/* 各頂点ごとに色を指定 */
		geometry.setColor(0, new Color3f(Color.magenta));
		geometry.setColor(1, new Color3f(Color.cyan));

		/* 作成したGeometryを元に物体を作成 */
		Shape3D shape = new Shape3D(geometry);
		shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
		shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);

		//System.out.println(me.getPosition3d().x);
//		obj3.addChild(shape);
		return shape;
		
	}
	
	/**絶対値を返す**/
	public double abs(double x){
		if(x<0)return -x;
		else return x;
	}
	/**
	 * 膜 m にあるアトムについて力を作用させる。
	 * @param m
	 */
	protected void relax_init(runtime.AbstractMembrane m) {
		LMNtal3DFrame a;
		//System.out.println("relax");
		if(m==null) return;
		System.out.println("relax m ok");
		
		for (Iterator i=m.atomIterator();i.hasNext();) {

			Node3D me = (Node3D)i.next();

			double dx = me.getPosition3d().toPoint().x;
			double dy = me.getPosition3d().toPoint().y;
			double dz = me.getPosition3d().toPoint().z;
			atom = new Atom3D();
			
			obj2 = new LMNTransformGroup();
			
			atom.createNode(obj2,new Double3DPoint(dx,dy,dz),me.getName());
			BranchGroup objB = new BranchGroup();
			objB.addChild(obj2);
			obj.addChild(objB);
			
			me.setObj(obj2);
			
			Edge3D ie[] = new Edge3D[me.getEdgeCount()];

			for(int j=0;j<ie.length;j++) {
				ie[j]=new Edge3D(me, me.getNthNode3d(j));
			}
			
			obj2.setEdgeNum(me.getEdgeCount());
			obj2.setEdge(ie);
			/*TODO:setMeは必要？*/
			obj2.setMe(me);
			System.out.println("ID:"+me.getName()+",EdgeNum="+obj2.getEdgeNum());
			
			
			for (int j=0;j<ie.length;j++) {
				System.out.println("length"+ie.length);
				Edge3D edge = ie[j];
				if(me.getEdgeCount()<=1) continue;
				Node3D you = ie[j].to;
				
				/*冗長（二重）にリンクを張るのを防止*/
				if(me.getid() < you.getid()) continue;

				/*リンクの描画*/
				Shape3D shape=makeLink(me, you);
				BranchGroup obj3=new BranchGroup();
				ie[j].setShape(shape);
				obj3.addChild(shape);
				ie[j].setObj(obj3);
				obj.addChild(obj3);
				
				/*Edgeのリスト作成*/
				if(ie_tmp!=null)ie_tmp.setNext(ie[j]);
				else firstEdge.setNext(ie[j]);
				ie_tmp = ie[j];
			}
		}
	}
	protected void edgerelax(runtime.AbstractMembrane m,Node3D me) {
		LMNtal3DFrame a;

		if(m==null) return;
		
		
		for (Edge3D j=firstEdge.getNext();j.getNext()!=null;j=j.getNext()) {

			if(me.getEdgeCount()<1) break;

			Shape3D shape=j.getShape();
    		if(j.from==me || j.to ==me){
    			if(shape==null)System.out.println("nullP");
    			if(shape==null)continue;
    			Point3d[] vertex = new Point3d[2];
    			vertex[0] = new Point3d(j.from.getPosition3d().x,j.from.getPosition3d().y,j.from.getPosition3d().z);
    			vertex[1] = new Point3d(j.to.getPosition3d().x,j.to.getPosition3d().y,j.to.getPosition3d().z);
    			//System.out.println(vertex[0].toString());
    			//System.out.println(vertex[1].toString());
    			geometry = new LineArray(vertex.length, 
    					GeometryArray.COORDINATES | GeometryArray.COLOR_3);
    			geometry.setCoordinates(0, vertex);
    			geometry.setColor(0, new Color3f(Color.magenta));
    			geometry.setColor(1, new Color3f(Color.cyan));
    			shape.setGeometry(geometry);
    			//System.out.println("geometry reset");
    		}
			
		}

	}
	protected void relax(runtime.AbstractMembrane m) {
		LMNtal3DFrame a;
		//System.out.println("relax");
		if(m==null) return;
		//System.out.println("relax m ok");
		
		for (Iterator i=m.atomIterator();i.hasNext();) {

			Node3D me = (Node3D)i.next();
			
			Node3D ne[] = new Node3D[me.getEdgeCount()];

			/*比較対照のノードを取得*/
			for(int j=0;j<ne.length;j++) {
				ne[j]=me.getNthNode3d(j);
			}			
			
			for (int j=0;j<ne.length;j++) {
				double dx=me.getPosition3d().x - ne[j].getPosition3d().x;
				double dy=me.getPosition3d().y - ne[j].getPosition3d().y;
				double dz=me.getPosition3d().z - ne[j].getPosition3d().z;
				
				/*指定以下の距離ならば引き離す*/
				if((dx*dx)+(dy*dy)+(dz*dz)<EDGELENGTH_MIN*EDGELENGTH_MIN){
					double mex = me.getPosition3d().x;
					double mey = me.getPosition3d().y;
					double mez = me.getPosition3d().z;
					double youx = ne[j].getPosition3d().x;
					double youy = ne[j].getPosition3d().y;
					double youz = ne[j].getPosition3d().z;
					if(dx < dy && dx < dz){
						if(abs(mex)<abs(youx)){
							mex -= DELTAEDGE;
						}else{ 
							mex += DELTAEDGE;
						}
					}
					else if(dy < dx && dy < dz){
						if(abs(mey) < abs(youy)){
							mey -= DELTAEDGE;
						}
						else{
							mey += DELTAEDGE;
						}
					}
					else{
						if(abs(mez) < abs(youz)){
							mez -= DELTAEDGE;
						}
						else{
							mez += DELTAEDGE;
						}
					}
	
					Transform3D translation=new Transform3D();

					translation.setTranslation(new Vector3d(mex,mey,mez));
					me.getObj().setTransform(translation);
					me.setPosition3d(new Double3DPoint(mex,mey,mez));
					//translation.setTranslation(new Vector3d(youx,youy,youz));
					//ne[j].getObj().setTransform(translation);
					//ne[j].setPosition3d(new Double3DPoint(youx,youy,youz));
					
				}/*指定以上なら引き付ける*/
				else if((dx*dx)+(dy*dy)+(dz*dz)>EDGELENGTH_MAX*EDGELENGTH_MAX){
					double mex = me.getPosition3d().x;
					double mey = me.getPosition3d().y;
					double mez = me.getPosition3d().z;
					double youx = ne[j].getPosition3d().x;
					double youy = ne[j].getPosition3d().y;
					double youz = ne[j].getPosition3d().z;
					if(dx > dy && dx > dz){
						if(abs(mex) < abs(youx)){
							mex += DELTAEDGE;
						}
						else{ 
							mex -= DELTAEDGE;
						}
					}
					else if(dy > dx && dy > dz){
						if(abs(mey) < abs(youy)){
							mey += DELTAEDGE;
						}
						else{
							mey -= DELTAEDGE;
						}
					}
					else{
						if(abs(mez) < abs(youz)){
							mez += DELTAEDGE;
						}
						else{
							mez -= DELTAEDGE;
						}
					}
	
					Transform3D translation=new Transform3D();
					translation.setTranslation(new Vector3d(mex,mey,mez));
					me.getObj().setTransform(translation);
					me.setPosition3d(new Double3DPoint(mex,mey,mez));
					//translation.setTranslation(new Vector3d(youx,youy,youz));
					//ne[j].getObj().setTransform(translation);
					//ne[j].setPosition3d(new Double3DPoint(youx,youy,youz));
				}
				else continue;
			}
			edgerelax(me);
		}
	}
}
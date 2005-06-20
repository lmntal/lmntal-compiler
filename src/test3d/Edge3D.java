package test3d;

import javax.media.j3d.*;


class Edge3D{
	public Node3D from, to;
	private BranchGroup obj;//自分がぶら下がっているBG
	private Shape3D shape;//自分自身
	
	private Edge3D next=null;
	
	public void setShape(Shape3D s){
		shape=s;
	}
	public Shape3D getShape(){
		return shape;
	}
	public void setObj(BranchGroup o){
		obj=o;
	}
	public BranchGroup getObj(){
		return obj;
	}
	public void setNext(Edge3D i){
		next=i;
	}
	public Edge3D getNext(){
		return next;
	}
	public Edge3D (Node3D f,Node3D t){
		from=f;
		to=t;
	}
	public Edge3D (){
	}

	/**
	 * 絶対角を計算して返す。(rad)
	 * 東が 0 、時計回りに増える
	 * 
	 * @return
	 */
	public double getAngle() {
		double x = getVx();
		double y = getVy();
		double z = getVz();
		
		if(x==0.0) x=0.000000001;
		double a = Math.atan(y/x);
		if(x<0.0) a += Math.PI;
		
		a = regulate(a);
//		System.out.println(a);
		return a;
	}
	static double regulate(double a) {
		while(a<0.0) a+= Math.PI*2;
		while(a>=2*Math.PI) a-= Math.PI*2;
		return a;
	}
	
	// ソートするのにつかう
	public int compareTo(Object o) {
		Edge3D ie = (Edge3D)o;
		return ie.getAngle() < this.getAngle() ? 1 : -1;
	}
	
	public double getVx() {
		return to.getPosition3d().x - from.getPosition3d().x;
	}

	public double getVy() {
		return to.getPosition3d().y - from.getPosition3d().y;
	}

	public double getVz() {
		return to.getPosition3d().z - from.getPosition3d().z;
	}
	
	public double getStdLen() {
		return runtime.Env.fDEMO ? 100.0 : 50.0;
	}
	
	public double getLen() {
		double x = getVx();
		double y = getVy();
		x = Math.sqrt(x*x+y*y);
		return x==0.0 ? 0.00001 : x;
	}
	
	public String toString() {
		return "from "+from+" to "+to+" angle= "+getAngle()*180/Math.PI;
	}
}
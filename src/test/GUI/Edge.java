package test.GUI;

/**
 * 有向グラフの辺。
 * 
 * @author 
 */
class Edge implements Comparable {
	Node from, to;
	
	Edge(Node f, Node t) {
		from = f;
		to = t;
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
		
		if(x==0.0) x=0.000000001;
		double a = Math.atan(y/x);
		if(x<0.0) a += Math.PI;
		
		a = GraphLayout.regulate(a);
//		System.out.println(a);
		return a;
	}
	
	// ソートするのにつかう
	public int compareTo(Object o) {
		Edge ie = (Edge)o;
		return ie.getAngle() < this.getAngle() ? 1 : -1;
	}
	
	public double getVx() {
		return to.getPosition().x - from.getPosition().x;
	}
	
	public double getVy() {
		return to.getPosition().y - from.getPosition().y;
	}
	
	public double getStdLen() {
		return 50.0;
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

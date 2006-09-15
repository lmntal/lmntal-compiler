package gui;

/**
 * 有向グラフの辺。
 * 
 * @author 
 */
public class Edge implements Comparable {
	public Node from, to;
	
	/** 使用する関数の設定 */
	public Edge(Node f, Node t) {
		from = f;
		to = t;
	}
	
	/**
	 * 絶対角を計算して返す。(rad)
	 * 東が 0 、時計回りに増える
	 * 
	 * @return a
	 */
	public double getAngle() {
		double x = getVx();
		double y = getVy();
		
		if(x==0.0) x=0.000000001;
		double a = Math.atan(y/x);
		if(x<0.0) a += Math.PI;
		
		a = GraphLayout.regulate(a);
		return a;
	}
	
	/** ソートするのにつかう */
	public int compareTo(Object o) {
		Edge ie = (Edge)o;
		return ie.getAngle() < this.getAngle() ? 1 : -1;
	}
	
	/** toとfromのx座標を取得する */
	public double getVx() {
		return to.getPosition().x - from.getPosition().x;
	}

	/** toとfromのy座標を取得する */
	public double getVy() {
		return to.getPosition().y - from.getPosition().y;
	}
	
	/** アトムサイズの数字を変更 */
	public double getStdLen() {
		return runtime.Env.fDEMO ? 100.0 : 50.0;
	}
	
	/** 始点と終点の距離を取得する */
	public double getLen() {
		double x = getVx();
		double y = getVy();
		x = Math.sqrt(x*x+y*y);
		return x==0.0 ? 0.00001 : x;
	}
	
	/** 出力する文字を作成 */
	public String toString() {
		return "from "+from+" to "+to+" angle= "+getAngle()*180/Math.PI;
	}
}

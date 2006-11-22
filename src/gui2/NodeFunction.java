package gui2;

import java.awt.geom.Point2D;

import runtime.Atom;

/**
 * アトムノード，膜ノードに関する操作
 * <p>
 * すべてのメソッドはstaticで宣言される
 * @author Nakano
 *
 */
public class NodeFunction {

	///////////////////////////////////////////////////////////////////////////
	/* ばね定数 */
	final static
	private double CONSTANT_SPRING = 0.01;
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * ばねモデルの計算
	 *
	 */
	static
	public void calcSpring(Node node){
		if(!Atom.class.isInstance(node.getObject())){
			return;
		} else {
			calcSpring_Atom(node);
		}
	}

	/**
	 * ばねモデルの計算(Atom用)
	 *
	 */
	static
	private void calcSpring_Atom(Node node){
		Atom atom = (Atom)node.getObject();
		// 表示されているNodeを取得する
		Node sourceNode = LinkSet.getNode(atom);
		Point2D myPoint = sourceNode.getCenterPoint();
		for(int i = 0; i < atom.getEdgeCount() ; i++){
			Atom nthAtom = atom.getNthAtom(i);
			// 表示されているNodeを取得する
			Node nthNode = LinkSet.getNode(nthAtom);
			Point2D nthPoint = nthNode.getCenterPoint();
			if(null == nthNode ||
					null == nthPoint ||
					sourceNode == nthNode)
			{ 
				continue; 
			}

			double distance =
		    Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());
			
			double f = -CONSTANT_SPRING * ((distance / ( 80 * 2)) - 1.0);

			double dx = myPoint.getX() - nthPoint.getX();
			double dy = myPoint.getY() - nthPoint.getY();

			double ddx = f * dx;
			double ddy = f * dy;
			node.moveDelta(ddx, ddy);
			nthNode.moveDelta(-ddx, -ddy);
			
		}
	}
	

	/**
	 * ばねモデルの計算(Membrane用)
	 *
	 */
	static
	private void calcSpring_Membrane(){
//		Membrane mem = (Membrane)myObject;
//		Point2D myPoint = getCenterPoint();
//		for(int i = 0; i < atom.getEdgeCount() ; i++){
//			Point2D nthPoint = LinkSet.getNodePoint(atom.getNthAtom(i));
//			if(null == nthPoint){ continue; }
//			
//			double distance =
//		    Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());
//			
//			double f = -CONSTANT_SPRING * (distance - 1.0);
//			
//			moveDelta((myPoint.getX() - nthPoint.getX()) * f, (myPoint.getY() - nthPoint.getY()) * f);
//			
//		}
	}
}

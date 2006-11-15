package gui2;

import java.awt.geom.Point2D;

import runtime.Atom;

/**
 * アトムノード，膜ノードに関する操作
 * @author Nakano
 *
 */
public class NodeFunction {

	///////////////////////////////////////////////////////////////////////////
	/* ばね定数 */
	final static
	private double CONSTANT_SPRING = 0.0001;
	
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
//			calcSpring_Atom(node);
		}
	}

	/**
	 * ばねモデルの計算(Atom用)
	 *
	 */
	static
	private void calcSpring_Atom(Node node){
		Atom atom = (Atom)node.getObject();
		Point2D myPoint = node.getCenterPoint();
		for(int i = 0; i < atom.getEdgeCount() ; i++){
			Point2D nthPoint = LinkSet.getNodePoint(atom.getNthAtom(i));
			if(null == nthPoint){ continue; }
			
			double distance =
		    Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());
			
			double f = -CONSTANT_SPRING * (distance - 1.0);
			
			node.moveDelta((myPoint.getX() - nthPoint.getX()) * f, (myPoint.getY() - nthPoint.getY()) * f);
			
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

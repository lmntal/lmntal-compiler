package gui2;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Map;

import runtime.Atom;
import runtime.Membrane;

/**
 * アトムノード，膜ノードに関する操作
 * <p>
 * すべてのメソッドはstaticで宣言される
 * @author Nakano
 *
 */
public class NodeFunction {

	///////////////////////////////////////////////////////////////////////////
	/** ばね定数 */
	final static
	private double CONSTANT_SPRING = 0.01;

	/** 引力定数 */
	final static
	private double CONSTANT_ATTRACTION = 0.00001;
	
	/** 斥力定数 */
	final static
	private double CONSTANT_REPULSIVE = 0.00001;
	
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * 引力の計算
	 * <p>
	 * 膜の中心とNodeが弱いバネでつながれていると考える．
	 * <BR>
	 * <font color="red">このメソッドを呼び出すときはsynchronized (nodeMap_) を行うこと。</font>
	 * @param node
	 * @param nodeMap
	 */
	static
	public void calcAttraction(Node node, Map nodeMap){
		if(!(node.getObject() instanceof Membrane) ||
				null != node.getInvisibleRootNode())
		{
			return;
		}
		
		Point2D myPoint = node.getCenterPoint();

		Iterator<Node> nodes = nodeMap.values().iterator();
		while(nodes.hasNext()){
			// 表示されているNodeを取得する
			Node targetNode = nodes.next();
			Point2D nthPoint = targetNode.getCenterPoint();

			double distance =
				Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());

			double f = -CONSTANT_ATTRACTION * distance;

			double dx = myPoint.getX() - nthPoint.getX();
			double dy = myPoint.getY() - nthPoint.getY();

			double ddx = f * dx;
			double ddy = f * dy;
			targetNode.moveDelta(-ddx, -ddy);
		}
	}
	
	/**
	 * 膜内部のNode間の斥力の計算
	 * <P>
	 * <font color="red">このメソッドを呼び出すときはsynchronized (nodeMap_) を行うこと。</font>
	 * @param node
	 * @param nodeMap
	 */
	static
	public void calcRepulsive(Node node, Map nodeMap){
		if(!Membrane.class.isInstance(node.getObject())){
			return;
		}

		Iterator<Node> nodes = nodeMap.values().iterator();
		while(nodes.hasNext()){
			Node sourceNode = nodes.next();
			Point2D sourcePoint = sourceNode.getCenterPoint();
			Rectangle2D sourceRect = sourceNode.getBounds2D();
			
			Iterator<Node> targetNodes = nodeMap.values().iterator();
			while(targetNodes.hasNext()){
				// 表示されているNodeを取得する
				Node targetNode = targetNodes.next();
				// 引力は働かせない
				if(sourceNode == targetNode ||
						!sourceRect.intersects(targetNode.getBounds2D()))
				{
					continue; 
				}

				Point2D targetPoint = targetNode.getCenterPoint();
				double distance =
					Point2D.distance(sourcePoint.getX(), sourcePoint.getY(), targetPoint.getX(), targetPoint.getY());

				double f = CONSTANT_REPULSIVE * distance;
				
				
				double dx = sourcePoint.getX() - targetPoint.getX();
				double dy = sourcePoint.getY() - targetPoint.getY();
				
				double ddx = f * dx;
				double ddy = f * dy;
				sourceNode.moveDelta(ddx, ddy);
				targetNode.moveDelta(-ddx, -ddy);
			}
		}
	}
	
	/**
	 * ばねモデルの計算
	 * @param node
	 * @param nodeMap
	 *
	 */
	static
	public void calcSpring(Node node){
		if(!Atom.class.isInstance(node.getObject())){
			return;
		}
		
		Atom atom = (Atom)node.getObject();
		// 表示されているNodeを取得する
		Node sourceNode = LinkSet.getNode(atom);
		if(null == sourceNode){ return; }
		Point2D myPoint = sourceNode.getCenterPoint();
		for(int i = 0; i < atom.getEdgeCount() ; i++){
			Atom nthAtom = atom.getNthAtom(i);
			// 表示されているNodeを取得する
			Node nthNode = LinkSet.getNode(nthAtom);
			if(null == nthNode){ continue; }
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
}

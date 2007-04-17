package gui.model.forces;

import gui.model.Node;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import runtime.Membrane;

public class AttractionForce {

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
	public void calcAttraction(Node node, Map<Object, Node> nodeMap){
		if(NodeFunction.heatingTime_ != 0 ||
				!attractionFlag_ ||
				!(node.getObject() instanceof Membrane) ||
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

	static 
	public void setAttractionFlag(boolean attractionFlag) {
		attractionFlag_ = attractionFlag;
	}

	/** 引力定数 */
	final static double CONSTANT_ATTRACTION = 0.000001;
	static boolean attractionFlag_ = false;

}

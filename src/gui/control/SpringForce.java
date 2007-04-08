package gui.control;

import gui.model.LinkSet;
import gui.model.Node;

import java.awt.geom.Point2D;

import runtime.Atom;

public class SpringForce {

	/** ばね定数 */
	static double constantSpring_ = -0.02;
	static boolean springFlag_ = true;
	/**
	 * ばねモデルの計算
	 * @param node
	 * @param nodeMap
	 *
	 */
	static
	public void calcSpring(Node node){
		if(!springFlag_ || !Atom.class.isInstance(node.getObject())){
			return;
		}
	
		// 表示されているNodeを取得する
		Node sourceNode = LinkSet.getVisibleNode(node);
		if(null == sourceNode){ return; }
		Point2D myPoint = sourceNode.getCenterPoint();
		for(int i = 0; i < node.getEdgeCount() ; i++){
			Node realNthNode = node.getNthNode(i);
			// 表示されているNodeを取得する
			Node nthNode = LinkSet.getVisibleNode(realNthNode);
			if(null == nthNode){ continue; }
			Point2D nthPoint = nthNode.getCenterPoint();
			if(null == nthNode ||
					null == nthPoint ||
					sourceNode == nthNode)
			{ 
				continue; 
			}
	
			int memNum = NodeFunction.countMembraneNum(sourceNode, nthNode);
			double distance =
				Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());
	
			double divergenceFource = (NodeFunction.heatingTime_ == 0) ? 1 : (Math.random() * 10) - (Math.random() * 5);
			double linkLength = 20 * (1.0 + (Math.pow((double)sourceNode.getEdgeCount() + (double)nthNode.getEdgeCount(), 2) / 10));
			double f = constantSpring_ * divergenceFource * ((distance / (linkLength + (60 * (memNum + 1)))) - 1.0);
			double dx = myPoint.getX() - nthPoint.getX();
			double dy = myPoint.getY() - nthPoint.getY();
	
			Node comNode = NodeFunction.getCommonParent(node, nthNode);
	
			double ddx = f * dx;
			double ddy = f * dy;
	
			{
				if(comNode == node.getParent()){
					node.moveDelta(ddx, ddy);
				} else {
					node.moveInside(ddx, ddy);
				}
			}
			{
				if(comNode == nthNode.getParent()){
					nthNode.moveDelta(-ddx, -ddy);
				} else {
					nthNode.moveInside(-ddx, -ddy);
				}
			}
		}
	}
	static 
	public void setSpringFlag(boolean springFlag) {
		springFlag_ = springFlag;
	}
	static
	public void setConstantSpring(double value){
		constantSpring_ = -(value / 1000);
	}

}

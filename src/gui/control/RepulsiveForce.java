package gui.control;

import gui.model.Node;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Map;

import runtime.Membrane;

public class RepulsiveForce {

	static 
	public void setRepulsiveFlag(boolean repulsiveFlag) {
		repulsiveFlag_ = repulsiveFlag;
	}

	/**
		 * 膜内部のNode間の斥力の計算
		 * <P>
		 * <font color="red">このメソッドを呼び出すときはsynchronized (nodeMap_) を行うこと。</font>
		 * @param node
		 * @param nodeMap
		 */
		static
		public void calcRepulsive(Node node, Map<Object, Node> nodeMap){
			if(!repulsiveFlag_ ||
					!(node.getObject() instanceof Membrane) ||
					null != node.getInvisibleRootNode())
			{
				return;
			}
	
			Iterator<Node> nodes = nodeMap.values().iterator();
			while(nodes.hasNext()){
				Node sourceNode = nodes.next();
				Point2D sourcePoint = sourceNode.getCenterPoint();
	
				Iterator<Node> targetNodes = nodeMap.values().iterator();
				while(targetNodes.hasNext()){
					// 表示されているNodeを取得する
					Node targetNode = targetNodes.next();
					// 引力は働かせない
					if(sourceNode == targetNode)
					{
						continue; 
					}
					
					Rectangle2D intersectionRect = 
						sourceNode.getBounds2D().createIntersection(targetNode.getBounds2D());
					double divergenceFource = CONSTANT_REPULSIVE;
	//				if(divergenceTimer_ != 0){
	//					divergenceFource = divergenceFource * 300;
	//				}
					if(intersectionRect.isEmpty() ||
							1 > intersectionRect.getWidth() ||
							1 >  intersectionRect.getHeight())
					{
						continue;
					}
	
					Point2D targetPoint = targetNode.getCenterPoint();
					
					
					double fx = divergenceFource * intersectionRect.getWidth();
					double dx = sourcePoint.getX() - targetPoint.getX();
					double fy = divergenceFource * intersectionRect.getHeight();
					double dy = sourcePoint.getY() - targetPoint.getY();
	
					boolean calcX = (((sourceNode.getBounds2D().getWidth() / 2) - Math.abs(dx)) < ((sourceNode.getBounds2D().getHeight() / 2) - Math.abs(dy)));
					
					double ddx = (calcX) ? fx * dx : 0;
					double ddy = (!calcX) ? fy * dy : 0;
	
					if(!sourceNode.isAtom() && !targetNode.isAtom()){
						sourceNode.moveDelta(ddx, ddy);
						targetNode.moveDelta(-ddx, -ddy);
					} else {
						if(sourceNode.isAtom()){
							sourceNode.moveDelta(ddx, ddy);
						}
						if(targetNode.isAtom()){
							targetNode.moveDelta(-ddx, -ddy);
						}
					}
	//				Point2D targetPoint = targetNode.getCenterPoint();
	//
	////				double size = Math.max(sourceNode.getSize() , targetNode.getSize());
	//				// TODO: Node　の大きさ
	//				double distance = 
	//					Point2D.distance(sourcePoint.getX(), sourcePoint.getY(), targetPoint.getX(), targetPoint.getY()) / 80;
	////					Point2D.distance(sourcePoint.getX(), sourcePoint.getY(), targetPoint.getX(), targetPoint.getY()) / size;
	//
	//				double divergenceFource = (divergenceTimer_ == 0) ? 1 : 2;
	//				if(distance > 1 * divergenceFource){
	//					continue; 
	//				}
	//				
	//				double distance2 = distance * distance;
	//				double f = CONSTANT_REPULSIVE * divergenceFource *(
	//						(1.25 * distance2 * distance) -
	//						(2.375 * distance2) +
	//						1.125);
	//				
	//				double dx = sourcePoint.getX() - targetPoint.getX();
	//				double dy = sourcePoint.getY() - targetPoint.getY();
	//				
	//				double ddx = f * dx;
	//				double ddy = f * dy;
	//				sourceNode.moveDelta(ddx, ddy);
	//				targetNode.moveDelta(-ddx, -ddy);
				}
			}
		}

	static boolean repulsiveFlag_ = true;
	/** 斥力定数 */
	final static double CONSTANT_REPULSIVE = 0.0005;

}

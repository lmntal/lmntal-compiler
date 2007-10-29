package gui.model.forces;

import gui.model.Node;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.awt.geom.Rectangle2D;
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
	
	static double constantAttraction_ = 0.00001;

	
	static
	public void groupNode(Node node,Map<Object, Node> nodeMap,
		Map<Set<Node>, Rectangle2D.Double> nodeGroupMap)
	{
		Iterator<Node> nodes = nodeMap.values().iterator();
		
		nodesWhile:
		while(nodes.hasNext()){
			Node targetNode = nodes.next();
			Iterator<Set<Node>> nodeSets = nodeGroupMap.keySet().iterator();
			while(nodeSets.hasNext()){
				Set<Node> nodeSet = nodeSets.next();
				if(nodeSet.contains(targetNode)){
					continue nodesWhile;
				}
			}
			
			Set<Node> nodeSet = new HashSet<Node>();
			addNthNode(nodeSet, targetNode);
			Rectangle2D.Double rect = createRect(nodeSet);
			nodeGroupMap.put(nodeSet, rect);
		}

	}
	
	
	static
	public Rectangle2D.Double createRect(Set<Node> nodeSet){
		double maxX = Double.MIN_VALUE;
		double minX = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;

		if(NodeFunction.heatingTime_ != 0)
		{
			return(null);
		}
		
		Iterator<Node> nodes = nodeSet.iterator();
		while(nodes.hasNext()){
			Node node = nodes.next();
			Rectangle2D targetRect = node.getBounds2D();
			if(maxX < targetRect.getMaxX()) maxX = targetRect.getMaxX();
			if(targetRect.getMinX() < minX) minX = targetRect.getMinX();
			if(maxY < targetRect.getMaxY()) maxY = targetRect.getMaxY();
			if(targetRect.getMinY() < minY) minY = targetRect.getMinY();
		}
		
		return (new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY));
		
	}
	
	static
	public void updateRect(Map<Set<Node>, Rectangle2D.Double> nodeGroupMap){
		Iterator<Set<Node>> nodeSets = nodeGroupMap.keySet().iterator();
		while(nodeSets.hasNext()){
			Set<Node> nodeSet = nodeSets.next();
			Rectangle2D.Double rect = nodeGroupMap.get(nodeSet);
			double maxX = Double.MIN_VALUE;
			double minX = Double.MAX_VALUE;
			double maxY = Double.MIN_VALUE;
			double minY = Double.MAX_VALUE;

			Iterator<Node> nodes = nodeSet.iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				Rectangle2D targetRect = node.getBounds2D();
				if(maxX < targetRect.getMaxX()) maxX = targetRect.getMaxX();
				if(targetRect.getMinX() < minX) minX = targetRect.getMinX();
				if(maxY < targetRect.getMaxY()) maxY = targetRect.getMaxY();
				if(targetRect.getMinY() < minY) minY = targetRect.getMinY();
			}
			rect.setRect(minX, minY, maxX - minX, maxY - minY);
		}
		
		
	}

	private static void addNthNode(Set<Node> nodeSet, Node node){
		if(nodeSet.contains(node)){
			return;
		}
		nodeSet.add(node);
		for(int i = 0; i < node.getEdgeCount(); i++){
			addNthNode(nodeSet, node.getNthNode(i));
		}
	}
	
	static
	public void calcAttraction(Node node, Map<Object, Node> nodeMap,
			Map<Set<Node>, Rectangle2D.Double> nodeGroupMap){
		if(NodeFunction.heatingTime_ != 0 ||
				!attractionFlag_ ||
				!(node.getObject() instanceof Membrane) ||
				null != node.getInvisibleRootNode())
		{
			return;
		}

		Iterator<Set<Node>> nodeSets = nodeGroupMap.keySet().iterator();
		nodeSets:
		while(nodeSets.hasNext()){
			Set<Node> nodeSet = nodeSets.next();
			Rectangle2D.Double rect = nodeGroupMap.get(nodeSet);
			if(rect == null){
				continue;
			}
			double centerX = rect.getCenterX();
			double centerY = rect.getCenterY();
			Point2D myPoint = node.getCenterPoint();
			if(node.isRoot()){
				myPoint = new Point2D.Double(0,0);
			}
			
			double distance =
				Point2D.distance(myPoint.getX(), myPoint.getY(), centerX, centerY);
	
			double f = -constantAttraction_ * distance;
	
			double dx = myPoint.getX() - centerX;
			double dy = myPoint.getY() - centerY;
	
			double ddx = f * dx;
			double ddy = f * dy;
			Iterator<Node> nodes = nodeSet.iterator();
			while(nodes.hasNext()){
				Node targetNode = nodes.next();
				if(targetNode.isUncalc() || targetNode.isClipped()){
					continue nodeSets;
				}

			}
			
			nodes = nodeSet.iterator();
			while(nodes.hasNext()){
				Node targetNode = nodes.next();
				targetNode.moveDelta(-ddx, -ddy);
			}
			
			
		}
		

	}

	static 
	public void setAttractionFlag(boolean attractionFlag) {
		attractionFlag_ = attractionFlag;
	}
	
	static
	public void setConstantAttraction(double value){
		constantAttraction_ = value / 100000000;
	}

	static boolean attractionFlag_ = false;

}

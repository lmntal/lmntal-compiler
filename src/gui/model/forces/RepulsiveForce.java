package gui.model.forces;

import gui.model.Node;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
	public void calcRepulsive(Node node, Map<String, Set<Node>> areaNodeMap){
		if(!repulsiveFlag_ ||
				!(node.getObject() instanceof Membrane) ||
				null != node.getInvisibleRootNode())
		{
			return;
		}

		Set<PairNode> calcedNodeSet = new HashSet<PairNode>();
		Iterator<Set<Node>> nodeSets = areaNodeMap.values().iterator();
		while(nodeSets.hasNext()){
			Set<Node> nodeSet = nodeSets.next();
			Iterator<Node> nodes = nodeSet.iterator();
			while(nodes.hasNext()){
				Node sourceNode = nodes.next();
				Point2D sourcePoint = sourceNode.getCenterPoint();

				Iterator<Node> targetNodes = nodeSet.iterator();
				targetNodes:
					while(targetNodes.hasNext()){
						// 表示されているNodeを取得する
						Node targetNode = targetNodes.next();
						if(sourceNode.getID() == targetNode.getID()){
							continue;
						}
						PairNode pn = new PairNode(sourceNode, targetNode);
						Iterator<PairNode> pairNodes = calcedNodeSet.iterator();
						while (pairNodes.hasNext()) {
							PairNode pairNode = (PairNode) pairNodes.next();
							if(pairNode.equals(pn)){
								continue targetNodes;
							}

						}
						calcedNodeSet.add(pn);

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

						double fx = intersectionRect.getWidth();
						double dx = (sourcePoint.getX() - targetPoint.getX() < 0) ? -1 : 1;
						double fy = intersectionRect.getHeight();
						double dy = (sourcePoint.getY() - targetPoint.getY() < 0) ? -1 : 1;

//						boolean calcX = (((intersectionRect.getBounds2D().getWidth() / 2) - Math.abs(dx)) < ((intersectionRect.getBounds2D().getHeight() / 2) - Math.abs(dy)));
						boolean calcX = (intersectionRect.getBounds2D().getWidth() < intersectionRect.getBounds2D().getHeight());

						double ddx = (calcX) ? fx * dx: 0;
						double ddy = (!calcX) ? fy * dy: 0;

//						System.out.println(sourceNode.getID());
//						System.out.println(targetNode.getID());
//						System.out.println("------------");
//						System.out.println("rep" +ddx + "," + ddy);

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
					}
			}
		}
	}

	static boolean repulsiveFlag_ = true;
	/** 斥力定数 */
	final static double CONSTANT_REPULSIVE = 0.0005;

	final static double ALFA_DIST = 5;

	static
	private class PairNode{
		private Node nodeA;
		private Node nodeB;

		public PairNode(Node a, Node b){
			nodeA = a;
			nodeB = b;
		}

		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof PairNode)){
				return false;
			}
			PairNode pn = (PairNode) obj;
			if((nodeA.getID() == pn.nodeA.getID() && nodeB.getID() == pn.nodeB.getID()) ||
					(nodeA.getID() == pn.nodeB.getID() && nodeB.getID() == pn.nodeA.getID()))
			{
				return true;
			}
			return false;
		}
	}

}

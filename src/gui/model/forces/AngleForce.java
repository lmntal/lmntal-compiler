package gui.model.forces;

import gui.model.LinkSet;
import gui.model.Node;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.TreeMap;

import runtime.Atom;

public class AngleForce {

	/** 角度調整力定数 */
	static double constantAngle_ = 3;
	static boolean angleFlag_ = true;
	static 
	public void setAngleFlag(boolean angleFlag) {
		angleFlag_ = angleFlag;
	}
	
	static
	public void setConstantAngle(double value){
		AngleForce.constantAngle_ = value / 10;
	}

	static
		public void calcRelaxAngle(Node node){
			if(!AngleForce.angleFlag_ || !(node.getObject() instanceof Atom)){
				return;
			}
			int edgeNum = node.getEdgeCount(); 
			
			if(edgeNum < 2){ return; }
	
			Node sourceNode = LinkSet.getVisibleNode(node);
			if(null == sourceNode){ return; }
			Point2D myPoint = sourceNode.getCenterPoint();
			Map<Double, Node> treeMap = new TreeMap<Double, Node>();
			
			for(int i = 0; i < edgeNum; i++){
				Node nthNode = LinkSet.getVisibleNode(node.getNthNode(i));
				if(null == nthNode){ continue; }
				Point2D nthPoint = nthNode.getCenterPoint();
	
				if(sourceNode == nthNode){ 
					continue; 
				}
				
				double dx = nthPoint.getX() - myPoint.getX();
				double dy = nthPoint.getY() - myPoint.getY();
	
				if(dx == 0.0){ dx=0.000000001; }
				double angle = Math.atan(dy / dx);
				if(dx < 0.0) angle += Math.PI;
				treeMap.put(angle, nthNode);
			}
			
			Object[] nthAngles = treeMap.keySet().toArray();
			for(int i = 0; i < nthAngles.length; i++ ){
				Double nthAngle = (Double)nthAngles[i];
				Node nthNode = treeMap.get(nthAngle);
				Point2D nthPoint = nthNode.getCenterPoint();
	
				
				if(null != nthNode){
					double anglePre = (i != 0) ? ((Double)nthAngles[i]).doubleValue() - ((Double)nthAngles[i - 1]).doubleValue() 
							: (Math.PI * 2) - ((Double)nthAngles[nthAngles.length - 1]).doubleValue() + ((Double)nthAngles[0]).doubleValue();
					double angleCur = (i != nthAngles.length - 1) ? ((Double)nthAngles[i + 1]).doubleValue() - ((Double)nthAngles[i]).doubleValue() 
							: (Math.PI * 2) - ((Double)nthAngles[nthAngles.length - 1]).doubleValue() + ((Double)nthAngles[0]).doubleValue();
					double angleR = angleCur - anglePre;
					double dx = nthPoint.getX() - myPoint.getX();
					double dy = nthPoint.getY() - myPoint.getY();
					double edgeLength = Math.sqrt((dx * dx) + (dy * dy));
					if(edgeLength == 0.0){ edgeLength = 0.00001; }
	
					double tx = -dy / edgeLength;
					double ty =  dx / edgeLength;
					
//					dx = AngleForce.constantAngle_ * tx * angleR;
//					dy = AngleForce.constantAngle_ * ty * angleR;
					dx = tx * angleR;
					dy = ty * angleR;
					
//					dx = dx * 2;
//					dy = dy * 2;
					sourceNode.moveDelta(-dx, -dy);
					nthNode.moveDelta(dx, dy);
					
				}
			}
	//		if(!angleFlag_ || !(node.getObject() instanceof Atom)){
	//			return;
	//		}
	//		int edgeNum = node.getEdgeCount(); 
	//		
	//		if(edgeNum < 2){ return; }
	//
	//		Node sourceNode = LinkSet.getVisibleNode(node);
	//		if(null == sourceNode){ return; }
	//		Point2D myPoint = sourceNode.getCenterPoint();
	//		// すべの隣のNodeを含んだソート済みマップ
	//		Map<Double, Node> sortedNodeMap = new TreeMap<Double, Node>();
	//		// リンクを二本以上保持している隣のNodeのソート済みマップ
	//		Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();
	//		
	//		// つながっているアトムを走査
	//		for(int i = 0; i < edgeNum; i++){
	//			Node nthNode = LinkSet.getVisibleNode(node.getNthNode(i));
	//			if(null == nthNode){ continue; }
	//			Point2D nthPoint = nthNode.getCenterPoint();
	//			if(null == nthNode ||
	//					null == nthPoint ||
	//					sourceNode == nthNode ||
	//					sortedNodeMap.containsValue(nthNode))
	//			{
	//				continue; 
	//			}
	//			
	//			double dx = nthPoint.getX() - myPoint.getX();
	//			double dy = nthPoint.getY() - myPoint.getY();
	//
	//			if(dx == 0.0){ dx=0.000000001; }
	//			double angle = Math.atan(dy / dx);
	//			if(dx < 0.0) angle += Math.PI;
	//			sortedNodeMap.put(angle, nthNode);
	//		}
	//
	//		int j = 0;
	//		int maxJ = 0;
	//		Object[] allNthNodes = sortedNodeMap.values().toArray();
	//		for(int i = 0; i < allNthNodes.length; i++ ){
	//			if(1 < ((Node)allNthNodes[i]).getEdgeCount()){
	//				idMap.put(j, i);
	//				maxJ = j;
	//				j++;
	//			}
	//		}
	//		Object[] allNthAngles = sortedNodeMap.keySet().toArray();
	//		
	//		j = 0;
	//		for(int i = 0; i < allNthAngles.length; i++ ){
	//			Double nthAngle = (Double)allNthAngles[i];
	//			Node nthNode = sortedNodeMap.get(nthAngle);
	//			Point2D nthPoint = nthNode.getCenterPoint();
	//
	//			double anglePre;
	//			double angleCur;
	//			if(1 < nthNode.getEdgeCount()){
	//				anglePre = (j != 0) ? ((Double)allNthAngles[idMap.get(j)]).doubleValue() - ((Double)allNthAngles[idMap.get(j - 1)]).doubleValue() 
	//						: (Math.PI * 2) - ((Double)allNthAngles[idMap.get(maxJ)]).doubleValue() + ((Double)allNthAngles[0]).doubleValue();
	//				angleCur = (j != maxJ) ? ((Double)allNthAngles[idMap.get(j + 1)]).doubleValue() - ((Double)allNthAngles[idMap.get(j)]).doubleValue() 
	//						: (Math.PI * 2) - ((Double)allNthAngles[idMap.get(maxJ)]).doubleValue() + ((Double)allNthAngles[0]).doubleValue();
	//				j++;
	//			} else {
	//				anglePre = (i != 0) ? ((Double)allNthAngles[i]).doubleValue() - ((Double)allNthAngles[i - 1]).doubleValue() 
	//						: (Math.PI * 2) - ((Double)allNthAngles[allNthAngles.length - 1]).doubleValue() + ((Double)allNthAngles[0]).doubleValue();
	//				angleCur = (i != allNthAngles.length - 1) ? ((Double)allNthAngles[i + 1]).doubleValue() - ((Double)allNthAngles[i]).doubleValue() 
	//						: (Math.PI * 2) - ((Double)allNthAngles[allNthAngles.length - 1]).doubleValue() + ((Double)allNthAngles[0]).doubleValue();
	//			}
	//			double angleR = angleCur - anglePre;
	//			double dx = nthPoint.getX() - myPoint.getX();
	//			double dy = nthPoint.getY() - myPoint.getY();
	//			double edgeLength = Math.sqrt(dx * dx + dy * dy);
	//			if(edgeLength == 0.0){ edgeLength = 0.00001; }
	//			
	//			//線分に垂直で長さ１のベクトル
	//			double tx = -dy / edgeLength;
	//			double ty =  dx / edgeLength;
	//
	//			dx = constantAngle_ * tx * angleR;
	//			dy = constantAngle_ * ty * angleR;
	//
	//			sourceNode.moveDelta(-dx, -dy);
	//			nthNode.moveDelta(dx, dy);
	//		}
		}

}

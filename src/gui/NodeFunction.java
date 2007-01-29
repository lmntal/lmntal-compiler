package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
	// final static
	
	/** ばね定数 */
	final static
	private double CONSTANT_SPRING = 0.02;

	/** 引力定数 */
	final static
	private double CONSTANT_ATTRACTION = 0.00001;
	
	/** 斥力定数 */
	final static
	private double CONSTANT_REPULSIVE = 0.0005;
	
	/** 発散定数 */
	final static
	private double CONSTANT_HEATING = 0.01;
	
	/** 角度調整力定数 */
	final static
	private double CONSTANT_ANGLE = 1.5;
	
	/** 発散時間 */
	final static
	private int HEATING_TIMER = 100;
	
	/** 局所発散と全体発散の力の差 */
	final static
	private int LOCAL_GLOBAL_RATE = 10;
	
	/** 局所発散時間 */
	final static
	private int LOCAL_HEATING_TIMER = 4;
	
	///////////////////////////////////////////////////////////////////////////
	// static
	
	static
	private boolean attractionFlag_ = false;
	
	static
	private int heatingTimer_ = 0;
	
	static
	private Map<Node, Integer> localHeating_ = new HashMap<Node, Integer>(); 

	static
	private boolean repulsiveFlag_ = true;
	
	static
	private boolean springFlag_ = true;
	
	static
	private boolean angleFlag_ = true;
	
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
	public void calcAttraction(Node node, Map<Object, Node> nodeMap){
		if(heatingTimer_ != 0 ||
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
	
	
	/**
	 * 発散の計算
	 * <P>
	 * 巻くの中心とNodeが強めのバネでつながれていると考える
	 * <BR>
	 * <font color="red">このメソッドを呼び出すときはsynchronized (nodeMap_) を行うこと。</font>
	 * @param node
	 * @param nodeMap
	 */
	static
	public void calcHeat(Node node, Map<Object, Node> nodeMap){
		if(localHeating_.isEmpty() &&
				(heatingTimer_ == 0 ||
				!(node.getObject() instanceof Membrane) ||
				null != node.getInvisibleRootNode()))
		{	
			return;
		}
		if(null == node.getParent() && 0 < heatingTimer_){
			heatingTimer_--;
		}
		
		Point2D myPoint = node.getCenterPoint();
		Iterator<Node> nodes = nodeMap.values().iterator();
		while(nodes.hasNext()){
			// 表示されているNodeを取得する
			Node targetNode = nodes.next();
			int heatParam = heatingTimer_;
			boolean contain = localHeating_.containsKey(targetNode); 
			if(0 == heatingTimer_ &&
					!localHeating_.isEmpty() &&
					!contain)
			{
				continue;
			} else if(contain){
				int count = localHeating_.get(targetNode);
				count--;
				if(0 < count){
					localHeating_.put(targetNode, count);
					targetNode.setHeating(true);
				} else {
					targetNode.setHeating(false);
					localHeating_.remove(targetNode);
					continue;
				}
				heatParam = count * LOCAL_GLOBAL_RATE;
			}
			Point2D nthPoint = targetNode.getCenterPoint();

			double f = -CONSTANT_HEATING;

			double dx = myPoint.getX() - nthPoint.getX();
			double dy = myPoint.getY() - nthPoint.getY();

			double dxr = (dx > 0) ? -(Math.random() * heatParam) + (heatParam / 2) : (Math.random() * heatParam) -  (heatParam / 2);
			double dyr = (dy > 0) ? -(Math.random() * heatParam) + (heatParam / 2) : (Math.random() * heatParam) -  (heatParam / 2);
			
			double ddx = (f * dx) + dxr;
			double ddy = (f * dy) + dyr;
			
			targetNode.moveDelta(ddx, ddy);
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
				if(sourceNode.getID() == targetNode.getID())
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
				double fy = divergenceFource * intersectionRect.getHeight();
				double dx = sourcePoint.getX() - targetPoint.getX();
				double dy = sourcePoint.getY() - targetPoint.getY();

				double ddx = fx * dx;
				double ddy = fy * dy;

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
	
	static
	public void calcRelaxAngle(Node node){
		if(!angleFlag_ || !(node.getObject() instanceof Atom)){
			return;
		}
		int edgeNum = node.getEdgeCount(); 
		
		if(edgeNum < 2){ return; }

		Node sourceNode = LinkSet.getVisibleNode(node);
		if(null == sourceNode){ return; }
		Point2D myPoint = sourceNode.getCenterPoint();
		Map<Double, Node> treeMap = new TreeMap<Double, Node>();
		
		// つながっているアトムを走査
		for(int i = 0; i < edgeNum; i++){
			Node nthNode = LinkSet.getVisibleNode(node.getNthNode(i));
			if(null == nthNode){ continue; }
			Point2D nthPoint = nthNode.getCenterPoint();

			if(null == nthNode ||
					null == nthPoint ||
					sourceNode == nthNode)
			{ 
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
				double edgeLength = Math.sqrt(dx * dx + dy * dy);
				if(edgeLength == 0.0){ edgeLength = 0.00001; }
				//線分に垂直で長さ１のベクトル
				double tx = -dy / edgeLength;
				double ty =  dx / edgeLength;
				
				dx = CONSTANT_ANGLE * tx * angleR;
				dy = CONSTANT_ANGLE * ty * angleR;
				
				dx = dx * 2;
				dy = dy * 2;
				sourceNode.moveDelta(-dx, -dy);
				nthNode.moveDelta(dx, dy);
				
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

			int memNum = countMembraneNum(sourceNode, nthNode);
			double distance =
				Point2D.distance(myPoint.getX(), myPoint.getY(), nthPoint.getX(), nthPoint.getY());

			double divergenceFource = (heatingTimer_ == 0) ? 1 : (Math.random() * 10) - (Math.random() * 5);
			
			double f = -CONSTANT_SPRING * divergenceFource * ((distance / (20 + (60 * (memNum + 1)))) - 1.0);
			double dx = myPoint.getX() - nthPoint.getX();
			double dy = myPoint.getY() - nthPoint.getY();

			Node comNode = getCommonParent(node, nthNode);
			
			double ddx = f * dx;
			double ddy = f * dy;

//			node.moveDelta(ddx, ddy);
//			nthNode.moveDelta(-ddx, -ddy);
//			ddx = ddx * 0.9;
//			ddy = ddy * 0.9;
			/*
			Node targetNode = node;
			while(targetNode.getParent() != comNode){
				targetNode = targetNode.getParent();
			}
			targetNode.moveDelta(ddx, ddy);
			
			targetNode = nthNode;
			while(targetNode.getParent() != comNode){
				targetNode = targetNode.getParent();
			}
			targetNode.moveDelta(-ddx, -ddy);
			*/
			double dxMargin = (ddx < 0) ? -Node.MARGIN : Node.MARGIN;
			double dyMargin = (ddy < 0) ? -Node.MARGIN : Node.MARGIN;
			{
				Node targetNode = node;
				while(targetNode.getParent() != comNode){
					targetNode = targetNode.getParent();
				}
				Rectangle2D rect = node.getBounds2D();
				rect.setRect(rect.getX() + ddx + dxMargin, rect.getY() + ddy + dyMargin, rect.getWidth(), rect.getHeight());
				if(!node.isUncalc() && !nthNode.isUncalc() && targetNode.getBounds2D().contains(rect)){
					node.moveDelta(ddx, ddy);
				} else {
					targetNode.moveDelta(ddx, ddy);
				}
			}
			{
				Node targetNode = nthNode;
				while(targetNode.getParent() != comNode){
					targetNode = targetNode.getParent();
				}
				Rectangle2D rect = nthNode.getBounds2D();
				rect.setRect(rect.getX() - ddx - dxMargin, rect.getY() - ddy + dyMargin, rect.getWidth(), rect.getHeight());
				if(!node.isUncalc() && !nthNode.isUncalc() && targetNode.getBounds2D().contains(rect)){
					nthNode.moveDelta(-ddx, -ddy);
				} else {
					targetNode.moveDelta(-ddx, -ddy);
				}
			}
		}
	}
	
	/**
	 * node1とnode2の間に階層の数を数える
	 * @param node1
	 * @param node2
	 * @return
	 */
	static
	private int countMembraneNum(Node node1, Node node2){
		if(node1.getParent() == node2.getParent()){
			return 1;
		}
		Node node = node1;
		Map<Integer, Integer> memMap = new HashMap<Integer, Integer>();
		int counter = 1;
		while(null != node.getParent()){
			node = node.getParent();
			memMap.put(node.getID(), counter);
			counter++;
		}
		node = node2;
		int counter2 = 0;
		while(null != node.getParent()){
			node = node.getParent();
			if(memMap.containsKey(node.getID())){
				return (memMap.get(node.getID()) + counter2);
			}
			counter2++;
		}
		return (counter + counter2);
	}
	
	static
	public int getDivergence(){
		return heatingTimer_;
	}
	
	/**
	 * 二つのNodeの共通Nodeを取得する
	 * @param node1
	 * @param node2
	 * @return
	 */
	static
	public Node getCommonParent(Node node1, Node node2){
		if(node1.getParent() == node2.getParent()){
			return node1.getParent();
		}
		Node node = node1;
		Map<Integer, Node> memMap = new HashMap<Integer, Node>();
		while(null != node.getParent()){
			node = node.getParent();
			memMap.put(node.getID(), node);
		}
		node = node2;
		while(null != node.getParent()){
			node = node.getParent();
			if(memMap.containsKey(node.getID())){
				return memMap.get(node.getID());
			}
		}
		
		return null;
	}
	
	static 
	public void setAttractionFlag(boolean attractionFlag) {
		attractionFlag_ = attractionFlag;
	}
	
	static
	public void setHeatup(){
		heatingTimer_ += HEATING_TIMER;
	}
	
	static
	public void setLocalHeating(Node node){
		if(localHeating_.containsKey(node)){
			localHeating_.put(node, localHeating_.get(node) + LOCAL_HEATING_TIMER);
		} else {
			localHeating_.put(node, LOCAL_HEATING_TIMER);
		}
	}
	
	static 
	public void setRepulsiveFlag(boolean repulsiveFlag) {
		repulsiveFlag_ = repulsiveFlag;
	}
	
	static 
	public void setSpringFlag(boolean springFlag) {
		springFlag_ = springFlag;
	}
	
	static 
	public void setAngleFlag(boolean angleFlag) {
		angleFlag_ = angleFlag;
	}
	
	static
	public void showNodeMenu(Node node, GraphPanel panel){
		if(node.getEdgeCount() == 0){ return; }
		Rectangle2D nodeRectangle = node.getBounds2D(); 
		JPopupMenu popup = new JPopupMenu();
		for(int i = 0; i < node.getEdgeCount(); i++){
			JMenuItem item = new JMenuItem("Link(" + i + "):Bezier on/off");
			item.addActionListener(new BezierLinkListener(node, node.getNthNode(i)));
			popup.add(item);
		}
		popup.show(panel, 
				(int)((nodeRectangle.getMaxX() * GraphPanel.getMagnification()) + (panel.getWidth() / 2)),
				(int)((nodeRectangle.getMaxY() * GraphPanel.getMagnification()) + (panel.getHeight() / 2)));
	}
	
	static
	public void stopHeating(){
		heatingTimer_ = 0;
		localHeating_.clear();
	}

	static
	private class BezierLinkListener implements ActionListener {
		private Node sourceNode_;
		private Node targetNode_;
		
		public BezierLinkListener(Node sourceNode, Node targetNode){
			sourceNode_ = sourceNode;
			targetNode_ = targetNode;
		}

		public void actionPerformed(ActionEvent e){
			sourceNode_.addBezier(targetNode_);
		}
	}
}

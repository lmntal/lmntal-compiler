package gui.model.forces;

import gui.GraphPanel;
import gui.model.Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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

	/** 発散定数 */
	final static
	private double CONSTANT_HEATING = 0.01;
	
	/** 発散時間 */
	final static
	private int HEATING_TIMER = 100;
	
	/** 局所発散と全体発散の力の差 */
	final static
	private int LOCAL_GLOBAL_RATE = 1;
	
	/** 局所発散時間 */
	final static
	private int LOCAL_HEATING_TIMER = 10;
	
	///////////////////////////////////////////////////////////////////////////
	// static
	
	static int heatingTime_ = 0;
	
	static
	private Map<Node, Integer> localHeating_ = new HashMap<Node, Integer>(); 

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
				(heatingTime_ == 0 ||
				!(node.getObject() instanceof Membrane) ||
				null != node.getInvisibleRootNode()))
		{	
			return;
		}
		if(null == node.getParent() && 0 < heatingTime_){
			heatingTime_--;
		}
		
		Point2D myPoint = node.getCenterPoint();
		Iterator<Node> nodes = nodeMap.values().iterator();
		while(nodes.hasNext()){
			// 表示されているNodeを取得する
			Node targetNode = nodes.next();
			int heatParam = heatingTime_;
			boolean contain = localHeating_.containsKey(targetNode);
			if(0 == heatingTime_ &&
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

			double dxr = 
				(dx > 0) ? -(Math.random() * heatParam)  
						: (Math.random() * heatParam);
			double dyr = 
				(dy > 0) ? -(Math.random() * heatParam) 
						: (Math.random() * heatParam) ;
			
			double ddx = (f * dx) + dxr;
			double ddy = (f * dy) + dyr;
			
			targetNode.moveDelta(ddx, ddy);
		}
	}
	
	/**
	 * node1とnode2の間に階層の数を数える
	 * @param node1
	 * @param node2
	 * @return
	 */
	static int countMembraneNum(Node node1, Node node2){
		if(node1.getParent() == node2.getParent()){
			return 1;
		}
		Node node = node1;
		Map<Node, Integer> memMap = new HashMap<Node, Integer>();
		int counter = 1;
		while(null != node.getParent()){
			node = node.getParent();
			memMap.put(node, counter);
			counter++;
		}
		node = node2;
		int counter2 = 0;
		while(null != node.getParent()){
			node = node.getParent();
			if(memMap.containsKey(node)){
				return (memMap.get(node) + counter2);
			}
			counter2++;
		}
		return (counter + counter2);
	}
	
	static
	public int getHeatingTime(){
		return heatingTime_;
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
		Set<Node> memSet = new HashSet<Node>();
		while(null != node.getParent()){
			node = node.getParent();
			memSet.add(node);
		}
		node = node2;
		while(null != node.getParent()){
			node = node.getParent();
			if(memSet.contains(node)){
				return node;
			}
		}
		
		return null;
	}
	
	static
	public void setHeatup(){
		heatingTime_ += HEATING_TIMER;
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
				(int)((nodeRectangle.getMaxX() * GraphPanel.getMagnification()) + ((double)panel.getWidth() / 2)),
				(int)((nodeRectangle.getMaxY() * GraphPanel.getMagnification()) + ((double)panel.getHeight() / 2)));
	}
	
	static
	public void stopHeating(){
		heatingTime_ = 0;
		Iterator<Node> heatingNodes = localHeating_.keySet().iterator();
		while(heatingNodes.hasNext()){
			heatingNodes.next().setHeating(false);
		}
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

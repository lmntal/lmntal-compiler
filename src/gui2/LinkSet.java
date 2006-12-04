package gui2;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Atom;

/**
 * UNYO-UNYOのリンクを管理するクラス
 * <P>
 * すべてのメソッドはstaticで宣言される
 * @author nakano
 *
 */
public class LinkSet {
	
	///////////////////////////////////////////////////////////////////////////

	final static
	private double LINK_NUM_DELTA = 30.0;
	
	///////////////////////////////////////////////////////////////////////////

	static
	private Set<Node> linkSet_ = new HashSet<Node>();
	
	static
	private boolean showLinkNum_ = false;
	
	///////////////////////////////////////////////////////////////////////////

	static
	private void addAllNode(Node node){
		linkSet_.add(node);
		Iterator<Node> nodes = node.getChildMap().values().iterator();
		while(nodes.hasNext()){
			addAllNode(nodes.next());
		}
	}
	
	/**
	 * リンクを持っているアトムを追加する
	 */
	static
	public void addLink(Node node){
		synchronized (linkSet_) {
			linkSet_.add(node);
		}
	}
	
	/**
	 * AtomまたはMembraneから可視Nodeの座標を取得する
	 * @param key
	 * @return
	 */
	static
	public Point2D.Double getNodePoint(Node keyNode){
		Node node = getVisibleNode(keyNode);
		return (null != node) ? node.getCenterPoint() : null;
	}

	
	/**
	 * 可視Nodeを取得する
	 * @param node
	 * @return
	 */
	static
	public Node getVisibleNode(Node node){
		if(null == node ||
				null == node.getParent() ||
				null == node.getParent().getInvisibleRootNode())
		{
			return node;
		}
		else {
			return getVisibleNode(node.getParent());
		}
	}
	
	/**
	 * リンクを描画する
	 * @param g
	 */
	static
	public void paint(Graphics g){
		synchronized (linkSet_) {
			Iterator<Node> keys = linkSet_.iterator();
			while(keys.hasNext()){
				Node key = keys.next();
				
				// keyがAtomの場合
				if(key.getObject() instanceof Atom){
					Node nodeSource = getVisibleNode(key);
					if(null == nodeSource){ return; }
					Rectangle2D rectSource = nodeSource.getBounds2D();
					for(int n = 0; n < nodeSource.getEdgeCount(); n++){
						Node nthNode = nodeSource.getNthNode(n);
						if(null == nthNode){ return; }
						if(showLinkNum_ || nodeSource.getID() <= nthNode.getID()){
							Node nodeTarget = getVisibleNode(nthNode);
							if((null == nodeTarget) ||
									(
											(null != nodeSource.getInvisibleRootNode()) &&
											(
													nodeSource != nodeTarget &&
													nodeSource.getInvisibleRootNode().equals(nodeTarget.getInvisibleRootNode())
											)
									)
							)
							{
								continue;
							}
							Rectangle2D rectTarget = nodeTarget.getBounds2D();

							if(nodeSource.getID() < nthNode.getID()){
								g.drawLine((int)rectSource.getCenterX(),
										(int)rectSource.getCenterY(),
										(int)rectTarget.getCenterX(),
										(int)rectTarget.getCenterY());
							}
							else if(nodeSource.getID() == nthNode.getID() && nodeSource == key){
								g.drawOval((int)rectSource.getCenterX(),
										(int)rectSource.getY(),
										50,
										50);
								continue;
							}
							
							if(showLinkNum_){
								double x0 = rectSource.getCenterX() - rectTarget.getCenterX();
								double y0 = rectSource.getCenterY() - rectTarget.getCenterY();
								if(x0 == 0.0){ x0=0.000000001; }
								double angle = Math.atan(y0 / x0);
								if(x0 < 0.0){ angle += Math.PI; }
								double x = Math.cos(angle) * LINK_NUM_DELTA;
								double y = Math.sin(angle) * LINK_NUM_DELTA;
								g.drawString(Integer.toString(n), (int)(rectSource.getCenterX() - x), (int)(rectSource.getCenterY() - y));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * アトムを除去する
	 */
	static
	public void removeLink(Node node){
		synchronized (linkSet_) {
			linkSet_.remove(node);
		}
	}
	
	static
	public void resetNodes(Node node){
		synchronized (linkSet_) {
			linkSet_.clear();
			addAllNode(node);
		}
	}
	
	
	static
	public void setShowLinkNum(boolean flag){
		showLinkNum_ = flag;
	}
	
	///////////////////////////////////////////////////////////////////////////
	private class NodeSet{
		private Node nodeA_;
		private Node nodeB_;
		
		public NodeSet(Node nodeA, Node nodeB){
			nodeA_ = nodeA;
			nodeB_ = nodeB;
		}
		
		public boolean equals(Object object){
			if(!(object instanceof NodeSet)){
				return false;
			}
			if(nodeA_.equals(((NodeSet)object).getA())){
				if(nodeB_.equals(((NodeSet)object).getB())){
					return true;
				}
			}
			if(nodeA_.equals(((NodeSet)object).getB())){
				if(nodeB_.equals(((NodeSet)object).getA())){
					return true;
				}
			}
			return false;
		}
		
		public Node getA(){
			return nodeA_;
		}
		public Node getB(){
			return nodeB_;
		}
		
	}
}

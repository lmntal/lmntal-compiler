package gui2;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	private Map<Object, Node> linkMap_ = new HashMap<Object, Node>();
	
	static
	private boolean showLinkNum_ = false;
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * リンクを持っているアトムを追加する
	 */
	static
	public void addLink(Object key, Node node){
		synchronized (linkMap_) {
			linkMap_.put(key, node);
		}
	}
	
	/**
	 * AtomまたはMembraneから可視Nodeを取得する
	 * <P>
	 * 可視Nodeは，描画時に見えるNodeのこと
	 * @param key
	 * @return
	 */
	static
	public Node getNode(Object key){
		return getVisibleNode((Node)linkMap_.get(key));
	}
	
	/**
	 * AtomまたはMembraneから可視Nodeの座標を取得する
	 * @param key
	 * @return
	 */
	static
	public Point2D.Double getNodePoint(Object key){
		Node node = getVisibleNode(linkMap_.get(key));
		return (null != node) ? node.getCenterPoint() : null;
	}

	
	/**
	 * 可視Nodeを取得する
	 * @param node
	 * @return
	 */
	static
	private Node getVisibleNode(Node node){
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
		synchronized (linkMap_) {
			Iterator keys = linkMap_.keySet().iterator();
			while(keys.hasNext()){
				Object key = keys.next();
				
				// keyがAtomの場合
				if(Atom.class.isInstance(key)){
					Atom atom = (Atom)key;
					Node nodeSource = getVisibleNode(linkMap_.get(atom));
					Rectangle2D rectSource = nodeSource.getBounds2D();

					for(int n = 0; n < atom.getEdgeCount(); n++){
						Atom nthAtom = atom.getNthAtom(n);
						if(null == nthAtom){ continue; }
						if(showLinkNum_ || atom.getid() <= nthAtom.getid()){
							Node nodeTarget = getVisibleNode(linkMap_.get(nthAtom));
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

							if(atom.getid() < nthAtom.getid()){
								g.drawLine((int)rectSource.getCenterX(),
										(int)rectSource.getCenterY(),
										(int)rectTarget.getCenterX(),
										(int)rectTarget.getCenterY());
							}
							else if(atom.getid() == nthAtom.getid() && nodeSource == linkMap_.get(atom)){
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
	public void removeLink(Object key){
		synchronized (linkMap_) {
			linkMap_.remove(key);
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

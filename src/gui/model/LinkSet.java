package gui.model;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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

	static
	private Set<Node> linkSet_ = new HashSet<Node>();
	
	static
	private Map<Object, Node> linkMap_ = new HashMap<Object, Node>();
	
	///////////////////////////////////////////////////////////////////////////

	static
	public Set<Node> getLinkSet(){
		return linkSet_;
	}
	
	static
	public Iterator<Node> getLinkNodes(){
		return linkSet_.iterator();
	}
	
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
		synchronized (linkMap_) {
			linkMap_.put(node.getObject(), node);
		}
	}
	
	static
	public Node getNodeByAtom(Atom atom){
		synchronized (linkMap_) {
			return linkMap_.get(atom);
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
	 * アトムを除去する
	 */
	static
	public void removeLink(Node node){
		synchronized (linkSet_) {
			linkSet_.remove(node);
			linkMap_.remove(node.getObject());
		}
	}
	
	static
	public void resetNodes(Node node){
		synchronized (linkSet_) {
			linkSet_.clear();
			addAllNode(node);
		}
	}
	
}

package gui;

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
	
	///////////////////////////////////////////////////////////////////////////

	final static
	private double LINK_NUM_DELTA = 30.0;
	
	final static
	private double DIPLO_LINK_DELTA = 50;
	
	///////////////////////////////////////////////////////////////////////////

	static
	private Set<Node> linkSet_ = new HashSet<Node>();
	
	static
	private Map<Object, Node> linkMap_ = new HashMap<Object, Node>();
	
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
	 * リンクを描画する
	 * @param g
	 */
	static
	public void paint(Graphics g){
		synchronized (linkSet_) {
			Iterator<Node> keys = linkSet_.iterator();
			while(keys.hasNext()){
				Node key = keys.next();
				
				// keyがAtomでない場合
				if(!(key.getObject() instanceof Atom)){ continue; }
				Node nodeSource = getVisibleNode(key);
				if(null == nodeSource){ return; }
				Rectangle2D rectSource = nodeSource.getBounds2D();
				
				// TODO:グローバル変数化？
				Map<Integer, Integer> diplolinkMap = new HashMap<Integer, Integer>();
				int selfLinkNum = 0;
				for(int n = 0; n < key.getEdgeCount(); n++){
					Node nthNode = key.getNthNode(n);
					if(null == nthNode){ return; }
					if((null != key.getInvisibleRootNode()) &&
							(key != nthNode &&
									key.getInvisibleRootNode().equals(nthNode.getInvisibleRootNode())))
					{ continue; }
					
					if(showLinkNum_ || key.getID() <= nthNode.getID()){
						Node nodeTarget = getVisibleNode(nthNode);
						// リンク元もリンク先も描画範囲に入っていない場合は描画しない
						if(null == g ||
								null == g.getClipBounds() ||
								(!g.getClipBounds().intersects(nodeSource.getBounds2D()) &&
								!g.getClipBounds().intersects(nthNode.getBounds2D())))
						{
							continue;
						}
						
						if(null == nodeTarget) { continue; }
						Rectangle2D rectTarget = nodeTarget.getBounds2D();
						if(key.getID() < nthNode.getID()){
							paintLink(g, nodeSource, nthNode, diplolinkMap, nthNode.getID(), rectSource, rectTarget);
						}
						else if(nodeSource.getID() == nthNode.getID() && nodeSource == key){
							if(0 == selfLinkNum % 2){
								g.drawOval((int)rectSource.getCenterX(),
										(int)rectSource.getY() -  (5 * selfLinkNum / 2),
										50 + (10 * selfLinkNum),
										50 + (5 * selfLinkNum));
							}
							selfLinkNum++;
							continue;
						}

						if(showLinkNum_){
							Node sourceBezNode = nodeSource.getBezierNode(nthNode);
							Node nthBezNode = nthNode.getBezierNode(nodeSource);
							if(null != sourceBezNode){
								Rectangle2D rect = sourceBezNode.getBounds2D();
								paintLinkNum(g, n,
										rectSource.getCenterX(),
										rectSource.getCenterY(),
										rect.getCenterX(),
										rect.getCenterY());
							}
							else if(null == sourceBezNode && null != nthBezNode){
								Rectangle2D rect = nthBezNode.getBounds2D();
								paintLinkNum(g, n,
										rectSource.getCenterX(),
										rectSource.getCenterY(),
										rect.getCenterX(),
										rect.getCenterY());
							}
							else{
								paintLinkNum(g, n,
										rectSource.getCenterX(),
										rectSource.getCenterY(),
										rectTarget.getCenterX(),
										rectTarget.getCenterY());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 多重リンクを描画する
	 * @param g
	 * @param diplolinkMap リンク先のNodeIDをキーにした、リンク先へのリンク描画回数
	 * @param nthNodeID
	 * @param rectSource
	 * @param rectTarget
	 */
	static
	private void paintLink(Graphics g,
			Node nodeSource,
			Node nthNode,
			Map<Integer, Integer> diplolinkMap,
			int nthNodeID,
			Rectangle2D rectSource,
			Rectangle2D rectTarget)
	{
		Node sourceBezNode = nodeSource.getBezierNode(nthNode);
		Node nthBezNode = nthNode.getBezierNode(nodeSource);
		boolean sourceBez = (null == sourceBezNode) ? false : true;
		boolean nthBez = (null == nthBezNode) ? false : true;
		
		if(sourceBez || nthBez){
			if(sourceBez && !nthBez){
				((Graphics2D)g).draw(
						new QuadCurve2D.Double(
								rectSource.getCenterX(),
								rectSource.getCenterY(),
								sourceBezNode.getCenterPoint().x,
								sourceBezNode.getCenterPoint().y,
								rectTarget.getCenterX(),
								rectTarget.getCenterY()));
			}
			else if(!sourceBez && nthBez){
				((Graphics2D)g).draw(
						new QuadCurve2D.Double(
								rectSource.getCenterX(),
								rectSource.getCenterY(),
								nthBezNode.getCenterPoint().x,
								nthBezNode.getCenterPoint().y,
								rectTarget.getCenterX(),
								rectTarget.getCenterY()));
			} else {
				((Graphics2D)g).draw(
						new CubicCurve2D.Double(
								rectSource.getCenterX(),
								rectSource.getCenterY(),
								sourceBezNode.getCenterPoint().x,
								sourceBezNode.getCenterPoint().y,
								nthBezNode.getCenterPoint().x,
								nthBezNode.getCenterPoint().y,
								rectTarget.getCenterX(),
								rectTarget.getCenterY()));
			}
			return;
		}
		
		if(!diplolinkMap.containsKey(nthNodeID)){
			g.drawLine((int)rectSource.getCenterX(),
					(int)rectSource.getCenterY(),
					(int)rectTarget.getCenterX(),
					(int)rectTarget.getCenterY());
			diplolinkMap.put(nthNodeID, 1);
			return;
		}
		int linkNum = diplolinkMap.get(nthNodeID);
		diplolinkMap.put(nthNodeID, linkNum + 1);

		double linkDelta = DIPLO_LINK_DELTA * ((linkNum + 1) / 2);

		double x0 = rectSource.getCenterX() - rectTarget.getCenterX();
		double y0 = rectSource.getCenterY() - rectTarget.getCenterY();
		if(x0 == 0.0){ x0=0.000000001; }
		double angle = Math.atan(y0 / x0);
		if(x0 < 0.0){ angle += Math.PI; }
		angle = (linkNum % 2 == 0) ? angle + (Math.PI / 2) : angle - (Math.PI / 2);

		double x = Math.cos(angle) * linkDelta;
		double y = Math.sin(angle) * linkDelta;

		((Graphics2D)g).draw(
				new QuadCurve2D.Double(
						rectSource.getCenterX(),
						rectSource.getCenterY(),
						((rectSource.getCenterX() + rectTarget.getCenterX()) / 2) + x,
						((rectSource.getCenterY() + rectTarget.getCenterY()) / 2) + y,
						rectTarget.getCenterX(),
						rectTarget.getCenterY()));
		
	}
	
	/**
	 * リンク番号を描画する
	 * @param g
	 * @param linkNum
	 * @param rectSource
	 * @param rectTarget
	 */
	static
	private void paintLinkNum(Graphics g,
			int linkNum,
			double sourceX,
			double sourceY,
			double targetX,
			double targetY)
	{
		double x0 = sourceX - targetX;
		double y0 = sourceY - targetY;
		if(x0 == 0.0){ x0=0.000000001; }
		double angle = Math.atan(y0 / x0);
		if(x0 < 0.0){ angle += Math.PI; }
		double x = Math.cos(angle) * LINK_NUM_DELTA;
		double y = Math.sin(angle) * LINK_NUM_DELTA;
		g.drawString(Integer.toString(linkNum), (int)(sourceX - x), (int)(sourceY - y));
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
	
	
	static
	public void setShowLinkNum(boolean flag){
		showLinkNum_ = flag;
	}
	
}

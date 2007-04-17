package gui.model;

import gui.GraphPanel;
import gui.model.forces.AngleForce;
import gui.model.forces.AttractionForce;
import gui.model.forces.NodeFunction;
import gui.model.forces.RepulsiveForce;
import gui.model.forces.SpringForce;
import gui.view.CommonView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import runtime.Atom;
import runtime.InterpretedRuleset;
import runtime.Membrane;
import runtime.Ruleset;

public class Node implements Cloneable{

	///////////////////////////////////////////////////////////////////////////
	// final static
	
	final static
	public double ATOM_SIZE = 40.0;
	
	final static
	public double MARGIN = 15.0;

	final static
	private double MAX_MOVE_DELTA = 300.0;
	
	/** この値以下の場合は移動しない */
	final static
	private double MIN_MOVE_DELTA = 0.1;
	
	final static
	private double ROUND = 40;
	
	final static
	private Color RULE_COLOR = new Color(207,207,207);

	//////////////////////////////////////////////////////////////////////////
	// static
	static
	private int nextID_ = 0;
	
	static
	private GraphPanel panel_;
	
	static
	private boolean richMode_ = false;
	
	static
	private boolean showFullName_ = true;
	
	static
	private boolean showRules_ = false;
	
	static
	private boolean showAll_ = false;
	
	static
	private Set<Node> bezierSet_ = new HashSet<Node>(); 
	
	///////////////////////////////////////////////////////////////////////////
	// private

	/** ベジエ曲線かするリンク */
	private Map<Node, Node> bezierMap_ = new HashMap<Node, Node>();
	
	/** 継続非計算フラグ */
	private boolean clipped_ = false;
	
	/** 移動情報 */
	private double dx_;

	/** 移動情報 */
	private double dy_;
	
	private double insideDx_;
	
	private double insideDy_;
	
	private int fireID_ = 0;
	
	private boolean heating_ = false;
	
	/** アトムまたはアトム的（閉じた膜）であるか */
	private boolean imAtom_;
	
	public ArrayList<Node> linkList_ = new ArrayList<Node>();
	
	/** 自Nodeの色 */
	private Color myColor_ = Color.BLUE;
	
	/** 自NodeのID */
	private int myID_;
	
	/** 自Nodeのオブジェクト（Atom または Membrane）*/
	private Object myObject_;

	/** Node名 */
	private String name_ = "";
	
	/** 子Node */
	public Map<Object, Node> nodeMap_ = new HashMap<Object, Node>();
	
	/** ルールNode */
	public Map<String, Node> ruleNodeMap_ = new HashMap<String, Node>();
	
	/** 親膜 */
	private Node parent_;
	
	/** 取得可否 */
	private boolean pickable_ = true;
	
	/** ピンのアニメーション用変数 */
	public int pinAnime_ = 0;
	
	/** ピンのアニメーション用座標変数 */
	private double pinPosY_;
	
	/** 描画用の形 */
	private RoundRectangle2D.Double rect_ =
		new RoundRectangle2D.Double((Math.random() * 800) - 400,
			(Math.random() * 600) - 300,
			ATOM_SIZE,
			ATOM_SIZE,
			ROUND,
			ROUND);
	
	private boolean rootMembrane_ = false;
	
	/** 非計算フラグ */
	private boolean uncalc_ = false;

	private boolean uncalcOutsideFource_ = false;
	
	/** 可視フラグ */
	private boolean visible_;
	
	/** 一番ルートに近い不可視のNode */
	private Node invisibleRootNode_ = null;

	private boolean selected_ = false;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	
	public Node(){}
	
	public Node(Node parent, Object object){
		parent_ = parent;
		myObject_ = object;
		myID_ = nextID_;
		nextID_++;

		visible_ = true;
		// 膜、アトムの初期化
		if(object instanceof Atom){
			setAtom((Atom)object);
			initPosition();
		} else if(object instanceof Membrane) {
			setMembrane((Membrane)object);
		} else if(null == object){
			setBez();
			initPosition();
		}
		
		// 世界膜ならば可視それ以外は不可視
		if(null != parent_){
			setInvisibleRootNode(null);
			setVisible(showAll_, true);
			if(showAll_){
				rect_.setFrameFromCenter(rect_.getCenterX(),
						rect_.getCenterY(),
						rect_.getCenterX() - ATOM_SIZE,
						rect_.getCenterY() - ATOM_SIZE);
			}
//			resetAllLink();
		} else {
			rootMembrane_ = true;
		}
	}
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * GraphPanleをセットする
	 * @return
	 */
	static 
	public void setPanel(GraphPanel panel) {
		panel_ = panel;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public void addBezier(Node node){
		if(bezierMap_.containsKey(node)){
			bezierMap_.remove(node);
			bezierSet_.remove(node);
			return;
		}
		Node bezNode = new Node(this, null);
		bezierMap_.put(node, bezNode);
		bezierSet_.add(bezNode);
	}

	/**
	 * 位置座標などの計算
	 */
	public void calc(){
		AngleForce.calcRelaxAngle(this);
		if(clipped_ || uncalc_){ return; }
		SpringForce.calcSpring(this);
		synchronized (nodeMap_) {
			AttractionForce.calcAttraction(this, nodeMap_);
			RepulsiveForce.calcRepulsive(this, nodeMap_);
			NodeFunction.calcHeat(this, nodeMap_);
		}
//		moveCalc();
	}
	
	/**
	 * 位座標などの計算を自分を含めたすべての子Nodeにて行う
	 */
	public void calcAll(){
		calc();
		if(uncalc_){ return; }
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.calcAll();
			}
		}
	}
	
	public void calcMembraneSize(){
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double minX = Integer.MAX_VALUE;
		double minY = Integer.MAX_VALUE;
		boolean sizeChange = false;
		// 子膜をリセット
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				if(!showRules_ && node.getObject() instanceof String){
					continue;
				}
				sizeChange = true;

				Rectangle2D rectangle = node.getBounds2D();
				minX = (minX < rectangle.getMinX()) ? minX : rectangle.getMinX(); 
				maxX = (maxX > rectangle.getMaxX()) ? maxX : rectangle.getMaxX(); 
				minY = (minY < rectangle.getMinY()) ? minY : rectangle.getMinY(); 
				maxY = (maxY > rectangle.getMaxY()) ? maxY : rectangle.getMaxY(); 
			}
		}
		// サイズ変更
		if(visible_ && sizeChange){
			rect_.setFrameFromDiagonal(minX - MARGIN, minY - MARGIN, maxX + MARGIN, maxY + MARGIN);
		}
		else if(rect_.width != ATOM_SIZE || rect_.height != ATOM_SIZE){
			rect_.setFrameFromCenter(rect_.getCenterX(),
					rect_.getCenterY(),
					rect_.getCenterX() - ATOM_SIZE,
					rect_.getCenterY() - ATOM_SIZE);
		}

	}
	
	public Node cloneNode(Map<Node, Node> cloneMap){
		Node cloneNode = new Node();
		cloneMap.put(this, cloneNode);
		
		synchronized (nodeMap_) {
			Iterator keys = nodeMap_.keySet().iterator();
			while(keys.hasNext()){
				Object key = keys.next();
				Node oldNode = nodeMap_.get(key);
				oldNode.cloneNode(cloneMap);
			}
		}
		return cloneNode;
	}
	
	public Node cloneNodeParm(Map<Node, Node> cloneMap, Node orginNode){
			parent_ = cloneMap.get(orginNode.parent_);
			invisibleRootNode_ = cloneMap.get(orginNode.invisibleRootNode_);
			rootMembrane_ = orginNode.rootMembrane_;
			myObject_ = orginNode.myObject_;
			clipped_ = orginNode.clipped_;
			dx_ = orginNode.dx_;
			dy_ = orginNode.dy_;
			imAtom_ = orginNode.imAtom_;
			myColor_ = orginNode.myColor_;
			myID_ = orginNode.myID_;
			name_ = orginNode.name_;
			pickable_ = orginNode.pickable_;
			pinAnime_ = orginNode.pinAnime_;
			pinPosY_ = orginNode.pinPosY_;
			rect_ = orginNode.rect_;
			uncalc_ = orginNode.uncalc_;
			visible_ = orginNode.visible_;

			Map<Object, Node> cloneNodeMap = new HashMap<Object, Node>();
			ArrayList<Node> cloneLinkList = new ArrayList<Node>();

			synchronized (orginNode.nodeMap_) {
				Iterator keys = orginNode.nodeMap_.keySet().iterator();
				while(keys.hasNext()){
					Object key = keys.next();
					Node oldNode = orginNode.nodeMap_.get(key);
					Node newNode = cloneMap.get(oldNode);
					if(newNode == null){ continue; }
					newNode.cloneNodeParm(cloneMap, oldNode);
					cloneNodeMap.put(key, newNode);

				}
				nodeMap_ = cloneNodeMap;
			}

			Iterator<Node> linkNodes = orginNode.linkList_.iterator();
			while(linkNodes.hasNext()){
				Node oldNode  = linkNodes.next();
				Node newNode = cloneMap.get(oldNode);
//				cloneMap.put(oldNode, newNode);
				cloneLinkList.add(newNode);
			}

			linkList_ = cloneLinkList;
			return this;
	}
	
	/**
	 * 自Nodeおよび子Nodeを含む、最小の矩形を取得する
	 * @return
	 */
	public Rectangle getArea(){
		Rectangle rect = new Rectangle(rect_.getBounds());
		if(null != invisibleRootNode_){
			return rect;
		}
		Iterator<Node> nodes = nodeMap_.values().iterator();
		while(nodes.hasNext()){
			Node node = nodes.next();
			if(node == null){ continue; }
			rect.add(node.getArea());
		}
		
		return rect;
	}
	
	public Node getBezierNode(Node node){
		return bezierMap_.get(node);
	}

	/**
	 * 位置、座標を取得する
	 * @return
	 */
	public Rectangle2D getBounds2D(){
		return rect_.getBounds2D();
	}
	
	/**
	 * Nodeの中心点を取得する
	 * @return Nodeの中心点
	 */
	public Point2D.Double getCenterPoint(){
		return (new Point2D.Double(rect_.getCenterX(), rect_.getCenterY()));
	}
	
	/**
	 * 子NodeのIteratorを取得する
	 * @return
	 */
	public Map<Object, Node> getChildMap(){
		return nodeMap_;
	}
	
	public Iterator<Node> getChilds(){
		return nodeMap_.values().iterator();
	}
	
	public Color getColor(){
		return myColor_;
	}

	public int getEdgeCount(){
		return linkList_.size();
	}
	public int getNextFireID(){
		fireID_ = (fireID_ < CommonView.FIRE_ID_MAX) ? fireID_ + 1 : 0;
		return fireID_;
	}
	
	/**
	 * 非表示Nodeを取得する
	 * <p>
	 * 非表示Nodeは祖先Nodeのもっとも根に近い非表示のNode．
	 * @return 非表示のNode
	 */
	public Node getInvisibleRootNode(){
		return invisibleRootNode_;
	}
	
	public String getName(){
		return name_;
	}
	
	/**
	 * 隣のNodeを取得する
	 * @param i
	 * @return
	 */
	public Node getNthNode(int i){
		Node node = null;
		try {
			node = linkList_.get(i);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		return node;
	}
	
	/**
	 * 自オブジェクト（Atom,Membrane）を返す
	 * @return
	 */
	public Object getObject(){
		return myObject_;
	}
	
	/**
	 * Nodeの左上の座標を取得する
	 * @return Nodeの左上の座標
	 * @return
	 */
	public Point2D.Double getPoint(){
		return (new Point2D.Double(rect_.getX(), rect_.getY()));
	}

	public RoundRectangle2D getRoundRectangle2D(){
		return rect_;
	}
	
	static
	public boolean getShowFullName(){
		return showFullName_;
	}
	
	/**
	 * Node固有のIDを取得する
	 * @return
	 */
	public int getID(){
		return myID_;
	}
	
	/**
	 * 親Nodeを返す
	 * @return
	 */
	public Node getParent(){
		return parent_;
	}
	
	public int getPinAnimeCounter(){
		return pinAnime_;
	}
	
	public double getPinPosY(){
		return pinPosY_;
	}
	
	/**
	 * 指定された範囲にあるNodeを取得する
	 */
	public Node getPointNode(Rectangle2D rect, boolean force){
		// ベジエ曲線のチェック
//		if(null == myObject_){
		if(null == parent_){
			Iterator<Node> nodes = bezierSet_.iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				if(!node.getParent().isSelected()){ continue; }
				node = node.getPointNode(rect, false);
				if(null != node){ return node; }
			}
		}

		if(rect_.intersects(rect)){
			if(isPickable()){
				return this;
			} else{
				synchronized (nodeMap_) {
					Iterator<Node> nodes = nodeMap_.values().iterator();
					while(nodes.hasNext()){
						Node node = nodes.next();
						node = node.getPointNode(rect, force);
						if(null != node){ return node; }
					}
				}
			}
			return (force && !rootMembrane_ && null != myObject_) ? this : null;
		}
		return null;
	}
	
	public Set<Node> getPointNodes(Rectangle2D rect, boolean withMembrane){
		Set<Node> nodeSet = new HashSet<Node>();
		if(rect_.intersects(rect)){
			if(!rootMembrane_ &&
					isVisible() &&
					(myObject_ instanceof Atom ||
							(withMembrane &&
									myObject_ instanceof Membrane)))
			{
				nodeSet.add(this);
			}
			synchronized (nodeMap_) {
				Iterator<Node> nodes = nodeMap_.values().iterator();
				while(nodes.hasNext()){
					Node node = nodes.next();
					nodeSet.addAll(node.getPointNodes(rect, withMembrane));
				}
			}
		}
		return nodeSet;
	}
	
	public RoundRectangle2D getShape(){
		return rect_;
	}
	
	public double getSize(){
		if(null == myObject_ || myObject_ instanceof Atom){
			return rect_.getHeight();
		}
	
		return ((rect_.getHeight() + rect_.getWidth()) / 2 );
	}
	
	/**
	 * 初期位置をリンクされているアトムに極力近くする
	 *
	 */
	public void initPosition(){
		if(null == myObject_){
			rect_.x = parent_.getBounds2D().getCenterX() + 100;
			rect_.y = parent_.getBounds2D().getCenterY();
			return;
		}
		if(!(myObject_ instanceof Atom)){ return; }
		int nthNum = ((Atom)myObject_).getEdgeCount();
		if(0 == nthNum){ return; }
		if(1 == nthNum){
			Node nthNode = LinkSet.getNodeByAtom(((Atom)myObject_).getNthAtom(0));
			if(null == nthNode){ return; }
			Point2D nthPoint = nthNode.getCenterPoint();

			double x = nthPoint.getX();
			double y = nthPoint.getY();
			if(x == 0.0){ x=0.000000001; }
			double angle = Math.atan(y / x);
			
			double dx = (nthPoint.getX() > 0) ? (Math.cos(angle) * 100) : -(Math.cos(angle) * 100); 
			double dy = (nthPoint.getY() > 0) ? (Math.sin(angle) * 100) : -(Math.sin(angle) * 100); 
			
			rect_.x = nthPoint.getX() + dx + Math.random();
			rect_.y = nthPoint.getY() + dy + Math.random();
		} else {
			double x = 0;
			double y = 0;
			double findNthNum = 0;
			for(int i = 0; i < nthNum; i++){
				Node nthNode = LinkSet.getNodeByAtom(((Atom)myObject_).getNthAtom(i));
				if(null == nthNode){ continue; }
				Point2D nthPoint = nthNode.getPoint();
				x += nthPoint.getX();
				y += nthPoint.getY();
				findNthNum++;
			}
			if(1 == findNthNum){
				rect_.x = x;
				rect_.y = y;
			}
			else if(1 < findNthNum){
				rect_.x = (x / findNthNum) + 1;
				rect_.y = (y / findNthNum) + 1;
			}
			
		}
	}
	/*
	public void initPosition(){
		if(null == myObject_){
			rect_.x = parent_.getBounds2D().getCenterX() + 100;
			rect_.y = parent_.getBounds2D().getCenterY();
			return;
		}
		if(!(myObject_ instanceof Atom)){ return; }
		int nthNum = ((Atom)myObject_).getEdgeCount();
		if(0 == nthNum){ return; }
		if(1 == nthNum){
			Node nthNode = LinkSet.getNodeByAtom(((Atom)myObject_).getNthAtom(0));
			if(null == nthNode){ return; }
			Point2D nthPoint = nthNode.getCenterPoint();
			rect_.x = nthPoint.getX() + 10;
			rect_.y = nthPoint.getY() + 10;
		} else {
			double x = 0;
			double y = 0;
			double findNthNum = 0;
			for(int i = 0; i < nthNum; i++){
				Node nthNode = LinkSet.getNodeByAtom(((Atom)myObject_).getNthAtom(i));
				if(null == nthNode){ continue; }
				Point2D nthPoint = nthNode.getCenterPoint();
				x += nthPoint.getX();
				y += nthPoint.getY();
				findNthNum++;
			}
			if(1 == findNthNum){
				rect_.x = x;
				rect_.y = y;
			}
			else if(1 < findNthNum){
				rect_.x = x / findNthNum;
				rect_.y = y / findNthNum;
			}
			
		}
	}
	*/
	
	/**
	 * アトム（または閉じた膜）である場合Trueを返す．
	 * @return
	 */
	public boolean isAtom(){
		return (null == myObject_ ||
				myObject_ instanceof Atom ||
				(myObject_ instanceof Membrane && !visible_));
	}
	
	public boolean isBezNode(){
		return (null == myObject_);
	}
	
	public boolean isClipped(){
		return clipped_;
	}
	
	public boolean isHeating(){
		return heating_;
	}
	
	public Iterator<Node> getBeziers(){
		return bezierMap_.values().iterator();
	}
	
	/**
	 * マウスで拾い上げることが出来るか
	 * @return
	 */
	public boolean isPickable(){
		return (pickable_ && isAtom() && !rootMembrane_);
	}
	
	
	public boolean isRoot(){
		return rootMembrane_;
	}
	
	public boolean isUncalc(){
		return uncalc_;
	}

	public boolean isSelected(){
		return selected_;
	}
	
	/**
	 * 可視状態であるか
	 */
	public boolean isVisible(){
		if(Atom.class.isInstance(myObject_)){
			return parent_.isVisible();
		}
		return visible_;
	}
	
	/**
	 * 可視になりアトム的になる
	 *
	 */
	private void iWillBeAMembrane(){
		if(!Membrane.class.isInstance(myObject_)){
			return;
		}
		visible_ = true;
		if(rootMembrane_){ return; }
		if(null != parent_){
			parent_.iWillBeAMembrane();
		}
	}
	
	/**
	 * 不可視になりアトム的になる
	 *
	 */
	private void iWillBeAnAtom(){
		if(!Membrane.class.isInstance(myObject_)){
			return;
		}
		rect_.setFrameFromCenter(rect_.getCenterX(), rect_.getCenterY(), rect_.getCenterX() - 40, rect_.getCenterY() - 40);
		LinkSet.addLink(this);
		
	}
	
	/**
	 * 移動を自分を含めたすべての子Nodeにて行う
	 */
	public void moveAll(){
		if(uncalc_){ return; }
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.moveAll();
			}
			Iterator<Node> ruleNodes = ruleNodeMap_.values().iterator();
			while(ruleNodes.hasNext()){
				Node node = ruleNodes.next();
				node.moveCalc();
			}
			Iterator<Node> bezNodes = bezierMap_.values().iterator();
			while(bezNodes.hasNext()){
				Node node = bezNodes.next();
				node.moveAll();
			}
		}
		moveCalc();
		if(myObject_ instanceof Membrane){
			calcMembraneSize();
		}
	}
	
	public void moveCalc(){
		moveCalcInside();
		moveCalc(dx_, dy_);
		dx_ = 0;
		dy_ = 0;
	}
	
	/**
	 * moveDeltaで加算された移動距離分を実際に移動させる．
	 * 移動後は移動予定距離を初期化する．
	 */
	public void moveCalc(double dx, double dy){
		// 最大移動距離を制限
		if(MAX_MOVE_DELTA < dx){ dx = MAX_MOVE_DELTA; }
		else if(dx < -MAX_MOVE_DELTA){ dx = -MAX_MOVE_DELTA; }
		if(MAX_MOVE_DELTA < dy){ dy = MAX_MOVE_DELTA; }
		else if(dy < -MAX_MOVE_DELTA){ dy = -MAX_MOVE_DELTA; }

		// 移動後に限界を越えない
		if(Integer.MAX_VALUE - dx < rect_.x){ return; }
		if(rect_.x < Integer.MIN_VALUE - dx){ return; }
		if(Integer.MAX_VALUE - dy < rect_.y){ return; }
		if(rect_.y < Integer.MIN_VALUE - dy){ return; }
		
		if(MIN_MOVE_DELTA < Math.abs(dx)){
			rect_.x += dx;
		}
		if(MIN_MOVE_DELTA < Math.abs(dy)){
			rect_.y += dy;
		}
		Iterator<Node> bezNodes = bezierMap_.values().iterator();
		while(bezNodes.hasNext()){
			Node node = bezNodes.next();
			node.moveDelta(dx, dy);
		}
	}
	
	public void moveCalcInside(){
		if(insideDx_ == 0 && insideDy_ == 0){
			return;
		}
		double dxMargin = insideDx_ + ((insideDx_ < 0) ? -Node.MARGIN : Node.MARGIN);
		double dyMargin = insideDy_ + ((insideDy_ < 0) ? -Node.MARGIN : Node.MARGIN);
		
		Rectangle2D rect = getBounds2D();
		rect.setRect(rect.getX() + dxMargin,
				rect.getY() + dyMargin,
				rect.getWidth() + Node.MARGIN,
				rect.getHeight() + Node.MARGIN);
		if(getParent().getBounds2D().contains(rect)){
			moveDelta(insideDx_, insideDy_);
		} else {
			if(null != parent_){
				parent_.moveInside(insideDx_, insideDy_);
			} else {
				moveDelta(insideDx_, insideDy_);
			}
		}
		insideDx_ = 0;
		insideDy_ = 0;
	}
	
	/**
	 * 移動予定距離を加算する．
	 * moveCalcが呼ばれるまでは移動はされない．
	 * @param dx
	 * @param dy
	 */
	public void moveDelta(double dx, double dy){
		if(uncalc_ || clipped_ || uncalcOutsideFource_){ return; }
		dx_ += dx;
		dy_ += dy;
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.moveDelta(dx, dy);
			}
		}
	}
	
	public void moveInside(double dx, double dy){
		if(uncalc_ || clipped_ || uncalcOutsideFource_){ return; }
		insideDx_ += dx;
		insideDy_ += dy;
	}
	
	/**
	 * 原点を中心に回転移動させる
	 * @param angle
	 */
	public void moveRotate(double angle) {
		if(null != parent_){
			double dx = rect_.getX();
			double dy = rect_.getY();

			if(dx == 0.0){ dx=0.000000001; }
			double moveAngle = Math.atan(dy / dx) + angle;
			if(dx < 0.0) moveAngle += Math.PI;
			double length = Math.sqrt((dx * dx) + (dy * dy));
			if(length == 0.0){ length = 0.00001; }
			rect_.x = Math.cos(moveAngle) * length;
			rect_.y = Math.sin(moveAngle) * length;
		}
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.moveRotate(angle);
			}
		}
	}
	
	/**
	 * 自身を描画する
	 * @param g
	 */
	public void paint(Graphics g){
		// 描画範囲に入っていないものは描画しない
		if(!g.getClipBounds().intersects(rect_.getBounds())){
			return;
		}

	}
	
	/**
	 * nodeおよび、その子NodeをLinkSetから削除する
	 * @param node
	 */
	public void removeAll(Node node){
		synchronized (node.getChildMap()) {
			Iterator<Node> nodes = node.getChildMap().values().iterator();
			while(nodes.hasNext()){
				Node targetNode = nodes.next();
				targetNode.removeAll(targetNode);
			}
		}
		LinkSet.removeLink(node);
	}
	
	/**
	 * Nodeをリセットする
	 * @param object
	 */
	public boolean reset(Object object){
		if(null != parent_ &&
				null != myObject_ &&
				null != object &&
				myObject_ != object)
		{
			if(parent_.getChildMap().containsKey(myObject_)){
				Node node = parent_.getChildMap().remove(myObject_);
				parent_.getChildMap().put(object, node);
			}
		}
		myObject_ = object;

		if(object instanceof Atom){
			setAtom((Atom)object);
		} else if(object instanceof Membrane){
			return setMembrane((Membrane)object);
		} else if(object instanceof String){
			setRule((String)object);
		}
		
		return true;
	}
	
	public void resetAllLink(){
//		resetLink();
//		synchronized (nodeMap_) {
//			Iterator<Node> nodes = nodeMap_.values().iterator();
//			while(nodes.hasNext()){
//				Node node = nodes.next();
//				node.resetAllLink();
//			}
//		}
	}
	
	/**
	 * リンク先を再取得
	 */
	public boolean resetLink(){
		if(!(myObject_ instanceof Atom)){ return true; }
		int nthNum = ((Atom)myObject_).getEdgeCount();
		linkList_.clear();
		for(int i = 0; i < nthNum; i++){
			Node node = LinkSet.getNodeByAtom(((Atom)myObject_).getNthAtom(i));
			if(null == node) return false;
			linkList_.add(node);
		}
		return true;
	}
	
	/**
	 * アトム用の初期化処理
	 * @param atom
	 */
	public void setAtom(Atom atom){
		imAtom_ = true;
		name_ = (null != atom.getName()) ? atom.getName() : "";
		if(name_.toLowerCase().endsWith("black")){
			myColor_ = Color.BLACK;
		} else if(name_.toLowerCase().endsWith("blue")){
			myColor_ = Color.BLUE;
		} else if(name_.toLowerCase().endsWith("cyan")){
			myColor_ = Color.CYAN;
		} else if(name_.toLowerCase().endsWith("dark_gray")){
			myColor_ = Color.DARK_GRAY;
		} else if(name_.toLowerCase().endsWith("gray")){
			myColor_ = Color.GRAY;
		} else if(name_.toLowerCase().endsWith("green")){
			myColor_ = Color.GREEN;
		} else if(name_.toLowerCase().endsWith("light_gray")){
			myColor_ = Color.LIGHT_GRAY;
		} else if(name_.toLowerCase().endsWith("magenta")){
			myColor_ = Color.MAGENTA;
		} else if(name_.toLowerCase().endsWith("orange")){
			myColor_ = Color.ORANGE;
		} else if(name_.toLowerCase().endsWith("pink")){
			myColor_ = Color.PINK;
		} else if(name_.toLowerCase().endsWith("red")){
			myColor_ = Color.RED;
		} else if(name_.toLowerCase().endsWith("white")){
			myColor_ = Color.WHITE;
		} else if(name_.toLowerCase().endsWith("yellow")){
			myColor_ = Color.YELLOW;
		} else {
			// [0, 7] -> [128, 255] for eash R G B
			int ir = 0x7F - ((name_.hashCode() & 0xF00) >> 8) * 0x08 + 0x7F;
			int ig = 0x7F - ((name_.hashCode() & 0x0F0) >> 4) * 0x08 + 0x7F;
			int ib = 0x7F - ((name_.hashCode() & 0x00F) >> 0) * 0x08 + 0x7F;
			myColor_ = new Color(ir, ig, ib);
		}
			
		if(0 < atom.getEdgeCount()){
			LinkSet.addLink(this);
			double atomSize = ATOM_SIZE + ((ATOM_SIZE / 8) * atom.getEdgeCount());
			rect_.width = atomSize;
			rect_.height = atomSize;
			rect_.archeight = atomSize;
			rect_.arcwidth = atomSize;
		}
	}
	
	private void setBez(){
		myColor_ = Color.BLACK;
		rect_ = new RoundRectangle2D.Double((Math.random() * 800) - 400,
				(Math.random() * 600) - 300,
				ATOM_SIZE / 2,
				ATOM_SIZE / 2,
				ROUND / 2,
				ROUND / 2);
	}
	
	/**
	 * 継続非計算フラグを立てる
	 * @param clipped
	 */
	public void setClipped(boolean clipped){
		clipped_ = clipped;
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.setClipped(clipped_);
			}
		}
		if(clipped_){
			pinAnime_ = 5;
			pinPosY_ =
				-(
						panel_.getHeight() +
						(panel_.getPin().getHeight(panel_) * Math.random() * 15)
				) / GraphPanel.getMagnification();
		}
	}
	
	public void setHeating(boolean flag){
		heating_ = flag;
	}
	
	/**
	 * 一番ルートに近い不可視のNodeをセットする
	 * NodeがNULLで、自分が不可視である場合は、
	 * 子Nodeには一番ルートに近い不可視のNodeは自分であると通知
	 * @param invisbleNode
	 */
	public void setInvisibleRootNode(Node invisibleNode){
		invisibleRootNode_ = ((null == invisibleNode) && (!visible_)) ? this : invisibleNode;
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.setInvisibleRootNode(invisibleRootNode_);
			}
		}
	}
	
	/**
	 * 膜用の初期化処理
	 * @param mem セットする膜
	 * @param setChildren 子Nodeを更新するか（サイズの更新だけならばfalse）
	 */
	public boolean setMembrane(Membrane mem){
		boolean success = true;
		LinkSet.addLink(this);
		imAtom_ = false;
		name_ = (null != mem.getName()) ? mem.getName() : "";

		Map<Membrane, Node> memNodeMap = new HashMap<Membrane, Node>();
		Map<Atom, Node> atomNodeMap = new HashMap<Atom, Node>();
		Map<String, Node> ruleNodeMap = new HashMap<String, Node>();

		// 子膜をリセット
		synchronized (nodeMap_) {
			Iterator mems = mem.memIterator();
			while(mems.hasNext()){
				Membrane childMem = (Membrane)mems.next();
				Node node;
				if(nodeMap_.containsKey(childMem)){
					node = nodeMap_.get(childMem);
					if(success){
						success = node.reset(childMem);
					} else {
						node.reset(childMem);
					}
				} else {
					node = new Node(this, childMem);
					nodeMap_.put(childMem, node);
					if(success){
						success = node.reset(childMem);
					} else {
						node.reset(childMem);
					}
				}
				
				memNodeMap.put(childMem, node);
			}

			// アトムをリセット
			Iterator atoms = mem.atomIterator();
			while(atoms.hasNext()){
				Atom childAtom = (Atom)atoms.next();
				// ProxyAtomは無視
				if(childAtom.getFunctor().isInsideProxy() ||
						childAtom.getFunctor().isOutsideProxy())
				{
					continue;
				}

				Node node;
				if(nodeMap_.containsKey(childAtom)){
					node = nodeMap_.get(childAtom);
					if(success){
						success = node.reset(childAtom);
					} else {
						node.reset(childAtom);
					}
				} else {
					node = new Node(this, childAtom);
					nodeMap_.put(childAtom, node);
					node.reset(childAtom);
				}
				atomNodeMap.put(childAtom, node);
			}
			
			Iterator rules = mem.rulesetIterator();
			while(rules.hasNext()){
				Ruleset ruleset = (Ruleset)rules.next();
				if(!(ruleset instanceof InterpretedRuleset)){
					continue;
				}
				String[] ruleValues =
					((InterpretedRuleset)ruleset).encodeRulesIndividually();

				for(int i = 0; i < ruleValues.length; i++){
					Node node;
					if(ruleNodeMap_.containsKey(ruleValues[i])){
						node = ruleNodeMap_.get(ruleValues[i]);
						if(success){
							success = node.reset(ruleValues[i]);
						} else {
							node.reset(ruleValues[i]);
						}
					} else {
						node = new Node(this, ruleValues[i]);
						ruleNodeMap_.put(ruleValues[i], node);
						node.reset(ruleValues[i]);
					}
					ruleNodeMap.put(ruleValues[i], node);
				}
			}

			// 不要になったリンクを削除
			Iterator<Object> removeNodes = nodeMap_.keySet().iterator();
			while(removeNodes.hasNext()){
				Object key = removeNodes.next();
				if(!memNodeMap.containsKey(key) &&
						!atomNodeMap.containsKey(key))
				{
					removeAll(nodeMap_.get(key));
				}
			}

			// Nodeのリンク情報を再設定
			Iterator<Node> newAtoms = atomNodeMap.values().iterator();
			while(newAtoms.hasNext()){
				Node newAtom = newAtoms.next();
				if(success){
					success = newAtom.resetLink();
				} else {
					newAtom.resetLink();
				}
			}
			
			// 不要になったNode削除
			nodeMap_.clear();
			nodeMap_.putAll(memNodeMap);
			nodeMap_.putAll(atomNodeMap);
			
			ruleNodeMap_.clear();
			ruleNodeMap_.putAll(ruleNodeMap);
			
			if(showRules_){
				nodeMap_.putAll(ruleNodeMap_);
			}
		}
		return success;
	}
	
	/**
	 * 親膜をセットする
	 * @param memNode
	 */
	public void setParentNode(Node node) {
		parent_ = node;
	}
	
	/**
	 * マウスで拾い上げの許可を設定
	 * @param pick
	 */
	public void setPickable(boolean pick){
		pickable_ = pick;
	}
	
	public void setPinAnimeCounter(int counter){
		pinAnime_ = counter;
	}
	
	/**
	 * 座標の強制変更
	 * moveCalcを待たずに即座に移動する．
	 * ただし，移動予定距離とは違い，引数の座標に強制的に移動する
	 * @param p
	 */
	public void setPos(double x, double y){
		synchronized (nodeMap_) {
			x = x - (rect_.getWidth() / 2);
			y = y - (rect_.getHeight() / 2);
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.setPosDelta(x, y);
			}
			Iterator<String> ruleKeys = ruleNodeMap_.keySet().iterator();
			while(ruleKeys.hasNext()){
				String key = ruleKeys.next(); 
				if(nodeMap_.containsKey(key)){
					continue;
				}
				Node node = ruleNodeMap_.get(key);
				node.setPosDelta(x, y);
			}
			Iterator<Node> bezNodes = bezierMap_.values().iterator();
			while(bezNodes.hasNext()){
				Node node = bezNodes.next();
				node.setPosDelta(x, y);
			}
			rect_.x = x;
			rect_.y = y;
		}
	}
	
	/**
	 * 座標の強制変更
	 * moveCalcを待たずに即座に移動する．
	 * ただし，setPosとは違い，引数の値分に強制的に移動する
	 * @param p
	 */
	public void setPosDelta(double dx, double dy){
		if(null != myObject_ && null == parent_){
			dx = dx + rect_.x;
			dy = dy + rect_.y;
		} else {
			Rectangle2D parentRect = parent_.getBounds2D();
			dx = dx + (rect_.x - parentRect.getX());
			dy = dy + (rect_.y - parentRect.getY());
		}
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.setPosDelta(dx, dy);
			}
			Iterator<Node> bezNodes = bezierMap_.values().iterator();
			while(bezNodes.hasNext()){
				Node node = bezNodes.next();
				node.setPosDelta(dx, dy);
			}
			rect_.x = dx;
			rect_.y = dy;
		}
		
	}
	
	static
	public void setRichMode(boolean flag){
		richMode_ = flag;
	}
	
	public void setRoot(boolean flag){
		rootMembrane_ = flag;
	}
	
	/**
	 * ルールNodeの名前や色を設定する
	 * @param rule
	 */
	private void setRule(String rule){
		name_ = rule;
		
		myColor_ = RULE_COLOR;
	}
	
	public void setSelected(boolean selected){
		selected_ = selected;
	}
	
	static
	public void setShowAll(boolean flag){
		showAll_ = flag;
	}
	
	/**
	 * ルールの表示非表示設定
	 * @param showRules
	 */
	public void setShowRules(boolean showRules){
		showRules_ = showRules;
		synchronized (nodeMap_) {
			if(showRules){
				nodeMap_.putAll(ruleNodeMap_);
			} else {
				Iterator keys = ruleNodeMap_.keySet().iterator();
				while(keys.hasNext()){
					nodeMap_.remove(keys.next());
				}
			}
		}
	}
	
	/**
	 * アトムの名称を全部表示するか、頭文字を表示するかの設定
	 * @param showFullName
	 */
	static
	public void setShowFullName(boolean showFullName){
		showFullName_ = showFullName;
	}
	
	public void setUncalcOutSideForce(boolean uncalc){
		uncalcOutsideFource_ = uncalc;
	}
	
	/**
	 * 非計算ノードのフラグをセットする
	 * @param uncalc
	 */
	public void setUncalc(boolean uncalc) {
		uncalc_ = uncalc;
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.setUncalc(uncalc);
			}
		}
	}
	
	/**
	 * 可視状態をセットする
	 * @param flag 可視状態
	 * @param foruce 強制的に子膜も可視状態をflagにセットする
	 */
	public void setVisible(boolean flag, boolean foruce){
		visible_ = (!rootMembrane_) ? flag : true;

		
		if(foruce){
			synchronized (nodeMap_) {
				Iterator<Node> nodes = nodeMap_.values().iterator();
				while(nodes.hasNext()){
					Node node = nodes.next();
					node.setVisible(flag, true);
				}
			}
		}
		
		// 可視になった
		if(visible_ && Membrane.class.isInstance(myObject_)){
			iWillBeAMembrane();
			calcMembraneSize();
			if(this == invisibleRootNode_){
				setInvisibleRootNode(null);
			}
		}
		// 不可視になった
		else if(!visible_ && Membrane.class.isInstance(myObject_)){
			iWillBeAnAtom();
			setInvisibleRootNode(this);
		}
	}
	
	/**
	 * 継続非計算フラグを反転する
	 *
	 */
	public void swapClipped(){
		setClipped(!clipped_);
	}
	
	/**
	 * 可視不可視を反転する
	 *
	 */
	public void swapVisible(){
		// アトムが選択された場合は親膜を反転
		if(Atom.class.isInstance(myObject_)){
			parent_.swapVisible();
			return;
		}
		setVisible(!visible_, false);
	}
}

package gui2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import runtime.Atom;
import runtime.Membrane;

public class Node implements Cloneable{

	///////////////////////////////////////////////////////////////////////////
	// final static
	
	final static
	private double ATOM_SIZE = 40.0;

	final static
	private Font FONT = new Font("SansSerif", Font.PLAIN, 20);
	
	final static
	private double MARGIN = 15.0;

	final static
	private double MAX_MOVE_DELTA = 300.0;
	
	/** この値以下の場合は移動しない */
	final static
	private double MIN_MOVE_DELTA = 0.1;
	
	final static
	private double ROUND = 40;

	//////////////////////////////////////////////////////////////////////////
	// static

	static
	private int nextID_ = 0;
	
	static
	private GraphPanel panel_;
	
	///////////////////////////////////////////////////////////////////////////
	// private

	/** 継続非計算フラグ */
	private boolean clipped_ = false;
	
	/** 移動情報 */
	private double dx_;

	/** 移動情報 */
	private double dy_;
	
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
	
	/** 親膜 */
	private Node parent_;
	
	/** 取得可否 */
	private boolean pickable_ = true;
	
	/** ピンのアニメーション用変数 */
	public int pinAnime_ = 0;
	
	/** ピンのアニメーション用座標変数 */
	private double pinPosY_;
	
	/** 描画用の形 */
	private RoundRectangle2D.Double rect_ = new RoundRectangle2D.Double((Math.random() * 800) - 400,
			(Math.random() * 600) - 300,
			ATOM_SIZE,
			ATOM_SIZE,
			ROUND,
			ROUND);
	
	/** 非計算フラグ */
	private boolean uncalc_ = false;
	
	/** 可視フラグ */
	private boolean visible_;
	
	/** 一番ルートに近い不可視のNode */
	private Node invisibleRootNode_ = null;
	
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
		} else {
			setMembrane((Membrane)object);
		}
		
		// 世界膜ならば可視それ以外は不可視
		if(null != parent_){
			setVisible(false, true);
			setInvisibleRootNode(null);
//			resetAllLink();
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

	/**
	 * 位置座標などの計算
	 */
	public void calc(){
		NodeFunction.calcRelaxAngle(this);
		if(clipped_ || uncalc_){ return; }
		NodeFunction.calcSpring(this);
		synchronized (nodeMap_) {
			NodeFunction.calcAttraction(this, nodeMap_);
			NodeFunction.calcRepulsive(this, nodeMap_);
			NodeFunction.calcDivergence(this, nodeMap_);
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
		synchronized (nodeMap_) {
			double maxX = Integer.MIN_VALUE;
			double maxY = Integer.MIN_VALUE;
			double minX = Integer.MAX_VALUE;
			double minY = Integer.MAX_VALUE;
			
			// 子膜をリセット
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				
				Rectangle2D rectangle = node.getBounds2D();
				minX = (minX < rectangle.getMinX()) ? minX : rectangle.getMinX(); 
				maxX = (maxX > rectangle.getMaxX()) ? maxX : rectangle.getMaxX(); 
				minY = (minY < rectangle.getMinY()) ? minY : rectangle.getMinY(); 
				maxY = (maxY > rectangle.getMaxY()) ? maxY : rectangle.getMaxY(); 
			}

			// サイズ変更
			if(visible_ && 0 < nodeMap_.size()){
				rect_.setFrameFromDiagonal(minX - MARGIN, minY - MARGIN, maxX + MARGIN, maxY + MARGIN);
			}
			else if(rect_.width != ATOM_SIZE || rect_.height != ATOM_SIZE){
				rect_.setFrameFromCenter(rect_.getCenterX(),
						rect_.getCenterY(),
						rect_.getCenterX() - ATOM_SIZE,
						rect_.getCenterY() - ATOM_SIZE);
			}
		}
		
	}
	
	public Node cloneNode(Map<Node, Node> cloneMap){
		synchronized (nodeMap_) {
			Node cloneNode = new Node();
			cloneMap.put(this, cloneNode);

			Iterator keys = nodeMap_.keySet().iterator();
			while(keys.hasNext()){
				Object key = keys.next();
				Node oldNode = nodeMap_.get(key);
				oldNode.cloneNode(cloneMap);

			}
			return cloneNode;
		}
	}
	
	public Node cloneNodeParm(Map<Node, Node> cloneMap, Node orginNode){
		synchronized (orginNode.nodeMap_) {
			parent_ = cloneMap.get(orginNode.parent_);
			invisibleRootNode_ = cloneMap.get(orginNode.invisibleRootNode_);
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

	public int getEdgeCount(){
		return linkList_.size();
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
	
	/**
	 * 隣のNodeを取得する
	 * @param i
	 * @return
	 */
	public Node getNthNode(int i){
		return linkList_.get(i);
	}
	
	/**
	 * 自オブジェクト（Atom,Membrane）を返す
	 * @return
	 */
	public Object getObject(){
		return myObject_;
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
	
	/**
	 * 指定された座標にあるNodeを取得する
	 */
	public Node getPointNode(int x, int y, boolean force){
		synchronized (nodeMap_) {
			if(rect_.contains(x, y)){
				if(isPickable()){
					return this;
				} else{
					Iterator<Node> nodes = nodeMap_.values().iterator();
					while(nodes.hasNext()){
						Node node = nodes.next();
						node = node.getPointNode(x, y, force);
						if(null != node){ return node; }
					}
				}
				return (force && null != parent_) ? this : null;
			}
		}
		return null;
	}
	
	public double getSize(){
		if(myObject_ instanceof Atom){
			return (ATOM_SIZE / 2);
		}
		else {
			
		}
		return 0;
	}
	
	/**
	 * 初期位置をリンクされているアトムに極力近くする
	 *
	 */
	public void initPosition(){
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
				rect_.y = x;
			}
			else if(1 < findNthNum){
				rect_.x = x / findNthNum;
				rect_.y = x / findNthNum;
			}
			
		}
	}
	
	/**
	 * アトム（または閉じた膜）である場合Trueを返す．
	 * @return
	 */
	public boolean isAtom(){
		return (imAtom_ || !visible_);
	}
	
	/**
	 * マウスで拾い上げることが出来るか
	 * @return
	 */
	public boolean isPickable(){
		return (pickable_ && isAtom() && null != parent_);
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
		if(null == parent_){ return; }
		parent_.iWillBeAMembrane();
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
		}
		moveCalc();
		if(myObject_ instanceof Membrane){
			calcMembraneSize();
		}
	}
	
	/**
	 * moveDeltaで加算された移動距離分を実際に移動させる．
	 * 移動後は移動予定距離を初期化する．
	 */
	public void moveCalc(){
		// 最大移動距離を制限
		if(MAX_MOVE_DELTA < dx_){ dx_ = MAX_MOVE_DELTA; }
		else if(dx_ < -MAX_MOVE_DELTA){ dx_ = -MAX_MOVE_DELTA; }
		if(MAX_MOVE_DELTA < dy_){ dy_ = MAX_MOVE_DELTA; }
		else if(dy_ < -MAX_MOVE_DELTA){ dy_ = -MAX_MOVE_DELTA; }

		// 移動後に限界を越えない
		if(Integer.MAX_VALUE - dx_ < rect_.x){ return; }
		if(rect_.x < Integer.MIN_VALUE - dx_){ return; }
		if(Integer.MAX_VALUE - dy_ < rect_.y){ return; }
		if(rect_.y < Integer.MIN_VALUE - dy_){ return; }
		
		if(MIN_MOVE_DELTA < Math.abs(dx_)){
			rect_.x += dx_;
		}
		if(MIN_MOVE_DELTA < Math.abs(dy_)){
			rect_.y += dy_;
		}
		dx_ = 0;
		dy_ = 0;
	}
	
	/**
	 * 移動予定距離を加算する．
	 * moveCalcが呼ばれるまでは移動はされない．
	 * @param dx
	 * @param dy
	 */
	public void moveDelta(double dx, double dy){
		if(uncalc_ || clipped_){ return; }
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
	
	/**
	 * 自身を描画する
	 * @param g
	 */
	public void paint(Graphics g){
		if(null == parent_){
			LinkSet.paint(g);
		}
		synchronized (nodeMap_) {
			// アトムまたは閉じた膜の描画
			if(isAtom()){
				g.setColor(myColor_);
				((Graphics2D)g).fill(rect_);
				g.setColor(Color.BLACK);
				((Graphics2D)g).draw(rect_);
				g.setFont(FONT);
				g.drawString(name_, (int)rect_.x, (int)rect_.y);
			}
			// 膜の描画
			else {
				Iterator<Node> nodes = nodeMap_.values().iterator();
				while(nodes.hasNext()){
					Node node = nodes.next();
					node.paint(g);
				}
				if(null != parent_){
					g.setColor(myColor_);
					if(visible_){
						((Graphics2D)g).draw(rect_);
						g.setColor(Color.BLACK);
						g.setFont(FONT);
						g.drawString(name_, (int)rect_.x, (int)rect_.y);
					} else {
						((Graphics2D)g).fill(rect_);
						g.setColor(Color.BLACK);
						g.setFont(FONT);
						g.drawString(name_, (int)rect_.x, (int)rect_.y);
					}
				}

			}
			if(clipped_){
				paintPin(g, 0, 0);
			}
		}
	}
	
	/**
	 * ピンの描画およびアニメーション用の計算を行う
	 * @param g
	 * @param deltaX
	 * @param deltaY
	 */
	public void paintPin(Graphics g, int deltaX, int deltaY){
		if((pinAnime_ != 0) && (pinPosY_ < rect_.getCenterY())){
			pinPosY_ += Math.abs(panel_.getHeight() / pinAnime_); 
		}
		if(pinPosY_ > rect_.getCenterY()){ pinAnime_ = 0; }
		if(pinAnime_ == 0){ pinPosY_ = rect_.getY() - (panel_.getPin().getHeight(panel_) / 2); }

		if((myObject_ instanceof Membrane) && visible_){
			return;
		}
		g.drawImage(panel_.getPin(),
				(int)(rect_.getCenterX() + deltaX),
				(int)(pinPosY_ + deltaY),
				panel_
				);
		
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
		boolean success = true;
		myObject_ = object;

		if(Atom.class.isInstance(object)){
			setAtom((Atom)object);
		} else {
			success = setMembrane((Membrane)object);
		}
		return success;
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
	private void setAtom(Atom atom){
		imAtom_ = true;
		name_ = (null != atom.getName()) ? atom.getName() : "";
		
		// [0, 7] -> [128, 255] for eash R G B
		int ir = 0x7F - ((name_.hashCode() & 0xF00) >> 8) * 0x08 + 0x7F;
		int ig = 0x7F - ((name_.hashCode() & 0x0F0) >> 4) * 0x08 + 0x7F;
		int ib = 0x7F - ((name_.hashCode() & 0x00F) >> 0) * 0x08 + 0x7F;
		myColor_ = new Color(ir, ig, ib);
		if(0 < atom.getEdgeCount()){
			LinkSet.addLink(this);
		}
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
		synchronized (nodeMap_) {
			LinkSet.addLink(this);
			imAtom_ = false;
			name_ = (null != mem.getName()) ? mem.getName() : "";

			Map<Membrane, Node> memNodeMap = new HashMap<Membrane, Node>();
			Map<Atom, Node> atomNodeMap = new HashMap<Atom, Node>();

			// 子膜をリセット
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

			// 不要になったリンクを削除
			Iterator<Object> removeNodes = nodeMap_.keySet().iterator();
			while(removeNodes.hasNext()){
				Object key = removeNodes.next();
				if(!memNodeMap.containsKey(key) && !atomNodeMap.containsKey(key)){
//					LinkSet.removeLink(key)
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
		if(null == parent_){
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
			rect_.x = dx;
			rect_.y = dy;
		}
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
		visible_ = (parent_ != null) ? flag : true;

		
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

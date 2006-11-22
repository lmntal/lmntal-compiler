package gui2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import runtime.Atom;
import runtime.Membrane;

public class Node {

	///////////////////////////////////////////////////////////////////////////
	// static
	
	final static
	private double MARGIN = 15.0;

	final static
	private double ROUND = 40;
	

	static
	private GraphPanel panel_;
	
	///////////////////////////////////////////////////////////////////////////
	
	
	/** 移動情報 */
	private double dx_;

	/** 移動情報 */
	private double dy_;
	
	/** アトムまたはアトム的（閉じた膜）であるか */
	private boolean imAtom_;
	
	/** 自Nodeの色 */
	private Color myColor_ = Color.BLUE;
	
	/** 自Nodeのオブジェクト（Atom または Membrane）*/
	private Object myObject;

	/** Node名 */
	private String name_ = "";
	
	/** 子Node */
	private Map<Object, Node> nodeMap_ = new HashMap<Object, Node>();
	
	/** 親膜 */
	private Node parent_;
	
	/** 取得可否 */
	private boolean pickable_ = true;
	
	/** 描画用の形 */
	private RoundRectangle2D.Double rect_ = new RoundRectangle2D.Double((Math.random() * 800) - 400,
			(Math.random() * 600) - 300,
			40.0,
			40.0,
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
	public Node(Node node, Object object){
		parent_ = node;
		myObject = object;
		
		visible_ = true;
		
		// 膜、アトムの初期化
		if(Atom.class.isInstance(object)){
			setAtom((Atom)object);
		} else {
			setMembrane((Membrane)object);
		}
		
		// 世界膜ならば可視それ以外は不可視
		if(null != parent_){
			setVisible(false, true);
			setInvisibleRootNode(null);
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
		NodeFunction.calcSpring(this);
//		moveCalc();
	}
	
	/**
	 * 位座標などの計算を自分を含めたすべての子Nodeにて行う
	 */
	public void calcAll(){
		if(uncalc_){ return; }
		calc();
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.calcAll();
			}
		}
	}
	
	/**
	 * 位置、座標を取得する
	 * @return
	 */
	public Rectangle2D getBounds2D(){
		return rect_.getBounds2D();
	}
	
	public Point2D.Double getCenterPoint(){
		return (new Point2D.Double(rect_.getCenterX(), rect_.getCenterY()));
	}
	
	public Node getInvisibleRootNode(){
		return invisibleRootNode_;
	}
	
	/**
	 * 自オブジェクト（Atom,Membrane）を返す
	 * @return
	 */
	public Object getObject(){
		return myObject;
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
	 * 計算対象であるかの取得
	 * @return
	 */
	public boolean isUncalc() {
		return uncalc_;
	}
	
	/**
	 * 可視状態であるか
	 */
	public boolean isVisible(){
		if(Atom.class.isInstance(myObject)){
			return parent_.isVisible();
		}
		return visible_;
	}
	
	/**
	 * 可視になりアトム的になる
	 *
	 */
	private void iWillBeAMembrane(){
		if(!Membrane.class.isInstance(myObject)){
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
		if(!Membrane.class.isInstance(myObject)){
			return;
		}
		rect_.setFrameFromCenter(rect_.getCenterX(), rect_.getCenterY(), rect_.getCenterX() - 40, rect_.getCenterY() - 40);
		LinkSet.addLink(myObject, this);
		
	}
	
	/**
	 * 移動を自分を含めたすべての子Nodeにて行う
	 */
	public void moveAll(){
		if(uncalc_){ return; }
		moveCalc();
		synchronized (nodeMap_) {
			Iterator<Node> nodes = nodeMap_.values().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				node.moveAll();
			}
		}
	}
	
	/**
	 * moveDeltaで加算された移動距離分を実際に移動させる．
	 * 移動後は移動予定距離を初期化する．
	 */
	public void moveCalc(){
		rect_.x += dx_;
		rect_.y += dy_;
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
		if(uncalc_){ return; }
		dx_ += dx;
		dy_ += dy;
		if(null != invisibleRootNode_){
			synchronized (nodeMap_) {
				Iterator<Node> nodes = nodeMap_.values().iterator();
				while(nodes.hasNext()){
					Node node = nodes.next();
					node.moveDelta(dx, dy);
				}
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
						g.drawString(name_, (int)rect_.x, (int)rect_.y);
					} else {
						((Graphics2D)g).fill(rect_);
						g.setColor(Color.BLACK);
						g.drawString(name_, (int)rect_.x, (int)rect_.y);
					}
				}

			}
		}
	}
	
	/**
	 * Nodeをリセットする
	 * @param object
	 */
	public void reset(Object object){
		myObject = object;

		if(Atom.class.isInstance(object)){
			setAtom((Atom)object);
		} else {
			setMembrane((Membrane)object);
		}
		
		calc();
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
			LinkSet.addLink(atom, this);
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
	 * @param mem
	 */
	private void setMembrane(Membrane mem){
		synchronized (nodeMap_) {
			LinkSet.addLink(mem, this);
			double maxX = Integer.MIN_VALUE;
			double maxY = Integer.MIN_VALUE;
			double minX = Integer.MAX_VALUE;
			double minY = Integer.MAX_VALUE;
			
			imAtom_ = false;
			name_ = (null != mem.getName()) ? mem.getName() : "";

			Map<Object, Node> memNodeMap = new HashMap<Object, Node>();
			Map<Object, Node> atomNodeMap = new HashMap<Object, Node>();

			// 子膜をリセット
			Iterator mems = mem.memIterator();
			while(mems.hasNext()){
				Membrane childMem = (Membrane)mems.next();
				Node node;
				if(nodeMap_.containsKey(childMem)){
					node = nodeMap_.get(childMem);
					node.reset(childMem);
				} else {
					node = new Node(this, childMem);
					nodeMap_.put(childMem, node);
				}
				
				Rectangle2D rectangle = node.getBounds2D();
				minX = (minX < rectangle.getMinX()) ? minX : rectangle.getMinX(); 
				maxX = (maxX > rectangle.getMaxX()) ? maxX : rectangle.getMaxX(); 
				minY = (minY < rectangle.getMinY()) ? minY : rectangle.getMinY(); 
				maxY = (maxY > rectangle.getMaxY()) ? maxY : rectangle.getMaxY(); 
				
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
					node.reset(childAtom);
				} else {
					node = new Node(this, childAtom);
					nodeMap_.put(childAtom, new Node(this, childAtom));
				}
				
				Rectangle2D rectangle = node.getBounds2D();
				minX = (minX < rectangle.getMinX()) ? minX : rectangle.getMinX(); 
				maxX = (maxX > rectangle.getMaxX()) ? maxX : rectangle.getMaxX(); 
				minY = (minY < rectangle.getMinY()) ? minY : rectangle.getMinY(); 
				maxY = (maxY > rectangle.getMaxY()) ? maxY : rectangle.getMaxY(); 
				
				atomNodeMap.put(childAtom, node);
			}

			// 不要になったリンクを削除
			Iterator<Object> removeAtoms = nodeMap_.keySet().iterator();
			while(removeAtoms.hasNext()){
				Object key = removeAtoms.next();
				if(!memNodeMap.containsKey(key) && !atomNodeMap.containsKey(key)){
					LinkSet.removeLink(key);
				}
			}
			
			// 不要になったNode削除
			nodeMap_.clear();
			nodeMap_.putAll(memNodeMap);
			nodeMap_.putAll(atomNodeMap);
			
			// サイズ変更
			if(visible_){
				rect_.setFrameFromDiagonal(minX - MARGIN, minY - MARGIN, maxX + MARGIN, maxY + MARGIN);
			}
		}
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
		if(visible_ && Membrane.class.isInstance(myObject)){
			iWillBeAMembrane();
			setMembrane((Membrane)myObject);
			if(this == invisibleRootNode_){
				setInvisibleRootNode(null);
			}
		}
		// 不可視になった
		else if(!visible_ && Membrane.class.isInstance(myObject)){
			iWillBeAnAtom();
			setInvisibleRootNode(this);
		}
	}
	
	/**
	 * 可視不可視を反転する
	 *
	 */
	public void swapVisible(){
		// アトムが選択された場合は親膜を反転
		if(Atom.class.isInstance(myObject)){
			parent_.swapVisible();
			return;
		}
		setVisible(!visible_, false);
	}
}

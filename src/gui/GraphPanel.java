package gui;

import gui.control.NodeFunction;
import gui.model.LinkSet;
import gui.model.Node;
import gui.view.CommonView;
import gui.view.LinkView;
import gui.view.MembraneView;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

final
public class GraphPanel extends JPanel {

	///////////////////////////////////////////////////////////////////////////
	// static

	/**　 自動焦点の位置決めの遊び（振動防止） */
	final static
	private int AUTO_FOCUS_POS_DELTA = 5;
	
	/** 自動焦点の余白サイズ */
	final static
	private int AUTO_FOCUS_SIZE_DELTA = 10;
	
	/** 局所加熱の加熱範囲 */
	final static
	private double FIRE_AREA = 75.0;

	/** 炎のアニメーション表示位置 */
	final static
	private int FIRE_HEIGHT_MARGIN = 40;
	
	/** 炎のアニメーション表示位置 */
	final static
	private int FIRE_WIDTH_MARGIN = 40;

	/** 不可視カーソル */
	final static
	private Cursor NULL_CURSOR = createNullCursor();

	/** Nodeを掴むときの当たり範囲（値が小さいほどシビア） */
	final static
	private double POINT_DELTA_AREA = 10.0;
	
	/** 炎のイメージ */
	static
	private Image[] fire_ = new Image[7];
	
	/** 表示倍率 */
	static
	private double magnification_ = 0.5;
	
	/** 固定用ピンのイメージ */
	static
	private Image pin_;
	
	static
	private boolean stopPainting_ = false;
	
	static
	private Point lastMousePoint;
	
	///////////////////////////////////////////////////////////////////////////
	
	private AffineTransform af_ = new AffineTransform();
	private AutoFocusThread autoFocusTh_ = null;
	private CalcThread calcTh_ = null;
	private Commons commonListener_ = new Commons(this);
	private Cursor currentCursor = new Cursor(Cursor.HAND_CURSOR);
	private double deltaX;
	private double deltaY;
	private boolean editLinkMode_ = false;
	private int fireID_ = 0;
	private boolean history_ = false;
	private boolean localHeatingMode_ = false;
	private List<String> logList_ = new ArrayList<String>();
	private Node moveTargetNode_ = null;
	private Map<Object, Membrane> newNodeMap_ = new HashMap<Object, Membrane>();
	private Map<Node, Node> newLinkMap_ = new HashMap<Node, Node>();
	private boolean nowHeating_ = false;
	private Node selectedNode_ = null;
	private Node orgRootNode_;
	private RepaintThread repaintTh_ = null;
	private Map<Node, Node> removeLinkMap_ = new HashMap<Node, Node>();
	private Map<Node, String> renameAtomMap_ = new HashMap<Node, String>();
	private Map<Node, String> renameMembraneMap_ = new HashMap<Node, String>();
	private Membrane rootMembrane_;
	private Node rootNode_;
	private List<Node> rootNodeList_ = new ArrayList<Node>();
	private Node tmpRootNode_;
	private GraphPanel myPanel_ = this;
	
	///////////////////////////////////////////////////////////////////////////
	
	public GraphPanel() {
		super();
		Node.setPanel(this);
		pin_ = Toolkit.getDefaultToolkit().getImage(getClass().getResource("gabyou.gif"));
		fire_[0] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire1.png"));
		fire_[1] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire2.png"));
		fire_[2] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire3.png"));
		fire_[3] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire4.png"));
		fire_[4] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire5.png"));
		fire_[5] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire6.png"));
		fire_[6] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("fire7.png"));
		// PINのロード待ち　ここから
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(pin_, 0);
		mt.addImage(fire_[0], 1);
		mt.addImage(fire_[1], 2);
		mt.addImage(fire_[2], 3);
		mt.addImage(fire_[3], 4);
		mt.addImage(fire_[4], 5);
		mt.addImage(fire_[5], 6);
		mt.addImage(fire_[6], 7);
		try {
			mt.waitForAll();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		CommonView.initView(this.getClass());
		
		//　PINのロード待ち　ここまで
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addMouseListener(new PanelMouseAdapter());
		
		addMouseMotionListener(new MouseMotionAdapter() {
			/**
			 * マウスがドラッグされたときの処理
			 * <p>移動した距離の取得や局所加熱処理</p>
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				// 局所加熱処理
				if(localHeatingMode_){
					localHeating(e);
					return;
				}
				
				if(editLinkMode_ &&
						moveTargetNode_ != rootNode_ &&
						null != moveTargetNode_ &&
						moveTargetNode_.getObject() instanceof Atom)
				{
					lastMousePoint = e.getPoint();
					return;
				}
					
				// 移動距離の取得
				if(moveTargetNode_ == null){ return; }
				int pointX = (int)((e.getX() - deltaX) / getMagnification());
				int pointY = (int)((e.getY() - deltaY) / getMagnification());
				moveTargetNode_.setPos(pointX, pointY);
			}

		}
		);
		
		addMouseWheelListener(commonListener_);

		calcTh_ = new CalcThread();
		calcTh_.start();
		repaintTh_ = new RepaintThread();
		repaintTh_.start();
	}
	///////////////////////////////////////////////////////////////////////////
	
	public void addAtom(String name, Membrane targetMem){
		Functor func = new SymbolFunctor(name, 0);
		synchronized (newNodeMap_) {
			newNodeMap_.put(func, targetMem);
		}
	}
	
	public void addMembrane(String name, Membrane targetMem){
		Membrane mem = new Membrane();
		mem.setName(name);
		synchronized (newNodeMap_) {
			newNodeMap_.put(mem, targetMem);
		}
	}

	public void addRenameAtom(String name, Node node){
		if(!(node.getObject() instanceof Atom)){ return; }
		synchronized (renameAtomMap_) {
			renameAtomMap_.put(node, name);
		}
	}
	
	public void addRenameMembrane(String name, Node node){
		if(!(node.getObject() instanceof Membrane)){ return; }
		synchronized (renameMembraneMap_) {
			renameMembraneMap_.put(node, name);
		}
	}
	
	/**
	 * addAtomやaddMembraneで追加予約したNodeを実際に追加する
	 *
	 */
	private void addNode(){
		synchronized (newNodeMap_) {
			Iterator objects = newNodeMap_.keySet().iterator();
			while(objects.hasNext()){
				Object obj = objects.next();
				Membrane targetMem = newNodeMap_.get(obj);
				if(obj instanceof Functor){
					targetMem.addAtom(new Atom(targetMem, (Functor)obj));
				}
				else if(obj instanceof Membrane){
					targetMem.addMem((Membrane)obj);
				}
			}
			newNodeMap_.clear();
		}
		rootNode_.reset(rootMembrane_);
		resetLink();
		commonListener_.setLog(rootMembrane_.toString());
	}
	
	/**
	 * リンクを張りなおす。<p>
	 * 現状は、アトムの再利用が無理。（再利用しつつリンク数を増やせないので）。
	 */
	private void addLink(){
		synchronized (newLinkMap_) {
			Iterator<Node> atoms = newLinkMap_.keySet().iterator();
			while(atoms.hasNext()){
				Node sourceNode = atoms.next();
				Node targetNode = newLinkMap_.get(sourceNode);
				Atom sourceAtom = (Atom)sourceNode.getObject();
				Atom targetAtom = (Atom)targetNode.getObject();
				
				Functor newSourceFunctor;
				Functor newTargetFunctor;
				
//				if(sourceAtom.getEdgeCount() == 0){
//					try {
//						int value = Integer.parseInt(sourceAtom.getName());
//						newSourceFunctor =
//							new IntegerFunctor(value);
//					} catch (NumberFormatException e) {
//						newSourceFunctor =
//							new SymbolFunctor(sourceAtom.getName(), 1);
//					}
//				} else {
//					newSourceFunctor =
//						new SymbolFunctor(sourceAtom.getName(), sourceAtom.getEdgeCount() + 1);
//				}
				
				
				newSourceFunctor =
					new SymbolFunctor(sourceAtom.getName(), sourceAtom.getEdgeCount() + 1);
				newTargetFunctor =
					new SymbolFunctor(targetAtom.getName(), targetAtom.getEdgeCount() + 1);

				Atom newSourceAtom = new Atom(sourceAtom.getMem(), newSourceFunctor);
				Atom newTargetAtom = new Atom(targetAtom.getMem(), newTargetFunctor);
				sourceAtom.getMem().addAtom(newSourceAtom);
				targetAtom.getMem().addAtom(newTargetAtom);
				
				for(int i = 0; i < sourceAtom.getEdgeCount(); i++){
					sourceAtom.getMem().relink(newSourceAtom, i, sourceAtom, i);
				}
				sourceAtom.remove();
				
				for(int i = 0; i < targetAtom.getEdgeCount(); i++){
					targetAtom.getMem().relink(newTargetAtom, i, targetAtom, i);
				}
				targetAtom.remove();
				
				newSourceAtom.getMem().newLink(newSourceAtom,
						newSourceAtom.getEdgeCount() - 1,
						newTargetAtom,
						newTargetAtom.getEdgeCount() - 1);
				synchronized (sourceNode.getParent().getChildMap()) {
					sourceNode.reset(newSourceAtom);
				}
				synchronized (targetNode.getParent().getChildMap()) {
					targetNode.reset(newTargetAtom);
				}
			}
			newLinkMap_.clear();
		}
//		rootNode_.reset(rootMembrane_);
		resetLink();
		commonListener_.setLog(rootMembrane_.toString());
		
	}
	
	/**
	 * 自動焦点
	 */
	public void autoFocus(){
		autoFocusTh_ = new AutoFocusThread();
		autoFocusTh_.start();
	}
	
	/**
	 * 力学モデルの計算・移動の計算を行う
	 *
	 */
	public void calc(){
		if(null == rootNode_){ return; }
		if(!newNodeMap_.isEmpty()){
			addNode();
		}
		if(!newLinkMap_.isEmpty()){
			addLink();
		}
		if(!removeLinkMap_.isEmpty()){
			removeLink();
		}
		if(!renameAtomMap_.isEmpty()){
			renameAtom();
		}
		rootNode_.calcAll();
		rootNode_.moveAll();
	}
	
	/**
	 * 見えないカーソルを生成する
	 * @return
	 */
	static 
	public Cursor createNullCursor() {
        BufferedImage image = new BufferedImage(16,16, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(0,0,0,0));    // 黒で透明 black & transparency
        g2.fillRect(0,0, 16,16);
        g2.dispose();
        return Toolkit.getDefaultToolkit().createCustomCursor(
                image, new Point(0,0), "null_cursor");
    }

	/** Atomの名前を書き換える */
	public void renameAtom(){
		synchronized (renameAtomMap_) {
			Iterator<Node> nodes = renameAtomMap_.keySet().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				Atom atom = (Atom)node.getObject();
				atom.setName(renameAtomMap_.get(node));
				synchronized (node.getParent().getChildMap()) {
					node.setAtom(atom);
				}
			}
			renameAtomMap_.clear();
		}
	}
	
	/** Membraneの名前を書き換える */
	public void renameMenbrane(){
		synchronized (renameMembraneMap_) {
			Iterator<Node> nodes = renameMembraneMap_.keySet().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				Membrane mem = (Membrane)node.getObject();
				mem.setName(renameMembraneMap_.get(node));
				synchronized (node.getParent().getChildMap()) {
					node.setMembrane(mem);
				}
			}
			renameMembraneMap_.clear();
		}
	}

	/**
	 * 拡大縮小の倍率を取得する
	 * @return
	 */
	static
	public double getMagnification(){
		return magnification_;
	}

	/**
	 * Pinのイメージを取得する
	 * @return
	 */
	public Image getPin(){
		return pin_;
	}
	
	/**
	 * すべての膜を非表示に設定
	 *
	 */
	public void hideAll(){
		rootNode_.setVisible(false, true);
		rootNode_.setInvisibleRootNode(null);
	}

	public void loadState(int value){
		if(rootNodeList_.size() == 0 || value >= rootNodeList_.size() || value < 0){
			return;
		}
		if(value == rootNodeList_.size() - 1){
			rootNode_ = orgRootNode_;
			LinkSet.resetNodes(orgRootNode_);
			commonListener_.setLog(logList_.get(value));
		} else {
			commonListener_.setLog(logList_.get(value));
			Node newNode = rootNodeList_.get(value);
			rootNode_ = newNode;
			LinkSet.resetNodes(newNode);
		}
	}
	
	/**
	 * 局所加熱を行う対象を検出および加熱
	 * @param e
	 */
	public void localHeating(MouseEvent e){
		if(null == rootNode_){ return; }
		nowHeating_ = true;
		setCursor(NULL_CURSOR);
		//　Nodeの当たり判定を行うために、クリックしたPointを範囲（Rectangle）に変換
		int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
		int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
		Rectangle2D rect = new Rectangle2D.Double(pointX - ((FIRE_AREA / 2) / getMagnification()),
				pointY - ((FIRE_AREA / 2) / getMagnification()),
				FIRE_AREA / getMagnification(),
				FIRE_AREA / getMagnification());
		Set<Node> nodeSet = rootNode_.getPointNodes(rect, false);
		Iterator<Node> nodes = nodeSet.iterator();
		while(nodes.hasNext()){
			NodeFunction.setLocalHeating(nodes.next());
		}
	}
	
	public void moveRotate(double angle){
		rootNode_.moveRotate(angle);
	}
	
	/**
	 * グラフを描画する
	 */
	public void paint(Graphics g) {
		if(stopPainting_){
			g.setColor(Color.GRAY);
			g.fillRect(0, 0,(int)getWidth(), (int)getHeight());
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,(int)getWidth(), (int)getHeight());

			g.setColor(Color.WHITE);
			af_.setTransform(getMagnification(),
					0,
					0,
					getMagnification(),
					(double)getWidth() / 2,
					(double)getHeight() / 2);
			((Graphics2D)g).setTransform(af_);

			g.setColor(Color.BLACK);
			if(null != rootNode_){
				synchronized (rootNode_.getChildMap()) {
					LinkView.draw(g, false);
					MembraneView.draw(g, rootNode_, false, this);
				}
			}

			// EditLink時に補助線を引く
			if(editLinkMode_ &&
					null != lastMousePoint &&
					null != moveTargetNode_ &&
					moveTargetNode_ != rootNode_ &&
					moveTargetNode_.getObject() instanceof Atom)
			{
				g.setColor(Color.RED);
				Point2D sourcePoint = moveTargetNode_.getCenterPoint();
				g.drawLine((int)sourcePoint.getX(),
						(int)sourcePoint.getY(),
						(int)((lastMousePoint.getX() - ((double)getWidth() / 2)) / getMagnification()),
						(int)((lastMousePoint.getY() - ((double)getHeight() / 2)) / getMagnification()));
			}

			// 初期位置に戻す
			((Graphics2D)g).setTransform(new AffineTransform());

			if(localHeatingMode_ && nowHeating_){
				Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				g.drawImage(fire_[fireID_],
						mousePoint.x - FIRE_WIDTH_MARGIN,
						mousePoint.y - FIRE_HEIGHT_MARGIN,
						this);
				fireID_ = (fireID_ < 6) ? fireID_ + 1 : 0; 
			}
		}
		int heatingTimer = NodeFunction.getHeatingTime();
		if(0 < heatingTimer){
			g.setColor(Color.RED);
			g.drawString("Heating Timer:" + heatingTimer, 10, 30);
		}
	}
	
	/**
	 * リンクを張りなおす。<p>
	 * 現状は、アトムの再利用が無理。（再利用しつつリンク数をへらせないので）。
	 */
	private void removeLink(){
		synchronized (removeLinkMap_) {
			Iterator<Node> atoms = removeLinkMap_.keySet().iterator();
			while(atoms.hasNext()){
				Node sourceNode = atoms.next();
				Node targetNode = removeLinkMap_.get(sourceNode);
				Atom sourceAtom = (Atom)sourceNode.getObject();
				Atom targetAtom = (Atom)targetNode.getObject();
				int sourceAtomIndex = -1;
				int targetAtomIndex = -1;
				for(int i = 0; i < sourceAtom.getEdgeCount(); i++){
					if(sourceAtom.getNthAtom(i) == targetAtom){
						sourceAtomIndex = i;
						break;
					}
				}
				for(int i = 0; i < targetAtom.getEdgeCount(); i++){
					if(targetAtom.getNthAtom(i) == sourceAtom){
						targetAtomIndex = i;
						break;
					}
				}
				// 対象アトム同士がつながっていない
				if(sourceAtomIndex < 0 || targetAtomIndex < 0){
					continue;
				}
				
				Functor newSourceFunctor =
					new SymbolFunctor(sourceAtom.getName(), sourceAtom.getEdgeCount() - 1);
				Functor newTargetFunctor =
					new SymbolFunctor(targetAtom.getName(), targetAtom.getEdgeCount() - 1);

				Atom newSourceAtom = new Atom(sourceAtom.getMem(), newSourceFunctor);
				Atom newTargetAtom = new Atom(targetAtom.getMem(), newTargetFunctor);
				sourceAtom.getMem().addAtom(newSourceAtom);
				targetAtom.getMem().addAtom(newTargetAtom);

				synchronized (targetNode.getParent().getChildMap()) {
					for(int i = 0, j = 0; i < targetAtom.getEdgeCount(); i++){
						if(i == targetAtomIndex){ continue; }
						targetAtom.getMem().relink(newTargetAtom, j, targetAtom, i);
						j++;
					}
					targetNode.reset(newTargetAtom);
				}
				synchronized (sourceNode.getParent().getChildMap()) {
					// sourceAtomの繋ぎ変え
					for(int i = 0, j = 0; i < sourceAtom.getEdgeCount(); i++){
						if(i == sourceAtomIndex){ continue; }
						sourceAtom.getMem().relink(newSourceAtom, j, sourceAtom, i);
						j++;
					}

					// proxyAtom消し
					Atom proxyAtom = sourceAtom.nthAtom(sourceAtomIndex);
					Set<Atom> removeAtomSet = new HashSet<Atom>();
					while(proxyAtom.getFunctor().isInsideProxy() || proxyAtom.getFunctor().isOutsideProxy()) {
						removeAtomSet.add(proxyAtom);
						proxyAtom = proxyAtom.nthAtom(0).nthAtom(1);
					}
					Iterator<Atom> removeAtoms = removeAtomSet.iterator();
					while(removeAtoms.hasNext()){
						Atom removeAtom = removeAtoms.next();
						removeAtom.remove();
					}
					sourceNode.reset(newSourceAtom);
				}
				sourceAtom.remove();
				targetAtom.remove();
			}
			removeLinkMap_.clear();
		}
//		rootNode_.reset(rootMembrane_);
		resetLink();
		commonListener_.setLog(rootMembrane_.toString());
		
	}
	
	public void revokeState(){
		commonListener_.revokeTime();
		rootNodeList_.clear();
		logList_.clear();
	}
	
	public void resetLink(){
		if(rootNode_ != orgRootNode_){
			rootNode_ = orgRootNode_;
			LinkSet.resetNodes(orgRootNode_);
		}
		boolean success = false;
		while(!success){
			success = rootNode_.setMembrane(rootMembrane_);
		}
	}
	
	public void saveState(){
		rootNode_.setMembrane(rootMembrane_);
		resetLink();
		if(!history_){ return; }
		// 同じ状態であれば記録しない
		if(logList_.size() != 0 &&
				commonListener_.getLog().equals(logList_.get(logList_.size() - 1)))
		{
			return;
		}
		if(0 < rootNodeList_.size()){
			commonListener_.addTime();
		}
		resetLink();
		Map<Node, Node> cloneMap = new HashMap<Node, Node>();
		Node newNode = rootNode_.cloneNode(cloneMap);
		newNode.cloneNodeParm(cloneMap, rootNode_);
		rootNodeList_.add(newNode);
		if(0 == logList_.size()){
			logList_.add(commonListener_.getLog());
		} else {
			logList_.add(rootMembrane_.toString());
		}
	}
	
	public void setEditLinkMode(boolean flag){
		editLinkMode_ = flag;
		if(flag){
			currentCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		} else {
			currentCursor = new Cursor(Cursor.HAND_CURSOR);
		}
		setCursor(currentCursor);
	}
	
	public void setLocalHeatingMode(boolean flag){
		localHeatingMode_ = flag;
		if(flag){
			currentCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		} else {
			currentCursor = new Cursor(Cursor.HAND_CURSOR);
		}
		setCursor(currentCursor);
	}
	
	public void setShowRules(boolean flag){
		rootNode_.setShowRules(flag);
	}
	
	static
	public void setStopPainting(boolean flag){
		stopPainting_ = flag;
	}
	
	public void setHistory(boolean flag){
		history_ = flag;
		if(history_){
			saveState();
		}
	}

	static
	public void setMagnification(double magni){
		magnification_ = magni * 2;
		if(0.01 > magnification_){
			magnification_ = 0.01;
		}
	}

	/**
	 * ルート膜をセットする
	 * @param mem
	 */
	public void setRootMem(Membrane mem){
		rootMembrane_ = mem;
		rootNode_ = new Node(null, mem);
		orgRootNode_ = rootNode_;
	}
	
	public void setTempRootNode(Node node){
		System.out.println(node);
		if(null == node ||
				!(node.getObject() instanceof Membrane) ||
				node.getParent() == null ||
				tmpRootNode_ == node)
		{
			if(null != tmpRootNode_){
				System.out.println("false");
				tmpRootNode_.setRoot(false);
				tmpRootNode_ = null;
			}
			System.out.println("true");
			rootNode_.setRoot(true);
			return;
		}
		System.out.println(node.getObject());
		node.setRoot(true);
		tmpRootNode_ = node;
		rootNode_.setRoot(false);
	}
	
	public void setUncalc(boolean flag){
		rootNode_.setUncalc(flag);
	}
	
	/**
	 * すべての膜を表示に設定
	 *
	 */
	public void showAll(){
		rootNode_.setVisible(true, true);
		rootNode_.setInvisibleRootNode(null);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private class PanelMouseAdapter extends MouseAdapter {
		/** 
		 * マウスが押されたときの処理
		 * 局所加熱モード時は何もしない
		 */
		public void mousePressed(MouseEvent e) {
			if(null == rootNode_){ return; }
			if(localHeatingMode_){
				localHeating(e);
				return;
			}
			//　Nodeの当たり判定を行うために、クリックしたPointを範囲（Rectangle）に変換
			int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
			int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
			Rectangle2D rect = new Rectangle2D.Double(pointX - ((POINT_DELTA_AREA / 2) / getMagnification()),
					pointY - ((POINT_DELTA_AREA / 2) / getMagnification()),
					POINT_DELTA_AREA / getMagnification(),
					POINT_DELTA_AREA / getMagnification());
//			if(e.isAltDown()){
//				moveTargetNode_ = rootNode_.getPointNode(rect, true);
//				setTempRootNode(moveTargetNode_);
//				return;
//				
//			}
			// 可視不可視を反転
			if(e.isControlDown()){
				moveTargetNode_ = rootNode_.getPointNode(rect, true);
				if(null == moveTargetNode_){ return; }
				moveTargetNode_.swapVisible();
				moveTargetNode_.setUncalc(false);
				if(null != moveTargetNode_.getParent()){
					moveTargetNode_.getParent().setUncalcOutSideForce(false);
				}
				moveTargetNode_ = null;
				return;
			}
			
			if(e.getClickCount() == 2){
				moveTargetNode_ = rootNode_.getPointNode(rect, true);
				if(null != moveTargetNode_){
					moveTargetNode_.swapClipped();
				}
				moveTargetNode_ = null;
				return;
			}
			
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
			moveTargetNode_ = rootNode_.getPointNode(rect, true);
			
			/*
			 * 右クリック処理
			 * Nodeのリンクをベジエ曲線化
			 */
			if(!editLinkMode_ && e.getButton() == MouseEvent.BUTTON3){
				if(null == moveTargetNode_){ return; }
				
				NodeFunction.showNodeMenu(moveTargetNode_, myPanel_);
				
				moveTargetNode_ = null;
				return;
			}
			if(null == moveTargetNode_){
				moveTargetNode_ = rootNode_;
			}
			if(editLinkMode_ && moveTargetNode_.getObject() instanceof Atom){
				rootNode_.setUncalc(true);
				return;
			}
			moveTargetNode_.setUncalc(true);
			if(null != moveTargetNode_.getParent()){
				moveTargetNode_.getParent().setUncalcOutSideForce(true);
			}
			if(selectedNode_ != moveTargetNode_ && 
					!moveTargetNode_.isBezNode()){
				moveTargetNode_.setSelected(true);
				if(null != selectedNode_){
					selectedNode_.setSelected(false);
				}
				commonListener_.setSelectedNode(moveTargetNode_);
				selectedNode_ = moveTargetNode_;
			} else if(selectedNode_ == moveTargetNode_){
				moveTargetNode_.setSelected(false);
				selectedNode_ = null;
				commonListener_.setSelectedNode(null);
			}
			deltaX = e.getX() - (moveTargetNode_.getCenterPoint().x * getMagnification());
			deltaY = e.getY() - (moveTargetNode_.getCenterPoint().y * getMagnification());
		}
		
		/**
		 * マウスが離されたときの処理
		 * <p>最初に押されたときより移動していたら移動した距離を取得</p>
		 */
		public void mouseReleased(MouseEvent e) {
			if(editLinkMode_ &&
					null != moveTargetNode_ &&
					moveTargetNode_.getObject() instanceof Atom)
			{
				//　Nodeの当たり判定を行うために、クリックしたPointを範囲（Rectangle）に変換
				int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
				int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
				Rectangle2D rect = new Rectangle2D.Double(pointX - ((POINT_DELTA_AREA / 2) / getMagnification()),
						pointY - ((POINT_DELTA_AREA / 2) / getMagnification()),
						POINT_DELTA_AREA / getMagnification(),
						POINT_DELTA_AREA / getMagnification());
				
				Node targetNode = rootNode_.getPointNode(rect, true);
				if(null != targetNode &&
						targetNode.getObject() instanceof Atom)
				{
					if(e.getButton() == MouseEvent.BUTTON1){
						newLinkMap_.put(moveTargetNode_, targetNode);
					}
					else if(e.getButton() == MouseEvent.BUTTON3){
						removeLinkMap_.put(moveTargetNode_, targetNode);
					}
				}
				rootNode_.setUncalc(false);
			}
			nowHeating_ = false;
			if(null != moveTargetNode_){
				moveTargetNode_.setUncalc(false);
				if(null != moveTargetNode_.getParent()){
					moveTargetNode_.getParent().setUncalcOutSideForce(false);
				}
				moveTargetNode_ = null;
			}
			setCursor(currentCursor);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 演算用スレッド
	 * @author nakano
	 *
	 */
	private class CalcThread extends Thread {
		private boolean runnable_ = true;
		
		public CalcThread() {}
		
		public void run() {
			while(true) {
				try {
					sleep(40);
					if(runnable_){
						calc();
					}
				} catch (Exception e) {
				}
			}
		}
		
		public void setRunnable(boolean runnable){
			runnable_ = runnable;
		}
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * 描画用スレッド
	 * @author nakano
	 *
	 */
	private class RepaintThread extends Thread {
		private boolean runnable_ = true;
		
		public RepaintThread() {}
		
		public void run() {
			while(true) {
				try {
					sleep(50);
					if(runnable_){
						repaint();
					}
				} catch (Exception e) {
				}
			}
		}
		
		public void setRunnable(boolean runnable){
			runnable_ = runnable;
		}
		
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * AutoFocus用スレッド
	 * @author nakano
	 *
	 */
	private class AutoFocusThread extends Thread {
		boolean change_ = true;
		boolean reduction_ = false;
		
		public AutoFocusThread() {}
		
		public void run() {
			while(change_ && autoFocusTh_ == Thread.currentThread()) {
				try {
					sleep(20);
					autoFocus();
				} catch (Exception e) {
				}
			}
		}
		
		
		/**
		 * rect　が最適に表示できるように倍率および位置を調節する。
		 * @param rect
		 */
		public void autoFocus() {
			Rectangle rect = rootNode_.getArea();
			change_ = false;
			// 縮小処理
			if(0.01 < magnification_ &&
					(getHeight() < (rect.height * magnification_) + AUTO_FOCUS_SIZE_DELTA ||
					getWidth() < (rect.width * magnification_) + AUTO_FOCUS_SIZE_DELTA))
			{
				change_ = true;
				reduction_ = true;
				magnification_ -= 0.011;
				double value = (magnification_ / 2) * SubFrame.SLIDER_MAX;
				commonListener_.setMagnificationSliderValue((int)value);
			}
			// 拡大処理
			else if(magnification_ < 2 && 
					!reduction_ &&
					(getHeight() > (rect.height * magnification_) + AUTO_FOCUS_SIZE_DELTA ||
							getWidth() > (rect.width * magnification_) + AUTO_FOCUS_SIZE_DELTA))
			{
				change_ = true;
				reduction_ = false;
				magnification_ += 0.01;
				double value = (magnification_ / 2) * SubFrame.SLIDER_MAX;
				commonListener_.setMagnificationSliderValue((int)value);
			}
			// 移動処理
			if(AUTO_FOCUS_POS_DELTA < Math.abs(rect.getCenterX())){
				change_ = true;
				rootNode_.setPosDelta(-(rect.getCenterX() / 10),
						0);
			}
			if(AUTO_FOCUS_POS_DELTA < Math.abs(rect.getCenterY())){
				change_ = true;
				rootNode_.setPosDelta(0,
						-(rect.getCenterY() / 10));
			}
		}
		
	}
}
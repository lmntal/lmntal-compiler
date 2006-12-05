package gui2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import runtime.Membrane;

public class GraphPanel extends JPanel {

	///////////////////////////////////////////////////////////////////////////
	// static
	
	static
	private double magnification_ = 0.5;
	
	static
	private Image pin_;
	
	///////////////////////////////////////////////////////////////////////////
	
	private Thread calcTh_ = null;
	private Thread repaintTh_ = null;
	private Node moveTargetNode_ = null;
	private Node rootNode_;
	private Membrane rootMembrane_;
	private Node orgRootNode_;
	private AffineTransform af_ = new AffineTransform();
	private double deltaX;
	private double deltaY;
	private CommonListener commonListener_ = new CommonListener(this);
	private List<Node> rootNodeList_ = new ArrayList<Node>();
	private List<String> logList_ = new ArrayList<String>();
	
	///////////////////////////////////////////////////////////////////////////
	
	public GraphPanel() {
		super();
		Node.setPanel(this);
		pin_ = Toolkit.getDefaultToolkit().getImage(getClass().getResource("gabyou.gif"));
		// PINのロード待ち　ここから
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(pin_, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//　PINのロード待ち　ここまで
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addMouseListener(new MouseAdapter() {
			
			/** 
			 * マウスが押されたときの処理
			 */
			public void mousePressed(MouseEvent e) {
				if(null == rootNode_){ return; }
				int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
				int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
				
				// 可視不可視を反転
				if(e.isControlDown()){
					moveTargetNode_ = rootNode_.getPointNode(pointX, pointY, true);
					if(null == moveTargetNode_){ return; }
					moveTargetNode_.swapVisible();
					moveTargetNode_.setUncalc(false);
					moveTargetNode_ = null;
					return;
				}
				
				if(e.getClickCount() == 2){
					moveTargetNode_ = rootNode_.getPointNode(pointX, pointY, true);
					if(null != moveTargetNode_){
						moveTargetNode_.swapClipped();
					}
					moveTargetNode_ = null;
					return;
				}
				
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
				moveTargetNode_ = rootNode_.getPointNode(pointX, pointY, true);
				if(null != moveTargetNode_){
					moveTargetNode_.setUncalc(true);
				} else {
					moveTargetNode_ = rootNode_;
				}
				deltaX = e.getX() - (moveTargetNode_.getCenterPoint().x * getMagnification());
				deltaY = e.getY() - (moveTargetNode_.getCenterPoint().y * getMagnification());
			}
			
			/**
			 * マウスが離されたときの処理
			 * <p>最初に押されたときより移動していたら移動した距離を取得</p>
			 */
			public void mouseReleased(MouseEvent e) {
				if(null != moveTargetNode_){
					moveTargetNode_.setUncalc(false);
					moveTargetNode_ = null;
				}
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			
			/**
			 * マウスがドラッグされたときの処理
			 * <p>移動した距離を取得</p>
			 */
			public void mouseDragged(MouseEvent e) {
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
	
	/**
	 * 力学モデルの計算・移動の計算を行う
	 *
	 */
	public void calc(){
		if(null == rootNode_){ return; }
		if(rootNode_ == orgRootNode_){
			rootNode_.setMembrane(rootMembrane_);
		}
		rootNode_.calcAll();
		rootNode_.moveAll();
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
	
	public void loadPrevState(){
//		if(rootNodeList_.size() == 0 || nowHistoryPos_ == rootNodeList_.size()){
//			return;
//		}
//		nowHistoryPos_++;
//		rootNode_ = rootNodeList_.get(rootNodeList_.size() - nowHistoryPos_);
//		commonListener_.setLog(rootMembrane_.toString());
	}
	
	public void loadNextState(){
//		if(rootNodeList_.size() == 0 || nowHistoryPos_ == 0){
//			return;
//		}
//		nowHistoryPos_--;
//		if(nowHistoryPos_ == 0){
//			rootMembrane_ = orgRootMembrane_;
//			rootNode_ = orgRootNode_;
//			return;
//		}
//		rootNode_ = rootNodeList_.get(rootNodeList_.size() - nowHistoryPos_);
//		commonListener_.setLog(rootMembrane_.toString());
	}

	public void loadState(int value){
		if(rootNodeList_.size() == 0 || value >= rootNodeList_.size() || value < 0){
			return;
		}
		if(value == rootNodeList_.size() - 1){
//			LinkSet.resetNodes(orgRootNode_);
			rootNode_ = orgRootNode_;
			LinkSet.resetNodes(orgRootNode_);
			commonListener_.setLog(logList_.get(value));
		} else {
//			Map<Node, Node> cloneMap = new HashMap<Node, Node>();
//			Node newNode = rootNodeList_.get(value).cloneNode(cloneMap);
			Node newNode = rootNodeList_.get(value);
			commonListener_.setLog(logList_.get(value));
//			newNode.cloneNodeParm(cloneMap, rootNodeList_.get(value)); 
			rootNode_ = newNode;
			LinkSet.resetNodes(newNode);
		}
	}

	/**
	 * グラフを描画する
	 */
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,(int)getWidth(), (int)getHeight());
	
		g.setColor(Color.WHITE);
		af_.setTransform(getMagnification(), 0, 0, getMagnification(), getWidth() / 2, getHeight() / 2);
		((Graphics2D)g).setTransform(af_);
	
		g.setColor(Color.BLACK);
		if(null != rootNode_){
			rootNode_.paint(g);
		}
	
		// 初期位置に戻す
		int divergenceTimer = NodeFunction.getDivergence();
		if(0 < divergenceTimer){
			((Graphics2D)g).setTransform(new AffineTransform());
			g.setColor(Color.RED);
			g.drawString("Divergence Timer:" + divergenceTimer, 10, 30);
		}
	}
	
	public void saveState(){
		if(rootNode_ != orgRootNode_){
			rootNode_ = orgRootNode_;
			LinkSet.resetNodes(orgRootNode_);
		}
		boolean success = false;
		while(!success){
			success = rootNode_.setMembrane(rootMembrane_);
		}
		Map<Node, Node> cloneMap = new HashMap<Node, Node>();
		Node newNode = rootNode_.cloneNode(cloneMap);
		newNode.cloneNodeParm(cloneMap, rootNode_);
		rootNodeList_.add(newNode);
		logList_.add(rootMembrane_.toString());
	}

	public void setMagnification(double magni){
		magnification_ = magni * 2;
	}

	/**
	 * ルート膜をセットする
	 * @param mem
	 */
	public void setRootMem(Membrane mem){
		rootMembrane_ = mem;
//		System.out.println(mem);
		rootNode_ = new Node(null, mem);
		orgRootNode_ = rootNode_;
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
	/**
	 * 演算用スレッド
	 * @author nakano
	 *
	 */
	class CalcThread extends Thread {
		private boolean runnable_ = true;
		
		public CalcThread() {}
		
		public void run() {
			while(runnable_) {
				try {
					sleep(40);
					calc();
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
	class RepaintThread extends Thread {
		private boolean runnable_ = true;
		
		public RepaintThread() {}
		
		public void run() {
			while(runnable_) {
				try {
					sleep(50);
					repaint();
				} catch (Exception e) {
				}
			}
		}
		
		public void setRunnable(boolean runnable){
			runnable_ = runnable;
		}
	}
}
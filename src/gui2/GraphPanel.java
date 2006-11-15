package gui2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import runtime.Membrane;

public class GraphPanel extends JPanel implements Runnable {

	///////////////////////////////////////////////////////////////////////////
	// static
	
	static
	private double magnification_ = 0.5;
	
	static
	private Image pin_;
	
	///////////////////////////////////////////////////////////////////////////
	
	private Thread th_ = null;
	private Image OSI_ = null;
	private Graphics OSG_ = null;
	private Node moveTargetNode_ = null;
	private Node rootNode_;
	private Membrane rootMembrane_;
	private AffineTransform af_ = new AffineTransform();
	private Point lastPoint;
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//　PINのロード待ち　ここまで
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				OSI_ = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
				OSG_ = OSI_.getGraphics();	
			}

		});
		addMouseListener(new MouseAdapter() {
			
			/** 
			 * マウスが押されたときの処理
			 */
			public void mousePressed(MouseEvent e) {
				int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
				int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
				
				// 可視不可視を反転
				if(e.isControlDown()){
					moveTargetNode_ = rootNode_.getPointNode(pointX, pointY, true);
					if(null == moveTargetNode_){ return; }
					moveTargetNode_.swapVisible();
					moveTargetNode_ = null;
					lastPoint = null;
					return;
				}
				
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
				moveTargetNode_ = rootNode_.getPointNode(pointX, pointY, false);
				lastPoint = e.getPoint();
			}
			
			/**
			 * マウスが離されたときの処理
			 * <p>最初に押されたときより移動していたら移動した距離を取得</p>
			 */
			public void mouseReleased(MouseEvent e) {
				moveTargetNode_ = null;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			
			/**
			 * マウスがドラッグされたときの処理
			 * <p>移動した距離を取得</p>
			 */
			public void mouseDragged(MouseEvent e) {
				if(moveTargetNode_ != null){
					int pointX = (int)((e.getX() - (getWidth() / 2)) / getMagnification());
					int pointY = (int)((e.getY() - (getHeight() / 2)) / getMagnification());
					moveTargetNode_.setPos(pointX, pointY);
				} else {
					if(null == lastPoint){ return; }
					rootNode_.setPosDelta(e.getPoint().getX() - lastPoint.getX(),
							e.getPoint().getY() - lastPoint.getY());
					lastPoint = e.getPoint();
				}
			}

		}
		);
		
		start();
	}
	
	public void setRootMem(Membrane mem){
		rootMembrane_ = mem;
		rootNode_ = new Node(null, mem);
	}
	
	public Image getPin(){
		return pin_;
	}
	
	/**
	 * すべての膜を表示に設定
	 *
	 */
	public void showAll(){
		rootNode_.setVisible(true, true);
		rootNode_.setInvisibleRootNode(null);
	}
	
	/**
	 * すべての膜を非表示に設定
	 *
	 */
	public void hideAll(){
		rootNode_.setVisible(false, true);
		rootNode_.setInvisibleRootNode(null);
	}

	public void calc(){
		if(null == rootNode_){ return; }
		rootNode_.reset(rootMembrane_);
		rootNode_.calcAll();
	}

	public void setMagnification(double magni){
		magnification_ = magni * 2;
	}
	
	public double getMagnification(){
		return magnification_;
	}
	
	public void start() {
		if (th_ == null) {
			th_ = new Thread(this);
			th_.start();
		}
	}
	
	public void stop() {
		th_ = null;
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th_) {
			try {
				calc();
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
	
	public void paint(Graphics g) {
		if (OSI_ == null) {
			OSI_ = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
			OSG_ = OSI_.getGraphics();			
		}
		if (OSI_ != null){
			OSG_.setColor(Color.WHITE);
			OSG_.fillRect(0, 0,(int)getWidth(), (int)getHeight());
			
			g.setColor(Color.WHITE);
			g.drawImage(OSI_,0,0,this);
			af_.setTransform(getMagnification(), 0, 0, getMagnification(), getWidth() / 2, getHeight() / 2);
	        ((Graphics2D)g).setTransform(af_);

	        g.setColor(Color.BLACK);
	        if(null != rootNode_){
	        	rootNode_.paint(g);
	        }
		}
	}
}
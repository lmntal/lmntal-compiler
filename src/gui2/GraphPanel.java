package gui2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import runtime.Membrane;

public class GraphPanel extends JPanel implements Runnable {

	private Thread th = null;
	private Image OSI = null;
	private Graphics OSG = null;
	private GraphMembrane rootGraphMembrane;
	private Membrane rootMembrane;
	private GraphAtom moveAtom;
	
	static
	private double magnification = 0.5;
	
	public GraphPanel() {
		super();
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				OSI = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
				OSG = OSI.getGraphics();	
			}

		});
		addMouseListener(new MouseAdapter() {
			
			/** 
			 * マウスが押されたときの処理
			 * <p>押されたらclickedを出力する。座標の取得など。</p>
			 */
			public void mousePressed(MouseEvent e) {
				//determine nearest node
				moveAtom = rootGraphMembrane.getNearestAtom(e.getPoint());
				if(e.getButton() == MouseEvent.BUTTON1){
					if(e.getClickCount() == 2){
						moveAtom.flipClip();
					}
				}
				moveAtom.setHold(true);
			}
			
			/**
			 * マウスが離されたときの処理
			 * <p>最初に押されたときより移動していたら移動した距離を取得</p>
			 */
			public void mouseReleased(MouseEvent e) {
				if(moveAtom==null){ return; }
				moveAtom.setHold(false);
				moveAtom = null;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			
			/**
			 * マウスがドラッグされたときの処理
			 * <p>移動した距離を取得</p>
			 */
			public void mouseDragged(MouseEvent arg0) {
				if(moveAtom==null){ return; }
				moveAtom.setPosition(arg0.getPoint().x, arg0.getPoint().y);
			}

		}
		);
		
		start();
	}
	
	public void setRootMem(Membrane mem){
		rootMembrane = mem;
		rootGraphMembrane = new GraphMembrane(mem, this);
	}
	
	public void calc(){
		if(null == rootGraphMembrane){ return; }
		rootGraphMembrane.resetMembrane(rootMembrane);
	}

	static
	public void setMagnification(double magni){
		magnification = magni;
	}
	
	static
	public double getMagnification(){
		return magnification;
	}
	
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}
	
	public void stop() {
		th = null;
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				calc();
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
	
	public void paint(Graphics g) {
		if (OSI == null) {
			OSI = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
			OSG = OSI.getGraphics();			
		}
		if (OSI != null){
			OSG.setColor(Color.WHITE);
			OSG.fillRect(0,0,(int) getSize().getWidth(), (int) getSize().getHeight());
			g.drawImage(OSI,0,0,this);
			
			if(null != rootGraphMembrane){
				rootGraphMembrane.paint(g);
			}
		}
	}
}
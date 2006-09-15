package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * パネルを描画する
 */
public class GraphPanel extends JPanel implements Runnable {

	private GraphLayout gLayout = null;
	private Thread th = null;
	private Image OSI = null;
	private Graphics OSG = null;
	
	/**
	 * イメージを取得してきて描画する
	 */
	public GraphPanel() {
		super();
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				OSI = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
				OSG = OSI.getGraphics();			
			}

		});
		gLayout = new GraphLayout(this);
	}
	
	public GraphLayout getGraphLayout() {
		return gLayout;
	}
	
	/** スレッドが始まるときの処理
	 * <p>自分をスレッドに入れてgLayoutも動かす</p>
	 */
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
			gLayout.start();
		}
	}
	
	/** スレッドが終わるときの処理 
	 * <p>gLayoutも同時に終了</p>
	 */
	public void stop() {
		th = null;
		gLayout.stop();
	}
	
	/** もし自分がスレッドに入っていたら走っている */
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
	
	/** 
	 * OSIにイメージが入っていなかったらイメージを取得してくる。
	 * 入っていたら城背景の四角を描画のちイメージ(OSI)を貼る。 
	 */
	public void paint(Graphics g) {
		if (OSI == null) {
			OSI = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
			OSG = OSI.getGraphics();			
		}
		if (OSI != null){
			OSG.setColor(Color.WHITE);
			OSG.fillRect(0,0,(int) getSize().getWidth(), (int) getSize().getHeight());
			gLayout.paint(OSG);
			g.drawImage(OSI,0,0,this);
		}
	}
	
//	public Rectangle getPreferredArea() {
//		return gLayout.getPreferredArea();
//	}
	
//	public Dimension getPreferredSize() {
//		return getPreferredArea().getSize(); 	
//	}
}
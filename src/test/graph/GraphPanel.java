package test.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.*;

public class GraphPanel extends JPanel implements Runnable {

	private GraphLayout gLayout = null;
	private Thread th = null;
	private final int NODE_MAX = 10;
	private final int EDGE_MAX = 10;
	private Image OSI = null;
	private Graphics OSG = null;
	
	public GraphPanel() {
		super();
		
		gLayout = new GraphLayout(this);
	}
	
	public GraphLayout getGraphLayout() {
		return gLayout;
	}

	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
			gLayout.start();
		}
	}
	
	public void stop() {
		th = null;
	}
	
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
	
	public Rectangle getPreferredArea() {
		return gLayout.getPreferredArea();
	}
	
	public Dimension getPreferredSize() {
		return getPreferredArea().getSize(); 	
	}
}
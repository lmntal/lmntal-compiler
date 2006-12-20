package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 実行中のLMNtalプログラムを表示するためのLOGウィンドウ
 * @author nakano
 *
 */
public class AdvanceFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = SubFrame.WINDOW_WIDTH;
	
	final static
	public int WINDOW_HIEGHT = LogFrame.WINDOW_HIEGHT;
	
	final static
	public String TITLE = "Advance Panel";
	
	final static
	private Font FONT = new Font("SansSerif", Font.PLAIN, 25);
	
	/////////////////////////////////////////////////////////////////
	
	private LMNtalFrame mainFrame_;
	
	private CommonListener commonListener_ = new CommonListener(this);
	
	private Node selectedNode_;
	
	private AdvancePanel panel_ = new AdvancePanel();
	
	private Thread panelThread_;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public AdvanceFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, LMNtalFrame.WINDOW_HIEGHT);
		
		initComponents();
		
//		setVisible(true);
//		
//		panelThread = new Thread(panel_);
//		panelThread.start();
		
	}
	
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		setTitle(TITLE);
		setLayout(new BorderLayout());
		add(panel_, BorderLayout.CENTER);
	}
	
	public void setSelectedNode(Node node){
		selectedNode_ = node;
	}
	
	

	///////////////////////////////////////////////////////////////////////////
	
	private class AdvancePanel extends JPanel implements Runnable {
		
		public void paint(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HIEGHT);

			if(null == selectedNode_){ return; }
			g.setColor(Color.BLACK);
			g.setFont(FONT);
			g.drawString(selectedNode_.getName(), 10, 20);
			RoundRectangle2D selectedShape = (RoundRectangle2D)selectedNode_.getShape().clone();
			selectedShape.setFrameFromCenter(WINDOW_WIDTH / 2,
					(WINDOW_HIEGHT / 2) - 10,
					((selectedShape.getMaxX() - selectedShape.getMinX()) / 2) + (WINDOW_WIDTH / 2),
					((selectedShape.getMaxY() - selectedShape.getMinY()) / 2) + (WINDOW_HIEGHT / 2) - 10);
			((Graphics2D)g).draw(selectedShape);
		}
		
		public void run() {
			while(true){
				try {
					paint(getGraphics());
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}

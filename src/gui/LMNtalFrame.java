package gui;

import gui.model.Node;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import runtime.Membrane;

public class LMNtalFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH;
	
	final static
	public int WINDOW_HIEGHT;
	
	final static
	public String TITLE = "UNYO UNYO";
	
	final static
	private long SLEEP_TIME = 500;
	
	final static
	private String[] ext = {"*.png"};
	
	
	static {
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width - SubFrame.WINDOW_WIDTH - 100;
		int hieght = screenSize.height - LogFrame.WINDOW_HIEGHT - 50;
		WINDOW_WIDTH = (width < 100) ? 100 : width;
		WINDOW_HIEGHT = (hieght < 50) ? 50 : hieght;
	}
	/////////////////////////////////////////////////////////////////
	
	private int step_ = 1;
	private boolean stopCalc_ = true;
	public boolean running_ = true;
	
	private GraphPanel panel_ = null;
	private EditFrame editFrame_;
	private SubFrame subFrame_;
	private LogFrame logFrame_;
	private Membrane rootMembrane_;
	private List<Node> rootMemList_ = new ArrayList<Node>();

	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopCalc_ = false;
				running_ = false;
			}
		});
		addKeyListener(new GraphPanelKeyListener(this));

		initComponents();
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setVisible(true);
		
		// 管理ウィンドウの生成
		subFrame_ = new SubFrame(this);
		logFrame_ = new LogFrame(this);
		editFrame_ = new EditFrame(this);
		
	}
	/////////////////////////////////////////////////////////////////
	
	/**
	 * すべての膜を非表示に設定
	 *
	 */
	public void hideAll(){
		panel_.hideAll();
	}
	
	private void initComponents() {
		panel_ = new GraphPanel();

		setTitle(TITLE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(panel_), BorderLayout.CENTER);
	}
	
	/**
	 * 処理系本体が計算を続行してよいかの問い合わせ受付。
	 *
	 */
	public void onTrace(){
		if(null != rootMembrane_){
			logFrame_.setLog(rootMembrane_.toString());
		}
		panel_.saveState();
		while(running_) {
			if(!stopCalc_){
				break;
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		step_ = (step_ == 0) ? 0 : step_ - 1;
		stopCalc_ = (step_ == 0) ? true : false;
	}
	
	/**
	 * 倍率をセットする
	 * @param magni
	 */
	public void setMagnification(double magni){
		panel_.setMagnification(magni);	
	}
	
	/**
	 * root膜をセットする
	 * @param mem
	 */
	public void setRootMem(Membrane mem){
		rootMembrane_ = mem;
		panel_.setRootMem(mem);
	}
	
	public void setStep(int step){
		step_ = step;
	}
	
	public void setStopCalc(boolean flag){
		stopCalc_ = flag;
	}
	
	/**
	 * すべての膜を表示に設定
	 *
	 */
	public void showAll(){
		panel_.showAll();
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private class GraphPanelKeyListener implements KeyListener {
		
		private JFrame frame_;
		private boolean s_ = false;
		private boolean a_ = false;
		private boolean v_ = false;
		
		public GraphPanelKeyListener(JFrame frame){
			frame_ = frame;
		}

		public void keyPressed(KeyEvent e) {
			String saveTitle = "Save Graph";
			boolean spSave = false;
			
			switch (e.getKeyCode()) {
			default:
				s_= a_ = v_ = false;
				break;
			case KeyEvent.VK_S:
				s_ = true;
				a_ = v_ = false;
				break;
			case KeyEvent.VK_A:
				if(s_ && !a_ && !v_){
					a_ = true;
				} else {
					s_= a_ = v_ = false;
				}
				break;
			case KeyEvent.VK_V:
				if(s_ && a_ && !v_){
					v_ = true;
				} else {
					s_= a_ = v_ = false;
				}
				break;
			case KeyEvent.VK_E:
				if(!(s_ && a_ && v_)){
					s_= a_ = v_ = false;
					break;
				} else {
					saveTitle = "v('-')";
					spSave = true;
				}
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F12 || spSave){
				s_= a_ = v_ = false;
				spSave = false;
			    BufferedImage image = new BufferedImage((int)panel_.getWidth(),
			    		(int)panel_.getHeight(),
			    		BufferedImage.TYPE_INT_BGR);
			    
			    Graphics graphics = image.createGraphics();
			    graphics.setClip(panel_.getX(), panel_.getY(), (int)panel_.getWidth(), (int)panel_.getHeight());
			    panel_.paint(graphics);
			    
			    FileDialog saveDialog = new FileDialog(frame_, saveTitle , FileDialog.SAVE);
			    saveDialog.setFile("*.png");
			    saveDialog.setVisible(true);

			    if(null == saveDialog.getFile()){
			    	return;
			    }
			    
			    String fileType = "";
			    if(!saveDialog.getFile().endsWith(".png")){
			    	fileType = ".png";
			    }
			    File saveFile = new File(saveDialog.getDirectory() + saveDialog.getFile() + fileType);

			    if(!saveFile.getParentFile().exists()){
			    	System.out.println("not found");
			    	return;
			    }

			    try {
			        ImageIO.write(image, "png", saveFile);
			    } catch (IOException exception) {
			        exception.printStackTrace();
			    }

			}
			
		}

		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {}
		
	}
}
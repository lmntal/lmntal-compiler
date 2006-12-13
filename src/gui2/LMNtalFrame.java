package gui2;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import runtime.Membrane;

public class LMNtalFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = 800;
	
	final static
	public int WINDOW_HIEGHT = 600;
	
	final static
	public String TITLE = "UNYO UNYO";
	
	final static
	private long SLEEP_TIME = 500;
	
	/////////////////////////////////////////////////////////////////
	
	private int step_ = 1;
	private boolean stopCalc_ = true;
	public boolean running_ = true;
	
	private GraphPanel panel_ = null;
	private AdvanceFrame advanceFrame_;
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
		
		addKeyListener(new CommonListener(panel_));
		
		initComponents();
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setVisible(true);
		
		// 管理ウィンドウの生成
		subFrame_ = new SubFrame(this);
		logFrame_ = new LogFrame(this);
		advanceFrame_ = new AdvanceFrame(this);
		
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
}
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

public class LMNtalFrame extends JFrame implements KeyListener {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = 800;
	
	final static
	public int WINDOW_HIEGHT = 600;
	
	final static
	public String TITLE = "It's LMNtal";
	
	final static
	private long SLEEP_TIME = 500;
	
	/////////////////////////////////////////////////////////////////
	
	private int step_ = 1;
	private boolean stopCalc_ = true;
	public boolean running = true;
	
	private GraphPanel panel = null;
	private SubFrame subFrame;
	private LogFrame logFrame;
	private Thread th;
	private Membrane rootMembrane;
	private List<Node> rootMemList = new ArrayList<Node>();
	

	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopCalc_ = false;
				running = false;
			}
		});
		
//		addKeyListener(this);
		
		initComponents();
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setVisible(true);
		
		// 管理ウィンドウの生成
		subFrame = new SubFrame(this);
		logFrame = new LogFrame(this);
		
	}
	/////////////////////////////////////////////////////////////////
	
	/**
	 * すべての膜を非表示に設定
	 *
	 */
	public void hideAll(){
		panel.hideAll();
	}
	
	private void initComponents() {
		panel = new GraphPanel();

		setTitle(TITLE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A){
			if(rootMemList.size() == 0){ return; }
			panel.setRootNode(rootMemList.remove(rootMemList.size() - 1));
			setRootMem(rootMembrane);
			System.out.println(rootMembrane);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			rootMemList.add(panel.getRootNode());
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * 処理系本体が計算を続行してよいかの問い合わせ受付。
	 *
	 */
	public void onTrace(){
		if(null != rootMembrane){
			logFrame.setLog(rootMembrane.toString());
		}
		while(running) {
			if(!stopCalc_){
				break;
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stopCalc_ = (step_ == 0) ? true : false;
		step_--;
	}
	
	/**
	 * 倍率をセットする
	 * @param magni
	 */
	public void setMagnification(double magni){
		panel.setMagnification(magni);	
	}
	
	/**
	 * root膜をセットする
	 * @param mem
	 */
	public void setRootMem(Membrane mem){
		rootMembrane = mem;
		panel.setRootMem(mem);
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
		panel.showAll();
	}
}
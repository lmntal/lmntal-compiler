package gui2;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import runtime.Env;
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
	
	public boolean stopCalc = true;
	public boolean running = true;
	
	private GraphPanel panel = null;
	private SubFrame subFrame;
	private Thread th;
	private Membrane rootMembrane;
	

	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopCalc = false;
				running = false;
			}
		});
		
		initComponents();
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setVisible(true);
		
		// 管理ウィンドウの生成
		subFrame = new SubFrame(this);
		
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		panel = new GraphPanel();

		setTitle(TITLE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
	}
	
	static
	public void setMagnification(double magni){
		GraphPanel.setMagnification(magni);	
	}
	
	public void onTrace(){
		calc();
		while(running) {
			if(!stopCalc){
				break;
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		stopCalc = true;
	}
	
	public void setRootMem(Membrane mem){
		panel.setRootMem(mem);
	}
	
	/**
	 * すべての膜を表示に設定
	 *
	 */
	public void showAll(){
		panel.showAll();
	}
	
	/**
	 * すべての膜を非表示に設定
	 *
	 */
	public void hideAll(){
		panel.hideAll();
	}
	
	public void calc(){
		panel.calc();
	}

	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());
	}
}

class MyThread extends Thread {
	LMNtalFrame f;
	MyThread(LMNtalFrame ff) {
		f = ff;
	}
	
	public void run() {
		while(true) {
			try {
				sleep(4000);
			} catch (Exception e) {
			}
		}
	}
}
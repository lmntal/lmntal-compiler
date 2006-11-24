package gui2;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 実行中のLMNtalプログラムを表示するためのLOGウィンドウ
 * @author nakano
 *
 */
public class LogFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = LMNtalFrame.WINDOW_WIDTH;
	
	final static
	public int WINDOW_HIEGHT = 100;
	
	final static
	public String TITLE = "Log Panel";
	
	/////////////////////////////////////////////////////////////////
	
	private JTextArea logArea = new JTextArea("log");

	private LMNtalFrame mainFrame;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LogFrame(LMNtalFrame f) {
		
		mainFrame = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(0, LMNtalFrame.WINDOW_HIEGHT);
		
		initComponents();
		
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		setTitle(TITLE);
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		add(logArea);
	}
	
	public void setLog(String log){
		logArea.setText(log);
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 実行中のLMNtalプログラムを表示するためのLOGウィンドウ
 * @author nakano
 *
 */
public class LogFrame extends JFrame{

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = LMNtalFrame.WINDOW_WIDTH;
	
	final static
	public int WINDOW_HIEGHT = 200;
	
	final static
	public String TITLE = "Log Panel";
	
	/////////////////////////////////////////////////////////////////
	
	static
	private JTextArea logArea = new JTextArea("log");

	static
	private LMNtalFrame mainFrame;
	
	static
	private JSlider timeSlider_ = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
	
	static
	private CommonListener commonListener_;
	
	static
	private boolean timeSliderResizing_ = false;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LogFrame(LMNtalFrame f) {
		
		mainFrame = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(0, LMNtalFrame.WINDOW_HIEGHT);
		commonListener_ = new CommonListener(this);
		
		initComponents(this);
		setTitle(TITLE);
		
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	public void addTime(){
		timeSliderResizing_ = true;
		timeSlider_.setMaximum(timeSlider_.getMaximum() + 1);
		timeSlider_.setValue(timeSlider_.getMaximum());
		timeSlider_.setMajorTickSpacing(1);
		timeSlider_.setPaintTicks(true);
		timeSliderResizing_ = false;
	}
	
	public String getLog(){
		return logArea.getText();
	}
	
	static
	public void initComponents(Container container) {
		
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		timeSlider_.setSnapToTicks(true);
		timeSlider_.addChangeListener(new LogChangeListener());
		container.setLayout(new BorderLayout());
		container.add(timeSlider_, BorderLayout.NORTH);
		container.add(new JScrollPane(logArea), BorderLayout.CENTER);
	}
	
	public void revokeTime(){
		timeSliderResizing_ = true;
		timeSlider_.setValue(0);
		timeSlider_.setMaximum(0);
		timeSliderResizing_ = false;
	}
	
	public void setLog(String log){
		logArea.setText(log);
	}

	///////////////////////////////////////////////////////////////////////////
	
	static
	private class LogChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			if(timeSliderResizing_){ return; }
			commonListener_.setState(timeSlider_.getValue());
		}
	}
}

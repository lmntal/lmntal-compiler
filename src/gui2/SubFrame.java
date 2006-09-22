package gui2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SubFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = 200;
	
	final static
	public int WINDOW_HIEGHT = 600;
	
	final static
	public String TITLE = "Control Panel";
	
	final static
	private int SLIDER_MIN = 0;
	
	final static
	private int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	/////////////////////////////////////////////////////////////////
	
	// 倍率
	static public int magnification;
	
	private JPanel buttonPanel;
	
	private JButton goBt = new JButton("Go ahead");

	private JButton bt1 = new JButton("OK!?");
	
	private JButton bt2 = new JButton("OK!?");
	
	private JSlider js1 = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	private LMNtalFrame mainFrame;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);
		
		initComponents();
		
		LMNtalFrame.setMagnification((double)js1.getValue() / (double)js1.getMaximum());
		
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		bt1.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bt1.setActionCommand("window");
		
		bt2.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bt2.setActionCommand("window");
		
		goBt.addActionListener(new ActionAdapter(this));

		js1.addChangeListener(new SliderChanged());
		js1.setPaintTicks(true);      //目盛りを表示
		js1.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		js1.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		js1.setLabelTable(js1.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		js1.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

		setTitle(TITLE);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(goBt, BorderLayout.PAGE_START);
		getContentPane().add(js1, BorderLayout.LINE_START);
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
		
		buttonPanel.add(bt1);
		buttonPanel.add(bt2);
		//bt1.addActionListener(new ActionAdapter(this));
	}
	
	public class SliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			LMNtalFrame.setMagnification((double)source.getValue() / (double)source.getMaximum());
		}
	}

	private class ActionAdapter implements ActionListener {
		SubFrame frame;
		ActionAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.stopCalc = false;
		}
	}
}

package gui2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SubFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = 250;
	
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
	
	private JButton goBt = new JButton("Go ahead");
	
	private JButton hideBt = new JButton("Hide All");

	private JButton showBt = new JButton("Show All");

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
		
		mainFrame.setMagnification((double)js1.getValue() / (double)js1.getMaximum());
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		goBt.addActionListener(new ActionAdapter(this));
		showBt.addActionListener(new ShowAllAdapter(this));
		hideBt.addActionListener(new HideAllAdapter(this));

		js1.addChangeListener(new SliderChanged());
		js1.setPaintTicks(true);      //目盛りを表示
		js1.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		js1.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		js1.setLabelTable(js1.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		js1.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

		setTitle(TITLE);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(goBt, gbc);

		gbc.gridy = 1;
		gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
		getContentPane().add(js1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(showBt, gbc);

		gbc.gridx = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(hideBt, gbc);

	}
	
	public class SliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			mainFrame.setMagnification((double)source.getValue() / (double)source.getMaximum());
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
	
	private class ShowAllAdapter implements ActionListener {
		SubFrame frame;
		ShowAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.showAll();
		}
	}
	
	private class HideAllAdapter implements ActionListener {
		SubFrame frame;
		HideAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.hideAll();
		}
	}
	
}

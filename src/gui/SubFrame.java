package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	public int SLIDER_MIN = 0;
	
	final static
	public int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	/////////////////////////////////////////////////////////////////
	
	// 倍率
	static
	public int magnification_;
	
	private JButton goBt_ = new JButton("Go ahead");
	
	private JButton hideBt_ = new JButton("Hide All");

	private JButton showBt_ = new JButton("Show All");

	private JButton heatBt_ = new JButton("Heat");

	private JButton stopHeatingBt_ = new JButton("Stop Heating");

	private JButton autoFocusBt_ = new JButton("Auto Focues");
	
	private JScrollPane menuScroll_;
	
	private JPanel menuPanel_ = new JPanel();
	
	private JTextField  stepBox_ = new JTextField("1");
	
	private JCheckBox linkNumCheck_ = new JCheckBox("Show Link Number");
	
	private JCheckBox historyCheck_ = new JCheckBox("Take History");
	
	private JCheckBox showFullNameCheck_ = new JCheckBox("Show Full Name");
	
	private JCheckBox showRulesCheck_ = new JCheckBox("Show Rules");
	
	private JCheckBox springCheck_ = new JCheckBox("Calc Spring");
	
	private JCheckBox angleCheck_ = new JCheckBox("Calc Angle");

	private JCheckBox attractionCheck_ = new JCheckBox("Calc Attraction");
	
	private JCheckBox repulsiveCheck_ = new JCheckBox("Calc Replusive");

	private JSlider zoomSlider_ = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	private LMNtalFrame mainFrame_;
	
	private CommonListener commonListener_ = new CommonListener(this);
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);
		
		initComponents();
		
		mainFrame_.setMagnification((double)zoomSlider_.getValue() / (double)zoomSlider_.getMaximum());
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	
	public int getSliderValue(){
		return zoomSlider_.getValue();
	}
	
	private void initComponents() {
		
		goBt_.addActionListener(new GoActionAdapter(this));
		showBt_.addActionListener(new ShowAllAdapter(this));
		hideBt_.addActionListener(new HideAllAdapter(this));
		heatBt_.addActionListener(new HeatupAdapter());
		stopHeatingBt_.addActionListener(new StopHeatingAdapter());
		autoFocusBt_.addActionListener(new AutoFocusgAdapter());
		stepBox_.setHorizontalAlignment(JTextField.RIGHT);
		
		
		///////////////////////////////////////////////////////////////////////
		// メニューを追加する処理
		
		angleCheck_.addItemListener(new AngleAdapter());
		angleCheck_.setSelected(true);
		linkNumCheck_.addItemListener(new LinkNumAdapter());
		linkNumCheck_.setSelected(false);
		historyCheck_.addItemListener(new HistoryAdapter());
		historyCheck_.setSelected(false);
		showFullNameCheck_.addItemListener(new ShowFullNameAdapter());
		showFullNameCheck_.setSelected(true);
		showRulesCheck_.addItemListener(new ShowRulesAdapter());
		showRulesCheck_.setSelected(false);
		springCheck_.addItemListener(new SpringAdapter());
		springCheck_.setSelected(true);
		repulsiveCheck_.addItemListener(new RepulsiveAdapter());
		repulsiveCheck_.setSelected(true);
		attractionCheck_.addItemListener(new AttractionAdapter());
		attractionCheck_.setSelected(false);
		menuPanel_.setLayout(new BoxLayout(menuPanel_, BoxLayout.PAGE_AXIS));
		menuPanel_.add(historyCheck_);
		menuPanel_.add(linkNumCheck_);
		menuPanel_.add(showFullNameCheck_);
		menuPanel_.add(showRulesCheck_);
		menuPanel_.add(angleCheck_);
		menuPanel_.add(attractionCheck_);
		menuPanel_.add(repulsiveCheck_);
		menuPanel_.add(springCheck_);
		menuPanel_.addMouseWheelListener(commonListener_);
		
		menuScroll_ = new JScrollPane(menuPanel_);
		///////////////////////////////////////////////////////////////////////
		
		zoomSlider_.addChangeListener(new SliderChanged());
		zoomSlider_.setPaintTicks(true);      //目盛りを表示
		zoomSlider_.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		zoomSlider_.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		zoomSlider_.setLabelTable(zoomSlider_.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		zoomSlider_.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示
		getContentPane().addMouseWheelListener(commonListener_);

		setTitle(TITLE);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		getContentPane().add(stepBox_, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(goBt_, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
		getContentPane().add(zoomSlider_, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(showBt_, gbc);

		gbc.gridx = 2;
		getContentPane().add(hideBt_, gbc);

		gbc.gridx = 1;
		gbc.gridy += 1;
		gbc.gridwidth = 1;
		getContentPane().add(heatBt_, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 1;
		getContentPane().add(stopHeatingBt_, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		gbc.gridy += 1;
		getContentPane().add(autoFocusBt_, gbc);
		
		gbc.gridy += 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 100.0;
		getContentPane().add(menuScroll_, gbc);

	}

	public void setSliderValue(int value){
		zoomSlider_.setValue(value);
	}
	
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * ばねモデルの有効・無効化
	 * @author nakano
	 *
	 */
	private class AngleAdapter implements ItemListener {
		public AngleAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setAngleFlag(angleCheck_.isSelected());
		}
	}
	
	/**
	 * 引力計算の有効・無効化
	 * @author nakano
	 *
	 */
	private class AttractionAdapter implements ItemListener {
		public AttractionAdapter() { }

		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setAttractionFlag(attractionCheck_.isSelected());
		}
	}

	/**
	 * AutoFocus処理開始
	 * @author nakano
	 *
	 */
	private class AutoFocusgAdapter implements ActionListener {
		public AutoFocusgAdapter() { }
		public void actionPerformed(ActionEvent e) {
			commonListener_.autoFocus();
		}
	}

	/**
	 * 加熱処理開始
	 * @author nakano
	 *
	 */
	private class HeatupAdapter implements ActionListener {
		public HeatupAdapter() { }
		public void actionPerformed(ActionEvent e) {
			NodeFunction.setHeatup();
		}
	}

	/**
	 * 計算続行ボタン
	 * @author nakano
	 *
	 */
	private class GoActionAdapter implements ActionListener {
		private SubFrame frame;
		public GoActionAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				frame.mainFrame_.setStep(Integer.parseInt(stepBox_.getText()));
			} catch (NumberFormatException e1) {
				frame.mainFrame_.setStep(1);
			}
			frame.mainFrame_.setStopCalc(false);
		}
	}
	
	/**
	 * すべての膜を非表示化
	 * @author nakano
	 *
	 */
	private class HideAllAdapter implements ActionListener {
		private SubFrame frame;
		public HideAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame_.hideAll();
		}
	}
	
	private class HistoryAdapter implements ItemListener {
		public HistoryAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			commonListener_.setHistory(historyCheck_.isSelected());
		}
	}
	
	/**
	 * リンク番号の表示
	 * @author nakano
	 *
	 */
	private class LinkNumAdapter implements ItemListener {
		public LinkNumAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			LinkSet.setShowLinkNum(linkNumCheck_.isSelected());
		}
	}
	
	/**
	 * 斥力計算の有効・無効化
	 * @author nakano
	 *
	 */
	private class RepulsiveAdapter implements ItemListener {
		public RepulsiveAdapter() { }
		
		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setRepulsiveFlag(repulsiveCheck_.isSelected());
		}
	}
	
	/**
	 * すべての膜を表示化
	 * @author nakano
	 *
	 */
	private class ShowAllAdapter implements ActionListener {
		private SubFrame frame;
		public ShowAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame_.showAll();
		}
	}
	
	/**
	 * アトム名をすべて表示化
	 * @author nakano
	 *
	 */
	private class ShowFullNameAdapter implements ItemListener {
		public ShowFullNameAdapter() { }
		public void itemStateChanged(ItemEvent e) {
			Node.setShowFullName(showFullNameCheck_.isSelected());
		}
	}
	
	/**
	 * アトム名をすべて表示化
	 * @author nakano
	 *
	 */
	private class ShowRulesAdapter implements ItemListener {
		public ShowRulesAdapter() { }
		public void itemStateChanged(ItemEvent e) {
			commonListener_.setShowRules(showRulesCheck_.isSelected());
		}
	}
	
	/**
	 * 倍率変更のスライダー
	 * @author nakano
	 *
	 */
	private class SliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			mainFrame_.setMagnification((double)source.getValue() / (double)source.getMaximum());
		}
	}
	/**
	
	 * ばねモデルの有効・無効化
	 * @author nakano
	 *
	 */
	private class SpringAdapter implements ItemListener {
		public SpringAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setSpringFlag(springCheck_.isSelected());
		}
	}

	/**
	 * 加熱処理終了
	 * @author nakano
	 *
	 */
	private class StopHeatingAdapter implements ActionListener {
		public StopHeatingAdapter() { }
		public void actionPerformed(ActionEvent e) {
			NodeFunction.stopHeating();
		}
	}
	
}

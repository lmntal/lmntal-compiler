package gui;

import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SubFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数

	
	final static
	private int ANGLE_DEFAULT = 30;
	
	final static
	private int ANGLE_MAX = 100;
	
	final static
	private int ANGLE_MIN = 0;
	
	final static
	public int WINDOW_WIDTH = 260;
	
	final static
	public int WINDOW_HIEGHT = LMNtalFrame.WINDOW_HIEGHT;
	
	final static
	public String TITLE = "Control Panel";
	
	final static
	public int SLIDER_MIN = 0;
	
	final static
	public int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	final static
	private int SPRING_DEFAULT = 20;
	
	final static
	private int SPRING_MAX = 200;
	
	final static
	private int SPRING_MIN = 0;
	
	/////////////////////////////////////////////////////////////////
	
	// 倍率
	static
	public int magnification_;
	
	static
	private JCheckBox advanceModeCheck_ = new JCheckBox("Advance Menu");
	
	static
	private JButton goBt_ = new JButton("Go ahead");
	
	
	static
	private JButton hideBt_ = new JButton("Hide All");

	
	static
	private JButton showBt_ = new JButton("Show All");

	
	static
	private JButton heatBt_ = new JButton("Heat");

	static
	private JButton stopHeatingBt_ = new JButton("Stop Heating");

	static
	private JButton autoFocusBt_ = new JButton("Auto Focus");
	
	static
	private JToggleButton localHeatingBt_ = new JToggleButton("Normal Mode");
	
	static
	private JScrollPane menuScroll_;
	
	static
	private JPanel menuPanel_ = new JPanel();
	
	static
	private JTextField  stepBox_ = new JTextField("1");
	
	static
	private JCheckBox linkNumCheck_ = new JCheckBox("Show Link Number");
	
	static
	private JCheckBox historyCheck_ = new JCheckBox("Take History");
	
	static
	private JCheckBox richCheck_ = new JCheckBox("Rich Mode");
	
	static
	private JCheckBox showFullNameCheck_ = new JCheckBox("Show Full Name");
	
	static
	private JCheckBox showRulesCheck_ = new JCheckBox("Show Rules");
	
	static
	private JCheckBox springCheck_ = new JCheckBox("Calc Spring");
	
	static
	private JSlider springSlider_ = new JSlider(SPRING_MIN, SPRING_MAX, SPRING_DEFAULT);
	
	static
	private JCheckBox angleCheck_ = new JCheckBox("Calc Angle");
	
	static
	private JSlider angleSlider_ =
		new JSlider(ANGLE_MIN, ANGLE_MAX, ANGLE_DEFAULT);

	static
	private JCheckBox attractionCheck_ = new JCheckBox("Calc Attraction");
	
	static
	private JCheckBox repulsiveCheck_ = new JCheckBox("Calc Repulsive");

	static
	private JSlider zoomSlider_ = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	static
	private LMNtalFrame mainFrame_;
	
	static
	private CommonListener commonListener_;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		commonListener_ = new CommonListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);

		initListener();
		initComponents(getContentPane());
		
		mainFrame_.setMagnification((double)zoomSlider_.getValue() / (double)zoomSlider_.getMaximum());
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	
	public int getSliderValue(){
		return zoomSlider_.getValue();
	}
	
	private void initListener(){
		goBt_.addActionListener(new GoActionAdapter());
		showBt_.addActionListener(new ShowAllAdapter());
		hideBt_.addActionListener(new HideAllAdapter());
		heatBt_.addActionListener(new HeatupAdapter());
		stopHeatingBt_.addActionListener(new StopHeatingAdapter());
		autoFocusBt_.addActionListener(new AutoFocusgAdapter());
		menuPanel_.addMouseWheelListener(commonListener_);
		addMouseWheelListener(commonListener_);
	}
	
	static
	public void initComponents(Container container) {
		
		localHeatingBt_.addActionListener(new LocalHeatingAdapter());
		stepBox_.setHorizontalAlignment(JTextField.RIGHT);
		
		
		///////////////////////////////////////////////////////////////////////
		// メニューを追加する処理
		advanceModeCheck_.addItemListener(new AdvanceAdapter());
		advanceModeCheck_.setSelected(false);
		Dimension preferredSize = advanceModeCheck_.getPreferredSize();
		
		angleCheck_.addItemListener(new AngleAdapter());
		angleCheck_.setSelected(true);
		angleSlider_.addChangeListener(new AngleSliderChanged());
		angleSlider_.setPreferredSize(preferredSize);
		linkNumCheck_.addItemListener(new LinkNumAdapter());
		linkNumCheck_.setSelected(false);
		historyCheck_.addItemListener(new HistoryAdapter());
		historyCheck_.setSelected(false);
		richCheck_.addItemListener(new RichAdapter());
		richCheck_.setSelected(false);
		showFullNameCheck_.addItemListener(new ShowFullNameAdapter());
		showFullNameCheck_.setSelected(true);
		showRulesCheck_.addItemListener(new ShowRulesAdapter());
		showRulesCheck_.setSelected(false);
		springCheck_.addItemListener(new SpringAdapter());
		springCheck_.setSelected(true);
		springSlider_.addChangeListener(new SpringSliderChanged());
		springSlider_.setPreferredSize(preferredSize);
		repulsiveCheck_.addItemListener(new RepulsiveAdapter());
		repulsiveCheck_.setSelected(true);
		attractionCheck_.addItemListener(new AttractionAdapter());
		attractionCheck_.setSelected(false);
		menuPanel_.setLayout(new BoxLayout(menuPanel_, BoxLayout.PAGE_AXIS));
		menuPanel_.add(advanceModeCheck_);
		menuPanel_.add(richCheck_);
		menuPanel_.add(historyCheck_);
		menuPanel_.add(linkNumCheck_);
		menuPanel_.add(showFullNameCheck_);
		menuPanel_.add(showRulesCheck_);
		menuPanel_.add(angleCheck_);
		menuPanel_.add(angleSlider_);
		menuPanel_.add(attractionCheck_);
		menuPanel_.add(repulsiveCheck_);
		menuPanel_.add(springCheck_);
		menuPanel_.add(springSlider_);
		angleSlider_.setVisible(false);
		springSlider_.setVisible(false);
		
		menuScroll_ = new JScrollPane(menuPanel_);
		///////////////////////////////////////////////////////////////////////
		
		zoomSlider_.addChangeListener(new ZoomSliderChanged());
		zoomSlider_.setPaintTicks(true);      //目盛りを表示
		zoomSlider_.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		zoomSlider_.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		zoomSlider_.setLabelTable(zoomSlider_.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		zoomSlider_.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

		if(container instanceof JFrame){
			((JFrame)container).setTitle(TITLE);
		}
		container.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		container.add(stepBox_, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		container.add(goBt_, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
		container.add(zoomSlider_, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		container.add(showBt_, gbc);

		gbc.gridx = 2;
		container.add(hideBt_, gbc);

		gbc.gridx = 1;
		gbc.gridy += 1;
		gbc.gridwidth = 1;
		container.add(heatBt_, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 1;
		container.add(stopHeatingBt_, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		gbc.gridy += 1;
		container.add(autoFocusBt_, gbc);
		
		gbc.gridy += 1;
		container.add(localHeatingBt_, gbc);
		
		gbc.gridy += 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 100.0;
		container.add(menuScroll_, gbc);

	}
	
	static
	public void setAdvanceMode(boolean flag){
		angleSlider_.setVisible(flag);
		springSlider_.setVisible(flag);
		if(!flag){
			angleSlider_.setValue(ANGLE_DEFAULT);
			springSlider_.setValue(SPRING_DEFAULT);
		}
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
	static
	private class AdvanceAdapter implements ItemListener {
		public AdvanceAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			setAdvanceMode(advanceModeCheck_.isSelected());
		}
	}
	
	/**
	 * ばねモデルの有効・無効化
	 * @author nakano
	 *
	 */
	static
	private class AngleAdapter implements ItemListener {
		public AngleAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setAngleFlag(angleCheck_.isSelected());
		}
	}
	
	static
	private class AngleSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			NodeFunction.setConstantAngle(source.getValue());
		}
	}
	
	/**
	 * 引力計算の有効・無効化
	 * @author nakano
	 *
	 */
	static
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
	static
	private class AutoFocusgAdapter implements ActionListener {
		public AutoFocusgAdapter() { }
		public void actionPerformed(ActionEvent e) {
			commonListener_.autoFocus();
		}
	}

	/**
	 * LocalHeatingMode切り替え処理開始
	 * @author nakano
	 *
	 */
	static
	private class LocalHeatingAdapter implements ActionListener {
		public LocalHeatingAdapter() { }
		public void actionPerformed(ActionEvent e) {
			commonListener_.setLocalHeatingMode(localHeatingBt_.isSelected());
			if(localHeatingBt_.isSelected()){
				localHeatingBt_.setText("Local Heating Mode");
			} else {
				localHeatingBt_.setText("Normal Mode");
			}
		}
	}

	/**
	 * 加熱処理開始
	 * @author nakano
	 *
	 */
	static
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
	static
	private class GoActionAdapter implements ActionListener {
		public GoActionAdapter() { }
		public void actionPerformed(ActionEvent e) {
			try {
				mainFrame_.setStep(Integer.parseInt(stepBox_.getText()));
			} catch (NumberFormatException e1) {
				mainFrame_.setStep(1);
			}
			mainFrame_.setStopCalc(false);
		}
	}
	
	/**
	 * すべての膜を非表示化
	 * @author nakano
	 *
	 */
	static
	private class HideAllAdapter implements ActionListener {
		public HideAllAdapter() { }
		public void actionPerformed(ActionEvent e) {
			mainFrame_.hideAll();
		}
	}

	static
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
	static
	private class LinkNumAdapter implements ItemListener {
		public LinkNumAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			LinkSet.setShowLinkNum(linkNumCheck_.isSelected());
		}
	}
	static
	private class RichAdapter implements ItemListener {
		public RichAdapter() { }
		public void itemStateChanged(ItemEvent e) {
			Node.setRichMode(richCheck_.isSelected());
		}
	}
	
	/**
	 * 斥力計算の有効・無効化
	 * @author nakano
	 *
	 */
	static
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
	static
	private class ShowAllAdapter implements ActionListener {
		public ShowAllAdapter() { }
		public void actionPerformed(ActionEvent e) {
			mainFrame_.showAll();
		}
	}
	
	/**
	 * アトム名をすべて表示化
	 * @author nakano
	 *
	 */
	static
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
	static
	private class ShowRulesAdapter implements ItemListener {
		public ShowRulesAdapter() { }
		public void itemStateChanged(ItemEvent e) {
			commonListener_.setShowRules(showRulesCheck_.isSelected());
		}
	}
	
	/**
	 * ばねモデルの有効・無効化
	 * @author nakano
	 *
	 */
	static
	private class SpringAdapter implements ItemListener {
		public SpringAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			NodeFunction.setSpringFlag(springCheck_.isSelected());
		}
	}
	
	static
	private class SpringSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			NodeFunction.setConstantSpring(source.getValue());
		}
	}

	/**
	 * 加熱処理終了
	 * @author nakano
	 *
	 */
	static
	private class StopHeatingAdapter implements ActionListener {
		public StopHeatingAdapter() { }
		public void actionPerformed(ActionEvent e) {
			NodeFunction.stopHeating();
		}
	}
	
	/**
	 * 倍率変更のスライダー
	 * @author nakano
	 *
	 */
	static
	private class ZoomSliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if(null == mainFrame_){
				return;
			}
			JSlider source = (JSlider)e.getSource();
			mainFrame_.setMagnification((double)source.getValue() / (double)source.getMaximum());
		}
	}
	
}

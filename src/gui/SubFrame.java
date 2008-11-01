package gui;

import gui.model.Node;
import gui.model.forces.AngleForce;
import gui.model.forces.AttractionForce;
import gui.model.forces.NodeFunction;
import gui.model.forces.RepulsiveForce;
import gui.model.forces.SpringForce;
import gui.view.LinkView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

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
import javax.swing.event.MouseInputListener;

public class SubFrame extends JPanel {

	/////////////////////////////////////////////////////////////////
	// 定数

	
	final static
	private int ANGLE_DEFAULT = 30;
	
	final static
	private int ANGLE_MAX = 500;
	
	final static
	private int ANGLE_MIN = 0;
	
	final static
	public int WINDOW_WIDTH = 260;
	
	final static
	public int WINDOW_HEIGHT = LMNtalFrame.WINDOW_HEIGHT-LogFrame.WINDOW_HEIGHT;
	
	final static
	public String TITLE = "Control Panel";
	
	final static
	public int SLIDER_MIN = 0;
	
	final static
	public int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	final static
	public int ATTRACTION_MIN = 0;
	
	final static
	public int ATTRACTION_MAX = 10000;
	
	final static
	private int ATTRACTION_DEFAULT = 1000;
	
	final static
	private int SPRING_DEFAULT = 20;
	
	final static
	private int SPRING_MAX = 200;
	
	final static
	private int SPRING_MIN = 0;
	
	final static
	private int REPULSIVE_MIN = 0;
	
	final static
	private int REPULSIVE_MAX = 100;
	
	final static
	private int REPULSIVE_DEFAULT = 50;

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
	private JButton modeBt_ = new JButton("Normal Mode");
	
	static
	private JScrollPane menuScroll_;
	
	static
	private JPanel menuPanel_ = new JPanel();
	
	static
	private JTextField  stepBox_ = new JTextField("1");
	
	static
	private JCheckBox linkNumCheck_ = new JCheckBox("Show Link Number");
	
	static
	private JCheckBox atomSizeCheck_ = new JCheckBox("Change Atom Size");
	
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
	private JSlider attractionSlider_ = new JSlider(ATTRACTION_MIN, ATTRACTION_MAX, ATTRACTION_DEFAULT);

	static
	private JCheckBox stopPainting_ = new JCheckBox("Stop painting");
	
	static
	private JCheckBox angleCheck_ = new JCheckBox("Calc Angle");
	
	static
	private JSlider angleSlider_ =
		new JSlider(ANGLE_MIN, ANGLE_MAX, ANGLE_DEFAULT);
	
	static
	private JSlider repulsiveSlider_ =
		new JSlider(REPULSIVE_MIN, REPULSIVE_MAX, REPULSIVE_DEFAULT);

	static
	private JCheckBox attractionCheck_ = new JCheckBox("Calc Attraction");
	
	static
	private JCheckBox repulsiveCheck_ = new JCheckBox("Calc Repulsive");
	
	// break point
	static
	private JCheckBox breakPointCheck_ = new JCheckBox("Break Point"); 

	static
	private JSlider zoomSlider_ = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	static
	private LMNtalFrame mainFrame_;
	
	static
	private Commons commonListener_;
	
	static
	private WheelPanel wheelPanel_ = new WheelPanel();
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		commonListener_ = new Commons(this);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);

		initListener();
//		initComponents(getContentPane());
		initComponents(this);
			
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
		
		modeBt_.addActionListener(new LocalHeatingAdapter());
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
		atomSizeCheck_.addItemListener(new AtomSizeAdapter());
		atomSizeCheck_.setSelected(false);
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
		stopPainting_.addItemListener(new StopPaintingAdapter());
		stopPainting_.setSelected(false);
		springSlider_.addChangeListener(new SpringSliderChanged());
		springSlider_.setPreferredSize(preferredSize);
		repulsiveCheck_.addItemListener(new RepulsiveAdapter());
		repulsiveCheck_.setSelected(true);
		repulsiveSlider_.addChangeListener(new RepulsiveSliderChanged());
		repulsiveSlider_.setPreferredSize(preferredSize);
		attractionCheck_.addItemListener(new AttractionAdapter());
		attractionCheck_.setSelected(false);
		attractionSlider_.addChangeListener(new AttractionSliderChanged());
		attractionSlider_.setPreferredSize(preferredSize);
		breakPointCheck_.addItemListener(new BreakPointAdapter());
		breakPointCheck_.setSelected(false);
		menuPanel_.setLayout(new BoxLayout(menuPanel_, BoxLayout.PAGE_AXIS));
		menuPanel_.add(advanceModeCheck_);
		menuPanel_.add(stopPainting_);
//		menuPanel_.add(richCheck_);
		menuPanel_.add(historyCheck_);
		menuPanel_.add(linkNumCheck_);
		menuPanel_.add(atomSizeCheck_);
		menuPanel_.add(showFullNameCheck_);
		menuPanel_.add(showRulesCheck_);
		menuPanel_.add(angleCheck_);
		menuPanel_.add(angleSlider_);
		menuPanel_.add(attractionCheck_);
		menuPanel_.add(attractionSlider_);
		menuPanel_.add(repulsiveCheck_);
		menuPanel_.add(repulsiveSlider_);
		menuPanel_.add(springCheck_);
		menuPanel_.add(springSlider_);
		menuPanel_.add(breakPointCheck_);
		menuPanel_.add(wheelPanel_);
		angleSlider_.setVisible(false);
		springSlider_.setVisible(false);
		attractionSlider_.setVisible(false);
		repulsiveSlider_.setVisible(false);
		
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
		container.add(modeBt_, gbc);
		
		gbc.gridy += 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 100.0;
		container.add(menuScroll_, gbc);
		
		wheelPanel_.repaint();

	}
	
	static
	public void setAdvanceMode(boolean flag){
		angleSlider_.setVisible(flag);
		springSlider_.setVisible(flag);
		attractionSlider_.setVisible(flag);
		repulsiveSlider_.setVisible(flag);
		if(!flag){
			angleSlider_.setValue(ANGLE_DEFAULT);
			springSlider_.setValue(SPRING_DEFAULT);
			attractionSlider_.setValue(ATTRACTION_DEFAULT);
			repulsiveSlider_.setValue(REPULSIVE_DEFAULT);
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
			AngleForce.setAngleFlag(angleCheck_.isSelected());
		}
	}
	
	static
	private class AngleSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			AngleForce.setConstantAngle(source.getValue());
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
			AttractionForce.setAttractionFlag(attractionCheck_.isSelected());
		}
	}

	/**
	 * ブレークポイント
	 * @author saito
	 *
	 */
	static
	private class BreakPointAdapter implements ItemListener {
		public BreakPointAdapter() { }

		public void itemStateChanged(ItemEvent e) {
			BreakPoint.setBreakPointFlag(breakPointCheck_.isSelected());
		}
	}
	
	static 
	public void setBreakPointCheck(boolean f){
		breakPointCheck_.setSelected(f);
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
			if(modeBt_.getText().equals("Normal Mode")){
				commonListener_.setLocalHeatingMode(true);
				modeBt_.setText("Local Heating Mode");
			} else if(modeBt_.getText().equals("Local Heating Mode")){
				commonListener_.setLocalHeatingMode(false);
				commonListener_.setEditLinkMode(true);
				modeBt_.setText("Edit Link Mode");
			} else {
				commonListener_.setEditLinkMode(false);
				modeBt_.setText("Normal Mode");
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
			try{
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
			LinkView.setShowLinkNum(linkNumCheck_.isSelected());
		}
	}
	/**
	 * アトムサイズをリンク数によって変える
	 * @author wakako
	 *
	 */
	static
	private class AtomSizeAdapter implements ItemListener {
		public AtomSizeAdapter() { } 
		
		public void itemStateChanged(ItemEvent e) {
			Node.changeAtomSize(atomSizeCheck_.isSelected());
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
			RepulsiveForce.setRepulsiveFlag(repulsiveCheck_.isSelected());
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
			SpringForce.setSpringFlag(springCheck_.isSelected());
		}
	}
	
	static
	private class SpringSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			SpringForce.setConstantSpring(source.getValue());
		}
	}
	
	
	static
	private class AttractionSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			AttractionForce.setConstantAttraction(source.getValue());
		}
	}
	
	static
	private class RepulsiveSliderChanged implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			RepulsiveForce.setConstantRepulsive(source.getValue());
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
	 * 描画処理中断処理終了
	 * @author nakano
	 *
	 */
	static
	private class StopPaintingAdapter implements ItemListener {
		public StopPaintingAdapter() { }
		public void itemStateChanged(ItemEvent e) {
			GraphPanel.setStopPainting(stopPainting_.isSelected());
		}
	}
	
	static
	private class WheelPanel extends JPanel implements MouseInputListener {
		final static
		private int WHEEL_SIZE = 75;
		private Image wheel_;
		private double theta_ = 0;
		private double lastTheta_ = 0;
		private int startPoint_;
		
		public WheelPanel(){
			wheel_ = Toolkit.getDefaultToolkit().getImage(getClass().getResource("wheel.gif"));
			this.setBackground(Color.GRAY);
			this.setOpaque(false);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		@Override
		public void paint(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
			int x = (getWidth() - WHEEL_SIZE) / 4;
			g2d.rotate(theta_, (WHEEL_SIZE /2) + x , WHEEL_SIZE / 2);
			g2d.drawImage(wheel_, x, 0, WHEEL_SIZE, WHEEL_SIZE, this);
		}

		public void mouseDragged(MouseEvent e) {
			Rectangle rect = getBounds();
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			double dx = rect.getCenterX() - mousePoint.getX();
			double dy = rect.getCenterY() - mousePoint.getY();
			if(dx == 0.0) { dx = 0.00001; }
			theta_ = Math.atan(dy / dx);
			System.out.println(theta_);
			theta_ = (startPoint_ + MouseInfo.getPointerInfo().getLocation().x) % 360;
//			if(0 < Math.abs(theta_ - lastTheta_)){
//				commonListener_.moveRotate(((double)(theta_ - lastTheta_)) / 10);
//				lastTheta_ = theta_;
//			}
			commonListener_.moveRotate(theta_ - lastTheta_);
			lastTheta_ = theta_;
			repaint();
		}

		public void mousePressed(MouseEvent e) {
			startPoint_ = MouseInfo.getPointerInfo().getLocation().x;
			lastTheta_ = theta_;
		}

		public void mouseReleased(MouseEvent e) { 
		}

		public void mouseMoved(MouseEvent e) { }
		public void mouseClicked(MouseEvent e) { }
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
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

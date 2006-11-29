package gui2;

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
	private int SLIDER_MIN = 0;
	
	final static
	private int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	/////////////////////////////////////////////////////////////////
	
	// 倍率
	static
	public int magnification_;
	
	private JButton goBt_ = new JButton("Go ahead");
	
	private JButton hideBt_ = new JButton("Hide All");

	private JButton showBt_ = new JButton("Show All");

	private JButton divergenceBt_ = new JButton("Divergence");

	private JButton stopDivergenceBt_ = new JButton("Stop Divergence");
	
	private JScrollPane menuScroll_;
	
	private JPanel menuPanel_ = new JPanel();
	
	private JCheckBox springCheck_ = new JCheckBox("Calc Spring");
	
	private JCheckBox angleCheck_ = new JCheckBox("Calc Angle");

	private JCheckBox attractionCheck_ = new JCheckBox("Calc Attraction");
	
	private JCheckBox repulsiveCheck_ = new JCheckBox("Calc Replusive");

	private JSlider js1_ = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	private LMNtalFrame mainFrame_;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);
		
		initComponents();
		
		mainFrame_.setMagnification((double)js1_.getValue() / (double)js1_.getMaximum());
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		goBt_.addActionListener(new GoActionAdapter(this));
		showBt_.addActionListener(new ShowAllAdapter(this));
		hideBt_.addActionListener(new HideAllAdapter(this));
		divergenceBt_.addActionListener(new DivergenceAdapter());
		stopDivergenceBt_.addActionListener(new StopDivergenceAdapter());
		
		
		///////////////////////////////////////////////////////////////////////
		// メニューを追加する処理
		
		angleCheck_.addItemListener(new AngleAdapter());
		angleCheck_.setSelected(true);
		springCheck_.addItemListener(new SpringAdapter());
		springCheck_.setSelected(true);
		repulsiveCheck_.addItemListener(new RepulsiveAdapter());
		repulsiveCheck_.setSelected(true);
		attractionCheck_.addItemListener(new AttractionAdapter());
		attractionCheck_.setSelected(true);
		menuPanel_.setLayout(new BoxLayout(menuPanel_, BoxLayout.PAGE_AXIS));
		menuPanel_.add(angleCheck_);
		menuPanel_.add(springCheck_);
		menuPanel_.add(repulsiveCheck_);
		menuPanel_.add(attractionCheck_);
		
		
		menuScroll_ = new JScrollPane(menuPanel_);
		///////////////////////////////////////////////////////////////////////
		
		js1_.addChangeListener(new SliderChanged());
		js1_.setPaintTicks(true);      //目盛りを表示
		js1_.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		js1_.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		js1_.setLabelTable(js1_.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		js1_.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

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
		getContentPane().add(goBt_, gbc);

		gbc.gridy = 1;
		gbc.gridwidth = 1;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
		getContentPane().add(js1_, gbc);

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
		gbc.gridy = 2;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
		getContentPane().add(divergenceBt_, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
		getContentPane().add(stopDivergenceBt_, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 100.0;
		getContentPane().add(menuScroll_, gbc);

	}
	
	/**
	 * 倍率変更のスライダー
	 * @author nakano
	 *
	 */
	public class SliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			mainFrame_.setMagnification((double)source.getValue() / (double)source.getMaximum());
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
			frame.mainFrame_.stopCalc = false;
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

	/**
	 * 拡散処理開始
	 * @author nakano
	 *
	 */
	private class DivergenceAdapter implements ActionListener {
		public DivergenceAdapter() { }
		public void actionPerformed(ActionEvent e) {
			NodeFunction.setDivergence();
		}
	}

	/**
	 * 拡散処理終了
	 * @author nakano
	 *
	 */
	private class StopDivergenceAdapter implements ActionListener {
		public StopDivergenceAdapter() { }
		public void actionPerformed(ActionEvent e) {
			NodeFunction.stopDivergence();
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
	
}

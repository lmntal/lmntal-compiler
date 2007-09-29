package debug;


import gui.LMNtalFrame;
import gui.SubFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BreakPointFrame extends JFrame{
	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	private int WINDOW_WIDTH = 250;
	
	final static
	private int WINDOW_HEIGHT = 200;
	
	final static
	public String TITLE = "Break Points";
	
	final static
	private String ADD_RULE_NAME = "Add";
	
	final static
	private String REMOVE_RULE_NAME = "Remove";

	
	/////////////////////////////////////////////////////////////////

	
	static
	private JButton removeRuleBt_ = new JButton(REMOVE_RULE_NAME);
	
	static
	private JButton addRuleBt_ = new JButton(ADD_RULE_NAME);
	
	static
	private JLabel ruleLabel_ = new JLabel("Rule name: ");
	
	static
	private JLabel infoLabel_ = new JLabel("Information: ");
	
	static
	private JTextField ruleArea_ = new JTextField();
	
	public static JTextArea breakDetail_ = new JTextArea();
	
	

	/////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public BreakPointFrame() {
		
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH-WINDOW_WIDTH, LMNtalFrame.WINDOW_HEIGHT-WINDOW_HEIGHT);
		
		initComponents();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SubFrame.setBreakPointCheck(false);
			}
		});
		
		setVisible(true);
		
	}
	/////////////////////////////////////////////////////////////////
	private void initComponents() {
		setTitle(TITLE);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		panel.add(ruleLabel_, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		panel.add(ruleArea_, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		addRuleBt_.setEnabled(true);
		addRuleBt_.addActionListener(new SetRuleAction());
		add(addRuleBt_, gbc);
		
		gbc.gridx += 1;
		removeRuleBt_.setEnabled(true);
		removeRuleBt_.addActionListener(new SetRuleAction());
		add(removeRuleBt_, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy += 1;
		gbc.gridwidth = 2;
		add(panel, gbc);

		gbc.gridy += 1;
		add(infoLabel_, gbc);
		
		gbc.gridy += 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		breakDetail_.setEditable(false);
		breakDetail_.setText("");
		add(new JScrollPane(breakDetail_), gbc);
		

	}

	static
	public class SetRuleAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(ruleArea_.getText().equals("")){
				Toolkit.getDefaultToolkit().beep();
				return;
			}

			if(((JButton)e.getSource()).getText().equals(ADD_RULE_NAME)){
				BreakPoint.addBreakPoint(ruleArea_.getText());
				breakDetail_.setText(breakDetail_.getText() + "\nADD: rule name \""+ruleArea_.getText()+"\"");
			}
			
			if(((JButton)e.getSource()).getText().equals(REMOVE_RULE_NAME)){
				BreakPoint.removeBreakPoint(ruleArea_.getText());
				breakDetail_.setText(breakDetail_.getText() + "\nREMOVE: rule name \""+ruleArea_.getText()+"\"");
			}

		}
	}
	

}

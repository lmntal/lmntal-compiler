package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import runtime.Atom;
import runtime.Membrane;

/**
 * �¹����LMNtal�ץ�������ɽ�����뤿���LOG������ɥ�
 * @author nakano
 *
 */
public class EditFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// ���
	
	final static
	public int WINDOW_WIDTH = SubFrame.WINDOW_WIDTH;
	
	final static
	public int WINDOW_HIEGHT = LogFrame.WINDOW_HIEGHT;
	
	final static
	public String TITLE = "Edit Panel";
	
	final static
	private Font FONT = new Font("SansSerif", Font.PLAIN, 25);
	
	final static
	private String INFO_NOTHING = "Nothing has been selected.";
	
	final static
	private String INFO_ATOM_TEXT = "Selected : Atom \n" +
								"Atom name : %s \n" +
								"Number of links : %d ";
	
	final static
	private String INFO_MEM_TEXT = "Selected : Membrane \n" +
								"Membrane name : %s \n" +
								"Number of atoms : %d ";
	
	/////////////////////////////////////////////////////////////////

	static
	private JButton createAtomBt_ = new JButton("Create Atom");
	
	static
	private JButton createMemBt_ = new JButton("Create Membrane");
	
	static
	private JLabel nodeNameLabel_ = new JLabel("New name : ");
	
	static
	private JLabel infoLabel_ = new JLabel("Information :");
	
	static
	private JTextField nodeNameArea_ = new JTextField();
	
	static
	private JTextArea nodeDetail_ = new JTextArea();
	
	static
	private JSeparator separator_ = new JSeparator(JSeparator.HORIZONTAL);
	
	/////////////////////////////////////////////////////////////////
	
	private LMNtalFrame mainFrame_;
	
	static
	private CommonListener commonListener_;
	
	static
	private Node selectedNode_;
	
//	private EditPanel panel_ = new EditPanel();
//	
//	private Thread panelThread_;
	
	/////////////////////////////////////////////////////////////////
	// ���󥹥ȥ饯��
	public EditFrame(LMNtalFrame f) {
		
		mainFrame_ = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, LMNtalFrame.WINDOW_HIEGHT);
		commonListener_ = new CommonListener(this);
		
		initComponents();
		
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
		panel.add(nodeNameLabel_, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		panel.add(nodeNameArea_, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		createAtomBt_.setEnabled(false);
		createAtomBt_.addActionListener(new CreateAtomAction());
		add(createAtomBt_, gbc);
		
		gbc.gridx += 1;
		createMemBt_.setEnabled(false);
		createMemBt_.addActionListener(new CreateMembraneAction());
		add(createMemBt_, gbc);
		
		gbc.gridx = 0;
		gbc.gridy += 1;
		gbc.gridwidth = 2;
		add(panel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy += 1;
		add(separator_, gbc);

		gbc.gridy += 1;
		add(infoLabel_, gbc);
		
		gbc.gridy += 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		nodeDetail_.setEditable(false);
		nodeDetail_.setText(INFO_NOTHING);
		add(nodeDetail_, gbc);
		
	}
	
	static
	public void setSelectedNode(Node node){
		selectedNode_ = node;
		
		String detail = "";
		
		if(null == node){
			createAtomBt_.setEnabled(false);
			createMemBt_.setEnabled(false);

			nodeDetail_.setText(INFO_NOTHING);
			return;
		}
		if(node.getObject() instanceof Atom){
			String nodeName = node.getName();
			int linkNum = ((Atom)node.getObject()).getEdgeCount();
			
			createAtomBt_.setEnabled(false);
			createMemBt_.setEnabled(false);
			detail =
				String.format(INFO_ATOM_TEXT, nodeName, linkNum);
		} 
		else if(node.getObject() instanceof Membrane){
			String nodeName = node.getName();
			int nodeNum = ((Membrane)node.getObject()).getAtomCount();
			
			createAtomBt_.setEnabled(true);
			createMemBt_.setEnabled(true);
			detail =
				String.format(INFO_MEM_TEXT, nodeName, nodeNum);
		}
		nodeDetail_.setText(detail);
	}
	
	

	///////////////////////////////////////////////////////////////////////////
	
	static
	private class CreateAtomAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(nodeNameArea_.getText().equals("") ||
					!(selectedNode_.getObject() instanceof Membrane)){
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			commonListener_.addAtom(nodeNameArea_.getText(),
					(Membrane)selectedNode_.getObject());
		}
		
	}
	
	static
	private class CreateMembraneAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!(selectedNode_.getObject() instanceof Membrane)){
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			commonListener_.addMembrane(nodeNameArea_.getText(),
					(Membrane)selectedNode_.getObject());
		}
		
	}
	
	private class EditPanel extends JPanel implements Runnable {
		
		public void paint(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HIEGHT);

			if(null == selectedNode_){ return; }
			g.setColor(Color.BLACK);
			g.setFont(FONT);
			g.drawString(selectedNode_.getName(), 10, 20);
			RoundRectangle2D selectedShape = (RoundRectangle2D)selectedNode_.getShape().clone();
			selectedShape.setFrameFromCenter(WINDOW_WIDTH / 2,
					(WINDOW_HIEGHT / 2) - 10,
					((selectedShape.getMaxX() - selectedShape.getMinX()) / 2) + (WINDOW_WIDTH / 2),
					((selectedShape.getMaxY() - selectedShape.getMinY()) / 2) + (WINDOW_HIEGHT / 2) - 10);
			((Graphics2D)g).draw(selectedShape);
		}
		
		public void run() {
			while(true){
				try {
					paint(getGraphics());
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.tree.*;

import compile.parser.LMNParser;
import compile.parser.ParseException;
//import java.util.LinkedList;
import compile.structure.*;

import test.graph.*;

public class ParserTest extends JFrame implements ActionListener {

	private JTextArea srcInput = null;
	private DefaultMutableTreeNode rootTree = null;
	private JTree tree = null;
	private JButton btnRun = null;
	private JButton btnOpen = null;
	private LMNGraphPanel pLMN = null;
	
	public ParserTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(800,600);
		setVisible(true);
	}
	
	protected void initComponents() {
		setTitle("Parser Test");
		getContentPane().setLayout(new BorderLayout());
		// ソースウィンドウ
		JPanel pSrc = new JPanel();
		pSrc.setLayout(new BorderLayout());
		srcInput = new JTextArea();
		pSrc.add(new JScrollPane(srcInput), BorderLayout.CENTER);
		pSrc.add(new JLabel("LMNtal Source"), BorderLayout.NORTH);
		
		// ログウィンドウ
		JPanel pLog = new JPanel();
		pLog.setLayout(new BorderLayout());
		rootTree = new DefaultMutableTreeNode("LMNtal Source");
		tree = new JTree(rootTree);
		pLog.add(new JScrollPane(tree), BorderLayout.CENTER);
		pLog.add(new JLabel("Log output"), BorderLayout.NORTH);
		
		// メインウィンドウ
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pSrc, pLog);
		main.setDividerSize(2);
		getContentPane().add(main, BorderLayout.CENTER);
		main.setDividerLocation(400);
		
		// オペレーションパネル
		JPanel pOpe = new JPanel();
		pOpe.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
		pOpe.add(btnOpen);
		btnRun = new JButton("Run");
		btnRun.addActionListener(this);
		pOpe.add(btnRun);
		
		getContentPane().add(pOpe, BorderLayout.NORTH);
		
		pLMN = new LMNGraphPanel();
		
		getContentPane().add(new JScrollPane(pLMN), BorderLayout.SOUTH);
		
	}
	
	public static void main(String args[]) {
		ParserTest app = new ParserTest();
	}

	/**
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRun) {
			String s = srcInput.getText();
			LMNParser parser = new LMNParser(new StringReader(s));
			try {
				Membrane src = parser.parse();
				rootTree.removeAllChildren();
				addMembrane(src, rootTree);
				tree.setModel(new DefaultTreeModel(rootTree));
				pLMN.setMembrane(src);
				pLMN.start();
			} catch (ParseException pe) {
				System.err.println(pe);
			}
		}
		else if (e.getSource() == btnOpen) {
			JFileChooser choose  = new JFileChooser();
			if (JFileChooser.APPROVE_OPTION == choose.showOpenDialog(this)) {
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(choose.getSelectedFile()));
					StringBuffer buff = new StringBuffer();
					String s;
					while (true) {
						s = in.readLine();
						if (s == null) break;
						buff.append(s+"\n");
					}
					srcInput.setText(buff.toString());
				} catch (Exception ee) {
					System.err.println(ee);
				}
			}
		}
	}
	
	private void addMembrane(Membrane mem, DefaultMutableTreeNode node) {
		// アトムの追加
		for (int i=0;i<mem.atoms.size();i++) {
			addAtom((Atom)mem.atoms.get(i), node);
		}
		// 子膜の追加
		for (int i=0;i<mem.mems.size();i++) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode("<Membrane>");
			addMembrane((Membrane)mem.mems.get(i), child);
			node.add(child);
		}
		// ルールの追加
		for (int i=0;i<mem.rules.size();i++) {
			addRule((RuleStructure)mem.rules.get(i),node);
		}
		// プロセス変数の追加
		for (int i=0;i<mem.processContexts.size();i++) {
			addProcessContext((ProcessContext)mem.processContexts.get(i),node);
		}
		// ルール変数の追加
		for (int i=0;i<mem.ruleContexts.size();i++) {
			addRuleContext((RuleContext)mem.ruleContexts.get(i),node);
		}
	}
	
	private void addAtom(Atom atom, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nodeAtom = new DefaultMutableTreeNode("<Atom> "+atom.functor);
		// リンクの追加
		for (int i=0;i<atom.args.length;i++) {
			int nID = atom.args[i].hashCode();
			String s = "";
			LinkOccurrence buddy = ((LinkOccurrence)atom.args[i]).buddy; 
			if ((buddy != null)) {
				nID = Math.min(nID,buddy.hashCode());
				s = "<Link> "+nID;
			} else {
				s = "<FreeLink> "+nID;
			}
				 
			
			nodeAtom.add(new DefaultMutableTreeNode(s));
		}
		node.add(nodeAtom);
	}
	
	private void addProcessContext(ProcessContext proc, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nodeProc = new DefaultMutableTreeNode("<ProcessContext>"+proc.getName());
		node.add(nodeProc);
	}
	
	private void addRuleContext(RuleContext proc, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nodeProc = new DefaultMutableTreeNode("<RuleContext>"+proc.getName());
		node.add(nodeProc);
	}

	private void addRule(RuleStructure rule, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nodeRule = new DefaultMutableTreeNode("<Rule>");
		DefaultMutableTreeNode nodeRuleHead = new DefaultMutableTreeNode("<Head>");
		DefaultMutableTreeNode nodeRuleBody = new DefaultMutableTreeNode("<Body>");
		addMembrane(rule.leftMem,nodeRuleHead);
		addMembrane(rule.rightMem,nodeRuleBody);
		nodeRule.add(nodeRuleHead);
		nodeRule.add(nodeRuleBody);
		
		node.add(nodeRule);
	}
}

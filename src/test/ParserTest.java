package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.StringReader;

import compile.parser.LMNParser;
import compile.parser.ParseException;
//import java.util.LinkedList;
import compile.structure.Membrane;

public class ParserTest extends JFrame implements ActionListener {

	private JTextArea srcInput = null;
	private JTextArea logOutput = null;
	private JButton btnRun = null;
	
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
		logOutput = new JTextArea();
		pLog.add(new JScrollPane(logOutput), BorderLayout.CENTER);
		pLog.add(new JLabel("Log output"), BorderLayout.NORTH);
		
		// メインウィンドウ
		JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pSrc, pLog);
		main.setDividerSize(2);
		getContentPane().add(main, BorderLayout.CENTER);
		main.setDividerLocation(400);
		
		// オペレーションパネル
		JPanel pOpe = new JPanel();
		pOpe.setLayout(new FlowLayout());
		btnRun = new JButton("Run");
		btnRun.addActionListener(this);
		pOpe.add(btnRun);
		
		getContentPane().add(pOpe, BorderLayout.NORTH);
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
			String log = null;
			LMNParser parser = new LMNParser(new StringReader(s));
			try {
//				LinkedList list = parser.parseSrc();
//				log = SrcDumper.dump(list,0);
				Membrane src = parser.parse();
				log = src.toStringWithoutBrace();
			} catch (ParseException pe) {
				log = pe.getMessage();
			}
			logOutput.setText(log);
		}
	}
}

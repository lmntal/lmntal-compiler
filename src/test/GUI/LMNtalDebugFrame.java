/*
 * 作成日: 2006/01/25
 *
 */
package test.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

import runtime.*;

/**
 * @author inui
 * デバッグ時に使用するフレーム
 */
public class LMNtalDebugFrame extends LMNtalFrame {
	/** ソースを表示する */
	private JTextPane jt;
	
	/** コンソール */
	private JTextArea console;
	
	/** 行番号を表示するテキストエリア */
	private JTextPane linenoArea;
	
	/**
	 * デバッグ時ソース表示用のテキストエリアを生成する
	 */
	private JTextPane createJTextArea() {
		final JTextPane jt=new JTextPane() {
			final int SIZE = 16;
			public void paint(Graphics g) {
				super.paint(g);
				
				// ブレークポイントの表示
				g.setColor(Color.red);
				Iterator iter = Debug.breakPointIterator();
				while (iter.hasNext()) {
					g.fillRect(0, SIZE*(((Rule)iter.next()).lineno-1)+8, SIZE-8, SIZE-2);
				}
				
				// 現在停止中のルールの表示
				g.setColor(Color.blue);
				g.setFont(new Font("Monospace", Font.PLAIN, 12));
				int lineno = Debug.getCurrentRuleLineno();
				if (lineno > 0) {
					String arrow = ">";
					if (Debug.getTestType() == Debug.ATOM) arrow = "->";
					else if (Debug.getTestType() == Debug.MEMBRANE) arrow = "=>";
					g.drawString(arrow, 0, SIZE*(lineno-1)+19);
				}
			}
		};
		jt.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int offset = jt.getCaretPosition();
				try {
					String text = jt.getText(0, offset);
					int lineno = 0;
					for (int i = 0; i < text.length(); i++) {
						if (text.charAt(i) == '\n')
							lineno++;
					}
					Debug.toggleBreakPointAt(lineno);
					System.out.println("lineno"+lineno);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				jt.repaint();
			}
		});
		jt.setContentType("text/html");
		jt.setEditable(false);
		//jt.setFont(new Font("Monospaced", Font.PLAIN, 12));
		return jt;
	}
	
	/**
	 * コンポーネントを初期化します。
	 */
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		lmnPanel.getGraphLayout().initGraphDialog(this);
		setTitle("It's LMNtal Debugger");
		getContentPane().setLayout(new BorderLayout());
		
		//ソースビュー
		final JPanel p = new JPanel(new BorderLayout());
		linenoArea = new JTextPane();
		p.add("West", linenoArea);
		jt = createJTextArea();
		p.add("Center", jt);
		linenoArea.setContentType("text/html");

		JScrollPane jsp = new JScrollPane(p);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, lmnPanel, jsp);
		getContentPane().add(split, BorderLayout.CENTER);
		
		//コンソール
//		console = new JTextArea(5, 10);
//		jsp = new JScrollPane(console);
//		getContentPane().add("South", jsp);
		
		// ツールバーの生成
		JToolBar toolBar = new JToolBar();
		JButton nextButton = new JButton("Next");
		nextButton.setToolTipText("apply a rule only one time");
		nextButton.addActionListener(new NextButtonActionListener(this));
		toolBar.add(nextButton);
		JButton continueButton = new JButton("Continue");
		continueButton.setToolTipText("apply rules until a break point");
		continueButton.addActionListener(new ContinueButtonActionListener(this));
		toolBar.add(continueButton);
		JCheckBox linenoCheckBox = new JCheckBox("LineNumber");
		linenoCheckBox.setSelected(true);
		linenoCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) p.add("West", linenoArea);
				else p.remove(linenoArea);
				getContentPane().validate();
			}
		});
		toolBar.add(linenoCheckBox);
		getContentPane().add("North", toolBar);
	}
	
	/**
	 * ソースエリアにテキストをセットします
	 * @param s ソーステキスト
	 * @param lineno 行番号
	 */
	public void setSourceText(String s, int lineno) {
		jt.setText(s);
		StringBuffer buf = new StringBuffer();
		buf.append("<style>pre {font-size:10px; font-family:monospace;}</style>\n");
		buf.append("<pre>\n");
		for (int i = 1; i <= lineno; i++)
			buf.append(i+"\n");
		buf.append("</pre>");
		linenoArea.setText(buf.toString());			
	}
	
	public JTextArea getConsole() {
		return console;
	}

	/** Nextボタンを押したときのAction */
	class NextButtonActionListener implements ActionListener {
		private LMNtalFrame frame;
		
		public NextButtonActionListener(LMNtalFrame f) {
			frame = f;
		}
		
		public void actionPerformed(ActionEvent e) {
			frame.busy = false;
			Debug.endBreakPoint(Debug.NEXT);
		}
	}
	
	/** Continueボタンを押したときのAction */
	class ContinueButtonActionListener implements ActionListener {
		private LMNtalFrame frame;
		
		public ContinueButtonActionListener(LMNtalFrame f) {
			frame = f;
		}
		
		public void actionPerformed(ActionEvent e) {
			frame.busy = false;
			Debug.endBreakPoint(Debug.CONTINUE);
		}
	}
}
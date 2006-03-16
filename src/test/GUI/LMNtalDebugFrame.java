package test.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;

import runtime.Debug;
import runtime.Env;
import runtime.FrontEnd;
import runtime.MasterLMNtalRuntime;
import runtime.Membrane;
import runtime.Rule;

/**
 * @author inui
 * 標準出力をJTextAreaに切替えるためのクラス
 */
class ConsolePrintStream extends PrintStream {
	private JTextArea jt;
	private JScrollPane scroll;
	
	public ConsolePrintStream(OutputStream os, JTextArea jt, JScrollPane scroll) throws FileNotFoundException {
		super(os);
		this.jt = jt;
		this.scroll = scroll;
	}

	public void println(String s) {
		//super.println(s);
		jt.append(s+"\n");
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
	}
}

/**
 * @author inui
 * デバッグ時に使用するフレーム
 */
public class LMNtalDebugFrame extends JFrame {	
	/** LMNtalFrame */
	private LMNtalFrame lmntalFrame;
	
	/** ソースを表示する */
	private JTextPane jt;
	
	/** 行番号を表示するテキストエリア */
	private JTextPane linenoArea;
	
	/** プロファイル情報を表示するかどうか */
	private boolean showProfile = false;
	
	/** コンソール */
	private JTextArea console;
	
	private JCheckBox guiCheckBox;
	
	/**
	 * コンストラクタです
	 */
	public LMNtalDebugFrame(LMNtalFrame lmntalFrame) {
		this.lmntalFrame = lmntalFrame;
		
		//lmntalFrame.getContentPane().remove(1);//通常時と動作が変わるのでGo aheadボタンを削除
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(600, 600);
		setVisible(true);
	}
	
	/**
	 * デバッグ時ソース表示用のテキストエリアを生成する
	 */
	private JTextPane createJTextArea() {		
		final JTextPane jt=new JTextPane() {
			public void paint(Graphics g) {
				super.paint(g);
				
				final int SIZE = (Env.fDEMO  ? 25 : 18);
				
				// ブレークポイントの表示
				g.setColor(Color.red);
				Iterator iter = Debug.breakPointIterator();
				while (iter.hasNext()) {
					//g.fillRect(0, SIZE*(((Rule)iter.next()).lineno-1)+9, SIZE-8, SIZE-2);
					final int x = 0;
					final int y = SIZE*(((Rule)iter.next()).lineno-1)+9;
					final int w = SIZE-(Env.fDEMO ? 5 : 2);
					final int h = SIZE-(Env.fDEMO ? 5 : 2);
					g.fillOval(x, y, w, h);
				}
				
				// 現在停止中のルールの表示
				g.setColor(Color.blue);
				int lineno = Debug.getCurrentRuleLineno();
				if (lineno > 0) {
					g.setColor(Color.blue);
					g.setXORMode(Color.black);
					g.fillRect(SIZE-4, SIZE*(lineno-1)+9, getWidth(), SIZE-2);
					g.setPaintMode();
				}
				
				if (Env.profile && showProfile) {
					g.setColor(Color.blue);
					//g.setFont(new Font("Monospace", Font.PLAIN, SIZE));
					Font font = new Font(getFont().getName(), Font.PLAIN, Env.fDEMO ? 16 : 12);
					g.setFont(font);
					Iterator rules = Debug.ruleIterator();
					if (rules != null) {
						while (rules.hasNext()) {
							Rule r = (Rule)rules.next();
							double time = (Env.majorVersion == 1 && Env.minorVersion > 4) ? r.time / 1000000 : r.time;
							String s = r.succeed + "/" + r.apply+ "(" + time + "ms)";
							g.drawString(s, getWidth()-(Env.fDEMO ? 110 : 70), SIZE*r.lineno+(Env.fDEMO ? 2 : 4));
						}
					}
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
					//System.out.println("lineno"+lineno);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				jt.repaint();
			}
		});
		jt.setContentType("text/html");
		jt.setEditable(false);
		return jt;
	}
	
	/**
	 * コンポーネントを初期化します。
	 */
	protected void initComponents() {
		setTitle("It's LMNtal Debugger - "+Debug.getUnitName());
		getContentPane().setLayout(new BorderLayout());
		
		final JPanel p = new JPanel(new BorderLayout());
		
		//行番号表示領域
		linenoArea = new JTextPane();
		linenoArea.setContentType("text/html");
		p.add("West", linenoArea);

		//ソースビュー
		jt = createJTextArea();
		p.add("Center", jt);
		JScrollPane scroll1 = new JScrollPane(p);
		//getContentPane().add(jsp, BorderLayout.CENTER);
		
		//コンソール
		console = new JTextArea();
		console.setFont(new Font("Monospaced", Font.PLAIN, Env.fDEMO ? 16 : 12));
		console.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(console);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//標準出力を切り替える
		try {
			System.setOut(new ConsolePrintStream(System.out, console, scroll2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll1, scroll2);
		getContentPane().add(split, BorderLayout.CENTER);
		split.setDividerLocation(400);
		
		// ツールバーの生成
		JToolBar toolBar = new JToolBar();
		JButton nextButton = new JButton("Next");
		nextButton.setToolTipText("apply a rule only one time");
		nextButton.addActionListener(new NextButtonActionListener(lmntalFrame));
		toolBar.add(nextButton);
		JButton continueButton = new JButton("Continue");
		continueButton.setToolTipText("apply rules until a break point");
		continueButton.addActionListener(new ContinueButtonActionListener(lmntalFrame));
		toolBar.add(continueButton);
		
		//行番号チェックボックス
		{
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
		}
		
		//デモオプション切り替えチェックボックス
		{
			JCheckBox checkBox = new JCheckBox("Demo");
			checkBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						Env.atomSize = 40;//TODO どこかに定数を用意する
						Env.fDEMO = true;
					} else {
						Env.atomSize = 16;
						Env.fDEMO = false;
					}
					changeFontSize(jt);
					changeFontSize(linenoArea);
					console.setFont(new Font("Monospaced", Font.PLAIN, Env.fDEMO ? 16 : 12));
				}
			});
			toolBar.add(checkBox);
		}
		
		//プロファイルの表示オプションチェックボックス
		{
			JCheckBox checkBox = new JCheckBox("ShowProfile");
			checkBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					showProfile = (e.getStateChange() == ItemEvent.SELECTED);
					jt.repaint();
				}
			});
			toolBar.add(checkBox);
		}
		
		//gオプション切替えチェックボックス
		{
			guiCheckBox = new JCheckBox("GUI");
			guiCheckBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						lmntalFrame.setVisible(true);
					} else {
						lmntalFrame.setVisible(false);
					}
					
				}
			});
			toolBar.add(guiCheckBox);
		}
		
		getContentPane().add("North", toolBar);
	}
	
	private void changeFontSize(JTextPane jt) {
		String s = jt.getText();
		if (Env.fDEMO) s = s.replaceFirst("10px", "14px");
		else s = s.replaceFirst("14px", "10px");
		jt.setText(s);
	}
	
	/**
	 * ソースエリアにテキストをセットします
	 * @param s ソーステキスト
	 * @param lineno 行番号
	 */
	public void setSourceText(String s, int lineno) {
		jt.setText(s);
		StringBuffer buf = new StringBuffer();
		//buf.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; CHARSET=EUC-JP\">\n");
		buf.append("<style>pre {font-size:10px; font-family:monospace;}</style>\n");
		buf.append("<pre>\n");
		for (int i = 1; i <= lineno; i++)
			buf.append(i+"\n");
		buf.append("</pre>\n");
		linenoArea.setText(buf.toString());			
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
package debug;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

import runtime.Env;
import runtime.FrontEnd;
import test.GUI.LMNtalFrame;

/**
 * @author inui
 * デバッグ時に使用するフレーム
 */
public class DebugFrame extends JFrame {	
	public boolean restart;
	
	/** LMNtalFrame */
	private LMNtalFrame lmntalFrame;
	
	/** ソースを表示する */
	private SourceTextComponent jt;
	
	/** 行番号を表示するテキストエリア */
	private JTextPane linenoArea;
	
	/** コンソール */
	private JTextArea console;
	
	private InstructionFrame instFrame;
	
	private JCheckBox guiCheckBox;
	private JCheckBox instCheckBox;
	private JButton nextButton;
	private JButton continueButton;
	private JSplitPane split;
	private JScrollPane scroll1;
	private Thread th;
	
	private File currentDirectory;
	
	/**
	 * コンストラクタです
	 */
	public DebugFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(600, 600);
		setVisible(true);
		
		currentDirectory = new File(System.getProperty("user.dir"));
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem("Open");
		item.addActionListener(new OpenActionListener());
		menu.add(item);
		menubar.add(menu);
		return menubar;
	}
	
	/**
	 * コンポーネントを初期化します。
	 */
	protected void initComponents() {
		instFrame = new InstructionFrame();
		
		final JPanel p = new JPanel(new BorderLayout());
		
		//行番号表示領域
		linenoArea = new JTextPane();
		linenoArea.setContentType("text/html");
		p.add("West", linenoArea);

		//ソースビュー
		jt = new SourceTextComponent();
		p.add("Center", jt);
		scroll1 = new JScrollPane(p);
		
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
		
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll1, scroll2);
		getContentPane().add(split, BorderLayout.CENTER);
		split.setDividerLocation(400);
		
		//ツールバーの生成
		JToolBar toolBar = new JToolBar();

		nextButton = new JButton("Next");
		nextButton.setToolTipText("apply a rule only one time");
		toolBar.add(nextButton);
		
		continueButton = new JButton("Continue");
		continueButton.setToolTipText("apply rules until a break point");
		toolBar.add(continueButton);
		
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new RestartActionListener());
		toolBar.add(restartButton);
		
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
					Font font = new Font("Monospaced", Font.PLAIN, Env.fDEMO ? 16 : 12);
					console.setFont(font);
					instFrame.jt.setFont(font);
				}
			});
			toolBar.add(checkBox);
		}
		
		//プロファイルの表示オプションチェックボックス
		{
			JCheckBox checkBox = new JCheckBox("Profile");
			checkBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					jt.showProfile(e.getStateChange() == ItemEvent.SELECTED);
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
		
		//中間命令の表示切替えチェックボックス
		{
			instCheckBox = new JCheckBox("Instruction");
			instCheckBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						instFrame.setLocation(getX()+getWidth(), getY());
						instFrame.setVisible(true);
					} else {
						instFrame.setVisible(false);
					}
				}
			});
			instFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					instCheckBox.setSelected(false);
				}
			});
			toolBar.add(instCheckBox);
		}
		
		//メニューバーを生成してセット
		setJMenuBar(createJMenuBar());
		
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
		buf.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; CHARSET=EUC-JP\">\n");
		buf.append("<style>pre {font-size:10px; font-family:monospace; color:gray;}</style>\n");
		buf.append("<pre>\n");
		for (int i = 1; i <= lineno; i++)
			buf.append(i+"\n");
		buf.append("</pre>\n");
		linenoArea.setText(buf.toString());			
	}
	
	public void setLMNtalFrame(LMNtalFrame lmntalFrame) {
		this.lmntalFrame = lmntalFrame;
		nextButton.addActionListener(new NextActionListener(lmntalFrame));
		continueButton.addActionListener(new ContinueActionListener(lmntalFrame));
		lmntalFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				guiCheckBox.setSelected(false);
			}
		});
		setTitle("It's LMNtal Debugger - "+Debug.getUnitName());
	}
	
	public void setInstruction(String s) {
		instFrame.jt.setText(s);
	}
	
	/**
	 * スクロールバーの位置をlinenoの行が見える位置に更新します
	 * @param lineno 表示したい行番号
	 */
	public void updateScrollBar(int lineno) {
		if (lineno == -1) return;
		int value = scroll1.getVerticalScrollBar().getValue();
		System.err.println("lineno="+lineno);
		final int SIZE = (Env.fDEMO  ? 25 : 18);
		final int height = split.getDividerLocation();
		if (SIZE*(lineno-1)+9 > value && SIZE*(lineno-1)+9+(SIZE-2) < value+height) return;
		scroll1.getVerticalScrollBar().setValue(SIZE*(lineno-1)+9-height/2);
	}

	class NextActionListener implements ActionListener {
		private LMNtalFrame frame;
		
		public NextActionListener(LMNtalFrame f) {
			frame = f;
		}
		
		public void actionPerformed(ActionEvent e) {
			frame.busy = false;
			Debug.endBreakPoint(Debug.NEXT);
		}
	}
	
	class ContinueActionListener implements ActionListener {
		private LMNtalFrame frame;
		
		public ContinueActionListener(LMNtalFrame f) {
			frame = f;
		}
		
		public void actionPerformed(ActionEvent e) {
			frame.busy = false;
			Debug.endBreakPoint(Debug.CONTINUE);
		}
	}
	
	class OpenActionListener implements ActionListener, Runnable {
		Reader r;
		String name;
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser(currentDirectory);
			int res = fc.showOpenDialog(null);
			if (res != JFileChooser.APPROVE_OPTION) return;
			
			if (lmntalFrame != null) lmntalFrame.dispose();
			console.setText("");
			
			File f = currentDirectory = fc.getSelectedFile();
			Debug.setUnitName(f.getAbsolutePath());
			try {
				r = new FileReader(f);
				name = f.getAbsolutePath();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			th = new Thread(this);
			th.start();
			guiCheckBox.setSelected(false);
		}
		
		public void run() {
			restart = false;
			FrontEnd.run(r, name);
		}
	}
	
	class RestartActionListener implements ActionListener, Runnable {
		public void actionPerformed(ActionEvent e) {
			if (lmntalFrame != null) lmntalFrame.dispose();
			console.setText("");
			guiCheckBox.setSelected(false);
			th = new Thread(this);
			th.start();
		}
		
		public void run() {
			restart = true;
			try {
				FileReader r = new FileReader(Debug.getUnitName());
				FrontEnd.run(r, Debug.getUnitName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
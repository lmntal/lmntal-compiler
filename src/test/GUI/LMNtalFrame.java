package test.GUI;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import runtime.Env;
import debug.Debug;
//import compile.parser.ParseException;

class MyThread extends Thread {
	LMNtalFrame f;
	MyThread(LMNtalFrame ff) {
		f = ff;
	}
	
	public void run() {
		while(true) {
			try {
//				System.out.println("go");
				f.busy = false;
				sleep(4000);
			} catch (Exception e) {
			}
		}
	}
}

public class LMNtalFrame extends JFrame implements KeyListener {
	Thread th;
	
	public LMNGraphPanel lmnPanel = null;
	JTextArea jt;
	public boolean busy = true;
	public boolean running = true;
	
	public LMNtalFrame() {
		setDefaultCloseOperation(Env.debugOption ? JFrame.HIDE_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (!Env.debugOption) {//2006.3.16 by inui
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					running = busy = false;
					runtime.LMNtalRuntimeManager.terminateAllThreaded();	// 追加 n-kato 2004-10-30
				}
			});
		}
		initComponents();
		setSize(800,600);
		if(Env.getExtendedOption("screen").equals("max")) {
			setExtendedState(Frame.MAXIMIZED_BOTH | getExtendedState());
		}
		if (!Env.debugOption) setVisible(true);
		if(!Env.getExtendedOption("auto").equals("")) {
			th = new MyThread(this);
			th.start();
		}
	}
	
	public void keyPressed(KeyEvent e) {
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());
	}

	
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		lmnPanel.getGraphLayout().initGraphDialog(this);
		JButton bt;
//		try {
//			;
////			p.setSource("a(\n b(c, \n  d(e,\nf(g\n,h))))\n, b");
////			p.setSource("append(c(aa,c(bb,c(cc,n))),\nc(dd,c(ee,n)),\nresult)");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		setTitle("It's LMNtal");
		getContentPane().setLayout(new BorderLayout());
//		getContentPane().add(jt=new JTextArea(5, 80), BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(lmnPanel), BorderLayout.CENTER);
		if (!Env.debugOption) {//2006.3.16 by inui
			getContentPane().add(bt=new JButton("Go ahead"), BorderLayout.SOUTH);
			bt.addActionListener(new ActionAdapter(this));
		}
//		getContentPane().addKeyListener(new MyKeyAdapter(this));
		
//		addKeyListener(this);
		
//		jt.addKeyListener(new KeyAdapter() {
//			public void keyTyped(KeyEvent e) {
//				setSource(jt.getText());
//			}
//		});
	}
	
//	/**
//	 * 新しい LMNtal ソースコードによりグラフを更新する。
//	 * @param s
//	 */
//	void setSource(String s) {
//		try {
//			lmnPanel.setSource(s);
//		} catch (ParseException e) {
////			e.printStackTrace();
//		}
//	}
	
	public void waitBusy() {
//		lmnPanel.getGraphLayout().calc();
		lmnPanel.getGraphLayout().setAllowRelax(true);
		if (Env.debugFrame != null) Env.debugFrame.updateScrollBar(Debug.getCurrentRuleLineno());
		busy = true;
		while(busy) {
			try {
				this.wait(100);
			} catch (Exception e) {
			}
		}
		lmnPanel.getGraphLayout().setAllowRelax(false);
	}
	
	/** @return ルールスレッドの実行を継続してよいかどうか */
	public boolean onTrace() {
		if(Env.fGUI && Env.gui.running) {
			lmnPanel.start();
//			Env.gui.lmnPanel.setMembrane((runtime.Membrane)Env.theRuntime.getGlobalRoot());
			Env.gui.waitBusy();
			lmnPanel.stop();
		}
		return Env.gui.running;
	}
	
	public void dispose() {
		super.dispose();
		if (Env.debugOption) {
			running = busy = false;
			runtime.LMNtalRuntimeManager.terminateAllThreaded();
		}
	}
}

class ActionAdapter implements ActionListener {
	LMNtalFrame frame;
	ActionAdapter(LMNtalFrame f) {
		frame = f;
	}
	public void actionPerformed(ActionEvent e) {
//		e.getSource();
		frame.busy = false;
	}
}

class MyKeyAdapter extends KeyAdapter {
	LMNtalFrame frame;
	MyKeyAdapter(LMNtalFrame f) {
		frame = f;
	}
	public void keyPressed(KeyEvent e) {
		frame.busy = false;
//		super.keyPressed(e);
	}
}

package test.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import runtime.Env;
import compile.parser.ParseException;

public class LMNtalFrame extends JFrame implements KeyListener {
	
	public LMNGraphPanel lmnPanel = null;
	JTextArea jt;
	public boolean busy = true;
	
	public LMNtalFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(800,600);
		setVisible(true);
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
		getContentPane().add(bt=new JButton("Go ahead"), BorderLayout.SOUTH);
		bt.addActionListener(new ActionAdapter(this));
//		getContentPane().addKeyListener(new MyKeyAdapter(this));
		
//		addKeyListener(this);
		
//		jt.addKeyListener(new KeyAdapter() {
//			public void keyTyped(KeyEvent e) {
//				setSource(jt.getText());
//			}
//		});
	}
	
	/**
	 * 新しい LMNtal ソースコードによりグラフを更新する。
	 * @param s
	 */
	void setSource(String s) {
		try {
			lmnPanel.setSource(s);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
	
	public void waitBusy() {
//		lmnPanel.getGraphLayout().calc();
		lmnPanel.getGraphLayout().setAllowRelax(true);
		busy = true;
		while(busy) {
			try {
				this.wait(100);
			} catch (Exception e) {
			}
		}
		lmnPanel.getGraphLayout().setAllowRelax(false);
	}
	
	public void onTrace() {
		if(Env.fGUI) {
			lmnPanel.start();
//			Env.gui.lmnPanel.setMembrane((runtime.Membrane)Env.theRuntime.getGlobalRoot());
			Env.gui.waitBusy();
			lmnPanel.stop();
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

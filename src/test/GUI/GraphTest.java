package test.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import compile.parser.ParseException;

public class GraphTest extends JFrame {
	
	private LMNGraphPanel p = null;
	JTextArea jt;
	
	public GraphTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(800,600);
		setVisible(true);
		p.start();
	}

	protected void initComponents() {
		p = new LMNGraphPanel();
//		try {
//			;
////			p.setSource("a(\n b(c, \n  d(e,\nf(g\n,h))))\n, b");
////			p.setSource("append(c(aa,c(bb,c(cc,n))),\nc(dd,c(ee,n)),\nresult)");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		setTitle("GraphTest");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jt=new JTextArea(5, 80), BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(p), BorderLayout.CENTER);
		
		jt.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				setSource(jt.getText());
			}
		}
		);
	}
	
	/**
	 * 新しい LMNtal ソースコードによりグラフを更新する。
	 * @param s
	 */
	void setSource(String s) {
		try {
			p.setSource(s);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
	
}
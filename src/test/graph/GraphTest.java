package test.graph;

import java.awt.BorderLayout;

import javax.swing.*;

import compile.parser.ParseException;

public class GraphTest extends JFrame {
	
	private LMNGraphPanel p = null;
	
	public GraphTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(800,600);
		setVisible(true);
		p.start();
	}
	
	protected void initComponents() {
		p = new LMNGraphPanel();
		try {
			p.setSource("append(c(aa,c(bb,c(cc,n))),c(dd,c(ee,n)),result)");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setTitle("GraphTest");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		GraphTest app = new GraphTest();
	}
}
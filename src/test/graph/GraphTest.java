package test.graph;

import java.awt.BorderLayout;

import javax.swing.*;

public class GraphTest extends JFrame {
	
	private GraphPanel p = null;
	
	public GraphTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setSize(800,600);
		setVisible(true);
		p.start();
	}
	
	protected void initComponents() {
		p = new LMNGraphPanel();
		setTitle("GraphTest");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		GraphTest app = new GraphTest();
	}
}
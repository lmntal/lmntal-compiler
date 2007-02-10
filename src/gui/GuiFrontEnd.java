package gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JApplet;
import javax.swing.JPanel;

public class GuiFrontEnd extends JApplet implements Runnable {
	int x = 10;
	public void run() {
		
		for (; x < 1000 ; x++) {
			System.out.println(x);
			repaint();
			try {	Thread.sleep(100);	}
			catch(Exception err) {}
		}
	}

	public void init() {
		Thread th = new Thread(this);
		th.start();

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 10.0;
		gbc.weighty = 0.0;

		panel.add(new GraphPanel(), gbc);

		JPanel panel2 = new JPanel();
		gbc.weightx = 1.0;
		SubFrame.initComponents(panel2);

		gbc.gridx += 1;
		panel.add(panel2, gbc);

		JPanel panel3 = new JPanel();
		LogFrame.initComponents(panel3);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(panel3, gbc);
		
		add(panel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
	}
	
}

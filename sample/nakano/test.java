import javax.swing.*;
import java.awt.*;

public class test implements Runnable{
	public static void main(String args[]) {
		JFrame frm = new JFrame("Kitty on your lap");
		frm.setBounds(0 , 0 , 400 , 200);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start();
	}
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillOval(10, 10, 50, 50);
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				Thread.sleep(100);
				repaint();
			} catch (InterruptedException e) {
			}
		}
	}
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}
	public void stop() {
		th = null;
	}
}
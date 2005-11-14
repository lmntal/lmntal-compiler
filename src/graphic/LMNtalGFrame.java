package graphic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import runtime.Env;
/**
 * 
 * @author nakano
 *	ウィンドウを生成し、ボタンを配置。
 *	塗りつぶしや、配置などはLMNGraphPanelに一任。
 *
 */
//class MyThread extends Thread {
//	LMNtalGFrame f;
//	MyThread(LMNtalGFrame ff) {
//		f = ff;
//	}
//	
//	public void run() {
//		while(true) {
//			try {
////				System.out.println("go");
//				f.busy = false;
//				sleep(4000);
//			} catch (Exception e) {
//			}
//		}
//	}
//}

public class LMNtalGFrame extends JFrame{
	//Thread th;

	public LMNGraphPanel lmnPanel = null;
	public boolean busy = true;
	public boolean running = true;
	
    public LMNtalGFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = busy = false;
				runtime.LMNtalRuntimeManager.terminateAllThreaded();
				//閉じる際に、lmnPanelを殺す。
				if(lmnPanel!=null)
					lmnPanel.stop();
			}
		});
		initComponents();
		setSize(800,600);
		if(Env.getExtendedOption("screen").equals("max")) {
			setExtendedState(Frame.MAXIMIZED_BOTH | getExtendedState());
		}
		setVisible(true);
//		if(!Env.getExtendedOption("auto").equals("")) {
//			th = new MyThread(this);
//			th.start();
//		}
    }
	/** @return ルールスレッドの実行を継続してよいかどうか */
	public boolean onTrace() {
		if(Env.fGraphic) {
//			lmnPanel.start();
			waitBusy();
//			lmnPanel.stop();
		}
		return running;
	}
	
	public void waitBusy() {
		busy = true;
		System.out.print("waitbusy");
		while(busy) {
			try {
				this.wait(100);
			} catch (Exception e) {
			}
		}
	}
	
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		JButton bt;
		
		setTitle("It's LMNtal");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(lmnPanel), BorderLayout.CENTER);
		getContentPane().add(bt=new JButton("Go ahead"), BorderLayout.SOUTH);
		bt.addActionListener(new ActionAdapter(this));
	}
}
class ActionAdapter implements ActionListener {
	LMNtalGFrame frame;
	ActionAdapter(LMNtalGFrame f) {
		frame = f;
	}
	public void actionPerformed(ActionEvent e) {
//		e.getSource();
		frame.busy = false;
	}
}
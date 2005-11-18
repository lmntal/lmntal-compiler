package graphic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import runtime.Env;
/**
 * 
 * @author nakano
 *	ウィンドウを生成。
 *	塗りつぶしや、配置などはLMNGraphPanelに一任。
 *
 */

public class LMNtalGFrame extends JFrame{

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
//		System.out.print("*");
		while(busy) {
			try {
				lmnPanel.th.sleep(10);
				busy = false;
			} catch (Exception e) {
			}
		}
	}
	
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		
		setTitle("It's Graphical LMNtal");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(lmnPanel), BorderLayout.CENTER);
	}
}
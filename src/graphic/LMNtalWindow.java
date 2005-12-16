package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

import runtime.AbstractMembrane;
import runtime.Env;
import test.GUI.Node;

public class LMNtalWindow extends JFrame{

	public LMNGraphPanel lmnPanel = null;
	public LMNtalGFrame lmnframe = null;
	public boolean busy = true;
	public boolean running = true;
	public boolean waitawhile = false;
	private boolean killed = false;
	public long timer = 0;
	
	/*ウィンドウ生成に必要*/
	private boolean ready = false; 
	public String name = null;
	int sizex = 0;
	int sizey = 0;
	public int color_r = 255;
	public int color_g = 255;
	public int color_b = 255;
	private int win_x = 0;
	private int win_y = 0;
	private boolean win_loc = false;
	
	public void setcolor(int a, int b , int c){
		if(a > 255) color_r = 255;
		else if(a < 0)color_r = 0;
		else color_r = a;
		
		if(b > 255) color_g =255;
		else if(b < 0)color_g = 0;
		else color_g = b;
		
		if(c > 255) color_b = 255;
		else if(c < 0)color_b = 0;
		else color_b = c;
	}
	
    public LMNtalWindow(AbstractMembrane m, LMNtalGFrame frame){
    	ready = setatoms(m);
    	lmnframe = frame;
    }
    
	public void setname(int n){
		name = Integer.toString(n);
	}
	public void setname(String n){
		name = n;
	}
	public void setwinloc(int x, int y){
		win_x=x;
		win_y=y;
		win_loc=true;
	}
    
	public boolean makewindow(){
		if(!ready)return false;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = busy = waitawhile = false;
				//閉じる際に、lmnPanelを殺す。
				if(lmnPanel!=null){
					lmnPanel.stop();
//					lmnPanel = null;
				}
				killed = true;
				lmnframe.closewindow(name);
				
			}
		});
		initComponents();
		setSize(sizex,sizey);
		if(Env.getExtendedOption("screen").equals("max")) {
			setExtendedState(Frame.MAXIMIZED_BOTH | getExtendedState());
		}
//		setLocationByPlatform(true);
		if(win_loc)
			setLocation(win_x,win_y);
		setVisible(true);
//		System.out.println("make window");
		return true;
	}
	
	public boolean setgraphicmem(AbstractMembrane m, int distance){
		synchronized(Global.lock){
			if(killed) return true;
			if(lmnPanel == null)return false;
			lmnPanel.setgraphicmem(m, distance);
			lmnPanel.repaint();
		}
		return true;
	}
	
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		
		setTitle("It's Graphical LMNtal");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(lmnPanel), BorderLayout.CENTER);
	}
	
	/**
	 * 描画用のアトム郡の取得
	 */
	private boolean setatoms(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName()=="name"){
				if(a.getEdgeCount() != 1)return false;
				setname(a.getNthNode(0).getName());
			}
			/**サイズの取得*/
			else if(a.getName()=="size"){
				if(a.getEdgeCount() != 2)return false;
				try{
					sizex = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){
					return false;
				}

				try{
					sizey = Integer.parseInt(a.getNthNode(1).getName());
				}catch(NumberFormatException error){
					return false;
					
				}
			}
			/**背景色の取得*/
			else if(a.getName()=="bgcolor"){
				if(a.getEdgeCount() != 3)return false;
				try{
					setcolor(Integer.parseInt(a.getNthNode(0).getName()), Integer.parseInt(a.getNthNode(1).getName()), Integer.parseInt(a.getNthNode(2).getName()));
				}catch(NumberFormatException error){
					return false;
				}
			}
			/**ウィンドウ生成位置の取得*/
			else if(a.getName()=="position"){
				if(a.getEdgeCount() != 2)return false;
				try{
					setwinloc(Integer.parseInt(a.getNthNode(0).getName()), Integer.parseInt(a.getNthNode(1).getName()));
				}catch(NumberFormatException error){
					return false;
				}
			}
			/**計算後の待機時間の取得*/
			else if(a.getName()=="timer"){
				if(a.getEdgeCount() != 1)return false;
				try{
					timer=(Long.parseLong(a.getNthNode(0).getName()));
				}catch(NumberFormatException error){
					return false;
				}
			}
		}
		if(name!=null & sizex > 0 & sizey > 0)
			return true;
		return false;
	}
}

class Global{
	public static Object lock =new Object();
}
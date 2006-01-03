package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

import runtime.AbstractMembrane;
import runtime.Membrane;
import runtime.Env;
import runtime.Functor;
import runtime.Atom;
import test.GUI.Node;

public class LMNtalWindow extends JFrame{
	
	public LMNGraphPanel lmnPanel = null;
	public LMNtalGFrame lmnframe = null;
	private boolean busy = false;
	private boolean killed = false;
	public long timer = 0;
	private AbstractMembrane mem=null;
	private Thread th;
	private boolean keyListener = false;
	
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
	
	public boolean getBusy(){
		return busy;
	}
	public void setBusy(boolean b){
		busy = b;
	}
	
	public void setmem(AbstractMembrane m){
		mem=m;
		try{
			notifyAll();
		}catch(IllegalMonitorStateException e){}
	}
	public AbstractMembrane getmem(){
		return mem;
	}
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
				//閉じる際に、lmnPanelを殺す。
				if(lmnPanel!=null){
					lmnPanel.stop();
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
			setLocation(win_x, win_y);
		
		if(keyListener)
			this.addKeyListener(new MyKeyAdapter(this));
		
		Iterator ite = mem.atomIterator();
		while(ite.hasNext()){
			Atom a = (Atom)ite.next();
			if(a.getName()=="keyListener"){
				a.remove();
				Atom key = mem.newAtom(new Functor("keyListener", 1));
				Atom nil = mem.newAtom(new Functor("[]", 1));
				mem.newLink(key, 0, nil, 0);

				break;
			}
		}
		setVisible(true);
		return true;
	}
	
	public synchronized boolean setgraphicmem(AbstractMembrane m, int distance){
		if(killed) return true;
		if(lmnPanel == null)return false;
		lmnPanel.setgraphicmem(m, distance);
		lmnPanel.repaint();
		return true;
	}
	
	protected void initComponents() {
		lmnPanel = new LMNGraphPanel(this);
		
		setTitle(name);
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
			if(a.getName() == "name"){
				if(a.getEdgeCount() != 1)return false;
				setname(a.getNthNode(0).getName());
			}
			/**サイズの取得*/
			else if(a.getName() == "size"){
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
			/**キーアダプタの設置*/
			else if(a.getName() == "keyListener"){
				keyListener = true;
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

class MyKeyAdapter extends KeyAdapter{
	LMNtalWindow window;
	MyKeyAdapter(LMNtalWindow w) {
		window = w;
	}
	
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		String input;
		if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)input = String.valueOf(e.getKeyCode());
		else input = String.valueOf(e.getKeyChar());
		window.lmnframe.setAddAtom((new Functor(input,1)), window.name);
		
	}
}
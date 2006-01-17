package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

import runtime.AbstractMembrane;
import runtime.Env;
import runtime.Functor;
import runtime.StringFunctor;
import runtime.IntegerFunctor;
import runtime.Atom;
import test.GUI.Node;

public class LMNtalWindow extends JFrame{
	
	private LMNGraphPanel lmnPanel = null;
	private LMNtalGFrame lmnframe = null;
//	private boolean busy = false;
	private boolean killed = false;
	public long timer = 0;
	private AbstractMembrane mem=null;
	private boolean keyChar = false;
	private boolean keyListener = false;
	private Functor keyAtomFunctor = null;
	private boolean keyCache = true;
	
	/*ウィンドウ生成に必要*/
	private boolean ready = false; 
	private String name = null;
	private int sizex = 0;
	private int sizey = 0;
	private int color_r = 255;
	private int color_g = 255;
	private int color_b = 255;
	private int win_x = 0;
	private int win_y = 0;
	private boolean win_loc = false;
	private LinkedList atomlist = new LinkedList();
	
	public Color getColor(){
		return (new Color(color_r,color_g,color_b));
	}
//	public boolean getBusy(){
//		return busy;
//	}
//	public void setBusy(boolean b){
//		busy = b;
//	}
	
	public void setMem(AbstractMembrane m){
		mem=m;
	}
	public AbstractMembrane getMem(){
		return mem;
	}
	public void setColor(int a, int b , int c){
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
		ready = setAtoms(m);
		lmnframe = frame;
	}
	
	public void setName(int n){
		name = Integer.toString(n);
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	public void setWinLoc(int x, int y){
		win_x=x;
		win_y=y;
		win_loc=true;
	}
	
	public boolean makeWindow(){
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
		
		if(keyListener && keyChar)
			this.addKeyListener(new MyKeyAdapter(this, true));
		else if(keyListener && !keyChar)
			this.addKeyListener(new MyKeyAdapter(this, false));
		
		Iterator ite = mem.atomIterator();
		while(ite.hasNext()){
			Atom a = (Atom)ite.next();
			if(a.getName()=="keyChar" || a.getName()=="keyCode"){
				Atom key = mem.newAtom(new Functor(a.getName(), a.getFunctor().getArity()+1));
				for(int i=0;i < a.getFunctor().getArity();i++){
					mem.relink(key,i+1,a,i);
				}
				a.remove();
				Atom nil = mem.newAtom(new Functor("[]", 1));
				mem.newLink(key, 0, nil, 0);
				mem.activate();

				break;
			}
		}
		setVisible(true);
		return true;
	}
	
	public synchronized boolean setGraphicMem(AbstractMembrane m, int distance){
		if(killed) return true;
		if(lmnPanel == null)return false;
		lmnPanel.setgraphicmem(m, distance);
		lmnPanel.repaint();
		return true;
	}
	
	public synchronized boolean removeGraphicMem(AbstractMembrane m){
		if(killed) return true;
		if(lmnPanel == null)return false;
		lmnPanel.removegraphicmem(m);
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
	private boolean setAtoms(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;
		
		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName() == "name"){
				if(a.getEdgeCount() != 1)return false;
				setName(a.getNthNode(0).getName());
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
			else if(a.getName() == "keyChar"){
				keyListener = true;
				keyChar = true;
				keyCache = true;
			}
			else if(a.getName() == "keyCode"){
				keyListener = true;
				keyChar = false;
				keyCache = true;
			}
			else if(a.getName() == "keyCharNoChache"){
				keyListener = true;
				keyChar = true;
				keyCache = false;
			}
			else if(a.getName() == "keyCodeNoCache"){
				keyListener = true;
				keyChar = false;
				keyCache = false;
			}
			/**背景色の取得*/
			else if(a.getName()=="bgcolor"){
				if(a.getEdgeCount() != 3)return false;
				try{
					setColor(Integer.parseInt(a.getNthNode(0).getName()), Integer.parseInt(a.getNthNode(1).getName()), Integer.parseInt(a.getNthNode(2).getName()));
				}catch(NumberFormatException error){
					return false;
				}
			}
			/**ウィンドウ生成位置の取得*/
			else if(a.getName()=="position"){
				if(a.getEdgeCount() != 2)return false;
				try{
					setWinLoc(Integer.parseInt(a.getNthNode(0).getName()), Integer.parseInt(a.getNthNode(1).getName()));
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
	public void setAddAtom(Functor f){
		if(keyCache){
			synchronized(this){
				atomlist.add(f);
			}
		}
		else
			keyAtomFunctor = f;
	}
	/**キー入力でリストに積まれたアトム（Functor）を膜に追加する*/
	public synchronized void doAddAtom(){
		synchronized(this){
			Iterator ite = getMem().atomIterator();
			/*keyCacheがfalseのとき*/
			if(!keyCache){
				/*積まれたアトムを追加するリストを検索*/
				while(keyAtomFunctor!=null && ite.hasNext()){
					Atom a = (Atom)ite.next();
					if(a.getName()=="keyCharNoCache" || a.getName()=="keyCodeNoCache"){
						Atom data = getMem().newAtom(keyAtomFunctor);
//						Atom key = mem.newAtom(new Functor(a.getName(), 1));
						Atom key = mem.newAtom(new Functor(a.getName(), a.getFunctor().getArity()+1));
						for(int i=0;i < a.getFunctor().getArity();i++){
							mem.relink(key,i+1,a,i);
						}
						getMem().newLink(key, 0, data, 0);
						a.remove();
						keyAtomFunctor = null;
						break;
					}
				}
				return;
			}
			/*keyCacheがtrueのとき*/
			while(!atomlist.isEmpty()){
				Functor fa = (Functor)atomlist.removeFirst();
				
//				WindowSet win = (WindowSet)windowmap.get(wa.window);
				/*積まれたアトムを追加するリストを検索*/
				while(ite.hasNext()){
					Atom a = (Atom)ite.next();
					if(a.getName()=="keyChar" || a.getName()=="keyCode"){
						Atom nth1 = a;
						Atom nth2 = null;
						int nth1_arg=0;
						while(true){
							try{
								nth2 = nth1.getArg(nth1_arg).getAtom();
							}catch(ArrayIndexOutOfBoundsException e){
								break;
							}
							if(nth2.getName().equals("[]")){
								Atom data = getMem().newAtom(fa);
								Atom dot = getMem().newAtom(new Functor(".", 3));
								getMem().newLink(dot, 0, data, 0);
								getMem().newLink(nth1, nth1_arg, dot, 2);
								getMem().newLink(nth2, 0, dot, 1);
								break;
							}
							nth1 = nth2;
							nth1_arg=1;
//							if(nth1.getFunctor().getArity()==1)
//							nth1_arg=0;
						}
						break;
					}
				}
			}
		}
	}
}


class MyKeyAdapter extends KeyAdapter{
	LMNtalWindow window;
	boolean byChar;
	
	MyKeyAdapter(LMNtalWindow w, boolean c) {
		window = w;
		byChar = c;
	}
	
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if(byChar){
			String input;
			if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)input = String.valueOf(e.getKeyCode());
			else input = String.valueOf(e.getKeyChar());
			window.setAddAtom((new StringFunctor(input)));
		}else{
			int input = e.getKeyCode();
			window.setAddAtom((new IntegerFunctor(input)));
		}
	}
}
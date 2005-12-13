package graphic;

import java.awt.*;
import java.util.*;


import runtime.AbstractMembrane;
import runtime.Env;
import test.GUI.Node;
/**
 * 
 * @author nakano
 *	ウィンドウをクラスを管理。
 *　　ウィンドウ生成はLMNWindowに一任。
 *	塗りつぶしや、配置などはLMNGraphPanelに一任。
 *
 */

//public class LMNtalGFrame{
	public class LMNtalGFrame implements Runnable{

	public LMNGraphPanel lmnPanel = null;
	boolean busy;
	Thread th;
	runtime.Membrane rootMem;
	HashMap windowmap=new HashMap();
	LinkedList tmplist = new LinkedList();
	int killednum=0;
	public static Object lock2 = new Object();
	long start ,stop,diff;
	public boolean running = true;

	
    public LMNtalGFrame(){
    	this.start();
    }

	/**
	 * 指定された名称のアトムが存在するか検索。
	 * あれば真を、なければ偽を返す。
	 */
	private synchronized String searchatom(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

		while(ite.hasNext()){
			a = (Node)ite.next();
			if(a.getName() == "window"){
				return "window";
			}else if(a.getName() == "draw"){
				return "draw";
			}else if(a.getName() == "graphic"){
				return "graphic";
			}else if(a.getName() == "remove"){
				return "remove";
			}
		}
		return null;
				
	}
	
	public void setRootMem(runtime.Membrane rootMem) {
		this.rootMem = rootMem;
		
	}
	
    public synchronized void setmem(AbstractMembrane m){
		String s = searchatom(m);
			/*ウィンドウ膜の登録*/
	    	if(s == "window"){
	    		setwindowmem(m);    		
	    	}
	    	/*描画膜の登録*/
	    	else if(s=="draw" || s=="graphic"){
	    		setgraphicmem(m);
	    	}
    }
    
   /**マウスの位置を検出する。ライブラリmouseで使用*/
   public Point getMousePoint(AbstractMembrane m){
	   String memname = getname(m);
	   if(!windowmap.containsKey(memname)) return null;
	   WindowSet winset = (WindowSet)windowmap.get(memname);
	   return winset.window.getMousePosition();
   }
   
    /**ウィンドウオブジェクトを生成*/
    private void setwindowmem(AbstractMembrane m){
		WindowSet win = new WindowSet();
		win.mem = m;
		win.window = new LMNtalWindow(m, this);
		if(!windowmap.containsKey(win.window.name)){
			win.window.makewindow();
			
			windowmap.put(win.window.name, win);
		}else{
			WindowSet tmpwin = (WindowSet)windowmap.get(win.window.name);
			tmpwin.window.timer = win.window.timer;
		}
    }
    private synchronized void setgraphicmem(AbstractMembrane tmp){
	   if(tmp == null || tmp.isRoot() )return;
	   AbstractMembrane m = tmp.getParent();
	   /*ウィンドウ膜との距離*/
	   int distance = 0;
	   
	   while(m!=null){
		   if(m.isRoot())
			   return;
		   String n = getname(m);
		   /*ウィンドウ膜が登録済み*/
		   if(windowmap.containsKey(n)){
			   	WindowSet win = (WindowSet)windowmap.get(n);
			   	
				/*描画間隔の測定用*/
				stop = System.currentTimeMillis();
				diff = stop - start;
				long diff2 = win.window.timer - diff;
				if(diff2 > 0)
					waitBusy(diff2);
//				System.out.println("実行時間 : "+diff+"ミリ秒");
				start = System.currentTimeMillis();
				
				win.window.setgraphicmem(tmp,distance);
				

				return;
			}
		   /*ウィンドウ膜が未登録*/
		   else{
			   if(searchwinmem(m)){
				   n = getname(m);
				   if(windowmap.containsKey(n)){
						
					   	WindowSet win = (WindowSet)windowmap.get(n);
						/*描画間隔の測定用*/
						stop = System.currentTimeMillis();
						diff = stop - start;
						long diff2 = win.window.timer - diff;
						if(diff2 > 0)
							waitBusy(diff2);
//						System.out.println("実行時間 : "+diff+"ミリ秒");
						start = System.currentTimeMillis();
						win.window.setgraphicmem(tmp,distance);
						return;
					}
			   }
		   }
			m = m.getParent();
			distance++;
	   }
   }
    
    /**再帰的に親膜を探索しウィンドウ膜を探す。発見できれば真。出来なければ偽を返す。*/
    private boolean searchwinmem(AbstractMembrane m){

    	String s = searchatom(m);
		/*ウィンドウ膜の登録*/
    	if(s == "window"){
    		setwindowmem(m);
    		return true;
    	}else{
    		if(m.getParent()!=null & !m.getParent().isRoot()){
    			searchwinmem(m.getParent());
    		}
    	}
    	return false;
    }
   
    /**ウィンドウが閉じられたときの動作。すべてのウィンドウが閉じたら終了。*/
   public void closewindow(String killme){
	   if(!windowmap.containsKey(killme)){
		   return;
	   }
	   
	   WindowSet win = (WindowSet)windowmap.get(killme);
	   win.killed = true;
//	   windowmap.put(killme, win);
	   
	   killednum++;
	   if(killednum == windowmap.size()){
		  busy=true;  
	   }

   }
   
   /**nameアトムがあればそれに繋がったアトム名を取得。なければnullを返す。*/
   private String getname(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName()=="name"){
				if(a.getEdgeCount() != 1)return null;
				return a.getNthNode(0).getName();
			}
			
		}
		return null;
	}
    
	/**
	 * スレッド関係
	 */
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}


	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				th.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	/** @return ルールスレッドの実行を継続してよいかどうか */
	public boolean onTrace() {
		if(Env.fGraphic) {
////			lmnPanel.start();
			//waitBusy();
////			lmnPanel.stop();
		}
//		System.out.println(busy);
		if(busy)return false;
		
//		return running;
		return true;
	}
	
	public void waitBusy(long s) {
//		busy = true;
////		System.out.print("*");
//		while(busy) {
			try {
				th.sleep(s);
//				th.wait(s);
				//System.out.println("wait");
				//busy = waitawhile;
			} catch (InterruptedException e) {
			}
//		}
	}
}
/**ウィンドウ膜クラス。膜と、生存フラグ、ウィンドウを保持*/
class WindowSet{
	public AbstractMembrane mem;
	public LMNtalWindow window;
	public boolean killed = false;
	WindowSet(){
	}
}
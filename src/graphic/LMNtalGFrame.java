package graphic;

import java.awt.*;
import java.util.*;


import runtime.AbstractMembrane;
import runtime.*;
import test.GUI.Node;
/**
 * 
 * @author nakano
 *	ウィンドウをクラスを管理。
 *　　ウィンドウ生成はLMNWindowに一任。
 *	塗りつぶしや、配置などはLMNGraphPanelに一任。
 *
 */

public class LMNtalGFrame{
	
	public LMNGraphPanel lmnPanel = null;
	boolean busy;
	private Thread th;
	runtime.Membrane rootMem;
	HashMap windowmap=new HashMap();
	LinkedList tmplist = new LinkedList();
	int killednum=0;
	public static Object lock2 = new Object();
	long start ,stop,diff;
	public boolean running = true;
	public LinkedList atomlist = new LinkedList();
	
	
	public LMNtalGFrame(){
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
			doAddAtom();		
		}
		/*描画膜の登録*/
		else if(s=="draw" || s=="graphic"){
			setgraphicmem(m);
		}
	}
	
	/**マウスの位置を検出する。ライブラリmouseで使用*/
	public Point getMousePoint(AbstractMembrane m){
		if(m.isRoot())return null;
		String memname = getname(m);
		if(!windowmap.containsKey(memname)) return getMousePoint(m.getParent());
		WindowSet winset = (WindowSet)windowmap.get(memname);
		return winset.window.getMousePosition();
	}
	
	/**ウィンドウオブジェクトを生成*/
	private void setwindowmem(AbstractMembrane m){
		WindowSet win = new WindowSet();
		win.window = new LMNtalWindow(m, this);
		win.window.setmem(m);
		if(!windowmap.containsKey(win.window.name)){
			win.window.makewindow();
			windowmap.put(win.window.name, win);
		}else{
			WindowSet tmpwin = (WindowSet)windowmap.get(win.window.name);
			tmpwin.window.setmem(m);
			tmpwin.window.timer = win.window.timer;
//			WindowSet tmpwin2 = (WindowSet)windowmap.get(win.window.name);
		}
	}
	
	public LMNtalWindow getWindow(String name){
		if(!windowmap.containsKey(name))return null;
		WindowSet tmpwin = (WindowSet)windowmap.get(name);
		return tmpwin.window;
	}
	public void setAddAtom(Functor a, String win){
		WaitingAtomSet wa = new WaitingAtomSet(a,win);
		atomlist.add(wa);
	}
	public void doAddAtom(){
		while(!atomlist.isEmpty()){
			WaitingAtomSet wa = (WaitingAtomSet)atomlist.removeFirst();
			
			if(windowmap.containsKey(wa.window)){
				WindowSet win = (WindowSet)windowmap.get(wa.window);
				Atom data = win.window.getmem().newAtom(wa.functor);
				Iterator ite = win.window.getmem().atomIterator();
				while(ite.hasNext()){
					Atom a = (Atom)ite.next();
					if(a.getName()=="keyListener"){
						Atom nth1 = a;
						Atom nth2 = null;
						while(true){
							nth2 = nth1.getArg(nth1.getFunctor().getArity()-1).getAtom();
							System.out.println(nth2.getName());
							if(nth2.getName().equals("[]")){
								Atom dot = win.window.getmem().newAtom(new Functor(".", 3));
								win.window.getmem().newLink(dot, 0, data, 0);
								win.window.getmem().relink(dot, 2, nth2, 1);
								win.window.getmem().newLink(dot, 1, nth2, 1);
//								win.window.getmem().newLink(nth1, nth1.getFunctor().getArity()-1, addedAtom, 0);
//								win.window.getmem().newLink(nth2, 0, addedAtom, 1);
								break;
							}
							nth1 = nth2;
						}
						break;
					}
				}
			}
			
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
//				System.out.println("実行時間 : "+diff+"+"+diff2+"="+(diff+diff2)+"ミリ秒");
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
//						System.out.println("実行時間 : "+diff+"+"+diff2+"="+(diff+diff2)+"ミリ秒");
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
		
		killednum++;
		if(killednum == windowmap.size()){
			busy=true;
			runtime.LMNtalRuntimeManager.terminateAllThreaded();
			System.exit(0);
		}
		
	}
	
	/**nameアトムがあればそれに繋がったアトム名を取得。なければnullを返す。*/
	public String getname(AbstractMembrane m){
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

	/** @return ルールスレッドの実行を継続してよいかどうか */
	public boolean onTrace() {
		if(busy)return false;
		return true;
	}
	
	public void waitBusy(long s) {	
		try {
			th.sleep(s);
		} catch (InterruptedException e) {}
	}
}
/**ウィンドウ膜クラス。膜と、生存フラグ、ウィンドウを保持*/
class WindowSet{
	public LMNtalWindow window;
	public boolean killed = false;
}

class WaitingAtomSet{
	public Functor functor;
	public String window;
	public WaitingAtomSet(Functor f, String w){
		functor=f;
		window=w;
	}
}
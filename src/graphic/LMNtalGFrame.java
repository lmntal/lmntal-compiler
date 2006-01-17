package graphic;

import java.awt.*;
import java.util.*;


import runtime.AbstractMembrane;
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
	
	boolean busy;
	private Thread th;
	runtime.Membrane rootMem;
	HashMap windowmap=new HashMap();
//	LinkedList tmplist = new LinkedList();
	int killednum=0;
	long start ,stop,diff;
	public boolean running = true;
	
	
	/**
	 * 特定の名称のアトムが存在するか検索。
	 * あればアトム名を、なければnullを返す。
	 */
	private synchronized String searchAtom(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;
		
		while(ite.hasNext()){
			a = (Node)ite.next();
			if(a.getName() == "window"){
				return "window";
			}else if(a.getName() == "draw"){
				return "draw";
			}else if(a.getName() == "relative"){
				return "relative";
			}else if(a.getName() == "remove"){
				return "remove";
			}
		}
		return null;
		
	}
	
	public void setRootMem(runtime.Membrane rootMem) {
		this.rootMem = rootMem;
		
	}
	
	public synchronized void setMem(AbstractMembrane m){
		
		String s = searchAtom(m);
		/*ウィンドウ膜の登録*/
		if(s == "window"){
			setWindowMem(m);
//			doAddAtom();		
		}
		/*描画膜の登録*/
		else if(s=="draw" || s=="relative"){
			setGraphicMem(m);
		}
		/*描画膜の削除*/
		else if(s=="remove"){
			removeGraphicMem(m);
		}
	}
	
	/**マウスの位置を検出する。ライブラリmouseで使用*/
	public Point getMousePoint(AbstractMembrane m){
		if(m.isRoot())return null;
		String memname = getName(m);
		if(!windowmap.containsKey(memname)) return getMousePoint(m.getParent());
		WindowSet winset = (WindowSet)windowmap.get(memname);
		return winset.window.getMousePosition();
	}
	
	/**ウィンドウオブジェクトを生成*/
	private void setWindowMem(AbstractMembrane m){
		WindowSet win = new WindowSet();
		win.window = new LMNtalWindow(m, this);
		win.window.setMem(m);
		if(!windowmap.containsKey(win.window.getName())){
			win.window.makeWindow();
			windowmap.put(win.window.getName(), win);
		}else{
			WindowSet tmpwin = (WindowSet)windowmap.get(win.window.getName());
			tmpwin.window.setMem(m);
			tmpwin.window.timer = win.window.timer;
			tmpwin.window.doAddAtom();
//			WindowSet tmpwin2 = (WindowSet)windowmap.get(win.window.name);
		}
	}
	
	public LMNtalWindow getWindow(String name){
		if(!windowmap.containsKey(name))return null;
		WindowSet tmpwin = (WindowSet)windowmap.get(name);
		return tmpwin.window;
	}
	public synchronized void removeGraphicMem(AbstractMembrane tmp){
		if(tmp == null || tmp.isRoot() )return;

		String n = searchWinName(tmp);
		/*ウィンドウ膜が登録済み*/
		if(windowmap.containsKey(n)){
			WindowSet win=(WindowSet)windowmap.get(n);
			win.window.removeGraphicMem(tmp);
			return;
		}
	}
	
	private synchronized void setGraphicMem(AbstractMembrane tmp){
		if(tmp == null || tmp.isRoot() )return;
		AbstractMembrane m = tmp.getParent();
		/*ウィンドウ膜との距離*/
		int distance = 0;
		
		while(m!=null){
			if(m.isRoot())
				return;
			String n = getName(m);
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
				
				win.window.setGraphicMem(tmp,distance);
				
				
				return;
			}
			/*ウィンドウ膜が未登録*/
			else{
				if(searchWinMem(m)){
					n = getName(m);
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
						win.window.setGraphicMem(tmp,distance);
						return;
					}
				}
			}
			m = m.getParent();
			distance++;
		}
	}
	
	/**再帰的に親膜を探索しウィンドウ膜を探す。発見できれば真。出来なければ偽を返す。*/
	private boolean searchWinMem(AbstractMembrane m){
		
		String s = searchAtom(m);
		/*ウィンドウ膜の登録*/
		if(s == "window"){
			setWindowMem(m);
			return true;
		}else{
			if(m.getParent()!=null & !m.getParent().isRoot()){
				return searchWinMem(m.getParent());
			}
		}
		return false;
	}
	
	/**再帰的に親膜を探索しウィンドウ膜を探す。発見できればウィンドウ膜の名前。出来なければnullを返す。*/
	private String searchWinName(AbstractMembrane m){
		
		String s = searchAtom(m);
		/*ウィンドウ膜の登録*/
		if(s == "window"){
			return getName(m);
		}else{
			if(m.getParent()!=null & !m.getParent().isRoot()){
				return searchWinName(m.getParent());
			}
		}
		return null;
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
	public String getName(AbstractMembrane m){
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

package graphic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;

import runtime.AbstractMembrane;
import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

/**
 * Graphic LMNtalのメインクラス．
 * 本体とのすべての通信はこのクラスを介す．
 * @author nakano
 *
 */
public class LMNtalGFrame{	
	final static
	private Functor WINDOW_MEM = new Functor("window",0);
	
	final static
	private Functor NAME_ATOM = new Functor("name",1); 
	
	// ウィンドウ管理マップ．ウィンドウ名(String)をキーにして管理
	final private HashMap windowMap = new HashMap();

	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalGFrame(){}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 本体からアンロックされた膜すべて受け取る．
	 * 受け取った膜はグラフィック膜であるとは限らない．
	 * @param mem
	 */
	public void setMem(Membrane mem){
		// windowアトムがあればウィンドウ膜の登録
		if(mem.getAtomCountOfFunctor(WINDOW_MEM)>0){
			setWindowMem(mem);
		}
		// ウィンドウ膜でなく，かつ親がルート膜でなければ膜管理マップに追加
		else if(null != mem.getParent() && !mem.getParent().isRoot()){
			LMNtalWindow window = searchWindowMem(mem.getParent());
			if(null != window){ window.setChildMem(mem); }
		}
	}
	
	/**
	 * 祖先膜からウィンドウ膜を探索する．
	 * @param mem
	 * @return LMNtalWindow
	 */
	private LMNtalWindow searchWindowMem(AbstractMembrane mem){
		while(!mem.isRoot()){
			if(windowMap.containsKey(mem.getGlobalMemID())){
				return (LMNtalWindow)windowMap.get(mem.getGlobalMemID());
			}
			else if(mem.getAtomCountOfFunctor(WINDOW_MEM)>0){
				setWindowMem(mem);
				Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_ATOM);
				if(!nameAtomIte.hasNext()){ continue; }
				String windowName = ((Atom)nameAtomIte.next()).nth(0);
				return (LMNtalWindow)windowMap.get(windowName);
			}
			mem = mem.getParent();
		}
		return null;
	}
	
	/**
	 * ウィンドウ膜の登録を行う．
	 * 受け取る膜はウィンドウ膜であることを保証されていること．
	 * 未登録の膜の場合はウィンドウを生成する，
	 * 登録済みの膜の場合は膜のIDを更新する．
	 * @param mem
	 */
	public void setWindowMem(AbstractMembrane mem){
		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_ATOM);
		if(nameAtomIte.hasNext()){
			String windowName = ((Atom)nameAtomIte.next()).nth(0);
			// すでにウィンドウがある
			if(windowMap.containsKey(windowName)){
				LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
				window.resetWindow(mem);
			}
			//　ウィンドウが無い
			else{
				LMNtalWindow window = new LMNtalWindow(mem);
				windowMap.put(windowName, window);
			}
		}
	}
	
	/**
	 * 本体から除去された膜をすべて受け取る．
	 * 受け取った膜はグラフィック膜であるとは限らない．
	 * @param mem
	 */
	public void removeGraphicMem(AbstractMembrane mem){
		if(null != mem.getParent()){
			Iterator windowIte = windowMap.values().iterator();
			while(windowIte.hasNext()){ ((LMNtalWindow)windowIte.next()).removeChildMem(mem); }
		}
	}
	
	public void setRepaint(Membrane mem, boolean flag){
		if(windowMap.containsKey(mem.getGlobalMemID())){
			LMNtalWindow window = (LMNtalWindow)windowMap.get(mem.getGlobalMemID());
			window.setRepaint(flag);
		}
	}
	/**
	 * マウスの位置を検出する。ライブラリmouseで使用
	 * @param mem
	 */
	  
	public Point getMousePoint(AbstractMembrane mem){
		if(mem.isRoot())return null;
		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_ATOM);
		if(nameAtomIte.hasNext()){
			String windowName = ((Atom)nameAtomIte.next()).nth(0);
			LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
			if(null != window){
				window.getMousePosition();
			}
		}
		return getMousePoint(mem.getParent());
	}
	
}

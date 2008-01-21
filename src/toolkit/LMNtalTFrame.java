package toolkit;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

/** --wtモードで実行したときの処理 */
public class LMNtalTFrame {

	final static
	private Functor WINDOW_FUNCTOR = new SymbolFunctor("window",0);

	final static
	private Functor NAME_FUNCTOR = new SymbolFunctor("name",1); 
	
	final static
	private Set UPDATE_COMMAND = new HashSet();
	
	// ウィンドウ管理マップ．ウィンドウ名(String)をキーにして管理
	final private HashMap windowMap = new HashMap();

	/** 消される膜の中身を管理 */
	final private HashSet<String> removedIDSet = new HashSet<String>();
		
	/** LMNtalTFrameのコンストラクタ */
	public LMNtalTFrame(){}
	
	/** 更新されたコンポーネントを追加する */
	public static void addUpdateComponent(LMNComponent component){
		UPDATE_COMMAND.add(component);
	}
	
	/** 追加されたコンポーネントを読んでアトムを追加する */
	private void addAtom(){
		// UPDATE_COMMANDの中身をIteratorとしてaddAtomIteに入れる
		Iterator addAtomIte = UPDATE_COMMAND.iterator();
		while(addAtomIte.hasNext()){
			// Setで受け取ったオブジェクトをキャストして、componentに入れる
			LMNComponent component = (LMNComponent)addAtomIte.next();
			component.addAtom();
		}
		UPDATE_COMMAND.clear();
	}
	
	/**
	 * 本体からアンロックされた膜すべて受け取る(=mem)
	 * 受け取った膜はグラフィック膜であるとは限らない．
	 * (アンロックされる直前に呼ばれるメソッド) runtime.Membraneのunlock(boolean)
	 */
	public void setMem(Membrane mem){
		addAtom();
		//指定されたファンクタ(window.)をもつアトムの数を取得し,
		//0以上ならそのままその膜をsetWindowMemに渡す。
		if(mem.getAtomCountOfFunctor(WINDOW_FUNCTOR)>0){
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
	private LMNtalWindow searchWindowMem(Membrane mem){
		while(!mem.isRoot()){
			if(windowMap.containsKey(mem.getMemID())){
				return (LMNtalWindow)windowMap.get(mem.getMemID());
			}
			else if(mem.getAtomCountOfFunctor(WINDOW_FUNCTOR)>0){
				//window.というアトムをその膜に持っていたら
				setWindowMem(mem);
				Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_FUNCTOR);
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
	public void setWindowMem(Membrane mem){
		if(mem.getAtomCountOfFunctor(NAME_FUNCTOR)==0){ // 正しくなかったらエラー出して終了
			System.out.println("Name Error. Plaese make Name_Atom with one argument");
			System.exit(0);
		}
		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_FUNCTOR);
		if(nameAtomIte.hasNext()){
			String windowName = ((Atom)nameAtomIte.next()).nth(0);
			// すでにウィンドウがある
			if(windowMap.containsKey(windowName)){
				LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
			}
			//　ウィンドウが無い
			else{
				LMNtalWindow window = new LMNtalWindow(mem, windowName);
				windowMap.put(windowName, window);			
			}
		}
	}
	
	/**
	 * 本体から除去された膜をすべて受け取る．
	 * 受け取った膜はグラフィック膜であるとは限らない．
	 * @param mem
	 */
	public void addRemovedMem(Membrane mem){ // 消える直前の処理(runtime.Membraneより)
		String id = mem.getMemID(); // 左辺の膜のIDを取得
		String componentID = LMNtalWindow.getmemIDMapKey(id); //消える膜に対応するコンポーネントのIDを取得
		if(componentID != null)
			removedIDSet.add(componentID); // removeされた膜のIDをremovedIDSetの中に残す
		LMNtalWindow.removememIDMap(id);
	}
	
	public void removeMem(){ // ルール適用後の処理(runtime.InterpretedRulesetより)
		Iterator<String> ids = removedIDSet.iterator();
		while(ids.hasNext()){
			String id = ids.next();
			if(id == null){
				return;
			}
			Iterator<LMNtalWindow> windows = windowMap.values().iterator();
			while(windows.hasNext()){
				windows.next().removeChildMem(id); // 左辺にあるコンポーネント消す
			}
		}
		removedIDSet.clear();
		Iterator<LMNtalWindow> windows = windowMap.values().iterator();
		while(windows.hasNext()){
			windows.next().setVisible(true); // ここで再描画
		}		
		System.out.println("remove");
	}
	
	public void setRepaint(Membrane mem, boolean flag){
		setMem(mem);
		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_FUNCTOR);
		if(nameAtomIte.hasNext()){
			String windowName = ((Atom)nameAtomIte.next()).nth(0);
			LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
//			window.setRepaint(flag);

		}
	}
		
//	/**
//	 * マウスの位置を検出する。ライブラリmouseで使用
//	 * @param mem
//	 */
//	  
//	public Point getMousePoint(Membrane mem){
//		if(mem.isRoot())return null;
//		setMem(mem);
//		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_FUNCTOR);
//		if(nameAtomIte.hasNext()){
//			String windowName = ((Atom)nameAtomIte.next()).nth(0);
//			LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
//			if(null != window){
//				window.getMousePosition();
//			}
//		}
//		return getMousePoint(mem.getParent());
//	}

	
	public Dimension getWindowSize(Membrane mem){
		if(mem.isRoot())return null;
		setMem(mem);
		Iterator nameAtomIte = mem.atomIteratorOfFunctor(NAME_FUNCTOR);
		if(nameAtomIte.hasNext()){
			String windowName = ((Atom)nameAtomIte.next()).nth(0);
			LMNtalWindow window = (LMNtalWindow)windowMap.get(windowName);
			if(null != window){
				return window.getSize();
			}
		}
		return getWindowSize(mem.getParent());
	}
	
	
}


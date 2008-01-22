package toolkit;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

import runtime.Atom;
import runtime.Dumper;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;
import util.Util;

public class LMNtalWindow extends JFrame {
	
	/////////////////////////////////////////////////////////////////
	// 検索用Functor
	
	final static
	private Functor NAME_FUNCTOR = new SymbolFunctor("name",1); 

	final static
	private Functor SIZE_FUNCTOR = new SymbolFunctor("size", 2);
	
	final static
	private Functor KILLER_FUNCTOR = new SymbolFunctor("killer", 0);
		
	final static
	private Functor BUTTON_FUNCTOR = new SymbolFunctor("button",0);

	final static
	private Functor TEXTAREA_FUNCTOR = new SymbolFunctor("textarea",0);
	
	final static
	private Functor HTML_FUNCTOR = new SymbolFunctor("html",0);

	final static
	private Functor LABEL_FUNCTOR = new SymbolFunctor("label",0);

	final static
	private Functor GRAPHIC_FUNCTOR = new SymbolFunctor("graphic",0);
	
	final static
	private Functor ID_FUNCTOR = new SymbolFunctor("id", 1);

	final static
	private Functor IMAGE_FUNCTOR = new SymbolFunctor("image", 0);

	final static
	private Functor TIMER_FUNCTOR = new SymbolFunctor("timer", 0);

	/////////////////////////////////////////////////////////////////

	// ウィンドウが閉じられると，プログラムを強制的に終了させるかどうかのフラグ
	private boolean killer = false;

//	private String memID;
	private Membrane mymem;
	private String windowName;
	private int sizeX = 0;
	private int sizeY = 0;
	private GridBagLayout layout;

	private boolean sizeUpdate = false;
	
	// IDとコンポーネントのマップ
	private Map<String, LMNComponent> componentMap = new HashMap();

	// コンポーネントのIDと膜のIDのマップ
	public static Map<String, String> memIDMap = new HashMap();

	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalWindow(Membrane mem, String name){
		resetMembrane(mem);
		windowName = name;
		makeWindow();
	}
	///////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 膜の情報をすべて取得し，再設定する．
	 * 受け取る膜はウィンドウ膜であること保証がされていること．
	 * @param mem
	 */
	public void resetMembrane(Membrane mem){
		Iterator atomIte;
		Atom targetAtom;		
		mymem = mem;
		
		// name atom
		atomIte= mem.atomIteratorOfFunctor(NAME_FUNCTOR);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			windowName = ((null != targetAtom) ? targetAtom.nth(0) : "");
		}
			
		// sizeアトム　を処理
		setSizeAtom(mem);
		
		// killerアトムを処理
		setKiller(mem);

	}
	
	/**
	 * componentの有無の判別
	 */
	public void setChildMem(Membrane mem){
	
		String id = getID(mem); // IDを取得

		if(id == null) {
			setVisible(true); // IDないものは消えてるので再描画			
			return; // IDがなかったら戻る
		}
		
		// IDが存在するとき所属している膜のIDを取得
		String memID = mem.getMemID();
		memIDMap.put(memID, id); // idと膜名を登録
		
		//componentMapのkeyにIDがあったら更新
		if(componentMap.containsKey(id)) {
			LMNComponent component = 
				(LMNComponent)componentMap.get(id); //=button? textarea? label?
			component.resetMembrane(mem);
			return;
		}

		if(mem.getAtomCountOfFunctor(BUTTON_FUNCTOR)>0){
			LMNtalButton button = new LMNtalButton(this, mem);
			componentMap.put(id, button);
		}

		if(mem.getAtomCountOfFunctor(TEXTAREA_FUNCTOR)>0){
			LMNtalTextArea textarea = new LMNtalTextArea(this, mem);
			componentMap.put(id, textarea);
		}
		
		if(mem.getAtomCountOfFunctor(HTML_FUNCTOR)>0){
			LMNtalHtml html = new LMNtalHtml(this, mem);
			componentMap.put(id, html);
		}
		
		if(mem.getAtomCountOfFunctor(LABEL_FUNCTOR)>0){
			LMNtalLabel label = new LMNtalLabel(this, mem);
			componentMap.put(id, label);
		}

		if(mem.getAtomCountOfFunctor(IMAGE_FUNCTOR)>0){
			LMNtalImage image = new LMNtalImage(this, mem);
			componentMap.put(id, image);
		}

		if(mem.getAtomCountOfFunctor(TIMER_FUNCTOR)>0){
			LMNtalTimer timer = new LMNtalTimer(this, mem);
//			componentMap.put(id, timer);
		}

		
		/* LMNtalPanelだけ，特別で，ここではputしない．
		 * searchGraphicMemで管理する．
		 * また，LMNtalPanelの子膜あるかもチェックする．
		 */
		LMNtalGraphic graphicPanel = searchGraphicMem(mem);
		if(graphicPanel != null){
			graphicPanel.setChildMem(mem);
		}
		
	}

	
	public void removeChildMem(String id){
		//componentMapのkeyにIDがあったら更新
		LMNComponent component = 
			(LMNComponent)componentMap.get(id); //=button? textarea? label? ...?
		if(component == null) {
			return;
		}
		remove(component.getComponent()); // 左辺にあったIDに対応するコンポーネントを削除
		componentMap.remove(id);
	}	
	
	// IDの取得
	public static String getID(Membrane mem){
		String Id = null;
		Iterator IdAtomIte= mem.atomIteratorOfFunctor(ID_FUNCTOR);
		if(IdAtomIte.hasNext()){
			Atom targetAtom = (Atom)IdAtomIte.next();
			Id = ((null != targetAtom) ? targetAtom.nth(0) : "");			
		}
		return Id;
	}
	
	/**
	 * 膜内のアトムを検索し，"size"アトムを取得する．
	 * アトムの第一リンク先を幅，第二リンクを高さとする．
	 * @param mem 検索対象の膜
	 */
	private void setSizeAtom(Membrane mem){
		Atom targetAtom;
		Iterator atomIte= mem.atomIteratorOfFunctor(SIZE_FUNCTOR);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				if( (null != targetAtom) &&
					( (sizeX != Integer.parseInt(targetAtom.nth(0))) ||
					  (sizeY != Integer.parseInt(targetAtom.nth(1))) )){
					sizeUpdate = true;
				}
				sizeX = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : 0);
				sizeY = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : 0);
			}
			catch(NumberFormatException e){}
		}
		
	}

	/**
	 * 膜内のアトムを検索し，"killer"アトムを取得する．
	 * "killer"アトムが存在した場合は，killerフラグを立てる
	 * @param mem 検索対象の膜
	 */
	private void setKiller(Membrane mem) {
		// killer
		Iterator atomIte= mem.atomIteratorOfFunctor(KILLER_FUNCTOR);
		if(atomIte.hasNext()){
			killer = true;
		}
	}
	
	
	/** ウィンドウを生成する */
	public void makeWindow(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Util.println("make window");
		setTitle(windowName);
		layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		
		setSize(sizeX, sizeY);
		
		// killerがtrueのとき，ウィンドウを閉じると，プログラムを強制終了させる
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 閉じる際の処理
				if(killer){
					String dump = Dumper.dump(mymem.getTask().getRoot()).toString();
					dump = dump.replaceAll("\\}", "\\}\n");
					dump = dump.replaceAll("\\{", "\n\\{");
					dump = dump.substring(1);
					Util.println(dump);
					System.exit(0);
				}
			}
		});

		setVisible(true);
	}
	
	public GridBagLayout getGridBagLayout(){
		return layout;
	}
	
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * 祖先膜からグラフィック膜を探索する．
	 * LMNtalGraphicの子膜->LMNtalGraphicを返す．
	 * LMNtalGraphicの子膜でない->nullを返す
	 * @param mem
	 * @return LMNtalGraphic
	 */
	private LMNtalGraphic searchGraphicMem(Membrane mem){
		while(!mem.isRoot()){
			String key = getID(mem); // IDを取得
			if(key != null){
				
				// graphic.というアトムをその膜に持っていたら
				if(mem.getAtomCountOfFunctor(GRAPHIC_FUNCTOR)>0){
					
					// すでに登録されてたらこっちから返す
					if(componentMap.containsKey(key)){
						return (LMNtalGraphic)componentMap.get(key);
					}
					
					LMNtalGraphic graphicPanel = new LMNtalGraphic(this, mem);
					componentMap.put(key, graphicPanel);
					return graphicPanel; // graphicが存在した(初回のみ)
				}
			}
			mem = mem.getParent();
		}
		return null;
	}
	
	/**
	 * 膜のIDから、コンポーネントのIDを返す
	 */
	public static String getmemIDMapKey(String memID){
		String id = memIDMap.get(memID);
		return id;
	}
	
	/**
	 * ルール左辺に対応するものはmemIDMapから削除
	 */
	public static void removememIDMap(String memID){
		memIDMap.remove(memID);
	}
	
}
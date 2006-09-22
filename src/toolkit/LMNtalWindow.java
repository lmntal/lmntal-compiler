package toolkit;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.*;

import runtime.Membrane;
import runtime.Atom;
import runtime.Dumper;
import runtime.Functor;
import runtime.SymbolFunctor;

public class LMNtalWindow extends JFrame {
	
	/////////////////////////////////////////////////////////////////
	// 検索用Functor
	
	final static
	private Functor NAME_ATOM = new SymbolFunctor("name",1); 

	final static
	private Functor SIZE_ATOM = new SymbolFunctor("size", 2);
	
	final static
	private Functor KILLER_ATOM = new SymbolFunctor("killer", 0);
	
	final static
	private Functor BUTTON_FUNCTOR = new SymbolFunctor("button",0);
	
	final static
	private Functor TEXTAREA_FUNCTOR = new SymbolFunctor("textarea",0);
	
	/////////////////////////////////////////////////////////////////

	// ウィンドウが閉じられると，プログラムを強制的に終了させるかどうかのフラグ
	private boolean killer = false;

	private String memID;
	private Membrane mymem;
	private String windowName;
	private int sizeX = 0;
	private int sizeY = 0;
	private GridBagLayout layout;

	private boolean sizeUpdate = false;

	
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
		// membrane ID
		memID = mem.getGlobalMemID();
				
		// name atom
		atomIte= mem.atomIteratorOfFunctor(NAME_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			windowName = ((null != targetAtom) ? targetAtom.nth(0) : "");
		}
			
		// sizeアトム　を処理
		setSizeAtom(mem);
		
		// killerアトムを処理
		setKiller(mem);
//		doAddAtom(mem);
	}
	
	/**
	 * componentの有無の判別
	 */
	public void setChildMem(Membrane mem){
	
		if(mem.getAtomCountOfFunctor(BUTTON_FUNCTOR)>0){
			LMNtalButton button = new LMNtalButton(this, mem);
		}

		if(mem.getAtomCountOfFunctor(TEXTAREA_FUNCTOR)>0){
			LMNtalTextArea textarea = new LMNtalTextArea(this, mem);
		}

	}
	
	/**
	 * 膜内のアトムを検索し，"size"アトムを取得する．
	 * アトムの第一リンク先を幅，第二リンクを高さとする．
	 * @param mem 検索対象の膜
	 */
	private void setSizeAtom(Membrane mem){
		Atom targetAtom;
		Iterator atomIte= mem.atomIteratorOfFunctor(SIZE_ATOM);
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
		Iterator atomIte= mem.atomIteratorOfFunctor(KILLER_ATOM);
		if(atomIte.hasNext()){
			killer = true;
		}
	}
	
	
	/** ウィンドウを生成する */
	public void makeWindow(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		System.out.println("make window");
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
					System.out.println(dump);
					System.exit(0);
				}
			}
		});

		setVisible(true);
	}
	
	public GridBagLayout getGridBagLayout(){
		return layout;
	}
	
	
}
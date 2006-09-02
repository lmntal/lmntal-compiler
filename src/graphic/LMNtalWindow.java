package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import runtime.Atom;
import runtime.Dumper;
import runtime.Env;
import runtime.Functor;
import runtime.IntegerFunctor;
import runtime.Membrane;
import runtime.StringFunctor;
import runtime.SymbolFunctor;

public class LMNtalWindow extends JFrame{
	final static
	private Functor NAME_ATOM = new SymbolFunctor("name",1); 
	
	final static
	private Functor SIZE_ATOM = new SymbolFunctor("size", 2);
	
	final static
	private Functor BGCOLOR_ATOM = new SymbolFunctor("bgcolor", 3);
	
	final static
	private Functor POSITION_ATOM = new SymbolFunctor("position", 2);
	
	final static
	private Functor TIMER_ATOM = new SymbolFunctor("timer", 1);
	
	final static
	private Functor KILLER_ATOM = new SymbolFunctor("killer", 0);
	
	final static
	private Functor CLICK_LISTENER_ATOM = new SymbolFunctor("clickListener", 0);
	
	final static
	private Functor KEY_CHAR_ATOM = new SymbolFunctor("keyChar", 0);
	
	final static
	private Functor KEY_CODE_ATOM = new SymbolFunctor("keyCode", 0);
	
	final static
	private Functor KEY_CHAR_ATOM_CREATED = new SymbolFunctor("keyChar", 1);
	
	final static
	private Functor KEY_CODE_ATOM_CREATED = new SymbolFunctor("keyCode", 1);
	
	
	private LMNtalPanel lmnPanel = null;
	private Atom keyAtom = null;
	private Functor keyFunctor = null;
	private LinkedList waitingAtomlist = new LinkedList();
	
	private String memID;
	private Membrane mymem;
	private String windowName;
	private int sizeX = 0;
	private int sizeY = 0;
	private Color bgcolor = Color.WHITE;
	private int posX = 0;
	private int posY = 0;
	private long timer = 1;
	private boolean killer = false;
	private long time;
	private boolean sizeUpdate = false;
	private boolean positionUpdate = false;
	private boolean getClicked = false;
	private Atom sizeAtom = null;
	private Dimension d;
	private boolean windowStateUpdate = false;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalWindow(Membrane mem){
		resetMembrane(mem);
		makeWindow();
		Iterator childMem = mem.memIterator();
		while(childMem.hasNext()){
			Membrane child = (Membrane)childMem.next();
			setChildMem(child);
		}
		mem.activate();
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
			
		// size atom
		atomIte= mem.atomIteratorOfFunctor(SIZE_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				if( sizeX != ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : sizeX) ||
					sizeY != ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : sizeY) ){
					sizeUpdate = true;
				}
				sizeX = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : 0);
				sizeY = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : 0);
			}
			catch(NumberFormatException e){}
		}
		
		// bgcolor
		atomIte= mem.atomIteratorOfFunctor(BGCOLOR_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				bgcolor = ((null != targetAtom) ?
						new Color(Integer.parseInt(targetAtom.nth(0)),
								  Integer.parseInt(targetAtom.nth(1)),
								  Integer.parseInt(targetAtom.nth(2)))
						: Color.WHITE);
			}
			catch(NumberFormatException e){}
		}
		
		// killer
		atomIte= mem.atomIteratorOfFunctor(KILLER_ATOM);
		if(atomIte.hasNext()){
			killer = true;
		}
		
		// position
		atomIte= mem.atomIteratorOfFunctor(POSITION_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				if( posX != ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : posX) ||
					posY != ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : posY) ){
						positionUpdate = true;
				}
				posX = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(0)) : 0);
				posY = ((null != targetAtom) ? Integer.parseInt(targetAtom.nth(1)) : 0);
			}
			catch(NumberFormatException e){}
		}

		// timer
		atomIte= mem.atomIteratorOfFunctor(TIMER_ATOM);
		if(atomIte.hasNext()){
			targetAtom = (Atom)atomIte.next();
			try{
				timer = ((null != targetAtom) ? Long.parseLong(targetAtom.nth(0)) : 0);
			}
			catch(NumberFormatException e){}
		}

		// get Clicked
		atomIte= mem.atomIteratorOfFunctor(CLICK_LISTENER_ATOM);
		if(atomIte.hasNext() && !getClicked){ getClicked = true; }
		else if(getClicked){ getClicked = false; }
		
		if(null == keyAtom){
			// keyChar
			atomIte= mem.atomIteratorOfFunctor(KEY_CHAR_ATOM);
			if(atomIte.hasNext()){
				Atom head = (Atom)atomIte.next();
				keyAtom = mem.newAtom(new SymbolFunctor(head.getName(), 1)); 
				Atom nil = mem.newAtom(new SymbolFunctor("[]", 1));
				mem.newLink(keyAtom, 0, nil, 0);
				mem.removeAtom(head);
				keyFunctor = KEY_CHAR_ATOM;
			}
			// keyCode
			atomIte= mem.atomIteratorOfFunctor(KEY_CODE_ATOM);
			if(atomIte.hasNext()){
				Atom head = (Atom)atomIte.next();
				keyAtom = mem.newAtom(new SymbolFunctor(head.getName(), 1));
				Atom nil = mem.newAtom(new SymbolFunctor("[]", 1));
				mem.newLink(keyAtom, 0, nil, 0);
				mem.removeAtom(head);
				keyFunctor = KEY_CODE_ATOM;
			}
		}
		else{
			// keyChar
			atomIte= mem.atomIteratorOfFunctor(KEY_CHAR_ATOM_CREATED);
			if(atomIte.hasNext()){
				keyAtom = (Atom)atomIte.next(); 
			}
			// keyCode
			atomIte= mem.atomIteratorOfFunctor(KEY_CODE_ATOM_CREATED);
			if(atomIte.hasNext()){
				keyAtom = (Atom)atomIte.next(); 
			}
		}
		
		doAddAtom(mem);
	}
	
	/**
	 * ウィンドウを生成する．
	 */
	public void makeWindow(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(keyFunctor == KEY_CHAR_ATOM){
			addKeyListener(new MyKeyAdapter(this, true));
		}
		else if(keyFunctor == KEY_CODE_ATOM){
			addKeyListener(new MyKeyAdapter(this, false));
		}
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
		
		lmnPanel = new LMNtalPanel(bgcolor);
		
		setTitle(windowName);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(lmnPanel), BorderLayout.CENTER);
		
		setSize(sizeX,sizeY);
		if(Env.getExtendedOption("screen").equals("max")) {
			setExtendedState(Frame.MAXIMIZED_BOTH | getExtendedState());
		}
		
		setLocation(posX, posY);

		setVisible(true);
	}
	
	/**
	 * ウィンドウの情報を再セットする．
	 * @param mem
	 */
	public void resetWindow(Membrane mem){
		resetMembrane(mem);
		// TODO: ウィンドウの設定に変更があった場合，更新．サイズの位置で更新を分けるべき？
		if(sizeUpdate){
			setSize(sizeX,sizeY);
			sizeUpdate = false;
		}
		if(positionUpdate){
			setLocation(posX, posY);
			positionUpdate = false;
		}
	}
	
	/**
	 * 管理する子孫膜を記憶する．
	 * @param mem
	 */
	public void setChildMem(Membrane mem){
		boolean res = lmnPanel.setChildMem(mem);
		// パネルクラスが作業中または最短更新時間に満てないときは待つ
		while(res && ((timer > System.currentTimeMillis() - time) || lmnPanel.isBusy())){}
		lmnPanel.setWindowID(memID);
		lmnPanel.paint(lmnPanel.getGraphics());
		// 最終更新時間のセット
		time = System.currentTimeMillis();
	}
	
	/**
	 * 管理する子孫膜を削除する．
	 * @param mem
	 */
	public void removeChildMem(Membrane mem){
		lmnPanel.removeChildMem(mem);
		// パネルクラスが作業中または最短更新時間に満てないときは待つ
		//while(timer > System.currentTimeMillis() - time || lmnPanel.isBusy()){}
		//lmnPanel.repaint();
		//// 最終更新時間のセット
		//time = System.currentTimeMillis();
	}
	
	/**
	 * ライブラリで前回描いたものを消さないモードに変更するためのメソッド．
	 * @param flag
	 */
	public void setRepaint(boolean flag){
		if(null != lmnPanel){
			lmnPanel.setRepaint(flag);
		}
	}
	
	/**
	 * キーアダプタから追加するアトムのファンクターを受け取り予約する
	 * @param functor
	 */
	public void setAddAtom(Functor functor){
		waitingAtomlist.add(functor);
	}
	
	/**
	 * 予約されているファンクターをアトムとして膜に追加する
	 * @param mem
	 */
	private void doAddAtom(Membrane mem){
		// ウィンドウの状態変更
//		d = getSize();
//		if(d.width != sizeX ||
//				d.height != sizeY){
//			// size atom
//			Iterator atomIte= mem.atomIteratorOfFunctor(SIZE_ATOM);
//			if(atomIte.hasNext()){
//				Atom sizeAtom = (Atom)atomIte.next();
//				Atom atomW = sizeAtom.getMem().newAtom(new IntegerFunctor(d.width));
//				Atom atomH = sizeAtom.getMem().newAtom(new IntegerFunctor(d.height));
//				sizeAtom.getMem().relink(atomW,0,sizeAtom,0);
//				sizeAtom.getMem().relink(atomH,0,sizeAtom,1);
//			}
//			sizeX = d.width;
//			sizeY = d.height;
//		}

		if(null != lmnPanel && getClicked && lmnPanel.hasClickedPoint()){
			Point point;// = lmnPanel.getClickedPoint();
			while(null != (point = lmnPanel.getClickedPoint())){
				Atom px = mem.newAtom(new IntegerFunctor(point.x));
				Atom py = mem.newAtom(new IntegerFunctor(point.y));
				Atom clickedAtom = mem.newAtom(new SymbolFunctor("clicked", 2));
				mem.newLink(px, 0, clickedAtom, 0);
				mem.newLink(py, 0, clickedAtom, 1);
	//			lmnPanel.clearClikedPoint();
//				mem.activate();
			}
//			String s = mem.toString();
//			System.out.println(s.replaceAll("\\{", "\n\\{").replaceAll("\\}", "\\}\n"));
//			Task.activatePerpetualMem(mem);
		}
		if(null != keyAtom){ 
			while(!waitingAtomlist.isEmpty()){
				Functor fa = (Functor)waitingAtomlist.removeFirst();
				Atom nth1 = keyAtom;
				Atom nth2 = null;
				int nth1_arg=0;
				
				while(true){
					try{
						nth2 = nth1.getArg(nth1_arg).getAtom();
					}catch(ArrayIndexOutOfBoundsException e){
						break;
					}
					if(nth2.getName().equals("[]")){
						Atom data = mem.newAtom(fa);
						Atom dot = mem.newAtom(new SymbolFunctor(".", 3));
						mem.newLink(dot, 0, data, 0);
						mem.newLink(nth1, nth1_arg, dot, 2);
						mem.newLink(nth2, 0, dot, 1);
						break;
					}
					nth1 = nth2;
					nth1_arg=1;
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
			String input =
			(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
			? String.valueOf(e.getKeyCode())
			: String.valueOf(e.getKeyChar());
			
			window.setAddAtom((new StringFunctor(input)));
		}else{
			int input = e.getKeyCode();
			window.setAddAtom((new IntegerFunctor(input)));
		}
	}
}
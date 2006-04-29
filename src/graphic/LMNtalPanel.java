package graphic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import runtime.AbstractMembrane;
import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;

public class LMNtalPanel extends JPanel implements Runnable {
	
	final
	private int SLEEP_TIME = 10;
	
	final
	private Functor DRAW_MEM = new Functor("draw",0); 
	
	final 
	private Functor GETPIC_ATOM = new Functor("getpic",1); 
	
	final 
	private Functor GETPIC2_ATOM = new Functor("getpic",2);
	
	final 
	private Functor STRING_ATOM = new Functor("string",3);
	
	final
	private String OVAL = "oval";
	
	final
	private String CIRCLE = "circle";
	
	final
	private String RECT = "rect";
	
	final
	private String TRIANGLE = "triangle";
	
	final
	private String FILL_OVAL = "filloval";
	
	final
	private String FILL_CIRCLE = "fillcircle";
	
	final
	private String FILL_RECT = "fillrect";
	
	final
	private String FILL_TRIANGLE = "filltriangle";
	
	final
	private String LINE = "line";
		
	private boolean repaintFlag = true;
	
	private boolean busy = false;
	
	// 膜管理マップ
	private Map memMap = Collections.synchronizedMap(new HashMap());
	private Map objMap = Collections.synchronizedMap(new HashMap());
	
	private Color bgcolor = Color.WHITE;
	private Image OSI = null;
	private Graphics OSG = null;
	private Thread th = null;
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラク
	public LMNtalPanel(Color color){
		super(true);
		bgcolor = color;
		
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				OSI = createImage((int) getSize().getWidth(),
						(int) getSize().getHeight());
				OSG = OSI.getGraphics();
				boolean flag = repaintFlag;
				repaintFlag = true;
				paint(OSG);
				repaintFlag = flag;
			}
			
		});
		start();
	}
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 管理する子孫膜を記憶する．
	 * @param mem
	 */
	public boolean setChildMem(Membrane mem){
		busy = true;
		GraphicObj gObj = null;
		
		synchronized (memMap) {
			if(mem.getAtomCountOfFunctor(DRAW_MEM)>0){
				gObj = setGraphicMem(mem);
			}
			// 登録済み
			if(memMap.containsKey(mem.getGlobalMemID())){
				if(null != gObj){ objMap.put(mem.getGlobalMemID(), gObj); }
			}
			// 未登録
			else{
				memMap.put(mem.getGlobalMemID(), mem.getParent().getGlobalMemID());
				if(null != gObj){ objMap.put(mem.getGlobalMemID(), gObj); }
			}
		}
		busy = false;
		return ((null != gObj) ? true : false);
	}
	
	/**
	 * ウィンドウ膜以外のグラフィック膜の登録を行う．
	 * 受け取る膜はウィンドウ膜以外の膜であることが保証されていること．
	 * @param mem
	 */
	private GraphicObj setGraphicMem(Membrane mem){
		// get getpic
		Iterator atomIte = mem.atomIteratorOfFunctor(GETPIC_ATOM);
		if(atomIte.hasNext()){
			Atom atomGetpic = (Atom)atomIte.next();
			String getpic = (atomGetpic).nth(0);
			// TODO: 対応した描画オブジェクトを返す
			if(OVAL == getpic){ return (new OvalObj(mem)); }
			else if(FILL_OVAL.compareToIgnoreCase(getpic) == 0){ return (new FillOvalObj(mem)); }
			else if(CIRCLE.compareToIgnoreCase(getpic) == 0){ return (new OvalObj(mem)); }
			else if(FILL_CIRCLE.compareToIgnoreCase(getpic) == 0){ return (new FillOvalObj(mem)); }
			else if(RECT.compareToIgnoreCase(getpic) == 0){ return (new RectObj(mem)); }
			else if(FILL_RECT.compareToIgnoreCase(getpic) == 0){ return (new FillRectObj(mem)); }
			else if(LINE.compareToIgnoreCase(getpic) == 0){ return (new LineObj(mem)); }
			else if(TRIANGLE.compareToIgnoreCase(getpic) == 0){ return (new TriangleObj(mem)); }
			else if(FILL_TRIANGLE.compareToIgnoreCase(getpic) == 0){ return (new FillTriangleObj(mem)); }
			else if(atomGetpic.nthAtom(0).getFunctor().equals(STRING_ATOM)){ return (new StringObj(mem)); }
		}
		return null;
	}
	
	/**
	 * 管理する子孫膜を削除する．
	 * @param mem
	 */
	public boolean removeChildMem(AbstractMembrane mem){
		busy = true;
		Object gObj = null;
		synchronized (memMap) {
			memMap.remove(mem.getGlobalMemID());
			gObj = objMap.remove(mem.getGlobalMemID());
		}
		busy = false;
		return ((null != gObj) ? true : false);
	}
	
	/**
	 * 描画を行う
	 * @param g
	 */
	public void paint(Graphics g) {
		busy = true;
		synchronized (memMap) {
//			try{
				//画面を白地で初期化（塗りつぶす）
				if(repaintFlag){
					OSG.setColor(bgcolor);
					OSG.fillRect(0,
							0,
							(int)getSize().getWidth(),
							(int)getSize().getHeight());
				}
				
				// 描画オブジェクトを描画
				Iterator ite = objMap.values().iterator();
				while(ite.hasNext()){
					GraphicObj gObj = (GraphicObj)ite.next();
					gObj.drawAtom(OSG);
				}
				g.drawImage(OSI,0,0,this);
//			}catch(NullPointerException e){System.out.println("null!");}
		}
		busy = false;
	}
	
	public void setRepaint(boolean flag){
		repaintFlag = flag;
	}
	
	public boolean isBusy(){
		return busy;
	}
	
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
	}
	
	public void stop() {
		th = null;
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}
}
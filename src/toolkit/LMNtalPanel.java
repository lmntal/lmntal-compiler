package toolkit;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.sun.org.apache.xalan.internal.xslt.Process;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

public class LMNtalPanel extends JPanel implements Runnable {
	
	final
	private int SLEEP_TIME = 10; // 10秒ごとにrepaintされる。
	
	final
	private Functor DRAW_MEM = new SymbolFunctor("draw",0); 
	
	final 
	private Functor GETPIC_ATOM = new SymbolFunctor("getpic",1); 
	
	final 
	private Functor STRING_ATOM = new SymbolFunctor("string",3);
	
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
	
	private Color bgcolor;
	private Image OSI = null;
	private Graphics OSG = null;
	private Thread th = null;
	private String windowID = null;	
	private LinkedList clickedPoint = new LinkedList();
	
	///////////////////////////////////////////////////////////////////////////
	// コンストラクタ
	public LMNtalPanel(Membrane mem){
		super(true);
		windowID = LMNtalWindow.getID(mem);
		bgcolor = Color.WHITE;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				setClickedPoint(e.getPoint());
			}
		});
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
			else{ gObj = new RelativeObj(mem); }
			String key = LMNtalWindow.getID(mem);
			String parentID = LMNtalWindow.getID(mem.getParent());
			memMap.put(key, parentID);
			objMap.put(key, gObj);
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
			else if((new File(getpic)).exists()){ return (new FileObj(mem, getpic));}
		}
		return (new RelativeObj(mem));
	}
	
	/**
	 * 管理する子孫膜を削除する．
	 * @param mem
	 */
	public boolean removeChildMem(Membrane mem){
		busy = true;
		Object gObj = null;
		synchronized (memMap) {
			String id = LMNtalWindow.getID(mem);
			if(id != null){
				memMap.remove(id);
				gObj = objMap.remove(id);
			}
		}
		busy = false;
		return ((null != gObj) ? true : false);
	}
	
	/**
	 * 描画を行う
	 * @param g
	 */
	public void paint(Graphics g) {
		if(null == g){ return; }
		if(null == OSG){ return; }
		busy = true;
		synchronized (memMap) {
			//画面を白地で初期化（塗りつぶす）
			if(repaintFlag){
				OSG.setColor(bgcolor);
				OSG.fillRect(0,
						0,
						(int)getSize().getWidth(),
						(int)getSize().getHeight());
			}
			// 描画オブジェクトを描画
			drawChildren(windowID, new Point());
			g.drawImage(OSI,0,0,this);
		}
		busy = false;
	}
	
	/**
	 * 子膜を描画する
	 * @param id - 親膜のIDがidと等しいものを描画する
	 */
	private void drawChildren(String id, Point delta){
		Iterator ite = memMap.keySet().iterator();
		ArrayList list = new ArrayList();
		
		// 値がidと等しいkey（膜のid）をリストに格納
		while(ite.hasNext()){
			String target = (String)ite.next();
			if(id.equals(((String)memMap.get(target)))){
				list.add(target);
				GraphicObj gObj = (GraphicObj)objMap.get(target);
				if(null != gObj){
					while(null == OSG){ }
					gObj.drawAtom(OSG, delta);
				}
			}
		}
		
		// リストに格納されたIDを元に子膜の描画
		ite = list.iterator();
		while(ite.hasNext()){
			String target = (String)ite.next();
			Point parentPoint = ((GraphicObj)objMap.get(target)).getPosition();
			drawChildren(target, (new Point(delta.x + parentPoint.x, delta.y + parentPoint.y)));
		}
	}
	
	public void setRepaint(boolean flag){
		repaintFlag = flag;
	}
	
	private void setClickedPoint(Point p){
		clickedPoint.add(p);
	}
	
	public Point getClickedPoint(){
		if(!clickedPoint.isEmpty()){
			return (Point)clickedPoint.remove();
		}
		return null;
	}
	
	public boolean hasClickedPoint(){
		if(clickedPoint.isEmpty()){ return false; }
		return true;
	}
	
	public void clearClikedPoint(){
		clickedPoint = null;
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
				repaint();
			} catch (InterruptedException e) {
			}
		}
	}
	
	
}
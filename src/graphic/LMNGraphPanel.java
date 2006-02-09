package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import runtime.AbstractMembrane;
import runtime.Functor;
import test.GUI.Node;
/**
 * 
 * @author nakano
 * LMNGraphPanel で背景を塗りつぶす
 */
public class LMNGraphPanel extends JPanel implements Runnable {
	LMNtalWindow frame;
	public Thread th = null;
	/**ロックされている描画膜があるかどうか*/
	public boolean locked = false;
	private Image OSI = null;
	private Graphics OSG = null;
//	private boolean ready=false; 
	private final Functor NAME_ATOM = new Functor("name",1); 
	/**描画するオブジェクトリスト*/
	private LinkedList drawlist = new LinkedList();
	/**レラティブ膜のリスト*/
	private HashMap relativemap = new HashMap();
	
	public LMNGraphPanel(LMNtalWindow f) {
//		super();
		frame = f;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				OSI = createImage((int) getSize().getWidth(), (int) getSize().getHeight());
				OSG = OSI.getGraphics();
			}

		});
		start();
	}
	
	
	public void paint(Graphics g) {
		try{
			//画面を白地で初期化（塗りつぶす）
			OSG.setColor(frame.getColor());
			OSG.fillRect(0,0,(int) getSize().getWidth(), (int) getSize().getHeight());
			paintLayout();
			g.drawImage(OSI,0,0,this);
		}catch(NullPointerException e){repaint();}
	}
	/**
	 * 指定された名称のアトムが存在するか検索。
	 * あれば真を、なければ偽を返す。
	 */
	private String searchAtom(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

		while(ite.hasNext()){
			a = (Node)ite.next();
			if(a.getName() == "draw"){
				return "draw";
			}else if(a.getName() == "relative"){
				return "relative";
			}else if(a.getName() == "remove"){
				return "remove";
			}
		}
		return null;
				
	}
	
	/**レラティブ膜情報の取得*/
	private Relativemem getRelativeMem(AbstractMembrane m){

		Node a;
		Relativemem rm = new Relativemem();
		
		synchronized(this){
			Iterator ite = m.atomIterator();
	
			if(m.getLockThread() != null) locked = true;
			while(ite.hasNext()){
				a = (Node)ite.next();
				rm.name=m.getGlobalMemID();
				/**位置情報の取得*/
				if(a.getName()=="position"){
					if(a.getEdgeCount() != 2)continue;
					try{
						rm.setloc(Integer.parseInt(a.getNthNode(0).getName()),Integer.parseInt(a.getNthNode(1).getName()));
					}catch(NumberFormatException error){			
					}
				}
				/**名前の取得*/
//				else if(a.getName()=="name"){
//					if(a.getEdgeCount() != 1)continue;
//					rm.name=a.getNthNode(0).getName().toString();
//				}
				/**回転角度の取得*/
				else if(a.getName()=="angle"){
					if(a.getEdgeCount() != 1)continue;
					rm.setangle(Integer.parseInt(a.getNthNode(0).getName().toString()));
				}
	//			/**名前の取得*/
	//			else if(a.getName()=="name"){
	//				ga.setname(a.getNthNode(0).getName());
	//			}
			}
		}
		return rm;
		
	}
	/**
	 * 描画用のアトム郡の取得
	 */
	private GraphicObj getGraphicAtoms(AbstractMembrane m){
		GraphicObj ga = new GraphicObj(m);
		if(ga.isset())
			return ga;
		else
			return null;
	}
	
	/**nameアトムがあればそれに繋がったアトム名を取得。なければnullを返す。*/
	public String getName(AbstractMembrane m){
//		Iterator ite = m.atomIterator();
//		Iterator ite = m.atomIteratorOfFunctor(NAME_ATOM);
//		Node a;
//		
//		while(ite.hasNext()){
//			a = (Node)ite.next();
//			/**描画するファイルの取得*/
//			if(a.getName()=="name"){
//				if(a.getEdgeCount() != 1)return null;
//				return a.getNthNode(0).getName();
//			}
//		}
//		return null;
		return m.getGlobalMemID();
	}
	
	/**再帰的にレラティブ膜を探す。レラティブ膜自身のunlock時はこちらを呼ぶ。*/
	private String searchRelativeMem_self(AbstractMembrane relativetmp , int distance){

		while(distance >= 0){
			distance--;
			/*レラティブ膜発見*/
			if(searchAtom(relativetmp)=="relative"){
				Relativemem remem = getRelativeMem(relativetmp);
				
				/*レラティブ膜の登録*/
				relativemap.put(remem.name,remem);
				if(distance >= 0){
					remem.parentremem = searchRelativeMem(relativetmp.getParent(),distance);
				}
				return remem.name;
			}
		}
		return null;
	}
	/**再帰的にレラティブ膜を探す*/
	private String searchRelativeMem(AbstractMembrane relativetmp , int distance){

		while(distance >= 0){
			distance--;
			/*レラティブ膜発見*/
			if(searchAtom(relativetmp)=="relative"){
				Relativemem remem = getRelativeMem(relativetmp);
				/*すでに登録済みなら名前だけ返して終了*/
				if(relativemap.containsKey(remem.name)){
					return remem.name;
				}
				/*未登録ならば、再帰的にレラティブ膜の探索*/
				/*レラティブ膜の登録*/
				relativemap.put(remem.name,remem);
				if(distance >= 0){
					remem.parentremem = searchRelativeMem(relativetmp.getParent(),distance);
				}
				return remem.name;
			}
		}
		return null;
	}
	
	/**
	 * 実際の描画処理
	 *
	 */

	public synchronized void setGraphicMem(AbstractMembrane m, int distance){
		GraphicObj ga;
		String mode = searchAtom(m);
		if(mode == "draw"){
			ga = getGraphicAtoms(m);
			if(ga == null) return;
			
			/**レラティブ膜のチェック*/
			if(distance > 0){
				ga.remem=searchRelativeMem(m.getParent(), distance - 1);
			}
			/**同一atomがなければ、リストに追加（表示優先順位考慮）*/
			for(int i = 0; i < drawlist.size(); i++){
				GraphicObj ga2 = (GraphicObj)drawlist.get(i);
				if(ga2==null) continue;
				if(ga.name.equals(ga2.name)){
					drawlist.set(i, ga);
					break;
				}else if(ga.sequence < ga2.sequence){
					drawlist.add(i, ga);
					break;
				}else if(i == drawlist.size() - 1){
					drawlist.addLast(ga);
					break;					
				}
			}
			if(drawlist.size() == 0){
				drawlist.add(ga);
			}
		}else if(mode == "remove"){
			ga = getGraphicAtoms(m);
			if(ga == null) return;
			System.out.println("find relative");
			/**同一atomがなければ、リストに追加（表示優先順位考慮）*/
			for(int i = 0; i < drawlist.size(); i++){
				GraphicObj ga2 = (GraphicObj)drawlist.get(i);
				if(ga2==null) continue;
				if(ga.name.equals(ga2.name)){
					drawlist.remove(i);
					break;
				}
			}			
		}
		else
			searchRelativeMem_self(m, distance);
	}
	public synchronized void removeGraphicMem(AbstractMembrane m){
		String name = getName(m);
		for(int i = 0; i < drawlist.size(); i++){
			GraphicObj ga2 = (GraphicObj)drawlist.get(i);
			if(ga2==null) continue;
			if(ga2.name.equals(name)){
				drawlist.remove(i);
				break;
			}
		}
	}
	private synchronized void paintLayout(){
		Iterator ite = drawlist.iterator();
		while(ite.hasNext()){
			GraphicObj ga = (GraphicObj)ite.next();
			ga.drawAtom(OSG, relativemap);
		}
		
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
	public void stop() {
		th = null;
	}
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				Thread.sleep(10);
//				this.wait();
			} catch (InterruptedException e) {
			}
//			repaint();
		}
	}

}

class Relativemem{
	private int x = 0, y = 0;
	public String name;
	public String parentremem = null;
	private double angle = 0.0;
	
	public double getangle(HashMap m){
		if(parentremem==null || !m.containsKey(parentremem))
			return Math.PI / 180 * angle;

		Relativemem remem = (Relativemem)m.get(parentremem);
		return (Math.PI / 180 * angle) + remem.getangle(m);
		
	}
	public void setloc(int a, int b){
		x=a;
		y=b;
	}
	public void setangle(int a){
		angle = a;
	}
	
	public int getx(HashMap m){
		if(parentremem==null || !m.containsKey(parentremem))
			return x;
		Relativemem remem = (Relativemem)m.get(parentremem);
		double resx = new Integer(x).doubleValue();
		double resy = new Integer(y).doubleValue();
		int res = new Double(resx * Math.cos(remem.getangle(m)) - resy * Math.sin(remem.getangle(m))).intValue();
		return remem.getx(m) + res;
	}
	
	public int gety(HashMap m){
		if(parentremem==null || !m.containsKey(parentremem))
			return y;
		Relativemem remem = (Relativemem)m.get(parentremem);
		double resx = new Integer(x).doubleValue();
		double resy = new Integer(y).doubleValue();
		int res = new Double(resx * Math.sin(remem.getangle(m)) + resy * Math.cos(remem.getangle(m))).intValue();
		return remem.gety(m) + res;
	}
	
}
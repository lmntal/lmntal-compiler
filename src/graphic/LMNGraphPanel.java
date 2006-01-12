package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import runtime.AbstractMembrane;
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
	private boolean ready=false;
	/**描画するオブジェクトリスト*/
	LinkedList drawlist = new LinkedList();
	/**レラティブ膜のリスト*/
	HashMap relativemap = new HashMap();
	
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
			OSG.setColor(new Color(frame.color_r,frame.color_g,frame.color_r));
			OSG.fillRect(0,0,(int) getSize().getWidth(), (int) getSize().getHeight());
			paintlayout();
			g.drawImage(OSI,0,0,this);
		}catch(NullPointerException e){repaint();}
	}
	/**
	 * 指定された名称のアトムが存在するか検索。
	 * あれば真を、なければ偽を返す。
	 */
	private String searchatom(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

		while(ite.hasNext()){
			a = (Node)ite.next();
			if(a.getName() == "draw"){
				return "draw";
			}else if(a.getName() == "graphic"){
				return "graphic";
			}else if(a.getName() == "remove"){
				return "remove";
			}
		}
		return null;
				
	}
	
	/**レラティブ膜情報の取得*/
	private Relativemem getrelativemem(AbstractMembrane m){

		Node a;
		Relativemem rm = new Relativemem();
		
		synchronized(frame.lmnframe.lock2){
			Iterator ite = m.atomIterator();
	
			if(m.getLockThread() != null) locked = true;
			while(ite.hasNext()){
				a = (Node)ite.next();
				/**位置情報の取得*/
				if(a.getName()=="position"){
					if(a.getEdgeCount() != 2)continue;
					try{
						rm.setloc(Integer.parseInt(a.getNthNode(0).getName()),Integer.parseInt(a.getNthNode(1).getName()));
					}catch(NumberFormatException error){			
					}
				}
				/**名前の取得*/
				else if(a.getName()=="name"){
					if(a.getEdgeCount() != 1)continue;
					rm.name=a.getNthNode(0).getName().toString();
				}
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
	private GraphicAtoms getgraphicatoms(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;
		GraphicAtoms ga = new GraphicAtoms();

		if(m.getLockThread() != null) locked = true;
		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName()=="getpic"){
				if(a.getEdgeCount() != 1)continue;
				String picName = a.getNthNode(0).getName();
				if(picName.equals("string")){
					if(a.getNthNode(0).getEdgeCount()==2){
//						System.out.println("Nth0"+a.getNthNode(0).getNthNode(0).getName());
//						System.out.println("Nth1"+a.getNthNode(0).getNthNode(1).getName());
						ga.setString(a.getNthNode(0).getNthNode(0).getName());
					}else if(a.getNthNode(0).getEdgeCount()==3){
//						System.out.println("Nth0"+a.getNthNode(0).getNthNode(0).getName());
//						System.out.println("Nth1"+a.getNthNode(0).getNthNode(1).getName());
//						System.out.println("Nth2"+a.getNthNode(0).getNthNode(2).getName());
						ga.setString(a.getNthNode(0).getNthNode(0).getName(),a.getNthNode(0).getNthNode(1).getName());
					}
				}
				ga.SetPic( picName );
			}
			/**名前の取得*/
			else if(a.getName()=="name"){
				if(a.getEdgeCount() != 1)continue;
				ga.setname(a.getNthNode(0).getName());
			}
			/**描画の順番（前後関係）の取得*/
			else if(a.getName()=="sequence"){
				try{
					ga.sequence = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){					
				}
				
			}
			/**描画の順番（前後関係）の取得*/
			else if(a.getName()=="color"){
				if(a.getEdgeCount() != 3)continue;
				try{
					ga.setcolor( Integer.parseInt(a.getNthNode(0).getName()),Integer.parseInt(a.getNthNode(1).getName()),Integer.parseInt(a.getNthNode(2).getName()));
				}catch(NumberFormatException error){			
				}
				
			}
			/**描画する位置の取得*/
			else if(a.getName()=="position"){
				if(a.getEdgeCount() ==2){
					try{
						ga.setarraypos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								0,0,0,0,0,0
								);
					}catch(NumberFormatException error){
						return null;
					}
				}else if(a.getEdgeCount() ==4){
					try{
						ga.setarraypos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								0,0,0,0
								);
					}catch(NumberFormatException error){
						return null;
					}
				}else if(a.getEdgeCount() ==6){
					try{
						ga.setarraypos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								Integer.parseInt(a.getNthNode(4).getName()),
								Integer.parseInt(a.getNthNode(5).getName()),
								0,0
								);
					}catch(NumberFormatException error){
						return null;
					}
				}else if(a.getEdgeCount() ==8){
					try{
						ga.setarraypos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								Integer.parseInt(a.getNthNode(4).getName()),
								Integer.parseInt(a.getNthNode(5).getName()),
								Integer.parseInt(a.getNthNode(6).getName()),
								Integer.parseInt(a.getNthNode(7).getName())
								);
					}catch(NumberFormatException error){
						return null;
					}
				}else continue;
				
			}
			/**描画するサイズの取得*/
			else if(a.getName()=="size"){
				if(a.getEdgeCount() != 2)continue;
				try{
					ga.sizex = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){
					return null;
				}

				try{
					ga.sizey = Integer.parseInt(a.getNthNode(1).getName());
				}catch(NumberFormatException error){
					return null;
					
				}
				
			}
			/**描画するかどうかの取得*/
			else if(a.getName()=="enable"){
				ga.enable = true;				
			}
		}
		if(!ga.isset())
			return null;
		return ga;
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
	
	/**再帰的にレラティブ膜を探す。レラティブ膜自身のunlock時はこちらを呼ぶ。*/
	private String searchrelativemem_self(AbstractMembrane relativetmp , int distance){

		while(distance >= 0){
			distance--;
			/*レラティブ膜発見*/
			if(searchatom(relativetmp)=="graphic"){
				Relativemem remem = getrelativemem(relativetmp);
				
				/*レラティブ膜の登録*/
				relativemap.put(remem.name,remem);
				if(distance >= 0){
					remem.parentremem = searchrelativemem(relativetmp.getParent(),distance);
				}
				return remem.name;
			}
		}
		return null;
	}
	/**再帰的にレラティブ膜を探す*/
	private String searchrelativemem(AbstractMembrane relativetmp , int distance){

		while(distance >= 0){
			distance--;
			/*レラティブ膜発見*/
			if(searchatom(relativetmp)=="graphic"){
				Relativemem remem = getrelativemem(relativetmp);
				/*すでに登録済みなら名前だけ返して終了*/
				if(relativemap.containsKey(remem.name)){
					return remem.name;
				}
				/*未登録ならば、再帰的にレラティブ膜の探索*/
				/*レラティブ膜の登録*/
				relativemap.put(remem.name,remem);
				if(distance >= 0){
					remem.parentremem = searchrelativemem(relativetmp.getParent(),distance);
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

	public synchronized void setgraphicmem(AbstractMembrane m, int distance){
		GraphicAtoms ga;
		String mode = searchatom(m);
		if(mode == "draw"){
			ga = getgraphicatoms(m);
			if(ga == null) return;
			
			/**レラティブ膜のチェック*/
			if(distance > 0){
				ga.remem=searchrelativemem(m.getParent(), distance - 1);
			}
			/**同一atomがなければ、リストに追加（表示優先順位考慮）*/
			for(int i = 0; i < drawlist.size(); i++){
				GraphicAtoms ga2 = (GraphicAtoms)drawlist.get(i);
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
			ga = getgraphicatoms(m);
			if(ga == null) return;
			/**同一atomがなければ、リストに追加（表示優先順位考慮）*/
			for(int i = 0; i < drawlist.size(); i++){
				GraphicAtoms ga2 = (GraphicAtoms)drawlist.get(i);
				if(ga2==null) continue;
				if(ga.name.equals(ga2.name)){
					drawlist.remove(i);
					break;
				}
			}			
		}
		else
			searchrelativemem_self(m, distance);
	}
	public synchronized void removegraphicmem(AbstractMembrane m){
		String name = getname(m);
		for(int i = 0; i < drawlist.size(); i++){
			GraphicAtoms ga2 = (GraphicAtoms)drawlist.get(i);
			if(ga2==null) continue;
			if(ga2.name.equals(name)){
				drawlist.remove(i);
				break;
			}
		}
	}
	private synchronized void paintlayout(){
		Iterator ite = drawlist.iterator();
		while(ite.hasNext()){
			GraphicAtoms ga = (GraphicAtoms)ite.next();
			ga.drawatom(OSG, relativemap);
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
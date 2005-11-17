package graphic;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

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
	LMNtalGFrame frame;
	public Thread th = null;
	/**ロックされている描画膜があるかどうか*/
	public boolean locked = false;
	private Image OSI = null;
	private Graphics OSG = null;
	runtime.Membrane rootMem;
	/**描画するオブジェクトリスト*/
	LinkedList drawlist = new LinkedList();
	
	public LMNGraphPanel(LMNtalGFrame f) {
		super();
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

		//画面を白地で初期化（塗りつぶす）
		OSG.setColor(Color.WHITE);
		OSG.fillRect(0,0,(int) getSize().getWidth(), (int) getSize().getHeight());
		getlayout();
		paintlayout();
		g.drawImage(OSI,0,0,this);
	}
	/**
	 * 指定された名称のアトムが存在するか検索。
	 * あれば真を、なければ偽を返す。
	 */
	private boolean searchatom(AbstractMembrane m , String sstring){
		Iterator ite = m.atomIterator();
		Node a;
//		LinkedList atoms = new LinkedList();
//		
//		while(ite.hasNext()){
//			atoms.add(ite.next());
//		}
//		
//		while(atoms.size() > 0){
//			a = (Node)atoms.removeFirst();
//			if(a.getName() == sstring){
//				return true;
//			}
//		}
		while(ite.hasNext()){
			a = (Node)ite.next();
			if(a.getName() == sstring){
				return true;
			}
		}
		return false;
				
	}
	
	/**
	 * 描画用のアトム郡の取得
	 */
	private GraphicAtoms getgraphicatoms(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;
		LinkedList atoms = new LinkedList();
		GraphicAtoms ga = new GraphicAtoms();
		
//		while(ite.hasNext()){
//			atoms.add(ite.next());
//		}
//		
//		while(atoms.size() > 0){
//			a = (Node)atoms.removeFirst();
		if(m.getLockThread() != null) locked = true;
		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName()=="getpic"){
				if(a.getEdgeCount() != 1)continue;
				ga.SetPic( a.getNthNode(0).getName() );
//				System.out.print(ga.filename + "\n");
			}
			/**描画の順番（前後関係）の取得*/
			else if(a.getName()=="name"){
				ga.name = a.getNthNode(0).getName();
			}else if(a.getName()=="sequence"){
				try{
					ga.sequence = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){
					try{
						ga.sequence = Integer.parseInt(a.getNthNode(0).getNthNode(0).getName());
					}catch(NumberFormatException e){
						
					}						
				}
				
			}
			/**描画する位置の取得*/
			else if(a.getName()=="position"){
				if(a.getEdgeCount() != 2)continue;
				try{
					ga.posx = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){
//					try{
//						ga.posx = Integer.parseInt(a.getNthNode(0).getNthNode(0).getName());
//					}catch(NumberFormatException e){
//						
//					}	
					return null;
				}

				try{
					ga.posy = Integer.parseInt(a.getNthNode(1).getName());
				}catch(NumberFormatException error){
//					try{
//						ga.posy = Integer.parseInt(a.getNthNode(1).getNthNode(0).getName());
//					}catch(NumberFormatException e){
//						
//					}	
					return null;
					
				}
				
			}
			/**描画するかどうかの取得*/
			else if(a.getName()=="enable"){
				ga.enable = true;
//				System.out.print("find_enable\n");
				
			}
		}
		return ga;
	}
	
	
	/**
	 * 実際の描画処理
	 *
	 */
	private void getlayout(){
		if(rootMem == null) return;
		
		
		Iterator ite = rootMem.memIterator();
//		LinkedList mems = new LinkedList();
		AbstractMembrane m;
		
		GraphicAtoms ga;
		
		searchatom(rootMem , "draw");
		
		locked = false;
		/*すべての膜に対して行う*/
		while(ite.hasNext()){
			m = (AbstractMembrane)ite.next();
			if(searchatom(m , "draw")){
				ga = getgraphicatoms(m);
				if(ga == null) continue;
				/**同一atomがなければ、リストに追加（表示優先順位考慮）*/
				for(int i = 0; i < drawlist.size(); i++){
					GraphicAtoms ga2 = (GraphicAtoms)drawlist.get(i);

					if(ga.name.equals(ga2.name)){
						drawlist.set(i, ga);
						break;
					}
					if(ga.sequence < ga2.sequence){
						drawlist.add(i, ga);
						break;
					}
				}
				if(drawlist.size() == 0)
					drawlist.add(ga);
			}
		}
	}

	private void paintlayout(){
		Iterator ite = drawlist.iterator();
		while(ite.hasNext()){
			GraphicAtoms ga = (GraphicAtoms)ite.next();
			ga.drawatom(OSG);
		}
		
	}

	public void setRootMem(runtime.Membrane rootMem) {
		this.rootMem = rootMem;
		
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
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}

}
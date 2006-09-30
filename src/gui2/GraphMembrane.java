package gui2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import runtime.Atom;
import runtime.Dumper;
import runtime.Membrane;

public class GraphMembrane {
	
	
	/////////////////////////////////////////////////////////////////
	//	定数
	
	final static
	private double EDGE_LENGTH = 150.0;
	
	final static
	private double EDGE_LENGTH_OVER_MEM = 450.0;
	
	final static
	private int ROUND = 40;
	
	final static
	private Color MEM_COLOR = new Color(102, 153, 255);
	
	/////////////////////////////////////////////////////////////////

	private boolean root = false; 
	private Membrane myMem;
	private GraphMembrane myParent;
	private int posX1;
	private int posY1;
	private int posX2;
	private int posY2;
	private boolean viewInside = false;
	private GraphAtom dummyGraphAtom = new GraphAtom(null);
	
	
	private Map<Atom, GraphAtom> atomMapTemp =
		Collections.synchronizedMap(new HashMap<Atom, GraphAtom>());
	
	/** 膜内のアトムを保持する（Atom, GraphAtom） */
	private Map<Atom, GraphAtom> atomMap =
		Collections.synchronizedMap(new HashMap<Atom, GraphAtom>());
	
	private Map<Membrane, GraphMembrane> memMapTemp =
		Collections.synchronizedMap(new HashMap<Membrane, GraphMembrane>());
	
	/** 膜内の子膜を保持する（Membrane, GraphMembrane） */
	private Map<Membrane, GraphMembrane> memMap =
		Collections.synchronizedMap(new HashMap<Membrane, GraphMembrane>());
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public GraphMembrane(GraphMembrane parent, Membrane mem) {
		myParent = parent;
		resetMembrane(mem);
	}
	
	public GraphMembrane(Membrane mem, boolean rootFlag) {
		root = rootFlag;
		myParent = null;
		resetMembrane(mem);
	}
	
	/////////////////////////////////////////////////////////////////
	
	/**
	 * 膜の中身を再設定する
	 * @param mem 再設定対象の膜
	 */
	public void resetMembrane(Membrane mem){
		synchronized (atomMap) {
			myMem = mem;
			/////////////////////////////////////////////////////////////
			// アトムの増減処理
			atomMapTemp.clear();
			Iterator atoms = mem.atomIterator();
			posX1 = posY1 = 1000;
			posX2 = posY2 = 0;
			
			if(!getViewInside()){
				atomMap.clear();
				atomMap.put(null, dummyGraphAtom);
				if(posX1 > dummyGraphAtom.getPosX()){ posX1 = dummyGraphAtom.getPosX(); }
				if(posY1 > dummyGraphAtom.getPosY()){ posY1 = dummyGraphAtom.getPosY(); }
				if(posX2 < dummyGraphAtom.getPosX()){ posX2 = dummyGraphAtom.getPosX(); }
				if(posY2 < dummyGraphAtom.getPosY()){ posY2 = dummyGraphAtom.getPosY(); }
			}
			while(getViewInside() && atoms.hasNext()){
				Atom atom = (Atom)atoms.next();
				
				// Proxyアトムは無視
//				if(atom.getFunctor().isInsideProxy() || atom.getFunctor().isOutsideProxy()){
//					continue;
//				}
				
				GraphAtom targetAtom = null;
				if(atomMap.containsKey(atom)){
					targetAtom = atomMap.get(atom);
					atomMapTemp.put(atom, targetAtom);
				}
				else{
					targetAtom = new GraphAtom(atom);
					atomMapTemp.put(atom, targetAtom);
				}
				if(targetAtom != null){
					// ProxyAtomからはリンクを描画しない
					if(targetAtom.me.getFunctor().isInsideProxy() ||
							targetAtom.me.getFunctor().isOutsideProxy())
					{
						continue;
					}
					if(posX1 > targetAtom.getPosX()){ posX1 = targetAtom.getPosX(); }
					if(posY1 > targetAtom.getPosY()){ posY1 = targetAtom.getPosY(); }
					if(posX2 < targetAtom.getPosX()){ posX2 = targetAtom.getPosX(); }
					if(posY2 < targetAtom.getPosY()){ posY2 = targetAtom.getPosY(); }
				}
			}
			atomMap.clear();
			atomMap.putAll(atomMapTemp);
			
			/////////////////////////////////////////////////////////////
			// 膜の増減処理
			memMapTemp.clear();
			Iterator<Membrane> mems = mem.memIterator();
			while(mems.hasNext()){
				Membrane targetMem = mems.next();
				GraphMembrane targetGraphMem = null;
				if(memMap.containsKey(targetMem)){
					targetGraphMem = memMap.get(targetMem);
					memMapTemp.put(targetMem, targetGraphMem);
					targetGraphMem.resetMembrane(targetMem);
				}
				else{
					targetGraphMem = new GraphMembrane(this, targetMem);
					memMapTemp.put(targetMem, targetGraphMem);
				}
			}
			memMap.clear();
			memMap.putAll(memMapTemp);
			SubFrame.resetList(memMap);
			
			/////////////////////////////////////////////////////////////
			// アトムの位置調節
			relaxEdge();
			relaxAngle();
			moveCalc();
		}
		
	}
	
	/**
	 * 膜の左上のX座標を取得する。
	 * @return
	 */
	public int getPosX1(){ return posX1; }
	
	/**
	 * 膜の右下のX座標を取得する。
	 * @return
	 */
	public int getPosX2(){ return posX2; }
	
	/**
	 * 膜の左上のY座標を取得する。
	 * @return
	 */
	public int getPosY1(){ return posY1; }
	
	/**
	 * 膜の右下のY座標を取得する。
	 * @return
	 */
	public int getPosY2(){ return posY2; }
	
	/**
	 * 膜に含まれるアトムの位置を実際に変更する。
	 *
	 */
	private void moveCalc(){
		Iterator<GraphAtom> graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			graphAtoms.next().moveCalc();
		}
		
	}
	
	/**
	 * 指定された膜に対応するGraphMembraneを取得する。
	 * @param targetMem
	 * @return
	 */
	public GraphMembrane findGraphMem(Membrane targetMem){
		if(memMap.containsKey(targetMem)){
			return memMap.get(targetMem);
		}
		Iterator<GraphMembrane> graphMems = memMap.values().iterator();
		while(graphMems.hasNext()){
			GraphMembrane graphMem = graphMems.next().findGraphMem(targetMem);
			if(graphMem != null){ return graphMem; }
			
		}
		return null;
	}
	
	/**
	 * 角度を調節する。
	 * ただし、実際の移動はmoveCalcが呼ばれるまで行われない。
	 *
	 */
	private void relaxAngle(){
		GraphAtom targetAtom;
		Iterator<GraphAtom> graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			targetAtom = graphAtoms.next();

			// ProxyAtomは無視
			if(targetAtom.me.getFunctor().isInsideProxy() ||
					targetAtom.me.getFunctor().isOutsideProxy())
			{
				continue;
			}
			
			int edgeNum = targetAtom.me.getEdgeCount(); 
			
			if(edgeNum < 2){ continue; }

			Map<Double, GraphAtom> treeMap = new TreeMap<Double, GraphAtom>();
			
			// つながっているアトムを走査
			for(int i = 0; i < edgeNum; i++){
				GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
				
				nthAtom = getRealNthAtom(nthAtom);
				
				if(nthAtom == null){ continue; }
				
				if(null != nthAtom){
					double dx = nthAtom.getPosX() - targetAtom.getPosX();
					double dy = nthAtom.getPosY() - targetAtom.getPosY();
					if(dx == 0.0){ dx=0.000000001; }
					double angle = Math.atan(dy / dx);
					angle = regulate(angle);
					if(dx < 0.0) angle += Math.PI;
					treeMap.put(angle, nthAtom);
				}
			}
		
			Object[] nthAngles = treeMap.keySet().toArray();
			for(int i = 0; i < nthAngles.length; i++ ){
				Double nthAngle = (Double)nthAngles[i];
				GraphAtom nthAtom = (GraphAtom)treeMap.get(nthAngle);
				
				// ProxyAtomは無視
				if(nthAtom.me.getFunctor().isInsideProxy() ||
						nthAtom.me.getFunctor().isOutsideProxy())
				{
					continue;
				}
				
				if(null != nthAtom){
					double anglePre = (i != 0) ? ((Double)nthAngles[i]).doubleValue() - ((Double)nthAngles[i - 1]).doubleValue() 
					                            : (Math.PI * 2) - ((Double)nthAngles[nthAngles.length - 1]).doubleValue() + ((Double)nthAngles[0]).doubleValue();
					double angleCur = (i != nthAngles.length - 1) ? ((Double)nthAngles[i + 1]).doubleValue() - ((Double)nthAngles[i]).doubleValue() 
					                      		: (Math.PI * 2) - ((Double)nthAngles[nthAngles.length - 1]).doubleValue() + ((Double)nthAngles[0]).doubleValue();
					double angleR = angleCur - anglePre;
					double dx = nthAtom.getPosX() - targetAtom.getPosX();
					double dy = nthAtom.getPosY() - targetAtom.getPosY();
					double edgeLength = Math.sqrt(dx * dx + dy * dy);
					if(edgeLength == 0.0){ edgeLength = 0.00001; }
					//線分に垂直で長さ１のベクトル
					// これが you に働く力の単位ベクトルになる
					double tx = -dy / edgeLength;
					double ty =  dx / edgeLength;

					dx = 1.5 * tx * angleR;
					dy = 1.5 * ty * angleR;
					
					targetAtom.moveDelta(-dx, -dy);
					nthAtom.moveDelta(dx, dy);

				}
			}
			
		}
	}
	
	/**
	 * [0, 2PI) に正規化してかえす。mod 2PI みたいなかんじ。
	 * @param a
	 * @return
	 */
	static double regulate(double a) {
		while(a<0.0) a+= Math.PI*2;
		while(a>=2*Math.PI) a-= Math.PI*2;
		return a;
	}
	
	/**
	 * リンクの長さを調節する。
	 *
	 */
	private void relaxEdge(){
		double length = EDGE_LENGTH;
		GraphAtom targetAtom;
		Iterator graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			targetAtom = (GraphAtom)graphAtoms.next();
			
			// ProxyAtomは無視
			if(targetAtom.me.getFunctor().isInsideProxy() ||
					targetAtom.me.getFunctor().isOutsideProxy())
			{
				continue;
			}
			
			int dx = 0, dy = 0;
			int edgeNum = targetAtom.me.getEdgeCount(); 
			
			if(edgeNum == 0){ continue; }
			
			// つながっているアトムを走査
			for(int i = 0; i < edgeNum; i++){
				GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
				if(null == nthAtom){
					continue;
				}
				
				nthAtom = getRealNthAtom(nthAtom);
				
				if(nthAtom == null){ continue; }
				
				dx = nthAtom.getPosX() - targetAtom.getPosX();
				dy = nthAtom.getPosY() - targetAtom.getPosY();
				
				double edgeLen = Math.sqrt((double)((dx * dx) + (dy * dy)));
				double f = (edgeLen - (length * GraphPanel.getMagnification()));
				double ddx = 0.05 * f * dx / edgeLen;
				double ddy = 0.05 * f * dy / edgeLen;
				
				targetAtom.moveDelta(ddx, ddy);
				nthAtom.moveDelta(-ddx, -ddy);
			}
		}
		
	}
	
	public GraphAtom getGraphAtom(Atom atom){
		return atomMap.get(atom);
	}
	
	public GraphAtom getDummyGraphAtom(){
		return dummyGraphAtom;
	}
	
	public GraphMembrane getParent(){
		return myParent;
	}
	
	public boolean isRoot(){
		return root;
	}
	
	public GraphAtom getRealNthAtom(GraphAtom nthAtom){
		Atom overProxyAtom = nthAtom.me;
		GraphMembrane overProxyMem = null;
		// リンク先がProxyAtomだったら、その先（自膜の外または子膜中）のアトムを取得
		while(overProxyAtom.getFunctor().isInsideProxy() ||
				overProxyAtom.getFunctor().isOutsideProxy()) 
		{
			overProxyAtom = overProxyAtom.nthAtom(0).nthAtom(1);
			overProxyMem = findGraphMem(overProxyAtom.getMem());
			if(overProxyMem == null){ break; }
			nthAtom = overProxyMem.getGraphAtom(overProxyAtom);
//			if(nthAtom == null){ break; }
		}

		if((nthAtom == null) && (overProxyMem != null)){
			while((!overProxyMem.isRoot()) && (!overProxyMem.getParent().getViewInside())){
				overProxyMem = overProxyMem.getParent();
			}
			nthAtom = overProxyMem.getDummyGraphAtom();
		}
		return nthAtom;
	}
	
	/**
	 * 膜の内部、および膜を描画する。
	 * ただし、膜内が非表示設定になっている場合は、
	 * アトムおよび子膜を描画しない。
	 * @param g
	 */
	public void paint(Graphics g){
		int deltaPos = (int)((GraphAtom.ATOM_DEF_SIZE / 2) * GraphPanel.getMagnification());
		int margin1 = GraphAtom.getAtomSize() * 2;
		int margin2 = GraphAtom.getAtomSize() * 4;
		synchronized (atomMap) {
			
			if(getViewInside()){
				Iterator graphAtoms = atomMap.values().iterator();
				
				// リンクの描画
				while(graphAtoms.hasNext()){
					GraphAtom targetAtom = (GraphAtom)graphAtoms.next();
					// ProxyAtomは無視
					if(targetAtom.me.getFunctor().isInsideProxy() ||
							targetAtom.me.getFunctor().isOutsideProxy())
					{
						continue;
					}
					
					int edgeNum = targetAtom.me.getEdgeCount(); 
					g.setColor(Color.GRAY);
					
					if(edgeNum == 0){
						continue; 
					}
					
					// つながっているアトムを走査
					for(int i = 0; i < edgeNum; i++){
						GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
						
						if(null != nthAtom){
							
							nthAtom = getRealNthAtom(nthAtom);
							
							if(nthAtom == null){ continue; }
							
							if((!nthAtom.isDummy()) && (targetAtom.me.getid() < nthAtom.me.getid())){
								continue;
							}
							g.drawLine(targetAtom.getPosX() + deltaPos,
									targetAtom.getPosY() + deltaPos,
									nthAtom.getPosX() + deltaPos,
									nthAtom.getPosY() + deltaPos);
						}
					}
				}
				
				// アトムの描画
				graphAtoms = atomMap.values().iterator();
				while(graphAtoms.hasNext()){
					GraphAtom targetAtom = (GraphAtom)graphAtoms.next();
					if(targetAtom.me.getFunctor().isInsideProxy() ||
							targetAtom.me.getFunctor().isOutsideProxy())
					{
						continue;
					}
					targetAtom.paint(g);
				}
				
				// 膜の描画
				Iterator graphMems = memMap.values().iterator();
				while(graphMems.hasNext()){
					GraphMembrane targetMem = (GraphMembrane)graphMems.next();
					targetMem.paint(g);
					
					if(posX1 > targetMem.getPosX1() - margin1){ posX1 = targetMem.getPosX1() - margin1; }
					if(posY1 > targetMem.getPosY1() - margin1){ posY1 = targetMem.getPosY1() - margin1; }
					if(posX2 < targetMem.getPosX2() + margin1){ posX2 = targetMem.getPosX2() + margin1; }
					if(posY2 < targetMem.getPosY2() + margin1){ posY2 = targetMem.getPosY2() + margin1; }
				}
			}

			g.setColor(MEM_COLOR);

			// 塗りつぶしなし
			if(viewInside && !root){
				g.drawRoundRect(posX1 - margin1,
						posY1 - margin1,
						posX2 - posX1 + margin2,
						posY2 - posY1 + margin2,
						ROUND,
						ROUND);
			}
			// 塗りつぶしあり
			else if(!root){
				g.fillRoundRect(posX1 - margin1,
						posY1 - margin1,
						posX2 - posX1 + margin2,
						posY2 - posY1 + margin2,
						ROUND,
						ROUND);
			}

		}
	}
	
	/**
	 * 膜内を描画するかどうか
	 * @param view
	 */
	public void setViewInside(boolean view){
		viewInside = view;
	}
	
	
	/**
	 * 膜内を描画するかどうか
	 * @return
	 */
	public boolean getViewInside(){
		return (viewInside || root);
	}
	
	/** 
	 * clickedPoint に一番近いアトムを取得する
	 * 検索対象には子膜も含まれる。
	 * @param clickedPoint
	 */
	public GraphAtom getNearestAtom(Point clickedPoint){
		GraphAtom nearestAtom = null;
		double distance = 0.0;
		synchronized (atomMap) {
			Iterator atoms = atomMap.values().iterator();
			
			// 膜内を表示しない場合は、ダミーGraphAtomで位置の比較
			if(!getViewInside()){
				GraphAtom targetAtom = dummyGraphAtom;
				double dx = targetAtom.getPosX() - clickedPoint.x;
				double dy = targetAtom.getPosY() - clickedPoint.y;
				double distanceTmp = Math.sqrt((dx * dx) + (dy * dy));
				
				if(null != nearestAtom){
					if(distance >= distanceTmp){
						distance = distanceTmp;
						nearestAtom = targetAtom;
					}
				} else {
					distance = distanceTmp;
					nearestAtom = targetAtom;
				}
			}
			
			// 膜内を表示する場合は全アトムに対して位置の比較
			while(getViewInside() && atoms.hasNext()){
				GraphAtom targetAtom = (GraphAtom)atoms.next();
				double dx = targetAtom.getPosX() - clickedPoint.x;
				double dy = targetAtom.getPosY() - clickedPoint.y;
				double distanceTmp = Math.sqrt((dx * dx) + (dy * dy));
				
				if(null != nearestAtom){
					if(distance < distanceTmp){
						continue;
					}
					distance = distanceTmp;
					nearestAtom = targetAtom;
				} else {
					distance = distanceTmp;
					nearestAtom = targetAtom;
				}
			}
			
			// 子膜から子膜内での一番近いアトムを取得し、現在保持しているアトムと比較する
			Iterator mems = memMap.values().iterator();
			while(mems.hasNext()){
				GraphMembrane targetMem = (GraphMembrane)mems.next();
				GraphAtom targetAtom = targetMem.getNearestAtom(clickedPoint);
				double dx = targetAtom.getPosX() - clickedPoint.x;
				double dy = targetAtom.getPosY() - clickedPoint.y;
				double distanceTmp = Math.sqrt((dx * dx) + (dy * dy));
				
				if(null != nearestAtom){
					if(distance < distanceTmp){
						continue;
					}
					distance = distanceTmp;
					nearestAtom = targetAtom;
				} else {
					distance = distanceTmp;
					nearestAtom = targetAtom;
				}
			}
		}
		
		return nearestAtom;
	}
	

	/**
	 * 膜内を文字列として返す。
	 */
	public String toString(){
		return myMem.toString();
	}
}

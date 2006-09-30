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
import runtime.Membrane;

public class GraphMembrane {
	
	
	/////////////////////////////////////////////////////////////////
	//	定数
	final static
	private double EDGE_LENGTH = 150.0;
	
	final static
	private int ROUND = 40;
	
	/////////////////////////////////////////////////////////////////

	private boolean isRoot = false; 
	private Membrane myMem;
	private int posX1;
	private int posY1;
	private int posX2;
	private int posY2;
	private boolean viewInside = true;
	
	
	private Map<Atom, GraphAtom> atomMapTemp =
		Collections.synchronizedMap(new HashMap<Atom, GraphAtom>());
	
	private Map<Atom, GraphAtom> atomMap =
		Collections.synchronizedMap(new HashMap<Atom, GraphAtom>());
	
	private Map<Membrane, GraphMembrane> memMapTemp =
		Collections.synchronizedMap(new HashMap<Membrane, GraphMembrane>());
	
	private Map<Membrane, GraphMembrane> memMap =
		Collections.synchronizedMap(new HashMap<Membrane, GraphMembrane>());
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public GraphMembrane(Membrane mem) {
		resetMembrane(mem);
	}
	
	public GraphMembrane(Membrane mem, boolean rootFlag) {
		isRoot = rootFlag;
		resetMembrane(mem);
	}
	
	/////////////////////////////////////////////////////////////////
	
	public void resetMembrane(Membrane mem){
		synchronized (atomMap) {
			myMem = mem;
			/////////////////////////////////////////////////////////////
			// アトムの増減処理
			atomMapTemp.clear();
			Iterator atoms = mem.atomIterator();
			posX1 = posY1 = 1000;
			posX2 = posY2 = 0;
			
			while(atoms.hasNext()){
				Atom atom = (Atom)atoms.next();
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
				if(memMap.containsKey(targetMem)){
					GraphMembrane existMem = memMap.get(targetMem);
					memMapTemp.put(targetMem, existMem);
					existMem.resetMembrane(targetMem);
				}
				else{
					memMapTemp.put(targetMem, new GraphMembrane(targetMem));
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
	
	private void moveCalc(){
		Iterator<GraphAtom> graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			graphAtoms.next().moveCalc();
		}
		
	}
	
	private void relaxAngle(){
		GraphAtom targetAtom;
		Iterator<GraphAtom> graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			targetAtom = graphAtoms.next();
			int edgeNum = targetAtom.me.getEdgeCount(); 
			
			if(edgeNum < 2){ continue; }

			Map<Double, GraphAtom> treeMap = new TreeMap<Double, GraphAtom>();
			
			// つながっているアトムを走査
			for(int i = 0; i < edgeNum; i++){
				GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
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
	
	private void relaxEdge(){
		GraphAtom targetAtom;
		Iterator graphAtoms = atomMap.values().iterator();
		
		// 膜に直接含まれるアトムが対象
		while(graphAtoms.hasNext()){
			targetAtom = (GraphAtom)graphAtoms.next();
			int dx = 0, dy = 0;
			int edgeNum = targetAtom.me.getEdgeCount(); 
			
			if(edgeNum == 0){ continue; }
			
			// つながっているアトムを走査
			for(int i = 0; i < edgeNum; i++){
				GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
				if(null == nthAtom){
					continue;
				}
				dx = nthAtom.getPosX() - targetAtom.getPosX();
				dy = nthAtom.getPosY() - targetAtom.getPosY();
				
				double edgeLen = Math.sqrt((double)((dx * dx) + (dy * dy)));
				double f = (edgeLen - (EDGE_LENGTH * GraphPanel.getMagnification()));
				double ddx = 0.05 * f * dx / edgeLen;
				double ddy = 0.05 * f * dy / edgeLen;
				
				targetAtom.moveDelta(ddx, ddy);
				nthAtom.moveDelta(-ddx, -ddy);
			}
		}
		
	}
	
	public void paint(Graphics g){
		int deltaPos = (int)((GraphAtom.ATOM_DEF_SIZE / 2) * GraphPanel.getMagnification());
		synchronized (atomMap) {
			Iterator graphAtoms = atomMap.values().iterator();
			
			// リンクの描画
			while(graphAtoms.hasNext()){
				GraphAtom targetAtom = (GraphAtom)graphAtoms.next();
				int edgeNum = targetAtom.me.getEdgeCount(); 
				g.setColor(Color.GRAY);
				
				if(edgeNum == 0){
					continue; 
				}
				
				// つながっているアトムを走査
				for(int i = 0; i < edgeNum; i++){
					GraphAtom nthAtom = (GraphAtom)atomMap.get(targetAtom.me.nthAtom(i));
					if(null != nthAtom){
						if(targetAtom.me.getid() < nthAtom.me.getid()){
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
				targetAtom.paint(g);
			}
			
			// 膜の描画
			Iterator graphMems = memMap.values().iterator();
			while(graphMems.hasNext()){
				GraphMembrane targetMem = (GraphMembrane)graphMems.next();
				targetMem.paint(g);
			}
			

			if(viewInside && !isRoot){
				g.drawRoundRect(posX1 - (GraphAtom.getAtomSize() * 2),
						posY1 - (GraphAtom.getAtomSize() * 2),
						posX2 - posX1 + (GraphAtom.getAtomSize() * 4),
						posY2 - posY1 + (GraphAtom.getAtomSize() * 4),
						ROUND,
						ROUND);
			}
			else if(!isRoot){
				g.fillRoundRect(posX1 - (GraphAtom.getAtomSize() * 2),
						posY1 - (GraphAtom.getAtomSize() * 2),
						posX2 - posX1 + (GraphAtom.getAtomSize() * 4),
						posY2 - posY1 + (GraphAtom.getAtomSize() * 4),
						ROUND,
						ROUND);
			}

		}
	}
	
	public void setViewInside(boolean view){
		viewInside = view;
	}
	
	public boolean getViewInside(){
		return viewInside;
	}
	
	public GraphAtom getNearestAtom(Point clickedPoint){
		GraphAtom nearestAtom = null;
		double distance = 0.0;
		synchronized (atomMap) {
			Iterator atoms = atomMap.values().iterator();
			while(atoms.hasNext()){
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
	

	public String toString(){
		return myMem.toString();
	}
}

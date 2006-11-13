package gui2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import runtime.Atom;
import runtime.Membrane;

public class MembraneNode{
//
//	final static
//	private Color MEM_COLOR = new Color(102, 153, 255);
//	
//	final static
//	private Color MEM_EDGE_COLOR = Color.BLACK;
//	
//	final static
//	private double ROUND = 40;
//	
//	final static
//	private double MARGIN = 50;
//
//	///////////////////////////////////////////////////////////////////////////
//	
//	private Membrane myMem;
//	private boolean viewInside = true;
//	
//	private Map<Membrane, MembraneNode> memMap = new HashMap<Membrane, MembraneNode>();
//	private Map<Membrane, MembraneNode> memMapTemp = new HashMap<Membrane, MembraneNode>();
//	private Map<Atom, AtomNode> atomMap = new HashMap<Atom, AtomNode>();
//	private Map<Atom, AtomNode> atomMapTemp = new HashMap<Atom, AtomNode>();
//	
//	///////////////////////////////////////////////////////////////////////////
//	
//	public MembraneNode(MembraneNode parentNode, Membrane mem){
//		myMem = mem;
//		setParentNode(parentNode);
//		setShape(new RoundRectangle2D.Double((Math.random() * 800) - 400,
//				(Math.random() * 600) - 300,
//				50.0,
//				50.0,
//				ROUND,
//				ROUND));
//		resetMembrane(mem);
//	}
//	
//	///////////////////////////////////////////////////////////////////////////
//
//	public void resetMembrane(Membrane mem){
//		searchAtom(mem);
//		searchMem(mem);
//	}
//	
//	public boolean isRoot(){
//		return (getParentNode() == null);
//	}
//	
//	private RoundRectangle2D.Double getRect(){
//		return (RoundRectangle2D.Double)getShape();
//	}
//	
//	private void searchAtom(Membrane mem){
////		if(isViewInside()){
////			getRect().width = 0;
////			getRect().x = Integer.MAX_VALUE;
////			getRect().height = 0;
////			getRect().y = Integer.MAX_VALUE;
////		}
////		
//		synchronized (atomMap) {
//			atomMapTemp.clear();
//			Iterator<Atom> atoms = mem.atomIterator();
//			while(atoms.hasNext()){
//				Atom atom = (Atom)atoms.next();
//
//				// ProxyAtomは無視
//				if(atom.getFunctor().isInsideProxy() ||
//						atom.getFunctor().isOutsideProxy())
//				{
//					continue;
//				}
//				
//				AtomNode targetAtom = null;
//				if(atomMap.containsKey(atom)){
//					targetAtom = atomMap.get(atom);
//					if(isViewInside()){
//						if(getRect().getMaxX()  < targetAtom.getEllipse().getMaxX() + MARGIN){
//							getRect().width = targetAtom.getEllipse().getMaxX() + MARGIN - getRect().getX();
//						} 
//						if(getRect().getMinX() > targetAtom.getEllipse().getMinX() - MARGIN){
//							getRect().x = targetAtom.getEllipse().x - MARGIN;
//							getRect().width = targetAtom.getEllipse().getMaxX() + MARGIN - getRect().getX();
//						}
//						if(getRect().getMaxY()  < targetAtom.getEllipse().getMaxY() + MARGIN){
//							getRect().height = targetAtom.getEllipse().getMaxY() + MARGIN - getRect().getY();
//						} 
//						if(getRect().getMinY() > targetAtom.getEllipse().getMinY() - MARGIN){
//							getRect().y = targetAtom.getEllipse().y - MARGIN;
//							getRect().height = targetAtom.getEllipse().getMaxY() + MARGIN - getRect().getY();
//						}
//					}
//						
//					atomMapTemp.put(atom, targetAtom);
//				}
//				else{
//					targetAtom = new AtomNode(this, atom);
//					atomMapTemp.put(atom, targetAtom);
//				}
//			}
//			atomMap.clear();
//			atomMap.putAll(atomMapTemp);
//		}
//	}
//	
//	/**
//	 * (x, y)にヒットするNodeを取得する．
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	public Node getPointNode(int x, int y){
//		Node targetNode = null;
//		synchronized (atomMap) {
//			Iterator<AtomNode> atoms = atomMap.values().iterator();
//			while(atoms.hasNext()){
//				AtomNode atom = atoms.next();
//				targetNode = atom.pickupNode(x, y);
//				if(targetNode != null && targetNode.isPickable()){
//					return targetNode;
//				}
//			}
//		}
//
//		synchronized (memMap) {
//			Iterator<MembraneNode> mems = memMap.values().iterator();
//			while(mems.hasNext()){
//				MembraneNode mem = mems.next();
//				targetNode = mem.pickupNode(x, y);
//				// 位置はあっていて，拾える
//				if(targetNode != null && targetNode.isPickable()){
//					return targetNode;
//				}
//				// 位置はあっているが，拾えない
//				else if(isRoot() || (targetNode != null && !targetNode.isPickable())){
//					targetNode = mem.getPointNode(x, y);
//				}
//			}
//		}
//		return null;
//	}
//	
//	private void searchMem(Membrane mem){
//		synchronized (memMap) {
//			memMapTemp.clear();
//			Iterator<Membrane> mems = mem.memIterator();
//			while(mems.hasNext()){
//				Membrane targetMem = mems.next();
//				MembraneNode targetGraphMem = null;
//				if(memMap.containsKey(targetMem)){
//					targetGraphMem = memMap.get(targetMem);
//					targetGraphMem.resetMembrane(targetMem);
//					memMapTemp.put(targetMem, targetGraphMem);
//				}
//				else{
//					targetGraphMem = new MembraneNode(this, targetMem);
//					memMapTemp.put(targetMem, targetGraphMem);
//				}
//				
//
//				if(isViewInside()){
//					if(getRect().getMaxX()  < targetGraphMem.getRect().getMaxX()){
//						getRect().width = targetGraphMem.getRect().getMaxX() - getRect().getX();
//					} 
//					if(getRect().getMinX() > targetGraphMem.getRect().getMinX()){
//						getRect().x = targetGraphMem.getRect().x;
//						getRect().width = targetGraphMem.getRect().getMaxX() - getRect().getX();
//					}
//					if(getRect().getMaxY()  < targetGraphMem.getRect().getMaxY()){
//						getRect().height = targetGraphMem.getRect().getMaxY() - getRect().getY();
//					} 
//					if(getRect().getMinY() > targetGraphMem.getRect().getMinY()){
//						getRect().y = targetGraphMem.getRect().y;
//						getRect().height = targetGraphMem.getRect().getMaxY() - getRect().getY();
//					}
//				}
//			}
//			memMap.clear();
//			memMap.putAll(memMapTemp);
//		}
//	}
//	
//	/**
//	 * 膜内を描画するかどうか
//	 * @return
//	 */
//	public boolean isViewInside(){
//		return (viewInside || isRoot());
//	}
//	
//	///////////////////////////////////////////////////////////////////////////
//	
//	@Override
//	public boolean isMembrane() {
//		return true;
//	}
//
//	@Override
//	public void paint(Graphics g) {
//		// 塗りつぶしなし時の膜描画
//		if(isViewInside() && !isRoot()){
//			g.setColor(MEM_COLOR);
//			((Graphics2D)g).draw(getShape());
//		}
//		if(isViewInside()){
//			synchronized (memMap) {
//				Iterator<MembraneNode> mems = memMap.values().iterator();
//				while(mems.hasNext()){
//					MembraneNode mem = mems.next();
//					mem.paint(g);
//				}
//			}
//			
//			synchronized (atomMap) {
//				Iterator<AtomNode> atoms = atomMap.values().iterator();
//				while(atoms.hasNext()){
//					AtomNode atom = atoms.next();
//					atom.paint(g);
//				}
//			}
//		}
//		
//		// 塗りつぶしあり時の膜描画
//		if(!isViewInside()){
//			int posX = (int)getRect().x;
//			int posY = (int)getRect().y;
//			int sizeX = 50;
//			int sizeY = 50;
//			// 塗りつぶし
//			g.setColor(MEM_COLOR);
//			((Graphics2D)g).fill(getShape());
////			g.fillRoundRect(posX,
////					posY,
////					sizeX,
////					sizeY,
////					(int)ROUND,
////					(int)ROUND);
//			// 縁描画
//			g.setColor(MEM_EDGE_COLOR);
//			g.drawRoundRect(posX,
//					posY,
//					sizeX,
//					sizeY,
//					(int)ROUND,
//					(int)ROUND);
//		}
//	}
//
//	@Override
//	public void setPos(double x, double y) {
////		synchronized (atomMap) {
////			Iterator<AtomNode> atoms = atomMap.values().iterator();
////			while(atoms.hasNext()){
////				AtomNode atom = atoms.next();
////				atom.setPos(x - getRect().x + atom.getEllipse().getX(),
////						    y -  getRect().y + atom.getEllipse().getX());
////			}
////		}
////
////		synchronized (memMap) {
////			Iterator<MembraneNode> mems = memMap.values().iterator();
////			while(mems.hasNext()){
////				MembraneNode mem = mems.next();
////				mem.setPos(x - getRect().x + mem.getRect().x,
////						   y - getRect().y + mem.getRect().y);
////			}
////		}
//		getRect().x = x - getRect().width / 2;
//		getRect().y = y - getRect().height / 2;
//	}
//
//	@Override
//	public void moveCalc() {
//		// TODO Auto-generated method stub
//		
//	}
//	
}

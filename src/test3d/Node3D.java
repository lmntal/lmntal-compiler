package test3d;

import runtime.Functor;


public interface Node3D {
	public int getid();
	public void initNode3d();
	public String getName();
	public Double3DPoint getPosition3d();
	public void setPosition3d(Double3DPoint p);
	public int getEdgeCount();
	public Node3D getNthNode3d(int index);
	public boolean isVisible();
	public Functor getFunctor();
	
	public void setObj(LMNTransformGroup obj);
	public LMNTransformGroup getObj();
	
	public void setMoveDelta3d(double dx, double dy, double dz);
	public void initMoveDelta3d();
//	public void move(Rectangle area);
//	public void paintNode(Graphics g);
//	public void paintEdge(Graphics g);
}

package test3d;

import javax.media.j3d.TransformGroup;


public class LMNTransformGroup extends TransformGroup{
	private int id;
	private int edge_num;
	private Edge3D ie[];
	private Node3D me;
	private boolean visible = true;
	

	public void setMe(Node3D i){
		me=i;
	}
	public Node3D getMe(){
		return me;
	}
	public void setVisible(boolean v){
		visible=v;
	}
	public boolean getVisible(){
		return visible;
	}
	public void setEdge(Edge3D i[]){
		ie=i;
	}
	public Edge3D[] getEdge(){
		return ie;
	}
	public int getEdgeNum(){
		return edge_num;
	}
	public void setEdgeNum(int i){
		edge_num=i;
	}
	public int getid(){
		return id;
	}
	public void setid(int i){
		id = i;
	}
}
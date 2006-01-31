package graphic;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import runtime.AbstractMembrane;
import test.GUI.Node;


/**
 * 
 * @author nakano
 * 描画用の膜を、画像オブジェクトにする
 */
public class GraphicObj{
	public String name = null;
	private String atomString = null;
	private int atomStringPoint = 15;
	//boolean enable;
	int sizex = 0;
	int sizey = 0;
	private int[] array_x = new int[4] ;
	private int[] array_y = new int[4];
	public int sequence = 0;
	/**色の設定*/
	private int color_r = 0;
	private int color_g = 0;
	private int color_b = 0;
	Image atomimg = null;
	public String atomobj = null;
	/**ウィンドウ膜との距離*/
	public int distance;
	public String remem = null;
	
	public GraphicObj(AbstractMembrane m){
		Iterator ite = m.atomIterator();
		Node a;

//		if(m.getLockThread() != null) locked = true;
		while(ite.hasNext()){
			a = (Node)ite.next();
			/**描画するファイルの取得*/
			if(a.getName()=="getpic"){
				if(a.getEdgeCount() != 1)continue;
				String picName = a.getNthNode(0).getName();
				if(picName.equals("string")){
					if(a.getNthNode(0).getEdgeCount()==2){
						setString(a.getNthNode(0).getNthNode(0).getName());
					}else if(a.getNthNode(0).getEdgeCount()==3){
						setString(a.getNthNode(0).getNthNode(0).getName(),a.getNthNode(0).getNthNode(1).getName());
					}
				}
				SetPic( picName );
			}
			/**名前の取得*/
			else if(a.getName()=="name"){
				if(a.getEdgeCount() != 1)continue;
				setName(a.getNthNode(0).getName());
			}
			/**描画の順番（前後関係）の取得*/
			else if(a.getName()=="sequence"){
				try{
					sequence = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){					
				}
				
			}
			/**描画の順番（前後関係）の取得*/
			else if(a.getName()=="color"){
				if(a.getEdgeCount() != 3)continue;
				try{
					setColor( Integer.parseInt(a.getNthNode(0).getName()),Integer.parseInt(a.getNthNode(1).getName()),Integer.parseInt(a.getNthNode(2).getName()));
				}catch(NumberFormatException error){			
				}
				
			}
			/**描画する位置の取得*/
			else if(a.getName()=="position"){
				if(a.getEdgeCount() ==2){
					try{
						setArrayPos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								0,0,0,0,0,0
								);
					}catch(NumberFormatException error){
					}
				}else if(a.getEdgeCount() ==4){
					try{
						setArrayPos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								0,0,0,0
								);
					}catch(NumberFormatException error){
					}
				}else if(a.getEdgeCount() ==6){
					try{
						setArrayPos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								Integer.parseInt(a.getNthNode(4).getName()),
								Integer.parseInt(a.getNthNode(5).getName()),
								0,0
								);
					}catch(NumberFormatException error){
					}
				}else if(a.getEdgeCount() ==8){
					try{
						setArrayPos(Integer.parseInt(a.getNthNode(0).getName()),
								Integer.parseInt(a.getNthNode(1).getName()),
								Integer.parseInt(a.getNthNode(2).getName()),
								Integer.parseInt(a.getNthNode(3).getName()),
								Integer.parseInt(a.getNthNode(4).getName()),
								Integer.parseInt(a.getNthNode(5).getName()),
								Integer.parseInt(a.getNthNode(6).getName()),
								Integer.parseInt(a.getNthNode(7).getName())
								);
					}catch(NumberFormatException error){
					}
				}else continue;
				
			}
			/**描画するサイズの取得*/
			else if(a.getName()=="size"){
				if(a.getEdgeCount() != 2)continue;
				try{
					sizex = Integer.parseInt(a.getNthNode(0).getName());
				}catch(NumberFormatException error){
				}

				try{
					sizey = Integer.parseInt(a.getNthNode(1).getName());
				}catch(NumberFormatException error){
					
				}
				
			}
		}
	}
	private void setColor(int a, int b , int c){
		if(a > 255) color_r = 255;
		else if(a < 0)color_r = 0;
		else color_r = a;
		
		if(b > 255) color_g =255;
		else if(b < 0)color_g = 0;
		else color_g = b;
		
		if(c > 255) color_b = 255;
		else if(c < 0)color_b = 0;
		else color_b = c;
	}
	public void setName(int n){
		name = Integer.toString(n);
	}
	public void setName(String n){
		name = n;
	}
	public boolean isset(){
		if((atomimg == null & atomobj == null) || name == null){
			return false;
		}
		return true;
	}
	
	private boolean SetPic(String filename){
		if(filename == null || filename ==""){
			return false;
		}
		File file = new File(filename);
		if(file.exists()){
			Toolkit toolkit = Toolkit.getDefaultToolkit();
	//		Imageオブジェクトの生成
			atomimg = toolkit.getImage(filename);
		}else{
			atomobj=filename;
		}
		return true;
	}
	
	private void setString(String s){
		atomString = s;
	}
	
	private void setString(String s, String i){
		atomString = s;
		try{
			atomStringPoint = Integer.parseInt(i);
		}catch(NumberFormatException e){}
	}

	private void setArrayPos(int x1, int y1,int x2, int y2, int x3,int y3,int x4,int y4){
		array_x[0] = x1;
		array_x[1] = x2;
		array_x[2] = x3;
		array_x[3] = x4;
		array_y[0] = y1;
		array_y[1] = y2;
		array_y[2] = y3;
		array_y[3] = y4;
	}
	
	public boolean drawAtom(Graphics g, HashMap m){
		int[] x;
		int[] y;
		if(atomimg == null & atomobj == null){
			return false;
		}
		if(remem!=null){
			Relativemem rm = (Relativemem)m.get(remem);
			int dx=0,dy=0;
			dx = rm.getx(m);
			dy = rm.gety(m);
			x=new int[]{
					(new Double((new Integer(array_x[0]).doubleValue())* Math.cos(rm.getangle(m)) - (new Integer(array_y[0]).doubleValue()) * Math.sin(rm.getangle(m))).intValue()) + dx,
					(new Double((new Integer(array_x[1]).doubleValue())* Math.cos(rm.getangle(m)) - (new Integer(array_y[1]).doubleValue()) * Math.sin(rm.getangle(m))).intValue()) + dx,
					(new Double((new Integer(array_x[2]).doubleValue())* Math.cos(rm.getangle(m)) - (new Integer(array_y[2]).doubleValue()) * Math.sin(rm.getangle(m))).intValue()) + dx,
					(new Double((new Integer(array_x[3]).doubleValue())* Math.cos(rm.getangle(m)) - (new Integer(array_y[3]).doubleValue()) * Math.sin(rm.getangle(m))).intValue()) + dx,
					};
			y=new int[]{
					(new Double((new Integer(array_x[0]).doubleValue())* Math.sin(rm.getangle(m)) + (new Integer(array_y[0]).doubleValue()) * Math.cos(rm.getangle(m))).intValue()) + dy,
					(new Double((new Integer(array_x[1]).doubleValue())* Math.sin(rm.getangle(m)) + (new Integer(array_y[1]).doubleValue()) * Math.cos(rm.getangle(m))).intValue()) + dy,
					(new Double((new Integer(array_x[2]).doubleValue())* Math.sin(rm.getangle(m)) + (new Integer(array_y[2]).doubleValue()) * Math.cos(rm.getangle(m))).intValue()) + dy,
					(new Double((new Integer(array_x[3]).doubleValue())* Math.sin(rm.getangle(m)) + (new Integer(array_y[3]).doubleValue()) * Math.cos(rm.getangle(m))).intValue()) + dy,
					};
		}else{
			x=new int[]{array_x[0],array_x[1],array_x[2],array_x[3]};
			y=new int[]{array_y[0],array_y[1],array_y[2],array_y[3]};
		}
		if(atomobj != null){
			g.setColor(new Color(color_r, color_g, color_b));
			if(atomobj.equals("circle") || atomobj.equals("oval"))
				g.drawOval(x[0], y[0], sizex, sizey);
			else if (atomobj.equals("rect"))
				g.drawRect(x[0], y[0], sizex, sizey);
			else if (atomobj.equals("line")){
				g.drawLine(x[0], y[0], x[1],y[1]);
			}
			else if (atomobj.equals("filltriangle")){
				g.fillPolygon(x, y, 3);
			}
			else if (atomobj.equals("triangle")){
				g.drawPolygon(x, y, 3);
			}
			else if(atomobj.equals("fillrect")){
				g.fillRect(x[0], y[0], sizex, sizey);
			}
			else if (atomobj.equals("fillcircle") || atomobj.equals("filloval")){
				g.fillOval(x[0], y[0], sizex, sizey);
			}
			else if (atomobj.equals("string")){
				g.setFont(new Font("myFont",Font.PLAIN,atomStringPoint));
				g.drawString(atomString, x[0], y[0]);
			}
		}
		else if(atomimg != null)	
			g.drawImage(atomimg,array_x[0], array_y[0],sizex,sizey,null);
		return true;
	}
	
}
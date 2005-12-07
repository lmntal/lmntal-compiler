package graphic;

import java.awt.*;
import java.io.File;
import java.util.HashMap;


/**
 * 
 * @author nakano
 * 描画用の膜を、画像オブジェクト（アトム？）にする
 */
public class GraphicAtoms{
	public String name = null;
	boolean enable;
	int sizex = 0;
	int sizey = 0;
	int[] array_x = new int[4] ;
	int[] array_y = new int[4];
	int sequence = 0;
	/**色の設定*/
	private int color_r = 0;
	private int color_g = 0;
	private int color_b = 0;
	Image atomimg = null;
	public String atomobj = null;
	/**ウィンドウ膜との距離*/
	int distance;
	public String remem = null;
	
	public GraphicAtoms(){
		enable = false;
	}
	public void setcolor(int a, int b , int c){
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
	public void setname(int n){
		name = Integer.toString(n);
	}
	public void setname(String n){
		name = n;
	}
	public boolean isset(){
		if((atomimg == null & atomobj == null) || name == null){
			return false;
		}
		return true;
	}
	
	public boolean SetPic(String filename){
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

	public void setarraypos(int x1, int y1,int x2, int y2, int x3,int y3,int x4,int y4){
		array_x[0] = x1;
		array_x[1] = x2;
		array_x[2] = x3;
		array_x[3] = x4;
		array_y[0] = y1;
		array_y[1] = y2;
		array_y[2] = y3;
		array_y[3] = y4;
	}
	
	public boolean drawatom(Graphics g, HashMap m){
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
					(new Double((new Integer(array_x[0]).doubleValue())* Math.cos(rm.getangle()) - (new Integer(array_y[0]).doubleValue()) * Math.sin(rm.getangle())).intValue()) + dx,
					(new Double((new Integer(array_x[1]).doubleValue())* Math.cos(rm.getangle()) - (new Integer(array_y[1]).doubleValue()) * Math.sin(rm.getangle())).intValue()) + dx,
					(new Double((new Integer(array_x[2]).doubleValue())* Math.cos(rm.getangle()) - (new Integer(array_y[2]).doubleValue()) * Math.sin(rm.getangle())).intValue()) + dx,
					(new Double((new Integer(array_x[3]).doubleValue())* Math.cos(rm.getangle()) - (new Integer(array_y[3]).doubleValue()) * Math.sin(rm.getangle())).intValue()) + dx,
					};
			y=new int[]{
					(new Double((new Integer(array_x[0]).doubleValue())* Math.sin(rm.getangle()) + (new Integer(array_y[0]).doubleValue()) * Math.cos(rm.getangle())).intValue()) + dy,
					(new Double((new Integer(array_x[1]).doubleValue())* Math.sin(rm.getangle()) + (new Integer(array_y[1]).doubleValue()) * Math.cos(rm.getangle())).intValue()) + dy,
					(new Double((new Integer(array_x[2]).doubleValue())* Math.sin(rm.getangle()) + (new Integer(array_y[2]).doubleValue()) * Math.cos(rm.getangle())).intValue()) + dy,
					(new Double((new Integer(array_x[3]).doubleValue())* Math.sin(rm.getangle()) + (new Integer(array_y[3]).doubleValue()) * Math.cos(rm.getangle())).intValue()) + dy,
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
		}
		else if(atomimg != null)	
			g.drawImage(atomimg,array_x[0], array_y[0],sizex,sizey,null);
		return true;
	}
	
}
package graphic;

import java.awt.*;
import java.io.File;


/**
 * 
 * @author nakano
 * 描画用の膜を、画像オブジェクト（アトム？）にする
 */
public class GraphicAtoms{
	public String name = null;
	boolean enable;
	int posx = 0;
	int posy = 0;
	int sizex = 0;
	int sizey = 0;
	int sequence = 0;
	private int color_r = 0;
	private int color_g = 0;
	private int color_b = 0;
	Image atomimg = null;
	public String atomobj = null;
	
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
	
	public boolean drawatom(Graphics g){
		if(atomimg == null & atomobj == null){
			return false;
		}
		if(atomobj != null){
			g.setColor(new Color(color_r, color_g, color_b));
			if(atomobj.equals("circle") || atomobj.equals("oval"))
				g.drawOval(posx, posy, sizex, sizey);
			else if (atomobj.equals("rect"))
				g.drawRect(posx, posy, sizex, sizey);
			else if (atomobj.equals("line"))
				g.drawLine(posx, posy, sizex, sizey);
		}
		else if(atomimg != null)	
			g.drawImage(atomimg,posx,posy,sizex,sizey,null);
		return true;
	}
	
}
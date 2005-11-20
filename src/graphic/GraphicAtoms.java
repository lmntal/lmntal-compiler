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
	Image atomimg = null;
	public String atomobj = null;
	
	public GraphicAtoms(){
		enable = false;
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
			g.setColor(Color.BLACK);
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
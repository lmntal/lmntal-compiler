package graphic;

import java.awt.*;

public class GraphicAtoms{
	String name = null;
	boolean enable;
	int posx = -1;
	int posy = -1;
	int sequence = 0;
	Image atomimg = null;
	
	public GraphicAtoms(){
		enable = false;
	}
	
	public boolean SetPic(String filename){
		if(filename == null || filename ==""){
			return false;
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Imageオブジェクトの生成
		atomimg = toolkit.getImage(filename);
		return true;
	}
	
	public boolean drawatom(Graphics g){
		if(posx < 0 || posy < 0 || atomimg == null){
			return false;
		}
			
		g.drawImage(atomimg,posx,posy,null);
		return true;
	}
	
}
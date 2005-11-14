package graphic;

import java.awt.*;

public class GraphicAtoms{
	String name = null;
	boolean enable;
	int posx = -1;
	int posy = -1;
	Image atomimg = null;
	
	public GraphicAtoms(){
		enable = false;
	}
	
	public boolean SetPic(String filename){
		if(filename == null || filename ==""){

			System.out.print("nostring");
			return false;
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Imageオブジェクトの生成
		atomimg = toolkit.getImage(filename);
		return true;
	}
	
	public boolean drawatom(Graphics g){
		if(posx < 0 || posy < 0 || name == null || atomimg == null){
			System.out.print("boo");
			return false;
		}
			
		g.drawImage(atomimg,posx,posy,null);
		return true;
	}
	
}
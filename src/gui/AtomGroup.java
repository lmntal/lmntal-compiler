package gui;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import runtime.Env;

public class AtomGroup {
	public  static final double margin = runtime.Env.atomSize * 1.5;
	public Vector atoms = new Vector();
	
	public AtomGroup(Vector a){
		atoms = a;
	}
	
	public int size(){
		return atoms.size();
	}
	
	public DoublePoint getMaxPos(){
		double x,y;
		DoublePoint p;
		Node node;
		Iterator it = atoms.iterator();
		
		do{
			node = (Node)it.next();	
		} while (!node.isVisible() && it.hasNext());
		
		p = node.getPosition();
		x = p.x;
		y = p.y;
		
		while(it.hasNext()){
			node = (Node)it.next();
			if(node.isVisible()){
				p = node.getPosition();
				if(p.x > x)x = p.x;
				if(p.y > y)y = p.y;
			}
			
		}
		
		return new DoublePoint(x,y);
	}
	
	public DoublePoint getMinPos(){
		double x,y;
		DoublePoint p;
		Node node;
		Iterator it = atoms.iterator();
		
		do{
			node = (Node)it.next();	
		} while (!node.isVisible() && it.hasNext());
		
		p = node.getPosition();
		x = p.x;
		y = p.y;
		
		while(it.hasNext()){
			node = (Node)it.next();
			if(node.isVisible()){
				p = node.getPosition();
				if(p.x < x)x = p.x;
				if(p.y < y)y = p.y;
			}
			
		}
		
		return new DoublePoint(x,y);
	}
	
	public DoublePoint getCenterPos(){
		double x,y;
		x = (getMaxPos().x + getMinPos().x)/2;
		y = (getMaxPos().y + getMinPos().y)/2;
		return new DoublePoint(x,y);
	}
	
	public boolean doesOverLap(Rectangle2D.Double a){
		DoublePoint max,min,maxa,mina;
		double mg = Env.atomSize;
		boolean a1,a2,a3,a4,b1,b2,b3,b4;

		max = getMaxPos();min = getMinPos();
		maxa = new DoublePoint(a.getMaxX(),a.getMaxY());mina = new DoublePoint(a.getMinX(),a.getMinY());
		
		//マージンの分だけmax,minの座標をずらす
		max.x += mg;max.y += mg;
		min.x -= mg;min.y -= mg;
		/*maxa.x += mg;maxa.y += mg;
		mina.x -= mg;mina.y -= mg;*/
		
		if(max.x >= mina.x && max.x <= maxa.x)a1 = true;else a1 =false;
		if(min.x >= mina.x && min.x <= maxa.x)a2 = true;else a2 =false;
		if(max.y >= mina.y && max.y <= maxa.y)a3 = true;else a3 =false;
		if(min.y >= mina.y && min.y <= maxa.y)a4 = true;else a4 =false;
		if((a1||a2)&&(a3||a4))return true;
		
		if(maxa.x >= min.x && maxa.x <= max.x)b1 = true;else b1 =false;
		if(mina.x >= min.x && mina.x <= max.x)b2 = true;else b2 =false;
		if(maxa.y >= min.y && maxa.y <= max.y)b3 = true;else b3 =false;
		if(mina.y >= min.y && mina.y <= max.y)b4 = true;else b4 =false;
		if((b1||b2)&&(b3||b4))return true;
		
		if((a1||a2)&&(b3||b4))return true;
		if((b1||b2)&&(a3||a4))return true;
		
		return false;		
		
	}
	public boolean doesOverLap(AtomGroup a){
		DoublePoint max,min,maxa,mina;
		boolean a1,a2,a3,a4,b1,b2,b3,b4;

		max = getMaxPos();min = getMinPos();
		maxa = a.getMaxPos();mina = a.getMinPos();
		
		max.x += margin;max.y += margin;
		min.x -= margin;min.y -= margin;
		maxa.x += margin;maxa.y += margin;
		mina.x -= margin;mina.y -= margin;
		
		if(max.x >= mina.x && max.x <= maxa.x)a1 = true;else a1 =false;
		if(min.x >= mina.x && min.x <= maxa.x)a2 = true;else a2 =false;
		if(max.y >= mina.y && max.y <= maxa.y)a3 = true;else a3 =false;
		if(min.y >= mina.y && min.y <= maxa.y)a4 = true;else a4 =false;
		if((a1||a2)&&(a3||a4))return true;
		
		if(maxa.x >= min.x && maxa.x <= max.x)b1 = true;else b1 =false;
		if(mina.x >= min.x && mina.x <= max.x)b2 = true;else b2 =false;
		if(maxa.y >= min.y && maxa.y <= max.y)b3 = true;else b3 =false;
		if(mina.y >= min.y && mina.y <= max.y)b4 = true;else b4 =false;
		if((b1||b2)&&(b3||b4))return true;
		
		if((a1||a2)&&(b3||b4))return true;
		if((b1||b2)&&(a3||a4))return true;
		
		return false;
	}
	
	/**
	 * グループ同士の反発する力を計算する。
	 */
	 public double repulsiveForce(AtomGroup a){
			DoublePoint max,min,maxa,mina;
			boolean a1,a2,a3,a4,b1,b2,b3,b4;
			double f,f1,f2,l;
			double d = 0;
			f = 0.0;
			f1 = 1.6;
			f2 = 6.0;
			l = margin * 1.5;
			
			max = getMaxPos();min = getMinPos();
			maxa = a.getMaxPos();mina = a.getMinPos();
			
			max.x += margin;max.y += margin;
			min.x -= margin;min.y -= margin;
			maxa.x += margin;maxa.y += margin;
			mina.x -= margin;mina.y -= margin;
			
			if(max.x >= mina.x - l && max.x <= maxa.x + l)a1 = true;else a1 =false;
			if(min.x >= mina.x - l && min.x <= maxa.x + l)a2 = true;else a2 =false;
			if(max.y >= mina.y - l && max.y <= maxa.y + l)a3 = true;else a3 =false;
			if(min.y >= mina.y - l && min.y <= maxa.y + l)a4 = true;else a4 =false;

			if(maxa.x >= min.x - l && maxa.x <= max.x + l)b1 = true;else b1 =false;
			if(mina.x >= min.x - l && mina.x <= max.x + l)b2 = true;else b2 =false;
			if(maxa.y >= min.y - l && maxa.y <= max.y + l)b3 = true;else b3 =false;
			if(mina.y >= min.y - l && mina.y <= max.y + l)b4 = true;else b4 =false;

			
			if((a1&&a2&&a3&&a4)||(b1&&b2&&b3&&b4))return f2;
			if(((a1&&a2)&&(a3||a4))||((b1&&b2)&&(b3||b4))){
				if(a1&&a2){
					if(a3){
						d =  mina.y - max.y;
					} else if(a4){
						d =  min.y - maxa.y;
					}
				} else if(b1&&b2){
					if(b3){
						d = min.y - maxa .y;
					} else if(b4){
						d = mina .y - max.y;
					}
				}
			} else if(((a1||a2)&&(a3&&a4))||((b1||b2)&&(b3&&b4))){
				if(a3&&a4){
					if(a1){
						d = mina.x - max.x;
					} else if(a2){
						d = min.x - maxa.x;
					}
				} else if(b3&&b4){
					if(b1){
						d = min.x - maxa.x;
					} else if(b2){
						d = mina.x - max.x;
					}
				}
			} else if(((a1||a2)&&(a3||a4))||((b1||b2)&&(b3||b4))){
				double dtemp;
				if(a1){
					if(a3){
						d = mina.x - max.x;
						dtemp = mina.y - max.y;
						if(dtemp > d)d = dtemp;
					} else if(a4){
						d = mina.x - max.x;
						dtemp = min.y - maxa.y;
						if(dtemp > d)d = dtemp;
					}
				} else if(a2){
					if(a3){
						d = min.x - maxa.x;
						dtemp = mina.y - max.y;
						if(dtemp > d)d = dtemp;
					} else if(a4){
						d = min.x -maxa.x;
						dtemp = min.y - maxa.y;
						if(dtemp > d)d = dtemp;
					}
				}
			} else if(((a1||a2)&&(b3||b4))||((b1||b2)&&(a3||a4))){
				return f2;
			}
			
			return repulsiveForcef1(d,f1,f2);	

	 }
	 
	 double repulsiveForcef1(double x,double f1,double f2){
	 	double f;
	 	x = x / 15;
		if(x+f1 <= 0){
			return f2;
		}
		f = f1 / (x + f1);
		if(f > f2){
			return f2;
		}
	 	return f;
	 }
	
	/**各ノードをグループの中心を軸にある程度回転させ、グループの高さが少なくなればその座標に設定し、
	 * 少なくならない場合は元の値のままにする。
	 * @return
	 */
	public boolean rotateAtomGroup(){
		LinkedList a;
		Point2D.Double[] b;
		DoublePoint c;
		Point2D.Double p;
		double height;
		Rectangle2D.Double rect;
		
		DoublePoint d = getCenterPos();
		AffineTransform atr = AffineTransform.getRotateInstance(Math.PI/72,d.x,d.y);
		AffineTransform atl = AffineTransform.getRotateInstance(-Math.PI/36,d.x,d.y);
		
		a = new LinkedList(atoms);
		b = new Point2D.Double[a.size()];
		
		int i = 0;
		Iterator it = a.iterator();
		while(it.hasNext()){
			c = ((Node)it.next()).getPosition();
			b[i] = new Point2D.Double(c.x,c.y);
			i++;
		}
		
		rect =  new Rectangle2D.Double();
		
		for(i = 0;i < a.size();i++){
			rect.add(b[i]);
		}
		
		//左に回転して高さが小さくなっていればその値をセットする。
		height = rect.getMaxY() - rect.getMinY();
		atr.transform(b,0,b,0,a.size());
		
		rect = new Rectangle2D.Double();
		for(i = 0;i < a.size();i++){
			rect.add(b[i]);
		}
		
		if(height > (rect.getMaxY() - rect.getMinY())){
			it = a.iterator();
			i = 0;
			while(it.hasNext()){
				c = new DoublePoint(b[i].x,b[i].y);
				((Node)it.next()).setPosition(c);
				i++;
			}
			return true;
		}
		
		//右に回転して高さが小さくなっていれば値をセットする。
		atl.transform(b,0,b,0,a.size());
		rect = new Rectangle2D.Double();
		for(i = 0;i < a.size();i++){
			rect.add(b[i]);
		}
		
		if(height > (rect.getMaxY() - rect.getMinY())){
			it = a.iterator();
			i = 0;
			while(it.hasNext()){
				c = new DoublePoint(b[i].x,b[i].y);
				((Node)it.next()).setPosition(c);
				i++;
			}
			return true;
		}
		
		//左右どちらに回転しても高さが小さくならなかった場合、元に戻して false を返す。
		atr.transform(b,0,b,0,a.size());
		
		it = a.iterator();
		i = 0;
		while(it.hasNext()){
			c = new DoublePoint(b[i].x,b[i].y);
			((Node)it.next()).setPosition(c);
			i++;
		}
		return true;
		
	}
	
}
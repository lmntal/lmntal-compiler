import runtime.*;
import java.util.*;
/*inline_define*/
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FiveStoneFrame extends JFrame implements MouseListener{
    final Membrane mem;
    volatile boolean flgWait;
    int gameState;
    final int screen_w=500;
    final int screen_h=500;
    final int begin_x = 25;
    final int begin_y = 25+25;
    final int stage_w = 19;
    final int stage_h = 19;
    final int box_w=25;
    final int box_h=25;
    final int stone_w = 24;
    final int stone_h = 24;
    final int space_top = 25;
    final int space_left= 0;
    final int EMPTY = 0;
    final int BLACK = 1;
    final int WHITE = 2;

    int cursorX;
    int cursorY;
    LinkedList stones;
    LinkedList lineStones;
    public FiveStoneFrame(Membrane targetMem){
        this.mem = targetMem;
        setTitle("5 stone pazzle");
        setBounds(100,100,screen_w+space_left,screen_h+space_top);
        addMouseListener(this);
        setVisible(true);
        setResizable(false);
        flgWait=true;
        cursorX=0;
        cursorY=0;
        stones=new LinkedList();
        lineStones=new LinkedList();
        gameState=0;
    }
    public void paint(Graphics g){
        drawStage(g);
        Object[] stonesArray=stones.toArray();
        System.out.println("paint");
        for(int i=0;i<stonesArray.length;i++){
            drawStone(false,
                      ((Stone)stonesArray[i]).color,
                      ((Stone)stonesArray[i]).x,
                      ((Stone)stonesArray[i]).y,g);
        }
        if(gameState>0){
            Object[] lineStonesArray=lineStones.toArray();
            for(int i=0;i<lineStonesArray.length;i++){
                drawStone(true,
                      ((Stone)lineStonesArray[i]).color,
                      ((Stone)lineStonesArray[i]).x,
                      ((Stone)lineStonesArray[i]).y,g);
            }
        }
    }
    Image img;
    Graphics gr;
    public void update(Graphics g){
        if(img==null){
            img=createImage(screen_w+space_left,screen_h+space_top);
            gr=img.getGraphics();
        }
        paint(gr);
        g.drawImage(img,0,0,this);
    }

    public void drawStone(boolean on_line,int color,int x,int y,Graphics g){
        if(color==EMPTY)return;
        if(on_line){
            g.setColor(Color.red);
            g.fillOval(begin_x+box_w*x-stone_w/2-1,
                       begin_y+box_h*y-stone_h/2-1,
                       stone_w,stone_h);
        }
        g.setColor(Color.black);
        g.fillOval(begin_x+box_w*x-stone_w/2,
                   begin_y+box_h*y-stone_h/2,
                   stone_w-2,stone_h-2);
        g.setColor((color==BLACK)?Color.black:Color.white);
        g.fillOval(begin_x+box_w*x-stone_w/2+1,
                   begin_y+box_h*y-stone_h/2+1,
                   stone_w-4,stone_h-4);
    }

    public void drawStage(Graphics g){
        g.setColor(new Color(255,240,130));
        g.fillRect(0,0,begin_x+(stage_w-1)*box_w+begin_x,
                       begin_y+(stage_h-1)*box_h+begin_y);
        g.setColor(Color.black);
        for(int i=0;i<stage_w;i++){
            g.drawLine(begin_x+i*box_w,begin_y,
                       begin_x+i*box_w,begin_y+(stage_h-1)*box_h);
        }
        for(int i=0;i<stage_h;i++){
            g.drawLine(begin_x,begin_y+i*box_h,
                       begin_x+(stage_w-1)*box_w,begin_y+i*box_h);
        }
    }

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
       System.out.println("mouse clicked");
       switch(e.getButton()){
       case MouseEvent.BUTTON1:
           flgWait=false;
           int x=((e.getX()+stone_w/2-begin_x) / box_w);
           int y=((e.getY()+stone_h/2-begin_y) / box_h);
           if(x>=0 && x<stage_w && y>=0 && y<stage_h && gameState==0){
               Object[] stonesArray = stones.toArray();
               boolean flgPresented=false;
               for(int i=0;i<stonesArray.length;i++){
                   if(((Stone)stonesArray[i]).x==x &&
                       ((Stone)stonesArray[i]).y==y){
                       flgPresented=true;
                       break;
                   }
               }
               if(flgPresented){
                   mem.newAtom(new Functor("wait",0));
               }else{
                   mem.asyncLock();
                   Atom ax=mem.newAtom(new IntegerFunctor(x));
                   Atom ay=mem.newAtom(new IntegerFunctor(y));
                   Atom input=mem.newAtom(new Functor("input",2));
                   mem.newLink(input,0,ax,0);
                   mem.newLink(input,1,ay,0);
                   mem.asyncUnlock();
                   System.out.println("input atom created.");
               }
           }else{
               mem.newAtom(new Functor("wait",0));
           }
           break;
       case MouseEvent.BUTTON3:
           flgWait=false;
           mem.newAtom(new Functor("end",0));
           break;
       }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    public void waiting(){
        flgWait=true;
        repaint();
        while(flgWait){
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){}
        }
        System.out.println("loop out");
    }

    public void addStone(int x,int y,int color){
        System.out.println("add stone : "+ x + ","+y+":"+color);
        stones.add(new Stone(x,y,color));
    }
    
    public void addLineStone(int x,int y,int color){
        System.out.println("add line stone : "+x + ","+y+":"+color);
        lineStones.add(new Stone(x,y,color));
    }
    
    public void clearStones(){
        System.out.println("clear");
        stones.clear();
    }
    
    //public void redraw(){
    //    System.out.println("repait");
    //    repaint();
    //}
    
    public void setWinner(int winner){
        System.out.println("win");
        gameState=winner;
    }

}

class Stone{
    public int x,y,color;
    public Stone(int fx,int fy,int fc){
        x=fx;y=fy;color=fc;
    }
}

public class SomeInlineCode5mokunarabe implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCode5mokunarabeCustomGuardImpl";

			CustomGuard cg=(CustomGuard)Class.forName(name).newInstance();

			if(cg==null) throw new GuardNotFoundException();

			return cg.run(guardID, mem, obj);

		} catch(GuardNotFoundException e) {
			throw new GuardNotFoundException();

		} catch(ClassNotFoundException e) {
		} catch(InstantiationException e) {
		} catch(IllegalAccessException e) {
		} catch(Exception e) {

			e.printStackTrace();

		}

		throw new GuardNotFoundException();

	}
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 4: {
			/*inline*/
    int x=((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
    int y=((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
    int c=((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(3).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    
    frame.addStone(x,y,c);
    me.nthAtom(0).remove();
    me.nthAtom(1).remove();
    me.nthAtom(2).remove();
    me.nthAtom(3).remove();
    me.remove();
    
			break; }
		case 3: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    frame.setVisible(false);
    System.out.println("end");
    frame.dispose();
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);

			break; }
		case 6: {
			/*inline*/
    int x=((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
    int y=((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
    int c=((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(3).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    frame.addLineStone(x,y,c);
    me.nthAtom(0).remove();
    me.nthAtom(1).remove();
    me.nthAtom(2).remove();
    me.nthAtom(3).remove();
    me.remove();
    
			break; }
		case 1: {
			/*inline*/
    FiveStoneFrame frame = new FiveStoneFrame((Membrane)mem);
    Atom a= mem.newAtom(new Functor("frame",1));
    Atom b= mem.newAtom(new ObjectFunctor(frame));
    mem.newLink(a,0,b,0);
    mem.removeAtom(me);
    mem.makePerpetual();

			break; }
		case 7: {
			/*inline*/
    int x=((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
    int y=((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
    int c=((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(3).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    frame.addStone(x,y,c);
    me.nthAtom(0).remove();
    me.nthAtom(1).remove();
    me.nthAtom(2).remove();
    me.nthAtom(3).remove();
    me.remove();
    
			break; }
		case 0: {
			/*inline*/
    int x1=((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
    int x2=((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
    int y1=((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();
    int y2=((IntegerFunctor)me.nthAtom(3).getFunctor()).intValue();
    int hn1=((IntegerFunctor)me.nthAtom(4).getFunctor()).intValue();
    int hn2=((IntegerFunctor)me.nthAtom(5).getFunctor()).intValue();
    System.out.println("link ("+ x1+","+y1+"):"+hn1+",("+x2+","+y2+"):"+hn2+".");
    me.nthAtom(0).remove();
    me.nthAtom(1).remove();
    me.nthAtom(2).remove();
    me.nthAtom(3).remove();
    me.nthAtom(4).remove();
    me.nthAtom(5).remove();
    me.remove();
  
			break; }
		case 8: {
			/*inline*/
    ObjectFunctor framefunc=(ObjectFunctor)me.nthAtom(0).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    //synchronized(frame){
    //    frame.redraw();
    //}
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		case 5: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    int winner=((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
    frame.clearStones();
    frame.setWinner(winner);
    me.nthAtom(0).remove();
    me.nthAtom(1).remove();
    me.remove();
    
			break; }
		case 2: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    FiveStoneFrame frame = (FiveStoneFrame)framefunc.getObject();
    frame.waiting();
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);
  
			break; }
		}
	}
}

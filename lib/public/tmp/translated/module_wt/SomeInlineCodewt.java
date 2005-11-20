package translated.module_wt;
import runtime.*;
import java.util.*;
/*inline_define*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class LMNtalFrame extends JFrame{
  final Membrane mem;

  public LMNtalFrame(Membrane targetMem){
    this.mem = targetMem;
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        mem.asyncLock();
        mem.newAtom(new Functor("terminate",0));
        mem.asyncUnlock();
      }
    });
  }
}

class PriorityQueue{
  ArrayList list = new ArrayList();
  
  public int insert(int priority){
    int i;
    for(i=0;i<list.size()&&priority>((Integer)list.get(i)).intValue();i++);
    list.add(i,new Integer(priority));
    return(i);
  }
}

public class SomeInlineCodewt {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    int w = Integer.parseInt(me.nth(1));
    int h = Integer.parseInt(me.nth(2));
    frame.setSize(w,h);
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me.nthAtom(1));
    mem.removeAtom(me.nthAtom(2));
    mem.removeAtom(me);
    frame.setVisible(true); 
  
			break; }
		case 4: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    Panel border = new Panel(new BorderLayout());
    frame.getContentPane().add(border);
    Atom a = mem.newAtom(new Functor("border",1));
    Atom b = mem.newAtom(new ObjectFunctor(border));
    mem.newLink(a,0,b,0);
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);
  
			break; }
		case 7: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    frame.setVisible(false);
    frame.dispose();
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);
  
			break; }
		case 5: {
			/*inline*/
        final Membrane memb = (Membrane)mem;
        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
        Panel grid = (Panel)panelfunc.getObject();
        ObjectFunctor queuefunc = (ObjectFunctor)me.nthAtom(1).getFunctor();
        PriorityQueue pQueue = (PriorityQueue)queuefunc.getObject();
        String title = me.nthAtom(2).toString();
        int location = Integer.parseInt(me.nth(3));
        final String event = me.nthAtom(4).toString();
        Button bt = new Button(title);
        bt.setVisible(true);
        bt.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            memb.asyncLock();
            memb.newAtom(new Functor(event,0));
            memb.asyncUnlock();
          }
        });
        int index = pQueue.insert(location);
        grid.add(bt,index);
        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(5).getFunctor();
        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
        frame.setVisible(true);
        mem.removeAtom(me.nthAtom(0));
        mem.removeAtom(me.nthAtom(1));
        mem.removeAtom(me.nthAtom(2));
        mem.removeAtom(me.nthAtom(3));
        mem.removeAtom(me.nthAtom(4));
        mem.removeAtom(me.nthAtom(5));
        mem.removeAtom(me); 
      
			break; }
		case 6: {
			/*inline*/
        final Membrane memb = (Membrane)mem;
        ObjectFunctor panelfunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
        Panel border = (Panel)panelfunc.getObject();
        String title = me.nthAtom(1).toString();
        String location = me.nthAtom(2).toString();
        final String event = me.nthAtom(3).toString();
        Button bt = new Button(title);
        bt.setVisible(true);
        bt.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            memb.asyncLock();
            memb.newAtom(new Functor(event,0));
            memb.asyncUnlock();
          }
        });
        border.add(location,bt);
        ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(4).getFunctor();
        LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
        frame.setVisible(true);
        mem.removeAtom(me.nthAtom(0));
        mem.removeAtom(me.nthAtom(1));
        mem.removeAtom(me.nthAtom(2));
        mem.removeAtom(me.nthAtom(3));
        mem.removeAtom(me.nthAtom(4));
        mem.removeAtom(me); 
      
			break; }
		case 3: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    int rows = Integer.parseInt(me.nth(1));
    int cols = Integer.parseInt(me.nth(2));
    Panel grid = new Panel(new GridLayout(rows,cols));
    frame.getContentPane().add(grid);
    PriorityQueue pQueue = new PriorityQueue();
    Atom a = mem.newAtom(new Functor("grid",2));
    Atom b = mem.newAtom(new ObjectFunctor(grid));
    Atom c = mem.newAtom(new ObjectFunctor(pQueue));
    mem.newLink(a,0,b,0);
    mem.newLink(a,1,c,0);
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me.nthAtom(1));
    mem.removeAtom(me.nthAtom(2));
    mem.removeAtom(me); 
  
			break; }
		case 2: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    String title = me.nthAtom(1).toString();
    frame.setTitle(title);
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me.nthAtom(1));
    mem.removeAtom(me);
  
			break; }
		case 0: {
			/*inline*/
    LMNtalFrame frame = new LMNtalFrame((Membrane)mem);
    Atom a = mem.newAtom(new Functor("frame",1));
    Atom b = mem.newAtom(new ObjectFunctor(frame));
    mem.newLink(a,0,b,0);
    mem.removeAtom(me);
    mem.makePerpetual();
  
			break; }
		}
	}
}

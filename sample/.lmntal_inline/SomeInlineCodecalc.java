import runtime.*;
import java.util.*;
/*inline_define*/
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DigitButton    extends JButton { DigitButton(   String s){super(s);} }
class OperatorButton extends JButton { OperatorButton(String s){super(s);} }
class ControlButton  extends JButton { ControlButton( String s){super(s);} }

class LMNtalFrame extends JFrame implements ActionListener {
  final Membrane mem;
  JLabel screen;
  Container mainPanel;
  public LMNtalFrame(Membrane targetMem) {
    this.mem = targetMem;
    setTitle("LMNtal Calc");
//  setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e) {
        mem.asyncLock();
        mem.newAtom(new Functor("terminate",0));
        mem.asyncUnlock();
      }
    });
    mainPanel = getContentPane();
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    GridBagConstraints c2 = (GridBagConstraints)c.clone();
    c2.gridwidth = GridBagConstraints.REMAINDER;
    screen = new JLabel();
    screen.setHorizontalAlignment(JLabel.RIGHT);
    addComponent(screen,c2);
    makeDigitBtn(7,c); makeDigitBtn(8,c); makeDigitBtn(9,c); makeOpBtn("/",c2);
    makeDigitBtn(4,c); makeDigitBtn(5,c); makeDigitBtn(6,c); makeOpBtn("*",c2);
    makeDigitBtn(1,c); makeDigitBtn(2,c); makeDigitBtn(3,c); makeOpBtn("-",c2);
    makeDigitBtn(0,c); makeCtlBtn(".",c); makeOpBtn("=",c);  makeOpBtn("+",c2);
    makeCtlBtn("AC",c); makeCtlBtn("off",c);
    pack();
    setSize(400,300);
    setVisible(true);
  }
  public void addComponent(Component component, GridBagConstraints c) {
    mainPanel.add(component);
    component.setFont(new Font("SansSerif",Font.PLAIN,24));
    ((GridBagLayout)mainPanel.getLayout()).setConstraints(component, c);
  }
  public void makeDigitBtn(int digit, GridBagConstraints c) {
    JButton btn = new DigitButton(""+digit);
    addComponent(btn, c);
    btn.addActionListener(this);
  }
  public void makeOpBtn(String text, GridBagConstraints c) {
    JButton btn = new OperatorButton(text);
    addComponent(btn, c);
    btn.addActionListener(this);
  }
  public void makeCtlBtn(String text, GridBagConstraints c) {
    JButton btn = new ControlButton(text);
    addComponent(btn, c);
    btn.addActionListener(this);
  }
  public void actionPerformed(ActionEvent e) {
    String text = e.getActionCommand();
    if (e.getSource() instanceof DigitButton) {
      dispatchCommand("digit", new IntegerFunctor(Integer.parseInt(text)));
    }
    else if (e.getSource() instanceof OperatorButton) {
      dispatchCommand("operator", new StringFunctor(text));
    }
    else {
      dispatchCommand("control", new Functor(text.toLowerCase(),1));
    }
  }
  protected void dispatchCommand(String name, Functor arg) {
    Functor f1 = new Functor("cmd",2);
    Functor f2 = new Functor(name,1);
    mem.asyncLock();
    Atom a = mem.newAtom(f1);
    Atom b = mem.newAtom(f2);
    Atom c = mem.newAtom(arg);
    mem.newLink(a,0,b,0);
    mem.newLink(a,1,c,0);
    mem.asyncUnlock();
  }
  public void update() {
    Iterator it = mem.atomIteratorOfFunctor(new Functor("screen",1));
    if (it.hasNext()) {
      String text = ((Atom)it.next()).nthAtom(0).getFunctor().getName();
      text = text.replaceAll("[.]0$","");
      screen.setText(text);
    }
  }
}

public class SomeInlineCodecalc implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
//    Membrane mem = (Membrane)me.getMem();
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
    frame.update();
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);
  
			break; }
		case 0: {
			/*inline*/
//    Membrane mem = (Membrane)me.getMem();
    LMNtalFrame frame = new LMNtalFrame((Membrane)mem);
    Atom a = mem.newAtom(new Functor("frame",1));
    Atom b = mem.newAtom(new ObjectFunctor(frame));
    mem.newLink(a,0,b,0);
    mem.removeAtom(me);
    mem.makePerpetual();
  
			break; }
		case 2: {
			/*inline*/
    ObjectFunctor framefunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    LMNtalFrame frame = (LMNtalFrame)framefunc.getObject();
//    Membrane mem = (Membrane)me.getMem();
    frame.setVisible(false);
    frame.dispose();
    mem.removeAtom(me.nthAtom(0));
    mem.removeAtom(me);
  
			break; }
		}
	}
}

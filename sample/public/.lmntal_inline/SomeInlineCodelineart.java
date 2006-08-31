import runtime.*;
import java.util.*;
/*inline_define*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

class TimerListener implements ActionListener {
	Atom me;
	private static final Functor MOVE = new Functor("move", 1);
	private static final Functor WAIT = new Functor("wait", 1);
	public void actionPerformed(ActionEvent e) {
		Membrane mem = me.getMem();
		mem.asyncLock();
		Atom wait = me.nthAtom(0);
		if (wait.getFunctor().equals(WAIT)) {
			Atom move = mem.newAtom(MOVE);
			mem.newLink(me, 0, move, 0);
			mem.removeAtom(wait);
		}
		mem.asyncUnlock();
	}
}
class LineArtFrame extends Frame implements WindowListener {
	private final Membrane mem;
	Atom me;
	private Canvas canvas;
	LineArtFrame(Membrane m, int width, int height, Color backColor) {
		mem = m;

		addWindowListener(this);
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setBackground(backColor);
		add(canvas);
		pack();
		show();
	}
	void drawLine(int x1, int y1, int x2, int y2, Color c) {
		Graphics graphics = canvas.getGraphics();
		graphics.setColor(c);
		graphics.drawLine(x1, y1, x2, y2);
		graphics.dispose();
	}
	public void windowClosing(WindowEvent e) {
		mem.asyncLock();
		//LMntal側からFrameをdisposeしようとすると、デッドロックする。(もしかしたら嘘かも)
		this.dispose();
		mem.newAtom(new Functor("exit", 0));
		Atom frame = me.nthAtom(0);
		mem.removeAtom(frame);
		mem.removeAtom(me);
		mem.asyncUnlock();
	}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
public class SomeInlineCodelineart implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodelineartCustomGuardImpl";

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
		Membrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
		Atom af = me.nthAtom(0);
		Atom ax1 = me.nthAtom(1);
		Atom ay1 = me.nthAtom(2);
		Atom ax2 = me.nthAtom(3);
		Atom ay2 = me.nthAtom(4);
		Atom ar = me.nthAtom(5);
		Atom ag = me.nthAtom(6);
		Atom ab = me.nthAtom(7);
		LineArtFrame frame = (LineArtFrame)((ObjectFunctor)af.getFunctor()).getObject();
		int x1 = ((IntegerFunctor)ax1.getFunctor()).intValue();
		int y1 = ((IntegerFunctor)ay1.getFunctor()).intValue();
		int x2 = ((IntegerFunctor)ax2.getFunctor()).intValue();
		int y2 = ((IntegerFunctor)ay2.getFunctor()).intValue();
		int r = ((IntegerFunctor)ar.getFunctor()).intValue();
		int g = ((IntegerFunctor)ag.getFunctor()).intValue();
		int b = ((IntegerFunctor)ab.getFunctor()).intValue();
		frame.drawLine(x1, y1, x2, y2, new Color(r, g, b));
		mem.removeAtom(ax1);
		mem.removeAtom(ay1);
		mem.removeAtom(ax2);
		mem.removeAtom(ay2);
		mem.removeAtom(ar);
		mem.removeAtom(ag);
		mem.removeAtom(ab);
		Atom a = mem.newAtom(new Functor("frame", 1));
		mem.relinkAtomArgs(a, 0, me, 0);
		mem.removeAtom(me);
	
			break; }
		case 2: {
			/*inline*/
		Atom a = me.nthAtom(0);
		int delay = ((IntegerFunctor)a.getFunctor()).intValue();
		TimerListener listener = new TimerListener();
		Timer t = new Timer(delay, listener);
		t.start();
		Atom at = mem.newAtom(new ObjectFunctor(t));
		listener.me = at;
		mem.relinkAtomArgs(at, 0, me, 1);
		mem.removeAtom(a);
		mem.removeAtom(me);
	
			break; }
		case 0: {
			/*inline*/
		Atom w = me.nthAtom(0), h = me.nthAtom(1);
		Atom r = me.nthAtom(2), g = me.nthAtom(3), b = me.nthAtom(4);
		int width = ((IntegerFunctor)w.getFunctor()).intValue();
		int height = ((IntegerFunctor)h.getFunctor()).intValue();
		int red = ((IntegerFunctor)r.getFunctor()).intValue();
		int green = ((IntegerFunctor)g.getFunctor()).intValue();
		int blue = ((IntegerFunctor)b.getFunctor()).intValue();
		LineArtFrame frame = new LineArtFrame(mem, width, height, new Color(red, blue, green));
		Atom a = mem.newAtom(new ObjectFunctor(frame));
		frame.me = a;
		mem.relinkAtomArgs(a, 0, me, 5);
		mem.removeAtom(w);
		mem.removeAtom(h);
		mem.removeAtom(r);
		mem.removeAtom(g);
		mem.removeAtom(b);
		mem.removeAtom(me);
	
			break; }
		case 3: {
			/*inline*/
		Atom a = me.nthAtom(0);
		Timer t = (Timer)((ObjectFunctor)a.getFunctor()).getObject();
		t.stop();
		mem.removeAtom(a);
		mem.removeAtom(me);
	
			break; }
		}
	}
}

package test.GUI;

import java.io.StringReader;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import compile.parser.*;
import compile.structure.*;

public class LMNGraphPanel extends GraphPanel {
	LMNtalFrame frame;
	Node movingNode;
	
	public LMNGraphPanel(LMNtalFrame f) {
		super();
		frame = f;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				//determine nearest node
				movingNode = getGraphLayout().getNearestNode(arg0.getPoint());
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent arg0) {
				if(movingNode==null) return;
				movingNode.setPosition(new DoublePoint(arg0.getPoint()));
			}

		}
		);
	}
	
	public void setSource(String src) throws ParseException {
		LMNParser lp = new LMNParser(new StringReader(src));
		Membrane mem = lp.parse();
		
		setMembrane(mem);
	}
	
	public void setMembrane(Membrane mem) {
		frame.busy = true;
		GraphLayout layout = getGraphLayout();
		layout.removeAllNodes();
		addMembrane(mem);
		layout.calc();
	}
	
	public void setMembrane(runtime.Membrane mem) {
		frame.busy = true;
		GraphLayout layout = getGraphLayout();
		layout.removeAllNodes();
		col=row=0;
		addMembrane(mem);
		layout.calc();
	}
	
	static int col, row=2;
	GraphNode node_of_atom(runtime.Atom atom, Map atoms) {
		GraphNode n = (GraphNode)atoms.get(atom);
		if(n==null) {
			Point p = new Point(30 + col * 0 + (int)(Math.random()*200), 60 + row * 0 + (int)(Math.random()*300));
//			Point p = new Point(30 + (int)(Math.random()*200) + (int)(Math.random()*3), 30 + (int)(Math.random()*600) + (int)(Math.random()*3));
//			Point p = new Point(150+(int)(Math.random()*20), 100+(int)(Math.random()*20));
			n = new GraphNode(atom.getName(), new DoublePoint(p));
			atoms.put(atom, n);
			getGraphLayout().addNode(n);
			if(col++ >10) { row++; col=0; }
		}
		return n;
	}
	
	GraphNode node_of_atom(Atom atom, Map atoms) {
		GraphNode n = (GraphNode)atoms.get(atom);
		if(n==null) {
			Point p = new Point(30 + atom.column * 20 + (int)(Math.random()*3), 30 + atom.line * 40 + (int)(Math.random()*3));
			n = new GraphNode(atom.functor.getName(), new DoublePoint(p));
			atoms.put(atom, n);
			getGraphLayout().addNode(n);
		}
		return n;
	}
	
	runtime.Atom buddy(runtime.Atom a, int i) {
		return a.nthAtom(i);
	}
	
	Atom buddy(Atom a, int i) {
		LinkOccurrence link = a.args[i];
		if(link.buddy != null) {
			return link.buddy.atom;
		}
		return null;
	}
	
	/**
	 * 膜を追加する
	 */
	public void addMembrane(runtime.Membrane mem) {
		Map atoms = new Hashtable();
		
		// atom
		Iterator it = mem.atomIterator();
		while(it.hasNext()) {
			runtime.Atom atom = (runtime.Atom)it.next();
			GraphNode node = node_of_atom(atom, atoms);
			for (int j=0;j<atom.getFunctor().getArity();j++) {
				GraphNode node_to = node_of_atom(buddy(atom, j), atoms);
				node.addLinkedNode(node_to);
			}
			atoms.put(atom, node);
		}
	}
	
	/**
	 * 膜を追加する
	 */
	public void addMembrane(Membrane mem) {
		Map atoms = new Hashtable();
		
		// atom
		Iterator it = mem.atoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			GraphNode node = node_of_atom(atom, atoms);
			for (int j=0;j<atom.args.length;j++) {
				GraphNode node_to = node_of_atom(buddy(atom, j), atoms);
				node.addLinkedNode(node_to);
			}
			atoms.put(atom, node);
		}
		
		// child mem
//		for (int i=0; i<mem.mems.size();i++) {
//			Membrane child = (Membrane)mem.mems.get(i);
//			Point p = new Point((int)(Math.random()*getPreferredArea().getWidth()), (int)(Math.random()*getPreferredArea().getHeight()));
//			GraphNode node = new GraphNode("Mem@"+child.hashCode() , p);
//			getGraphLayout().addNode(node);
//			for (Iterator it = child.freeLinks.keySet().iterator(); it.hasNext(); ) {
//				LinkOccurrence link = (LinkOccurrence)child.freeLinks.get(it.next());
//				if ((link.buddy != null)
//				 && (link.hashCode() > link.buddy.hashCode())) {
//					table_link.put(link,link);
//				 }
//			}
//			atoms.put(child, node);
//		}
	}
}

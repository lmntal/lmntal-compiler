package test.graph;

import java.io.StringReader;
import java.util.*;
import java.awt.*;

import compile.parser.*;
import compile.structure.*;

public class LMNGraphPanel extends GraphPanel {

	public LMNGraphPanel() {
		super();
			
	}
	
	public void setSource(String src) throws ParseException {
		LMNParser lp = new LMNParser(new StringReader(src));
		Membrane mem = lp.parse();
		
		setMembrane(mem);
	}
	
	public void setMembrane(Membrane mem) {
		GraphLayout layout = getGraphLayout();
		layout.removeAllNodes();
		layout.removeAllEdges();
		addMembrane(mem);
	}
	
	public void addMembrane(Membrane mem) {
		Hashtable table_atom = new Hashtable();
		Hashtable table_link = new Hashtable();
		
		// atom
		for (int i=0; i<mem.atoms.size();i++) {
			Atom atom = (Atom)mem.atoms.get(i);
			Point p = new Point(30 + atom.column * 20, 30 + atom.line * 40);
			GraphNode node = new GraphNode(atom.functor.getName() , p);
			getGraphLayout().addNode(node);
			for (int j=0;j<atom.args.length;j++) {
				LinkOccurrence link = atom.args[j];
				if ((link.buddy != null)
				 && (link.hashCode() > link.buddy.hashCode())) {
				 	table_link.put(link,link);
				 }
			}
			table_atom.put(atom, node);
		}
		
		// child mem
		for (int i=0; i<mem.mems.size();i++) {
			Membrane child = (Membrane)mem.mems.get(i);
			Point p = new Point((int)(Math.random()*getPreferredArea().getWidth()), (int)(Math.random()*getPreferredArea().getHeight()));
			GraphNode node = new GraphNode("Mem@"+child.hashCode() , p);
			getGraphLayout().addNode(node);
			for (Iterator it = child.freeLinks.keySet().iterator(); it.hasNext(); ) {
				LinkOccurrence link = (LinkOccurrence)child.freeLinks.get(it.next());
				if ((link.buddy != null)
				 && (link.hashCode() > link.buddy.hashCode())) {
					table_link.put(link,link);
				 }
			}
			table_atom.put(child, node);
		}
		
		Enumeration e = table_link.keys();
		while (e.hasMoreElements()) {
			LinkOccurrence link = (LinkOccurrence)e.nextElement();
			// atom
			GraphNode from = (GraphNode)table_atom.get(link.atom);
			GraphNode to = (GraphNode)table_atom.get(link.buddy.atom);
			// child
			if (from == null) from = (GraphNode)table_atom.get(link.atom.mem);
			if (to == null) to = (GraphNode)table_atom.get(link.buddy.atom.mem);

			GraphEdge edge = new GraphEdge(from, to);
			getGraphLayout().addEdge(edge);
		}
	}
}
package test.graph;

import compile.parser.*;
import compile.structure.*;
import java.io.StringReader;

public class LMNGraphPanel extends GraphPanel {

	public LMNGraphPanel() {
		super();
			
	}
	
	public void setSource(String src) throws ParseException {
		GraphLayout layout = getGraphLayout();
		layout.removeAllNodes();
		layout.removeAllNodes();
		
		LMNParser lp = new LMNParser(new StringReader(src));
		Membrane mem = lp.parse();
		
		addMembrane(mem);
	}
	
	protected void addMembrane(Membrane mem) {
		
	}
}
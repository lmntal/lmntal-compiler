package graphic;

import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import test.GUI.GraphPanel;
import test.GUI.Node;


public class LMNGraphPanel extends GraphPanel {
	LMNtalGFrame frame;
	Node movingNode;
	
	public LMNGraphPanel(LMNtalGFrame f) {
		super();
		frame = f;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
	}
}
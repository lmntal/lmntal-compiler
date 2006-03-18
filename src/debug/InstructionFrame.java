/*
 * ºîÀ®Æü: 2006/03/17
 */
package debug;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import runtime.Env;

public class InstructionFrame extends JFrame {
	JTextArea jt;
	
	public InstructionFrame() {
		super("Instruction");
		
		jt = new JTextArea();
		jt.setFont(new Font("Monospaced", Font.PLAIN, Env.fDEMO ? 16 : 12));
		JScrollPane scroll = new JScrollPane(jt);
		getContentPane().add("Center", scroll);
		
		setSize(400, 400);
	}
}

import runtime.*;
import java.util.*;
public class SomeInlineCodetest2 implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
        final Membrane memb = (Membrane)mem;
        addKeyListener(new KeyAdapter(){
	       	public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				memb.asyncLock();
	            memb.newAtom(new Functor("key",0));
	            memb.asyncUnlock();
	    
			}
        });
        //frame.setVisible(true);
        mem.removeAtom(me); 
      
			break; }
		}
	}
}

/*
 * ì¬“ú: 2003/10/21
 *
 */
package runtime;
import java.util.ArrayList;

/**
 * 1 ‚Â‚Ì–½—ß‚ğ•Û‚·‚éB
 * 
 * •¡”‚Å‚à\‚í‚È‚¢‚Æ‚¨‚à‚¤B
 * 
 * @author pa
 *
 */
public class Instruction {
	public ArrayList data = new ArrayList();
	
	public Instruction() {
		// ‚½‚Æ‚¦‚Î [react, [1, 2, 5]]
		ArrayList sl = new ArrayList();
		sl.add(new Integer(1));
		sl.add(new Integer(2));
		sl.add(new Integer(5));
		data.add("react");
		data.add(sl);
		System.out.println(data);
	}
}

import runtime.*;
public class MyInlineCode implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
	//String s = System.getProperty(query):
	java.util.Enumeration e = System.getProperties().keys();
	while(e.hasMoreElements()) {
		String o = (String)e.nextElement(); 
		System.out.println(o+"  =>  "+System.getProperty(o));
	}
	
			break; }
		}
	}
}

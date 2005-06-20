import runtime.*;
import java.util.*;
public class SomeInlineCodetest implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
         Runtime r2;
         r2 = Runtime.getRuntime();
         r2.gc();
         System.out.print("after  :");
         System.out.println(r2.totalMemory() - r2.freeMemory());
 
			break; }
		case 0: {
			/*inline*/
         Runtime r;
         r = Runtime.getRuntime();
         r.gc();
         System.out.print("before :");
         System.out.println(r.totalMemory() - r.freeMemory());
 
			break; }
		}
	}
}

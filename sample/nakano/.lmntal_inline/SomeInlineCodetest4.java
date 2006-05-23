import runtime.*;
import java.util.*;
public class SomeInlineCodetest4 implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodetest4CustomGuardImpl";

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
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
         Runtime r;
         r = Runtime.getRuntime();
         r.gc();
         System.out.print("before :");
         System.out.println(r.totalMemory() - r.freeMemory());
 
			break; }
		case 1: {
			/*inline*/
         Runtime r2;
         r2 = Runtime.getRuntime();
         r2.gc();
         System.out.print("after  :");
         System.out.println(r2.totalMemory() - r2.freeMemory());
 
			break; }
		}
	}
}

package translated.module_nd;
import runtime.*;
import java.util.*;
public class SomeInlineCodend {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodendCustomGuardImpl";

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
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
			Atom out = me.nthAtom(0);
			Atom in = out.nthAtom(0);
			Atom plus = in.nthAtom(1);
			Membrane mem1 = (Membrane)in.getMem();
			Iterator it = mem1.memIterator();
			Membrane mem2 = (Membrane)it.next();
			((Task)mem.getTask()).nondeterministicExec(mem2);
			mem.removeAtom(me);
			mem.removeAtom(out);
			mem1.removeAtom(in);
			plus.dequeue();
			mem1.removeAtom(plus);
		
			break; }
		}
	}
}

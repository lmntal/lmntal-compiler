import runtime.*;
import java.util.*;
public class SomeInlineCodetestmax implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodetestmaxCustomGuardImpl";

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
		Membrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
		Atom result = mem.newAtom(new SymbolFunctor("a", 1));
		System.out.println(me.nthAtom(0));
		mem.relink(result, 0, me, 1);
		me.remove();
	
			break; }
		}
	}
}

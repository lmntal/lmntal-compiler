import runtime.*;
import java.util.*;
public class SomeInlineCodetimer implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodetimerCustomGuardImpl";

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
		case 2: {
			/*inline*/
			AbstractMembrane mem1 = me.getMem();
			mem1.removeAtom(me);
			mem1.removeAtom(me.getArg(0).getAtom());
			mem1.removeAtom(me.getArg(1).getAtom());
			long time1 = (Long) ((ObjectFunctor) me.getArg(0).getAtom().getFunctor()).getObject();
			long time2 = (Long) ((ObjectFunctor) me.getArg(1).getAtom().getFunctor()).getObject();
			double res = (time1 - time2) / 1000.0;
			Atom resatom = mem1.newAtom(new FloatingFunctor(res));
			mem1.relinkAtomArgs(resatom,0,me,2);
			break; }
		case 0: {
			/*inline*/me.getMem().alterAtomFunctor( me,
			new IntegerFunctor(new Long(System.currentTimeMillis()).intValue()) );
			break; }
		case 1: {
			/*inline*/me.getMem().alterAtomFunctor( me,
			new ObjectFunctor(System.currentTimeMillis()) );
			break; }
		}
	}
}

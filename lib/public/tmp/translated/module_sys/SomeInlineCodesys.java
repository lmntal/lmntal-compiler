package translated.module_sys;
import runtime.*;
import java.util.*;
public class SomeInlineCodesys {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 6: {
			/*inline*/
	try {
		int count = mem.getAtomCountOfFunctor(new Functor(me.nth(0), ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue()));
		Atom p = mem.newAtom(new IntegerFunctor(count));
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		mem.relink(p, 0, me, 2);
		me.remove();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
			break; }
		case 2: {
			/*inline*/
	Env.fTrace=false;
	me.setName("nil");
	
			break; }
		case 1: {
			/*inline*/
	Env.fTrace=true;
	me.setName("nil");
	
			break; }
		case 4: {
			/*inline*/
	try {
		Atom p = mem.newAtom(new ObjectFunctor(
			Runtime.getRuntime().exec( me.nth(0), util.Util.arrayOfList(me.getArg(1), "str") )));
//	System.out.println(p);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		mem.relink(p, 0, me, 2);
		me.remove();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
			break; }
		case 0: {
			/*inline*/
	me.setName("nil");
	System.out.println(Dumper.dump(mem));
	
			break; }
		case 3: {
			/*inline*/
	util.Util.makeList(me.getArg(0), Env.argv);
	me.remove();
	
			break; }
		case 5: {
			/*inline*/
	mem.makePerpetual();
	me.setName("nil");
	
			break; }
		}
	}
}

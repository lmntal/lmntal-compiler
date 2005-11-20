package translated.module_array;
import runtime.*;
import java.util.*;
public class SomeInlineCodearray {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 3: {
			/*inline*/
		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		int len = me.nthAtom(0).getFunctor().getArity()-1;
		i = (i+len) % len;
		
	  int v = ((IntegerFunctor)me.nthAtom(0).nthAtom(i).getFunctor()).intValue();
		Atom result = mem.newAtom(new IntegerFunctor(v));
		/* Functor f = me.nthAtom(0).nthAtom(i).getFunctor(); */
		/* Atom result = mem.newAtom(f); */
		mem.relink(result, 0, me, 2);
		mem.unifyAtomArgs(me, 3, me, 0);
		me.nthAtom(1).remove();
		me.remove();
		
			break; }
		case 4: {
			/*inline*/
		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		int len = me.nthAtom(0).getFunctor().getArity()-1;
		i = (i+len) % len;
		
		me.nthAtom(0).nthAtom(i).remove();
		mem.relink(me.nthAtom(0), i, me, 2);
		mem.unifyAtomArgs(me, 3, me, 0);
		me.nthAtom(1).remove();
		me.remove();
		
			break; }
		case 1: {
			/*inline*/
		int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		int len = me.nthAtom(0).getFunctor().getArity()-1;
		i = (i+len) % len;
		
		mem.swapAtomArgs(me.nthAtom(0), i, me, 3);
		mem.unifyAtomArgs(me, 4, me, 0);
		mem.unifyAtomArgs(me, 3, me, 2);
		me.nthAtom(1).remove();
		me.remove();
		
			break; }
		case 0: {
			/*inline*/
		int l = ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		Atom result = mem.newAtom(new Functor("int_array", 2));
		Atom result2 = mem.newAtom(new Functor("@", l+1));
		for(int i=0;i<l;i++) {
			Atom el = mem.newAtom(new IntegerFunctor(0));
			mem.newLink(el, 0, result2, i);
		}
		mem.newLink(result, 0, result2, l);
		mem.relink(result, 1, me, 1);
		me.nthAtom(0).remove();
		me.remove();
		
			break; }
		case 2: {
			/*inline*/
	  int i = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		int len = me.nthAtom(0).getFunctor().getArity()-1;
		i = (i+len) % len;
		
		mem.swapAtomArgs(me.nthAtom(0), i, me, 3);
		mem.unifyAtomArgs(me, 4, me, 0);
		mem.unifyAtomArgs(me, 3, me, 2);
		me.nthAtom(1).remove();
		me.remove();
		
			break; }
		}
	}
}

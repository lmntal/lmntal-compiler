package translated.module_float;
import runtime.*;
import java.util.*;
public class SomeInlineCodefloat {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 14: {
			/*inline*/
		double a0=
		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 13: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 11: {
			/*inline*/
		double a0=
		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 7: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 17: {
			/*inline*/
		double a0=
		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 0: {
			/*inline*/		

		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();

		Atom result = mem.newAtom(
		new Functor((( a0 > a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
	
			break; }
		case 4: {
			/*inline*/

		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();

		Atom result = mem.newAtom(
		new Functor((( a0 == a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
	
			break; }
		case 9: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 12: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 * a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 6: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 15: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 3: {
			/*inline*/		

		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();

		Atom result = mem.newAtom(
		new Functor((( a0 >= a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
	
			break; }
		case 8: {
			/*inline*/
		double a0=
		((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 + a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 10: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 - a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 5: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();
		Atom result = mem.newAtom(
		new Functor((( a0 != a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 1: {
			/*inline*/		

		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();

		Atom result = mem.newAtom(
		new Functor((( a0 < a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
	
			break; }
		case 16: {
			/*inline*/
		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
		Atom result = mem.newAtom(new FloatingFunctor(a0 / a1));
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
			break; }
		case 2: {
			/*inline*/		

		double a0=
		((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue();
		double a1=
		((FloatingFunctor)me.nthAtom(1).getFunctor()).floatValue();

		Atom result = mem.newAtom(
		new Functor((( a0 <= a1 )?"true":"false"), 1)
		);
		mem.relink(result, 0, me, 2);
		me.nthAtom(0).remove();
		me.nthAtom(1).remove();
		me.remove();
	
	
			break; }
		}
	}
}

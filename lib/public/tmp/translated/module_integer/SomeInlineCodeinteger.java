package translated.module_integer;
import runtime.*;
import java.util.*;
public class SomeInlineCodeinteger {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
 int a = 
  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  int b =
  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
  Atom result = mem.newAtom(new IntegerFunctor(a & b));
  mem.relink(result, 0, me, 2);
  me.nthAtom(0).remove();
  me.nthAtom(1).remove();
  me.remove();
 
			break; }
		case 4: {
			/*inline*/
  int n = 
  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  Random rand = new Random();
  int rn = (int)(n*Math.random());
  Atom result = mem.newAtom(new IntegerFunctor(rn));
  mem.relink(result, 0, me, 1);
  me.nthAtom(0).remove();
  me.remove();
  
			break; }
		case 3: {
			/*inline*/
  int a = 
  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  int n =
  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
  int r = 1;  
  for(int i=n;i>0;i--) r*=a;
  Atom result = mem.newAtom(new IntegerFunctor(r));
  mem.relink(result, 0, me, 2);
  me.nthAtom(0).remove();
  me.nthAtom(1).remove();
  me.remove();
 
			break; }
		case 1: {
			/*inline*/
 int a = 
  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  int b =
  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
  Atom result = mem.newAtom(new IntegerFunctor(a | b));
  mem.relink(result, 0, me, 2);
  me.nthAtom(0).remove();
  me.nthAtom(1).remove();
  me.remove();
 
			break; }
		case 2: {
			/*inline*/
 int a = 
  ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  int b =
  ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
  Atom result = mem.newAtom(new IntegerFunctor(a ^ b));
  mem.relink(result, 0, me, 2);
  me.nthAtom(0).remove();
  me.nthAtom(1).remove();
  me.remove();
 
			break; }
		case 5: {
			/*inline*/
	String s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();
	Random rand = new Random();
	int v=0;
	try{
		v = Integer.parseInt(s);
	} catch(NumberFormatException e) {
	}
	Atom result = mem.newAtom(new IntegerFunctor(v));
	mem.relink(result, 0, me, 1);
	me.nthAtom(0).remove();
	me.remove();
	
			break; }
		}
	}
}

import runtime.*;
import java.util.*;
public class SomeInlineCodeinteger implements InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {
		try {
		String name = "SomeInlineCodeintegerCustomGuardImpl";

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
		case 1: {
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
		case 5: {
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
		case 4: {
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
		case 2: {
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
		case 3: {
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
		case 0: {
			/*inline*/
  int start =
 ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
  int stop =
 ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
  
  Atom result = mem.newAtom(new IntegerFunctor(start));
  mem.relink(result, 0, me, 2);
  me.nthAtom(0).remove();
  me.nthAtom(1).remove();
  me.remove();
  Atom toLink = result.nthAtom(0);
  int toArg=0;
  
  for(int i = 0; i < toLink.getEdgeCount(); i++){
    if(toLink.nthAtom(i) == result){
      toArg = i;
      break;
    }
  }

  for(int i = start; i < stop; i++){
    /*Ê£À½*/
  	Map atomMap = new HashMap();
    atomMap.put(result, mem.newAtom(result.getFunctor()));
    atomMap = mem.copyAtoms(result, atomMap);
    
    result.remove();
    result = mem.newAtom(new IntegerFunctor(i+1));
    mem.newLink(result, 0, toLink, toArg);
    toLink = result.nthAtom(0);
  }
 
			break; }
		case 6: {
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

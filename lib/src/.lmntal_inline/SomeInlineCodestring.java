import runtime.*;
import java.util.*;
public class SomeInlineCodestring implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 9: {
			/*inline*/
	String s = "";
	try{
		s = Double.toString(((FloatingFunctor)me.nthAtom(0).getFunctor()).floatValue());
	} catch(Exception e) {}
	Atom res = mem.newAtom(new StringFunctor(s));
	mem.relinkAtomArgs(res, 0, me, 1);
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 6: {
			/*inline*/
	int b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
	int e = ((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();
	String s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();
	Atom sub = null;
	try{
		sub = mem.newAtom(new StringFunctor(s.substring(b,e)));
	} catch(Exception exc) {}
	mem.relinkAtomArgs(sub, 0, me, 3);
	
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me.nthAtom(1));
	mem.removeAtom(me.nthAtom(2));
	mem.removeAtom(me);
	
			break; }
		case 5: {
			/*inline*/
	int b = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
	String s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();
	Atom sub = null;
	try{
		sub = mem.newAtom(new StringFunctor(s.substring(b)));
	} catch(Exception e){}
	mem.relinkAtomArgs(sub, 0, me, 2);
	
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me.nthAtom(1));
	mem.removeAtom(me);
	
			break; }
		case 1: {
			/*inline*/
	String s=null;
	try {
		s = me.nth(0).replaceAll(
		((StringFunctor)me.nthAtom(1).getFunctor()).stringValue(),
		((StringFunctor)me.nthAtom(2).getFunctor()).stringValue()
		);
	} catch(Exception e) {}
	if(s==null) s = ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue();
	Atom result = mem.newAtom(new Functor(s, 1));
	mem.relink(result, 0, me, 3);
	me.nthAtom(0).remove();
	me.nthAtom(1).remove();
	me.nthAtom(2).remove();
	me.remove();
	
			break; }
		case 11: {
			/*inline*/
	double d = 0.0;
	try{
		d = Double.parseDouble( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());
	} catch(Exception e) {}
	Atom res = mem.newAtom(new FloatingFunctor(d));
	mem.relinkAtomArgs(res, 0, me, 1);

	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 2: {
			/*inline*/
	boolean b=false;
	try {
		b = java.util.regex.Pattern.compile(
		((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ).matcher(
		((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() ).find();
	} catch(Exception e) {e.printStackTrace();}
	Atom result = mem.newAtom(new Functor(b?"true":"false", 1));
	mem.relink(result, 0, me, 2);
	me.nthAtom(0).remove();
	me.nthAtom(1).remove();
	me.remove();
	
			break; }
		case 3: {
			/*inline*/
	String r[] = ((StringFunctor)me.nthAtom(1).getFunctor()).stringValue().split(
	((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() );

//	util.Util.makeList(me.getArg(2), java.util.Arrays.asList(r));

// util.Util.makeList¤Îcopy&paste&½¤Àµ
	List l = java.util.Arrays.asList(r);
	Link link = me.getArg(2);

	Iterator it = l.iterator();
	//AbstractMembrane mem = link.getAtom().getMem();
	Atom parent=null;
	boolean first=true;
	while(it.hasNext()) {
		Atom c = mem.newAtom(new Functor(".", 3));  // .(Value Next Parent)
		Atom v = mem.newAtom(new StringFunctor(it.next().toString()));
		//new Functor(it.next().toString(), 1)); // value(Value)
		mem.newLink(c, 0, v, 0);
		if(first) {
			mem.inheritLink(c, 2, link);
		} else {
			mem.newLink(c, 2, parent, 1);
		}
		parent = c;
		first=false;
	}
	Atom nil = mem.newAtom(new Functor("[]", 1));
	if(first) {
		mem.inheritLink(nil, 0, link);
	} else {
		mem.newLink(nil, 0, parent, 1);
	}
	
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me.nthAtom(1));
	mem.removeAtom(me);
	
			break; }
		case 4: {
			/*inline*/
	Atom cat = mem.newAtom(new StringFunctor(
	((StringFunctor)me.nthAtom(0).getFunctor()).stringValue() +
	((StringFunctor)me.nthAtom(1).getFunctor()).stringValue() ));
	mem.relinkAtomArgs(cat, 0, me, 2);
	
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me.nthAtom(1));
	mem.removeAtom(me);
	
			break; }
		case 7: {
			/*inline*/
	int n=0;
	try{
		n = Integer.parseInt( ((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());
	} catch(Exception e) {}
	Atom res = mem.newAtom(new IntegerFunctor(n));
	mem.relinkAtomArgs(res, 0, me, 1);
	
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 10: {
			/*inline*/
	String s = "";
	char c;
	try{
		c = (char)((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue();
		s = Character.toString(c);
	} catch(Exception e) {}
	Atom res = mem.newAtom(new StringFunctor(s));
	mem.relinkAtomArgs(res, 0, me, 1);
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 8: {
			/*inline*/
	String s = "";
	try{
		s = Integer.toString(((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue());
	} catch(Exception e) {}
	Atom res = mem.newAtom(new StringFunctor(s));
	mem.relinkAtomArgs(res, 0, me, 1);
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 0: {
			/*inline*/
	StringBuffer b = new StringBuffer(((StringFunctor)me.nthAtom(0).getFunctor()).stringValue());
	StringBuffer r = new StringBuffer("");
	int times = ((IntegerFunctor)me.nthAtom(1).getFunctor()).intValue();
	for(int i=1;i<=times;i<<=1, b.append(b)) {
		if((i&times)>0) r.append(b);
	}
	Atom result = mem.newAtom(new StringFunctor(r.toString()));
	mem.relink(result, 0, me, 2);
	me.nthAtom(0).remove();
	me.nthAtom(1).remove();
	me.remove();
	
			break; }
		}
	}
}

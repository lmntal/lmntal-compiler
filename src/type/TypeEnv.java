package type;

import java.util.HashMap;
import java.util.Map;

import runtime.FloatingFunctor;
import runtime.Functor;
import runtime.IntegerFunctor;
import runtime.ObjectFunctor;
import runtime.StringFunctor;
import runtime.SymbolFunctor;

import compile.structure.Atom;

public final class TypeEnv {

	public static final int ACTIVE = -1;

	public static final int CONNECTOR = -2;
	
	private static final Map functorToOut = new HashMap();
	private static final Map functorToTypeName = new HashMap();

	static{
		functorToOut.put(Functor.UNIFY,new Integer(CONNECTOR));
		functorToOut.put(Functor.INSIDE_PROXY,new Integer(CONNECTOR));
		functorToOut.put(Functor.OUTSIDE_PROXY,new Integer(CONNECTOR));
		/* Passive atom */
		/* List atom */
		functorToOut.put(new SymbolFunctor(".",3),new Integer(2));
		functorToOut.put(new SymbolFunctor("[]",1),new Integer(0));
		/* Boolean atom */
		functorToOut.put(new SymbolFunctor("true",1),new Integer(0));
		functorToOut.put(new SymbolFunctor("false",1),new Integer(0));
	}
	public static int outOfPassiveAtom(Atom atom) {
		Functor f = atom.functor;
		/* Special atom */
		if (f instanceof IntegerFunctor)
			return 0;
		if (f instanceof FloatingFunctor)
			return 0;
		if (f instanceof StringFunctor)
			return 0;
		if (f instanceof ObjectFunctor)
			return 0;
		if (functorToOut.containsKey(f))
			return ((Integer)functorToOut.get(f)).intValue();
		return ACTIVE;
	}

	static{
		functorToTypeName.put(new SymbolFunctor(".",3),"list");
		functorToTypeName.put(new SymbolFunctor("[]",1),"list");
		functorToTypeName.put(new SymbolFunctor("true",1),"bool");
		functorToTypeName.put(new SymbolFunctor("false",1),"bool");
	}
	public static String getTypeNameOfPassiveFunctor(Functor f){
		/* Special Atom */
		if(f instanceof IntegerFunctor)
			return "int";
		if(f instanceof FloatingFunctor)
			return "float";
		if(f instanceof StringFunctor)
			return "string";
		if(f instanceof ObjectFunctor)
			return "java-object";
		if (functorToTypeName.containsKey(f))
			return (String)functorToTypeName.get(f);
		else return null;
	}
}

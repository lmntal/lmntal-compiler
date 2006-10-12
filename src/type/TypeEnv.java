package type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.FloatingFunctor;
import runtime.Functor;
import runtime.IntegerFunctor;
import runtime.ObjectFunctor;
import runtime.StringFunctor;
import runtime.SymbolFunctor;

import compile.structure.Atom;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.RuleStructure;

public final class TypeEnv {

	public static final int ACTIVE = -1;

	public static final int CONNECTOR = -2;
	
	private static final Map<Functor, Integer> functorToOut = new HashMap<Functor, Integer>();
	private static final Map<Functor, String> functorToTypeName = new HashMap<Functor, String>();

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

	/** ∫∏ ’ÀÏ§™§Ë§”∫∏ ’Ω–∏ΩÀÏ§ŒΩ∏πÁ */
	private static final Set<Membrane> lhsmems = new HashSet<Membrane>();

	/**
	 * ∫∏ ’Ω–∏ΩÀÏ§Ú$lhsmems§À≈–œø§π§Î
	 * @param mem
	 */
	public static void collectLHSMems(List<RuleStructure> rules){
		Iterator<RuleStructure> it = rules.iterator();
		while(it.hasNext()){
			collectLHSMems(it.next());
		}
	}
	/**
	 * ∫∏ ’Ω–∏ΩÀÏ§Ú$lhsmems§À≈–œø§π§Î
	 * @param rule
	 */
	private static void collectLHSMems(RuleStructure rule){
		collectLHSMem(rule.leftMem);
//		 ∫∏ ’§À•Î°º•Î§œΩ–∏Ω§∑§ §§
		Iterator<RuleStructure> it = rule.rightMem.rules.iterator();
		while(it.hasNext()){
			collectLHSMems(it.next());
		}
	}
	/**
	 * ∫∏ ’Ω–∏ΩÀÏ§Ú$lhsmems§À≈–œø§π§Î
	 * @param mem ∫∏ ’Ω–∏ΩÀÏ
	 */
	private static void collectLHSMem(Membrane mem){
		lhsmems.add(mem);
		Iterator it = mem.mems.iterator();
		while(it.hasNext()){
			Membrane cmem = (Membrane)it.next();
			collectLHSMem(cmem);
		}
	}
	
	/** ∫∏ ’§Œ•¢•»•‡§´§…§¶§´§Ú ÷§π */
	public static boolean isLHSAtom(Atom atom) {
		return isLHSMem(atom.mem);
	}

	/** ∫∏ ’§ŒÀÏ§´§…§¶§´§Ú ÷§π */
	public static boolean isLHSMem(Membrane mem) {
		return lhsmems.contains(mem);
	}

	/**
	 * get real buddy through =/2, $out, $in
	 * 
	 * @param lo
	 * @return
	 */
	public static LinkOccurrence getRealBuddy(LinkOccurrence lo) {
		if (lo.buddy.atom instanceof Atom) {
			Atom a = (Atom) lo.buddy.atom;
			int o = TypeEnv.outOfPassiveAtom(a);
			if (o == TypeEnv.CONNECTOR)
				return getRealBuddy(a.args[1 - lo.buddy.pos]);
			else
				return lo.buddy;
		} else
			return lo.buddy;
	}
	
	
	public static final String ANNONYMOUS = "??";
	/**
	 * 
	 */
	public static String getMemName(Membrane mem){
		if(mem.name == null)return ANNONYMOUS;
		else return mem.name;
	}

}

package type;

import java.util.HashMap;
import java.util.HashSet;
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

	public static final int COUNT_DEFALUT = 0;
	public static final int COUNT_APPLY = 1;
	public static final int countLevel = COUNT_APPLY;
	
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
	
	public static int outOfPassiveFunctor(Functor f){
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

	public static int outOfPassiveAtom(Atom atom) {
		Functor f = atom.functor;
		return outOfPassiveFunctor(f);
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

	/** 左辺膜および左辺出現膜の集合 */
	private static final Set<Membrane> lhsmems = new HashSet<Membrane>();

	private static final Map<Membrane, String> memToName = new HashMap<Membrane, String>();
	
	public static void initialize(Membrane root){
		// 全ての膜について、ルールの左辺最外部出現かどうかの情報を得る
		TypeEnv.collectLHSMemsAndNames(root.rules);
		
		// 全ての右辺膜について、
	}
	
	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * 本膜の膜名を所属膜の膜名とする
	 * @param mem
	 */
	private static void collectLHSMemsAndNames(List<RuleStructure> rules){
		for(RuleStructure rule : rules)
			collectLHSMemsAndNames(rule);
	}
	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * @param rule
	 */
	private static void collectLHSMemsAndNames(RuleStructure rule){
		collectLHSMem(rule.leftMem);
		memToName.put(rule.leftMem, rule.parent.name);
		memToName.put(rule.rightMem, rule.parent.name);
//		 左辺にルールは出現しない
		for(RuleStructure rhsrule : ((List<RuleStructure>)rule.rightMem.rules))
			collectLHSMemsAndNames(rhsrule);
	}
	/**
	 * 左辺出現膜を$lhsmemsに登録する
	 * @param mem 左辺出現膜
	 */
	private static void collectLHSMem(Membrane mem){
		lhsmems.add(mem);
		for(Membrane cmem : ((List<Membrane>)mem.mems))
			collectLHSMem(cmem);
	}
	
	/** 左辺のアトムかどうかを返す */
	public static boolean isLHSAtom(Atom atom) {
		return isLHSMem(atom.mem);
	}

	/** 左辺の膜かどうかを返す */
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
	 * ルールの本膜については所属膜の名前を返す
	 */
	public static String getMemName(Membrane mem){
		String registered = memToName.get(mem);
		if(registered == null){
			if(mem.name == null)return ANNONYMOUS;
			else return mem.name;
		}
		else return registered;
	}

}

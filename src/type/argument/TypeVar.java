package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import runtime.functor.Functor;
import type.TypeEnv;


/**
 * 型変数を表す
 * @author kudo
 *
 */
public class TypeVar {
	private int id;

	private TypeVar realTypeVar;

	private static int varIndex = 0;

	private Set<Functor> passiveFunctors;
	
	public void addPassiveFunctor(Functor f){
		if(self().passiveFunctors == null)self().passiveFunctors = new HashSet<Functor>();
		self().passiveFunctors.add(f);
		passiveFunctors = self().passiveFunctors;
	}
	
	public TypeVar() {
		id = varIndex++;
		this.realTypeVar = this;
	}

	public TypeVar self() {
		if (realTypeVar == this)
			return this;
		else{
			realTypeVar = realTypeVar.self();
			return realTypeVar;
		}
	}
	
	public void unify(TypeVar tv){
		TypeVar tvs = tv.self();
		TypeVar s = self();
		if(tvs.id>=s.id)
			tvs.realTypeVar=s;
		else
			s.realTypeVar=tvs;
	}

	public boolean equals(Object o){
		if(o instanceof TypeVar){
			TypeVar tv = (TypeVar)o;
			return self() == tv.self();
		}else return false;
	}
	
	public int hashCode(){
		return self().id;
	}
	
	public String toString() {
		return "'t" + self().id+"=" + (self().passiveFunctors==null?("{?}"):("{"+stringOfPassiveFunctors()+"}"));
	}
	
	private boolean showDetailOfDataFunctor = false;
	public String stringOfPassiveFunctors(){
		String ret = "";
		Iterator<Functor> itf = self().passiveFunctors.iterator();
		if(!showDetailOfDataFunctor){
			Set<String> dataTypes = new HashSet<String>();
			dataTypes.add(typeNameOfFunctor(itf.next()));
			while(itf.hasNext()){
				String tn = typeNameOfFunctor(itf.next());
				if(dataTypes.contains(tn))continue;
				else dataTypes.add(tn);
			}
			Iterator<String> itd = dataTypes.iterator();
			ret += itd.next();
			while(itd.hasNext()){
				ret += ", "+ itd.next();
			}
		}
		else{
			ret += itf.next();
			while(itf.hasNext()){
				ret += ", " + itf.next();
			}
		}
		return ret;
	}
	
	private Set<String> typenames;// = new HashSet<String>();
	public Set<String> getTypeName(){//throws TypeException{
		if(typenames == null){
			typenames = new HashSet<String>();
			if(self().passiveFunctors == null){
				return new HashSet<String>();
			}
			for(Functor f : self().passiveFunctors){
				String tn = typeNameOfFunctor(f);
				if(!typenames.contains(tn))
					typenames.add(tn);
//				if(typename==null)typename = tn;
//				else if(typename.equals(tn))continue;
//				else throw new TypeException("two data types in one type variable : " + this);
			}
		}
		return typenames;
	}
	public void setTypeName(Set<String> typenames){
//		Set<String> tns = new HashSet<String>();
		this.typenames = typenames;
//		this.typename = typename;
	}
	
	public String shortString(){
		return "(" + (self().passiveFunctors==null?("'t" + self().id):stringOfPassiveFunctors()) + ")";
	}
	
	public String shortStringLMNSyntax() {
		return "(" + (self().passiveFunctors == null ? ("t" + self().id) : stringOfPassiveFunctors()) + ")";
	}
	
	private String typeNameOfFunctor(Functor f){
		String tn = TypeEnv.getTypeNameOfPassiveFunctor(f);
		if(tn != null)return tn;
		else return f.toString();
	}
}

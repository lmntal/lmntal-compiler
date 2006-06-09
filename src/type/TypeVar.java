package type;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Functor;

public class TypeVar {
	private int id;

	private TypeVar realTypeVar;

	private static int varIndex = 0;

	private Set passiveFunctors;
	
	public void addPassiveFunctor(Functor f){
		if(self().passiveFunctors == null)self().passiveFunctors = new HashSet();
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
		}
		else return false;
	}
	
	public int hashCode(){
		return self().id;
	}
	
	public String toString() {
		return (self().passiveFunctors==null?("'t" + self().id+"={?}"):(stringOfPassiveFunctors()));
	}
	
	public String stringOfPassiveFunctors(){
		String ret = "{";
		Iterator it = self().passiveFunctors.iterator();
		ret += it.next();
		while(it.hasNext()){
			ret += ", " + it.next();
		}
		return ret + "}";
	}

}

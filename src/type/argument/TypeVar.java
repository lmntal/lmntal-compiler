package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Functor;
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

	public boolean equals(TypeVar tv){
			return self() == tv.self();
	}
	
	public int hashCode(){
		return self().id;
	}
	
	public String toString() {
		return (self().passiveFunctors==null?("'t" + self().id+"={?}"):(stringOfPassiveFunctors()));
	}
	
	boolean showDetailOfDataFunctor = false;
	public String stringOfPassiveFunctors(){
		String ret = "{";
		Iterator<Functor> itf = self().passiveFunctors.iterator();
		if(!showDetailOfDataFunctor){
			Set<String> dataTypes = new HashSet<String>();
			dataTypes.add(typeNameOfFunctor(itf.next()));
			while(itf.hasNext()){
				String tn = typeNameOfFunctor(itf.next());
				if(dataTypes.contains(tn))continue;
				else dataTypes.add(tn);
			}
			Iterator itd = dataTypes.iterator();
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
		return ret + "}";
	}
	private String typeNameOfFunctor(Functor f){
		String tn = TypeEnv.getTypeNameOfPassiveFunctor(f);
		if(tn != null)return tn;
		else return f.toString();
	}
}

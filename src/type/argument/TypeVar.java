package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Env;
import runtime.Functor;
import type.TypeEnv;
import type.TypeException;

/**
 * ���ѿ���ɽ��
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
		return ret;
	}
	
	private String typename = null;
	public String getTypeName()throws TypeException{
		if(typename == null){
			for(Functor f : self().passiveFunctors){
				String tn = typeNameOfFunctor(f);
				if(typename==null)typename = tn;
				else if(typename.equals(tn))continue;
				else throw new TypeException("two data types in one type variable : " + this);
			}
		}
		return typename;
	}
	public void setTypeName(String typename){
		this.typename = typename;
	}
	
	public String shortString(){
		return "(" + (self().passiveFunctors==null?("'t" + self().id):stringOfPassiveFunctors()) + ")";
	}
	
	private String typeNameOfFunctor(Functor f){
		String tn = TypeEnv.getTypeNameOfPassiveFunctor(f);
		if(tn != null)return tn;
		else return f.toString();
	}
}
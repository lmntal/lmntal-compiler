package type;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import runtime.Env;

/**
 * 
 * @author kudo
 * 
 */
public class UnifySolver {
	private Map<Path,TypeVar> pathToTV;

	private ModesSet modesSet;

	public UnifySolver() {
		this.pathToTV = new HashMap<Path,TypeVar>();
		this.modesSet = new ModesSet();
	}

	public void add(UnifyConstraint uc) throws TypeConstraintException{
		PolarizedPath pp1 = uc.getPPath1();
		PolarizedPath pp2 = uc.getPPath2();
		Path p1 = pp1.getPath();
		Path p2 = pp2.getPath();
		int sign1 = pp1.getSign();
		int sign2 = pp2.getSign();
		TypeVar tp1 = getTypeVar(p1);
		TypeVar tp2 = getTypeVar(p2);
		tp1.unify(tp2);
		modesSet.add(sign1*sign2, p1, p2);
	}
	
	private TypeVar getTypeVar(Path p){
		if(!pathToTV.containsKey(p)){
			pathToTV.put(p, new TypeVar());
		}
		return (TypeVar)pathToTV.get(p);
	}
	
	public void solveTypeAndMode(Collection<Set<ReceiveConstraint>> receiveConstraintsSet)throws TypeConstraintException{
		Iterator<Set<ReceiveConstraint>> itrcs = receiveConstraintsSet.iterator();
		while(itrcs.hasNext()){
			Iterator<ReceiveConstraint> itrc = itrcs.next().iterator();
			while(itrc.hasNext()){
				ReceiveConstraint rc = itrc.next();
				PolarizedPath pp = rc.getPPath();
				int sign = pp.getSign();
				Path p = pp.getPath();
				TypeVar tp = getTypeVar(p);
				tp.addPassiveFunctor(rc.getFunctor());
				ModeSet ms = modesSet.getModeSet(p);
				try{
					ms.bindSign(sign);
				}catch(TypeConstraintException e){
					Env.e(rc + " ==> " + e.getMessage());
				}
			}
		}
	}

	public Set<TypeVarConstraint> getTypeVarConstraints() throws TypeConstraintException{
		Set<TypeVarConstraint> typeVarConstraints = new HashSet<TypeVarConstraint>();
		Iterator it = pathToTV.keySet().iterator();
		while(it.hasNext()){
			Path p = (Path)it.next();
			typeVarConstraints.add(new TypeVarConstraint(p, getTypeVar(p), modesSet.getModeSet(p)));
		}
		return typeVarConstraints;
	}
}

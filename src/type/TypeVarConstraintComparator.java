package type;

import java.util.Comparator;

/**
 * comparator for fixed type-var constraints
 * @author kudo
 *
 */
public class TypeVarConstraintComparator implements Comparator {
	
	public int compare(Object arg0, Object arg1) {
		if(!(arg0 instanceof TypeVarConstraint && arg1 instanceof TypeVarConstraint))
			return 0;
		TypeVarConstraint tvc1 = (TypeVarConstraint)arg0;
		TypeVarConstraint tvc2 = (TypeVarConstraint)arg1;
		Path p1 = tvc1.getPath();
		Path p2 = tvc2.getPath();
		if(p1 instanceof ActiveAtomPath){
			if(p2 instanceof ActiveAtomPath){
				ActiveAtomPath ap1 = (ActiveAtomPath)p1;
				ActiveAtomPath ap2 = (ActiveAtomPath)p2;
				int cs = ap1.getMemName().compareTo(ap2.getMemName());
				if(cs==0){
					int cf = ap1.getFunctor().getName().compareTo(ap2.getFunctor().getName());
					if(cf==0){
						int ca = ap1.getFunctor().getArity() - ap2.getFunctor().getArity();
						if(ca==0)
							return tvc1.hashCode() - tvc2.hashCode();
						else return ca;
					}
					else return cf;
				}
				else return cs;
			}
			else return -1;
		}
		else if(p1 instanceof TracingPath){
			if(p2 instanceof ActiveAtomPath)
				return 1;
			else if(p2 instanceof TracingPath){
				TracingPath tp1 = (TracingPath)p1;
				TracingPath tp2 = (TracingPath)p2;
				int cp = compare(tp1.getPath(),tp2.getPath());
				if(cp==0){
					int cf = tp1.getFunctor().getName().compareTo(tp2.getFunctor().getName());
					if(cf==0){
						int ca = tp1.getFunctor().getArity() - tp2.getFunctor().getArity();
						if(ca==0)
							return tvc1.hashCode() - tvc2.hashCode();
						else return ca;
					}
					else return cf;
				}
				else return cp;
			}
			else return -1;
		}
		else{
			if(p2 instanceof RootPath){
				return tvc1.hashCode() - tvc2.hashCode();
			}
			else return 1;
		}
	}

}

/**
 * 
 */
package type;

/**
 * @author kudo
 *
 */
public class TypeVarConstraint implements Constraint {

	private Path p;
	
	public Path getPath(){
		return p;
	}
	
	private TypeVar tp;
	
	private ModeSet ms;

	public TypeVarConstraint(Path p, TypeVar tp, ModeSet ms){
		this.p = p;
		this.tp = tp;
		this.ms = ms;
	}
	
	public boolean equals(Object o){
		if(o instanceof TypeVarConstraint){
			TypeVarConstraint tvc = (TypeVarConstraint)o;
			return p.equals(tvc.p) && tp.equals(tvc.tp)
			&& ms.equals(tvc.ms)
			;
		}
		else return false;
	}
	
	public int hashCode(){
		return p.hashCode() + tp.self().hashCode() + tp.self().hashCode();
	}
	
	public String toString(){
		return fixLength(p.toStringWithOutAnonMem(),15) + " : mode=" + 
		(fixLength(ms.toString(),12)) + ", type=" + tp;
	}
	
	private String fixLength(String orig, int length){
		for(int s = orig.length();s<length;s++)
			orig += " ";
		return orig;
	}
	
}

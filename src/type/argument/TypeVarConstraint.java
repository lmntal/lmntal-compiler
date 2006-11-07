/**
 * 
 */
package type.argument;


/**
 * 各パスについての型変数、モード変数を持つ
 * @author kudo
 *
 */
public class TypeVarConstraint {

	private Path p;
	
	public Path getPath(){
		return p;
	}
	
	private TypeVar tp;
	
	private ModeVar ms;

	public TypeVarConstraint(Path p, TypeVar tp, ModeVar ms){
		this.p = p;
		this.tp = tp;
		this.ms = ms;
	}
	
	public boolean equals(TypeVarConstraint tvc){
		return p.equals(tvc.p) && tp.equals(tvc.tp) && ms.equals(tvc.ms);
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

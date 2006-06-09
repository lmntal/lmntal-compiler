/**
 * 
 */
package type;

/**
 * @author kudo
 * 
 */
public class UnifyConstraint implements Constraint {

	private PolarizedPath pp1;

	private PolarizedPath pp2;

	public PolarizedPath getPPath1() {
		return pp1;
	}

	public PolarizedPath getPPath2() {
		return pp2;
	}
	
	public void regularize(){
		if(pp1.getSign() == -1 ){
			pp1.inverse();
			pp2.inverse();
		}
	}

	public void setPPathes(PolarizedPath pp1, PolarizedPath pp2) {
		this.pp1 = pp1;
		this.pp2 = pp2;
	}

	public UnifyConstraint(PolarizedPath pp1, PolarizedPath pp2) {
		this.pp1 = pp1;
		this.pp2 = pp2;
	}

	public String toString() {
		return "(" + pp1 + "=" + pp2 + ")";
	}

	public boolean equals(Object o) {
		if (o instanceof UnifyConstraint) {
			UnifyConstraint uc = (UnifyConstraint) o;
			return pp1.equals(uc.pp1) && pp2.equals(uc.pp2);
		} else
			return false;
	}

	public int hashCode() {
		return pp1.hashCode() * 2 + pp2.hashCode();
	}

}

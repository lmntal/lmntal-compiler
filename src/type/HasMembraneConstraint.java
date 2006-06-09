package type;

/**
 * @author kudo
 * 
 */
public class HasMembraneConstraint implements Constraint {

	private String parentName;

	private String childName;

	public String getParentName(){
		return parentName;
	}
	
	public String getChildName(){
		return childName;
	}
	
	/**
	 * @param parentName
	 * @param childName
	 */
	public HasMembraneConstraint(String parentName, String childName) {
		this.parentName = parentName == null ? "??" : parentName;
		this.childName = childName == null ? "??" : childName;
	}

	public boolean equals(Object o) {
		if (!(o instanceof HasMembraneConstraint))
			return false;
		HasMembraneConstraint hmc = (HasMembraneConstraint) o;
		return parentName.equals(hmc.parentName)
				&& childName.equals(hmc.childName);
	}

	public int hashCode() {
		return parentName.hashCode() * 2 + childName.hashCode();
	}

	public String toString() {
		return "(" + parentName + ":" + childName + ")";
	}

}

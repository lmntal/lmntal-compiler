package type.argument;

import compile.structure.LinkOccurrence;

public class RootPath implements Path {
	LinkOccurrence target;

	public RootPath(LinkOccurrence target) {
		this.target = target;
	}

	public LinkOccurrence getTarget() {
		return target;
	}

	public int hashCode(){
		return target.hashCode();
	}
	
	public boolean equals(Path p) {
		if (p instanceof RootPath) {
			return target.equals(p);
		} else
			return false;
	}

	public String toString() {
		return target.toString();
	}

	public String toStringWithOutAnonMem(){
		return target.toString();
	}
	
}

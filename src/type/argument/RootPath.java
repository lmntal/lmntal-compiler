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
	
	public boolean equals(Object o) {
		if (o instanceof RootPath) {
			return target.equals(o);
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

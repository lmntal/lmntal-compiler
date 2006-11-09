package type.argument;

import runtime.Functor;

public class ReceiveConstraint{
	private PolarizedPath ppath;

	private Functor functor;
	
	public PolarizedPath getPPath(){
		return ppath;
	}
	public void setPPath(PolarizedPath ppath){
		this.ppath = ppath;
	}

	public Functor getFunctor(){
		return functor;
	}
	
	public ReceiveConstraint(PolarizedPath pp, Functor f) {
		this.ppath = pp;
		this.functor = f;
	}

	public String toString() {
		return "(" + ppath + ":" + functor + ")";
	}

	public boolean equals(Object o) {
		if (o instanceof ReceiveConstraint) {
			ReceiveConstraint rpc = (ReceiveConstraint) o;
			return (functor.equals(rpc.functor) && ppath.equals(rpc.ppath));
		} else
			return false;
	}

	public int hashCode() {
		return (ppath.hashCode() * 2) + functor.hashCode();
	}
}

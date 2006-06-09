package type;

import runtime.Functor;

public class ActiveAtomPath implements Path {

	private String mem;
	
	public String getMemName(){
		return mem;
	}

	private Functor functor;
	
	public Functor getFunctor(){
		return functor;
	}

	private int pos;

	/**
	 * @param mem
	 * @param functor
	 * @param pos
	 */
	public ActiveAtomPath(String mem, Functor functor, int pos) {
		this.mem = (mem == null) ? "??" : mem;
		this.functor = functor;
		this.pos = pos;
	}

	public String toString() {
		return "<" + mem + ":" + functor + "," + pos + ">";
	}
	
	public String toStringWithOutAnonMem(){
		return "<" + (mem=="??"?"":(mem + ":")) + functor + "," + pos + ">";
	}

	public int hashCode(){
		return mem.hashCode() + functor.hashCode() + pos;
	}
	
	public boolean equals(Object o){
		if(o instanceof ActiveAtomPath){
			ActiveAtomPath aap = (ActiveAtomPath)o;
			return mem.equals(aap.mem) && functor.equals(aap.functor) && pos == aap.pos;
		}
		else return false;
	}
}

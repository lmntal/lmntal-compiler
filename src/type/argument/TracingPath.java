package type.argument;

import runtime.Functor;

public class TracingPath implements Path {

	private Path tracedPath;
	
	public Path getPath(){
		return tracedPath;
	}

	private Functor functor;
	
	public Functor getFunctor(){
		return functor;
	}
	
	private int pos;

	public TracingPath(Path tracedPath, Functor functor, int pos){
		this.tracedPath = tracedPath;
		this.functor = functor;
		this.pos = pos;
	}

	public int hashCode(){
		return tracedPath.hashCode() + functor.hashCode() + pos;
	}
	
	public boolean equals(Path p) {
		if (p instanceof TracingPath) {
			TracingPath tp = (TracingPath) p;
			return tracedPath.equals(tp.tracedPath)
					&& functor.equals(tp.functor)
					&& pos == tp.pos;
		} else
			return false;
	}

	public String toString() {
		return tracedPath + "<" + functor + "," +
				+ pos + ">";
	}

	public String toStringWithOutAnonMem(){
		return tracedPath.toStringWithOutAnonMem() + "<" + functor + "," +
		+ pos + ">";
	}
	
}

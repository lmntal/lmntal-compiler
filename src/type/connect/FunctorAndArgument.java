package type.connect;

import runtime.Functor;

class FunctorAndArgument {
	final Functor functor;
	final int i;
	
	FunctorAndArgument(Functor f, int i){
		this.functor = f;
		this.i = i;
	}
	
	@Override public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (!(o instanceof FunctorAndArgument)) {
			return false;
		}
		FunctorAndArgument faa = (FunctorAndArgument) o;
		if (!this.functor.equals(faa.functor)){
			return false;
		}
		if (this.i != faa.i){
			return false;
		}
		return true;
	}
	
	@Override public int hashCode() {
		int result = 17;
		result = 31 * result + functor.hashCode();
		result = 31 * result + i;
		return result;
	}
	
	@Override public String toString() {
		return "(" + functor.toString() + " " + i + ")";
	}
}

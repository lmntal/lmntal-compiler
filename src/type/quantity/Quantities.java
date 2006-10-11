package type.quantity;

import java.util.HashMap;
import java.util.Map;

import runtime.Functor;

public class Quantities {
	Map<Functor,AtomQuantity> functorToQuantity;
	public Quantities(){
		functorToQuantity = new HashMap();
	}
	public void putAtomQuantity(Functor f, AtomQuantity aq){
		if(!functorToQuantity.containsKey(f)){
			functorToQuantity.put(f,aq);
		}
		else{
			AtomQuantity aqold = functorToQuantity.get(f);
			functorToQuantity.put(f,aq.merge(aqold, aq));
		}
	}

}

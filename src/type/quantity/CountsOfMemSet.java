package type.quantity;

import java.util.HashMap;
import java.util.Map;

import compile.structure.Membrane;

public class CountsOfMemSet {
	Map<Membrane,CountsOfMem> memToCounts;
	public CountsOfMemSet(){
		memToCounts = new HashMap<Membrane,CountsOfMem>();
	}
	public void add(CountsOfMem counts){
		Membrane mem = counts.mem;
		if(!memToCounts.containsKey(mem))
			memToCounts.put(mem,counts);
		else{
			CountsOfMem oldcounts = memToCounts.get(mem);
			oldcounts.merge(counts);
		}
	}
	/**
	 * 膜内の構造がn倍されることを意味する
	 *
	 */
	public void addMultiple(Count count, String name){
		//TODO 実装
	}
}

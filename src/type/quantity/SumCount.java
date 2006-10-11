package type.quantity;

import java.util.LinkedList;
import java.util.List;

public class SumCount extends Count {

	private List<Count> counts;
	
	public SumCount(Count first, Count second){
		this.counts = new LinkedList<Count>();
		counts.add(first);
		counts.add(second);
	}
	
	public SumCount(List<Count> counts){
		this.counts = counts;
	}
	public String toString(){
		if(counts.size() == 0)return "()";
		else if(counts.size() == 1)return "(" + counts.get(0) + ")";
		else{
			String ret = counts.get(0).toString();
			for(int i=1;i<counts.size();i++)
				ret += "+" + counts.get(i);
			return "(" + ret + ")";
		}
	}
	public Count add(Count c){
		counts.add(c);
		return this;
	}
}

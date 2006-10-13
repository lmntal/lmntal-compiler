package type.quantity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SumCount extends Count {

	private List<Count> counts;
	
	public List<Count> getCounts(){
		return counts;
	}
	
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
	
	public Count reflesh(){
		/** ルール変数 -> 出現数 */
		Map<VarCount, Integer> rvSizes = new HashMap<VarCount, Integer>();
		int consts = 0;
		Iterator<Count> itc = counts.iterator();
		while(itc.hasNext()){
			Count rc = itc.next().reflesh();
			if(rc instanceof NumCount){
				consts += ((NumCount)rc).value;
			}
			else if(rc instanceof VarCount){
				if(rvSizes.containsKey(rc))
					rvSizes.put((VarCount)rc, rvSizes.get(rc)+1);
				else rvSizes.put((VarCount)rc, 1);
			}
			else if(rc instanceof MinCount){
				Count b = ((MinCount)rc).getBody();
				if(b instanceof VarCount){
					if(rvSizes.containsKey(b))
						rvSizes.put((VarCount)b, rvSizes.get(b)-1);
					else rvSizes.put((VarCount)b,-1);
				}
				else if(b instanceof NumCount){
					consts += ((NumCount)rc).value;
				}
				else System.err.println("fatal error on SumCount.reflesh");
//				else refleshedCounts.add(rc);
			}
			else if(rc instanceof SumCount){
				Iterator<Count> itcc = ((SumCount)rc).getCounts().iterator();
				while(itcc.hasNext()){
					Count scc = itcc.next();
					if(scc instanceof MinCount){
						Count b = ((MinCount)rc).getBody();
						if(b instanceof VarCount){
							if(rvSizes.containsKey(b))
								rvSizes.put((VarCount)b, rvSizes.get(b)-1);
							else rvSizes.put((VarCount)b,-1);
						}
						else if(b instanceof NumCount){
							consts += ((NumCount)b).value;
						}
						else System.err.println("fatal error on SumCount.reflesh");
//						else refleshedCounts.add(rc);
					}
					else if(scc instanceof VarCount){
						if(rvSizes.containsKey(scc))
							rvSizes.put((VarCount)scc, rvSizes.get(scc)+1);
						else rvSizes.put((VarCount)scc, 1);
					}
					else if(scc instanceof NumCount){
						consts += ((NumCount)scc).value;
					}
					else System.err.println("fatal error on SumCount.reflesh");
//					else refleshedCounts.add(scc);
				}
			}
			else System.err.println("fatal error on SumCount.reflesh");
//			else refleshedCounts.add(rc);
		}
		List<Count> refleshedCounts = new LinkedList<Count>();
		Iterator<VarCount> itrv = rvSizes.keySet().iterator();
		while(itrv.hasNext()){
			VarCount rv = itrv.next();
			int size = rvSizes.get(rv);
			if(size == 0)continue;
			refleshedCounts.add(new MulCount(size,rv));
		}
		if(refleshedCounts.size() == 0)return new NumCount(consts);
		else{
			refleshedCounts.add(new NumCount(consts));
			counts = refleshedCounts;
			return this;
		}
	}
	
	public FixedCount evaluate(){
		FixedCount fc = new NumCount(0);
		Iterator<Count> itc = counts.iterator();
		while(itc.hasNext()){
			FixedCount f = itc.next().evaluate();
			fc = fc.add(f);
		}
		return fc;
	}
	
	public Count inverse(){
		List<Count> inversedList = new LinkedList<Count>();
		Iterator<Count> itc = counts.iterator();
		while(itc.hasNext()){
			Count ic = itc.next().inverse();
			inversedList.add(ic);
		}
		return new SumCount(inversedList);
	}
}

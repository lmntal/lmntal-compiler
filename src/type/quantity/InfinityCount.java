package type.quantity;

/**
 * 無限値。
 * @author kudo
 *
 */
public class InfinityCount extends ValueCount {
	public final boolean minus;
	
	public InfinityCount(boolean minus){
		this.minus = minus;
	}
	public String toString(){
		return (minus?"-":"") + "#inf";
	}
	public ValueCount inverse(){
		if(minus)return Count.INFINITY;
		else return Count.M_INFINITY;
	}
	public ValueCount mul(int m){
		if(m >=0)
			return this;
		else
			return inverse();
	}
	/**
	 * 無限に何を足しても無限(-無限の時はありうるか?)
	 */
	public ValueCount add(ValueCount v){
		return this;
	}
	
	public int compare(ValueCount vc){
		if(vc instanceof ValueCount)
			return (minus?-1:1);
		else{
			InfinityCount ic = (InfinityCount)vc;
			if(minus == ic.minus)return 0;
			else if(minus && !ic.minus)return -1;
			else return 1;
		}
	}
	
	public FixedCount or(ValueCount vc){
		if(vc instanceof InfinityCount){
			InfinityCount ic = (InfinityCount)vc;
			if(ic.minus == minus)return this;
			else return new IntervalCount(Count.M_INFINITY, Count.INFINITY);
		}
		else{
			NumCount nc = (NumCount)vc;
			if(minus)return new IntervalCount(this,nc);
			else return new IntervalCount(nc,this);
		}
	}
	
}

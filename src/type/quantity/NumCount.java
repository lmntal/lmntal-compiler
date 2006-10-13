package type.quantity;

public class NumCount extends ValueCount{
	public final int value;
	public NumCount(int value){
		this.value = value;
	}
	public String toString(){
		return Integer.toString(value);
	}
	public ValueCount inverse(){
		return new NumCount(-value);
	}
	public ValueCount mul(int m){
		return new NumCount(m * value);
	}
	public FixedCount inverseFixed(){
		return inverse();
	}
	public ValueCount add(ValueCount vc){
		if(vc instanceof NumCount){
			return new NumCount(((NumCount)vc).value + value);
		}
		else return vc.add(this);
	}
	public int compare(ValueCount vc){
		if(vc instanceof InfinityCount)
			return -vc.compare(this);
		else return value - ((NumCount)vc).value;
	}
	
	public FixedCount or(ValueCount vc){
		if(vc instanceof NumCount){
			int n = ((NumCount)vc).value;
			if(n==0&&value==0)System.out.println("wao");
			return ValueCount.newIntervalCount(n,value);
		}
		return vc.or(this);
	}
}

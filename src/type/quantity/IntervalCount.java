package type.quantity;

/**
 * 区間値
 * @author kudo
 *
 */
public class IntervalCount{

	public final ValueCount min;
	public final ValueCount max;

	public IntervalCount(int min, int max){
		this(new NumCount(min), new NumCount(max));
	}
	
	public IntervalCount(ValueCount min, ValueCount max){
		this.min = min;
		this.max = max;
	}

	public IntervalCount mul(int m) {
		if(m>=0)
			return new IntervalCount(min.mul(m), max.mul(m));
		else return new IntervalCount(max.mul(m), min.mul(m));
	}

	public IntervalCount add(IntervalCount f) {
		IntervalCount c = (IntervalCount)f;
		return new IntervalCount(min.add(c.min),max.add(c.max));
	}
	
	public IntervalCount or(IntervalCount fc){
		IntervalCount c = (IntervalCount)fc;
		ValueCount tmpmin = (min.compare(c.min) <= 0)?min:c.min;
		ValueCount tmpmax = (max.compare(c.max) >= 0)?max:c.max;
		return new IntervalCount(tmpmin,tmpmax);
	}

	public final IntervalCount or0(){
		return or(new IntervalCount(0,0));
	}
	
	public String toString() {
		if(min.equals(max))return min.toString();
		return "[" + min + "," + max + "]";
	}
}

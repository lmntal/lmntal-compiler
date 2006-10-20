package type.quantity;

/**
 * ¶è´ÖÃÍ
 * @author kudo
 *
 */
public class IntervalCount extends FixedCount {

	public final ValueCount min;
	public final ValueCount max;

	public IntervalCount(int min, int max){
		this(new NumCount(min), new NumCount(max));
	}
	
	public IntervalCount(ValueCount min, ValueCount max){
		this.min = min;
		this.max = max;
	}
	
//	public FixedCount inverse() {
//		return new IntervalCount(max.inverse(),min.inverse());
//	}

	public FixedCount mul(int m) {
		if(m>=0)
			return new IntervalCount(min.mul(m), max.mul(m));
		else return new IntervalCount(max.mul(m), min.mul(m));
	}

	public FixedCount add(FixedCount f) {
		if(f instanceof ValueCount){
			ValueCount v = (ValueCount)f;
			return new IntervalCount(min.add(v),max.add(v));
		}
		else{
			IntervalCount c = (IntervalCount)f;
			return new IntervalCount(min.add(c.min),max.add(c.max));
		}
	}
	
	public FixedCount or(FixedCount fc){
		if(fc instanceof NumCount){
			NumCount nc = (NumCount)fc;
			if(nc.compare(min)<0)return new IntervalCount(nc, max);
			else if(nc.compare(max)>0)return new IntervalCount(min, nc);
			else return this;
		}
		else if(fc instanceof InfinityCount){
			InfinityCount ic = (InfinityCount)fc;
			if(ic.minus)return new IntervalCount(ic, max);
			else return new IntervalCount(min, ic);
		}
		else{
			IntervalCount c = (IntervalCount)fc;
			ValueCount tmpmin = (min.compare(c.min) <= 0)?min:c.min;
			ValueCount tmpmax = (max.compare(c.max) >= 0)?max:c.max;
			return new IntervalCount(tmpmin,tmpmax);
		}
	}

	public String toString() {
		return "[" + min + "," + max + "]";
	}
}

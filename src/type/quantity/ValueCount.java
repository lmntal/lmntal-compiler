package type.quantity;

/**
 * 評価済みの値を表すクラス。区間値もしくは実数値、ないし無限値が入る
 * @author kudo
 *
 */
public abstract class ValueCount extends FixedCount {
	public abstract ValueCount add(ValueCount v);
	public final FixedCount add(FixedCount f){
		if(f instanceof ValueCount)
			return add((ValueCount)f);
		else return f.add(this);
	}
	public abstract ValueCount mul(int m);
	
	public abstract int compare(ValueCount vc);

	public final FixedCount or(FixedCount f){
		if(f instanceof ValueCount)return ((ValueCount)f).or(this);
		else return f.or(this);
	}
	public abstract FixedCount or(ValueCount vc);

	public static FixedCount newIntervalCount(int m1, int m2){
		return newIntervalCount(new NumCount(m1), new NumCount(m2));
	}

	public static FixedCount newIntervalCount(NumCount nm1, NumCount nm2){
		if(nm1.value>nm2.value)return new IntervalCount(nm2,nm1);
		else if(nm1.value<nm2.value)return new IntervalCount(nm1,nm2);
		else return nm1;
	}
}

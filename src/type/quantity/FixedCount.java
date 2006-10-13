package type.quantity;

/**
 * 評価結果として返るもの
 * @author kudo
 *
 */
public abstract class FixedCount extends Count {

	public abstract String toString();

	public final Count reflesh() {
		return this;
	}
	
	public final FixedCount evaluate() {
		return this;
	}

	public abstract FixedCount add(FixedCount v);
	public final Count add(Count c){
		if(c instanceof FixedCount)
			return add((FixedCount)c);
		else return new SumCount(c, this);
	}
	
	public abstract FixedCount mul(int m);

	public abstract FixedCount inverse();

	public final FixedCount or0(){
		return or(new NumCount(0));
	}
	public abstract FixedCount or(FixedCount fc);
}

package type.quantity;

/**
 * 評価結果として返るもの
 * @author kudo
 *
 */
public abstract class FixedCount{

	public abstract String toString();

	public final FixedCount evaluate() {
		return this;
	}

	public abstract FixedCount add(FixedCount v);

	public abstract FixedCount mul(int m);

	public final FixedCount or0(){
		return or(new NumCount(0));
	}
	public abstract FixedCount or(FixedCount fc);
}

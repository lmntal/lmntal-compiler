package runtime;

/** 浮動小数点数アトム用の1引数ファンクタを表すクラス
 * @author n-kato */

public class FloatingFunctor extends Functor {
	double value;
	public FloatingFunctor(double value) { super("",1);  this.value = value; }
	public String getName() { return "" + value; }
	public String toString() { return getName() + "_1"; }
	public int hashCode() { return (int)(Double.doubleToLongBits(value) >> 32); }
	public double floatValue() { return value; }
	public Object getValue() { return new Float(value); }
	public boolean equals(Object o) {
		return (o instanceof FloatingFunctor) && ((FloatingFunctor)o).value == value;
	}
}

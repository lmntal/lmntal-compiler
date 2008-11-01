package runtime;

/** 浮動小数点数アトム用の1引数ファンクタを表すクラス
 * @author n-kato */

public class FloatingFunctor extends DataFunctor {
	double value;
	public FloatingFunctor(double value) { this.value = value; }
//	public String getName() { return "" + value; }
	public String toString() { return getName() + "_1"; }
	public int hashCode() { return (int)(Double.doubleToLongBits(value) >> 32); }
	public double floatValue() { return value; }
	public Object getValue() { return new Float(value); }
	public boolean equals(Object o) {
		return (o instanceof FloatingFunctor) && ((FloatingFunctor)o).value == value;
	}
	
	public boolean isNumber() { return true; }
	
	public String getName() {
		return Double.toString(value);
	}
	
	public String getQuotedAtomName() {
		if (Env.colorMode) return "\033[0;34m"+getName()+"\033[0m";
		return getName();
	}
}

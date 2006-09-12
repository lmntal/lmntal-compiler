package runtime;

/** 整数アトム用の1引数ファンクタを表すクラス
 * @author n-kato */

public class IntegerFunctor extends DataFunctor {
	int value;
	public IntegerFunctor(int value) {
		this.value = value;
	}
	
	public int hashCode() { return value; }
	public int intValue() { return value; }
	public Object getValue() { return new Integer(value); }
	public boolean equals(Object o) {
		return (o instanceof IntegerFunctor) && ((IntegerFunctor)o).value == value;
	}
	
	public String toString() {
		return getAbbrName() + "_" + getArity();
	}
	
	public String getName() {
		return Integer.toString(value);
	}	
}

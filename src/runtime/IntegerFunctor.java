package runtime;

/** 整数アトム用の1引数ファンクタを表すクラス
 * @author n-kato */

public class IntegerFunctor extends Functor {
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
	
	/**
	 * シンボルファンクタかどうかを調べる．
	 * @return false
	 */
	public boolean isSymbol() {
		return false;
	}

	public String toString() {
		return getAbbrName() + "_" + getArity();
	}
	
	/**
	 * このファンクタがアクティブかどうかを取得する。
	 * @return false
	 */
	public boolean isActive() {
		return false;
	}
	
	public String getName() {
		return Integer.toString(value);
	}
	
	public int getArity() {
		return 1;
	}
}

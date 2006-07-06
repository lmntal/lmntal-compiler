package runtime;

import java.math.BigInteger;

/**
 * 整数アトム用の1引数ファンクタを表すクラス
 * @author inui
 * @since 2006.07.03
 */

public class BigIntegerFunctor extends Functor {
	BigInteger value;
	public BigIntegerFunctor(BigInteger value) { super(value.toString(),1);  this.value = value; }
//	public BigIntegerFunctor(String value) { super(value,1);  this.value = new BigInteger(value); }
	public int hashCode() { return value.hashCode(); }
	public BigInteger intValue() { return value; } //Object を返す方だと cast が面倒
	public Object getValue() { return value; }
	public boolean equals(Object o) {
		return (o instanceof BigIntegerFunctor) && ((BigIntegerFunctor)o).value.equals(value);
	}
	protected String getAbbrName() {
		return value.toString();
	}
}

package runtime;

import java.math.BigDecimal;

/**
 * 浮動小数点数アトム用の1引数ファンクタを表すクラス
 * @author inui
 * @since 2006.07.03
 */

public class BigFloatingFunctor extends Functor {
	BigDecimal value;
	public BigFloatingFunctor(BigDecimal value) { super(value.toString(),1);  this.value = value; }
//	public BigFloatingFunctor(String value) { super(value,1);  this.value = new BigDecimal(value); }
	public String toString() { return getName() + "_1"; }
	public int hashCode() { return value.hashCode(); } //Object を返す方だと cast が面倒
	public BigDecimal floatValue() { return value; }
	public Object getValue() { return value; }
	public boolean equals(Object o) {
		return (o instanceof BigFloatingFunctor) && ((BigFloatingFunctor)o).value.equals(value);
	}
	protected String getAbbrName() {
		return value.toString();
	}
}

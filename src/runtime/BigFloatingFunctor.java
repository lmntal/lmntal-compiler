package runtime;

import java.math.BigDecimal;

/**
 * 浮動小数点数アトム用の1引数ファンクタを表すクラス
 * @author inui
 * @since 2006.07.03
 */

public class BigFloatingFunctor extends ObjectFunctor {
	public BigFloatingFunctor(BigDecimal value) {
		super(value);
	}
	
	public BigDecimal floatValue() {
		return (BigDecimal)data;
	}
	
	public boolean equals(Object o) {
		return (o instanceof BigFloatingFunctor) && ((BigFloatingFunctor)o).data.equals(data);
	}
	protected String getAbbrName() {
		return data.toString();
	}
}

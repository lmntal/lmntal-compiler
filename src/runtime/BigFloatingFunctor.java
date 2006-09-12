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
	
	//多倍長なので省略しない
	protected String getAbbrName() {
		return data.toString();
	}
}

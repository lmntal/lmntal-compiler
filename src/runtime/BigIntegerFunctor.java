package runtime;

import java.math.BigInteger;

/**
 * 整数アトム用の1引数ファンクタを表すクラス
 * @author inui
 * @since 2006.07.03
 */

public class BigIntegerFunctor extends ObjectFunctor {
	public BigIntegerFunctor(BigInteger value) {
		super(value);
	}
	
	public BigInteger intValue() {
		return (BigInteger)data;
	}
	
	//多倍長なので省略しない
	protected String getAbbrName() {
		return data.toString();
	}
}

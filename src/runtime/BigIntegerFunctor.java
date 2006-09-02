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
	
	public boolean equals(Object o) {
		return (o instanceof BigIntegerFunctor) && ((BigIntegerFunctor)o).data.equals(data);
	}
	protected String getAbbrName() {
		return data.toString();
	}
}

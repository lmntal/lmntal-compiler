package runtime;

import util.Util;

/** 定数文字列を表現するための1引数ファンクタを表すクラス
 * todo inlineの扱いがおかしいので何とかする
 * @author n-kato
 * @see ObjectFunctor#getName() */
public class StringFunctor extends Functor {
	public StringFunctor(String data) { super(data, 1);}
	public String getQuotedAtomName() { return getStringLiteralText(getName()); }
	public String getQuotedFunctorName() { return getQuotedAtomName(); }
	public String stringValue() {return getName();}
	public String toString() {
		return Util.quoteString(getName(), '"') + "_" + getArity();
	}
	// 2006/06/28 by kudo
	public boolean equals(Object o){
		if(!(o instanceof StringFunctor))return false;
		//コンストラクタでinternしているので、==で比較できる。
		return (getName() == ((StringFunctor)o).getName());//2006.6.30 by inui
	}
}

package runtime;

import util.Util;

/** 定数文字列を表現するための1引数ファンクタを表すクラス
 * todo inlineの扱いがおかしいので何とかする
 * @author n-kato
 * @see ObjectFunctor#getName() */
public class StringFunctor extends ObjectFunctor {
	public StringFunctor(String data) { super(data); }
	
	public String getQuotedAtomName() {
		//ダブルクオートした名前を返す
		String quotedAtomName = Util.quoteString(getName(), '\"');
		return (Env.colorMode ? "\033[0;31m"+quotedAtomName+"\033[0m" : quotedAtomName);
	}
	
	public String getQuotedFunctorName() { return getQuotedAtomName(); }
	
	public String stringValue() {return getName();}
	
	// 2006/06/28 by kudo
	public boolean equals(Object o){
		if(!(o instanceof StringFunctor))return false;
		return ((StringFunctor)o).data.equals(this.data);
	}
}

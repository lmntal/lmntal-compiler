package runtime;

/** 定数文字列を表現するための1引数ファンクタを表すクラス
 * todo inlineの扱いがおかしいので何とかする
 * @author n-kato
 * @see ObjectFunctor#getName() */
public class StringFunctor extends ObjectFunctor {
	public StringFunctor(String data) { super(data); }
	public String getQuotedAtomName() { return getStringLiteralText(getName()); }
	public String getQuotedFuncName() { return getQuotedAtomName(); }
}
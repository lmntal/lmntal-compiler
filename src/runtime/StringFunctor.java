package runtime;

/** 定数文字列を表現するための1引数ファンクタを表すクラス
 * @author n-kato
 * @see ObjectFunctor#getName() */
public class StringFunctor extends ObjectFunctor {
	public StringFunctor(String data) { super(data); }
	public String getQuotedFuncName() { return getStringLiteralText(data.toString()); }
	public String getQuotedAtomName() { return getStringLiteralText(data.toString()); }
}
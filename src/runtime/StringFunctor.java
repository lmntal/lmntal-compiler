package runtime;

/** 定数文字列を表現するための1引数ファンクタを表すクラス
 * @author n-kato
 * @see ObjectFunctor#getName() */
public class StringFunctor extends ObjectFunctor {
	public StringFunctor(String data) { super(data); }
	public String getQuotedAtomName() {
		if (getName().startsWith("/*inline*/")) return "/*inline*/";
		return getStringLiteralText(getName());
	}
	public String getQuotedFuncName() { return getQuotedAtomName(); }
}
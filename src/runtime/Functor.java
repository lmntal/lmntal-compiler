package runtime;


/**
 * Stringの名前とリンク数の組からなるアトムのFunctor。
 * TODO SymbolFunctorというサブクラスを作ったほうがいいかもしれない。
 */
public class Functor {
	/** 膜の内側の自由リンク管理アトムを表すファンクタ inside_proxy/2 */
	public static final Functor INSIDE_PROXY = new Functor("$in",2,null,"$in_2");
	/** 膜の外側の自由リンク管理アトムを表すファンクタ outside_proxy/2 */
	public static final Functor OUTSIDE_PROXY = new Functor("$out",2,null,"$out_2");
	/** $pにマッチしたプロセスの自由リンクのために一時的に使用されるアトム
	 * を表すファンクタ transient_inside_proxy （通称:star）*/
	public static final Functor STAR = new Functor("$star",2,null,"$star_2");
	
	/** シンボル名。このクラスのオブジェクトの場合は、名前の表示名が格納される。
	 * 空文字列のときは、サブクラスのオブジェクトであることを表す。*/
	private String name;
	/** アリティ（引数の個数）*/
	private int arity;
	/** シンボルファンクタとしてのID。各種メソッドで使うために保持しておく。整合性要注意 */
	private String strFunctor;
	/** ファンクタが所属するモジュール名（明示的に指定されていない場合はnull）*/
	private String path = null;
	
	////////////////////////////////////////////////////////////////
	
	/** シンボルの表示名に対する内部名を取得する。
	 * 現在の仕様では、内部名とは . と $ がエスケープされた名前を表す。*/
	public static String escapeName(String name) {
		name = name.replaceAll("\\.","..");
		name = name.replaceAll("\\$",".\\$");
		return name;
	}
	/** 引数をもつアトムの名前として表示名を印字するための文字列を返す */
	public String getQuotedFunctorName() {
		String text = getAbbrName();
		if (strFunctor.startsWith("$")) return text;	// 臨時
		if (!text.matches("^([a-z0-9][A-Za-z0-9_]*)$")) {
			text = quoteName(text);
		}
		if (path != null) text = path + "." + text;
		return text;
	}
	/** 引数をもたないアトムの名前として表示名を印字するための文字列を返す */
	public String getQuotedAtomName() {
		String text = getAbbrName();
		if (!text.matches("^([a-z0-9][A-Za-z0-9_]*|\\[\\])$")) {
			if (!text.matches("^(-?[0-9]+|[+-]?[0-9]*\\.?[0-9]+([Ee][+-]?[0-9]+)?)$")) {
				text = quoteName(text);
			}
		}
		if (path != null) text = path + "." + text;
		return text;
	}
	/** 指定された文字列を表すシンボルリテラルのテキスト表現を取得する。
	 * 例えば a'b を渡すと 'a''b' が返る。*/
	static final String quoteName(String text) {
		if (text.equals("")) return "\"\"";
		if (text.indexOf('\n') == -1) {
			text = text.replaceAll("'","''");
			text = "'" + text + "'";
			return text;
		}
		return getStringLiteralText(text);
	}
	/** 指定された文字列を表す文字列リテラルのテキスト表現を取得する。
	 * 例えば a"b を渡すと "a\"b" が返る。
	 * <p>StringFunctorクラスのクラスメソッドにするのが正しい。*/
	static final String getStringLiteralText(String text) {
		text = text.replaceAll("\\\\","\\\\\\\\");
		text = text.replaceAll("\"","\\\\\"");
		text = text.replaceAll("\n","\\\\n");
		text = text.replaceAll("\t","\\\\t");
		text = text.replaceAll("\f","\\\\f");
		text = text.replaceAll("\r","\\\\r");
		text = "\"" + text + "\"";
		return text;
	}
	////////////////////////////////////////////////////////////////
	
	/** モジュール名なしのファンクタを生成する。
	 * @param name シンボル名 */
	public Functor(String name, int arity) {
		this(name,arity,null);
	}	
	/** 指定されたモジュール名を持つファンクタを生成する。
	 * @param name シンボル名（モジュール名を指定してはいけない）
	 * @param arity 引数の個数
	 * @param path モジュール名（またはnull）
	 */
	public Functor(String name, int arity, String path) {
		this.name  = name;
		this.arity = arity;
		this.path  = path;
		name = escapeName(name);
		if (path != null) name = escapeName(path) + "." + name;
		// == で比較できるようにするためにinternしておく。
		strFunctor = (name + "_" + arity).intern();
	}
	private Functor(String name, int arity, String path, String strFunctor) {
		this.name  = name;
		this.arity = arity;
		this.path  = path;
		this.strFunctor = strFunctor;
	}

	////////////////////////////////////////////////////////////////

	/** 適切に省略された表示名を取得 */
	public String getAbbrName() {
		String full = getName();
		return full.length() > 14 ? full.substring(0, 12) + ".." : full;
	}
	/** シンボル名を取得する。
	 * @return nameフィールドの値。サブクラスのオブジェクトのときそのときに限り空文字列が返る。*/
	public final String getSymbolName() {
		return name;
	}
	/** シンボルファンクタとしてのIDを取得する。
	 * @return strFunctorフィールドの値。*/
	public final String getSymbolFunctorID() {
		return strFunctor;
	}
	/** 名前の表示名を取得する。サブクラスは空文字列が出力されないようにオーバーライドすること。*/
	public String getName() {
		return name;
	}
	/** アリティを取得する。*/
	public int getArity() {
		return arity;
	}
	/** このファンクタがアクティブかどうかを取得する。*/
	public boolean isActive() {
		// （仮）
		if (getSymbolFunctorID().equals("n_1")) return false;
		return getSymbolFunctorID().matches("^[a-z].*$");
	}
	/** このクラスのオブジェクトかどうかを調べる。*/
	public boolean isSymbol() {
		return !name.equals("");
	}
	/** オーバーライドしない限り getAbbrName()+"_"+getArity() を返す */
	public String toString() {
		return getAbbrName() + "_" + getArity();
	}
	public int hashCode() {
		return strFunctor.hashCode();
	}
	public boolean equals(Object o) {
		// コンストラクタでinternしているので、==で比較できる。
		// 引数oがFunctorのサブクラスの場合、falseを返す。
		return ((Functor)o).strFunctor == this.strFunctor;
	}
	public String getPath() {
		return path;
	}
}

//////////////////////////////

/*
class VectorFunctor extends ObjectFunctor {
	public VectorFunctor() { super(new java.util.ArrayList()); }
	public String toString() {
		return "{ ... }";
	}
}
*/
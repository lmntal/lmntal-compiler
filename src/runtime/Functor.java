package runtime;

import java.io.*;

import compile.parser.SrcName;


/**
 * Stringの名前とリンク数の組からなるアトムのFunctor。
 * todo SymbolFunctorというサブクラスを作ったほうがいいかもしれない。
 */
public class Functor implements Serializable {
	//**注意**：特殊なFunctorを追加した場合、readObjectメソッドを変更する事。
	/** 膜の内側の自由リンク管理アトムを表すファンクタ inside_proxy/2 */
	public static final Functor INSIDE_PROXY = new SpecialFunctor("$in",2);
	/** 膜の外側の自由リンク管理アトムを表すファンクタ outside_proxy/2 */
	public static final Functor OUTSIDE_PROXY = new SpecialFunctor("$out",2);
	/** $pにマッチしたプロセスの自由リンクのために一時的に使用されるアトム
	 * を表すファンクタ transient_inside_proxy （通称:star）*/
	public static final Functor STAR = new SpecialFunctor("$star",2);
	
	/** シンボル名。このクラスのオブジェクトの場合は、名前の表示名が格納される。
	 * 常に intern した値を格納する。
	 * 空文字列のときは、サブクラスのオブジェクトであることを表す。*/
	private String name;
	/** アリティ（引数の個数）*/
	protected int arity;
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
	public Object getValue() {
		return name;
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
		this.name = name.intern();
		this.arity = arity;
		if (path != null)
			this.path = path.intern();
	}

	/**
	 * 直列化復元時に呼ばれる。
	 * author mizuno
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		name = name.intern();
		if (path != null)
			path = path.intern();
	}
	////////////////////////////////////////////////////////////////

	/** 適切に省略された表示名を取得 */
	public String getAbbrName() {
		String full = getName();
		return full.length() > Env.printLength ? full.substring(0, Env.printLength-2) + ".." : full;
	}
	/** シンボル名を取得する。
	 * @return nameフィールドの値。サブクラスのオブジェクトのときそのときに限り空文字列が返る。*/
	public final String getSymbolName() {
		return name;
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
		if (arity == 0) return true;
		if (name.equals("")) return false;
		char c = name.charAt(0);
		return c >= 'a' && c <= 'z';
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
		return (path == null ? 0 : path.hashCode()) + name.hashCode() + arity;
	}
	public boolean equals(Object o) {
		// コンストラクタでinternしているので、==で比較できる。
		// 引数oがFunctorのサブクラスの場合、falseを返す。
		Functor f = (Functor)o;
		return f.path == path && f.name == name && f.arity == arity;
	}
	public String getPath() {
		return path;
	}
	
	////////////////////////////////////////////////////////////////
	//
	// serialize/deserialize/build
	//
	
	// todo pathやStringFunctorを考慮に入れる
	
	public String serialize() {
		return getName() + "_" + getArity(); // todo 将来は、直列化を使う
	}
	public static Functor deserialize(String text) {
		int loc = text.lastIndexOf('_');
		String name = "err";
		int arity = 0;
		try {
			name = text.substring(0,loc);
			arity = Integer.parseInt(text.substring(loc + 1));
		} catch (Exception e) {}
		if (arity == 2) {
			if (name.equals("$in"))  return Functor.INSIDE_PROXY;
			if (name.equals("$out")) return Functor.OUTSIDE_PROXY;
		}
		return build(name,arity,SrcName.PLAIN);
	}
	
	/** 指定されたファンクタを生成する。（仮）
	 * <p>compile.parser.LMNParser.addSrcAtomToMemから移動してきた。
	 * @param name 名前トークンの表す文字列
	 * @param arity ファンクタのアリティ
	 * @param nametype 名前トークンの種類（compile.parser.SrcNameで定義される定数のいずれか）*/
	public static Functor build(String name, int arity, int nametype) {
		String path = null;
		if (nametype == SrcName.PATHED) {
			int pos = name.indexOf('.');
			path = name.substring(0, pos);
			name = name.substring(pos + 1);
		}
		if (arity == 1 && path == null) {
			if (nametype == SrcName.PLAIN || nametype == SrcName.SYMBOL) {
				try {
					return new IntegerFunctor(Integer.parseInt(name));
				}
				catch (NumberFormatException e) {}
				try {
					return new FloatingFunctor(Double.parseDouble(name));
				}
				catch (NumberFormatException e2) {}
			}
			else if (nametype == SrcName.STRING || nametype == SrcName.QUOTED) {
				return new StringFunctor(name); // new runtime.ObjectFunctor(name);
			}
		}
		return new Functor(name, arity, path);
	}
}

class SpecialFunctor extends Functor {
	private String name;
	SpecialFunctor(String name, int arity) {
		super("", arity);
		this.name = name;
	}
	public int hashCode() {
		return name.hashCode() + arity;
	}
	public boolean equals(Object o) {
		return this == o;
	}
	public String getName() {
		return name; 
	}

	/**
	 * 直列化復元時に呼ばれる。
	 * author mizuno
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		name = name.intern();
	}

	/** 引数をもつアトムの名前として表示名を印字するための文字列を返す */
	public String getQuotedFunctorName() {
		return getAbbrName();
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
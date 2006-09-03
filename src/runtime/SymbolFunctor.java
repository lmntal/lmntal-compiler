package runtime;

import java.io.IOException;

import util.Util;

/**
 * 通常アトム用のファンクタを表すクラス
 * @author inui
 * @since 2006-08-30
 */
public class SymbolFunctor extends Functor {
	/**
	 * シンボル名。このクラスのオブジェクトの場合は、名前の表示名が格納される。 常に intern した値を格納する。
	 */
	private String name;

	/** アリティ（引数の個数） */
	private int arity;
	
	/**
	 * ファンクタが所属するモジュール名．
	 * 明示的に指定されていない場合はnull（空文字列ではいけない）．
	 */
	private String path;
	
	/**
	 * モジュール名なしのファンクタを生成する。
	 * 
	 * @param name シンボル名
	 */
	public SymbolFunctor(String name, int arity) {
		this(name, arity, null);
	}

	/**
	 * 指定されたモジュール名を持つファンクタを生成する。
	 * 
	 * @param name
	 *            シンボル名（モジュール名を指定してはいけない）
	 * @param arity
	 *            引数の個数
	 * @param path
	 *            モジュール名（またはnull）
	 */
	public SymbolFunctor(String name, int arity, String path) {
		this.arity = arity;
		this.name = name.intern();
		if (path != null)
			this.path = path.intern();
	}
	
	public boolean equals(Object o) {
		// コンストラクタでinternしているので、==で比較できる。
		if (!(o instanceof SymbolFunctor)) return false;
		SymbolFunctor f = (SymbolFunctor)o;
		return f.path == path && f.name == name && f.arity == arity;
	}
	
	/**
	 * シンボルファンクタかどうかを調べる．
	 * @return true
	 */
	public boolean isSymbol() {
		return true;
	}
	
	/**
	 * このファンクタがアクティブかどうかを取得する。
	 * @return アクティブなら true
	 */
	public boolean isActive() {
		if (name.equals(""))
			return false;
		if (equals(CONS))
			return false;
		if (equals(NIL))
			return false;
		if (name.equals("thread"))
			return false;
		return true;
	}
	
	/**
	 * ファンクタの値を返す
	 * @return ファンクタの名前
	 */
	public Object getValue() {
		return name;
	}
	
	public String toString() {
		if (Env.compileonly)
			return (path == null ? "" : Util.quoteString(path, '\'') + ".") + Util.quoteString(name, '\'') + "_" + getArity();
		return getQuotedFunctorName() + "_" + getArity();
	}
	
	/**
	 * ハッシュコードを計算する
	 * @return ハッシュコード
	 */
	public int hashCode() {
		return (path == null ? 0 : path.hashCode()*2) + name.hashCode() + arity;
	}
	
	/**
	 * ファンクタが所属するモジュール名を返す
	 * @return ファンクタが所属するモジュール名を返す
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * 直列化復元時に呼ばれる。
	 * @author mizuno
	 */
	protected void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		name = name.intern();
	}
	
	public String getName() {
		return name;
	}
	
	public int getArity() {
		return arity;
	}
}

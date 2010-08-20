package runtime;

import java.io.Serializable;

import util.Util;

import compile.parser.SrcName;

/**
 * Stringの名前とリンク数の組からなるアトムのFunctorを表す抽象クラス。
 * このクラスは名前とリンク数のフィールドは持っていないので，
 * サブクラスはこれらの情報を取得する getName, getArity を実装する．
 * オブジェクトの生成は各サブクラスを new する他に build メソッドを使うことが出来る．
 */
public abstract class Functor implements Serializable {
	// **注意**：特殊なFunctorを追加した場合、readObjectメソッドを変更する事。
	
	/** 膜の内側の自由リンク管理アトムを表すファンクタ $in/2 */
	public static final Functor INSIDE_PROXY = new SpecialFunctor(SpecialFunctor.INSIDE_PROXY_NAME, 2);

	/** 膜の外側の自由リンク管理アトムを表すファンクタ $out/2 */
	public static final Functor OUTSIDE_PROXY = new SpecialFunctor(SpecialFunctor.OUTSIDE_PROXY_NAME, 2);

	/**
	 * $pにマッチしたプロセスの自由リンクのために一時的に使用されるアトム を表すファンクタ transient_inside_proxy
	 * （通称:star）
	 */
	public static final Functor STAR = new SpecialFunctor("$star", 2);
	
	/**
	 * cons アトムを表すファンクタ ./3
	 */
	public static final Functor CONS = new SymbolFunctor(".", 3);

	/**
	 * nil アトムを表すファンクタ []/1
	 */
	public static final Functor NIL = new SymbolFunctor("[]", 1);

	/**
	 * 単一化を意味するファンクタ。=/2
	 */
	public static final Functor UNIFY = new SymbolFunctor("=", 2);

	// //////////////////////////////////////////////////////////////

	/**
	 * 引数をもつアトムの名前として表示名を印字するための文字列を返す。 通常の名前以外（数値や記号）の場合、クォートして返す。
	 */
	public String getQuotedFunctorName() {
		return quoteFunctorName(getAbbrName());
	}

	/**
	 * 改行文字を取り除いたファンクタ名を返す
	 * @return 改行文字を取り除いたファンクタ名
	 */
	public String getQuotedFullyFunctorName() {
		// \rや\nがparseの際に邪魔になるため
		return quoteFunctorName(getName()).replaceAll("\\\\r", "").replaceAll("\\\\n", "");
	}

	private String quoteFunctorName(String text) {
		if (Env.verbose > Env.VERBOSE_SIMPLELINK || ( Env.dump2 && (!Dumper2.isInfixNotation()||!Dumper2.isAbbrAtom() )) ) {
			if (!text.matches("^([a-z0-9][A-Za-z0-9_]*)$")) {
				text = quoteName(text);
			}
		} else {
			if (!text.matches("^([a-z0-9-\\+][A-Za-z0-9_]*)$")) {
				text = quoteName(text);
			}
		}
		if (getPath() != null)
			text = getPath() + "." + text;
		return text;
	}
	
	/**
	 * 引数をもたないアトムの名前として表示名を印字するための文字列を返す。
	 * 通常の名前以外のもののうち、リスト構成要素や数値以外のものはクォートして返す。
	 */
	public abstract String getQuotedAtomName();

	/**
	 * クオートされた省略しないアトム名を返す
	 * @return クオートされた省略しないアトム名
	 */
	public String getQuotedFullyAtomName() {
		// \rや\nがparseの際に邪魔になるため
		return quoteAtomName(getName()).replaceAll("\\\\r", "").replaceAll("\\\\n", "");
	}

	protected String quoteAtomName(String text) {
		if (!text.matches("^([a-z0-9][A-Za-z0-9_]*|\\[\\])$")) {
			if (!text
					.matches("^(-?[0-9]+|[+-]?[0-9]*\\.?[0-9]+([Ee][+-]?[0-9]+)?)$")) {
				text = quoteName(text);
			}
		}
		if (getPath() != null)
			text = getPath() + "." + text;
		return text;
	}
	
	/**
	 * 指定された文字列を表すシンボルリテラルのテキスト表現を取得する。 例えば a'b を渡すと 'a\'b' が返る。
	 */
	static final String quoteName(String text) {
		return Util.quoteString(text, '\'');
	}

	// //////////////////////////////////////////////////////////////

	/** 適切に省略された表示名を取得 */
	protected String getAbbrName() {
		String full = getName();
		return full.length() > Env.printLength ? full.substring(0,
				Env.printLength - 2)
				+ ".." : full;
	}
	
	/**
	 * ファンクタが所属するモジュール名を返す
	 * （SymbolFunctor 以外は 常に null を返す）
	 * @return ファンクタが所属するモジュール名
	 */
	public String getPath() {
		return null;
	}

	public String toString() {
		if (Env.compileonly)
			return Util.quoteString(getName(), '\'') + "_" + getArity();
		return getQuotedFunctorName() + "_" + getArity();
	}
	
	// //////////////////////////////////////////////////////////////
	//
	// serialize/deserialize/build
	//

	// todo pathやStringFunctorを考慮に入れる

	public String serialize() {
		return getName() + "_" + getArity(); // TODO 将来は、直列化を使う
	}

	public static Functor deserialize(String text) {
		int loc = text.lastIndexOf('_');
		String name = "err";
		int arity = 0;
		try {
			name = text.substring(0, loc);
			arity = Integer.parseInt(text.substring(loc + 1));
		} catch (Exception e) {
		}
		if (arity == 2) {
			if (name.equals(SpecialFunctor.INSIDE_PROXY_NAME))
				return Functor.INSIDE_PROXY;
			if (name.equals(SpecialFunctor.OUTSIDE_PROXY_NAME))
				return Functor.OUTSIDE_PROXY;
		}
		return build(name, arity, SrcName.PLAIN);
	}

	/**
	 * 指定されたファンクタを生成する。（仮）
	 * <p>
	 * compile.parser.LMNParser.addSrcAtomToMemから移動してきた。
	 * 
	 * @param name
	 *            名前トークンの表す文字列
	 * @param arity
	 *            ファンクタのアリティ
	 * @param nametype
	 *            名前トークンの種類（compile.parser.SrcNameで定義される定数のいずれか）
	 */
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
					int radix = 10;
					if (name.matches("\\+[0-9]+")) {
						name = name.substring(1);
					} else if (name.matches("\\+0x[0-9a-fA-F]+")) {//+16進 2006.6.26 by inui
						name = name.substring(3);
						radix = 16;
					} else if (name.matches("0x[0-9a-fA-F]+")) {//16進 2006.6.26 by inui
						name = name.substring(2);
						radix = 16;
					}
					return new IntegerFunctor(Integer.parseInt(name, radix));
				} catch (NumberFormatException e) {
				}
				try {
					return new FloatingFunctor(Double.parseDouble(name));
				} catch (NumberFormatException e2) {
				}
			} else if (nametype == SrcName.STRING || nametype == SrcName.QUOTED) {
				return new StringFunctor(name); // new
				// runtime.ObjectFunctor(name);
			}
		}
		return new SymbolFunctor(name, arity, path);
	}
	
	////////////////////////////////////////////////
	// 抽象メソッド

	public abstract int hashCode();

	public abstract boolean equals(Object o);

	/**
	 * シンボルファンクタかどうかを判定する
	 * @return シンボルを表すファンクタなら true 
	 */
	public abstract boolean isSymbol();

	/**
	 * inside_proxy かどうかを返す．SpecialFunctor 以外は常に false
	 * @return inside_proxy なら true
	 */
	public abstract boolean isInsideProxy();
	
	/**
	 * outside_proxy かどうかを返す．SpecialFunctor 以外は常に false
	 * @return outside_proxy なら true
	 */
	public abstract boolean isOutsideProxy();
	
	/**
	 * このファンクタがアクティブかどうかを判定する。
	 * @return アクティブなら true
	 */
	public abstract boolean isActive();
	
	/**
	 * このファンクタが数値アトムかどうかを判定する。
	 * @return 数値アトムなら true
	 */
	public abstract boolean isNumber();
	
	/**
	 * このファンクタが int 型のアトムかどうかを判定する。
	 * @return int 型のアトムなら true
	 */
	public abstract boolean isInteger();
	
	/**
	 * このファンクタの値を返す
	 * @return ファンクタの値
	 */
	public abstract Object getValue();
	
	/** 名前の表示名を取得する。サブクラスは空文字列が出力されないようにオーバーライドすること。 */
	public abstract String getName();

	/** アリティを取得する。 */
	public abstract int getArity();


}
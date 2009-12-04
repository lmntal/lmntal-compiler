package runtime;

import java.io.IOException;

/**
 * 特殊なファンクタ (inside_proxy, outside_proxy, star) を表すクラス
 */
public class SpecialFunctor extends Functor {
	static final String OUTSIDE_PROXY_NAME = "$out".intern();
	static final String INSIDE_PROXY_NAME = "$in".intern();
	
	private String name;
	private int arity;
	private int kind;
	
	SpecialFunctor(String name, int arity) {
		this(name, arity, 0);
	}
	public SpecialFunctor(String name, int arity, int kind) {
		this.name = name.intern();
		this.arity = arity;
		this.kind = kind;
	}
	public boolean equals(Object o) {
		if(o instanceof SpecialFunctor) {
			SpecialFunctor f = (SpecialFunctor)o;
			return name == f.name && kind == f.kind;
		}
		return false;
	}
	
	/**
	 * outside_proxy かどうかを判定する
	 * @return outside_proxy なら true
	 */
	public boolean isOutsideProxy(){
		return name == OUTSIDE_PROXY_NAME;
	}
	
	/**
	 * inside_proxy かどうかを判定する
	 * @return outside_proxy なら true
	 */
	public boolean isInsideProxy(){
		return name == INSIDE_PROXY_NAME;
	}
	
	/**
	 * ファンクタ名を返す
	 * @return ファンクタ名を返す
	 */
	public String getName() {
		return name + (kind==0 ? "" : ""+kind); 
	}
	/**
	 * 引数つきのファンクタ名を返す
	 * @return 引数つきのファンクタ名
	 */
	public String toString() {
		return name + (kind==0 ? "" : ""+kind) + "_" + getArity();
	}
	/**
	 * 膜のタイプを返す
	 * @return 膜のタイプ
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * 直列化復元時に呼ばれる。
	 * author mizuno
	 */
	protected void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		name = name.intern();
	}

	/** 引数をもつアトムの名前として表示名を印字するための文字列を返す */
	public String getQuotedFunctorName() {
		return getAbbrName();
	}

	@Override
	public String getQuotedAtomName() {
		return getAbbrName();
	}
	
	/**
	 * シンボルファンクタかどうかを調べる．
	 * @return false
	 */
	public boolean isSymbol() {
		return false;
	}
	
	/**
	 * このファンクタがアクティブかどうかを取得する。
	 * @return false
	 */
	public boolean isActive() {
		return false;
	}
	
	/**
	 * このファンクタが数値かどうかを取得する。
	 * @return false
	 */
	public boolean isNumber() {
		return false;
	}
	/**
	 * このファンクタが int 型かどうかを取得する。
	 * @return false
	 */
	public boolean isInteger() {
		return false;
	}
	
	/**
	 * ファンクタの値を返す
	 * @return ファンクタの名前
	 */
	public Object getValue() {
		return name;
	}
	
	/**
	 * ハッシュコードを計算する
	 * @return ハッシュコード
	 */
	public int hashCode() {
		return getName().hashCode() + getArity();
	}
	
	public int getArity() {
		return arity;
	}
}
package runtime;

/**
 * Stringの名前とリンク数の組からなるアトムのFunctor。
 */
public class Functor {
	//TODO 自由リンク管理アトムの名前が通常アトムと同じにならないようにする
	/** 自由リンク管理アトム */
	public static final Functor INSIDE_PROXY = new Functor("$inside_proxy", 2);
	/** 自由リンク管理アトム */
	public static final Functor OUTSIDE_PROXY = new Functor("$outside_proxy", 2);
	/** $pの移動時にできるアトム。temporary_proxy? */
	public static final Functor STAR = new Functor("$star", 2);
	
	private String name;
	private int arity;
	/** 各種メソッドで使うために保持しておく。整合性要注意 */
	private String strFunctor;
	public Functor(String name, int arity) {
		this.name = name;
		this.arity = arity;
		// == で比較できるようにするためにinternしておく。
		strFunctor = (name + "_" + arity).intern();
	}
	public String getName() {
		return name;
	}
	public int getArity() {
		return arity;
	}
	public String toString() {
		return strFunctor;
	}
	public int hashCode() {
		return strFunctor.hashCode();
	}
	public boolean equals(Object o) {
		// コンストラクタでinternしているので、==で比較できる。
		return ((Functor)o).strFunctor == this.strFunctor;
	}
}

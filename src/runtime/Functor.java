package runtime;

/**
 * Stringの名前とリンク数の組からなるアトムのFunctor。
 */
public class Functor {
	//TODO 自由リンク管理アトムの名前が通常アトムと同じにならないようにする
	/** 膜の内側の自由リンク管理アトムを表すファンクタ inside_proxy/2 */
	public static final Functor INSIDE_PROXY = new Functor("$inside_proxy", 2);
	/** 膜の外側の自由リンク管理アトムを表すファンクタ outside_proxy/2 */
	public static final Functor OUTSIDE_PROXY = new Functor("$outside_proxy", 2);
	/** $pにマッチしたプロセスの自由リンクのために一時的に使用されるアトム
	 * を表すファンクタ temporary_inside_proxy （通称:star）*/
	public static final Functor STAR = new Functor("$transient_inside_proxy", 2);
	
	private String name;	// "" は予約
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
		// 引数oがFunctorのサブクラスの場合、falseを返す。
		return ((Functor)o).strFunctor == this.strFunctor;
	}
}

//////////////////////////////

class IntegerFunctor extends Functor {
	int value;
	public IntegerFunctor(int value) { super("",1);  this.value = value; }
	public String toString() { return "" + value; }
	public int hashCode() { return value; }
	public boolean equals(Object o) {
		return (o instanceof IntegerFunctor) && ((IntegerFunctor)o).value == value;
	}
}
class ObjectFunctor extends Functor {
	Object data;
	public ObjectFunctor(Object data) { super("",1);  this.data = data; }
	public String toString() { return data.toString(); }
	public int hashCode() { return data.hashCode(); }
	public boolean equals(Object o) {
		return o.getClass() == getClass() && data.equals(((ObjectFunctor)o).data);
	}
}
/*
class VectorFunctor extends ObjectFunctor {
	public VectorFunctor() { super(new java.util.ArrayList()); }
	public String toString() {
		return "{ ... }";
	}
}
*/
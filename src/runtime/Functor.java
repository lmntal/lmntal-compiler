package runtime;


/**
 * Stringの名前とリンク数の組からなるアトムのFunctor。
 */
public class Functor {
	//TODO 自由リンク管理アトムの名前が通常アトムと同じにならないようにする
	/** 膜の内側の自由リンク管理アトムを表すファンクタ inside_proxy/2 */
	public static final Functor INSIDE_PROXY = new Functor("$inside", 2);
	/** 膜の外側の自由リンク管理アトムを表すファンクタ outside_proxy/2 */
	public static final Functor OUTSIDE_PROXY = new Functor("$outside", 2);
	/** $pにマッチしたプロセスの自由リンクのために一時的に使用されるアトム
	 * を表すファンクタ temporary_inside_proxy （通称:star）*/
	public static final Functor STAR = new Functor("$transient_inside_proxy", 2);
	
	private String name;	// "" は予約
	private int arity;
	/** 各種メソッドで使うために保持しておく。整合性要注意 */
	private String strFunctor;
//	/** ファンクタ表記中の所属膜名（明示的に指定されていない場合はnull）*/
//	public String path = null;

//	/**
//	 * ファンクタ表記中の所属膜名。ソースコードで明示的に指定されたらそれ。
//	 * 指定されなかったら、デフォルトとしてそのファンクタが実際に所属する膜。
//	 * @deprecated
//	 */
//	public String path;
//
//	/**
//	 * 所属膜が明示的に指定されなかった時に真。
//	 */
//	public boolean pathFree;
	
	public Functor(String name, int arity) {
//		this(name, arity, null);
//	}
//	public Functor(String name, int arity, compile.structure.Membrane m) {

//		int pos = name.indexOf('.');
//		if(pos!=-1) {
//			this.path = name.substring(0, pos);
//			if(path.indexOf('\n')!=-1 || path.indexOf('/')!=-1 || path.indexOf('*')!=-1) this.path=null;
//		}
		this.name = name;
		this.arity = arity;
		// == で比較できるようにするためにinternしておく。
		strFunctor = (name + "_" + arity).intern();
	}
	/** 適切に省略された表示名を取得 */
	public String getAbbrName() {
		String full = getName();
		return full.length() > 10 ? full.substring(0, 10) : full;
	}
	/** 名前の内部名を取得する。
	 * @return nameフィールドの値。サブクラスならば空文字列が返る。*/
	public final String getInternalName() {
		return name;
	}
	/** 名前の表示名を取得する。サブクラスは空文字列が出力されないようにオーバーライドすること。*/
	public String getName() {
		return name;
	}
	public int getArity() {
		return arity;
	}
	/**
	 * この名前がアクティブならtrue
	 */
	boolean isActive() {
		//とりあえず全部アクティブ
		return true;
	}
	public String toString() {
		return strFunctor.length() > 10 ? strFunctor.substring(0, 10) : strFunctor;
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

class ObjectFunctor extends Functor {
	Object data;
	public ObjectFunctor(Object data) { super("",1);  this.data = data; }
	public String toString() { return data.toString(); }
	public int hashCode() { return data.hashCode(); }
	public Object getObject() { return data; }
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
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
	
	/**
	 * ファンクタ表記中の所属膜名。ソースコードで明示的に指定されたらそれ。
	 * 指定されなかったら、デフォルトとしてそのファンクタが実際に所属する膜。
	 */
	public String   path;
	
	/**
	 * 所属膜が明示的に指定されなかった時に真。
	 */
	public boolean pathFree;
	
	public Functor(String name, int arity) {
		this(name, arity, null);
	}
	public Functor(String name, int arity, compile.structure.Membrane m) {
		// 名前空間
		int pos = name.indexOf('.');
		if(pos!=-1) {
			this.path = name.substring(0, pos);
			this.name = name.substring(pos+1);
		} else {
			this.pathFree = true;
			if(m!=null) this.path = m.name;
			this.name = name;
		}
		//Env.p("new Fun "+path+"  "+name+" "+m);
		this.arity = arity;
		// == で比較できるようにするためにinternしておく。
		strFunctor = (name + "_" + arity).intern();
	}
	/** 適切に省略された名前を取得 */
	public String getAbbrName() {
		String full = path==null ? name : path+"."+name;
		return full.length() > 10 ? full.substring(0, 10) : full;
	}
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

class IntegerFunctor extends Functor {
	int value;
	public IntegerFunctor(int value) { super("",1);  this.value = value; }
	public String toString() { return "" + value; }
	public int hashCode() { return value; }
	public int intValue() { return value; }
	public boolean equals(Object o) {
		return (o instanceof IntegerFunctor) && ((IntegerFunctor)o).value == value;
	}
	// builtin呼び出し用（計画中）
	// 注意：実際には整数演算は組み込み命令にコンパイルされるため、これらは使われない。
	// また、ガードではbuiltinは使えないと思われるため、ltなどは無意味かもしれないし、
	// 戻り値もvoidでよいかもしれない。
	public static boolean builtin__2B(Membrane mem, Link[] links) { // "+"
		int x = ((IntegerFunctor)links[0].getAtom().getFunctor()).value;
		int y = ((IntegerFunctor)links[1].getAtom().getFunctor()).value;
		Atom atom = mem.newAtom(new IntegerFunctor(x+y));
		mem.inheritLink(atom,0,links[2]);
		return true;
	}
	public static boolean builtin__2F(Membrane mem, Link[] links) { // "/"
		int x = ((IntegerFunctor)links[0].getAtom().getFunctor()).value;
		int y = ((IntegerFunctor)links[1].getAtom().getFunctor()).value;
		if (y == 0) return false;
		Atom atom = mem.newAtom(new IntegerFunctor(x/y));
		mem.inheritLink(atom,0,links[2]);
		return true;
	}
	public static boolean builtin__3C(Membrane mem, Link[] links) { // "<"
		int x = ((IntegerFunctor)links[0].getAtom().getFunctor()).value;
		int y = ((IntegerFunctor)links[1].getAtom().getFunctor()).value;
		return x < y;
	}
	public static boolean builtin_abs(Membrane mem, Link[] links) { // "abs"
		int x = ((IntegerFunctor)links[0].getAtom().getFunctor()).value;
		int y = (x >= 0 ? x : -x);
		Atom atom = mem.newAtom(new IntegerFunctor(y));
		mem.inheritLink(atom,0,links[1]);
		return true;
	}
}

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
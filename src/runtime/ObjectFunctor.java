package runtime;

/** オブジェクトへの参照を保持するファンクタ。
 * このクラスをSymbolFunctorの基底クラスにした方がよさそう
 * 分散環境で転送される可能性がある場合、オブジェクトはSerializableインターフェースを実装する必要がある。
 * @author n-kato */
public class ObjectFunctor extends Functor {
	Object data;
	public ObjectFunctor(Object data) { super("",1);  this.data = data; }
	public int hashCode() { return data.hashCode(); }
	public Object getObject() { return data; }
	public Object getValue() { return data; }
	public boolean equals(Object o) {
//		return o.getClass() == getClass() && data.equals(((ObjectFunctor)o).data);
		
		//2006.6.26 by inui
		try {
			return (o.getClass() == getClass()) && isSubclass(Class.forName(data.toString()), Class.forName(((ObjectFunctor)o).data.toString()));
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	public String getName() { return data.toString(); }
	
	//c1がc2のサブクラスかどうか判定します 2006.6.26 by inui
	private static boolean isSubclass(Class c1, Class c2) {
		if (c1.equals(c2)) return true;
		if (c1.equals(Object.class)) return false;
		return isSubclass(c1.getSuperclass(), c2);
	}
}
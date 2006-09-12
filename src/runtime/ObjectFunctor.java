package runtime;

/** オブジェクトへの参照を保持する1引数ファンクタ。
 * <strike>分散環境で転送される可能性がある場合、オブジェクトはSerializableインターフェースを実装する必要がある。</strike>
 * @author n-kato */
public class ObjectFunctor extends DataFunctor {
	Object data;
	public ObjectFunctor(Object data) {
		this.data = data;
	}
	
	public int hashCode() { return data.hashCode(); }
	/**
	 * このファンクタが保持しているオブジェクトを取得します
	 * @return このファンクタが保持しているオブジェクト
	 */
	public Object getObject() { return data; }
	/**
	 * このファンクタが保持しているオブジェクトを取得します
	 * @return このファンクタが保持しているオブジェクト
	 */
	public Object getValue() { return data; }
	
	public boolean equals(Object o) {
		return o.getClass() == getClass() && data.equals(((ObjectFunctor)o).data);
	}
	
	/**
	 * このファンクタの名前を返す
	 * @return 保持しているオブジェクトの名前
	 */
	public String getName() {
		return data.toString();
	}
	
	/**
	 * c1がc2のサブクラスかどうか判定します
	 * @since 2006.6.26
	 * @author inui
	 */
	public static boolean isSubclass(Class c1, Class c2) {
//		System.err.println(c1+","+c2);
		if (c1.equals(c2)) return true;
		if (c1.equals(Object.class)) return false;
		return isSubclass(c1.getSuperclass(), c2);
	}
}
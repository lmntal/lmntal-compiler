package runtime;

/** オブジェクトへの参照を保持するファンクタ。
 * このクラスをSymbolFunctorの基底クラスにした方がよさそう
 * @author n-kato */
public class ObjectFunctor extends Functor {
	Object data;
	public ObjectFunctor(Object data) { super("",1);  this.data = data; }
	public int hashCode() { return data.hashCode(); }
	public Object getObject() { return data; }
	public Object getValue() { return data; }
	public boolean equals(Object o) {
		return o.getClass() == getClass() && data.equals(((ObjectFunctor)o).data);
	}
	public String getName() { return data.toString(); }
}
package runtime;

/** オブジェクトへの参照を保持するファンクタ
 * @author n-kato */
public class ObjectFunctor extends Functor {
	Object data;
	public ObjectFunctor(Object data) { super("",1);  this.data = data; }
	public int hashCode() { return data.hashCode(); }
	public Object getObject() { return data; }
	public boolean equals(Object o) {
		return o.getClass() == getClass() && data.equals(((ObjectFunctor)o).data);
	}
	public String getName() { return data.toString(); }
	// 以下2つは、本来はStringFunctor固有の出力方法のはず。やはりFunctorFactoryを作ろう。
	public String getQuotedFuncName() { return getStringLiteralText(data.toString()); }
	public String getQuotedAtomName() { return getStringLiteralText(data.toString()); }
}
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
	
	@Override
	public String getQuotedAtomName() {
		if (Env.colorMode) return "\033[0;35m'"+data.getClass().getSimpleName()+"'\033[0m";
		return "'"+data.getClass().getSimpleName()+"'";
	}

	/**
	 * このファンクタの名前を返す
	 * @return 保持しているオブジェクトの名前
	 */
	public String getName() {
		return data.toString();
	}
	
	public boolean isNumber() { return false; }
	public boolean isInteger() { return false; }
}
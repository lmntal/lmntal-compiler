package compile.structure;

// TODO このファイルは廃止

/** ソースコード中での型付きプロセス文脈出現を表すクラス */
final public class TypedProcessContext extends Context {
	/** この型付きプロセス文脈の自由リンク（こちら側） */
	public LinkOccurrence freeLink;
	/** この型付きプロセス文脈の名前 */
	protected String typedName;
	/** ソース出現 */
	public TypedProcessContext src;
	/** コンストラクタ */
	public TypedProcessContext(Membrane mem, String name, LinkOccurrence freeLink) {
		super(mem,"",1);	// 1はバグの元に違いない
		this.typedName = name;
		this.freeLink = freeLink;
	}
	public String getName() {
		return "$" + typedName;
	}
	public String toString() {
		return "$" + typedName + "[" + freeLink + "]";
	}
}

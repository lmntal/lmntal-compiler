package compile.structure;

/**
 * ソースコード中のプロセス文脈出現、型付きプロセス文脈出現、またはルール文脈出現を表す抽象クラス。
 * todo リンクが張られるためAtomのサブクラスになっているが、不自然なのでいずれ修正すべきである。*/

public abstract class Context extends Atom {
	/** コンテキストの限定名 */
	protected String qualifiedName;	
	/** ヘッドとボディでちょうど2回出現する場合に、もう片方の出現を保持する
	 * TODO 廃止する？ */
	//public Context buddy = null;
	/** コンテキスト名の情報 */
	public ContextDef def;
	
	/** コンストラクタ
	 * @param mem 所属膜
	 * @param qualifiedName コンテキストの限定名
	 * @param arity コンテキスト出現の明示的な自由リンク引数の個数
	 */
	protected Context(Membrane mem, String qualifiedName, int arity) {
		super(mem,"",arity);
		this.qualifiedName = qualifiedName;
	}
	
	/** コンテキストの限定名を返す */
	public String getQualifiedName() {
		return qualifiedName;
	}
	/** コンテキストの名前を返す（仮） */
	public String getName() {
		return qualifiedName.substring(1);
	}
	abstract public String toString();	
}

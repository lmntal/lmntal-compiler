package compile.parser;

/**
 * ソースファイル中のリンク・リンク束・プロセスコンテキスト・ルールコンテキストの抽象親クラス
 * <p>プロセス文脈名およびルール文脈名には '...' や [[...]] が使えないようにした。
 */

abstract class SrcContext {
 	
 	protected String name = null;
 	
 	/**
 	 * 指定された名前でコンテキストを初期化します
 	 * @param name コンテキスト名
 	 */
	protected SrcContext(String name) {
		this.name = name;
	}
	
	/**
	 * コンテキストの名前を返す。
	 * @return コンテキストの名前
	 */
	public String getName() {
		return name;
	}
	/** コンテキストの限定名（種類と名前の組に対する一意な識別子として使用できる文字列）を返す。*/
	abstract public String getQualifiedName();
	public String toString() {
		return getQualifiedName();
	}
}
/**
 * プロセスコンテキストとルールコンテキストの抽象クラス
 */

package compile.parser;
 
abstract class SrcContext {
 	
 	private String name = null;
 	
 	/**
 	 * 指定された名前でコンテキストを初期化します
 	 * @param name コンテキスト名
 	 */
	protected SrcContext(String name) {
		this.name = name;
	}
	
	/**
	 * コンテキストの名前を得ます
	 * @return コンテキストの名前
	 */
	public String getName() {
		return name;
	}
}
/**
 * ソースファイル中のプロセスコンテキストの表現
 */

package compile.parser;

class SrcProcessContext extends SrcContext {
	
	/**
	 * 指定された名前をもつプロセスコンテキストを作成します
	 * @param name コンテキスト名
	 */
	public SrcProcessContext(String name) {
		super(name);	
	}
}
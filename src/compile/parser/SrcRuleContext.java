/**
 * ソースファイル中のルールコンテキストの表現
 */

package compile.parser;

class SrcRuleContext extends SrcContext {

	/**
	 * 指定された名前を持つルールコンテキストを作成します
	 * @param name ルールコンテキスト名
	 */
	protected SrcRuleContext(String name) {
		super(name);
	}
}

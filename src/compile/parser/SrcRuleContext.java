package compile.parser;

/** ソースファイル中のルールコンテキストの表現 */

class SrcRuleContext extends SrcContext {
	/**
	 * 指定された名前を持つルールコンテキストを作成します
	 * @param name ルールコンテキスト名
	 */
	public SrcRuleContext(String name) {
		super(name);
	}
	public String getQualifiedName() {
		return "@" + name;
	}
}

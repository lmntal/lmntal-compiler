package compile.parser;

/** ソースファイル中のリンク表現 */

class SrcLink extends SrcContext {
	/**
	 * 指定された名前のリンクを作成します
	 * @param name リンク名
	 */
	public SrcLink(String name) {
		super(name);
	}
	public String getQualifiedName() {
		return " " + name;
	}
}
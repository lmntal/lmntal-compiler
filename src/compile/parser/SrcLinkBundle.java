package compile.parser;

/** ソースファイル中のリンク束表現 */

class SrcLinkBundle extends SrcContext {
	/**
	 * 指定された名前のリンク束を作成します
	 * @param name リンク束の名前
	 */
	public SrcLinkBundle(String name) {
		super(name);
	}
	public String getQualifiedName() {
		return "*" + name;
	}
}
package compile.parser;

/** ソースファイル中のリンク束表現 */

class SrcLinkBundle extends SrcLink {
	static final String PREFIX_TAG = "*";
	/**
	 * 指定された名前のリンク束を作成します
	 * @param name リンク束の名前
	 */
	public SrcLinkBundle(String name) {
		super(name);
	}
	public String getQualifiedName() {
		return PREFIX_TAG + name;
	}
}
package compile.parser;

/** ソースファイル中のリンク表現 */

class SrcHyperLink extends SrcLink {
	int lineno;//2012.7.10 by meguro
	
	/**
	 * 指定された名前のリンクを作成します
	 * @param name リンク名
	 */
	public SrcHyperLink(String name) {
		super(name, -1);
	}
	
	/**
	 * 指定された名前と行番号のリンクを作成します
	 * @param name リンク名
	 * @param lineno 行番号
	 */
	public SrcHyperLink(String name, int lineno) {
	    super(name, lineno);
	}
	public String getQualifiedName() {
		return " " + name;
	}
}

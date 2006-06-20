package compile.parser;

/** ソースファイル中のリンク表現 */

class SrcLink extends SrcContext {
	int lineno;//2006.6.20 by inui
	
	/**
	 * 指定された名前のリンクを作成します
	 * @param name リンク名
	 */
	public SrcLink(String name) {
		this(name, -1);
	}
	
	/**
	 * 指定された名前と行番号のリンクを作成します
	 * @param name リンク名
	 * @param lineno 行番号
	 */
	public SrcLink(String name, int lineno) {
		super(name);
		this.lineno = lineno;
	}
	public String getQualifiedName() {
		return " " + name;
	}
}
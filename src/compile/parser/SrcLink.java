/**
 * ソース中のリンク表現
 */

package compile.parser;

class SrcLink {
	
	protected String name = null;
	
	/**
	 * 指定された名前のリンクを作成します
	 * @param name リンク名
	 */
	public SrcLink(String name) {
		this.name = name;	
	}
	
	/**
	 * リンクの名前を取得します
	 * @return リンクの名前
	 */
	public String getName() {
		return this.name;
	}
}
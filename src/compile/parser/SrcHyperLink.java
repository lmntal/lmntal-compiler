package compile.parser;

/**
 * ソースファイル中のリンク表現
 */
class SrcHyperLink extends SrcLink
{
	/** 
         * 属性名．現状ではデータアトム（のうち整数と文字列）のみ想定
         * 属性なしの場合は空文字列
         */
	protected SrcName attr;

	/**
	 * 指定された名前のリンクを作成します
	 * @param name リンク名
	 * @param attr 属性名
	 */
	public SrcHyperLink(String name, SrcName attr)
	{
		this(name, attr, -1);
	}

	/**
	 * 指定された名前と行番号のリンクを作成します
	 * @param name リンク名
	 * @param attr 属性名
	 * @param lineno 行番号
	 */
	public SrcHyperLink(String name, SrcName attr, int lineno)
	{
	        super(name, lineno);
		this.attr = attr;
	}

	public String getQualifiedName()
	{
		return " " + name;
	}
}

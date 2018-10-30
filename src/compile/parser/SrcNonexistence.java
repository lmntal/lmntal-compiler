package compile.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * ソース中のルールを表します
 */
class SrcNonexistence
{
	public LinkedList processList;
	public SrcProcessContext pcxt;

	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcNonexistence(LinkedList processList, SrcProcessContext pcxt)
	{
		this.processList = processList;
		this.pcxt = pcxt;
	}

	/**
	 * ルールのガードを得ます
	 * @return ガードのリスト
	 */
	public LinkedList getProcessList()
	{
		return processList;
	}

	/**
	 * ルールのボディを取得します
	 * @return ボディのリスト
	 */
	public SrcProcessContext getProcessContext()
	{
		return pcxt;
	}

	public String toString()
	{
		return "(nonexistence:" + processList.toString() + " in " + pcxt.toString() + ")";
	}

	/**
	 * LMNtalソース形式のテキスト表現を取得する。
	 */
	public String getText()
	{
		return text;
	}

	void setText()
	{
		text = SrcDumper.dump(this);
	}
}

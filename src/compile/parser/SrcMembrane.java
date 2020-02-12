package compile.parser;

import java.util.LinkedList;

/**
 * ソースファイル中の膜表現
 */
class SrcMembrane extends SrcAbstract
{
	/** 膜の内容プロセスの表現 */
	LinkedList<SrcAbstract> process = null;

	/** 終了フラグの有無 */
	public boolean stable = false;

	/** 膜のタイプ */
	public int kind = 0;

	/** ＠指定またはnull */
	Object pragma = null;

	/** 膜名 */
	public String name;

	/**
	 * 空の膜を作成します 
	 */
	public SrcMembrane()
	{
		this(new LinkedList<SrcAbstract>());
	}

	/**
	 * 指定された子プロセスを持つ膜を作成します
	 * @param process 膜に含まれる子プロセス
	 */
	public SrcMembrane(LinkedList<SrcAbstract> process)
	{
		this.process = process;
	}

	/**
	 * 子プロセスを取得します
	 * @return 子プロセスのリスト
	 */
	public LinkedList<SrcAbstract> getProcess()
	{
		return process;
	}

	public String toString()
	{
		return SrcDumper.dumpMembrane(this);
	}
}

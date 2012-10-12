package compile.structure;

import java.util.List;
import java.util.ArrayList;

/**
 * プロセス文脈名・型付きプロセス文脈名・ルール文脈名と、それに関する情報を保持するクラス。
 */
public class ContextDef
{
	/**
	 * コンテキストの名前
	 */
	protected String name;

	/**
	 * 型付きプロセスコンテキストかどうかを格納する
	 */
	public boolean typed = false;

	/**
	 * ＠指定される左辺の膜またはnull（仮）
	 * @see Membrane.pragma
	 * todo HashMap を使うようにしてlhsMemは廃止する
	 */
	public Membrane lhsMem = null;

	/**
	 * 左辺での出現またはnull。
	 * <strike>右辺での生成時に使うオリジナルへの参照。
	 * nullのとき、ルールコンパイラはガード出現を代入してよい。</strike>
	 */
	public Context lhsOcc = null;

	/**
	 * 右辺でのコンテキスト出現 (Context) のリスト
	 */
	public List rhsOccs = new ArrayList();

	/**
	 * コンストラクタ
	 * @param name コンテキストの限定名
	 */
	public ContextDef(String name)
	{
		this.name = name;
	}

	/**
	 * コンテキストの限定名を取得する
	 * @return コンテキストの限定名
	 */
	public String getName()
	{
		return name;
	}

	public String toString()
	{
		return getName();
	}

	public boolean isTyped()
	{
		return typed;
	}
}

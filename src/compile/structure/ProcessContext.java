package compile.structure;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * ソースコード中のプロセス文脈出現を表すクラス
 */
public final class ProcessContext extends Context
{
	/**
	 * 引数のリンク束
	 */
	public LinkOccurrence bundle = null;

	/**
	 * 分離した同名型付きプロセス文脈の名前を格納
	 */
	public LinkedList sameNameList = null;//seiji

	/**
	 * リンク名
	 */
	public String linkName = null;//seiji

	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 * @param qualifiedName 限定名
	 * @param arity 明示的な自由リンク引数の個数
	 */
	public ProcessContext(Membrane mem, String qualifiedName, int arity)
	{
		super(mem,qualifiedName,arity);
	}

	/**
	 * 指定された名前でリンク束を登録する
	 */
	public void setBundleName(String bundleName)
	{
		bundle = new LinkOccurrence(bundleName, this, -1);
	}

	/**
	 * $p[A,B|*Z]のような文字列表現を返す。自動補完された$p[...|*p]のときは$pを返す。
	 */
	public String toString()
	{
		String argstext = "";
		if (bundle == null || bundle.name.matches("\\*[A-Z_].*")) // TODO: (buddy!=null)かどうかで判定すべきである
		{
			argstext = "[" + Arrays.asList(args).toString()
				.replaceAll("^.|.$","").replaceAll(", ",",");
			if (bundle != null) argstext += "|" + bundle;
			argstext += "]";
		}
		return getQualifiedName() + argstext;
	}

	/**
	 * 同名プロセス文脈の分離により新たに生成された名前を格納しているリストを返す
	 */
	public LinkedList getSameNameList()
	{//seiji
		return sameNameList;
	}

	/**
	 * 同名プロセス文脈の分離を行なっているか否か
	 */
	public boolean hasSameName()
	{//seiji
		return sameNameList != null;
	}

	public String getLinkName()
	{//seiji
		return linkName;
	}
}

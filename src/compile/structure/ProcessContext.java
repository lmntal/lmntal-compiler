package compile.structure;

/** ソースコード中のプロセス文脈出現を表すクラス */

final public class ProcessContext extends Context {
	/** 引数のリンク束 */
	public LinkOccurrence bundle = null;
	/** コンストラクタ
	 * @param mem 所属膜
	 * @param qualifiedName 限定名
	 * @param arity 明示的な自由リンク引数の個数
	 */
	public ProcessContext(Membrane mem, String qualifiedName, int arity) {
		super(mem,qualifiedName,arity);
	}
	/** 指定された名前でリンク束を登録する */
	public void setBundleName(String bundleName) {
		bundle = new LinkOccurrence(bundleName, this, -1);
	}
	/** $p[A,B|*Z]のような文字列表現を返す。自動補完された$p[...|*p]のときは$pを返す。 */
	public String toString() {
		String argstext = "";
		if (bundle == null || bundle.name.matches("\\*[A-Z_].*")) { // todo (buddy!=null)かどうかで判定すべきである
			argstext = "[" + java.util.Arrays.asList(args).toString()
				.replaceAll("^.|.$","").replaceAll(", ",",");
			if (bundle != null) argstext += "|" + bundle;
			argstext += "]";		
		}
		return getQualifiedName() + argstext;
	}
}

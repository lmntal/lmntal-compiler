package compile.structure;

import java.util.List;
import java.util.ArrayList;

/** プロセス文脈名・型付きプロセス文脈名・ルール文脈名と、それに関する情報を保持するクラス。
 * 編集中 */

public abstract class ContextDef {
	/** コンテキストの名前 */
	protected String name;
	
	/**
	 * コンストラクタ
	 * @param name コンテキスト名
	 */
	protected ContextDef(String name) {
		this.name = name;
	}
	
	/**
	 * コンテキストの名前を得ます
	 * @return コンテキスト名
	 */
	public String getName() {
		return name;
	}
	
	/** ソース出現 */
	public Context src;
	
	/** ソースでない出現  */
	List rhsMems = new ArrayList();
	
}

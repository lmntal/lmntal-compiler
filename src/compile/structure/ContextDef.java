package compile.structure;

import java.util.List;
import java.util.ArrayList;

/** プロセス文脈名・型付きプロセス文脈名・ルール文脈名と、それに関する情報を保持するクラス。*/

public class ContextDef {
	/** コンテキストの名前 */
	protected String name;
	/** 型付きプロセスコンテキストかどうかを格納する */
	public boolean typed = false;
	
	/**
	 * コンストラクタ
	 * @param name コンテキストの限定名
	 */
	public ContextDef(String name) {
		this.name = name;
	}
	
	/**
	 * コンテキストの限定名を取得する
	 * @return コンテキストの限定名
	 */
	public String getName() {
		return name;
	}
	public boolean isTyped() {
		return typed;
	}
	/** ソース出現（右辺での生成時に使うオリジナルへの参照）またはnull
	 * nullのとき、ルールコンパイラはガード出現を代入してよい。*/
	public Context src = null;
	
	/** 右辺でのコンテキスト出現 (Context) のリスト */
	public List rhsOccs = new ArrayList();
	
}

/*
 * 作成日: 2003/10/22
 */
package runtime;

/**
 * LMNtal のメイン
 * 
 * <pre>
 * TODO for 親愛なる矢島さん 
 * コマンドライン引数の処理
 * （ファイル名が指定されていたらそれを解釈実行
 * 　指定されてなかったら runREPL() 実行）
 * 
 * TODO 名前は FrontEnd でいいんだろうか。
 *       案：FrontEnd
 *           素直に Main
 * </pre>
 * 
 * 作成日: 2003/10/22
 */
public class FrontEnd {
	/**
	 * 全ての始まり
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
		/**
		 * TODO コマンドライン引数があったらファイルの中身を解釈実行
		 */
		
		//指定がなければこれを呼ぶ
		REPL repl = new REPL();
		repl.run();
	}
}

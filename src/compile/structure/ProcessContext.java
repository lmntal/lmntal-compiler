package compile.structure;

/**
 * ソースコード中のプロセス文脈の構造を表すクラス
 * <br>TODO 型付きプロセス文脈の扱いは？
 */
final public class ProcessContext extends Context{
	/**
	 * 引数のリンク束
	 * <br>TODO コンストラクタで設定するのか、メソッドを作るのか</p>
	 */
	private LinkOccurrence[] arg;
	/**
	 * 引数のリンク束
	 * <bf>
	 * TODO コンストラクタで設定するのか、メソッドを作るのか<br>
	 * TODO 専用のクラスを作る？
	 */
	private LinkOccurrence bundle;
	ProcessContext(String name) {
		super(name);
	}
}

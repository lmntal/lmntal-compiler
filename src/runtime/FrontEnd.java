/*
 * 作成日: 2003/10/22
 */
package runtime;

import java.io.EOFException;

import org.gnu.readline.Readline;
import org.gnu.readline.ReadlineLibrary;

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
	 * LMNtal-REPL を実行する。
	 * 
	 * インタラクティブモード。
	 * LMNtal 言語が受理する文字列を 1 行入力すると
	 * それを実行した結果の文字列が表示される。
	 *
	 */
	public void runREPL() {
		String line;
		try {
			//Readline.load(ReadlineLibrary.Getline);
			//Readline.load(ReadlineLibrary.GnuReadline);
			Readline.load(ReadlineLibrary.PureJava);
		}
		catch (UnsatisfiedLinkError ignore_me) {
			System.err.println("couldn't load readline lib. Using simple stdin.");
		}

		Readline.initReadline("LMNtal");

		Runtime.getRuntime()                       // if your version supports
		  .addShutdownHook(new Thread() {          // addShutdownHook (since 1.3)
			 public void run() {
			   Readline.cleanup();
			 }
			});

		System.out.println("        LMNtal version 0.01");
		System.out.println("");
		System.out.println("[TIPS] Type q to quit.");
		System.out.println("");
		while (true) {
			try {
				line = Readline.readline("LMNtal> ");
				if (line == null) {
					//System.out.println("no input");
				} else if(line.equals("q")) {
					break;
				} else {
					processLine(line);
				}
			} 
			catch (EOFException e) {
				break;
			} 
			catch (Exception e) {
				//doSomething();
			}
		}
		Readline.cleanup();  // see note above about addShutdownHook
	}
	
	/**
	 * Process a line
	 * 
	 * @param line     LMNtal statement (one liner)
	 */
	private void processLine(String line) {
		System.out.println(line+"  =>  {a, b, {c}}, ({b, $p}:-{c, $p})");
	}
	
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
		fe.runREPL();
	}
}

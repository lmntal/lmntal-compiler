/*
 * 作成日: 2003/10/28
 *
 */
package runtime;

import java.io.EOFException;
import java.io.StringReader;
import java.util.Arrays;

import org.gnu.readline.Readline;
import org.gnu.readline.ReadlineLibrary;

/**
 * インタラクティブモード。(Read-Eval-Print-Loop)
 * LMNtal 言語が受理する文字列を 1 行入力すると
 * それを実行した結果の文字列が表示される。
 * 
 * @author hara
 */
public class REPL {
	/**
	 * LMNtal-REPL を実行する。
	 * REPL から抜けるコマンドが入力されると、戻ってくる。
	 */
	public static void run() {
		String line;
		try {
			//Readline.load(ReadlineLibrary.Getline);
			//Readline.load(ReadlineLibrary.GnuReadline);
			Readline.load(ReadlineLibrary.PureJava);
		} catch (UnsatisfiedLinkError ignore_me) {
			System.err.println("couldn't load readline lib. Using simple stdin.");
		}
		
		Readline.initReadline("LMNtal");
		
		//if your version supports
		//addShutdownHook (since 1.3)
		Runtime.getRuntime().addShutdownHook(
			new Thread() {
				public void run() {
					Readline.cleanup();
				}
			});
		
		System.out.println("        LMNtal version 0.04.20040114");
		System.out.println("");
//		System.out.println("[TIPS] Type q to quit.");
		System.out.println("Commands:");
		System.out.println("  [no]debug          - set debug mode");
		System.out.println("  [no]shuffle        - set shuffle mode");
		System.out.println("  [no]trace          - set trace mode");
		System.out.println("  [no]optimize [0-9] - set optimize level"); //書き方変えた方がよい？
		System.out.println("  q                  - quit");
		System.out.println("");
		while (true) {
			try {
				line = Readline.readline("# ");
				if (line == null) {
					//System.out.println("no input");
				} else if(line.equals("q")) {
					break;
				} else if(line.equals("debug")) {
					Env.p("debug mode on");
					Env.debug = 1;
					continue;
				} else if(line.equals("nodebug")) {
					Env.p("debug mode off");
					Env.debug = 0;
					continue;
				} else if(line.equals("trace")) {
					Env.p("trace mode on");
					Env.fTrace = true;
					continue;
				} else if(line.equals("notrace")) {
					Env.p("trace mode off");
					Env.fTrace = false;
					continue;
				} else if(line.equals("shuffle")) {
					Env.p("shuffle mode on");
					Env.fRandom = true;
					continue;
				} else if(line.equals("noshuffle")) {
					Env.p("shuffle mode off");
					Env.fRandom = false;
					continue;
				} else if (line.equals("optimize")) {
					Env.p("optimize level 5");
					Env.optimize = 5;
					continue;
				} else if (line.equals("nooptimize")) {
					Env.p("optimize level 0");
					Env.optimize = 0;
					continue;
				} else if (line.matches("optimize [0-9]")) {
					Env.optimize = line.charAt(9) - '0';
					Env.p("optimize level " + Env.optimize);
					continue;
				} else {
					processLine(line);
				}
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
				System.err.println(Arrays.asList(e.getStackTrace()));
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
	public static void processLine(String line) {
		FrontEnd.run(new StringReader(line));
		//System.out.println(line+"  =>  {a, b, {c}}, ({b, $p}:-{c, $p})");
	}
}

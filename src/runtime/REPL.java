/*
 * 作成日: 2003/10/28
 *
 */
package runtime;

import java.io.EOFException;
import java.io.StringReader;

import compile.*;
import compile.parser.*;
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
	public void run() {
		String line;
		try {
			//Readline.load(ReadlineLibrary.Getline);
			//Readline.load(ReadlineLibrary.GnuReadline);
			Readline.load(ReadlineLibrary.PureJava);
		} catch (UnsatisfiedLinkError ignore_me) {
			System.err.println("couldn't load readline lib. Using simple stdin.");
		}

		Readline.initReadline("LMNtal");

		Runtime.getRuntime()                       // if your version supports
		  .addShutdownHook(new Thread() {          // addShutdownHook (since 1.3)
			 public void run() {
			   Readline.cleanup();
			 }
			});

		System.out.println("        LMNtal version 0.02");
			System.out.println("");
		System.out.println("[TIPS] Type q to quit.");
		System.out.println("");
		while (true) {
			try {
				line = Readline.readline("# ");
				if (line == null) {
					//System.out.println("no input");
				} else if(line.equals("q")) {
					break;
				} else {
					processLine(line);
				}
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
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
		try {
			// 字句解析　構文解析　意味解析
			LMNParser lp = new LMNParser(new StringReader(line));
			
			// ルールセット生成
			compile.structure.Membrane m = lp.parse();
			compile.structure.Membrane root = RuleSetGenerator.runStartWithNull(m);
			InterpretedRuleset ir = (InterpretedRuleset)root.ruleset;
			
			// 実行
			LMNtalRuntime rt = new LMNtalRuntime(ir);
			rt.exec();
			Membrane result = (Membrane)rt.getRootMem();
			
			
			Env.p("");
			Env.p( "After parse   : "+m );
			Env.p( "After compile : "+ir );
			m.showAllRule();
			Env.p( "After execute : " );
			Env.p( Dumper.dump(result) );
			Env.p( result );
		} catch (ParseException e) {
			Env.p(e);
		}
		
		//System.out.println(line+"  =>  {a, b, {c}}, ({b, $p}:-{c, $p})");
	}
}

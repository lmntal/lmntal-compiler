/*
 * 作成日: 2003/10/28
 *
 */
package runtime;

import java.io.EOFException;
import java.io.StringReader;

//import org.gnu.readline.Readline;
//import org.gnu.readline.ReadlineLibrary;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class Readline {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static void load(int dummy) {}
	static void initReadline(String dummy) {}
	static String readline(String prompt) throws IOException {
		System.out.print(prompt);
		try {
			String line = in.readLine();
			if (line.length() == 0) return null;
			return line;
		}
		catch (NullPointerException e) {}
		throw new EOFException();
	}
	static void cleanup() {}
}
class ReadlineLibrary {
	static final int PureJava = 0;
}

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
		
		System.out.println("        LMNtal version 0.64.20040528");
		System.out.println("");
		System.out.println("Type h to see help.");
		System.out.println("Type q to quit.");
		System.out.println("Enter an empty line to run the input.");
		System.out.println("");
		StringBuffer lb = new StringBuffer();
		while (true) {
			try {
				line = Readline.readline( lb.length() == 0 ? "# " : "  " );
				if (line == null) {
					//System.out.println("no input");
					processLine(lb.toString());
					lb.setLength(0);
				} else if(line.equals("q")) {
					break;
				} else if(line.equals("h")) {
					System.out.println("Commands:");
					System.out.println("  [no]debug    [0-9] - set debug level");
					System.out.println("  [no]optimize [0-9] - set optimization level");
					System.out.println("  [no]verbose  [0-9] - set verbose level");
					System.out.println("  [no]shuffle  [0-4] - set shuffle level");
					System.out.println("  [no]trace          - set trace mode");					
					System.out.println("  h                  - help");
					System.out.println("  q                  - quit");
					continue;
				} else if(line.matches("nodebug|debug( [0-9])?")) {
					if (line.length() == 5) Env.debug = Env.DEBUG_DEFAULT;
					else if (line.charAt(0) == 'n') Env.debug = 0;
					else Env.debug = line.charAt(line.length() - 1) - '0';
					Env.p("debug level " + Env.debug);
					continue;
				} else if(line.matches("noverbose|verbose( [0-9])?")) {
					int old = Env.verbose;
					if (line.charAt(0) == 'n') Env.verbose = 0;
					else if (line.charAt(line.length() - 1) == 'e') Env.verbose = Env.VERBOSE_DEFAULT;
					else Env.verbose = line.charAt(line.length() - 1) - '0';
					Env.p("verbose level set to " + Env.verbose + " (previously " + old + ")");
					continue;
				} else if(line.matches("nooptimize|optimize( [0-9])?")) {
					if (line.charAt(0) == 'n') Env.optimize = 0;
					else if (line.charAt(line.length() - 1) == 'e') Env.optimize = 5;
					else Env.optimize = line.charAt(line.length() - 1) - '0';
					Env.p("optimization level " + Env.optimize);
					continue;
				} else if(line.matches("noshuffle|shuffle( [0-9])?")) {
					int old = Env.shuffle;
					if (line.charAt(0) == 'n') Env.shuffle = Env.SHUFFLE_INIT;
					else if (line.charAt(line.length() - 1) == 'e') Env.shuffle = Env.SHUFFLE_DEFAULT;
					else Env.shuffle = line.charAt(line.length() - 1) - '0';
					Env.p("shuffle level " + Env.shuffle + " (previously " + old + ")");
					continue;
				} else if(line.equals("trace")) {
					Env.p("trace mode on");
					Env.fTrace = true;
					continue;
				} else if(line.equals("notrace")) {
					Env.p("trace mode off");
					Env.fTrace = false;
					continue;
				} else {
					lb.append(line);
					lb.append("\n");
				}
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
				e.printStackTrace();
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

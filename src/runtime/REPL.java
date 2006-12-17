/*
 * 作成日: 2003/10/28
 *
 */
package runtime;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;

import compile.Optimizer;
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
		
		System.out.println("        LMNtal version " + Env.LMNTAL_VERSION);
		System.out.println("");
		System.out.println("Type "+Env.replCommandPrefix+"h to see help.");
		System.out.println("Type "+Env.replCommandPrefix+"q to quit.");
		if(Env.replTerm.equals("null_line")) System.out.println("Enter an empty line to run the input.");
		System.out.println("");
		StringBuffer lb = new StringBuffer();
		while (true) {
			try {
				line = Readline.readline( lb.length() == 0 ? "# " : "  " );
				if (line == null) {
					//System.out.println("no input");
					if(Env.replTerm.equals("null_line")) {
						processLine(lb.toString());
						lb.setLength(0);
					}
				} else if(line.startsWith(Env.replCommandPrefix)) {
					// 特殊コマンドの処理
					String nline = line.replaceAll(Env.replCommandPrefix, "");
					if(nline.equals("q")) {
						break;
					} else if(nline.equals("h")) {
						showHelp();
						continue;
					} else if(nline.matches("nodebug|debug( [0-9])?")) {
						if (nline.length() == 5) Env.debug = Env.DEBUG_DEFAULT;
						else if (nline.charAt(0) == 'n') Env.debug = 0;
						else Env.debug = nline.charAt(nline.length() - 1) - '0';
						Env.p("debug level " + Env.debug);
						continue;
					} else if(nline.matches("noverbose|verbose( [0-9])?")) {
						int old = Env.verbose;
						if (nline.charAt(0) == 'n') Env.verbose = 0;
						else if (nline.charAt(nline.length() - 1) == 'e') Env.verbose = Env.VERBOSE_DEFAULT;
						else Env.verbose = nline.charAt(nline.length() - 1) - '0';
						Env.p("verbose level set to " + Env.verbose + " (previously " + old + ")");
						continue;
					} else if(nline.matches("nooptimize|optimize( [0-9])?")) {
						Optimizer.clearFlag();
						int level;
						if (nline.charAt(0) == 'n') level = 0;
						else if (nline.charAt(nline.length() - 1) == 'e') level = 1;
						else level = nline.charAt(nline.length() - 1) - '0';
						Optimizer.setLevel(level);
						Env.p("optimization level " + level);
						continue;
					} else if(nline.equals("optimize-inlining")) {
						Optimizer.fInlining = true;
						Env.p("optimize inlining on");
						continue;
					} else if(nline.equals("optimize-reuse-atom")) {
						Optimizer.fReuseAtom = true;
						Env.p("optimize reuse atom on");
						continue;
					} else if(nline.equals("optimize-reuse-mem")) {
						Optimizer.fReuseMem = true;
						Env.p("optimize reuse mem on");
						continue;
					} else if(nline.equals("optimize-loop")) {
						Optimizer.fLoop = true;
						Env.p("optimize loop on");
						continue;
					} else if(nline.matches("noshuffle|shuffle( [0-9])?")) {
						int old = Env.shuffle;
						if (nline.charAt(0) == 'n') Env.shuffle = Env.SHUFFLE_INIT;
						else if (nline.charAt(nline.length() - 1) == 'e') Env.shuffle = Env.SHUFFLE_DEFAULT;
						else Env.shuffle = nline.charAt(nline.length() - 1) - '0';
						if(Env.compileonly == false)Env.p("shuffle level " + Env.shuffle + " (previously " + old + ")");
						continue;
					} else if(nline.equals("trace")) {
						Env.p("trace mode on");
						Env.fTrace = true;
						continue;
					} else if(nline.equals("notrace")) {
						Env.p("trace mode off");
						Env.fTrace = false;
						continue;
					} else if(nline.equals("remain")) {
						Env.p("remain mode on");
						Env.fREMAIN = true;
						continue;
					} else if(nline.equals("noremain")) {
						Env.p("remain mode off");
						Env.fREMAIN = false;
						continue;
					} else if(nline.equals("gui")) {//2006.2.8 inui
						Env.p("gui mode on");
						Env.fGUI2 = true;
						continue;
					} else if(nline.equals("nogui")) {//2006.2.8 inui
						Env.p("gui mode off");
						Env.fGUI2 = false;
						continue;
					} else if(nline.startsWith("l")) {//2006.5.25 by inui
						String ss[] = nline.split(" ");
						if (ss.length == 1) {
							Env.p("load [file ...]");
							continue;
						}
						Env.fREMAIN = true;
						for (int i = 1; i < ss.length; i++) {
//							BufferedReader br = new BufferedReader(new FileReader(ss[i]));
//							String s = null;
//							while ((s = br.readLine()) != null)	Env.p(s);
							FrontEnd.run(new FileReader(ss[i]));
						}
						Env.p("remain mode on");
					} else if(nline.equals("r") || nline.equals("rules")) {
						showRules();
						continue;
					} else if(nline.startsWith("!")) {//2006.6.1 by inui
						String command = nline.substring(1).trim();//!のあとはスペースはあってもなくてもok
						ProcessBuilder pb = new ProcessBuilder(command.split("[ \t]+"));
						Process p = pb.start();						
						//出力を受け取って表示する
						InputStream is = p.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String s = null;
						while ((s = br.readLine()) != null) {
							System.out.println(s);
						}
					} else if (nline.equals("clear")) {//2006.6.6 by inui
						Env.remainedRuntime = null;//全プロセスを消去
					} else if(nline.startsWith("rm") || nline.startsWith("remove")) {
						if(Env.remainedRuntime!=null) {
							String s[] = nline.split(" ");
							if(s.length==1) Env.p("rm [ruleset number ...]");
							for(int i=1;i<s.length;i++) {
								Iterator it=Env.remainedRuntime.getGlobalRoot().rulesets.iterator();
								while(it.hasNext()) {
									InterpretedRuleset rs = (InterpretedRuleset)it.next();
									if(rs.toString().matches(".*?"+s[i]+".*")) {
										Env.p(rs+" removed.");
										it.remove();
									}
								}
							}
							showRules();
						}
						continue;
					} else {
						Env.p("Unknown command "+nline+".");
						showHelp();
					}
				} else {
					if(Env.replTerm.equals("null_line")) {
						lb.append(line);
						lb.append("\n");
					} else {
						processLine(line.toString());
					}
				}
			} catch (EOFException e) {
				break;
			} catch (FileNotFoundException e) {//2006.6.1 by inui
				System.err.println("No such file or directory");
			} catch (IOException e) {
				System.err.println(e);
//			} catch (Exception e) {
//				e.printStackTrace();
//				//doSomething();
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
	
	/**
	 * --remain モードの時、今あるルール一覧を表示する
	 *
	 */
	public static void showRules() {
		if(Env.remainedRuntime!=null) {
			Iterator it=Env.remainedRuntime.getGlobalRoot().rulesets.iterator();
			while(it.hasNext()) {
				InterpretedRuleset rs = (InterpretedRuleset)it.next();
				Env.p(rs);
				Iterator it2 = rs.rules.iterator();
				while(it2.hasNext()) {
					Rule r = (Rule)it2.next();
					Env.p("  "+r.text);
					r.showDetail();
				}
			}
		}
	}
	
	/**
	 * 特殊コマンドの説明を印字する
	 *
	 */
	public static void showHelp() {
		System.out.println("Commands:");
		System.out.println("  "+"[no]debug    [0-9] - set debug level");
		System.out.println("  "+"[no]optimize [0-9] - set optimization level");
		System.out.println("  "+"[no]verbose  [0-9] - set verbose level");
		System.out.println("  "+"[no]shuffle  [0-4] - set shuffle level");
		System.out.println("  "+"[no]trace          - set trace mode");					
		System.out.println("  "+"[no]remain         - set remain mode");
		System.out.println("  "+"[no]gui            - set gui mode");//2006.2.9 inui
		System.out.println("  "+"r | rules          - show current rules");					
		System.out.println("  "+"(rm | remove) [ruleset number...]");					
		System.out.println("  "+"                   - remove specified rulesets  (ex: rm 601)");
		System.out.println("  "+"l | load [file...] - load specified files (and remain mode on)");
		System.out.println("  "+"clear              - clear all processes");
		System.out.println("  "+"h                  - help");
		System.out.println("  "+"q                  - quit");
	}
}

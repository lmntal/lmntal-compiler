/*
 * 作成日: 2003/10/22
 */
package runtime;

import java.io.FileInputStream;
import java.io.SequenceInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.*;

import compile.*;
import compile.parser.*;

/**
 * LMNtal のメイソ
 * 
 * 
 * 作成日: 2003/10/22
 */
public class FrontEnd {
	/**
	 * 全ての始まり。
	 * 
	 * <pre>
	 * コマンドライン引数
	 *   なし                       → REPL 起動
	 *   ファイル名                 → ファイルの中身を実行して終わる
	 *   -e [LMNtal oneliner]       → [LMNtal oneliner] （１行文）を実行して終わる
	 *   -d                         → デバッグモード
	 *   --help                     → ヘルプを表示
	 *   -g                         → GUI で途中経過を表示しる
	 * </pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//バージョンチェック by 水野
		try {
			String ver = System.getProperty("java.version");
			StringTokenizer tokenizer = new StringTokenizer(ver, ".");
			int major = Integer.parseInt(tokenizer.nextToken());
			int minor = Integer.parseInt(tokenizer.nextToken());
			if (major < 1 || (major == 1 && minor < 4)) {
				System.err.println("use jre 1.4 or higher!!");
				System.exit(-1);
			}
		// うまくいかなかった場合は無視する
		} catch (SecurityException e) {
		} catch (NoSuchElementException e) {
		} catch (NumberFormatException e) {
		}
		
		FileInputStream fis = null;
		InputStream is = null;
		Reader src = null;
		
		/**
		 * コマンドライン引数があったらファイルの中身を解釈実行
		 */
		for(int i = 0; i < args.length;i++){
			// 必ずlength>0, '-'ならオプション
			if(args[i].charAt(0) == '-'){
				if(args[i].length() < 2){ // '-'のみの時
					System.out.println("不明なオプション:" + args[i]);
					System.exit(-1);					
				}else{ // オプション解釈部
					switch(args[i].charAt(1)){
					case 'I':
						compile.Module.libPath.add(args[++i]);
						break;
					case 'c':
						if (args[i].equals("-cgi")) {
							System.out.println("Content-type: text/html\n");
						}
						break;
					case 'd':
						if (args[i].matches("-d[0-9]")) {
							Env.debug = args[i].charAt(2) - '0';
						} else {
							Env.debug = Env.DEBUG_DEFAULT;
						}
//						System.out.println("debug level " + Env.debug);
						break;
					case 'v':
						if (args[i].matches("-v[0-9]")) {
							Env.verbose = args[i].charAt(2) - '0';
						} else {
							Env.verbose = Env.VERBOSE_DEFAULT;
						}
//						System.out.println("verbose level " + Env.verbose);
						break;
					case 'g':
						Env.fGUI = true;
						break;
					case 't':
						Env.fTrace = true;
						break;
					case 's':
						if (args[i].matches("-s[0-9]")) {
							Env.shuffle = args[i].charAt(2) - '0';
						} else {
							Env.shuffle = Env.SHUFFLE_DEFAULT;
						}
						System.out.println("shuffle level " + Env.shuffle);
						break;
					case 'e':
						// lmntal -e 'a,(a:-b)'
						// 形式で実行できるようにする。like perl
						Env.REPL = args[++i];
						break;
					case 'O':
						if (args[i].length() == 2) {
							Env.optimize = 5;
						} else if (args[i].matches("-O[0-9]")) {
							Env.optimize = args[i].charAt(2) - '0';
						} else {
							System.out.println("不明なオプション:" + args[i]);
							System.exit(-1);
						}
						break;
					case '-': // 文字列オプション
						if(args[i].equals("--help")){
							System.out.println("usage: FrontEnd [options...] [filenames...]");
						}else{
							System.out.println("不明なオプション:" + args[i]);
							System.exit(-1);
						}
						break;
					default:
						System.out.println("不明なオプション:" + args[i]);
						System.exit(-1);						
					}							
				}
			}else{ // '-'以外で始まるものはファイルとみなす
				try{
					fis = new FileInputStream(args[i]);
				}catch(FileNotFoundException e){
					System.out.println("ファイルが見つかりません:" + args[i]);
					System.exit(-1);
				}catch(SecurityException e){
					System.out.println("ファイルが開けません:" + args[i]);
					System.exit(-1);
				}
				if(is == null) is = fis;
				else is = new SequenceInputStream(is, fis); // ソースファイルを連結
			}
		}
		
		Env.initGUI();
		if(Env.REPL!=null) {
			REPL.processLine(Env.REPL);
			return;
//			System.exit(-1);
		}
		
		// ソースなしならREPL, ありならソースを解釈実行。
		if(is == null){
			REPL.run();
		}else{			
			run( new BufferedReader(new InputStreamReader(is)) );
		}
	}
	
	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * @param src Reader 型で表されたソース
	 */
	public static void run(Reader src) {
		try {
			LMNParser lp = new LMNParser(src);
				
			compile.structure.Membrane m = lp.parse();
//			if (Env.debug >= Env.DEBUG_SYSDEBUG) {
//				Env.d("");
//				Env.d( "Parse Result: " + m.toStringWithoutBrace() );
//			}

			Ruleset rs = RulesetCompiler.compileMembrane(m);
			((InterpretedRuleset)rs).showDetail();
			m.showAllRules();
			
			// 実行
			RemoteMachine.init();
			LMNtalRuntime rt = new LMNtalRuntime();
			Membrane root = (Membrane)rt.getGlobalRoot();
			//root.blockingLock();
			rs.react(root); // TODO 初期配置で子タスクを作った場合にどうなるか考える
			//root.blockingUnlock();
			((Task)root.getTask()).execAsMasterTask();
			RemoteMachine.terminateAll();
			
			//rt.exec();
			if (!Env.fTrace && Env.verbose > 0) {
				Env.d( "Execution Result:" );
				Env.p( Dumper.dump(rt.getGlobalRoot()) );
			}
		} catch (Exception e) {
			Env.e("!! catch !! "+e+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
	}
}

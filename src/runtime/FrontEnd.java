/*
 * 作成日: 2003/10/22
 */
package runtime;

import java.io.*;
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
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				Inline.terminate();
			}
		});
		
		/**
		 * コマンドライン引数があったらファイルの中身を解釈実行
		 */
		for(int i = 0; i < args.length;i++){
			// 必ずlength>0, '-'ならオプション
			// -> 引数を "" にすると長さ 0 になるのでチェックする。
			if(args[i].length()>0 && args[i].charAt(0) == '-'){
				if(args[i].length() < 2){ // '-'のみの時
					System.out.println("不明なオプション:" + args[i]);
					System.exit(-1);
				} else { // オプション解釈部
					switch(args[i].charAt(1)){
					case 'x':
						// ユーザー定義オプション。書式： -x <name> <value>
						String name  = i+1<args.length ? args[i+1] : "";
						String value = i+2<args.length ? args[i+2] : "";
						Env.extendedOption.put(name, value);
						i+=2;
						break;
					case 'L':
						// インラインコードのコンパイル時に -classpath オプションになる部分を追加することができる。
						Inline.classPath.add(0, new File(args[++i]));
						break;
					case 'I':
						// LMNtal ライブラリの検索パスを追加することができる。
						compile.Module.libPath.add(new File(args[++i]));
						break;
					case 'c':
						if (args[i].equals("-cgi")) {
							Env.fCGI = true;
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
						Env.oneLiner = args[++i];
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
						} else if(args[i].equals("--demo")){
							Env.fDEMO = true;
						} else {
							System.out.println("不明なオプション:" + args[i]);
							System.exit(-1);
						}
						break;
					default:
						System.out.println("不明なオプション:" + args[i]);
						System.exit(-1);						
					}
				}
			}else{ // '-'以外で始まるものは (実行ファイル名, argv[0], arg[1], ...) とみなす
				Env.argv.add(args[i]);
			}
		}
		
		if(Env.fCGI) {
			System.setErr(System.out);
			System.out.println("Content-type: text/html\n");
		}
		
		/// 実行
		
		if(Env.oneLiner!=null) {
			// 一行実行の場合はそれを優先
			REPL.processLine(Env.oneLiner);
			return;
		}
		// ソースありならソースを解釈実行、なしなら REPL。
		if(Env.argv.isEmpty()) {
			REPL.run();
		} else {
			run(Env.argv);
		}
	}
	
	/**
	 * 与えられた名前のファイルたちをくっつけたソースについて、一連の実行を行う。
	 * 
	 * @param files ソースファイル
	 */
	public static void run(List files) {
		InputStream is = null;
		try{
			for(Iterator i=files.iterator();i.hasNext();) {
				String filename = (String)i.next();
				FileInputStream fis = new FileInputStream(filename);
				if(is==null) is = fis;
				else         is = new SequenceInputStream(is, fis);
			}
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch(SecurityException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// 複数のファイルのときはファイル名が１つに決められない。
		String unitName = files.size()==1 ? (String)files.get(0) : InlineUnit.DEFAULT_UNITNAME;
		run( new BufferedReader(new InputStreamReader(is)), unitName );
	}
	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * @param src Reader 型で表されたソース
	 */
	public static void run(Reader src) {
		run(src, InlineUnit.DEFAULT_UNITNAME);
	}
	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * @param src Reader 型で表されたソース
	 * @param unitName String ファイル名。インラインコードのキャッシュはこの名前ベースで管理される。
	 */
	public static void run(Reader src, String unitName) {
		try {
			LMNParser lp = new LMNParser(src);
			
			compile.structure.Membrane m = lp.parse();
//			if (Env.debug >= Env.DEBUG_SYSDEBUG) {
//				Env.d("");
//				Env.d( "Parse Result: " + m.toStringWithoutBrace() );
//			}
			
			Ruleset rs = RulesetCompiler.compileMembrane(m, unitName);
			Inline.makeCode();
			((InterpretedRuleset)rs).showDetail();
			m.showAllRules();
			
			// 実行
			MasterLMNtalRuntime rt = new MasterLMNtalRuntime();
			LMNtalRuntimeManager.init();

			Membrane root = rt.getGlobalRoot();
			Env.initGUI(root);
			//root.blockingLock();
			rs.react(root); // TODO 初期配置で子タスクを作った場合にどうなるか考える
			if (Env.gui != null) {
				Env.gui.lmnPanel.getGraphLayout().calc();
				Env.gui.onTrace();
			}
			//root.blockingUnlock();
			((Task)root.getTask()).execAsMasterTask();

			if (!Env.fTrace && Env.verbose > 0) {
				Env.d( "Execution Result:" );
				Env.p( Dumper.dump(rt.getGlobalRoot()) );
			}
			if (Env.gui != null) {
				while(true) Env.gui.onTrace();
			}
			
			LMNtalRuntimeManager.terminateAllNeighbors();
			LMNtalRuntimeManager.disconnectFromDaemon();
			
		} catch (Exception e) {
			Env.e("!! catch !! "+e+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
	}
}

/*
 * 作成日: 2003/10/22
 */
package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import type.TypeException;
import type.TypeInferer;
import util.StreamDumper;
import chorus.Output;

import compile.Module;
import compile.Optimizer;
import compile.RulesetCompiler;
import compile.Translator;
import compile.parser.LMNParser;
import compile.parser.ParseException;
import compile.parser.intermediate.RulesetParser;

import debug.Debug;

/**
 * LMNtal のメイン
 * 
 * 
 * 作成日: 2003/10/22
 */
public class FrontEnd {
	/**
	 * 全ての始まり。
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		
		checkVersion();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				Inline.terminate();
			}
		});

//		//TODO REPL で LMNtal プログラムを実行中の場合は、実行を中止してプロンプトに戻るようにする。
//		//注意 : ハンドラを追加しても、標準入力には EOF が送られてくるので、EOF を読んでも終了しないように変更する必要がある。
//		//Ctrl-C のハンドラ
//		Signal.handle(new Signal("INT"), new SignalHandler () {
//			public void handle(Signal sig) {
//			}
//		});

		processOptions(args);
		if (Env.debugOption) Debug.openSocket(); //2006.4.27 by inui
		
		// 実行

		if(Env.oneLiner!=null) {
			// 一行実行の場合はそれを優先
			REPL.processLine(Env.oneLiner);
		} else {
			// ソースありならソースを解釈実行、なしなら REPL。
			if(Env.srcs.isEmpty()) {
				if (Env.stdinLMN) run(new InputStreamReader(System.in)); //2006.07.11 inui
				else if (Env.stdinTAL) { //2006.07.11 inui
					try {
						run(RulesetParser.parse(new InputStreamReader(System.in)));
					} catch (ParseException e) {}
				}
				else REPL.run();
			} else {
				run(Env.srcs);
				if(Env.fREMAIN) REPL.run();
			}
		}
		
		if(Env.fREPL) {
			REPL.run();
		}
	}
	
	/**
	 * Javaのバージョンをチェックする
	 * <p>java1.4以上を使っていないとエラー出力する</p>
	 */
	public static void checkVersion() {
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
			Env.majorVersion=major;
			Env.minorVersion=minor;
		// うまくいかなかった場合は無視する
		} catch (SecurityException e) {
		} catch (NoSuchElementException e) {
		} catch (NumberFormatException e) {
		}		
	}
	
	/**
	 * コマンドライン引数の処理
	 * @param args 引数
	 */
	public static void processOptions(String[] args) {
		// --args 以降ならばfalse
		boolean isSrcs = true;
		for(int i = 0; i < args.length;i++){
			// 必ずlength>0, '-'ならオプション
			// -> 引数を "" にすると長さ 0 になるのでチェックする。
			// 2006/07/11 --args　以降を全てLMNtalプログラムへのコマンドライン引数とするように変更 by kudo
			if(isSrcs && (args[i].length()>0) && (args[i].charAt(0) == '-')){
				if(args[i].length() < 2){ // '-'のみの時
					System.err.println("不明なオプション:" + args[i]);
					System.exit(-1);
				} else { // オプション解釈部
					switch(args[i].charAt(1)){
					case 'c':
						// -c
						// CGI mode.  Output the header 'Content-type:text/html'
						if (args[i].equals("-cgi")) {
							Env.fCGI = true;
						}
						break;
					case 'd':
						/// -d[<0-9>]
						/// Debug output level.
						if (args[i].matches("-d[0-9]")) {
							Env.debug = args[i].charAt(2) - '0';
						} else {
							Env.debug = Env.DEBUG_DEFAULT;
						}
//						System.out.println("debug level " + Env.debug);
						break;
					case 'e':
						/// -e <LMNtal program>
						/// One liner code execution like Perl.
						/// Example: -e 'a,(a:-b)'
						if (++i < args.length)  Env.oneLiner = args[i];
						break;
					case 'g':
						/// -g
						/// GUI mode. Atoms, membranes and links are drawn graphically.
						/// Click button to proceed reaction. Close the window to quit.
						Env.fGUI = true;
						break;
					case 'o':
						/// -o <file>
						/// Specify the output JAR file name.
						Translator.outputName = args[++i];
						break;
					case 'I':
						/// -I <path>
						/// Additional path for LMNtal library. 
						/// This option is available only when --use-source-library option is specified.
						/// Otherwise, LMNtal library must be in your CLASSPATH environment variable. 
						/// The default path is ./lib and ../lib
						compile.Module.libPath.add(args[++i]);
						break;
					case 'L':
						/// -L <path>
						/// Additional path for classpath (inline code compile time)
						Inline.classPath.add(0, new File(args[++i]));
						break;
					case 'O':
						/// -O[<0-9>]  (-O=-O1)
						/// Optimization level.
						/// Intermediate instruction sequences are optimized.
						/// -O1 is equivalent to --optimize-reuse-atom --optimize-reuse-mem,
						///  --optimize-guard-move.
						/// -O2 is equivalent to -O1 now.
						/// -O3 is equivalent to --O2 --optimize-inlining
						int level = -1;
						if (args[i].length() == 2)
							level = 1;
						else if (args[i].length() == 3)
							level = args[i].charAt(2) - '0';
						
						if (level >= 0 && level <= 9) {
							Optimizer.setLevel(level);
							break;
						} else {
							System.err.println("Invalid option: " + args[i]);
							System.exit(-1);
						}
						break;
					case 's':
						/// -s[<0-9>]  (-s=-s3)
						/// Shuffle level.  Select a strategy of rule application.
						///   0: default. use an atom stack for each membrane (LIFO)
						///   1: atoms are selected in some arbitrary manner
						///   2: select atoms and membranes randomly from a membrane
						///   3: select atoms, mems and rules randomly from a membrane
						if (args[i].matches("-s[0-9]")) {
							Env.shuffle = args[i].charAt(2) - '0';
						} else {
							Env.shuffle = Env.SHUFFLE_DEFAULT;
						}
						System.err.println("shuffle level " + Env.shuffle);
						break;
					case 't':
						/// -t
						/// Trace mode.
						Env.fTrace = true;
						break;
					case 'v':
						/// -v[<0-9>]
						/// Verbose output level.
						if (args[i].matches("-v[0-9]")) {
							Env.verbose = args[i].charAt(2) - '0';
						} else {
							Env.verbose = Env.VERBOSE_DEFAULT;
						}
//						System.out.println("verbose level " + Env.verbose);
						break;
					case 'x':
						/// -x <name> <value>
						/// User defined option.
						/// <name>    <value>    description
						/// ===========================================================
						/// screen    max        full screen mode
						/// auto      on         reaction auto proceed mode when GUI on
						/// dump      1          indent mem (etc...)
						/// chorus    filename   output chorus file
						if (i + 2 < args.length) {
							String name  = i+1<args.length ? args[i+1] : "";
							String value = i+2<args.length ? args[i+2] : "";
							Env.extendedOption.put(name, value);
						}
						i+=2;
						break;
					case 'p':
						/// -p[<0-3>]  (-p=-p0)
						/// Profiling program.
						/// Profile level.  Select a detail levels of profiling.
						///   0: execution times of atom driven tests and membrane driven tests for each thread
						///   1: test counts and applying counts and execution times for each rule 
						///   2: 1 + backtrack counts and lockfailure counts for each rule
						///   3: profiles each rule for each test and for each thread
						///      output by CSV
						if (args[i].matches("-p[0-3	]")) {
							Env.profile = args[i].charAt(2) - '0';
						} else {
							Env.profile = Env.PROFILE_DEFAULT;
						}
						if(Env.profile != Env.PROFILE_ALL) System.err.println("profile level " + Env.profile);
						if (Env.profile == Env.PROFILE_ALL) 
							Env.p( Dumper.PROFILE_TABS );
						break;
					case '-': // 文字列オプション						
						if(args[i].equals("--gui")){
							Env.fGUI = true;
						} else if (args[i].equals("--color")) {//2006.11.13 inui
							/// --color
							/// color dump
							Env.colorMode = true;
						} else if(args[i].equals("--compileonly")){
					    	// コンパイル後の中間命令列を出力するモード
							Env.compileonly = true;
							Env.fInterpret = true;
						} else if(args[i].equals("--slimcode")){
					    	// コンパイル後の中間命令列を出力するモード
							Env.compileonly = true;
							Env.slimcode = true;
							Env.fInterpret = true;
						} else if(args[i].equals("--memtest-only")){
					    	// コンパイル後の中間命令列を出力するモード
							Env.fInterpret = true;
							Env.memtestonly = true;
						} else if(args[i].equals("--debug-daemon")){
							// --debug-daemon
							// dump debug message of LMNtalDaemon
							Env.debugDaemon = Env.DEBUG_DEFAULT;
					    } else if (args[i].matches("--event-port")) {//2006.4.27 by inui
					    	Debug.setEventPort(Integer.parseInt(args[i+1]));	
							i++;
						} else if(args[i].equals("--graphic")){
							/// --graphic
							/// Graphic LMNtal mode.
							Env.fGraphic = true;
						} else if(args[i].equals("--wt")){
							/// --wt
							/// tool mode. Use tools.
							Env.fTool = true;
						} else if(args[i].equals("--help")){
							/// --help
							/// Show usage (this).
							System.out.println("usage: java -jar lmntal.jar [options...] [filenames...]");
							// usage: FrontEnd [options...] [filenames...]
							
							// commandline: perl src/help_gen.pl < src/runtime/FrontEnd.java > src/runtime/Help.java
							Help.show();
					        System.exit(-1);
						} else if(args[i].equals("--immediate")){
							/// --immediate
							/// Use a single newline (rather than two newlines)
							/// to start execution in the REPL mode
							Env.replTerm = "immediate";
						} else if (args[i].equals("--interpret")) {
							/// --interpret
							/// Interpret intermediate instruction sequences without translating into Java.
							/// In REPL mode and one-liner, alwas interpret.
							Env.fInterpret = true;
//							} else if (args[i].equals("--keep-temporary-files")) {
//							// --keep-temporary-files
//							// Do not delete the translated Java source.
//							Translator.fKeepSource = true;
						} else if (args[i].equals("--library")) {
							/// --library
							/// Generate library.
							Env.fLibrary = true;
						} else if (args[i].startsWith("--max-string-length=")) { //2006.07.02 inui
							/// --max-string-length=<integer>
							/// Set <integer> as translator's maxStringLength (0 <= integer <= 65534)
							Translator.maxStringLength = Integer.parseInt(args[i].substring(20));
						} else if (args[i].equals("--nd")) {
							/// --nd
							/// Nondeterministic mode. Execute the all reduction paths.
							Env.ndMode = Env.ND_MODE_ND_ALL;
						} else if (args[i].equals("--nd2")) {
							Env.ndMode = Env.ND_MODE_ND_ANSCESTOR;
						} else if (args[i].equals("--nd3")) {
							Env.ndMode = Env.ND_MODE_ND_NOTHING;
						} else if (args[i].equals("--interactive")) {
							/// --interactive
							/// Interactive mode. This option is available only in the nondeterministic mode.
							Env.fInteractive = true;
						} else if(args[i].equals("--optimize-grouping")) {
							/// --optimize-grouping
							/// Group the head instructions. (EXPERIMENTAL)
							Optimizer.fGrouping = true;
						} else if(args[i].equals("--optimize-guard-move")) {
							/// --optimize-guard-move
							/// Move up the guard instructions.
							Optimizer.fGuardMove = true;
						} else if(args[i].equals("--optimize-merging")) {
							/// --optimize-merging
							/// Merge instructions.
							Optimizer.fMerging = true;
							Env.fMerging = true;
						} else if(args[i].equals("--optimize-systemrulesetsinlining")) {
							Optimizer.fSystemRulesetsInlining = true;
						} else if(args[i].equals("--optimize-inlining")) {
							/// --optimize-inlining
							/// Inlining tail jump.
							Optimizer.fInlining = true;
						} else if(args[i].equals("--optimize-loop")) {
							/// --optimize-loop
							/// Use loop instruction. (EXPERIMENTAL)
							Optimizer.fLoop = true;
						} else if(args[i].equals("--optimize-reuse-atom")) {
							/// --optimize-reuse-atom
							/// Reuse atoms.
							Optimizer.fReuseAtom = true;
						} else if(args[i].equals("--optimize-reuse-mem")) {
							/// --optimize-reuse-mem
							/// Reuse mems.
							Optimizer.fReuseMem = true;
						} else if(args[i].matches("--port")){
							/// --port portnumber
							/// Specifies the port number that LMNtalDaemon listens on. The default is 60000. 
							/// Only dynamic and private ports defined by IANA is usable: port 49152 through 65535. 
							
							if (args[i+1].matches("\\d*")) {
								try{ 
									/*
									 * http://www.iana.org/assignments/port-numbers
									 * 
									 * DYNAMIC AND/OR PRIVATE PORTS
									 *  The Dynamic and/or Private Ports are those from 49152 through 65535
									 */
									int portnum = Integer.parseInt(args[i+1]);
									if(portnum < 49152 || portnum > 65535){
										System.err.println("Invalid option: " + args[i] + " " + args[i+1]);
										System.err.println("only port 49152 through 65535 is available");
										System.exit(-1);
									}
									Env.daemonListenPort = portnum;
								} catch (NumberFormatException e){
									//e.printStackTrace();
									System.err.println("Invalid option: " + args[i] + " " + args[i+1]);
									System.err.println("Cannot parse as integer: " + args[i+1]);
									System.exit(-1);
								}
							} else {
								System.err.println("Invalid option: " + args[i] + " " + args[i+1]);
								System.exit(-1);
							}
							
							i++;
						} else if (args[i].equals("--pp0")) {
							// 暫定オプション
							Env.preProcess0 = true;
						} else if (args[i].startsWith("--public-dir=")) {
							// 開発者用オプション
							// --public-dir=<dir>
							// use <dir> as public directory
							Translator.publicDirName = args[i].substring(13);
						} else if(args[i].equals("--remain")){
							/// --remain
							/// Processes remain in REPL mode
							Env.fREMAIN = true;
//						} else if(args[i].equals("--REPL")){
//							/// --REPL
//							/// REPL(Read-Eval-Print-Loop) mode
//							Env.fREPL = true;
//							Env.fREMAIN = true;
						} else if (args[i].matches("--request-port")) {//2006.4.27 by inui
							Debug.setRequestPort(Integer.parseInt(args[i+1]));
							i++;
						} else if (args[i].equals("--stdin-lmn")) { //2006.07.11 inui
							/// --stdin-lmn
							/// read LMNtal program from standard input
							Env.stdinLMN = true;
						} else if (args[i].equals("--stdin-tal")) { //2006.07.11 inui
							/// --stdin-tal
							/// read LMNtal intermediate instruction list from standard input
							Env.stdinTAL = true;
						} else if (args[i].startsWith("--stdlib-name=")) {
							// 開発者用オプション
							// --stdlib-name=<name>
							// use <name> as standard library name
							Translator.stdlibName = args[i].substring(14);
						} else if(args[i].equals("--start-daemon")){
							/// --start-daemon
							/// Start LMNtalDaemon
							Env.startDaemon = true;			
						} else if(args[i].equals("--showproxy")){
							/// --showproxy
							/// Show proxy atoms
							Env.hideProxy = false;
						} else if(args[i].equals("--hideruleset")){
							/// --hideruleset
							/// Hide ruleset
							Env.showruleset = false;
						} else if(args[i].equals("--hiderule")){
							/// --hiderule
							/// Hide rule names
							Env.showrule = false;
						} else if(args[i].equals("--dump-converted-rules")){
							/// --show-converted_rules
							/// Dump converted rules
							Env.dumpConvertedRules = true;
						} else if (args[i].startsWith("--thread-max=")) {
							/// --thread-max=<integer>
							/// set <integer> as the upper limit of threads occured in leftside rules.
							Env.threadMax = Integer.parseInt(args[i].substring(13));
						} else if (args[i].startsWith("--temporary-dir=")) {
							/// --temporary-dir=<path>
							/// use <path> as temporary directory
							Translator.baseDirName = args[i].substring(16);
							Translator.fKeepSource = true;
						} else if (args[i].equals("--use-source-library")) {
							/// --use-source-library
							/// Use source libraries in lib/src and lib/public.
							Env.fUseSourceLibrary = true;
						} else if (args[i].equals("--debug")) {
							/// --debug
							/// run command-line debugger.
							Env.debugOption = true;
							Env.fInterpret = true;
						} else if (args[i].equals("--nothread")) {
							// 暫定オプション
							// スレッドルールの変換を行わない
							Env.fThread = false;
						} else if (args[i].equals("--type")){
							// --type
							// enable type check
							// ( 今はまだ非公開 )
							Env.fType = true;
						} else if (args[i].startsWith("--type-count-level=")){
							// --type-count-level
							// set count-analysis level.
							int ctlevel = 0;
							try{
								ctlevel = Integer.parseInt(args[i].substring(19));
							}catch(NumberFormatException e){
								ctlevel = Env.COUNT_DEFAULT; // 最大限
							}
							if(ctlevel > Env.COUNT_APPLYANDMERGEDETAIL)
								ctlevel = Env.COUNT_APPLYANDMERGEDETAIL;
							Env.quantityInferenceLevel = ctlevel;
							Env.fType = true;
						} else if (args[i].equals("--type-argument")){
							// --type-argument
							// enable argument type system.
							Env.flgArgumentInference = true;
							Env.flgQuantityInference = false;
							Env.flgOccurrenceInference = false;
							Env.fType = true;
						} else if (args[i].equals("--type-count")){
							// --type-count
							// enable count type system
							Env.flgArgumentInference = false;
							Env.flgQuantityInference = true;
							Env.flgOccurrenceInference = false;
							Env.fType = true;
						} else if (args[i].equals("--type-verbose")){
							// --type-verbose
							// print type information.
							Env.fType = true;
							Env.flgShowConstraints = true;
						} else if (args[i].equals("--args")) {
							/// --args
							/// give command-line options after this to LMNtal program.
							isSrcs = false;
						} else if (args[i].equals("--safe")) {
							// 060804 safe mode
							Env.safe = true;
							Env.maxStep = Integer.parseInt(args[++i]);
						} else {
							System.err.println("Invalid option: " + args[i]);
							System.err.println("Use option --help to see a long list of options.");
							System.exit(-1);
						}
						break;
					default:
						System.err.println("Invalid option: " + args[i]);
						System.err.println("Use option --help to see a long list of options.");
						System.exit(-1);
					}
				}
			}else{ // '-'以外で始まるものは (実行ファイル名, argv[0], arg[1], ...) とみなす
				if(isSrcs) {
					Env.srcs.add(args[i]);
				} else {
					Env.argv.add(args[i]);
				}
			}
		}
		//オプションの正規化
		if (Env.ndMode != Env.ND_MODE_D) {
			if (Env.fInterpret) {
				System.err.println("Non Deterministic execution is not supported in interpreted mode");
				System.exit(-1);
			}
			if (Env.shuffle < Env.SHUFFLE_DONTUSEATOMSTACKS)
				Env.shuffle = Env.SHUFFLE_DONTUSEATOMSTACKS;
			Env.fMemory = false;
		}
		//REPL と one-liner では常に解釈実行
		if (Env.oneLiner != null || Env.srcs.isEmpty()) {
			Env.fInterpret = true;
		}
		
		if(Env.fCGI) {
			System.setErr(System.out);
			System.out.println("Content-type: text/html\n");
		}
		
		//start LMNtalDaemon
		if(Env.startDaemon){
			String classpath = System.getProperty("java.class.path");
			String newCmdLine =
				new String(
						"java -classpath"
						+ " "
						+ classpath
						+ " "
						+"daemon.LMNtalDaemon"
						+ " "
						+ Env.debugDaemon
						+ " "
						+ Env.daemonListenPort
						); 
			
			//System.out.println(newCmdLine);
			try {
				Process daemon = Runtime.getRuntime().exec(newCmdLine);

				//daemonが起動するまで待つ
				//TODO 既にLMNtalDaemonが起動していたら起動しない
				InputStreamReader daemonStdout =new InputStreamReader(daemon.getInputStream()); 
				for(int i = 0;  i < 10; i++){ //ネットワークインタフェースがあがってない時は永久にready()はfalseなので
					if(daemonStdout.ready()) break;
					try {
						Thread.sleep(100);
						//System.out.println("LMNtalDaemon not yet started...");
						//System.out.println(daemonStdout.ready());
					} catch (InterruptedException e2) {
						//e2.printStackTrace();
					}
				}
				//daemonStdout.close();

				if(Env.debugDaemon > 0){
					Thread dumpErr = new Thread(new StreamDumper("LMNtalDaemon.stderr", daemon.getErrorStream()),"StreamDumper");
					Thread dumpOut = new Thread(new StreamDumper("LMNtalDaemon.stdout", daemon.getInputStream()),"StreamDumper");
					dumpErr.start();
					dumpOut.start();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	/**
	 * 与えられた名前のファイルたちをくっつけたソースについて、一連の実行を行う。
	 * 
	 * @param files ソースファイル
	 */
	public static void run(List<String> files) {
		InputStream is = null;
		try{
			for(String filename : files) {
				FileInputStream fis = new FileInputStream(filename);
				if(is==null) is = fis;
				else         is = new SequenceInputStream(is, fis);
			}
		} catch(FileNotFoundException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch(SecurityException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// 複数のファイルのときはファイル名が１つに決められない。
		String unitName = files.size()==1 ? (String)files.get(0) : InlineUnit.DEFAULT_UNITNAME;
		if (((String)files.get(0)).endsWith(".tal")) {
			//中間命令列
			try {
				Ruleset rs = RulesetParser.parse(new BufferedReader(new InputStreamReader(is)));
				((InterpretedRuleset)rs).showDetail();
				run(rs);
			} catch (ParseException e) {/*メッセージは出力済み*/}
		} else {
			run( new BufferedReader(new InputStreamReader(is)), unitName );
		}
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
		if(Env.preProcess0) src = preProcess0(src);
		try {
			compile.structure.Membrane m;
			Env.clearErrors();
			try {
				LMNParser lp = new LMNParser(src);
				m = lp.parse();
			}	
			catch (ParseException e) {
				Env.p("Compilation Failed");
				Env.e(e.getMessage());
				return;	
			}

			/* 2006/06/09 by kudo */
			if(Env.fType){
				TypeInferer tci = new TypeInferer(m);
				try{
					tci.infer();
//					tci.printAllConstraints();
				}catch (TypeException e){
					Env.p("Type Inference Failed");
					Env.e("TYPE ERROR: " + e.getMessage());
//					tci.printAllConstraints();
					return;
				}
			}
			
			Ruleset rs;
			if (Env.fInterpret) {
				rs = RulesetCompiler.compileMembrane(m, unitName);
				Inline.makeCode();
				if (Env.nErrors > 0) {
					Env.e("Compilation Failed");
					return;
				}
			} else {
				try {
					if (!Translator.init(unitName)) {
						//エラーメッセージは出力済み
						return;
					}
					//実際の Translate は、compileMembrane の中で、ルールセットを生成した後に行っている。
					//わかりにくいのでなおした方が良いかもしれない。
					rs = RulesetCompiler.compileMembrane(m, unitName);
					if (Env.nErrors > 0) {
						Env.e("Compilation Failed");
						return;
					}
					if (Translator.genInlineCode()) {
						Translator.genModules(m);
						if (!Env.fLibrary) {
							Translator.genMain((InterpretedRuleset)rs, m);
						}
						Translator.genJAR();
					}
				} catch (IOException e) {
					Env.e("Failed to write Translated File. " + e.getLocalizedMessage());
					return;
				} finally {
					Translator.deleteTemporaryFiles();
				}
			}
			
			((InterpretedRuleset)rs).showDetail();
			m.showAllRules();
			if (Env.compileonly) {
				//ソースから読み込んだライブラリのルールセットを表示（--use-source-library指定時）
				for (String libName : Module.loaded) {
					compile.structure.Membrane mem = (compile.structure.Membrane)Module.memNameTable.get(libName);
					Iterator it2 = mem.rulesets.iterator();
					while (it2.hasNext()) {
						((InterpretedRuleset)it2.next()).showDetail();
					}
				}
				//モジュールのルールセット一覧を表示（同一ソース内モジュールと、--use-source-library指定時のライブラリ）
				Module.showModuleList();
				//インラインコード一覧を出力
				Inline.initInline();
				Inline.showInlineList();
				return;
			}
			
			if (Env.fInterpret) {
				Debug.setUnitName(unitName);
				run(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
//			Env.e("!! catch !! "+e+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
	}
	/**
	 * 与えられた初期データ生成ルールセットを元に、一連の実行を行う。
	 * @param rs (:-m) というルール１つだけからなるルールセット
	 */
	public static void run(Ruleset rs) {
		// 060804 safe mode
		Env.counter = 0;
		try {
			// 実行
			LMNtalRuntime rt = new LMNtalRuntime();
//			LMNtalRuntimeManager.init();

			Membrane root = rt.getGlobalRoot();
			
			if(Env.fREMAIN) {
				if(Env.remainedRuntime!=null) {
					root.moveCellsFrom(Env.remainedRuntime.getGlobalRoot());
					root.rulesets.addAll(Env.remainedRuntime.getGlobalRoot().rulesets);
				}
			}
			
			Env.initGUI();
			Env.initGraphic();
			Env.initTool();

			

			
			if(Env.fGUI) Env.gui.setRootMem(root);
//			if(Env.f3D) Env.threed.lmnPanel.getGraph3DLayout().setRootMem(root);
//			root.asyncLock();
			boolean t = Env.fTrace;
			Env.fTrace = false;
			rs.react(root);
			Env.fTrace = t;
//			root.asyncUnlock();
//			rt.asyncFlag = false;
			
			//by inui
			if (Env.debugOption) {
				Task.initTrace();
				Debug.init();
				Debug.inputCommand();//2006.4.27 by inui
			}

			boolean ready = true;
			
			if (Env.gui != null) {
				Env.gui.onTrace();
			}

//			if(Env.fUNYO){
//				unyo.Mediator.sync(root);
//			}
			
			/*TODO:3d calc*/
			/*nakano*
			if (Env.threed != null) {
				Env.threed.lmnPanel.getGraph3DLayout().init();
				if (!Env.threed.onTrace())  ready = false;
			}
			*/
			if (ready) {
				((Task)root.getTask()).execAsMasterTask(); //rt.exec();

				if (Env.profile == Env.PROFILE_ALL) {
					Env.d( "Execution Result:" );
					Env.p( Dumper.dump(rt.getGlobalRoot()) );
				} //else if (!Env.fTrace && Env.verbose > 0 && Env.ndMode == Env.ND_MODE_D) {
					else if (Env.verbose > 0 && Env.ndMode == Env.ND_MODE_D) {
					Env.d( "Execution Result:" );
					Env.p( Dumper.dump(rt.getGlobalRoot()) );
				}
				if(Env.getExtendedOption("chorus") != ""){ Output.out(Env.getExtendedOption("chorus"), rt.getGlobalRoot()); }
				if (Env.gui != null) {
					Env.gui.onTrace();
				}
			}
			if(Env.fREMAIN) {
				Env.remainedRuntime = rt;
			}
			if (Env.LMNgraphic != null)  Env.LMNgraphic = null;
			
//			LMNtalRuntimeManager.terminateAll();
//			LMNtalRuntimeManager.terminateAllThreaded();
			//if(true) System.out.println("FrontEnd: terminateAll() finished!");
//			LMNtalRuntimeManager.disconnectFromDaemon();
			
			if (Env.debugOption) //2006.4.26 by inui
				Debug.terminate();
			
			// 060831 okabe
			Env.theRuntime.terminate();
			
		} catch (Exception e) {
			e.printStackTrace();
//			Env.e("!! catch !! "+e+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
	}
	/**
	 * プリプロセッサ0
	 * 
	 * リンク<u> ... :- ...  ==> リンク ... :- unary(リンク) | ...
	 * 
	 * 同様に、
	 *   u -> unary
	 *   g -> ground
	 *   s -> string
	 *   i -> int
	 * 
	 * 例：
	 *   a(Hah<u>), b(A<g>):-rhs.
	 *  ==>
	 *   a(Hah), b(A):-ground(A), unary(Hah), |rhs.
	 * 
	 * @param r
	 * @return
	 */
	static Reader preProcess0(Reader r) {
		try {
			BufferedReader br = new BufferedReader(r);
			String s;
			StringBuffer sb=new StringBuffer();
			while((s=br.readLine())!=null) {
				sb.append(s);
			}
			s = sb.toString();
			s = s.replaceAll(":\\-([^|.]*)\\.", ":-|$1.");
//			System.out.println(s);
			
			String b=s, a;
			{
				for(a=b;;b=a) {
					a = a.replaceAll("([A-Z][0-9a-zA-Z]*)<u>(.*?)\\:\\-(.*?)\\|", "$1$2:-unary($1), $3|");
					a = a.replaceAll("([A-Z][0-9a-zA-Z]*)<s>(.*?)\\:\\-(.*?)\\|", "$1$2:-string($1), $3|");
					a = a.replaceAll("([A-Z][0-9a-zA-Z]*)<i>(.*?)\\:\\-(.*?)\\|", "$1$2:-int($1), $3|");
					a = a.replaceAll("([A-Z][0-9a-zA-Z]*)<g>(.*?)\\:\\-(.*?)\\|", "$1$2:-ground($1), $3|");
					if(b.equals(a)) break; // "stable"
//					System.out.println(a);
				}
			}
			System.out.println(a);
			return new StringReader(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
// TODO 初期配置で子タスクを作る
package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import type.TypeException;
import type.TypeInferer;
import util.Util;

import compile.Module;
import compile.Optimizer;
import compile.RulesetCompiler;
import compile.parser.LMNParser;
import compile.parser.ParseException;

public class FrontEnd
{
	public static void main(String[] args)
	{
		checkVersion();

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				Inline.terminate();
			}
		});

		processOptions(args);

		if (Env.oneLine)
		{
			run(new StringReader(Env.oneLineCode), InlineUnit.DEFAULT_UNITNAME);
		}
		else
		{
			run(Env.srcs);
		}
	}

	/**
	 * Javaのバージョンをチェックする
	 * <p>
	 * java1.4以上を使っていないとエラー出力する
	 * </p>
	 */
	public static void checkVersion()
	{
		// バージョンチェック by 水野
		try {
			String ver = System.getProperty("java.version");
			StringTokenizer tokenizer = new StringTokenizer(ver, ".");
			int major = Integer.parseInt(tokenizer.nextToken());
			int minor = Integer.parseInt(tokenizer.nextToken());
			if (major < 1 || (major == 1 && minor < 4)) {
				Util.errPrintln("use jre 1.4 or higher!!");
				System.exit(-1);
			}
			Env.majorVersion = major;
			Env.minorVersion = minor;
			// うまくいかなかった場合は無視する
		} catch (SecurityException e) {
		} catch (NoSuchElementException e) {
		} catch (NumberFormatException e) {
		}
	}

	/**
	 * コマンドライン引数の処理
	 * 2011-10-04 追記 (shinobu): コマンドラインオプションのHelp用文字列の記述は
	 * 行コメントの冒頭を "//@ " とするように統一しました（help_gen.plも修正済み）
	 * @param args
	 *            引数
	 */
	public static void processOptions(String[] args) {
		// --args 以降ならばfalse
		boolean isSrcs = true;
		for (int i = 0; i < args.length; i++) {
			// 必ずlength>0, '-'ならオプション
			// -> 引数を "" にすると長さ 0 になるのでチェックする。
			// 2006/07/11 --args　以降を全てLMNtalプログラムへのコマンドライン引数とするように変更 by kudo
			if (isSrcs && (args[i].length() > 0) && (args[i].charAt(0) == '-')) {
				if (args[i].length() < 2) { // '-'のみの時
					Util.errPrintln("不明なオプション:" + args[i]);
					System.exit(-1);
				} else { // オプション解釈部
					switch (args[i].charAt(1)) {
					case 'e':
						//@ -e <LMNtal program>
						//@ One liner code execution like Perl.
						//@ Example: -e 'a,(a:-b)'
						if (++i < args.length)
						{
							Env.oneLine = true;
							Env.oneLineCode = args[i];
						}
						break;
					case 'd':
						//@ -d[<0-9>]
						//@ Debug output level.
						if (args[i].matches("-d[0-9]")) {
							Env.debug = args[i].charAt(2) - '0';
						} else {
							Env.debug = Env.DEBUG_DEFAULT;
						}
						// System.out.println("debug level " + Env.debug);
						break;
					case 'I':
						//@ -I <path>
						//@ Additional path for LMNtal library.
						//@ This option is available only when --use-source-library
						//@ option is specified.
						//@ Otherwise, LMNtal library must be in your CLASSPATH
						//@ environment variable.
						//@ The default path is ./lib and ../lib
						compile.Module.libPath.add(args[++i]);
						break;
					case 'L':
						//@ -L <path>
						//@ Additional path for classpath (inline code compile time)
						Inline.classPath.add(0, new File(args[++i]));
						break;
					case 'O':
						//@ -O[<0-9>]  (-O=-O1)
						//@ Optimization level.
						//@ Intermediate instruction sequences are optimized.
						//@ -O1 is equivalent to --optimize-reuse-atom, --optimize-reuse-mem,
						//@  --optimize-guard-move.
						//@ -O2 is equivalent to -O1 now.
						//@ -O3 is equivalent to --O2 --optimize-inlining
						int level = -1;
						if (args[i].length() == 2)
							level = 1;
						else if (args[i].length() == 3)
							level = args[i].charAt(2) - '0';

						if (level >= 0 && level <= 9) {
							Optimizer.setLevel(level);
							break;
						} else {
							Util.errPrintln("Invalid option: " + args[i]);
							System.exit(-1);
						}
						break;
					case 'v':
						//@ -v[<0-9>]
						//@ Verbose output level.
						if (args[i].matches("-v[0-9]")) {
							Env.verbose = args[i].charAt(2) - '0';
						} else {
							Env.verbose = Env.VERBOSE_DEFAULT;
						}
						// System.out.println("verbose level " + Env.verbose);
						break;
					case 'x':
						//@ -x <name> <value>
						//@ User defined option.
						//@ <name> <value> description
						//@ ===========================================================
						//@ screen max         : full screen mode
						//@ auto   on          : reaction auto proceed mode when GUI on
						//@ dump   1           : indent mem (etc...)
						//@ dump2  propertyfile: apply LMNtal prettyprinter
						//@                      to output
						//@ chorus filename    : output chorus file
						if (i + 2 < args.length) {
							String name = i + 1 < args.length ? args[i + 1] : "";
							String value = i + 2 < args.length ? args[i + 2] : "";
							Env.extendedOption.put(name, value);
						}
						i += 2;
						break;
					case '-': // 文字列オプション
						if (args[i].equals("--compileonly")) {
							// コンパイル後の中間命令列を出力するモード
							Env.compileonly = true;
						} else if (args[i].equals("--slimcode")) {
							// コンパイル後の中間命令列を出力するモード
							Env.compileonly = true;
							Env.slimcode = true;
						} else if (args[i].equals("--use-findatom2")) {
							// Env.compileonly = true;
							Env.slimcode = true;
							Env.findatom2 = true;
							Optimizer.fGuardMove = true; // これをtrueにしないと動かない
						} else if (args[i].equals("--memtest-only")) {
							Env.memtestonly = true;
						} else if (args[i].equals("--help")) {
							//@ --help
							//@ Show usage (this).
							Util
							.println("usage: java -jar lmntal.jar [options...] [filenames...]");
							// usage: FrontEnd [options...] [filenames...]

							// commandline: perl src/help_gen.pl <
							// src/runtime/FrontEnd.java > src/runtime/Help.java
							Help.show();
							System.exit(-1);
						} else if (args[i].equals("--optimize-grouping")) {
							//@ --optimize-grouping
							//@ Group the head instructions. (EXPERIMENTAL)
							Optimizer.fGrouping = true;
						} else if (args[i].equals("--optimize-guard-move")) {
							//@ --optimize-guard-move
							//@ Move up the guard instructions.
							Optimizer.fGuardMove = true;
						} else if (args[i].equals("--optimize-merging")) {
							//@ --optimize-merging
							//@ Merge instructions.
							Optimizer.fMerging = true;
							Env.fMerging = true;
						} else if (args[i]
						                .equals("--optimize-systemrulesetsinlining")) {
							Optimizer.fSystemRulesetsInlining = true;
						} else if (args[i].equals("--optimize-inlining")) {
							//@ --optimize-inlining
							//@ Inlining tail jump.
							Optimizer.fInlining = true;
						} else if (args[i].equals("--optimize-loop")) {
							//@ --optimize-loop
							//@ Use loop instruction. (EXPERIMENTAL)
							Optimizer.fLoop = true;
						} else if (args[i].equals("--optimize-reuse-atom")) {
							//@ --optimize-reuse-atom
							//@ Reuse atoms.
							Optimizer.fReuseAtom = true;
						} else if (args[i].equals("--optimize-reuse-mem")) {
							//@ --optimize-reuse-mem
							//@ Reuse mems.
							Optimizer.fReuseMem = true;
						} else if (args[i].equals("--optimize-slimoptimizer")) {

						} else if (args[i].equals("--pp0")) {
							// 暫定オプション
							Env.preProcess0 = true;
						} else if (args[i].equals("--stdin-lmn")) { // 2006.07.11
							// inui
							//@ --stdin-lmn
							//@ read LMNtal program from standard input
							Env.stdinLMN = true;
						} else if (args[i].equals("--showlongrulename")) {
							Env.showlongrulename = true;
						} else if (args[i].equals("--dump-converted-rules")) {
							//@ --show-converted_rules
							//@ Dump converted rules
							Env.dumpConvertedRules = true;
						} else if (args[i].startsWith("--thread-max=")) {
							//@ --thread-max=<integer>
							//@ set <integer> as the upper limit of threads occured
							//@ in leftside rules.
							Env.threadMax = Integer.parseInt(args[i]
							                                      .substring(13));
						} else if (args[i].equals("--use-source-library")) {
							//@ --use-source-library
							//@ Use source libraries in lib/src and lib/public.
							Env.fUseSourceLibrary = true;
						} else if (args[i].equals("--debug")) {
							//@ --debug
							//@ run command-line debugger.
							Env.debugOption = true;
						} else if (args[i].equals("--type")) {
							// --type
							// enable type check
							// ( 今はまだ非公開 )
							Env.fType = true;
						} else if (args[i].startsWith("--type-count-level=")) {
							// --type-count-level
							// set count-analysis level.
							int ctlevel = 0;
							try {
								ctlevel = Integer.parseInt(args[i]
								                                .substring(19));
							} catch (NumberFormatException e) {
								ctlevel = Env.COUNT_DEFAULT; // 最大限
							}
							if (ctlevel > Env.COUNT_APPLYANDMERGEDETAIL)
								ctlevel = Env.COUNT_APPLYANDMERGEDETAIL;
							Env.quantityInferenceLevel = ctlevel;
							Env.fType = true;
						} else if (args[i].equals("--type-argument")) {
							// --type-argument
							// enable argument type system.
							Env.flgArgumentInference = true;
							Env.flgQuantityInference = false;
							Env.flgOccurrenceInference = false;
							Env.fType = true;
						} else if (args[i].equals("--type-count")) {
							// --type-count
							// enable count type system
							Env.flgArgumentInference = false;
							Env.flgQuantityInference = true;
							Env.flgOccurrenceInference = false;
							Env.fType = true;
						} else if (args[i].equals("--type-verbose")) {
							// --type-verbose
							// print type information.
							Env.fType = true;
							Env.flgShowConstraints = true;
						} else if (args[i].equals("--args")) {
							//@ --args
							//@ give command-line options after this to LMNtal program.
							isSrcs = false;
						} else if (args[i].equals("--compile-rule")) {
							// -- --compile-rule
							// compile one rule (for SLIM model checking mode)
							Env.compileRule = true;
							Env.compileonly = true;
						} else if (args[i].equals("--hl") || args[i].equals("--hl-opt")) { //seiji
							boolean slimcode = false;
							for (int j = 0; j < args.length; j++)
								if (args[j].equals("--slimcode")) slimcode = true;
							if (slimcode) {
								Env.hyperLink = true;
								if (args[i].equals("--hl-opt")) 
									Env.hyperLinkOpt = true;
							} else {
								Util.errPrintln("Can't use option " + args[i] + " without option --slimcode.");
								System.exit(-1);
							}
						} else if (args[i].equals("--use-swaplink")) {
							Env.useSwapLink = true;
						} else if (args[i].equals("--use-cyclelinks")) {
							Env.useCycleLinks = true;
						} else {
							Util.errPrintln("Invalid option: " + args[i]);
							Util
							.errPrintln("Use option --help to see a long list of options.");
							System.exit(-1);
						}
						break;
					default:
						Util.errPrintln("Invalid option: " + args[i]);
						Util.errPrintln("Use option --help to see a long list of options.");
					System.exit(-1);
					}
				}
			} else { // '-'以外で始まるものは (実行ファイル名, argv[0], argv[1], ...) とみなす
				if (isSrcs) {
					Env.srcs.add(args[i]);
				} else {
					Env.argv.add(args[i]);
				}
			}
		}

		if (Env.slimcode) {
			Optimizer.fReuseAtom = false;
			// Env.findatom2 = true;
		}
	}

	/**
	 * 与えられた名前のファイルたちをくっつけたソースについて、一連の実行を行う。
	 * @param files ソースファイル名のリスト
	 */
	public static void run(List<String> files)
	{
		InputStream is = null;
		try
		{
			for (String filename : files)
			{
				InputStream fis = new FileInputStream(filename);
				if (is == null)
				{
					is = fis;
				}
				else
				{
					is = new SequenceInputStream(is, fis);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			Util.println(e.getMessage());
			System.exit(-1);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		// 複数のファイルのときはファイル名が１つに決められない。
		String unitName = files.size() == 1 ? files.get(0) : InlineUnit.DEFAULT_UNITNAME;
		run(new BufferedReader(new InputStreamReader(is)), unitName);
	}

	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * 
	 * @param src Reader型で表されたソース
	 * @param unitName ファイル名。インラインコードのキャッシュはこの名前ベースで管理される。
	 */
	public static void run(Reader src, String unitName)
	{
		if (Env.preProcess0)
		{
			src = preProcess0(src);
		}

		try
		{
			Env.clearErrors();

			// 構文解析
			// 抽象構文木からコンパイル時データ構造を生成する
			compile.structure.Membrane m;
			try
			{
				LMNParser lp = new LMNParser(src);
				m = lp.parse();
			}
			catch (ParseException e)
			{
				Env.p("Compilation Failed");
				Env.e(e.getMessage());
				return;
			}

			if (Env.fType)
			{
				TypeInferer tci = new TypeInferer(m);
				try
				{
					tci.infer();
					// tci.printAllConstraints();
				}
				catch (TypeException e)
				{
					Env.p("Type Inference Failed");
					Env.e("TYPE ERROR: " + e.getMessage());
					// tci.printAllConstraints();
					return;
				}
			}

			// コンパイル、コード生成
			// コンパイル時データ構造からルールセットの中間命令列を生成する
			Ruleset rs = RulesetCompiler.compileMembrane(m, unitName);
			if (Env.getErrorCount() > 0)
			{
				Env.e("Compilation Failed");
				return;
			}

			if (Env.compileRule)
			{
				try
				{
					List<Ruleset> rulesets = m.rulesets;
					InterpretedRuleset r = (InterpretedRuleset)rulesets.get(0);
					r.rules.get(0).showDetail();
				}
				catch (Exception e)
				{
					Env.e("Compilation Failed: no rule");
				}
				return;
			}
			else
			{
				// 通常はこっち？
				showIL((InterpretedRuleset)rs, m);
			}

			if (Env.compileonly)
			{
				// ソースから読み込んだライブラリのルールセットを表示（--use-source-library指定時）
				for (String libName : Module.loaded)
				{
					compile.structure.Membrane mem = (compile.structure.Membrane) Module.memNameTable
					.get(libName);
					for (Ruleset r : mem.rulesets)
					{
						((InterpretedRuleset)r).showDetail();
					}
				}
				// モジュールのルールセット一覧を表示（同一ソース内モジュールと、--use-source-library指定時のライブラリ）
				Module.showModuleList();
				// インラインコード一覧を出力
				Inline.initInline();
				Inline.showInlineList();
				return;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 中間命令列を出力する
	 * @param rs 初期化ルールのみを含むルールセット
	 * @param m グローバル膜
	 */
	private static void showIL(InterpretedRuleset rs, compile.structure.Membrane m)
	{
		rs.showDetail();
		m.showAllRules();
	}

	/**
	 * プリプロセッサ0
	 * 
	 * リンク<u> ... :- ... ==> リンク ... :- unary(リンク) | ...
	 * 
	 * 同様に、 u -> unary g -> ground s -> string i -> int
	 * 
	 * 例： a(Hah<u>), b(A<g>):-rhs. ==> a(Hah), b(A):-ground(A), unary(Hah),
	 * |rhs.
	 * 
	 * @param r
	 * @return
	 */
	static Reader preProcess0(Reader r) {
		try {
			BufferedReader br = new BufferedReader(r);
			String s;
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			s = sb.toString();
			s = s.replaceAll(":\\-([^|.]*)\\.", ":-|$1.");
			// System.out.println(s);

			String b = s, a;
			{
				for (a = b;; b = a) {
					a = a.replaceAll(
							"([A-Z][0-9a-zA-Z]*)<u>(.*?)\\:\\-(.*?)\\|",
					"$1$2:-unary($1), $3|");
					a = a.replaceAll(
							"([A-Z][0-9a-zA-Z]*)<s>(.*?)\\:\\-(.*?)\\|",
					"$1$2:-string($1), $3|");
					a = a.replaceAll(
							"([A-Z][0-9a-zA-Z]*)<i>(.*?)\\:\\-(.*?)\\|",
					"$1$2:-int($1), $3|");
					a = a.replaceAll(
							"([A-Z][0-9a-zA-Z]*)<g>(.*?)\\:\\-(.*?)\\|",
					"$1$2:-ground($1), $3|");
					if (b.equals(a))
						break; // "stable"
					// System.out.println(a);
				}
			}
			Util.println(a);
			return new StringReader(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

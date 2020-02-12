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
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
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
	private static Charset sourceCharset = Charset.forName("UTF-8");

	public static void main(String[] args)
	{
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
		else if (Env.stdinLMN)
		{
			run(new BufferedReader(new InputStreamReader(System.in)), InlineUnit.DEFAULT_UNITNAME);
		}
		else
		{
			if (Env.srcs.isEmpty())
			{
				System.err.println("no input file.");
				System.exit(1);
			}
			run(Env.srcs);
		}
	}

	/**
	 * <p>コマンドライン引数の処理</p>
	 * <p>
	 * 2011-10-04 追記 (shinobu): コマンドラインオプションのHelp用文字列の記述は
	 * 行コメントの冒頭を {@code "//@ "} とするように統一しました（help_gen.plも修正済み）</p>
	 * <p>Help 出力を更新するには、Ant タスクの help を実行するか、端末上で
	 * <code>perl src/help_gen.pl &lt; src/runtime/FrontEnd.java &gt; src/runtime/Help.java</code><br>
	 * を実行します。</p>
	 * @param args 引数
	 */
	private static void processOptions(String[] args)
	{
		boolean isSrcs = true; // --args 以降ならばfalse
		for (int i = 0; i < args.length; i++)
		{
			// 必ずlength>0, '-'ならオプション
			// -> 引数を "" にすると長さ 0 になるのでチェックする。
			// 2006/07/11 --args　以降を全てLMNtalプログラムへのコマンドライン引数とするように変更 by kudo
			if (isSrcs && (args[i].length() > 0) && (args[i].charAt(0) == '-'))
			{
				if (args[i].length() < 2) // '-'のみの時
				{
					Util.errPrintln("不明なオプション:" + args[i]);
					System.exit(1);
				}
				else // オプション解釈部
				{
					switch (args[i].charAt(1))
					{
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
						if (args[i].matches("-d[0-9]"))
						{
							Env.debug = args[i].charAt(2) - '0';
						}
						else
						{
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
						{
							level = 1;
						}
						else if (args[i].length() == 3)
						{
							level = args[i].charAt(2) - '0';
						}
						if (level >= 0 && level <= 9)
						{
							Optimizer.setLevel(level);
							break;
						}
						else
						{
							Util.errPrintln("Invalid option: " + args[i]);
							System.exit(1);
						}
						break;
					case 'v':
						//@ -v[<0-9>]
						//@ Verbose output level.
						if (args[i].matches("-v[0-9]"))
						{
							Env.verbose = args[i].charAt(2) - '0';
						}
						else
						{
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
						if (i + 2 < args.length)
						{
							String name = i + 1 < args.length ? args[i + 1] : "";
							String value = i + 2 < args.length ? args[i + 2] : "";
							Env.extendedOption.put(name, value);
						}
						i += 2;
						break;
					case '-': // 文字列オプション
						if (args[i].equals("--args"))
						{
							//@ --args
							//@ give command-line options after this to LMNtal program.
							isSrcs = false;
						}
						else
						{
							i = processLongOptions(args, i);
						}
						break;
					default:
						Util.errPrintln("Invalid option: " + args[i]);
						Util.errPrintln("Use option --help to see a long list of options.");
						System.exit(1);
					}
				}
			}
			else // '-'以外で始まるものは (実行ファイル名, argv[0], argv[1], ...) とみなす
			{
				if (isSrcs)
				{
					Env.srcs.add(args[i]);
				}
				else
				{
					Env.argv.add(args[i]);
				}
			}
		}

		if (!Optimizer.forceReuseAtom)
		{
			Optimizer.fReuseAtom = false;
			// Env.findatom2 = true;
		}
	}

	private static int processLongOptions(String[] args, int i)
	{
		String opt = args[i];
		if (opt.equals("--compileonly"))
		{
			// コンパイル後の中間命令列を出力するモード
			//@ --compileonly
			//@ Output compiled intermediate instruction sequence only.
			//@ Compiler will not translate to Java or execute the program.
			Env.compileonly = true;
		}
		else if (opt.equals("--slimcode"))
		{
			// SLIM用の中間命令列を出力するモード
			// v1.46以降はオプションがなくても強制的にオンになる
			// （互換性のため分岐を残している）
			//@ --slimcode
			//@ Output intermediate instruction sequence to be executed by SLIM.
			Env.compileonly = true;
		}
		else if (opt.equals("--charset"))
		{
			//@ --charset
			//@ Specify charset of LMNtal source code (the default charset is UTF-8).
			if (++i < args.length)
			{
				try
				{
					sourceCharset = Charset.forName(args[i]);
				}
				catch (UnsupportedCharsetException e)
				{
					System.err.println("Warning: '" + e.getCharsetName() + "' is not available (default charset '" + sourceCharset + "' is used)");
				}
			}
			else
			{
				System.err.println("Error: no argument was provided to '--charset'");
				System.exit(1);
			}
		}
		//@ --use-findatom2
		//@ Use findatom2 instruction (findatom with history).
		else if (opt.equals("--use-findatom2"))
		{
			// Env.compileonly = true;
			Env.findatom2 = true;
			Optimizer.fGuardMove = true; // これをtrueにしないと動かない
		}
		else if (opt.equals("--memtest-only"))
		{
			//@ --memtest-only
			//@ Use membrane test only.
			Env.memtestonly = true;
		}
		else if (opt.equals("--help"))
		{
			//@ --help
			//@ Show usage (this).
			Util.println("usage: java -jar lmntal.jar [options...] [filenames...]");
			Help.show();
			System.exit(0);
		}
		else if (opt.equals("--version"))
		{
			//@ --version
			//@ Show LMNtal compiler version.
			Util.println(String.format("LMNtal Compiler %s (%s)", Env.LMNTAL_COMPILER_VERSION, Env.RELEASE_DATE));
			System.exit(0);
		}
		else if (opt.equals("--optimize-grouping"))
		{
			//@ --optimize-grouping
			//@ Group the head instructions. (EXPERIMENTAL)
			Optimizer.fGrouping = true;
		}
		else if (opt.equals("--optimize-guard-move"))
		{
			//@ --optimize-guard-move
			//@ Move up the guard instructions.
			Optimizer.fGuardMove = true;
		}
		else if (opt.equals("--optimize-merging"))
		{
			//@ --optimize-merging
			//@ Merge instructions.
			Optimizer.fMerging = true;
			Env.fMerging = true;
		}
		else if (opt.equals("--optimize-systemrulesetsinlining"))
		{
			Optimizer.fSystemRulesetsInlining = true;
		}
		else if (opt.equals("--optimize-inlining"))
		{
			//@ --optimize-inlining
			//@ Inlining tail jump.
			Optimizer.fInlining = true;
		}
		else if (opt.equals("--optimize-loop"))
		{
			//@ --optimize-loop
			//@ Use loop instruction. (EXPERIMENTAL)
			Optimizer.fLoop = true;
		}
		else if (opt.equals("--optimize-reuse-atom"))
		{
			//@ --optimize-reuse-atom
			//@ Reuse atoms.
			Optimizer.fReuseAtom = true;
			Optimizer.forceReuseAtom = true;
		}
		else if (opt.equals("--optimize-reuse-mem"))
		{
			//@ --optimize-reuse-mem
			//@ Reuse mems.
			Optimizer.fReuseMem = true;
		}
		else if (opt.equals("--optimize-slimoptimizer"))
		{
		}
		else if (opt.equals("--pp0"))
		{
			// 暫定オプション
			Env.preProcess0 = true;
		}
		else if (opt.equals("--stdin-lmn")) // 2006.07.11
		{
			// inui
			//@ --stdin-lmn
			//@ read LMNtal program from standard input
			Env.stdinLMN = true;
		}
		else if (opt.equals("--showlongrulename"))
		{
			Env.showlongrulename = true;
		}
		else if (opt.equals("--dump-converted-rules"))
		{
			//@ --show-converted_rules
			//@ Dump converted rules
			Env.dumpConvertedRules = true;
		}
		else if (opt.startsWith("--thread-max="))
		{
			//@ --thread-max=<integer>
			//@ set <integer> as the upper limit of threads occured
			//@ in leftside rules.
			Env.threadMax = Integer.parseInt(args[i].substring(13));
		}
		else if (opt.equals("--use-source-library"))
		{
			//@ --use-source-library
			//@ Use source libraries in lib/src and lib/public.
			Env.fUseSourceLibrary = true;
		}
		else if (opt.equals("--debug"))
		{
			//@ --debug
			//@ run command-line debugger.
			Env.debugOption = true;
		}
		else if (opt.equals("--type"))
		{
			// --type
			// enable type check
			// ( 今はまだ非公開 )
			Env.fType = true;
		}
		else if (opt.startsWith("--type-count-level="))
		{
			// --type-count-level
			// set count-analysis level.
			int ctlevel = 0;
			try
			{
				ctlevel = Integer.parseInt(args[i].substring(19));
			}
			catch (NumberFormatException e)
			{
				ctlevel = Env.COUNT_DEFAULT; // 最大限
			}
			if (ctlevel > Env.COUNT_APPLYANDMERGEDETAIL)
			{
				ctlevel = Env.COUNT_APPLYANDMERGEDETAIL;
			}
			Env.quantityInferenceLevel = ctlevel;
			Env.fType = true;
		}
		else if (opt.equals("--type-argument"))
		{
			// --type-argument
			// enable argument type system.
			Env.flgArgumentInference = true;
			Env.flgQuantityInference = false;
			Env.flgOccurrenceInference = false;
			Env.fType = true;
		}
		else if (args[i].equals("--type-count"))
		{
			// --type-count
			// enable count type system
			Env.flgArgumentInference = false;
			Env.flgQuantityInference = true;
			Env.flgOccurrenceInference = false;
			Env.fType = true;
		}
		else if (args[i].equals("--type-verbose"))
		{
			// --type-verbose
			// print type information.
			Env.fType = true;
			Env.flgShowConstraints = true;
		}
		else if (args[i].equals("--compile-rule"))
		{
			// -- --compile-rule
			// compile one rule (for SLIM model checking mode)
			Env.compileRule = true;
			Env.compileonly = true;
		}
		else if (opt.equals("--hl") || opt.equals("--hl-opt")) //seiji
		{
			//@ --hl, --hl-opt
			//@ Use hyperlinks (HyperLMNtal).
			Env.hyperLink = true;
			if (opt.equals("--hl-opt"))
			{
				Env.hyperLinkOpt = true;
			}
		}
		else if (opt.equals("--use-swaplink"))
		{
			// リンク操作に swaplink 命令を使用する (shinobu)
			//@ --use-swaplink
			//@ Use swaplink instruction to manipulate links.
			Env.useSwapLink = true;
		}
		else if (opt.equals("--use-cyclelinks"))
		{
			// リンク操作に cyclelinks 命令を使用する (shinobu)
			//@ --use-cyclelinks
			//@ Use cyclelinks instruction to manipulate links.
			Env.useCycleLinks = true;
		}
		else if (opt.equals("--use-atomlistop"))
		{
			// アトムリスト操作に必要な中間命令を出力する (aoyama)
			//@ --use-atomlistop
			//@ Output intermediate instructions to optimize execution by SLIM
			//@ by dynamically modifying atomlist (a data structure in SLIM).
			Env.useSwapLink = true;
			Env.useAtomListOP = true;
		}
		else if (opt.equals("--verbose-linkext"))
		{
			// swaplink/cyclelinks使用時において置換過程を出力する開発者用オプション (shinobu)
			//@ --verbose-linkext
			//@ (For developers) Output process of permutation by swaplink/cyclelinks.
			Env.verboseLinkExt = true;
		}
		else if (opt.equals("--Wempty-head"))
		{
			//@ --Wempty-head
			//@ Warn if there are any rules that has an empty head.
			Env.warnEmptyHead = true;
		}
		else if (opt.equals("--interpret"))
		{
			// DO NOTHING
		}
		else
		{
			Util.errPrintln("Invalid option: " + opt);
			Util.errPrintln("Use option --help to see a long list of options.");
			System.exit(1);
		}
		return i;
	}

	/**
	 * 与えられた名前のファイルたちをくっつけたソースについて、一連の実行を行う。
	 * @param files ソースファイル名のリスト（少なくとも1つの要素を含む）
	 */
	private static void run(List<String> files)
	{
		try
		{
			InputStream is = new FileInputStream(files.get(0));
			for (int i = 1; i < files.size(); i++)
			{
				is = new SequenceInputStream(is, new FileInputStream(files.get(i)));
			}

			// 複数のファイルのときはファイル名が１つに決められない。
			String unitName = files.size() == 1 ? files.get(0) : InlineUnit.DEFAULT_UNITNAME;
			run(new BufferedReader(new InputStreamReader(is, sourceCharset)), unitName);
		}
		catch (FileNotFoundException e)
		{
			Util.println(e.getMessage());
			System.exit(1);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * 
	 * @param src Reader型で表されたソース
	 * @param unitName ファイル名。インラインコードのキャッシュはこの名前ベースで管理される。
	 */
	private static void run(Reader src, String unitName)
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
			compile.structure.Membrane m = null;
			try
			{
				LMNParser lp = new LMNParser(src);
				m = lp.parse();
			}
			catch (ParseException e)
			{
				Env.p("Compilation Failed");
				Env.e(e.getMessage());
				System.exit(1);
			}

			if (Env.fType)
			{
				if (!analyseTypes(m))
				{
					System.exit(1);
				}
			}

			// コンパイル、コード生成
			// コンパイル時データ構造からルールセットの中間命令列を生成する
			Ruleset rs = RulesetCompiler.compileMembrane(m, unitName);
			if (Env.getErrorCount() > 0)
			{
				Env.e("Compilation Failed");
				System.exit(1);
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
					System.exit(1);
				}
				System.exit(0);
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
				System.exit(0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static boolean analyseTypes(compile.structure.Membrane m)
	{
		try
		{
			TypeInferer tci = new TypeInferer(m);
			tci.infer();
			// tci.printAllConstraints();
			return true;
		}
		catch (TypeException e)
		{
			Env.p("Type Inference Failed");
			Env.e("TYPE ERROR: " + e.getMessage());
			// tci.printAllConstraints();
		}
		return false;
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
	private static Reader preProcess0(Reader r) {
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

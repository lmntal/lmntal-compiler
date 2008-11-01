/*
 * 作成日: 2006/01/17
 *
 */
package debug;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import runtime.Dumper;
import runtime.Env;
import runtime.InterpretedRuleset;
import runtime.Membrane;
import runtime.Rule;
import runtime.Ruleset;
import util.Util;

/**
 * LMNtalデバッガ
 * コマンドラインデバッガと，eclipse のデバッガの両方の機能が含まれる．
 * @author inui
 */
public class Debug {
	/** アトム主導テストを表す定数  */
	public static final int ATOM = 1;
	
	/** 膜主導テストを表す定数  */
	public static final int MEMBRANE = 2;
	
	//直前に適用されたルールの行番号 */
	private static int currentLineNumber;
	
	//直前に適用されたルールのテストタイプ
	private static int testType;
	
	//ブレークポイントの集合
	private static Set<Integer> breakPoints = new HashSet<Integer>();
	
	//全ルールのセット
	private static Set<Rule> rules = new HashSet<Rule>();
	
	//実行中のファイル名(とりあえずファイルが1つであると仮定)
	private static String unitName;
	
	//ソースプログラム
	private static Vector<String> source;
	
	//最後に表示した行番号
	private static int lastLineno;
	private static int listsize = 10;
	
	//ポート番号
	private static int requestPort = -1;
	private static int eventPort = -1;
	
	//ソケット
	private static ServerSocket requestSocket = null;
	private static ServerSocket eventSocket = null;
	
	//状態
	private static boolean isRunning = false;
	private static boolean isStepping = false;
	
	//通信
	private static BufferedReader requestIn;
	private static PrintWriter requestOut;
	private static PrintWriter eventOut;
	
	/**
	 * 全膜のルールを再帰的に収集する
	 * @param mem ルート膜
	 */
	private static void collectAllRules(Membrane mem) {
		Iterator<Ruleset> itr = mem.rulesetIterator();
		while (itr.hasNext()) {
			Object o = itr.next();
			if (!(o instanceof InterpretedRuleset)) continue;
			InterpretedRuleset ruleset = (InterpretedRuleset)o;
			for(Rule rule : ruleset.rules){
				Debug.rules.add(rule);
			}
		}
		
		Iterator<Membrane> memIterator = mem.memIterator();
		while (memIterator.hasNext()) {
			collectAllRules(memIterator.next());
		}
	}
	
	/**
	 * 少なくとも最初に1回実行する
	 */
	public static void init() {
		lastLineno = 1;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(unitName));
			String s = null;
			source = new Vector<String>();
			source.add("** system rule **");
			while ((s = br.readLine()) != null)
				source.add(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//全てのルールを収集する
		Membrane rootMem = Env.theRuntime.getGlobalRoot();
		collectAllRules(rootMem);
	}
	
	/**
	 * 行番号を指定してブレークポイントを切り替える
	 * @param lineno
	 */
	public static void toggleBreakPointAt(int lineno) {
		//すでに存在するブレークポイントなら削除
		if (breakPoints.contains(lineno)) breakPoints.remove(lineno);
		else breakPoints.add(lineno);
	}
	
	/**
	 * 行番号を指定してブレークポイントを追加する
	 * @param lineno
	 * @return 指定された行にルールがなかったらfalse
	 */
	public static boolean addBreakPoint(int lineno) {
		for (Rule r : rules) {
			if (r.lineno == lineno) {
				breakPoints.add(lineno);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ルール名を指定してブレークポイントを追加する
	 * @param name ルール名
	 * @return 指定されたルール名がなかったらfalse
	 */
	public static boolean addBreakPoint(String name) {
		for (Rule r : rules) {
			if (r.name != null && r.name.equals(name)) {
				breakPoints.add(r.lineno);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 今ブレークポイントかどうか調べる
	 * @return
	 */
	public static boolean isBreakPoint() {

		if (isStepping) return true;
		for (Integer lineno : breakPoints) {
			if (currentLineNumber == lineno.intValue()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ブレークポイント
	 * @param r 適用されたルール
	 * @param testType アトム主導テストか膜主導テストかを表す定数
	 * ATOM, MEMBRANE
	 */
	public static void breakPoint(int r, int testType) {
		System.out.println(r);
		currentLineNumber = r;
		Debug.testType = testType;
	}
	
	/**
	 * セットされているブレークポイントのiteratorを返します。
	 * @return iterator
	 */
	public static Iterator<Integer> breakPointIterator() {
		return breakPoints.iterator();
	}
	
	/**
	 * 現在ブレークしている行番号を取得する
	 * @return 現在ブレークしている行番号
	 */
	public static int getCurrentRuleLineno() {
		if (currentLineNumber == 0) return -1;
		return currentLineNumber;
	}

	/**
	 * unitNameをセットします
	 * @param unitName ファイル名
	 */
	public static void setUnitName(String unitName) {
		Debug.unitName = unitName;
	}

	/**
	 * 現在ブレークしているルールのテストの種類
	 * @return 膜主導テスト (MEMBRANE_TEST) かアトム主導テスト (ATOM_TEST) 
	 */
	public static int getTestType() {
		return testType;
	}
	
	/**
	 * ルールのイテレータを返します
	 */
	public static Iterator<Rule> ruleIterator() {
		if (rules == null) return null;
		return rules.iterator();
	}
	
	/**
	 * 実行しているファイル名を返す
	 * @return ファイル名
	 */
	public static String getUnitName() {
		return unitName;
	}
	
	/**
	 * プログラムを表示します
	 */
	public static void showList() {
		int i;
		for (i = lastLineno; i < Math.min(lastLineno+listsize, source.size()); i++) {
			Util.println(i+"\t"+source.get(i));
		}
		lastLineno = i;
	}

	/**
	 * 標準入力からコマンドを受け付ける．
	 * eclipse から起動しているときはソケット通信する．
	 */
	public static void inputCommand() {
		if (isRunning) {
			if (!isStepping) eventOut.println("Breakpoint at "+getUnitName()+":"+currentLineNumber);
			//else out.println("step "+currentRule);
			eventOut.println(currentLineNumber+" "+source.get(currentLineNumber));	
		}
		
		isStepping = false;
		try {
			while (true) {
				Util.print("(ldb) ");
//				System.err.println("クライアントからの接続をポート"+requestPort+"で待ちます");
				String s = requestIn.readLine().trim();
//				System.err.println("'"+s+"'を受信しました");
				if (s.equals("")) {
				} else if (s.startsWith("b")) {//ブレークポイントの切り替え
					String[] ss = s.split("[ \t]+");
					if (ss.length < 2) continue;
					try {
						int lineNumber = Integer.parseInt(ss[1]);
						if (addBreakPoint(lineNumber)) Util.println("Breakpoint "+breakPoints.size()+", line"+lineNumber);
						else Util.println("No rlue at line "+lineNumber);
					} catch (NumberFormatException e) {
						if (addBreakPoint(ss[1])) Util.println("Breakpoint "+breakPoints.size()+", "+ss[1]);
						else Util.println("No rlue "+ss[1]);
					}
				} else if (s.startsWith("c")) {//実行を再開
					Util.println("Continuing.");
					break;
				} else if (s.startsWith("h")) {
					showHelp();
				} else if (s.startsWith("l")) {
					String[] ss = s.split("[ \t]+");
					if (ss.length >= 2) {
						try {
							int lineNumber = Integer.parseInt(ss[1]);
							lastLineno = Math.max(1, lineNumber-listsize/2);
						} catch (NumberFormatException e) {
							Util.println("Rule \""+ss[1]+"\" not defined.");
							continue;
						}
					}
					showList();
				} else if (s.startsWith("n")) {//ステップ実行
					if (!isRunning)	Util.println("The program is not being run.");
					else isStepping = true;
					break;
				} else if (s.startsWith("p")) {//内部状態を表示
					Membrane memToDump = Env.theRuntime.getGlobalRoot();
					requestOut.println(Dumper.dump(memToDump));
				} else if (s.startsWith("r")) {//実行開始
					if (isRunning) {
						Util.println("The program being debugged has been started already.");
					} else {
						isRunning = true;
						Util.println("Starting program: "+getUnitName()+"\n");
						break;
					}
				} else if (s.startsWith("f")) {//フレーム情報を表示（今は現在の行番号を表示）
					Util.println(currentLineNumber);
				} else if (s.startsWith("q")) {//デバッグ終了
					System.exit(0);//TODO exitしちゃって良いかな？
				} else if (s.startsWith("set l")) {
					String[] ss = s.split("[ \t]+");
					if (ss.length < 3) continue;
					try {
						listsize = Integer.parseInt(ss[2]);
					} catch (NumberFormatException e) {
						Util.println("No symbol \""+ss[2]+"\" in current context.");
					}
				} else {
					Util.println("Undefined command: \""+s+"\".  Try \"help\".");
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		currentLineNumber = 0;
	}
	
	/**
	 * requestPortをセットします。
	 * @param requestPort
	 */
	public static void setRequestPort(int requestPort) {
		Debug.requestPort = requestPort;
	}
	
	/**
	 * eventPortをセットします。
	 * @param eventPort
	 */
	public static void setEventPort(int eventPort) {
		Debug.eventPort = eventPort;
	}
	
	/**
	 * ソケットを開く
	 */
	public static void openSocket() {
		try{
			if (requestPort == -1) {
				requestIn = new BufferedReader(new InputStreamReader(System.in));
				eventOut = new PrintWriter(System.out, true);
				requestOut = new PrintWriter(System.out, true);
			} else {
				//サーバーソケットの生成
				requestSocket = new ServerSocket(requestPort);
				Util.errPrintln("クライアントからの接続をポート"+requestPort+"で待ちます");
				
				eventSocket = new ServerSocket(eventPort);
				Util.errPrintln("クライアントからの接続をポート"+eventPort+"で待ちます");
				
				Socket socket1 = requestSocket.accept();
				Util.errPrintln(socket1.getInetAddress() + "から接続を受付ました");
				requestIn = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				requestOut = new PrintWriter(socket1.getOutputStream(), true);
				
				Socket socket2 = eventSocket.accept();
				Util.errPrintln(socket2.getInetAddress() + "から接続を受付ました");
				eventOut = new PrintWriter(socket2.getOutputStream(), true);
			}
		} catch (IOException e) {}
	}
	
	//デバッガを終了する
	public static void terminate() {
		Util.println("");
		eventOut.println("Program exited normally.");
		try {
			requestIn.close();
			eventOut.close();
			requestOut.close();
			if (requestSocket != null) requestSocket.close();
			if (eventSocket != null) eventSocket.close();
		} catch (IOException e) {
			Util.errPrintln(e);
		}
	}
	
	public static void showHelp() {
		Util.println("List of commands:");
		Util.println("");
		Util.println("break -- Set breakpoint at specified line or function");
		Util.println("continue -- Continue program being debugged");
		Util.println("help -- Print list of commands");
		Util.println("list -- List specified line");
		Util.println("next -- Step program");
		Util.println("print -- dump membrane");
		Util.println("run -- Start debugged program");					
		//System.out.println("frame -- Select and print a stack frame");
		Util.println("quit -- Exit ldb");
	}
}

/*
 * 作成日: 2006/01/17
 *
 */
package debug;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import runtime.Dumper;
import runtime.Env;
import runtime.InterpretedRuleset;
import runtime.MasterLMNtalRuntime;
import runtime.Membrane;
import runtime.Rule;

/**
 * LMNtalデバッガ
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
	
	//ブレークポイントのリスト
	private static List breakPoints = new ArrayList();
	
	//全ルールのセット
	private static Set rules = new HashSet();
	
	//実行中のファイル名(とりあえずファイルが1つであると仮定)
	private static String unitName;
	
	//ソースプログラム
	private static Vector source;
	
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
		Iterator itr = mem.rulesetIterator();
		while (itr.hasNext()) {
			Object o = itr.next();
			if (!(o instanceof InterpretedRuleset)) continue;
			InterpretedRuleset ruleset = (InterpretedRuleset)o;
			List rules = ruleset.rules;
			Iterator ruleIterator = rules.iterator();
			while (ruleIterator.hasNext()) {
				Rule rule = (Rule)ruleIterator.next();
				Debug.rules.add(rule);
			}
		}
		
		Iterator memIterator = mem.memIterator();
		while (memIterator.hasNext()) {
			collectAllRules((Membrane)memIterator.next());
		}
	}
	
	/**
	 * 少なくとも最初に1回実行する
	 */
	public static void init() {
		if (Env.debugFrame != null && Env.debugFrame.restart) {
			currentLineNumber = -1;
			Env.debugFrame.repaint();
			return;
		}
		
		//TODO DebugFrameに任せるべき処理
		try {
			FileReader fr = new FileReader(unitName);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer buf = new StringBuffer();
			String s = null;
			int lineno = 0;
			buf.append("<style>pre {font-size:"+(Env.fDEMO ? 14 : 10)+"px; font-family:monospace;}</style>\n");
			buf.append("<pre>\n");
			source = new Vector();
			source.add("*System Rule*");
			while ((s = br.readLine()) != null) {
				source.add(s);
				Matcher m = Pattern.compile("(.*)(//|%)(.*)").matcher(s);
				if (m.matches()) {//コメントだったらその他の色付けはしない
					s = m.group(1)+"<font color=green>"+m.group(2)+m.group(3)+"</font>";
				} else {
					s = s.replace("=", "<font color=fuchsia>=</font>");
					s = s.replace(":-", "<font color=fuchsia>:-</font>");
					s = s.replace("|", "<font color=fuchsia>|</font>");
					s = s.replace("{", "<font color=blue>{</font>");
					s = s.replace("}", "<font color=blue>}</font>");
				}
				buf.append("  "+s+"\n");
				lineno++;
			}
			buf.append("</pre>\n");
			s = buf.toString();
			s = s.replaceAll("/\\*", "<font color=green>/*");
			s = s.replaceAll("\\*/", "*/</font>");
			if (Env.debugFrame != null) Env.debugFrame.setSourceText(s, lineno);
			if (Env.gui != null) Env.gui.repaint();
		} catch (IOException e) {
			System.err.println(e);
		}
		
		//全てのルールを収集する
		Membrane rootMem = ((MasterLMNtalRuntime)Env.theRuntime).getGlobalRoot();
		collectAllRules(rootMem);
	}
	
	/**
	 * 行番号を指定してブレークポイントを切り替える
	 * @param lineno
	 */
	public static void toggleBreakPointAt(int lineno) {
		//すでに存在するブレークポイントなら削除
		for (int i = 0; i < breakPoints.size(); i++) {
			if (((Integer)breakPoints.get(i)).intValue() == lineno) {
				breakPoints.remove(i);
				return;
			}
		}
		Iterator iter = ruleIterator();
		while (iter.hasNext()) {
			int r = ((Rule)iter.next()).lineno;
			if (r == lineno) {
				breakPoints.add(new Integer(lineno));
				break;
			}
		}
	}
	
	/**
	 * 行番号を指定してブレークポイントを追加する
	 * @param lineno
	 * @return 指定された行にルールがなかったらfalse
	 */
	public static boolean addBreakPoint(int lineno) {
		Iterator iter = ruleIterator();
		while (iter.hasNext()) {
			int r = ((Rule)iter.next()).lineno;
			if (r == lineno) {
				breakPoints.add(new Integer(lineno));
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
		Iterator itr = breakPointIterator();
		while (itr.hasNext()) {
			if (currentLineNumber == ((Integer)itr.next()).intValue()) {
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
		currentLineNumber = r;
		Debug.testType = testType;
	}
	
	/**
	 * セットされているブレークポイントのiteratorを返します。
	 * @return iterator
	 */
	public static Iterator breakPointIterator() {
		return breakPoints.iterator();
	}
	
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

	public static int getTestType() {
		return testType;
	}
	
	/**
	 * ルールのイテレータを返します
	 */
	public static Iterator ruleIterator() {
		if (rules == null) return null;
		return rules.iterator();
	}
	
	public static String getUnitName() {
		return unitName;
	}

	/**
	 * コマンド受付
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
				System.out.print("(ldb) ");
//				System.err.println("クライアントからの接続をポート"+requestPort+"で待ちます");
				String s = requestIn.readLine().trim();
//				System.err.println("'"+s+"'を受信しました");
				if (s.equals("")) {
				} else if (s.startsWith("b")) {//ブレークポイントの切り替え
					String[] ss = s.split("[ \t]+");
					int lineNumber = Integer.parseInt(ss[1]);
					if (addBreakPoint(lineNumber)) System.out.println("Breakpoint "+breakPoints.size()+", line"+lineNumber);
					else System.out.println("No rlue at line "+lineNumber);
				} else if (s.startsWith("c")) {//実行を再開
					System.out.println("Continuing.");
					break;
				} else if (s.startsWith("n")) {//ステップ実行
					if (!isRunning)	System.out.println("The program is not being run.");
					else isStepping = true;
					break;
				} else if (s.startsWith("p")) {//内部状態を表示
					Membrane memToDump = ((MasterLMNtalRuntime)Env.theRuntime).getGlobalRoot();
					requestOut.println(Dumper.dump(memToDump));
				} else if (s.startsWith("r")) {//実行開始
					if (isRunning) {
						System.out.println("The program being debugged has been started already.");
					} else {
						isRunning = true;
						System.out.println("Starting program: "+getUnitName()+"\n");
						break;
					}
				} else if (s.startsWith("f")) {//フレーム情報を表示（今は現在の行番号を表示）
					System.out.println(currentLineNumber);
				} else if (s.startsWith("q")) {//デバッグ終了
					System.exit(0);//TODO exitしちゃって良いかな？
				} else {
					System.out.println("Undefined command: \""+s+"\".  Try \"help\".");
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
				System.err.println("クライアントからの接続をポート"+requestPort+"で待ちます");
				
				eventSocket = new ServerSocket(eventPort);
				System.err.println("クライアントからの接続をポート"+eventPort+"で待ちます");
				
				Socket socket1 = requestSocket.accept();
				System.err.println(socket1.getInetAddress() + "から接続を受付ました");
				requestIn = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				requestOut = new PrintWriter(socket1.getOutputStream(), true);
				
				Socket socket2 = eventSocket.accept();
				System.err.println(socket2.getInetAddress() + "から接続を受付ました");
				eventOut = new PrintWriter(socket2.getOutputStream(), true);
			}
		} catch (IOException e) {}
	}
	
	//デバッガを終了する
	public static void terminate() {
		System.out.println();
		eventOut.println("Program exited normally.");
		try {
			requestIn.close();
			eventOut.close();
			requestOut.close();
			if (requestSocket != null) requestSocket.close();
			if (eventSocket != null) eventSocket.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	///////////////////////////////////////////////////
	//DebugFramegが使用
	
	public static void step() {
		isStepping = true;
	}
	
	public static void doContinue() {
		isStepping = false;
	}
}

/*
 * 作成日: 2006/01/17
 *
 */
package runtime;

import java.io.*;
import java.util.*;

import test.GUI.*;

//class ConsolePrintStream extends PrintStream {
//	private JTextArea jt;
//	
//	public ConsolePrintStream(OutputStream os, JTextArea jt) throws FileNotFoundException {
//		super(os);
//		this.jt = jt;
//	}
//
//	@Override
//	public void println(String s) {
//		super.println(s);
//		jt.append(s+"\n");
//	}
//	
//}

/**
 * LMNtalデバッガ
 * @author inui
 */
public class Debug {
	public static final int INIT = 0;
	public static final int RUN = 1;
	public static final int NEXT = 2;
	public static final int CONTINUE = 3;
	public static final int ATOM = 4;
	public static final int MEMBRANE = 5;
	
	/** 直前に適用されたルール */
	private static Rule currentRule;
	
	/** 直前に適用されたルールのテストの種類 */
	private static int testType;
	
	/** ブレークポイント */
	private static List breakPoints = new ArrayList();
	
	/** 全ルール */
	private static Set rules = null;
	
	/** 実行中のファイル名(ファイルが1つであると仮定) */
	private static String unitName;
	
	/** デバッグ状態 */
	private static int state = INIT;
	
	/**
	 * 全膜のルールを再帰的に収集する
	 * @param mem ルート膜
	 */
	private static void collectAllRules(Membrane mem) {
		Iterator itr = mem.rulesetIterator();
		while (itr.hasNext()) {
			InterpretedRuleset ruleset = (InterpretedRuleset)itr.next();
			List rules = ruleset.rules;
			Iterator ruleIterator = rules.iterator();
			while (ruleIterator.hasNext()) {
				Rule rule = (Rule)ruleIterator.next();
				Debug.rules.add(rule);
			}
		}
		
		Iterator memIterator = mem.mems.iterator();
		while (memIterator.hasNext()) {
			collectAllRules((Membrane)memIterator.next());
		}
	}
	
	/**
	 * 少なくとも最初に1回実行する
	 */
	public static void init() {
		try {
			FileReader fr = new FileReader(unitName);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer buf = new StringBuffer();
			String s = null;
			int lineno = 0;
			buf.append("<style>pre {font-size:10px; font-family:monospace;}</style>\n");
			buf.append("<pre>\n");
			while ((s = br.readLine()) != null) {
				buf.append("  "+s.replace(":-", "<font color=red>:-</font>")+"\n");
				lineno++;
			}
			buf.append("</pre>\n");
			s = buf.toString();
			s = s.replaceAll("/\\*", "<font color=green>/*");
			s = s.replaceAll("\\*/", "</font>*/");
			Env.guiDebug.setSourceText(s, lineno);
			Env.gui.repaint();
		} catch (IOException e) {
			System.err.println(e);
		}
		
		//全てのルールを収集する
		Debug.rules = new HashSet();
		Membrane rootMem = ((MasterLMNtalRuntime)Env.theRuntime).getGlobalRoot();
		collectAllRules(rootMem);
		
		//標準出力を切り替える
//		try {
//			System.setOut(new ConsolePrintStream(System.out, ((LMNtalDebugFrame)Env.gui).getConsole()));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 行番号を指定してブレークポイントを切り替える
	 * @param lineno
	 */
	public static void toggleBreakPointAt(int lineno) {
		//すでに存在するブレークポイントなら削除
		for (int i = 0; i < breakPoints.size(); i++) {
			if (((Rule)breakPoints.get(i)).lineno == lineno) {
				breakPoints.remove(i);
				return;
			}
		}
		
		Iterator itr = rules.iterator();
		while (itr.hasNext()) {
			Rule rule = (Rule)itr.next();
			if (rule.lineno == lineno) {
				breakPoints.add(rule);
				System.out.println("Breakpoint "+breakPoints.size()+" at "+rule.name+": file "+unitName+", line "+lineno);
			}
		}
	}
	
	/**
	 * 今ブレークポイントかどうか調べる
	 * @return
	 */
	public static boolean isBreakPoint() {
		if (state == NEXT) return true;
		Iterator itr = breakPointIterator();
		while (itr.hasNext()) {
			if (currentRule != null && currentRule.equals(itr.next())) {
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
	public static void breakPoint(Rule r, int testType) {
		currentRule = r;
		Debug.testType = testType;
	}
	
	/**
	 * ブレークポイントの処理が終了したら呼ぶ
	 * @param state 次のデバッグ状態
	 * NEXT, CONTINUE
	 *
	 */
	public static void endBreakPoint(int state) {
		currentRule = null;
		Debug.state = state;
	}
	
	/**
	 * セットされているブレークポイントのiteratorを返します。
	 * @return iterator
	 */
	public static Iterator breakPointIterator() {
		return breakPoints.iterator();
	}
	
	public static int getCurrentRuleLineno() {
		if (currentRule == null) return -1;
		return currentRule.lineno;
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
}

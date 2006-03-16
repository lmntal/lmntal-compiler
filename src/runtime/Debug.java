/*
 * 作成日: 2006/01/17
 *
 */
package runtime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static int currentRule;
	
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
		
		Iterator memIterator = mem.mems.iterator();
		while (memIterator.hasNext()) {
			collectAllRules((Membrane)memIterator.next());
		}
	}
	
	/**
	 * 少なくとも最初に1回実行する
	 */
	public static void init() {
		if (Env.guiDebug.restart) return;
		
		try {
			FileReader fr = new FileReader(unitName);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer buf = new StringBuffer();
			String s = null;
			int lineno = 0;
			buf.append("<style>pre {font-size:"+(Env.fDEMO ? 14 : 10)+"px; font-family:monospace;}</style>\n");
			buf.append("<pre>\n");
			while ((s = br.readLine()) != null) {
				Matcher m = Pattern.compile("(.*)(//|%)(.*)").matcher(s);
				if (m.matches()) {//コメントだったらその他の色付けはしない
					s = m.group(1)+"<font color=green>"+m.group(2)+m.group(3)+"</font>";
				} else {
					s = s.replace("=", "<font color=fuchsia>=</font>");
					s = s.replace(":-", "<font color=fuchsia>:-</font>");
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
			Env.guiDebug.setSourceText(s, lineno);
			Env.gui.repaint();
		} catch (IOException e) {
			System.err.println(e);
		}
		
		//全てのルールを収集する
		Debug.rules = new HashSet();
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
		
		breakPoints.add(lineno);
	}
	
	/**
	 * 今ブレークポイントかどうか調べる
	 * @return
	 */
	public static boolean isBreakPoint() {
		if (state == NEXT) return true;
		Iterator itr = breakPointIterator();
		while (itr.hasNext()) {
			if (currentRule == ((Integer)itr.next()).intValue()) {
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
		currentRule = 0;
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
		if (currentRule == 0) return -1;
		return currentRule;
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
}

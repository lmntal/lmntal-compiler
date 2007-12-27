package test;

import runtime.Dumper;
import runtime.LMNtalRuntime;
import runtime.Membrane;
import runtime.Ruleset;
import util.Util;
/**
 * LMNtal のメイソ
 * 
 * 
 * 作成日: 2003/10/22
 */
public class DumperTest {
	/**
	 * 実行、出力テストのため、SampleInitRulesetを初期化ルールとして実行、Dumpを行う。
	 */
	public static void main(String[] args) {
		Util.println("before init:");
		Ruleset rule = new SampleInitRuleset();
		LMNtalRuntime mm = new LMNtalRuntime();
		Membrane m = mm.getGlobalRoot();
		rule.react(m);
		Util.println("before exec:");
		Util.println(Dumper.dump(m));
//		mm.exec(); // 仮
		Util.println("after exec:");
		Util.println(Dumper.dump(m));
	}
}

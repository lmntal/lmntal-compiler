package test;

import runtime.LMNtalRuntime;
import runtime.Dumper;
import runtime.Ruleset;
import test.SampleInitRuleset;
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
		System.out.println("before init:");
		Ruleset rule = new SampleInitRuleset();
		LMNtalRuntime mm = new LMNtalRuntime();
		mm.applyRulesetOnce(rule);
		System.out.println("before exec:");
		Dumper.dump(mm.getRoot());
		mm.exec();
		System.out.println("after exec:");
		Dumper.dump(mm.getRoot());
	}
}

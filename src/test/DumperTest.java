package test;

import runtime.*;
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
		Membrane m = mm.getGlobalRoot();
		rule.react(m);
		System.out.println("before exec:");
		System.out.println(Dumper.dump(m));
//		mm.exec(); // 仮
		System.out.println("after exec:");
		System.out.println(Dumper.dump(m));
	}
}

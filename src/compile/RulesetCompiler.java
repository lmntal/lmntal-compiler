/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import compile.structure.*;

/**
 * ルールセットを生成して返す。
 * @author hara
 * 
 */
public class RulesetCompiler {
	
	/**
	 * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
	 * より正確に言うと、与えられた膜構造に対応する膜を1回だけ生成するreactメソッドを
	 * 実装するルールセットを持った膜構造を生成する。
	 * メソッド実行中、膜構造内部にあるルール構造がルールセットにコンパイルされる。
	 * @param m 膜構造
	 * @return 生成したルールセットを持つ膜構造
	 */
	public static Membrane runStartWithNull(Membrane m) {
		Env.c("RulesetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure(root);
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		processMembrane(root);
		Module.genInstruction(root);
		return root;
	}
	
//	public static InterpretedRuleset run(Membrane m) {
//		Env.c("RulesetCompiler.run");
//		processMembrane(m);
//		return (InterpretedRuleset)m.ruleset;
//	}
	
	/**
	 * 与えられた膜の階層下にある全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem) {
		Env.c("RulesetCompiler.processMembrane");
		// 子膜にあるルールをルールセットにコンパイルする
		Iterator it = mem.mems.listIterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			processMembrane(submem);
		}
		// この膜にあるルールをルールセットにコンパイルする
		ArrayList rules = new ArrayList();
//		runtime.Ruleset ruleset = mem.ruleset;
		it = mem.rules.listIterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			// ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
			processMembrane(rs.leftMem); // 一応左辺も
			processMembrane(rs.rightMem);
			//
			RuleCompiler rc = new RuleCompiler(rs);
			rc.compile();
			// ↓ いずれ ruleset.add(rc.theRule); にした方がよい
			rules.add(rc.theRule);
//			((runtime.InterpretedRuleset)ruleset).rules.add(rc.theRule);
		}
		if (Env.fRandom) {
			it = rules.iterator();
			while (it.hasNext()) {
				runtime.Ruleset ruleset = new runtime.InterpretedRuleset();
				((runtime.InterpretedRuleset)ruleset).rules.add(it.next());
				//ruleset.compile();
				mem.rulesets.add(ruleset);
			}
		} else {
			if (rules.size() > 0) {
				runtime.Ruleset ruleset = new runtime.InterpretedRuleset();
				it = rules.iterator();
				while (it.hasNext()) {
					((runtime.InterpretedRuleset)ruleset).rules.add(it.next());
				}
				//ruleset.compile();
				mem.rulesets.add(ruleset);
			}
		}
			
		// ruleset.compile();
	}
	
}

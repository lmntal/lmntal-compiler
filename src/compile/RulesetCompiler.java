/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import runtime.InlineUnit;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SystemRuleset;
import compile.structure.*;
import runtime.InterpretedRuleset;

/**
 * ルールセットを生成して返す。
 * @author hara
 * 
 */
public class RulesetCompiler {
	/**
	 * 与えられた膜構造を生成するreactメソッドを実装するルールセットを生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * 
	 * @param unitName ファイル名
	 * @param m 膜構造
	 * @return (:-m)というルール1つだけからなるルールセット
	 */
	public static Ruleset compileMembrane(Membrane m, String unitName) {
		return (Ruleset)compileMembraneToGeneratingMembrane(m, unitName).rulesets.get(0);
	}
	
	public static Ruleset compileMembrane(Membrane m) {
		return compileMembrane(m, InlineUnit.DEFAULT_UNITNAME);
	}
	
	/**
	 * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
	 * より正確に言うと、与えられた膜構造の内容に対応するプロセスを1回だけ生成するreactメソッドを
	 * 実装するルールセットを唯一のルールセットとして持つ膜構造を生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * @param m 膜構造
	 * @return 生成したルールセットを持つ膜構造
	 */
	protected static Membrane compileMembraneToGeneratingMembrane(Membrane m) {
		return compileMembraneToGeneratingMembrane(m, InlineUnit.DEFAULT_UNITNAME);
	}
	protected static Membrane compileMembraneToGeneratingMembrane(Membrane m, String unitName) {
		Env.c("RulesetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure(root);
		rs.fSuppressEmptyHeadWarning = true;
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		processMembrane(root, unitName);
		Module.resolveModules(root);
		return root;
	}
	
	/**
	 * 与えられた膜の階層下にある全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * <p>ルールをちょうど1つ持つ膜にはちょうど1つのルールセットが追加される。
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem) {
		processMembrane(mem, InlineUnit.DEFAULT_UNITNAME);
	}
	public static void processMembrane(Membrane mem, String unitName) {
		Env.c("RulesetCompiler.processMembrane");
		// 子膜にあるルールをルールセットにコンパイルする
		Iterator it = mem.mems.listIterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			processMembrane(submem, unitName);
		}
		// この膜にあるルール構造をルールオブジェクトにコンパイルする
		ArrayList rules = new ArrayList();
		it = mem.rules.listIterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			// ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
			processMembrane(rs.leftMem, unitName); // 一応左辺も
			processMembrane(rs.rightMem, unitName);
			//
			RuleCompiler rc = new RuleCompiler(rs, unitName);
			rc.compile();
			rules.add(rc.theRule);
		}
		// 生成したルールオブジェクトのリストをルールセット（のセット）にコンパイルする
		InterpretedRuleset ruleset;
		if (!rules.isEmpty()) {
			if (Env.shuffle >= Env.SHUFFLE_RULES) {
				it = rules.iterator();
				while (it.hasNext()) {
					ruleset = new InterpretedRuleset();
					ruleset.rules.add(it.next());
					//ruleset.compile();
					mem.rulesets.add(ruleset);
				}
			} else {
				ruleset = new InterpretedRuleset();
				it = rules.iterator();
				while (it.hasNext()) {
					ruleset.rules.add(it.next());
				}
				//ruleset.compile();
				mem.rulesets.add(ruleset);
			}
		}
		// 必要ならシステムルールセットに登録
		if(mem.is_system_ruleset) {
			//Env.p("Use system_ruleset "+mem);
			Iterator ri = mem.rulesets.iterator();
			while(ri.hasNext()) {
				InterpretedRuleset ir = (InterpretedRuleset)ri.next();
				Iterator rii = ir.rules.iterator();
				while(rii.hasNext()) {
					Rule r = (Rule)rii.next();
					SystemRuleset.ruleset.rules.add(r);
				}
			}
		}
	}
}

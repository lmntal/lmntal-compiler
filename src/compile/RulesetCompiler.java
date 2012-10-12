/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import runtime.Env;
import runtime.InlineUnit;
import runtime.InterpretedRuleset;
import runtime.MergedBranchMap;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SystemRulesets;

import compile.parser.LMNParser;
import compile.parser.ParseException;
import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.RuleStructure;

/**
 * ルールセットを生成して返す。
 * @author hara
 * 
 */
public class RulesetCompiler
{
	private static boolean recursived = false;

	/**
	 * 与えられた膜構造を生成するreactメソッドを実装するルールセットを生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * 
	 * @param unitName ファイル名
	 * @param m 膜構造
	 * @return (:-m)というルール1つだけからなるルールセット
	 */
	public static Ruleset compileMembrane(Membrane m, String unitName)
	{
		return compileMembraneToGeneratingMembrane(m, unitName).rulesets.get(0);
	}

	public static Ruleset compileMembrane(Membrane m)
	{
		return compileMembrane(m, InlineUnit.DEFAULT_UNITNAME);
	}

	/**
	 * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
	 * より正確に言うと、与えられた膜構造の内容に対応するプロセスを1回だけ生成するreactメソッドを
	 * 実装するルールセットを唯一のルールセットとして持つ膜構造を生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * @param m 膜構造
	 * @param unitName
	 * @return 生成したルールセットを持つ膜構造
	 */
	protected static Membrane compileMembraneToGeneratingMembrane(Membrane m, String unitName)
	{
		Env.c("RulesetGenerator.runStartWithNull");

		// グローバルルート膜
		Membrane root = new Membrane(null);

		// 初期構造を生成するルール
		RuleStructure rs = new RuleStructure(root, "(initial rule)");
		rs.fSuppressEmptyHeadWarning = true;
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);

		processMembrane(root, unitName);

		if (Env.fUseSourceLibrary)
		{
			Module.resolveModules(root);
		}
		return root;
	}

	/**
	 * 与えられた膜の階層下にある全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * <p>ルールをちょうど1つ持つ膜にはちょうど1つのルールセットが追加される。
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem)
	{
		processMembrane(mem, InlineUnit.DEFAULT_UNITNAME);
	}

	/**
	 * 与えられた膜の階層下にある全ての {@code RuleStructure} について、対応する {@code Rule} を生成してその膜のルールセットに追加する。
	 * <p>ルールをちょうど1つ持つ膜にはちょうど1つのルールセットが追加される。</p>
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem, String unitName)
	{
		// 子膜にあるルールをルールセットにコンパイルする
		for (Membrane submem : mem.mems)
		{
			processMembrane(submem, unitName);
		}

		List<Rule> rules = new ArrayList<Rule>();

		// この膜にあるルール構造をルールオブジェクトにコンパイルする
		for (RuleStructure rs : mem.rules)
		{
			// ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
			processMembrane(rs.leftMem, unitName); // 一応左辺も
			processMembrane(rs.rightMem, unitName);

			RuleCompiler rc = null;
			try
			{
				rc = new RuleCompiler(rs, unitName);
				rc.compile();
				//2006.1.22 Ruleに行番号を渡す by inui
				rc.theRule.lineno = rs.lineno;
			}
			catch (CompileException e)
			{
				Env.p("    in " + rs.toString() + "\n");
			}
			rules.add(rc.theRule);
		}

		// 編み上げを行う
		Merger merger = new Merger();
		MergedBranchMap mbm = null;
		MergedBranchMap systemmbm = null;
		if (Optimizer.fMerging)
		{
			mbm = merger.Merging(rules, false);
			merger.clear();
			systemmbm = merger.createSystemRulesetsMap();
		}

		// 生成したルールオブジェクトのリストをルールセット（のセット）にコンパイルする
		if (!rules.isEmpty())
		{
			InterpretedRuleset ruleset = new InterpretedRuleset();
			for (Rule r : rules)
			{
				ruleset.rules.add(r);
			}
			ruleset.branchmap = mbm;
			ruleset.systemrulemap = systemmbm;
			Ruleset compiledRuleset = compileRuleset(ruleset);
			mem.rulesets.add(ruleset);
		}
		// 必要ならシステムルールセットに登録
		boolean isSystemRuleset = false;
		for (Atom atom : mem.atoms)
		{
			if (atom.functor.getName().equals("system_ruleset"))
			{
				isSystemRuleset = true;
				break;
			}
		}
		if (isSystemRuleset)
		{
			for (Ruleset r : mem.rulesets)
			{
				InterpretedRuleset ir = (InterpretedRuleset)r;
				SystemRulesets.addUserDefinedSystemRuleset(ir);
				ir.isSystemRuleset = true;
			}
		}
	}

	public static Ruleset compileRuleset(InterpretedRuleset rs)
	{
		return rs; //返すルールセットはそのまま。どうするのが良いのだろうか？
	}
}

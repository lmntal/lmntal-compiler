package runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * compile.RulesetCompiler によって生成される。
 * @author hara, nakajima, n-kato
 */
public final class InterpretedRuleset extends Ruleset
{
	/** このルールセットのローカルID */
	private int id;
	private static int lastId = 600;

	/** とりあえずルールの配列として実装 */
	public List<Rule> rules;
	
	/** 編み上げ後の命令列 */
	public MergedBranchMap branchmap;
	public MergedBranchMap systemrulemap;
	
	/** 現在実行中のルール */
	public Rule currentRule;
	
	// private int backtracks, lockfailure;

	/**
	 * RuleCompiler では、まず生成してからデータを入れ込む。
	 * ので、特になにもしない
	 */
	public InterpretedRuleset()
	{
		rules = new ArrayList<>();
		id = ++lastId;
		branchmap = null;
		systemrulemap = null;
	}

	/** グローバルルールセットID（未定義の場合はnull）*/
	// private String globalRulesetID;

	/**このルールセットのローカルIDを取得する。*/
	public int getId()
	{
		return id;
	}

	/**（仮）*/
	// 061129 okabe runtimeid 廃止による
//	public String getGlobalRulesetID() {
//		// todo ランタイムIDの有効期間を見直す
//		if (globalRulesetID == null) {
//			globalRulesetID = Env.theRuntime.getRuntimeID() + ":" + id;
////			IDConverter.registerRuleset(globalRulesetID, this);
//		}
//		return globalRulesetID;
//	}

	public String toString()
	{
		String ret = "@" + id;
		if (Env.verbose >= Env.VERBOSE_EXPANDRULES)
		{
			ret += dumpRules();
		}
		return ret;
	}
	
	public String dumpRules()
	{
		StringBuilder s = new StringBuilder();
		for (Rule rule : rules)
		{
			s.append(" ");
			s.append(rule);
		}
		return s.toString();
	}

	public String encode()
	{
		StringBuilder s = new StringBuilder();
		boolean isFirst = true;
		for (Rule rule : rules)
		{
			s.append(rule.getFullText().replace("\\n", "").replace("\\r", ""));
			if (isFirst)
			{
				s.append(", ");
				isFirst = false;
			}
		}
		return s.toString();
	}

	public String[] encodeRulesIndividually()
	{
		String[] result = new String[rules.size()];
		int i = 0;
		for (Rule rule : rules)
		{
			result[i] = rule.getFullText().replace("\\n", "").replace("\\r", "");
			if (2 < result[i].length())
			{
				result[i] = result[i].substring(1, result[i].length() - 1);
			}
			i++;
		}
		return result;
	}

	public void showDetail()
	{
		if (isSystemRuleset)
		{
			Env.p("Compiled SystemRuleset @" + id + dumpRules());
		}
		else
		{
			Env.p("Compiled Ruleset @" + id + dumpRules());
		}	
		for (Rule rule : rules)
		{
			rule.showDetail();
		}
		Env.p("");
	}
}

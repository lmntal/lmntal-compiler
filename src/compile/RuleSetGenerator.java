/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.util.Iterator;
import runtime.Env;
import runtime.InterpretedRuleset;
import compile.structure.*;

/**
 * ルールセットを生成して返す。
 * @author hara
 *
 */
public class RuleSetGenerator {
	
	/**
	 * 与えられた膜直属の全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * 
	 * @param m 対象となる膜
	 * @return 指定した膜のルールセット
	 */
	public static InterpretedRuleset run(Membrane m) {
		Env.c("RuleSetGenerator.run");
		processMembrane(m);
		return (InterpretedRuleset)m.ruleset;
	}
	
	/**
	 * 与えられた膜直属の全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * @param m 対象となる膜
	 */
	public static void processMembrane(Membrane m) {
		Env.c("RuleSetGenerator.processMembrane");
		
		Iterator i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			RuleCompiler rc = new RuleCompiler(rs);
			rc.compile();
		}
	}
}

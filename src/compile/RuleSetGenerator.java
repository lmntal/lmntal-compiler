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
	 * 与えられた膜を生成するルールを持った膜を作り、
	 * その直属の全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * 
	 * @param m 対象となる膜
	 * @return 指定した膜のルールセット
	 */
	public static InterpretedRuleset runStartWithNull(Membrane m) {
		Env.c("RuleSetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure();
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		rs.parent = root;
		processMembrane(root);
		return (InterpretedRuleset)root.ruleset;
	}
	
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

/* 
 * 作成日: 2003/10/24
 * 
 */
package compile;

import runtime.Env;
import runtime.InterpretedRuleset;

/**
 * 実行時データ構造を命令列（仮に foo と呼ぶ）に変換する。
 * 
 * じっけん段階
 * @author hara
 *
 */
public class RuleCompilerTest {
	/**
	 * テスト用めいん
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RuleStructure rs = getTestStructure();
		Env.p(rs);
		RuleCompiler rc = new RuleCompiler(rs);
		rc.simplify();
		InterpretedRuleset r = rc.compile();
	}
	
	/**
	 * デバッグ用データ構造をつくる。
	 * 
	 * v, w, ( v :- w, w )
	 * 
	 * @return まく
	 */
	static RuleStructure getTestStructure() {
		// ルート膜の親膜は null
		RuleStructure rs = new RuleStructure();
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = new RuleStructure();
		r.leftMem.atoms.add( new Atom(m, "v", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		
		m.atoms.add( new Atom(m, "v", 0) );
		m.atoms.add( new Atom(m, "w", 0) );
		m.rules.add(r);
		
		rs.leftMem = new Membrane(null);
		rs.rightMem = m;
		return rs;
	}
}


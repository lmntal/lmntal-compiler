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
public class RuleSetGeneratorTest {
	/**
	 * テスト用めいん
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Membrane m = getTestStructure1();
		//RuleStructure rs = getTestStructure2();
		Env.p(m);
		RuleSetGenerator.run(m);
		
		Env.p("Membrane with RuleSet.");
		Env.p(m);
		//r.showDetail();
	}
	
	/**
	 * デバッグ用データ構造をつくる。
	 * 
	 * v, w, ( v :- w, w )
	 * 
	 * @return RuleStructure
	 */
	static Membrane getTestStructure1() {
		// ルート膜の親膜は null
		RuleStructure rs = new RuleStructure();
		rs.parent = new Membrane(null);
		
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = getTestStructure2();
		
		m.atoms.add( new Atom(m, "v", 0) );
		m.atoms.add( new Atom(m, "w", 0) );
		m.rules.add(r);
		r.parent = m;
		
		rs.leftMem = new Membrane(null);
		rs.rightMem = m;
		
		rs.parent.rules.add(rs);
		return rs.parent;
	}
	
	/**
	 * デバッグ用データ構造をつくる。2
	 * 
	 * ( v :- w, w )
	 * 
	 * @return RuleStructure
	 */
	static RuleStructure getTestStructure2() {
		// ルート膜の親膜は null
		RuleStructure rs = new RuleStructure();
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = new RuleStructure();
		r.leftMem.atoms.add( new Atom(m, "v", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		return r;
	}
}


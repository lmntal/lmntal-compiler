/* 
 * 作成日: 2003/10/24
 * 
 */
package compile;

import java.io.StringReader;

import runtime.Env;
import runtime.InterpretedRuleset;

import compile.parser.*;
import compile.structure.*;

/**
 * 実行時データ構造からルールセットを得るテスト
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
		//test("v");
		//test("v,v,w");
		//test("{}");
		//test("{v}");
		//test("a(X), b(X), c(d)");
		//test("a(X,Y), b(X,Y)");
		//test("(a:-b)");
		//test("(a:-b), (c:-d)");
		test("a(X), { b(X) }");
	}
	
	/**
	 * テストする
	 * @param src
	 */
	public static void test(String src) {
		try {
			// thnx to 永田書記長
			LMNParser lp = new LMNParser(new StringReader(src));
			Membrane m = lp.parse();
			
			//Membrane m = getTestStructure1();
			//Membrane m = getTestStructure2();
			Env.p(m);
			Membrane root = RuleSetGenerator.runStartWithNull(m);
			InterpretedRuleset ir = (InterpretedRuleset)root.ruleset;
			
			Env.p("");
			Env.p("Compiled Membrane :");
			root.showAllRuleset();
			root.showAllRule();
			
			//Env.p("");
			//Env.p("Generated InterpretedRuleset :");
			//Env.p(ir);
			//ir.showDetail();
		} catch (ParseException e) {
			Env.p(e);
		}
	}
	
	/**
	 * デバッグ用膜構造をつくる。
	 * 
	 * ( :- v, w, ( v :- w, w ) )
	 * 
	 * @return RuleStructure
	 */
	static Membrane getTestStructure1() {
		// ルート膜の親膜は null
		RuleStructure rs = new RuleStructure();
		rs.parent = new Membrane(null);
		
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = getTestRule();
		
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
	 * デバッグ用膜構造をつくる。2
	 * 
	 * ( v :- w, w )
	 * 
	 * @return RuleStructure
	 */
	static Membrane getTestStructure2() {
		// ルール
		RuleStructure rs = getTestRule();
		rs.parent = new Membrane(null);
		
		rs.parent.rules.add(rs);
		return rs.parent;
	}
	
	/**
	 * デバッグ用ルール構造をつくる。
	 * 
	 * ( v :- w, w )
	 * 
	 * @return RuleStructure
	 */
	static RuleStructure getTestRule() {
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


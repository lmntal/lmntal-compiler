/* 
 * 作成日: 2003/10/24
 * 
 */
package compile;

import java.io.StringReader;
import java.util.List;

import javax.swing.JOptionPane;

import runtime.Env;

import compile.parser.*;
import compile.structure.*;

/**
 * 実行時データ構造からルールセットを得るテスト
 * 
 * じっけん段階
 * @author hara
 *
 */
public class RuleSetCompilerTest {
	
	public static void gui() {
		Env.d( JOptionPane.showInputDialog(null, "Hello, Verno") );
	}
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
		//test("a(X), { b(X) }");
		gui();
	}
	
	/**
	 * テストする
	 * @param src
	 */
	public static List test(String src) {
		try {
			// thnx to 永田書記長
			LMNParser lp = new LMNParser(new StringReader(src));
			Membrane m = lp.parse();
			
			//Membrane m = getTestStructure1();
			//Membrane m = getTestStructure2();
			Env.d(m);
			Membrane root = RulesetCompiler.runStartWithNull(m);
			List ir = root.rulesets;
			
			Env.d("");
			Env.d("Compiled Membrane :");
			root.showAllRuleset();
			root.showAllRule();
			
			return ir;
			
			//Env.d("");
			//Env.d("Generated InterpretedRuleset :");
			//Env.d(ir);
			//ir.showDetail();
		} catch (ParseException e) {
			Env.d(e);
		}
		return null;
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
		RuleStructure rs = new RuleStructure(new Membrane(null));
		
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
		RuleStructure rs = new RuleStructure(null);
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = new RuleStructure(null);
		r.leftMem.atoms.add( new Atom(m, "v", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		return r;
	}
}


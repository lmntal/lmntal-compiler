/*
 * 作成日: 2003/10/24
 *
 */
package compile;

import java.util.*;
import runtime.Env;

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
		Membrane m = getTestStructure();
		Env.p(m);
	}
	
	/**
	 * デバッグ用データ構造をつくる。
	 * 
	 * v, w, ( v :- w, w )
	 * 
	 * @return まく
	 */
	static Membrane getTestStructure() {
		// ルート膜の親膜は null
		Membrane m = new Membrane(null);
		
		// ルール
		RuleStructure r = new RuleStructure();
		r.leftMem.atoms.add( new Atom(m, "v", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		r.rightMem.atoms.add( new Atom(m, "w", 0) );
		
		m.atoms.add( new Atom(m, "v", 0) );
		m.atoms.add( new Atom(m, "w", 0) );
		m.rules.add(r);
		return m;
	}
}

/* 古いコード
	public static void main(String[] args) {
		List ab=genTestAbterms();
		Env.p(ab);
		
		// 最初に、初期状態のブツを生成するようなルールを生成する。
		// ( :- world)
		RuleCompiler rc = new RuleCompiler();
		rc.loadProc(rc.r, ab);
		rc.simplify();
		
		Rule r = rc.compile();
		
		// 生成
		r.react(Env.rootMembrane);
	}

	static List gen(String s) {
		List l=new ArrayList();
		l.add(new Integer(Instruction.NAME));
		l.add(s);
		return l;
	}
	static List genTestAbterms() {
		List ab=new ArrayList();
		
		ab.add(gen("v"));
		ab.add(gen("w"));
		
		List l=gen(":-");
		l.add(gen("v"));
		List ll=gen(",");
		ll.add(gen("w"));
		ll.add(gen("w"));
		l.add(ll);
		
		ab.add(l);
		return ab;
	}
*/

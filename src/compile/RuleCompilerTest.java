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
		RuleCompiler rc = new RuleCompiler(m);
		rc.compile();
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


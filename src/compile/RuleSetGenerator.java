/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.util.*;
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
	public static Membrane runStartWithNull(Membrane m) {
		Env.c("RuleSetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure();
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		rs.parent = root;
		processMembrane(root);
		listupModules(root);
		Env.p("\n=== Modules = \n"+modules+"\n\n");
		return root;
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
	
	public static Map modules = new HashMap();
	public static void listupModules(Membrane m) {
		Env.p("listupModules");
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			runtime.Functor f = new runtime.Functor("name", 1);
			if(a.functor.equals(f)) {
				Env.p("Module found : "+a.args[0].atom);
				modules.put(a.args[0].atom.mem, a.args[0].atom.toString());
			}
		}
		i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			//Env.p("");
			//Env.p("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			listupModules(rs.leftMem);
			//Env.p("About rule structure (LEFT): "+rs.rightMem+" of "+rs);
			listupModules(rs.rightMem);
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			listupModules((Membrane)i.next());
		}
	}
}

/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import runtime.Instruction;
import runtime.InterpretedRuleset;
import compile.structure.*;

/**
 * ルールセットを生成して返す。
 * TODO RuleSetGeneratorはいずれRulesetCompilerに名称変更する
 * @author hara
 * 
 */
public class RuleSetGenerator {
	
	/**
	 * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
	 * より正確に言うと、与えられた膜構造に対応する膜を1回だけ生成するreactメソッドを
	 * 実装するルールセットを持った膜構造を生成する。
	 * メソッド実行中、膜構造内部にあるルール構造がルールセットにコンパイルされる。
	 * @param m 膜構造
	 * @return 生成したルールセットを持つ膜構造
	 */
	public static Membrane runStartWithNull(Membrane m) {
		Env.c("RuleSetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure(root);
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		processMembrane(root);
		listupModules(root);
		fixupLoadRuleset(root);
		Env.d("\n=== Modules = \n"+modules+"\n\n");
		return root;
	}
	
//	public static InterpretedRuleset run(Membrane m) {
//		Env.c("RuleSetGenerator.run");
//		processMembrane(m);
//		return (InterpretedRuleset)m.ruleset;
//	}
	
	/**
	 * 与えられた膜の階層下にある全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem) {
		Env.c("RuleSetGenerator.processMembrane");
		// 子膜にあるルールをルールセットにコンパイルする
		Iterator it = mem.mems.listIterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			processMembrane(submem);
		}
		// この膜にあるルールをルールセットにコンパイルする
		ArrayList rules = new ArrayList();
//		runtime.Ruleset ruleset = mem.ruleset;
		it = mem.rules.listIterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			// ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
			processMembrane(rs.leftMem); // 一応左辺も
			processMembrane(rs.rightMem);
			//
			RuleCompiler rc = new RuleCompiler(rs);
			rc.compile();
			// ↓ いずれ ruleset.add(rc.theRule); にした方がよい
			rules.add(rc.theRule);
//			((runtime.InterpretedRuleset)ruleset).rules.add(rc.theRule);
		}
		if (Env.fRandom) {
			it = rules.iterator();
			while (it.hasNext()) {
				runtime.Ruleset ruleset = new runtime.InterpretedRuleset();
				((runtime.InterpretedRuleset)ruleset).rules.add(it.next());
				//ruleset.compile();
				mem.rulesets.add(ruleset);
			}
		} else {
			if (rules.size() > 0) {
				runtime.Ruleset ruleset = new runtime.InterpretedRuleset();
				it = rules.iterator();
				while (it.hasNext()) {
					((runtime.InterpretedRuleset)ruleset).rules.add(it.next());
				}
				//ruleset.compile();
				mem.rulesets.add(ruleset);
			}
		}
			
		// ruleset.compile();
	}
	
	public static Map modules = new HashMap();
	public static void listupModules(Membrane m) {
		//Env.d("listupModules");
		runtime.Functor f = new runtime.Functor("name", 1);
		
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			if(a.functor.equals(f)) {
				Env.d("Module found : "+a.args[0].atom);
				modules.put(a.args[0].buddy.atom.functor.getName(), a.args[0].atom.mem);
			}
		}
		i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			//Env.d("");
			//Env.d("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			listupModules(rs.leftMem);
			//Env.d("About rule structure (LEFT): "+rs.rightMem+" of "+rs);
			listupModules(rs.rightMem);
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			listupModules((Membrane)i.next());
		}
	}
	public static void fixupLoadRuleset(Membrane m) {
		//Env.d("fixupLoadRuleset");
		
		Iterator i;
		i = ((InterpretedRuleset)m.ruleset).rules.listIterator();
		while(i.hasNext()) {
			runtime.Rule rule = (runtime.Rule)i.next();
			ListIterator ib = rule.body.listIterator();
			while(ib.hasNext()) {
				Instruction inst = (Instruction)ib.next();
				// きたない。
				if(inst.getKind()==Instruction.LOADRULESET && inst.getArg2() instanceof String) {
					//Env.p("module solved : "+modules.get(inst.getArg2()));
					ib.set(new Instruction(Instruction.LOADRULESET, inst.getIntArg1(), 
						((Membrane)modules.get(inst.getArg2())).ruleset ));
				}
			}
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			fixupLoadRuleset((Membrane)i.next());
		}
	}
}

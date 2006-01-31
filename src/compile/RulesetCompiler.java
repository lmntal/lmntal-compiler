/*
 * 作成日: 2003/11/18
 *
 */
package compile;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import runtime.Env;
import runtime.InlineUnit;
import runtime.InterpretedRuleset;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SystemRulesets;

import compile.parser.*;
import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.RuleStructure;

/**
 * ルールセットを生成して返す。
 * @author hara
 * 
 */
public class RulesetCompiler {
	
	private static boolean recursived = false;
	
	/**
	 * 与えられた膜構造を生成するreactメソッドを実装するルールセットを生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * 
	 * @param unitName ファイル名
	 * @param m 膜構造
	 * @return (:-m)というルール1つだけからなるルールセット
	 */
	public static Ruleset compileMembrane(Membrane m, String unitName) {
		return (Ruleset)compileMembraneToGeneratingMembrane(m, unitName).rulesets.get(0);
	}
	
	public static Ruleset compileMembrane(Membrane m) {
		return compileMembrane(m, InlineUnit.DEFAULT_UNITNAME);
	}
	
	/**
	 * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
	 * より正確に言うと、与えられた膜構造の内容に対応するプロセスを1回だけ生成するreactメソッドを
	 * 実装するルールセットを唯一のルールセットとして持つ膜構造を生成する。
	 * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
	 * @param m 膜構造
	 * @param unitName
	 * @return 生成したルールセットを持つ膜構造
	 */
	protected static Membrane compileMembraneToGeneratingMembrane(Membrane m, String unitName) {
		Env.c("RulesetGenerator.runStartWithNull");
		// 世界を生成する
		Membrane root = new Membrane(null);
		RuleStructure rs = new RuleStructure(root, "(initial rule)");
		rs.fSuppressEmptyHeadWarning = true;
		rs.leftMem  = new Membrane(null);
		rs.rightMem = m;
		root.rules.add(rs);
		processMembrane(root, unitName);
		if (Env.fUseSourceLibrary) {
			Module.resolveModules(root);
		}
		return root;
	}
	
	/**
	 * 与えられた膜の階層下にある全ての RuleStructure について、
	 * 対応する Rule を生成してその膜のルールセットに追加する。
	 * <p>ルールをちょうど1つ持つ膜にはちょうど1つのルールセットが追加される。
	 * @param mem 対象となる膜
	 */
	public static void processMembrane(Membrane mem) {
		processMembrane(mem, InlineUnit.DEFAULT_UNITNAME);
	}
	public static void processMembrane(Membrane mem, String unitName) {
		Env.c("RulesetCompiler.processMembrane");
		// 子膜にあるルールをルールセットにコンパイルする
		Iterator it = mem.mems.listIterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			processMembrane(submem, unitName);
		}
		// この膜にあるルール構造をルールオブジェクトにコンパイルする
		ArrayList rules = new ArrayList();
		it = mem.rules.listIterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			// ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
			processMembrane(rs.leftMem, unitName); // 一応左辺も
			processMembrane(rs.rightMem, unitName);
			//
			RuleCompiler rc = null;
			try {
				rc = new RuleCompiler(rs, unitName);
				rc.compile();
				//2006.1.22 Ruleに行番号を渡す by inui
				rc.theRule.lineno = rs.lineno;
//				rc.theRule.showDetail();
			}
			catch (CompileException e) {
				Env.p("    in " + rs.toString() + "\n");
			}
			if(Env.fThread && rc.theRule.getFullText().matches(".*thread.*") && !recursived){
				RuleConverter conv = new RuleConverter();
				Iterator ite = conv.convert(rc.theRule.getFullText());
				while(ite.hasNext()){
					String s = (String)ite.next();
					try {
						LMNParser lp = new LMNParser(new StringReader(s));
						Membrane m = lp.parse();
						recursived = true;
						compileMembrane(m);
						recursived = false;
						rules.add(((InterpretedRuleset)m.rulesets.get(0)).rules.get(0));
					} catch(ParseException e) {
						e.printStackTrace();
					}
				}
			} else {
				rules.add(rc.theRule);
			}
		}
		// 生成したルールオブジェクトのリストをルールセット（のセット）にコンパイルする
		if (!rules.isEmpty()) {
			if (Env.shuffle >= Env.SHUFFLE_RULES) {
				it = rules.iterator();
				while (it.hasNext()) {
					InterpretedRuleset ruleset = new InterpretedRuleset();
					ruleset.rules.add(it.next());
					Ruleset compiledRuleset = compileRuleset(ruleset);
					mem.rulesets.add(ruleset);
				}
			} else {
				InterpretedRuleset ruleset = new InterpretedRuleset();
				it = rules.iterator();
				while (it.hasNext()) {
					ruleset.rules.add(it.next());
				}
				Ruleset compiledRuleset = compileRuleset(ruleset);
				mem.rulesets.add(ruleset);
			}
		}
		// 必要ならシステムルールセットに登録
		boolean is_system_ruleset = false;
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			if (((Atom)it.next()).functor.getName().equals("system_ruleset")) {
				is_system_ruleset = true;
				break;
			}
		}
		if (is_system_ruleset) {
			//Env.p("Use system_ruleset "+mem);
			Iterator ri = mem.rulesets.iterator();
			while(ri.hasNext()) {
				InterpretedRuleset ir = (InterpretedRuleset)ri.next();
				SystemRulesets.addUserDefinedSystemRuleset(ir);
			}
		}
	}
	public static Ruleset compileRuleset(InterpretedRuleset rs) {
		// todo ここでグローバルルールセットIDを生成するとよいはず
		if (!Env.fInterpret) {
			try {
				new Translator(rs).translate();
			} catch (IOException e) {
				Env.e("Failed to write Translated File. " + e.getLocalizedMessage());
			}
		}
		return rs; //返すルールセットはそのまま。どうするのが良いのだろうか？
	}
}

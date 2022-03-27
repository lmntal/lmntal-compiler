/*
 * 作成日: 2003/11/18
 */
package compile;

import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.RuleStructure;
import compile.structure.TypeDefStructure;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import runtime.Env;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.InterpretedRuleset;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SystemRulesets;

/**
 * ルールセットを生成して返す。
 * @author hara
 */
public class RulesetCompiler {

  private RulesetCompiler() {}

  /**
   * 与えられた膜構造を生成するreactメソッドを実装するルールセットを生成する。
   * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
   *
   * @param m 膜構造
   * @return (:-m)というルール1つだけからなるルールセット
   */
  // (2021-08-14 ueda) パーザを変更して初期プロセス生成ルールが最初から
  // 存在するようにしたため，初期プロセス生成ルールの別扱いを中止した．
  // このため compileMembrane は processMembrane と同じになった．
  // HeadCompiler, RuleCompiler の compileMembrane（２引数）と紛らわしいので，
  // こちらは processMembrane を直接呼び出すことにしたい．
  //
  // // public static Ruleset compileMembrane(Membrane m)
  // public static void compileMembrane(Membrane m)
  // {
  //      // return compileMembraneToGeneratingMembrane(m).rulesets.get(0)
  // 	processMembrane(m);
  // }

  /**
   * 与えられた膜の階層下にある全ての RuleStructure について、
   * 対応する Rule を生成してその膜のルールセットに追加する。
   * <p>ルールをちょうど1つ持つ膜にはちょうど1つのルールセットが追加される。
   * @param mem 対象となる膜
   */
  public static void processMembrane(Membrane mem) {
    // 子膜にあるルールをルールセットにコンパイルする
    for (Membrane submem : mem.mems) {
      processMembrane(submem);
    }

    List<Rule> rules = new ArrayList<>();

    // この膜にあるルール構造をルールオブジェクトにコンパイルする
    for (RuleStructure rs : mem.rules) {
      // ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
      processMembrane(rs.leftMem); // 一応左辺も
      processMembrane(rs.rightMem);

      RuleCompiler rc = null;
      try {
        rc = new RuleCompiler(rs);
        rc.compile(false);
        //2006.1.22 Ruleに行番号を渡す by inui
        rc.theRule.lineno = rs.lineno;
      } catch (CompileException e) {
        Env.p("    in " + rs.toString() + "\n");
      }
      rules.add(rc.theRule);
    }

    // typedef におけるサブルールをコンパイルする
    for (TypeDefStructure typeDefStructure : mem.typeDefs) {
      if (!mem.typeDefs.isEmpty()) {
        Rule controlRule = new Rule();
        Atom typeAtom = typeDefStructure.mem.rules.get(0).leftMem.atoms.get(0);
        String typeName = typeAtom.getName();
        int arity = typeAtom.getArity();
        controlRule.typeDefName = typeName;
        controlRule.isTypeDef = true;
        controlRule.memMatch.add(Instruction.spec(arity + 1, arity + 2));
        ArrayList<Integer> arglist = new ArrayList<>();
        for (int i = 0; i <= arity; i++) {
          arglist.add(i);
        }
        for (int i = 0; i < typeDefStructure.mem.rules.size(); i++) {
          InstructionList subBranch = new InstructionList();
          subBranch.insts.add(
            Instruction.subrule(arity + 1, 0, typeName + "_" + i, arglist)
          );
          subBranch.insts.add(
            new Instruction(Instruction.SUCCRETURN, arity + 1)
          );
          controlRule.memMatch.add(
            new Instruction(Instruction.BRANCH, subBranch)
          );
        }
        controlRule.memMatch.add(new Instruction(Instruction.FAILRETURN));
        rules.add(controlRule);
      }

      int i = 0;
      for (RuleStructure rs : typeDefStructure.mem.rules) {
        if (true) {
          // ルールの右辺膜以下にある子ルールをルールセットにコンパイルする
          processMembrane(rs.leftMem); // 一応左辺も
          processMembrane(rs.rightMem);

          RuleCompiler rc = null;
          try {
            rc = new RuleCompiler(rs);
            rc.compile(true);

            //2006.1.22 Ruleに行番号を渡す by inui
            rc.theRule.lineno = rs.lineno;
          } catch (CompileException e) {
            Env.p("    in " + rs.toString() + "\n");
          }
          rc.theRule.typeDefName =
            typeDefStructure.typeAtom.atoms.get(0).getName() + "_" + i++;
          rules.add(rc.theRule);
        }
      }
    }

    // 生成したルールオブジェクトのリストをルールセット（のセット）にコンパイルする
    if (!rules.isEmpty()) {
      InterpretedRuleset ruleset = new InterpretedRuleset();
      for (Rule r : rules) {
        ruleset.rules.add(r);
      }
      ruleset.branchmap = null;
      ruleset.systemrulemap = null;
      mem.rulesets.add(ruleset);
    }
    // 必要ならシステムルールセットに登録
    boolean isSystemRuleset = false;
    for (Atom atom : mem.atoms) {
      if (atom.functor.getName().equals("system_ruleset")) {
        isSystemRuleset = true;
        break;
      }
    }
    if (isSystemRuleset) {
      for (Ruleset r : mem.rulesets) {
        InterpretedRuleset ir = (InterpretedRuleset) r;
        SystemRulesets.addUserDefinedSystemRuleset(ir);
        ir.isSystemRuleset = true;
      }
    }
  }

  public static Ruleset compileRuleset(InterpretedRuleset rs) {
    return rs; //返すルールセットはそのまま。どうするのが良いのだろうか？
  }
  /**
   * 与えられた膜構造を生成するルール1つだけを要素に持つ膜を生成する。
   * より正確に言うと、与えられた膜構造の内容に対応するプロセスを1回だけ生成するreactメソッドを
   * 実装するルールセットを唯一のルールセットとして持つ膜構造を生成する。
   * メソッド実行中、膜構造内部にあるルール構造が再帰的にルールセットにコンパイルされる。
   * @param m 膜構造
   * @return 生成したルールセットを持つ膜構造
   */
  // private static Membrane compileMembraneToGeneratingMembrane(Membrane m)
  // {
  // 	Env.c("RulesetGenerator.runStartWithNull");

  // 	// グローバルルート膜
  // 	Membrane root = new Membrane(null);

  // 	// 初期構造を生成するルール
  // 	RuleStructure rs = RuleStructure.createInitialRule(root);
  // 	rs.leftMem  = new Membrane(null);
  // 	rs.rightMem = m;
  // 	root.rules.add(rs);

  // 	processMembrane(root);
  // 	return m;
  // }
}

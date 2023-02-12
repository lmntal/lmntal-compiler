package runtime;

import java.util.ArrayList;
import java.util.List;
import runtime.functor.Functor;

/** ルールの集合。 現在はルールの配列として表現しているが、将来的には複数のルールのマッチングを １つのマッチングテストで行うようにする。 */
public abstract class Ruleset {

  /** new束縛された名前の具体値を格納する配列 */
  protected Functor[] holes;

  public List<Rule> compiledRules = new ArrayList<>();
  public boolean isRulesSetted = false;
  public boolean isSystemRuleset = false;

  public abstract String toString();

  public abstract String encode();

  public String[] encodeRulesIndividually() {
    return null;
  }

  /**
   * new束縛された名前の具体値を指定して新しいRulesetを作成する。
   *
   * @return 新しいRuleset
   */
  public Ruleset fillHoles(Functor[] holes) {
    return null;
  }
}

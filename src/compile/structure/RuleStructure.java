package compile.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ソースコード中のルールの構造を表すクラス
 */
public final class RuleStructure {

  /**
   * 所属膜。コンパイル時につかう
   */
  @JsonIgnore
  public Membrane parent;

  /**
   * ルール名
   */
  public String name;

  /**
   * テキスト表現
   */
  private String text;

  /**
   * 左辺が空のときの警告を抑制するかどうか
   * @deprecated
   */
  @Deprecated
  public boolean fSuppressEmptyHeadWarning = false;

  /**
   * Headを格納する膜
   */
  public Membrane leftMem = new Membrane(null);

  /**
   * Bodyを格納する膜
   */
  public Membrane rightMem = new Membrane(null);

  /**
   * ガード型制約を格納する膜
   */
  public Membrane guardMem = new Membrane(null);

  //	/** ガードの型制約 (TypeConstraint) のリスト */
  //	public LinkedList typeConstraints = new LinkedList();

  /**
   * ガード否定条件（ProcessContextEquationのLinkedList）のリスト
   */
  public List<List<ProcessContextEquation>> guardNegatives = new LinkedList<>();

  /**
   * プロセス文脈の限定名 ("$p"などのString) {@literal -->} 文脈の定義 (ContextDef)
   */
  public Map<String, ContextDef> processContexts = new HashMap<>();

  /**
   * ルール文脈の限定名 ("@p"などのString) {@literal -->} 文脈の定義 (ContextDef)
   */
  public Map<String, ContextDef> ruleContexts = new HashMap<>();

  /**
   * 型付きプロセス文脈の限定名 ("$p"などのString) {@literal -->} 文脈の定義 (ContextDef)
   */
  public Map<String, ContextDef> typedProcessContexts = new HashMap<>();

  /**
   * 行番号 2006.1.22 by inui
   */
  public int lineno;

  /**
   * このルールが初期構造生成ルールであることを表す。
   */
  private boolean initialRule;

  /**
   * コンストラクタ
   * @param mem 所属膜
   */
  public RuleStructure(Membrane mem, String text) {
    this.parent = mem;
    // io:{(print:-inline)} の print は io.print にしたい。
    // が、print は io 膜直属ではなくルール左辺の膜に所属するのでルールの膜も同じ名前をつけておく。
    leftMem.name = mem.name;
    rightMem.name = mem.name;
    this.text = text;
  }

  //2006.1.22 by inui
  /**
   * コンストラクタ
   * @param mem 所属膜
   * @param lineno 行番号
   */
  public RuleStructure(Membrane mem, String text, int lineno) {
    this(mem, text);
    this.lineno = lineno;
  }

  /**
   * このルールが初期構造生成ルールである場合に {@code true} を返す。
   * @return 初期構造生成ルールである場合に {@code true}
   */
  public boolean isInitialRule() {
    return initialRule;
  }

  public String toString() {
    return text;
  }

  /**
   * 初期構造生成ルールを作成する。
   * @param parentMem 所属膜
   * @return 初期構造生成ルール
   */
  public static RuleStructure createInitialRule(Membrane parentMem) {
    RuleStructure rs = new RuleStructure(parentMem, "(initial rule)");
    rs.initialRule = true;
    return rs;
  }
}

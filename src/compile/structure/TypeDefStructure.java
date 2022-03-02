package compile.structure;

import java.util.LinkedList;

/**
 * ソースコード中のルールの構造を表すクラス
 */
public final class TypeDefStructure {

  /**
   * 所属膜。コンパイル時につかう
   */
  public Membrane parent;

  /**
   * 型名
   */
  public Membrane typeAtom;

  /**
   * TypeRule 列
   */
  public Membrane mem;

  // /**
  //  * テキスト表現
  //  */
  // public String text;

  /**
   * 行番号
   */
  public int lineno;

  /**
   * コンストラクタ
   * @param mem 所属膜
   * @param lineno 行番号
   */
  public TypeDefStructure(Membrane parent, int lineno) {
    this.parent = parent;
    this.lineno = lineno;
  }
}

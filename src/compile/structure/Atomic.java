package compile.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ソースコード中のリンク列を持つ構造を表す抽象クラス。
 * アトム、アトム集団、コンテキストなどの親クラス。
 * @author Takahiko Nagata, n-kato
 * @date 2003/10/28
 */
public abstract class Atomic {

  /**
   * 所属膜
   */
  @JsonIgnore public Membrane mem = null;

  /**
   * アトムのリンク列（またはアトム集団のリンク束列）
   */
  public LinkOccurrence[] args;

  /**
   * デバッグ情報:ソースコード中での出現位置(行)
   * 情報が無いときは-1を代入
   * author Tomohito Makino
   */
  public int line = -1;

  /**
   * デバッグ情報:ソースコード中での出現位置(桁)
   * 情報が無いときは-1を代入
   * author Tomohito Makino
   */
  public int column = -1;

  /**
   * コンストラクタ
   * @param mem このアトムの所属膜
   * @param arity リンク列の長さ
   */
  public Atomic(Membrane mem, int arity) {
    this.mem = mem;
    args = new LinkOccurrence[arity];
  }

  public void setSourceLocation(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public abstract String toString();

  /**
   * 明示的な自由リンク引数の個数を取得する。
   */
  public int getArity() {
    return args.length;
  }

  /**
   * ファンクタの名前を取得する。ファンクタが無い場合は空文字列を返す。
   */
  public String getName() {
    return "";
  }
}

package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * ソースファイル中のリンク・リンク束・プロセスコンテキスト・ルールコンテキストの抽象親クラス
 * <p>プロセス文脈名およびルール文脈名には '...' や [[...]] が使えないようにした。
 */
@JsonAutoDetect(
  fieldVisibility = Visibility.ANY,
  getterVisibility = Visibility.ANY
)
abstract class SrcContext {

  protected String name = null;
  protected int lineno = -1;

  public SrcContext() {}

  /**
   * 指定された名前でコンテキストを初期化します
   * @param name コンテキスト名
   */
  protected SrcContext(String name) {
    this(name, -1);
  }

  protected SrcContext(String name, int lineno) {
    this.name = name;
    this.lineno = lineno;
  }

  /**
   * コンテキストの名前を返す。
   * @return コンテキストの名前
   */
  public String getName() {
    return name;
  }

  /** コンテキストの限定名（種類と名前の組に対する一意な識別子として使用できる文字列）を返す。*/
  public abstract String getQualifiedName();

  public String toString() {
    return getQualifiedName();
  }
}

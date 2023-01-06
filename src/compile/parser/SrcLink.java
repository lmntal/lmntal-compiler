package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * ソースファイル中のリンク表現
 */
@JsonAutoDetect(
  fieldVisibility = Visibility.ANY,
  getterVisibility = Visibility.NONE
)
class SrcLink extends SrcContext {

  public SrcLink() {}

  /**
   * 指定された名前のリンクを作成します
   * @param name リンク名
   */
  public SrcLink(String name) {
    this(name, -1);
  }

  /**
   * 指定された名前と行番号のリンクを作成します
   * @param name リンク名
   * @param lineno 行番号
   */
  public SrcLink(String name, int lineno) {
    super(name, lineno);
  }

  public String getQualifiedName() {
    return " " + name;
  }
}

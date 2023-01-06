package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * ソースファイル中のルールコンテキストの表現
 */
@JsonAutoDetect(
  fieldVisibility = Visibility.ANY,
  getterVisibility = Visibility.NONE
)
class SrcRuleContext extends SrcContext {

  public SrcRuleContext() {}

  /**
   * 指定された名前を持つルールコンテキストを作成します
   * @param name ルールコンテキスト名
   */
  public SrcRuleContext(String name) {
    super(name);
  }

  public String getQualifiedName() {
    return "@" + name;
  }
}

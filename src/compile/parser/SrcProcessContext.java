package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.LinkedList;

/**
 * ソースファイル中のプロセスコンテキストの表現
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
class SrcProcessContext extends SrcContext {

  /**
   * 明示的な引数列
   * <p>利用側でLinkedListを生成して代入すること
   */
  @JsonTypeInfo(use = Id.CLASS)
  public LinkedList<SrcElement> args = null;

  /** リンク束 */
  public SrcLinkBundle bundle = null;

  /** 分離した同名型付きプロセス文脈の名前を格納 */
  public LinkedList<String> sameNameList = null;

  /** リンク名 */
  public String linkName = null;

  public SrcProcessContext() {}

  /**
   * 指定された名前をもつ引数無しのプロセスコンテキストを作成する
   * @param name コンテキスト名
   */
  public SrcProcessContext(String name) {
    super(name);
    //		// プロセス文脈名をエスケープする。
    //		// ＜いずれ文脈名にquote記法の使用を禁止すれば不要になる。＞→禁止したので廃止した
    //		// $X $_7 や *p などを内部予約としているためにquoteが必要となっている。
    //		if (name.matches("^[A-Z_].*")) { this.name = "_" + name; }
  }

  /**
   * 内部予約名を持つプロセスコンテキストを作成する。
   * @param name コンテキスト名（_で始まる内部予約名を渡すことができる）
   * @param dummy trueを渡すこと
   * <p>文脈名でのquote記法の使用を禁止したので、このメソッドは廃止してよい。
   */
  public SrcProcessContext(String name, boolean dummy) {
    super(name);
  }

  public String getQualifiedName() {
    return "$" + name;
  }

  public LinkedList<String> getSameNameList() {
    return sameNameList;
  }

  public boolean hasSameNameList() {
    return sameNameList != null;
  }

  public String getLinkName() {
    return linkName;
  }
}

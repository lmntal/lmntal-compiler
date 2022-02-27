package compile.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * ソース中の型定義を表します
 */
class SrcTypeDef {

  public SrcName srcname; // 型名
  public LinkedList link; // 引数のリンクリスト
  public LinkedList rule; // 型の内容
  public int lineno; // 行番号

  private String text; // typedef 構文のテキスト表現

  public SrcTypeDef(
    SrcName srcname,
    LinkedList linklist,
    LinkedList processlist,
    int lineno
  ) {
    this.srcname = srcname;
    this.link = linklist;
    this.rule = processlist;
    this.lineno = lineno;
  }

  public void setName(SrcName srcname) {
    this.srcname = srcname;
  }

  public void setName(String name) {
    this.srcname = new SrcName(name);
  }

  public void setLink(LinkedList link) {
    this.link = link;
  }

  public void setRule(LinkedList rule) {
    this.rule = rule;
  }

  public SrcName getSrcName() {
    return srcname;
  }

  public String getSourceName() {
    return srcname.getSourceName();
  }

  public String getName() {
    return srcname.getName();
  }

  public LinkedList getLink() {
    return link;
  }

  public LinkedList getRule() {
    return rule;
  }

  /**
   * LMNtal ソース形式のテキスト表現を取得する。
   */
  public String getText() {
    return text;
  }

  void setText() {
    text = SrcDumper.dump(this);
  }
}

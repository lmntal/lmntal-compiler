package compile.parser;

import java.util.LinkedList;

/**
 * ソース中の型定義を表します
 */
class SrcTypeDef extends SrcElement {

  // public SrcName srcname; // 型名
  // public LinkedList links; // 引数のリンクリスト
  public SrcAtom typeName; // 型名と引数のリンクリスト
  public LinkedList<SrcElement> rules; // 型の内容
  public int lineno; // 行番号

  private String text; // typedef 構文のテキスト表現

  public SrcTypeDef(
      SrcName name, LinkedList<SrcElement> links, LinkedList<SrcElement> processlist, int lineno) {
    this.typeName = new SrcAtom(name, links);
    this.rules = processlist;
    this.lineno = lineno;
  }

  public void setTypeAtom(SrcAtom typeName) {
    this.typeName = typeName;
  }

  public void setRules(LinkedList<SrcElement> rule) {
    this.rules = rule;
  }

  public SrcAtom getTypeAtom() {
    return typeName;
  }

  public LinkedList<SrcElement> getRules() {
    return rules;
  }

  public int getLineNo() {
    return lineno;
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

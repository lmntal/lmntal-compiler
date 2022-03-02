package compile.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * ソース中の型定義を表します
 */
class SrcTypeDef {

  // public SrcName srcname; // 型名
  // public LinkedList links; // 引数のリンクリスト
  public SrcAtom typeName; // 型名と引数のリンクリスト
  public LinkedList<Object> rules; // 型の内容
  public int lineno; // 行番号

  private String text; // typedef 構文のテキスト表現

  public SrcTypeDef(SrcAtom typeName, LinkedList processlist, int lineno) {
    this.typeName = typeName;
    this.rules = processlist;
    this.lineno = lineno;
  }

  public void setTypeAtom(SrcAtom typeName) {
    this.typeName = typeName;
  }

  public void setRules(LinkedList rule) {
    this.rules = rule;
  }

  public SrcAtom getTypeAtom() {
    return typeName;
  }

  public LinkedList getRules() {
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

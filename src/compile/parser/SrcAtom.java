package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.LinkedList;

/** ソースファイル中のアトム表現 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
class SrcAtom extends SrcElement {

  @JsonTypeInfo(use = Id.CLASS)
  protected LinkedList<SrcElement> process = null;

  /** 名前トークン */
  protected SrcName srcname;

  /**
   * ソースコード中での出現位置(行)
   * @author Tomohito Makino
   */
  int line = -1;

  /**
   * ソースコード中での出現位置(桁)
   * @author Tomohito Makino
   */
  int column = -1;

  public SrcAtom() {}

  /**
   * 指定された名前の子プロセスなしのアトム構文を生成する
   * @param name アトム名
   */
  public SrcAtom(String name) {
    this(new SrcName(name));
  }

  /**
   * 指定された名前トークンを持つ子プロセスなしのアトム構文を生成する
   * @param srcname 名前トークン
   */
  public SrcAtom(SrcName srcname) {
    this(srcname, new LinkedList<>(), -1, -1);
  }

  /**
   * 指定された名前と子供プロセスで初期化します
   * @param name アトム名
   * @param process 子供プロセス
   */
  public SrcAtom(String name, LinkedList<SrcElement> process) {
    this(new SrcName(name), process, -1, -1);
  }

  /**
   * 指定された名前トークンと子供プロセスで初期化します
   * @param srcname 名前トークン
   * @param process 子供プロセス
   */
  public SrcAtom(SrcName srcname, LinkedList<SrcElement> process) {
    this(srcname, process, -1, -1);
  }

  /**
   * デバッグ情報も受け取るコンストラクタ(子供プロセス無し)
   * @author Tomohito Makino
   * @param srcname 名前トークン
   * @param line ソースコード上での出現位置(行)
   * @param column ソースコード上での出現位置(桁)
   */
  public SrcAtom(SrcName srcname, int line, int column) {
    this(srcname, new LinkedList<>(), line, column);
  }

  /**
   * デバッグ情報も受け取るコンストラクタ
   * @author Tomohito Makino
   * @param nametoken 名前トークン
   * @param process 子供プロセス
   * @param line ソースコード上での出現位置(行)
   * @param column ソースコード上での出現位置(桁)
   */
  public SrcAtom(SrcName nametoken, LinkedList<SrcElement> process, int line, int column) {
    this.srcname = nametoken;
    this.process = process;
    this.line = line;
    this.column = column;
  }

  public void setSourceLocation(int line, int column) {
    this.line = line;
    this.column = column;
  }

  /** 名前トークンを取得する
   * @deprecated
   */
  public SrcName getSrcName() {
    return srcname;
  }

  /** アトム名を取得する */
  public String getName() {
    return srcname.getName();
  }

  /** アトム名のソースコード中の表現を取得する。*/
  public String getSourceName() {
    return srcname.getSourceName();
  }

  /** アトム名トークンの種類を取得する */
  public int getNameType() {
    return srcname.getType();
  }

  /**
   * このアトムの子プロセスを得ます
   * @return 子プロセスのリスト
   */
  public LinkedList<SrcElement> getProcess() {
    return process;
  }

  public SrcAtom clone() {
    return new SrcAtom(srcname.clone(), new LinkedList<SrcElement>(process), line, column);
  }

  public String toString() {
    return SrcDumper.dump(this);
  }
}

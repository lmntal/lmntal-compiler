package compile.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.LinkedList;

/**
 * ソースファイル中の膜表現
 */
class SrcMembrane extends SrcElement {

  /** 膜の内容プロセスの表現 */
  @JsonTypeInfo(use = Id.CLASS)
  LinkedList<SrcElement> process = null;

  /** 終了フラグの有無 */
  public boolean stable = false;

  /** 膜のタイプ */
  public int kind = 0;

  /** ＠指定またはnull */
  SrcElement pragma = null;

  /** 膜名 */
  public String name;

  /**
   * 空の膜を作成します
   */
  public SrcMembrane() {
    this(new LinkedList<>());
  }

  /**
   * 指定された子プロセスを持つ膜を作成します
   * @param process 膜に含まれる子プロセス
   */
  public SrcMembrane(LinkedList<SrcElement> process) {
    this.process = process;
  }

  /**
   * 子プロセスを取得します
   * @return 子プロセスのリスト
   */
  public LinkedList<SrcElement> getProcess() {
    return process;
  }

  public String toString() {
    return SrcDumper.dumpMembrane(this);
  }
}

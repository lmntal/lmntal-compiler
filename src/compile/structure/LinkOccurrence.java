package compile.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * ソースコード中のリンクまたはリンク束の各出現を表す。<br>
 * {@link runtime.Link} と違って、{@code LinkOccurrence.atom} はこちら側のアトムオブジェクトが入っている。
 */
public final class LinkOccurrence {

  /**
   * このリンクの名前
   */
  public String name;

  /**
   * このリンクが所属するアトムオブジェクト
   */
  @JsonIgnore
  public Atomic atom;

  /**
   * このリンクが所属するアトムにおけるこのリンクの引数番号
   */
  public int pos;

  /**
   * 2回しか出現しない場合に、もう片方の出現を保持する
   */
  @JsonIgnore
  public LinkOccurrence buddy = null;

  /**
   * リンク出現を生成する。
   * @param name リンク名
   * @param atom 所属するアトム
   * @param pos 所属するアトムでの場所
   */
  public LinkOccurrence(String name, Atomic atom, int pos) {
    this.name = name;
    this.atom = atom;
    this.pos = pos;
  }

  public String toString() {
    return name.replace('~', '_');
  }

  public String getInformativeText() {
    return (
      toString() +
      "(" +
      atom.getName() +
      "/" +
      atom.getArity() +
      "," +
      pos +
      ")"
    );
  }

  public boolean equals(Object o) {
    return (
      o == this || o instanceof LinkOccurrence && equals((LinkOccurrence) o)
    );
  }

  public boolean equals(LinkOccurrence l) {
    return atom == l.atom && pos == l.pos;
  }

  public int hashCode() {
    return atom.hashCode() ^ (17 * pos);
  }
}

package type.argument;

import java.util.Objects;
import runtime.functor.Functor;

public class ActiveAtomPath implements Path {

  /**
   * アクティブアトムの所属膜
   */
  private String memname;

  public String getMemName() {
    return memname;
  }

  /**
   * アクティブアトムのファンクタ
   */
  private Functor functor;

  public Functor getFunctor() {
    return functor;
  }

  /**
   * このパスの引数位置
   */
  private int pos;

  public int getPos() {
    return pos;
  }

  /**
   * @param memname null文字列が渡されることはない
   * @param functor
   * @param pos
   */
  public ActiveAtomPath(String memname, Functor functor, int pos) {
    this.memname = memname;
    this.functor = functor;
    this.pos = pos;
  }

  public String toString() {
    return "<" + memname + ":" + functor + "," + pos + ">";
  }

  public String toStringWithOutAnonMem() {
    return ("<"
        + (Objects.equals(memname, "??") ? "" : (memname + ":"))
        + functor
        + ","
        + pos
        + ">");
  }

  public int hashCode() {
    return memname.hashCode() + functor.hashCode() + pos;
  }

  public boolean equals(Object o) {
    if (o instanceof ActiveAtomPath) {
      ActiveAtomPath aap = (ActiveAtomPath) o;
      return (memname.equals(aap.memname) && functor.equals(aap.functor) && pos == aap.pos);
    } else return false;
  }
}

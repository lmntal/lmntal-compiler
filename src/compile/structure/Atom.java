package compile.structure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import runtime.functor.Functor;
import runtime.functor.SymbolFunctor;

/**
 * ソースコード中のアトム（またはアトム集団または型制約）の構造を表すクラス。
 * @author Takahiko Nagata
 * @date 2003/10/28
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
public class Atom extends Atomic {

  /**
   * アトムのファンクタ
   */
  public Functor functor;

  /**
   * 型制約の場合：名前トークンの種類(定数単項アトム(unary)型の制約かどうかの判定に使う)
   */
  public boolean isSelfEvaluated = false;

  /**
   * コンストラクタ
   * @param mem このアトムの所属膜
   * @param functor ファンクタ
   */
  public Atom(Membrane mem, Functor functor) {
    super(mem, functor.getArity());
    this.functor = functor;
  }

  /**
   * コンストラクタ
   * @param mem このアトムの所属膜
   * @param name アトム名を表す文字列
   * @param arity リンクの数
   */
  public Atom(Membrane mem, String name, int arity) {
    this(mem, new SymbolFunctor(name, arity));
  }

  public String toString() {
    if (args.length == 0) return functor.getQuotedAtomName();

    String argstext = "";
    for (int i = 0; i < args.length; i++) {
      argstext += "," + args[i];
    }
    return functor.getQuotedFunctorName() + "(" + argstext.substring(1) + ")";
  }

  public String toStringAsTypeConstraint() {
    if (args.length == 0) return functor.getQuotedAtomName();

    String argstext = "";
    for (int i = 0; i < args.length; i++) {
      argstext += "," + ((ProcessContext) args[i].buddy.atom).getQualifiedName();
    }
    return functor.getQuotedFunctorName() + "(" + argstext.substring(1) + ")";
  }

  public String getPath() {
    return functor.getPath();
  }

  public String getName() {
    return functor.getName();
  }

  public int hashCode() {
    return functor.hashCode();
  }
}

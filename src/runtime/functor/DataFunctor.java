package runtime.functor;

/**
 * データアトム用の1引数ファンクタを表すクラス
 * @author inui
 */
public abstract class DataFunctor extends Functor {

  public boolean isOutsideProxy() {
    return false;
  }

  public boolean isInsideProxy() {
    return false;
  }

  public boolean isSymbol() {
    return false;
  }

  public boolean isActive() {
    return false;
  }

  public int getArity() {
    return 1;
  }
}

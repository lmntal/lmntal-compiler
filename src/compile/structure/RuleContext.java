package compile.structure;

/**
 * ソースコード中のルール文脈出現を表すクラス
 */
public final class RuleContext extends Context {

  /**
   * @param mem 親膜
   * @param qualifiedName 限定名
   */
  public RuleContext(Membrane mem, String qualifiedName) {
    super(mem, qualifiedName, 0);
  }

  public String toString() {
    return getQualifiedName();
  }
}

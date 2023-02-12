/**
 * ルールコンパイルでのエラー
 */
package compile;

public class CompileException extends Exception {

  public CompileException(String s) {
    super(s);
  }
}

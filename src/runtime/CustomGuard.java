package runtime;


/**
 * ガードインラインのコードは、このインターフェースを実装するクラスに書く必要がある。
 *
 * カスタムガード制約の名前は
 *
 * "custom" + "_" + <入出力文字列> + "_" + <識別名>
 *
 * と書く必要がある。
 *
 * <入出力文字列>
 * カスタムガード制約の引数のモードをそれぞれ "i" or "o" で書く。
 * "i"(入力)は未束縛の引数を許さないが、"o"(出力)は許す。
 * 引数の数と同じ長さでなければいけない。
 *
 * <識別名>
 * インラインコードにてガード名を識別するための文字。ガード名に書ける文字列ならなんでも良い。
 *
 *
 * 例：
 *
 * mod(A, B, C) は、A, B が入力で C (=A mod B) が出力。mod(+A, +B, -C) と書く。
 *
 * この場合 LMNtal ルールには "custom_iio_mod(A, B, C)" と書けばいい。
 *
 * LMNtal ルールにて custom_io_guardName(In, Out) というガードがあったときguardName が渡される。
 *
 * @author hara
 */
public interface CustomGuard {
  /**
   * カスタムガード制約が実行されるときに呼び出される。
   * @param guardID 識別名
   * @param mem 現在の膜
   * @param obj カスタムガード制約の引数（List of Atom）
   * @return
   */
  boolean run(String guardID, Membrane mem, Object obj);
}

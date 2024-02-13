package type.quantity;

/**
 * 評価済みの値を表すクラス。区間値もしくは実数値、ないし無限値が入る
 * @author kudo
 *
 */
public abstract class ValueCount {

  public abstract ValueCount add(ValueCount v);

  public abstract ValueCount mul(int m);

  public abstract long compare(ValueCount vc);

  public boolean equals(ValueCount v) {
    return compare(v) == 0;
  }
}

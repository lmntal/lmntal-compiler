package type.quantity;

public class FixedDynamicCounts {

  public final int multiple;

  /** 削除するプロセス */
  public final FixedCounts removeCounts;
  /** 生成するプロセス */
  public final FixedCounts generateCounts;

  public FixedDynamicCounts(DynamicCounts dom) {
    removeCounts = dom.removeCounts.solve();
    generateCounts = dom.generateCounts.solve();
    multiple = dom.multiple;
  }
}

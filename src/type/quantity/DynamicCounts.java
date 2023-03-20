package type.quantity;

import compile.structure.Membrane;
import runtime.Env;

/**
 * それぞれの膜について作られる。
 * 動的な量情報を表すクラス
 * @author kudo
 *
 */
public class DynamicCounts {

  public final Membrane mem;

  /** 適用回数を表す変数 */
  public final VarCount applyCount;

  public void assignToVar(IntervalCount ic) {
    applyCount.bind(ic);
  }

  /**
   * この膜の所属プロセスが何倍されるかを表す。
   * 1 : 移動時(あるいはルールの本膜等)
   * &gt;1 : 複製、マージ
   */
  public final int multiple;

  /** 削除するプロセス */
  public final StaticCounts removeCounts;
  /** 生成するプロセス */
  public final StaticCounts generateCounts;

  public DynamicCounts(
      StaticCounts removeCounts, int multiple, StaticCounts generateCounts, VarCount applyCount) {
    this.mem = generateCounts.mem;
    this.multiple = multiple;
    this.applyCount = applyCount;
    this.removeCounts = removeCounts;
    this.generateCounts = generateCounts;
  }

  //	public void addAllCounts(DynamicCounts dom){
  //		removeCounts.addAllCounts(dom.removeCounts);
  //		generateCounts.addAllCounts(dom.generateCounts);
  //	}

  /**
   * 具体値にする
   * @return
   */
  public FixedDynamicCounts solve() {
    return new FixedDynamicCounts(this);
  }

  /**
   * 変数名をつけかえた自身のクローンを返す
   */
  public DynamicCounts clone() {
    VarCount newvar = new VarCount();
    StaticCounts newR = removeCounts.clone(applyCount, newvar);
    StaticCounts newG = generateCounts.clone(applyCount, newvar);
    return new DynamicCounts(newR, multiple, newG, newvar);
  }

  public void print() {
    Env.p("---dynamic count in " + mem.name + " :");
    Env.p("----remove:");
    removeCounts.print();
    Env.p("----generate:");
    generateCounts.print();
    //		Env.p("----atoms of " + TypeEnv.getMemName(mem) + ":");
    //		Iterator<Functor> itf = functorToCount.keySet().iterator();
    //		while(itf.hasNext()){
    //			Functor f = itf.next();
    //			Env.p(f + ":" + functorToCount.get(f));
    //		}
    //		Env.p("----mems of " + TypeEnv.getMemName(mem) + ":");
    //		Iterator<String> itm = memnameToCount.keySet().iterator();
    //		while(itm.hasNext()){
    //			String m = itm.next();
    //			Env.p(m + ":" + memnameToCount.get(m));
    //		}
  }
}

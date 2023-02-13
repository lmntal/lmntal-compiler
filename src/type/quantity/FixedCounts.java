package type.quantity;

import compile.structure.Membrane;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import runtime.Env;
import runtime.functor.Functor;
import type.TypeEnv;

/**
 * マージする為のオブジェクト
 * @author kudo
 *
 */
public class FixedCounts {

  /** ファンクタ {@literal -->} 量 */
  public final Map<Functor, IntervalCount> functorToCount;
  /** 膜名 {@literal -->} 量 */
  public final Map<String, IntervalCount> memnameToCount;

  final Membrane mem;

  /**
   * 受け取った解析結果を解く(この段階でルール適用回数変数が束縛されていること)
   *
   */
  public FixedCounts(StaticCounts com) {
    this.mem = com.mem;
    functorToCount = new HashMap<>();
    memnameToCount = new HashMap<>();
    for (Functor f : com.functorToCount.keySet()) {
      Count c = com.functorToCount.get(f);
      functorToCount.put(f, c.evaluate());
    }
    for (String name : com.memnameToCount.keySet()) {
      Count c = com.memnameToCount.get(name);
      memnameToCount.put(name, c.evaluate());
    }
  }

  /**
   * マージする.
   * マージとは[0, 3] , [2, 5] {@literal -->} [0, 5]みたいなこと
   * TODO 倍数により分ける
   * @param fcs
   */
  public void merge(FixedCounts fcs) {
    Set<Functor> mergedFunctors = new HashSet<>();
    mergedFunctors.addAll(functorToCount.keySet());
    mergedFunctors.addAll(fcs.functorToCount.keySet());
    for (Functor f : mergedFunctors) {
      IntervalCount fc1 = functorToCount.get(f);
      IntervalCount fc2 = fcs.functorToCount.get(f);
      if (fc1 == null && fc2 != null) functorToCount.put(f, fc2.or0());
      else if ( // 片方にしかない場合
      fc1 != null && fc2 == null) functorToCount.put(f, fc1.or0());
      else if ( // 片方にしかない場合
      fc1 != null && fc2 != null) functorToCount.put(f, fc1.or(fc2));
    }
    Set<String> mergedNames = new HashSet<>();
    mergedNames.addAll(memnameToCount.keySet());
    mergedNames.addAll(fcs.memnameToCount.keySet());
    for (String name : mergedNames) {
      IntervalCount fc1 = memnameToCount.get(name);
      IntervalCount fc2 = fcs.memnameToCount.get(name);
      if (fc1 == null && fc2 != null) memnameToCount.put(name, fc2.or0());
      else if (fc1 != null && fc2 == null) memnameToCount.put(name, fc1.or0());
      else if (fc1 != null && fc2 != null) memnameToCount.put(name, fc1.or(fc2));
    }
  }

  public void apply(FixedDynamicCounts fdc) {
    addAllCounts(fdc.removeCounts);
    addAllCounts(fdc.generateCounts);
  }

  public void addAllCounts(FixedCounts fc) {
    addCounts(functorToCount, fc.functorToCount);
    addCounts(memnameToCount, fc.memnameToCount);
  }

  public <Key> void addCounts(
      Map<Key, IntervalCount> countmap1, Map<Key, IntervalCount> countmap2) {
    for (Key key : countmap2.keySet()) {
      if (countmap1.containsKey(key)) {
        IntervalCount oldfc = countmap1.get(key);
        oldfc.add(countmap2.get(key));
      } else {
        countmap1.put(key, countmap2.get(key));
      }
    }
  }

  public void print() {
    Env.p(
        "----atoms of " + TypeEnv.getMemName(mem)
        /* + "(" + multiple + ") :"*/
        );
    for (Functor f : functorToCount.keySet()) {
      Env.p(f + ":" + functorToCount.get(f));
    }
    Env.p("----mems of " + TypeEnv.getMemName(mem) /*+ "(" + multiple + ") :"*/);
    for (String m : memnameToCount.keySet()) {
      Env.p(m + ":" + memnameToCount.get(m));
    }
  }
}

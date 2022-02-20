package type;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import runtime.Env;
import runtime.functor.Functor;
import type.argument.ActiveAtomPath;
import type.argument.ArgumentInferer;
import type.argument.ConstraintSet;
import type.argument.Path;
import type.argument.TypeVarConstraint;
import type.connect.ConnectInferer;
import type.quantity.FixedCounts;
import type.quantity.IntervalCount;
import type.quantity.NumCount;
import type.quantity.QuantityInferer;

/**
 * 型解析・推論結果を出力する
 *
 * @author kudo
 *
 */
class TypePrinter {

  private final Map<String, Map<Functor, TypeVarConstraint[]>> memnameToFunctorTypes;
  private final TreeSet<String> sortedMemNames;
  /** 念のため、ファンクタを名前順->引数数順でソートして管理する(意味ないかも) */
  private final TreeSet<Functor> sortedFunctors;

  /** 膜名 -> アトム／子膜の子数のセット */
  private final Map<String, FixedCounts> memnameToCounts;

  private ConnectInferer ci;

  TypePrinter(ArgumentInferer ai, QuantityInferer qi, ConnectInferer ci) {
    sortedMemNames = new TreeSet<>();
    sortedFunctors = new TreeSet<>(new FunctorComparator());

    memnameToFunctorTypes = new HashMap<>();
    if (ai != null) {
      // まず引数の型情報を集約する
      ConstraintSet cs = ai.getConstraints();
      Set<TypeVarConstraint> tvcs = cs.getTypeVarConstraints();
      for (TypeVarConstraint tvc : tvcs) {
        Path p = tvc.getPath();
        // TODO TracingPathについてはとりあえず無視
        if (!(p instanceof ActiveAtomPath)) continue;
        ActiveAtomPath aap = (ActiveAtomPath) p;
        String memname = aap.getMemName();
        sortedMemNames.add(memname);
        if (
          !memnameToFunctorTypes.containsKey(memname)
        ) memnameToFunctorTypes.put(memname, new HashMap<>());
        Map<Functor, TypeVarConstraint[]> functorToArgumentTypes = memnameToFunctorTypes.get(
          memname
        );
        Functor f = aap.getFunctor();
        sortedFunctors.add(f);
        if (!functorToArgumentTypes.containsKey(f)) functorToArgumentTypes.put(
          f,
          new TypeVarConstraint[f.getArity()]
        );
        TypeVarConstraint[] argtypes = functorToArgumentTypes.get(f);
        argtypes[aap.getPos()] = tvc;
      }
    }

    if (qi != null) {
      // 次に個数情報を得、ファンクタ名を集約する
      memnameToCounts = qi.getMemNameToFixedCountsSet();
      for (String memname : memnameToCounts.keySet()) {
        sortedMemNames.add(memname);
        FixedCounts fcs = memnameToCounts.get(memname);
        for (Functor f : fcs.functorToCount.keySet()) {
          sortedFunctors.add(f);
        }
      }
    } else {
      memnameToCounts = new HashMap<>();
    }

    if (ci != null) {
      this.ci = ci;
    }
  }

  /**
   * LMNtal syntax での型情報の表示
   * printAll メソッドを修正したもの
   */
  void printAllLMNSyntax() {
    Env.p("typeInformation{ ");

    ci.printLMNSyntax();

    for (String memname : sortedMemNames) {
      FixedCounts fcs = memnameToCounts.get(memname);
      String memNameLMN = memname.equals("??") ? "" : memname;
      Env.p(" " + memNameLMN + "{");
      // アクティブアトムの情報を出力
      for (Functor f : sortedFunctors) {
        // データアトム、コネクタは無視する
        if (TypeEnv.outOfPassiveFunctor(f) != TypeEnv.ACTIVE) continue;

        Map<Functor, TypeVarConstraint[]> functorToArgumentTypes = memnameToFunctorTypes.get(
          memname
        );

        boolean flagNoCount = false;
        if (fcs == null) flagNoCount = true;
        IntervalCount fc = null;

        if (!flagNoCount) {
          fc = fcs.functorToCount.get(f);
          if (fc == null) continue;
          if (
            fc.min.compare(new NumCount(0)) == 0 &&
            fc.max.compare(new NumCount(0)) == 0
          ) {
            continue;
          }
        }

        StringBuffer texp = new StringBuffer("");
        texp.append("\t{ functor(");
        texp.append(f.getQuotedAtomName());
        texp.append("), arity(");
        texp.append(f.getArity());
        texp.append("), link( ");

        if (f.getArity() > 0) {
          if (
            functorToArgumentTypes != null &&
            functorToArgumentTypes.containsKey(f)
          ) {
            // 引数の型を表示
            TypeVarConstraint[] argtypes = functorToArgumentTypes.get(f);
            for (int i = 0; i < argtypes.length; i++) {
              if (i != 0) texp.append(", ");
              texp.append(argtypes[i].shortStringLMNSyntax());
            }
          } else {
            for (int i = 0; i < f.getArity(); i++) {
              if (i != 0) texp.append(", ");
              texp.append("null");
            }
          }
        }
        texp.append("), count = ");

        if (flagNoCount) texp.append("null"); else texp.append(fc);

        texp.append(". }.");
        Env.p(texp);
        // }
        // else{
        // StringBuffer texp = new StringBuffer("");
        // texp.append("\t" + f.getQuotedAtomName());
        //
        // }
      }

      // 子膜の情報を出力
      if (fcs != null) {
        for (String childname : sortedMemNames) {
          if (fcs.memnameToCount.containsKey(childname)) {
            IntervalCount fc = fcs.memnameToCount.get(childname);
            if (fc == null) continue;
            String cname = childname.equals("??") ? "" : childname;
            Env.p("\tmem(" + cname + "{}, " + fc + ")");
          }
        }
      }

      Env.p(" }.");
    }

    Env.p("}.");
  }

  /**
   * 型情報の表示
   * 元々こちらが使われていたが上のメソッドを使うように修正
   * 現在はどこからも使われていないが残しておく
   */
  void printAll() {
    Env.p("Type Information : ");
    for (String memname : sortedMemNames) {
      FixedCounts fcs = memnameToCounts.get(memname);
      // if(fcs==null)continue;
      Env.p(memname + "{");
      // アクティブアトムの情報を出力
      for (Functor f : sortedFunctors) {
        // データアトム、コネクタは無視する
        if (TypeEnv.outOfPassiveFunctor(f) != TypeEnv.ACTIVE) continue;

        Map<Functor, TypeVarConstraint[]> functorToArgumentTypes = memnameToFunctorTypes.get(
          memname
        );

        // if(fcs.functorToCount.containsKey(f)){
        boolean flagNoCount = false;
        if (fcs == null) flagNoCount = true;
        IntervalCount fc = null;
        if (!flagNoCount) {
          fc = fcs.functorToCount.get(f);
          if (fc == null) continue;
          if (
            fc.min.compare(new NumCount(0)) == 0 &&
            fc.max.compare(new NumCount(0)) == 0
          ) {
            continue;
          }
        }

        StringBuffer texp = new StringBuffer("");
        texp.append("\t" + f.getQuotedAtomName());
        texp.append("(");
        if (f.getArity() > 0) {
          if (
            functorToArgumentTypes != null &&
            functorToArgumentTypes.containsKey(f)
          ) {
            // 引数の型を表示
            TypeVarConstraint[] argtypes = functorToArgumentTypes.get(f);
            for (int i = 0; i < argtypes.length; i++) {
              if (i != 0) texp.append(", ");
              texp.append(argtypes[i].shortString());
            }
          } else {
            for (int i = 0; i < f.getArity(); i++) {
              if (i != 0) texp.append(", ");
              texp.append("??");
            }
          }
        }
        texp.append(") : ");

        if (flagNoCount) texp.append("??"); else texp.append(fc);

        Env.p(texp);
        // }
        // else{
        // StringBuffer texp = new StringBuffer("");
        // texp.append("\t" + f.getQuotedAtomName());
        //
        // }
      }

      // 子膜の情報を出力
      if (fcs != null) {
        for (String childname : sortedMemNames) {
          if (fcs.memnameToCount.containsKey(childname)) {
            IntervalCount fc = fcs.memnameToCount.get(childname);
            if (fc == null) continue;
            Env.p("\t" + childname + "{} : " + fc);
          }
        }
      }

      Env.p("}");
    }
  }

  /**
   * ファンクタを名前順(辞書式)->引数数順でソートする
   */
  class FunctorComparator implements Comparator<Functor> {

    public int compare(Functor f1, Functor f2) {
      int nc = f1.getName().compareTo(f2.getName());
      if (nc != 0) return nc; else {
        return f1.getArity() - f2.getArity();
      }
    }
  }
}

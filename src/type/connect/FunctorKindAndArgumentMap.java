package type.connect;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import runtime.functor.FloatingFunctor;
import runtime.functor.IntegerFunctor;
import runtime.functor.StringFunctor;

class FunctorKindAndArgumentMap {

  Multimap<FunctorAndArgument, FunctorAndArgument> map;

  private static FunctorAndArgument INTEGER = new FunctorAndArgument(
    new IntegerFunctor(0),
    0
  );
  private static FunctorAndArgument STRING = new FunctorAndArgument(
    new StringFunctor(""),
    0
  );
  private static FunctorAndArgument FLOATING = new FunctorAndArgument(
    new FloatingFunctor(0),
    0
  );

  FunctorKindAndArgumentMap() {
    map = new Multimap<>();
  }

  Set<Map.Entry<FunctorAndArgument, Set<FunctorAndArgument>>> entrySet() {
    return map.entrySet();
  }

  int getSetSize(FunctorAndArgument faa) {
    return map.getSetSize(faa);
  }

  Set<FunctorAndArgument> getSet(FunctorAndArgument faa) {
    return map.getSet(faa);
  }

  void addAll(FunctorAndArgument faa, Set<FunctorAndArgument> set) {
    if (!faa.functor.isSymbol()) return;
    if (faa.functor.getName().equals("=")) return;
    if (set == null) {
      set = new HashSet<>();
    }
    map.addAll(faa, set);
  }

  void add(FunctorAndArgument faa, FunctorAndArgument faa2) {
    if (!faa.functor.isSymbol()) return;
    if (faa.functor.getName().equals("=")) return;

    if (faa2.functor.isInteger()) {
      map.add(faa, INTEGER);
    } else if (faa2.functor.isNumber()) {
      map.add(faa, FLOATING);
    } else if (faa2.functor.isString()) {
      map.add(faa, STRING);
    } else {
      map.add(faa, faa2);
    }
  }

  @Override
  public String toString() {
    return map.toString();
  }
}

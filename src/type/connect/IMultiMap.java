package type.connect;

import java.util.Map;
import java.util.Set;

interface IMultiMap<T, U> {
  void add(T t, U u);

  void addAll(T t, Set<U> su);

  Set<Map.Entry<T, Set<U>>> entrySet();

  boolean isEmpty();

  boolean containsKey(T t);

  Set<U> getSet(T t);

  int getSetSize(T t);
}

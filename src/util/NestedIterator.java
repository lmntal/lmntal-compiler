package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 指定された配列中の反復子が返す値を順に返す反復子
 * @author Mizuno
 */
public class NestedIterator<T> implements Iterator<T> {

  private Iterator<T>[] its;
  private int nextIndex;
  private Iterator<T> it;
  private T next;

  private void setNext() {
    while (!it.hasNext()) {
      if (nextIndex == its.length) {
        next = null;
        return;
      }
      it = its[nextIndex++];
    }
    next = it.next();
  }

  /** 指定されたMap内にあるデータを列挙する反復子を生成する */
  public NestedIterator(Iterator<T>[] its) {
    if (its.length == 0) {
      next = null;
    } else {
      this.its = its;
      it = its[0];
      nextIndex = 1;
      setNext();
    }
  }

  public boolean hasNext() {
    return next != null;
  }

  public T next() {
    if (next == null) throw new NoSuchElementException();
    T ret = next;
    setNext();
    return ret;
  }

  /** サポートしないので、UnsupportedOperationExceptionを投げる */
  public void remove() {
    throw new UnsupportedOperationException();
  }
}

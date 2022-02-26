package util;

/** Stackに積まれる要素のための親クラス。 このクラスのインスタンスはhead/tail等特殊な用途の要素のためにのみ使用し、 実際の要素には子クラスのインスタンスを使用する。 */
public class QueuedEntity {

  QueuedEntity next, prev;
  /** この entity がつまれているスタック */
  protected Stack stack;

  protected QueuedEntity() {
    next = prev = null;
  }

  /** スタックに積まれている場合はtrueを返す */
  public boolean isQueued() {
    return stack != null;
  }

  /** スタックに積まれていれば除去する。 */
  public void dequeue() {
    if (!isQueued()) {
      // System.out.println("SYSTEM ERROR: dequeued entity is not in a queue");
      return;
    }
    try {
      synchronized (stack) {
        next.prev = prev;
        prev.next = next;
        prev = null;
        next = null;
        stack = null;
      }
    } catch (NullPointerException e) {
      // 非同期に除去されていたので、何もしない。
    }
  }
}

package util;

/**
 * Stackに積まれる要素のための親クラス。
 * このクラスのインスタンスはhead/tail等特殊な用途の要素のためにのみ使用し、
 * 実際の要素には子クラスのインスタンスを使用する。
 */
public class QueuedEntity {
	/** 子クラス内からはこれらの変数に直接アクセスしない */
	QueuedEntity next, prev;
	/** このクラスのインスタンスを直接生成するのは同一パッケージ内のみ */
	protected QueuedEntity() {
		next = prev = null;
	}
	/** スタックに積まれている場合はtrueを返す */
	public boolean isQueued() {
		return next != null;
	}
	/** スタックから除去 */
	public void dequeue() {
		if (!isQueued()) {
			System.out.println("SYSTEM ERROR: dequeued entity is not in a queue");
			return;
		}
		next.prev = prev;
		prev.next = next;
		prev = null;
		next = null;
	}
}

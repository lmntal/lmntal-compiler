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
	/** スタックに積まれている場合はtrue。 */
	public boolean isQueued() {
		return next == null && prev == null;
	}
	/** スタックから除去 */
	public void dequeue() {
		next.prev = prev;
		prev.next = next;
	}
}

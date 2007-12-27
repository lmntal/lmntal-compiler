package util;
import java.util.ArrayList;

public final class Stack {
	/** 底 */
	private QueuedEntity head;
	/** トップ */
	private QueuedEntity tail;
	
	public Stack() {
		head = new QueuedEntity();
		tail = new QueuedEntity();
		head.prev = tail.prev = head;
		head.next = tail.next = tail;
	}
	synchronized public void push(QueuedEntity entity) {
		if (entity.isQueued()) {
			//タスクが、アトム主導テストに失敗した後で親膜のアトムスタックに積むとき、
			//他のスレッドがすでに他のスタックに積んでいる可能性がある。
			//…が、システムコールアトムを実装しないとそう言う状況は発生しない。
			//タスクが移動するのは、自分が管理する膜のアトムのみ。
			//子タスクは操作できないし、親タスクも（このタスクが本膜をロックしているので）操作できない。
			Util.errPrintln("SYSTEM ERROR: enqueued entity is already in a queue");
			entity.dequeue();
		}
		entity.stack = this;
		entity.prev = tail.prev;
		entity.next = tail;
		entity.prev.next = entity;
		tail.prev = entity;
	}
	/** スタックstackの内容をこのスタックの底に移動する */
	synchronized public void moveFrom(Stack stack) {
		//stack は仮スタックであり、ルート膜をロックしているスレッド以外操作しいないので
		//排他制御の必要はない。

		if (stack.isEmpty()) return;
		QueuedEntity oldFirst = head.next;
		QueuedEntity addedLast = stack.tail.prev;

		//先頭
		head.next = stack.head.next;
		stack.head.next.prev = head;
		
		//(引数に渡されたstackの)最後
		addedLast.next = oldFirst;
		oldFirst.prev = addedLast;
		stack.head.next = stack.tail;
		stack.tail.prev = stack.head;

		//途中
		for (QueuedEntity t = head.next; t != oldFirst; t = t.next) {
			t.stack = this;
		}
	}
	synchronized public QueuedEntity pop() {
		if(isEmpty()) return null;
		
		QueuedEntity ret = tail.prev;
		tail.prev = ret.prev;
		ret.prev.next = tail;

		ret.next = ret.prev = null;
		ret.stack = null;
		return ret;
	}
	synchronized public QueuedEntity peek() {
		if(isEmpty()) return null;
		return tail.prev;
	}
	/** スタックが空ならtrue */
	public boolean isEmpty() {
		return tail.prev == head;
	}
//	/**@deprecated*/
//	public void remove(QueuedEntity entity) {
//		entity.remove();
//	}
	public String toString() {
		ArrayList list = new ArrayList();
		QueuedEntity entity = head.next;
		while (entity != tail) {
			list.add(entity);
			entity = entity.next;
		}
		return list.toString();
	}
}

package util;

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
	public void push(QueuedEntity entity) {
		if (entity.isQueued()) {
			System.out.println("SYSTEM ERROR: enqueued entity is already in a queue");
			entity.dequeue();
		}
		entity.prev = tail.prev;
		entity.next = tail;
		entity.prev.next = entity;
		tail.prev = entity;
	}
//	public void pushAll(Collection entities) {
//		Iterator i = entities.iterator();
//		QueuedEntity last = tail.prev;
//		while (i.hasNext()) {
//			QueuedEntity e = (QueuedEntity)i.next();
//			last.next = e;
//			e.prev = last;
//			last = e;
//		}
//		last.next = tail;
//		tail.prev = last;
//	}
	/** スタックstackの内容をこのスタックの底に移動する */
	public void moveFrom(Stack stack) {
		if (stack.isEmpty()) return;
		QueuedEntity oldFirst = head.next;
		QueuedEntity addedLast = stack.tail.prev;
		head.next = stack.head.next;
		stack.head.next.prev = head;
		addedLast.next = oldFirst;
		oldFirst.prev = addedLast;
		stack.head.next = stack.tail;
		stack.tail.prev = stack.head;
	}
	/** このメソッドは使わないかもしれない */
	public void pushToBottom(QueuedEntity entity) {
		entity.prev = head;
		entity.next = head.next;
		head.next = entity;
		entity.next.prev = entity;
	}
	public QueuedEntity pop() {
		if(isEmpty()) return null;
		
		QueuedEntity ret = tail.prev;
		tail.prev = ret.prev;
		ret.prev.next = tail;

		ret.next = ret.prev = null;
		return ret;
	}
	public QueuedEntity peek() {
		if(isEmpty()) return null;
		return tail.prev;
	}
	/** スタックが空ならtrue */
	public boolean isEmpty(){
		return tail.prev == head;
	}
//	/**@deprecated*/
//	public void remove(QueuedEntity entity) {
//		entity.remove();
//	}
}

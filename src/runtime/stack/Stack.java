package runtime.stack;

import java.util.*;

public final class Stack {
	private QueuedEntity head, tail;
	
	public Stack() {
		head = new QueuedEntity();
		tail = new QueuedEntity();
		head.prev = tail.prev = head;
		head.next = tail.next = tail;
	}
	public void push(QueuedEntity entity) {
		entity.prev = tail.prev;
		entity.next = tail;
		entity.prev.next = entity;
		tail.prev = entity;
	}
	public void pushAll(Collection entities) {
		Iterator i = entities.iterator();
		QueuedEntity last = tail.prev;
		while (i.hasNext()) {
			QueuedEntity e = (QueuedEntity)i.next();
			last.next = e;
			e.prev = last;
			last = e;
		}
		last.next = tail;
		tail.prev = last;
	}
	public void pushToBottom(QueuedEntity entity) {
		entity.prev = head;
		entity.next = head.next;
		head.next = entity;
		entity.next.prev = entity;
	}
	public QueuedEntity pop() {
		QueuedEntity ret = tail.prev;
		tail.prev = ret.prev;
		ret.prev.next = tail;

		ret.next = ret.prev = null;
		return ret;
	}
	public QueuedEntity peek() {
		return tail.prev;
	}
//	QueuedEntity.remove‚ª‚ ‚é‚Ì‚ÅŽg‚í‚È‚¢
/*	public void remove(QueuedEntity entity) {
		entity.prev.next = entity.next;
		entity.next.prev = entity.prev;
		entity.prev = entity.next = null;
	}
*/
}

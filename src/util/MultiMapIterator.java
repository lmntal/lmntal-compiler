package util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * マップの値に格納されたコレクション中のデータを列挙する反復子。
 * 複数要素を格納するマップを実現するために、値にコレクションを挿入するマップに対して利用する。
 * @author Mizuno
 */
public class MultiMapIterator implements Iterator{
	private Iterator setIterator;
	private Iterator dataIterator;
	private Object next;
	private void setNext() {
		while (!dataIterator.hasNext()) {
			if (!setIterator.hasNext()) {
				next = null;
				return;
			}
			dataIterator = ((Collection)setIterator.next()).iterator();
		}
		next = dataIterator.next();
	}

	/** 指定されたMap内にあるデータを列挙する反復子を生成する */
	public MultiMapIterator(Map map) {
		setIterator = map.values().iterator();
		dataIterator = Util.NULL_ITERATOR;
		setNext();
	}
	public boolean hasNext() {
		return next != null;
	}
	public Object next() {
		if (next == null)
			throw new NoSuchElementException();
		Object ret = next;
		setNext();
		return ret;
	}
	/** サポートしないので、UnsupportedOperationExceptionを投げる */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

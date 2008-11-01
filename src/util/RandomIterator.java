/*
 * 作成日: 2004/01/09
 *
 */
package util;

import java.util.*;

/**
 * @author Ken
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public final class RandomIterator<E> implements Iterator<E> {
	private static Random random = new Random();
	private int[] index;
	private List<E> list;
	private int size;
	
	public RandomIterator(List<E> list) {
		this.list = list;
		size = list.size();
		index = new int[size];
		for (int i = 0; i < size; i++) {
			index[i] = i;
		}
	}
	/* (非 Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/* (非 Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return size > 0;
	}

	/* (非 Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public E next() {
		int i = random.nextInt(size);
		int i2 = index[i];
		index[i] = index[size-1];
		size--;
		return list.get(i2);
	}

}

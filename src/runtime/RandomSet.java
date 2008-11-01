/*
 * 作成日: 2004/05/05
 *
 */
package runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import util.RandomIterator;
import util.Util;

/**
 * @author Mizuno
 *
 * O(1)での追加・削除とランダムアクセスができるセット。
 */
public class RandomSet<E> implements Set<E> {
	private ArrayList<E> array = new ArrayList<E>();
	private HashMap<E, Integer> map = new HashMap<E, Integer>();

	public int size() {
		return array.size();
	}

	public void clear() {
		array.clear();
		map.clear();
	}

	public boolean isEmpty() {
		return array.isEmpty();
	}

	public Object[] toArray() {
		return array.toArray();
	}

	public boolean add(E o) {
		if (map.containsKey(o)) {
			return false;
		}
		array.add(o);
		map.put(o, new Integer(array.size() - 1));
		return true;
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public boolean remove(Object o) {
		if (!map.containsKey(o)) {
			return false;
		}
		int pos = ((Integer)map.get(o)).intValue();
		if (pos == array.size() - 1) {
			array.remove(pos);
		} else {
			E last = array.remove(array.size() - 1);
			array.set(pos, last);
			map.put(last, pos);
		}
		map.remove(o);
		return true;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		Iterator<? extends E> i = c.iterator();
		while (i.hasNext()) {
			if (add(i.next())) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean containsAll(Collection<?> c) {
		return map.keySet().containsAll(c);
	}

	public boolean removeAll(Collection <?>c) {
		boolean ret = false;
		Iterator<?> i = c.iterator();
		while (i.hasNext()) {
			if (remove(i.next())) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public Iterator<E> iterator() {
//		2005/10/23 Mizuno
//		ランダムにしたくないときは、このクラスのインスタンスは利用しない
		return new RandomIterator<E>(array);
	}

	public <T> T[] toArray(T[] a) {
		return array.toArray(a);
	}

	/** 整合性を検査する。デバッグ用。*/
	public boolean verify() {
		if (array.size() != map.size()) {
			return false;
		}
		Iterator<E> i = map.keySet().iterator();
		while (i.hasNext()) {
			E o = i.next();
			E o2 = array.get(map.get(o).intValue());
			if (o == null) {
				if (o2 != null) {
					return false;
				}
			} else {
				if (!o.equals(o2)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Set)) {
			return false;
		}
		Set<E> s = (Set)o;
		if (s.size() != size()) {
			return false;
		}
		for (int i = 0; i < size(); i++) {
			if (!s.contains(array.get(i))) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int ret = 0;
		for (int i = 0; i < size(); i++) {
			if (array.get(i) != null) {
				ret += array.get(i).hashCode();
			}
		}
		return ret;
	}

	//このクラスのテスト

	private static final int N = 5;	
	public static void main(String[] args) {

		HashSet h = new HashSet();
		RandomSet r = new RandomSet();
		Integer[] data = new Integer[N];
		for (int i = 0; i < N; i++) {
			data[i] = new Integer(i);
		}

		assertTrue(h.size() == r.size());
		assertTrue(h.isEmpty() == r.isEmpty());
		assertTrue(h.contains(data[0]) == r.contains(data[0]));
		assertTrue(h.contains(null) == r.contains(null));
		for (int i = 0; i < N; i++) {
			assertTrue(h.add(data[i]) == r.add(data[i]));
		}
		assertTrue(h.add(data[0]) == r.add(data[0]));
		for (int i = 0; i < N; i++) {
			assertTrue(h.contains(data[i]) == r.contains(data[i]));
		}
		assertTrue(h.contains(null) == r.contains(null));
		assertTrue(h.add(null) == r.add(null));
		assertTrue(h.contains(null) == r.contains(null));
		int i = 0;
		Iterator it = r.iterator();
		while (it.hasNext()) {
			i++;
			assertTrue(r.contains(it.next()));
		}
		assertTrue(i == N+1); //nullを追加したから。
		assertTrue(h.size() == r.size());
		assertTrue(h.isEmpty() == r.isEmpty());
		assertTrue(h.containsAll(Arrays.asList(data)) == r.containsAll(Arrays.asList(data)));
		assertTrue(h.remove(data[0]) == r.remove(data[0]));
		Object o = new Object();
		assertTrue(h.remove(new Object()) == r.remove(new Object()));
		assertTrue(r.containsAll(h));
		assertTrue(h.containsAll(Arrays.asList(data)) == r.containsAll(Arrays.asList(data)));
		assertTrue(h.addAll(Arrays.asList(data)) == r.addAll(Arrays.asList(data)));
		assertTrue(r.equals(h));
		assertTrue(h.hashCode() == r.hashCode());
		assertTrue(r.verify());

		assertTrue(r.size() == 0);
		assertTrue(h.add(o) == r.add(o));
		assertTrue(h.remove(o) == r.remove(o));
		assertTrue(h.remove(o) == r.remove(o));
		assertTrue(r.equals(h));
		r.add(o);
		assertTrue(!r.equals(h));
		h.add(o);
		assertTrue(r.equals(h));
		h.add(data[0]);
		assertTrue(!r.equals(h));
		assertTrue(r.verify());
		Util.println("ok");

	}
	private static void assertTrue(boolean b) {
		if (!b) {
			throw new RuntimeException();
		}
	}
}

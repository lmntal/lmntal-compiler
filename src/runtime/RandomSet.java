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

/**
 * @author Mizuno
 *
 * O(1)での追加・削除とランダムアクセスができるセット。
 */
public class RandomSet implements Set {
	private ArrayList array = new ArrayList();
	private HashMap map = new HashMap();
	
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

	public boolean add(Object o) {
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
			Object last = array.remove(array.size() - 1);
			array.set(pos, last);
			map.put(last, new Integer(pos));
		}
		map.remove(o);
		return true;
	}

	public boolean addAll(Collection c) {
		boolean ret = false;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			if (add(i.next())) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean containsAll(Collection c) {
		return map.keySet().containsAll(c);
	}

	public boolean removeAll(Collection c) {
		boolean ret = false;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			if (remove(i.next())) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public Iterator iterator() {
		if (Env.shuffle >= Env.SHUFFLE_MEMS) {
			return new RandomIterator(array);
		} else {
			return array.iterator();
		}
	}

	public Object[] toArray(Object[] a) {
		return array.toArray(a);
	}

	/** 整合性を検査する。デバッグ用。*/
	public boolean verify() {
		if (array.size() != map.size()) {
			return false;
		}
		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			Object o = i.next();
			Object o2 = array.get(((Integer)map.get(o)).intValue());
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
		Set s = (Set)o;
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
		
		assert(h.size() == r.size());
		assert(h.isEmpty() == r.isEmpty());
		assert(h.contains(data[0]) == r.contains(data[0]));
		assert(h.contains(null) == r.contains(null));
		for (int i = 0; i < N; i++) {
			assert(h.add(data[i]) == r.add(data[i]));
		}
		assert(h.add(data[0]) == r.add(data[0]));
		for (int i = 0; i < N; i++) {
			assert(h.contains(data[i]) == r.contains(data[i]));
		}
		assert(h.contains(null) == r.contains(null));
		assert(h.add(null) == r.add(null));
		assert(h.contains(null) == r.contains(null));
		int i = 0;
		Iterator it = r.iterator();
		while (it.hasNext()) {
			i++;
			assert(r.contains(it.next()));
		}
		assert(i == N+1); //nullを追加したから。
		assert(h.size() == r.size());
		assert(h.isEmpty() == r.isEmpty());
		assert(h.containsAll(Arrays.asList(data)) == r.containsAll(Arrays.asList(data)));
		assert(h.remove(data[0]) == r.remove(data[0]));
		Object o = new Object();
		assert(h.remove(new Object()) == r.remove(new Object()));
		assert(r.containsAll(h));
		assert(h.containsAll(Arrays.asList(data)) == r.containsAll(Arrays.asList(data)));
		assert(h.addAll(Arrays.asList(data)) == r.addAll(Arrays.asList(data)));
		assert(r.equals(h));
		assert(h.hashCode() == r.hashCode());
		assert(r.verify());
		r.clear();
		h.clear();
		assert(r.size() == 0);
		assert(h.add(o) == r.add(o));
		assert(h.remove(o) == r.remove(o));
		assert(h.remove(o) == r.remove(o));
		assert(r.equals(h));
		r.add(o);
		assert(!r.equals(h));
		h.add(o);
		assert(r.equals(h));
		h.add(data[0]);
		assert(!r.equals(h));
		assert(r.verify());
		System.out.println("ok");
	}
	private static void assert(boolean b) {
		if (!b) {
			throw new RuntimeException();
		}
	}
}

package runtime;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import util.Util;

/**
 * アトムの集合を管理するためのクラス。
 * 要素はAbstractAtomクラスかその子孫のクラスのインスタンスのみと仮定する。
 * Functorをキーとし、そのFunctorを持つアトムの集合を値とするMapを使って、
 * Functor毎にアトムを管理している。
 * @author Mizuno
 */
public final class AtomSet implements Set{
	/** atoms内のアトムの数。整合性要注意 */
	private int size = 0;
	/** 実際にアトムの集合を管理している変数 */
	private Map atoms = new HashMap();

	/** アトム数の取得 */	
	public int size() {
		return size;
	}
	/** 空の場合にtrue */
	public boolean isEmpty() {
		return size == 0;
	}
	/** 与えられたアトムがこの集合内にある場合はtrue。 */
	public boolean contains(Object o) {
		Functor f = ((Atom)o).getFunctor();
		Set s = (Set)atoms.get(f);
		if (s == null) {
			return false;
		} else {
			return s.contains(o);
		}
	}

//2003/10/22 Mizuno このメソッドはやめて、iteratorOfFunctorを使うようにする。
//	/**
//	 * 与えられたFunctorを持つアトムの集合を返す。
//	 * そのようなアトムがない場合は空の集合を返す。
//	 * 返された集合に直接add/remove等の操作しないこと。
//	 */	
//	public Set getAtomsOfFunctor(Functor f) {
//		Set s = (Set)atoms.get(f);
//		if (s == null) {
//			return new HashSet();
//		} else {
//			return s;
//		}
//	}
	/** この集合内にあるアトムの反復子を返す */
	public Iterator iterator() {
		return new AtomIterator(atoms);
	}
	/** 与えられた名前を持つアトムの反復子を返す */
	public Iterator iteratorOfFunctor(Functor functor) {
		Set s = (Set)atoms.get(functor);
		if (s == null) {
			return Util.NULL_ITERATOR;
		} else {
			return s.iterator();
		}
	}
	/** 
	 * Functorの反復子を返す。
	 * この集合内にあるアトムのFunctorは全てこの反復子を使って取得できるが、
	 * この反復子で取得できるFunctorを持つアトムが必ずこの集合内にあるとは限らない。
	 */
	public Iterator functorIterator() {
		return atoms.keySet().iterator();
	}
	/**
	 * この集合内の全てのアトムか格納されている配列を返す。
	 * 返される配列の実行時の型はAbstractAtom[]です。
	 */
	public Object[] toArray() {
		Object[] ret = new Atom[size];
		int index = 0;
		Iterator it = iterator();
		while (it.hasNext()) {
			ret[index++] = it.next();
		}
		if (index != size) {
			System.err.println("SYSTEM ERROR!: AtomSet.size is incorrect");
		}
		return ret;
	}
	/**
	 * この集合内の全てのアトムか格納されている配列を返す。
	 * 返される配列の実行時の型は引数に渡された配列の実行時の型と同じです。
	 */
	public Object[] toArray(Object[] a) {
		if (a.length < size) {
			a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
		}
		int index = 0;
		Iterator it = iterator();
		while (it.hasNext()) {
			a[index++] = it.next();
		}
		while (index < size) {
			a[index++] = null;
		}
		return a;
	}
	/**
	 * 指定されたアトムを追加する。
	 * @return この集合が変更された場合はtrue
	 */
	public boolean add(Object o) {
		Functor f = ((Atom)o).getFunctor();
		Set s = (Set)atoms.get(f);
		if (s == null) {
			s = new HashSet();
			s.add(o);
			atoms.put(f, s);
			size++;
			return true;
		} else if (s.add(o)) {
			size++;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 指定されたアトムがあれば除去する。
	 * @return この集合が変更された場合はtrue
	 */
	public boolean remove(Object o) {
		Functor f = ((Atom)o).getFunctor();
		Set s = (Set)atoms.get(f);
		if (s == null) {
			return false;
		} else if (s.remove(o)) {
			size--;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 指定されたコレクション内の全ての要素が含まれる場合にはtrueを返す。
	 * 現在は効率の悪い実装をしているので、将来変更する必要がある。 
	 */
	public boolean containsAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (contains(it.next()) == false) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 指定されたコレクション内の全ての要素をこの集合に追加する。
	 * 現在は効率の悪い実装をしているので、将来変更する必要がある。 
	 * @return この集合が変更された場合はtrue
	 */
	public boolean addAll(Collection c) {
		boolean ret = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (add(it.next())) {
				ret = true;
			}
		}
		return ret;
	}
	/**
	 * 指定されたコレクション内の全ての要素をこの集合から除去する。
	 * 現在は効率の悪い実装をしているので、将来変更する必要がある。 
	 * @return この集合が変更された場合はtrue
	 */
	public boolean removeAll(Collection c) {
		boolean ret = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (remove(it.next())) {
				size--;
				ret = true;
			}
		}
		return ret;
	}
	/** サポートしない */
	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}
	/** 全ての要素を除去する */
	public void clear() {
		atoms.clear();
		size = 0;
	}
}
/** AtomSetの要素に対して使用する反復子 */
final class AtomIterator implements Iterator {
	/** Functorをキーとし、アトムの集合（Set）を値とするMap */
	Map atoms;
	/** あるFunctorを持つアトムの集合の反復子 */
	Iterator atomSetIterator;
	/** あるFunctorを持つアトムを列挙する反復子。 */
	Iterator atomIterator;

	/** 指定されたMap内にあるアトムを列挙する反復子を生成する */
	AtomIterator(Map atoms) {
		this.atoms = atoms;
		atomSetIterator = atoms.values().iterator();
		if (atomSetIterator.hasNext()) {
			atomIterator = ((Set)atomSetIterator.next()).iterator();
		} else {
			atomIterator = Util.NULL_ITERATOR;
		}
	}
	public boolean hasNext() {
		while (atomIterator.hasNext() == false) {
			if (atomSetIterator.hasNext() == false) {
				return false;
			}
			atomIterator = ((Set)atomSetIterator.next()).iterator();
		}
		return true;
	}
	public Object next() {
		while (atomIterator.hasNext() == false) {
			// 最後まで来ていた場合、ここでNoSuchElementExceptionが発生する
			atomIterator = ((Set)atomSetIterator.next()).iterator();
		}
		return atomIterator.next();
	}
	/** サポートしないので、UnsupportedOperationExceptionを投げる */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}

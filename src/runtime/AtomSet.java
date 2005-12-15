package runtime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import util.Util;

/**
 * アトムの集合を管理するためのクラス。
 * 要素はAtomクラスのインスタンスのみと仮定する。
 * Functorをキーとし、そのFunctorを持つアトムの集合を値とするMapを使って、
 * Functor毎にアトムを管理している。
 * @author Mizuno
 */
public final class AtomSet implements Serializable {
	/** atoms内のアトムの数。整合性要注意 */
	private int size = 0;
	/** 実際にアトムの集合を管理している変数 */
	private Map atoms = new HashMap();
	/** OUTSIDE_PROXYの集合を管理している変数 */
	private Map outs = new HashMap();

	/** アトム数の取得 */
	public int size() {
		return size;
	}
	/** 自由リンク管理アトム以外のアトムの数の取得 */
	public int getNormalAtomCount() {
		return size - getAtomCountOfFunctor(Functor.INSIDE_PROXY)
					 - getOutCount();
	}
	/** 指定されたFunctorを持つアトムの数の取得 */
	public int getAtomCountOfFunctor(Functor f) {
		Collection c = f.isOUTSIDE_PROXY() ? (Collection)outs.get(f)
										   : (Collection)atoms.get(f);
		if (c == null) {
			return 0;
		} else {
			return c.size();
		}
	}
	/** OUTSIDE_PROXYの数の取得 */
	public int getOutCount() {
		Iterator i = outs.values().iterator();
		int k=0;
		while(i.hasNext()){
			k += ((Collection)i.next()).size();
		}
		return k;
	}
	/** 空かどうかを返す */
	public boolean isEmpty() {
		return size == 0;
	}
	/** 与えられたアトムがこの集合内にあるかどうかを返す */
	public boolean contains(Object o) {
		return ((Atom)o).mem.atoms == this;
	}

	/** この集合内にあるアトムの反復子を返す */
	public Iterator iterator() {
		return new AtomIterator(atoms, outs);
	}
	
	/** 与えられた名前を持つアトムの反復子を返す。所属膜指定を無視する版 */
//	public Iterator ignorePathIterator(Functor f) {
//		return new AtomIgnorePathIterator(this, f);
//	}
	
	/** 与えられた名前を持つアトムの反復子を返す */
	public Iterator iteratorOfFunctor(Functor f) {
		Collection c = f.isOUTSIDE_PROXY() ? (Collection)outs.get(f)
										   : (Collection)atoms.get(f);
		if (c == null) {
			return Util.NULL_ITERATOR;
		} else {
			return c.iterator();
		}
	}
	/** OUTSIDE_PROXYの反復氏を返す */
	public Iterator iteratorOfOUTSIDE_PROXY() {
		return new OutIterator(outs);
	}
	/** 
	 * OUTSIDE_PROXYを除くFunctorの反復子を返す。
	 * この集合内にあるアトムのFunctorは全てこの反復子を使って取得できるが、
	 * この反復子で取得できるFunctorを持つアトムが必ずこの集合内にあるとは限らない。
	 * 
	 * todo removeされた時に、アトム数が0になったFunctor除去するようにした方が良いか？
	 * (n-kato) 膜のgcメソッド（コピーGCによるローカルidの振り直しを行う予定）に任せて放置していいと思います。
	 * ただし、gcメソッドは現在呼ばれませんけど。
	 */
	public Iterator functorIterator() {
		return atoms.keySet().iterator();
	}
	/**
	 * この集合内の全てのアトムか格納されている配列を返す。
	 * 返される配列の実行時の型はAtom[]です。
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
		Collection c = f.isOUTSIDE_PROXY() ? (Collection)outs.get(f)
										   : (Collection)atoms.get(f);
		if (c == null) {
			if (Env.shuffle >= Env.SHUFFLE_ATOMS)
				c = new RandomSet();
			else
				c = new HashSet();
			if(f.isOUTSIDE_PROXY())
				outs.put(f, c);
			else
				atoms.put(f, c);
		}
		if (c.add(o)) {
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
		Collection c = f.isOUTSIDE_PROXY() ? (Collection)outs.get(f)
										   : (Collection)atoms.get(f);
		if (c.remove(o)) {
			size--;
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * 指定されたコレクション内の全ての要素が含まれる場合にはtrueを返す。
	 * <p>現在は効率の悪い実装をしているので、頻繁に使うなら変更する必要がある。 
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
	 * <p>現在は効率の悪い実装をしているので、頻繁に使うなら変更する必要がある。 
	 * @return この集合が変更された場合はtrue
	 */
	public boolean addAll(Collection c) {
		boolean ret = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (add(it.next())) {
				ret = true;
				//addメソッドの中でsizeは変更している
			}
		}
		return ret;
	}
	/**
	 * 指定されたコレクション内の全ての要素をこの集合から除去する。
	 * <p>現在は効率の悪い実装をしているので、頻繁に使うなら変更する必要がある。 
	 * @return この集合が変更された場合はtrue
	 */
	public boolean removeAll(Collection c) {
		boolean ret = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			ret |= remove(it.next());
			//removeメソッドの中でsizeは変更している
		}
		return ret;
	}
	/** サポートしない */
	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}
	/** 全ての要素を除去する */
	protected void clear() {
		atoms.clear();
		outs.clear();
		size = 0;
	}
	
	/** sizeの整合性を検査する。デバッグ用。*/
	public boolean verify() {
		int n = 0;
		Iterator it = atoms.values().iterator();
		while (it.hasNext()) {
			n += ((RandomSet)it.next()).size();
		}
		return size == n;
	}
	/**デバッグ用出力*/
	public void print() {
		System.out.println("AtomSet: ");
		Iterator it = iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("result of verify() is " + verify());
	}
}

/** AtomSetの要素に対して使用する反復子 */
final class AtomIterator implements Iterator {
	/** Functorをキーとし、アトムの集合（Set）を値とするMap */
	Map atoms, outs;
	/** あるFunctorを持つアトムの集合の反復子 */
	Iterator atomSetIterator, outSetIterator;
	/** あるFunctorを持つアトムを列挙する反復子。 */
	Iterator atomIterator;
	/** atomSetIteratorのhasNext()がfalseになったらtrue */
	boolean atomSet_null = false;
	
	/** 指定されたMap内にあるアトムを列挙する反復子を生成する */
	AtomIterator(Map atoms, Map outs) {
		this.atoms = atoms;
		this.outs = outs;
		atomSetIterator = atoms.values().iterator();
		outSetIterator = outs.values().iterator();
		if (atomSetIterator.hasNext()) {
			atomIterator = ((Collection)atomSetIterator.next()).iterator();
		} else if(outSetIterator.hasNext()) {
			atomSet_null = true;
			atomIterator = ((Collection)outSetIterator.next()).iterator();
		} else {
			atomIterator = Util.NULL_ITERATOR;
		}
	}
	public boolean hasNext() {
		if(atomSet_null)
			return hasNext2();
		while (atomIterator.hasNext() == false) {
			if (atomSetIterator.hasNext() == false) {
				atomSet_null = true;
				return hasNext2();
			}
			atomIterator = ((Collection)atomSetIterator.next()).iterator();
		}
		return true;
	}
	private boolean hasNext2() {
		while (atomIterator.hasNext() == false) {
			if (outSetIterator.hasNext() == false) {
				return false;
			}
			atomIterator = ((Collection)outSetIterator.next()).iterator();
		}
		return true;
	}
	public Object next() {
		while (atomIterator.hasNext() == false) {
			// 最後まで来ていた場合、ここでNoSuchElementExceptionが発生する
			if(atomSet_null)				
				atomIterator = ((Collection)outSetIterator.next()).iterator();
			else if(atomSetIterator.hasNext())
				atomIterator = ((Collection)atomSetIterator.next()).iterator();
			else{
				atomSet_null = true;
				atomIterator = ((Collection)outSetIterator.next()).iterator();
			}
				
		}
		return atomIterator.next();
	}
	/** サポートしないので、UnsupportedOperationExceptionを投げる */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}

/** AtomSetのOUTSIDE_PROXYの要素に対して使用する反復子 */
final class OutIterator implements Iterator {
	/** Functorをキーとし、アトムの集合（Set）を値とするMap */
	Map outs;
	/** あるFunctorを持つアトムの集合の反復子 */
	Iterator outSetIterator;
	/** あるFunctorを持つアトムを列挙する反復子。 */
	Iterator outIterator;

	/** 指定されたMap内にあるアトムを列挙する反復子を生成する */
	OutIterator(Map outs) {
		this.outs = outs;
		outSetIterator = outs.values().iterator();
		if (outSetIterator.hasNext()) {
			outIterator = ((Collection)outSetIterator.next()).iterator();
		} else {
			outIterator = Util.NULL_ITERATOR;
		}
	}
	public boolean hasNext() {
		while (outIterator.hasNext() == false) {
			if (outSetIterator.hasNext() == false) {
				return false;
			}
			outIterator = ((Collection)outSetIterator.next()).iterator();
		}
		return true;
	}
	public Object next() {
		while (outIterator.hasNext() == false) {
			// 最後まで来ていた場合、ここでNoSuchElementExceptionが発生する
			outIterator = ((Collection)outSetIterator.next()).iterator();
		}
		return outIterator.next();
	}
	/** サポートしないので、UnsupportedOperationExceptionを投げる */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}

//final class AtomIgnorePathIterator implements Iterator {
//	Iterator it;
//	Atom next;
//	Functor f;
//	
//	AtomIgnorePathIterator(AtomSet as, Functor f) {
//		it = as.iterator();
//		this.f = f;
//	}
//	public Object next() {
//		
//	}
//	public boolean hasNext() {
//		while(it.hasNext()) {
//			next = (Atom)it.next();
//			if(next.getFunctor())
//		}
//	}
//	public void remove() {
//		throw new UnsupportedOperationException();
//	}
//}

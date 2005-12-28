package runtime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import util.MultiMapIterator;
import util.NestedIterator;
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
	/** メモリ利用量削減のため、データアトムはまとめて管理 */
	private ArrayList dataAtoms = new ArrayList();
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
		if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
			ArrayList l = (ArrayList)(f.isOUTSIDE_PROXY() ? outs.get(f) : atoms.get(f));
			if (l == null) {
				return 0;
			} else {
				return l.size();
			}
		} else {
			Iterator it = new DataAtomIterator(f);
			int size = 0;
			while (it.hasNext()) {
				size++;
			}
			return size;
		}
	}
	/** OUTSIDE_PROXYの数の取得 */
	public int getOutCount() {
		Iterator i = outs.values().iterator();
		int k=0;
		while(i.hasNext()){
			k += ((ArrayList)i.next()).size();
		}
		return k;
	}
	/** 空かどうかを返す */
	public boolean isEmpty() {
		return size == 0;
	}
	/** この集合内にあるアトムの反復子を返す */
	public Iterator iterator() {
		return new NestedIterator(new Iterator[] {dataAtoms.iterator(),
													new MultiMapIterator(outs),
													new MultiMapIterator(atoms)});
	}
	
	/** 与えられた名前を持つアトムの反復子を返す */
	public Iterator iteratorOfFunctor(Functor f) {
		if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
			ArrayList l = (ArrayList)(f.isOUTSIDE_PROXY() ? outs.get(f) : atoms.get(f));
			if (l == null) {
				return Util.NULL_ITERATOR;
			} else {
				return l.iterator();
			}
		} else {
			return new DataAtomIterator(f);
		}
	}
	/** OUTSIDE_PROXYの反復氏を返す */
	public Iterator iteratorOfOUTSIDE_PROXY() {
		return new MultiMapIterator(outs);
	}
	/** 
	 * アクティブアトムのFunctorの反復子を返す。
	 * この集合内にあるアトムのFunctorは全てこの反復子を使って取得できるが、
	 * この反復子で取得できるFunctorを持つアトムが必ずこの集合内にあるとは限らない。
	 * 
	 * todo removeされた時に、アトム数が0になったFunctor除去するようにした方が良いか？
	 * (n-kato) 膜のgcメソッド（コピーGCによるローカルidの振り直しを行う予定）に任せて放置していいと思います。
	 * ただし、gcメソッドは現在呼ばれませんけど。
	 */
	public Iterator activeFunctorIterator() {
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
		ArrayList l;
		if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
			Map map = f.isOUTSIDE_PROXY() ? outs : atoms;
			l = (ArrayList)map.get(f);
			if (l == null) {
				l = new ArrayList();
				map.put(f, l);
			}
		} else {
			l = dataAtoms;
		}
		l.add(o);
		((Atom)o).index = l.size() - 1;
		size++;
		return true;
	}
	/**
	 * 指定されたアトムがあれば除去する。
	 * @return この集合が変更された場合はtrue
	 */
	public boolean remove(Object o) {
		Atom a = (Atom)o;
		Functor f = a.getFunctor();
		ArrayList l;
		if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
			l = (ArrayList)(f.isOUTSIDE_PROXY() ? outs.get(f) : atoms.get(f));
		} else {
			l = dataAtoms;
		}
		if (a.index == l.size() - 1) {
			l.remove(l.size() - 1);
		} else {
			Atom t = (Atom)l.remove(l.size() - 1);
			l.set(a.index, t);
			t.index = a.index;
		}
		a.index = -1;
		size--;
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
	/** 全ての要素を除去する */
	protected void clear() {
		atoms.clear();
		dataAtoms.clear();
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
	
	/** dataAtoms 中の特定のファンクタだけを返す反復子 */
	private class DataAtomIterator implements Iterator {
		private Iterator it;
		private Atom next;
		private Functor functor;
		DataAtomIterator(Functor functor) {
			this.functor = functor;
			it = dataAtoms.iterator();
			setNext();
		}
		private void setNext() {
			while (true) {
				if (!it.hasNext()) {
					next = null;
					break;
				}
				next = (Atom)it.next();
				if (next.getFunctor().equals(functor)) {
					break;
				}
			}
		}
		public boolean hasNext() {
			return next != null;
		}
		public Object next() {
			Atom ret = next;
			setNext();
			return ret;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/////////////////////////////////
	// non deterministic LMNtal
	private HashSet checked1 = new HashSet(), checked2 = new HashSet();
	public boolean equals(Object o) {
		AtomSet s = (AtomSet)o;
		ArrayList funcs = getCanonicalFunctorList();
		if (funcs.size() != s.getCanonicalFunctorList().size()) return false;
		Collections.sort(funcs, sizeComparator);
		checked1.clear();
		checked2.clear();
		for (int i = 0; i < funcs.size(); i++) {
			Functor f = (Functor)funcs.get(i);
			ArrayList l1 = (ArrayList)atoms.get(f);
			ArrayList l2 = (ArrayList)s.atoms.get(f);
			if (l2 == null || l1.size() != l2.size()) return false;
			if (!check(l1, l2)) return false;
		}
		return check(dataAtoms, s.dataAtoms);
	}
	private boolean check(ArrayList l1, ArrayList l2) {
		for (int j = 0; j < l1.size(); j++) {
			if (checked1.contains(l1.get(j))) continue;
			boolean flg = false;
			for (int k = 0; k < l2.size(); k++) {
				if (checked2.contains(l2.get(k))) continue;
				HashMap map = new HashMap();
				if (compare((Atom)l1.get(j), (Atom)l2.get(k), map)) {
					flg = true;
					checked1.addAll(map.keySet());
					checked2.addAll(map.values());
					break;
				}
			}
			if (!flg) return false;
		}
		return true;
	}
	private ArrayList getCanonicalFunctorList() {
		ArrayList list = new ArrayList();
		Iterator it = atoms.keySet().iterator();
		while (it.hasNext()) {
			Functor f = (Functor)it.next();
			if (((ArrayList)atoms.get(f)).size() > 0)
				list.add(f);
		}
		return list;
	}
	private final Comparator sizeComparator = new Comparator() {
		public int compare(Object f0, Object f1) {
			return ((ArrayList)atoms.get(f0)).size() - ((ArrayList)atoms.get(f1)).size();
		}
	};
	private boolean compare(Atom a1, Atom a2, HashMap map) {
		if (!a1.getFunctor().equals(a2.getFunctor()))
			return false;
		if (map.containsKey(a1)) {
			return a2 == map.get(a1);
		}
		if (checked1.contains(a1) || checked2.contains(a2)) throw new RuntimeException();
		map.put(a1, a2);
		for (int i = 0; i < a1.getArity(); i++) {
			if (!compare(a1.nthAtom(i), a2.nthAtom(i), map))
				return false;
		}
		return true;
	}
	public int hashCode() {
		int ret = 0;
		Iterator it = atoms.values().iterator();
		while (it.hasNext()) {
			ArrayList l = (ArrayList)it.next();
			for (int i = 0; i < l.size(); i++) {
				Atom a = (Atom)l.get(i);
				int t = a.getFunctor().hashCode();
				for (int j = 0; j < a.getArity(); j++) {
					ret += t * a.nthAtom(j).getFunctor().hashCode();
				}
			}
		}
		return ret;
	}
}

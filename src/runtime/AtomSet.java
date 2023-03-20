package runtime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import runtime.functor.Functor;
import runtime.functor.SpecialFunctor;
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
public final class AtomSet implements Iterable<Atom> {

  /** atoms内のアトムの数。整合性要注意 */
  private int size = 0;
  /** 実際にアトムの集合を管理している変数 */
  private Map<Functor, List<Atom>> atoms = null;
  /** メモリ利用量削減のため、データアトムはまとめて管理 */
  private List<Atom> dataAtoms = null;
  /** OUTSIDE_PROXYの集合を管理している変数 */
  private Map<Functor, List<Atom>> outs = null;

  private List<Atom> startAtoms = null;

  /** アトム数の取得 */
  public int size() {
    return size;
  }

  /** 自由リンク管理アトム以外のアトムの数の取得 */
  public int getNormalAtomCount() {
    return size - getAtomCountOfFunctor(Functor.INSIDE_PROXY) - getOutCount();
  }

  /** 指定されたFunctorを持つアトムの数の取得 */
  public int getAtomCountOfFunctor(Functor f) {
    if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
      List<Atom> l = (f.isOutsideProxy() ? getOuts().get(f) : getAtoms().get(f));
      if (l == null) {
        return 0;
      } else {
        return l.size();
      }
    } else {
      Iterator<Atom> it = new DataAtomIterator(f);
      int size = 0;
      while (it.hasNext()) {
        size++;
      }
      return size;
    }
  }

  /** OUTSIDE_PROXYの数の取得 */
  public int getOutCount() {
    Iterator<List<Atom>> i = getOuts().values().iterator();
    int k = 0;
    while (i.hasNext()) {
      k += i.next().size();
    }
    return k;
  }

  /** 空かどうかを返す */
  public boolean isEmpty() {
    return size == 0;
  }

  /** この集合内にあるア	トムの反復子を返す */
  @SuppressWarnings("unchecked")
  public Iterator<Atom> iterator() {
    return new NestedIterator<Atom>(
        new Iterator[] {
          getDataAtoms().iterator(),
          new MultiMapIterator<>(getOuts()),
          new MultiMapIterator<>(getAtoms()),
        });
  }

  /** 与えられた名前を持つアトムの反復子を返す */
  public Iterator<Atom> iteratorOfFunctor(Functor f) {
    if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
      List<Atom> l = (f.isOutsideProxy() ? getOuts().get(f) : getAtoms().get(f));
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
  public Iterator<Atom> iteratorOfOUTSIDE_PROXY() {
    return new MultiMapIterator<>(getOuts());
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
  public Iterator<Functor> activeFunctorIterator() {
    return getAtoms().keySet().iterator();
  }

  /**
   * この集合内の全てのアトムか格納されている配列を返す。
   * 返される配列の実行時の型はAtom[]です。
   */
  public Object[] toArray() {
    Object[] ret = new Atom[size];
    int index = 0;
    Iterator<Atom> it = iterator();
    while (it.hasNext()) {
      ret[index++] = it.next();
    }
    if (index != size) {
      Util.errPrintln("SYSTEM ERROR!: AtomSet.size is incorrect");
    }
    return ret;
  }

  /**
   * この集合内の全てのアトムか格納されている配列を返す。
   * 返される配列の実行時の型は引数に渡された配列の実行時の型と同じです。
   */
  public Object[] toArray(Object[] a) {
    if (a.length < size) {
      a = (Object[]) Array.newInstance(a.getClass().getComponentType(), size);
    }
    int index = 0;
    Iterator<Atom> it = iterator();
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
  public boolean add(Atom atom) {
    Functor f = atom.getFunctor();
    List<Atom> l;
    if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
      Map<Functor, List<Atom>> map = f.isOutsideProxy() ? getOuts() : getAtoms();
      l = map.get(f);
      if (l == null) {
        l = new ArrayList<>();
        map.put(f, l);
      }
    } else {
      l = getDataAtoms();
    }
    l.add(atom);
    atom.index = l.size() - 1;
    size++;
    return true;
  }

  /**
   * 指定されたアトムがあれば除去する。
   * @return この集合が変更された場合はtrue
   */
  public boolean remove(Atom atom) {
    Atom a = atom;
    Functor f = a.getFunctor();
    List<Atom> l;

    if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
      l = f.isOutsideProxy() ? getOuts().get(f) : getAtoms().get(f);
    } else {
      l = getDataAtoms();
    }
    if (a.index == l.size() - 1) {
      l.remove(l.size() - 1);
      if (l.isEmpty()) {
        l = null;
      }
    } else {
      Atom t = l.remove(l.size() - 1);
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
  public boolean addAll(Collection<Atom> c) {
    boolean ret = false;
    Iterator<Atom> it = c.iterator();
    while (it.hasNext()) {
      if (add(it.next())) {
        ret = true;
        // addメソッドの中でsizeは変更している
      }
    }
    return ret;
  }

  /**
   * 指定されたコレクション内の全ての要素をこの集合から除去する。
   * <p>現在は効率の悪い実装をしているので、頻繁に使うなら変更する必要がある。
   * @return この集合が変更された場合はtrue
   */
  public boolean removeAll(Collection<Atom> c) {
    boolean ret = false;
    Iterator<Atom> it = c.iterator();
    while (it.hasNext()) {
      ret |= remove(it.next());
      // removeメソッドの中でsizeは変更している
    }
    return ret;
  }

  /** 全ての要素を除去する */
  protected void clear() {
    atoms = null;
    dataAtoms = null;
    outs = null;
    size = 0;
  }

  /**デバッグ用出力*/
  public void print() {
    Util.println("AtomSet: ");
    Iterator<Atom> it = iterator();
    while (it.hasNext()) {
      Util.println(it.next());
    }
  }

  /** dataAtoms 中の特定のファンクタだけを返す反復子 */
  private class DataAtomIterator implements Iterator<Atom> {

    private Iterator<Atom> it;
    private Atom next;
    private Functor functor;

    DataAtomIterator(Functor functor) {
      this.functor = functor;
      it = getDataAtoms().iterator();
      setNext();
    }

    private void setNext() {
      while (true) {
        if (!it.hasNext()) {
          next = null;
          break;
        }
        next = it.next();
        if (next.getFunctor().equals(functor)) {
          break;
        }
      }
    }

    public boolean hasNext() {
      return next != null;
    }

    public Atom next() {
      Atom ret = next;
      setNext();
      return ret;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public void gc() {
    Iterator<Functor> it = getAtoms().keySet().iterator();
    while (it.hasNext()) {
      Functor f = it.next();
      if (atoms.get(f).size() == 0) it.remove();
    }
  }

  /**
   * このアトムセットがもう変更されない事を宣言する。
   * アトムセット間の比較を行う際の前準備と、ハッシュコードの計算を行う。
   */
  public void freeze() {
    gc();
    /////////////////////////////////
    // non deterministic LMNtal
    Comparator<Functor> sizeComparator =
        new Comparator<Functor>() {
          public int compare(Functor f0, Functor f1) {
            return getAtoms().get(f0).size() - atoms.get(f1).size();
          }
        };
    // 比較のための準備
    List<Functor> funcs = new ArrayList<>();
    funcs.addAll(getAtoms().keySet());
    Collections.sort(funcs, sizeComparator);
    HashSet<Atom> checked = new HashSet<>();
    startAtoms = new ArrayList<>();
    for (int i = 0; i < funcs.size(); i++) {
      searchAtomGroup(atoms.get(funcs.get(i)), checked);
    }
    searchAtomGroup(getDataAtoms(), checked);

    calcHashCode();
  }

  private void searchAtomGroup(List<Atom> name, HashSet<Atom> checked) {
    for (int j = 0; j < name.size(); j++) {
      Atom a = name.get(j);
      if (!checked.contains(a)) {
        searchAtomGroup(a, checked);
        startAtoms.add(a);
      }
    }
  }

  private void searchAtomGroup(Atom a, HashSet<Atom> checked) {
    checked.add(a);
    for (int i = 0; i < a.getArity(); i++) {
      Atom a2 = a.nthAtom(i);
      if (!checked.contains(a2)) searchAtomGroup(a2, checked);
    }
  }

  public boolean equals(Object o) {
    AtomSet s = (AtomSet) o;
    if (size() != s.size()) return false;
    if (getAtoms().size() != s.atoms.size()) return false;
    if (getDataAtoms().size() != s.dataAtoms.size()) return false;

    HashSet<Atom> checked2 = new HashSet<>();
    HashMap<Atom, Atom> map = new HashMap<>();
    for (int i = 0; i < startAtoms.size(); i++) {
      Atom a1 = (Atom) startAtoms.get(i);
      Functor f = a1.getFunctor();
      List<Atom> l2;
      if (!Env.fMemory || f.isSymbol() || f instanceof SpecialFunctor) {
        l2 = s.atoms.get(f);
        if (l2 == null || atoms.get(f).size() != l2.size()) return false;
      } else {
        l2 = s.dataAtoms;
      }
      boolean flg = false;
      for (int j = 0; j < l2.size(); j++) {
        if (checked2.contains(l2.get(j))) continue;
        map.clear();
        if (compare(a1, l2.get(j), map, checked2)) {
          flg = true;
          checked2.addAll(map.values());
          break;
        }
      }
      if (!flg) return false;
    }
    return true;
  }

  private boolean compare(Atom a1, Atom a2, HashMap<Atom, Atom> map, HashSet<Atom> checked2) {
    if (!a1.getFunctor().equals(a2.getFunctor())) return false;
    if (map.containsKey(a1)) {
      return a2 == map.get(a1);
    }
    if (checked2.contains(a2)) throw new RuntimeException();
    map.put(a1, a2);
    for (int i = 0; i < a1.getArity(); i++) {
      if (!compare(a1.nthAtom(i), a2.nthAtom(i), map, checked2)) return false;
    }
    return true;
  }

  /*--------------------ハッシュコード--------------*/
  private int hashCode;

  private void calcHashCode() {
    hashCode = 0;
    Iterator<List<Atom>> it = getAtoms().values().iterator();
    while (it.hasNext()) {
      List<Atom> l = it.next();
      for (int i = 0; i < l.size(); i++) {
        Atom a = l.get(i);
        int t = a.getFunctor().hashCode();
        for (int j = 0; j < a.getArity(); j++) {
          t = t * 31 + a.nthAtom(j).getFunctor().hashCode();
        }
        hashCode += t;
      }
    }
  }

  public int hashCode() {
    return hashCode;
  }

  private Map<Functor, List<Atom>> getOuts() {
    if (null == outs) {
      outs = new HashMap<>();
    }
    return outs;
  }

  private Map<Functor, List<Atom>> getAtoms() {
    if (null == atoms) {
      atoms = new HashMap<>();
    }
    return atoms;
  }

  private List<Atom> getDataAtoms() {
    if (null == dataAtoms) {
      dataAtoms = new ArrayList<>();
    }
    return dataAtoms;
  }
}

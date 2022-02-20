package runtime;

import runtime.functor.Functor;
import util.QueuedEntity;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
public final class Atom extends QueuedEntity {

  /** 所属膜。AbstractMembraneとそのサブクラスが変更してよい。
   * ただし値を変更するときはindexも同時に更新すること。(mem,index)==(null, -1)は所属膜なしを表す。
   */
  Membrane mem;

  /** 所属膜のAtomSet内でのインデックス */
  public int index = -1;

  public int getid() {
    return id;
  }

  /** ファンクタ（名前とリンク数） */
  private Functor functor;

  /** リンク */
  Link[] args;

  private static int lastId = 0;

  /** このアトムのローカルID */
  int id;

  /**
   * 指定された名前とリンク数を持つアトムを作成する。
   * AbstractMembraneのnewAtomメソッド内で呼ばれる。
   * @param mem 所属膜
   */
  public Atom(Membrane mem, Functor functor) {
    this.mem = mem;
    this.functor = functor;
    if (functor.getArity() > 0) args = new Link[functor.getArity()]; else args =
      null;
    id = lastId++;
  }

  public void setFunctor(Functor newFunctor) {
    if (args == null) {
      if (newFunctor.getArity() != 0) {
        throw new RuntimeException(
          "SYSTEM ERROR: insufficient link vector length"
        );
      }
    } else if (newFunctor.getArity() > args.length) {
      throw new RuntimeException(
        "SYSTEM ERROR: insufficient link vector length"
      );
    }
    functor = newFunctor;
  }

  /** ファンクタ名を設定する。 */
  //public void setName(String name)
  //{
  //setFunctor(name, getFunctor().getArity());
  //}

  /** ファンクタを設定する。
   * AtomSetを更新するため、膜のalterAtomFunctorメソッドを呼ぶ。*/
  //public void setFunctor(String name, int arity)
  //{
  //mem.alterAtomFunctor(this, new SymbolFunctor(name, arity));
  //}

  public String toString() {
    return functor.getName();
  }

  /**
   * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
   * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
   */
  public int hashCode() {
    return id;
  }

  /** このアトムのローカルIDを取得する */
  String getLocalID() {
    return Integer.toString(id);
  }

  /** 所属膜の取得 */
  public Membrane getMem() {
    return mem;
  }

  /** ファンクタを取得 */
  public Functor getFunctor() {
    return functor;
  }

  /** 名前を取得 */
  public String getName() {
    return functor.getName();
  }

  /** リンク数を取得 */
  public int getArity() {
    return functor.getArity();
  }

  /** 最終引数を取得 */
  public Link getLastArg() {
    return args[getArity() - 1];
  }

  /** 第pos引数に格納されたリンクオブジェクトを取得 */
  public Link getArg(int pos) {
    return args[pos];
  }

  /** 第 n 引数につながってるアトムの名前を取得する */
  public String nth(int n) {
    return nthAtom(n).getFunctor().getName();
  }

  /** 第 n 引数につながってるアトムを取得する */
  public Atom nthAtom(int n) {
    return args[n].getAtom();
  }

  /**
   * プロキシを飛ばした実際の隣のアトムを取得する
   * @param index
   * @return
   */
  public Atom getNthAtom(int index) {
    if (null == args[index]) {
      return null;
    }
    Atom a = nthAtom(index);
    while (a.getFunctor().isInsideProxy() || a.getFunctor().isOutsideProxy()) {
      a = a.nthAtom(0).nthAtom(1);
    }
    return a;
  }

  public int getEdgeCount() {
    return functor.getArity();
  }
}

package runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import runtime.functor.Functor;
import util.QueuedEntity;
import util.Stack;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * todo （効率改善） 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * <p><b>排他制御</b></p>
 * <p>lockThread フィールドへの代入（すなわちロックの取得・解放）と、ロック解放の待ち合わせのために
 * このクラスのインスタンスに関する synchronized 節を利用する。
 * フィールドへの代入をする時は、特に記述がない限りこの膜のロックを取得している必要がある。
 * @author Mizuno, n-kato
 */
public final class Membrane extends QueuedEntity {

  /** 親膜。リモートにあるならばRemoteMembraneオブジェクトを参照する。GlobalRootならばnull。
   * 修正するときは、親膜のロックを取得している必要がある。
   * null を代入する（=この膜を除去する）時は、この膜と親膜の両方のロックを取得している必要がある。 */
  protected Membrane parent;
  /** アトムの集合 */
  protected AtomSet atoms = new AtomSet();
  /** 子膜の集合 */
  protected Set<Membrane> mems = null;
  //	/** このセルの自由リンクの数 */
  //	protected int freeLinkCount = 0;
  /** ルールセットの集合。 */
  protected List<Ruleset> rulesets = new ArrayList<>();
  /** 膜のタイプ */
  protected int kind = 0;
  public static final int KIND_ND = 2;
  /** trueならばこの膜以下に適用できるルールが無い */
  protected boolean stable = false;
  /** 永続フラグ（trueならばルール適用できなくてもstableにならない）*/
  public boolean perpetual = false;
  /** この膜をロックしているスレッド。ロックされていないときはnullが入っている。*/
  protected Thread lockThread = null;

  private static int nextId = 0;
  private int id;

  /** 子膜->(コピー元のアトムin子膜->コピー先のアトムinコピーされた子膜)
   * TODO 膜のメンバ変数でいいのかどうか */
  HashMap<Membrane, Map<Atom, Atom>> memToCopyMap = null;

  /** この膜の名前（internされた文字列またはnull） */
  String name = null;

  public boolean equalName(String s) {
    if (name == null && s == null) return true; else if (
      name != null && name != null
    ) return name.equals(s); else return false;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  } // 仕様が固まったらコンストラクタで渡すようにすべきかも

  /** 実行アトムスタック。
   * 操作する際にこの膜のロックを取得している必要はない。
   * 排他制御には、Stack インスタンスに関する synchronized 節を利用している。 */
  private Stack ready = null;

  //	/** リモートホストとの通信でこの膜のアトムを同定するときに使用されるatomidの表。
  //	* <p>atomid (String) -> Atom
  //	* <p>この膜のキャッシュ送信後、この膜の連続するロック期間中のみ有効。
  //	* キャッシュ送信時に初期化され、引き続くリモートホストからの要求を解釈するために使用される。
  //	* リモートホストからの要求で新しくアトムが作成されると、受信したNEW_をキーとするエントリが追加される。
  //	* $inside_proxyアトムの場合、命令ブロックの返答のタイミングでローカルIDで上書きされる。
  //	* $inside_proxy以外のアトムの場合、ロック解除までNEW_のまま放置される。
  //	* @see Atom.remoteid */
  //	protected HashMap atomTable = null;

  ///////////////////////////////
  // コンストラクタ

  /** 指定されたタスクに所属する膜を作成する。newMem/newRoot から呼ばれる。*/
  private Membrane(Membrane parent) {
    mems = new HashSet<>();

    this.parent = parent;
    id = nextId++;
  }

  /** 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。*/
  protected Membrane() {
    this(null);
  }

  /** この膜のグローバルIDを取得する */
  public String getMemID() {
    return Integer.toString(id);
  }

  //	/** この膜が所属する計算ノードにおける、この膜の指定されたアトムのIDを取得する */
  //	public String getAtomID(Atom atom) { return atom.getLocalID(); }

  ///////////////////////////////
  // 情報の取得

  /**
   * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
   * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
   */
  public int hashCode() {
    return id;
  }

  // 061028 okabe ランタイムは唯一つなのでGlobal/Local の区別は必要ない
  //	/** この膜のローカルIDを取得する */
  //	public String getLocalID() {  //publicなのはLMNtalDaemonから呼んでいるから→呼ばなくなったのでprotectedでよい
  //	return Integer.toString(id);
  //	}

  /** 親膜の取得 */
  public Membrane getParent() {
    return parent;
  }

  /** AtomSetを配列形式で取得 */
  // 使ってる場所がないのでコメントアウト
  //	public Atom[] getAtomSet() {
  //	return (Atom[])atoms.toArray();
  //	}
  /** 060727 */
  /** ルールセット数を取得 */
  public int getRulesetCount() {
    return rulesets.size();
  }

  public int getMemCount() {
    return mems.size();
  }

  /** proxy以外のアトムの数を取得
   * todo この名前でいいのかどうか */
  public int getAtomCount() {
    return atoms.getNormalAtomCount();
  }

  /** 指定されたファンクタをもつアトムの数を取得*/
  public int getAtomCountOfFunctor(Functor functor) {
    return atoms.getAtomCountOfFunctor(functor);
  }

  /** このセルの自由リンクの数を取得 */
  public int getFreeLinkCount() {
    return atoms.getAtomCountOfFunctor(Functor.INSIDE_PROXY);
  }

  /** 膜のタイプを取得 */
  public int getKind() {
    return kind;
  }

  /** 膜のタイプを変更 */
  public void changeKind(int k) {
    kind = k;
    if (kind == KIND_ND) {
      //非決定的実行膜は、普通の方法では実行しない
      dequeue();
      toStable();
    }
  }

  /** この膜とその子孫に適用できるルールがない場合にtrue */
  public boolean isStable() {
    return stable;
  }

  /** stableフラグをONにする 10/26矢島 Task#exec()内で使う*/
  void toStable() {
    stable = true;
  }

  /** 永続フラグをONにする */
  public void makePerpetual(boolean f) {
    perpetual = f;
  }

  //	/** 永続フラグをOFFにする */
  //	public void makeNotPerpetual() {
  //	AbstractLMNtalRuntime machine = getTask().getMachine();
  //	synchronized(machine) {
  //	perpetual = false;
  //	machine.notify();
  //	}
  //	}
  /** この膜にルールがあればtrue */
  public boolean hasRules() {
    return !rulesets.isEmpty();
  }

  public boolean isNondeterministic() {
    return kind == KIND_ND;
  }

  // 反復子

  public Object[] getAtomArray() {
    return atoms.toArray();
  }

  public Object[] getMemArray() {
    if (mems.isEmpty()) {
      return null;
    }
    return mems.toArray();
  }

  /** 06/07/27 */
  /** ルールセットのコピーを取得 */
  public ArrayList<Ruleset> getRuleset() {
    ArrayList<Ruleset> al = new ArrayList<>(rulesets);
    return al;
  }

  /** 子膜のコピーを取得 */
  public HashSet<Membrane> getMemCopy() {
    return new HashSet<>(mems);
    //RandomSet s = new RandomSet();
    //s.addAll(mems);
    //return s;
  }

  /** この膜にあるアトムの反復子を取得する */
  public Iterator<Atom> atomIterator() {
    return atoms.iterator();
  }

  /** この膜にある子膜の反復子を取得する */
  public Iterator<Membrane> memIterator() {
    return mems.iterator();
  }

  /** 名前funcを持つアトムの反復子を取得する */
  public Iterator<Atom> atomIteratorOfFunctor(Functor functor) {
    return atoms.iteratorOfFunctor(functor);
  }

  /** この膜にあるルールセットの反復子を返す */
  public Iterator<Ruleset> rulesetIterator() {
    return rulesets.iterator();
  }

  AtomSet getAtoms() {
    return atoms;
  }

  /** 新しいタイプがkの子膜を作成し、活性化する */
  public Membrane newMem(int k) {
    //		if (remote != null) {
    //		remote.send("NEWMEM",this);
    //		return null; // todo
    //	}
    Membrane m = new Membrane(this);
    m.changeKind(k);
    mems.add(m);
    // 親膜と同じ実行膜スタックに積む
    if (k != KIND_ND) stack.push(m);
    return m;
  }

  /** 新しいデフォルトタイプの子膜を作成し、活性化する */
  public Membrane newMem() {
    return newMem(0);
  }

  /** デバッグ用 */
  String getReadyStackStatus() {
    return ready.toString();
  }

  /** 実行アトムスタックの先頭のアトムを取得し、実行アトムスタックから除去 */
  Atom popReadyAtom() {
    if (null == ready) {
      return null;
    }
    Atom atom = (Atom) ready.pop();
    if (ready.isEmpty()) {
      ready = null;
    }
    return atom;
  }

  // hash ここから

  /* 膜のハッシュコードを返す */
  static int calculate(Membrane m) {
    return calculate(m, new HashMap<>());
  }

  /*
   * 膜のハッシュコードを返す
   * @param m ハッシュコード算出対象の膜
   * @param m2hc mの子膜からハッシュコードへのマップ。子膜のハッシュコード算出を繰り返さないために使う。
   */
  private static int calculate(Membrane m, Map<Membrane, Integer> m2hc) {
    //System.out.println("membrane:" + m);
    final long MAX_VALUE = Integer.MAX_VALUE;
    /*
     * add: m内の分子のハッシュコードが加算されていく変数
     * mult: m内の分子のハッシュコードが乗算されていく変数
     */
    long add = 3412; // 3412は適当な初期値
    long mult = 3412;

    /* 一時変数 */
    Atom a = null;
    Membrane mm = null;
    QueuedEntity q = null;

    /*
     * contents:この膜内のアトムと子膜全体の集合
     * toCalculate:現在計算中の分子内の未処理アトムまたは子膜の集合
     * calculated:現在計算中の分子内の処理済アトムまたは子膜の集合
     */
    Set<QueuedEntity> contents = new HashSet<>(), toCalculate = new HashSet<>(), calculated = new HashSet<>();

    for (Iterator<Atom> i = m.atomIterator(); i.hasNext();) {
      a = i.next();
      if (a.getFunctor().isOutsideProxy() || a.getFunctor().isInsideProxy()) {
        continue;
      }
      contents.add(a);
    }

    for (Iterator<Membrane> i = m.memIterator(); i.hasNext();) {
      mm = i.next();
      contents.add(mm);
      m2hc.put(mm, calculate(mm, m2hc));
    }

    while (!contents.isEmpty()) {
      //System.out.println("uncalculated:" + contents);
      q = contents.iterator().next();
      contents.remove(q);

      /*
       * mol: この分子のハッシュコードを保持する
       * mol_add: 基本計算単位のハッシュコードが加算されていく変数
       * mol_mult: 基本計算単位のハッシュコードが乗算されていく変数
       * temp: 基本計算単位のハッシュコードを保持する
       */

      long mol = -1, mol_add = 0, mol_mult = 41, temp = 0;

      toCalculate.clear();
      calculated.clear();
      toCalculate.add(q);

      // 分子のハッシュコードの計算
      while (!toCalculate.isEmpty()) {
        q = toCalculate.iterator().next();
        calculated.add(q);
        toCalculate.remove(q);

        if (q instanceof Atom) {
          a = (Atom) q;
          temp = a.getFunctor().hashCode();

          // このアトムのリンクを処理する
          int arity = a.getFunctor().getArity();
          for (int k = 0; k < arity; k++) {
            temp *= 31;
            Link link = a.getArg(k);
            if (link.getAtom().getFunctor().isInsideProxy()) {
              Atom inside = link.getAtom();
              int pos = link.getPos() + 1;
              temp += (inside.getFunctor().hashCode() * pos);
            } else if (link.getAtom().getFunctor().isOutsideProxy()) { // リンク先が子膜の場合
              /*
               * リンク先アトムに至るまで貫いた子膜のハッシュコードと
               * 最終的なリンク先アトムのハッシュコードと
               * そのアトムの引数番号の組を
               * このアトムから子膜へのリンクを表現する項とする。
               */
              int t = 0;
              mm = link.getAtom().nthAtom(0).getMem();
              if (!calculated.contains(mm)) {
                toCalculate.add(mm);
              }
              while (link.getAtom().getFunctor().isOutsideProxy()) {
                link = link.getAtom().nthAtom(0).getArg(1);
                mm = link.getAtom().getMem();
                t += m2hc.get(mm);
                t *= 13;
              }

              t *= link.getAtom().getFunctor().hashCode();
              t *= link.getPos() + 1;
              temp += t;
            } else { // リンク先がプロキシ以外のアトムの場合
              Atom linked = link.getAtom();
              if (!calculated.contains(linked)) {
                toCalculate.add(linked);
              }
              int pos = link.getPos() + 1;
              // 接続先の引数番号をハッシュコードに関与させる
              temp += (linked.getFunctor().hashCode() * pos);
            }
          }
        } else {
          Membrane mt = (Membrane) q;
          final int thisMembsHC = m2hc.get(mt);
          temp = thisMembsHC;

          // この膜から膜の外部へのリンクを処理する
          Link link = null;
          for (
            Iterator<Atom> i = mt.atomIteratorOfFunctor(Functor.INSIDE_PROXY);
            i.hasNext();
          ) {
            Atom inside = i.next();
            // この膜外部の（プロキシでない）リンク先アトムまでトレース
            int s = 0;
            link = inside.nthAtom(0).getArg(1);

            if (link.getAtom().getFunctor().isOutsideProxy()) { // この膜のリンク先が膜のとき
              mm = link.getAtom().nthAtom(0).getMem();
              if (!calculated.contains(mm)) {
                toCalculate.add(mm);
              }
            } else { // この膜のリンク先がアトムの場合
              a = link.getAtom();
              if (!calculated.contains(a)) {
                toCalculate.add(a);
              }
            }

            while (link.getAtom().getFunctor().isOutsideProxy()) {
              link = link.getAtom().nthAtom(0).getArg(1);
              s += m2hc.get(link.getAtom().getMem());
              s *= 13;
            }
            s += link.getAtom().getFunctor().hashCode();
            s *= link.getPos() + 1;

            // この膜内部の（プロキシでない）リンク元アトムまでトレース
            int t = 0;
            link = inside.getArg(1);
            while (link.getAtom().getFunctor().isOutsideProxy()) {
              link = link.getAtom().nthAtom(0).getArg(1);
              t += m2hc.get(link.getAtom().getMem());
              t *= 13;
            }
            t *= link.getAtom().getFunctor().hashCode();
            t *= link.getPos() + 1;
            temp += thisMembsHC ^ t * s;
          }
        }

        mol_add += temp;
        mol_add %= MAX_VALUE;
        mol_mult *= temp;
        mol_mult %= MAX_VALUE;
      }
      mol = mol_add ^ mol_mult;
      //System.out.println("molecule: " + calculated + " = " + mol);
      /* ハッシュコードを算出した分子を計算対象から取り除く */
      contents.removeAll(calculated);

      add += mol;
      add %= MAX_VALUE;
      mult *= mol;
      mult %= MAX_VALUE;
    }

    //System.out.println("membrane:" + m + " = " + (mult^add) + " (mult=" + mult + ", add=" + add + ")");
    return (int) (mult ^ add);
  }
}

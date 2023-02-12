package compile.structure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import runtime.Env;
import runtime.InterpretedRuleset;
import runtime.Ruleset;
import runtime.functor.Functor;

/**
 * ソースコード中の膜の構造を表すクラス<br>
 * memo:全て1つの配列に入れる方法もある。<br>
 * 各要素はListとして保持される。
 */
public final class Membrane {

  /** 親膜 */
  public Membrane parent = null;

  /** 終了フラグがセットされているかどうかを表す */
  public boolean stable = false;

  /** 膜のタイプ */
  public int kind = 0;

  /** ＠指定またはnull
   * <p><b>仮仕様</b>
   * ホスト指定を表す文字列が入る型付きプロセス文脈名を持った
   * 所属膜を持たないプロセスコンテキストが代入される。
   * <br>[要注意]例外的に、引数の長さおよびbundleは0にセットされる。
   * @see ContextDef#lhsMem */
  public ProcessContext pragmaAtHost = null;
  //	/** システムルールセットとして使うなら真 */
  //	public boolean is_system_ruleset = false;

  /** 膜名 */
  public String name;

  /** アトム(compile.structure.Atom)のリスト */
  public List<Atom> atoms = new LinkedList<>();

  /** 子膜(compile.structure.Membrane)のリスト */
  public List<Membrane> mems = new LinkedList<>();

  /** ルール(compile.structure.RuleStructure)のリスト */
  public List<RuleStructure> rules = new LinkedList<>();

  /** 型定義(compile.structure.TypeDefStructure)のリスト */
  public List<TypeDefStructure> typeDefs = new LinkedList<>();

  ////////////////////////////////////////////////////////////////

  /** アトム集団(compile.structure.Atom)のリスト */
  public List<Atom> aggregates = new LinkedList<>();

  /** プロセス文脈出現(compile.structure.ProcessContext)のリスト */
  public List<ProcessContext> processContexts = new LinkedList<>();

  /** ルール文脈出現(compile.structure.RuleContext)のリスト */
  public List<RuleContext> ruleContexts = new LinkedList<>();

  /** 型付きプロセス文脈出現(compile.structure.ProcessContext)のリスト */
  public List<ProcessContext> typedProcessContexts = new LinkedList<>();

  ////////////////////////////////////////////////////////////////

  /** 膜の自由リンク名(String)からそのリンク出現(compile.structure.LinkOccurrence)への写像 */
  public HashMap<String, LinkOccurrence> freeLinks = new HashMap<>();

  /** ルールセット。生成されたルールオブジェクトは逐次ここに追加されていく。*/
  //	public runtime.Ruleset ruleset = new InterpretedRuleset();
  public List<Ruleset> rulesets = new LinkedList<>();

  ////////////////////////////////////////////////////////////////

  /** コンパイラ用データ構造。atomやmemをGROUP化されるべきものについて近くに配置する。 **/
  private RependenceGraph rg = new RependenceGraph();

  ////////////////////////////////////////////////////////////////

  /**
   * コンストラクタ
   * @param mem 親膜
   */
  public Membrane(Membrane mem) {
    this.parent = mem;
  }

  public int getNormalAtomCount() {
    int c = 0;
    for (Atom a : atoms) {
      if (!a.functor.isInsideProxy() && !a.functor.isOutsideProxy()) c++;
    }
    return c;
  }

  /** この膜にあるinside_proxyアトムの個数を取得する */
  public int getFreeLinkAtomCount() {
    int c = 0;
    for (Atom a : atoms) {
      if (a.functor.equals(Functor.INSIDE_PROXY)) c++;
    }
    return c;
  }

  /**
   * {} なしで出力する。
   *
   * ルールの出力の際、{} アリだと
   * (a:-b) が ({a}:-{b}) になっちゃうから。
   *
   * @return String
   */
  public String toStringWithoutBrace() {
    List<Object> list = new LinkedList<>();
    list.addAll(atoms);
    list.addAll(mems);
    list.addAll(rules);
    list.addAll(processContexts);
    list.addAll(ruleContexts);
    list.addAll(typedProcessContexts);
    // return list.toString().replaceAll("^.|.$","");
    return Env.parray(list, ", ");
  }

  public String toString() {
    String ret =
        "{ " + toStringWithoutBrace() + " }" + (kind == 1 ? "_" : "") + (stable ? "/" : "");
    if (pragmaAtHost != null) {
      ret += "@" + ((ProcessContext) pragmaAtHost).getQualifiedName();
    }
    return ret;
  }

  public String toStringAsGuardTypeConstraints() {
    if (atoms.isEmpty()) return "";
    String text = "";
    for (Atom atom : atoms) {
      text += " " + atom.toStringAsTypeConstraint();
    }
    return text.substring(1);
  }

  /**
   * この膜に含まれる全てのルールセットを表示する。
   */
  public void showAllRulesets() {
    for (Ruleset r : rulesets) {
      Env.d(r);
    }

    // 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
    for (RuleStructure rs : rules) {
      rs.leftMem.showAllRulesets();
      rs.rightMem.showAllRulesets();
    }

    // 子膜それぞれ
    for (Membrane mem : mems) {
      mem.showAllRulesets();
    }
  }

  /**
   * この膜の中にあるルールを全て表示する。
   *
   * <pre>
   * 「この膜の中にあるルール」とは、以下の３種類。
   * 　[1]この膜のルールセットに含まれる全てのルール
   * 　[2] [1]の左辺膜の中にあるルール
   * 　[3] [1]の右辺膜の中にあるルール
   * </pre>
   */
  public void showAllRules() {
    Env.c("Membrane.showAllRules " + this);
    for (Ruleset r : rulesets) {
      ((InterpretedRuleset) r).showDetail();
    }

    // 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
    for (RuleStructure rs : rules) {
      // Env.p("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
      rs.leftMem.showAllRules();
      // Env.p("About rule structure (RIGHT): "+rs.rightMem+" of "+rs);
      rs.rightMem.showAllRules();
    }

    // 子膜それぞれ
    for (Membrane submem : mems) {
      submem.showAllRules();
    }
  }

  /*
   * okabe
   * この膜に含まれるアトムのうち一番最初のアトムのアトム名を返す
   * ルールの左辺が膜の場合は再帰呼び出し
   * トレースモードで使用
   */
  public String getFirstAtomName() {
    Iterator<Atom> atomIt = atoms.iterator();
    Iterator<Membrane> memIt = mems.iterator();
    if (atomIt.hasNext()) {
      return atomIt.next().getName();
    } else if (memIt.hasNext()) {
      return memIt.next().getFirstAtomName();
    } else {
      // プロキシとかプロセス文脈のときはとりあえず放置
      return "null";
    }
  }

  /**
   * 与えられた膜の中身をこの膜に追加する。
   * @param m
   */
  public void add(Membrane m) {
    atoms.addAll(m.atoms);
    mems.addAll(m.mems);
    rules.addAll(m.rules);
    aggregates.addAll(m.aggregates);
    processContexts.addAll(m.processContexts);
    ruleContexts.addAll(m.ruleContexts);
    typedProcessContexts.addAll(m.typedProcessContexts);
    freeLinks.putAll(m.freeLinks);
    rulesets.addAll(m.rulesets);
  }

  public void connect(Object x, Object y) {
    //		System.out.println("connect "+x + "   " + y);
    rg.connect(x, y);
  }

  public void printfRG() {
    System.out.println(rg.toString());
  }

  public void createRG() {
    rg.addAll(atoms);
    rg.addAll(mems);
  }

  public Collection<List<Object>> allKnownElements() {
    return rg.allKnownElements();
  }
}

class RependenceGraph {

  private UnionFind uf;

  RependenceGraph() {
    uf = new UnionFind();
  }

  RependenceGraph(List<Atomic> atoms, List<Membrane> mems) {
    uf = new UnionFind();
    uf.addAll(atoms);
    uf.addAll(mems);
  }

  void addAll(List<? extends Object> x) {
    uf.addAll(x);
  }

  void connect(Object x, Object y) {
    uf.union(x, y);
  }

  private void reachable(Object x, Object y) {
    uf.areUnified(x, y);
  }

  public String toString() {
    return uf.allKnownElements().toString();
  }

  public Collection<List<Object>> allKnownElements() {
    return uf.allKnownElements();
  }

  private static class UnionFind {

    private Map<Object, Object> lnk = new HashMap<>();
    private Map<Object, Integer> lnkSiz = new HashMap<>();
    private Map<Object, List<Object>> lists = new HashMap<>();

    private void union(Object x, Object y) {
      if (!lnkSiz.containsKey(x)) add(x);
      if (!lnkSiz.containsKey(y)) add(y);
      Object tx = find(x);
      Object ty = find(y);
      Object temp = link_repr(tx, ty);
      List<Object> listx = lists.get(tx);
      List<Object> listy = lists.get(ty);
      if (temp == tx) {
        listx.addAll(listy);
        lists.remove(ty);
      } else if (temp == ty) {
        listy.addAll(listx);
        lists.remove(tx);
      }
    }

    public String toString() {
      return lists.toString();
    }

    private void add(Object x) {
      if (!lnkSiz.containsKey(x)) {
        lnkSiz.put(x, 1);
        List<Object> list = new LinkedList<>();
        list.add(x);
        lists.put(x, list);
      }
    }

    private void addAll(Collection<? extends Object> c) {
      for (Object o : c) add(o);
    }

    private boolean areUnified(Object x, Object y) {
      return find(x) == find(y);
    }

    public Collection<List<Object>> allKnownElements() {
      return lists.values();
    }

    private Object find(Object x) {
      // ここでPath圧縮すると計算量が nlog(n) から n ack^-1(n) に
      while (lnk.containsKey(x)) x = lnk.get(x);
      return x;
    }

    private Object link_repr(Object x, Object y) {
      if (x == y) return -1;

      // グループ化
      if (lnkSiz.get(x) < lnkSiz.get(y)) {
        lnk.put(x, y);
        lnkSiz.put(y, lnkSiz.get(y) + lnkSiz.get(x));
        return y;
      } else {
        lnk.put(y, x);
        lnkSiz.put(x, lnkSiz.get(x) + lnkSiz.get(y));
        return x;
      }
    }
  }
}

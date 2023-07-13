package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.LinkedList;
import java.util.List;

/**
 * ソース中のルールを表します
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
class SrcRule {

  public String name; // ルール名
  public int lineno; // 行番号 2006.1.22 by inui
  public LinkedList head; // ヘッドプロセス

  @JsonTypeInfo(use = Id.CLASS)
  public LinkedList body; // ボディプロセス

  @JsonTypeInfo(use = Id.CLASS)
  public LinkedList guard; // ガードプロセス

  @JsonTypeInfo(use = Id.CLASS)
  public LinkedList guardNegatives; // ガード否定条件構文のリスト

  private String text; // ルールのテキスト表現

  /**
   * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
   * @param head ヘッドのリスト
   * @param body ボディのリスト
   */
  public SrcRule(String name, LinkedList head, LinkedList body) {
    this(name, head, new LinkedList(), body);
  }

  // 2006.1.22 by inui
  /**
   * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
   * @param head ヘッドのリスト
   * @param body ボディのリスト
   * @param lineno 行番号
   */
  public SrcRule(String name, LinkedList head, LinkedList body, int lineno) {
    this(name, head, new LinkedList(), body);
    this.lineno = lineno;
  }

  /**
   * 指定されたヘッドルールとボディルールとガードでルールを初期化します
   * @param head ヘッドのリスト
   * @param gurad ガードのリスト
   * @param body ボディのリスト
   */
  public SrcRule(String name, LinkedList head, LinkedList guard, LinkedList body) {
    this.name = name;
    this.head = head;
    this.guard = guard;
    this.guardNegatives = new LinkedList();
    this.body = body;
    addHyperLinkConstraint(head, this.guard, body);
  }

  // 2006.1.22 by inui
  /**
   * 指定されたヘッドルールとボディルールとガードでルールを初期化します
   * @param head ヘッドのリスト
   * @param gurad ガードのリスト
   * @param body ボディのリスト
   * @param lineno 行番号
   */
  public SrcRule(String name, LinkedList head, LinkedList guard, LinkedList body, int lineno) {
    this(name, head, guard, body);
    this.lineno = lineno;
  }

  // 2006/07/07 by kudo
  /**
   * 指定されたヘッドルールとボディルールとガードルールで初期化する。
   * simpagation ruleの構文に対して使われるコンストラクタ。
   * @param head ヘッドのリスト
   * @param head2 ヘッドのリスト ( \ の後ろ )
   * @param guard ガードのリスト
   * @param body ボディのリスト
   * @param lineno 行番号
   */
  public SrcRule(
      String name, LinkedList head, List head2, LinkedList guard, LinkedList body, int lineno) {
    this.name = name;
    this.head = head;
    this.guard = (guard == null ? new LinkedList() : guard);
    this.guardNegatives = new LinkedList();
    this.body = body;
    if (head2 != null) {
      unSimpagationize(head2);
    }
    LinkedList head3 = this.head;
    LinkedList body2 = this.body;
    /* I から始まるアトムを int 型として扱う旧時代の機能
     * なぜか simpagation にだけ残っていたのでコメントアウト
     * addTypeConstraint(head3);
     */
    addHyperLinkConstraint(head3, this.guard, body2);
  }

  /**
   *	 Head部, Body部に!Xが出現した場合，Guard部にhlink(X)もしくはnew(X)を追加する.
   *	 meguro
   *       Head, Bodyに!X:Aが出現した場合，Guardにhlink(X,A)もしくはnew(X,A)を追加する.
   *       ただし!Xもしくは!X:Aが複数回出現する場合，初出の出現における属性の有無および
   *       属性値が採用される．
   *       つまり a(!H:1,!H:1,!H:1) と書いても a(!H:1,!H,!H) と書いても同じになる．
   *       a(!H:1,!H:2,!H:3) も同じになってしまうのは要改良．
   *       ueda
   */
  public void addHyperLinkConstraint(LinkedList head, LinkedList guard, LinkedList body) {
    LinkedList headhl = new LinkedList();
    addHyperLinkConstraintSub(head, guard, body, headhl);
  }

  private LinkedList addHyperLinkConstraintSub(
      LinkedList head, LinkedList guard, LinkedList body, LinkedList headhl) {
    if (head != null) {
      for (Object o : head) {
        if (o instanceof SrcHyperLink) {
          SrcHyperLink shl = (SrcHyperLink) o;
          LinkedList newl = new LinkedList();
          SrcLink name = new SrcLink(shl.name);
          SrcAtom attr = new SrcAtom(shl.attr);
          newl.add(name);
          if (!attr.getName().equals("")) {
            newl.add(attr);
          }
          headhl.add(name.toString());
          SrcAtom newg = new SrcAtom("hlink", newl);
          guard.add(newg);

        } else if (o instanceof SrcAtom) {
          SrcAtom sa = (SrcAtom) o;
          headhl = addHyperLinkConstraintSub(sa.process, guard, null, headhl);
        } else if (o instanceof SrcMembrane) {
          SrcMembrane sm = (SrcMembrane) o;
          headhl = addHyperLinkConstraintSub(sm.process, guard, null, headhl);
        }
      }
    }

    if (body != null) {
      for (Object o : body) {
        if (o instanceof SrcHyperLink) {
          SrcHyperLink shl = (SrcHyperLink) o;
          SrcLink name = new SrcLink(shl.name);
          SrcAtom attr = new SrcAtom(shl.attr);
          if (!headhl.contains(name.toString())) {
            LinkedList newl = new LinkedList();
            newl.add(name);
            if (!attr.getName().equals("")) {
              newl.add(attr);
            }
            headhl.add(name.toString());
            SrcAtom newg = new SrcAtom("new", newl);
            guard.add(newg);
          }
        } else if (o instanceof SrcAtom) {
          SrcAtom sa = (SrcAtom) o;
          headhl = addHyperLinkConstraintSub(null, guard, sa.process, headhl);
        } else if (o instanceof SrcMembrane) {
          SrcMembrane sm = (SrcMembrane) o;
          headhl = addHyperLinkConstraintSub(null, guard, sm.process, headhl);
        }
      }
    }
    return headhl;
  }

  /**
   * リンク名が_IXなど、頭に_Iがつくと自動でガードにint(_IX)を加える.
   * hara. nakano.
   * */
  public void addTypeConstraint(LinkedList l) {
    if (l == null) {
      return;
    }

    for (Object o : l) {
      if (o instanceof SrcLink) {
        SrcLink sl = (SrcLink) o;
        if (sl.name.matches("^_I.*")) { // int
          LinkedList newl = new LinkedList();
          newl.add(new SrcLink(sl.name));
          SrcAtom newg = new SrcAtom("int", newl);
          guard.add(newg);
        } else if (sl.name.matches("^_G.*")) { // ground
          LinkedList newl = new LinkedList();
          newl.add(new SrcLink(sl.name));
          SrcAtom newg = new SrcAtom("ground", newl);
          guard.add(newg);
        } else if (sl.name.matches("^_S.*")) { // string
          LinkedList newl = new LinkedList();
          newl.add(new SrcLink(sl.name));
          SrcAtom newg = new SrcAtom("string", newl);
          guard.add(newg);
        } else if (sl.name.matches("^_U.*")) { // unary
          LinkedList newl = new LinkedList();
          newl.add(new SrcLink(sl.name));
          SrcAtom newg = new SrcAtom("unary", newl);
          guard.add(newg);
        }
      } else if (o instanceof SrcAtom) {
        SrcAtom sa = (SrcAtom) o;
        addTypeConstraint(sa.process);
      } else if (o instanceof SrcMembrane) {
        SrcMembrane sm = (SrcMembrane) o;
        addTypeConstraint(sm.process);
      }
    }
  }

  // by kudo (2006/07/07)
  /**
   * simpagation rule を、通常のルールの形に直す。コンストラクタから呼ばれる。
   * @param head2 ヘッドの'\'の後ろ部分のリスト
   */
  private void unSimpagationize(List head2) {
    // head を全てbodyへコピー (前に追加のほうが再利用の上でも都合がいい？)
    body.addAll(copySrcs(head));
    // head2をheadの後ろに連結
    head.addAll(head2);
  }

  /**
   * ソースオブジェクトのリストをコピーする。
   * @param l
   * @return
   */
  private LinkedList copySrcs(List l) {
    if (l == null) {
      return null;
    }

    LinkedList ret = new LinkedList(); // List 型だと各所で使っているgetFirstが無い
    for (Object o : l) {
      if (o instanceof SrcAtom) {
        SrcAtom sa = (SrcAtom) o;
        ret.add(new SrcAtom(sa.getSrcName(), copySrcs(sa.getProcess())));
      } else if (o instanceof SrcMembrane) {
        SrcMembrane sm = (SrcMembrane) o;
        SrcMembrane cpm = new SrcMembrane(copySrcs(sm.getProcess()));
        cpm.name = sm.name;
        cpm.kind = sm.kind;
        cpm.stable = sm.stable;
        cpm.pragma = sm.pragma;
        ret.add(cpm);
      } else if (o instanceof SrcProcessContext) {
        SrcProcessContext spc = (SrcProcessContext) o;
        SrcProcessContext cppc = new SrcProcessContext(spc.getName());
        cppc.args = copySrcs(spc.args);
        if (spc.bundle != null) {
          cppc.bundle = new SrcLinkBundle(spc.bundle.getName());
        }
        ret.add(cppc);
      } else if (o instanceof SrcContext) { // SrcLink, SrcLinkBundle, SrcRuleContext
        SrcLink sl = (SrcLink) o;
        ret.add(new SrcLink(sl.getName()));
      } else if (o instanceof SrcRule) {
      } // ※左辺にルールは出現しない筈
    }
    return ret;
  }

  /**
   * ヘッドを設定する
   */
  public void setHead(LinkedList head) {
    this.head = head;
  }

  /**
   * ルールのヘッドを取得します
   * @return ヘッドのリスト
   */
  public LinkedList getHead() {
    return head;
  }

  /**
   * ルールのガードを得ます
   * @return ガードのリスト
   */
  public LinkedList getGuard() {
    return guard;
  }

  /**
   * ガード否定条件を取得する
   */
  public LinkedList getGuardNegatives() {
    return guardNegatives;
  }

  /**
   * ルールのボディを取得します
   * @return ボディのリスト
   */
  public LinkedList getBody() {
    return body;
  }

  public String toString() {
    return "(rule:" + name + "), head: " + head + ", guard: " + guard + ", body: " + body;
  }

  /**
   * LMNtalソース形式のテキスト表現を取得する。
   */
  public String getText() {
    return text;
  }

  void setText() {
    text = SrcDumper.dump(this);
  }
}

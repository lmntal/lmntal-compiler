package runtime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import runtime.functor.Functor;
import util.Util;

/**
 * <p>LMNtal中間命令を定義するフィールドであることを表します。</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface LMNtalIL {}

/*
 * <p><b>注意</b>　方法4の文書に対して、「ロックしたまま実行膜スタックに積む」という操作
 * およびその結果の「ロックされた、かつ実行膜スタックにも積まれた」状態が追加された。
 *
 * <p>ボディ実行の流れは次の通り。生成と活性化を親膜側から行った後、ロックを子膜側から解放する。
 * <ul>
 * <li>活性化する膜が本膜と同じタスクの膜の場合、親膜側から実際に実行膜スタックに積む。
 * 本膜が実行中なので、新しい膜が実行されることはない。
 * このルールの適用終了前にロックは解放するが、何も起こらない。
 * このルールの適用が終了すると、子膜の実行が開始される。
 * <li>活性化する膜がリモート膜や他のタスクの膜の場合、一時的な「仮の」実行膜スタックを作り、
 * そこに親膜側から積んでいく。
 * リモートのルート膜のロックが解放されると、実行膜スタックの先頭に丸ごと移動される。
 * これによって実行膜スタックに全ての膜がアトミックに積まれることになる。
 * </ul>
 * コンパイラは次のコードを出力する：addmemやnewrootした膜は、ルール実行終了時に（子膜側から順番に）unlockmemを実行する。
 */

// doneTODO LOCAL関係のコメントを完全に削除する (1) -> 0912 done
// TODO 引数mem がいらない箇所がある（デーモン削除の影響）

/**
 * 1 つの命令を保持する。通常は、InstructionのArrayListとして保持する。
 *
 * デバッグ用表示メソッドを備える。
 *
 * @author hara, nakajima, n-kato
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
public class Instruction implements Cloneable {

  /** 命令毎の引数情報を入れるテーブル */
  private static Map<Integer, ArgType> argTypeTable = new HashMap<>();
  /**アトム*/
  public static final int ARG_ATOM = 0;
  /**膜*/
  public static final int ARG_MEM = 1;
  /**アトム・膜以外の変数*/
  public static final int ARG_VAR = 2;
  /**整数*/
  public static final int ARG_INT = 3;
  /**命令列*/
  public static final int ARG_INSTS = 4;
  /**ラベル付き命令列*/
  public static final int ARG_LABEL = 5;
  /**変数番号のList*/
  public static final int ARG_VARS = 6;
  /**その他オブジェクト(ルールなど)への参照*/
  public static final int ARG_OBJ = 7;
  /**その他オブジェクト(ルールなど)への参照のList*/
  public static final int ARG_OBJS = 8;

  /** 命令の種類を保持する。*/
  private int kind;

  /**
   * 命令の引数を保持する。
   * 命令の種類によって引数の型が決まっている。
   */
  public List<Object> data = new ArrayList<>();

  //////////
  // 定数

  /** 型付きアトムに対する命令がアトムではなく、ファンクタを対象にしていることを表す修飾子 */
  public static final int OPT = 100;

  /**
   * ダミーの命令
   */
  @LMNtalIL public static final int DUMMY = -1;

  /**
   * 未定義の命令
   */
  @LMNtalIL public static final int UNDEF = 0;

  /// ** 命令の最大種類数。命令の種類を表す値はこれより小さな数にすること。*/
  // private static final int END_OF_INSTRUCTION = 1024;

  // アトムに関係する出力する基本ガード命令 (1--5)
  // deref     [-dstatom, srcatom, srcpos, dstpos]
  // derefatom [-dstatom, srcatom, srcpos]
  // dereflink [-dstatom, srclink, dstpos]
  // findatom  [-dstatom, srcmem, funcref]

  /**
   * deref [-dstatom, srcatom, srcpos, dstpos]
   *
   * <br><strong><font color="#ff0000">出力するガード命令</font></strong><br>
   * アトム$srcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、
   * リンク先のアトムへの参照を$dstatomに代入する。
   */
  @LMNtalIL public static final int DEREF = 1;

  static {
    setArgType(DEREF, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_INT, ARG_INT));
  }

  /**
   * derefatom [-dstatom, srcatom, srcpos]
   *
   * <br>出力する失敗しない最適化用＋型付き拡張用ガード命令<br>
   * アトム$srcatomの第srcpos引数のリンク先のアトムへの参照を$dstatomに代入する。
   * <p>引き続き$dstatomが、単項アトム（整数なども含む）や自由リンク管理アトムと
   * マッチするかどうか検査する場合に使用することができる。
   */
  @LMNtalIL public static final int DEREFATOM = 2;

  static {
    setArgType(DEREFATOM, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_INT));
  }

  /**
   * dereflink [-dstatom, srclink, dstpos]
   *
   * <br><strong><font color="#ff0000">出力するガード命令</font></strong><br>
   * リンク$srclink先が第dstpos引数に接続していることを確認したら、
   * リンク先のアトムへの参照を$dstatomに代入する。
   */
  @LMNtalIL public static final int DEREFLINK = 3;

  static {
    setArgType(DEREFLINK, new ArgType(true, ARG_ATOM, ARG_VAR, ARG_INT));
  }

  /**
   * findatom [-dstatom, srcmem, funcref]
   *
   * <br>反復するガード命令<br>
   * 膜$srcmemにあってファンクタfuncrefを持つアトムへの参照を次々に$dstatomに代入する。*/
  @LMNtalIL public static final int FINDATOM = 4;

  static {
    setArgType(FINDATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_OBJ));
  }

  // 膜に関係する出力する基本ガード命令 (5--9)
  // lockmem    [-dstmem, freelinkatom, memname]
  // anymem     [-dstmem, srcmem, memtype, memname]
  // lock       [srcmem]
  // getmem     [-dstmem, srcatom, memtype, memname]
  // getparent  [-dstmem, srcmem]

  /**
   * lockmem [-dstmem, freelinkatom, memname]
   *
   * <br>ロック取得するガード命令<br>
   * 自由リンク出力管理アトム$freelinkatomが所属する膜に対して、
   * ノンブロッキングでのロックを取得を試みる。
   * そしてロック取得に成功したこの膜への参照を$dstmemに代入する。
   * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
   * <p>
   * ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
   * （この検査はRuby版ではneqmem命令で行っていた）。
   * <p>膜の外からのリンクで初めて特定された膜への参照を取得するために使用される。
   */
  @LMNtalIL public static final int LOCKMEM = 5;

  static {
    setArgType(LOCKMEM, new ArgType(true, ARG_MEM, ARG_ATOM, ARG_OBJ));
  }

  /**
   * anymem [-dstmem, srcmem, memtype, memname]
   *
   * <br>反復するロック取得するガード命令<br>
   * 膜$srcmemの子膜のうち、$memtypeで表せるタイプのまだロックを取得していない膜に対して次々に、
   * ノンブロッキングでのロック取得を試みる。
   * そして、ロック取得に成功した$memtypeで表せるタイプの各子膜への参照を$dstmemに代入する。
   * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
   * <p><b>注意</b>　ロック取得に失敗した場合と、その膜が存在していなかった場合とは区別できない。
   */
  @LMNtalIL public static final int ANYMEM = 6;

  static {
    setArgType(ANYMEM, new ArgType(true, ARG_MEM, ARG_MEM, ARG_INT, ARG_OBJ));
  }

  /**
   * lock [srcmem]
   *
   * <br>ロック取得するガード命令<br>
   * 膜$srcmemに対して、ノンブロッキングでのロックを取得を試みる。
   * 取得したロックは、後続の命令列が失敗したときに解放される。
   * <p>アトム主導テストで、主導するアトムによって特定された膜のロックを取得するために使用される。
   */
  @LMNtalIL public static final int LOCK = 7;

  static {
    setArgType(LOCK, new ArgType(false, ARG_MEM));
  }

  // 膜に関係する出力しない基本ガード命令 (10--19)
  // testmem    [dstmem, srcatom]
  // norules    [srcmem]
  // nfreelinks [srcmem, count]
  // natoms     [srcmem, count]
  // nmems      [srcmem, count]
  // eqmem      [mem1, mem2]
  // neqmem     [mem1, mem2]
  // stable     [srcmem]

  /**
   * testmem [dstmem, srcatom]
   *
   * <br>ガード命令<br>
   * アトム$srcatomが（ロックされた）膜$dstmemに所属することを確認する。
   * <p><b>注意</b>　Ruby版ではgetmemで参照を取得した後でeqmemを行っていた。
   */
  @LMNtalIL public static final int TESTMEM = 10;

  static {
    setArgType(TESTMEM, new ArgType(false, ARG_MEM, ARG_ATOM));
  }

  /**
   * norules [srcmem]
   *
   * <br>ガード命令<br>
   * 膜$srcmemにルールが存在しないことを確認する。
   */
  @LMNtalIL public static final int NORULES = 11;

  static {
    setArgType(NORULES, new ArgType(false, ARG_MEM));
  }

  /**
   * nfreelinks [srcmem, count]
   *
   * <br>ガード命令<br>
   * 膜$srcmemの自由リンク数がcountであることを確認する。
   */
  @LMNtalIL public static final int NFREELINKS = 12;

  static {
    setArgType(NFREELINKS, new ArgType(false, ARG_MEM, ARG_INT));
  }

  /**
   * natoms [srcmem, count]
   *
   * <br>ガード命令<br>
   * 膜$srcmemの自由リンク管理アトム以外のアトム数がcountであることを確認する。
   */
  @LMNtalIL public static final int NATOMS = 13;

  static {
    setArgType(NATOMS, new ArgType(false, ARG_MEM, ARG_INT));
  }

  /**
   * natomsindirect [srcmem, countfunc]
   *
   * <br>ガード命令<br>
   * 膜$srcmemの自由リンク管理アトム以外のアトム数が$countfuncの値であることを確認する。
   */
  @LMNtalIL public static final int NATOMSINDIRECT = 14;

  static {
    setArgType(NATOMSINDIRECT, new ArgType(false, ARG_MEM, ARG_VAR));
  }

  /**
   * nmems [srcmem, count]
   *
   * <br>ガード命令<br>
   * 膜$srcmemの子膜の数がcountであることを確認する。
   */
  @LMNtalIL public static final int NMEMS = 15;

  static {
    setArgType(NMEMS, new ArgType(false, ARG_MEM, ARG_INT));
  }

  // 16は予約 see isground

  /**
   * eqmem [mem1, mem2]
   *
   * <br>ガード命令<br>
   * $mem1と$mem2が同一の膜を参照していることを確認する。
   * <p><b>注意</b> Ruby版のeqから分離
   */
  @LMNtalIL public static final int EQMEM = 17;

  static {
    setArgType(EQMEM, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * neqmem [mem1, mem2]
   *
   * <br>ガード命令<br>
   * $mem1と$mem2が異なる膜を参照していることを確認する。
   * <p><b>注意</b> Ruby版のneqから分離
   * <p><font color=red><b>この命令は不要かも知れない</b></font>
   */
  @LMNtalIL public static final int NEQMEM = 18;

  static {
    setArgType(NEQMEM, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * stable [srcmem]
   *
   * <br>ガード命令<br>
   * 膜$srcmemとその子孫の全ての膜の実行が停止していることを確認する。
   */
  @LMNtalIL public static final int STABLE = 19;

  static {
    setArgType(STABLE, new ArgType(false, ARG_MEM));
  }

  // アトムに関係する出力しない基本ガード命令 (20-24)
  // func     [srcatom, funcref]
  // notfunc  [srcatom, funcref]
  // eqatom   [atom1, atom2]
  // neqatom  [atom1, atom2]
  // samefunc [atom1, atom2]

  /**
   * func [srcatom, funcref]
   *
   * <br>ガード命令<br>
   * アトム$srcatomがファンクタfuncrefを持つことを確認する。
   * <p>getfunc[tmp,srcatom];loadfunc[func,funcref];eqfunc[tmp,func] と同じ。
   */
  @LMNtalIL public static final int FUNC = 20;

  static {
    setArgType(FUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));
  }

  /**
   * notfunc [srcatom, funcref]
   *
   * <br>ガード命令<br>
   * アトム$srcatomがファンクタfuncrefを持たないことを確認する。
   * <p>典型的には、プロセス文脈の明示的な自由リンクの出現アトムが$inside_proxyでないことを確認するために使われる。
   * <p>getfunc[tmp,srcatom];loadfunc[func,funcref];neqfunc[tmp,func] と同じ。
   */
  @LMNtalIL public static final int NOTFUNC = 21;

  static {
    setArgType(NOTFUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));
  }

  /**
   * eqatom [atom1, atom2]
   *
   * <br>ガード命令<br>
   * $atom1と$atom2が同一のアトムを参照していることを確認する。
   * <p><b>注意</b> Ruby版のeqから分離
   */
  @LMNtalIL public static final int EQATOM = 22;

  static {
    setArgType(EQATOM, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  /**
   * neqatom [atom1, atom2]
   *
   * <br>ガード命令<br>
   * $atom1と$atom2が異なるアトムを参照していることを確認する。
   * <p><b>注意</b> Ruby版のneqから分離
   */
  @LMNtalIL public static final int NEQATOM = 23;

  static {
    setArgType(NEQATOM, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  /**
   * samefunc [atom1, atom2]
   *
   * <br>ガード命令<br>
   * $atom1と$atom2が同じファンクタを持つことを確認する。
   * <p>getfunc[func1,atom1];getfunc[func2,atom2];eqfunc[func1,func2]と同じ。
   */
  @LMNtalIL public static final int SAMEFUNC = 24;

  static {
    setArgType(SAMEFUNC, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  // ファンクタに関係する命令 (25--29)
  // dereffunc [-dstfunc, srcatom, srcpos]
  // getfunc   [-func,    atom]
  // loadfunc  [-func,    funcref]
  // eqfunc              [func1, func2]
  // neqfunc             [func1, func2]

  /**
   * dereffunc [-dstfunc, srcatom, srcpos]
   *
   * <br>出力する失敗しない拡張ガード命令
   * アトム$srcatomの第srcpos引数のリンク先のアトムのファンクタを取得し、$dstfuncに代入する。
   * <p>引き続き、型付き単項アトムのマッチングを行うために使用される。
   * <p>単項アトムでない型付きプロセス文脈は、リンクオブジェクトを使って操作する。
   * <p>derefatom[dstatom,srcatom,srcpos];getfunc[dstfunc,dstatom]と同じなので廃止？
   */
  @LMNtalIL public static final int DEREFFUNC = 25;

  static {
    setArgType(DEREFFUNC, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));
  }

  /**
   * getfunc [-func, atom]
   *
   * <br>出力する失敗しない拡張ガード命令<br>
   * アトム$atomのファンクタへの参照を$funcに代入する。
   */
  @LMNtalIL public static final int GETFUNC = 26;

  static {
    setArgType(GETFUNC, new ArgType(true, ARG_VAR, ARG_ATOM));
  }

  /**
   * loadfunc [-func, funcref]
   *
   * <br>出力する失敗しない拡張ガード命令<br>
   * ファンクタfuncrefへの参照を$funcに代入する。
   */
  @LMNtalIL public static final int LOADFUNC = 27;

  static {
    setArgType(LOADFUNC, new ArgType(true, ARG_VAR, ARG_OBJ));
  }

  // func/funcrefはVAR? OBJ? -> (n-kato) func=VAR, funcref=OBJ

  /**
   * eqfunc [func1, func2]
   *
   * <br>型付き拡張用ガード命令<br>
   * ファンクタ$func1と$func2が等しいことを確認する。
   */
  @LMNtalIL public static final int EQFUNC = 28;

  static {
    setArgType(EQFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * neqfunc [func1, func2]
   *
   * <br>型付き拡張用ガード命令<br>
   * ファンクタ$func1と$func2が異なることを確認する。
   */
  @LMNtalIL public static final int NEQFUNC = 29;

  static {
    setArgType(NEQFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  // アトムを操作する基本ボディ命令 (30--39)
  // removeatom                  [srcatom, srcmem, funcref]
  // newatom           [-dstatom, srcmem, funcref]
  // newatomindirect   [-dstatom, srcmem, func]
  // enqueueatom                 [srcatom]
  // dequeueatom                 [srcatom]
  // freeatom                    [srcatom]
  // alterfunc                   [atom, funcref]
  // alterfuncindirect           [atom, func]

  /**
   * removeatom [srcatom, srcmem, funcref]
   *
   * <br>ボディ命令<br>
   * （膜$srcmemにあってファンクタ$funcを持つ）アトム$srcatomを現在の膜から取り出す。
   * 実行アトムスタックは操作しない。
   */
  @LMNtalIL public static final int REMOVEATOM = 30;

  static {
    setArgType(REMOVEATOM, new ArgType(false, ARG_ATOM, ARG_MEM, ARG_OBJ));
  }

  /**
   * newatom [-dstatom, srcmem, funcref]
   *
   * <br>ボディ命令<br>
   * 膜$srcmemにファンクタfuncrefを持つ新しいアトム作成し、参照を$dstatomに代入する。
   * アトムはまだ実行アトムスタックには積まれない。
   */
  @LMNtalIL public static final int NEWATOM = 31;

  static {
    setArgType(NEWATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_OBJ));
  }

  /**
   * freeatom [srcatom]
   *
   * <br>最適化用ボディ命令<br>
   * 何もしない。
   * <p>$srcatomがどの膜にも属さず、かつこの計算ノード内の実行アトムスタックに積まれていないことを表す。
   * アトムを他の計算ノードで積んでいる場合、輸出表の整合性は大丈夫か調べる。
   * → 輸出表は作らないことにしたので大丈夫。
   */
  @LMNtalIL public static final int FREEATOM = 35;

  static {
    setArgType(FREEATOM, new ArgType(false, ARG_ATOM));
  }

  // アトムを操作する型付き拡張用命令 (40--49)
  // allocatom         [-dstatom, funcref]
  // allocatomindirect [-dstatom, func]
  // copyatom          [-dstatom, mem, srcatom]
  // addatom           [dstmem, atom]

  /**
   * allocatom [-dstatom, funcref]
   *
   * <br>型付き拡張用命令<br>
   * ファンクタfuncrefを持つ所属膜を持たない新しいアトム作成し、参照を$dstatomに代入する。
   * <p>ガード検査で使われる定数アトムを生成するために使用される。
   */
  @LMNtalIL public static final int ALLOCATOM = 40;

  static {
    setArgType(ALLOCATOM, new ArgType(true, ARG_ATOM, ARG_OBJ));
  }

  /**
   * allocatomindirect [-dstatom, func]
   *
   * <br>型付き拡張用最適化用命令<br>
   * ファンクタ$funcを持つ所属膜を持たない新しいアトムを作成し、参照を$dstatomに代入する。
   * <p>ガード検査で使われる定数アトムを生成するために使用される。
   */
  @LMNtalIL public static final int ALLOCATOMINDIRECT = 41;

  static {
    setArgType(ALLOCATOMINDIRECT, new ArgType(true, ARG_ATOM, ARG_VAR));
  }

  /**
   * copyatom [-dstatom, mem, srcatom]
   *
   * <br>型付き拡張用ボディ命令
   * アトム$srcatomと同じ名前のアトムを膜$memに生成し、$dstatomに代入して返す。
   * 実行アトムスタックは操作しない。
   * <p>マッチングで得た型付きアトムをコピーするために使用する。
   * <p>getfunc[func,srcatom];newatomindirect[dstatom,mem,func]と同じ。よって廃止？
   * copygroundtermに移行すべきかもしれない。
   */
  @LMNtalIL public static final int COPYATOM = 42;

  static {
    setArgType(COPYATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_ATOM));
  }

  /**
   * addatom [dstmem, atom]
   *
   * <br>ボディ命令<br>
   * （所属膜を持たない）アトム$atomを膜$dstmemに所属させる。
   */
  @LMNtalIL public static final int ADDATOM = 43;

  static {
    setArgType(ADDATOM, new ArgType(false, ARG_MEM, ARG_ATOM));
  }

  // 膜を操作する基本ボディ命令 (50--60)
  // removemem                [srcmem, parentmem]
  // newmem          [-dstmem, srcmem, memtype]
  // allocmem        [-dstmem]
  // newroot         [-dstmem, srcmem, node, memtype]
  // movecells                [dstmem, srcmem]
  // enqueueallatoms          [srcmem]
  // freemem                  [srcmem]
  // addmem                   [dstmem, srcmem]
  // enququmem                [srcmem]
  // unlockmem                [srcmem]
  // setmemname               [dstmem, name]

  /**
   * removemem [srcmem, parentmem]
   *
   * <br>ボディ命令<br>
   * 膜$srcmemを親膜（$parentmem）から取り出す。
   * <strike>膜$srcmemはロック時に実行膜スタックから除去されているため、実行膜スタックは操作しない。</strike>
   * 実行膜スタックに積まれている場合は除去する。
   */
  @LMNtalIL public static final int REMOVEMEM = 50;

  static {
    setArgType(REMOVEMEM, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * newmem [-dstmem, srcmem, memtype]
   *
   * <br>ボディ命令<br>
   * （活性化された）膜$srcmemに新しい（ルート膜でない）$memtypeで表せるタイプの子膜を作成し、
   *  $dstmemに代入し、活性化する。
   * この場合の活性化は、$srcmemと同じ実行膜スタックに積むことを意味する。
   */
  @LMNtalIL public static final int NEWMEM = 51;

  static {
    setArgType(NEWMEM, new ArgType(true, ARG_MEM, ARG_MEM, ARG_INT));
  }

  /**
   * allocmem [-dstmem]
   *
   * <br>最適化用ボディ命令<br>
   * 親膜を持たない新しい膜を作成し、参照を$dstmemに代入する。
   */
  @LMNtalIL public static final int ALLOCMEM = 52;

  static {
    setArgType(ALLOCMEM, new ArgType(true, ARG_MEM));
  }

  /**
   * newroot [-dstmem, srcmem, nodeatom, memtype]
   *
   * <br>ボディ命令<br>
   * 膜$srcmemの子膜にアトム$nodeatomの名前で指定された計算ノードで実行される新しいロックされた
   * $memtypeで表せるタイプのルート膜を作成し、参照を$dstmemに代入し、（ロックしたまま）活性化する。
   * この場合の活性化は、仮の実行膜スタックに積むことを意味する。
   * <p>ただし上記の仕様は計算ノード指定が空文字列でないときのみ。
   * 空文字列の場合は、newmemと同じだがロックされた状態で作られる。
   * <p>newmemと違い、このルート膜のロックは明示的に解放しなければならない。
   */
  @LMNtalIL public static final int NEWROOT = 53;

  static {
    setArgType(NEWROOT, new ArgType(true, ARG_MEM, ARG_MEM, ARG_ATOM, ARG_INT));
  }

  /**
   * movecells [dstmem, srcmem]
   *
   * <br>ボディ命令<br>
   * （親膜を持たない）膜$srcmemにある全てのアトムと子膜（ロックを取得していない）を膜$dstmemに移動する。
   * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
   * <p>実行後、膜$srcmemはこのまま廃棄されなければならない
   * <strike>（内容物のルールセットに限り参照してよい？）</strike>
   * <p>実行後、膜$dstmemの全てのアクティブアトムをエンキューし直すべきである。
   * <p><b>注意</b>　Ruby版のpourから名称変更
   * <p>moveCellsFromメソッドを呼び出す。
   */
  @LMNtalIL public static final int MOVECELLS = 54;

  static {
    setArgType(MOVECELLS, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * enqueueallatoms [srcmem]
   *
   * <br>（予約された）ボディ命令<br>
   * 何もしない。または、膜$srcmemにある全てのアクティブアトムをこの膜の実行アトムスタックに積む。
   * <p>アトムがアクティブかどうかを判断するには、
   * ファンクタを動的検査する方法と、2つのグループのアトムがあるとして所属膜が管理する方法がある。
   */
  @LMNtalIL public static final int ENQUEUEALLATOMS = 55;

  static {
    setArgType(ENQUEUEALLATOMS, new ArgType(false, ARG_MEM));
  }

  /**
   * freemem [srcmem]
   *
   * <br>ボディ命令<br>
   * 膜$srcmemを廃棄する。
   * <p>$srcmemがどの膜にも属さず、かつスタックに積まれていないことを表す。
   */
  @LMNtalIL public static final int FREEMEM = 56;

  static {
    setArgType(FREEMEM, new ArgType(false, ARG_MEM));
  }

  /**
   * addmem [dstmem, srcmem]
   *
   * <br>ボディ命令<br>
   * ロックされた（親膜の無い）膜$srcmemを（活性化された）膜$dstmemに移動する。
   * 子膜のロックは取得していないものとする。
   * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
   * <p>膜$srcmemを再利用するために使用される。
   * <p>newmemと違い、$srcmemのロックは明示的に解放しなければならない。
   * <p>moveToメソッドを呼び出す。
   */
  @LMNtalIL public static final int ADDMEM = 57;

  static {
    setArgType(ADDMEM, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * enqueuemem [srcmem]
   *
   * ロックされた膜$srcmemをロックしたまま活性化する。
   * この場合の活性化は、$srcmemがルート膜の場合、仮の実行膜スタックに積むことを意味し、
   * ルート膜でない場合、親膜と同じ実行膜スタックに積むことを意味する。
   * すでに実行膜スタックまたは仮の実行膜スタックに積まれている場合は、何もしない。
   */
  @LMNtalIL public static final int ENQUEUEMEM = 58;

  static {
    setArgType(ENQUEUEMEM, new ArgType(false, ARG_MEM));
  }

  /**
   * setmemname [dstmem, name]
   *
   * <br>ボディ命令<br>
   * 膜$dstmemの名前を文字列（またはnull）nameに設定する。
   * <p>現在、膜の名前の使用目的は表示用のみ。いずれ、膜名に対するマッチングができるようになるはず。
   */
  @LMNtalIL public static final int SETMEMNAME = 60;

  static {
    setArgType(SETMEMNAME, new ArgType(false, ARG_MEM, ARG_OBJ));
  }

  // 予約 (61--62)

  // リンクに関係する出力するガード命令 (63--64)

  //	-----  getlink   [-link,atom,pos]
  //	-----  alloclink [-link,atom,pos]

  /**
   * getlink [-link, atom, pos]
   *
   * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
   * アトム$atomの第pos引数に格納されたリンクオブジェクトへの参照を$linkに代入する。
   * <p>典型的には、$atomはルールヘッドに存在する。
   */
  @LMNtalIL public static final int GETLINK = 63;

  static {
    setArgType(GETLINK, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));
  }

  /**
   * alloclink [-link, atom, pos]
   *
   * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
   * アトム$atomの第pos引数を指すリンクオブジェクトを生成し、参照を$linkに代入する。
   * <p>典型的には、$atomはルールボディに存在する。
   */
  @LMNtalIL public static final int ALLOCLINK = 64;

  static {
    setArgType(ALLOCLINK, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));
  }

  // リンクを操作するボディ命令 (65--69)
  // newlink     [atom1, pos1, atom2, pos2, mem1]
  // relink      [atom1, pos1, atom2, pos2, mem]
  // unify       [atom1, pos1, atom2, pos2, mem]
  // inheritlink [atom1, pos1, link2, mem]
  // unifylinks  [link1, link2, mem]

  /**
   * newlink [atom1, pos1, atom2, pos2, mem1]
   *
   * <br>ボディ命令<br>
   * アトム$atom1（膜$mem1にある）の第pos1引数と、
   * アトム$atom2の第pos2引数の間に両方向リンクを張る。
   * <p>典型的には、$atom1と$atom2はいずれもルールボディに存在する。
   * <p><b>注意</b>　Ruby版の片方向から仕様変更された。
   * <p>alloclink[link1,atom1,pos1];alloclink[link2,atom2,pos2];unifylinks[link1,link2,mem1]と同じ。
   */
  @LMNtalIL public static final int NEWLINK = 65;

  static {
    setArgType(NEWLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));
  }

  /**
   * relink [atom1, pos1, atom2, pos2, mem]
   *
   * <br>ボディ命令<br>
   * アトム$atom1（膜$memにある）の第pos1引数と、
   * アトム$atom2の第pos2引数のリンク先（膜$memにある）の引数を接続する。
   * <p>典型的には、$atom1はルールボディに、$atom2はルールヘッドに存在する。
   * <p>実行後、$atom2[pos2]の内容は無効になる。
   * <p>getlink[link2,atom2,pos2];inheritlink[atom1,pos1,link2,mem]と同じ。
   * <p>alloclink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem]と同じ。
   */
  @LMNtalIL public static final int RELINK = 66;

  static {
    setArgType(RELINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));
  }

  /**
   * unify [atom1, pos1, atom2, pos2, mem]
   *
   * <br>ボディ命令<br>
   * アトム$atom1の第pos1引数のリンク先<strike>（膜$memにある）</strike>の引数と、
   * アトム$atom2の第pos2引数のリンク先<strike>（膜$memにある）</strike>の引数を接続する。
   * <strike>ただし$atom1および$atom2のリンク先がどちらも所属膜を持たない場合も許されており、
   * この場合、何もしないで終わってもよいことになっている。これは f(A,A),(f(X,Y):-X=Y) の書き換えなどで起こる。</strike>
   * $atom1 と $atom2 の両方もしくは一方が所属膜を持たない場合もある。
   * これは a(A),f(A,B),(a(X),f(Y,Z):-Y=Z,b(X)) の書き換えなどで起こる。
   * <p>典型的には、$atom1と$atom2はいずれもルールヘッドに存在する。
   * <p>getlink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem]と同じ。
   */
  @LMNtalIL public static final int UNIFY = 67;

  static {
    setArgType(UNIFY, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));
  }

  /**
   * inheritlink [atom1, pos1, link2, mem]
   *
   * <br>最適化用ボディ命令<br>
   * アトム$atom1（膜$memにある）の第pos1引数と、
   * リンク$link2のリンク先（膜$memにある）を接続する。
   * <p>典型的には、$atom1はルールボディに存在し、$link2はルールヘッドに存在する。relinkの代用。
   * <p>$link2は再利用されるため、実行後は$link2は廃棄しなければならない。
   * <p>alloclink[link1,atom1,pos1];unifylinks[link1,link2,mem]と同じ。
   */
  @LMNtalIL public static final int INHERITLINK = 68;

  static {
    setArgType(INHERITLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_VAR, ARG_MEM));
  }

  /**
   * unifylinks [link1, link2, mem]
   *
   * <br>ボディ命令<br>
   * リンク$link1の指すアトム引数とリンク$link2の指すアトム引数との間に双方向のリンクを張る。
   * ただし$link1は膜$memのアトムを指しているか、または所属膜の無いアトムを指している。
   * 後者の場合、何もしないで終わってもよいことになっている。
   * <p>todo 命令の解釈時にmem引数が使われることはないので、引数に含めないようにした方がよい。
   * <p>実行後$link1および$link2は無効なリンクオブジェクトとなるため、参照を使用してはならない。
   * <p>基底項データ型のコンパイルで使用される。
   */
  @LMNtalIL public static final int UNIFYLINKS = 69;

  static {
    setArgType(UNIFYLINKS, new ArgType(false, ARG_VAR, ARG_VAR, ARG_MEM));
  }

  // 自由リンク管理アトム自動処理のためのボディ命令 (70--74)
  //  -----  removeproxies          [srcmem]
  //  -----  removetoplevelproxies  [srcmem]
  //  -----  insertproxies          [parentmem,childmem]
  //  -----  removetemporaryproxies [srcmem]

  /**
   * removeproxies [srcmem]
   *
   * <br>ボディ命令<br>
   * $srcmemを通る無関係な自由リンク管理アトムを自動削除する。
   * <p>removememの直後に同じ膜に対して呼ばれる。
   */
  @LMNtalIL public static final int REMOVEPROXIES = 70;

  static {
    setArgType(REMOVEPROXIES, new ArgType(false, ARG_MEM));
  }

  /**
   * removetoplevelproxies [srcmem]
   *
   * <br>ボディ命令<br>
   * 膜$srcmem（本膜）を通過している無関係な自由リンク管理アトムを除去する。
   * <p>removeproxiesが全て終わった後で呼ばれる。
   */
  @LMNtalIL public static final int REMOVETOPLEVELPROXIES = 71;

  static {
    setArgType(REMOVETOPLEVELPROXIES, new ArgType(false, ARG_MEM));
  }

  /**
   * insertproxies [parentmem,childmem]
   *
   * <br>ボディ命令<br>
   * 指定された膜間に自由リンク管理アトムを自動挿入する。
   * <p>addmemが全て終わった後で呼ばれる。
   */
  @LMNtalIL public static final int INSERTPROXIES = 72;

  static {
    setArgType(INSERTPROXIES, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * removetemporaryproxies [srcmem]
   *
   * <br>ボディ命令<br>
   * 膜$srcmem（本膜）に残された"star"アトムを除去する。
   * <p>insertproxiesが全て終わった後で呼ばれる。
   */
  @LMNtalIL public static final int REMOVETEMPORARYPROXIES = 73;

  static {
    setArgType(REMOVETEMPORARYPROXIES, new ArgType(false, ARG_MEM));
  }

  // ルールを操作するボディ命令 (75--79)
  // loadruleset [dstmem, ruleset]
  // copyrules   [dstmem, srcmem]
  // clearrules  [dstmem]

  /**
   * loadruleset [dstmem, ruleset]
   *
   * <br>ボディ命令<br>
   * ルールセットrulesetを膜$dstmemにコピーする。
   * <p>この膜のアクティブアトムは再エンキューすべきである。
   */
  @LMNtalIL public static final int LOADRULESET = 75;

  static {
    setArgType(LOADRULESET, new ArgType(false, ARG_MEM, ARG_OBJ));
  }

  /**
   * copyrules [dstmem, srcmem]
   *
   * <br>ボディ命令<br>
   * 膜$srcmemにある全てのルールを膜$dstmemにコピーする。
   * <p><b>注意</b>　Ruby版のinheritrulesから名称変更
   */
  @LMNtalIL public static final int COPYRULES = 76;

  static {
    setArgType(COPYRULES, new ArgType(false, ARG_MEM, ARG_MEM));
  }

  /**
   * clearrules [dstmem]
   *
   * <br>ボディ命令<br>
   * 膜$dstmemにある全てのルールを消去する。
   */
  @LMNtalIL public static final int CLEARRULES = 77;

  static {
    setArgType(CLEARRULES, new ArgType(false, ARG_MEM));
  }

  /**
   * loadmodule [dstmem, ruleset]
   *
   * <br>ボディ命令<br>
   * ルールセットrulesetを膜$dstmemにコピーする。
   */
  @LMNtalIL public static final int LOADMODULE = 78;

  static {
    setArgType(LOADMODULE, new ArgType(false, ARG_MEM, ARG_OBJ));
  }

  // 型付きでないプロセス文脈をコピーまたは廃棄するための命令 (80--89)
  //  ----- recursivelock            [srcmem]
  //  ----- recursiveunlock          [srcmem]
  //  ----- copymem         [-dstmap, dstmem, srcmem]
  //  ----- dropmem                  [srcmem]

  /**
   * copycells [-dstmap, dstmem, srcmem]
   *
   * <br>（予約された）ボディ命令<br>
   * 再帰的にロックされた膜$srcmemの内容のコピーを作成し,膜$dstmemに入れる.
   * その際、リンク先がこの膜の(子膜を含めて)中に無いアトムの情報を
   * コピーされるアトムオブジェクト {@literal -->} コピーされたアトムオブジェクト
   * (2005/01/13 従来のAtom.idからの参照を変更)
   * というMapオブジェクトとして,dstmapに入れる.
   **/
  @LMNtalIL public static final int COPYCELLS = 82;

  static {
    setArgType(COPYCELLS, new ArgType(true, ARG_VAR, ARG_MEM, ARG_MEM));
  }

  /**
   * dropmem [srcmem]
   *
   * <br>（予約された）ボディ命令<br>
   * 再帰的にロックされた膜$srcmemを破棄する。
   * この膜や子孫の膜をルート膜とするタスクは強制終了する。
   */
  @LMNtalIL public static final int DROPMEM = 83;

  static {
    setArgType(DROPMEM, new ArgType(false, ARG_MEM));
  }

  /**
   * lookuplink [-dstlink, srcmap, srclink]
   *
   * srclinkのリンク先のアトムのコピーを$srcmapより得て、
   * そのアトムをリンク先とする-dstlinkを作って返す。
   */
  @LMNtalIL public static final int LOOKUPLINK = 84;

  static {
    setArgType(LOOKUPLINK, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  /**
   * insertconnectors [-dstset,linklist,mem]
   *
   * linklistリストの各変数番号にはリンクオブジェクトが格納されている。
   * それらのリンクオブジェクトのリンク先は$mem内のアトムである。
   * それらのリンクオブジェクトの全ての組み合わせに対し、buddyの関係にあるかどうかを検査し、
   * その場合には'='アトムを挿入する。
   * 挿入したアトムを$dstsetに追加する。
   */
  @LMNtalIL public static final int INSERTCONNECTORS = 85;

  static {
    setArgType(INSERTCONNECTORS, new ArgType(true, ARG_VAR, ARG_VARS, ARG_MEM));
  }

  // (2006/09/24 kudo)
  // TODO 名前が長い上に意味不明なのでなんとかする
  /**
   * insertconnectorsinnull[-dstset,linklist]
   *
   * linklistリストの各変数番号にはリンクオブジェクトが格納されている。
   * それらのリンクオブジェクトの全ての組み合わせに対し、buddyの関係にあるかどうかを検査し、
   * その場合には'='アトムを挿入する。
   * ただし'='は所属膜を持たない．
   * 挿入したアトムを$dstsetに追加する。
   * この命令は型付きプロセス文脈の複製に伴い発行される．
   */
  @LMNtalIL public static final int INSERTCONNECTORSINNULL = 86;

  static {
    setArgType(INSERTCONNECTORSINNULL, new ArgType(true, ARG_VAR, ARG_VARS));
  }

  /**
   * deleteconnectors[srcset, srcmap]
   *
   * $srcsetに含まれる'='アトムをコピーしたアトムを$srcmapから得て、
   * 削除し、リンクをつなぎなおす。<strike>$memはコピー先の膜。</strike>
   * 膜引数は廃止(2006/09/21)
   */
  @LMNtalIL public static final int DELETECONNECTORS = 87;

  static {
    setArgType(DELETECONNECTORS, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  //	/** * [srcatom,pos]
  //	 * <br>（予約された）ボディ命令<br>
  //	 * アトムsrcatomの第1引数のリンク先のリンク先のアトムの第pos引数と、
  //	 * srcatomの第1引数の間に双方向リンクを貼る。自由リンク管理アトムをどうするか。
  //	 * <p>プロセス文脈のコピー時に使用される。*/
  //	public static final int * = err;

  // アトム集団のコンパイルには、findatomとrunを使うといいかもしれないが、できないかもしれない。

  // コピーした後でrelinkする。

  // 予約 (90--99)

  //////////////////////////////////////////////////////////////////

  // 制御命令 (200--209)
  //  -----  react       [ruleref,         [memargs...], [atomargs...], [varargs...]]
  //  -----  jump        [instructionlist, [memargs...], [atomargs...], [varargs...]]
  //  -----  commit      [ruleref]
  //  -----  resetvars   [[memargs...], [atomargs...], [varargs...]]
  //  -----  changevars  [[memargs...], [atomargs...], [varargs...]]
  //  -----  spec        [formals,locals]
  //  -----  proceed
  //  -----  stop
  //  -----  branch      [[instructions...]]
  //  -----  loop        [[instructions...]]
  //  -----  run         [[instructions...]]
  //  -----  not         [instructionslist]

  /**
   * react [ruleref, [memargs...], [atomargs...], [varargs...]]
   *
   * <br>失敗しないガード命令<br>
   * ルールrulerefに対するマッチングが成功したことを表す。
   * 処理系はこのルールのボディを呼び出さなければならない。
   * <p>spec        [formals, locals];
   *    resetvars   [[memargs...], [atomargs...], [varargs...]];
   *    branch      [body] と同じ。
   * ただしbodyはrulerefのボディ命令列で、先頭の命令はspec[formals,locals]
   * <p>（未使用命令）
   */
  @LMNtalIL public static final int REACT = 1200;

  static {
    setArgType(REACT, new ArgType(false, ARG_OBJ, ARG_VARS, ARG_VARS, ARG_VARS));
  }

  /**
   * jump [instructionlist, [memargs...], [atomargs...], [varargs...]]
   *
   * <br>制御命令<br>
   * 指定の引数列でラベル付き命令列instructionlistを呼び出す。
   * <p>
   * 指定した命令列の実行に失敗すると、この命令が失敗する。
   * 指定した命令列の実行に成功すると、ここで終了する。
   * <p>spec        [formals, locals];
   *    resetvars   [[memargs...], [atomargs...], [varargs...]];
   *    branch      [body] と同じ。
   * ただしbodyはinstructionlistの命令列で、先頭の命令はspec[formals,locals]
   */
  @LMNtalIL public static final int JUMP = 200;

  static {
    setArgType(JUMP, new ArgType(false, ARG_LABEL, ARG_VARS, ARG_VARS, ARG_VARS));
  }

  /**
   * commit [ruleref]
   *
   * <br>無視される最適化用およびトレース用命令<br>
   * 現在の実引数ベクタでルールrulerefに対するマッチングが成功したことを表す。
   * 処理系は、この命令に到達するまでに行った全ての分岐履歴を忘却してよい。
   */
  @LMNtalIL public static final int COMMIT = 201;

  // static {setArgType(COMMIT, new ArgType(false, ARG_OBJ));}
  /**
   * 変更版 commit [rulename, lineno]
   * トレースとデバッガに必要な情報を保持する.
   * トレース用にルール名を文字列で保持.
   * デバッガ用にソース上の行番号linenoを整数で保持.
   * sakurai
   */
  static {
    setArgType(COMMIT, new ArgType(false, ARG_OBJ, ARG_INT));
  }

  /**
   * resetvars [[memargs...], [atomargs...], [varargs...]]
   *
   * <br>失敗しないガード命令および最適化用ボディ命令<br>
   * 変数ベクタの内容を再定義する。新しい変数番号は、膜、アトム、その他の順番で0から振り直される。
   * <b>注意</b>　memargs[0]は本膜が予約しているため変更してはならない。
   */
  @LMNtalIL public static final int RESETVARS = 202;

  static {
    setArgType(RESETVARS, new ArgType(false, ARG_VARS, ARG_VARS, ARG_VARS));
  }

  /**
   * changevars [[memargs...], [atomargs...], [varargs...]]
   *
   * <br>失敗しないガード命令および最適化用ボディ命令<br>
   * 変数ベクタの内容および長さを再定義する。
   * 新しい変数番号は、膜、アトム、その他のいずれも0から振り直される。
   * ただしnullが入っている要素は無視される。
   * 同じ番号に異なる種類のオブジェクトが振られないように注意すること。
   * <b>注意</b>　memargs[0]は本膜が予約しているため変更してはならない。
   * <p>（未使用命令）
   */
  @LMNtalIL public static final int CHANGEVARS = 1202;

  static {
    setArgType(CHANGEVARS, new ArgType(false, ARG_VARS, ARG_VARS, ARG_VARS));
  }

  /** spec [formals, locals]
   * <br>制御命令<br>
   * 仮引数と局所変数の個数を宣言する。
   * 局所変数の個数が不足している場合、変数ベクタを拡張する。
   */
  @LMNtalIL public static final int SPEC = 203;

  static {
    setArgType(SPEC, new ArgType(false, ARG_INT, ARG_INT));
  }

  /**
   * proceed []
   *
   * <br>ボディ命令<br>
   * このproceed命令が所属する命令列の実行が成功したことを表す。
   * <p>トップレベル命令列で使用された場合、ルールの適用が終わり、
   * 再利用された全ての膜のロックを解放し、生成した全ての膜を活性化したことを表す。
   * <p><b>注意</b>　proceedなしで命令列の終端まで進んだ場合、
   * その命令列の実行は失敗したものとする仕様が採用された。
   */
  @LMNtalIL public static final int PROCEED = 204;

  static {
    setArgType(PROCEED, new ArgType(false));
  }

  /**
   * stop []
   * <br>（予約された）失敗しないガード命令<br>
   * proceedと同じ。マッチングとボディの命令が統合されたことに伴って廃止される予定。
   * <p>典型的には、否定条件にマッチしたことを表すために使用される。
   */
  @LMNtalIL public static final int STOP = 205;

  static {
    setArgType(STOP, new ArgType(false));
  }

  /**
   * branch [[instructions...]]
   *
   * <br>構造化命令<br>
   * 引数の命令列を実行することを表す。
   * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、branchの次の命令に進む。
   * 引数実行中にproceed命令を実行した場合、ここで終了する。
   */
  @LMNtalIL public static final int BRANCH = 206;

  static {
    setArgType(BRANCH, new ArgType(false, ARG_LABEL));
  }

  /**
   * loop [[instructions...]]
   *
   * <br>構造化命令<br>
   * 引数の命令列を実行することを表す。
   * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、loopの次の命令に進む。
   * 引数実行中にproceed命令を実行した場合、このloop命令の実行を繰り返す。
   */
  @LMNtalIL public static final int LOOP = 207;

  static {
    setArgType(LOOP, new ArgType(false, ARG_INSTS));
  }

  /**
   * run [[instructions...]]
   *
   * <br>（予約された）構造化命令<br>
   * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
   * 引数実行中に失敗した場合、runの次の命令に進む。
   * 引数実行中にproceed命令を実行した場合、次の命令に進む。
   * <p>将来、明示的な引数付きのプロセス文脈のコンパイルに使用するために予約。
   */
  @LMNtalIL public static final int RUN = 208;

  static {
    setArgType(RUN, new ArgType(false, ARG_INSTS));
  }

  /**
   * not [instructionlist]
   *
   * <br>（予約された）構造化命令<br>
   * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
   * 引数実行中に失敗した場合、notの次の命令に進む。
   * 引数実行中にproceed命令を実行した場合、この命令が失敗する。
   * <p>将来、否定条件のコンパイルに使用するために予約。
   */
  @LMNtalIL public static final int NOT = 209;

  static {
    setArgType(NOT, new ArgType(false, ARG_LABEL));
  }

  // 組み込み機能に関する命令（仮） (210--213)
  //  -----  inline  [atom, inlineref]
  //  -----  builtin [class, method, [links...]]

  /**
   * callback [srcmem, atom]
   *
   * <br>ボディ命令<br>
   * 所属膜がsrcmemのアトム$atomに対して、Cのコールバック関数を呼び出す
   */
  @LMNtalIL public static final int CALLBACK = 213;

  static {
    setArgType(CALLBACK, new ArgType(false, ARG_MEM, ARG_ATOM));
  }

  ///////////////////////////////////////////////////////////////////////

  // 型付きプロセス文脈を扱うための追加命令 (214--215)

  /**
   * uniq [ [Links...] ]
   *
   * <br>拡張ガード命令<br>
   * 型付きプロセス文脈 Links... に対して、過去にこの組み合わせで反応が起きていたら失敗する。
   * 起きていなかったら履歴にこの組み合わせを記録して成功する。
   */
  @LMNtalIL public static final int UNIQ = 214;

  static {
    setArgType(UNIQ, new ArgType(false, ARG_VARS));
  }

  ///////////////////////////////////////////////////////////////////////

  // 型付きプロセス文脈を扱うための追加命令 (216--220)

  /**
   * eqground [link1,link2]
   *
   * <br>（予約された）拡張ガード命令<br>
   * （どちらかが基底項プロセスを指すとわかっている）
   * 2つのリンクlink1とlink2に対して、
   * それらが同じ形状の基底項プロセスであることを確認する。
   */
  @LMNtalIL public static final int EQGROUND = 216;

  static {
    setArgType(EQGROUND, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * neqground [link1,link2]
   *
   * <br>拡張ガード命令<br>
   * （どちらかが基底項プロセスを指すとわかっている）
   * 2つのリンクlink1とlink2に対して、
   * それらが同じ形状の基底項プロセスでないことを確認する。
   */
  @LMNtalIL public static final int NEQGROUND = 217;

  static {
    setArgType(NEQGROUND, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * copyground [-dstlist, srclinklist, dstmem, attrs]
   *
   * （基底項プロセスを指す）リンク列$srclinklistを$dstmemに複製し、
   * $dstlistの第1要素はコピーされたリンク列を，
   * 第二要素にはコピー元のアトムからコピー先のアトムへのマップがそれぞれ格納される．
   * attrs を属性としたハイパーリンクは，コピー元の基底項に局所的ならばコピーされ
   * （つまり新たなハイパーリンクが作成され），局所的でないならばコピーされず共有される．
   * 属性をもたないハイパーリンクやattrs に指定されていない属性のハイパーリンクは
   * コピーされない．
   */
  @LMNtalIL public static final int COPYGROUND = 218;

  static {
    setArgType(COPYGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_MEM, ARG_OBJS));
  }

  /**
   * removeground [srclinklist, srcmem]
   *
   * $srcmemに属する（基底項プロセスを指す）リンク列$srclinklistを現在の膜から取り出す。
   * 実行アトムスタックは操作しない。
   */
  @LMNtalIL public static final int REMOVEGROUND = 219;

  static {
    setArgType(REMOVEGROUND, new ArgType(false, ARG_VAR, ARG_MEM));
  }

  /**
   * freeground [srclinklist]
   *
   * 基底項プロセス$srclinklistがどの膜にも属さず、かつスタックに積まれていないことを表す。
   */
  @LMNtalIL public static final int FREEGROUND = 220;

  static {
    setArgType(FREEGROUND, new ArgType(false, ARG_VAR));
  }

  // 型検査のためのガード命令 (221--229)

  /**
   * isground [-natomsfunc, linklist, avolist, attrs]
   *
   * <br>（予約された）ロック取得する拡張ガード命令<br>
   * リンク列$linklistの指す先が基底項プロセスであることを確認する。
   * すなわち、リンク先から（戻らずに）到達可能なアトムが全てこの膜に存在していることを確認する。
   * ただし、$avolistに登録されたリンクに到達したら失敗する。
   * 見つかった基底項プロセスを構成するこの膜のアトムの個数（をラップしたInteger）を$natomsに格納する。
   *
   * 基底項プロセスのハイパーリンクはたどらないが，attrsに指定した属性のハイパーリンクで
   * すべての端点がこの基底項プロセスに属する場合，そのハイパーリンクはこの基底項プロセス
   * の局所ハイパーリンクといい，後続のcopyground命令で（共有でなく）新規作成の対象となる．
   *
   * <p>natomsとnmemsと統合した命令を作り、$natomsの総和を引数に渡すようにする。
   * 子膜の個数の照合は、本膜がロックしていない子膜の個数が0個かどうか調べればよい。
   * しかし本膜がロックしたかどうかを調べるメカニズムが今は備わっていないため、保留。
   *
   * groundには膜は含まれないことになったので、上記は不要
   */
  @LMNtalIL public static final int ISGROUND = 221;

  static {
    setArgType(ISGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR, ARG_OBJS));
  }

  /**
   * isunary [atom]
   *
   * <br>ガード命令<br>
   * アトム$atomが1引数のアトムであることを確認する。
   */
  @LMNtalIL public static final int ISUNARY = 222;

  static {
    setArgType(ISUNARY, new ArgType(false, ARG_ATOM));
  }

  /**
   * isint [atom]
   *
   * <br>ガード命令<br>
   * アトム$atomが整数アトムであることを確認する。
   */
  @LMNtalIL public static final int ISINT = 225;

  @LMNtalIL public static final int ISFLOAT = 226;

  @LMNtalIL public static final int ISSTRING = 227;

  static {
    setArgType(ISINT, new ArgType(false, ARG_ATOM));
  }

  static {
    setArgType(ISFLOAT, new ArgType(false, ARG_ATOM));
  }

  static {
    setArgType(ISSTRING, new ArgType(false, ARG_ATOM));
  }

  /**
   * isintfunc [func]
   *
   * <br>最適化用ガード命令<br>
   * ファンクタ$funcが整数ファンクタであることを確認する。
   */
  @LMNtalIL public static final int ISINTFUNC = ISINT + OPT;

  @LMNtalIL public static final int ISFLOATFUNC = ISFLOAT + OPT;

  static {
    setArgType(ISINTFUNC, new ArgType(false, ARG_VAR));
  }

  static {
    setArgType(ISFLOATFUNC, new ArgType(false, ARG_VAR));
  }

  /**
   * getclass [-stringatom, atom]
   *
   * <br>出力するガード命令<br>
   * アトム$atomがObjectFunctorまたはそのサブクラスのファンクタを持つことを確認し、
   * 格納されたオブジェクトのクラスの完全修飾名文字列を表すファンクタを持つアトムを生成し、
   * $stringatomに代入する。
   * ただし、Translator を利用した場合、同一ソースのInlineコードで定義されたクラスに関しては単純名を取得する。(2005/10/17 Mizuno )
   */
  @LMNtalIL public static final int GETCLASS = 228;

  static {
    setArgType(GETCLASS, new ArgType(true, ARG_ATOM, ARG_ATOM));
  }

  /////////////////////////////////////////////////////////////////////////

  /**
   * newlinklist [-dstlist]
   *
   * 新しいリンクのリストを作る
   */
  @LMNtalIL public static final int NEWLIST = 242;

  static {
    setArgType(NEWLIST, new ArgType(true, ARG_VAR));
  }

  /**
   * addtolist [dstlist, src]
   *
   * $srcをリスト$dstlistの最後に追加する
   * ( 2006/09/13 kudo )
   */
  @LMNtalIL public static final int ADDTOLIST = 243;

  static {
    setArgType(ADDTOLIST, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * getfromlist [-dst, list, pos]
   *
   * $listからpos番目の要素を$dstに取得する
   */
  @LMNtalIL public static final int GETFROMLIST = 244;

  static {
    setArgType(GETFROMLIST, new ArgType(true, ARG_VAR, ARG_VAR, ARG_INT));
  }

  ///////////////////////////////////////////////////////////////////////

  // hyperlink用命令 (250--) //seiji
  /**
   * newhlink [link]
   *
   * 新たなhyperlinkを生成し, link先に接続することを示す
   */
  @LMNtalIL public static final int NEWHLINK = 250;

  static {
    setArgType(NEWHLINK, new ArgType(true, ARG_VAR));
  }

  /**
   * makehlink [ID, link]
   *
   * 過去に生成されたhyperlinkのうち, 識別子IDを持つhyperlinkを生成し, link先に接続することを示す
   * （未実装、hyperlinkへの値の代入などに使用できるかも？）
   */
  // @LMNtalIL public static final int MAKEHLINK = 251;
  // static {setArgType(MAKEHLINK, new ArgType(true, ARG_VAR, ARG_VAR));}

  /**
   * ishlink [link]
   *
   * link先に接続する構造がhyperlinkであることをチェックすることを示す
   */
  @LMNtalIL public static final int ISHLINK = 257;

  static {
    setArgType(ISHLINK, new ArgType(false, ARG_VAR));
  }

  /**
   * getnum [hyperlink, atom]
   *
   * hyperlinkの要素数をatomに返すことを示す
   */
  @LMNtalIL public static final int GETNUM = 264;

  static {
    setArgType(GETNUM, new ArgType(true, ARG_VAR, ARG_VAR));
  }

  /**
   * unifyhlinks [mem, unify_atom]
   *
   * 膜memにあるunify_atom{@code ><}に対してhyperlinkの併合操作を行なうことを示す
   */
  @LMNtalIL public static final int UNIFYHLINKS = 268;

  static {
    setArgType(UNIFYHLINKS, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * findproccxt [atom1, length1, arg1, atom2, length2, arg2]
   *
   * アトム番号atom1(価数=lenght1)の第arg1引数の型付きプロセス文脈が、
   * アトム番号atom2(価数=lenght2)の第arg2引数の型付きプロセス文脈と同名であることを示す
   * 必ず(atom1,arg1)がオリジナル、(atom2,arg2)が新たに生成された名前になるよう配置されている
   */
  @LMNtalIL public static final int FINDPROCCXT = 300;

  static {
    setArgType(
        FINDPROCCXT, new ArgType(false, ARG_ATOM, ARG_INT, ARG_INT, ARG_ATOM, ARG_INT, ARG_INT));
  }

  ///////////////////////////////////////////////////////////////////////

  // hlground命令 (280--286)
  // hlground型関連の命令列一覧。具体的な内容はmeguroさんの修論またはwiki等参照してください。

  /**
   * copyhlground [dstlist, srclist, mem, attrs]
   *
   * srclistの接続先の、attrsを属性としたhlground型の構造をdstlistの先にコピーする。
   * attrsに指定されていない属性のハイパーリンクのコピーしない。
   *
   */
  @LMNtalIL public static final int COPYHLGROUND = 280;

  static {
    setArgType(COPYHLGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_MEM, ARG_OBJS));
  }

  /**
   * removehlground [srclist, mem, attrs]
   *
   * mem内のsrclistの接続先の、attrsを属性としたhlground型の構造を削除する。
   * attrsに指定されていない属性のハイパーリンクは削除しない。
   */
  @LMNtalIL public static final int REMOVEHLGROUND = 281;

  static {
    setArgType(REMOVEHLGROUND, new ArgType(false, ARG_VAR, ARG_MEM, ARG_OBJS));
  }

  /**
   * freehlground [srclist, attrs]
   *
   * srclistの接続先のattrsを属性としたhlground型の構造のメモリを解放する。
   * attrsに指定されていない属性のハイパーリンクのメモリの解放しない。
   */
  @LMNtalIL public static final int FREEHLGROUND = 282;

  static {
    setArgType(FREEHLGROUND, new ArgType(false, ARG_VAR, ARG_OBJS));
  }

  /**
   * ishlground [natoms, srclist, avoidlist, attrs]
   *
   * srclistに含まれるリンク群に接続している構造が、attrsを属性としたhlground型であることを検査し、探索したアトムの個数を natomsに格納する。
   * attrsに指定されていない属性のハイパーリンクは探索しない。探索中に膜やavoidlistに含まれるリンク群に遭遇したときは完全には失敗とならず、マッチするものだけを対象に処理する。
   */
  @LMNtalIL public static final int ISHLGROUND = 283;

  static {
    setArgType(ISHLGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR, ARG_OBJS));
  }

  /**
   * newhlinkwithattr [dstatom, attr]
   *
   * 新たな属性付きhyperlinkを生成し, link先に接続することを示す。
   */
  @LMNtalIL public static final int NEWHLINKWITHATTR = 284;

  static {
    setArgType(NEWHLINKWITHATTR, new ArgType(true, ARG_VAR, ARG_OBJ));
  }

  /**
   * getattratom [dstatom, atom]
   *
   * ハイパーリンクの属性を取得する
   */
  @LMNtalIL public static final int GETATTRATOM = 285;

  static {
    setArgType(GETATTRATOM, new ArgType(true, ARG_VAR, ARG_VAR));
  }

  /**
   * hypergetlink [link, atom, pos]
   *
   * アトム$atomの第pos引数に格納されたリンクオブジェクトへの参照を$linkに代入する。
   */
  @LMNtalIL public static final int HYPERGETLINK = 286;

  static {
    setArgType(HYPERGETLINK, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));
  }

  ///////////////////////////////////////////////////////////////////////

  // 整数用の組み込みボディ命令 (400--419+OPT)

  /**
   * iadd [-dstintatom, intatom1, intatom2]
   *
   * <br>整数用の組み込み命令<br>
   * 整数アトムの加算結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。
   * <p>idivおよびimodに限り失敗する。
   */
  @LMNtalIL public static final int IADD = 400;

  @LMNtalIL public static final int ISUB = 401;

  @LMNtalIL public static final int IMUL = 402;

  @LMNtalIL public static final int IDIV = 403;

  @LMNtalIL public static final int INEG = 404;

  @LMNtalIL public static final int IMOD = 405;

  //	@LMNtalIL public static final int INOT = 410;
  @LMNtalIL public static final int IAND = 411;

  @LMNtalIL public static final int IOR = 412;

  @LMNtalIL public static final int IXOR = 413;

  @LMNtalIL public static final int ISAL = 414;

  static {
    setArgType(IADD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(ISUB, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IMUL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IDIV, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(INEG, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IMOD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  //	static {setArgType(INOT, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
  static {
    setArgType(IAND, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IOR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IXOR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(ISAL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  /**
   * iaddfunc [-dstintfunc, intfunc1, intfunc2]
   *
   * <br>整数用の最適化用組み込み命令<br>
   * 整数ファンクタの加算結果を表す整数ファンクタを生成し、$dstintfuncに代入する。
   * <p>idivfuncおよびimodfuncに限り失敗する。
   */
  @LMNtalIL public static final int IADDFUNC = IADD + OPT;

  @LMNtalIL public static final int ISUBFUNC = ISUB + OPT;

  @LMNtalIL public static final int IMULFUNC = IMUL + OPT;

  @LMNtalIL public static final int IDIVFUNC = IDIV + OPT;

  @LMNtalIL public static final int IMODFUNC = IMOD + OPT;

  static {
    setArgType(IADDFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(ISUBFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(IMULFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(IDIVFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(IMODFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));
  }

  // 整数用の組み込みガード命令 (420--429+OPT)

  /**
   * ilt [intatom1, intatom2]
   *
   * <br>整数用の組み込みガード命令<br>
   * 整数アトムの値の大小比較が成り立つことを確認する。
   */
  @LMNtalIL public static final int ILT = 420;

  @LMNtalIL public static final int ILE = 421;

  @LMNtalIL public static final int IGT = 422;

  @LMNtalIL public static final int IGE = 423;

  @LMNtalIL public static final int IEQ = 424;

  @LMNtalIL public static final int INE = 425;

  static {
    setArgType(ILT, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(ILE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IGT, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IGE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(IEQ, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(INE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  /**
   * iltfunc [intfunc1, intfunc2]
   *
   * <br>整数用の最適化用組み込みガード命令<br>
   * 整数ファンクタの値の大小比較が成り立つことを確認する。
   */
  @LMNtalIL public static final int ILTFUNC = ILT + OPT;

  @LMNtalIL public static final int ILEFUNC = ILE + OPT;

  @LMNtalIL public static final int IGTFUNC = IGT + OPT;

  @LMNtalIL public static final int IGEFUNC = IGE + OPT;

  static {
    setArgType(ILTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(ILEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(IGTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(IGEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  // 浮動小数点数用の組み込みボディ命令 (600--619+OPT)
  @LMNtalIL public static final int FADD = 600;

  @LMNtalIL public static final int FSUB = 601;

  @LMNtalIL public static final int FMUL = 602;

  @LMNtalIL public static final int FDIV = 603;

  @LMNtalIL public static final int FNEG = 604;

  static {
    setArgType(FADD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FSUB, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FMUL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FDIV, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FNEG, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));
  }

  // 浮動小数点数用の組み込みガード命令 (620--629+OPT)
  @LMNtalIL public static final int FLT = 620;

  @LMNtalIL public static final int FLE = 621;

  @LMNtalIL public static final int FGT = 622;

  @LMNtalIL public static final int FGE = 623;

  @LMNtalIL public static final int FEQ = 624;

  @LMNtalIL public static final int FNE = 625;

  static {
    setArgType(FLT, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FLE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FGT, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FGE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FEQ, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(FNE, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  // int と float の相互変換の組み込みガード命令 (630--639+OPT)
  @LMNtalIL public static final int FLOAT2INT = 630;

  @LMNtalIL public static final int INT2FLOAT = 631;

  static {
    setArgType(FLOAT2INT, new ArgType(true, ARG_ATOM, ARG_ATOM));
  }

  static {
    setArgType(INT2FLOAT, new ArgType(true, ARG_ATOM, ARG_ATOM));
  }

  @LMNtalIL public static final int FLOAT2INTFUNC = FLOAT2INT + OPT;

  @LMNtalIL public static final int INT2FLOATFUNC = INT2FLOAT + OPT;

  static {
    setArgType(FLOAT2INTFUNC, new ArgType(true, ARG_VAR, ARG_VAR));
  }

  static {
    setArgType(INT2FLOATFUNC, new ArgType(true, ARG_VAR, ARG_VAR));
  }

  // グループ化に関する命令
  /**
   * group [subinsts]
   *
   * subinsts 内部の命令列
   * sakurai
   */
  @LMNtalIL public static final int GROUP = 2000;

  static {
    setArgType(GROUP, new ArgType(false, ARG_INSTS));
  }

  /**
   * subclass [atom1, atom2]
   *
   * atom1 が atom2 のサブクラスかどうかを判定する
   * 2006.6.30 by inui
   */
  @LMNtalIL public static final int SUBCLASS = 3000;

  static {
    setArgType(SUBCLASS, new ArgType(false, ARG_ATOM, ARG_ATOM));
  }

  /**
   * swaplink [atom1, pos1, atom2, pos2]
   *
   * アトム $atom1 の第 $pos1 引数の接続先とアトム $atom2 の第 $pos2 引数の接続先を交換する
   */
  @LMNtalIL public static final int SWAPLINK = 88;

  static {
    setArgType(SWAPLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT));
  }

  /**
   * cyclelinks [alist, plist]
   * アトムリスト $alist と番号リスト $plist によって表されるアトム引数列について、その接続先に対して巡回置換を行う
   */
  @LMNtalIL public static final int CYCLELINKS = 89;

  static {
    setArgType(CYCLELINKS, new ArgType(false, ARG_VARS, ARG_VARS));
  }

  /**
   * clearlink [atom, pos]
   *
   * アトム $atom の第 $pos 引数の接続先の情報をNULLにする命令。SLIM専用で、swaplink命令と併用して使われる。(2014/6/5 by aoyama)
   */
  @LMNtalIL public static final int CLEARLINK = 90;

  static {
    setArgType(CLEARLINK, new ArgType(false, ARG_ATOM, ARG_INT));
  }

  /**
   * headatom [atom, mem]
   *
   * SLIM専用の最適化命令。$mem 中のアトム $atom をそのファンクタのアトムリストのheadに持ってくる。--use-atomlistopのオプションが必須。(2014/6/8 by aoyama)
   */
  @LMNtalIL public static final int HEADATOM = 91;

  static {
    setArgType(HEADATOM, new ArgType(false, ARG_ATOM, ARG_MEM));
  }

  /**
   * tailatomlist [atom, mem]
   *
   * SLIM専用の最適化命令。$mem 中のアトム $atom の前にある部分アトムリストをアトムリストのtailにつなぎ替える。--use-atomlistopのオプションが必須。(2014/6/8 by aoyama)
   */
  @LMNtalIL public static final int TAILATOMLIST = 92;

  static {
    setArgType(TAILATOMLIST, new ArgType(false, ARG_ATOM, ARG_MEM));
  }

  /**
   * ispairedlink [link1, link2]
   *
   * CSLMNtalによる型制約実装のためのガード中間命令
   * リンク link1 及びリンク link2 が直接繋がっているとき, 成功する.
   * そうでなければ失敗する.
   */
  public static final int ISPAIREDLINK = 980;

  static {
    setArgType(ISPAIREDLINK, new ArgType(false, ARG_VAR, ARG_VAR));
  }

  /**
   * succreturn [retset]
   *
   * CSLMNtalによる型制約実装のための中間命令
   * レジスタのリスト retset を呼び出し元の subrule 命令で指定したレジスタへ代入し, 呼び出し元へ制御を移す.
   */
  public static final int SUCCRETURN = 990;

  static {
    setArgType(SUCCRETURN, new ArgType(false, ARG_VAR));
  }

  /**
   * failreturn []
   *
   * CSLMNtalによる型制約実装のための中間命令
   * 呼び出し元の subrule 命令へ制御を移す.
   * 呼び出し元の subrule 命令は失敗となる.
   */
  public static final int FAILRETURN = 991;

  static {
    setArgType(FAILRETURN, new ArgType(false));
  }

  /**
   * allocset [set]
   *
   * CSLMNtalによる型制約実装のための中間命令
   * レジスタのリスト set をメモリに確保する.
   */
  public static final int ALLOCSET = 992;

  static {
    setArgType(ALLOCSET, new ArgType(true, ARG_VAR));
  }

  /**
   * subrule [-retset, memi, typename, arglist]
   *
   * CSLMNtalによる型制約実装のための中間命令
   * ルール内にユーザー定義型のガードが現れた場合subrule命令を発行し、対象の型検査を行う
   */
  public static final int SUBRULE = 999;

  static {
    setArgType(SUBRULE, new ArgType(true, ARG_VAR, ARG_INT, ARG_OBJ, ARG_VARS));
  }

  /** 命令の種類を取得する。*/
  public int getKind() {
    return kind;
  }

  /**@deprecated*/
  public int getID() {
    return getKind();
  }

  public int getIntArg(int pos) {
    return (Integer) data.get(pos);
  }

  public int getIntArg1() {
    return getIntArg(0);
  }

  public int getIntArg2() {
    return getIntArg(1);
  }

  public int getIntArg3() {
    return getIntArg(2);
  }

  public int getIntArg4() {
    return getIntArg(3);
  }

  public int getIntArg5() {
    return getIntArg(4);
  }

  public int getIntArg6() {
    return getIntArg(5);
  }

  public Object getArg(int pos) {
    return data.get(pos);
  }

  public Object getArg1() {
    return getArg(0);
  }

  public Object getArg2() {
    return getArg(1);
  }

  public Object getArg3() {
    return getArg(2);
  }

  public Object getArg4() {
    return getArg(3);
  }

  public Object getArg5() {
    return getArg(4);
  }

  public Object getArg6() {
    return getArg(5);
  }

  ////////////////////////////////////////////////////////////////

  /**
   * 引数を追加するマクロ。
   * @param o オブジェクト型の引数
   */
  public final void add(Object o) {
    data.add(o);
  }

  /**
   * 引数を追加するマクロ。
   * @param n int 型の引数
   */
  public final void add(int n) {
    data.add(n);
  }

  ////////////////////////////////////////////////////////////////

  /**
   * ダミー命令を生成する。
   * さしあたって生成メソッドがまだできてない命令はこれを使う
   * @param s 説明用の文字列
   * @deprecated
   */
  public static Instruction dummy(String s) {
    Instruction i = new Instruction(DUMMY);
    i.add(s);
    return i;
  }

  /**
   * react 命令を生成する。
   * @param r 反応できるルールオブジェクト
   * @param actual 実引数列
   * @deprecated
   */
  public static Instruction react(Rule r, List actual) {
    Instruction i = new Instruction(REACT);
    i.add(r);
    i.add(actual);
    return i;
  }

  /**
   * react 命令を生成する。
   * @param r 反応できるルールオブジェクト
   * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
   * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
   * @deprecated
   */
  public static Instruction react(Rule r, List memactuals, List atomactuals) {
    Instruction i = new Instruction(REACT);
    i.add(r);
    i.add(memactuals);
    i.add(atomactuals);
    i.add(new ArrayList());
    return i;
  }

  /**
   * react 命令を生成する。
   * @param r 反応できるルールオブジェクト
   * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
   * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
   * @param varactuals その他の実引数のリスト。その他の変数番号からなる。
   */
  public static Instruction react(Rule r, List memactuals, List atomactuals, List varactuals) {
    Instruction i = new Instruction(REACT);
    i.add(r);
    i.add(memactuals);
    i.add(atomactuals);
    i.add(varactuals);
    return i;
  }

  /**
   * jump 命令を生成する。
   * @param insts ジャンプ先のラベル付き命令列
   * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
   * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
   * @param varactuals その他の実引数のリスト。その他の変数番号からなる。
   */
  public static Instruction jump(
      InstructionList insts, List memactuals, List atomactuals, List varactuals) {
    Instruction i = new Instruction(JUMP);
    i.add(insts);
    i.add(memactuals);
    i.add(atomactuals);
    i.add(varactuals);
    return i;
  }

  /**
   * commit 命令を生成する。
   * @param r 反応するルールオブジェクト
   */
  public static Instruction commit(Rule r) {
    Instruction i = new Instruction(COMMIT);
    i.add(r);
    return i;
  }

  public static Instruction commit(String rulename, int lineno) {
    Instruction inst = new Instruction(COMMIT, rulename, lineno);
    return inst;
  }

  /** resetvars 命令を生成する */
  public static Instruction resetvars(List memargs, List atomargs, List varargs) {
    Instruction i = new Instruction(RESETVARS);
    i.add(memargs);
    i.add(atomargs);
    i.add(varargs);
    return i;
  }

  /** findatom 命令を生成する */
  public static Instruction findatom(int dstatom, int srcmem, Functor func) {
    return new Instruction(FINDATOM, dstatom, srcmem, func);
  }

  /** anymem 命令を生成する */
  public static Instruction anymem(int dstmem, int srcmem, String name) {
    return anymem(dstmem, srcmem, 0, name);
  }

  public static Instruction anymem(int dstmem, int srcmem, int kind, String name) {
    return new Instruction(ANYMEM, dstmem, srcmem, kind, (Object) name);
  }

  /** newatom 命令を生成する */
  public static Instruction newatom(int dstatom, int srcmem, Functor func) {
    return new Instruction(NEWATOM, dstatom, srcmem, func);
  }

  /** spec 命令を生成する */
  public static Instruction spec(int formals, int locals) {
    Instruction i = new Instruction(SPEC);
    i.add(formals);
    i.add(locals);
    return i;
  }

  /** newmem 命令を生成する */
  public static Instruction newmem(int ret, int srcmem) {
    return new Instruction(NEWMEM, ret, srcmem, 0);
  }

  /** newmem 命令を生成する */
  public static Instruction newmem(int ret, int srcmem, int kind) {
    return new Instruction(NEWMEM, ret, srcmem, kind);
  }

  /** newlink 命令を生成する */
  public static Instruction newlink(int atom1, int pos1, int atom2, int pos2, int mem1) {
    return new Instruction(NEWLINK, atom1, pos1, atom2, pos2, mem1);
  }

  /** loadruleset 命令を生成する */
  public static Instruction loadruleset(int mem, Ruleset rs) {
    return new Instruction(LOADRULESET, mem, rs);
  }

  /** removeatom 命令を生成する*/
  public static Instruction removeatom(int atom, int mem) {
    return new Instruction(REMOVEATOM, atom, mem);
  }

  /** fail擬似命令を生成する */
  public static Instruction fail() {
    InstructionList label = new InstructionList();
    label.add(new Instruction(PROCEED));
    return new Instruction(Instruction.NOT, label);
  }

  /** subrule命令を生成する
   * @param retset
   * @param memi
   * @param typename
   * @param arglist
   */
  public static Instruction subrule(int retset, int memi, String typename, List arglist) {
    Instruction i = new Instruction(SUBRULE);
    i.add(retset);
    i.add(memi);
    i.add(typename);
    i.add(arglist);
    return i;
  }

  // コンストラクタ

  /** 無名命令を作る。*/
  public Instruction() {}

  /**
   * 指定された命令をつくる。いずれprivateにするといいのかも知れない。
   * @param kind
   */
  public Instruction(int kind) {
    this.kind = kind;
  }

  public Instruction(int kind, int arg1) {
    this.kind = kind;
    add(arg1);
  }

  public Instruction(int kind, Object arg1) {
    this.kind = kind;
    add(arg1);
  }

  public Instruction(int kind, int arg1, int arg2) {
    this.kind = kind;
    add(arg1);
    add(arg2);
  }

  public Instruction(int kind, int arg1, Object arg2) {
    this.kind = kind;
    add(arg1);
    add(arg2);
  }

  public Instruction(int kind, Object arg1, int arg2) {
    this.kind = kind;
    add(arg1);
    add(arg2);
  }

  public Instruction(int kind, Object arg1, Object arg2) {
    this.kind = kind;
    add(arg1);
    add(arg2);
  }

  public Instruction(int kind, int arg1, int arg2, int arg3) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
  }

  public Instruction(int kind, int arg1, Object arg2, int arg3) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
  }

  public Instruction(int kind, int arg1, int arg2, Object arg3) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
  }

  public Instruction(int kind, Object arg1, Object arg2, Object arg3) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
  }

  public Instruction(int kind, int arg1, int arg2, int arg3, int arg4) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
  }

  public Instruction(int kind, int arg1, int arg2, int arg3, Object arg4) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
  }

  public Instruction(int kind, int arg1, int arg2, int arg3, int arg4, Object arg5) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
    add(arg5);
  }

  public Instruction(int kind, Object arg1, Object arg2, Object arg3, Object arg4) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
  }

  public Instruction(int kind, int arg1, int arg2, int arg3, int arg4, int arg5) {
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
    add(arg5);
  }

  public Instruction(
      int kind, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) { // seiji
    this.kind = kind;
    add(arg1);
    add(arg2);
    add(arg3);
    add(arg4);
    add(arg5);
    add(arg6);
  }

  /** パーザー用コンストラクタ */
  public Instruction(String name, List<Object> data) {
    try {
      Field f = Instruction.class.getField(name.toUpperCase());
      this.kind = f.getInt(null);
      this.data = data;
      return;
    } catch (NoSuchFieldException ignored) {
    } catch (IllegalAccessException ignored) {
    }
    // 例外発生時
    throw new RuntimeException("invalid instruction name : " + name);
  }

  public Object clone() {
    Instruction c = new Instruction();
    c.kind = this.kind;
    for (Object o : data) {
      if (o instanceof ArrayList) {
        c.data.add(((ArrayList) o).clone());
      } else if (kind != Instruction.JUMP && o instanceof InstructionList) {
        c.data.add(((InstructionList) o).clone());
      } else {
        c.data.add(o);
      }
    }
    return c;
  }

  //////////////////////////////////
  // 最適化器が使う、命令列書き換えのためのクラスメソッド
  // @author Mizuno

  // todo argtype は signature に名称変更するとよい

  private static void setArgType(int kind, ArgType argtype) {
    if (argTypeTable.containsKey(kind)) {
      throw new RuntimeException("setArgType for '" + kind + "' was called more than once");
    }
    argTypeTable.put(kind, argtype);
  }

  private static class ArgType {

    private boolean output;
    private int[] type;

    ArgType(boolean output) {
      this.output = output;
      type = new int[0];
    }

    ArgType(boolean output, int arg1) {
      this.output = output;
      type = new int[] {arg1};
    }

    ArgType(boolean output, int arg1, int arg2) {
      this.output = output;
      type = new int[] {arg1, arg2};
    }

    ArgType(boolean output, int arg1, int arg2, int arg3) {
      this.output = output;
      type = new int[] {arg1, arg2, arg3};
    }

    ArgType(boolean output, int arg1, int arg2, int arg3, int arg4) {
      this.output = output;
      type = new int[] {arg1, arg2, arg3, arg4};
    }

    ArgType(boolean output, int arg1, int arg2, int arg3, int arg4, int arg5) {
      this.output = output;
      type = new int[] {arg1, arg2, arg3, arg4, arg5};
    }

    ArgType(boolean output, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
      this.output = output;
      type = new int[] {arg1, arg2, arg3, arg4, arg5, arg6};
    }
  }

  /**
   * 出力引数でない引数のリストを返す
   * @return 引数のリスト
   */
  public ArrayList getVarArgs(HashMap<Integer, Integer> listn) {
    ArrayList ret = new ArrayList();
    listn.clear();
    ArgType argtype = argTypeTable.get(getKind());
    int i = 0;
    int j = 0;
    if (getOutputType() != -1) {
      i = 1;
    }
    for (; i < data.size(); i++) {
      switch (argtype.type[i]) {
        case ARG_ATOM:
        case ARG_MEM:
        case ARG_VAR:
          ret.add(getArg(i));
          listn.put(j, i);
          j++;
      }
    }
    return ret;
  }

  /**
   * 同一命令かどうかを
   */
  public boolean equalsInst(Instruction inst) {
    if (kind == JUMP || inst.getKind() == JUMP) return false;
    if (kind != inst.getKind()) return false;
    List<Object> data2 = inst.data;
    if (data.size() != data2.size()) {
      return false;
    } else {
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i) == null && data2.get(i) == null) return true;
        if (data.get(i) == null || data2.get(i) == null) return false;
        if (!data.get(i).equals(data2.get(i))) return false;
      }
    }
    return true;
  }

  /**
   * 与えられた対応表によって、ボディ命令列中のアトム変数を書き換える。<br>
   * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
   *
   * @param list 書き換える命令列
   * @param map 変数の対応表。
   */
  public static void changeAtomVar(List<Instruction> list, Map<Integer, Integer> map) {
    for (Instruction inst : list) {
      ArgType argtype = argTypeTable.get(inst.getKind());
      for (int i = 0; i < inst.data.size(); i++) {
        switch (argtype.type[i]) {
          case ARG_ATOM:
            changeArg(inst, i + 1, map);
            break;
        }
      }
    }
  }

  /**
   * 与えられた対応表によって、ボディ命令列中の膜変数を書き換える。<br>
   * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
   *
   * @param list 書き換える命令列
   * @param map 変数の対応表。
   */
  public static void changeMemVar(List<Instruction> list, Map<Integer, Integer> map) {
    for (Instruction inst : list) {
      ArgType argtype = argTypeTable.get(inst.getKind());
      for (int i = 0; i < inst.data.size(); i++) {
        switch (argtype.type[i]) {
          case ARG_MEM:
            changeArg(inst, i + 1, map);
            break;
        }
      }
    }
  }

  /**
   * 与えられた対応表によって、ボディ命令列中のアトム・膜以外の変数を書き換える。<br>
   * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
   *
   * @param list 書き換える命令列
   * @param map 変数の対応表。
   */
  public static void changeOtherVar(List<Instruction> list, Map<Integer, Integer> map) {
    for (Instruction inst : list) {
      ArgType argtype = argTypeTable.get(inst.getKind());
      for (int i = 0; i < inst.data.size(); i++) {
        switch (argtype.type[i]) {
          case ARG_VAR:
            changeArg(inst, i + 1, map);
            break;
        }
      }
    }
  }

  /**
   * 与えられた対応表によって、ボディ命令列中のすべての変数を書き換える。<br>
   * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
   *
   * @param list 書き換える命令列
   * @param map 変数の対応表。
   */
  public static void applyVarRewriteMap(List<Instruction> list, Map<Integer, Integer> map) {
    for (Instruction inst : list) {
      ArgType argtype = argTypeTable.get(inst.getKind());
      for (int i = 0; i < inst.data.size(); i++) {
        switch (argtype.type[i]) {
          case ARG_MEM:
          case ARG_ATOM:
          case ARG_VAR:
            changeArg(inst, i + 1, map);
            break;
          case ARG_LABEL:
            if (inst.getKind() == JUMP) break; // JUMP命令のLABEL引数はただのラベルなので除外
            applyVarRewriteMap(((InstructionList) inst.data.get(i)).insts, map);
            break;
          case ARG_INSTS:
            applyVarRewriteMap((List<Instruction>) inst.data.get(i), map);
            break;
          case ARG_VARS:
            ListIterator li = ((List) inst.data.get(i)).listIterator();
            while (li.hasNext()) {
              Object varnum = li.next();
              if (map.containsKey(varnum)) {
                li.set(map.get(varnum));
              }
            }
            break;
        }
      }
      if (inst.getKind() == RESETVARS || inst.getKind() == CHANGEVARS) break;
    }
  }

  /** 命令列後半部分に対して変数番号を付け替える */
  public static void applyVarRewriteMapFrom(
      List<Instruction> list, Map<Integer, Integer> map, int start) {
    applyVarRewriteMap(list.subList(start, list.size()), map);
  }

  ////////////////////////////////////////////////////////////////

  /**
   * 対応表によって引数を書き換える。
   * @param inst 書き換える命令
   * @param pos 書き換える引数番号
   * @param map 書き換えマップ
   */
  private static void changeArg(Instruction inst, int pos, Map<Integer, Integer> map) {
    Integer id = (Integer) inst.data.get(pos - 1);
    if (map.containsKey(id)) {
      inst.data.set(pos - 1, map.get(id));
    }
  }

  /** 指定された命令列中で変数が参照される回数を返す（代入は含まない）
   * @param list   命令列
   * @param varnum 変数番号
   * author n-kato */
  public static int getVarUseCount(List<Instruction> list, Integer varnum) {
    int count = 0;
    for (Instruction inst : list) {
      if (inst.getKind() == RESETVARS || inst.getKind() == CHANGEVARS) {
        // (n-kato 2008.01.15) TODO 誰かがこれを実装する↓
        /* if (inst contains varnum) varnum = lookupNewVarnum(inst,varnum);
         * else return count;
         * continue;
         */
      }
      ArgType argtype = argTypeTable.get(inst.getKind());
      int i = 0;
      if (argtype.output) i++;
      for (; i < inst.data.size(); i++) {
        switch (argtype.type[i]) {
          case ARG_MEM:
          case ARG_ATOM:
          case ARG_VAR:
            if (inst.getKind() == FINDPROCCXT) break; // FINDPROCCXT命令が持つ引数はただのラベルなので除外
            if (inst.data.get(i).equals(varnum)) count++;
            break;
          case ARG_LABEL:
            if (inst.getKind() == JUMP) break; // JUMP命令のLABEL引数はただのラベルなので除外
            count += getVarUseCount(((InstructionList) inst.getArg(i)).insts, varnum);
            break;
          case ARG_INSTS:
            count += getVarUseCount(((InstructionList) inst.getArg(i)).insts, varnum);
            break;
          case ARG_VARS:
            if (inst.getKind() == RESETVARS)
              break; // <strike>RESETVARS命令のVARS引数はただのラベルなので除外</strike>
            Iterator it2 = ((List) inst.data.get(i)).iterator();
            while (it2.hasNext()) {
              if (it2.next().equals(varnum)) count++;
            }
            break;
        }
      }
    }
    return count;
  }

  /** 指定された命令列後半部分で変数が参照される回数を返す（代入は含まない）
   * @param list   命令列
   * @param varnum 変数番号
   * @param start  開始位置 */
  public static int getVarUseCountFrom(List<Instruction> list, Integer varnum, int start) {
    return getVarUseCount(list.subList(start, list.size()), varnum);
  }

  ////////////////////////////////////////////////////////////////
  /**
   * この命令が出力命令の場合、出力の種類を返す。
   * そうでない場合、-1を返す。
   */
  public int getOutputType() {
    ArgType argtype = argTypeTable.get(kind);
    if (argtype.output) {
      return argtype.type[0];
    } else {
      return -1;
    }
  }

  /**
   * この命令の引数の種類を返す。
   */
  public int getArgType(int i) {
    ArgType argtype = argTypeTable.get(kind);
    return argtype.type[i];
  }

  /** この命令が副作用を持つ可能性があるかどうかを返す。不明な場合trueを返さなければならない。
   * ただし膜のロック取得は副作用とは見なさない。
   * <p>どうやら、従来「ガード命令」と呼んでいたものに相当するらしい。*/
  public boolean hasSideEffect() {
    // todo 水野君方式のかっこいい管理にして正しく実装する予定
    switch (getKind()) {
      case Instruction.DEREF:
      case Instruction.DEREFATOM:
      case Instruction.DEREFFUNC:
      case Instruction.GETFUNC:
      case Instruction.FUNC:
      case Instruction.EQATOM:
      case Instruction.SAMEFUNC:
      case Instruction.LOADFUNC:
      case Instruction.GETLINK:
      case Instruction.ALLOCLINK:
      case Instruction.ALLOCMEM:
      case Instruction.LOOKUPLINK:
      case Instruction.LOCK:
      case Instruction.ISINT:
      case Instruction.ISUNARY:
      case Instruction.IADD:
      case Instruction.IADDFUNC:
      case Instruction.IEQ:
      case Instruction.ILT:
      case Instruction.ILE:
      case Instruction.IGT:
      case Instruction.IGE:
      case Instruction.INE:
      case Instruction.FEQ:
      case Instruction.FLT:
      case Instruction.FLE:
      case Instruction.FGT:
      case Instruction.FGE:
      case Instruction.FNE:
        return false;
    }
    return true;
  }

  /** この命令が制御動作をする可能性があるかどうかを返す。
   * 制御動作とは失敗や反復などを表す。不明な場合trueを返さなければならない。*/
  public boolean hasControlEffect() {
    // todo 水野君方式のかっこいい管理にして正しく実装する予定
    switch (getKind()) {
      case Instruction.DEREFATOM:
      case Instruction.DEREFFUNC:
      case Instruction.GETFUNC:
      case Instruction.LOADFUNC:
      case Instruction.GETLINK:
      case Instruction.ALLOCLINK:
      case Instruction.ALLOCMEM:
      case Instruction.LOOKUPLINK:
      case Instruction.IADD:
      case Instruction.IADDFUNC:
        return false;
      case Instruction.DEREF:
      case Instruction.IDIV:
      case Instruction.IDIVFUNC:
      case Instruction.LOCK:
        return true;
    }
    return true;
  }

  //////////////////////////////////
  //
  // デバッグ用表示メソッド
  //

  /** Integerでラップされた命令番号から命令名へのハッシュ。
   * <p>処理系開発が収束した頃に、もっと効率のよい別の構造で置き換えてもよい。 */
  private static HashMap<Integer, String> instructionTable = new HashMap<>();

  // インスタンス生成時にスタックオーバーフローを起こしたので修正しました。 by Mizuno
  // ExceptionInInitializerError がおきてたので修正 by hara
  static {
    try {
      Instruction inst = new Instruction();
      Field[] fields = inst.getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        Field f = fields[i];
        // 追加。hara
        if (!f.getType().isPrimitive()) continue;
        if (f.getName().startsWith("ARG_")) continue; // added by mizuno
        if (f.getName().equals("depth")) continue; // added by n-kato, todo make local
        int kind = f.getInt(inst);
        if (
        /*kind != LOCAL
        && okabe*/ f.getType().getName().equals("int")
            && Modifier.isStatic(f.getModifiers())) {
          if (instructionTable.containsKey(kind)) {
            Util.errPrintln("WARNING: collision detected at instruction kind number: " + kind);
          }
          instructionTable.put(kind, f.getName().toLowerCase());
        }
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  // 命令リスト出力用 main
  // CSV形式で出力される
  public static void main(String[] args) {
    SortedMap<Integer, String> idToName = new TreeMap<>();
    SortedMap<String, Integer> nameToId = new TreeMap<>();
    try {
      Field[] fields = Instruction.class.getDeclaredFields();
      for (Field f : fields) {
        // アノテーションを使った判別。
        // 静的初期化ブロック内のフィールド名による判別に代わる方法。
        // もちろん、ゆくゆくはリフレクションに頼らない方法にしたい。
        if (!Modifier.isStatic(f.getModifiers()) || !f.isAnnotationPresent(LMNtalIL.class))
          continue;

        Class<?> type = f.getType();
        if (!type.getName().equals("int")) continue;

        try {
          int id = f.getInt(null);
          String name = f.getName().toLowerCase();
          if (idToName.containsKey(id)) {
            System.err.printf(
                "conflict(Name:%s) - ID %d is already defined as %s.\n",
                name, id, idToName.get(id));
          } else if (nameToId.containsKey(name)) {
            System.err.printf(
                "conflict(ID:%d) - Name %s is already used for ID %d.\n",
                id, name, nameToId.get(name));
          } else {
            idToName.put(id, name);
            nameToId.put(name, id);
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
      System.out.printf("\"ID\",\"Name\",\"Ordered by ID\"\n");
      for (Map.Entry<Integer, String> entry : idToName.entrySet()) {
        System.out.printf("%d,\"%s\"\n", entry.getKey(), entry.getValue());
      }
      System.out.printf("\n\"Name\",\"ID\",\"Ordered by Name\"\n");
      for (Map.Entry<String, Integer> entry : nameToId.entrySet()) {
        System.out.printf("\"%s\",%d\n", entry.getKey(), entry.getValue());
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * デバッグ用表示メソッド。
   * 命令のkind (int)を与えると、該当する命令の名前 (String) を返してくれる。
   *
   * author NAKAJIMA Motomu nakajima@ueda.info.waseda.ac.jp
   * @return String
   *
   */
  public static String getInstructionString(int kind) {
    return instructionTable.get(kind);
  }

  public static int depth;

  /**
   * デバッグ用表示メソッド。
   *
   * author NAKAJIMA Motomu nakajima@ueda.info.waseda.ac.jp
   * @return String
   *
   * メモ：Instructionの中は、Listの中にArrayListが入れ子になって入っている。
   * つまり、【int型のname】命令 [引数1, 引数2, … , 引数n]
   * ※一つのInstructionインスタンスには、1つしか命令がない
   * ただし命令はInteger型、引数はObject。
   *
   * メモ：出力の時、モードが−の変数をインデントする。
   *
   */
  public String toString() {
    depth++;
    // nakajima版2004-01-21
    StringBuilder buffer = new StringBuilder();
    String instName = getInstructionString(kind);
    int spaces = 15;
    ArgType argtype = argTypeTable.get(kind);
    if (argtype.output) {
      spaces -= 2;
    }
    buffer.append(instName);
    spaces -= instName.length();
    while (spaces > 0) {
      buffer.append(' ');
      spaces--;
    }

    if (data.size() == 1 && data.get(0) instanceof ArrayList) {
      ArrayList arg1 = (ArrayList) data.get(0);
      if (arg1.size() == 1 && arg1.get(0) instanceof ArrayList) {
        ArrayList insts = (ArrayList) arg1.get(0);
        if (insts.size() == 0) {
          buffer.append("[[]]");
        } else {
          buffer.append("[[\n");
          for (int i = 0; i < insts.size(); i++) {
            buffer.append("                  ");
            buffer.append(insts.get(i));
            if (i + 1 < insts.size()) {
              buffer.append(", \n");
            } else {
              buffer.append(" ]]");
            }
          }
          return buffer.toString();
        }
      }
    }

    if (kind != Instruction.JUMP && data.size() >= 1 && data.get(0) instanceof InstructionList) {
      List insts = ((InstructionList) data.get(0)).insts;
      if (insts.size() == 0) {
        buffer.append("[]");
      } else {
        buffer.append("[[\n");
        int i;
        for (i = 0; i < insts.size() - 1; i++) {
          // アトム主導テストの命令列を見やすく(?)する sakurai
          //					if(((Instruction)insts.get(i)).getKind() == Instruction.GROUP
          //						|| ((Instruction)insts.get(i)).getKind() == Instruction.COMMIT){
          //						buffer.append("\n");
          //					}
          buffer.append("                ");
          for (int j = 0; j < depth; j++) buffer.append("  ");
          buffer.append(insts.get(i));
          // TODO 出力引数だったらインデントを下げる.
          buffer.append("\n");
        }
        buffer.append("                ");
        for (int j = 0; j < depth; j++) buffer.append("  ");
        buffer.append(insts.get(i));
        for (int j = 1; j < data.size(); j++) {
          buffer.append("                  ");
          buffer.append("     ");
          buffer.append(", " + data.get(j));
        }
        buffer.append(" ]]");
        depth--;
        return buffer.toString();
      }
    }

    buffer.append("[");
    // パーズできるようにエスケープ by mizuno
    for (int i = 0; i < data.size(); i++) {
      if (i != 0) buffer.append(", ");
      Object o = data.get(i);
      String str = (o == null ? "null" : o.toString());
      if (o instanceof String || (o instanceof Rule)) {
        str = Util.quoteString(str, '"');
      }
      buffer.append(str);
    }
    buffer.append("]");
    depth--;
    return buffer.toString();
  }

  /** spec命令の引数値を新しい値に更新する（暫定的措置）*/
  public void updateSpec(int formals, int locals) {
    if (getKind() == SPEC) {
      data.set(0, formals);
      data.set(1, locals);
    }
  }
}

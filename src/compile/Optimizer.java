/*
 * 作成日: 2003/11/30
 */
package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import runtime.Env;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.Rule;
import runtime.functor.Functor;

/**
 * 最適化を行うクラスメソッドを持つクラス。
 * @author Mizuno
 */
public class Optimizer {

  /** 命令列のインライニングを行う*/
  public static boolean fInlining;
  /** 膜の再利用を行う */
  public static boolean fReuseMem;
  /** 命令列のループ化を行う */
  public static boolean fLoop;
  /** 命令列の並び替えを行う */
  public static boolean fGuardMove;
  /** 命令列のグループ化を行う */
  public static boolean fGrouping;
  /** システムルールセットのインライン展開 */
  public static boolean fSystemRulesetsInlining;

  /**
   * 全ての最適化フラグをオフにする
   */
  public static void clearFlag() {
    fInlining = fReuseMem = fLoop = false;
    fGuardMove = fGrouping = fSystemRulesetsInlining = false;
  }

  /**
   * 最適化レベルを設定する。
   * レベルに応じて最適化フラグをオンにする。
   * 低いレベルを指定しても、すでにオンにしてあるフラグをオフにすることはない。
   * 新しい最適化を実装する際には、フラグをフィールドに定義して、ここでオン・オフを行う。
   * @param level 最適化レベル
   */
  public static void setLevel(int level) {
    if (level >= 1) {
      fReuseMem = true;
      fGuardMove = true;
    }
    if (level >= 2) {
      //			ループ化はまだバグがいるので、個別に指定しない限り実行しない
      //			fLoop = true;
      //			fGrouping = true;
    }
    if (level >= 3) {
      // 明示的に指定すると、ボディもくっつける
      // やるべきじゃないかも
      fInlining = true;
    }
  }

  /** ルールオブジェクトを最適化する
   * 命令列に対する最適化機能を追加する時は、ここを最適化メソッドの呼び出し元にするといい。
   *
   *  @param rule ルールオブジェクト
   */
  public static void optimizeRule(Rule rule) {
    // TODO 最適化器を統合する
    // System.err.println("rule.guard0 " + rule.guard);
    Compactor.compactRule(rule);
    // System.err.println("rule.guard1 " + rule.guard);
    // TODO 本質的にインライン展開が必要ないものは、展開しなくてもできるようにする
    // ガードでのシンボルアトムの allocatom は現行の slim で扱えないので
    // 必ず allocatomReduce で消去する必要がある（データアトムは大丈夫）．
    // このため，コンパイルオプションにかかわらず常に head と guard をくっつける
    // if (fInlining || fGuardMove || fGrouping || fReuseMem || fLoop || rule.isTypeDef) {
    if (true) {
      inlineExpandTailJump(rule.memMatch);
      rule.guardLabel = null;
      rule.guard = null;
    }
    if (rule.isTypeDef) {
      rule.memMatch.remove(rule.memMatch.size() - 1);
    }
    optimize(rule.memMatch, rule.body);
    if (fGuardMove) {
      guardMove(rule.memMatch);
      allocMove(rule.memMatch);
    }
    if (fGrouping) {
      Grouping g = new Grouping();
      g.grouping(rule.memMatch);
    }
    allocatomReduce(rule.memMatch, rule.body); // ueda
    if (Env.hyperLinkOpt) findproccxtMove(rule.memMatch); // seiji
    if (fSystemRulesetsInlining) inlineExpandSystemRuleSets(rule.body);
    if (fInlining) {
      // head(+guard) と body をくっつける
      inlineExpandTailJump(rule.memMatch);

      rule.bodyLabel = null;
      rule.body = null;
    }
  }

  /**
   * 渡された命令列を、現在の最適化レベルに応じて最適化する。<br>
   * 命令列中には、1引数のremoveatom/removemem命令が現れていてはいけない。
   * 現在の引数仕様は暫定的なもので、将来変更される予定。
   * @param head 膜主導マッチング命令列
   * @param body ボディ命令列
   */
  public static void optimize(List<Instruction> head, List<Instruction> body) {
    if (fReuseMem) {
      reuseMem(head, body);
      // swaplinkと膜再利用を両方実行すると冗長なremoveatom/addatomができる場合があるので消す
      // TODO: reuseMemにも冗長なremoveatom/addatomを打ち消すような処理があるが、打ち消せていないのはなぜだろう？
      if (Env.useSwapLink) {
        removeUnnecessaryInsts(body);
      }
    }
    if (fLoop) {
      makeLoop(head, body);
    }
  }

  ///////////////////////////////////////////////////////
  // @author n-kato
  // TODO spec命令の身分を考える

  /** 命令列の末尾のjump命令をインライン展開する。
   * @param insts 命令列
   * <pre>{@code
   *     [ spec[X,Y];  C;jump[L,A1..Am] ] where L:[spec[m,m+n];D]
   * ==> [ spec[X,Y+n];C; D{ 1..m->A1..Am, m+1..m+n->Y+1..Y+n } ]
   * }</pre> */
  public static void inlineExpandTailJump(List<Instruction> insts) {
    if (insts.isEmpty()) return;
    Instruction spec = insts.get(0);
    if (spec.getKind() != Instruction.SPEC) return;
    for (int i = 1; i < insts.size(); i++) {
      Instruction branch = insts.get(i);
      if (branch.getKind() != Instruction.BRANCH) break;
      InstructionList label = (InstructionList) branch.getArg1();
      inlineExpandTailJump(label.insts);
    }

    int formals = spec.getIntArg1();
    int locals = spec.getIntArg2();
    locals = inlineExpandTailJump(insts, locals);
    spec.updateSpec(formals, locals);
  }

  /** 命令列の末尾のjump命令をインライン展開する。specはまだ更新されない。
   *  @param insts 命令列
   *  @param varcount 展開前の実引数
   *  @return 展開後の実引数
   *  */
  public static int inlineExpandTailJump(List<Instruction> insts, int varcount) {
    if (insts.isEmpty()) return varcount;
    int size = insts.size();
    Instruction jump = insts.get(size - 1);
    if (jump.getKind() != Instruction.JUMP) return varcount;
    InstructionList label = (InstructionList) jump.getArg1();
    List<Instruction> subinsts = InstructionList.cloneInstructions(label.insts);
    Instruction subspec = subinsts.get(0);

    HashMap map = new HashMap();
    // 仮引数は、実引数番号で置換する。
    List memargs = (List) jump.getArg2();
    List atomargs = (List) jump.getArg3();
    List otherargs = (List) jump.getArg4();
    for (int i = 0; i < memargs.size(); i++) map.put(i, memargs.get(i));
    for (int i = 0; i < atomargs.size(); i++) map.put(memargs.size() + i, atomargs.get(i));
    for (int i = 0; i < otherargs.size(); i++)
      map.put(memargs.size() + atomargs.size() + i, otherargs.get(i));
    // 局所変数は、新鮮な変数番号で置換する。
    int subformals = subspec.getIntArg1();
    int sublocals = subspec.getIntArg2();
    for (int i = subformals; i < sublocals; i++) {
      map.put(i, varcount++);
    }
    Instruction.applyVarRewriteMap(subinsts, map);
    subinsts.remove(0); // specを除去
    insts.remove(size - 1); // jump命令を除去
    insts.addAll(subinsts);
    return varcount;
  }

  // n-kato 終

  // sakurai
  /**
   * ガード命令を可能な限り前に移動させる.
   * ボディ命令は並び替えない
   * @param insts 命令列(headとguardをくっつけたもの)
   */
  public static void guardMove(List<Instruction> insts) {
    for (int i = 1; i < insts.size(); i++) {
      Instruction inst = insts.get(i);

      switch (inst.getKind()) {
          // ボディ命令列は並び替えない -> ボディの先頭はcommit
        case Instruction.COMMIT:
          return;
          // 否定条件は放置(位置を変えない)
          // todo どうするか考える
        case Instruction.NOT:
          continue;
          // 位置を変えたくない命令。他にあればここに追加する。
        case Instruction.FINDATOM:
        case Instruction.ANYMEM:
        case Instruction.NEWLIST:
        case Instruction.PROCEED:
        case Instruction.JUMP:
        case Instruction.RESETVARS:
        case Instruction.UNIQ:
        case Instruction.ADDTOLIST:
          continue;
          // 引数に命令列を持つ命令
        case Instruction.GROUP:
        case Instruction.BRANCH:
          InstructionList subinsts = (InstructionList) inst.getArg1();
          guardMove(subinsts.insts);
          break;
          // 上に該当しない命令は、その引数の変数番号が定義された命令より前にならない限り、
          // 前に動かせる。
        default:
          int judge = guardMove(insts, inst, i - 1);
          if (judge == 2) {
            insts.remove(i);
            i--;
          } else if (judge == 1) {
            insts.remove(i + 1);
          }
      }
    }
  }

  /* 中間命令findproccxtをfindatomよりも前に移動させる */
  private static void findproccxtMove(List<Instruction> insts) { // seiji
    int insert = 0, max = insts.size();
    for (int i = 0; i < max; i++) {
      if (insts.get(i).getKind() == Instruction.FINDATOM) {
        insert = i;
        break;
      }
    }
    for (int i = 0; i < max; i++) {
      if (insts.get(i).getKind() == Instruction.FINDPROCCXT) {
        Instruction ins = insts.get(i);
        insts.remove(i);
        insts.add(insert, ins);
      }
    }
  }

  /** ガードでの allocatom に続く getfunc を loadfunc に変換する
   * allocatom は単項アトムにしか使わないので，isunary の検査も消去する
   * allocatom が参照されなくなった（または最初からされない）場合は
   * 対応する freeatom とともに除去する
   * @param match ガードまでの命令列
   * @param body  右辺命令列
   */
  // TODO: ガードで本来必要なのはファンクタだけなので，要らなくなるアトムを
  // 作って解放するのでなく，最初からファンクタだけを扱うのが望ましい
  private static void allocatomReduce(List<Instruction> match, List<Instruction> body) { // ueda
    int maxm = match.size();
    int maxb = body.size();

    // typedef のコンパイル時に呼ばれた場合（命令列 match の最後が
    // jump でなく succreturn の場合）は何もしない
    Instruction jump = match.get(maxm - 1);
    // System.err.println("jump: " + jump);
    if (jump.getKind() != Instruction.JUMP) return;

    // 通常ルールの場合は，freeatom 命令の消去作業に備えて，
    // allocatom (guard), freeatom (body) のレジスタ番号の対応関係表を作る
    // 対応関係はガードからボディへのjump命令からわかる
    List memargs = (List) jump.getArg2();
    List atomargs = (List) jump.getArg3();
    HashMap map = new HashMap();
    for (int i = 0; i < atomargs.size(); i++) {
      map.put(atomargs.get(i), memargs.size() + i);
    }
    // System.err.println("map: " + map);

    for (int i = 0; i < maxm; i++) {
      if (match.get(i).getKind() == Instruction.ALLOCATOM) {
        boolean referred = false;
        int allocreg = (Integer) match.get(i).getArg1();
        Functor func = (Functor) match.get(i).getArg2();
        for (int j = i + 1; j < maxm; j++) {
          if (match.get(j).getKind() == Instruction.GETFUNC) {
            int setreg = (Integer) match.get(j).getArg1();
            int refreg = (Integer) match.get(j).getArg2();
            if (allocreg == refreg) {
              match.remove(j);
              match.add(j, new Instruction(Instruction.LOADFUNC, setreg, func));
            }
          } else if (match.get(j).getKind() == Instruction.ISUNARY) {
            int refreg = (Integer) match.get(j).getArg1();
            if (allocreg == refreg) {
              match.remove(j);
              j--;
              maxm--;
            }
          } else {
            Instruction inst = match.get(j);
            int size = inst.data.size();
            for (int k = 0; k < size; k++) {
              if (inst.getArgType(k) == Instruction.ARG_ATOM) {
                if ((Integer) inst.data.get(k) == allocreg) {
                  // System.err.println("ALLOCATOM referred! " + inst);
                  referred = true;
                  break;
                }
              }
            }
          }
        }
        if (!referred) {
          match.remove(i);
          i--;
          maxm--;
          for (int j = 0; j < maxb; j++) {
            if (body.get(j).getKind() == Instruction.FREEATOM) {
              int freereg = (Integer) body.get(j).getArg1();
              // System.err.println("allocreg, freereg: " + allocreg + " " + freereg);
              if ((int) map.get(allocreg) == freereg) {
                body.remove(j);
                maxb--;
              }
            }
          }
        }
      }
    }
  }

  private static int max(int m, int n) {
    if (m > n) return m;
    else return n;
  }

  private static int guardMove(List<Instruction> insts, Instruction inst, int locate) {
    int moveok = 0; // 移動可能判定フラグ
    ArrayList list;
    HashMap listn = new HashMap();
    int i = locate;
    int insert_index = 0;
    ff:
    for (; i >= 0; i--) {
      list = inst.getVarArgs(listn);
      Instruction inst2 = insts.get(i);
      if (inst2.getKind() == Instruction.GROUP) {
        InstructionList subinsts = (InstructionList) inst2.getArg1();
        moveok = max(moveok, guardMove(subinsts.insts, inst, subinsts.insts.size() - 1));
        if (moveok > 0) {
          moveok = 2;
          i = 0;
          continue;
        }
        continue;
      } else if (inst2.getKind() == Instruction.BRANCH) {
        InstructionList subinsts = (InstructionList) inst2.getArg1();
        Instruction instrep = (Instruction) inst.clone();
        moveok = max(moveok, guardMove(subinsts.insts, inst, subinsts.insts.size() - 1));
        if (moveok > 0) {
          moveok = 2;
        }
        inst = instrep;
        continue;
      } else if (inst2.getKind() == Instruction.RESETVARS) {
        int memnum = ((List) inst2.getArg1()).size();
        ArrayList mems = (ArrayList) inst2.getArg1();
        ArrayList atoms = (ArrayList) inst2.getArg2();

        for (int j = 0; j < list.size(); j++) {
          int num = (Integer) list.get(j);
          // getVarArgsのうち、j番目に設定すべき
          if (num < memnum) {
            inst.data.set((Integer) listn.get(j), (Integer) mems.get(num));
          } else if ((num - memnum) >= atoms.size() || num - memnum < 0) {
            continue;
          } else {
            inst.data.set((Integer) listn.get(j), (Integer) atoms.get(num - memnum));
          }
          //						inst.setArg(j+1, new Integer(((Integer)atoms.get(num - memnum)).intValue()));
        }
        continue;
      }

      // 　instをinst2の下に配置するべきか判定
      ArrayList list2 = inst2.getVarArgs(listn);
      if (inst.getKind() == Instruction.ALLOCATOM || inst.getKind() == Instruction.NEWLIST) {
        if (list2.contains(inst.getArg1())) {
          moveok = max(moveok, 1);
          insert_index = i;
          //						i--;
          //						break ff;
        }
      } else {
        for (int j = 0; j < list.size(); j++) {
          if (inst2.getKind() == Instruction.ALLOCATOM || inst2.getKind() == Instruction.NEWLIST)
            continue;
          if (inst2.getOutputType() != -1) {
            if (list.get(j).equals(inst2.getArg1())) {
              moveok = max(moveok, 1);
              insert_index = i + 1;
              break ff;
            }
          } else if (list2.contains(list.get(j))) {
            moveok = max(moveok, 1);
            insert_index = i + 1;
            break ff;
          }
        }
        //					if(!moveok) break;
      }
    }
    if (moveok > 0) {
      if (insert_index != 0) {
        insts.add(insert_index, inst);
      }
      return moveok;
    }
    return 0;
  }

  /**
   * ガード命令を可能な限り前に移動させる.
   * ボディ命令は並び替えない
   * @param insts 命令列(headとguardをくっつけたもの)
   */
  public static void allocMove(List<Instruction> insts) {
    for (int i = 1; i < insts.size(); i++) {
      Instruction inst = insts.get(i);
      switch (inst.getKind()) {
        case Instruction.ALLOCATOM:
        case Instruction.NEWLIST:
          int judge = guardMove(insts, inst, i - 1);
          if (judge == 2) {
            insts.remove(i);
            i--;
          } else if (judge == 1) {
            insts.remove(i + 1);
          }
          break;
        case Instruction.GROUP:
        case Instruction.BRANCH:
          InstructionList subinsts = (InstructionList) inst.getArg1();
          allocMove(subinsts.insts);
          break;
        default:
          continue;
      }
    }
  }

  /**
   * システムルールセットをボディ命令列中に展開する
   * @param body ボディ命令列
   */
  private static void inlineExpandSystemRuleSets(List<Instruction> body) {
    Instruction spec = body.get(0);
    if (spec.getKind() != Instruction.SPEC) return;
    int locals = spec.getIntArg2();
    HashMap getlinkmap = new HashMap();
    HashSet<Instruction> removelinks = new HashSet<>();
    InstructionList inline1;
    InstructionList inline2;
    HashSet<Instruction> newlink = new HashSet<>();
    HashSet<Instruction> newlinks2;
    HashMap<Integer, Integer> old2new = new HashMap<>();
    for (int i = 1; i < body.size(); i++) {
      inline1 = new InstructionList();
      inline2 = new InstructionList();
      newlinks2 = new HashSet<>();
      Instruction inst = body.get(i);
      if (inst.getKind() == Instruction.GETLINK) {
        if (!getlinkmap.containsKey(inst.getArg1())) getlinkmap.put(inst.getArg1(), inst);
      }
      if (inst.getKind() == Instruction.NEWATOM) {
        int newlinkmem = 0;
        Functor func = (Functor) inst.getArg3();
        String funcname = func.getName();
        int funcarity = func.getArity();
        int newatomvar = inst.getIntArg1();
        int arg1, arg2, result, resultlink;
        int op, typecheck;
        op = 0;
        typecheck = 0;
        arg1 = arg2 = result = resultlink = -1;
        if (funcarity == 3 && funcname.equals("+")
            || funcname.equals("-")
            || funcname.equals("*")
            || funcname.equals("/")
            || funcname.equals("mod")
            || funcname.equals("logand")
            || funcname.equals("logior")
            || funcname.equals("logxor")
            || funcname.equals("ash")
            || funcname.equals("+.")
            || funcname.equals("-.")
            || funcname.equals("*.")
            || funcname.equals("/.")) {
          if (funcname.equals("+")) {
            op = Instruction.IADD;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("-")) {
            op = Instruction.ISUB;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("*")) {
            op = Instruction.IMUL;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("/")) {
            op = Instruction.IDIV;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("mod")) {
            op = Instruction.IMOD;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("logand")) {
            op = Instruction.IAND;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("logior")) {
            op = Instruction.IOR;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("logxor")) {
            op = Instruction.IXOR;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("ash")) {
            op = Instruction.ISAL;
            typecheck = Instruction.ISINT;
          } else if (funcname.equals("+.")) {
            op = Instruction.FADD;
            typecheck = Instruction.ISFLOAT;
          } else if (funcname.equals("-.")) {
            op = Instruction.FSUB;
            typecheck = Instruction.ISFLOAT;
          } else if (funcname.equals("*.")) {
            op = Instruction.FMUL;
            typecheck = Instruction.ISFLOAT;
          } else if (funcname.equals("/.")) {
            op = Instruction.FDIV;
            typecheck = Instruction.ISFLOAT;
          }
          for (int j = i + 1; j < body.size(); j++) {
            Instruction inst2 = (Instruction) body.get(j);
            if (inst2.getKind() == Instruction.NEWLINK) {
              if (inst2.getIntArg1() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                if (inst2.getIntArg2() == 2) {
                  result = inst2.getIntArg3();
                  resultlink = inst2.getIntArg4();
                  newlinkmem = inst2.getIntArg5();
                } else {
                  if (inst2.getIntArg2() == 0) arg1 = inst2.getIntArg3();
                  else arg2 = inst2.getIntArg3();
                }
              } else if (inst2.getIntArg3() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                if (inst2.getIntArg4() == 2) {
                  result = inst2.getIntArg1();
                  resultlink = inst2.getIntArg2();
                  newlinkmem = inst2.getIntArg5();
                } else {
                  if (inst2.getIntArg4() == 0) arg1 = inst2.getIntArg1();
                  else arg2 = inst2.getIntArg1();
                }
              }
            } else if (inst2.getKind() == Instruction.RELINK) {
              if (inst2.getIntArg1() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                if (inst2.getIntArg2() == 2) {
                  result = inst2.getIntArg3();
                  resultlink = inst2.getIntArg4();
                  newlinkmem = inst2.getIntArg5();
                } else {
                  inline1.add(
                      new Instruction(
                          Instruction.DEREFATOM, locals, inst2.getIntArg3(), inst2.getIntArg4()));
                  if (inst2.getIntArg2() == 0) arg1 = locals;
                  else arg2 = locals;
                }
                locals++;
              } else if (inst2.getIntArg3() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                if (inst2.getIntArg4() == 2) {
                  result = inst2.getIntArg1();
                  resultlink = inst2.getIntArg2();
                  newlinkmem = inst2.getIntArg5();
                } else {
                  inline1.add(
                      new Instruction(
                          Instruction.DEREFATOM, locals, inst2.getIntArg3(), inst2.getIntArg4()));
                  if (inst2.getIntArg4() == 0) arg1 = locals;
                  else arg2 = locals;
                }
                locals++;
              }
            } else if (inst2.getKind() == Instruction.INHERITLINK) {
              if (inst2.getIntArg1() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                newlinkmem = inst2.getIntArg4();
                if (getlinkmap.containsKey(inst2.getArg3())) {
                  Instruction getlink = (Instruction) getlinkmap.get(inst2.getArg3());
                  inline1.add(
                      new Instruction(
                          Instruction.DEREFATOM,
                          locals,
                          getlink.getIntArg2(),
                          getlink.getIntArg3()));
                  inline2.add(getlink);
                  if (!removelinks.contains(getlink)) removelinks.add(getlink);
                }
                if (arg1 == -1) arg1 = locals;
                else arg2 = locals;
                locals++;
              } else if (inst2.getIntArg3() == newatomvar) {
                if (!newlink.contains(inst2)) {
                  newlink.add(inst2);
                  newlinks2.add(inst2);
                }
                if (!removelinks.contains(inst2)) removelinks.add(inst2);
                newlinkmem = inst2.getIntArg4();
                if (getlinkmap.containsKey(inst2.getArg1())) {
                  Instruction getlink = (Instruction) getlinkmap.get(inst2.getArg1());
                  inline1.add(
                      new Instruction(
                          Instruction.DEREFATOM,
                          locals,
                          getlink.getIntArg2(),
                          getlink.getIntArg3()));
                  inline2.add(getlink);
                  if (!removelinks.contains(getlink)) removelinks.add(getlink);
                }
                if (inst2.getIntArg2() == 1) arg1 = locals;
                else arg2 = locals;
                locals++;
              }
            }

            if (arg1 != -1 && arg2 != -1 && result != -1 && resultlink != -1) {
              // システムルールセット成功時の命令列 inline1
              if (old2new.containsKey(arg1)) arg1 = old2new.get(arg1);
              if (old2new.containsKey(arg1)) arg2 = old2new.get(arg2);

              inline1.add(new Instruction(typecheck, arg1));
              inline1.add(new Instruction(typecheck, arg2));
              inline1.add(new Instruction(op, locals, arg1, arg2));
              inline1.add(new Instruction(Instruction.ADDATOM, newlinkmem, locals));
              inline1.add(
                  new Instruction(Instruction.NEWLINK, locals, 0, result, resultlink, newlinkmem));

              inline1.add(new Instruction(Instruction.REMOVEATOM, arg1, newlinkmem));
              inline1.add(new Instruction(Instruction.REMOVEATOM, arg2, newlinkmem));
              inline1.add(new Instruction(Instruction.REMOVEATOM, newatomvar, newlinkmem));
              inline1.add(new Instruction(Instruction.FREEATOM, arg1));
              inline1.add(new Instruction(Instruction.FREEATOM, arg2));
              inline1.add(new Instruction(Instruction.PROCEED));
              if (!old2new.containsKey(newatomvar)) old2new.put(newatomvar, locals);
              locals++;

              // システムルールセット失敗時の命令列 inline2
              Iterator<Instruction> it = newlinks2.iterator();
              while (it.hasNext()) inline2.add(it.next());
              inline2.add(new Instruction(Instruction.PROCEED));
              break;
            } else continue;
          }
        }
      }
    }

    body.remove(0);
    body.add(0, new Instruction(Instruction.SPEC, spec.getIntArg1(), locals));
    //		不要なnewlinkの除去
    for (int i2 = 0; i2 < body.size(); i2++) {
      Instruction inst2 = body.get(i2);
      if (removelinks.contains(inst2)) body.remove(i2--);
    }
  }

  ///////////////////////////////////////////////////////
  // 膜最適化関連

  /**
   * 膜の再利用を行うコードを生成する。<br>
   * 命令列中には、1引数のremovemem命令が現れていてはいけない。
   * 命令列の最後はproceed命令でなければならない。
   * @param head ヘッド命令列
   * @param body ボディ命令列
   */
  private static void reuseMem(List<Instruction> head, List<Instruction> body) {
    Instruction spec = body.get(0);
    if (spec.getKind() != Instruction.SPEC) {
      return;
    }
    Instruction last = body.get(body.size() - 1);
    if (last.getKind() != Instruction.PROCEED) {
      return;
    }
    // バグ回避
    for (Iterator<Instruction> itb = body.iterator(); itb.hasNext(); ) {
      int itbKind = itb.next().getKind();
      if (itbKind == Instruction.COPYCELLS || itbKind == Instruction.DROPMEM) return;
    }

    HashMap<Integer, Integer> reuseMap = new HashMap<>();
    HashSet<Integer> reuseMems = new HashSet<>(); // 再利用される膜のIDの集合
    HashMap parent = new HashMap();
    HashMap<Integer, List<Integer>> removedChildren = new HashMap<>(); // map -> list of children
    HashMap<Integer, List<Integer>> createdChildren = new HashMap<>(); // map -> list of children
    HashMap<Integer, List<Integer>> pourMap = new HashMap<>();
    HashSet pourMems = new HashSet(); // pour命令の第２引数に含まれる膜
    HashMap<Integer, List<Integer>> copyRulesMap = new HashMap<>();

    HashMap<Integer, String> headMemName = new HashMap<>(); // head に関する膜から膜名への map
    HashMap<Integer, String> bodyMemName = new HashMap<>(); // body に関する膜から膜名への map

    // 再利用する膜の組み合わせを決定する
    for (Instruction inst : body) {
      switch (inst.getKind()) {
        case Instruction.REMOVEMEM:
          parent.put(inst.getArg1(), inst.getArg2());
          addToMap(removedChildren, inst.getArg2(), inst.getArg1());
          break;
        case Instruction.NEWMEM:
          parent.put(inst.getArg1(), inst.getArg2());
          addToMap(createdChildren, inst.getArg2(), inst.getArg1());
          break;
        case Instruction.MOVECELLS:
          addToMap(pourMap, inst.getArg1(), inst.getArg2());
          pourMems.add(inst.getArg2());
          break;
        case Instruction.COPYRULES:
          addToMap(copyRulesMap, inst.getArg2(), inst.getArg1());
          break;
      }
    }

    // 膜名を記憶する
    for (Instruction inst : head) {
      switch (inst.getKind()) {
        case Instruction.LOCKMEM:
          headMemName.put((Integer) inst.getArg1(), (String) inst.getArg3());
          break;
        case Instruction.ANYMEM:
          headMemName.put((Integer) inst.getArg1(), (String) inst.getArg4());
          break;
      }
    }
    for (Instruction inst : body) {
      switch (inst.getKind()) {
        case Instruction.SETMEMNAME:
          bodyMemName.put((Integer) inst.getArg1(), (String) inst.getArg2());
      }
    }

    createReuseMap(
        reuseMap, reuseMems, parent, removedChildren, createdChildren, pourMap, pourMems, 0);

    // 命令列を書き換える
    // その際、冗長なremovemem/addmem命令を除去する
    HashSet<Integer> set = new HashSet<>(); // removemem/addmem命令の不要な膜再利用に関わる膜
    for (Iterator<Integer> it = reuseMap.keySet().iterator(); it.hasNext(); ) {
      Integer i1 = it.next();
      Integer i2 = reuseMap.get(i1);
      Integer p1 = (Integer) parent.get(i1);
      Integer p2 = (Integer) parent.get(i2);
      if (reuseMap.containsKey(p1)) {
        p1 = reuseMap.get(p1);
      }
      if (p1.equals(p2)) { // 親が同じだったら
        set.add(i1);
        set.add(i2);
      }
    }

    // ルールの退避
    HashMap<Integer, Integer> ruleMem = new HashMap<>(); // ルールを退避した膜
    HashMap<Integer, Integer> varInBody = new HashMap<>(); // ヘッドでの変数名→ボディでの変数名

    Instruction react = head.get(head.size() - 1);
    if (react.getKind() != Instruction.REACT && react.getKind() != Instruction.JUMP) {
      return;
    }
    int i = 0;
    ArrayList args = (ArrayList) react.getArg2();
    for (Iterator it = args.iterator(); it.hasNext(); ) {
      varInBody.put((Integer) it.next(), i++);
    }

    for (Iterator<Instruction> it = head.iterator(); it.hasNext(); ) {
      Instruction inst = it.next();
      if (inst.getKind() == Instruction.NORULES) {
        // ルール退避の対象から外す
        varInBody.remove(inst.getArg1());
      }
    }
    // 退避する命令の生成
    ArrayList<Instruction> tmpInsts = new ArrayList<>();
    int nextArg = spec.getIntArg2();
    for (Iterator<Integer> it = varInBody.keySet().iterator(); it.hasNext(); ) {
      Integer memInHead = it.next();
      Integer mem = varInBody.get(memInHead);
      if (reuseMems.contains(mem)) { // 再利用元の膜の場合
        List<Integer> copyRulesTo = copyRulesMap.get(mem);
        boolean flg = false;
        if (copyRulesTo == null) {
          // 右辺にルール文脈が出現しないので退避不要、削除のみ
          tmpInsts.add(new Instruction(Instruction.CLEARRULES, mem));
        } else {
          for (Integer dstmem : copyRulesTo) {
            if (mem.equals(reuseMap.get(dstmem))) {
              flg = true; // 自分にコピーしているので退避不要
              break;
            }
          }
          if (!flg) {
            // 退避処理
            ruleMem.put(mem, nextArg);
            tmpInsts.add(new Instruction(Instruction.ALLOCMEM, nextArg));
            tmpInsts.add(new Instruction(Instruction.COPYRULES, nextArg, mem));
            tmpInsts.add(new Instruction(Instruction.CLEARRULES, mem));
            nextArg++;
          }
        }
      }
    }
    body.addAll(2, tmpInsts); /* 最初のボディ命令はcommit，その直後に入れる */

    // TODO removeproxies/insertproxies命令を適切に変更する
    ListIterator<Instruction> lit = body.listIterator();
    while (lit.hasNext()) {
      Instruction inst = lit.next();
      switch (inst.getKind()) {
        case Instruction.REMOVEMEM:
          if (set.contains(inst.getArg1())) {
            lit.remove();
          }
          break;
        case Instruction.NEWMEM:
          Integer arg1 = (Integer) inst.getArg1();
          if (reuseMap.containsKey(arg1)) {
            lit.remove();
            // addmem・enqueuemem命令に変更
            int m = (Integer) reuseMap.get(arg1);
            if (!set.contains(arg1)) {
              lit.add(new Instruction(Instruction.ADDMEM, inst.getIntArg2(), m));
            }
            lit.add(new Instruction(Instruction.ENQUEUEMEM, m));

            // null でない膜名から null な膜名へ再利用するときには setmemname が必要 (バグ/090207_-O2膜の複製バグ)
            if (bodyMemName.get(arg1) == null && headMemName.get(args.get(m)) != null) {
              lit.add(new Instruction(Instruction.SETMEMNAME, m, null));
            }
          }
          break;
        case Instruction.MOVECELLS:
          if (reuseMems.contains(inst.getArg2())) {
            // addmem命令で移動が完了しているため除去
            // ※ある膜が、2つ以上の膜の再利用の根拠となることはない
            lit.remove();
          }
          break;
          //				case Instruction.LOADRULESET:
          //				//ルールを退避しない場合に備えて最後に移動
          //				tmpInsts.add(inst);
          //				lit.remove();
          //				break;
        case Instruction.COPYRULES:
          Integer srcmem = (Integer) inst.getArg2();
          Integer dstmem = (Integer) inst.getArg1();
          if (ruleMem.containsKey(srcmem)) {
            if (!ruleMem.get(srcmem).equals(dstmem)) { // 退避のための命令でなければ
              // 退避した膜からのコピーに変更
              lit.remove();
              lit.add(
                  new Instruction(
                      Instruction.COPYRULES,
                      dstmem.intValue(),
                      ((Integer) ruleMem.get(srcmem)).intValue()));
            }
          } else if (srcmem.equals(reuseMap.get(dstmem))) {
            // 自分へのコピーなので削除
            lit.remove();
          }
          break;
        case Instruction.FREEMEM:
          if (reuseMems.contains(inst.getArg1())) {
            lit.remove();
          }
          break;
      }
    }
    lit.previous(); // 最後のproceed命令の手前に追加
    //		//loadruleset命令の移動
    //		it = tmpInsts.iterator();
    //		while (it.hasNext()) {
    //		lit.add(it.next());
    //		}
    // 再利用した膜のunlockmem命令の追加
    addUnlockInst(lit, reuseMap, 0, createdChildren);
    // 退避に使用した膜の解放
    for (Iterator<Integer> it = ruleMem.values().iterator(); it.hasNext(); ) {
      lit.add(new Instruction(Instruction.FREEMEM, it.next()));
    }

    // specの変更
    //		body.set(0, Instruction.spec(spec.getIntArg1(), nextArg));
    spec.updateSpec(spec.getIntArg1(), nextArg);

    Instruction.changeMemVar(body, reuseMap);
  }

  /*
  	removeatom [X, Y]
  	addatom   [Y, X]
  	となっている場合これらの命令列は冗長なので消す
  */
  public static void removeUnnecessaryInsts(List<Instruction> body) {
    ListIterator<Instruction> lit = body.listIterator();
    while (lit.hasNext()) {
      Instruction inst = lit.next();
      if (inst.getKind() == Instruction.REMOVEATOM && lit.hasNext()) {
        Instruction inst2 = lit.next();
        if (inst2.getKind() == Instruction.ADDATOM) {
          if (inst.getArg1() == inst2.getArg2() && inst.getArg2() == inst2.getArg1()) {
            // 同じ膜から同じアトムをremoveしてaddしていたら
            lit.remove();
            lit.previous();
            lit.remove();
          }
        }
      }
    }
  }

  private static void addUnlockInst(
      ListIterator<Instruction> lit,
      HashMap<Integer, Integer> reuseMap,
      Integer mem,
      HashMap<Integer, List<Integer>> children) {
    // 子膜を先に処理
    List<Integer> c = children.get(mem);
    if (c != null) {
      Iterator<Integer> it = c.iterator();
      while (it.hasNext()) {
        addUnlockInst(lit, reuseMap, it.next(), children);
      }
    }
  }

  /**
   * Listを値とするようなマップに値を追加する。
   * 指摘されたキーがすでに存在する場合は値のリストにvalueを追加する。
   * 存在しない場合は新しくリストを登録し、マップに追加する。
   * @param map マップ
   * @param key キー
   * @param value 値
   */
  private static void addToMap(HashMap map, Object key, Object value) {
    ArrayList list = (ArrayList) map.get(key);
    if (list == null) {
      list = new ArrayList();
      map.put(key, list);
    }
    list.add(value);
  }

  /**
   * 再利用する膜を決定します。
   * startで指定された膜内にある膜について、再帰呼び出しを行います。
   * @param reuseMap 再利用方法を入れるためのマップ
   * @param reuseMems 再利用される膜の集合を入れるためのセット
   * @param parent 親膜へのマップ
   * @param children 子膜集合へのマップ
   * @param pourMap pour命令の対応を入れたマップ
   * @param pourMems pour命令の第２引数に含まれる膜のセット
   * @param start この膜内の膜の再利用を決定する。
   */
  private static void createReuseMap(
      HashMap<Integer, Integer> reuseMap,
      HashSet<Integer> reuseMems,
      HashMap parent,
      HashMap<Integer, List<Integer>> removedChildren,
      HashMap<Integer, List<Integer>> createdChildren,
      HashMap<Integer, List<Integer>> pourMap,
      HashSet pourMems,
      Integer start) {
    List<Integer> list = createdChildren.get(start);
    if (list == null || list.size() == 0) {
      return;
    }

    Integer start2; // startの、再利用後の変数番号
    if (reuseMap.containsKey(start)) {
      start2 = reuseMap.get(start);
    } else {
      start2 = start;
    }
    for (int mem : list) {
      // memの再利用元を決める
      Integer candidate = null; // pour命令による再利用候補を１つ保持しておく
      Integer result = null; // 決定した再利用先を入れる
      List<Integer> list2 = pourMap.get(mem);
      if (list2 != null) {
        for (int mem2 : list2) {
          // すでに再利用することが決まっている場合は無視
          if (reuseMems.contains(mem2)) {
            continue;
          }

          // 候補に登録
          candidate = mem2;

          if (parent.get(mem2).equals(start2)) {
            // 親膜が同じ膜からのpour命令がある場合は、それを優先
            result = mem2;
            break;
          }
        }
      }
      if (result == null) {
        // 上の方法で決まらなかった場合
        if (candidate == null) {
          // 共通の親膜を持つ、プロセス文脈のない膜の中から適当に決定。
          // 該当する膜がなければ再利用しない
          List<Integer> list3 = removedChildren.get(start2);
          if (list3 != null) {
            for (int m : list3) {
              if (!pourMems.contains(m) && !reuseMems.contains(m)) {
                result = m;
                break;
              }
            }
          }
        } else {
          // pour命令がある中から適当に決定
          result = candidate;
        }
      }
      if (result != null) {
        reuseMap.put(mem, result);
        reuseMems.add(result);
      }
      // 再帰呼び出し
      createReuseMap(
          reuseMap, reuseMems, parent, removedChildren, createdChildren, pourMap, pourMems, mem);
    }
  }

  //////////////////////////////////////////////////////////////////////
  // アトム再利用関連

  /**
   * relink命令をgetlink/inheritlink命令に変換し、
   * getlink/unify命令をボディ命令列の先頭に移動する。
   * 先頭の命令はspecでなければならない。
   * @param list 変換する命令列
   * @return 変換に成功した場合はtrue
   */
  public static boolean changeOrder(List<Instruction> list) {
    Instruction spec = list.get(0);
    if (spec.getKind() != Instruction.SPEC) {
      return false;
    }
    int nextId = spec.getIntArg2();

    List<Instruction> moveInsts = new ArrayList<>();
    for (ListIterator<Instruction> it = list.listIterator(1); it.hasNext(); ) {
      Instruction inst = it.next();
      switch (inst.getKind()) {
        case Instruction.UNIFY:
          moveInsts.add(inst);
          it.remove();
          break;
        case Instruction.RELINK:
          moveInsts.add(
              new Instruction(Instruction.GETLINK, nextId, inst.getIntArg3(), inst.getIntArg4()));
          it.set(
              new Instruction(
                  Instruction.INHERITLINK,
                  inst.getIntArg1(),
                  inst.getIntArg2(),
                  nextId,
                  inst.getIntArg5()));
          nextId++;
          break;
      }
    }
    //		list.set(0, Instruction.spec(spec.getIntArg1(), nextId));
    spec.updateSpec(spec.getIntArg1(), nextId);
    if (list.size() >= 2 && ((Instruction) list.get(1)).getKind() == Instruction.COMMIT)
      list.addAll(2, moveInsts); // (061128okabe) commitがある場合は２番目でなければならない．
    else list.addAll(1, moveInsts);
    //		spec.data.set(1, new Integer(nextId)); //ローカル変数の数を変更
    return true;
  }

  //////////////////////////////////////////////////////////////
  // ループ化関連

  /**
   * アトムと引数番号の組を保持するクラス。
   * @author Ken
   */
  private static class Link {

    int atom;
    int pos;

    Link(int atom, int pos) {
      this.atom = atom;
      this.pos = pos;
    }

    public boolean equals(Object o) {
      if (o == null) {
        return false;
      }
      Link l = (Link) o;
      return this.atom == l.atom && this.pos == l.pos;
    }

    public int hashCode() {
      return atom + pos;
    }

    public String toString() {
      return "(" + atom + ", " + pos + ")";
    }
  }

  /**
   * 同一ルールの複数回同時適用<br>
   * とりあえず、次の条件を満たしている場合にのみ処理を行う。
   * <ul>
   *  <li>ボディ実行仮引数と実引数が同じである。
   *  <li>spec命令以外の最初の命令がfindatomで、その第二引数は0である。
   *  <li>はじめのfindatom命令によって取得されたアトムが再利用されている。
   *  <li>derefatom,dereffuncを利用していない
   * </ul>
   * 条件が満たされない場合は何もしない。
   * @param head 膜主導マッチング命令列
   * @param body ボディ命令列
   */
  private static void makeLoop(List<Instruction> head, List<Instruction> body) {
    Instruction inst = head.get(0);
    if (inst.getKind() != Instruction.SPEC) {
      return;
    }
    //		if (!inst.getArg2().equals(new Integer(0))) {
    //		//マッチング命令列にローカル変数がある場合
    //		return;
    //		}
    inst = head.get(1);
    if (inst.getKind() != Instruction.FINDATOM || inst.getIntArg2() != 0) {
      return;
    }
    Integer firstAtom = (Integer) inst.getArg1();

    // 条件に合致するか検査＋情報収集
    HashMap<Link, Link> links = new HashMap<>(); // newlink命令で生成したリンクの情報
    HashMap linkGetFrom = new HashMap(); // リンク -> getlinkした(atom,pos)
    HashMap functor = new HashMap(); // atom -> functor
    HashMap inherit = new HashMap(); // (atom,pos) -> inheritするリンク
    // 種類ごとに変数一覧を作成
    ArrayList memvars = new ArrayList();
    ArrayList atomvars = new ArrayList();
    ArrayList othervars = new ArrayList();
    Instruction react = head.get(head.size() - 1);
    //		Iterator it = ((List)react.getArg2()).iterator();
    //		while (it.hasNext()) {
    //		memvars.add(it.next());
    //		}
    //		it = ((List)react.getArg3()).iterator();
    //		while (it.hasNext()) {
    //		atomvars.add(it.next());
    //		}
    HashMap varInBody = new HashMap();

    List memlist = (List) react.getArg2();
    for (int i = 0; i < memlist.size(); i++) {
      memvars.add(i);
      varInBody.put(memlist.get(i), i);
    }
    List atomlist = (List) react.getArg3();
    Integer newFirstAtom = firstAtom;
    for (int i = 0; i < atomlist.size(); i++) {
      atomvars.add(memlist.size() + i);
      if (firstAtom.equals(atomlist.get(i))) {
        newFirstAtom = memlist.size() + i;
      }
      varInBody.put(atomlist.get(i), memlist.size() + i);
    }
    firstAtom = newFirstAtom;
    //		added by <<n-kato
    List otherlist = (List) react.getArg4();
    for (int i = 0; i < otherlist.size(); i++) {
      othervars.add(i);
      varInBody.put(otherlist.get(i), memlist.size() + atomlist.size() + i);
    }
    //		n-kato
    for (ListIterator<Instruction> lit = head.listIterator(); lit.hasNext(); ) {
      inst = lit.next();
      switch (inst.getKind()) {
        case Instruction.FINDATOM:
          functor.put(varInBody.get(inst.getArg1()), inst.getArg3());
          break;
        case Instruction.FUNC:
          functor.put(varInBody.get(inst.getArg1()), inst.getArg2());
          break;
      }
    }
    for (ListIterator<Instruction> lit = body.listIterator(); lit.hasNext(); ) {
      inst = lit.next();
      switch (inst.getOutputType()) {
        case Instruction.ARG_ATOM:
          atomvars.add(inst.getArg1());
          break;
        case Instruction.ARG_MEM:
          memvars.add(inst.getArg1());
          break;
        case Instruction.ARG_VAR:
          othervars.add(inst.getArg1());
          break;
        case -1:
          break;
        default:
          throw new RuntimeException("invalid output type : " + inst);
      }
      switch (inst.getKind()) {
        case Instruction.DEREFATOM:
        case Instruction.DEREFFUNC:
          //				System.out.println(inst);
          return;
        case Instruction.REMOVEATOM:
          //				case Instruction.FREEATOM:
          if (inst.getArg1().equals(firstAtom)) {
            //					System.out.println(inst);
            return;
          }
          break;
        case Instruction.NEWLINK:
          Link l1 = new Link(inst.getIntArg1(), inst.getIntArg2());
          Link l2 = new Link(inst.getIntArg3(), inst.getIntArg4());
          links.put(l1, l2);
          links.put(l2, l1);
          break;
        case Instruction.INHERITLINK:
          Link l = new Link(inst.getIntArg1(), inst.getIntArg2());
          inherit.put(l, inst.getArg3());
          break;
        case Instruction.NEWATOM:
          functor.put(inst.getArg1(), inst.getArg3());
          //				atomvars.add(inst.getArg1());
          break;
        case Instruction.GETLINK:
          linkGetFrom.put(inst.getArg1(), new Link(inst.getIntArg2(), inst.getIntArg3()));
          //				othervars.add(inst.getArg1());
          break;
      }
    }

    // ループ内命令列の生成

    // まずはコピーして変数番号付け替え
    Instruction spec = body.get(0);

    List<Instruction> loop = new ArrayList<>(); // ループ内の命令列
    // マッチング命令列
    for (ListIterator<Instruction> lit = head.subList(2, head.size() - 1).listIterator();
        lit.hasNext(); ) { // spec,findatom,react/jumpを除去
      loop.add((Instruction) lit.next().clone());
    }
    if (react.getKind() != Instruction.REACT && react.getKind() != Instruction.JUMP) {
      //			System.out.println(react);
      return;
    }
    // ボディ命令列に変数を合わせる
    Instruction.applyVarRewriteMap(loop, varInBody);
    // ボディ命令列
    for (ListIterator<Instruction> lit = body.listIterator(1); lit.hasNext(); ) { // specを除去
      loop.add((Instruction) lit.next().clone());
    }

    // ループ内変数の付け替え

    // もともとの変数→ループ内で、再定義する場合の変数
    HashMap<Integer, Integer> memVarMap = new HashMap<>();
    HashMap<Integer, Integer> atomVarMap = new HashMap<>();
    HashMap<Integer, Integer> otherVarMap = new HashMap<>();
    // ループ内で再定義している変数→もともとの変数
    HashMap<Integer, Integer> reverseAtomVarMap = new HashMap<>();
    int base = atomvars.size() + memvars.size() + othervars.size(); // ループ内命令列で使用する変数の開始値
    int nextArg = base;
    // 膜
    Iterator<Integer> it = memvars.subList(1, memvars.size()).iterator(); // はじめは本膜
    while (it.hasNext()) {
      memVarMap.put(it.next(), nextArg++);
    }
    memVarMap.put(0, 0);
    // アトム
    it = atomvars.iterator();
    while (it.hasNext()) {
      Integer a = (Integer) it.next();
      if (!a.equals(firstAtom)) {
        Integer t = nextArg++;
        atomVarMap.put(a, t);
        reverseAtomVarMap.put(t, a);
      }
    }
    Integer firstAtomInLoop = memvars.size() + atomvars.indexOf(firstAtom);
    atomVarMap.put(firstAtom, firstAtomInLoop);
    reverseAtomVarMap.put(firstAtomInLoop, firstAtom);

    // その他
    it = othervars.iterator();
    while (it.hasNext()) {
      otherVarMap.put(it.next(), nextArg++);
    }
    Instruction.changeMemVar(loop, memVarMap);
    Instruction.changeAtomVar(loop, atomVarMap);
    Instruction.changeOtherVar(loop, otherVarMap);

    Instruction resetVars = Instruction.resetvars(memvars, atomvars, othervars);

    // ループ中の、変数->前回時データの入った変数
    HashMap<Integer, Integer> beforeVar = new HashMap<>();
    // ループ外の変数->ループ内での前回時データが入った変数
    HashMap outToBeforeVar = new HashMap();
    for (int i = 0; i < memvars.size(); i++) {
      beforeVar.put(memVarMap.get(memvars.get(i)), i);
      outToBeforeVar.put(memvars.get(i), i);
    }
    for (int i = 0; i < atomvars.size(); i++) {
      beforeVar.put(atomVarMap.get(atomvars.get(i)), memvars.size() + i);
      outToBeforeVar.put(atomvars.get(i), memvars.size() + i);
    }
    for (int i = 0; i < othervars.size(); i++) {
      beforeVar.put(otherVarMap.get(othervars.get(i)), memvars.size() + atomvars.size() + i);
      outToBeforeVar.put(othervars.get(i), memvars.size() + atomvars.size() + i);
    }

    // 命令列の変更を行う

    // ループ内用変数置き換えマップ
    // 検査を省略して、前回ループの変数を使用する場合の、
    // 「再定義した場合の変数->前回ループ時の値が入っている変数」
    HashMap atomVarMap2 = new HashMap();
    // 上の逆
    //		HashMap reverseAtomVarMap2 = new HashMap();
    //		HashMap memVarMap2 = new HashMap();
    HashMap otherVarMap2 = new HashMap();
    //		memVarMap2.put(new Integer(0), new Integer(0)); //本膜
    //		atomVarMap.put(new Integer(firstAtom.intValue() + base), firstAtom);
    atomVarMap2.put(firstAtomInLoop, firstAtomInLoop);
    //		reverseAtomVarMap2.put(firstAtom, firstAtom);

    // dereflink命令から、すでにリンクしている事がわかっている(atom,pos) -> (atom,pos) (一方向)
    HashMap<Link, Link> alreadyLinked = new HashMap<>();

    ArrayList<Instruction> moveInsts = new ArrayList<>();
    ListIterator<Instruction> baseIterator =
        head.subList(2, head.size() - 1).listIterator(); // １回目用命令列
    ListIterator<Instruction> loopIterator = loop.listIterator(); // ループ内命令列
    while (baseIterator.hasNext()) {
      baseIterator.next();
      inst = loopIterator.next();
      switch (inst.getKind()) {
        case Instruction.DEREF:
          //				Link l = (Link)links.get(new Link(inst.getIntArg2(), inst.getIntArg3()));
          //				Link l = (Link)links.get(new
          // Link(((Integer)reverseAtomVarMap.get(inst.getArg2())).intValue(), inst.getIntArg3()));
          if (atomVarMap2.containsKey(inst.getArg2())) {
            Integer a = (Integer) atomVarMap2.get(inst.getArg2());
            if (a < memvars.size() + atomvars.size()) {
              Link l =
                  links.get(
                      new Link((Integer) atomvars.get(a - memvars.size()), inst.getIntArg3()));
              if (l != null) {
                if (l.pos == inst.getIntArg4()) {
                  //						atomVarMap2.put(inst.getArg1(), new Integer(l.atom));
                  atomVarMap2.put(inst.getArg1(), outToBeforeVar.get(l.atom));
                  //						reverseAtomVarMap2.put(new Integer(l.atom), inst.getArg1());
                  loopIterator.remove();
                  break; // 削除したので後の処理はしない
                } else {
                  // 絶対失敗するのでループ化しない
                  //								System.out.println(inst);
                  return;
                }
              }
            }
          }

          //				begin
          //				Integer beforeAtom =
          // (Integer)atomVarMap.get(atomvars.get(((Integer)beforeVar.get(inst.getArg2())).intValue() - memvars.size()));
          Integer atom = (Integer) atomVarMap2.get(inst.getArg2());
          if (atom != null && atom < memvars.size() + atomvars.size()) {
            Integer out = (Integer) atomvars.get(atom - memvars.size());
            Link t = new Link(out, inst.getIntArg3());
            if (inherit.containsKey(t)) {
              Integer beforeAtom = (Integer) atomVarMap.get(out);
              //						end
              if (beforeVar.get(beforeAtom).equals(atomVarMap2.get(beforeAtom))) {
                Integer t1 = (Integer) inherit.get(t);
                // Integer t2 = (Integer) outToBeforeVar.get(t1);
                //							loopIterator.set(new Instruction(Instruction.DEREFLINK, inst.getIntArg1(),
                // t2.intValue(), inst.getIntArg4()));

                // 冗長なnewlink除去のためのデータ
                Link l1 = new Link(inst.getIntArg1(), inst.getIntArg4());
                Link l2out = (Link) linkGetFrom.get(t1);
                Link l2 = new Link((Integer) outToBeforeVar.get(l2out.atom), l2out.pos);
                alreadyLinked.put(l1, l2);
              }
            }
          }
          break;
        case Instruction.FUNC:
          if (atomVarMap2.containsKey(inst.getArg1())) {
            //					begin
            //					Integer atom = (Integer)atomVarMap2.get(inst.getArg1());
            int afterchange = (Integer) atomVarMap2.get(inst.getArg1());
            if (afterchange < memvars.size() + atomvars.size()) {
              atom = (Integer) atomvars.get(afterchange - memvars.size());
              //						end
              if (functor.containsKey(atom)) {
                if (!functor.get(atom).equals(inst.getArg2())) {
                  // 絶対失敗するので複数回同時適用は行わない
                  //								System.out.println(inst);
                  return;
                }
                // 絶対成功するので除去
                loopIterator.remove();
              }
            }
          }
          break;
      }
    }

    HashMap changeToNewlink = new HashMap(); // newlinkに変更するリンク -> リンク先
    //		HashMap changeLink = new HashMap(); //inheritlinkの移動に伴い削除されたgetlink命令の第2引数の前回ループ時変数→第1引数
    baseIterator = body.listIterator(1); // １回目用命令列
    // loopIteratorはさっきの続き
    while (baseIterator.hasNext()) {
      Instruction baseInst = baseIterator.next();
      inst = loopIterator.next();
      switch (inst.getKind()) {
        case Instruction.GETLINK:
          // リンク先がわかっているgetlinkの除去
          Integer atom = (Integer) inst.getArg2();
          //				Integer baseAtom;
          if (atomVarMap2.containsKey(atom)) {
            atom = (Integer) atomVarMap2.get(inst.getArg2()); // 変数置き換え後
            //					baseAtom = (Integer)reverseAtomVarMap2.get(atom); //前回ループ
            if (atom < memvars.size() + atomvars.size()) {
              Integer baseAtom = (Integer) atomvars.get(atom - memvars.size()); // ループ外変数
              //					} else {
              //						baseAtom = null;//(Integer)atomVarMap.get(atom);
              //					}
              //					if (baseAtom != null) {
              Link l = links.get(new Link(baseAtom, inst.getIntArg3()));
              if (l != null) { // 前回のループのnewlinkによってリンク先が特定できる場合
                //							Integer baseAtom2 = new Integer(l.atom);
                Integer atom2 = atomVarMap.get(l.atom);
                //							if (baseAtom.equals(atomVarMap2.get(atom)) ||
                //								baseAtom2.equals(atomVarMap2.get(atomVarMap.get(baseAtom2)))) {
                // //前回のループのnewlink命令が削除されるものの場合
                Integer baseAtomInLoop = atomVarMap.get(baseAtom);
                if (beforeVar.get(baseAtomInLoop).equals(atomVarMap2.get(baseAtomInLoop))
                    || beforeVar
                        .get(atom2)
                        .equals(atomVarMap2.get(atom2))) { // 前回のループのnewlink命令が削除されるものの場合
                  // getlinkを削除し、inheritlinkをnewlinkに変更
                  loopIterator.remove();
                  changeToNewlink.put(inst.getArg1(), l);
                  // 削除したので他の処理は行わない
                  break;
                }
              }

              // inheritlinkを最後に移動する事に伴うgetlinkの処理
              //						if (baseAtom.equals(atomVarMap2.get(reverseAtomVarMap.get(baseAtom)))) {
              if (atom < memvars.size() + atomvars.size()) {
                Integer beforeOutVar = (Integer) atomvars.get(atom - memvars.size());
                Integer beforeAtom = atomVarMap.get(beforeOutVar);
                if (beforeVar.get(beforeAtom).equals(atomVarMap2.get(beforeAtom))) {
                  loopIterator.remove();
                  //							changeLink.put(beforeAtom, inst.getArg1());
                  otherVarMap2.put(
                      inst.getArg1(),
                      outToBeforeVar.get(inherit.get(new Link(beforeOutVar, inst.getIntArg3()))));
                }
              }
            }
          }
          break;
        case Instruction.INHERITLINK:
          Integer atomVar = (Integer) inst.getArg1();
          //				Integer baseAtom = (Integer)reverseAtomVarMap.get(atomVar);
          Integer beforeAtom = beforeVar.get(atomVar);
          if (beforeAtom.equals(atomVarMap2.get(atomVar))) { // もともとの変数番号と同じになっている場合
            // 最後に移動
            moveInsts.add(baseInst);
            baseIterator.remove();
            loopIterator.remove();
            //					if (changeLink.containsKey(atomVar)) {
            //					otherVarMap2.put(changeLink.get(atomVar), beforeVar.get(inst.getArg1()));
            //					}
          } else {
            Integer linkVar = (Integer) inst.getArg3();
            if (changeToNewlink.containsKey(linkVar)) {
              Link l = (Link) changeToNewlink.get(linkVar);
              // newlinkをループ後に移動したのに伴いinheritlinkをnewlinkに変更
              Link l1 = new Link(inst.getIntArg1(), inst.getIntArg2());
              Link l2 = new Link((Integer) outToBeforeVar.get(l.atom), l.pos);
              // 冗長な場合は除去
              if (l1.equals(alreadyLinked.get(l2)) || l2.equals(alreadyLinked.get(l1))) {
                loopIterator.remove();
              } else {
                loopIterator.set(
                    Instruction.newlink(l1.atom, l1.pos, l2.atom, l2.pos, inst.getIntArg5()));
              }
            }
          }
          break;
        case Instruction.NEWLINK:
          atomVar = (Integer) inst.getArg1();
          Integer atomVar2 = (Integer) inst.getArg3();
          beforeAtom = beforeVar.get(inst.getArg1());
          Integer beforeAtom2 = beforeVar.get(inst.getArg3());
          if (beforeAtom.equals(atomVarMap2.get(atomVar))
              || beforeAtom2.equals(atomVarMap2.get(atomVar2))) { // もともとの変数番号と同じになっている場合
            // 最後に移動
            moveInsts.add(baseInst);
            baseIterator.remove();
            loopIterator.remove();
            break;
          }

          // 冗長なリンク生成の除去
          Link l1 = new Link(inst.getIntArg1(), inst.getIntArg2());
          Link l2 = new Link(inst.getIntArg3(), inst.getIntArg4());
          if (l1.equals(alreadyLinked.get(l2)) || l2.equals(alreadyLinked.get(l1))) {
            loopIterator.remove();
          }
          break;
      }
    }
    // ループの最後にresetvars命令を挿入
    //		int memmax = ((List)react.getArg2()).size();
    //		int atommax = memmax + ((List)react.getArg3()).size();

    ArrayList memvars2 = new ArrayList();
    ArrayList atomvars2 = new ArrayList();
    ArrayList othervars2 = new ArrayList();
    for (ListIterator lit = memvars.listIterator(); lit.hasNext(); ) {
      memvars2.add((Integer) memVarMap.get(lit.next()));
    }
    for (ListIterator lit = atomvars.listIterator(); lit.hasNext(); ) {
      Integer var = atomVarMap.get(lit.next());
      if (atomVarMap2.containsKey(var)) {
        var = (Integer) atomVarMap2.get(var);
      }
      atomvars2.add(var);
    }
    for (ListIterator lit = othervars.listIterator(); lit.hasNext(); ) {
      othervars2.add(otherVarMap.get(lit.next()));
    }

    loop.add(loop.size() - 1, Instruction.resetvars(memvars2, atomvars2, othervars2));

    Instruction.changeAtomVar(loop, atomVarMap2);

    // proceed命令の前に挿入
    body.add(body.size() - 1, resetVars);
    ArrayList looparg = new ArrayList();
    looparg.add(loop);
    body.add(body.size() - 1, new Instruction(Instruction.LOOP, looparg));
    // 最後に1回実行する命令を挿入
    Instruction.applyVarRewriteMap(moveInsts, outToBeforeVar);
    body.addAll(body.size() - 1, moveInsts);

    // spec命令の変更
    if (nextArg > spec.getIntArg1()) {
      //			body.set(0, Instruction.spec(spec.getIntArg1(), nextArg));
      spec.updateSpec(spec.getIntArg1(), nextArg);
    }
  }
}

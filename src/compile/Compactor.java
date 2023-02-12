/*
 * 作成日: 2004/10/25
 */
package compile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.Rule;

/**
 * 命令列の長さに関する最適化を行うクラスメソッドを持つクラス。開発時用。 RISC化された命令列の可読性を高めるために使うかもしれない。 RISC化テスト用のexpandメソッドも持つ。
 * 命令セットのRISC化に一役買う予定。
 *
 * @author n-kato
 */
public class Compactor {

  /** ルールオブジェクトを最適化する（予定） */
  public static void compactRule(Rule rule) {
    compactInstructionList(rule.memMatch);
    compactInstructionList(rule.guard);
    compactInstructionList(rule.body);
  }

  /** 命令列を最適化する（予定） */
  public static void compactInstructionList(List<Instruction> insts) {
    // if (true) return;
    // List insts = label.insts;
    Instruction spec = insts.get(0);
    int formals = spec.getIntArg1();
    int varcount = spec.getIntArg2();
    varcount = expandBody(insts, varcount); // 展開（RISC化）
    boolean changed = true;
    while (changed) {
      changed = false;
      //			if (liftUpTestInstructions(insts))  changed = true;
      if (eliminateCommonSubexpressions(insts)) changed = true;
      if (eliminateRedundantInstructions(insts)) changed = true;
    }
    packUnifyLinks(insts);
    varcount = compactBody(insts, varcount); // 圧縮 (CISC化）
    varcount = renumberLocals(insts, varcount); // 局所変数を振りなおす
    spec.updateSpec(formals, varcount);
  }

  /** 命令列をRISC化する */
  public static int expandBody(List<Instruction> insts, int varcount) {
    int size = insts.size();
    for (int i = 0; i < size; i++) {
      Instruction inst = insts.get(i);
      //			060831okabe			int modify = (inst.getKind() >= Instruction.LOCAL ? Instruction.LOCAL : 0);
      switch (inst.getKind()) {
          // inheritlink [atom1,pos1,link2,mem1]
          // ==> alloclink[link1,atom1,pos1];unifylinks[link1,link2,mem1]
        case Instruction.INHERITLINK:
          insts.remove(i);
          insts.add(
              i,
              new Instruction(
                  Instruction.ALLOCLINK, varcount, inst.getIntArg1(), inst.getIntArg2()));
          insts.add(i + 1, new Instruction(Instruction.UNIFYLINKS, varcount, inst.getIntArg3()));
          if (inst.data.size() == 4) insts.get(i + 1).data.add(inst.getArg4());
          varcount += 1;
          size += 1;
          i += 1;
          continue;
          // unify[atom1,pos1,atom2,pos2,mem1]
          // ==> getlink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
        case Instruction.UNIFY:
          insts.remove(i);
          insts.add(
              i,
              new Instruction(Instruction.GETLINK, varcount, inst.getIntArg1(), inst.getIntArg2()));
          insts.add(
              i + 1,
              new Instruction(
                  Instruction.GETLINK, varcount + 1, inst.getIntArg3(), inst.getIntArg4()));
          insts.add(i + 2, new Instruction(Instruction.UNIFYLINKS, varcount, varcount + 1));
          if (inst.data.size() == 5) insts.get(i + 2).data.add(inst.getArg5());
          varcount += 2;
          size += 2;
          i += 2;
          continue;
          // newlink[atom1,pos1,atom2,pos2,mem1]
          // ==>
          // alloclink[link1,atom1,pos1];alloclink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
        case Instruction.NEWLINK:
          insts.remove(i);
          insts.add(
              i,
              new Instruction(
                  Instruction.ALLOCLINK, varcount, inst.getIntArg1(), inst.getIntArg2()));
          insts.add(
              i + 1,
              new Instruction(
                  Instruction.ALLOCLINK, varcount + 1, inst.getIntArg3(), inst.getIntArg4()));
          insts.add(i + 2, new Instruction(Instruction.UNIFYLINKS, varcount, varcount + 1));
          if (inst.data.size() == 5) insts.get(i + 2).data.add(inst.getArg5());
          varcount += 2;
          size += 2;
          i += 2;
          continue;
          // relink[atom1,pos1,atom2,pos2,mem1]
          // ==> alloclink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
        case Instruction.RELINK:
          insts.remove(i);
          insts.add(
              i,
              new Instruction(
                  Instruction.ALLOCLINK, varcount, inst.getIntArg1(), inst.getIntArg2()));
          insts.add(
              i + 1,
              new Instruction(
                  Instruction.GETLINK, varcount + 1, inst.getIntArg3(), inst.getIntArg4()));
          insts.add(i + 2, new Instruction(Instruction.UNIFYLINKS, varcount, varcount + 1));
          if (inst.data.size() == 5) insts.get(i + 2).data.add(inst.getArg5());
          varcount += 2;
          size += 2;
          i += 2;
          continue;
          // samefunc[atom1,atom2]
          // ==> getfunc[func1,atom1];getfunc[func2,atom2];eqfunc[func1,func2]
        case Instruction.SAMEFUNC:
          insts.remove(i);
          insts.add(i, new Instruction(Instruction.GETFUNC, varcount, inst.getIntArg1()));
          insts.add(i + 1, new Instruction(Instruction.GETFUNC, varcount + 1, inst.getIntArg2()));
          insts.add(i + 2, new Instruction(Instruction.EQFUNC, varcount, varcount + 1));
          varcount += 2;
          size += 2;
          i += 2;
          continue;
      }
    }
    return varcount;
  }

  /** 命令列をCISC化する */
  public static int compactBody(List<Instruction> insts, int varcount) {
    int size = insts.size() - 2;
    for (int i = 0; i < size; i++) {
      Instruction inst;
      Instruction inst0 = insts.get(i);
      Instruction inst1 = insts.get(i + 1);
      Instruction inst2 = insts.get(i + 2);

      // getlink[!link1,atom1,pos1];getlink[!link2,atom2,pos2];unifylinks[!link1,!link2(,mem1)]
      // ==> unify[atom1,pos1,atom2,pos2(,mem1)]
      if (inst0.getKind() == Instruction.GETLINK
          && inst1.getKind() == Instruction.GETLINK
          && inst2.getKind() == Instruction.UNIFYLINKS
          && inst0.getIntArg1() == inst2.getIntArg1()
          && inst1.getIntArg1() == inst2.getIntArg2()
          && Instruction.getVarUseCountFrom(insts, (Integer) inst0.getArg1(), i + 3) == 0
          && Instruction.getVarUseCountFrom(insts, (Integer) inst1.getArg1(), i + 3) == 0) {
        insts.remove(i);
        insts.remove(i);
        insts.remove(i);
        inst =
            new Instruction(
                Instruction.UNIFY,
                inst0.getIntArg2(),
                inst0.getIntArg3(),
                inst1.getIntArg2(),
                inst1.getIntArg3(),
                inst2.getIntArg3());
        insts.add(i, inst);
        size -= 2;
        continue;
      }

      // alloclink[!link1,atom1,pos1];alloclink[!link2,atom2,pos2];unifylinks[!link1,!link2(,mem1)]
      // ==> newlink[atom1,pos1,atom2,pos2(,mem1)]
      if (inst0.getKind() == Instruction.ALLOCLINK
          && inst1.getKind() == Instruction.ALLOCLINK
          && inst2.getKind() == Instruction.UNIFYLINKS
          && inst0.getIntArg1() == inst2.getIntArg1()
          && inst1.getIntArg1() == inst2.getIntArg2()
          && Instruction.getVarUseCountFrom(insts, (Integer) inst0.getArg1(), i + 3) == 0
          && Instruction.getVarUseCountFrom(insts, (Integer) inst1.getArg1(), i + 3) == 0) {
        insts.remove(i);
        insts.remove(i);
        insts.remove(i);
        inst =
            new Instruction(
                Instruction.NEWLINK,
                inst0.getIntArg2(),
                inst0.getIntArg3(),
                inst1.getIntArg2(),
                inst1.getIntArg3(),
                inst2.getIntArg3());
        insts.add(i, inst);
        size -= 2;
        continue;
      }

      // alloclink[!link1,atom1,pos1];getlink[!link2,atom2,pos2];unifylinks[!link1,!link2(,mem1)]
      // ==> relink[atom1,pos1,atom2,pos2(,mem1)]
      if (inst0.getKind() == Instruction.ALLOCLINK
          && inst1.getKind() == Instruction.GETLINK
          && inst2.getKind() == Instruction.UNIFYLINKS
          && inst0.getIntArg1() == inst2.getIntArg1()
          && inst1.getIntArg1() == inst2.getIntArg2()
          && Instruction.getVarUseCountFrom(insts, (Integer) inst0.getArg1(), i + 3) == 0
          && Instruction.getVarUseCountFrom(insts, (Integer) inst1.getArg1(), i + 3) == 0) {
        insts.remove(i);
        insts.remove(i);
        insts.remove(i);
        inst =
            new Instruction(
                Instruction.RELINK,
                inst0.getIntArg2(),
                inst0.getIntArg3(),
                inst1.getIntArg2(),
                inst1.getIntArg3(),
                inst2.getIntArg3());
        insts.add(i, inst);
        size -= 2;
        continue;
      }

      // getlink[!link1,atom1,pos1];alloclink[!link2,atom2,pos2];unifylinks[!link2,!link1(,mem2)]
      // ==> relink[atom2,pos2,atom1,pos1(,mem2)]
      if (inst0.getKind() == Instruction.GETLINK
          && inst1.getKind() == Instruction.ALLOCLINK
          && inst2.getKind() == Instruction.UNIFYLINKS
          && inst0.getIntArg1() == inst2.getIntArg2()
          && inst1.getIntArg1() == inst2.getIntArg1()
          && Instruction.getVarUseCountFrom(insts, (Integer) inst0.getArg1(), i + 3) == 0
          && Instruction.getVarUseCountFrom(insts, (Integer) inst1.getArg1(), i + 3) == 0) {
        insts.remove(i);
        insts.remove(i);
        insts.remove(i);
        inst =
            new Instruction(
                Instruction.RELINK,
                inst1.getIntArg2(),
                inst1.getIntArg3(),
                inst0.getIntArg2(),
                inst0.getIntArg3(),
                inst2.getIntArg3());
        insts.add(i, inst);
        size -= 2;
        continue;
      }
      // (ueda) Commented out to allow optimization of ==/2 that converts
      //        ALLOCATOM + GETFUNC to LOADFUNC (to eliminate ALLOCATOM in guard)
      // getfunc[!func1,atom1];getfunc[!func2,atom2];eqfunc[!func1,!func2]
      // ==> samefunc[atom1,atom2]
      // if (inst0.getKind() == Instruction.GETFUNC
      // 		&& inst1.getKind() == Instruction.GETFUNC
      // 		&& inst2.getKind() == Instruction.EQFUNC
      // 		&& inst0.getIntArg1() == inst2.getIntArg1() && inst1.getIntArg1() == inst2.getIntArg2()
      // 		&& Instruction.getVarUseCountFrom(insts, (Integer)inst0.getArg1(), i + 3) == 0
      // 		&& Instruction.getVarUseCountFrom(insts, (Integer)inst1.getArg1(), i + 3) == 0) {
      // 	insts.remove(i); insts.remove(i); insts.remove(i);
      // 	inst = new Instruction(Instruction.SAMEFUNC,
      // 			inst0.getIntArg2(), inst1.getIntArg2() );
      // 	insts.add(i,inst);
      // 	size -= 2;
      // 	continue;
      // }
    }
    return varcount;
  }

  /** 命令列の変数番号を振りなおす */
  public static int renumberLocals(List<Instruction> insts, int varcount) {
    int size = insts.size();
    Instruction spec = insts.get(0);
    int locals = spec.getIntArg1(); // 最初の局所変数の番号は、仮引数の個数にする
    //		for (int i = 1; i < size; i++) {
    //		Instruction inst = (Instruction)insts.get(i);
    //		if (inst.getOutputType() == -1) continue;
    //		if (inst.getIntArg1() != locals) {
    //		Integer src = (Integer)inst.getArg1();
    //		Integer dst = new Integer(locals);
    //		Integer tmp = new Integer(varcount);
    //		HashMap map1 = new HashMap();
    //		HashMap map2 = new HashMap();
    //		HashMap map3 = new HashMap();
    //		map1.put(src, tmp);
    //		map2.put(dst, src);
    //		map3.put(tmp, dst);
    //		Instruction.applyVarRewriteMapFrom(insts,map1,i);
    //		Instruction.applyVarRewriteMapFrom(insts,map2,i);
    //		Instruction.applyVarRewriteMapFrom(insts,map3,i);
    //		}
    //		locals++;
    //		}
    //		return locals;
    return renumberLocalsSub(insts.subList(1, size), locals, varcount);
  }

  public static int renumberLocalsSub(List<Instruction> insts, int locals, int varcount) {
    int max = 0;
    for (int i = 0; i < insts.size(); i++) {
      Instruction inst = insts.get(i);
      if (inst.getKind() == Instruction.NOT) {
        List<Instruction> subinsts = ((InstructionList) inst.getArg1()).insts;
        int sub = renumberLocalsSub(subinsts, locals, varcount);
        if (sub > max) max = sub;
      }
      if (inst.getOutputType() == -1) continue;
      if (inst.getIntArg1() != locals) {
        renumberLocalsSub2(inst.getIntArg1(), locals, varcount, insts, i);
      }
      locals++;
    }
    if (locals > max) max = locals;
    return max;
  }

  /**
   * 命令列 insts の begin 番地以降について、変数番号 isrc と locals の出現をすべて交換する。
   *
   * @param isrc
   * @param locals
   * @param varcount
   * @param insts
   * @param begin
   */
  static void renumberLocalsSub2(
      int isrc, int locals, int varcount, List<Instruction> insts, int begin) {
    Integer src = isrc;
    Integer dst = locals;
    Integer tmp = varcount;
    HashMap<Integer, Integer> map1 = new HashMap<>();
    HashMap<Integer, Integer> map2 = new HashMap<>();
    HashMap<Integer, Integer> map3 = new HashMap<>();
    map1.put(src, tmp);
    map2.put(dst, src);
    map3.put(tmp, dst);
    Instruction.applyVarRewriteMapFrom(insts, map1, begin);
    Instruction.applyVarRewriteMapFrom(insts, map2, begin);
    Instruction.applyVarRewriteMapFrom(insts, map3, begin);
  }

  ////////////////////////////////////////////////////////////////
  // CISC化用

  /**
   * UnifyLinks命令の引数に渡されるリンクの取得をできるだけ遅延する（仮）
   *
   * <p>とりあえず取得命令がALLOCLINKまたはLOOKUPLINKのときのみ機能する。
   */
  public static void packUnifyLinks(List<Instruction> insts) {
    for (int i = insts.size(); --i >= 1; ) {
      Instruction inst = insts.get(i);
      if (inst.getKind() == Instruction.UNIFYLINKS) {
        int stopper = i;
        for (int k = i; --k >= 1; ) {
          Instruction inst2 = insts.get(k);
          switch (inst2.getKind()) {
            case Instruction.ALLOCLINK:
            case Instruction.LOOKUPLINK:
              if (inst2.getIntArg1() == inst.getIntArg1()
                  || inst2.getIntArg1() == inst.getIntArg2()) {
                delayAllocLinkInstruction(insts, k, stopper);
                // 1回目のinst2がinstの第2引数リンク生成であり、inst直前に移動できた場合、
                // 2回目のinst2は1回目のinst2を追い越さないようにする
                if (inst2.getIntArg1() == inst.getIntArg2() && insts.get(stopper - 1) == inst2)
                  stopper--;
              }
          }
        }
      }
    }
  }

  /** insts[i]のALLOCLINK/LOOKUPLINK命令をできるだけ遅延する。ただしinsts[stopper]は追い越さない。 */
  public static void delayAllocLinkInstruction(List<Instruction> insts, int i, int stopper) {
    Instruction inst = insts.get(i);
    while (++i < stopper) {
      // insts[i - 1]はALLOCLINK/LOOKUPLINK命令
      Instruction inst2 = insts.get(i);

      //     ALLOCLINK[link0,...];alloclink[link1,atom1,pos1]
      // ==> alloclink[link1,atom1,pos1];ALLOCLINK[link0,...]
      if (inst2.getKind() == Instruction.ALLOCLINK) {
      }
      //	   ALLOCLINK[link0,...];unifylinks[link1,link2(,mem1)]
      // ==> unifylinks[link1,link2(,mem1)];ALLOCLINK[link0,...]
      // if link0 != link1 && link0 != link2
      else if (inst2.getKind() == Instruction.UNIFYLINKS
          && inst.getIntArg1() != inst2.getIntArg1()
          && inst.getIntArg1() != inst2.getIntArg2()) {
      }
      //	   ALLOCLINK[link0,...];getlink[link1,atom1,pos1]
      // ==> getlink[link1,atom1,pos1];ALLOCLINK[link0,...]
      else if (inst2.getKind() == Instruction.GETLINK) {
      }
      //	   ALLOCLINK[link0,...];lookuplink[link2,set1,link1]
      // ==> lookuplink[link2,set1,link1];ALLOCLINK[link0,...]
      // if link0 != link1
      else if (inst2.getKind() == Instruction.LOOKUPLINK
          && inst.getIntArg1() != inst2.getIntArg3()) {
      } else break;

      insts.remove(i - 1);
      insts.add(i, inst);
    }
  }

  ////////////////////////////////////////////////////////////////

  /**
   * 共通部分式を除去する
   *
   * @return changed
   */
  public static boolean eliminateCommonSubexpressions(List<Instruction> insts) {
    boolean changed = false;
    for (int i1 = insts.size() - 1; i1 >= 0; i1--) {
      Instruction inst1 = insts.get(i1);
      if (!inst1.hasSideEffect() && !inst1.hasControlEffect()) {
        // 取得命令
        for (int i2 = i1 + 1; i2 < insts.size(); i2++) {
          Instruction inst2 = insts.get(i2);
          if (inst2.hasSideEffect()) {
            break;
          }
          if (sameTypeAndSameInputArgs(inst1, inst2, true)) {
            Map<Integer, Integer> varChangeMap = new HashMap<>();
            varChangeMap.put((Integer) inst2.getArg1(), (Integer) inst1.getArg1());
            insts.remove(i2);
            i2--;
            Instruction.applyVarRewriteMap(insts, varChangeMap);
            changed = true;
          }
        }
      } else {
        switch (inst1.getKind()) {
            // 検査命令
          case Instruction.EQATOM:
          case Instruction.EQMEM:
          case Instruction.EQFUNC:
          case Instruction.SAMEFUNC:
          case Instruction.ISINT:
          case Instruction.ISUNARY:
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
            for (int i2 = i1 + 1; i2 < insts.size(); i2++) {
              Instruction inst2 = (Instruction) insts.get(i2);
              if (inst2.hasSideEffect()) {
                break; // for
              }
              if (sameTypeAndSameInputArgs(inst1, inst2, false)) {
                insts.remove(i2);
                i2--;
                changed = true;
              }
            }
            break; // switch
        }
      }
    }
    return changed;
  }

  /** ２つの命令が、同じ種類で、同じ入力引数を持つかどうかを検査する。 */
  private static boolean sameTypeAndSameInputArgs(
      Instruction inst1, Instruction inst2, boolean hasOutputArg) {
    if (inst1.getKind() != inst2.getKind()) {
      return false;
    }
    for (int i = (hasOutputArg ? 1 : 0); i < inst1.data.size(); i++) {
      if (!inst1.getArg(i).equals(inst2.getArg(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * 冗長な命令を除去する
   *
   * @return changed
   */
  public static boolean eliminateRedundantInstructions(List<Instruction> insts) {
    boolean changed = false;
    for (int i = insts.size(); --i >= 1; ) {
      Instruction inst = insts.get(i);
      // getlink[!link,atom,pos] ==> ; など
      if (!inst.hasSideEffect() && !inst.hasControlEffect()) {
        if (Instruction.getVarUseCountFrom(insts, (Integer) inst.getArg1(), i + 1) == 0) {
          insts.remove(i);
          i++;
          changed = true;
          continue;
        }
      }
      switch (inst.getKind()) {
          // eqfunc[func,func] ==> ; など
        case Instruction.EQATOM:
        case Instruction.EQMEM:
        case Instruction.EQFUNC:
        case Instruction.SAMEFUNC:
        case Instruction.IEQ:
        case Instruction.FEQ:
          if (inst.getIntArg1() == inst.getIntArg2()) {
            insts.remove(i);
            i++;
            changed = true;
            continue;
          }
      }
    }
    return changed;
  }
}

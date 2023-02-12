package type;

import compile.structure.Atom;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import runtime.functor.FloatingFunctor;
import runtime.functor.Functor;
import runtime.functor.IntegerFunctor;
import runtime.functor.ObjectFunctor;
import runtime.functor.StringFunctor;
import runtime.functor.SymbolFunctor;

public final class TypeEnv {

  public static final int ACTIVE = -1;

  public static final int CONNECTOR = -2;

  private static final Map<Functor, Integer> functorToOut = new HashMap<>();
  private static final Map<Functor, String> functorToTypeName = new HashMap<>();

  static {
    functorToOut.put(Functor.UNIFY, CONNECTOR);
    functorToOut.put(Functor.INSIDE_PROXY, CONNECTOR);
    functorToOut.put(Functor.OUTSIDE_PROXY, CONNECTOR);
    /* Passive atom */
    /* List atom */
    functorToOut.put(new SymbolFunctor(".", 3), 2);
    functorToOut.put(new SymbolFunctor("[]", 1), 0);
    /* Boolean atom */
    functorToOut.put(new SymbolFunctor("true", 1), 0);
    functorToOut.put(new SymbolFunctor("false", 1), 0);
  }

  public static void registerDataFunctor(Functor f, String dataname, int out) {
    functorToOut.put(f, out);
    functorToTypeName.put(f, dataname);
  }

  public static int outOfPassiveFunctor(Functor f) {
    /* Special atom */
    if (f instanceof IntegerFunctor) return 0;
    if (f instanceof FloatingFunctor) return 0;
    if (f instanceof StringFunctor) return 0;
    if (f instanceof ObjectFunctor) return 0;
    if (functorToOut.containsKey(f)) return (Integer) functorToOut.get(f);
    return ACTIVE;
  }

  public static int outOfPassiveAtom(Atom atomic) {
    //		if(atomic instanceof Atom){
    Atom atom = (Atom) atomic;
    Functor f = atom.functor;
    return outOfPassiveFunctor(f);
    //		}
    //		else(atomic instanceof ProcessContext){
    //			ProcessContext pc = (ProcessContext)atomic;
    //			String dt = def2type.get(pc.def);
    //			if(dt == null)return PC;
    //		}
  }

  static {
    functorToTypeName.put(new SymbolFunctor(".", 3), "list");
    functorToTypeName.put(new SymbolFunctor("[]", 1), "list");
    functorToTypeName.put(new SymbolFunctor("true", 1), "bool");
    functorToTypeName.put(new SymbolFunctor("false", 1), "bool");
  }

  public static String getTypeNameOfPassiveFunctor(Functor f) {
    /* Special Atom */
    if (f instanceof IntegerFunctor) return "int";
    if (f instanceof FloatingFunctor) return "float";
    if (f instanceof StringFunctor) return "string";
    if (f instanceof ObjectFunctor) return "java-object";
    if (functorToTypeName.containsKey(f)) return (String) functorToTypeName.get(
      f
    ); else return null;
  }

  /** 左辺膜および左辺出現膜の集合 */
  private static final Set<Membrane> lhsmems = new HashSet<>();

  private static final Map<Membrane, String> memToName = new HashMap<>();

  public static void initialize(Membrane root) throws TypeException {
    // 全ての膜について、ルールの左辺最外部出現かどうかの情報を得る
    TypeEnv.collectLHSMemsAndNames(root.rules);

    // 全ての型付きプロセス文脈について、プロセス型を知っておく
    // TODO いずれガードコンパイラに役立てる
    knowTPCMem(root);
    // 全ての右辺膜について、
  }

  /** 型付きプロセス文脈定義 -> データ型*/
  private static Map<ContextDef, Functor> def2type = new HashMap<>();

  public static Functor dataTypeOfContextDef(ContextDef cd) {
    return def2type.get(cd);
  }

  private static void knowTPCMem(Membrane mem) throws TypeException {
    for (RuleStructure rs : mem.rules) {
      knowTPCGuard(rs.guardMem);
      knowTPCMem(rs.rightMem);
    }
    for (Membrane child : mem.mems) {
      knowTPCMem(child);
    }
  }

  private static void knowTPCGuard(Membrane guardmem) throws TypeException {
    Functor intf = new IntegerFunctor(0);
    Functor floatf = new FloatingFunctor(0.0);
    Functor stringf = new StringFunctor("hello");
    Functor classf = new ObjectFunctor(new Object());
    for (Atom guard : guardmem.atoms) {
      if (guard.functor.equals(new SymbolFunctor("int", 1))) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, intf);
      } else if (guard.functor.equals(new SymbolFunctor("float", 1))) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, floatf);
      } else if (guard.functor.equals(new SymbolFunctor("string", 1))) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, stringf);
      } else if (guard.functor.equals(new SymbolFunctor("class", 2))) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, classf);
      } else if (
        guard.functor.equals(new SymbolFunctor("=:=", 2)) ||
        guard.functor.equals(new SymbolFunctor("=\\=", 2)) ||
        guard.functor.equals(new SymbolFunctor(">", 2)) ||
        guard.functor.equals(new SymbolFunctor("<", 2)) ||
        guard.functor.equals(new SymbolFunctor(">=", 2)) ||
        guard.functor.equals(new SymbolFunctor("=<", 2))
      ) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, intf);
        tpc = (ProcessContext) guard.args[1].buddy.atom;
        constrainTPC(tpc.def, intf);
      } else if (
        guard.functor.equals(new SymbolFunctor("+", 3)) ||
        guard.functor.equals(new SymbolFunctor("-", 3)) ||
        guard.functor.equals(new SymbolFunctor("*", 3)) ||
        guard.functor.equals(new SymbolFunctor("/", 3)) ||
        guard.functor.equals(new SymbolFunctor("mod", 3))
      ) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, intf);
        tpc = (ProcessContext) guard.args[1].buddy.atom;
        constrainTPC(tpc.def, intf);
        tpc = (ProcessContext) guard.args[2].buddy.atom;
        constrainTPC(tpc.def, intf);
      } else if (
        guard.functor.equals(new SymbolFunctor("=:=.", 2)) ||
        guard.functor.equals(new SymbolFunctor("=\\=.", 2)) ||
        guard.functor.equals(new SymbolFunctor(">.", 2)) ||
        guard.functor.equals(new SymbolFunctor("<.", 2)) ||
        guard.functor.equals(new SymbolFunctor(">=.", 2)) ||
        guard.functor.equals(new SymbolFunctor("=<.", 2))
      ) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, floatf);
        tpc = (ProcessContext) guard.args[1].buddy.atom;
        constrainTPC(tpc.def, floatf);
      } else if (
        guard.functor.equals(new SymbolFunctor("+.", 3)) ||
        guard.functor.equals(new SymbolFunctor("-.", 3)) ||
        guard.functor.equals(new SymbolFunctor("*.", 3)) ||
        guard.functor.equals(new SymbolFunctor("/.", 3))
      ) {
        ProcessContext tpc = (ProcessContext) guard.args[0].buddy.atom;
        constrainTPC(tpc.def, floatf);
        tpc = (ProcessContext) guard.args[1].buddy.atom;
        constrainTPC(tpc.def, floatf);
        tpc = (ProcessContext) guard.args[2].buddy.atom;
        constrainTPC(tpc.def, floatf);
      }
    }
    for (Atom guard : guardmem.atoms) {
      if (
        guard.functor.equals(new SymbolFunctor("=", 2)) ||
        guard.functor.equals(new SymbolFunctor("==", 2))
      ) {
        ProcessContext tpc1 = (ProcessContext) guard.args[0].buddy.atom;
        ProcessContext tpc2 = (ProcessContext) guard.args[0].buddy.atom;
        if (def2type.get(tpc1.def) != null) {
          constrainTPC(tpc2.def, def2type.get(tpc1.def));
        } else if (def2type.get(tpc2.def) != null) {
          constrainTPC(tpc1.def, def2type.get(tpc2.def));
        }
      }
    }
  }

  private static void constrainTPC(ContextDef cd, Functor datatype)
    throws TypeException {
    Functor df = def2type.get(cd);
    if (df == null) def2type.put(cd, datatype); else if (
      df.equals(datatype)
    ) return; else throw new TypeException(
      "Typed Process Context is constrained two process type. : " + cd.getName()
    );
  }

  /**
   * 左辺出現膜を$lhsmemsに登録する
   * 本膜の膜名を所属膜の膜名とする
   * @param mem
   */
  private static void collectLHSMemsAndNames(List<RuleStructure> rules) {
    for (RuleStructure rule : rules) collectLHSMemsAndNames(rule);
  }

  /**
   * 左辺出現膜を$lhsmemsに登録する
   * @param rule
   */
  private static void collectLHSMemsAndNames(RuleStructure rule) {
    collectLHSMem(rule.leftMem);
    memToName.put(rule.leftMem, rule.parent.name);
    memToName.put(rule.rightMem, rule.parent.name);
    //		 左辺にルールは出現しない
    for (RuleStructure rhsrule : (
      (List<RuleStructure>) rule.rightMem.rules
    )) collectLHSMemsAndNames(rhsrule);
  }

  /**
   * 左辺出現膜を$lhsmemsに登録する
   * @param mem 左辺出現膜
   */
  private static void collectLHSMem(Membrane mem) {
    lhsmems.add(mem);
    for (Membrane cmem : ((List<Membrane>) mem.mems)) collectLHSMem(cmem);
  }

  /** 左辺のアトムかどうかを返す */
  public static boolean isLHSAtom(Atom atom) {
    return isLHSMem(atom.mem);
  }

  /** 左辺の膜かどうかを返す */
  public static boolean isLHSMem(Membrane mem) {
    return lhsmems.contains(mem);
  }

  /**
   * get real buddy through =/2, $out, $in
   *
   * @param lo
   * @return
   */
  public static LinkOccurrence getRealBuddy(LinkOccurrence lo) {
    if (lo.buddy.atom instanceof Atom) {
      Atom a = (Atom) lo.buddy.atom;
      int o = TypeEnv.outOfPassiveAtom(a);
      if (o == TypeEnv.CONNECTOR) return getRealBuddy(
        a.args[1 - lo.buddy.pos]
      ); else return lo.buddy;
    } else return lo.buddy;
  }

  public static final String ANNONYMOUS = "??";

  /**
   * ルールの本膜については所属膜の名前を返す
   */
  public static String getMemName(Membrane mem) {
    String registered = memToName.get(mem);
    if (registered == null) {
      if (mem.name == null) return ANNONYMOUS; else return mem.name;
    } else return registered;
  }
}

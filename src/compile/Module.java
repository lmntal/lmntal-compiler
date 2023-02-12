/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import compile.structure.Membrane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import runtime.Ruleset;
import util.Util;

/**
 * モジュールシステムを実現するクラス。<br>
 * <br>
 * 概要
 *
 * <ul>
 *   <li>モジュールの定義方法 module_name : { ... } (膜に名前をつける)
 *   <li>モジュールの使い方 module_name.name
 *   <li>モジュールの実態は名前つきの膜である。
 *   <li>「モジュールを使う」とは、現在の膜内にモジュールのルールを読み込んで、勝手に反応させることである。
 *       たいてい、モジュールのルールの左辺に含まれるアトムを書くことで反応させることになる。
 * </ul>
 *
 * <br>
 *
 * @author hara
 */
public class Module {

  public static List<String> libPath = new ArrayList<>();
  public static Map<String, Membrane> memNameTable = new HashMap<>();
  public static Set<String> loaded = new HashSet<>();

  /**
   * 膜を名表に登録する。
   *
   * @param m
   */
  public static void regMemName(String name, Membrane m) {
    memNameTable.put(name, m);
  }

  /** モジュールが持つルールセット一覧を出力する。 */
  public static void showModuleList() {
    if (memNameTable.size() == 0) return;

    Util.println("Module");
    Iterator<String> it = memNameTable.keySet().iterator();
    while (it.hasNext()) {
      String name = it.next();
      Membrane mem = memNameTable.get(name);
      name = name.replaceAll("\\\\", "\\\\\\\\");
      name = name.replaceAll("'", "\\\\'");
      name = name.replaceAll("\r", "\\\\r");
      name = name.replaceAll("\n", "\\\\n");
      Util.print("'" + name + "'");
      Util.print(" {");
      if (mem.rulesets.size() > 0) {
        Iterator<Ruleset> it2 = mem.rulesets.iterator();
        Util.print(it2.next());
        while (it2.hasNext()) {
          System.out.print(", " + it2.next());
        }
      }
      Util.println("}");
    }
  }
}

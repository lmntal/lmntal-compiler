/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.Ruleset;
import util.Util;

import compile.parser.LMNParser;
import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.RuleStructure;

/**
 * モジュールシステムを実現するクラス。<br><br>
 * 
 * 概要
 * 
 * <ul>
 * <li> モジュールの定義方法   module_name : { ... }  (膜に名前をつける)
 * <li> モジュールの使い方     module_name.name
 * <li> モジュールの実態は名前つきの膜である。
 * <li> 「モジュールを使う」とは、現在の膜内にモジュールのルールを読み込んで、勝手に反応させることである。
 * 		たいてい、モジュールのルールの左辺に含まれるアトムを書くことで反応させることになる。
 * </ul><br>
 * 
 * @author hara
 *
 */
public class Module {
	public static List<String> libPath = new ArrayList<>();
	public static Map<String, Membrane> memNameTable = new HashMap<>();
	public static Set<String> loaded = new HashSet<>();
	
	static {
		String home = System.getProperty("LMNTAL_HOME");
		if (home == null) {
			Env.e("Warning : LMNTAL_HOME is not set. Using relative path.");
			libPath.add("./lib/src");
			libPath.add("../lib/src");
			libPath.add("./lib/public");
			libPath.add("../lib/public");
			libPath.add(".");
		} else {
			libPath.add(home + "/lib/src");
			libPath.add(home + "/lib/public");
		}
	}
	
	/**
	 * 膜を名表に登録する。
	 * @param m
	 */
	public static void regMemName(String name, Membrane m) {
		memNameTable.put(name, m);
	}
	
	/**
	 * 指定したモジュールを指定した膜に読み込む
	 * @param m         読み込まれる膜
	 * @param mod_name  モジュール名
	 */
	public static void loadModule(Membrane m, String mod_name) {
		if(loaded.contains(mod_name)) return;
		
		for (String thePath : libPath) {
			File file = new File(thePath+"/"+mod_name+".lmn");
			StringBuffer sb = new StringBuffer("Loading Module "+mod_name+" from "+file+" ...");
			try {
				LMNParser lp = new LMNParser(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
				runtime.Ruleset rs = RulesetCompiler.compileMembrane(lp.parse(), file.toString());
				//Env.p("MOD compiled "+rs);
				//memNameTable がモジュール膜への参照を保持しているので、GCされない。
				m.rulesets.add(rs);
				sb.append(" [ OK ] ");
				Env.d(sb.toString());
				loaded.add(mod_name);
				return;
			} catch (Exception e) {
				sb.append(" [ FAILED ] ");
				Env.d(sb.toString());
				//Env.e("!! catch !! "+e+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
			}
		}
	}
	
	/**
	 * 指定した膜について、未定義モジュールを解決する。
	 * @param m
	 */
	public static void resolveModules(Membrane m) {
		List<String> need = new ArrayList<>();
		getNeedModules(m, need);
		for(String s : need){
			loadModule(m, s);
		}
	}
	
	/**
	 * 指定した膜について、未解決モジュール一覧をつくる。
	 * @param m
	 * @param need 出力引数。モジュール一覧が入る。
	 */
	static void getNeedModules(Membrane m, List<String> need) {
		Iterator<Atom> i_a = m.atoms.listIterator();
		while(i_a.hasNext()) {
			Atom a = i_a.next();
			String path = a.getPath();
			if(path == null) continue;
			if(path.equals(m.name)) continue;
			if(!memNameTable.containsKey(path)) {
				need.add(path);
			}
		}
		Iterator<RuleStructure>i_r = m.rules.listIterator();
		while(i_r.hasNext()) {
			RuleStructure rs = i_r.next();
			getNeedModules(rs.leftMem, need);
			getNeedModules(rs.rightMem, need);
		}
		Iterator<Membrane> i_m = m.mems.listIterator();
		while(i_m.hasNext()) {
			getNeedModules(i_m.next(), need);
		}
	}
	
	/** モジュールが持つルールセット一覧を出力する。*/
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

/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

//import runtime.Functor;
import runtime.Env;
import compile.parser.LMNParser;
import compile.structure.*;

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
	public static List libPath = new ArrayList();
	public static Map memNameTable = new HashMap();
	public static Map loaded = new HashMap();
	public static Object EXIST = new Object();
	
	static {
		//libPath.add("hoge");
		//libPath.add("FOO");
		libPath.add("./lmntal_lib");
		libPath.add("../lmntal_lib");
		libPath.add(".");
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
		if(loaded.get(mod_name)!=null) return;
		
		Iterator it = libPath.iterator();
		while(it.hasNext()) {
			String thePath = (String)it.next();
			String filename = thePath+"/"+mod_name+".lmn";
			StringBuffer sb = new StringBuffer("Loading Module "+mod_name+" from "+filename+" ...");
			try {
				LMNParser lp = new LMNParser(new BufferedReader(new InputStreamReader(new FileInputStream(filename))));
				runtime.Ruleset rs = RulesetCompiler.compileMembrane(lp.parse(), filename);
				//Env.p("MOD compiled "+rs);
				//memNameTable がモジュール膜への参照を保持しているので、GCされない。
				m.rulesets.add(rs);
				sb.append(" [ OK ] ");
				Env.d(sb.toString());
				loaded.put(mod_name, EXIST);
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
		List need = new ArrayList();
		getNeedModules(m, need);
		Iterator i = need.iterator();
		while(i.hasNext()) {
			loadModule(m, (String)i.next());
		}
	}
	
	/**
	 * 指定した膜について、未解決モジュール一覧をつくる。
	 * @param m
	 * @param need 出力引数。モジュール一覧が入る。
	 */
	static void getNeedModules(Membrane m, List need) {
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			String path = a.getPath();
			if(path == null) continue;
			if(path.equals(m.name)) continue;
//			Env.p("Check module existence "+path);
			if(!memNameTable.containsKey(path)) {
//				Env.p("TODO: search lib file : " + path);
				need.add(path);
			}
		}
		i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			getNeedModules(rs.leftMem, need);
			getNeedModules(rs.rightMem, need);
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			getNeedModules((Membrane)i.next(), need);
		}
	}
	
	/**
	 * 
	 * @param m
	 */
	public static void fixupLoadModule(Membrane m) {
		//Env.d("genInstruction");
//		
//		//Env.p(memNameTable);
//		
//		Iterator i;
//		Iterator it0 = m.rulesets.iterator();
//		while (it0.hasNext()){
//			i = ((InterpretedRuleset)it0.next()).rules.listIterator();
//			while(i.hasNext()) {
//				runtime.Rule rule = (runtime.Rule)i.next();
//				ListIterator ib = rule.body.listIterator();
//				while(ib.hasNext()) {
//					Instruction inst = (Instruction)ib.next();
//					// きたない。
//					if(inst.getKind()==Instruction.LOADMODULE) {
//						//Env.p("module solved : "+modules.get(inst.getArg2()));
//						ib.remove();
//						// モジュール膜直属のルールセットを全部読み込む
//						Membrane mem = (Membrane)memNameTable.get(inst.getArg2());
//						if(mem==null) {
//							Env.e("Undefined module "+inst.getArg2());
//						} else {
//							Iterator it3 = mem.rulesets.iterator();
//							while (it3.hasNext()) {
//								ib.add(new Instruction(Instruction.LOADMODULE, inst.getIntArg1(),
//									(runtime.Ruleset)it3.next()));
//							}
//						}
//					}
//				}
//			}
//		}
//		i = m.rules.listIterator();
//		while(i.hasNext()) {
//			RuleStructure rs = (RuleStructure)i.next();
//			fixupLoadModule(rs.leftMem);
//			fixupLoadModule(rs.rightMem);
//		}
//		i = m.mems.listIterator();
//		while(i.hasNext()) {
//			fixupLoadModule((Membrane)i.next());
//		}
	}
}

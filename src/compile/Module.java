/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.InterpretedRuleset;
import runtime.Env;
import compile.structure.*;

/**
 * モジュールシステムを実現するクラス。<br><br>
 * 
 * 仕様メモ：
 * 
 * <ul>
 * <li> モジュールの実態は名前つきの膜である。
 * <li> 「モジュールを使う」とは、現在の膜内にモジュールのルールを読み込んで、勝手に反応させることである。
 * 		たいてい、モジュールのルールの左辺に含まれるアトムを書くことで反応させることになる。
 * <li> module_name : { ... } により、膜に名前をつける。これがモジュール名となる。
 * 		ただし、現状は name(X) が含まれる膜に対して X につながるアトムのファンクタ名と同じ名前をつける。 
 * <li> モジュールが公開するのは、そのモジュールに含まれるルールだけ。
 * <li> アトム名 ::= [パッケージ名.]名前 ということにする。
 * <li> Functor に package (所属膜の名前)フィールドを設け、セットする。
 * 		set:{count} で count.package eq "set"
 * <li> モジュールの使い方 [1] module_name.name
 * <li> モジュールの使い方 [2] use(module_name), name
 * 		→何回も使う時にモジュール名を省略できる。衝突したときはどれが反応するか不定。
 * <li> コンパイラはコンパイル後、全ての「モジュール名.名前」についてそれぞれそのモジュールを解決し、
 * 		解決されたモジュールのルールセットを「モジュール.名前」の所属膜に読み込む命令を生成する。
 * 		解決順序は、現在のコンパイル済み構造→ライブラリパスから「パッケージ名.lmn」を探してコンパイルした構造
 * <li> FINDATOM 命令の動作を変更する。（AtomSet を変えることになるかな）
 * 		func を探すとき、マッチするファンクタ集合は以下のもの。
 * 		<ul>
 * 		<li>func.package ne "" の場合は、name, arity, package が等しいもの
 * 		<li>func.package eq "" の場合は、name, arity が等しいもの
 * 		</ul>
 * <li> 
 * </ul>
 * 
 * @author hara
 *
 */
public class Module {
	public static Map modules = new HashMap();
	public static void listupModules(Membrane m) {
		//Env.d("listupModules");
		runtime.Functor f = new runtime.Functor("name", 1);
		
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			if(a.functor.equals(f)) {
				Env.d("Module found : "+a.args[0].atom);
				modules.put(a.args[0].buddy.atom.functor.getName(), a.args[0].atom.mem);
			}
		}
		i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			//Env.d("");
			//Env.d("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			listupModules(rs.leftMem);
			//Env.d("About rule structure (LEFT): "+rs.rightMem+" of "+rs);
			listupModules(rs.rightMem);
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			listupModules((Membrane)i.next());
		}
	}
	public static void fixupLoadRuleset(Membrane m) {
		//Env.d("fixupLoadRuleset");
		
		Iterator it0 = m.rulesets.iterator();
		while (it0.hasNext()){
			Iterator i = ((InterpretedRuleset)it0.next()).rules.listIterator();
			while(i.hasNext()) {
				runtime.Rule rule = (runtime.Rule)i.next();
				ListIterator ib = rule.body.listIterator();
				while(ib.hasNext()) {
					Instruction inst = (Instruction)ib.next();
					// きたない。
					// TODO この用途での LOADRULESET は LOADMODULE に名称変更し、Interpreterでロードする
					if(inst.getKind()==Instruction.LOADRULESET && inst.getArg2() instanceof String) {
						//Env.p("module solved : "+modules.get(inst.getArg2()));
						ib.remove();
						Iterator it3 = ((Membrane)modules.get(inst.getArg2())).rulesets.iterator();
						while (it3.hasNext()) {
							ib.add(new Instruction(Instruction.LOADRULESET, inst.getIntArg1(),
								(runtime.Ruleset)it3.next()));
						}
						
	//					ib.set(new Instruction(Instruction.LOADRULESET, inst.getIntArg1(), 
	//						((Membrane)modules.get(inst.getArg2())).ruleset ));
						
					}
				}
			}
		}
		Iterator i = m.mems.listIterator();
		while(i.hasNext()) {
			fixupLoadRuleset((Membrane)i.next());
		}
	}
}

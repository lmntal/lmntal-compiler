/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import java.util.*;

import runtime.Functor;
import runtime.Instruction;
import runtime.InterpretedRuleset;
import runtime.Env;
import compile.structure.*;

/**
 * モジュールシステムを実現するクラス。<br><br>
 * 
 * 概要
 * 
 * <ul>
 * <li> モジュールの定義方法   module_name : { ... }
 * <li> モジュールの使い方 [1] module_name.name
 * <li> モジュールの使い方 [2] use(module_name), name<br>
 * 		→何回も使う時にモジュール名を省略できる。<br>
 * 		　が、複数のモジュールが同名のファンクタを定義してる時はどれが反応するか不定なので、この使い方は推奨されない。
 * <li> モジュールの実態は名前つきの膜である。
 * <li> 「モジュールを使う」とは、現在の膜内にモジュールのルールを読み込んで、勝手に反応させることである。
 * 		たいてい、モジュールのルールの左辺に含まれるアトムを書くことで反応させることになる。
 * <li> module_name : { ... } により、膜に名前をつける。これがモジュール名となる。
 * 		ただし、現状は name(X) が含まれる膜に対して X につながるアトムのファンクタ名と同じ名前をつける。 
 * </ul><br>
 * 
 * 内部の仕組み
 * 
 * <ul>
 * <li> モジュールが公開するのは、そのモジュールに含まれるルールだけ。
 * <li> ファンクタ名の表記を [所属膜名.]名前 とする。
 * <li> 以下のフィールドを設け、次に指定した方法でコンパイル時に初期化する。
 * 		<dl>
 * 		<dt>String  Functor.path</dt>
 * 		<dd>ファンクタ表記中の所属膜名。
 * 			ソースコードで明示的に指定されたらそれ。
 * 			＜指定されなかったら、デフォルトとしてそのファンクタが所属する膜＞。
 *          TODO 【デフォルトは所属膜無しにしないと、モジュール膜内でリストなどのデータが処理できません】(n-kato)</dd>
 * 		<dt>boolean Functor.pathFree</dt>
 * 		<dd>所属膜が明示的に指定されなかった時に真。</dd>
 *          TODO 'ModuleName$AtomName' などという名前でファンクタを生成すれば解決すると思うのですが、ダメですか？＞原君 (n-kato)
 * 		</dl>
 * <li> コンパイラはコンパイル後、全ての「モジュール名.名前」についてそれぞれそのモジュールを解決し、LOADMODULE命令を生成する。
 * 		解決順序は、現在のコンパイル済み構造→ライブラリパスから「モジュール名.lmn」を探してコンパイルした構造。
 * 		(TODO)
 * <li> LOADMODULE命令とは、指定したモジュールのルールセットを指定した膜に読み込む中間命令だ。
 * <li> FINDATOM 命令の動作を変更する。（TODO AtomSet を変えることになるかな）
 * 		func を探すとき、マッチするファンクタ集合は以下のもの。
 * 		<ul>
 * 		<li>!func.pathFree の場合は、name, arity, package が等しいもの
 * 		<li> func.pathFree の場合は、name, arity が等しいもの（従来どおり）
 * 		</ul>
 *      TODO この区別はルールコンパイラが行うのであるため、コンパイル時データ構造のみが識別する必要があり、実行時には不要。(n-kato)
 * </ul>
 * 
 * @author hara
 *
 */
public class Module {
	public static Map memNameTable = new HashMap();
	
	/**
	 * 膜を名表に登録する。
	 * @param m
	 */
	public static void regMemName(String name, Membrane m) {
		memNameTable.put(name, m);
	}
	
	/**
	 * モジュールの解決をする。必要に応じてライブラリファイルから読み込む。
	 * @param m
	 */
	public static void genInstruction(Membrane m) {
		//Env.d("genInstruction");
		
		//Env.p(memNameTable);
		
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			Functor f = a.functor;
			if(f.path==null) continue;
			if(f.path.equals(m.name)) continue;
			Env.p("Check module existence "+f.path);
			if(!memNameTable.containsKey(f.path)) {
				//TODO search lib file
				Env.p("TODO: search lib file : "+f.path);
			}
		}
		i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			genInstruction(rs.leftMem);
			genInstruction(rs.rightMem);
		}
		i = m.mems.listIterator();
		while(i.hasNext()) {
			genInstruction((Membrane)i.next());
		}
//		Iterator it0 = m.rulesets.iterator();
//		while (it0.hasNext()){
//			Iterator i = ((InterpretedRuleset)it0.next()).rules.listIterator();
//			while(i.hasNext()) {
//				runtime.Rule rule = (runtime.Rule)i.next();
//				ListIterator ib = rule.body.listIterator();
//				while(ib.hasNext()) {
//					Instruction inst = (Instruction)ib.next();
//					// きたない。
//					// TODO この用途での LOADRULESET は LOADMODULE に名称変更し、Interpreterでロードする
//					if(inst.getKind()==Instruction.LOADRULESET && inst.getArg2() instanceof String) {
//						//Env.p("module solved : "+modules.get(inst.getArg2()));
//						ib.remove();
//						Iterator it3 = ((Membrane)memNameTable.get(inst.getArg2())).rulesets.iterator();
//						while (it3.hasNext()) {
//							ib.add(new Instruction(Instruction.LOADRULESET, inst.getIntArg1(),
//								(runtime.Ruleset)it3.next()));
//						}
//					}
//				}
//			}
//		}
//		Iterator i = m.mems.listIterator();
//		while(i.hasNext()) {
//			genInstruction((Membrane)i.next());
//		}
	}
}

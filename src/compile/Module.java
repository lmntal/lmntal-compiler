/*
 * 作成日: 2004/01/10
 *
 */
package compile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import runtime.Functor;
import runtime.Env;
import compile.parser.LMNParser;
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
 *          【下を参照】【ルールデフォルトは所属膜無しにしないと、モジュール膜内でリストなどのデータが処理できません】(n-kato)</dd>
 * 		<dt>boolean Functor.pathFree</dt>
 * 		<dd>所属膜が明示的に指定されなかった時に真。</dd>
 *          【下を参照】'ModuleName$AtomName' などという名前でファンクタを生成すれば解決すると思うのですが、ダメですか？＞原君 (n-kato)
 * 		</dl>
 * <li> コンパイラはコンパイル後、全ての「モジュール名.名前」についてそれぞれそのモジュールを解決し、LOADMODULE命令を生成する。
 * 		解決順序は、現在のコンパイル済み構造→ライブラリパスから「モジュール名.lmn」を探してコンパイルした構造。
 * <li> LOADMODULE命令とは、指定したモジュールのルールセットを指定した膜に読み込む中間命令だ。
 * <li> FINDATOM 命令の動作を変更する。（AtomSet を変えることになるかな）
 * 		func を探すとき、マッチするファンクタ集合は以下のもの。
 * 		<ul>
 * 		<li>!func.pathFree の場合は、name, arity, path が等しいもの
 * 		<li> func.pathFree の場合は、name, arity が等しいもの（従来どおり）
 * 		</ul>
 *      【下を参照】この区別はルールコンパイラが行うのであるため、コンパイル時データ構造のみが識別する必要があり、実行時には不要。(n-kato)
 * </ul>
 * 
 * TODO 【原君は、これを確認してください】
 * 
 * ■ 名前 m.p に対して、モジュール名をワイルドカードにしてマッチングが取れるようにする場合
 * 
 * runtime.Functorはpのみを表すようにして、
 * runtime.Atom側にモジュール名を表すフィールドを追加するのがよいと思います。
 * 
 * [1] ルールのヘッドでの m.p の出現は、m.p のみにマッチする。（m.pの'm,p'という新しい名前としての使用）
 *     これは、findatom dstatom,srcmem,p の次に、
 *     例えば path dstatom,m という命令を発行するようにルールコンパイラがコンパイルする。
 * [2] ルールのヘッドでの p の出現は、全ての p にマッチする。(*A)
 *     findatom dstatom,srcmem,p
 * [3] ルールのボディでの m.p の出現は、m.p を生成する。
 *     newatom dstmem,p,m
 * [4] ルールのボディでの p の出現は、m.p を生成する。
 *     newatom dstmem,p,m
 * [5] モジュール定義内のルール外での m.p の出現は、m.p を生成する。
 * [6] モジュール定義内のルール外での p の出現は、m.p を生成する。
 * 
 * ■ 名前 m.p に対して、モジュール名をワイルドカードにしてマッチングが取れないようにする場合
 * 
 * m.pは、単に新しい名前'm,p'を表すことになる。
 * 
 * [1] ルールのヘッドでの m.p の出現は、'm.p' なので、m.p のみにマッチする。
 *     findatom dstatom,srcmem,'m,p'
 * [2] ルールのヘッドでの p の出現は、p のみにマッチする。
 *     findatom dstatom,srcmem,p 
 * [3] ルールのボディでの m.p の出現は、m.p を生成する。
 *     newatom dstmem,mem,'m,p'
 * [4] ルールのボディでの p の出現は、p を生成する。
 *     newatom dstmem,mem,p
 * [5] モジュール定義内のルール外での m.p の出現は、m.p を生成する。
 * [6] モジュール定義内のルール外での p の出現は、p を生成する。
 * 
 * ■ 'm.p' の用途で使用するアトムを p を使ってマッチングすること(*A)はあるのですか？
 *    あるとすれば、どんなときに役に立つのですか？
 * 
 *    無いのであれば、マッチングは取れないようにして、
 *    いつも新しい名前'm.p'と解釈する方がいいと思います。
 * 
 * ■ 話が変わりますが、モジュール内の静的なデータの扱い方はどうなりましたか？
 *    ルールをコピーするだけでは静的なデータは実現できませんので、何か方法を考えてください。
 *    これを考えることが卒論のメインテーマだったはずです。これに比べたら残りはただの作業です。
 * 
 *    inlineを使ってごまかすのは、どうしてもできなかった時だけにしたいのですが。
 * 
 * @author hara
 *
 */
public class Module {
	public static String libPath = "../lmntal_lib/";
	public static Map memNameTable = new HashMap();
	
	/**
	 * よきにとりはからう
	 * @param m
	 */
	public static void run(Membrane m) {
		resolveModules(m);
		fixupLoadModule(m);
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
		String filename = libPath+mod_name+".lmn";
		StringBuffer sb = new StringBuffer("Loading Module "+mod_name+" ...");
		try {
			LMNParser lp = new LMNParser(new BufferedReader(new InputStreamReader(new FileInputStream(filename))));
			Membrane nm = RulesetCompiler.runStartWithNull(lp.parse());
//			Env.p("MOD compiled "+nm);
			//memNameTable がモジュール膜への参照を保持しているので、GCされない。
			//m.add(nm);
			sb.append(" [ OK ] ");
		} catch (Exception e) {
			Env.e("!! catch !! "+e.getMessage()+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
			sb.append(" [ FAILED ] ");
		}
		Env.d(sb.toString());
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
	 * 指定した膜について、必要なモジュール一覧をつくる。
	 * @param m
	 * @param need 出力引数。モジュール一覧が入る。
	 */
	static void getNeedModules(Membrane m, List need) {
		Iterator i;
		i = m.atoms.listIterator();
		while(i.hasNext()) {
			Atom a = (Atom)i.next();
			Functor f = a.functor;
			if(f.path==null) continue;
			if(f.path.equals(m.name)) continue;
//			Env.p("Check module existence "+f.path);
			if(!memNameTable.containsKey(f.path)) {
//				Env.p("TODO: search lib file : "+f.path);
				need.add(f.path);
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

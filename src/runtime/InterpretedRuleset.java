package runtime;

import java.util.*;

// TODO 【重要】マッチング検査の途中で取得したロックを全て解放する必要がある
// memo：ロックを開放する単位がリストになるように修正

/**
 * compile.RuleCompiler によって生成される。
 * @author n-kato, nakajima
 */
public final class InterpretedRuleset extends Ruleset {
	/** ルールセット番号 */
	private int id;
	private static int lastId=600;
	
	/** とりあえずルールの配列として実装 */
	public List rules;
	
	
	/**
	 * RuleCompiler では、まず生成してからデータを入れ込む。
	 * ので、特になにもしない
	 */
	public InterpretedRuleset() {
		rules = new ArrayList();
		id = ++lastId;
	}
	
	/**
	 * あるルールについてアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		Iterator it = rules.iterator();
		while(it.hasNext()) {
			Rule r = (Rule)it.next();
			result |= matchTest(mem, atom, r.atomMatch, (Instruction)r.body.get(0));
		}
		return result;
	}
	
	/**
	 * あるルールについて膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		boolean result = false;
		Iterator it = rules.iterator();
		while(it.hasNext()) {
			Rule r = (Rule)it.next();
			result |= matchTest(mem, null, r.memMatch, (Instruction)r.body.get(0));
		}
		return result;
	}
	
	/**
	 * 膜主導かアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	private boolean matchTest(Membrane mem, Atom atom, List matchInsts, Instruction spec) {
		Env.p("atomMatch."+matchInsts);
		int formals = spec.getIntArg1();
		int locals  = spec.getIntArg2();
		AbstractMembrane[] mems  = new AbstractMembrane[formals];
		Atom[]             atoms = new Atom[formals];
		mems[0]  = mem;
		atoms[1] = atom;
		return matchTestStep(mems,atoms,matchInsts,0);
	}
	private boolean matchTestStep(AbstractMembrane[] mems, Atom[] atoms,
									List matchInsts, int pc) {
		while (pc < matchInsts.size()) {
			Instruction inst = (Instruction)matchInsts.get(pc++);
			switch (inst.getKind()){
			//case Instruction.....:
			case Instruction.REACT:
				Rule rule = (Rule)inst.getArg1();
				List bodyInsts = (List)rule.body;
				Instruction spec = (Instruction)bodyInsts.get(0);
				int formals = spec.getIntArg1();
				int locals  = spec.getIntArg2();
				AbstractMembrane[] bodymems  = new AbstractMembrane[locals];
				Atom[]             bodyatoms = new Atom[locals];
				List memformals  = (List)inst.getArg2();
				List atomformals = (List)inst.getArg3();
				for (int i = 0; i < memformals.size(); i++) {
					bodymems[i]  = mems[((Integer)memformals.get(i)).intValue()];
				}
				for (int i = 0; i < atomformals.size(); i++) {
					bodyatoms[i]  = atoms[((Integer)atomformals.get(i)).intValue()];
				}
				body(bodyInsts, bodymems, bodyatoms);
				return true;
			case Instruction.FINDATOM: // findatom [-dstatom, srcmem, funcref]
				Functor func = (Functor)inst.getArg3();
				Iterator it = mems[inst.getIntArg2()].atoms.iteratorOfFunctor(func);
				while (it.hasNext()){
					Atom a = (Atom)it.next();
					atoms[inst.getIntArg1()] = a;					
					if (matchTestStep(mems,atoms,matchInsts,pc)) return true;
				}
				break;
			}
		}
		return false;
	}
	
	/**
	 * ルールを適用する。<br>
	 * 引数の膜と、引数のアトムの所属膜はすでにロックされているものとする。
	 * @param ruleid 適用するルール
	 * @param memArgs 実引数のうち、膜であるもの
	 * @param atomArgs 実引数のうち、アトムであるもの
	 * @author nakajima
	 *
	 * 
	 */
	private void body(List rulebody, AbstractMembrane[] mems, Atom[] atoms) {
		Iterator it = rulebody.iterator();
		while(it.hasNext()){
			Instruction hoge = (Instruction)it.next();


			//ここ以下のswitchは移動予定 bodyとguardを両方扱うメソッドを定義する予定
		switch (hoge.getID()){
		case Instruction.DEREF:
			//deref [-dstatom, +srcatom, +srcpos, +dstpos]
			//if (atomArgs[1].args[srcpos] == atomArgs[1].args[dstpos]) {
				//リンク先のアトムをdstatomに代入する。
			//            }
			break;

		case Instruction.GETMEM:
			//getmem [-dstmem, srcatom]
			//TODO 回答：聞く:「膜の所属膜」とは？→[1]が膜のときはGETPARENTを使う。「親膜」の意味。
			//ruby版だとgetparentと同じ実装になってるのでとりあえずそうする
			//コメント：Java版では命令ごとに引数の型を固定するために、名前を分けることにしました
            
				memArgs[0] = memArgs[1].mem;
				//TODO ルール実行中の変数ベクタを参照渡しして、引数で間接参照する設計にする方がよい。
				// そうしないと、出力引数がうまく反映しない。
				break;

		case Instruction.GETPARENT:
			//getparent [-dstmem, srcmem] 
			memArgs[0] = memArgs[1].mem;
			break;

		case Instruction.ANYMEM:
			//anymem [??dstmem, srcmem]
			for (int i = 0; i <  memArgs[1].mems.size(); i++ ){
				//ノンブロッキングでのロック取得を試みる。
				//if(成功){各子膜をdstmemに代入する}
			}
			break;

		case Instruction.FINDATOM:
			// findatom [dstatom, srcmem, func]
			ListIterator i = memArgs[1].atom.iterator();
			while (i.hasNext()){
				Atom a;
				a = (Atom)i.next();
				if ( a.functor == atomArgs[1]){
					atomArgs[0] = a;
				}
			}
			break;

		case Instruction.FUNC:
			//func [srcatom, func]
			if (atomArgs[0].functor == func){
				//同じだった
			} else {
				//違ってた
			}
			break;

		case Instruction.NORULES:
			//norules [srcmem]
			if(memArgs[0].rules.isEmpty()){
				//ルールが存在しないことを確認
			} else {
				//ルールが存在していることを確認
			}
			break;

		case Instruction.NATOMS:
			// natoms [srcmem, count]
			//if (memArgs[0].atoms.size() == count) { //確認した  }
			break;

		case Instruction.NFREELINKS:
			//nfreelinks [srcmem, count]
			//if (memArgs[0].freeLinks.size() == count) { //確認した  }
			break;

		case Instruction.NMEMS:
			//nmems [srcmem, count]
			//if (memArgs[0].mems.size() == count) { //確認した  }
			break;

		case Instruction.EQ:
			//eq [atom1, atom2]
			//eq [mem1, mem2]
			if(memArgs.length == 0){
				if (atomArgs[0] == atomArgs[1]){
					//同一のアトムを参照
				}
			} else {
				if (memArgs[0] == memArgs[1]){
					//同一の膜を参照
				}
			} 
			break;

		case Instruction.NEQ:
			//neq [atom1, atom2]
			//neq [mem1, mem2]
			if(memArgs.length == 0){
				if (atomArgs[0] != atomArgs[1]){
					//同一のアトムを参照
				}
			} else {
				if (memArgs[0] != memArgs[1]){
					//同一の膜を参照
				}
			} 
			break;

		case Instruction.REMOVEATOM:
			//removeatom [srcatom]
            
			break;

		case Instruction.REMOVEMEM:
			//removemem [srcmem]

			break;

		case Instruction.INSERTPROXIES:
			//insertproxies [parentmem M], [srcmem N]
            
			break;

		case Instruction.REMOVEPROXIES:

			break;

		case Instruction.NEWATOM:
			//newatom [dstatom, srcmem, func] 
			memArgs[1].atoms.add(atomArgs[1]);
			atomArgs[0] = atomArgs[1];
			break;

		case Instruction.NEWMEM:
			//newmem [dstmem, srcmem] 
			memArgs[1] = new Membrane(memArgs[0]);
			memArgs[0].mems.add(memArgs[1]);
			break;

		case Instruction.NEWLINK:
			//newlink [atom1, pos1, atom2, pos2]
			//atomArgs[0].args[pos1]    atomArgs[1].args[pos2]
			break;

		case Instruction.RELINK:
			//relink [atom1, pos1, atom2, pos2]
            
			break;
		case Instruction.UNIFY:
			break;
		case Instruction.DEQUEUEATOM:
			break;
		case Instruction.DEQUEUEMEM:
			break;
		case Instruction.MOVEMEM:
			break;
		case Instruction.RECURSIVELOCK:
			break;
		case Instruction.RECURSIVEUNLOCK:
			break;
		case Instruction.COPY:
			break;
		case Instruction.NOT:
			break;
		case Instruction.STOP:
			break;
		case Instruction.REACT:
			break;

		 default:
			System.out.println("Invalid rule");
			break;
		}
	}


	}
	public String toString() {
		StringBuffer s=new StringBuffer("");
		Iterator l;
		l = rules.iterator();
		while(l.hasNext()) s.append( ((Rule)l.next()).toString()+" " );
		return "@" + id + "  " + s;
	}
    
	public void showDetail() {
		Env.p("InterpretedRuleset.showDetail "+this);
		Iterator l;
		l = rules.listIterator();
		while(l.hasNext()) {
			Rule r = ((Rule)l.next());
			r.showDetail();
		} 
	}
}
/* 
 
[最も簡単な実装方法]

4種類のデータに対応するArrayListを保持する。
とりあえず最初はメソッド内の局所変数としてでよい。

命令引数が変数番号のときはgetやsetを使ってリスト内を間接参照する。

命令が失敗したら、ロックした膜を逆順に解放する。

proceed命令では自動解放せずにそのままreturnする。

spec命令の仕様はとりあえず無視して何となく作って下さい。
2引数の仕様を仮定する場合は4つのリストを全部varcountの長さで初期化すれば
大丈夫です。いずれ、この3/4が無駄になっているデータ保持方法を改めます。

**********

まずはbodyから作って下さい。

メソッドへの入力は配列でなくArrayListにして、
それを局所変数用にも使い回すようにするためにサイズ変更しても結構です。

*/



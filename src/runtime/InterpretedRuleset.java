package runtime;

import java.util.*;

// TODO 【重要】マッチング検査の途中で取得したロックを全て解放する必要がある

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
     * アトム主導テストを行い、マッチすれば適用する
     * @return ルールを適用した場合はtrue
     */
    boolean react(Membrane mem, Atom atom) {

	
	

	return false;
    }
    /**
     * 膜主導テストを行い、マッチすれば適用する
     * @return ルールを適用した場合はtrue
     */
    boolean react(Membrane mem) {
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
    //    private void body(int ruleid, AbstractMembrane[] memArgs, Atom[] atomArgs) {
    private void body(Rule rule, AbstractMembrane[] memArgs, Atom[] atomArgs) {
	Iterator i = rule.body.iterator();

	while(i.hasNext()){
	    hoge = (Instruction)i.next();

	switch (hoge){
	case Instruction.DEREF:
	    //deref [-dstatom, +srcatom, +srcpos, +dstpos]
	    //if (atomArgs[1].args[srcpos] == atomArgs[1].args[dstpos]) {
		//リンク先のアトムをdstatomに代入する。
	    //	    }
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

	    //廃止
	    //	case Instruction.LOCK:
	    //	    break;

	case Instruction.UNLOCK:
	    //unlock [srcmem]

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

package runtime;

import java.util.*;


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
	
    //	/** 膜主導実行用命令列。１つ目の添え字はルール番号 */
    //	public Instruction[] memMatch;
    //	/** アトム主導実行用命令列。Mapにすべき？ */
    //	public Instruction[] atomMatches;
    //	/** ボディ実行用命令列。１つ目の添え字はルール番号 */
    //	public Instruction[] body;
	
    /**
     * RuleCompiler では、まず生成してからデータを入れ込む。
     * ので、特になにもしない
     */
    public InterpretedRuleset() {
	rules = new ArrayList();
	id = ++lastId;
    }
	
	
    public String toString() {
	return "@" + id;
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
    private void body(int ruleid, AbstractMembrane[] memArgs, Atom[] atomArgs) {
	switch (ruleid){
	case Instruction.DEREF:
	    //deref [-dstatom, +srcatom, +srcpos, +dstpos]
	    if (atomArgs[1].args[srcpos] == atomArgs[1].args[dstpos]) {
		//リンク先のアトムをdstatomに代入する。
	    }
	    break;

	case Instruction.GETMEM:
	    //getmem [dstmem, srcatom]
	    //TODO:聞く:「膜の所属膜」とは？
	    //ruby版だとgetparentと同じ実装になってるのでとりあえずそうする
	    memArgs[0] = memArgs[1].mem;
	    break;

	case Instruction.GETPARENT:
	    //getparent [dstmem, srcmem] 
	    memArgs[0] = memArgs[1].mem;
	    break;

	case Instruction.ANYMEM:
	    break;

	case Instruction.FINDATOM:
	    break;

	case Instruction.FUNC:
	    break;

	case Instruction.NORULES:
	    break;

	case Instruction.NATOMS:
	    break;

	case Instruction.NFREELINKS:
	    break;

	case Instruction.NMEMS:
	    break;

	case Instruction.EQ:
	    break;

	case Instruction.NEQ:
	    break;
	case Instruction.LOCK:
	    break;
	case Instruction.UNLOCK:
	    break;
	case Instruction.REMOVEATOM:
	    break;
	case Instruction.REMOVEMEM :
	    break;
	case Instruction.INSERTPROXIES:
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
	    break;
	case Instruction.RELINK:
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
	
    public void showDetail() {
	Iterator l;
	l = rules.listIterator();
	while(l.hasNext()) ((Rule)l.next()).showDetail();
    }
}

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

	case Instruction.NEWMEM:
	    //newmem [dstmem, srcmem] 
	    memArgs[1] = new Membrane(memArgs[0]);
	    memArgs[0].mems.add(memArgs[1]);
	    break;

	case Instruction.NEWATOM:
	    //newatom [dstatom, srcmem, func] 
	    memArgs[1].atoms.add(atomArgs[1]);
	    atomArgs[0] = atomArgs[1];
	    break;

	case Instruction.REMOVEATOM:

	    break;

	case Instruction.REMOVEMEM:

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

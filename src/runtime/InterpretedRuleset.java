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
	switch ruleid{
	case Instruction.LOCK:
	    break;

	case Instruction.UNLOCK:
	    break;

	case Instruction.REMOVEATOM:
	    break;

	case Instruction.INSERTPROXIES:
	    break;

	case Instruction.REMOVEPROXIES:
	    break;

	case Instruction.NEWATOM:
	    break;

	case Instruction.NEWMEM:
	    break;

	case Instruction.NEWLINK:
	    break;

	case Instruction.RELINK:
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

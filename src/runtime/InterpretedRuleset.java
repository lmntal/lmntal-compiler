package runtime;

import java.util.Iterator;
import java.util.Arrays;

public final class InterpretedRuleset extends Ruleset {
	/** とりあえずルールの配列として実装 */
	private int id;
	private static int lastId=600;
	
	
	/** 膜主導実行用命令列。１つ目の添え字はルール番号 */
	public Instruction[] memMatch;
	/** アトム主導実行用命令列。Mapにすべき？ */
	public Instruction[] atomMatches;
	/** ボディ実行用命令列。１つ目の添え字はルール番号 */
	public Instruction[] body;
	
	public InterpretedRuleset() {
		id = ++lastId;
	}
	
	public void showDetail() {
		Iterator l;
		l = Arrays.asList( atomMatches ).listIterator();
		Env.p("--atommatches :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = Arrays.asList( memMatch ).listIterator();
		Env.p("--memmatch :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = Arrays.asList( body ).listIterator();
		Env.p("--body :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		Env.p(toString());
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
	 */
	private void body(int ruleid, AbstractMembrane[] memArgs, Atom[] atomArgs) {
	}
}

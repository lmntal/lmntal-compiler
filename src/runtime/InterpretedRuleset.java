package runtime;

public final class InterpretedRuleset extends Ruleset {
	/** とりあえずルールの配列として実装 */
	private int id;
	private static int lastId;
	
	
	/** 膜主導実行用命令列。１つ目の添え字はルール番号 */
	private HeadInstruction[][] memMatch;
	/** アトム主導実行用命令列。Mapにすべき？ */
	private HeadInstruction[][] atomMatches;
	/** ボディ実行用命令列。１つ目の添え字はルール番号 */
	private BodyInstruction[][] body;
	
	public InterpretedRuleset(compile.Rule[] rules) {
	}
	
	public String toString() {
		return "@" + id;
	}
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	boolean react(Membrane mem, AbstractAtom atom) {
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
	private void body(int ruleid, AbstractMembrane[] memArgs, AbstractAtom[] atomArgs) {
	}
}

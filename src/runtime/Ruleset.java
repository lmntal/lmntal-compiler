package runtime;

/**
 * ルールの集合。
 * 現在はルールの配列として表現しているが、将来的には複数のルールのマッチングを
 * １つのマッチングテストで行うようにする。
 */
abstract public class Ruleset {
	/** new束縛された名前の具体値を格納する配列 */
	protected Functor[] holes;
	abstract public String toString();
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem, Atom atom);
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem);
	/** new束縛された名前の具体値を指定して新しいRulesetを作成する。
	 * @return 新しいRuleset */
	//abstract
	public Ruleset fillHoles(Functor[] holes) { return null; }
	/**
	 * ルールセットのIDを返す
	 * @author nakajima
	 * @return ルールセットID
	 * 
	 * 
	 */
	abstract public String getGlobalRulesetID();
}

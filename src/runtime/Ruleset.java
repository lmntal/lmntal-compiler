package runtime;

/**
 * ルールの集合。
 * 現在はルールの配列として表現しているが、将来的には複数のルールのマッチングを
 * １つのマッチングテストで行うようにする。
 */
abstract public class Ruleset {
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
	/**
	 * ルールセットのIDを返す
	 * @author nakajima
	 * @return ルールセットID
	 * 
	 * 
	 */
	abstract public String getGlobalRulesetID();
}

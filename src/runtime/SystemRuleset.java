package runtime;

final class SystemRuleset extends Ruleset {
	public String toString() {
		return "System Ruleset Object";
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
	
}

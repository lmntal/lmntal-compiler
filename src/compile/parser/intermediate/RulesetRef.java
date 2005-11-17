package compile.parser.intermediate;

/**
 * パーズ中に、ルールセットオブジェクトの代わりに利用するオブジェクト。
 * パーズ終了後に、該当するルールセットオブジェクトに置き換える。
 */
class RulesetRef {
	private Integer id;
	RulesetRef(Integer id) {
		this.id = id;
	}
}

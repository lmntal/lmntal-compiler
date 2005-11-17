package compile.parser.intermediate;

/**
 * パーズ中に、ルールオブジェクトの代わりに利用するオブジェクト。
 * パーズ終了後に、該当するルールオブジェクトに置き換える。
 */
public class RuleRef {
	private Integer id;
	RuleRef(Integer id) {
		this.id = id;
	}
}

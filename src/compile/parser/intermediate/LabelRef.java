package compile.parser.intermediate;

/**
 * パーズ中に、ルールオブジェクトの代わりに利用するオブジェクト。
 * パーズ終了後に、該当するルールオブジェクトに置き換える。
 */
public class LabelRef {
	private Integer id;
	LabelRef(Integer id) {
		this.id = id;
	}
	Integer getId() {
		return id;
	}
}

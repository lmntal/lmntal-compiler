/**
 * リンクの単一化
 */

package compile.structure;

public final class LinkUnify extends Atom {
	
	/**
	 * リンク単一化をあらわすアトム名
	 */
	public static final String LINK_UNIFY_NAME = "="; // "builtin::unify";
	
	/**
	 * リンクの単一化を表す
	 * @param mem 親膜
	 * @param leftLink 左側のリンク
	 * @param rightLink 右側のリンク
	 */
	public LinkUnify(Membrane mem, LinkOccurrence leftLink, LinkOccurrence rightLink) {
		super(mem, LINK_UNIFY_NAME, 2);
		this.args[0] = leftLink;
		this.args[1] = rightLink;	
	}

	/**
	 * リンクの単一化を表す
	 * @param mem 親膜
	 */
	public LinkUnify(Membrane mem) {
		super(mem, LINK_UNIFY_NAME, 2);
	}
}
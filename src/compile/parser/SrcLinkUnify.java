/**
 * ソースファイル中のリンク単一化の表現
 */

package compile.parser;
import java.util.LinkedList;

class SrcLinkUnify extends SrcAtom {

	private static final String LINK_UNIFY = "builtin::unify";
	
	/**
	 * 指定されたリンクの単一化を表すアトムを作成します
	 * @param leftLink 単一化するリンク
	 * @param rightLink 単一化するリンク
	 */
	public SrcLinkUnify(SrcLink leftLink, SrcLink rightLink) {
		super(LINK_UNIFY);
		LinkedList link = new LinkedList();
		link.add(leftLink);
		link.add(rightLink);
		this.process = link;
	}
}
package compile.structure;

/**
 * プロキシー
 * プロキシーの特別名として、PROXY_NAME を持つ
 * @author Takahiko Nagata
 * @date 2003/11/05
 */
final public class ProxyAtom extends Atom {
	
	public final static String PROXY_NAME  = "builtin::proxy";

	/**
	 * 膜を通過する自由リンクを管理するプロキシーを生成します
	 * @param mem 親膜
	 * @param insideLink 内側へのリンク
	 * @param outsideLink 膜を突き抜ける外側へのリンク
	 */
	public ProxyAtom(Membrane mem, LinkOccurrence insideLink, LinkOccurrence outsideLink) {
		super(mem, PROXY_NAME, 2);
		this.args[0] = insideLink;
		this.args[1] = outsideLink;
	}
}

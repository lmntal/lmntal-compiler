package compile.structure;

/**
 * プロキシー
 * プロキシーの特別名として、PROXY_NAME を持つ
 * プロキシのリンクのうち、
 * 他の膜へのリンクは0番目、
 * 同じ膜へのリンクは1番目とする
 * @author Takahiko Nagata
 * @date 2003/11/05
 */
final public class ProxyAtom extends Atom {
	
	public final static String PROXY_NAME  = "proxy";

	/**
	 * 膜を通過する自由リンクを管理するプロキシーを生成します
	 * @param mem 親膜
	 */
	public ProxyAtom(Membrane mem) {
		super(mem, PROXY_NAME, 2);
	}
}

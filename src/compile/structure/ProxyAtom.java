package compile.structure;

/**
 * プロキシー
 * プロキシーの特別名として、PROXY_NAME を持つ
 * プロキシのリンクのうち、
 * 他の膜へのリンクは1つ目のリンク(配列 0番目)
 * 同じ膜へのリンクは2つ目のリンク(配列 1番目)
 * とする
 * @author Takahiko Nagata
 * @date 2003/11/05
 * TODO typeは廃止して文字列から直接生成した方がよい。またはruntime.Functor内の定義を使い、このファイルを廃止する
 */
final public class ProxyAtom extends Atom {
	
	public final static String PROXY_NAME  = "proxy";

	/**
	 * 自由リンク出力管理アトムタイプ
	 */
	public final static int INSIDE_PROXY = 0x01;

	/**
	 * 自由リンク出力管理アトム名
	 */
	public final static String INSIDE_PROXY_NAME = "inside_proxy";
	
	/**
	 * 自由リンク出力管理アトムタイプ
	 */
	public final static int OUTSIDE_PROXY = 0x02;

	/**
	 * 自由リンク出力管理アトム名
	 */
	public final static String OUTSIDE_PROXY_NAME = "outside_proxy";

	/**
	 * 膜を通過する自由リンクを管理するプロキシーを生成します
	 * @param mem 親膜
	 */
	public ProxyAtom(Membrane mem) {
		this(-1, mem);
	}
	
	/**
	 * 膜を通過する自由リンクを管理するプロキシーを生成します
	 * @param type プロキシの種類 
	 * @param mem 親膜
	 */
	public ProxyAtom(int type, Membrane mem) {
		super(mem, getProxyName(type), 2);
	}
	
	/**
	 * プロキシの種類からプロキシ名を得ます
	 * @param type プロキシタイプ INSIDE_PROXY or OUTSIDE_PROXY
	 * @return タイプに従ったプロキシ名
	 */
	public static String getProxyName(int type) {
		switch (type) {
			case INSIDE_PROXY: return INSIDE_PROXY_NAME;
			case OUTSIDE_PROXY: return OUTSIDE_PROXY_NAME;
			default: return PROXY_NAME;
		}
	}
}

package compile.structure;

/**
 * 膜を通過する自由リンクを管理するプロキシアトムの構造を表すクラス。
 * プロキシのリンクのうち、
 * 他の膜へのリンクは1つ目のリンク(配列 0番目)
 * 同じ膜へのリンクは2つ目のリンク(配列 1番目)
 * とする。
 * @author Takahiko Nagata, n-kato
 * @date 2003/11/05
 * <p>todo このクラスはいずれ廃止する
 */
final public class ProxyAtom extends Atom {

	/** 自由リンク出力管理アトム名 */
	public final static String INSIDE_PROXY_NAME = runtime.Functor.INSIDE_PROXY.getName();
	
	/** 自由リンク入力管理アトム名 */
	public final static String OUTSIDE_PROXY_NAME = runtime.Functor.OUTSIDE_PROXY.getName();
	
	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 * @param name プロキシの名前
	 */
	public ProxyAtom(Membrane mem, String name) {
		super(mem,name,2);
	}
	
}

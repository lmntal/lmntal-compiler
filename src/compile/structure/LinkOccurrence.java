package compile.structure;

/** 
 * ソースコード中のリンクの各出現を表すクラス
 */
public final class LinkOccurrence {
	/**
	 * リンク名
	 */
	public String name;
	
	/**
	 * 所属するアトムオブジェクト
	 */
	public Atom atom;
	
	/**
	 * アトムでのリンク位置
	 */
	public int pos;
	
	/** 2回しか出現しない場合に、もう片方の出現を保持する */
	public LinkOccurrence buddy;
	
	/**
	 * リンク出現を生成する。
	 * @param name リンク名
	 * @param atom 所属するアトム
	 * @param pos 所属するアトムでの場所
	 */
	LinkOccurrence(String name, Atom atom, int pos) {
		this.name = name;
		this.atom = atom;
		this.pos = pos;
	}
}

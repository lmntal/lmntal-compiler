package compile.structure;

/** 
 * ソースコード中のルールの構造を表すクラス
 */
public final class RuleStructure {
	/** 所属膜。コンパイル時につかう
	 * todo parentはいずれmemに名称変更する */
	public Membrane parent;

	/** Headを格納する膜 */
	public Membrane leftMem = new Membrane(null);
	
	/** Bodyを格納する膜 */
	public Membrane rightMem = new Membrane(null);
	
	/** ガードを格納する膜 */
	public Membrane guardMem = new Membrane(null);

	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 */
	public RuleStructure(Membrane mem) {
		this.parent = mem;
	}

	public String toString() {
		return "( "+leftMem.toStringWithoutBrace()+" :- "+rightMem.toStringWithoutBrace()+" )";
	}
}

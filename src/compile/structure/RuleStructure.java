package compile.structure;

import java.util.HashMap;

/** 
 * ソースコード中のルールの構造を表すクラス
 */
public final class RuleStructure {
	/** 所属膜。コンパイル時につかう
	 * <p>todo parentはいずれmemに名称変更する */
	public Membrane parent;

	/** Headを格納する膜 */
	public Membrane leftMem = new Membrane(null);
	
	/** Bodyを格納する膜 */
	public Membrane rightMem = new Membrane(null);
	
	/** ガードを格納する膜 */
	public Membrane guardMem = new Membrane(null);
	
	/** プロセス文脈の限定名 -> ContextDef */
	public HashMap processContexts = new HashMap();

	/** ルール文脈の限定名 -> ContextDef */
	public HashMap ruleContexts = new HashMap();

	/** 型付きプロセス文脈の限定名 -> ContextDef */
	public HashMap typedProcessContexts = new HashMap();

	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 */
	public RuleStructure(Membrane mem) {
		this.parent = mem;
		// io:{(print:-inline)} の print は io.print にしたい。
		// が、print は io 膜直属ではなくルール左辺の膜に所属するのでルールの膜も同じ名前をつけておく。
		leftMem.name = mem.name;
		rightMem.name = mem.name;
	}

	public String toString() {
		return "( "+leftMem.toStringWithoutBrace()+" :- "+rightMem.toStringWithoutBrace()+" )";
	}
}

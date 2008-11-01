package compile.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/** 
 * ソースコード中のルールの構造を表すクラス
 */
public final class RuleStructure {
	/** 所属膜。コンパイル時につかう
	 * <p>todo parentはいずれmemに名称変更する */
	public Membrane parent;
	
	/** ルール名
	 */
	public String name;
	/** テキスト表現 */
	private  String text;
	
	/** 左辺が空のときの警告を抑制するかどうか */
	public boolean fSuppressEmptyHeadWarning = false;

	/** Headを格納する膜 */
	public Membrane leftMem = new Membrane(null);
	
	/** Bodyを格納する膜 */
	public Membrane rightMem = new Membrane(null);
	
	/** ガード型制約を格納する膜 */
	public Membrane guardMem = new Membrane(null);

//	/** ガードの型制約 (TypeConstraint) のリスト */
//	public LinkedList typeConstraints = new LinkedList();
	
	/** ガード否定条件（ProcessContextEquationのLinkedList）のリスト */
	public LinkedList guardNegatives = new LinkedList();
	
	/** プロセス文脈の限定名 ("$p"などのString) -> 文脈の定義 (ContextDef) */
	public HashMap<String, ContextDef> processContexts = new HashMap<String, ContextDef>();

	/** ルール文脈の限定名 ("@p"などのString) -> 文脈の定義 (ContextDef) */
	public HashMap<String, ContextDef> ruleContexts = new HashMap<String, ContextDef>();

	/** 型付きプロセス文脈の限定名 ("$p"などのString) -> 文脈の定義 (ContextDef) */
	public HashMap<String, ContextDef> typedProcessContexts = new HashMap<String, ContextDef>();
	
	/** 行番号 2006.1.22 by inui */
	public int lineno;

	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 */
	public RuleStructure(Membrane mem, String text) {
		this.parent = mem;
		// io:{(print:-inline)} の print は io.print にしたい。
		// が、print は io 膜直属ではなくルール左辺の膜に所属するのでルールの膜も同じ名前をつけておく。
		leftMem.name = mem.name;
		rightMem.name = mem.name;
		this.text = text;
	}
	
	//2006.1.22 by inui
	/**
	 * コンストラクタ
	 * @param mem 所属膜
	 * @param lineno 行番号
	 */
	public RuleStructure(Membrane mem, String text, int lineno) {
		this(mem, text);
		this.lineno = lineno;
	}

	public String toString() {
		return text;
	}
}

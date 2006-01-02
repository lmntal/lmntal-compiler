package compile.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

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
	public HashMap processContexts = new HashMap();

	/** ルール文脈の限定名 ("@p"などのString) -> 文脈の定義 (ContextDef) */
	public HashMap ruleContexts = new HashMap();

	/** 型付きプロセス文脈の限定名 ("$p"などのString) -> 文脈の定義 (ContextDef) */
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
		String text="";
		if(name!=null) text+=name+" @@ ";
		text += "( " + leftMem.toStringWithoutBrace() + " :- ";
		String guard = "";
		if (!guardMem.atoms.isEmpty()) {
			guard += guardMem.toStringAsGuardTypeConstraints() + " ";
		}
		Iterator it = guardNegatives.iterator();
		while (it.hasNext()) {
			String eqstext = "";
			Iterator it2 = ((LinkedList)it.next()).iterator();
			while (it2.hasNext()) {
				eqstext += "," + ((ProcessContextEquation)it2.next()).toString();
			}
			if (eqstext.length() > 0)  eqstext = eqstext.substring(1);
			guard += "\\+(" + eqstext + ") ";
		}
		if (guard.length() > 0)  text += guard + "| ";
		return text + rightMem.toStringWithoutBrace() + " )";
	}
	// 2006.01.02 okabe
	/**
	 * @return String　ルール構造の文字列表現（アトム名やファンクタ名は省略しない）
	 */
	public String encode() {
		String text="";
		if(name!=null) text+=name+" @@ ";
		text += "( " + leftMem.encode() + " :- ";
		String guard = "";
		if (!guardMem.atoms.isEmpty()) {
			guard += guardMem.toStringAsGuardTypeConstraints() + " ";
		}
		Iterator it = guardNegatives.iterator();
		while (it.hasNext()) {
			String eqstext = "";
			Iterator it2 = ((LinkedList)it.next()).iterator();
			while (it2.hasNext()) {
				eqstext += "," + ((ProcessContextEquation)it2.next()).toString();
			}
			if (eqstext.length() > 0)  eqstext = eqstext.substring(1);
			guard += "\\+(" + eqstext + ") ";
		}
		if (guard.length() > 0)  text += guard + "| ";
		return text + rightMem.encode() + " )";
	}
}

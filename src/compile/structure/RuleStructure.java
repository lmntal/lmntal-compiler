package compile.structure;

/** 
 * ソースコード中のルールの構造を表すクラス
 */
public final class RuleStructure {
	/**
	 * Headルールを格納する膜
	 */
	public Membrane leftMem = new Membrane(null);
	
	/**
	 * Bodyルールを格納する膜
	 */
	public Membrane rightMem = new Membrane(null);
	
	public String toString() {
		return "( "+leftMem.toStringWithoutBrace()+" :- "+rightMem.toStringWithoutBrace()+" )";
	}
}

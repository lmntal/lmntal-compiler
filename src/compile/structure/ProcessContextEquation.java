package compile.structure;

/** 
 * ソースコード中のガード否定条件内の1つの等式（例: $p=(a(X),$pp) ）の構造を表すクラス<br>
 * 1つのガード否定条件は、ProcessContextEquationのLinkedListとして表現される。
 * <p>
 * 例: [$p=(a(X),$pp),$q=(b(X),$qq)]
 */
public final class ProcessContextEquation
{
	/**
	 * 等式左辺のプロセス文脈の定義
	 */
	public ContextDef def;

	/**
	 * 等式右辺のプロセス
	 */
	public Membrane mem = new Membrane(null);

	/**
	 * コンストラクタ
	 * @param def 等式左辺のプロセス文脈の定義
	 * @param mem 等式右辺のプロセスを格納する仮想的な膜
	 */
	public ProcessContextEquation(ContextDef def, Membrane mem)
	{
		this.def = def;
		this.mem = mem;
	}

	public String toString()
	{
		return def.toString() + "=(" + mem.toStringWithoutBrace() + ")";
	}
}

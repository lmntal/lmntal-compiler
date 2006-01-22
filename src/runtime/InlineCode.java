/*
 * 作成日: 2003/12/16
 *
 */
package runtime;

/**
 * インラインコードのインターフェース。
 * これを実装するクラスはコンパイラが自動生成する。
 * @author hara
 *
 */
public interface InlineCode {
	public boolean runGuard(String guardID, Membrane mem, Object obj) throws Exception;
	public void run(Atom a, int codeID);
}


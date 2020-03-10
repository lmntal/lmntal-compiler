/*
 * 作成日: 2003/12/16
 *
 */
package runtime;

import runtime.exception.GuardNotFoundException;

/**
 * インラインコードのインターフェース。
 * これを実装するクラスはコンパイラが自動生成する。
 * @author hara
 *
 */
public interface InlineCode {
	boolean runGuard(String guardID, Membrane mem, Object obj);
	void run(Atom a, int codeID);
}


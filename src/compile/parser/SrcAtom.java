/**
 * ソースファイル中でのアトム表現
 */
package compile.parser;

import java.util.LinkedList;

class SrcAtom {

	protected LinkedList process = null;
	protected String name = null;
	
	/**
	 * 指定された名前で子供プロセスなしで初期化します
	 * @param name アトム名
	 */
	public SrcAtom(String name) {
		this(name, null);
	}
	/**
	 * 指定された名前と子供プロセスで初期化します
	 * @param name アトム名
	 * @param process 子供プロセス
	 */
	public SrcAtom(String name, LinkedList process) {
		this.name = name;
		this.process = process;	
	}
	
	/**
	 * このアトムの名前を得ます
	 * @return アトム名をあらわす文字列
	 */
	public String getName() { return name; }
	
	/**
	 * このアトムの子プロセスを得ます
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
}

/**
 * ソースファイル中でのアトム表現
 */
package compile.parser;

import java.util.LinkedList;

class SrcAtom {

	protected LinkedList process = null;
	protected String name = null;

	/**
	 * ソースコード中での出現位置(行)
	 * @author Tomohito Makino
	 */
	int line;
	/**
	 * ソースコード中での出現位置(桁)
	 * @author Tomohito Makino
	 */
	int column;

	/**
	 * 指定された名前で子供プロセスなしで初期化します
	 * @param name アトム名
	 */
	public SrcAtom(String name) {
		this(name, new LinkedList());
	}
	
	/**
	 * 指定された名前と子供プロセスで初期化します
	 * @param name アトム名
	 * @param process 子供プロセス
	 */
	public SrcAtom(String name, LinkedList process) {
		this(name, process, -1, -1);
	}

	/**
	 * デバッグ情報も受け取るコンストラクタ(子供プロセス無し)
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(String name, int line, int column) {
		this(name, new LinkedList(), line, column);
	}
	
	/**
	 * デバッグ情報も受け取るコンストラクタ
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param process 子供プロセス
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(String name, LinkedList process, int line, int column) {
		this.name = name;
		this.process = process;
		this.line = line;
		this.column = column;	
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

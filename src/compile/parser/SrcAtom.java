package compile.parser;
import java.util.LinkedList;


/**（編集中）*/
/** ソースファイル中のアトム表現 */
class SrcAtom {

	protected LinkedList process = null;
	//protected String name = null;
	protected SrcName name;
	
	/**
	 * ソースコード中での出現位置(行)
	 * @author Tomohito Makino
	 */
	int line = -1;
	/**
	 * ソースコード中での出現位置(桁)
	 * @author Tomohito Makino
	 */
	int column = -1;

	/**
	 * 指定された名前の子プロセスなしのアトム構文を生成する
	 * @param name アトム名
	 */
	public SrcAtom(SrcName name) {
		this(name, new LinkedList(), -1,-1);
	}
	public SrcAtom(String name) {
		this(new SrcName(name));
	}
	
	/**
	 * 指定された名前と子供プロセスで初期化します
	 * @param name アトム名
	 * @param process 子供プロセス
	 */
	public SrcAtom(SrcName name, LinkedList process) {
		this(name, process, -1,-1);
	}
	public SrcAtom(String name, LinkedList process) {
		this(new SrcName(name), process);
	}

	/**
	 * デバッグ情報も受け取るコンストラクタ(子供プロセス無し)
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(SrcName name, int line, int column) {
		this(name, new LinkedList(), line, column);
	}
	public SrcAtom(String name, int line, int column) {
		this(new SrcName(name),line,column);
	}
	
	/**
	 * デバッグ情報も受け取るコンストラクタ
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param process 子供プロセス
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(SrcName name, LinkedList process, int line, int column) {
		this.name = name;
		this.process = process;
		this.line = line;
		this.column = column;	
	}
	public SrcAtom(String name, LinkedList process, int line, int column) {
		this(new SrcName(name),process,line,column);
	}
	
	public void setSourceLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}

	
	/** アトム名を返す */
	public SrcName getName() { return name; }
	
	/**
	 * このアトムの子プロセスを得ます
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
}

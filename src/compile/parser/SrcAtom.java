package compile.parser;
import java.util.LinkedList;

/** ソースファイル中のアトム表現 */
class SrcAtom {
	protected LinkedList process = null;
	/** 名前トークン */
	protected SrcName srcname;
	
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
	public SrcAtom(String name) {
		this(new SrcName(name));
	}
	/**
	 * 指定された名前トークンを持つ子プロセスなしのアトム構文を生成する
	 * @param srcname 名前トークン
	 */
	public SrcAtom(SrcName srcname) {
		this(srcname, new LinkedList(), -1,-1);
	}
	
	/**
	 * 指定された名前と子供プロセスで初期化します
	 * @param name アトム名
	 * @param process 子供プロセス
	 */
	public SrcAtom(String name, LinkedList process) {
		this(new SrcName(name), process, -1,-1);
	}

	/**
	 * デバッグ情報も受け取るコンストラクタ(子供プロセス無し)
	 * @author Tomohito Makino
	 * @param srcname 名前トークン
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(SrcName srcname, int line, int column) {
		this(srcname, new LinkedList(), line, column);
	}	
	/**
	 * デバッグ情報も受け取るコンストラクタ
	 * @author Tomohito Makino
	 * @param nametoken 名前トークン
	 * @param process 子供プロセス
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(SrcName nametoken, LinkedList process, int line, int column) {
		this.srcname = nametoken;
		this.process = process;
		this.line = line;
		this.column = column;	
	}
	
	public void setSourceLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}

	
	/** 名前トークンを取得する 
	 * @deprecated*/
	public SrcName getSrcName() { return srcname; }

	/** アトム名を取得する */
	public String getName() { return srcname.getName(); }
	/** アトム名トークンの種類を取得する */
	public int getNameType() { return srcname.getType(); }
	
	/**
	 * このアトムの子プロセスを得ます
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
	
	public String toString() { return SrcDumper.dumpAtom(this,0); }
}

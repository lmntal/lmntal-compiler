package compile.parser;
import java.util.LinkedList;


/**（編集中）*/
/** ソースファイル中のアトム表現 */
class SrcAtom {

	protected LinkedList process = null;
	protected String name = null;
	
	/** 名前トークンの種類
	 * <p>（名前のためのクラスを作り、種類ごとにサブクラス化するのが正しいが、サボった）*/
	int nametype;

	// typeの値
	static final int PLAIN   = 0;		// aaa
	static final int SYMBOL  = 1;		// 'aaa' 'AAA'
	static final int STRING  = 2;		// "aaa" "AAA"
	static final int QUOTED  = 3;		// [[aaa]] [[AAA]]
	static final int PATHED  = 4;		// aaa.bbb
	static final int INTEGER = 10;	// 345 -345
	static final int FLOAT   = 11;	// 3.14 -3.14e-33
	
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
	 * 指定されたPLAINな名前の子プロセスなしのアトム構文を生成する
	 * @param name アトム名
	 */
	public SrcAtom(String name) {
		this(name, PLAIN);
	}

	/**
	 * 指定された名前で子供プロセスなしで初期化します
	 * @param name アトム名
	 * @param nametype 名前トークンの種類
	 */
	public SrcAtom(String name, int nametype) {
		this(name, nametype, new LinkedList(), -1,-1);
	}
	
	/**
	 * 指定された名前と子供プロセスで初期化します
	 * @param name アトム名
	 * @param process 子供プロセス
	 */
	public SrcAtom(String name, LinkedList process) {
		this(name, PLAIN, process, -1,-1);
	}

	/**
	 * デバッグ情報も受け取るコンストラクタ(子供プロセス無し)
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(String name, int nametype, int line, int column) {
		this(name, nametype, new LinkedList(), line, column);
	}
	
	/**
	 * デバッグ情報も受け取るコンストラクタ
	 * @author Tomohito Makino
	 * @param name アトム名
	 * @param process 子供プロセス
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */
	public SrcAtom(String name, int nametype, LinkedList process, int line, int column) {
		this.name = name;
		this.nametype = nametype;
		this.process = process;
		this.line = line;
		this.column = column;	
	}
	
	public void setSourceLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}

	
	/**
	 * このアトムの名前を得ます
	 * @return アトム名をあらわす文字列
	 */
	public String getName() { return name; }

	public int getNameType() { return nametype; }
	
	/**
	 * このアトムの子プロセスを得ます
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
}

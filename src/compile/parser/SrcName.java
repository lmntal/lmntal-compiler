package compile.parser;

/**
 * ソースファイル中の名前トークン（アトムの名前の記述に使用される文字列）を表すクラス
 * <p>このクラスは現在、プロセス文脈名およびルール文脈名では使用されない。
 * <p>
 * '-1'(X)と書きたいことがあるため、処理系の方針として、
 * 12と'12'は区別せず、1引数ならばどちらも整数とみなすように仮決定した。
 * <p>
 * 12 という名前のシンボルは [[12]] と記述する。
 * 12]]33 という名前のシンボルは '12]]33' と記述する。
 */
class SrcName {
	/** 名前トークンが表す文字列 */
	protected String name;	
	/** 名前トークンの種類 */
	protected int type;

	// typeの値
	static final int PLAIN   = 0;		// aaa 12 -12 3.14 -3.14e-1
	static final int SYMBOL  = 1; 	// 'aaa' 'AAA' '12' '-12' '3.14' '-3.14e-1'
	static final int STRING  = 2;		// "aaa" "AAA" "12" "-12" "3.14" "-3.14e-1"
	static final int QUOTED  = 3;		// [[aaa]] [[AAA]] [[12]] [[-12]] [[3.14]] [[-3.14e-1]]
	static final int PATHED  = 4;		// module.p module:p
	
	/** 標準の名前トークンの表現を生成する。
	 * @param name 名前 */
	public SrcName(String name) {
		this.name = name;
		this.type = PLAIN;
	}
	/** 指定された種類の名前トークンの表現を生成する。*/
	public SrcName(String name, int type) {
		this.name = name;
		this.type = type;
	}
	/** この名前トークンが表す文字列を取得する */
	public String getName() {
		return name;
	}
	/** トークンの種類を返す */
	public int getType() {
		return type;
	}
}
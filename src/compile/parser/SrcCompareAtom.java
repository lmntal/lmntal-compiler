/**
 * ガード中に出現する比較アトム
 */
package compile.parser;

import java.util.LinkedList;

class SrcCompareAtom extends SrcAtom {

	public static final int LT = 0x0001; // <
	public static final int LE = 0x0002; // <=
	public static final int GT = 0x0011; // >
	public static final int GE = 0x0012; // <=
	public static final int EQ = 0x0021; // ==
	public static final int NE = 0x0022; // !=
	
	private int type;
	
	/**
	 * 比較アトムを生成します
	 * @param type 比較の種類
	 * @param left 左プロセス
	 * @param right 右プロセス
	 */
	public SrcCompareAtom(int type, Object left, Object right) {
		super(getTypeString(type));
		this.type = type;
		
		process = new LinkedList();
		process.add(left);
		process.add(right);
	}
	
	/**
	 * 比較の種類から名前を得ます
	 * @param type 比較の種類
	 * @return 指定された種類に対応した文字列、なければnull
	 */
	public static String getTypeString(int type) {
		switch (type) {
			case LT: return "<";
			case LE: return "<=";
			case GT: return ">";
			case GE: return ">=";
			case EQ: return "==";
			case NE: return "!=";
		}
		return null;
	}
}
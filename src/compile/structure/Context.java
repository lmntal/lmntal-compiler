package compile.structure;

import java.util.List;
import java.util.ArrayList;

/** 
 * ProcessContextとRuleContextの親となる抽象クラス
 */
public abstract class Context {
	/**
	 * コンテキストの名前
	 */
	protected String name;
	
	/**
	 * コンストラクタ
	 * @param name コンテキスト名
	 */
	protected Context(String name) {
		this.name = name;
		status = ST_FRESH;
	}
	
	/**
	 * コンテキストの名前を得ます
	 * @return コンテキスト名
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 左辺での所属膜
	 */
	public Membrane lhsMem;
	
	/**
	 * 右辺での所属膜の配列
	 */
	List rhsMems = new ArrayList();
	
	/**
	 * 現在の状態。ST_で始まる定数のいずれかの値をとる
	 */
	public int status = ST_FRESH;

	/**
	 * 初期状態
	 */
	public static final int ST_FRESH = 0;

	/**
	 * 左辺に一度出現した状態
	 */
	public static final int ST_LHSOK = 1;

	/**
	 * 左辺・右辺両方に出現した状態
	 */
	public static final int ST_READY = 2;
	
	/**
	 * エラー
	 */
	static final int ST_ERROR = 3;
}

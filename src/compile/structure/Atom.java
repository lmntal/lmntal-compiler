package compile.structure;

import runtime.Functor;
import java.util.Arrays;

/**
 * ソースコード中のアトムの構造を表すクラス
 * データ構造のみを持つ
 * @author Takahiko Nagata
 * @date 2003/10/28
 */
public class Atom {
	/**
	 * 親膜
	 */
	public Membrane mem = null;
	
	/**
	 * アトムの名前
	 */
	public Functor functor;
	
	/**
	 * アトムのリンク構造
	 */
	public LinkOccurrence[] args;
	
	/**
	 * コンストラクタ
	 * @param mem このアトムの親膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 */
	public Atom(Membrane mem, String name, int arity) {
		this.mem = mem;
		functor = new Functor(name, arity);
		args = new LinkOccurrence[arity];
	}
	
	public String toString() {
		return functor+(args.length==0 ? "" : "("+Arrays.asList(args)+")");
	}
}
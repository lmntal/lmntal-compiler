package compile.structure;

import runtime.Functor;
import runtime.Inline;
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
	 * デバッグ情報:ソースコード中での出現位置(行)
	 * 情報が無いときは-1を代入
	 * @author Tomohito Makino
	 */
	public int line;
	
	/**
	 * デバッグ情報:ソースコード中での出現位置(桁)
	 * 情報が無いときは-1を代入
	 * @author Tomohito Makino
	 */
	public int column;

	/**
	 * コンストラクタ
	 * @param mem このアトムの親膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 */
	public Atom(Membrane mem, String name, int arity) {
		this(mem,name,arity,-1,-1);
	}
	
	/**
	 * デバッグ情報を保持するコンストラクタ
	 * @author Tomohito Makino
	 * @param mem このアトムの親膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */	
	public Atom(Membrane mem, String name, int arity, int line, int column){
		this.mem = mem;
		functor = new Functor(name, arity);
		args = new LinkOccurrence[arity];
		this.line = line;
		this.column = column;
		
		//Inline
		Inline.add(name);
	}
	
	public String toString() {
		return functor+(args.length==0 ? "" : "("+Arrays.asList(args)+")");
	}
}
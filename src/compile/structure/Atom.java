package compile.structure;

import runtime.Functor;
import runtime.Inline;
import java.util.Arrays;

/**
 * ソースコード中のアトム（またはアトム集団）の構造を表すクラス。
 * データ構造のみを持つ。
 * @author Takahiko Nagata
 * @date 2003/10/28
 */
public class Atom {
	/** 所属膜 */
	public Membrane mem = null;
	/** アトムのファンクタ */
	public Functor functor;
	/** アトムのリンク列（またはアトム集団のリンク束列） */
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
	 * @param mem このアトムの所属膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 */
	public Atom(Membrane mem, String name, int arity) {
		this(mem,name,arity,-1,-1);
	}
	
	/**
	 * デバッグ情報を保持するコンストラクタ
	 * @author Tomohito Makino
	 * @param mem このアトムの所属膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 */	
	public Atom(Membrane mem, String name, int arity, int line, int column){
		this.mem = mem;
		functor = new Functor(name, arity, mem);
		args = new LinkOccurrence[arity];
		this.line = line;
		this.column = column;
		
		//Inline
		Inline.add(name);
	}
	
	public String toString() {
		if (args.length == 0) return functor.getAbbrName();
		String argstext = Arrays.asList(args).toString();
		argstext = argstext.substring(1, argstext.length() - 1);
		argstext = argstext.replaceAll(", ",",");
		return functor.getAbbrName() + "(" + argstext + ")";
	}
}
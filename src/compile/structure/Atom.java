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
//	/** 明示的に指定されたモジュール名（明示的に指定されていない場合はnull）*/
//	public String path = null;
	/** アトムのファンクタ */
	public Functor functor;
	/** アトムのリンク列（またはアトム集団のリンク束列） */
	public LinkOccurrence[] args;

	/**
	 * デバッグ情報:ソースコード中での出現位置(行)
	 * 情報が無いときは-1を代入
	 * @author Tomohito Makino
	 */
	public int line = -1;
	
	/**
	 * デバッグ情報:ソースコード中での出現位置(桁)
	 * 情報が無いときは-1を代入
	 * @author Tomohito Makino
	 */
	public int column = -1;

	/**
	 * コンストラクタ
	 * @param mem このアトムの所属膜
	 * @param functor ファンクタ
	 */
	public Atom(Membrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		args = new LinkOccurrence[functor.getArity()];
		Inline.add(functor.getName());
	}

	/**
	 * コンストラクタ
	 * @param mem このアトムの所属膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 */
	public Atom(Membrane mem, String name, int arity) {
		this(mem,new Functor(name,arity));
	}
	
	/**
	 * デバッグ情報を保持するコンストラクタ
	 * @author Tomohito Makino
	 * @param mem このアトムの所属膜
	 * @param name アトム名を表す文字列
	 * @param arity リンクの数
	 * @param line ソースコード上での出現位置(行)
	 * @param column ソースコード上での出現位置(桁)
	 * @deprecated
	 */	
	public Atom(Membrane mem, String name, int arity, int line, int column){
		this(mem,name,arity);
		setSourceLocation(line,column);		
	}
	public void setSourceLocation(int line, int column) {
		this.line = line;
		this.column = column;
	}
	public String toString() {
		if (args.length == 0) return functor.getQuotedAtomName();
		String argstext = Arrays.asList(args).toString();
		argstext = argstext.substring(1, argstext.length() - 1);
		argstext = argstext.replaceAll(", ",",");
		return functor.getQuotedFunctorName() + "(" + argstext + ")";
	}
	public String getPath() {
		return functor.getPath();
	}
	
}
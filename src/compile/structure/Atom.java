package compile.structure;

import runtime.Functor;

/**
 * ソースコード中のアトム（またはアトム集団または型制約）の構造を表すクラス。
 * @author Takahiko Nagata
 * @date 2003/10/28
 */
public class Atom extends Atomic{
//	/** 明示的に指定されたモジュール名（明示的に指定されていない場合はnull）*/
//	public String path = null;
	/** アトムのファンクタ */
	public Functor functor;

	/**
	 * コンストラクタ
	 * @param mem このアトムの所属膜
	 * @param functor ファンクタ
	 */
	public Atom(Membrane mem, Functor functor) {
		super(mem, functor.getArity());
		this.functor = functor;
		// ここでいいのかな hara
		if(functor.getName().equals("system_ruleset")) mem.is_system_ruleset=true;
		// todo 「モジュール機能」を使って表現した方がいいと思います。
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

	public String toString() {
		if (args.length == 0) return functor.getQuotedAtomName();
		String argstext = "";
		for (int i = 0; i < args.length; i++) {
			argstext += "," + args[i];
		}
		return functor.getQuotedFunctorName() + "(" + argstext.substring(1) + ")";
	}
	public String toStringAsTypeConstraint() {
		if (args.length == 0) return functor.getQuotedAtomName();
		String argstext = "";
		for (int i = 0; i < args.length; i++) {
			argstext += "," + ((ProcessContext)args[i].buddy.atom).getQualifiedName();
		}
		return functor.getQuotedFunctorName() + "(" + argstext.substring(1) + ")";
	}
	public String getPath() {
		return functor.getPath();
	}
	public String getName() {
		return functor.getName();
	}

}
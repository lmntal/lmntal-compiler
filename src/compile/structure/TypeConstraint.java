package compile.structure;

import runtime.Functor;
import java.util.Arrays;

/**
 * 編集中。
 * ソースコード中の（ガードにおける）型制約の構造を表すクラス。
 * @author n-kato
 */
public class TypeConstraint {
	/** 型制約アトムのファンクタ */
	public Functor functor;
	/** 型制約アトムの型付きプロセス文脈名の列 */
	public ContextDef[] args;

	/** デバッグ情報:ソースコード中での出現位置(行) */
	public int line = -1;
	/** デバッグ情報:ソースコード中での出現位置(桁) */
	public int column = -1;

	/**
	 * コンストラクタ
	 * @param name 型制約名を表す文字列
	 * @param arity 引数の個数
	 */
	public TypeConstraint(Functor functor) {
		this.functor = functor;
		args = new ContextDef[functor.getArity()];
	}

	public String toString() {
		if (args.length == 0) return functor.getAbbrName();
		String argstext = Arrays.asList(args).toString();
		argstext = argstext.substring(1, argstext.length() - 1);
		argstext = argstext.replaceAll(", ",",");
		return functor.getAbbrName() + "(" + argstext + ")";
	}
}
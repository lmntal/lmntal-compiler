package runtime;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

import util.QueuedEntity;
import java.lang.Integer;
//import util.Stack;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
public final class Atom extends QueuedEntity {
	/** 所属膜。AbstractMembraneとそのサブクラスが自由に変更してよい。nullが入ることもある。*/
	AbstractMembrane mem;
	/** ファンクタ（名前とリンク数） */
	private Functor functor;
	/** リンク */
	Link[] args;
	
	private static int lastId = 0;
	private int id;
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * AbstractMembraneのnewAtomメソッド内で呼ばれる。
	 * @param mem 所属膜
	 */
	Atom(AbstractMembrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		args = new Link[functor.getArity()];
		id = lastId++;
	}

	///////////////////////////////
	// 操作
	void setFunctor(Functor newFunctor) {
		if (newFunctor.getArity() > args.length) {
			// TODO SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: insufficient link vector length");
		}
		functor = newFunctor;
	}
	///////////////////////////////
	// 情報の取得

	public String toString() {
		return functor.getName();
	}
	/**
	 * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
	 * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
	 */
	public int hashCode() {
		return id;
	}
	/** このアトムのローカルIDを取得する */
	String getLocalID() {
		return Integer.toString(id);
	}

	/** ファンクタを取得 */
	Functor getFunctor(){
		return functor;
	}
	/** 名前を取得 */
	String getName() {
		return functor.getName();
	}
	/** リンク数を取得 */
	int getArity() {
		return functor.getArity();
	}
	/** 最終引数を取得 */
	Link getLastArg() {
		return args[getArity() - 1];
	}
	/** 第pos引数に格納されたリンクオブジェクトを取得 */
	public Link getArg(int pos) {
		return args[pos];
	}
	/** 所属膜の取得 */
	AbstractMembrane getMem() {
		return mem;
	}
//	/** 所属膜を設定する。AbstractMembraneとそのサブクラスのみ呼び出してよい。*/
//	void setMem(AbstractMembrane mem) {
//		this.mem = mem;
//	}
//	/**@deprecated*/	
//	void remove() {
//		mem.removeAtom(this);
//		mem = null;
//	}
//	/** スタックに入っていれば除去する */
//	public void dequeue() {
//		super.dequeue();
//	}
}

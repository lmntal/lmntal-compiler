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
	/** 所属膜。AbstractMembraneとそのサブクラスが変更してよい。
	 * ただし値を変更するときはindexも同時に更新すること。(null,-1)は所属膜なしを表す。*/
	AbstractMembrane mem;
	/** 所属膜のAtomSet内でのインデックス */
	int index = -1;

	/** ファンクタ（名前とリンク数） */
	private Functor functor;
	/** リンク */
	Link[] args;

	private static int lastId = 0;
	/** このアトムのローカルID */
	private int id;
	
	static void gc() {
		lastId = 0;
	}
	
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
	public void setFunctor(Functor newFunctor) {
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

	/** 所属膜の取得 */
	public AbstractMembrane getMem() {
		return mem;
	}
	/** ファンクタを取得 */
	public Functor getFunctor(){
		return functor;
	}
	/** 名前を取得 */
	public String getName() {
		return functor.getName();
	}
	/** 適切に省略された名前を取得 */
	public String getAbbrName() {
		return functor.getAbbrName();
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
	/** 第 n 引数につながってるアトムの名前を取得する */
	public String nth(int n) {
		return nthAtom(n).getFunctor().getName();
	}
	/** 第 n 引数につながってるアトムを取得する */
	public Atom nthAtom(int n) {
		return args[n].getAtom();
	}
	/** ファンクタ名を変える。（todo 変わらないかもしれないのでsetNameが正しい）
	 * 所属膜がリモートの場合もあり、しかもAtomSetは必ず更新しなければならないので、
	 * 膜のalterAtomFunctorメソッドを呼ぶ。*/
	public void changeName(String name) {
		mem.alterAtomFunctor(this, new Functor(name, getFunctor().getArity()));
	}
	/** けす TODO リンクもけす（その場合、メソッド名を変えて下さい）
	 * 抽象膜クラスにメソッドを作って呼ぶようにするか、または、
	 * このメソッドから抽象膜クラスのメソッドを呼ぶ。とりあえず現状通りの後者でよい。*/
	public void remove() {
		mem.removeAtom(this);
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

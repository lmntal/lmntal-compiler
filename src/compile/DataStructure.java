package compile;

import java.util.List;

import runtime.Functor;

/** ソースコード中のアトムの構造を表すクラス */
final class Atom {
	/** 親膜 */
	Membrane mem;
	Functor functor;
	LinkOccurrence[] args;
	Atom(Membrane mem, String name, int arity) {
		this.mem = mem;
		functor = new Functor(name, arity);
		args = new LinkOccurrence[arity];
	}
}

/** ソースコード中の膜の構造を表すクラス */
final class Membrane {
	/** 親膜 */
	Membrane mem;
	
	//memo:全て1つの配列に入れる方法もある。
	List atoms;
	/** 子膜 */
	List mems;
	List rules;
	List processContexts;
	List ruleContexts;
	List typedProcessContexts;
	
	Membrane(Membrane mem) {
		this.mem = mem;
	}
}

/** ソースコード中のリンクの各出現を表すクラス */
final class LinkOccurrence {
	String name;
	Atom atom;
	int pos;
	/**
	 * ルール中のどの場所に出現しているかを現す。
	 * 定数HEADかBODYのいずれかの値が入る。
	 * ルール外の出現の場合は、初めに１度だけ実行されるルールの右辺とみなす。
	 */
	int place;
	/** 2回しか出現しない場合に、もう片方の出現を保持する */
	LinkOccurrence buddy;
	
	/**
	 * リンク出現を生成する。
	 * @param place 
	 *         定数HEADかBODYのいずれかの値が入る。
	 *         ルール外の出現の場合は、初めに１度だけ実行されるルールの右辺とみなす。
	 */
	LinkOccurrence(String name, Atom atom, int pos, int place) {
		this.name = name;
		this.atom = atom;
		this.pos = pos;
		this.place = place;
	}
	static final int HEAD = 0;
	static final int BODY = 1;
	

	/** この出現が最終リンクの場合にtrueを返す */
	boolean isFunctorRef() { 
		return atom.functor.getArity() == pos;
	}
	/** 自由リンクを閉じる */
	void terminate(Membrane mem) {
		atom = new Atom(null, "*", 1); //todo:ルート膜
		pos = 1;
		atom.args[0] = this;
	}
}
/** ソースコード中のルールの構造を表すクラス */
final class RuleStructure {
	Membrane leftMem, rightMem;
}

/** ProcessContextとRuleContextの親となる抽象クラス */
abstract class Context {
	protected String name;
	protected Context(String name) {
		this.name = name;
	}
	String getName() {
		return name;
	}
	/** 左辺での所属膜 */
	Membrane lhsMem;
	/** 右辺での所属膜の配列 */
	List rhsMems;
	/** 現在の状態。ST_で始まる定数のいずれかの値をとる */
	int status = ST_FRESH;
	/** 初期状態 */
	static final int ST_FRESH = 0;
	/** 左辺に一度出現した状態 */
	static final int ST_LHSOK = 1;
	/** 左辺・右辺両方に出現した状態 */
	static final int ST_READY = 2;
	static final int ST_ERROR = 3;
}
/**
 * ソースコード中のプロセス文脈の構造を表すクラス
 * <br>TODO 型付きプロセス文脈の扱いは？
 */
final class ProcessContext extends Context{
	/**
	 * 引数のリンク束
	 * <br>TODO コンストラクタで設定するのか、メソッドを作るのか</p>
	 */
	private LinkOccurrence[] arg;
	/**
	 * 引数のリンク束
	 * <bf>
	 * TODO コンストラクタで設定するのか、メソッドを作るのか<br>
	 * TODO 専用のクラスを作る？
	 */
	private LinkOccurrence bundle;
	ProcessContext(String name) {
		super(name);
	}
}
/** ソースコード中のルール文脈の構造を表すクラス */
final class RuleContext extends Context{
	RuleContext(String name) {
		super(name);
	}
}

/** なぜかこれが無いとjavadocを作成できない */
class DataStructure {}

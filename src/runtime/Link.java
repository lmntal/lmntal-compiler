package runtime;

/**
 * リンクの接続先を、アトムと引数番号の組として表す。LMNtalのリンクには方向が無いので、
 * １つのリンクに対してこのクラスのインスタンスを２つ使用する。
 */
public final class Link implements Cloneable
{
	private static int lastId = 0;

	/** リンク先のアトム */
	private Atom atom;

	/** リンク先が第何引数か */
	private int pos;

	private int id;

	static void gc() {
		lastId = 0;
	}

	public Link(Atom atom, int pos) {
		set(atom, pos);
		id = lastId++;
	}

	public Object clone() {
		return new Link(atom, pos);
	}

	/** 対になる２つのリンクのidのうち、若い方をリンクの番号として使用する。 */
	public String toString() {
		int i;
		if (this.id < atom.args[pos].id) {
			i = this.id;
		} else {
			i = atom.args[pos].id;
		}
		if (Env.verbose > Env.VERBOSE_SIMPLELINK) return "_" + i;
		return "L" + i;
	}

	/** リンク先のアトムを取得する */
	public Atom getAtom() {
		return atom;
	}

	/** リンク先の引数番号を取得する */
	public int getPos() {
		return pos;
	}

	/** このリンクと対をなす逆向きのリンクを取得する */
	public Link getBuddy() {
		return atom.args[pos];
	}

	/** リンク先が最終リンクの場合にtrueを返す */
	boolean isFuncRef() {
		return atom.getArity() - 1 == pos;
	}

	/** リンクのidを取得する */
	public int getID() {
		return id;
	}

	/**
	 * 接続先を設定する。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Atom atom, int pos) {
		this.atom = atom;
		this.pos = pos;
	}

	/**
	 * このリンクの接続先を、与えられたリンクの接続先と同じにする。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Link link) {
		this.atom = link.atom;
		this.pos = link.pos;
	}
}

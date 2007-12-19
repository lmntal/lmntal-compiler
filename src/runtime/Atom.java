package runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import util.QueuedEntity;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
public final class Atom extends QueuedEntity implements Serializable {
	
	/** 所属膜。AbstractMembraneとそのサブクラスが変更してよい。
	 * ただし値を変更するときはindexも同時に更新すること。(mem,index)==(null, -1)は所属膜なしを表す。
	 */
	Membrane mem;
	
	/** 所属膜のAtomSet内でのインデックス */
	public int index = -1;

	public int getid(){
		return id;
	}
	
	/** ファンクタ（名前とリンク数） */
	private Functor functor;
	
	/** リンク */
	Link[] args;
	
	private static int lastId = 0;
	
	/** このアトムのローカルID */
	int id;
	
	/** リモートホストとの通信で使用されるこのアトムのID。リモート膜に所属するときのみ使用される。
	 * <p>所属膜のキャッシュ受信後、所属膜の連続するロック期間中のみ有効。
	 * キャッシュ受信時に初期化され、引き続くリモートホストへの要求を構築するために使用される。
	 * リモートホストへの要求で新しくアトムが作成されると、ローカルでNEW_が代入される。
	 * $inside_proxyアトムの場合、命令ブロックの返答を受けてリモート側のローカルIDで上書きされる。
	 * $inside_proxy以外のアトムの場合、ロック解除までNEW_のまま放置される。
	 * TODO 廃止する
	 * @see Membrane.atomTable */
//	protected String remoteid;

	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * AbstractMembraneのnewAtomメソッド内で呼ばれる。
	 * @param mem 所属膜
	 */
	public Atom(Membrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		if(functor.getArity() > 0)
			args = new Link[functor.getArity()];
		else
			args = null;
		id = lastId++;
	}

	///////////////////////////////
	// 操作
	public void setFunctor(Functor newFunctor) {
		if (args == null) {
			if (newFunctor.getArity() != 0) {
//				todo SystemError用の例外クラスを投げる
				throw new RuntimeException("SYSTEM ERROR: insufficient link vector length");
			}
		} else if (newFunctor.getArity() > args.length) {
			// todo SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: insufficient link vector length");
		}
		functor = newFunctor;
	}
	/** ファンクタ名を設定する。 */
	public void setName(String name) {
		setFunctor(name, getFunctor().getArity());
	}
	/** ファンクタを設定する。
	 * AtomSetを更新するため、膜のalterAtomFunctorメソッドを呼ぶ。*/
	public void setFunctor(String name, int arity) {
		mem.alterAtomFunctor(this, new SymbolFunctor(name, arity));
	}
	/** アトムを所属膜から取り除く（リンク先のアトムは除去しない）*/
	public void remove() {
		if(Env.fUNYO){
			unyo.Mediator.addRemovedAtom(this, mem.getMemID());
		}
		mem.removeAtom(this);
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
	public Membrane getMem() {
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
	/** リンク数を取得 */
	public int getArity() {
		return functor.getArity();
	}
	/** 最終引数を取得 */
	public Link getLastArg() {
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
	
	///////////////////////////////////////////////////////////////
	
	/* *** *** *** *** *** BEGIN GUI *** *** *** *** *** */
//
//	DoublePoint pos;
//	Double3DPoint pos3d;
//	LMNTransformGroup objTrans;
//	double vx, vy, vz;
	
	public boolean isVisible() {
		return !(functor instanceof SpecialFunctor);
	}

	/**
	 * プロキシを飛ばした実際の隣のアトムを取得する
	 * @param index
	 * @return
	 */
	public Atom getNthAtom(int index) {
		if(null == args[index]){
			return null;
		}
		Atom a = nthAtom(index);
		while(a.getFunctor().isInsideProxy() || a.getFunctor().isOutsideProxy()) {
			a = a.nthAtom(0).nthAtom(1);
		}
		return a;
	}
	
	public int getEdgeCount() {
		return functor.getArity();
	}
	
	/**
	 * このアトムを直列化してストリームに書き出します。
	 * キャッシュ更新や、プロセス文脈の移送の際に利用します。
	 * @param out 出力ストリーム
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		//out.writeInt(id);
		out.writeObject(functor);
		if (functor.equals(Functor.INSIDE_PROXY)) {
			//親膜へのリンクは送信しない
			out.writeObject(args[1]);
		} else if (functor.isOutsideProxy()) {
			//子膜へのリンクは、接続先atomID/memIDのみ送信。接続先はINSIDE_PROXYの第１引数なので、アトムのIDのみで十分。
			Atom a = args[0].getAtom();
			Membrane mem = a.mem;
			out.writeObject(mem.getMemID());
			// n-kato 削除 2006-09-07
			// //ルート膜以外ではグローバルIDが管理されていないので、とりあえず自分で作っている。
			// //todo もっとよい方法を考える
			//out.writeObject(mem.getTask().getMachine().runtimeid);
			//out.writeObject(mem.getLocalID());
			//out.writeInt(a.id);//todo 膜の何番目の自由リンクかを転送しなければならない
			out.writeObject(args[1]);
		} else {
			out.writeObject(args);
		}
	}
	
	/**
	 * このアトムの内容をストリームから復元します。
	 * キャッシュ更新や、プロセス文脈の移送の際に利用します。
	 * TODO OUTSIDE_PROXYを正しく処理する
	 * @param in 入力ストリーム
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		id = lastId++;
//		remoteid = Integer.toString(in.readInt());
		functor = (Functor)in.readObject();
		args = new Link[functor.getArity()];
		if (functor.equals(Functor.INSIDE_PROXY)) {
			//とりあえず復元しておく。後でRemoteMembrane内でつなぎ直され、このアトムは使われなくなる。
			args[1] = (Link)in.readObject();
		} else if (functor.isOutsideProxy()) {
			//子膜内のINSIDE_PROXYは送信されてこないので、ここで生成する。
			String globalid = (String)in.readObject();
//			String localid = (String)in.readObject();
//			String globalid = hostname + ":" + localid;
			Membrane mem = null; 		// todo 擬似膜が必要
//			mem.globalid = globalid;	// globalidは文字列フィールドにしなければならなくなるのか？
//			AbstractMembrane mem = IDConverter.lookupGlobalMembrane(globalid);
			//IDConverterには、RemoteMembrane.updateCache()で登録済みのはず。
			Atom inside = new Atom(mem, Functor.INSIDE_PROXY);
			mem.atoms.add(inside);
			
//			inside.remoteid = Integer.toString(in.readInt());
			args[0] = new Link(inside, 0);
			inside.args[0] = new Link(this, 0);
			//insideの第2引数の接続先は、架空のアトムで終端したほうがよいかも。
			args[1] = (Link)in.readObject();
		} else {
			args = (Link[])in.readObject();
		}
	}
}

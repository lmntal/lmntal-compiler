package runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.Map;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

//import util.QueuedEntity;
//import java.lang.Integer;
//import util.Stack;

/**
 * リンクの接続先を、アトムと引数番号の組として表す。LMNtalのリンクには方向が無いので、
 * １つのリンクに対してこのクラスのインスタンスを２つ使用する。
 */
public final class Link implements Cloneable, Serializable {
	/** リンク先のアトム */
	private Atom atom;
	/** リンク先が第何引数か */
	private int pos;

	private static int lastId = 0;
	private int id;
	
	static void gc() {
		lastId = 0;
	}
	
	///////////////////////////////
	// コンストラクタ
	
	public Link(Atom atom, int pos) {
		set(atom, pos);
		id = lastId++;
	}

	public Object clone() {
		return new Link(atom, pos);
	}

	///////////////////////////////
	// 情報の取得

	/** 対になる２つのリンクのidのうち、若い方をリンクの番号として使用する。 */
	public String toString() {
		int i;
		if (this.id < atom.args[pos].id) {
			i = this.id;
		} else {
			i = atom.args[pos].id;
		}
		return "_" + i;
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
	Link getBuddy() {
		return atom.args[pos];
	}
	/** リンク先が最終リンクの場合にtrueを返す */
	boolean isFuncRef() {
		return atom.getArity() - 1 == pos;
	}
	
	/** 
	 * by kudo
	 * 再帰的に基底項プロセスかどうかを検査する。
	 * リンク先を辿って出会った、srcSetに未登録のアトムの数を返す。
	 * ただし、自由リンク管理アトムに出会った場合は、-1を返す。
	 * @param srcSet 基底項プロセスを構成するアトムのSet
	 * @param avoSet 基底項プロセスに出てきてはいけないアトムのSet
	 * @return 基底項プロセスを構成するアトム数
	 */
	public int isGround(Set srcSet,Set avoSet){
		if(srcSet.contains(atom))return 0; //基底項を構成するアトムに辿りついたら
		if(avoSet.contains(atom))return -1; //避けるべきアトムに辿り着いたら
		if(atom.getFunctor().equals(Functor.INSIDE_PROXY)||
			atom.getFunctor().equals(Functor.OUTSIDE_PROXY))
			return -1; //自由リンク管理アトムに出会ったら失敗
		srcSet.add(atom); // 自分を追加
		int ac=1; //構成するアトム数:まず自分を含む
		for(int i=0;i<atom.getArity();i++){
			if(i==pos)continue; // 来し方は辿らない
			int gr=atom.getArg(i).isGround(srcSet,avoSet);
			if(gr == -1)return -1; //どっかで$in,$outに出会ったら失敗
			else ac+=gr; //各リンク先に新しく検出されたアトム数を合計する
		}
		return ac;
	}
	
	/**
	 * by kudo
	 * 再帰的に同じ構造を持った基底項プロセスかどうか検査する
	 * どちらか片方についてgroundかどうかの検査は済んでいるものとする
	 * @param srcLink 比較対象のリンク
	 * @param srcMap 比較元アトムから比較先アトムへのmap
	 * @return
	 */
	public boolean eqGround(Link srcLink,Map srcMap){
		if(srcLink.getPos() != pos)return false; // これは不要か?
		if(!srcLink.getAtom().getFunctor().equals(atom.getFunctor()))return false;
		if(!srcMap.containsKey(atom))srcMap.put(atom,srcLink.getAtom());
		else if(srcMap.get(atom) != srcLink.getAtom())return false;
		else return true;
		boolean flgequal = true;
		for(int i=0;i<atom.getArity();i++){
			if(i==pos)continue;
			flgequal &= atom.getArg(i).eqGround(srcLink.getAtom().getArg(i),srcMap);
			if(!flgequal)return false;
		}
		return flgequal;
	}

	///////////////////////////////
	// 操作
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

	/**
	 * 直列化復元時に呼ばれる。
	 * @param in 読み込むストリーム
	 * @throws IOException 入出力エラーが発生した場合
	 * @throws ClassNotFoundException 直列化されたオブジェクトのクラスが見つからなかった場合
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		id = lastId++;
	}
}
